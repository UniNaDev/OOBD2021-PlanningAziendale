
/*  Base di dati di un Planning Aziendale   */

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


*/

--ENUM PER TipoProgetto di PROGETTO
CREATE TYPE tipologia AS ENUM('Ricerca base','Ricerca industriale','Ricerca sperimentale','Sviluppo sperimentale','Altro');

CREATE TABLE Progetto (
	CodProgetto SERIAL,
	NomeProgetto varchar(100) NOT NULL,
	TipoProgetto tipologia NOT NULL ,
	DescrizioneProgetto varchar (500),
	DataCreazione DATE NOT NULL DEFAULT CURRENT_DATE,
	DataScadenza DATE,
	DataTerminazione DATE,
	Creatore char(40) NOT NULL,
	
	PRIMARY KEY(CodProgetto),
	--UNIQUE(NomeProgetto)--
	CONSTRAINT DataCreazioneValida CHECK(DataCreazione <= DataScadenza AND DataCreazione <= DataTerminazione)
);


CREATE TABLE AmbitoProgetto(
	IDAmbito SERIAL,
	NomeAmbito VARCHAR(20) NOT NULL,
	
	PRIMARY KEY (IDAmbito),
	UNIQUE (NomeAmbito),
    CONSTRAINT AmbitoLegit CHECK(NomeAmbito ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$')
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
	Nome varchar(30) NOT NULL,
	Cognome varchar(30) NOT NULL,
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
	CONSTRAINT NomeLegit CHECK(Nome ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$'),
	CONSTRAINT CognomeLegit CHECK(Cognome ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$'),
	CONSTRAINT SessoLegit CHECK(Sesso='M' OR Sesso='F'),
	CONSTRAINT SalarioPositivo CHECK (Salario>=0),
	CONSTRAINT DataNascitaValida CHECK (EXTRACT( YEAR FROM AGE(DataNascita)) >= 18),
	
	--Associazione 1 a Molti(LuogoNascita,Dipendente)
	FOREIGN KEY (CodComune) REFERENCES LuogoNascita(CodComune) ON UPDATE CASCADE
);


CREATE TABLE Skill(
	IDSkill SERIAL,
	NomeSkill varchar(50) NOT NULL,
	
	PRIMARY KEY (IDSkill),
	UNIQUE (NomeSkill),
    CONSTRAINT NomeSkillLegit CHECK (NomeSkill ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$')
);


CREATE TABLE SalaRiunione(
	CodSala varchar(10) ,
	Capienza integer NOT NULL,
	Indirizzo varchar(50) NOT NULL,
	Piano integer NOT NULL,
	
	PRIMARY KEY(CodSala),
	CONSTRAINT CapienzaEsistente CHECK (Capienza>0),
	CONSTRAINT PianoEsistente CHECK (Piano>=0)
);


--ENUM Modalità,Piattaforma
CREATE TYPE modalità AS ENUM ('Telematico','Fisico');
CREATE TYPE piattaforma AS ENUM('Microsoft Teams','Discord','Google Meet','Zoom','Cisco Webex','Skype','Altro');

CREATE TABLE Meeting(
	IDMeeting SERIAL,
	DataInizio DATE NOT NULL,
	DataFine DATE NOT NULL ,
	OrarioInizio TIME NOT NULL,
	OrarioFine TIME NOT NULL,
	Modalità modalità NOT NULL,
	Piattaforma piattaforma,
	Organizzatore char(16) NOT NULL,
	CodSala varchar(10),
	CodProgetto integer ,
	
	PRIMARY KEY(IDMeeting),
	CONSTRAINT DataValidaMeeting CHECK(DataInizio <= DataFine),
	CONSTRAINT OrarioValidoMeeting CHECK(OrarioInizio < OrarioFine AND DataInizio = DataFine),
	CONSTRAINT ModalitàRiunione CHECK ((Modalità='Telematico' AND Piattaforma IS NOT NULL AND CodSala IS NULL) OR (Modalità = 'Fisico' AND Piattaforma IS NULL AND CodSala IS NOT NULL)),
	
	
	--Associazione 1 a Molti(Sala,Meeting)
	FOREIGN KEY (CodSala) REFERENCES SalaRiunione(CodSala) ON DELETE CASCADE ON UPDATE CASCADE,
	
	--Associazione 1 a Molti (Progetto,Meeting)
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE --ON UPDATE CASCADE--
);


--Associazione Molti a Molti (AmbitoProgetto,Progetto)
CREATE TABLE AmbitoProgettoLink(
	IDAmbito integer NOT NULL,
	CodProgetto integer NOT NULL,
	
	CONSTRAINT AmbitoProgettoEsistente UNIQUE(IDAmbito,CodProgetto),
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,
	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito)
);


--Associazione Molti a Molti con classe di associazione (Progetto,Dipendente,RuoloDipendente)
CREATE TYPE ruolo AS ENUM('Project Manager','Team Member','Team Leader','Chief Financial Officer');

CREATE TABLE Partecipazione(
	CodProgetto integer NOT NULL,
	CF char(16) NOT NULL,
	RuoloDipendente ruolo NOT NULL,
	
	CONSTRAINT CfPartecipazione CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT PartecipazioneEsistente UNIQUE(CF,CodProgetto),
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,
	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE
);


--Associazione Molti a Molti (Dipendente,Skill)
CREATE TABLE Abilità(
	IDSkill integer,
	CF char(16) NOT NULL,
	
	CONSTRAINT CfAbilità CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT SkillEsistente UNIQUE(IDSkill,CF),
	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE
);


--Associazione Molti a Molti (Meeting,Dipendente)
CREATE TABLE Presenza(
	CF char(16) NOT NULL,
	IDMeeting integer NOT NULL ,
	Presente BOOLEAN NOT NULL DEFAULT FALSE,
	
	CONSTRAINT CfPresenza CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT PresenzaEsistente UNIQUE(CF,IDMeeting),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IDMeeting) REFERENCES Meeting(IDMeeting) ON DELETE CASCADE --ON UPDATE CASCADE--
);




/* Inserimento Dati */


--Progetto
INSERT INTO Progetto(NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione,Creatore) VALUES
	('Tecniche dei Linguaggi e delle Applicazioni','Ricerca base','Progetto di ricerca di base','10/12/2020','18/12/2020','18/12/2020','Michekle'),
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
	('RSsMrA80A01F839w','Mario','Rossi','29/12/2000','M','via sdff,28','m.rossi@unina.it','0817589891','387899899',100,'pass','F839'),
	('DLCGPp80L01H243p','Giuseppe Miriam Gerardi','De Lucia','01/07/1980','M','Via Alessandro Rossi,27,Ercolano(NA)','giudelucia@outlook.it','0817327550','3877199990',1000,'paddss','H243'),
	('SPsNDr02L01L259V','Andrea','Esposito','01/07/2002','M','Via Roma,38,Torre del Greco(NA)','a.esposito@gmail.com','0817589895','3448999000',12000,'passw','L259'),
	('CSANDR62B01C129Z','Andrea','Caso','31/12/1962','M','Via Giuseppe Cosenza,27,Castellammare Di Stabia(NA)','Andr.caso@gmail.com','0817327550','3935689810',1000,'paddss','C129');


--Skill
INSERT INTO Skill(NomeSkill) VALUES 
	('Teamwork'),
	('Affidabilità'),
	('Time management'),
	('Creatività'),
	('Flessibilità'),
	('Capacità comunicative'),
	('Competenze digitali'),
	('Problem Solving'),
	('Motivatore'),
	('Capacità di negoziazione');


--LuogoNascita INSERT tramite file CSV


--SalaRiunione
INSERT INTO SalaRiunione(CodSala,Capienza,Indirizzo,Piano) VALUES
	('Sala1',100,'Via Claudio, 21, 80143 Napoli(NA)',2),
	('Sala2',200,'Via Nuova Poggioreale , 80143 Napoli(NA)',5),
	('Sala3',75,'Via Foria, 272, 80139 Napoli (NA)',1),
	('Sala4',206,'Via Toledo, 343, 80134 Napoli (NA)',3),
	('Sala5',297,'Riviera di Chiaia, 77, 80123 Napoli (NA)',1);

--Meeting
INSERT INTO Meeting(DataInizio,DataFine,OrarioInizio,OrarioFine,Modalità,Piattaforma,Organizzatore,CodSala,CodProgetto) VALUES
	('12/10/2020','12/10/2020','18:00','19:50','Telematico','Microsoft Teams','Micchd',null,null),
	('12/10/2020','12/10/2020','14:05','16:50','Fisico',null,'Micchd','Sala5',null),
	('21/10/2020','21/10/2020','14:55','15:55','Telematico','Microsoft Teams','Micchddd',null,null);
	

--AmbitoProgettoLink
INSERT INTO AmbitoProgettoLink(IDAmbito,CodProgetto) VALUES
	(1,1),
	(2,1),
	(3,3);


--Partecipazione 
INSERT INTO Partecipazione(CodProgetto,CF,RuoloDipendente) VALUES
	(2,'RSsMrA80A01F839w','Chief Financial Officer'),
	(3,'RSsMrA80A01F839w','Chief Financial Officer'),
    (1,'RSsMrA80A01F839w','Project Manager');


--Abilità
INSERT INTO Abilità(IDSkill,CF) VALUES
	(1,'RssMra80A01F839W'),
	(1,'DLCGPP80L01H243P'),
	(2,'RSSMRA80A01F839W'),
	(2,'SpsNdR02L01L259V'),
	(3,'SpsNDR02L01L259v');


--Presenza
INSERT INTO Presenza(CF,IDMeeting) VALUES
	('RSsMrA80A01F839w',4),
	('RSsMrA80A01F839w',5),
	('RSsMrA80A01F839w',6);
	

/* --Interrogazioni-- */

--Dipendenti che hanno partecipato a dei progetti
SELECT *
FROM Partecipazione NATURAL JOIN Progetto


--Dipendenti che hanno partecipato ai meeting
SELECT IDMeeting,CF,Nome,Cognome
FROM Dipendente NATURAL JOIN Presenza


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


--Interrogazione per calcolo età
SELECT EXTRACT( YEAR FROM AGE(DataNascita))
FROM Dipendente









