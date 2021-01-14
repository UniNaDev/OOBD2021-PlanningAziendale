package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Meeting;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.LuogoNascita;
import gui.User;
import gui.UserProfile;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;

public class ControllerGestioneProfilo {

	User userFrame;
	UserProfile userProfileFrame;
	
	private ControllerAccesso controller;
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	
	private Dipendente loggedUser = null;

	public ControllerGestioneProfilo(ControllerAccesso controller) {
		this.controller = controller;
		
		this.luogoDAO = controller.getLuogoDAO();
		this.dipDAO = controller.getDipDAO();
		this.projDAO = controller.getProjDAO();
		this.meetDAO = controller.getMeetDAO();
		
		this.loggedUser = controller.getLoggedUser();
		
		userFrame=new User(this);
		userFrame.setVisible(true);
		
	}
	
	public void viewAccount() {
		// TODO Auto-generated method stub
		userProfileFrame=new UserProfile(this);
		userProfileFrame.setVisible(true);
	}
	
	public void closeWindow() {
		
		userProfileFrame.setVisible(false);
		
	}

	public void logout() {
		System.exit(0);
	}

	public void linkToProjectFrame() {
		
		ControllerProgetto controller= new ControllerProgetto(this);
	}
	
	public void linkToMeetingFrame() {
		
		ControllerMeeting controller= new ControllerMeeting(this);
		
	}
	public void reLinkToUserFrame() {
		
		userFrame.setVisible(false);
		
		userFrame=new User(this);
		userFrame.setVisible(true);
	}
	
	public ArrayList<CollaborazioneProgetto> ottieniProgetti() throws SQLException {
		return controller.getProjDAO().getProgettiByDipendente(loggedUser);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		ArrayList<Meeting> temp = controller.getMeetDAO().getMeetingsByInvitato(loggedUser);
		return temp;
	}
	
	public Dipendente getLoggedUser() {
		return loggedUser;
	}

	public void update(String nome, String cognome, char sesso, LocalDate dataNascita, LuogoNascita luogoNascita, String email, String password, String telefono, String cellulare, String indirizzo) throws SQLException {
		
		
		loggedUser.setNome(nome);
		loggedUser.setCognome(cognome);
		loggedUser.setSesso(sesso);
		loggedUser.setDataNascita(dataNascita);
		loggedUser.setLuogoNascita(luogoNascita);
		
		if(!loggedUser.getEmail().equals(email))
		loggedUser.setEmail(email);
		
		loggedUser.setPassword(password);
		loggedUser.setTelefonoCasa(telefono);
		loggedUser.setCellulare(cellulare);
		loggedUser.setIndirizzo(indirizzo);
		
	
		controller.getDipDAO().updateDipendente(loggedUser);
		
	}

	public User getUserFrame() {
		return userFrame;
	}

	public UserProfile getUserProfileFrame() {
		return userProfileFrame;
	}
	
	//Metodo che restituisce tutte le province del database
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia per il menù di creazione account
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}

	public LuogoNascitaDAO getLuogoDAO() {
		return luogoDAO;
	}

	public DipendenteDAO getDipDAO() {
		return dipDAO;
	}

	public ProgettoDAO getProjDAO() {
		return projDAO;
	}

	public MeetingDAO getMeetDAO() {
		return meetDAO;
	}
	
}
