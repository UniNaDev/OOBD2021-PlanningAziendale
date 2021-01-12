/*TRIGGER PER INVITO DELL'ORGANIZZATORE AL MEETING
**Ad ogni insert di un meeting aggiungi il suo organizzatore tra gli invitati.
*****************************************************************************/

--FUNZIONE
CREATE OR REPLACE FUNCTION invita_organizzatore() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
BEGIN
--Inserisce in Presenza l'organizzatore del meeting
INSERT INTO Presenza
VALUES (NEW.Organizzatore,NEW.IDMeeting,FALSE);
RETURN NEW;
END;
$$;
-------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER organizzatore_invitato AFTER INSERT ON Meeting
FOR EACH ROW
EXECUTE PROCEDURE invita_organizzatore();
--------------------------------------------------------------