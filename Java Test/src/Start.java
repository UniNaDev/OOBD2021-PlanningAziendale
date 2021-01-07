import java.sql.Connection;
import java.sql.SQLException;

import Controller.ControllerErrori;
import Controller.ControllerStart;
import DBManager.ManagerConnessioneDB;
import ImplementazioneDAO.DipendenteDAOPSQL;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;

public class Start {
	
	public static void main(String[] args) {
		
		try {
			ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
			
			Connection connection = connDB.getConnection();	//crea connessione al DB
			
			DipendenteDAO dipDAO = null;
			LuogoNascitaDAO luogoDAO = null;
			
			//Inizializzazione DAO implementati per PostgreSQL
			if (args[0].equals("postgres")) {
				dipDAO = new DipendenteDAOPSQL(connection);
				luogoDAO = new LuogoNascitaDAOPSQL(connection);
			}
			else if (args[0].equals("oracle")) {
				System.out.println("TODO: implementazione oracle");
			}
			
			ControllerStart controller = new ControllerStart(connection, luogoDAO, dipDAO);	//inizializza controller iniziale
		} 
		catch (SQLException e) {
			ControllerErrori erroriCTRL = new ControllerErrori("Errore codice " + e.getErrorCode() + "\n" + e.getMessage(), true);	//errore connessione al DB
		}
		catch (ArrayIndexOutOfBoundsException e) {
			ControllerErrori erroriCTRL = new ControllerErrori("Errore.\nManca argomento implementazione.", true);	//errore avvio
		}
	}

}
