/******************************************************************************
 * Classe ManagerConnessioneDB
 * La classe ha lo scopo di aprire e chiudere la connessione con il database e
 * di conservare le proprietà del database come username, password e url.
 * Di questa classe esiste un'unica istanza che sfrutteremo.
 *****************************************************************************/

package dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerConnessioneDB {
	
	//ATTRIBUTI
	
	private static ManagerConnessioneDB instance;	//istanza singola della classe
	
	private Connection connection = null;	//connessione al DB
	
	private final String username = "postgres";	//username per accesso al DB
	private final String password = "160995"; //password per accesso al DB
	private final String nomeDB = "iPlanner";	//nome del database
	private final String url = "jdbc:postgresql://localhost:5432/";	//url per accedere al DB su AWS
	
	//METODI
	
	//Costruttore del ManagerConnessioneDB
	/*Costruttore privato che può essere usato solo qui quando viene definita una sua istanza.
	*Quando viene costruito il ManagerConnessioneDB viene automaticamente creata anche una
	*connessione (connection:Connection) al DB. Se la connessione fallisce, restituisce un'eccezione
	*(SQLException).*/
	private ManagerConnessioneDB() throws SQLException
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url,username,password);	//stabilisce la connessione al DB
			creaDatabase(nomeDB);	//crea il database se non esiste
			connection = DriverManager.getConnection(url+nomeDB,username,password);	//si connette al DB creato/esistente
		}
		catch(ClassNotFoundException ex) 
		{
			System.out.println("Connessione al database fallita: " + ex.getMessage());	//messaggio di errore in caso di fallimento
		}
	}
	
	//Metodo getter della connessione GetConnection(): Connection
	/*Restituisce semplicemente la connessione al DB creata dal ManagerConnessioneDB.*/
	public Connection getConnection()
	{
		return connection;
	}
	
	//Metodo getter dell'istanza del ManagerConnessioneDB GetInstance(): ManagerConnessioneDB.
	/*Metodo static e public. Quando viene chiamato restituisce l'istanza del ManagerConnessioneDB
	*(e quindi volendo la connessione creata al DB) se l'istanza già esiste e la connessione è aperta,
	*altrimenti la definisce sul posto.*/
	public static ManagerConnessioneDB getInstance() throws SQLException
	{
		if (instance==null)	//se l'istanza non esiste ne crea una (e stabilisce una connessione al DB)
			instance = new ManagerConnessioneDB();
		else
			if (instance.getConnection().isClosed())	//se l'istanza esiste ma la connessione è chiusa la riapre creando una nuova istanza
				instance = new ManagerConnessioneDB();
		
		return instance;
	}
	
	//Metodo che crea il database nel caso non esista già
    private void creaDatabase(String nomeDB)
    {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + nomeDB);
            statement.close();
        }
        catch (SQLException sqlException) {
        	//Errore database già esistente
            if (sqlException.getErrorCode() == 1007) {
                System.out.println("Database " + nomeDB + " già esiste.");
            } 
            //altri errori
            else {
                System.out.println(sqlException.getMessage());
            }
        }
    }
}
