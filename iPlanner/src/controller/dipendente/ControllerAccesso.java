//Controller relativo alla finestra di login del programma.
//Contiene tutti i metodi necessari al corretto funzionamento della finestra.

package controller.dipendente;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.ControllerStart;
import entita.Dipendente;
import gui.dipendente.Login;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerAccesso {
    private Login loginFrame;

    private LuogoNascitaDAO luogoDAO = null;
    private DipendenteDAO dipDAO = null;
    private ProgettoDAO projDAO = null;
    private MeetingDAO meetDAO = null;
    private SkillDAO skillDAO = null;
    private SalaRiunioneDAO salaDAO = null;
    private AmbitoProgettoDAO ambitoDAO = null;

    private boolean isSegreteria = false;

    private Dipendente dipendenteLogged = null;

    public ControllerAccesso(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO,
	    SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO, boolean isSegreteria) {
	this.luogoDAO = luogoDAO;
	this.dipDAO = dipDAO;
	this.projDAO = projDAO;
	this.meetDAO = meetDAO;
	this.skillDAO = skillDAO;
	this.salaDAO = salaDAO;
	this.ambitoDAO = ambitoDAO;

	this.isSegreteria = isSegreteria;

	loginFrame = new Login(this);
	loginFrame.setVisible(true);

    }

    public void eseguiLogin(String email, String password) throws SQLException {
	dipendenteLogged = dipDAO.getLoggedDipendente(email, password);
	try {
	    dipendenteLogged.setSkills(skillDAO.getSkillsDipendente(dipendenteLogged.getCf()));
	} catch (SQLException e) {
	    JOptionPane.showMessageDialog(null,
		    e.getMessage()
		    + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
		    "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
	}
	loginFrame.setVisible(false);
	ControllerGestioneProfilo controller = new ControllerGestioneProfilo(luogoDAO, dipDAO, projDAO, meetDAO,
		skillDAO, salaDAO, ambitoDAO, dipendenteLogged);
    }

    public void tornaAIPlanner() {
	loginFrame.setVisible(false);
	ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO,
		ambitoDAO, isSegreteria);
    }
}