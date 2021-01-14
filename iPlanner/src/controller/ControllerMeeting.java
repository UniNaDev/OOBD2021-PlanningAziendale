package controller;

import entita.Dipendente;
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
	

}
