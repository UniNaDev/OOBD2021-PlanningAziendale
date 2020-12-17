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
DROP TYPE ambito;
DROP TYPE modalità;
DROP TYPE piattaforma;
DROP TYPE ruolo;
*/


CREATE TYPE tipologia AS ENUM('Ricerca base','Ricerca industriale','Ricerca sperimentale','Sviluppo sperimentale','Altro');

CREATE TABLE Progetto (
	CodProgetto integer ,
	NomeProgetto varchar(20) NOT NULL,
	TipoProgetto tipologia ,
	DescrizioneProgetto varchar (200),
	DataCreazione DATE NOT NULL,
	DataScadenza DATE,
	DataTerminazione DATE,
	Creatore char(40) NOT NULL,
	
	PRIMARY KEY(CodProgetto)
);


CREATE TYPE ambito AS ENUM('Economia','Medicina','Militare','Informatico','Scientifico','Intrattenimento','Altro');

CREATE TABLE AmbitoProgetto(
	IDAmbito integer,
	NomeAmbito ambito,
	
	PRIMARY KEY (IDAmbito),
	UNIQUE (NomeAmbito)
);


CREATE TABLE LuogoNascita(
	NomeComune varchar(50) NOT NULL,
	NomeProvincia varchar(50) NOT NULL,
	CodComune char(20),
	
	PRIMARY KEY(CodComune)
);


CREATE TABLE Dipendente(
	CF char(16) CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	Nome varchar(20) NOT NULL,
	Cognome varchar(20) NOT NULL,
	DataNascita DATE NOT NULL,
	Indirizzo varchar(100) NOT NULL,
	Email varchar(100) CHECK(Email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
	TelefonoCasa char(10),
	Cellulare char(10),
	Salario integer NOT NULL,
	Valutazione integer NOT NULL,
	Password varchar(50) NOT NULL,
	CodComune char(20) NOT NULL, 

	
	PRIMARY KEY(CF),
	UNIQUE(Email),
	UNIQUE(TelefonoCasa),
	UNIQUE(Cellulare),
	CHECK(Nome ~* '^[ a-zA-Z]+$'),
	CHECK(Cognome ~* '^[ a-zA-Z]+$'),
	
	--Associazione 1 a Molti(LuogoNascita,Dipendente)
	FOREIGN KEY (CodComune) REFERENCES LuogoNascita(CodComune)
);


CREATE TABLE Skill(
	IDSkill integer,
	NomeSkill varchar(50) NOT NULL,
	
	PRIMARY KEY (IDSkill),
	UNIQUE (NomeSkill)
);


CREATE TABLE SalaRiunione(
	CodSala char(10),
	Capienza integer NOT NULL,
	Indirizzo varchar(50) NOT NULL,
	Piano integer NOT NULL,
	
	PRIMARY KEY(CodSala)
);


CREATE TYPE modalità AS ENUM('Fisico','Online');
CREATE TYPE piattaforma AS ENUM('Microsoft Teams','Discord','Google Meet','Zoom','CiscoWepex','Skype','Altro');

CREATE TABLE Meeting(
	IDMeeting integer,
	DataInizio DATE NOT NULL,
	DataFine DATE ,
	OrarioInizio TIME NOT NULL,
	OrarioFine TIME,
	Modalità modalità,
	Piattaforma piattaforma,
	Organizzatore char(40) NOT NULL,
	CodSala char(10),
	CodProgetto integer,
	
	PRIMARY KEY(IDMeeting),
	
	--Associazione 1 a Molti(Sala,Meeting)
	FOREIGN KEY (CodSala) REFERENCES SalaRiunione(CodSala),
	
	--Associazione 1 a Molti (Progetto,Meeting)
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto)
);




--Associazione Molti a Molti (AmbitoProgetto,Progetto)
CREATE TABLE AmbitoProgettoLink(
	IDAmbito integer,
	CodProgetto integer,
	
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito)
);


--Associazione Molti a Molti con classe di associazione (Progetto,Dipendente,RuoloDipendente)
CREATE TYPE ruolo AS ENUM('Project Manager','Team Member','Team Leader','Chief Financial Officer');

CREATE TABLE Partecipazione(
	CodProgetto integer,
	CF char(16) CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	RuoloDipendente ruolo,
	
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Dipendente,Skill)
CREATE TABLE Abilità(
	IDSkill integer,
	CF char(16) CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	
	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Meeting,Dipendente)
CREATE TABLE Presenza(
	CF char(16) CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	IDMeeting integer,
	
	FOREIGN KEY (CF) REFERENCES Dipendente(CF),
	FOREIGN KEY (IDMeeting) REFERENCES Meeting(IDMeeting)
);





/* Inserimento Dati */


--Progetto
INSERT INTO Progetto(CodProgetto,NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione,Creatore) VALUES
	(1,'Mio Progetto','Ricerca base','dffgffgff','16/12/2020',null,null,'Michekle'),
	(2,'MibProgo','Sviluppo sperimentale','dffgffbbgff','16/12/2020',null,null,'Micheggkle'),
	(3,'MixccProgetto','Ricerca sperimentale','dffgffffgff','16/12/2020',null,null,'Micheklfe');


--AmbitoProgetto
INSERT INTO AmbitoProgetto(IDAmbito,NomeAmbito) VALUES
	(1,'Economia'),
	(2,'Medicina'),
	(3,'Militare'),
	(4,'Scientifico');


--Dipendente
INSERT INTO Dipendente(CF,Nome,Cognome,DataNascita,Indirizzo,Email,TelefonoCasa,Cellulare,Salario,Valutazione,Password,CodComune) VALUES
	('RSSMRA80A01F839W','Mario','Rossi','01/01/1980','via sdff,28','sjdndjnsj@ff.com','0817589891','38789999',10000,10,'pass','F839'),
	('DLCGPP80L01H243P','Giuseppe','De Lucia','01/07/1980','via sdffcc,27','sj545ffd@sjff2548.com','0817327550','3877199990',50000,1,'paddss','H243'),
	('SPSNDR02L01L259V','Andrea','Esposito','01/07/2002','via sdff,38','sjdndcccdfjnsj@ff.com','0817589895','3448999000',100000,5,'passw','L259');


--Skill
INSERT INTO Skill(IDSkill,NomeSkill) VALUES 
	(10,'Smart'),
	(1,'Group'),
	(100,'wow');


--LuogoNascita INSERT tramite file CSV


--SalaRiunione
INSERT INTO SalaRiunione(CodSala,Capienza,Indirizzo,Piano) VALUES
	('Sala1',100,'Via a achille',10),
	('Sala2',200,'Via acireale',1);
	

--Meeting
INSERT INTO Meeting(IDMeeting,DataInizio,DataFine,OrarioInizio,OrarioFine,Modalità,Piattaforma,Organizzatore,CodSala) VALUES
	(1,'12/10/2020',null,'13:55','14:55','Fisico',null,'Micchd','Sala1'),
	(2,'22/11/2020',null,'13:55','14:55','Online','Discord','Mivcchd',null),
	(3,'12/10/2020',null,'13:55','14:55','Fisico',null,'Micchddd','Sala2');
	

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

