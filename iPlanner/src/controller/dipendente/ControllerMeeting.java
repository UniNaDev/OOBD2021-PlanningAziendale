//Controller relativo alle finestre Miei Meeting e Gestione Meeting lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento delle finestre.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;


import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import gui.dipendente.GestioneMeetingDipendente;
import gui.dipendente.MieiMeeting;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;


public class ControllerMeeting {
	private MieiMeeting mieiMeetingFrame;
	private GestioneMeetingDipendente gestioneMeetingFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	
	private Dipendente dipendenteLogged = null;

	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;
		this.skillDAO=skillDAO;
		this.dipendenteLogged = dipendenteLogged;
		
		mieiMeetingFrame=new MieiMeeting(this);
		mieiMeetingFrame.setVisible(true);
	}

	public void apriGestioneMeeting() {
		gestioneMeetingFrame= new GestioneMeetingDipendente(this,dipendenteLogged);
		gestioneMeetingFrame.setVisible(true);
		
		mieiMeetingFrame.setVisible(false);
	}
	
	public void apriInserisciPartecipantiMeeting(Meeting meetingSelezionato) {
		ControllerPartecipantiMeeting controller=new ControllerPartecipantiMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, dipendenteLogged,meetingSelezionato);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(dipendenteLogged);
	}
	
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.getProgettiByDipendente(dipendenteLogged);
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			progetti.add(collaborazione.getProgetto());
		return progetti;
	}
	
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.getSale();
	}
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.getPiattaforme();
	}
	
	public void aggiornaMeeting(Meeting meeting, Progetto progettoSelezionato) throws SQLException {
		meetDAO.updateMeeting(meeting,progettoSelezionato);
	}

	public void creaMeeting(Meeting meetingInserito,String nomeProgettoDisusso) throws SQLException{
			meetDAO.insertMeeting(meetingInserito,nomeProgettoDisusso);
			meetDAO.insertOrganizzatore(dipendenteLogged.getCf()); 
	}

	public void rimuoviMeeting(int idMeeting) throws SQLException {
		meetDAO.deleteMeeting(idMeeting);
	}

	//TODO: ???
	public void inserisciMeetingCompleto(Meeting meetingInserito, Progetto progetto) throws SQLException {
		meetDAO.insertMeetingCompleto(meetingInserito, progetto);
		meetDAO.insertOrganizzatore(dipendenteLogged.getCf()); 
	}

	public Progetto ottieniProgettoInserito(Progetto progetto) throws SQLException {
		return projDAO.getProgettoByCod(progetto.getIdProgettto());
	}

	public String organizzatoreCheck(Meeting meeting) throws SQLException {
		return dipDAO.organizzatoreCheck(meeting);
	}
}
