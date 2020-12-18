--SEQUENCE PER SKILL
-------------------------------
CREATE SEQUENCE skill_serial
START WITH 1
INCREMENT BY 1;
-------------------------------

--TABELLA SKILL
---------------------------------------------------------------
CREATE TABLE Skill(
	IDSKill INTEGER PRIMARY KEY DEFAULT nextval('skill_serial'),
	NomeSkill VARCHAR(50) NOT NULL
);
--------------------------------------------------------------