
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
	NomeProgetto varchar(100) UNIQUE,
	TipoProgetto tipologia NOT NULL ,
	DescrizioneProgetto varchar (500),
	DataCreazione DATE NOT NULL DEFAULT CURRENT_DATE,
	DataScadenza DATE,
	DataTerminazione DATE,
	
	PRIMARY KEY(CodProgetto),
	CONSTRAINT DataCreazioneValida CHECK(DataCreazione <= DataScadenza AND DataCreazione <= DataTerminazione),
	CONSTRAINT DataTerminazioneCorretta CHECK(DataTerminazione<= current_date)
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
	CONSTRAINT TelefonoCasaLegit CHECK(TelefonoCasa ~* '^[0-9]*$'),
	CONSTRAINT CellulareLegit CHECK (Cellulare ~* '^[0-9]*$'),
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
	CodSala varchar(10),
	CodProgetto integer ,
	
	PRIMARY KEY(IDMeeting),
	CONSTRAINT DataValidaMeeting CHECK(DataInizio <= DataFine),
	CONSTRAINT OrarioValidoMeeting CHECK(OrarioInizio < OrarioFine AND DataInizio = DataFine),
	CONSTRAINT ModalitàRiunione CHECK ((Modalità='Telematico' AND Piattaforma IS NOT NULL AND CodSala IS NULL) OR (Modalità = 'Fisico' AND Piattaforma IS NULL AND CodSala IS NOT NULL)),
	
	--Associazione 1 a Molti(Sala,Meeting)
	FOREIGN KEY (CodSala) REFERENCES SalaRiunione(CodSala) ON DELETE CASCADE ON UPDATE CASCADE,
	
	--Associazione 1 a Molti (Progetto,Meeting)
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE
);


--Associazione Molti a Molti (AmbitoProgetto,Progetto)
CREATE TABLE AmbitoProgettoLink(
	IDAmbito integer NOT NULL,
	CodProgetto integer NOT NULL,
	
	CONSTRAINT AmbitoProgettoEsistente UNIQUE(IDAmbito,CodProgetto),
	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,
	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito) ON DELETE CASCADE
);


--Associazione Molti a Molti con classe di associazione (Progetto,Dipendente,RuoloDipendente)
CREATE TYPE ruolo AS ENUM('Project Manager','Team Member','Team Leader','Chief Financial Officer');

CREATE TABLE Partecipazione(
	CodProgetto integer NOT NULL,
	CF char(16) NOT NULL,
	RuoloDipendente ruolo NOT NULL, --Project Manager = Creatore
	
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
	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill) ON DELETE CASCADE,
	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE
);


--Associazione Molti a Molti (Meeting,Dipendente)
CREATE TABLE Presenza(
	CF char(16) NOT NULL,
	IDMeeting integer NOT NULL ,
	Presente BOOLEAN NOT NULL DEFAULT FALSE,
	Organizzatore BOOLEAN NOT NULL DEFAULT FALSE, --TRUE = Organizzatore del meeting, FALSE = semplice invitato
	
	CONSTRAINT CfPresenza CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT PresenzaEsistente UNIQUE(CF,IDMeeting),
	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IDMeeting) REFERENCES Meeting(IDMeeting) ON DELETE CASCADE
);


/* Funzioni esterne */
CREATE OR REPLACE FUNCTION accavallamento(DataInizioOLD DATE, DataFineOLD DATE, DataInizioNEW DATE, DataFineNEW DATE, OraInizioOLD TIME, OraFineOLD TIME, OraInizioNEW TIME, OraFineNEW TIME )
RETURNS BOOLEAN
LANGUAGE PLPGSQL
AS $$
DECLARE
InizioNEW TIMESTAMP;
InizioOLD TIMESTAMP;
FineNEW TIMESTAMP;
FineOLD TIMESTAMP;
BEGIN
InizioNEW := DataInizioNEW + OraInizioNEW;
InizioOLD := DataInizioOLD + OraInizioOLD;
FineNEW := DataFineNEW + OraFineNEW;
FineOLD := DataFineOLD + OraFineOLD;
--Controlla accavallamento
IF ((InizioNEW,FineNEW) OVERLAPS (InizioOLD,FineOLD)) THEN
	RETURN TRUE;	--accavallamento avvenuto
END IF;
RETURN FALSE;	--accavallamento non avvenuto
END;
$$;

