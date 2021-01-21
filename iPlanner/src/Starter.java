import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ControllerScelta;
import dbManager.ManagerConnessioneDB;
import implementazioniDAO.DipendenteDAOPSQL;
import implementazioniDAO.LuogoNascitaDAOPSQL;
import implementazioniDAO.MeetingDAOPSQL;
import implementazioniDAO.ProgettoDAOPSQL;
import implementazioniDAO.SalaRiunioneDAOPSQL;
import implementazioniDAO.SkillDAOPSQL;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class Starter {
	
	public static void main(String[] args) {
		
		try {
			//Crea connessione al database
			ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
			Connection connection = connDB.getConnection();
			
			//inizializza i DAO
			DipendenteDAO dipDAO = null;	//dao dipendente
			LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
			ProgettoDAO projDAO = null;	//dao progetto
			MeetingDAO meetDAO = null;	//dao meeting
			SkillDAO skillDAO = null;	//dao delle skill
			SalaRiunioneDAO salaDAO = null;	//dao delle sale riunioni
			
			boolean segreteria = false;	//indica il tipo di autorizzazione (true = segreteria, false = dipendente)
			
			//Inizializzazione DAO implementati per PostgreSQL
			if (args[0].equals("postgres")) {
				dipDAO = new DipendenteDAOPSQL(connection);
				luogoDAO = new LuogoNascitaDAOPSQL(connection);
				projDAO = new ProgettoDAOPSQL(connection);
				meetDAO = new MeetingDAOPSQL(connection);
				skillDAO = new SkillDAOPSQL(connection);
				salaDAO = new SalaRiunioneDAOPSQL(connection);
			}
			//Implementazioni oracle dei DAO
			else if (args[0].equals("oracle")) {
				System.out.println("TODO: implementazione oracle");
			}
			
			//Ottiene il tipo di autorizzaione (-s = autorizzazione per segreteria, -d autorizzazione per dipendenti)
			if (args[1].equals("-s"))
				segreteria = true;
			else if (args[1].equals("-d"))
				segreteria = false;
				
			ControllerScelta controller = new ControllerScelta(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, segreteria);	//inizializza controller iniziale passandogli l'autorizzazione e i dao
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
			    "Impossibile stabilire una connessione con il database.",
			    "Errore connessione #" + e.getErrorCode(),
			    JOptionPane.ERROR_MESSAGE);	//errore di connessione
		}
		catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null,
					"Mancano argomenti di autorizzazione in input.",
					"Errore argomenti iniziali",
					JOptionPane.ERROR_MESSAGE);	//errore argomenti a linea di comando mancanti
		}
	}

}
