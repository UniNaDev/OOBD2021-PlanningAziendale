//Controller principale dell'interfaccia grafica
package Controller;

import java.awt.Window;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManager.ManagerConnessioneDB;
import Entita.Dipendente;
import ImplementazioneDAO.DipendenteDAOPSQL;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;
import Viste.CreaAccountWindow;
import Viste.ErrorWindow;
import Viste.LoginWindow;
import Viste.StartWindow;

public class ControllerStart {

	//ATTRIBUTI
	//attributi GUI
	private StartWindow startWindow;	//finestra iniziale
	private LoginWindow loginWindow;	//finestra per effettuare il login
	private CreaAccountWindow creaAccountWindow;	//finestra di creazione dell'account
	
	//altri attributi
	private Connection connection = null;	//connessione al DB
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	
	private Dipendente loggedUser = null;	//utente che fa il login

	//METODI
	
	//Costruttore controller GUI
	public ControllerStart(Connection connection) throws SQLException {
		this.startWindow = new StartWindow(this);	//inizializza la finestra iniziale
		this.connection = connection;	//ottiene la connessione al DB
		this.luogoDAO = new LuogoNascitaDAOPSQL(connection);
		startWindow.setVisible(true);	//visualizza la finestra iniziale
	}
	
	//Metodo che passa da start window a login window
	public void startTOlogin() {
		loginWindow = new LoginWindow(this);	//inizializza la finestra per il login
		loginWindow.setVisible(true);	//visualizza la finestra di login
		startWindow.setVisible(false);	//nasconde la finestra iniziale
	}
	
	//Metodo che passa dalla finestra start a quella di creazione dell'account
	public void startTOcreaAccount() throws SQLException {
		creaAccountWindow = new CreaAccountWindow(this);
		creaAccountWindow.setVisible(true);
		startWindow.setVisible(false);
	}
	
	//Metodo per il login
	public void login(String email, String password) throws SQLException {
		DipendenteDAO dipDAO = new DipendenteDAOPSQL(this.connection);
		loggedUser = dipDAO.loginCheck(email, password);
		
		//TODO: cambia finestra e pulisci credenziali nei field
	}
	
	//Metodo che crea un nuovo account per il dipendente
	public void creaAccount() {
		
	}
	
	public LuogoNascitaDAO getLuogoDAO() {
		return luogoDAO;
	}
}
