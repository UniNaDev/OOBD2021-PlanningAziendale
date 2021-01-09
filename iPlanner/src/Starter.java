import java.sql.Connection;
import java.sql.SQLException;

import controller.ControllerScelta;
import dbManager.ManagerConnessioneDB;
import implementazioniDAO.DipendenteDAOPSQL;
import implementazioniDAO.LuogoNascitaDAOPSQL;
import implementazioniDAO.MeetingDAOPSQL;
import implementazioniDAO.ProgettoDAOPSQL;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;

public class Starter {
	
	public static void main(String[] args) {
		
		try {
			ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
			
			Connection connection = connDB.getConnection();	//crea connessione al DB
			
			DipendenteDAO dipDAO = null;
			LuogoNascitaDAO luogoDAO = null;
			ProgettoDAO projDAO = null;
			MeetingDAO meetDAO = null;
			
			boolean segreteria = false;
			
			//Inizializzazione DAO implementati per PostgreSQL
			if (args[0].equals("postgres")) {
				dipDAO = new DipendenteDAOPSQL(connection);
				luogoDAO = new LuogoNascitaDAOPSQL(connection);
				projDAO = new ProgettoDAOPSQL(connection);
				meetDAO = new MeetingDAOPSQL(connection);
			}
			else if (args[0].equals("oracle")) {
				System.out.println("TODO: implementazione oracle");
			}
			
			if (args[1].equals("-s"))
				segreteria = true;
			else if (args[1].equals("-d"))
				segreteria = false;
				
			
			ControllerScelta controller = new ControllerScelta(segreteria, luogoDAO, dipDAO, projDAO, meetDAO);	//inizializza controller iniziale
		} 
		catch (SQLException e) {
			//ControllerErrori erroriCTRL = new ControllerErrori("Errore codice " + e.getErrorCode() + "\n" + e.getMessage(), true);	//errore connessione al DB
		}
		catch (ArrayIndexOutOfBoundsException e) {
			//ControllerErrori erroriCTRL = new ControllerErrori("Errore.\nManca argomento implementazione.", true);	//errore avvio
		}
	}

}
