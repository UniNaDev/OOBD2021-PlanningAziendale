/*TRIGGER PER UNICITA' DEL PROJECT MANAGER PER OGNI PROGETTO
**Ad ogni insert/update della tabella Partecipazione controlla che
**ogni progetto nella tabella comprenda un solo project manager.
**Altrimenti evoca un'eccezione e non autorizza l'operazione.
*******************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_projectmanager() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
BEGIN
--Controlla che non ci siano altri record in Partecipazione con stesso progetto e ruolo project manager
IF (EXISTS (SELECT p.CF
			FROM Partecipazione AS p
			WHERE p.CodProgetto = NEW.CodProgetto AND p.RuoloDipendente = 'Project Manager' AND p.ruoloDipendente = NEW.ruolodipendente)) THEN
				RAISE EXCEPTION 'Esiste già un project manager per il progetto di codice %', NEW.CodProgetto
				USING
					ERRCODE = 'P0004';
				RETURN OLD;
END IF;
RETURN NEW;
END;
$$;
--------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER unicità_projectmanager BEFORE INSERT OR UPDATE ON Partecipazione
FOR EACH ROW
EXECUTE PROCEDURE check_projectmanager();
--------------------------------------------------------------------------------