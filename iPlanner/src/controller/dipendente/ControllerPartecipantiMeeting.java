//Controller relativo alla finestra Gestione Partecipanti Meeting lato dipendente.
//Contiene tutti i metodi necessari per farla funzionare correttamente.

package controller.dipendente;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Skill;
import gui.dipendente.GestioneMeetingDipendente;
import gui.dipendente.GestioneProgettiDipendente;
import gui.dipendente.InserisciPartecipantiMeeting;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerPartecipantiMeeting {
	
	private InserisciPartecipantiMeeting inserisciPartecipantiMeetingFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	
	private Dipendente dipendenteLogged = null;
	
	private Meeting meetingSelezionato;
	
	public ControllerPartecipantiMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente dipendente, Meeting meetingSelezionato) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;
		this.skillDAO=skillDAO;
		
		this.dipendenteLogged = dipendente;
		this.meetingSelezionato = meetingSelezionato;
		
		inserisciPartecipantiMeetingFrame = new InserisciPartecipantiMeeting(this,meetingSelezionato);
		inserisciPartecipantiMeetingFrame.setVisible(true);
		
		for (Frame frame : Frame.getFrames()) {
			if (frame.isVisible() && frame.getClass().equals(GestioneMeetingDipendente.class))
				frame.setVisible(false);
		}

	}
	
	public ArrayList<Dipendente> ottieniDipendentiNonInvitatiMeeting(Meeting meetingSelezionato) throws SQLException{
		return dipDAO.ottieniDipendentiNonInvitatiMeeting(meetingSelezionato);
	}
	
	public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		return skillDAO.ottieniSkillDipendente(cfDipendente);
	}

	public void eliminaInvitato(PartecipazioneMeeting partecipazioneMeeting) throws SQLException{
		meetDAO.eliminaInvitato(partecipazioneMeeting);
	}

	public void inserisciInvitatoMeeting(PartecipazioneMeeting partecipazioneMeeting) throws SQLException{
		meetDAO.inserisciInvitatoMeeting(partecipazioneMeeting);
		
	}

	public void aggiornaPresenzaInvitato(PartecipazioneMeeting partecipazioneMeeting) throws SQLException{
		meetDAO.aggiornaPresenzaInvitato(partecipazioneMeeting);
	}

}
