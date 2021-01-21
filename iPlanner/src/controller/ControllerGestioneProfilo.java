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
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerGestioneProfilo {

	//Attributi GUI
	private User userFrame;
	private UserProfile userProfileFrame;
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	
	//Altri attributi
	private Dipendente loggedUser = null;

	//Costruttore
	public ControllerGestioneProfilo(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente loggedUser) {
		
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO; 
		
		this.loggedUser = loggedUser;
		
		userFrame=new User(this);
		userFrame.setVisible(true);
		
	}
	
	public void viewAccount() {
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
		
		ControllerProgetto controller= new ControllerProgetto(luogoDAO,dipDAO,projDAO,meetDAO,loggedUser);
	}
	
	public void linkToMeetingFrame() {
		
		ControllerMeeting controller= new ControllerMeeting(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,loggedUser);
		
	}
	public void reLinkToUserFrame() {
		
		userFrame.setVisible(false);
		
		userFrame=new User(this);
		userFrame.setVisible(true);
	}
	
	public ArrayList<CollaborazioneProgetto> ottieniProgetti() throws SQLException {
		return projDAO.getProgettiByDipendente(loggedUser);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		ArrayList<Meeting> temp = meetDAO.getMeetingsByInvitato(loggedUser);
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
		
	
		dipDAO.updateDipendente(loggedUser);
		
	}
	
	//Metodo che restituisce tutte le province del database
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia per il menù di creazione account
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}	
}
