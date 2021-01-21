package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.SalaRiunione;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;


public class ControllerMeeting {

	//Attributi GUI
	private MieiMeeting meetingFrame;
	private GestioneMeeting insertMeetingFrame;
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao dei progetti
	private MeetingDAO meetDAO = null;	//dao dei meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao sale riunione
	
	//Altri attributi
	private Dipendente loggedUser = null;

	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente loggedUser) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;

		this.loggedUser = loggedUser;
		
		meetingFrame=new MieiMeeting(this);
		meetingFrame.setVisible(true);
	}

	public void createInsertMeetingFrame() {
		
		insertMeetingFrame= new GestioneMeeting(this);
		insertMeetingFrame.setVisible(true);	
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(loggedUser);
	}
	
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.getSale();
	}
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.getPiattaforme();
	}
	
	public void aggiornaMeeting(Meeting meeting) throws SQLException {
		meetDAO.updateMeeting(meeting);
	}
}
