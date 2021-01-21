//Classe costruttore del DB. Contiene la definizione delle tabelle, dei vincoli, dei trigger e di tutto ciò che è necessario per inizializzare il database prima dell'utilizzo del software.

package dbManager;

import java.sql.*;

public class CostruttoreDB {
	
	//ATTRIBUTI
	//------------------------------------------------
    private Connection connection;	//connessione al DB

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

    //CREAZIONE TABELLE
	//------------------------------------------------
    
    //Metodo che crea la tabella Skill
    public int creaTabellaSkill() throws SQLException{
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
}
