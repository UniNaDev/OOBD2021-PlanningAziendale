package controller.segreteria;

import gui.segreteria.AreaSegreteria;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerAreaSegreteria {
	private AreaSegreteria areaSegreteriaFrame;
	
    private LuogoNascitaDAO luogoDAO = null;
    private DipendenteDAO dipDAO = null;
    private ProgettoDAO projDAO = null;
    private MeetingDAO meetDAO = null;
    private SkillDAO skillDAO = null;
    private SalaRiunioneDAO salaDAO = null;
    private AmbitoProgettoDAO ambitoDAO = null;

    public ControllerAreaSegreteria(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO,SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
		
		areaSegreteriaFrame = new AreaSegreteria(this);
		areaSegreteriaFrame.setVisible(true);
    }
    
	public void apriGUIGestioneDipendentiSegreteria() {
		areaSegreteriaFrame.setVisible(false);
		ControllerDipendentiSegreteria controller = new ControllerDipendentiSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
	}
	
	public void apriGUIGestioneProgettiSegreteria() {
		areaSegreteriaFrame.setVisible(false);
		ControllerProgettiSegreteria controller = new ControllerProgettiSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
	}
	
	public void apriGUIGestioneMeetingSegreteria() {
		areaSegreteriaFrame.setVisible(false);
		ControllerMeetingSegreteria controller = new ControllerMeetingSegreteria(luogoDAO,dipDAO,projDAO,meetDAO,skillDAO,salaDAO,ambitoDAO);
	}
}
