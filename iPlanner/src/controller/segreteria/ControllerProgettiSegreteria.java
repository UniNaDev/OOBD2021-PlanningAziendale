package controller.segreteria;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import controller.ControllerStart;
import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Progetto;
import gui.segreteria.GestioneProgettiSegreteria;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerProgettiSegreteria {
	
	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	private GestioneProgettiSegreteria gestioneProgettiFrame;

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
	public ControllerProgettiSegreteria(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		gestioneProgettiFrame = new GestioneProgettiSegreteria(this);
		gestioneProgettiFrame.setVisible(true);
	}
	
	//Metodi gestione GUI
	//-----------------------------------------------------------------
	
	//Metodo che reindirizza al frame di scelta iniziale quando viene annullata la creazione dell'account
	public void tornaAiPlanner() {
		gestioneProgettiFrame.setVisible(false);	//chiude la finestra di creazione account
		ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, true);	//torna ad iPlanner in modalità segreteria
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che ottiene tutti i progetti
	public ArrayList<Progetto> ottieniProgetti() throws SQLException{
		return projDAO.getProgetti();
	}
	
	//Metodo che crea un nuovo ambito per progetti
	public void creaAmbitoProgetto(String nomeAmbito) throws SQLException {
		AmbitoProgetto temp = new AmbitoProgetto(nomeAmbito);
		ambitoDAO.addAmbito(temp);
	}
	
	//Metodo che ottiene gli ambiti di un progetto
	public ArrayList<AmbitoProgetto> ottieniAmbitiProgetto(Progetto progetto) throws SQLException {
		return ambitoDAO.getAmbitiProgetto(progetto);
	}
	
	//Metodo che ottiene tutte le collaborazioni a un progetto
	public ArrayList<CollaborazioneProgetto> ottieniCollaborazioni(Progetto progetto) throws SQLException{
		return projDAO.getPartecipanti(progetto.getIdProgettto());
	}
	
	//Metodo che ottiene tutti gli ambiti
	public ArrayList<AmbitoProgetto> ottieniTuttiAmbiti() throws SQLException{
		ArrayList<AmbitoProgetto> temp = ambitoDAO.getAmbiti();
		temp.add(0,null);
		return temp;
	}
	
	//Metodo che ottiene tutte le tipologie
	public ArrayList<String> ottieniTipologie() throws SQLException{
		ArrayList<String> temp = projDAO.getTipologie();
		temp.add(0,null);
		return temp;
	}
	
	//Metodo che filtra i progetti
	public ArrayList<Progetto> ottieniProgettiFiltrati(String nomeCercato, AmbitoProgetto ambitoCercato, String tipologiaCercata, String scaduto, String terminato) throws SQLException{
		//TODO: ambito = null non contemplato
			ArrayList<Progetto> temp =projDAO.getProgettiFiltrati(nomeCercato, ambitoCercato, tipologiaCercata);	//ottiene i progetti filtrati per nome, ambito e tipologia
		if (scaduto.equals("Si")) {
			//filtra temp prendendo solo quelli scaduti
			temp = filtraScaduti(temp);
		}
		else if (scaduto.equals("No")) {
			//filtra temp prendendo solo quelli NON scaduti
			temp = filtraNonScaduti(temp);
		}
		if (terminato.equals("Si")) {
			//filtra temp prendendo solo quelli terminati
			temp = filtraTerminati(temp);
		}
		else if (terminato.equals("No")) {
			//filtra temp prendendo solo quelli NON terminati
			temp = filtraNonTerminati(temp);
		}
		return temp;
	}
	
	//Metodo che filtra i progetti scaduti
	private ArrayList<Progetto> filtraScaduti(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getScadenza().isBefore(LocalDate.now())) {
				temp.add(proj);
			}
		}
		return temp;
	}
	
	//Metodo che filtra i progetti NON scaduti
	private ArrayList<Progetto> filtraNonScaduti(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getScadenza().isAfter(LocalDate.now())) {
				temp.add(proj);
			}
		}
		return temp;
	}
	
	//Metodo che filtra i progetti terminati
	private ArrayList<Progetto> filtraTerminati(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getDataTerminazione() != null) {
				temp.add(proj);
			}
		}
		return temp;
	}
	
	//Metodo che filtra i progetti NON terminati
	private ArrayList<Progetto> filtraNonTerminati(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getDataTerminazione() == null) {
				temp.add(proj);
			}
		}
		return temp;
	}
}
