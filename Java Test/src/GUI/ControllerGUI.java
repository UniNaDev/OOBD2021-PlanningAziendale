//Controller principale dell'interfaccia grafica
package GUI;

import java.sql.Connection;
import java.sql.SQLException;

import DBManager.ManagerConnessioneDB;
import Entità.Dipendente;
import ImplementazioneDAO.DipendenteDAOPSQL;
import InterfacceDAO.DipendenteDAO;

public class ControllerGUI {

	//ATTRIBUTI
	//attributi GUI
	private StartWindow startWindow;	//finestra iniziale
	private LoginWindow loginWindow;	//finestra per effettuare il login
	
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//METODI
	
	//Costruttore controller GUI
	public ControllerGUI(Connection connection) {
		this.startWindow = new StartWindow(this);	//inizializza la finestra iniziale
		this.connection = connection;	//ottiene la connessione al DB
		startWindow.setVisible(true);	//visualizza la finestra iniziale
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
		System.out.println("Logged in");
		
		//TODO: cambia finestra e pulisci credenziali nei field
	}
	
}
