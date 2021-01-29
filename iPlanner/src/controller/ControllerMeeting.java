package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entita.Dipendente;
import entita.Meeting;
import entita.SalaRiunione;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;


public class ControllerMeeting {

	//ATTRIBUTI
	//----------------------------------------------------
	
	//Attributi GUI
	private MieiMeeting mieiMeeting;
	private GestioneMeeting gestioneMeeting;
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao dei progetti
	private MeetingDAO meetDAO = null;	//dao dei meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao sale riunione
	
	//Altri attributi
	private Dipendente dipendente = null;

	//METODI
	//----------------------------------------------------
	
	//Costruttore
	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente dipendente) {
		//Ottiene i dao
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;

		this.dipendente = dipendente;	//ottiene il dipendente che ha fatto l'accesso
		
		mieiMeeting=new MieiMeeting(this);	//apre la finestra dei suoi meeting
		mieiMeeting.setVisible(true);
	}

	//Metodi per la gestione delle GUI
	
	//Metodo che apre la finestra di gestione dei meeting
	public void apriGestioneMeeting() {
		gestioneMeeting= new GestioneMeeting(this);
		gestioneMeeting.setVisible(true);	
	}
	
	//Altri metodi
	//Metodo che ottiene tutti i meeting a cui deve partecipare/ha partecipato il dipendente
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(dipendente);
	}
	
	//Metodo che ottiene tutte le sale disponibili nel DB
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.getSale();
	}
	
	//Metodo che ottiene tutte le piattaforme disponibili sul DB
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.getPiattaforme();
	}
	
	//Metodo che aggiorna le info di un meeting nel DB
	public void aggiornaMeeting(Meeting meeting) throws SQLException {
		meetDAO.updateMeeting(meeting);
	}

	public ArrayList<Dipendente> ottieniInvitati(int idMeeting) throws SQLException {
		// TODO Auto-generated method stub
		return meetDAO.getInvitati(idMeeting);
	}

	public String ottieniProgettoDiscusso(int idMeeting) throws SQLException {
		// TODO Auto-generated method stub
		return meetDAO.getProgettoRelativo(idMeeting);
	}

	public void inserisciMeeting(Meeting meetingInserito){
		// TODO Auto-generated method stub
		
		try {
			meetDAO.addMeeting(meetingInserito);
			JOptionPane.showMessageDialog(null, "Meeting Inserito Correttamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Inserimento non riuscito,controllare i campi inseriti");
		}
	
	}

	public void rimuoviMeeting(int idMeeting) throws SQLException {
		// TODO Auto-generated method stub
		meetDAO.removeMeeting(idMeeting);
		JOptionPane.showMessageDialog(null, "Meeting Eliminato Correttamente");
		gestioneMeeting.setVisible(false);
		gestioneMeeting= new GestioneMeeting(this);
		gestioneMeeting.setVisible(true);
		
		
	}
}
