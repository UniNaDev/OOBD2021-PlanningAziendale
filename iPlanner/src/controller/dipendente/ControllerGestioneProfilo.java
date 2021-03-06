//Controller relativo alle finestre di Home e Mio Account del programma.
//Contiene tutti i metodi per far funzionare correttamente le finestre e per viaggiare tra esse.

package controller.dipendente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import entita.Meeting;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.LuogoNascita;
import gui.dipendente.Home;
import gui.dipendente.MioAccount;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;
import interfacceDAO.SkillDAO;

public class ControllerGestioneProfilo {
    private Home homeFrame;
    private MioAccount accountFrame;

    private LuogoNascitaDAO luogoDAO = null;
    private DipendenteDAO dipDAO = null;
    private ProgettoDAO projDAO = null;
    private MeetingDAO meetDAO = null;
    private SkillDAO skillDAO = null;
    private SalaRiunioneDAO salaDAO = null;
    private AmbitoProgettoDAO ambitoDAO = null;

    private Dipendente dipendenteLogged = null;

    public ControllerGestioneProfilo(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO,
	    MeetingDAO meetDAO, SkillDAO skillDAO, SalaRiunioneDAO salaDAO, AmbitoProgettoDAO ambitoDAO, Dipendente dipendenteLogged) {
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.skillDAO = skillDAO;
		this.salaDAO = salaDAO;
		this.ambitoDAO = ambitoDAO;
	
		this.dipendenteLogged = dipendenteLogged;
	
		homeFrame = new Home(this, dipendenteLogged);
		homeFrame.setVisible(true);

    }

    public void apriGUIMioAccount() {
    	homeFrame.setVisible(false);
		accountFrame = new MioAccount(this, dipendenteLogged);
		accountFrame.setVisible(true);
    }

    public void chiudiGUIMioAccount() {
    	accountFrame.setVisible(false);
    	homeFrame.setVisible(true);
    }

    public void logout() {
    	System.exit(0);
    }

    public void apriGUIMieiProgetti() {
    	homeFrame.setVisible(false);
    	ControllerProgetto controller = new ControllerProgetto(luogoDAO, dipDAO, projDAO, meetDAO, ambitoDAO, skillDAO, salaDAO, dipendenteLogged);
    }

    public void apriGUIMieiMeeting() {
    	homeFrame.setVisible(false);
    	ControllerMeeting controller = new ControllerMeeting(luogoDAO, dipDAO, projDAO, meetDAO, skillDAO, ambitoDAO, salaDAO, dipendenteLogged);
    }

    public void tornaAHome() {
		homeFrame.dispose();
		homeFrame = new Home(this, dipendenteLogged);
		homeFrame.setVisible(true);
    }

    public ArrayList<CollaborazioneProgetto> ottieniProgettiDipendente() throws SQLException {
    	return projDAO.ottieniProgettiDipendente(dipendenteLogged);
    }

    public ArrayList<Meeting> ottieniMeetingDipendente() throws SQLException {
		ArrayList<Meeting> meeting = meetDAO.ottieniMeetingDipendente(dipendenteLogged);
		meeting.sort(new Comparator<Meeting>() {
			public int compare(Meeting meet1, Meeting meet2) {
				if (meet1.isPassato() && !meet2.isPassato())
					return 1;
				else if (!meet1.isPassato() && meet2.isPassato())
					return -1;
				else if (meet1.isPassato() && meet2.isPassato()) {
					if (meet1.getDataFine().isBefore(meet2.getDataFine()))
						return 1;
					else if (meet1.getDataFine().isAfter(meet2.getDataFine()))
						return -1;
					else {
						if (meet1.getOraFine().isBefore(meet2.getOraFine()))
							return -1;
						else if (meet1.getOraFine().isAfter(meet2.getOraFine()))
							return 1;
						else
							return 0;
					}
				} else if (!meet1.isPassato() && !meet2.isPassato()) {
					if (meet1.getDataInizio().isBefore(meet2.getDataInizio()))
						return -1;
					else if (meet1.getDataInizio().isAfter(meet2.getDataInizio()))
						return 1;
					else {
						if (meet1.getOraInizio().isBefore(meet2.getOraInizio()))
							return -1;
						else if (meet1.getOraInizio().isAfter(meet2.getOraInizio()))
							return 1;
						else
							return 0;
					}
				}
				return 0;
			}
		});
		return meeting;
	}

    public void aggiornaDipendente(Dipendente dipendenteModificato) throws SQLException {
    	dipDAO.aggiornaDipendente(dipendenteModificato);
    }

    public ArrayList<String> ottieniProvince() throws SQLException {
    	return luogoDAO.ottieniProvince();
    }

    public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException {
    	return luogoDAO.ottieniComuni(provincia);
    }
}
