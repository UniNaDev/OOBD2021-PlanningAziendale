//Controller relativo alla finestra della gestione dei meeting lato segreteria.
//Contiene i metodi necessari al corretto funzionamento della finestra e ai passaggi
//da quest'ultima alle altre finestre raggiungibili nel programma.

package controller.segreteria;

import java.sql.SQLException;
import java.util.ArrayList;

import controller.ControllerStart;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.SalaRiunione;
import gui.segreteria.GestioneMeetingSegreteria;
import gui.segreteria.GestioneSale;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerMeetingSegreteria {
	private GestioneMeetingSegreteria gestioneMeetingSegreteriaFrame;
	private GestioneSale gestioneSaleFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	private AmbitoProgettoDAO ambitoDAO = null;

	public ControllerMeetingSegreteria (LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		gestioneMeetingSegreteriaFrame = new GestioneMeetingSegreteria(this);
		gestioneMeetingSegreteriaFrame.setVisible(true);
	}
	public void tornaAiPlanner() {
		gestioneMeetingSegreteriaFrame.setVisible(false);
		ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, true);
	}
	
	public void apriGestioneSale() {
		gestioneMeetingSegreteriaFrame.setEnabled(false);
		gestioneSaleFrame = new GestioneSale(this);
		gestioneSaleFrame.setVisible(true);
		gestioneSaleFrame.toFront();
	}
	
	public void chiudiGestioneSale() {
		gestioneSaleFrame.setVisible(false);
		gestioneMeetingSegreteriaFrame.setEnabled(true);
		gestioneMeetingSegreteriaFrame.toFront();
	}
	
	private void aggiornaSaleFiltro() {
		gestioneMeetingSegreteriaFrame.aggiornaFiltroSale(this);
	}

	public ArrayList<Meeting> ottieniMeeting() throws SQLException{
		return meetDAO.getMeetings();
	}
	
	public ArrayList<PartecipazioneMeeting> ottieniPartecipanti(Meeting meeting) throws SQLException{
		return meetDAO.getInvitatiPartecipazioneMeeting(meeting.getIdMeeting());
	}

	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		ArrayList<SalaRiunione> sale = salaDAO.getSale();
		sale.add(0, null);
		return sale;
	}
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		ArrayList<String> piattaforme = meetDAO.getPiattaforme();
		piattaforme.add(0, null);
		return piattaforme;
	}
	
	public ArrayList<Meeting> filtraMeetingTelematici() throws SQLException{
		return meetDAO.getMeetingsByModalità("Telematico");
	}
	
	public ArrayList<Meeting> filtraMeetingFisici() throws SQLException{
		return meetDAO.getMeetingsByModalità("Fisico");
	}
	
	public ArrayList<Meeting> filtraMeetingPiattaforma(String piattaforma) throws SQLException{
		return meetDAO.getMeetingsByPiattaforma(piattaforma);
	}
	
	public ArrayList<Meeting> filtraMeetingSala(SalaRiunione sala) throws SQLException{
		return meetDAO.getMeetingsBySala(sala);
	}
	
	public void creaSala(SalaRiunione sala) throws SQLException {
		salaDAO.insertSala(sala);
		aggiornaSaleFiltro();
	}
	
	public void eliminaSala(String codSala) throws SQLException {
		SalaRiunione sala = salaDAO.getSalaByCod(codSala);
		salaDAO.deleteSala(sala);
		aggiornaSaleFiltro();
	}
	
	public void aggiornaSala(String codSala, int cap, String indirizzo, int piano) throws SQLException {
		SalaRiunione sala = salaDAO.getSalaByCod(codSala);
		sala.setCapienza(cap);
		sala.setIndirizzoSede(indirizzo);
		sala.setPiano(piano);
		salaDAO.updateSala(sala);
		aggiornaSaleFiltro();
	}
}
