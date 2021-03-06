CREATE OR REPLACE FUNCTION Presenza_uppercase() RETURNS TRIGGER
LANGUAGE PLPGSQL 
AS $$
BEGIN
  NEW.CF = UPPER(NEW.CF);  
  RETURN NEW;
END;
$$;
  

CREATE TRIGGER CF_uppercase_Presenza
  BEFORE INSERT OR UPDATE
  ON Presenza
  FOR EACH ROW
  EXECUTE PROCEDURE Presenza_uppercase();