--FUNCTION PER VALUTAZIONE MEETING
CREATE OR REPLACE FUNCTION ValutazioneMeeting(dip Dipendente.CF%TYPE) RETURNS FLOAT
LANGUAGE PLPGSQL
AS $$
DECLARE
ValutazioneMeeting FLOAT;
TotaleMeeting FLOAT;
Presenze FLOAT;
BEGIN
--Conta tutti i meeting a cui doveva partecipare
SELECT COUNT(*) INTO TotaleMeeting
FROM Presenza AS p
WHERE p.CF = dip AND p.IDMeeting IN(
	SELECT m.IDMeeting
	FROM Meeting AS m
	WHERE m.DataFine<CURRENT_DATE OR (m.DataFine=CURRENT_DATE AND m.OrarioFine<CURRENT_TIME));
--Se non ci sono meeting a cui doveva presenziare di default la sua valutazione a riguardo è 0
IF (TotaleMeeting = 0) THEN
	RETURN 0;
END IF;
--Conta le sue presenze per ciascuno di essi
SELECT COUNT(*) INTO Presenze
FROM Presenza AS p
WHERE p.CF=dip AND p.Presente=TRUE AND p.IDMeeting IN(
	SELECT m.IDMeeting
	FROM Meeting AS m
	WHERE m.DataFine<CURRENT_DATE OR (m.DataFine=CURRENT_DATE AND m.OrarioFine<CURRENT_TIME));
--Fai il rapporto e restituisci il valore in decimi
ValutazioneMeeting := (Presenze*10)/TotaleMeeting;
RETURN ValutazioneMeeting;
END;
$$;
----------------------------------------------------------------------------------------

--FUNCTION PER VALUTAZIONE PROGETTI
CREATE OR REPLACE FUNCTION ValutazioneProgetti(dip Dipendente.CF%TYPE) RETURNS FLOAT
LANGUAGE PLPGSQL
AS $$
DECLARE
TotaleProgetti FLOAT;
Partecipazioni FLOAT;
ValutazioneProgetti FLOAT;
BEGIN
--Calcola il numero totale di progetti dell'azienda
SELECT COUNT(*) INTO TotaleProgetti
FROM Progetto;
--Se non ci sono progetti di default la sua valutazione a riguardo è 0
IF (TotaleProgetti = 0) THEN
	RETURN 0;
END IF;
--Calcola il numero di progetti a cui il dipendente partecipa
SELECT COUNT(*) INTO Partecipazioni
FROM Partecipazione AS p
WHERE p.CF = dip;
--Fai il rapporto e restituisci il risultato
ValutazioneProgetti := (Partecipazioni*10)/TotaleProgetti;
RETURN ValutazioneProgetti;
END;
$$;
-----------------------------------------------------------------------------------------

--FUNCTION VALUTAZIONE COMPLETA
CREATE OR REPLACE FUNCTION Valutazione(dip Dipendente.CF%TYPE) RETURNS FLOAT
LANGUAGE PLPGSQL
AS $$
DECLARE
ValMeeting FLOAT;
ValProgetti FLOAT;
ValutazioneFinale FLOAT;
BEGIN
--Calcola valutazione meeting
ValMeeting := ValutazioneMeeting(dip);
--Calcola valutazione progetti
ValProgetti := ValutazioneProgetti(dip);
--Fai la media e restituisci il risultato in decimi
ValutazioneFinale := (ValMeeting+ValProgetti)/2;
RETURN ValutazioneFinale;
END;
$$;
--------------------------------------------------------------------------------------------------------------------------

