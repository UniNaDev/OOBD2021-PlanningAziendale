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
	private ControllerScelta controller;
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	
	private Dipendente loggedUser = null;	//utente che ha fatto il login

	//Costruttore controller di accesso che mostra la finestra di login
	public ControllerAccesso(ControllerScelta controller)
	{
		this.controller = controller;
		
		this.luogoDAO = controller.getLuogoDAO();
		this.dipDAO = controller.getDipDAO();
		this.projDAO = controller.getProjDAO();
		this.meetDAO = controller.getMeetDAO();
		this.skillDAO = controller.getSkillDAO();
		
		//Inizializza e mostra la finestra di login
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}

	//Metodo che verifica le credenziali per l'accesso e se sono corrette passa alla finestra principale dopo aver salvato l'utente che ha fatto il login
	public void verificaCredenziali(String user, String pass) throws SQLException {
		
		loggedUser = controller.getDipDAO().loginCheck(user, pass);	//salva il dipendente che fa il login
		loggedUser.setSkills(controller.getSkillDAO().getSkillDipendente(loggedUser)); 	//ottiene le skill del dipendente che ha fatto login
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerGestioneProfilo controller=new ControllerGestioneProfilo(this);	//inizializza il controller di gestione profilo che mostra la finestra principale del profilo utente
	}

	//Metodo chiamato dal pulsante annulla del login che fa ritornare l'utente alla schermata di scelta iniziale
	public void annulla() {
		loginFrame.setVisible(false);	//chiude la finestra di login
//		ControllerScelta controller=new ControllerScelta(this.controller.isSegreteria(), this.controller.getLuogoDAO(), this.controller.getDipDAO(), this.controller.getProjDAO(), this.controller.getMeetDAO(), this.controller.getSkillDAO());	//inizializza il controller scelta e mostra la finestra iniziale di scelta
		this.controller.getiPlannerFrame().setVisible(true);
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

	public SkillDAO getSkillDAO() {
		return skillDAO;
	}

	public Login getLoginFrame() {
		return loginFrame;
	}
	
	public Dipendente getLoggedUser() {
		return loggedUser;
	}
}
