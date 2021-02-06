package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.Skill;
import gui.dipendente.InserisciPartecipantiMeeting;
import gui.dipendente.InserisciPartecipantiProgetto;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerPartecipantiProgetto {
	
private InserisciPartecipantiProgetto inserisciPartecipantiProgetto;
	
	//DAO
		private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
		private DipendenteDAO dipDAO = null;	//dao del dipendente
		private ProgettoDAO projDAO = null;	//dao dei progetti
		private MeetingDAO meetDAO = null;	//dao dei meeting
		private SkillDAO skillDAO=null;
		
		//Altri attributi
		private Dipendente dipendente = null;
		private Progetto progettoSelezionato=null;
		private int codiceProgetto;
		//Costruttore
		public ControllerPartecipantiProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO,Dipendente dipendente, SkillDAO skillDAO, Progetto progettoSelezionato, int codiceProgetto) {
			//Ottiene i dao
			this.luogoDAO = luogoDAO;
			this.dipDAO = dipDAO;
			this.projDAO = projDAO;
			this.meetDAO = meetDAO;
			this.skillDAO=skillDAO;
			this.dipendente = dipendente;	//ottiene il dipendente che ha fatto l'accesso
			this.progettoSelezionato=progettoSelezionato;
			this.codiceProgetto=codiceProgetto;
			
			inserisciPartecipantiProgetto=new InserisciPartecipantiProgetto(this,progettoSelezionato,codiceProgetto);
			inserisciPartecipantiProgetto.setVisible(true);
		}

		
		//Metodo che ottiene i dipendenti che non partecipano al meeting selezionato
		public ArrayList<Dipendente> ottieniDipendenti() throws SQLException {
			return dipDAO.getDipendenti();
		}
		
		//Metodo che ottiene le skill del dipendente
		public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		
			return skillDAO.getSkillDipendente(cfDipendente);
		}
		
		public ArrayList<Dipendente> ottieniPartecipanti(int codiceProgetto) throws SQLException{
			
			
			return projDAO.getPartecipantiSenzaRuolo(codiceProgetto);
		}


		public void inserisciPartecipante(String cf, int codiceProgetto2, String ruolo) throws SQLException {
		projDAO.addPartecipante(cf, codiceProgetto2, ruolo);
			
		}


		public void eliminaPartecipante(Dipendente selectedValue, int codiceProgetto2) throws SQLException {
		
			projDAO.deletePartecipante(selectedValue, codiceProgetto2);
		}


//		public ArrayList<String> ottieniRuoli() throws SQLException {
//			
//			return projDAO.getRuoliDipendenti();
//		}
		
		public String [] ottieniRuoli() throws SQLException{
			String [] temp = new String [projDAO.getRuoliDipendenti().size()];
			for (int i = 0; i < projDAO.getRuoliDipendenti().size(); i++)
				temp[i] = projDAO.getRuoliDipendenti().get(i);
			return temp;
		}
}
