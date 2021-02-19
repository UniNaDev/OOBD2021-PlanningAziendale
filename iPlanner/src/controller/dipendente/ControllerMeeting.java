//Controller relativo alle finestre Miei Meeting e Gestione Meeting lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento delle finestre.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	
	private final String VIOLAZIONE_SALA_OCCUPATA = "P0001";
	private final String VIOLAZIONE_CAPIENZA_SALA = "P0002";
	private final String VIOLAZIONE_ONNIPRESENZA_DIPENDENTE = "P0003";

	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;
		this.skillDAO=skillDAO;
		this.dipendenteLogged = dipendenteLogged;
		
		mieiMeetingFrame = new MieiMeeting(this);
		mieiMeetingFrame.setVisible(true);
	}

	public void apriGestioneMeeting() {
		gestioneMeetingFrame= new GestioneMeetingDipendente(this);
		gestioneMeetingFrame.setVisible(true);
		mieiMeetingFrame.setVisible(false);
	}
	
	public void apriInserisciPartecipantiMeeting(Meeting meetingSelezionato) {
		ControllerPartecipantiMeeting controller = new ControllerPartecipantiMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, dipendenteLogged, meetingSelezionato);
	}
	
	public ArrayList<Meeting> ottieniMeetingDipendente() throws SQLException {
		return meetDAO.ottieniMeetingDipendente(dipendenteLogged);
	}
	
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.ottieniProgettiDipendente(dipendenteLogged);
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			progetti.add(collaborazione.getProgetto());
		return progetti;
	}
	
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.getSale();
	}
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.ottieniPiattaforme();
	}
	
	public void aggiornaMeeting(Meeting meeting) throws SQLException{
		meetDAO.updateMeeting(meeting);
	}
	
	public void creaMeeting(Meeting nuovoMeeting) throws SQLException{
		meetDAO.insertMeeting(nuovoMeeting);		
	}
	
	public void inserisciOrganizzatore() throws SQLException{
			meetDAO.insertOrganizzatore(dipendenteLogged.getCf());
	}

	public void rimuoviMeeting(int idMeeting) throws SQLException {
		meetDAO.rimuoviMeeting(idMeeting);
	}
	
	public ArrayList<Meeting> filtraMeetingTelematiciDipendenti() throws SQLException{
		return meetDAO.getMeetingDipendenteByModalità("Telematico",dipendenteLogged);
	}
	
	public ArrayList<Meeting> filtraMeetingFisiciDipendenti() throws SQLException {
		return meetDAO.getMeetingDipendenteByModalità("Fisico", dipendenteLogged);
	}
	
	public Progetto ottieniProgettoInserito(Progetto progetto) throws SQLException {
		return projDAO.getProgettoByCod(progetto.getIdProgettto());
	}

	public boolean isOrganizzatore(Meeting meeting) throws SQLException {
		String cfOrganizzatore = ottieniCFOrganizzatore(meeting);
		if (dipendenteLogged.getCf().equals(cfOrganizzatore))
			return true;
		return false;
	}
	
	private String ottieniCFOrganizzatore(Meeting meeting) throws SQLException {
		return meetDAO.getCFOrganizzatore(meeting);
	}

	public ArrayList<Meeting> filtraMeetingDipendentiSala(SalaRiunione sala) throws SQLException {
		
		return meetDAO.getMeetingsDipendenteBySala(sala,dipendenteLogged);
	}

	public ArrayList<Meeting> filtraMeetingDipendentePiattaforma(String piattaforma) throws SQLException {
		
		return meetDAO.getMeetingsDipendenteByPiattaforma(piattaforma,dipendenteLogged);
	}

	public int idUltimoMeetingInserito() throws SQLException {
		return meetDAO.getUltimoIDMeeting();
	}
}
