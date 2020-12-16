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
	Scadenza DATE,
	DataTerminazione DATE,
	Creatore char(40),
	
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
	CF char(16),
	Nome varchar(20) NOT NULL,
	Cognome varchar(20) NOT NULL,
	DataNascita DATE NOT NULL,
	Indirizzo varchar(100) NOT NULL,
	Email varchar(100) NOT NULL,
	TelefonoCasa integer,
	Cellulare integer,
	Salario integer NOT NULL,
	Valutazione integer NOT NULL,
	Password varchar(50) NOT NULL,
	CodComune char(20) NOT NULL, 
	
	PRIMARY KEY(CF),
	UNIQUE(Email),
	UNIQUE(TelefonoCasa),
	UNIQUE(Cellulare),
	
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
	CF char(16),
	RuoloDipendente ruolo,
	
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Dipendente,Skill)
CREATE TABLE Abilità(
	IDSkill integer,
	CF char(16),
	
	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF)
);


--Associazione Molti a Molti (Meeting,Dipendente)
CREATE TABLE Presenza(
	CF char(16),
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
	
	
--

--Dipendente
INSERT INTO Dipendente(CF,Nome,Cognome,DataNascita,Indirizzo,Email,TelefonoCasa,Cellulare,Salario,Valutazione,Password) VALUES
	('ZDDFCCD34544','Mi','mmmSss','11/12/2000','via sdff,28','sjdndjnsj@ff.com','08175898','38789999',10000,10,'pass'),
	('ZDDFCCD34554544','Mcci','nSss','17/12/2000','via sdffcc,27','sjdndjnffdsj@ff.com','08175755','387719999',50000,1,'paddss'),
	('ZDDFCCD3454544','Mivv','bnSss','19/12/2000','via sdff,38','sjdndcccdfjnsj@ff.com','0817589895','3448999',100000,5,'passw');


--Partecipazione
INSERT INTO Partecipazione(CodProgetto,CF,RuoloDipendente) VALUES
	(1,null,'Project Manager'),
	(2,null,'Team Member');




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
	

	


