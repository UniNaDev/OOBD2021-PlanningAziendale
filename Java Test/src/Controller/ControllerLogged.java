package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import Entita.Dipendente;
import Entita.Meeting;
import Entita.Progetto;
import InterfacceDAO.MeetingDAO;
import InterfacceDAO.ProgettoDAO;
import Viste.MainWindow;

public class ControllerLogged {

	//Attributi GUI
	MainWindow mainWindow = null;
	
	//Altri attributi
	private Connection connection;
	private Dipendente loggedUser;
	
	private ProgettoDAO progettoDAO;
	private MeetingDAO meetingDAO;

	//METODI
	//Costruttore
	public ControllerLogged(Connection connection, Dipendente loggedUser, ProgettoDAO projDAO, MeetingDAO meetDAO) {
		this.connection = connection;	//prende la connessione
		this.loggedUser = loggedUser;	//prende l'utente che ha fatto accesso
		this.progettoDAO = projDAO;
		this.meetingDAO = meetDAO;
		
		mainWindow = new MainWindow(this);	//inizializza la main window
		mainWindow.setTitle(loggedUser.getNome() + " " + loggedUser.getCognome()); 	//setta il titolo della finestra con nome e cognome del dipendente
		mainWindow.setVisible(true);	//visualizza la finestra
	}

	public Dipendente getLoggedUser() {
		return loggedUser;
	}
	
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		return progettoDAO.getProgettiByDipendente(loggedUser);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		return meetingDAO.getMeetingsByInvitato(loggedUser);
	}
	
}
