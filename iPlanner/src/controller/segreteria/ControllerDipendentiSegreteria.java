//Controller che si occupa della finestra GestioneDipendenti della segreteria.
//Contiene i metodi principali usati dalla finestra e quelli di passaggio alle altre finestre del programma.

package controller.segreteria;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.ControllerStart;
import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;
import gui.segreteria.GestioneDipendenti;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerDipendentiSegreteria {
	private GestioneDipendenti gestioneDipendentiFrame;

	private LuogoNascitaDAO luogoDAO = null;
	private DipendenteDAO dipDAO = null;
	private ProgettoDAO projDAO = null;
	private MeetingDAO meetDAO = null;
	private SkillDAO skillDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	private AmbitoProgettoDAO ambitoDAO = null;
	
	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	
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
	
	public void apriGestioneDipendenti() {
	  gestioneDipendentiFrame= new GestioneDipendenti(this);
	  gestioneDipendentiFrame.setVisible(true);	  
	}
	
	public void tornaASegreteria() {
		gestioneDipendentiFrame.setVisible(false);
		ControllerAreaSegreteria controller = new ControllerAreaSegreteria(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO);
	}
	
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.ottieniProvince();
	}
	
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.ottieniComuni(provincia);
	}
	
	public boolean creaDipendente(Dipendente dipendente) throws SQLException {
		dipDAO.insertDipendente(dipendente);
			for (Skill skill: dipendente.getSkills())
				try{
					skillDAO.insertSkillDipendente(skill, dipendente);
				} catch(SQLException e) {
					JOptionPane.showMessageDialog(null,
							"Errore inserimento delle skill del dipendente nel database.",
							"Errore Inserimento Skill",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			return true;
		}
	
	public void creaNuovaSkill(String nomeSkill) throws SQLException {
		Skill temp = new Skill(0, nomeSkill);
		skillDAO.insertSkill(temp);
	}
	
	public ArrayList<Skill> ottieniSkill() throws SQLException{
		return skillDAO.ottieniSkill();
	}
	
	public ArrayList<Dipendente> ottieniDipendenti() throws SQLException {
		return dipDAO.getDipendenti();
	}
	
	public float ottieniMaxStipendio() {
		try {
			return dipDAO.ottieniMaxStipendio();
		} catch (SQLException e) {
			return 100000000f;
		}
	}
	
	public ArrayList<Dipendente> filtraDipendenti(String nomeCognomeEmail, int etàMinima, int etàMassima, float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima, Skill skill) throws SQLException {
		ArrayList<Dipendente> dipendentiFiltrati = dipDAO.getDipendentiFiltrati(nomeCognomeEmail, etàMinima, etàMassima, salarioMinimo, salarioMassimo, valutazioneMinima, valutazioneMassima);
		if (skill != null)
			dipendentiFiltrati.retainAll(dipDAO.getDipendenteBySkill(skill));
		return dipendentiFiltrati;
	}
	
	public boolean aggiornaDipendente(Dipendente dipendente) throws SQLException {
		dipDAO.aggiornaDipendente(dipendente);
		for (Skill skill: dipendente.getSkills()) {
			try {
				skillDAO.insertSkillDipendente(skill, dipendente);
			}
			catch (SQLException e) {
				if (e.getSQLState().equals(VIOLAZIONE_PKEY_UNIQUE))
					continue;
				else {
					JOptionPane.showMessageDialog(null,
							e.getMessage() + "\nContattare uno sviluppatore",
							"Errore #" + e.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}
	
	public void eliminaDipendente(Dipendente dipendente) throws SQLException {
		dipDAO.eliminaDipendente(dipendente);
	}
}
