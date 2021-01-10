package controller;

import java.sql.SQLException;

import entita.Dipendente;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SkillDAO;

public class ControllerAccesso {
	
	//Attributi GUI
	private Login loginFrame;
	
	//Altri attributi
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetti
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao skill
	
	private boolean segreteria = false;	//autorizzazione (true = segreteria, false = dipendente)
	
	private Dipendente loggedUser = null;	//utente che ha fatto il login

	//Costruttore controller di accesso che mostra la finestra di login
	public ControllerAccesso(boolean segreteria, LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO)
	{
		//Ottiene i dao necessari
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		
		this.segreteria = segreteria;	//ottiene l'autorizzazione
		
		//Inizializza e mostra la finestra di login
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}

	//Metodo che verifica le credenziali per l'accesso e se sono corrette passa alla finestra principale dopo aver salvato l'utente che ha fatto il login
	public void verificaCredenziali(String user, String pass) throws SQLException {
		
		loggedUser = dipDAO.loginCheck(user, pass);	//salva il dipendente che fa il login
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerGestioneProfilo controller=new ControllerGestioneProfilo(loggedUser, projDAO, meetDAO, luogoDAO);	//inizializza il controller di gestione profilo che mostra la finestra principale del profilo utente
	}

	//Metodo chiamato dal pulsante annulla del login che fa ritornare l'utente alla schermata di scelta iniziale
	public void annulla() {
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerScelta controller=new ControllerScelta(segreteria, luogoDAO, dipDAO, projDAO, meetDAO, skillDAO);	//inizializza il controller scelta e mostra la finestra iniziale di scelta
	}





}
