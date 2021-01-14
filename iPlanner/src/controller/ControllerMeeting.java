package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;


public class ControllerMeeting {

	MieiMeeting meetingFrame;
	GestioneMeeting insertMeetingFrame;

	private ControllerGestioneProfilo controller;
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	private Dipendente loggedUser = null;

	public ControllerMeeting(ControllerGestioneProfilo controller) {
		this.controller = controller;
		
		this.luogoDAO = controller.getLuogoDAO();
		this.dipDAO = controller.getDipDAO();
		this.projDAO = controller.getProjDAO();
		this.meetDAO = controller.getMeetDAO();
		
		this.loggedUser = controller.getLoggedUser();
		
		meetingFrame=new MieiMeeting(this);
		meetingFrame.setVisible(true);
	}

	public void createInsertMeetingFrame() {
		
		insertMeetingFrame= new GestioneMeeting();
		insertMeetingFrame.setVisible(true);	
	}

	public MieiMeeting getMeetingFrame() {
		return meetingFrame;
	}

	public GestioneMeeting getInsertMeetingFrame() {
		return insertMeetingFrame;
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

	public Dipendente getLoggedUser() {
		return loggedUser;
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(loggedUser);
	}
}
