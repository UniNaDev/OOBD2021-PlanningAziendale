//Controller relativo alle finestre Miei Meeting e Gestione Meeting lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento delle finestre.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import gui.dipendente.GestioneMeetingDipendente;
import gui.dipendente.MieiMeeting;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;


public class ControllerMeeting {
	private MieiMeeting mieiMeetingFrame;
	private GestioneMeetingDipendente gestioneMeetingFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	
	private Dipendente dipendenteLogged = null;
	
	private final String VIOLAZIONE_SALA_OCCUPATA = "P0001";
	private final String VIOLAZIONE_CAPIENZA_SALA = "P0002";
	private final String VIOLAZIONE_ONNIPRESENZA_DIPENDENTE = "P0003";

	public ControllerMeeting(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.salaDAO = salaDAO;
		this.skillDAO=skillDAO;
		this.dipendenteLogged = dipendenteLogged;
		
		mieiMeetingFrame=new MieiMeeting(this);
		mieiMeetingFrame.setVisible(true);
	}

	public void apriGestioneMeeting() {
		gestioneMeetingFrame= new GestioneMeetingDipendente(this);
		gestioneMeetingFrame.setVisible(true);
		mieiMeetingFrame.setVisible(false);
	}
	
	public void apriInserisciPartecipantiMeeting(Meeting meetingSelezionato) {
		ControllerPartecipantiMeeting controller = new ControllerPartecipantiMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, dipendenteLogged, meetingSelezionato);
	}
	
	public ArrayList<Meeting> ottieniMeeting() throws SQLException {
		return meetDAO.getMeetingsByInvitato(dipendenteLogged);
	}
	
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.getProgettiByDipendente(dipendenteLogged);
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			progetti.add(collaborazione.getProgetto());
		return progetti;
	}
	
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException{
		return salaDAO.getSale();
	}
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException{
		return meetDAO.getPiattaforme();
	}
	
	public boolean aggiornaMeeting(Meeting meeting) {
		try {
			meetDAO.updateMeeting(meeting);
		} catch (SQLException e) {
			//TODO: verificare altre possibili eccezioni
			switch(e.getSQLState()) {
			case VIOLAZIONE_ONNIPRESENZA_DIPENDENTE:
				JOptionPane.showMessageDialog(null,
						"Ci sono problemi di accavallamento con il meeting che si sta tentando di modificare."
								+ "\nControllare che i dipendenti siano liberi per le date e orari inseriti.",
								"Errore Accavallamento Meeting",
								JOptionPane.ERROR_MESSAGE);
				break;
			case VIOLAZIONE_CAPIENZA_SALA:
				JOptionPane.showMessageDialog(null,
						"I partecipanti al meeting che si vuole modificare sono maggiori "
								+ "\nrispetto alla capienza massima della sala.\n"
								+ "Controllare che non ci siano più di " + meeting.getSala().getCapienza() + " partecipanti.",
								"Errore Capienza Sala",
								JOptionPane.ERROR_MESSAGE);
				break;
			case VIOLAZIONE_SALA_OCCUPATA:
				JOptionPane.showMessageDialog(null,
						"Errore: Ci sono problemi di accavallamento con il meeting che si sta tentando di modificare."
								+ "\nControllare che la sala inserita non sia già occupata per le date e gli orari inseriti.",
								"Errore Sala Occupata",
								JOptionPane.ERROR_MESSAGE);
				break;
				default:
					JOptionPane.showMessageDialog(null, e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
							"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}
		return true;
	}
	
	public boolean creaMeeting(Meeting nuovoMeeting){
		try {	
			meetDAO.insertMeeting(nuovoMeeting);
			try {
				meetDAO.insertOrganizzatore(dipendenteLogged.getCf());
			} catch(SQLException e) {
				//TODO: verificare altre possibli eccezioni
				switch(e.getSQLState()) {
				case VIOLAZIONE_ONNIPRESENZA_DIPENDENTE:
					JOptionPane.showMessageDialog(null,
							"\nImpossibile creare il meeting perchè si accavalla con altri meeting."
							+ "\nCambiare data e orario oppure modificare prima il meeting che si accavalla.",
							"Errore Accavallamento Meeting",
							JOptionPane.ERROR_MESSAGE);
					int idMeeting = idUltimoMeetingInserito();
					rimuoviMeeting(idMeeting);
					break;
					
				default:
					JOptionPane.showMessageDialog(null, e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
							"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
				}
				return false;
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Errore: Ci sono problemi di accavallamento con il meeting che si sta tentando di inserire."
							+ "\nControllare che la sala inserita non sia già occupata per le date e gli orari inseriti.",
							"Errore Sala Occupata",
							JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
		return true;
	}

	public boolean rimuoviMeeting(int idMeeting) {
		try {
			meetDAO.deleteMeeting(idMeeting);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public ArrayList<Meeting> filtraMeetingTelematiciDipendenti() throws SQLException{
		return meetDAO.getMeetingDipendenteByModalità("Telematico",dipendenteLogged);
	}
	
	public ArrayList<Meeting> filtraMeetingFisiciDipendenti() throws SQLException {
		return meetDAO.getMeetingDipendenteByModalità("Fisico", dipendenteLogged);
	}
	
	public Progetto ottieniProgettoInserito(Progetto progetto) throws SQLException {
		return projDAO.getProgettoByCod(progetto.getIdProgettto());
	}

	public boolean isOrganizzatore(Meeting meeting) throws SQLException {
		String cfOrganizzatore = ottieniCFOrganizzatore(meeting);
		if (dipendenteLogged.getCf().equals(cfOrganizzatore))
			return true;
		return false;
	}
	
	private String ottieniCFOrganizzatore(Meeting meeting) throws SQLException {
		return meetDAO.getCFOrganizzatore(meeting);
	}

	public ArrayList<Meeting> filtraMeetingDipendentiSala(SalaRiunione sala) throws SQLException {
		
		return meetDAO.getMeetingsDipendenteBySala(sala,dipendenteLogged);
	}

	public ArrayList<Meeting> filtraMeetingDipendentePiattaforma(String piattaforma) throws SQLException {
		
		return meetDAO.getMeetingsDipendenteByPiattaforma(piattaforma,dipendenteLogged);
	}

	public int idUltimoMeetingInserito() throws SQLException {
		return meetDAO.getUltimoIDMeeting();
	}


}
