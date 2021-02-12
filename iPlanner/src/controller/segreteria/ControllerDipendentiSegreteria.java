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
	
	public void tornaAiPlanner() {
		gestioneDipendentiFrame.setVisible(false);
		ControllerStart controller = new ControllerStart(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, salaDAO, ambitoDAO, true);
	}
	
	public ArrayList<String> ottieniProvince() throws SQLException{
		return luogoDAO.getProvince();
	}
	
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException{
		return luogoDAO.getLuoghiByProvincia(provincia);
	}
	
	public void creaDipendente(Dipendente dipendente) throws SQLException {
		if (dipDAO.insertDipendente(dipendente)) {
			for (Skill skill: dipendente.getSkills())
				if (!skillDAO.insertSkillDipendente(skill, dipendente)) {
					JOptionPane.showMessageDialog(null,
							"Errore inserimento delle skill nel database.",
							"Errore skill",
							JOptionPane.ERROR_MESSAGE);	
				}
			int yesNo = JOptionPane.showConfirmDialog(null,
					"Creazione riuscita.\nVuoi crearne un altro?",
					"Creazione riuscita",
					JOptionPane.YES_NO_OPTION);
			if (yesNo == 1)
				tornaAiPlanner();
			else {
				gestioneDipendentiFrame.dispose();
				gestioneDipendentiFrame=new GestioneDipendenti(this);
				gestioneDipendentiFrame.setVisible(true);
			}
		}
	}
	
	public void creaNuovaSkill(String nomeSkill) throws SQLException {
		Skill temp = new Skill(0, nomeSkill);
		skillDAO.insertSkill(temp);
	}
	
	public ArrayList<Skill> ottieniSkill() throws SQLException{
		return skillDAO.getSkills();
	}
	
	public ArrayList<Dipendente> ottieniDipendenti() throws SQLException {
		return dipDAO.getDipendenti();
	}
	
	public float ottieniMaxStipendio() {
		try {
			return dipDAO.getMaxStipendio();
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
	
	public void aggiornaDipendente(Dipendente dipendente) throws SQLException {
		dipDAO.updateDipendente(dipendente);
		for (Skill skill: dipendente.getSkills()) {
			try {
				skillDAO.insertSkillDipendente(skill, dipendente);
			}
			catch (SQLException e) {
				//violazione duplicati
				if (e.getSQLState().equals("23505"))
					continue;
				else
					JOptionPane.showMessageDialog(null,
							e.getMessage() + "\nContattare uno sviluppatore",
							"Errore #" + e.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void eliminaDipendente(Dipendente dipendente) throws SQLException {
		if (dipDAO.deleteDipendente(dipendente))
			return;
		else
			JOptionPane.showMessageDialog(null,
					"Impossibile trovare il dipendente da eliminare.",
					"Dipendente Non Trovato",
					JOptionPane.ERROR_MESSAGE);
	}
}
