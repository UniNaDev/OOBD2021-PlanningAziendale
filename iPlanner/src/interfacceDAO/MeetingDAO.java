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
	public ArrayList<Meeting> getMeetings() throws SQLException;
	
	public ArrayList<Meeting> getMeetingsByInvitato(Dipendente dip) throws SQLException;
	
	public ArrayList<Dipendente> getInvitati(int idMeeting) throws SQLException;
	
	public boolean insertMeeting(Meeting meeting) throws SQLException;
	
	public boolean deleteMeeting(int idMeeting) throws SQLException;
	
	public boolean updateMeeting(Meeting meeting) throws SQLException;
	
	public ArrayList<Meeting> getMeetingsBySala(SalaRiunione sala) throws SQLException;
	
	public ArrayList<Meeting> getMeetingsByPiattaforma(String piattaforma) throws SQLException;
	
	public ArrayList<String> getPiattaforme() throws SQLException;
	
	public boolean insertOrganizzatore(String CF) throws SQLException;
	
	public ArrayList<PartecipazioneMeeting> getInvitatiPartecipazioneMeeting(int idMeeting) throws SQLException;
	
	public ArrayList<Meeting> getMeetingsByModalità(String modalità) throws SQLException;

	public boolean insertPartecipanteMeeting(PartecipazioneMeeting partecipazione) throws SQLException;
	
	public boolean updatePresenzaPartecipante(PartecipazioneMeeting partecipazioneMeeting)throws SQLException;
	
	public boolean deletePartecipanteMeeting(PartecipazioneMeeting partecipazioneMeeting)throws SQLException;

	public ArrayList<Meeting> getMeetingDipendenteByModalità(String string, Dipendente dipendente) throws SQLException;

	public ArrayList<Meeting> getMeetingsDipendenteBySala(SalaRiunione sala, Dipendente dipendente) throws SQLException;

	public ArrayList<Meeting> getMeetingsDipendenteByPiattaforma(String piattaforma, Dipendente dipendente) throws SQLException;

	public int getUltimoIDMeeting() throws SQLException;
	
	public String getCFOrganizzatore(Meeting meeting) throws SQLException;
}
