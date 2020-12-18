--TABELLA SALA RIUNIONE
--------------------------------------------------------
CREATE TABLE SalaRiunione(
	CodSala CHAR(3) PRIMARY KEY,
	Capienza INTEGER NOT NULL,
	IndirizzoSede VARCHAR(50) NOT NULL,
	Piano INTEGER NOT NULL,
	CONSTRAINT capienza_positiva CHECK (Capienza>=0),
	CONSTRAINT piano_positivo CHECK (Piano>=0)
);
-------------------------------------------------------