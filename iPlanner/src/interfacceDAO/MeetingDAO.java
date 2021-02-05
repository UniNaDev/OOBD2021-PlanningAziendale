//Interfaccia per Meeting con il DBMS. Contiene tutte le operazioni comuni con il DBMS in merito ai meeting aziendali.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;

public interface MeetingDAO {
	
	//METODI
//	
//	public ArrayList<Meeting> getMeetingsByData(LocalDate data) throws SQLException; 	//metodo che restituisce la lista di meeting che cominciano in una data specifica
//	public ArrayList<Meeting> getMeetingsOrganizzati(Dipendente org) throws SQLException;	//metodo che restituisce la lista di meeting organizzati da un dato dipendente
	public ArrayList<Meeting> getMeetingsByInvitato(Dipendente dip) throws SQLException;	//metodo che restituisce la lista di meeting a cui un dipendente è invitato
	public ArrayList<Dipendente> getInvitati(int idMeeting) throws SQLException;	//metodo che restituisce gli invitati a un meeting
	public boolean addMeeting(Meeting meeting,String nomeProgettoDiscusso) throws SQLException;	//metodo che aggiunge un meeting al DB
	public boolean removeMeeting(int idMeeting) throws SQLException;	//metodo che rimuove un meeting dal DB
	public boolean updateMeeting(Meeting meeting, String nomeProgettoSelezionato) throws SQLException;	//metodo che aggiorna le informazioni di un meeting nel DB
//	public ArrayList<Meeting> getMeetingsBySala(SalaRiunione sala) throws SQLException;	//metodo che restituisce una lista di meeting che avvengono in una specifica sala
//	public ArrayList<Meeting> getMeetingsByPiattaforma(String platf) throws SQLException;//metodo che restituisce i meeting organizzati su una specifica piattaforma telematica
	public String getProgettoRelativo(int idMeeting) throws SQLException; //metodo che restituisce il progetto discusso in quel meeting
	public ArrayList<String> getPiattaforme() throws SQLException;	//metodo che restituisce tutte le piattaforme disponibili
	public boolean addOrganizzatore(String CF) throws SQLException; //metodo che aggiunge l'organizzatore tra gli invitati
	public boolean aggiungiPartecipanteMeeting(String cf, int idMeeting) throws SQLException;
	public boolean eliminaPartecipanteMeeting(String cf,int idMeeting)throws SQLException;
	public boolean addMeetingCompleto(Meeting meetingInserito, Progetto progetto) throws SQLException;
}
