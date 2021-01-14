package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import entita.Dipendente;
import entita.Progetto;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;


public class ControllerProgetto {

	Project projectFrame;
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
		
		projectFrame=new Project(this);
		projectFrame.setVisible(true);
	}


	public void createInsertProjectFrame() {
		newProjectFrame=new GestioneProgetto(this);
		newProjectFrame.setVisible(true);
	}

	public ArrayList<Progetto> getElementi() throws SQLException {
		
//		for(Progetto i:projDAO.getProgetti())
//			System.out.println(i);
		
		
		return projDAO.getProgetti();
	}
}
