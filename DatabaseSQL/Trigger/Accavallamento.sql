/*TRIGGER PER NO ACCAVALLAMENTI DI MEETING FISICI IN SALE COMUNI
**Ad ogni update o insert di meeting fisici nella tabella Meeting controlla che
**i meeting con la stessa sala non finiscano per accavallarsi con i tempi.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
*******************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_accavallamenti_sale() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
OLDMeeting RECORD;
BEGIN
	--Se esistono altri record di meeting fisici con la stessa sala del nuovo record
	IF (EXISTS(SELECT m.IDMeeting
		FROM Meeting AS m
		WHERE m.CodSala=NEW.CodSala AND m.Modalità='Fisico' AND m.DataFine >= CURRENT_DATE AND m.OrarioFine >= CURRENT_TIME)) THEN
		--Per ogni meeting con la stessa sala che non sia quello nuovo
		FOR OLDMeeting IN
			SELECT *
			FROM Meeting AS m
			WHERE m.CodSala=NEW.CodSala AND m.Modalità = 'Fisico' AND m.IDMeeting <> NEW.IDMeeting
		LOOP
			--Controlla che non si accavallino gli orari
			IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEW.DataInizio,NEW.DataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEW.OrarioInizio,NEW.OrarioFine)) THEN
				RAISE EXCEPTION 'ERRORE: Ci sono degli accavallamenti con il meeting di ID %', OLDMeeting.IDMeeting
				USING 
					HINT = 'Per favore controlla gli orari o cambia sala per questo meeting.',
					ERRCODE = 'P0001';
				RETURN OLD;
			END IF;
		END LOOP;
	END IF;
RETURN NEW;
END;
$$;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--TRIGGER
CREATE TRIGGER no_accavallamenti BEFORE INSERT OR UPDATE ON Meeting
FOR EACH ROW
WHEN (NEW.Modalità='Fisico')
EXECUTE PROCEDURE check_accavallamenti_sale();
--------------------------------------------------------------------