package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Progetto;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;


public class ControllerProgetto {

	MieiProgetti projectFrame;
	GestioneProgetto newProjectFrame;
	
	private ControllerGestioneProfilo controller;

	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	private Dipendente loggedUser = null;
	
	public ControllerProgetto(ControllerGestioneProfilo controller) {
		this.controller = controller;
		
		this.luogoDAO = controller.getLuogoDAO();
		this.dipDAO = controller.getDipDAO();
		this.projDAO = controller.getProjDAO();
		this.meetDAO = controller.getMeetDAO();
		
		this.loggedUser = controller.getLoggedUser();
		
		projectFrame=new MieiProgetti(this);
		projectFrame.setVisible(true);
	}


	public void createInsertProjectFrame() {
		newProjectFrame=new GestioneProgetto(this);
		newProjectFrame.setVisible(true);
	}

	
	//Ottiene i progetti del dipendente
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.getProgettiByDipendente(loggedUser);
		
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			temp.add(collaborazione.getProgetto());
		
		return temp;
	}


	public MieiProgetti getProjectFrame() {
		return projectFrame;
	}


	public GestioneProgetto getNewProjectFrame() {
		return newProjectFrame;
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
	
	
}
