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


DROP TYPE tipologia;
DROP TYPE modalità;
DROP TYPE piattaforma;
DROP TYPE ruolo;

DROP SEQUENCE CodProgettoSerial;
DROP SEQUENCE IDAmbitoSerial;
DROP SEQUENCE SkillSerial;
DROP SEQUENCE MeetingSerial;
*/

--ENUM PER TipoProgetto di PROGETTO
CREATE TYPE tipologia AS ENUM('Ricerca base','Ricerca industriale','Ricerca sperimentale','Sviluppo sperimentale','Altro');

--SEQUENCE PER CodProgetto di PROGETTO
CREATE SEQUENCE CodProgettoSerial
START WITH 1
INCREMENT BY 1;


CREATE TABLE Progetto (
	CodProgetto integer DEFAULT nextval('CodProgettoSerial'),
	NomeProgetto varchar(50) NOT NULL,
	TipoProgetto tipologia NOT NULL ,
	DescrizioneProgetto varchar (200),
	DataCreazione DATE NOT NULL DEFAULT CURRENT_DATE,
	DataScadenza DATE,
	DataTerminazione DATE,
	Creatore char(40) NOT NULL,
	
	PRIMARY KEY(CodProgetto),
	--UNIQUE(NomeProgetto)--
	CONSTRAINT DataCreazioneValida CHECK(DataCreazione <= DataScadenza AND DataCreazione <= DataTerminazione)
);


--SEQUENCE PER AMBITOPROGETTO
CREATE SEQUENCE IDAmbitoSerial
START WITH 1
INCREMENT BY 1;

CREATE TABLE AmbitoProgetto(
	IDAmbito integer DEFAULT nextval('IDAmbitoSerial'),
	NomeAmbito VARCHAR(20) NOT NULL,
	
	PRIMARY KEY (IDAmbito),
	UNIQUE (NomeAmbito)
);


CREATE TABLE LuogoNascita(
	NomeComune varchar(50) NOT NULL,
	NomeProvincia varchar(50) NOT NULL,
	CodComune char(4),
	
	PRIMARY KEY(CodComune),
	CONSTRAINT LuogoNascitaEsistente UNIQUE(NomeComune,NomeProvincia,CodComune)
);


