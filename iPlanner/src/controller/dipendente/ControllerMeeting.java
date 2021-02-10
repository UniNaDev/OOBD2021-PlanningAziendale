package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import entita.Skill;
import gui.*;
import gui.dipendente.GestioneMeetingDipendente;
import gui.dipendente.Home;
import gui.dipendente.InserisciPartecipantiMeeting;
import gui.dipendente.MieiMeeting;
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
	private Home home;
	private GestioneMeetingDipendente gestioneMeeting;
	private InserisciPartecipantiMeeting inserisciPartecipantiMeeting;
	
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
		this.skillDAO=skillDAO;
		this.dipendente = dipendente;	//ottiene il dipendente che ha fatto l'accesso
		
		mieiMeeting=new MieiMeeting(this);	//apre la finestra dei suoi meeting
		mieiMeeting.setVisible(true);
	}

	//Metodi per la gestione delle GUI
	
	//Metodo che apre la finestra di gestione dei meeting
	public void apriGestioneMeeting() {
		gestioneMeeting= new GestioneMeetingDipendente(this,dipendente);
		gestioneMeeting.setVisible(true);
		
		mieiMeeting.setVisible(false);
	}
	public void apriInserisciPartecipantiMeeting(Meeting meetingSelezionato) {
		
		ControllerPartecipantiMeeting controller=new ControllerPartecipantiMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, dipendente,meetingSelezionato);
		
	}
	
	//Altri metodi
	//Metodo che ottiene tutti i meeting a cui deve partecipare/ha partecipato il dipendente
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(dipendente);
	}
	
	//Ottiene i progetti del dipendente
	public ArrayList<Progetto> ottieniNomiProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.getProgettiByDipendente(dipendente);
		
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			temp.add(collaborazione.getProgetto());
		
		return temp;
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
	public void aggiornaMeeting(Meeting meeting, Progetto progettoSelezionato) throws SQLException {
		meetDAO.updateMeeting(meeting,progettoSelezionato);
	}

	//Metodo che ottiene gli invitati al meeting selezionato
//	public ArrayList<Dipendente> ottieniInvitati() throws SQLException {
//		return meetDAO.();
//	}

//	//Metodo che ottiene il progetto discusso nel meeting
//	public String ottieniProgettoDiscusso(int idMeeting) throws SQLException {
//		return meetDAO.getProgettoRelativo(idMeeting);
//	}

	//Metodo che inserisce il meeting del progetto da discutere
	public void inserisciMeeting(Meeting meetingInserito,String nomeProgettoDisusso) throws SQLException{

			//Prova ad inserire il meeting e del progetto da discutere
			meetDAO.addMeeting(meetingInserito,nomeProgettoDisusso);
			
			
			//Viene inserito come organizzatore la persona che crea il meeting
			meetDAO.addOrganizzatore(dipendente.getCf()); 
			

	
	}

	//Metodo che rimuove i meeting
	public void rimuoviMeeting(int idMeeting) throws SQLException {

		//Rimuove il meeting selezionato
		meetDAO.removeMeeting(idMeeting);

	}

	public void inserisciMeetingCompleto(Meeting meetingInserito, Progetto progetto) throws SQLException {
		
		meetDAO.addMeetingCompleto(meetingInserito, progetto);
		meetDAO.addOrganizzatore(dipendente.getCf()); 
	}

	public Progetto ottieniProgettoInserito(Progetto progetto) throws SQLException {
		
		return projDAO.getProgettoByCod(progetto.getIdProgettto());
	}

	public String organizzatoreCheck(Meeting meeting) throws SQLException {
		
		return dipDAO.organizzatoreCheck(meeting);
		
	}

	




}