/* Triggers */
/*TRIGGER PER NO ACCAVALLAMENTI DI MEETING FISICI IN SALE COMUNI
**Ad ogni update o insert di meeting fisici nella tabella Meeting controlla che
**i meeting con la stessa sala non finiscano per accavallarsi con i tempi.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
*******************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_accavallamenti_sale() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
OLDMeeting RECORD;
BEGIN
	--Se esistono altri record di meeting fisici con la stessa sala del nuovo record
	IF (EXISTS(SELECT m.IDMeeting
		FROM Meeting AS m
		WHERE m.CodSala=NEW.CodSala AND m.Modalità='Fisico' AND m.DataFine >= CURRENT_DATE AND m.OrarioFine >= CURRENT_TIME)) THEN
		--Per ogni meeting con la stessa sala che non sia quello nuovo
		FOR OLDMeeting IN
			SELECT *
			FROM Meeting AS m
			WHERE m.CodSala=NEW.CodSala AND m.Modalità = 'Fisico' AND m.IDMeeting <> NEW.IDMeeting
		LOOP
			--Controlla che non si accavallino gli orari
			IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEW.DataInizio,NEW.DataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEW.OrarioInizio,NEW.OrarioFine)) THEN
				RAISE EXCEPTION 'ERRORE: Ci sono degli accavallamenti con il meeting di ID %', OLDMeeting.IDMeeting
				USING 
					HINT = 'Per favore controlla gli orari o cambia sala per questo meeting.',
					ERRCODE = 'P0001';
				RETURN OLD;
			END IF;
		END LOOP;
	END IF;
RETURN NEW;
END;
$$;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--TRIGGER
CREATE TRIGGER no_accavallamenti BEFORE INSERT OR UPDATE ON Meeting
FOR EACH ROW
WHEN (NEW.Modalità='Fisico')
EXECUTE PROCEDURE check_accavallamenti_sale();
--------------------------------------------------------------------

/*TRIGGER PER CAPIENZA DELLE SALE RISPETTATE (PRESENZA)
**Ad ogni insert/update in Presenza controlla che
**il numero di invitati nel meeting fisico sia minore o uguale
**alla capienza della sala in cui avviene.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
*********************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_capienza_presenza() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
Cap SalaRiunione.Capienza%TYPE;
BEGIN
--Controlla che il meeting inserito/modificato sia fisico
IF ((SELECT m.Modalità
	FROM Meeting AS m
	WHERE m.IDMeeting = NEW.IDMeeting AND m.DataFine >= CURRENT_DATE AND m.OrarioFine >= CURRENT_TIME) = 'Fisico') THEN
		--Salva la capienza della sala
		SELECT s.Capienza INTO Cap
		FROM Meeting AS m NATURAL JOIN SalaRiunione AS s
		WHERE m.IDMeeting = NEW.IDMeeting;
		--Controlla che il numero di partecipanti al meeting non superi la capienza
		IF ((SELECT COUNT(p.CF)
			FROM Presenza AS p
			WHERE p.IDMeeting = NEW.IDMeeting
			GROUP BY p.IDMeeting) > Cap) THEN
				RAISE EXCEPTION 'Il numero di invitati al meeting supera la capienza (%) della sala stabilita.', Cap
				USING 
					HINT = 'Si consiglia di cambiare sala o di rimuovere qualche partecipante.',
					ERRCODE = 'P0002';
				RETURN NEW;
		END IF;
END IF;
RETURN NEW;
END;
$$;
----------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER capienza_rispettata_presenza AFTER INSERT OR UPDATE ON Presenza
FOR EACH ROW
EXECUTE PROCEDURE check_capienza_presenza();
--------------------------------------------------------------------------------

/*TRIGGER PER CAPIENZA RISPETTATA NELLE SALE (MEETING)
**Ad ogni update nella tabella Meeting di un meeting fisico nella colonna codice sala controlla che
**i partecipanti a quel meeting non superino la capienza della nuova sala scelta.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
***************************************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_capienza_meeting() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
Cap SalaRiunione.Capienza%TYPE;
BEGIN
--Se il meeting non è già terminato
IF (NEW.DataFine >= CURRENT_DATE AND NEW.OraFine >= CURRENT_TIME) THEN
	--Salva la capienza della nuova sala
	SELECT s.Capienza INTO Cap
	FROM SalaRiunione AS s
	WHERE s.CodSala = NEW.CodSala;
	--Controlla che il numero di partecipanti al meeting non superi la capienza della nuova sala
	IF ((SELECT COUNT(p.CF)
		FROM Presenza AS p 
		WHERE p.IDMeeting = NEW.IDMeeting
		GROUP BY p.IDMeeting) > Cap) THEN
			RAISE EXCEPTION 'Il numero di invitati al meeting supera la capienza (%) della nuvova sala %', Cap, NEW.CodSala
			USING 
				HINT = 'Si consiglia di cambiare sala o di rimuovere qualche partecipante.',
				ERRCODE = 'P0002';
			RETURN NEW;
	END IF;
END IF;
RETURN NEW;
END;
$$;
------------------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER capienza_rispettata_meeting BEFORE INSERT OR UPDATE OF CodSala ON Meeting
FOR EACH ROW
EXECUTE PROCEDURE check_capienza_meeting();
-----------------------------------------------------------------------------

/*TRIGGER PER ELIMINAZIONE SKILL NON NECESSARIE
**Quando avviene un delete nella tabella Ablità (quindi un dipendente perde una skill)
**controlla se la skill appartiene ancora a qualche dipendente altrimenti eliminala
************************************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_skill_existence() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
BEGIN
IF OLD.IDSkill NOT IN (
	SELECT a.IDSkill
	FROM Abilità AS a
) THEN
	DELETE
	FROM Skill AS s
	WHERE s.IDSkill = OLD.IDSkill;
END IF;
RETURN NEW;
END;
$$;
-------------------------------------------------------

--TRIGGER
CREATE TRIGGER composizione_skill AFTER DELETE ON Abilità
FOR EACH ROW
EXECUTE PROCEDURE check_skill_existence();
---------------------------------------------------------

/*TRIGGER PER ONNIPRESENZA DEI DIPENDENTI NEI MEETING (PRESENZA)
**Ad ogni insert o update di Presenza controlla che
**i meeting di quel dipendente non si accavallino con il nuovo.
**Nel caso evoca un'eccezione e non autorizza l'operazione.
****************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_onnipresenza_presenza()
RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
OLDMeeting RECORD; --record d'appoggio per i meeting da confrontare
NEWDataInizio DATE; --data inizio del meeting aggiunto/modificato in Presenza
NEWDataFine DATE; --data fine //
NEWOraInizio TIME; --ora inizio //
NEWOraFine TIME; --ora fine //
BEGIN
--Controlla se ci sono altri record in Presenza con lo stesso dipendente del nuovo record
IF (EXISTS(SELECT p.IDMeeting
	FROM Presenza AS p
	WHERE p.CF=NEW.CF)) THEN
	--Salva i valori del nuovo record necessari ai confronti con gli altri meeting
	SELECT m.DataInizio,m.DataFine,m.OrarioInizio,m.OrarioFine INTO NEWDataInizio,NEWDataFine,NEWOraInizio,NEWOraFine
	FROM Presenza AS p NATURAL JOIN Meeting AS m
	WHERE p.IDMeeting=NEW.IDMeeting;
	--Per ogni meeting non terminato dello stesso dipendente
	FOR OLDMeeting IN
		SELECT *
		FROM Presenza AS p NATURAL JOIN Meeting AS m
		WHERE p.CF = NEW.CF AND NEW.IDMeeting <> p.IDMeeting AND m.DataFine >= CURRENT_DATE AND OrarioFine >= CURRENT_TIME
	LOOP
		--Controlla che non si accavalli con quello nuovo
		IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEWDataInizio,NEWDataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEWOraInizio,NEWOraFine)) THEN
			RAISE EXCEPTION 'Il dipendente % ha il meeting % che si accavalla con questo',NEW.CF,OLDMeeting.IDMeeting
			USING 
				HINT = 'Cambia il meeting.',
				ERRCODE = 'P0003';
			RETURN OLD;
		END IF;
	END LOOP;
END IF;
RETURN NEW;
END;
$$;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER no_onnipresenza_presenza AFTER INSERT OR UPDATE ON Presenza
FOR EACH ROW
EXECUTE PROCEDURE check_onnipresenza_presenza();
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


/*TRIGGER PER ONNIPRESENZA DEI DIPENDENTI NEI MEETING (MEETING)
**Ad ogni update della tabella Meeting controlla che
**ciascun partecipante al meeting modificato non abbia altri
**meeting che si accavallano con quello appena modifica.
**Nel caso evoca un'eccezione e non autorizza l'operazione di update.
**********************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_onnipresenza_meeting()
RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
DECLARE
dip Dipendente.CF%TYPE; --variabile d'appoggio (CF) dei dipendenti partecipanti al meeting modificato per cui bisogna controllare eventuali accavallamenti
OLDMeeting RECORD; --record d'appoggio per i meeting da confrontare in cerca di accavallamento con il meeting modificato
BEGIN
--Per ogni dipendente che partecipa al meeting aggiornato
FOR dip IN
	SELECT p.CF
	FROM Presenza AS p
	WHERE p.IDMeeting = NEW.IDMeeting
LOOP
--Per ogni meeting non terminato a cui esso partecipa
	FOR OLDMeeting IN
		SELECT *
		FROM Presenza AS p NATURAL JOIN Meeting AS m
		WHERE m.IDMeeting <> NEW.IDMeeting AND p.CF=dip AND m.DataFine >= CURRENT_DATE AND m.OrarioFine >= CURRENT_TIME
	LOOP
		--Controlla se si accavalla con il meeting aggiornato
		IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEW.DataInizio,NEW.DataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEW.OrarioInizio,NEW.OrarioFine)) THEN
			RAISE EXCEPTION 'Il dipendente % potrebbe avere problemi di accavallamento con il meeting di ID %', dip, OLDMeeting.IDMeeting
			USING 
				HINT = 'Cambia il meeting.',
				ERRCODE = 'P0003';
			RETURN OLD;
		END IF;
	END LOOP;
END LOOP;
RETURN NEW;
END;
$$;
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER no_onnipresenza_meeting BEFORE INSERT OR UPDATE ON Meeting
FOR EACH ROW
EXECUTE PROCEDURE check_onnipresenza_meeting();
----------------------------------------------------------------

CREATE OR REPLACE FUNCTION Abilità_uppercase() RETURNS TRIGGER
LANGUAGE PLPGSQL 
AS $$
BEGIN
  NEW.CF = UPPER(NEW.CF);  
  RETURN NEW;
END;
$$;
  

CREATE TRIGGER CF_uppercase_Abilità
  BEFORE INSERT OR UPDATE
  ON Abilità
  FOR EACH ROW
  EXECUTE PROCEDURE Abilità_uppercase();
  
  CREATE OR REPLACE FUNCTION Dipendente_uppercase() RETURNS TRIGGER
LANGUAGE PLPGSQL 
AS $$
BEGIN
  NEW.CF = UPPER(NEW.CF);  
  RETURN NEW;
END;
$$;
  

CREATE TRIGGER CF_uppercase_Dipendente
  BEFORE INSERT OR UPDATE
  ON Dipendente
  FOR EACH ROW
  EXECUTE PROCEDURE Dipendente_uppercase();
  
  CREATE OR REPLACE FUNCTION Presenza_uppercase() RETURNS TRIGGER
LANGUAGE PLPGSQL 
AS $$
BEGIN
  NEW.CF = UPPER(NEW.CF);  
  RETURN NEW;
END;
$$;
  

CREATE TRIGGER CF_uppercase_Presenza
  BEFORE INSERT OR UPDATE
  ON Presenza
  FOR EACH ROW
  EXECUTE PROCEDURE Presenza_uppercase();
  
  CREATE OR REPLACE FUNCTION Partecipazione_uppercase() RETURNS TRIGGER
LANGUAGE PLPGSQL 
AS $$
BEGIN
  NEW.CF = UPPER(NEW.CF);  
  RETURN NEW;
END;
$$;
  

CREATE TRIGGER CF_uppercase_Partecipazione
  BEFORE INSERT OR UPDATE
  ON Partecipazione
  FOR EACH ROW
  EXECUTE PROCEDURE Partecipazione_uppercase();
  
/*TRIGGER PER UNICITA' DEL PROJECT MANAGER PER OGNI PROGETTO
**Ad ogni insert/update della tabella Partecipazione controlla che
**ogni progetto nella tabella comprenda un solo project manager.
**Altrimenti evoca un'eccezione e non autorizza l'operazione.
*******************************************************************/

