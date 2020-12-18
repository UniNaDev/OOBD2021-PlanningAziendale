--SEQUENCE PER MEETING
-----------------------------------
CREATE SEQUENCE meeting_serial
START WITH 1
INCREMENT BY 1;
-----------------------------------

--ENUM PER MODALITA' MEETING
-------------------------------------------------------
CREATE TYPE modalità AS ENUM ('Telematico','Fisico');
-------------------------------------------------------

--ENUM PER PIATTAFORMA MEETING
-----------------------------------------------------------------------------------
CREATE TYPE piattaforma AS ENUM ('Microsoft Teams','Discord','Google Meet','Zoom');
-----------------------------------------------------------------------------------

--TABELLA MEETING
----------------------------------------------------------------------------------------------
CREATE TABLE Meeting(
	IDMeeting INTEGER PRIMARY KEY DEFAULT nextval('meeting_serial'),
	DataInizio DATE NOT NULL,
	OrarioInizio TIME NOT NULL,
	DataFine DATE NOT NULL,
	OrarioFine TIME NOT NULL,
	Modalità modalità NOT NULL,
	Piattaforma piattaforma,
	Organizzatore CHAR(16) NOT NULL,
	CONSTRAINT fk_dipendente FOREIGN KEY (Organizzatore) REFERENCES Dipendente (CF),
	CONSTRAINT riunione_telematica CHECK (modalità='Telematico' AND Piattaforma IS NOT NULL),
	CONSTRAINT fine_valida CHECK (DataFine>=DataInizio AND OrarioFine>=OrarioInizio)
);
-----------------------------------------------------------------------------------------------