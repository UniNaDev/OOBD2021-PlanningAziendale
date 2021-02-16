//Classe main del programma

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

import controller.ControllerStart;
import dbManager.CostruttoreDB;
import dbManager.ManagerConnessioneDB;
import gui.customUI.DefaultLookManager;
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
	DefaultLookManager lookManager = new DefaultLookManager();
	lookManager.setDefaultLook();
	
	try {
	    ManagerConnessioneDB connDB = ManagerConnessioneDB.getInstance();
	    Connection connection = connDB.getConnection();
	    CostruttoreDB costruttoreDB = new CostruttoreDB(connection);
	    costruttoreDB.creaTabelle();
	    costruttoreDB.creaFunzioniTrigger();
	    costruttoreDB.importaLuoghi();

	    DipendenteDAO dipDAO = new DipendenteDAOPSQL(connection);
	    LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(connection);
	    ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);
	    MeetingDAO meetDAO = new MeetingDAOPSQL(connection);
	    SkillDAO skillDAO = new SkillDAOPSQL(connection);
	    SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
	    AmbitoProgettoDAO ambitoDAO = new AmbitoProgettoDAOPSQL(connection);

	    boolean isSegreteria = false;

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
		    e.getMessage()
			    + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
		    "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
	}
    }
}