--FUNCTION
CREATE OR REPLACE FUNCTION check_projectmanager() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
BEGIN
--Controlla che non ci siano altri record in Partecipazione con stesso progetto e ruolo project manager
IF (EXISTS (SELECT p.CF
			FROM Partecipazione AS p
	WHERE p.CodProgetto = NEW.CodProgetto AND p.RuoloDipendente = 'Project Manager' AND p.ruoloDipendente = NEW.ruolodipendente AND p.CF<>NEW.CF)) THEN
			RAISE EXCEPTION 'Esiste già un project manager per il progetto di codice %', NEW.CodProgetto
			USING
				ERRCODE = 'P0004';
				RETURN OLD;
END IF;
RETURN NEW;
END;
$$;
--------------------------------------------------------------------------------------------------------------

--TRIGGER
CREATE TRIGGER unicità_projectmanager AFTER INSERT OR UPDATE ON Partecipazione
FOR EACH ROW
EXECUTE PROCEDURE check_projectmanager();
--------------------------------------------------------------------------------

/* Inserimento Dati */

--LuogoNascita INSERT tramite file CSV

--Dipendente
INSERT INTO Dipendente(CF,Nome,Cognome,DataNascita,Sesso,Indirizzo,Email,TelefonoCasa,Salario,Password,CodComune) VALUES
	('RSSMRA91C06F839S','Mario','Rossi','06/03/1991','M','via sdff,28','m.rossi@unina.it','0817589891',100,'pass','F839'),
	('GNLGML63P10F839I','Grimaldi','Gianluca','10/09/1963','M','Via Alessandro Rossi,27,Ercolano(NA)','giudelucia@outlook.it','0817327550',1000,'paddss','F839'),
	('PRTGCM49T06F839W','Giacomo','Poretti','06/12/1949','M','Via Roma,38,Torre del Greco(NA)','a.esposito@gmail.com','0817589895',12000,'passw','F839'),
	('FRNGTN76A71F839G','Gastani','Frinzi','31/01/1976','F','Via Giuseppe Cosenza,27,Castellammare Di Stabia(NA)','Andr.caso@gmail.com','0817327550',1000,'paddss','F839');


