package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;
import gui.*;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerScelta {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private iPlanner iPlannerFrame;	//frame della finestra di scelta iniziale
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	
	//Altri attributi
	private boolean segreteria = false;	//autorizzazione (true = segreteria, false = dipendente)
	
	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore del controllee di scelta che mostra la prima finestra di scelta
	public ControllerScelta(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO, boolean segreteria) {
		//Ottiene le implementazioni dei DAO inizializzate nel main Starter
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		this.segreteria = segreteria;	//ottiene l'autorizzazione presa nel main dagli argomenti a linea di comando
		
		iPlannerFrame=new iPlanner(this, segreteria);	//inizializza la prima finestra di scelta
		iPlannerFrame.setVisible(true);	//mostra la finestra inizializzata
		
	}
	
	//Metodi gestione GUI
	//-----------------------------------------------------------------
	
	//Metodo che indirizza al Login
	public void apriLogin() {
		iPlannerFrame.setVisible(false);	//chiude la finestra di scelta
		ControllerAccesso controller=new ControllerAccesso(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO, segreteria);	//inizializza il controller di accesso che si occupa del login e che mostrerà la finestra di login
	}
	
	//Metodo che indirizza al controller gestione dipendenti della segreteria
	public void vaiAGestioneDipendenti() {
		iPlannerFrame.setVisible(false);	//chiude la finestra di scelta
		ControllerDipendentiSegreteria controller = new ControllerDipendentiSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);	//inizializza il controller della segreteria
	}
	
	//Metodo che indirizza al controller gestione progetti della segreteria
	public void vaiAGestioneProgetti() {
		iPlannerFrame.setVisible(false);	//chiude la finestra di scelta
		ControllerProgettiSegreteria controller = new ControllerProgettiSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);	//inizializza il controller
	}
	
	//Metodo che indirizza al controller gestione meeting della segreteria
	public void vaiAGestioneMeeting() {
		iPlannerFrame.setVisible(false);	//chiude la finestra di scelta
		ControllerMeetingSegreteria controller = new ControllerMeetingSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);	//inizializza il controller
	}
}
