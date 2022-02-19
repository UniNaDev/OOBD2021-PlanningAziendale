//Controller relativo alle finestre Miei Meeting e Gestione Meeting lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento delle finestre.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import gui.dipendente.GestioneMeetingDipendente;
import gui.dipendente.MieiMeeting;
import interfacceDAO.AmbitoProgettoDAO;
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
	private AmbitoProgettoDAO ambitoDAO = null;	
	
	private Dipendente dipendenteLogged = null;

	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, AmbitoProgettoDAO ambitoDAO, SalaRiunioneDAO salaDAO, Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;
		this.skillDAO = skillDAO;
		this.ambitoDAO = ambitoDAO;
		this.dipendenteLogged = dipendenteLogged;
		
		mieiMeetingFrame = new MieiMeeting(this);
		mieiMeetingFrame.setVisible(true);
	}

	public void apriGUIGestioneMeeting() {
		gestioneMeetingFrame= new GestioneMeetingDipendente(this);
		gestioneMeetingFrame.setVisible(true);
		mieiMeetingFrame.setVisible(false);
	}
	
	public void tornaAHome() {
		mieiMeetingFrame.setVisible(false);
		ControllerGestioneProfilo controller = new ControllerGestioneProfilo(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, dipendenteLogged);
	}
	
	public void apriGUIInserisciPartecipantiMeeting(Meeting meetingSelezionato) {
		ControllerPartecipantiMeeting controller = new ControllerPartecipantiMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, dipendenteLogged, meetingSelezionato);
	}
	
	public ArrayList<Meeting> ottieniMeetingDipendente() throws SQLException {
		ArrayList<Meeting> meeting = meetDAO.ottieniMeetingDipendente(dipendenteLogged);
		meeting.sort(new Comparator<Meeting>() {
			public int compare(Meeting meet1, Meeting meet2) {
				if (meet1.isPassato() && !meet2.isPassato())
					return 1;
				else if (!meet1.isPassato() && meet2.isPassato())
					return -1;
				else if (meet1.isPassato() && meet2.isPassato()) {
					if (meet1.getDataFine().isBefore(meet2.getDataFine()))
						return 1;
					else if (meet1.getDataFine().isAfter(meet2.getDataFine()))
						return -1;
					else {
						if (meet1.getOraFine().isBefore(meet2.getOraFine()))
							return -1;
						else if (meet1.getOraFine().isAfter(meet2.getOraFine()))
							return 1;
						else
							return 0;
					}
				} else if (!meet1.isPassato() && !meet2.isPassato()) {
					if (meet1.getDataInizio().isBefore(meet2.getDataInizio()))
						return -1;
					else if (meet1.getDataInizio().isAfter(meet2.getDataInizio()))
						return 1;
					else {
						if (meet1.getOraInizio().isBefore(meet2.getOraInizio()))
							return -1;
						else if (meet1.getOraInizio().isAfter(meet2.getOraInizio()))
							return 1;
						else
							return 0;
					}
				}
				return 0;
			}
		});
		return meeting;
	}
	
	public ArrayList<Progetto> ottieniProgettiDipendente() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioniDipendente = projDAO.ottieniProgettiDipendente(dipendenteLogged);
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioniDipendente)
			progetti.add(collaborazione.getProgetto());
		return progetti;
	}
	
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.ottieniSale();
	}
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.ottieniPiattaforme();
	}
	
	public void aggiornaMeeting(Meeting meeting) throws SQLException{
		meetDAO.aggiornaMeeting(meeting);
	}
	
	public void creaMeeting(Meeting nuovoMeeting) throws SQLException{
		meetDAO.creaMeeting(nuovoMeeting);		
	}
	
	public void inserisciOrganizzatore() throws SQLException{
		meetDAO.inserisciOrganizzatore(dipendenteLogged.getCf());
	}

	public void rimuoviMeeting(int idMeeting) throws SQLException {
		meetDAO.rimuoviMeeting(idMeeting);
	}
	
	public ArrayList<Meeting> filtraMeetingTelematiciDipendenti() throws SQLException{
		return meetDAO.filtraMeetingDipendentePerModalità("Telematico",dipendenteLogged);
	}
	
	public ArrayList<Meeting> filtraMeetingFisiciDipendenti() throws SQLException {
		return meetDAO.filtraMeetingDipendentePerModalità("Fisico", dipendenteLogged);
	}
	
	public Progetto ottieniProgettoInserito(Progetto progetto) throws SQLException {
		return projDAO.ottieniProgettoDaCodiceProgetto(progetto.getIdProgettto());
	}

	public boolean isOrganizzatore(Meeting meeting) throws SQLException {
		String cfOrganizzatore = ottieniCFOrganizzatore(meeting);
		if (dipendenteLogged.getCf().equals(cfOrganizzatore))
			return true;
		return false;
	}
	
	private String ottieniCFOrganizzatore(Meeting meeting) throws SQLException {
		return meetDAO.ottieniCFOrganizzatore(meeting);
	}

	public ArrayList<Meeting> filtraMeetingDipendentiSala(SalaRiunione sala) throws SQLException {
		return meetDAO.filtraMeetingDipendentiSala(sala,dipendenteLogged);
	}

	public ArrayList<Meeting> filtraMeetingDipendentePiattaforma(String piattaforma) throws SQLException {
		return meetDAO.filtraMeetingDipendentePiattaforma(piattaforma,dipendenteLogged);
	}

	public int ottieniIdUltimoMeetingInserito() throws SQLException {
		return meetDAO.ottieniIdUltimoMeetingInserito();
	}
}
