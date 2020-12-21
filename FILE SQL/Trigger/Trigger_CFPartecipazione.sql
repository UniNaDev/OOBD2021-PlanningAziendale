CREATE OR REPLACE FUNCTION Partecipazione_uppercase() RETURNS TRIGGER
LANGUAGE PLPGSQL 
AS $$
BEGIN
  NEW.CF = UPPER(NEW.CF);  
  RETURN NEW;
END;
$$;
  

CREATE TRIGGER CF_uppercase_Partecipazione
  BEFORE INSERT OR UPDATE
  ON Partecipazione
  FOR EACH ROW
  EXECUTE PROCEDURE Partecipazione_uppercase();