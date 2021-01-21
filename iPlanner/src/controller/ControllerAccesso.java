package controller;

import java.sql.SQLException;

import entita.Dipendente;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerAccesso {
	
	//Attributi GUI
	private Login loginFrame;
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	
	//Altri attributi
	private boolean segreteria;	//falso = dipendente, vero = segreteria
	private Dipendente loggedUser = null;	//utente che ha fatto il login

	//Costruttore controller di accesso che mostra la finestra di login
	public ControllerAccesso(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, boolean segreteria)
	{	
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		
		this.segreteria = segreteria;
		
		//Inizializza e mostra la finestra di login
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}

	//Metodo che verifica le credenziali per l'accesso e se sono corrette passa alla finestra principale dopo aver salvato l'utente che ha fatto il login
	public void verificaCredenziali(String user, String pass) throws SQLException {
		
		loggedUser = dipDAO.loginCheck(user, pass);	//salva il dipendente che fa il login
		loggedUser.setSkills(skillDAO.getSkillDipendente(loggedUser)); 	//ottiene le skill del dipendente che ha fatto login
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerGestioneProfilo controller=new ControllerGestioneProfilo(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,loggedUser);	//inizializza il controller di gestione profilo che mostra la finestra principale del profilo utente
	}

	//Metodo chiamato dal pulsante annulla del login che fa ritornare l'utente alla schermata di scelta iniziale
	public void annulla() {
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerScelta controller=new ControllerScelta(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, segreteria);	//inizializza il controller scelta e mostra la finestra iniziale di scelta
	}
	
}
