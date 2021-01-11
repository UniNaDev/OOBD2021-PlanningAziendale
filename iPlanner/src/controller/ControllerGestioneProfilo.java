package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Meeting;
import entita.Progetto;
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
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	
	private Dipendente loggedUser = null;

	public ControllerGestioneProfilo(DipendenteDAO dipDAO,Dipendente loggedUser, ProgettoDAO projDAO, MeetingDAO meetDAO, LuogoNascitaDAO luogoDAO) {
		this.dipDAO=dipDAO;
		this.loggedUser = loggedUser;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.luogoDAO = luogoDAO;
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
		// TODO Auto-generated method stub
		
		System.exit(0);
	}

	public void linkToProjectFrame() {
		
		ControllerProgetto controller= new ControllerProgetto(luogoDAO, dipDAO, projDAO, meetDAO);
	}
	
	public void linkToMeetingFrame() {
		
		ControllerMeeting controller= new ControllerMeeting(luogoDAO, dipDAO, projDAO, meetDAO);
		
	}
	
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		return projDAO.getProgettiByDipendente(loggedUser);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		ArrayList<Meeting> temp = meetDAO.getMeetingsByInvitato(loggedUser);
		return temp;
	}

	//Metodo che prende le province per il menù
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}
	
	public Dipendente getLoggedUser() {
		return loggedUser;
	}

	public void update(String nome, String cognome, char sesso, LocalDate dataNascita, LuogoNascita luogoNascita, String email, String password, String telefono, String cellulare, String indirizzo) throws SQLException {
		
		Dipendente tempDip=null;
		tempDip=new Dipendente(nome, cognome, sesso, dataNascita,luogoNascita, indirizzo, email, telefono, cellulare,loggedUser.getSalario(), password);
		
		dipDAO.updateDipendente(tempDip);
		
	}
}
