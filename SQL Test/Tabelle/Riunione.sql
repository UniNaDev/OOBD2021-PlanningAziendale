--TABELLA RIUNIONE
-------------------------------------------------------------------------------------
CREATE TABLE Riunione(
	Meeting INTEGER,
	Sala CHAR(3),
	CONSTRAINT pk_riunione PRIMARY KEY (Meeting,Sala),
	CONSTRAINT fk_meeting FOREIGN KEY (Meeting) REFERENCES Meeting (IDMeeting),
	CONSTRAINT fk_salariunione FOREIGN KEY (Sala) REFERENCES SalaRiunione (CodSala)
);
-------------------------------------------------------------------------------------