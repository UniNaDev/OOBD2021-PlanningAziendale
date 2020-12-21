/*TRIGGER PER ONNIPRESENZA DEI DIPENDENTI
**In caso di insert o update nella tabella presenza (quindi con aggiunta o modifica di partecipanti ai meeting)
**controlla che i meeting a cui deve partecipare il dipendente del nuovo record non si accavallino e quindi che
**il dipendente non può essere presente contemporaneamente in più meeting. Se così fosse invia un warning riguardo
**possibili coincidenze temporali e accetta comunque l'insert/update.
**Se avviene un update in meeting invece controlla che ogni partecipante non abbia altri meeting che avvengono
**nello stesso periodo temporale. Nel caso avvisa che alcuni dipendenti hanno problemi di coincidenze temporali,
**ma accetta l'update.
*****************************************************************************************************************/

--FUNCTION PER PRESENZA
CREATE OR REPLACE FUNCTION check_onnipresenza_presenza() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
temp RECORD;
DataInizioNuovo DATE;
DataFineNuovo DATE;
OraInizioNuovo TIME;
OraFineNuovo TIME;
BEGIN
--Salva valori di data/orario inizio/fine del meeting del nuovo record
SELECT Meeting.DataInizio, Meeting.DataFine, Meeting.OrarioInizio, Meeting.OrarioFine INTO DataInizioNuovo,DataFineNuovo,OraInizioNuovo,OraFineNuovo
FROM Meeting
WHERE Meeting.IDMeeting = NEW.Meeting;

--Controlla che ci siano altri record in Presenza con stesso dipendente (se ci sono almeno 2 record con quel dipendente)
IF ((SELECT COUNT(Presenza.Meeting)
   FROM Presenza
   WHERE Presenza.Dipendente=NEW.Dipendente
   GROUP BY Presenza.Dipendente) >= 2) THEN
	FOR temp IN
		SELECT *
		FROM Presenza AS p JOIN Meeting AS m ON (p.Meeting=m.IDMeeting)
		WHERE p.Dipendente=NEW.Dipendente AND m.IDMeeting<>NEW.Meeting
	LOOP
	--Controlla che i meeting non si accavallino con quello nuovo
	IF (temp.DataInizio=DataInizioNuovo AND temp.DataFine=DataFineNuovo AND ((OraFineNuovo>=temp.OrarioInizio AND OraFineNuovo<temp.OrarioFine)OR(OraInizioNuovo<temp.OrarioFine AND OraFineNUovo>=temp.OrarioFine))) THEN
		RAISE WARNING 'Attenzione: un dipendente potrebbe avere altri meeting che si accavallano';
		RETURN NEW;
	END IF;
	END LOOP;
END IF;
RETURN NEW;
END;
$$;
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER PER PRESENZA
CREATE TRIGGER no_onnipresenza_presenza AFTER INSERT OR UPDATE ON Presenza
FOR EACH ROW
EXECUTE PROCEDURE check_onnipresenza_presenza();
---------------------------------------------------------------------------

--FUNCTION PER MEETING
CREATE OR REPLACE FUNCTION check_onnipresenza_meeting() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
dip Dipendente.CF%TYPE;
temp RECORD;
BEGIN
--Per ogni dipendente partecipante al meeting modificato
FOR dip IN
	SELECT Presenza.Dipendente
	FROM Presenza
	WHERE Presenza.Meeting = NEW.IDMeeting
LOOP
	--per ogni meeting a cui esso partecipa
	FOR temp IN
		SELECT *
		FROM Presenza JOIN Meeting ON (Presenza.Meeting = Meeting.IDMeeting)
		WHERE Presenza.Dipendente=dip
	LOOP
	--controlla che non si accavalli con il meeting modificato
	IF (NEW.DataInizio=temp.DataInizio AND NEW.DataFine=temp.DataFine AND ((NEW.OrarioFine>=temp.OrarioInizio AND NEW.OrarioFine<temp.OrarioFine)OR(NEW.OrarioInizio<temp.OrarioFine AND NEW.OrarioFine>=temp.OrarioFine))) THEN
		RAISE WARNING 'Attenzione: un dipendente potrebbe avere altri meeting che si accavallano';
		RETURN NEW;
	END IF;
	END LOOP;
END LOOP;
RETURN NEW;
END;
$$;
---------------------------------------------------------------------

--TRIGGER PER MEETING
CREATE TRIGGER no_onnipresenza_meeting AFTER UPDATE ON Meeting
FOR EACH ROW
EXECUTE PROCEDURE check_onnipresenza_meeting();
---------------------------------------------------------------