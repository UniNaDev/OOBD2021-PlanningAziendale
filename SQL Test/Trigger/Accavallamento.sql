/*TRIGGER PER ACCAVALLAMENTI DELLE RIUNIONI FISICHE
**Ad ogni insert o update della tabella Riunione (quindi se vengono create nuove riunioni fisiche o modificate)
**controlla che non avvengano nella stessa sala riunione e contemporaneamente che non avvengano nello stesso orario.
**Nel caso evocca un'eccezione e non accetta l'insert/update.
*******************************************************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_accavallamenti_meeting() RETURNS TRIGGER
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
	IF (temp.DataInizio = DataInizioNuovo AND temp.DataFine = DataFineNuovo AND ((OraFineNuovo BETWEEN temp.OrarioInizio AND temp.OrarioFine) OR (OraInizioNuovo>=temp.OrarioInizio AND OraFineNuovo > temp.OrarioFine) OR (OraInizioNuovo < temp.OrarioInizio AND OraFineNuovo>temp.OrarioFine))) THEN
		RAISE EXCEPTION 'Errore: più meeting si accavallano nella stessa sala.';
		RETURN OLD;
	END IF;
	END LOOP;
END IF;
RETURN NEW;
END;
$$
-------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER no_accavallamenti AFTER INSERT OR UPDATE ON Riunione
FOR EACH ROW
EXECUTE PROCEDURE check_accavallamenti_meeting();
--------------------------------------------------------------------