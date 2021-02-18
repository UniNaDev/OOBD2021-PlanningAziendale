//Controller relativo alla finestra Gestione Partecipanti Progetto lato dipendente.
//Contiene tutti i metodi necessari per il corretto funzionamento della finestra.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Progetto;
import entita.Skill;
import gui.dipendente.InserisciPartecipantiProgetto;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SkillDAO;

public class ControllerPartecipantiProgetto {
	
	private InserisciPartecipantiProgetto inserisciPartecipantiProgettoFrame;
	
	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	
	private Dipendente dipendenteLogged = null;
	private Progetto progettoSelezionato = null;

	public ControllerPartecipantiProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO,Dipendente dipendenteLogged, SkillDAO skillDAO, Progetto progettoSelezionato) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO=skillDAO;
		this.dipendenteLogged = dipendenteLogged;
		this.progettoSelezionato = progettoSelezionato;
		
		inserisciPartecipantiProgettoFrame = new InserisciPartecipantiProgetto(this,progettoSelezionato);
		inserisciPartecipantiProgettoFrame.setVisible(true);
	}

	public ArrayList<Dipendente> ottieniDipendenti(Progetto progettoSelezionato) throws SQLException {
		return dipDAO.getDipendentiNonPartecipanti(progettoSelezionato);
	}
	
	public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		return skillDAO.getSkillsDipendente(cfDipendente);
	}
	
	public ArrayList<Dipendente> ottieniPartecipanti(int codiceProgetto) throws SQLException{
		return projDAO.getPartecipantiProgettoSenzaRuolo(codiceProgetto);
	}

	public boolean inserisciPartecipante(CollaborazioneProgetto collaborazioneProgetto){
		try {
			projDAO.insertPartecipanteProgetto(collaborazioneProgetto);
			return true;
		} catch (SQLException e) {
			
			String unicitàProjectManager="P0004";
			
			if(e.getSQLState().equals(unicitàProjectManager))
			JOptionPane.showMessageDialog(null, "Errore: Esiste già un project manager per questo progetto"
					+ "\nN.B. Se si vuole effettuare un cambio di project Manager,aggiornare "
					+ "\ni ruoli attraverso l'apposito pulsante aggiorna ruolo");
			return false;
		}
	}

	public boolean eliminaPartecipante(CollaborazioneProgetto collaborazioneProgetto){
		try {
			projDAO.deletePartecipanteProgetto(collaborazioneProgetto);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}
	
	public String [] ottieniRuoli() throws SQLException{
		String [] ruoli = new String [projDAO.getRuoliDipendenti().size()];
		for (int i = 0; i < projDAO.getRuoliDipendenti().size(); i++)
			ruoli[i] = projDAO.getRuoliDipendenti().get(i);
		return ruoli;
	}
	
	public boolean aggiornaPartecipante(CollaborazioneProgetto collaborazioneProgetto){
		try {
			projDAO.updatePartecipanteProgetto(collaborazioneProgetto);
			return true;
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}
	
	public float ottieniMaxStipendio() {
		try {
			return dipDAO.getMaxStipendio();
		} catch (SQLException e) {
			return 100000000f;
		}
	}

	public ArrayList<Dipendente> filtraDipendentiNonPartecipanti(String nomeCognomeEmail, int etàMinima, int etàMassima, float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima, Skill skill,Progetto progettoSelezionato,String tipologiaProgetto) throws SQLException {
		ArrayList<Dipendente> dipendentiFiltrati = dipDAO.getDipendentiNonPartecipantiFiltrati(nomeCognomeEmail, etàMinima, etàMassima, salarioMinimo, salarioMassimo, valutazioneMinima, valutazioneMassima,progettoSelezionato);
		if (skill != null)
			dipendentiFiltrati.retainAll(dipDAO.getDipendenteNonPartecipantiBySkill(progettoSelezionato,skill));
		
		if(tipologiaProgetto!=null)
		dipendentiFiltrati.retainAll(dipDAO.getDipendentiNonPartecipantiByTipologieProgetto(progettoSelezionato, tipologiaProgetto));
		
		return dipendentiFiltrati;
	}

	public ArrayList<Skill> ottieniSkill() throws SQLException{
		return skillDAO.getSkills();
	}

	public ArrayList<String> ottieniTipologieProgetto() throws SQLException {
		return projDAO.getTipologie();
	}
}
