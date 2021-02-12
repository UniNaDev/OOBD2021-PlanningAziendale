package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import entita.Meeting;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.LuogoNascita;
import gui.dipendente.Home;
import gui.dipendente.MioAccount;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerGestioneProfilo {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private Home homeFrame;	//finestra principale
	private MioAccount accountFrame;	//finestra con dettagli del proprio profilo
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	
	//Altri attributi
	private Dipendente dipendente = null;

	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore
	public ControllerGestioneProfilo(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO, Dipendente dipendente) {
		//ottiene tutti i dao
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		this.dipendente = dipendente;	//ottiene il dipendente che ha effettuato il login
		
		//apre la finestra principale
		homeFrame=new Home(this, dipendente);
		homeFrame.setVisible(true);
		
	}
	
	//Metodi per gestione GUI
	//-----------------------------------------------------------------
	
	//Metodo che apre la finestra dei dettagli del proprio profilo
	public void apriMioAccount() {
		accountFrame=new MioAccount(this, dipendente);
		accountFrame.setVisible(true);
	}
	
	//Metodo che chiude la finestra dei dettagli dell'account
	public void chiudiMioAccount() {
		accountFrame.setVisible(false);
	}

	//Metodo per il logout
	public void logout() {
		System.exit(0);
	}

	//Metodo che apre la gestione progetti del dipendente
	public void apriMieiProgetti() {
		ControllerProgetto controller= new ControllerProgetto(luogoDAO,dipDAO,projDAO,meetDAO,ambitoDAO,skillDAO, dipendente);
	}
	
	//Metodo che apre la gestione dei meeting del dipendente
	public void apriMieiMeeting() {
		ControllerMeeting controller= new ControllerMeeting(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,dipendente);
	}
	
	//Metodo che torna alla home
	public void tornaAHome() {
		homeFrame.setVisible(false);
		homeFrame=new Home(this, dipendente);
		homeFrame.setVisible(true);
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che ottiene i progetti a cui partecipa il dipendente
	public ArrayList<CollaborazioneProgetto> ottieniProgetti() throws SQLException {
		return projDAO.getProgettiByDipendente(dipendente);
	}
	
	//Metodo che ottiene i meeting a cui partecipa il dipendente
	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		return meetDAO.getMeetingsByInvitato(dipendente);
	}
	
	//Metodo che aggiorna le informazioni del dipendente
	public void aggiornaInfoDipendente(String nome, String cognome, char sesso, LocalDate dataNascita, LuogoNascita luogoNascita, String email, String password, String telefono, String cellulare, String indirizzo) throws SQLException {
		//setta le nuove informazioni del dipendente
		dipendente.setNome(nome);
		dipendente.setCognome(cognome);
		dipendente.setSesso(sesso);
		dipendente.setDataNascita(dataNascita);
		dipendente.setLuogoNascita(luogoNascita);
		if(!dipendente.getEmail().equals(email))
			dipendente.setEmail(email);
		dipendente.setPassword(password);
		dipendente.setTelefonoCasa(telefono);
		dipendente.setCellulare(cellulare);
		dipendente.setIndirizzo(indirizzo);
	
		dipDAO.updateDipendente(dipendente); //tenta di fare l'update nel DB	
			JOptionPane.showMessageDialog(null, "Modifica Effettuata con successo");
		chiudiMioAccount();
		tornaAHome();
		}
	
	
	//Metodo che restituisce tutte le province del database
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia per il menù di creazione account
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}	
}
