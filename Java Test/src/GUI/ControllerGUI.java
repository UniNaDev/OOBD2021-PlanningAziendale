//Controller principale dell'interfaccia grafica
package GUI;

import java.awt.Window;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManager.ManagerConnessioneDB;
import Entità.Dipendente;
import ImplementazioneDAO.DipendenteDAOPSQL;
import InterfacceDAO.DipendenteDAO;

public class ControllerGUI {

	//ATTRIBUTI
	//attributi GUI
	private StartWindow startWindow;	//finestra iniziale
	private LoginWindow loginWindow;	//finestra per effettuare il login
	private ErrorWindow errorWindow;	//finestra per messaggi di errore
	
	//altri attributi
	private Connection connection = null;	//connessione al DB
	
	private Dipendente loggedUser = null;	//utente che fa il login
	
	
	//MAIN
	public static void main(String[] args) {
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		try {
			connDB = ManagerConnessioneDB.getInstance();
			
			connection = connDB.getConnection();	//crea connessione al DB
			
			ControllerGUI controller = new ControllerGUI(connection);	//inizializza controller
			
		} 
		catch (SQLException e) {
			ControllerGUI controller = new ControllerGUI();	//inizializza controller senza connessione
			controller.mostraErrore("Connessione fallita.");	//mostra finestra di errore "connessione fallita"
		}
	}

	//METODI
	
	//Costruttore controller GUI
	public ControllerGUI(Connection connection) {
		this.startWindow = new StartWindow(this);	//inizializza la finestra iniziale
		this.connection = connection;	//ottiene la connessione al DB
		startWindow.setVisible(true);	//visualizza la finestra iniziale
	}
	
	//Costruttore senza connessione per messaggio di errore di connessione
	public ControllerGUI() {
		this.startWindow = new StartWindow(this);
	}
	
	//Metodo che passa da start window a login window
	public void startTOlogin() {
		loginWindow = new LoginWindow(this);	//inizializza la finestra per il login
		loginWindow.setVisible(true);	//visualizza la finestra di login
		startWindow.setVisible(false);	//nasconde la finestra iniziale
	}
	
	//Metodo per il login
	public void login(String email, String password) throws SQLException {
		DipendenteDAO dipDAO = new DipendenteDAOPSQL(this.connection);
		loggedUser = dipDAO.loginCheck(email, password);
		
		//TODO: cambia finestra e pulisci credenziali nei field
	}
	
	//Metodo per aprire finestra di errore
	public void mostraErrore(String errore) {
		errorWindow = new ErrorWindow(errore);	//inizializza la finestra di errore
		errorWindow.setVisible(true);	//mostra la finestra di errore
		errorWindow.setAlwaysOnTop(true); //pone la finestra di errore sempre sopra
		//rende le finestre visibili sottostanti non interagibili
		for (Window w: Window.getWindows())
			if (w.isShowing() && !w.equals(errorWindow))
				w.setEnabled(false);
	}
	
}
