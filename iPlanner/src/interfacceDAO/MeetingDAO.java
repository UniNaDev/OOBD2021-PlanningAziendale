//Interfaccia per Meeting con il DBMS. Contiene tutte le operazioni comuni con il DBMS in merito ai meeting aziendali.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Progetto;
import entita.SalaRiunione;

public interface MeetingDAO {
	
	//METODI
//	
	public ArrayList<Meeting> getMeetings() throws SQLException; 	//metodo che restituisce la lista di meeting
	public ArrayList<Meeting> getMeetingsByInvitato(Dipendente dip) throws SQLException;	//metodo che restituisce la lista di meeting a cui un dipendente è invitato
	public ArrayList<Dipendente> getInvitati(int idMeeting) throws SQLException;	//metodo che restituisce gli invitati a un meeting
	public boolean addMeeting(Meeting meeting,String nomeProgettoDiscusso) throws SQLException;	//metodo che aggiunge un meeting al DB
	public boolean removeMeeting(int idMeeting) throws SQLException;	//metodo che rimuove un meeting dal DB
	public boolean updateMeeting(Meeting meeting, Progetto progettoSelezionato) throws SQLException;	//metodo che aggiorna le informazioni di un meeting nel DB
	public ArrayList<Meeting> getMeetingsBySala(SalaRiunione sala) throws SQLException;	//metodo che restituisce una lista di meeting che avvengono in una specifica sala
	public ArrayList<Meeting> getMeetingsByPiattaforma(String platf) throws SQLException;//metodo che restituisce i meeting organizzati su una specifica piattaforma telematica
	public ArrayList<String> getPiattaforme() throws SQLException;	//metodo che restituisce tutte le piattaforme disponibili
	public boolean addOrganizzatore(String CF) throws SQLException; //metodo che aggiunge l'organizzatore tra gli invitati
	
	public boolean addMeetingCompleto(Meeting meetingInserito, Progetto progetto) throws SQLException;

	public ArrayList<PartecipazioneMeeting> getInvitatiPartecipazioneMeeting(int idMeeting) throws SQLException;
	public ArrayList<Meeting> getMeetingsByModalità(String modalità) throws SQLException;

	public boolean aggiungiPartecipanteMeeting(PartecipazioneMeeting partecipazione) throws SQLException; //Aggiunge un invitato al meeting
	public boolean aggiornaPresenza(PartecipazioneMeeting partecipazioneMeeting)throws SQLException; //Aggiorna la presenza ad un meeting
	public boolean eliminaPartecipanteMeeting(PartecipazioneMeeting partecipazioneMeeting)throws SQLException; //Elimina un invitato dal meeting
	

}
