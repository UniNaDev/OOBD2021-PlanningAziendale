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

	//Attributi GUI
	private MieiProgetti projectFrame;
	private GestioneProgetto newProjectFrame;

	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	//Altri attributi
	private Dipendente loggedUser = null;
	
	public ControllerProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, Dipendente loggedUser) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		
		this.loggedUser = loggedUser;
		
		projectFrame=new MieiProgetti(this, this.loggedUser);
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
}
