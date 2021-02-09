package controller.segreteria;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import controller.ControllerStart;
import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;
import gui.iPlanner;
import gui.segreteria.GestioneDipendenti;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerDipendentiSegreteria {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private GestioneDipendenti gestioneDipendentiFrame;	//frame della finestra per creare nuovi account (dipendenti)
	
	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetto
	private MeetingDAO meetDAO = null;	//dao meeting
	private SkillDAO skillDAO = null;	//dao delle skill
	private SalaRiunioneDAO salaDAO = null;	//dao delle sale
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	
	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore
	public ControllerDipendentiSegreteria(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO,
			SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		apriGestioneDipendenti();
	}
	
	//Metodi gestione GUI
	//-----------------------------------------------------------------
	
	//Metodo che indirizza al frame di creazione dipendente
	public void apriGestioneDipendenti() {
	  gestioneDipendentiFrame= new GestioneDipendenti(this);	//inizializza la finestra di creazione del nuovo account/dipendente
	  gestioneDipendentiFrame.setVisible(true);	//mostra la finestra di creazione dell'account	  
	}
	
	//Metodo che reindirizza al frame di scelta iniziale quando viene annullata la creazione dell'account
	public void tornaAiPlanner() {
		gestioneDipendentiFrame.setVisible(false);	//chiude la finestra di creazione account
		ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, true);	//torna ad iPlanner in modalità segreteria
	}
	
	//Altri metodi del controller
	//-----------------------------------------------------------------
		
	//Metodo che prende le province per il menù di creazione account
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	//Metodo che prende i comuni di una provincia per il menù di creazione account
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}
	
	//Metodo che crea un nuovo account per il dipendente
	public void creaAccount(String nome, String cognome, char sesso, LocalDate dataNascita, LuogoNascita luogoNascita, String email, String password, String telefono, String cellulare, String indirizzo, ArrayList<Skill> skills, float salario) throws SQLException {
		//crea un dipendente temporaneo con i parametri in input
		Dipendente temp = new Dipendente(null, nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,salario,password, 0f);
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
				tornaAiPlanner();
			//altrimenti apre una nuova finestra per la creazione account
			else {
				gestioneDipendentiFrame.dispose(); //chiude la finestra di creazione e ne apre un'altra
				gestioneDipendentiFrame=new GestioneDipendenti(this);	//inizializza la prima finestra di creazione account
				gestioneDipendentiFrame.setVisible(true);	//mostra la finestra inizializzata
			}
		}
	}
	
	//Metodo che crea una nuova skill e la inserisce nel database tramite il DAO
	public void creaNuovaSkill(String nomeSkill) throws SQLException {
		Skill temp = new Skill(0, nomeSkill);	//crea la skill temporanea
		skillDAO.addSkill(temp);	//aggiunge la skill al database
	}
	
	//Metodo che restituisce le skill del database
	public ArrayList<Skill> ottieniSkill() throws SQLException{
		return skillDAO.getSkills();
	}
	
	//Metodo che ottiene tutti i dipendenti
	public ArrayList<Dipendente> ottieniDipendenti() throws SQLException {
		ArrayList<Dipendente> temp = dipDAO.getDipendenti();
		return temp;
	}
	
	//Meotodo che ottiene il massimo stipendio nel DB per i filtri
	public float ottieniMaxStipendio() {
		try {
			return dipDAO.getMaxStipendio();
		} catch (SQLException e) {
			return 100000000f;
		}
	}
	
	//Meotodo che filtra i dipendenti
	public ArrayList<Dipendente> filtraDipendenti(String nomeCognomeEmail, int etàMinima, int etàMassima, float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima) throws SQLException {
		return dipDAO.getDipendentiFiltrati(nomeCognomeEmail, etàMinima, etàMassima, salarioMinimo, salarioMassimo, valutazioneMinima, valutazioneMassima);
	}
	
	//Metodo che aggiorna le informazioni di un dipendente
	public void aggiornaDipendente(Dipendente dipendente) throws SQLException {
		dipDAO.updateDipendente(dipendente);
		for (Skill skill: dipendente.getSkills()) {
			try {
				skillDAO.addSkillDipendente(skill, dipendente);
			}
			catch (SQLException e) {
				continue;
			}
		}
	}
}
