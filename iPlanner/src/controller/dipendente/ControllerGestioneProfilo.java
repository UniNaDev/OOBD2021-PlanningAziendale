//Controller relativo alle finestre di Home e Mio Account del programma.
//Contiene tutti i metodi per far funzionare correttamente le finestre e per viaggiare tra esse.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Meeting;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.LuogoNascita;
import gui.dipendente.Home;
import gui.dipendente.MioAccount;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerGestioneProfilo {
    private Home homeFrame;
    private MioAccount accountFrame;

    private LuogoNascitaDAO luogoDAO = null;
    private DipendenteDAO dipDAO = null;
    private ProgettoDAO projDAO = null;
    private MeetingDAO meetDAO = null;
    private SkillDAO skillDAO = null;
    private SalaRiunioneDAO salaDAO = null;
    private AmbitoProgettoDAO ambitoDAO = null;

    private Dipendente dipendenteLogged = null;

    public ControllerGestioneProfilo(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO,
	    MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO,Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
	
		this.dipendenteLogged = dipendenteLogged;
	
		homeFrame = new Home(this, dipendenteLogged);
		homeFrame.setVisible(true);

    }

    public void apriMioAccount() {
		accountFrame = new MioAccount(this, dipendenteLogged);
		accountFrame.setVisible(true);
    }

    public void chiudiMioAccount() {
    	accountFrame.setVisible(false);
    }

    public void logout() {
    	System.exit(0);
    }

    public void apriMieiProgetti() {
    	ControllerProgetto controller = new ControllerProgetto(luogoDAO, dipDAO, projDAO, meetDAO, ambitoDAO, skillDAO,
    	dipendenteLogged);
    }

    public void apriMieiMeeting() {
    	ControllerMeeting controller = new ControllerMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO,
		dipendenteLogged);
    }

    public void tornaAHome() {
		homeFrame.setVisible(false);
		homeFrame = new Home(this, dipendenteLogged);
		homeFrame.setVisible(true);
    }

    public ArrayList<CollaborazioneProgetto> ottieniProgetti() throws SQLException {
    	return projDAO.getProgettiByDipendente(dipendenteLogged);
    }

    public ArrayList<Meeting> ottieniMeeting() throws SQLException {
    	return meetDAO.getMeetingsByInvitato(dipendenteLogged);
    }

    public void aggiornaInfoDipendente(Dipendente dipendenteModificato) throws SQLException {
    	dipDAO.updateDipendente(dipendenteModificato);
    }

    public ArrayList<String> ottieniProvince() throws SQLException {
    	return luogoDAO.getProvince();
    }

    public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException {
    	return luogoDAO.getLuoghiByProvincia(provincia);
    }
}
