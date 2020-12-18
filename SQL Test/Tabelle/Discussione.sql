--TABELLA DISCUSSIONE
----------------------------------------------------------------------------------------
CREATE TABLE Discussione(
	Meeting INTEGER,
	Progetto INTEGER,
	CONSTRAINT pk_discussione PRIMARY KEY (Meeting,Progetto),
	CONSTRAINT fk_meeting FOREIGN KEY (Meeting) REFERENCES Meeting (IDMeeting),
	CONSTRAINT fk_progetto FOREIGN KEY (Progetto) REFERENCES Progetto (CodProgetto)
);
---------------------------------------------------------------------------------------