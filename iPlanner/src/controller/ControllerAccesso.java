package controller;

import java.sql.Connection;
import java.sql.SQLException;

import entita.Dipendente;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;

public class ControllerAccesso {
	
	Login loginFrame;
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	private boolean segreteria = false;
	
	private Dipendente loggedUser = null;

	public ControllerAccesso(boolean segreteria, LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO)
	{
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.segreteria = segreteria;
		
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}

	public void verificaCredenziali(String user, String pass) throws SQLException {
		// TODO Auto-generated method stub
		
		loggedUser = dipDAO.loginCheck(user, pass);
		//TODO Se le credenziali sono corrette esegui l'accesso altrimenti JDialog utente non presente o dati incorretti
		loginFrame.setVisible(false);
		ControllerGestioneProfilo controller=new ControllerGestioneProfilo(loggedUser, projDAO, meetDAO, luogoDAO);
		
		loginFrame.SvuotaCampi();
	}

	public void annulla() {
		// TODO Auto-generated method stub
		loginFrame.setVisible(false);
		ControllerScelta controller=new ControllerScelta(segreteria, luogoDAO, dipDAO, projDAO, meetDAO);
		controller.reLinkToIplannerFrame();
	}





}
