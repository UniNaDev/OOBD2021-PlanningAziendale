//Classe main del programma

import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

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
			
			//Setta il font di tutti i JOptionPane nel software
			UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.PLAIN, 15));
			UIManager.put("OptionPane.buttonFont", new Font("Consolas", Font.BOLD, 15));
		
		try {
			ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
			Connection connection = connDB.getConnection();
			CostruttoreDB costruttoreDB = new CostruttoreDB(connection);
			costruttoreDB.creaTabelle();
			costruttoreDB.creaFunzioniTrigger();
			costruttoreDB.importaLuoghi();

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
			ambitoDAO = new AmbitoProgettoDAOPSQL(connection);

			try {
				if (args[0].equals("-s"))
					isSegreteria = true;
				else if (args[0].equals("-d"))
					isSegreteria = false;

				ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO,
						ambitoDAO, isSegreteria);
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null,
						"Mancano argomenti di autorizzazione in input.\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
						"Errore Argomenti Autorizzazione", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage() + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
}
