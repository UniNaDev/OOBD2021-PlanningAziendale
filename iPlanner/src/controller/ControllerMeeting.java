package controller;

import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;


public class ControllerMeeting {

	MieiMeeting meetingFrame;
	GestioneMeeting insertMeetingFrame;

	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;

	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		meetingFrame=new MieiMeeting(this);
		meetingFrame.setVisible(true);
	}

	public void createInsertMeetingFrame() {
		
		insertMeetingFrame= new GestioneMeeting();
		insertMeetingFrame.setVisible(true);
		
		
	}
	

}
