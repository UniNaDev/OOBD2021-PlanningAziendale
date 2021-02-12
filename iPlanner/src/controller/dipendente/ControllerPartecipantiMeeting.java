//Controller relativo alla finestra Gestione Partecipanti Meeting lato dipendente.
//Contiene tutti i metodi necessari per farla funzionare correttamente.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Skill;
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
		
		inserisciPartecipantiMeetingFrame= new InserisciPartecipantiMeeting(this,meetingSelezionato);
		inserisciPartecipantiMeetingFrame.setVisible(true);

	}

	public ArrayList<Dipendente> ottieniInvitati(int idMeeting) throws SQLException {
		return meetDAO.getInvitati(idMeeting);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(dipendenteLogged);
	}
	
	public ArrayList<Dipendente> ottieniDipendenti(Meeting meetingSelezionato) throws SQLException {
		return dipDAO.getDipendentiNonInvitati(meetingSelezionato);
	}
	
	public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		return skillDAO.getSkillsDipendente(cfDipendente);
	}

	public void eliminaPartecipante(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		meetDAO.deletePartecipanteMeeting(partecipazioneMeeting);
	}

	public void inserisciPartecipante(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		meetDAO.insertPartecipanteMeeting(partecipazioneMeeting);
		
	}

	public void aggiornaPresenzaPartecipante(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		meetDAO.updatePresenzaPartecipante(partecipazioneMeeting);
		
	}

}
