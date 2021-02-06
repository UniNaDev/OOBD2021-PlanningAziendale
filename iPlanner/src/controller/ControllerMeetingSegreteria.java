package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.SalaRiunione;
import gui.GestioneMeetingSegreteria;
import gui.GestioneSale;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerMeetingSegreteria {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private GestioneMeetingSegreteria gestioneMeetingSegreteria;
	private GestioneSale gestioneSale;
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	
	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore
	public ControllerMeetingSegreteria (LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO) {
		//Ottiene le implementazioni dei DAO inizializzate nel main Starter
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		gestioneMeetingSegreteria = new GestioneMeetingSegreteria(this);
		gestioneMeetingSegreteria.setVisible(true);
	}
	
	//Metodi gestione GUI
	//-----------------------------------------------------------------
		
	//Metodo che reindirizza al frame di scelta iniziale quando viene annullata la creazione dell'account
	public void tornaAiPlanner() {
		gestioneMeetingSegreteria.setVisible(false);	//chiude la finestra di creazione account
		ControllerScelta controller = new ControllerScelta(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, true);	//torna ad iPlanner in modalità segreteria
	}
	
	//Metodo che passa alla gestione delle sale
	public void apriGestioneSale() {
		gestioneMeetingSegreteria.setEnabled(false);
		gestioneSale = new GestioneSale(this);
		gestioneSale.setVisible(true);
	}
	
	//Metodo che esce dalla gestione sale
	public void chiudiGestioneSale() {
		gestioneSale.setVisible(false);
		gestioneMeetingSegreteria.setEnabled(true);
		gestioneMeetingSegreteria.setAlwaysOnTop(true);
	}
		
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che ottiene tutti i meeting
	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		return meetDAO.getMeetings();
	}
	
	//Metodo che ottiene tutte le partecipazioni ai meeting
	public ArrayList<PartecipazioneMeeting> ottieniPartecipanti(Meeting meeting) throws SQLException{
		return meetDAO.getInvitatiPartecipazioneMeeting(meeting.getIdMeeting());
	}
	
	//Metodo che ottiene tutte le sale
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.getSale();
	}
	
	//Metodo che ottiene tutte le piattaforme
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.getPiattaforme();
	}
	
	//Metodo che ottiene i meeting filtrati per modalità
	public ArrayList<Meeting> filtraMeetingTelematici() throws SQLException{
		return meetDAO.getMeetingsByModalità("Telematico");
	}
	
	//Metodo che ottiene i meeting filtrati per modalità
	public ArrayList<Meeting> filtraMeetingFisici() throws SQLException{
		return meetDAO.getMeetingsByModalità("Fisico");
	}
	
	//Metodo che ottiene i meeting filtrati per piattaforma
	public ArrayList<Meeting> filtraMeetingPiattaforma(String piattaforma) throws SQLException{
		return meetDAO.getMeetingsByPiattaforma(piattaforma);
	}
	
	//Metodo che aggiunge una nuova sala al DB
	public void creaSala(SalaRiunione sala) throws SQLException {
		salaDAO.addSala(sala);
	}
}