CREATE TABLE Dipendente(
	CF char(16),
	Nome varchar(20) NOT NULL,
	Cognome varchar(20) NOT NULL,
	Sesso char(1) NOT NULL,
	DataNascita DATE NOT NULL,
	Indirizzo varchar(100) NOT NULL,
	Email varchar(100) NOT NULL ,
	TelefonoCasa char(10),
	Cellulare char(10),
	Salario float NOT NULL,
	Password varchar(50) NOT NULL,
	CodComune char(4) NOT NULL, 

	PRIMARY KEY(CF),
	UNIQUE(Email),
	UNIQUE(Cellulare),
	CONSTRAINT CFLegit CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT EmailLegit CHECK(Email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
	CONSTRAINT NomeLegit CHECK(Nome ~* '^[ a-zA-Z]+$'),
	CONSTRAINT CognomeLegit CHECK(Cognome ~* '^[ a-zA-Z]+$'),
	CONSTRAINT SessoLegit CHECK(Sesso='M' OR Sesso='F'),
	CONSTRAINT SalarioPositivo CHECK (Salario>=0),
	
	--Associazione 1 a Molti(LuogoNascita,Dipendente)
	FOREIGN KEY (CodComune) REFERENCES LuogoNascita(CodComune)
);

--SEQUENCE PER SKILL
CREATE SEQUENCE SkillSerial
START WITH 1
INCREMENT BY 1;

CREATE TABLE Skill(
	IDSkill integer DEFAULT nextval('SkillSerial'),
	NomeSkill varchar(50) NOT NULL,
	
	PRIMARY KEY (IDSkill),
	UNIQUE (NomeSkill)
);


CREATE TABLE SalaRiunione(
	CodSala varchar(10),
	Capienza integer NOT NULL,
	Indirizzo varchar(50) NOT NULL,
	Piano integer NOT NULL,
	
	PRIMARY KEY(CodSala),
	CONSTRAINT CapienzaEsistente CHECK (Capienza>0),
	CONSTRAINT PianoEsistente CHECK (Piano>=0)
);

--SEQUENCE PER MEETING
CREATE SEQUENCE MeetingSerial
START WITH 1
INCREMENT BY 1;

CREATE TYPE modalità AS ENUM ('Telematico','Fisico');

CREATE TYPE piattaforma AS ENUM('Microsoft Teams','Discord','Google Meet','Zoom','Cisco Webex','Skype','Altro');

CREATE TABLE Meeting(
	IDMeeting integer DEFAULT nextval('MeetingSerial'),
	DataInizio DATE NOT NULL,
	DataFine DATE NOT NULL ,
	OrarioInizio TIME NOT NULL,
	OrarioFine TIME NOT NULL,
	Modalità modalità NOT NULL,
	Piattaforma piattaforma,
	Organizzatore char(16) NOT NULL,
	CodSala varchar(10),
	CodProgetto integer,
	
	PRIMARY KEY(IDMeeting),
	CONSTRAINT DataValidaMeeting CHECK(DataInizio <= DataFine),
	CONSTRAINT OrarioValidoMeeting CHECK(OrarioInizio < OrarioFine),
	CONSTRAINT ModalitàRiunione CHECK ((Modalità='Telematico' AND Piattaforma IS NOT NULL AND CodSala IS NULL) OR (Modalità = 'Fisico' AND Piattaforma IS NULL AND CodSala IS NOT NULL)),
	
	
	--Associazione 1 a Molti(Sala,Meeting)
	FOREIGN KEY (CodSala) REFERENCES SalaRiunione(CodSala),
	
	--Associazione 1 a Molti (Progetto,Meeting)
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto)
);


--Associazione Molti a Molti (AmbitoProgetto,Progetto)
CREATE TABLE AmbitoProgettoLink(
	IDAmbito integer,
	CodProgetto integer,
	
	CONSTRAINT unique_ID_Cod UNIQUE(IDAmbito,CodProgetto),
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito)
);


--Associazione Molti a Molti con classe di associazione (Progetto,Dipendente,RuoloDipendente)
CREATE TYPE ruolo AS ENUM('Project Manager','Team Member','Team Leader','Chief Financial Officer');

CREATE TABLE Partecipazione(
	CodProgetto integer,
	CF char(16) ,
	RuoloDipendente ruolo NOT NULL,
	
	CONSTRAINT CfPartecipazione CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$')
	CONSTRAINT PartecipazioneEsistente UNIQUE(CF,CodProgetto),
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Dipendente,Skill)
CREATE TABLE Abilità(
	IDSkill integer,
	CF char(16) ,
	
	CONSTRAINT CfAbilità CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT SkillEsistente UNIQUE(IDSkill,CF),
	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Meeting,Dipendente)
CREATE TABLE Presenza(
	CF char(16),
	IDMeeting integer,
	
	CONSTRAINT CfPresenza CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT PresenzaEsistente UNIQUE(CF,IDMeeting),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF),
	FOREIGN KEY (IDMeeting) REFERENCES Meeting(IDMeeting)
);




/* Inserimento Dati */


--Progetto
INSERT INTO Progetto(NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione,Creatore) VALUES
	('Mio Progetto','Ricerca base','Progetto di ricerca di base','10/12/2020','18/12/2020','18/12/2020','Michekle'),
	('MibProgo','Sviluppo sperimentale','Progetto di sviluppo sperimentale','16/12/2020','18/12/2020','18/12/2020','Micheggkle'),
	('MixccProgetto','Ricerca sperimentale','Progetto di ricerca sperimentale','16/12/2020','20/12/2020','19/12/2020','Micheklfe');


