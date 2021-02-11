//Classe costruttore del DB. Contiene la definizione delle tabelle, dei vincoli, dei trigger e di tutto ciò che è necessario per inizializzare il database prima dell'utilizzo del software.

package dbManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class CostruttoreDB {
	
	//ATTRIBUTI
	//------------------------------------------------
    private Connection connection;	//connessione al DB
    
    //private final String pathCSVLuoghi = "D:\\Development\\Github\\OOBD2021-PlanningAziendale\\Risorse esterne\\Comuni.csv";	//path del file csv con tutti i luoghi italiani
    private final String pathCSVLuoghi = "lib/Comuni.csv";	//path del file csv con tutti i luoghi italiani

    //METODI
	//------------------------------------------------
    
    //Costruttore con connessione
    public CostruttoreDB(Connection connection)
    {
        this.connection = connection;
    }

    //Costruttore privo di connessione
    public CostruttoreDB()
    {
        connection = null;
    }

    //Metodo che afferma se la connessione esiste o meno con il db
    private boolean connectionExists() {
        return !(connection == null);
    }

    //Metodo che afferma se una certa tabella esiste già nel db
    private boolean esisteTabella(String nomeTabella) throws SQLException{
        DatabaseMetaData metadati = connection.getMetaData();	//ottiene i metadati
        ResultSet tabelle = metadati.getTables(null, null, nomeTabella, null);	//cerca tabelle con quel nome
        if (tabelle.next())
            return true;
        return false;
    }
    
    //Metodo che afferma se un enum type esiste o meno nel DB
    private boolean esisteEnum(String nomeEnum) throws SQLException{
    	if (connectionExists()) {
    		Statement st = connection.createStatement();
    		String sql = "SELECT typname FROM pg_type WHERE typname = '" + nomeEnum + "'" ;
        	ResultSet risultato = st.executeQuery(sql);
        	if(risultato.next())
        		return true;
    	}
    	return false;
    }
    
    //Metodo che afferma se un trigger esiste o meno nel DB
    private boolean esisteTrigger(String nomeTrigger) throws SQLException{
    	if (connectionExists()) {
    		Statement st = connection.createStatement();
    		String sql = "SELECT tgname FROM pg_trigger WHERE tgname = '" + nomeTrigger + "'" ;
        	ResultSet risultato = st.executeQuery(sql);
        	if(risultato.next())
        		return true;
    	}
    	return false;
    }

    //CREAZIONE TABELLE
	//------------------------------------------------
    
    //Metodo che crea la tabella Skill
    private int creaTabellaSkill() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Skill")) {
                String sql = "CREATE TABLE Skill(\r\n"
                		+ "	IDSkill SERIAL,\r\n"
                		+ "	NomeSkill varchar(50) NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY (IDSkill),\r\n"
                		+ "	UNIQUE (NomeSkill),\r\n"
                		+ "    CONSTRAINT NomeSkillLegit CHECK (NomeSkill ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$')\r\n"
                		+ ");";
                result = st.executeUpdate(sql);              
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella LuogoNascita
    private int creaTabellaLuogoNascita() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("LuogoNascita")) {
                String sql = "CREATE TABLE LuogoNascita(\r\n"
                		+ "	NomeComune varchar(50) NOT NULL,\r\n"
                		+ "	NomeProvincia varchar(50) NOT NULL,\r\n"
                		+ "	CodComune char(4),\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY(CodComune),\r\n"
                		+ "	CONSTRAINT LuogoNascitaEsistente UNIQUE(NomeComune,NomeProvincia,CodComune)\r\n"
                		+ ")";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella SalaRiunione
    private int creaTabellaSalaRiunione() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("SalaRiunione")) {
                String sql = "CREATE TABLE SalaRiunione(\r\n"
                		+ "	CodSala varchar(10) ,\r\n"
                		+ "	Capienza integer NOT NULL,\r\n"
                		+ "	Indirizzo varchar(50) NOT NULL,\r\n"
                		+ "	Piano integer NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY(CodSala),\r\n"
                		+ "	CONSTRAINT CapienzaEsistente CHECK (Capienza>0),\r\n"
                		+ "	CONSTRAINT PianoEsistente CHECK (Piano>=0)\r\n"
                		+ ");";
                result = st.executeUpdate(sql);               
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella AmbitoProgetto
    private int creaTabellaAmbitoProgetto() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("AmbitoProgetto")) {
                String sql = "CREATE TABLE AmbitoProgetto(\r\n"
                		+ "	IDAmbito SERIAL,\r\n"
                		+ "	NomeAmbito VARCHAR(20) NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	PRIMARY KEY (IDAmbito),\r\n"
                		+ "	UNIQUE (NomeAmbito),\r\n"
                		+ "    CONSTRAINT AmbitoLegit CHECK(NomeAmbito ~* '^[A-Za-zÀ-ÿ]+''?[ A-Za-zÀ-ÿ]+$')\r\n"
                		+ ");";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il type enum tipologia di un progetto nel DB (necessario per definire la tabella Progetto)
    private int creaEnumTipologia() throws SQLException{
    	int result = -1;
    	if (connectionExists()) {
    		Statement st = connection.createStatement();
    		if (!esisteEnum("tipologia")) {
    			String sql = "CREATE TYPE tipologia AS ENUM('Ricerca base',"
    					+ "'Ricerca industriale',"
    					+ "'Ricerca sperimentale',"
    					+ "'Sviluppo sperimentale',"
    					+ "'Altro');";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
    		}
    	}
    	return result;
    }
    
    //Metodo che crea la tabella Progetto
    private int creaTabellaProgetto() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
    		creaEnumTipologia();	//crea enum necessaria
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Progetto")) {
                String sql = "CREATE TABLE Progetto (\r\n"
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
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella Dipendente
    private int creaTabellaDipendente() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Dipendente")) {
                String sql = "CREATE TABLE Dipendente(\r\n"
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
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il type enum modalità di un meeting nel DB (necessario per definire la tabella Meeting)
    private int creaEnumModalita() throws SQLException{
    	int result = -1;
    	if (connectionExists()) {
    		Statement st = connection.createStatement();
    		if (!esisteEnum("modalità")) {
    			String sql = "CREATE TYPE modalità AS ENUM ('Telematico','Fisico');";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
    		}
    	}
    	return result;
    }
    
    //Metodo che crea il type enum piattaforma di un meeting nel DB (necessario per definire la tabella Meeting)
    private int creaEnumPiattaforma() throws SQLException{
    	int result = -1;
    	if (connectionExists()) {
    		Statement st = connection.createStatement();
    		if (!esisteEnum("piattaforma")) {
    			String sql = "CREATE TYPE piattaforma AS ENUM('Microsoft Teams',"
    					+ "'Discord',"
    					+ "'Google Meet',"
    					+ "'Zoom',"
    					+ "'Cisco Webex',"
    					+ "'Skype',"
    					+ "'Altro');";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
    		}
    	}
    	return result;
    }
    
    //Metodo che crea la tabella Meeting
    private int creaTabellaMeeting() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
    	creaEnumModalita();	//crea enum necessaria
    	creaEnumPiattaforma();	//crea enum necessaria
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Meeting")) {
                String sql = "CREATE TABLE Meeting(\r\n"
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
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella AmbitoProgettoLink (associazione AmbitoProgetto <-> Progetto)
    private int creaTabellaAmbitoProgettoLink() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("AmbitoProgettoLink")) {
                String sql = "CREATE TABLE AmbitoProgettoLink(\r\n"
                		+ "	IDAmbito integer NOT NULL,\r\n"
                		+ "	CodProgetto integer NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT AmbitoProgettoEsistente UNIQUE(IDAmbito,CodProgetto),\r\n"
                		+ "	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,\r\n"
                		+ "	FOREIGN KEY (IDAmbito) REFERENCES AmbitoProgetto(IDAmbito)\r\n"
                		+ ");";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il type enum ruolo di un dipendente in un progetto nel DB (necessario per definire la tabella Partecipazione)
    private int creaEnumRuolo() throws SQLException{
    	int result = -1;
    	if (connectionExists()) {
    		Statement st = connection.createStatement();
    		if (!esisteEnum("ruolo")) {
    			String sql = "CREATE TYPE ruolo AS ENUM('Project Manager',"
    					+ "'Team Member',"
    					+ "'Team Leader',"
    					+ "'Chief Financial Officer');";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
    		}
    	}
    	return result;
    }
    
    //Metodo che crea la tabella Partecipazione (associazione Progetto <-> Dipendente)
    private int creaTabellaPartecipazione() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
    	creaEnumRuolo();	//crea enum necessaria
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Partecipazione")) {
                String sql = "CREATE TABLE Partecipazione(\r\n"
                		+ "	CodProgetto integer NOT NULL,\r\n"
                		+ "	CF char(16) NOT NULL,\r\n"
                		+ "	RuoloDipendente ruolo NOT NULL, --Project Manager = Creatore\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT CfPartecipazione CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),\r\n"
                		+ "	CONSTRAINT PartecipazioneEsistente UNIQUE(CF,CodProgetto),\r\n"
                		+ "	FOREIGN KEY (CodProgetto) REFERENCES Progetto(CodProgetto) ON DELETE CASCADE,\r\n"
                		+ "	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
                		+ ");";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella Abilità (associazione Skill <-> Dipendente)
    private int creaTabellaAbilita() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Abilità")) {
                String sql = "CREATE TABLE Abilità(\r\n"
                		+ "	IDSkill integer,\r\n"
                		+ "	CF char(16) NOT NULL,\r\n"
                		+ "	\r\n"
                		+ "	CONSTRAINT CfAbilità CHECK(CF ~* '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),\r\n"
                		+ "	CONSTRAINT SkillEsistente UNIQUE(IDSkill,CF),\r\n"
                		+ "	FOREIGN KEY (IDSkill) REFERENCES Skill(IDSkill),\r\n"
                		+ "	FOREIGN KEY (CF) REFERENCES Dipendente(CF) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
                		+ ");";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la tabella Presenza (associazione Meeting <-> Dipendente)
    private int creaTabellaPresenza() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTabella("Presenza")) {
                String sql = "CREATE TABLE Presenza(\r\n"
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
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea tutte le tabelle di sopra
    public void creaTabelle() throws SQLException{
    	creaTabellaSkill();	//crea la tabella skill
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
    
    //Metodo che crea la funzione esterna accavallamento per controllare che due eventi non si accavallino temporalmente
    private int creaFunzioneAccavallamento() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION accavallamento(DataInizioOLD DATE, DataFineOLD DATE, DataInizioNEW DATE, DataFineNEW DATE, OraInizioOLD TIME, OraFineOLD TIME, OraInizioNEW TIME, OraFineNEW TIME )\r\n"
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
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna valutazioneMeeting che calcola la valutazione di un dipendente in base alle sue presenze ai meeting
    private int creaFunzioneValutazioneMeeting() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION ValutazioneMeeting(dip Dipendente.CF%TYPE) RETURNS FLOAT\r\n"
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
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna valutazioneProgetti che calcola la valutazione di un dipendente in base alle sue partecipazioni ai progetti
    private int creaFunzioneValutazioneProgetti() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION ValutazioneProgetti(dip Dipendente.CF%TYPE) RETURNS FLOAT\r\n"
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
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna valutazione che calcola la valutazione media di un dipendente partendo dalle due precedenti
    private int creaFunzioneValutazione() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION Valutazione(dip Dipendente.CF%TYPE) RETURNS FLOAT\r\n"
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
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_accavallamenti_sale che controlla se avvengono accavallamenti di meeting nella stessa sala
    private int creaFunzioneCheckAccavallamentiSale() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION check_accavallamenti_sale() RETURNS TRIGGER\r\n"
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
            		+ "				USING HINT = 'Per favore controlla gli orari o cambia sala per questo meeting.';\r\n"
            		+ "				RETURN OLD;\r\n"
            		+ "			END IF;\r\n"
            		+ "		END LOOP;\r\n"
            		+ "	END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger no_accavallamenti che controlla gli accavallamenti dei meeting nella stessa sala
    private int creaTriggerNoAccavallamenti() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("no_accavallamenti")) {
                String sql = "CREATE TRIGGER no_accavallamenti BEFORE INSERT OR UPDATE ON Meeting\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "WHEN (NEW.Modalità='Fisico')\r\n"
                		+ "EXECUTE PROCEDURE check_accavallamenti_sale();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_capienza_presenza per controllare che la capienza delle sale sia rispettata
    private int creaFunzioneCheckCapienzaPresenza() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION check_capienza_presenza() RETURNS TRIGGER\r\n"
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
            		+ "				RAISE WARNING 'Il numero di invitati al meeting supera la capienza (%) della sala stabilita', Cap\r\n"
            		+ "				USING HINT = 'Si consiglia di cambiare sala';\r\n"
            		+ "				RETURN NEW;\r\n"
            		+ "		END IF;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger capienza_rispettata_presenza per controllare che la capienza sia rispettata sempre
    private int creaTriggerCapienzaRispettataPresenza() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("capienza_rispettata_presenza")) {
                String sql = "CREATE TRIGGER capienza_rispettata_presenza BEFORE INSERT OR UPDATE ON Presenza\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_capienza_presenza();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_capienza_meeting per controllare che la capienza delle sale sia rispettata
    private int creaFunzioneCheckCapienzaMeeting() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION check_capienza_meeting() RETURNS TRIGGER\r\n"
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
            		+ "		RAISE WARNING 'Il numero di invitati al meeting supera la capienza (%) della nuvova sala %', Cap, NEW.CodSala\r\n"
            		+ "		USING HINT = 'Si consiglia di cambiare sala';\r\n"
            		+ "		RETURN NEW;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger capienza_rispettata_meeting per controllare che la capienza sia rispettata sempre
    private int creaTriggerCapienzaRispettataMeeting() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("capienza_rispettata_meeting")) {
                String sql = "CREATE TRIGGER capienza_rispettata_meeting AFTER UPDATE OF CodSala ON Meeting\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_capienza_meeting();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_skill_existence per eliminare skill non più utilizzate da nessuno nel DB
    private int creaFunzioneCheckSkillExistence() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION check_skill_existence() RETURNS TRIGGER\r\n"
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
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger composizione_skill per eliminare le skill inutilizzate
    private int creaTriggerComposizioneSkill() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("composizione_skill")) {
                String sql = "CREATE TRIGGER composizione_skill AFTER DELETE ON Abilità\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_skill_existence();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_onnipresenza_presenza per controllare eventuali casi di onnipresenza dei dipendenti
    private int creaFunzioneCheckOnnipresenzaPresenza() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION check_onnipresenza_presenza()\r\n"
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
            		+ "			USING HINT = 'Cambia il meeting oppure chiedi al dipendente di organizzarsi.';\r\n"
            		+ "			RETURN OLD;\r\n"
            		+ "		END IF;\r\n"
            		+ "	END LOOP;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger no_onnipresenza_presenza che controlla per le onnipresenze dei dipendenti
    private int creaTriggerNoOnnipresenzaPresenza() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("no_onnipresenza_presenza")) {
                String sql = "CREATE TRIGGER no_onnipresenza_presenza BEFORE INSERT OR UPDATE ON Presenza\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_onnipresenza_presenza();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_onnipresenza_meeting per controllare eventuali casi di onnipresenza dei dipendenti
    private int creaFunzioneCheckOnnipresenzaMeeting() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION check_onnipresenza_meeting()\r\n"
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
            		+ "			USING HINT = 'Cambia il meeting oppure chiedi al dipendente di organizzarsi.';\r\n"
            		+ "			RETURN OLD;\r\n"
            		+ "		END IF;\r\n"
            		+ "	END LOOP;\r\n"
            		+ "END LOOP;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger no_onnipresenza_meeting che controlla per le onnipresenze dei dipendenti
    private int creaTriggerNoOnnipresenzaMeeting() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("no_onnipresenza_meeting")) {
                String sql = "CREATE TRIGGER no_onnipresenza_meeting BEFORE UPDATE ON Meeting\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_onnipresenza_meeting();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna Abilità_uppercase per inserire solo cf con caratteri maiuscoli in Abilità
    private int creaFunzioneAbilitaUppercase() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION Abilità_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger CF_uppercase_Abilità
    private int creaTriggerCFUppercaseAbilita() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("CF_uppercase_Abilità")) {
                String sql = "CREATE TRIGGER CF_uppercase_Abilità\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Abilità\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Abilità_uppercase();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna Dipendente_uppercase per inserire sempre codici fiscali in maiuscolo
    private int creaFunzioneDipendenteUppercase() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION Dipendente_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger CF_uppercase_Dipendente per inserire codici fiscali in maiuscolo in Dipendente
    private int creaTriggerCFUppercaseDipendente() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("CF_uppercase_Dipendente")) {
                String sql = "CREATE TRIGGER CF_uppercase_Dipendente\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Dipendente\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Dipendente_uppercase();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna Presenza_uppercase per inserire sempre codici fiscali in maiuscolo
    private int creaFunzionePresenzaUppercase() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION Presenza_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger CF_uppercase_Presenza per inserire codici fiscali in maiuscolo in Presenza
    private int creaTriggerCFUppercasePresenza() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("CF_uppercase_Presenza")) {
                String sql = "CREATE TRIGGER CF_uppercase_Presenza\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Presenza\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Presenza_uppercase();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna Partecipazione_uppercase per inserire sempre codici fiscali in maiuscolo
    private int creaFunzionePartecipazioneUppercase() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
        //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            String sql = "CREATE OR REPLACE FUNCTION Partecipazione_uppercase() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL \r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "  NEW.CF = UPPER(NEW.CF);  \r\n"
            		+ "  RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger CF_uppercase_Partecipazione per inserire codici fiscali in maiuscolo in Partecipazione
    private int creaTriggerCFUppercasePartecipazione() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione della tabella
            //se la tabella non esiste scrivi la definizione della tabella ed esegui la sua creazione
            if (!esisteTrigger("CF_uppercase_Partecipazione")) {
                String sql = "CREATE TRIGGER CF_uppercase_Partecipazione\r\n"
                		+ "  BEFORE INSERT OR UPDATE\r\n"
                		+ "  ON Partecipazione\r\n"
                		+ "  FOR EACH ROW\r\n"
                		+ "  EXECUTE PROCEDURE Partecipazione_uppercase();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea la funzione esterna check_projectmanager che controlla che ci sia sempre un solo project manager per progetto
    private int creaFunzioneCheckProjectManager() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
        Statement st = connection.createStatement();	//crea uno statement per la creazione della funzione
        //se la funzione non esiste
            String sql = "CREATE OR REPLACE FUNCTION check_projectmanager() RETURNS TRIGGER\r\n"
            		+ "LANGUAGE PLPGSQL\r\n"
            		+ "AS $$\r\n"
            		+ "BEGIN\r\n"
            		+ "--Controlla che non ci siano altri record in Partecipazione con stesso progetto e ruolo project manager\r\n"
            		+ "IF (EXISTS (SELECT p.CF\r\n"
            		+ "			FROM Partecipazione AS p\r\n"
            		+ "			WHERE p.CodProgetto = NEW.CodProgetto AND p.RuoloDipendente = 'Project Manager' AND p.ruoloDipendente = NEW.ruoloDipendente)) THEN\r\n"
            		+ "				RAISE EXCEPTION 'Esiste già un project manager per il progetto di codice %', NEW.CodProgetto;\r\n"
            		+ "				RETURN OLD;\r\n"
            		+ "END IF;\r\n"
            		+ "RETURN NEW;\r\n"
            		+ "END;\r\n"
            		+ "$$;";
            result = st.executeUpdate(sql);
            st.close();	//chiudi statement
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea il trigger unicità_projectmanager che controlla che esista sempre solo un project manager per progetto
    private int creaTriggerUnicitaProjectManager() throws SQLException{
    int result = -1;	//risultato pari a -1 per ora
    //se la connessione con il db esiste
    if(connectionExists()) {
            Statement st = connection.createStatement();	//crea uno statement per la creazione del trigger
            //se il trigger non esiste
            if (!esisteTrigger("unicità_projectmanager")) {
                String sql = "CREATE TRIGGER unicità_projectmanager BEFORE INSERT ON Partecipazione\r\n"
                		+ "FOR EACH ROW\r\n"
                		+ "EXECUTE PROCEDURE check_projectmanager();";
                result = st.executeUpdate(sql);
                st.close();	//chiudi statement
                }
            }
    return result;	//restituisce il risultato per sapere se la creazione ha avuto o meno luogo
    }
    
    //Metodo che crea tutte le funzioni esterne
    public void creaFunzioniTrigger() throws SQLException{
    	creaFunzioneAccavallamento();	//accavallamento temporale
    	
    	creaFunzioneValutazioneMeeting();	//funzioni per valutazione dipendente
    	creaFunzioneValutazioneProgetti();
    	creaFunzioneValutazione();
    	
    	creaFunzioneCheckAccavallamentiSale();	//accavallamento nella stessa sala
    	creaTriggerNoAccavallamenti();
    	
    	creaFunzioneCheckCapienzaPresenza();	//capienza rispettata nelle sale
    	creaTriggerCapienzaRispettataPresenza();
    	creaFunzioneCheckCapienzaMeeting();
    	creaTriggerCapienzaRispettataMeeting();
    	
    	creaFunzioneCheckSkillExistence();	//rimozione skill non più utilizzate
    	creaTriggerComposizioneSkill();
    	
    	creaFunzioneCheckOnnipresenzaPresenza();	//onnipresenza dei dipendenti in più meeting e luoghi
    	creaTriggerNoOnnipresenzaPresenza();
    	creaFunzioneCheckOnnipresenzaMeeting();
    	creaTriggerNoOnnipresenzaMeeting();
    	
    	creaFunzioneAbilitaUppercase();	//uppercase di codice fiscale
    	creaTriggerCFUppercaseAbilita();
    	creaFunzioneDipendenteUppercase();
    	creaTriggerCFUppercaseDipendente();
    	creaFunzionePresenzaUppercase();
    	creaTriggerCFUppercasePresenza();
    	creaFunzionePartecipazioneUppercase();
    	creaTriggerCFUppercasePartecipazione();
    	
    	creaFunzioneCheckProjectManager();	//unicità del project manager per ogni progetto
    	creaTriggerUnicitaProjectManager();
    }
    
    //metodo che crea il primo dipendente nel db
    private int inserisciPrimoDipendente() throws SQLException
    {
    	int result =-1;
    	if(connectionExists())
    	{
    		Statement stmt = connection.createStatement();
    		
    		//inserisce il dipendente Mario Rossi
    		String sql = "INSERT INTO Dipendente(CF,Nome,Cognome,DataNascita,Sesso,Indirizzo,Email,TelefonoCasa,Cellulare,Salario,Password,CodComune) VALUES\r\n"
    				+ "	('RSSMRA91C06F839S','Mario','Rossi','06/03/1991','M','via sdff,28','m.rossi@unina.it','0817589891','3878998999',100,'pass','F839')";
    		
    		result = stmt.executeUpdate(sql);
    		stmt.close();
    	}
    	return result;
    }
    
    //metodo che crea degli ambiti predefiniti da cui partire (poi la segreteria volendo ne aggiungerà altri)
    private int creaAmbitiPredefiniti() throws SQLException
    {
    	int result =-1;
    	
    	if(connectionExists()) 
    	{
    		Statement stmt = connection.createStatement();
    		
    		//inserisci degli ambiti iniziali predefiniti
    		String sql ="INSERT INTO AmbitoProgetto(NomeAmbito) VALUES\r\n"
    				+ "	('Economia'),\r\n"
    				+ "	('Medicina'),\r\n"
    				+ "	('Militare'),\r\n"
    				+ "	('Scientifico'),\r\n"
    				+ "	('Altro')";
    		
    		result = stmt.executeUpdate(sql);
    		stmt.close();
    	}
    	return result;
    }
    
    //Metodo che inserisce dai dati da cui partire per poter utilizzare subito il software
    public void inserisciDatiIniziali() throws SQLException
    {
    	creaAmbitiPredefiniti(); // inserisce degli ambiti di partenza
    }
    
    //INSERIMENTO LUOGHI DI NASCITA DA AGENZIA DELLE ENTRATE
    //-------------------------------------------------------
    
    public void importaLuoghi() {
    	CopyManager copyManager = null;
		try {
			copyManager = new CopyManager((BaseConnection) connection);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathCSVLuoghi));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			copyManager.copyIn("COPY LuogoNascita (NomeComune,NomeProvincia,CodComune) FROM STDIN WITH (FORMAT CSV, DELIMITER ';')", reader);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
