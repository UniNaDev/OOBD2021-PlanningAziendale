//Classe main del programma

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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
			ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
			Connection connection = connDB.getConnection();
			CostruttoreDB costruttoreDB = new CostruttoreDB(connection);
			try {
				costruttoreDB.creaTabelle();
				costruttoreDB.creaFunzioniTrigger();
				costruttoreDB.importaLuoghi();
			} catch (SQLException e){
				//errori non contemplati (che non sono tabelle già esistenti o trigger già esistenti)
				if (!e.getSQLState().equals("42P07") && !e.getSQLState().equals("42710"))
					JOptionPane.showMessageDialog(null,
							e.getMessage() + "\nContattare uno sviluppatore.",
							"Errore #" + e.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
				}
			
			DipendenteDAO dipDAO = null;
			LuogoNascitaDAO luogoDAO = null;
			ProgettoDAO projDAO = null;
			MeetingDAO meetDAO = null;
			SkillDAO skillDAO = null;
			SalaRiunioneDAO salaDAO = null;
			AmbitoProgettoDAO ambitoDAO = null;
			
			boolean isSegreteria = false;
			
			dipDAO = new DipendenteDAOPSQL(connection);
			luogoDAO = new LuogoNascitaDAOPSQL(connection);
			projDAO = new ProgettoDAOPSQL(connection);
			meetDAO = new MeetingDAOPSQL(connection);
			skillDAO = new SkillDAOPSQL(connection);
			salaDAO = new SalaRiunioneDAOPSQL(connection);
			ambitoDAO= new AmbitoProgettoDAOPSQL(connection);
			
			if (args[0].equals("-s"))
				isSegreteria = true;
			else if (args[0].equals("-d"))
				isSegreteria = false;
				
			ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, isSegreteria);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null,
					"Mancano argomenti di autorizzazione in input.\nContattare uno sviluppatore.",
					"Errore Argomenti Autorizzazione",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
		JOptionPane.showMessageDialog(null,
		    "Impossibile stabilire una connessione con il database.",
		    "Errore connessione #" + e.getErrorCode(),
		    JOptionPane.ERROR_MESSAGE);
		}
	}
}
