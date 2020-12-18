--INSERT PER TABELLA PROGETTO
------------------------------------INSERT NORMALE-----------------------------------------------------
INSERT INTO Progetto (NomeProgetto,TipoProgetto,DescrizioneProgetto,Scadenza,DataTerminazione)
VALUES ('Test Normale','Ricerca base','Un normale test di insert in Progetto',DATE('24-12-2020'),NULL);

-----------------------------------INSERT ENUM SBAGLIATA-----------------------------------------------
INSERT INTO Progetto (NomeProgetto,TipoProgetto,DescrizioneProgetto,Scadenza,DataTerminazione)
VALUES ('Test Enumerazione Sbagliata','ENUM sbagliata','Un test di insert con enumerazione sbagliata',NULL,NULL);

----------------------------------INSERT SCADENZA SBAGLIATA--------------------------------------------
INSERT INTO Progetto (NomeProgetto,TipoProgetto,DescrizioneProgetto,Scadenza,DataTerminazione)
VALUES ('Test Scadenza Sbagliata','Ricerca base','Un test di insert con scadenza precedente a creazione',DATE('17-12-1999'),NULL);