--AmbitoProgetto
INSERT INTO AmbitoProgetto(NomeAmbito) VALUES
	('Economia'),
	('Medicina'),
	('Militare'),
	('Scientifico');


--Dipendente
INSERT INTO Dipendente(CF,Nome,Cognome,DataNascita,Sesso,Indirizzo,Email,TelefonoCasa,Cellulare,Salario,Password,CodComune) VALUES
	('RSsMrA80A01F839w','Mario','Rossi','29/02/2020','M','via sdff,28','m.rossi@unina.it','0817589891','387899899',100,'pass','F839'),
	('DLCGPp80L01H243p','Giuseppe','De Lucia','01/07/1980','M','Via Alessandro Rossi,27,Ercolano(NA)','giudelucia@outlook.it','0817327550','3877199990',1000,'paddss','H243'),
	('SPsNDr02L01L259V','Andrea','Esposito','01/07/2002','M','Via Roma,38,Torre del Greco(NA)','a.esposito@gmail.com','0817589895','3448999000',12000,'passw','L259');


--Skill
INSERT INTO Skill(NomeSkill) VALUES 
	('Smart'),
	('Group'),
	('wow');


--LuogoNascita INSERT tramite file CSV


--SalaRiunione
INSERT INTO SalaRiunione(CodSala,Capienza,Indirizzo,Piano) VALUES
	('Sala1',100,'Via a achille',10),
	('Sala2',200,'Via acireale',1);
	

--Meeting  (Trigger:Un dipendente non può partecipare nello stesso giorno e allo stesso orario a due meeting diversi)
INSERT INTO Meeting(DataInizio,DataFine,OrarioInizio,OrarioFine,Modalità,Piattaforma,Organizzatore,CodSala,CodProgetto) VALUES
	('12/10/2020','12/10/2020','13:55','14:00','Fisico',null,'Micchd','Sala1',null),
	('15/11/2020','15/11/2020','13:55','16:55','Telematico','Discord','Mivcchd',null,null),
	('21/10/2020','21/10/2020','13:55','14:55','Fisico',null,'Micchddd','Sala2',null);
	

--AmbitoProgettoLink
INSERT INTO AmbitoProgettoLink(IDAmbito,CodProgetto) VALUES
	(1,1),
	(2,1),
	(3,3);


--Partecipazione (Trigger:Non possono essere inseriti più Project Manager per lo stesso progetto)

INSERT INTO Partecipazione(CodProgetto,CF,RuoloDipendente) VALUES
	(124,'RSsMrA80A01F839W','Project Manager'),
	(125,'DLcGpP80L01H243P','Project Manager'),
    (126,'SPSNDR02L01L259v','Team Member');


--Abilità
INSERT INTO Abilità(IDSkill,CF) VALUES
	(10,'RssMra80A01F839W'),
	(10,'DLCGPP80L01H243P'),
	(11,'RSSMRA80A01F839W'),
	(12,'SpsNdR02L01L259V'),
	(10,'SpsNDR02L01L259v');


--Presenza
INSERT INTO Presenza(CF,IDMeeting) VALUES
	('RSSMRA80A01F839W',41),
	('RSSMRa80A01F839W',42),
	('SPSNdR02L01L259v',43);




/* --Interrogazioni-- */

--Dipendenti che hanno partecipato a dei progetti
SELECT *
FROM Partecipazione NATURAL JOIN Dipendente


--Dipendenti che hanno partecipato ai meeting
SELECT IDMeeting,CF,Nome,Cognome,valutazione
FROM Dipendente NATURAL JOIN Presenza
ORDER BY valutazione DESC


--Codice dei progetti e gli ambiti a loro associati
SELECT codprogetto,nomeambito
FROM AmbitoProgettoLink NATURAL JOIN Progetto NATURAL JOIN AmbitoProgetto

--Dipendenti presenti ai meeting
SELECT *
FROM Dipendente NATURAL JOIN Partecipazione

--Interrogazione CaseInsensitive
SELECT *
FROM Dipendente
WHERE nome ILIKE 'AnDrEa'


