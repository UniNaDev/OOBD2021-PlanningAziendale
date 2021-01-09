package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.LuogoNascita;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;

public class ControllerScelta {

	iPlanner iPlannerFrame;
	NuovoDipendente nuovoDipendenteFrame;
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	private boolean segreteria = false;
	
	//Costruttore che crea il frame iniziale
	public ControllerScelta(boolean segreteria, LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.segreteria = segreteria;
		
		iPlannerFrame=new iPlanner(this, segreteria);
		iPlannerFrame.setVisible(true);
		
	}
	
	//Metodo che indirizza al frame di registrazione
	public void linkToCreationFrame() {
		
	  iPlannerFrame.setVisible(false);
	  nuovoDipendenteFrame= new NuovoDipendente(this);
	  nuovoDipendenteFrame.setVisible(true);
	  
	}
	
	//Metodo che indirizza al Login
	public void linkToLoginFrame() {
		
		iPlannerFrame.setVisible(false);
		ControllerAccesso controller=new ControllerAccesso(segreteria, luogoDAO, dipDAO, projDAO, meetDAO);
		
	}
	
	//Metodo che reindirizza al loginFrame quando viene annullata la creazione dell'account
	public void annulla() {
		
		nuovoDipendenteFrame.setVisible(false);
		iPlannerFrame.setVisible(true);
		
	}

	//Metodo che reindirizza al Frame iniziale(Viene utilizzato dalla finestra di login) 
	public void reLinkToIplannerFrame() {
	
		iPlannerFrame.setVisible(true);
		
	}

	//Metodo che prende le province per il menù
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}
	
	//Metodo che crea un nuovo account per il dipendente
	public void creaAccount(String nome, String cognome, char sesso, LocalDate dataNascita, LuogoNascita luogoNascita, String email, String password, String telefono, String cellulare, String indirizzo) {
		Dipendente temp = new Dipendente(nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,0f,password);
		try {
			if (dipDAO.addDipendente(temp)) {
				ControllerAccesso controller=new ControllerAccesso(segreteria, luogoDAO, dipDAO, projDAO, meetDAO);
				nuovoDipendenteFrame.setVisible(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