--Progetto
INSERT INTO Progetto(NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione) VALUES
	('Tecniche dei Linguaggi e delle Applicazioni','Ricerca base','Progetto di ricerca di base','10/12/2020','18/12/2020','18/12/2020'),
	('MibProgo','Sviluppo sperimentale','Progetto di sviluppo sperimentale','16/12/2020','18/12/2020','18/12/2020'),
	('MixccProgetto','Ricerca sperimentale','Progetto di ricerca sperimentale','16/12/2020','20/12/2020','19/12/2020');


--AmbitoProgetto
INSERT INTO AmbitoProgetto(NomeAmbito) VALUES
	('Economia'),
	('Medicina'),
	('Militare'),
	('Scientifico');

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

--SalaRiunione
INSERT INTO SalaRiunione(CodSala,Capienza,Indirizzo,Piano) VALUES
	('Sala1',1,'Via Claudio, 21, 80143 Napoli(NA)',2),
	('Sala2',2,'Via Nuova Poggioreale , 80143 Napoli(NA)',5),
	('Sala3',2,'Via Foria, 272, 80139 Napoli (NA)',1),
	('Sala4',206,'Via Toledo, 343, 80134 Napoli (NA)',3),
	('Sala5',297,'Riviera di Chiaia, 77, 80123 Napoli (NA)',1);

