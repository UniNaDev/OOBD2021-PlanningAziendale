package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;
import gui.*;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SkillDAO;

public class ControllerScelta {

	private iPlanner iPlannerFrame;	//frame della finestra di scelta iniziale
	private NuovoDipendente nuovoDipendenteFrame;	//frame della finestra per creare nuovi account (dipendenti)
	
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	
	private boolean segreteria = false;	//autorizzazione (true = segreteria, false = dipendente)
	
	//Costruttore del controllee di scelta che mostra la prima finestra di scelta
	public ControllerScelta(boolean segreteria, LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO) {
		//Ottiene le implementazioni dei DAO inizializzate nel main Starter
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.segreteria = segreteria;	//ottiene l'autorizzazione presa nel main dagli argomenti a linea di comando
		
		iPlannerFrame=new iPlanner(this, segreteria);	//inizializza la prima finestra di scelta
		iPlannerFrame.setVisible(true);	//mostra la finestra inizializzata
		
	}
	
	//Metodo che indirizza al frame di registrazione
	public void linkToCreationFrame() {
		
	  iPlannerFrame.setVisible(false);	//chiude la finestra di scelta 
	  nuovoDipendenteFrame= new NuovoDipendente(this);	//inizializza la finestra di creazione del nuovo account/dipendente
	  nuovoDipendenteFrame.setVisible(true);	//mostra la finestra di creazione dell'account
	  
	}
	
	//Metodo che indirizza al Login
	public void linkToLoginFrame() {
		
		iPlannerFrame.setVisible(false);	//chiude la finestra di scelta
		ControllerAccesso controller=new ControllerAccesso(segreteria, luogoDAO, dipDAO, projDAO, meetDAO, skillDAO);	//inizializza il controller di accesso che si occupa del login e che mostrerà la finestra di login
		
	}
	
	//Metodo che reindirizza al frame di scelta iniziale quando viene annullata la creazione dell'account
	public void annulla() {
		
		nuovoDipendenteFrame.setVisible(false);	//chiude la finestra di creazione account
		iPlannerFrame.setVisible(true);	//mostra la finestra di scelta
		
	}

	//Metodo che prende le province per il menù di creazione account
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia per il menù di creazione account
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}
	
	//Metodo che crea un nuovo account per il dipendente
	public void creaAccount(String nome, String cognome, char sesso, LocalDate dataNascita, LuogoNascita luogoNascita, String email, String password, String telefono, String cellulare, String indirizzo, ArrayList<Skill> skills, float salario) {
		//crea un dipendente temporaneo con i parametri in input
		Dipendente temp = new Dipendente(null, nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,salario,password, 0f);
		temp.setCf(temp.generaCF()); 	//setta il suo codice fiscale appena generato
		try {
			//se l'insert nel database ha successo
			if (dipDAO.addDipendente(temp)) {
				temp.setSkills(skills);	//setta la skill del dipendente
				for (Skill skill: skills)	//aggiunge tutte le sue skill nel db in associazione con lui
					if (!skillDAO.addSkillDipendente(skill, temp)) {
						JOptionPane.showMessageDialog(null,
								"Errore inserimento delle skill nel database.",
								"Errore skill",
								JOptionPane.ERROR_MESSAGE);	
					}
				//chiedi se vuoi creare un altro account o uscire
				int yesNo = JOptionPane.showConfirmDialog(null,
						"Creazione riuscita.\nVuoi crearne un altro?",
						"Creazione riuscita",
						JOptionPane.YES_NO_OPTION);
				//se risponde "No" chiude il programma
				if (yesNo == 1)
					System.exit(0);
				//altrimenti apre una nuova finestra per la creazione account
				else {
					nuovoDipendenteFrame.dispose(); //chiude la finestra di creazione e ne apre un'altra
					nuovoDipendenteFrame=new NuovoDipendente(this);	//inizializza la prima finestra di creazione account
					nuovoDipendenteFrame.setVisible(true);	//mostra la finestra inizializzata
					}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage(),
					"Errore creazione account #" + e.getErrorCode(),
					JOptionPane.ERROR_MESSAGE);	//mostra messaggio di errore nella creazione account
		}
	}
	
	//Metodo che crea una nuova skill e la inserisce nel database tramite il DAO
	public void creaNuovaSkill(String nomeSkill) throws SQLException {
		Skill temp = new Skill(nomeSkill);	//crea la skill temporanea
		skillDAO.addSkill(temp);	//aggiunge la skill al database
	}
	
	//Metodo che restituisce le skill del database
	public ArrayList<Skill> ottieniSkill() throws SQLException{
		return skillDAO.getSkills();
	}
}
