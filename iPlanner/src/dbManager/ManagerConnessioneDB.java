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

import javax.swing.JOptionPane;

import gui.ErroreDialog;


public class ManagerConnessioneDB {
	private static ManagerConnessioneDB instance;
	
	private Connection connection = null;
	
	private final String username = "postgres";
	private final String password = "michele";
	private final String nomeDB = "MikeDB";
	private final String url = "jdbc:postgresql://localhost:5432/";
	
	private final String DB_ESISTENTE = "42P04";
	
	private ManagerConnessioneDB() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url,username,password);
			creaDatabase(nomeDB);
			connection = DriverManager.getConnection(url+nomeDB,username,password);
		} catch(ClassNotFoundException ex) {
			ErroreDialog errore = new ErroreDialog(ex, "Connessione Fallita", "Impossibile stabilire una connessione al database.", true);
			errore.setVisible(true);
        }
	}

	public Connection getConnection() {
		return connection;
	}
	
	public static ManagerConnessioneDB getInstance() throws SQLException {
		if (instance==null)
			instance = new ManagerConnessioneDB();
		else if (instance.getConnection().isClosed())
			instance = new ManagerConnessioneDB();
		
		return instance;
	}
	
    private void creaDatabase(String nomeDB) throws SQLException {
    	try {
	        Statement statement = null;
	        statement = connection.createStatement();
	        statement.executeUpdate("CREATE DATABASE " + nomeDB);
	        statement.close();
    	}
        catch (SQLException e) {
        	if (!e.getSQLState().equals(DB_ESISTENTE)) {
        		ErroreDialog errore = new ErroreDialog(e, true);
        		errore.setVisible(true);
        	}
        }
    }
}
