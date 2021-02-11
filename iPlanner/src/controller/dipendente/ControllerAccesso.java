package controller.dipendente;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.ControllerStart;
import entita.Dipendente;
import gui.*;
import gui.dipendente.Login;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerAccesso {
	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private Login loginFrame;	//finestra di login
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	
	//Altri attributi
	private boolean segreteria;	//falso = dipendente, vero = segreteria
	
	private Dipendente dipendente = null;	//utente che ha fatto il login

	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore controller di accesso che mostra la finestra di login
	public ControllerAccesso(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO, boolean segreteria){	
		//ottiene tutti i dao
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		this.segreteria = segreteria;	//ottiene il tipo di autorizzazione
		
		//Inizializza e mostra la finestra di login
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}

	//Metodo che verifica le credenziali per l'accesso e se sono corrette passa alla finestra principale dopo aver salvato l'utente che ha fatto il login
	public void verificaCredenziali(String user, String pass) throws SQLException {
		dipendente = dipDAO.loginCheck(user, pass);	//salva il dipendente che fa il login
		try {
			dipendente.setSkills(skillDAO.getSkillDipendente(dipendente.getCf())); 	//ottiene le skill del dipendente che ha fatto login
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossibile ottenere le skill del dipendente dal database.\nControllare che la connessione al database sia stabilita.",
					"Errore Interrogazione Database",
					JOptionPane.ERROR_MESSAGE);
		}
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerGestioneProfilo controller=new ControllerGestioneProfilo(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO,dipendente);	//inizializza il controller di gestione profilo che mostra la finestra principale del profilo utente
	}

	//Metodo chiamato dal pulsante annulla del login che fa ritornare l'utente alla schermata di scelta iniziale
	public void annulla() {
		loginFrame.setVisible(false);	//chiude la finestra di login
		ControllerStart controller=new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, segreteria);	//inizializza il controller scelta e mostra la finestra iniziale di scelta
	}
	
}
