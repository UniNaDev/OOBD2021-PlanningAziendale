--TABELLA PRESENZA
------------------------------------------------------------------------------------
CREATE TABLE Presenza(
	Meeting INTEGER,
	Dipendente CHAR(16),
	CONSTRAINT pk_presenza PRIMARY KEY (Meeting,Dipendente),
	CONSTRAINT fk_dipendente FOREIGN KEY (Dipendente) REFERENCES Dipendente (CF),
	CONSTRAINT fk_meeting FOREIGN KEY (Meeting) REFERENCES Meeting (IDMeeting)
);
------------------------------------------------------------------------------------