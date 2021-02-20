//Controller relativo alle finestre Miei Progetti e Gestione Progetti lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento delle finestre.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import gui.*;
import gui.dipendente.GestioneProgettiDipendente;
import gui.dipendente.MieiProgetti;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SkillDAO;


public class ControllerProgetto {
	private MieiProgetti mieiProgettiFrame;
	private GestioneProgettiDipendente gestioneProgettiFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private AmbitoProgettoDAO ambitoDAO = null;
	private SkillDAO skillDAO=null;
	
	private Dipendente dipendenteLogged = null;

	public ControllerProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, AmbitoProgettoDAO ambitoDAO,SkillDAO skillDAO, Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.ambitoDAO = ambitoDAO;
		this.skillDAO = skillDAO;
		this.dipendenteLogged = dipendenteLogged;
		
		mieiProgettiFrame = new MieiProgetti(this);
		mieiProgettiFrame.setVisible(true);
	}

	public void apriGUIInserisciPartecipantiProgetto(Progetto progettoSelezionato) {
		ControllerPartecipantiProgetto controller = new ControllerPartecipantiProgetto(luogoDAO, dipDAO, projDAO, meetDAO, dipendenteLogged, skillDAO, progettoSelezionato);
	}
	
	public void apriGUIGestioneProgetti() {
		gestioneProgettiFrame = new GestioneProgettiDipendente(this,dipendenteLogged);
		gestioneProgettiFrame.setVisible(true);
		mieiProgettiFrame.setVisible(false);
	}

	public ArrayList<Progetto> ottieniProgettiDipendente() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioniDipendente = projDAO.ottieniProgettiDipendente(dipendenteLogged);
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioniDipendente)
			progetti.add(collaborazione.getProgetto());
		return progetti;
	}
	
	public ArrayList<AmbitoProgetto> ottieniAmbiti() throws SQLException{
		return ambitoDAO.ottieniAmbiti();
	}
	
	public ArrayList<String> ottieniTipologie() throws SQLException{
		return projDAO.ottieniTipologie();
	}

	public void aggiornaProgetto(Progetto progettoModificato) throws SQLException{
		projDAO.aggiornaProgetto(progettoModificato);
	}
	
	public void aggiornaAmbitiProgetto(Progetto progettoModificato) throws SQLException {
		ambitoDAO.eliminaAmbitiProgetto(progettoModificato);
		ambitoDAO.inserisciAmbitiProgetto(progettoModificato);
	}
	
	public Progetto creaProgetto(Progetto nuovoProgetto) throws SQLException {
		projDAO.creaProgetto(nuovoProgetto);
		nuovoProgetto.setIdProgettto(projDAO.ottieniCodiceProgetto(nuovoProgetto));
		return nuovoProgetto;
	}

	public void inserisciAmbitiProgetto(Progetto nuovoProgetto) throws SQLException{
			ambitoDAO.inserisciAmbitiProgetto(nuovoProgetto);
	}
	
	public void inserisciProjectManager(Progetto nuovoProgetto) throws SQLException {
		projDAO.inserisciProjectManager(dipendenteLogged.getCf(), nuovoProgetto, "Project Manager");
	}
//	//
//	public ArrayList<AmbitoProgetto> ottieniAmbitiProgettoByCod(int codProgetto) throws SQLException {
//		return ambitoDAO.ottieniAmbitiProgettoDaCodice(codProgetto);
//	}
	
	public void rimuoviProgetto(Progetto progetto) throws SQLException {
		projDAO.rimuoviProgetto(progetto);
	}
	
	public boolean isProjectManager(Progetto progetto) throws SQLException {
		String cf =  projDAO.ottieniCFProjectManager(progetto);
		if (dipendenteLogged.getCf().equals(cf))
			return true;
		return false;
	}

	public ArrayList<Progetto> ottieniProgettiFiltrati(String nomeCercato, AmbitoProgetto ambitoCercato, String tipologiaCercata, String scaduto, String terminato) throws SQLException{
		ArrayList<Progetto> progettiFiltrati = projDAO.ottieniProgettiDipendentePerNome(nomeCercato,dipendenteLogged);
		ArrayList<Progetto> progettiConFiltro = new ArrayList<Progetto>();
		if (tipologiaCercata != null) {
			progettiConFiltro = projDAO.ottieniProgettiFiltratiPerTipo(tipologiaCercata);
			progettiFiltrati.retainAll(progettiConFiltro);
		}
		if (ambitoCercato != null) {
			progettiConFiltro = projDAO.ottieniProgettiFiltratiPerAmbito(ambitoCercato);
			progettiFiltrati.retainAll(progettiConFiltro);
		}
		if (scaduto.equals("Si")) {
			progettiFiltrati.retainAll(filtraScaduti(progettiFiltrati));
		}
		else if (scaduto.equals("No")) {
			progettiFiltrati.retainAll(filtraNonScaduti(progettiFiltrati));
		}
		if (terminato.equals("Si")) {
			progettiFiltrati.retainAll(filtraTerminati(progettiFiltrati));
		}
		else if (terminato.equals("No")) {
			progettiFiltrati.retainAll(filtraNonTerminati(progettiFiltrati));
		}
		return progettiFiltrati;
	}
	
	private ArrayList<Progetto> filtraScaduti(ArrayList<Progetto> progetti){
		ArrayList<Progetto> progettiScaduti = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getScadenza().isBefore(LocalDate.now())) {
				progettiScaduti.add(proj);
			}
		}
		return progettiScaduti;
	}
	
	private ArrayList<Progetto> filtraNonScaduti(ArrayList<Progetto> progetti){
		ArrayList<Progetto> progettiNonScaduti = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getScadenza().isAfter(LocalDate.now())) {
				progettiNonScaduti.add(proj);
			}
		}
		return progettiNonScaduti;
	}
	
	private ArrayList<Progetto> filtraTerminati(ArrayList<Progetto> progetti){
		ArrayList<Progetto> progettiTerminati = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getDataTerminazione() != null) {
				progettiTerminati.add(proj);
			}
		}
		return progettiTerminati;
	}
	
	private ArrayList<Progetto> filtraNonTerminati(ArrayList<Progetto> progetti){
		ArrayList<Progetto> progettiNonTerminati = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getDataTerminazione() == null) {
				progettiNonTerminati.add(proj);
			}
		}
		return progettiNonTerminati;
	}
}