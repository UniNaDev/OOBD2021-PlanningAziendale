--TABELLA ABILITA'
-------------------------------------------------------------------------
CREATE TABLE abilità(
	Skill INTEGER,
	Dipendente CHAR(16),
	CONSTRAINT pk_abilità PRIMARY KEY(Skill,Dipendente),
	CONSTRAINT fk_dip FOREIGN KEY (Dipendente) REFERENCES Dipendente (CF),
	CONSTRAINT fk_skill FOREIGN KEY (Skill) REFERENCES Skill (IDSkill)
);
---------------------------------------------------------------------------