/*
Base di dati di un Planning Aziendale
*/

/*
DROP TABLE AmbitoProgettoLink;
DROP TABLE Partecipazione;
DROP TABLE Abilità;
DROP TABLE Presenza;

DROP TABLE Meeting;
DROP TABLE Progetto;
DROP TABLE AmbitoProgetto;
DROP TABLE Dipendente;
DROP TABLE LuogoNascita;
DROP TABLE Skill;
DROP TABLE SalaRiunione;



*/

--OK
CREATE TABLE Progetto (
	CodProgetto integer ,
	NomeProgetto varchar2(20) NOT NULL,
	TipoProgetto varchar2(20) NOT NULL ,
	DescrizioneProgetto varchar2 (200),
	DataCreazione DATE NOT NULL,
	DataScadenza DATE,
	DataTerminazione DATE,
	Creator char(40) NOT NULL,
	
	PRIMARY KEY(CodProgetto),
	CONSTRAINT DataCreazioneValida CHECK(DataCreazione <= DataScadenza AND DataCreazione <= DataTerminazione),
	CONSTRAINT DataTerminazioneValida CHECK(DataTerminazione <= DataScadenza), 
	CONSTRAINT EnumTipologia CHECK(TipoProgetto IN('Ricerca base','Ricerca industriale','Ricerca sperimentale','Sviluppo sperimentale','Altro') )
);


--OK
CREATE TABLE AmbitoProgetto(
	IDAmbito integer,
	NomeAmbito varchar2(20),
	
	PRIMARY KEY (IDAmbito),
	UNIQUE (NomeAmbito),
    CONSTRAINT EnumAmbito CHECK(NomeAmbito IN ('Economia','Medicina','Militare','Informatico','Scientifico','Intrattenimento','Altro') )
);

--OK
CREATE TABLE LuogoNascita(
	NomeComune varchar2(50) NOT NULL,
	NomeProvincia varchar2(50) NOT NULL,
	CodComune char(20),
	
	PRIMARY KEY(CodComune)
);

--OK
CREATE TABLE Dipendente(
	CF char(16) ,
	Nome varchar2(20) NOT NULL,
	Cognome varchar2(20) NOT NULL,
	Sesso char(1) NOT NULL,
	DataNascita DATE NOT NULL,
	Indirizzo varchar2(100) NOT NULL,
	Email varchar2(100) NOT NULL ,
	TelefonoCasa char(10),
	Cellulare char(10),
	Salario integer NOT NULL,
	Valutazione integer NOT NULL,
	Pass varchar2(50) NOT NULL,
	CodComune char(20) NOT NULL, 

	PRIMARY KEY(CF),
	UNIQUE(Email),
	UNIQUE(TelefonoCasa),
	UNIQUE(Cellulare),

	CONSTRAINT SalarioPositivo CHECK (Salario>0),
	CONSTRAINT ValutazioneLimitata CHECK ( (Valutazione >=0) AND (Valutazione <=10) ),
    CONSTRAINT CFLegit CHECK( REGEXP_LIKE (CF,'^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$') ),
	CONSTRAINT EmailLegit CHECK(REGEXP_LIKE (Email,'^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$') ),
	CONSTRAINT NomeLegit CHECK(REGEXP_LIKE(Nome,'^[ a-zA-Z]+$') ),
	CONSTRAINT CognomeLegit CHECK(REGEXP_LIKE(Cognome,'^[ a-zA-Z]+$') ),
	
	--Associazione 1 a Molti(LuogoNascita,Dipendente)
	FOREIGN KEY (CodComune) REFERENCES LuogoNascita(CodComune)
);

--OK
CREATE TABLE Skill(
	IDSkill integer,
	NomeSkill varchar2(50) NOT NULL,
	
	PRIMARY KEY (IDSkill),
	UNIQUE (NomeSkill)
);

--OK
CREATE TABLE SalaRiunione(
	CodSala char(10),
	Capienza integer NOT NULL,
	Indirizzo varchar2(50) NOT NULL,
	Piano integer NOT NULL,
	
	PRIMARY KEY(CodSala),
	CONSTRAINT CapienzaEsistente CHECK (Capienza>0)
);


--OK
CREATE TABLE Meeting(
	IDMeeting integer,
	DataInizio DATE ,
	DataFine DATE  ,
	OrarioInizio TIMESTAMP WITH LOCAL TIME ZONE ,
	OrarioFine TIMESTAMP WITH LOCAL TIME ZONE ,
	Modalita varchar2(10),
	Piattaforma varchar2(20),
	Organizzatore char(40) NOT NULL,
	CodSala char(10),
	CodProgetto integer,
	
	PRIMARY KEY(IDMeeting),
    CONSTRAINT DataValidaMeeting CHECK(DataInizio <= DataFine),
    CONSTRAINT OrarioValidoMeeting CHECK(OrarioInizio < OrarioFine),
    CONSTRAINT EnumMeetingModalita CHECK(Modalita IN ('Fisico','Online') ),
    CONSTRAINT EnumMeetingPiattaforma CHECK(Piattaforma IN ('Microsoft Teams','Discord','Google Meet','Zoom','CiscoWepex','Skype','Altro') ),

   	--Associazione 1 a Molti(Sala,Meeting)
	FOREIGN KEY (CodSala) REFERENCES SalaRiunione(CodSala),
	
	--Associazione 1 a Molti (Progetto,Meeting)
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto)
);




