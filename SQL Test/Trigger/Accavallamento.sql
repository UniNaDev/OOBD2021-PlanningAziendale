/*TRIGGER PER ACCAVALLAMENTI DELLE RIUNIONI FISICHE
**Ad ogni insert o update della tabella Riunione (quindi se vengono create nuove riunioni fisiche o modificate)
**controlla che non avvengano nella stessa sala riunione e contemporaneamente che non avvengano nello stesso orario.
**Nel caso evocca un'eccezione e non accetta l'insert/update.
**Ad ogni update nella tabella Meeting per le colonne relative a date e orari di inizio/fine, se i meeting sono fisici,
**controlla che nella tabella Riunione non si vengano a creare accavallamenti, altrimenti evoca un'eccezione e non
**accetta l'operazione.
*******************************************************************************************************************/

--FUNCTION PER RIUNIONE
CREATE OR REPLACE FUNCTION check_accavallamenti_riunione() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
DataInizioNuovo DATE;
DataFineNuovo DATE;
OraInizioNuovo TIME;
OraFineNuovo TIME;
temp RECORD;
BEGIN

SELECT m.DataInizio, m.DataFine, m.OrarioInizio, m.OrarioFine INTO DataInizioNuovo,DataFineNuovo,OraInizioNuovo,OraFineNuovo
FROM Meeting AS m
WHERE m.IDMeeting = NEW.Meeting;

--Se la sala usata nell'insert/update già era usata per altre riunioni fisiche
IF ((SELECT COUNT(r.Sala)
	FROM Riunione AS r
	WHERE r.Sala=NEW.Sala
	GROUP BY r.Sala) >= 2) THEN
	FOR temp IN
	SELECT *
	FROM Riunione JOIN Meeting ON (Riunione.Meeting = Meeting.IDMeeting)
	WHERE Riunione.Sala = NEW.Sala
	LOOP
	--Controlla se hanno stesse date e stessi orari
	IF (temp.DataInizio = DataInizioNuovo AND temp.DataFine = DataFineNuovo AND ((OraFineNuovo>temp.OrarioInizio AND OraFineNuovo<=temp.OrarioFine) OR (OraInizioNuovo>=temp.OrarioInizio AND OraFineNuovo > temp.OrarioFine) OR (OraInizioNuovo < temp.OrarioInizio AND OraFineNuovo>temp.OrarioFine))) THEN
		RAISE EXCEPTION 'Errore: più meeting si accavallano nella stessa sala.';
		RETURN OLD;
	END IF;
	END LOOP;
END IF;
RETURN NEW;
END;
$$;
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER IN RIUNIONE
CREATE TRIGGER no_accavallamenti_riunione AFTER INSERT OR UPDATE ON Riunione
FOR EACH ROW
EXECUTE PROCEDURE check_accavallamenti_riunione();
-----------------------------------------------------------------------------

--FUNCTION PER MEETING
CREATE OR REPLACE FUNCTION check_accavallamenti_meeting() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
SalaNuovo SalaRiunione.CodSala%TYPE;
temp RECORD;
BEGIN
--Ottieni codice sala del meeting modificato
SELECT Riunione.Sala INTO SalaNuovo FROM Riunione WHERE Meeting=NEW.IDMeeting;

IF ((SELECT COUNT(r.Meeting)
   FROM Riunione AS r
	WHERE r.Sala = SalaNuovo
   GROUP BY r.Sala) >= 2) THEN --Se in Riunione il meeting modificato ha più istanze nella stessa sala
		FOR temp IN
			SELECT *
			FROM Riunione JOIN Meeting ON (Riunione.Meeting = Meeting.IDMeeting)
			WHERE Riunione.Sala = SalaNuovo AND Riunione.Meeting<>NEW.IDMeeting
		LOOP
		--Controlla che gli orari e le date non coincidano
		IF (temp.DataInizio = NEW.DataInizio AND temp.DataFine = NEW.DataFine AND (NEW.OrarioFine>temp.OrarioInizio AND NEW.OrarioFine<=temp.OrarioFine) OR (NEW.OrarioInizio>=temp.OrarioInizio AND NEW.OrarioFine > temp.OrarioFine) OR (NEW.OrarioInizio < temp.OrarioInizio AND NEW.OrarioFine>temp.OrarioFine)) THEN
		RAISE EXCEPTION 'Errore: più meeting si accavallano nella stessa sala.';
		RETURN OLD;
		END IF;
		END LOOP;
END IF;
RETURN NEW;
END;
$$;
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER PER MEETING
CREATE TRIGGER no_accavallamenti_meeting AFTER UPDATE OF DataInizio,DataFine,OrarioInizio,OrarioFine ON Meeting
FOR EACH ROW
WHEN (NEW.Modalità='Fisico')
EXECUTE PROCEDURE check_accavallamenti_meeting();
----------------------------------------------------------------------------------------------------------------