--Meeting
INSERT INTO Meeting(DataInizio,DataFine,OrarioInizio,OrarioFine,Modalità,Piattaforma,CodSala,CodProgetto) VALUES
	('12/10/2020','12/10/2020','18:00','19:50','Telematico','Microsoft Teams',null,1),
	('12/10/2020','12/10/2020','14:05','16:50','Fisico',null,'Sala5',3),
	('21/10/2020','21/10/2020','14:55','15:55','Telematico','Microsoft Teams',null,2);
	

--AmbitoProgettoLink
INSERT INTO AmbitoProgettoLink(IDAmbito,CodProgetto) VALUES
	(1,1),
	(2,1),
	(3,3);


--Partecipazione 
INSERT INTO Partecipazione(CodProgetto,CF,RuoloDipendente) VALUES
	(2,'PRTGCM49T06F839W','Project Manager'),
	(3,'FRNGTN76A71F839G','Project Manager'),
    (1,'RSSMRA91C06F839S','Project Manager');


--Abilità
INSERT INTO Abilità(IDSkill,CF) VALUES
	(1,'FRNGTN76A71F839G'),
	(1,'PRTGCM49T06F839W'),
	(2,'FRNGTN76A71F839G'),
	(2,'PRTGCM49T06F839W'),
	(3,'RSSMRA91C06F839S');


--Presenza
INSERT INTO Presenza(CF,IDMeeting,Organizzatore) VALUES
	('RSSMRA91C06F839S',1, TRUE),
	('FRNGTN76A71F839G',2, TRUE),
	('RSSMRA91C06F839S',3, TRUE);
	











