//Classe main del programma

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

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

	// L'UIMANAGER IMPOSTA UNA SERIE DI PROPRIETÀ PER TUTTI COMPONENTI UI PRESENTI
	// NEL SOFTWARE

	UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.PLAIN, 15)); // font dei JOptionPane
	UIManager.put("OptionPane.buttonFont", new Font("Consolas", Font.PLAIN, 15)); // font dei bottoni nei
										      // JOptionPane

	UIManager.put("Button.background", Color.WHITE); // sfondo di tutti i bottoni compresi quelli dei JOptionPane
	UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0))); // rimuove il rettangolo di selezione
										   // presente normalmente quando si
										   // preme su un bottone
	UIManager.put("Button.border", new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY)); // border di tutti i bottoni
	
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
