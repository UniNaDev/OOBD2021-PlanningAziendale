//Controller che si occupa dell'inizializzazione della finestra principale del programma
//e dei relativi passaggi alle finestre di login o di segreteria

package controller;



import controller.dipendente.ControllerAccesso;
import controller.segreteria.ControllerDipendentiSegreteria;
import controller.segreteria.ControllerMeetingSegreteria;
import controller.segreteria.ControllerProgettiSegreteria;
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
	
	private boolean isSegreteria = false;
	
	public ControllerStart(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO, boolean isSegreteria) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		this.isSegreteria = isSegreteria;
		
		iPlannerFrame=new iPlanner(this, isSegreteria);
		iPlannerFrame.setVisible(true);		
	}
	
	public void apriLogin() {
		iPlannerFrame.setVisible(false);
		ControllerAccesso controller=new ControllerAccesso(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO, isSegreteria);
	}
	
	public void vaiAGestioneDipendenti() {
		iPlannerFrame.setVisible(false);
		ControllerDipendentiSegreteria controller = new ControllerDipendentiSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
	}
	
	public void vaiAGestioneProgetti() {
		iPlannerFrame.setVisible(false);
		ControllerProgettiSegreteria controller = new ControllerProgettiSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
	}
	
	//Metodo che indirizza al controller gestione meeting della segreteria
	public void vaiAGestioneMeeting() {
		iPlannerFrame.setVisible(false);	//chiude la finestra di scelta
		ControllerMeetingSegreteria controller = new ControllerMeetingSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
	}
}
