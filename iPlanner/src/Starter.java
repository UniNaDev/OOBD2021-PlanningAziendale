import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import controller.ControllerStart;
import dbManager.CostruttoreDB;
import dbManager.ManagerConnessioneDB;
import implementazioniDAO.AmbitoProgettoDAOPSQL;
import implementazioniDAO.DipendenteDAOPSQL;
import implementazioniDAO.LuogoNascitaDAOPSQL;
import implementazioniDAO.MeetingDAOPSQL;
import implementazioniDAO.ProgettoDAOPSQL;
import implementazioniDAO.SalaRiunioneDAOPSQL;
import implementazioniDAO.SkillDAOPSQL;
import interfacceDAO.AmbitoProgettoDAO;
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
			
			//Inizializza il costruttore del DB
			CostruttoreDB costruttoreDB = new CostruttoreDB(connection);

			//Crea tabelle del DB
			try {
				costruttoreDB.creaTabelle();	//crea tutte le tabelle del DB
				costruttoreDB.creaFunzioniTrigger(); 	//crea tutte le funzioni esterne e i trigger del DB
				costruttoreDB.importaLuoghi();	//import dei luoghi italiani
				costruttoreDB.inserisciDatiIniziali(); //inserisce dei dati iniziali per poter utilizzare subito il software
			}
			catch (SQLException e){
				//se l'eccezione non è in merito all'esistenza delle tabelle che tenta di creare
				if (!e.getSQLState().equals("42P07"))
					JOptionPane.showMessageDialog(null,
							e.getMessage() + "\nContattare uno sviluppatore.",
							"Errore #" + e.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
			}
			
			//inizializza i DAO
			DipendenteDAO dipDAO = null;	//dao dipendente
			LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
			ProgettoDAO projDAO = null;	//dao progetto
			MeetingDAO meetDAO = null;	//dao meeting
			SkillDAO skillDAO = null;	//dao delle skill
			SalaRiunioneDAO salaDAO = null;	//dao delle sale riunioni
			AmbitoProgettoDAO ambitoDAO = null;	//dao degli ambiti
			
			boolean segreteria = false;	//indica il tipo di autorizzazione (true = segreteria, false = dipendente)
			
			//Inizializzazione DAO implementati per PostgreSQL
			dipDAO = new DipendenteDAOPSQL(connection);
			luogoDAO = new LuogoNascitaDAOPSQL(connection);
			projDAO = new ProgettoDAOPSQL(connection);
			meetDAO = new MeetingDAOPSQL(connection);
			skillDAO = new SkillDAOPSQL(connection);
			salaDAO = new SalaRiunioneDAOPSQL(connection);
			ambitoDAO= new AmbitoProgettoDAOPSQL(connection);
			
			//Ottiene il tipo di autorizzaione (-s = autorizzazione per segreteria, -d autorizzazione per dipendenti)
			if (args[0].equals("-s"))
				segreteria = true;
			else if (args[0].equals("-d"))
				segreteria = false;
				
			ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, segreteria);	//inizializza controller iniziale passandogli l'autorizzazione e i dao
			}
			catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null,
						"Mancano argomenti di autorizzazione in input.",
						"Errore argomenti iniziali",
						JOptionPane.ERROR_MESSAGE);	//errore argomenti a linea di comando mancanti
			}
			catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
			    "Impossibile stabilire una connessione con il database.",
			    "Errore connessione #" + e.getErrorCode(),
			    JOptionPane.ERROR_MESSAGE);	//errore di connessione
		}
	}

}
