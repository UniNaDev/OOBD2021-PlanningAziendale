--CREAZIONE ENUM RuoloDip
-------------------------------------------------------------------------------------------------------------------------------------------
CREATE TYPE ruolo AS ENUM ('Project Manager','Membro Team','Leader Team','Responsabile Marketing','Responsabile Finanze','Tester Qualità');
-------------------------------------------------------------------------------------------------------------------------------------------

--TABELLA PARTECIPAZIONE
-----------------------------------------------------------------------
CREATE TABLE Partecipazione(
	Progetto INTEGER,
	Dipendente CHAR(16),
	RuoloDip ruolo NOT NULL,
	CONSTRAINT pk_Partecipazione PRIMARY KEY(Progetto,Dipendente)
);
-----------------------------------------------------------------------