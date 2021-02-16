//Classe costruttore del DB. 
//Contiene la definizione delle tabelle, dei vincoli, dei trigger e di tutto ciò che è necessario per inizializzare il database prima dell'utilizzo del software.

package dbManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import javax.swing.JOptionPane;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class CostruttoreDB {
    private Connection connection;
    
    private final String pathCSVLuoghi = "lib/Comuni.csv";

	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
    
    public CostruttoreDB(Connection connection) {
        this.connection = connection;
    }

    public CostruttoreDB() {
        connection = null;
    }

    private boolean connessioneEsiste() {
        return !(connection == null);
    }

    private boolean esisteTabella(String nomeTabella) throws SQLException{
        DatabaseMetaData metadati = connection.getMetaData();
        ResultSet tabelle = metadati.getTables(null, null, nomeTabella, null);
        if (tabelle.next())
            return true;
        return false;
    }
    
    private boolean esisteEnum(String nomeEnum) throws SQLException{
    	if (connessioneEsiste()) {
    		Statement statement = connection.createStatement();
    		String query = "SELECT typname FROM pg_type WHERE typname = '" + nomeEnum + "'" ;
        	ResultSet risultato = statement.executeQuery(query);
        	if(risultato.next())
        		return true;
    	}
    	return false;
    }
    
    private boolean esisteTrigger(String nomeTrigger) throws SQLException{
    	if (connessioneEsiste()) {
    		Statement statement = connection.createStatement();
    		String query = "SELECT tgname FROM pg_trigger WHERE tgname = '" + nomeTrigger + "'" ;
        	ResultSet risultato = statement.executeQuery(query);
        	if(risultato.next())
        		return true;
    	}
    	return false;
    }

    //CREAZIONE TABELLE ED ENUMERAZIONI
	//------------------------------------------------
    
    private int creaTabellaSkill() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("skill")) {
                String createTable = "CREATE TABLE Skill(\r\n"
                		+ "	IDSkill SERIAL,\r\n"
                		+ "	NomeSkill varchar(50) NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY (IDSkill),\r\n"
                		+ "	UNIQUE (NomeSkill)\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);              
                statement.close();
                }
            }
	    return risultato;
    }
    
    private int creaTabellaLuogoNascita() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("luogonascita")) {
                String createTable = "CREATE TABLE LuogoNascita(\r\n"
                		+ "	NomeComune varchar(50) NOT NULL,\r\n"
                		+ "	NomeProvincia varchar(50) NOT NULL,\r\n"
                		+ "	CodComune char(4),\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY(CodComune),\r\n"
                		+ "	CONSTRAINT LuogoNascitaEsistente UNIQUE(NomeComune,NomeProvincia,CodComune)\r\n"
                		+ ")";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
	    return risultato;
    }
    
    private int creaTabellaSalaRiunione() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("salariunione")) {
                String createTable = "CREATE TABLE SalaRiunione(\r\n"
                		+ "	CodSala varchar(10) ,\r\n"
                		+ "	Capienza integer NOT NULL,\r\n"
                		+ "	Indirizzo varchar(50) NOT NULL,\r\n"
                		+ "	Piano integer NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY(CodSala),\r\n"
                		+ "	CONSTRAINT CapienzaEsistente CHECK (Capienza>0),\r\n"
                		+ "	CONSTRAINT PianoEsistente CHECK (Piano>=0)\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);               
                statement.close();
                }
            }
	    return risultato;
    }
    
    private int creaTabellaAmbitoProgetto() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("ambitoprogetto")) {
                String createTable = "CREATE TABLE AmbitoProgetto(\r\n"
                		+ "	IDAmbito SERIAL,\r\n"
                		+ "	NomeAmbito VARCHAR(20) NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY (IDAmbito),\r\n"
                		+ "	UNIQUE (NomeAmbito),\r\n"
                		+ "    CONSTRAINT AmbitoLegit CHECK(NomeAmbito ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$')\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
    	return risultato;
    }
    
    private int creaEnumTipologia() throws SQLException{
    	int risultato = -1;
    	if (connessioneEsiste()) {
    		Statement statement = connection.createStatement();
    		if (!esisteEnum("tipologia")) {
    			String createEnum = "CREATE TYPE tipologia AS ENUM('Ricerca base',"
    					+ "'Ricerca industriale',"
    					+ "'Ricerca sperimentale',"
    					+ "'Sviluppo sperimentale',"
    					+ "'Altro');";
                risultato = statement.executeUpdate(createEnum);
                statement.close();
    		}
    	}
    	return risultato;
    }
    
    private int creaTabellaProgetto() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
			creaEnumTipologia();
	        Statement statement = connection.createStatement();
	        if (!esisteTabella("progetto")) {
	            String createTable = "CREATE TABLE Progetto (\r\n"
	            		+ "	CodProgetto SERIAL,\r\n"
	            		+ "	NomeProgetto varchar(100) NOT NULL,\r\n"
	            		+ "	TipoProgetto tipologia NOT NULL ,\r\n"
	            		+ "	DescrizioneProgetto varchar (500),\r\n"
	            		+ "	DataCreazione DATE NOT NULL DEFAULT CURRENT_DATE,\r\n"
	            		+ "	DataScadenza DATE,\r\n"
	            		+ "	DataTerminazione DATE,\r\n"
	            		+ "	\r\n"
	            		+ "	PRIMARY KEY(CodProgetto),\r\n"
	            		+ "	CONSTRAINT DataCreazioneValida CHECK(DataCreazione <= DataScadenza AND DataCreazione <= DataTerminazione),"
	            		+ " CONSTRAINT DataTerminazioneCorretta CHECK(DataTerminazione<= current_date)\r\n"
	            		+ ");";
	            risultato = statement.executeUpdate(createTable);
	            statement.close();
	            }
	        }
	    return risultato;
    }
    
    private int creaTabellaDipendente() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("dipendente")) {
                String createTable = "CREATE TABLE Dipendente(\r\n"
                		+ "	CF char(16),\r\n"
                		+ "	Nome varchar(30) NOT NULL,\r\n"
                		+ "	Cognome varchar(30) NOT NULL,\r\n"
                		+ "	Sesso char(1) NOT NULL,\r\n"
                		+ "	DataNascita DATE NOT NULL,\r\n"
                		+ "	Indirizzo varchar(100) NOT NULL,\r\n"
                		+ "	Email varchar(100) NOT NULL ,\r\n"
                		+ "	TelefonoCasa char(10),\r\n"
                		+ "	Cellulare char(10),\r\n"
                		+ "	Salario float NOT NULL,\r\n"
                		+ "	Password varchar(50) NOT NULL,\r\n"
                		+ "	CodComune char(4) NOT NULL, \r\n"
                		+ "\r\n"
                		+ "	PRIMARY KEY(CF),\r\n"
                		+ "	UNIQUE(Email),\r\n"
                		+ "	UNIQUE(Cellulare),\r\n"
                		+ "	CONSTRAINT CFLegit CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),\r\n"
                		+ "	CONSTRAINT EmailLegit CHECK(Email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),\r\n"
                		+ "	CONSTRAINT NomeLegit CHECK(Nome ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$'),\r\n"
                		+ "	CONSTRAINT CognomeLegit CHECK(Cognome ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$'),\r\n"
                		+ " CONSTRAINT TelefonoCasaLegit CHECK(TelefonoCasa ~* '(0{1}[1-9]{1,3})[\\s|\\.|\\-]?(\\d{4,})'),\r\n"
                		+ " CONSTRAINT CellulareLegit CHECK (Cellulare ~* '(0{1}[1-9]{1,3})[\\s|\\.|\\-]?(\\d{4,})'),\r\n"
                		+ "	CONSTRAINT SessoLegit CHECK(Sesso='M' OR Sesso='F'),\r\n"
                		+ "	CONSTRAINT SalarioPositivo CHECK (Salario>=0),\r\n"
                		+ "	CONSTRAINT DataNascitaValida CHECK (EXTRACT( YEAR FROM AGE(DataNascita)) >= 18),\r\n"
                		+ "	\r\n"
                		+ "	--Associazione 1 a Molti(LuogoNascita,Dipendente)\r\n"
                		+ "	FOREIGN KEY (CodComune) REFERENCES LuogoNascita(CodComune) ON UPDATE CASCADE\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
	    return risultato;
    }
    
    private int creaEnumModalita() throws SQLException{
    	int risultato = -1;
    	if (connessioneEsiste()) {
    		Statement statement = connection.createStatement();
    		if (!esisteEnum("modalità")) {
    			String createEnum = "CREATE TYPE modalità AS ENUM ('Telematico','Fisico');";
                risultato = statement.executeUpdate(createEnum);
                statement.close();
    		}
    	}
    	return risultato;
    }
    
    private int creaEnumPiattaforma() throws SQLException{
    	int risultato = -1;
    	if (connessioneEsiste()) {
    		Statement statement = connection.createStatement();
    		if (!esisteEnum("piattaforma")) {
    			String createEnum = "CREATE TYPE piattaforma AS ENUM('Microsoft Teams',"
    					+ "'Discord',"
    					+ "'Google Meet',"
    					+ "'Zoom',"
    					+ "'Cisco Webex',"
    					+ "'Skype',"
    					+ "'Altro');";
                risultato = statement.executeUpdate(createEnum);
                statement.close();
    		}
    	}
    	return risultato;
    }
    
    private int creaTabellaMeeting() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	    	creaEnumModalita();
	    	creaEnumPiattaforma();
            Statement statement = connection.createStatement();
            if (!esisteTabella("meeting")) {
                String createTable = "CREATE TABLE Meeting(\r\n"
                		+ "	IDMeeting SERIAL,\r\n"
                		+ "	DataInizio DATE NOT NULL,\r\n"
                		+ "	DataFine DATE NOT NULL ,\r\n"
                		+ "	OrarioInizio TIME NOT NULL,\r\n"
                		+ "	OrarioFine TIME NOT NULL,\r\n"
                		+ "	Modalità modalità NOT NULL,\r\n"
                		+ "	Piattaforma piattaforma,\r\n"
                		+ "	CodSala varchar(10),\r\n"
                		+ "	CodProgetto integer ,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY(IDMeeting),\r\n"
                		+ "	CONSTRAINT DataValidaMeeting CHECK(DataInizio <= DataFine),\r\n"
                		+ "	CONSTRAINT OrarioValidoMeeting CHECK(OrarioInizio < OrarioFine AND DataInizio = DataFine),\r\n"
                		+ "	CONSTRAINT ModalitàRiunione CHECK ((Modalità='Telematico' AND Piattaforma IS NOT NULL AND CodSala IS NULL) OR (Modalità = 'Fisico' AND Piattaforma IS NULL AND CodSala IS NOT NULL)),\r\n"
                		+ "	\r\n"
                		+ "	\r\n"
                		+ "	--Associazione 1 a Molti(Sala,Meeting)\r\n"
                		+ "	FOREIGN KEY (CodSala) REFERENCES SalaRiunione(CodSala) ON DELETE CASCADE ON UPDATE CASCADE,\r\n"
                		+ "	\r\n"
                		+ "	--Associazione 1 a Molti (Progetto,Meeting)\r\n"
                		+ "	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE --ON UPDATE CASCADE--\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaTabellaAmbitoProgettoLink() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("ambitoprogettolink")) {
                String createTable = "CREATE TABLE AmbitoProgettoLink(\r\n"
                		+ "	IDAmbito integer NOT NULL,\r\n"
                		+ "	CodProgetto integer NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT AmbitoProgettoEsistente UNIQUE(IDAmbito,CodProgetto),\r\n"
                		+ "	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,\r\n"
                		+ "	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito) ON DELETE CASCADE\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
	    return risultato;
    }
    
    private int creaEnumRuolo() throws SQLException{
    	int risultato = -1;
    	if (connessioneEsiste()) {
    		Statement statement = connection.createStatement();
    		if (!esisteEnum("ruolo")) {
    			String createEnum = "CREATE TYPE ruolo AS ENUM('Project Manager',"
    					+ "'Team Member',"
    					+ "'Team Leader',"
    					+ "'Chief Financial Officer');";
                risultato = statement.executeUpdate(createEnum);
                statement.close();
    		}
    	}
    	return risultato;
    }
    
    private int creaTabellaPartecipazione() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	    	creaEnumRuolo();
            Statement statement = connection.createStatement();
            if (!esisteTabella("partecipazione")) {
                String createTable = "CREATE TABLE Partecipazione(\r\n"
                		+ "	CodProgetto integer NOT NULL,\r\n"
                		+ "	CF char(16) NOT NULL,\r\n"
                		+ "	RuoloDipendente ruolo NOT NULL, --Project Manager = Creatore\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT CfPartecipazione CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),\r\n"
                		+ "	CONSTRAINT PartecipazioneEsistente UNIQUE(CF,CodProgetto),\r\n"
                		+ "	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,\r\n"
                		+ "	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaTabellaAbilita() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("abilità")) {
                String createTable = "CREATE TABLE Abilità(\r\n"
                		+ "	IDSkill integer,\r\n"
                		+ "	CF char(16) NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT CfAbilità CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),\r\n"
                		+ "	CONSTRAINT SkillEsistente UNIQUE(IDSkill,CF),\r\n"
                		+ "	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill) ON DELETE CASCADE,\r\n"
                		+ "	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
	    return risultato;
    }
    
    private int creaTabellaPresenza() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTabella("presenza")) {
                String createTable = "CREATE TABLE Presenza(\r\n"
                		+ "	CF char(16) NOT NULL,\r\n"
                		+ "	IDMeeting integer NOT NULL ,\r\n"
                		+ "	Presente BOOLEAN NOT NULL DEFAULT FALSE,\r\n"
                		+ "	Organizzatore BOOLEAN NOT NULL DEFAULT FALSE, --TRUE = Organizzatore del meeting, FALSE = semplice invitato\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT CfPresenza CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),\r\n"
                		+ "	CONSTRAINT PresenzaEsistente UNIQUE(CF,IDMeeting),\r\n"
                		+ "	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE,\r\n"
                		+ "	FOREIGN KEY (IDMeeting) REFERENCES Meeting(IDMeeting) ON DELETE CASCADE --ON UPDATE CASCADE--\r\n"
                		+ ");";
                risultato = statement.executeUpdate(createTable);
                statement.close();
                }
            }
    return risultato;
    }
    
    public void creaTabelle() throws SQLException{
    	creaTabellaSkill();
    	creaTabellaAmbitoProgetto();
    	creaTabellaLuogoNascita();
    	creaTabellaSalaRiunione();
    	creaTabellaProgetto();
    	creaTabellaDipendente();
    	creaTabellaMeeting();
		creaTabellaAmbitoProgettoLink();
		creaTabellaPartecipazione();
		creaTabellaAbilita();
		creaTabellaPresenza();
    }
    
    //CREAZIONE FUNZIONI ESTERNE E TRIGGER
	//------------------------------------------------
    
    private int creaFunzioneAccavallamento() throws SQLException{
		int risultato = -1;
		if(connessioneEsiste()) {
			Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION accavallamento(DataInizioOLD DATE, DataFineOLD DATE, DataInizioNEW DATE, DataFineNEW DATE, OraInizioOLD TIME, OraFineOLD TIME, OraInizioNEW TIME, OraFineNEW TIME )\r\n"
            		+ "RETURNS BOOLEAN\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "InizioNEW TIMESTAMP;\r\n"
            		+ "InizioOLD TIMESTAMP;\r\n"
            		+ "FineNEW TIMESTAMP;\r\n"
            		+ "FineOLD TIMESTAMP;\r\n"
            		+ "BEGIN\r\n"
            		+ "InizioNEW := DataInizioNEW + OraInizioNEW;\r\n"
            		+ "InizioOLD := DataInizioOLD + OraInizioOLD;\r\n"
            		+ "FineNEW := DataFineNEW + OraFineNEW;\r\n"
            		+ "FineOLD := DataFineOLD + OraFineOLD;\r\n"
            		+ "--Controlla accavallamento\r\n"
            		+ "IF ((InizioNEW,FineNEW) OVERLAPS (InizioOLD,FineOLD)) THEN\r\n"
            		+ "	RETURN TRUE;	--accavallamento avvenuto\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN FALSE;	--accavallamento non avvenuto\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaFunzioneValutazioneMeeting() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION ValutazioneMeeting(dip Dipendente.CF%TYPE) RETURNS FLOAT\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "ValutazioneMeeting FLOAT;\r\n"
            		+ "TotaleMeeting FLOAT;\r\n"
            		+ "Presenze FLOAT;\r\n"
            		+ "BEGIN\r\n"
            		+ "--Conta tutti i meeting a cui doveva partecipare\r\n"
            		+ "SELECT COUNT(*) INTO TotaleMeeting\r\n"
            		+ "FROM Presenza AS p\r\n"
            		+ "WHERE p.CF = dip AND p.IDMeeting IN(\r\n"
            		+ "	SELECT m.IDMeeting\r\n"
            		+ "	FROM Meeting AS m\r\n"
            		+ "	WHERE m.DataFine<CURRENT_DATE OR (m.DataFine=CURRENT_DATE AND m.OrarioFine<CURRENT_TIME));\r\n"
            		+ "--Se non ci sono meeting a cui doveva presenziare di default la sua valutazione a riguardo è 0\r\n"
            		+ "IF (TotaleMeeting = 0) THEN\r\n"
            		+ "	RETURN 0;\r\n"
            		+ "END IF;\r\n"
            		+ "--Conta le sue presenze per ciascuno di essi\r\n"
            		+ "SELECT COUNT(*) INTO Presenze\r\n"
            		+ "FROM Presenza AS p\r\n"
            		+ "WHERE p.CF=dip AND p.Presente=TRUE AND p.IDMeeting IN(\r\n"
            		+ "	SELECT m.IDMeeting\r\n"
            		+ "	FROM Meeting AS m\r\n"
            		+ "	WHERE m.DataFine<CURRENT_DATE OR (m.DataFine=CURRENT_DATE AND m.OrarioFine<CURRENT_TIME));\r\n"
            		+ "--Fai il rapporto e restituisci il valore in decimi\r\n"
            		+ "ValutazioneMeeting := (Presenze*10)/TotaleMeeting;\r\n"
            		+ "RETURN ValutazioneMeeting;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaFunzioneValutazioneProgetti() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION ValutazioneProgetti(dip Dipendente.CF%TYPE) RETURNS FLOAT\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "TotaleProgetti FLOAT;\r\n"
            		+ "Partecipazioni FLOAT;\r\n"
            		+ "ValutazioneProgetti FLOAT;\r\n"
            		+ "BEGIN\r\n"
            		+ "--Calcola il numero totale di progetti dell'azienda\r\n"
            		+ "SELECT COUNT(*) INTO TotaleProgetti\r\n"
            		+ "FROM Progetto;\r\n"
            		+ "IF (TotaleProgetti = 0) THEN\r\n"
            		+ "	RETURN 0;"
            		+ "END IF;"
            		+ "--Calcola il numero di progetti a cui il dipendente partecipa\r\n"
            		+ "SELECT COUNT(*) INTO Partecipazioni\r\n"
            		+ "FROM Partecipazione AS p\r\n"
            		+ "WHERE p.CF = dip;\r\n"
            		+ "--Fai il rapporto e restituisci il risultato\r\n"
            		+ "ValutazioneProgetti := (Partecipazioni*10)/TotaleProgetti;\r\n"
            		+ "RETURN ValutazioneProgetti;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaFunzioneValutazione() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION Valutazione(dip Dipendente.CF%TYPE) RETURNS FLOAT\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "ValMeeting FLOAT;\r\n"
            		+ "ValProgetti FLOAT;\r\n"
            		+ "ValutazioneFinale FLOAT;\r\n"
            		+ "BEGIN\r\n"
            		+ "--Calcola valutazione meeting\r\n"
            		+ "ValMeeting := ValutazioneMeeting(dip);\r\n"
            		+ "--Calcola valutazione progetti\r\n"
            		+ "ValProgetti := ValutazioneProgetti(dip);\r\n"
            		+ "--Fai la media e restituisci il risultato in decimi\r\n"
            		+ "ValutazioneFinale := (ValMeeting+ValProgetti)/2;\r\n"
            		+ "RETURN ValutazioneFinale;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaFunzioneCheckAccavallamentiSale() throws SQLException{
    int risultato = -1;
    if(connessioneEsiste()) {
        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_accavallamenti_sale() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "OLDMeeting RECORD;\r\n"
            		+ "BEGIN\r\n"
            		+ "	--Se esistono altri record di meeting fisici con la stessa sala del nuovo record\r\n"
            		+ "	IF (EXISTS(SELECT m.IDMeeting\r\n"
            		+ "		FROM Meeting AS m\r\n"
            		+ "		WHERE m.CodSala=NEW.CodSala AND modalità='Fisico')) THEN\r\n"
            		+ "		--Per ogni meeting con la stessa sala che non sia quello nuovo\r\n"
            		+ "		FOR OLDMeeting IN\r\n"
            		+ "			SELECT *\r\n"
            		+ "			FROM Meeting AS m\r\n"
            		+ "			WHERE m.CodSala=NEW.CodSala AND modalità = 'Fisico' AND m.IDMeeting <> NEW.IDMeeting\r\n"
            		+ "		LOOP\r\n"
            		+ "			--Controlla che non si accavallino gli orari\r\n"
            		+ "			IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEW.DataInizio,NEW.DataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEW.OrarioInizio,NEW.OrarioFine)) THEN\r\n"
            		+ "				RAISE EXCEPTION 'ERRORE: Ci sono degli accavallamenti con il meeting di ID %', OLDMeeting.IDMeeting\r\n"
            		+ "				USING \r\n"
            		+ "					HINT = 'Per favore controlla gli orari o cambia sala per questo meeting.',\r\n"
            		+ "					ERRCODE = 'P0001';\r\n"
            		+ "				RETURN OLD;\r\n"
            		+ "			END IF;\r\n"
            		+ "		END LOOP;\r\n"
            		+ "	END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerNoAccavallamenti() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("no_accavallamenti")) {
                String createTrigger = "CREATE TRIGGER no_accavallamenti BEFORE INSERT OR UPDATE ON Meeting\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "WHEN (NEW.Modalità='Fisico')\r\n"
                		+ "EXECUTE PROCEDURE check_accavallamenti_sale();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneCheckCapienzaPresenza() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_capienza_presenza() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "Cap SalaRiunione.Capienza%TYPE;\r\n"
            		+ "BEGIN\r\n"
            		+ "--Controlla che il meeting inserito/modificato sia fisico\r\n"
            		+ "IF ((SELECT m.Modalità\r\n"
            		+ "	FROM Meeting AS m\r\n"
            		+ "	WHERE m.IDMeeting = NEW.IDMeeting) = 'Fisico') THEN\r\n"
            		+ "		--Salva la capienza della sala\r\n"
            		+ "		SELECT s.Capienza INTO Cap\r\n"
            		+ "		FROM Meeting AS m NATURAL JOIN SalaRiunione AS s\r\n"
            		+ "		WHERE m.IDMeeting = NEW.IDMeeting;\r\n"
            		+ "		--Controlla che il numero di partecipanti al meeting non superi la capienza\r\n"
            		+ "		IF ((SELECT COUNT(p.CF)\r\n"
            		+ "			FROM Presenza AS p\r\n"
            		+ "			WHERE p.IDMeeting = NEW.IDMeeting\r\n"
            		+ "			GROUP BY p.IDMeeting) > Cap) THEN\r\n"
            		+ "				RAISE EXCEPTION 'Il numero di invitati al meeting supera la capienza (%) della sala stabilita', Cap \r\n"
            		+ "					USING\r\n"
            		+ "						HINT = 'Si consiglia di cambiare sala.',\r\n"
            		+ "						ERRCODE = 'P0002';\r\n"
            		+ "				RETURN NEW;\r\n"
            		+ "		END IF;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerCapienzaRispettataPresenza() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("capienza_rispettata_presenza")) {
                String createTrigger = "CREATE TRIGGER capienza_rispettata_presenza AFTER INSERT OR UPDATE ON Presenza\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_capienza_presenza();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneCheckCapienzaMeeting() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_capienza_meeting() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "Cap SalaRiunione.Capienza%TYPE;\r\n"
            		+ "BEGIN\r\n"
            		+ "--Salva la capienza della nuova sala\r\n"
            		+ "SELECT s.Capienza INTO Cap\r\n"
            		+ "FROM SalaRiunione AS s\r\n"
            		+ "WHERE s.CodSala = NEW.CodSala;\r\n"
            		+ "--Controlla che il numero di partecipanti al meeting non superi la capienza della nuova sala\r\n"
            		+ "IF ((SELECT COUNT(p.CF)\r\n"
            		+ "	FROM Presenza AS p \r\n"
            		+ "	WHERE p.IDMeeting = NEW.IDMeeting\r\n"
            		+ "	GROUP BY p.IDMeeting) > Cap) THEN\r\n"
            		+ "		RAISE EXCEPTION 'Il numero di invitati al meeting supera la capienza (%) della nuvova sala %', Cap, NEW.CodSala\r\n"
            		+ "		 USING\r\n"
            		+ "		 	HINT = 'Si consiglia di cambiare sala.',\r\n"
            		+ "		 	ERRCODE = 'P0002';\r\n"
            		+ "		RETURN NEW;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerCapienzaRispettataMeeting() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("capienza_rispettata_meeting")) {
                String createTrigger = "CREATE TRIGGER capienza_rispettata_meeting AFTER UPDATE OF CodSala ON Meeting\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_capienza_meeting();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
	    private int creaFunzioneCheckSkillExistence() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_skill_existence() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "IF OLD.IDSkill NOT IN (\r\n"
            		+ "	SELECT a.IDSkill\r\n"
            		+ "	FROM Abilità AS a\r\n"
            		+ ") THEN\r\n"
            		+ "	DELETE\r\n"
            		+ "	FROM Skill AS s\r\n"
            		+ "	WHERE s.IDSkill = OLD.IDSkill;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerComposizioneSkill() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("composizione_skill")) {
                String createTrigger = "CREATE TRIGGER composizione_skill AFTER DELETE ON Abilità\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_skill_existence();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneCheckOnnipresenzaPresenza() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_onnipresenza_presenza()\r\n"
            		+ "RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "OLDMeeting RECORD; --record d'appoggio per i meeting da confrontare\r\n"
            		+ "NEWDataInizio DATE; --data inizio del meeting aggiunto/modificato in Presenza\r\n"
            		+ "NEWDataFine DATE; --data fine //\r\n"
            		+ "NEWOraInizio TIME; --ora inizio //\r\n"
            		+ "NEWOraFine TIME; --ora fine //\r\n"
            		+ "BEGIN\r\n"
            		+ "--Controlla se ci sono altri record in Presenza con lo stesso dipendente del nuovo record\r\n"
            		+ "IF (EXISTS(SELECT p.IDMeeting\r\n"
            		+ "	FROM Presenza AS p\r\n"
            		+ "	WHERE p.CF=NEW.CF)) THEN\r\n"
            		+ "	--Salva i valori del nuovo record necessari ai confronti con gli altri meeting\r\n"
            		+ "	SELECT m.DataInizio,m.DataFine,m.OrarioInizio,m.OrarioFine INTO NEWDataInizio,NEWDataFine,NEWOraInizio,NEWOraFine\r\n"
            		+ "	FROM Presenza AS p NATURAL JOIN Meeting AS m\r\n"
            		+ "	WHERE p.IDMeeting=NEW.IDMeeting;\r\n"
            		+ "	--Per ogni meeting dello stesso dipendente\r\n"
            		+ "	FOR OLDMeeting IN\r\n"
            		+ "		SELECT *\r\n"
            		+ "		FROM Presenza AS p NATURAL JOIN Meeting AS m\r\n"
            		+ "		WHERE p.CF = NEW.CF AND NEW.IDMeeting <> p.IDMeeting\r\n"
            		+ "	LOOP\r\n"
            		+ "		--Controlla che non si accavalli con quello nuovo\r\n"
            		+ "		IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEWDataInizio,NEWDataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEWOraInizio,NEWOraFine)) THEN\r\n"
            		+ "			RAISE EXCEPTION 'Il dipendente % ha il meeting % che si accavalla con questo',NEW.CF,OLDMeeting.IDMeeting\r\n"
            		+ "			USING \r\n"
            		+ "				HINT = 'Cambia il meeting oppure chiedi al dipendente di organizzarsi.',\r\n"
            		+ "				ERRCODE = 'P0003';\r\n"
            		+ "			RETURN OLD;\r\n"
            		+ "		END IF;\r\n"
            		+ "	END LOOP;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerNoOnnipresenzaPresenza() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("no_onnipresenza_presenza")) {
                String createTrigger = "CREATE TRIGGER no_onnipresenza_presenza AFTER INSERT OR UPDATE ON Presenza\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_onnipresenza_presenza();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneCheckOnnipresenzaMeeting() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_onnipresenza_meeting()\r\n"
            		+ "RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "DECLARE\r\n"
            		+ "dip Dipendente.CF%TYPE; --variabile d'appoggio (CF) dei dipendenti partecipanti al meeting modificato per cui bisogna controllare eventuali accavallamenti\r\n"
            		+ "OLDMeeting RECORD; --record d'appoggio per i meeting da confrontare in cerca di accavallamento con il meeting modificato\r\n"
            		+ "BEGIN\r\n"
            		+ "--Per ogni dipendente che partecipa al meeting aggiornato\r\n"
            		+ "FOR dip IN\r\n"
            		+ "	SELECT p.CF\r\n"
            		+ "	FROM Presenza AS p\r\n"
            		+ "	WHERE p.IDMeeting = NEW.IDMeeting\r\n"
            		+ "LOOP\r\n"
            		+ "--Per ogni meeting a cui esso partecipa\r\n"
            		+ "	FOR OLDMeeting IN\r\n"
            		+ "		SELECT *\r\n"
            		+ "		FROM Presenza AS p NATURAL JOIN Meeting AS m\r\n"
            		+ "		WHERE m.IDMeeting <> NEW.IDMeeting AND p.CF=dip\r\n"
            		+ "	LOOP\r\n"
            		+ "		--Controlla se si accavalla con il meeting aggiornato\r\n"
            		+ "		IF (accavallamento(OLDMeeting.DataInizio,OLDMeeting.DataFine,NEW.DataInizio,NEW.DataFine,OLDMeeting.OrarioInizio,OLDMeeting.OrarioFine,NEW.OrarioInizio,NEW.OrarioFine)) THEN\r\n"
            		+ "			RAISE EXCEPTION 'Il dipendente % potrebbe avere problemi di accavallamento con il meeting di ID %', dip, OLDMeeting.IDMeeting\r\n"
            		+ "			USING \r\n"
            		+ "				HINT = 'Cambia il meeting oppure chiedi al dipendente di organizzarsi.',\r\n"
            		+ "				ERRCODE = 'P0003';\r\n"
            		+ "			RETURN OLD;\r\n"
            		+ "		END IF;\r\n"
            		+ "	END LOOP;\r\n"
            		+ "END LOOP;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerNoOnnipresenzaMeeting() throws SQLException{
    int risultato = -1;
    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("no_onnipresenza_meeting")) {
                String createTrigger = "CREATE TRIGGER no_onnipresenza_meeting AFTER UPDATE ON Meeting\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_onnipresenza_meeting();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneAbilitaUppercase() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	    	Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION Abilità_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerCFUppercaseAbilita() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("cf_uppercase_abilità")) {
                String createTrigger = "CREATE TRIGGER CF_uppercase_Abilità\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Abilità\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Abilità_uppercase();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneDipendenteUppercase() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION Dipendente_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerCFUppercaseDipendente() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("cf_uppercase_dipendente")) {
                String createTrigger = "CREATE TRIGGER CF_uppercase_Dipendente\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Dipendente\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Dipendente_uppercase();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzionePresenzaUppercase() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION Presenza_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerCFUppercasePresenza() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("cf_uppercase_presenza")) {
                String createTrigger = "CREATE TRIGGER CF_uppercase_Presenza\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Presenza\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Presenza_uppercase();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzionePartecipazioneUppercase() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	    	Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION Partecipazione_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerCFUppercasePartecipazione() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("cf_uppercase_partecipazione")) {
                String createTrigger = "CREATE TRIGGER CF_uppercase_Partecipazione\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Partecipazione\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Partecipazione_uppercase();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    private int creaFunzioneCheckProjectManager() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
	        Statement statement = connection.createStatement();
            String createFunction = "CREATE OR REPLACE FUNCTION check_projectmanager() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "--Controlla che non ci siano altri record in Partecipazione con stesso progetto e ruolo project manager\r\n"
            		+ "IF (EXISTS (SELECT p.CF\r\n"
            		+ "			FROM Partecipazione AS p\r\n"
            		+ "			WHERE p.CodProgetto = NEW.CodProgetto AND p.RuoloDipendente = 'Project Manager' AND p.ruoloDipendente=NEW.ruolodipendente)) THEN\r\n"
            		+ "				RAISE EXCEPTION 'Esiste già un project manager per il progetto di codice %', NEW.CodProgetto\r\n"
            		+ "				USING\r\n"
            		+ "					ERRCODE = 'P0004';\r\n"
            		+ "				RETURN OLD;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            risultato = statement.executeUpdate(createFunction);
            statement.close();
            }
    return risultato;
    }
    
    private int creaTriggerUnicitaProjectManager() throws SQLException{
	    int risultato = -1;
	    if(connessioneEsiste()) {
            Statement statement = connection.createStatement();
            if (!esisteTrigger("unicità_projectmanager")) {
                String createTrigger = "CREATE TRIGGER unicità_projectmanager BEFORE INSERT OR UPDATE ON Partecipazione\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_projectmanager();";
                risultato = statement.executeUpdate(createTrigger);
                statement.close();
                }
            }
    return risultato;
    }
    
    public void creaFunzioniTrigger() throws SQLException{
    	creaFunzioneAccavallamento();
    	
    	creaFunzioneValutazioneMeeting();
    	creaFunzioneValutazioneProgetti();
    	creaFunzioneValutazione();
    	
    	creaFunzioneCheckAccavallamentiSale();
    	creaTriggerNoAccavallamenti();
    	
    	creaFunzioneCheckCapienzaPresenza();
    	creaTriggerCapienzaRispettataPresenza();
    	creaFunzioneCheckCapienzaMeeting();
    	creaTriggerCapienzaRispettataMeeting();
    	
    	creaFunzioneCheckSkillExistence();
    	creaTriggerComposizioneSkill();
    	
    	creaFunzioneCheckOnnipresenzaPresenza();
    	creaTriggerNoOnnipresenzaPresenza();
    	creaFunzioneCheckOnnipresenzaMeeting();
    	creaTriggerNoOnnipresenzaMeeting();
    	
    	creaFunzioneAbilitaUppercase();
    	creaTriggerCFUppercaseAbilita();
    	creaFunzioneDipendenteUppercase();
    	creaTriggerCFUppercaseDipendente();
    	creaFunzionePresenzaUppercase();
    	creaTriggerCFUppercasePresenza();
    	creaFunzionePartecipazioneUppercase();
    	creaTriggerCFUppercasePartecipazione();
    	
    	creaFunzioneCheckProjectManager();
    	creaTriggerUnicitaProjectManager();
    }
    
    //INSERIMENTO LUOGHI DI NASCITA DA AGENZIA DELLE ENTRATE
    //-------------------------------------------------------
    
    public void importaLuoghi() {
    	CopyManager copyManager = null;
		try {
			copyManager = new CopyManager((BaseConnection) connection);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, 
					e1.getMessage() + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e1.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
		}
    	BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathCSVLuoghi));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, 
					"Impossibile trovare il file Comuni.csv.\nVerificare che il file esista\noppure contattare uno sviluppatore.",
					"Errore File Non Trovato",
					JOptionPane.ERROR_MESSAGE);
		}
    	try {
			copyManager.copyIn("COPY LuogoNascita (NomeComune,NomeProvincia,CodComune) FROM STDIN WITH (FORMAT CSV, DELIMITER ';')", reader);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, 
					e.getMessage() + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore Importazione",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e1) {
			if (!e1.getSQLState().equals(VIOLAZIONE_PKEY_UNIQUE))
				JOptionPane.showMessageDialog(null, 
					e1.getMessage() + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e1.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
		}
    }
}
