--SEQUENCE PER AMBITOPROGETTO
-------------------------------------
CREATE SEQUENCE IDAmbito_Serial
START WITH 1
INCREMENT BY 1;
-------------------------------------

--ENUM PER AMBITOPROGETTO
-------------------------------------------------------------------------------------------------------------
CREATE TYPE ambito AS ENUM ('Economia','Medicina','Militare','Informatico','Scientifico','Intrattenimento');
-------------------------------------------------------------------------------------------------------------

--TABELLA AMBITOPROGETTO
---------------------------------------------------------------------
CREATE TABLE AmbitoProgetto(
	IDAmbito INTEGER PRIMARY KEY DEFAULT nextval('IDAmbito_Serial'),
	NomeAmbito ambito UNIQUE
);
---------------------------------------------------------------------
