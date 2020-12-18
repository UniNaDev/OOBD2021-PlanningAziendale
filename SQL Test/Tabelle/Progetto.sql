--SEQUENCE PER CodProgetto di PROGETTO
--------------------------------------
CREATE SEQUENCE CodProgetto_Serial
START WITH 1
INCREMENT BY 1;
--------------------------------------

--ENUM PER TipoProgetto di PROGETTO
--------------------------------------------------------------------------------------------
CREATE TYPE tipologia AS ENUM ('Ricerca base','Ricerca sperimentale','Ricerca industriale');
--------------------------------------------------------------------------------------------

--TABELLA PROGETTO
--------------------------------------------------------------------------------------------------------
CREATE TABLE Progetto (
	CodProgetto INTEGER PRIMARY KEY DEFAULT nextval('CodProgetto_Serial'),
	NomeProgetto VARCHAR(50) NOT NULL,
	TipoProgetto tipologia NOT NULL,
	DescrizioneProgetto VARCHAR(500),
	DataCreazione DATE NOT NULL DEFAULT CURRENT_DATE,
	Scadenza DATE DEFAULT NULL,
	DataTerminazione DATE DEFAULT NULL,
	CONSTRAINT scadenza_valida CHECK (Scadenza>=DataCreazione OR Scadenza IS NULL),
	CONSTRAINT terminazione_valida CHECK (DataTerminazione>=DataCreazione OR DataTerminazione IS NULL)
);
-------------------------------------------------------------------------------------------------------