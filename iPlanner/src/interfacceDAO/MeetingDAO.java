//Interfaccia per Meeting con il DBMS. Contiene tutte le operazioni comuni con il DBMS in merito ai meeting aziendali.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Progetto;
import entita.SalaRiunione;

public interface MeetingDAO {
	public ArrayList<Meeting> ottieniMeeting() throws SQLException;
	
	public ArrayList<Meeting> ottieniMeetingDipendente(Dipendente dip) throws SQLException;
	
	public boolean creaMeeting(Meeting meeting) throws SQLException;
	
	public boolean rimuoviMeeting(int idMeeting) throws SQLException;
	
	public boolean aggiornaMeeting(Meeting meeting) throws SQLException;
	
	public ArrayList<Meeting> filtraMeetingSala(SalaRiunione sala) throws SQLException;
	
	public ArrayList<Meeting> filtraMeetingPiattaforma(String piattaforma) throws SQLException;
	
	public ArrayList<String> ottieniPiattaforme() throws SQLException;
	
	public boolean inserisciOrganizzatore(String CF) throws SQLException;
	
	public ArrayList<PartecipazioneMeeting> ottieniInvitatiMeeting(int idMeeting) throws SQLException;
	
	public ArrayList<Meeting> filtraMeetingSegreteriaPerModalità(String modalità) throws SQLException;

	public boolean inserisciInvitatoMeeting(PartecipazioneMeeting partecipazione) throws SQLException;
	
	public boolean aggiornaPresenzaInvitato(PartecipazioneMeeting partecipazioneMeeting)throws SQLException;
	
	public boolean eliminaInvitato(PartecipazioneMeeting partecipazioneMeeting)throws SQLException;

	public ArrayList<Meeting> filtraMeetingDipendentePerModalità(String string, Dipendente dipendente) throws SQLException;

	public ArrayList<Meeting> filtraMeetingDipendentiSala(SalaRiunione sala, Dipendente dipendente) throws SQLException;

	public ArrayList<Meeting> filtraMeetingDipendentePiattaforma(String piattaforma, Dipendente dipendente) throws SQLException;

	public int ottieniIdUltimoMeetingInserito() throws SQLException;
	
	public String ottieniCFOrganizzatore(Meeting meeting) throws SQLException;
}
