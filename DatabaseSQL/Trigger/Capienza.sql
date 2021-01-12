/*TRIGGER PER CAPIENZA DELLE SALE RISPETTATE (PRESENZA)
**Ad ogni insert/update in Presenza controlla che
**il numero di invitati nel meeting fisico sia minore o uguale
**alla capienza della sala in cui avviene. Altrimenti invia
**un avvertimento che avvisa del superamento della capienza massima e
**consiglia di cambiare sala.
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
	WHERE m.IDMeeting = NEW.IDMeeting) = 'Fisico') THEN
		--Salva la capienza della sala
		SELECT s.Capienza INTO Cap
		FROM Meeting AS m NATURAL JOIN SalaRiunione AS s
		WHERE m.IDMeeting = NEW.IDMeeting;
		--Controlla che il numero di partecipanti al meeting non superi la capienza
		IF ((SELECT COUNT(p.CF)
			FROM Presenza AS p
			WHERE p.IDMeeting = NEW.IDMeeting
			GROUP BY p.IDMeeting) > Cap) THEN
				RAISE WARNING 'Il numero di invitati al meeting supera la capienza (%) della sala stabilita', Cap
				USING HINT = 'Si consiglia di cambiare sala';
				RETURN NEW;
		END IF;
END IF;
RETURN NEW;
END;
$$;
----------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER capienza_rispettata_presenza BEFORE INSERT OR UPDATE ON Presenza
FOR EACH ROW
EXECUTE PROCEDURE check_capienza_presenza();
--------------------------------------------------------------------------------

/*TRIGGER PER CAPIENZA RISPETTATA NELLE SALE (MEETING)
**Ad ogni update nella tabella Meeting di un meeting fisico nella colonna codice sala controlla che
**i partecipanti a quel meeting non superino la capienza della nuova sala scelta.
**Altrimenti invia un warning.
***************************************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_capienza_meeting() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
Cap SalaRiunione.Capienza%TYPE;
BEGIN
--Salva la capienza della nuova sala
SELECT s.Capienza INTO Cap
FROM SalaRiunione AS s
WHERE s.CodSala = NEW.CodSala;
--Controlla che il numero di partecipanti al meeting non superi la capienza della nuova sala
IF ((SELECT COUNT(p.CF)
	FROM Presenza AS p 
	WHERE p.IDMeeting = NEW.IDMeeting
	GROUP BY p.IDMeeting) > Cap) THEN
		RAISE WARNING 'Il numero di invitati al meeting supera la capienza (%) della nuvova sala %', Cap, NEW.CodSala
		USING HINT = 'Si consiglia di cambiare sala';
		RETURN NEW;
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
