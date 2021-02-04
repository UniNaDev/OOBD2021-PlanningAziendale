package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.Skill;

import gui.InserisciPartecipantiMeeting;
import gui.MieiMeeting;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerPartecipantiMeeting {
	
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
		private Meeting meetingSelezionato;
		private int codiceMeeting;
		//Costruttore
		public ControllerPartecipantiMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente dipendente, Meeting meetingSelezionato, int codiceMeeting) {
			//Ottiene i dao
			this.luogoDAO = luogoDAO;
			this.dipDAO = dipDAO;
			this.projDAO = projDAO;
			this.meetDAO = meetDAO;
			this.salaDAO = salaDAO;
			this.skillDAO=skillDAO;
			this.dipendente = dipendente;	//ottiene il dipendente che ha fatto l'accesso
			this.meetingSelezionato=meetingSelezionato;
			this.codiceMeeting=codiceMeeting;
			inserisciPartecipantiMeeting= new InserisciPartecipantiMeeting(this,meetingSelezionato,codiceMeeting);
			inserisciPartecipantiMeeting.setVisible(true);
	
		}
		
		//Metodo che ottiene gli invitati al meeting selezionato
		public ArrayList<Dipendente> ottieniInvitati(int idMeeting) throws SQLException {
			return meetDAO.getInvitati(idMeeting);
		}
		
		public ArrayList<Meeting> ottieniMeeting() throws SQLException {
			return meetDAO.getMeetingsByInvitato(dipendente);
		}
		
		//Metodo che ottiene i dipendenti che non partecipano al meeting selezionato
		public ArrayList<Dipendente> ottieniDipendenti() throws SQLException {
			return dipDAO.getDipendenti();
		}
		
		//Metodo che ottiene le skill del dipendente
		public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		
			return skillDAO.getSkillDipendente(cfDipendente);
		}

		//Metodo che inserisce i partecipanti ad un meeting
		public void inserisciPartecipante(String cf, int codiceMeeting) throws SQLException {
			meetDAO.aggiungiPartecipanteMeeting(cf,codiceMeeting);
		}

		public void eliminaPartecipante(Dipendente dipendente2,int idMeeting) throws SQLException {
			meetDAO.eliminaPartecipanteMeeting(dipendente2.getCf(),idMeeting);
		}

}
