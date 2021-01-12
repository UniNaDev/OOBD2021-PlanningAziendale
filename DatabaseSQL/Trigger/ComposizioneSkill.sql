/*TRIGGER PER ELIMINAZIONE SKILL NON NECESSARIE
**Quando avviene un delete nella tabella Ablità (quindi un dipendente perde una skill)
**controlla se la skill appartiene ancora a qualche dipendente altrimenti eliminala
************************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_skill_existence() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
BEGIN
IF OLD.IDSkill NOT IN (
	SELECT a.IDSkill
	FROM Abilità AS a
) THEN
	DELETE
	FROM Skill AS s
	WHERE s.IDSkill = OLD.IDSkill;
END IF;
RETURN NEW;
END;
$$;
-------------------------------------------------------

--TRIGGER
CREATE TRIGGER composizione_skill AFTER DELETE ON Abilità
FOR EACH ROW
EXECUTE PROCEDURE check_skill_existence();
---------------------------------------------------------