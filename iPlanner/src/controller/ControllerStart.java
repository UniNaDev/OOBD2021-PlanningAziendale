//Controller che si occupa dell'inizializzazione della finestra principale del programma
//e dei relativi passaggi alle finestre di login o di segreteria

package controller;

import gui.*;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerStart {
	private iPlanner iPlannerFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	private AmbitoProgettoDAO ambitoDAO = null;
	
	public ControllerStart(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
				
		iPlannerFrame = new iPlanner(this);
		iPlannerFrame.setVisible(true);		
	}
	
	public void apriGUILoginDipendente() {
		iPlannerFrame.setVisible(false);
		ControllerAccesso controller = new ControllerAccesso(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
		controller.apriGUILoginDipendente();
	}
	
	public void apriAutenticazioneSegreteria() {
		iPlannerFrame.setVisible(false);
		ControllerAccesso controller = new ControllerAccesso(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
		controller.apriAutenticazioneSegreteria();
	}
}
