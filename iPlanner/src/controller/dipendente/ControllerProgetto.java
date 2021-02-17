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

	public void apriInserisciPartecipantiProgetto(Progetto progettoSelezionato) {
		ControllerPartecipantiProgetto controller = new ControllerPartecipantiProgetto(luogoDAO, dipDAO, projDAO, meetDAO, dipendenteLogged, skillDAO, progettoSelezionato);
	}
	
	public void apriGestioneProgetti() {
		gestioneProgettiFrame = new GestioneProgettiDipendente(this,dipendenteLogged);
		gestioneProgettiFrame.setVisible(true);
		mieiProgettiFrame.setVisible(false);
	}

	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.getProgettiByDipendente(dipendenteLogged);
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			progetti.add(collaborazione.getProgetto());
		return progetti;
	}
	
	public ArrayList<AmbitoProgetto> ottieniAmbiti() throws SQLException{
		return ambitoDAO.getAmbiti();
	}
	
	public ArrayList<String> ottieniTipologie() throws SQLException{
		return projDAO.getTipologie();
	}

	//TODO: eliminabile probabilmente
	public ArrayList<Dipendente> ottieniPartecipantiProgetto (int codiceProgetto) throws SQLException {
		return projDAO.getPartecipantiProgettoSenzaRuolo(codiceProgetto);
	}

	//TODO: eliminabile probabilmente
	public ArrayList<Meeting> ottieniMeetingRelativiProgetto(int codProgettoSelezionato) throws SQLException {
		return projDAO.getMeetingRelativiProgetto(codProgettoSelezionato);
	}

	public boolean aggiornaProgetto(Progetto progettoModificato) {
		try {
			projDAO.updateProgetto(progettoModificato);
		} catch(SQLException e) {
			//TODO: aggiungi altre eccezioni
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			ambitoDAO.deleteAmbitiProgetto(progettoModificato);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			//TODO: aggiungi altre eccezioni
			ambitoDAO.insertAmbitiOfProgetto(progettoModificato);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean creaProgetto(Progetto nuovoProgetto) {
		try {
			//TODO: aggiungi altre eccezioni
			projDAO.insertProgetto(nuovoProgetto);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			nuovoProgetto.setIdProgettto(projDAO.getCodProgetto(nuovoProgetto));
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			//TODO: aggiungi altre eccezioni
			ambitoDAO.insertAmbitiOfProgetto(nuovoProgetto);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			//TODO: aggiungi altre eccezioni
			projDAO.insertProjectManager(dipendenteLogged.getCf(), nuovoProgetto, "Project Manager");
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	//TODO: eliminabile probabilmente
	public ArrayList<AmbitoProgetto> ottieniAmbitiProgettoByCod(int codProgetto) throws SQLException {
		return ambitoDAO.getAmbitiProgettoByCodice(codProgetto);
	}
	
	public boolean rimuoviProgetto (Progetto progetto) {
		try {
			projDAO.deleteProgetto(progetto);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public void updateProgetto(Progetto progetto) throws SQLException {
		projDAO.updateProgetto(progetto);
		ambitoDAO.deleteAmbitiProgetto(progetto);
		progetto.setAmbiti(progetto.getAmbiti());
		ambitoDAO.insertAmbitiOfProgetto(progetto);
	}
	
	public boolean isProjectManager(Progetto progetto) throws SQLException {
		String cf =  projDAO.getCFProjectManager(progetto);
		if (dipendenteLogged.getCf().equals(cf))
			return true;
		return false;
	}

	public ArrayList<Progetto> ottieniProgettiFiltrati(String nomeCercato, AmbitoProgetto ambitoCercato, String tipologiaCercata, String scaduto, String terminato) throws SQLException{
		ArrayList<Progetto> progettiFiltrati = projDAO.getProgettiDipendenteByNome(nomeCercato,dipendenteLogged);
		ArrayList<Progetto> progettiConFiltro = new ArrayList<Progetto>();
		if (tipologiaCercata != null) {
			progettiConFiltro = projDAO.getProgettiByTipo(tipologiaCercata);
			progettiFiltrati.retainAll(progettiConFiltro);
		}
		if (ambitoCercato != null) {
			progettiConFiltro = projDAO.getProgettiByAmbito(ambitoCercato);
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


	