--Associazione Molti a Molti (AmbitoProgetto,Progetto)
--OK
CREATE TABLE AmbitoProgettoLink(
	IDAmbito integer,
	CodProgetto integer,
	
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito)
);


--Associazione Molti a Molti con classe di associazione (Progetto,Dipendente,RuoloDipendente)

--OK
CREATE TABLE Partecipazione(
	CodProgetto integer,
	CF char(16) ,
	RuoloDipendente varchar2(30),
	
	CONSTRAINT CfPartecipazione CHECK( REGEXP_LIKE (CF,'^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$') ),
    CONSTRAINT EnumPartecipazione CHECK(RuoloDipendente IN ('Project Manager','Team Member','Team Leader','Chief Financial Officer') ),
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Dipendente,Skill)
--OK
CREATE TABLE Abilita(
	IDSkill integer,
	CF char(16) ,
	
	CONSTRAINT CfAbilit CHECK( REGEXP_LIKE (CF,'^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$') ),
	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Meeting,Dipendente)
--OK se Meeting funzionasse
CREATE TABLE Presenza(
	CF char(16),
	IDMeeting integer,
	
    CONSTRAINT CfPresenza CHECK( REGEXP_LIKE (CF,'^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$') ),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF),
	FOREIGN KEY (IDMeeting) REFERENCES Meeting(IDMeeting)
);





/* Inserimento Dati */


--Progetto
INSERT INTO Progetto(CodProgetto,NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione,Creatore) VALUES
	(1,'Mio Progetto','Ricerca base','dffgffgff','10/12/2020','18/12/2020','18/12/2020','Michekle'),
	(2,'MibProgo','Sviluppo sperimentale','dffgffbbgff','16/12/2020',null,null,'Micheggkle'),
	(3,'MixccProgetto','Ricerca sperimentale','dffgffffgff','16/12/2020',null,null,'Micheklfe');


--AmbitoProgetto
INSERT INTO AmbitoProgetto(IDAmbito,NomeAmbito) VALUES
	(1,'Economia'),
	(2,'Medicina'),
	(3,'Militare'),
	(4,'Scientifico');


--Dipendente
INSERT INTO Dipendente(CF,Nome,Cognome,DataNascita,Sesso,Indirizzo,Email,TelefonoCasa,Cellulare,Salario,Valutazione,Pass,CodComune) VALUES('RSSMRA80A01F839W','Mario','Rossi',to_date('05/16/13', 'MM/DD/YY'),'M','via sdff,28','m.rossi@unina.it','0817589891','38789999',100,10,'pass','f34');
	
INSERT INTO LuogoNascita VALUES('dffgf','ddddd','f34');

--Skill
INSERT INTO Skill(IDSkill,NomeSkill) VALUES (10,'Smart');


--LuogoNascita INSERT tramite file CSV


--SalaRiunione
INSERT INTO SalaRiunione(CodSala,Capienza,Indirizzo,Piano) VALUES ('Sala1',100,'Via a achille',10)

	
	

--Meeting
INSERT INTO Meeting(IDMeeting,DataInizio,DataFine,OrarioInizio,OrarioFine,Modalita,Piattaforma,Organizzatore,CodSala)VALUES(1,'12/10/2020','12/10/2020','13:55','14:00','Fisico',null,'Micchd','Sala1');
 VALUES(2,'15/11/2020','15/11/2020','13:55','16:55','Online','Discord','Mivcchd',null),
	 VALUES(3,'21/10/2020','21/10/2020','13:55','14:55','Fisico',null,'Micchddd','Sala2');
	

--AmbitoProgettoLink
INSERT INTO AmbitoProgettoLink(IDAmbito,CodProgetto) VALUES
	(1,1),
	(2,2),
	(3,3);


--Partecipazione (Trigger:Non possono essere inseriti più Project Manager per lo stesso progetto)
INSERT INTO Partecipazione(CodProgetto,CF,RuoloDipendente) VALUES
	(1,'RSSMRA80A01F839W','Project Manager'),
	(1,'DLCGPP80L01H243P','Project Manager'),
	(1,'RSSMRA80A01F839W','Project Manager'),
	(2,'RSSMRA80A01F839W','Team Member');
	
	
--Abilità
INSERT INTO Abilità(IDSkill,CF) VALUES
	(10,'RSSMRA80A01F839W'),
	(1,'DLCGPP80L01H243P'),
	(100,'SPSNDR02L01L259V');


--Presenza
INSERT INTO Presenza(CF,IDMeeting) VALUES
	('RSSMRA80A01F839W',1),
	('DLCGPP80L01H243P',2),
	('SPSNDR02L01L259V',3);

/* --Interrogazioni-- */

--Dipendenti che hanno partecipato a dei progetti
SELECT *
FROM Partecipazione NATURAL JOIN Dipendente


--Dipendenti che hanno partecipato ai meeting
SELECT IDMeeting,CF,Nome,Cognome,valutazione
FROM Dipendente NATURAL JOIN Presenza
ORDER BY valutazione DESC

