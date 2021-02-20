//Controller relativo alla finestra Gestione Partecipanti Meeting lato dipendente.
//Contiene tutti i metodi necessari per farla funzionare correttamente.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	
	public ArrayList<Dipendente> ottieniDipendentiNonInvitatiMeeting(Meeting meetingSelezionato) throws SQLException{
		return dipDAO.ottieniDipendentiNonInvitatiMeeting(meetingSelezionato);
	}
	
	public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		return skillDAO.ottieniSkillDipendente(cfDipendente);
	}

	public boolean eliminaInvitato(PartecipazioneMeeting partecipazioneMeeting){
		try {
			meetDAO.eliminaInvitato(partecipazioneMeeting);
			return true;
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}

	public boolean inserisciInvitatoMeeting(PartecipazioneMeeting partecipazioneMeeting){
		try {
			meetDAO.inserisciInvitatoMeeting(partecipazioneMeeting);
			return true;
		} catch (SQLException e1) {
			
			if(e1.getSQLState().equals("P0002"))
				JOptionPane.showMessageDialog(null, "Il numero di invitati al meeting supera la capienza massima consentita.\nSi consiglia di cambiare sala.");		
			if(e1.getSQLState().equals("P0003"))
				JOptionPane.showMessageDialog(null, "Il dipendente che si sta tentando di inserire partecipa ad un meeting che si accavalla con il corrente");
			
			return false;
		}
		
	}

	public boolean aggiornaPresenzaInvitato(PartecipazioneMeeting partecipazioneMeeting){
		try {
			meetDAO.aggiornaPresenzaInvitato(partecipazioneMeeting);
			return true;
		} catch (SQLException e1) {
			if(e1.getSQLState().equals("70000"))
				JOptionPane.showMessageDialog(null, "Il numero di invitati al meeting supera la capienza massima consentita.\nSi consiglia di cambiare sala.");		
			
			if(e1.getSQLState().equals("P0001"))
				JOptionPane.showMessageDialog(null, "Il dipendente che si sta tentando di inserire partecipa ad un meeting che si accavalla con il corrente");
			
			return false;
		}
		
	}

}
