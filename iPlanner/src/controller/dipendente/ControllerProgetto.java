//Controller relativo alle finestre Miei Progetti e Gestione Progetti lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento delle finestre.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public String [] ottieniTipologie() throws SQLException{
		String [] temp = new String [projDAO.getTipologie().size()];
		for (int i = 0; i < projDAO.getTipologie().size(); i++)
			temp[i] = projDAO.getTipologie().get(i);
		return temp;
	}

	//TODO: eliminabile probabilmente
	public ArrayList<Dipendente> ottieniPartecipantiProgetto (int codiceProgetto) throws SQLException {
		return projDAO.getPartecipantiProgettoSenzaRuolo(codiceProgetto);
	}

	//TODO: eliminabile probabilmente
	public ArrayList<Meeting> ottieniMeetingRelativiProgetto(int codProgettoSelezionato) throws SQLException {
		return projDAO.getMeetingRelativiProgetto(codProgettoSelezionato);
	}

	public void aggiornaProgetto(int codProgetto ,String nuovoNome ,String nuovaTipologia ,String nuovaDescrizione,LocalDate dataCreazione, LocalDate nuovaDataTerminazione , LocalDate nuovaDataScadenza, ArrayList<AmbitoProgetto> nuoviAmbiti) throws SQLException {
		Progetto progetto = new Progetto(codProgetto, nuovoNome , nuovaTipologia , nuovaDescrizione ,dataCreazione, nuovaDataScadenza, nuovaDataTerminazione);
		projDAO.updateProgetto(progetto);
		ambitoDAO.deleteAmbitiProgetto(progetto);
		progetto.setAmbiti(nuoviAmbiti);
		ambitoDAO.insertAmbitiOfProgetto(progetto);								
	}
	
	public void creaProgetto(String nomeProgetto , String tipologia , String descrizioneProgetto , LocalDate dataCreazione , LocalDate dataScadenza,ArrayList<AmbitoProgetto> ambiti) throws SQLException {
		Progetto progetto = new Progetto(nomeProgetto, tipologia, descrizioneProgetto , dataCreazione , dataScadenza);
		projDAO.insertProgetto(progetto);
		progetto.setIdProgettto(projDAO.getCodProgetto(progetto));
		progetto.setAmbiti(ambiti);
		ambitoDAO.insertAmbitiOfProgetto(progetto);
		projDAO.insertProjectManager(dipendenteLogged.getCf(), progetto, "Project Manager");	
	}
	
	//TODO: eliminabile probabilmente
	public ArrayList<AmbitoProgetto> ottieniAmbitiProgettoByCod(int codProgetto) throws SQLException {
		return ambitoDAO.getAmbitiProgettoByCodice(codProgetto);
	}
	
	//TODO: potrebbe diventare void
	public boolean rimuoviProgetto (Progetto progetto) throws SQLException {
		boolean risultato = projDAO.deleteProgetto(progetto);
		return risultato;
	}

	public void updateProgetto(Progetto progetto) throws SQLException {
		projDAO.updateProgetto(progetto);
		ambitoDAO.deleteAmbitiProgetto(progetto);
		progetto.setAmbiti(progetto.getAmbiti());
		ambitoDAO.insertAmbitiOfProgetto(progetto);
	}
	
	//TODO: eliminabile probabilmente
	public String ottieniProjectManager(Progetto progetto) throws SQLException {
		return projDAO.getProjectManager(progetto);
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


	

