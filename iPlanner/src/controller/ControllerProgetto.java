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

	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	public ControllerProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		projectFrame=new Project(this);
		projectFrame.setVisible(true);
	}


	public void createInsertProjectFrame() {
		// TODO Auto-generated method stub
		newProjectFrame=new GestioneProgetto(this);
		newProjectFrame.setVisible(true);
	}

	public ArrayList<Progetto> getElementi() throws SQLException {
		
//		for(Progetto i:projDAO.getProgetti())
//			System.out.println(i);
		
		
		return projDAO.getProgetti();
	
	}
	

}
