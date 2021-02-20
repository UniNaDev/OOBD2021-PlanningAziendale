/*TRIGGER PER CAPIENZA DELLE SALE RISPETTATE (PRESENZA)
**Ad ogni insert/update in Presenza controlla che
**il numero di invitati nel meeting fisico sia minore o uguale
**alla capienza della sala in cui avviene.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
*********************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_capienza_presenza() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
Cap SalaRiunione.Capienza%TYPE;
BEGIN
--Controlla che il meeting inserito/modificato sia fisico
IF ((SELECT m.Modalità
	FROM Meeting AS m
	WHERE m.IDMeeting = NEW.IDMeeting AND m.DataFine >= CURRENT_DATE AND m.OrarioFine >= CURRENT_TIME) = 'Fisico') THEN
		--Salva la capienza della sala
		SELECT s.Capienza INTO Cap
		FROM Meeting AS m NATURAL JOIN SalaRiunione AS s
		WHERE m.IDMeeting = NEW.IDMeeting;
		--Controlla che il numero di partecipanti al meeting non superi la capienza
		IF ((SELECT COUNT(p.CF)
			FROM Presenza AS p
			WHERE p.IDMeeting = NEW.IDMeeting
			GROUP BY p.IDMeeting) > Cap) THEN
				RAISE EXCEPTION 'Il numero di invitati al meeting supera la capienza (%) della sala stabilita.', Cap
				USING 
					HINT = 'Si consiglia di cambiare sala o di rimuovere qualche partecipante.',
					ERRCODE = 'P0002';
				RETURN NEW;
		END IF;
END IF;
RETURN NEW;
END;
$$;
----------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER capienza_rispettata_presenza AFTER INSERT OR UPDATE ON Presenza
FOR EACH ROW
EXECUTE PROCEDURE check_capienza_presenza();
--------------------------------------------------------------------------------

/*TRIGGER PER CAPIENZA RISPETTATA NELLE SALE (MEETING)
**Ad ogni update nella tabella Meeting di un meeting fisico nella colonna codice sala controlla che
**i partecipanti a quel meeting non superino la capienza della nuova sala scelta.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
***************************************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_capienza_meeting() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
Cap SalaRiunione.Capienza%TYPE;
BEGIN
--Se il meeting non è già terminato
IF (NEW.DataFine >= CURRENT_DATE AND NEW.OraFine >= CURRENT_TIME) THEN
	--Salva la capienza della nuova sala
	SELECT s.Capienza INTO Cap
	FROM SalaRiunione AS s
	WHERE s.CodSala = NEW.CodSala;
	--Controlla che il numero di partecipanti al meeting non superi la capienza della nuova sala
	IF ((SELECT COUNT(p.CF)
		FROM Presenza AS p 
		WHERE p.IDMeeting = NEW.IDMeeting
		GROUP BY p.IDMeeting) > Cap) THEN
			RAISE EXCEPTION 'Il numero di invitati al meeting supera la capienza (%) della nuvova sala %', Cap, NEW.CodSala
			USING 
				HINT = 'Si consiglia di cambiare sala o di rimuovere qualche partecipante.',
				ERRCODE = 'P0002';
			RETURN NEW;
	END IF;
END IF;
RETURN NEW;
END;
$$;
------------------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER capienza_rispettata_meeting AFTER UPDATE OF CodSala ON Meeting
FOR EACH ROW
EXECUTE PROCEDURE check_capienza_meeting();
-----------------------------------------------------------------------------
