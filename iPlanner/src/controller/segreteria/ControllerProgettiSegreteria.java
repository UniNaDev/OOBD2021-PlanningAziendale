//Controller relativo alla finestra per la gestione dei progetti lato segreteria.
//Contiene metodi necessari per il corretto funzionamento della finestra e per il
//corretto comportamento rispetto alle altre finestre raggiungibili del programma.

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
	private GestioneProgettiSegreteria gestioneProgettiFrame;

	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	private AmbitoProgettoDAO ambitoDAO = null;
	
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
	
	public void tornaASegreteria() {
		gestioneProgettiFrame.setVisible(false);
		ControllerAreaSegreteria controller = new ControllerAreaSegreteria(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO);
	}
	
	public ArrayList<Progetto> ottieniProgetti() throws SQLException{
		return projDAO.getProgetti();
	}
	
	public void creaAmbitoProgetto(String nomeAmbito) throws SQLException {
		AmbitoProgetto temp = new AmbitoProgetto(nomeAmbito);
		ambitoDAO.insertAmbito(temp);
	}
	
	public ArrayList<AmbitoProgetto> ottieniAmbitiProgetto(Progetto progetto) throws SQLException {
		return ambitoDAO.getAmbitiOfProgetto(progetto);
	}
	
	public ArrayList<CollaborazioneProgetto> ottieniCollaborazioni(Progetto progetto) throws SQLException{
		return projDAO.getPartecipantiProgetto(progetto.getIdProgettto());
	}
	
	public ArrayList<AmbitoProgetto> ottieniTuttiAmbiti() throws SQLException{
		ArrayList<AmbitoProgetto> temp = ambitoDAO.ottieniAmbiti();
		temp.add(0,null);
		return temp;
	}
	
	public ArrayList<String> ottieniTipologie() throws SQLException{
		ArrayList<String> temp = projDAO.ottieniTipologie();
		temp.add(0,null);
		return temp;
	}
	
	public ArrayList<Progetto> ottieniProgettiFiltrati(String nomeCercato, AmbitoProgetto ambitoCercato, String tipologiaCercata, String scaduto, String terminato) throws SQLException{
		ArrayList<Progetto> progetti = projDAO.getProgettiByNome(nomeCercato);
		ArrayList<Progetto> progettiConFiltro = new ArrayList<Progetto>();
		if (tipologiaCercata != null) {
			progettiConFiltro = projDAO.getProgettiByTipo(tipologiaCercata);
			progetti.retainAll(progettiConFiltro);
		}
		if (ambitoCercato != null) {
			progettiConFiltro = projDAO.getProgettiByAmbito(ambitoCercato);
			progetti.retainAll(progettiConFiltro);
		}
		if (scaduto.equals("Si")) {
			progetti.retainAll(filtraScaduti(progetti));
		}
		else if (scaduto.equals("No")) {
			progetti.retainAll(filtraNonScaduti(progetti));
		}
		if (terminato.equals("Si")) {
			progetti.retainAll(filtraTerminati(progetti));
		}
		else if (terminato.equals("No")) {
			progetti.retainAll(filtraNonTerminati(progetti));
		}
		return progetti;
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
