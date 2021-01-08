//Controller principale dell'interfaccia grafica
package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import Entita.Dipendente;
import Entita.LuogoNascita;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;
import InterfacceDAO.MeetingDAO;
import InterfacceDAO.ProgettoDAO;
import Viste.CreaAccountWindow;
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
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	
	private Dipendente loggedUser = null;	//utente che fa il login

	//METODI
	
	//Costruttore controller GUI
	public ControllerStart(Connection connection, LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO) throws SQLException {
		this.startWindow = new StartWindow(this);	//inizializza la finestra iniziale
		this.connection = connection;	//ottiene la connessione al DB
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
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
		loggedUser = dipDAO.loginCheck(email, password);
		ControllerLogged loggedCTRL = new ControllerLogged(connection, loggedUser, projDAO, meetDAO);
		loginWindow.setVisible(false);
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
			if (dipDAO.addDipendente(temp))
				System.out.println("Operazione riuscita");
		} catch (SQLException e) {
			ControllerErrori errorCTRL = new ControllerErrori("Errore codice " + e.getErrorCode() + "\n" + e.getMessage(), false);
		}
	}
	public LuogoNascitaDAO getLuogoDAO() {
		return luogoDAO;
	}

	public DipendenteDAO getDipDAO() {
		return dipDAO;
	}
}
