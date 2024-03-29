/*TRIGGER PER ONNIPRESENZA DEI DIPENDENTI NEI MEETING (PRESENZA)
**Ad ogni insert o update di Presenza controlla che
**i meeting di quel dipendente non si accavallino con il nuovo.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
****************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_onnipresenza_presenza()
RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
OLDMeeting RECORD; --record d'appoggio per i meeting da confrontare
NEWDataInizio DATE; --data inizio del meeting aggiunto/modificato in Presenza
NEWDataFine DATE; --data fine //
NEWOraInizio TIME; --ora inizio //
NEWOraFine TIME; --ora fine //
BEGIN
--Controlla se ci sono altri record in Presenza con lo stesso dipendente del nuovo record
IF (EXISTS(SELECT p.IDMeeting
	FROM Presenza AS p
	WHERE p.CF=NEW.CF)) THEN
	--Salva i valori del nuovo record necessari ai confronti con gli altri meeting
	SELECT m.DataInizio,m.DataFine,m.OrarioInizio,m.OrarioFine INTO NEWDataInizio,NEWDataFine,NEWOraInizio,NEWOraFine
	FROM Presenza AS p NATURAL JOIN Meeting AS m
	WHERE p.IDMeeting=NEW.IDMeeting;
	--Per ogni meeting non terminato dello stesso dipendente
	FOR OLDMeeting IN
		SELECT *
		FROM Presenza AS p NATURAL JOIN Meeting AS m
		WHERE p.CF = NEW.CF AND NEW.IDMeeting <> p.IDMeeting AND m.DataFine >= CURRENT_DATE AND OrarioFine >= CURRENT_TIME
	LOOP
		--Controlla che non si accavalli con quello nuovo
		IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEWDataInizio,NEWDataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEWOraInizio,NEWOraFine)) THEN
			RAISE EXCEPTION 'Il dipendente % ha il meeting % che si accavalla con questo',NEW.CF,OLDMeeting.IDMeeting
			USING 
				HINT = 'Cambia il meeting.',
				ERRCODE = 'P0003';
			RETURN OLD;
		END IF;
	END LOOP;
END IF;
RETURN NEW;
END;
$$;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER no_onnipresenza_presenza AFTER INSERT OR UPDATE ON Presenza
FOR EACH ROW
EXECUTE PROCEDURE check_onnipresenza_presenza();
--------------------------------------------------------------------------

/*TRIGGER PER ONNIPRESENZA DEI DIPENDENTI NEI MEETING (MEETING)
**Ad ogni update della tabella Meeting controlla che
**ciascun partecipante al meeting modificato non abbia altri
**meeting che si accavallano con quello appena modifica.
**Nel caso evoca un'eccezione e non autorizza l'operazione di update.
**********************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_onnipresenza_meeting()
RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
dip Dipendente.CF%TYPE; --variabile d'appoggio (CF) dei dipendenti partecipanti al meeting modificato per cui bisogna controllare eventuali accavallamenti
OLDMeeting RECORD; --record d'appoggio per i meeting da confrontare in cerca di accavallamento con il meeting modificato
BEGIN
--Per ogni dipendente che partecipa al meeting aggiornato
FOR dip IN
	SELECT p.CF
	FROM Presenza AS p
	WHERE p.IDMeeting = NEW.IDMeeting
LOOP
--Per ogni meeting non terminato a cui esso partecipa
	FOR OLDMeeting IN
		SELECT *
		FROM Presenza AS p NATURAL JOIN Meeting AS m
		WHERE m.IDMeeting <> NEW.IDMeeting AND p.CF=dip AND m.DataFine >= CURRENT_DATE AND m.OrarioFine >= CURRENT_TIME
	LOOP
		--Controlla se si accavalla con il meeting aggiornato
		IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEW.DataInizio,NEW.DataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEW.OrarioInizio,NEW.OrarioFine)) THEN
			RAISE EXCEPTION 'Il dipendente % potrebbe avere problemi di accavallamento con il meeting di ID %', dip, OLDMeeting.IDMeeting
			USING 
				HINT = 'Cambia il meeting.',
				ERRCODE = 'P0003';
			RETURN OLD;
		END IF;
	END LOOP;
END LOOP;
RETURN NEW;
END;
$$;
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER no_onnipresenza_meeting AFTER UPDATE ON Meeting
FOR EACH ROW
EXECUTE PROCEDURE check_onnipresenza_meeting();
----------------------------------------------------------------