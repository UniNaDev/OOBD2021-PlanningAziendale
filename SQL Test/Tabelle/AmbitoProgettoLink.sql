--TABELLA AMBITOPROGETTOLINK
-------------------------------------------------------------------------------------
CREATE TABLE AmbitoProgettoLink(
	Ambito INTEGER,
	Progetto INTEGER,
	CONSTRAINT pk_AmbitoProgettoLink PRIMARY KEY (Ambito,Progetto),
	CONSTRAINT fk_Ambito FOREIGN KEY (Ambito) REFERENCES AmbitoProgetto (IDAmbito),
	CONSTRAINT fk_Progetto FOREIGN KEY (Progetto) REFERENCES Progetto (CodProgetto)
);
-------------------------------------------------------------------------------------