import java.sql.Connection;
import java.sql.SQLException;

import Controller.ControllerErrori;
import Controller.ControllerStart;
import DBManager.ManagerConnessioneDB;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import InterfacceDAO.LuogoNascitaDAO;

public class Start {
	
	public static void main(String[] args) {
		
		try {
			ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
			
			Connection connection = connDB.getConnection();	//crea connessione al DB
			
			ControllerStart controller = new ControllerStart(connection);	//inizializza controller iniziale
		} 
		catch (SQLException e) {
			ControllerErrori erroriCTRL = new ControllerErrori("Errore codice " + e.getErrorCode() + "\n" + e.getMessage(), true);	//errore connessione al DB
		}
	}

}
