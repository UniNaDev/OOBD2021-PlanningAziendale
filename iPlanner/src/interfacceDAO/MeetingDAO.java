//Interfaccia per Meeting con il DBMS. Contiene tutte le operazioni comuni con il DBMS in merito ai meeting aziendali.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.Meeting;
import entita.SalaRiunione;

public interface MeetingDAO {
	
	//METODI
	
	public ArrayList<Meeting> getMeetingsByData(LocalDate data) throws SQLException; 	//metodo che restituisce la lista di meeting che cominciano in una data specifica
	public ArrayList<Meeting> getMeetingsOrganizzati(Dipendente org) throws SQLException;	//metodo che restituisce la lista di meeting organizzati da un dato dipendente
	public ArrayList<Meeting> getMeetingsByInvitato(Dipendente dip) throws SQLException;	//metodo che restituisce la lista di meeting a cui un dipendente è invitato
	public ArrayList<Dipendente> getInvitati(int idMeeting) throws SQLException;	//metodo che restituisce gli invitati a un meeting
	public boolean addMeeting(Meeting meeting) throws SQLException;	//metodo che aggiunge un meeting al DB
	public boolean removeMeeting(int idMeeting) throws SQLException;	//metodo che rimuove un meeting dal DB
	public boolean updateMeeting(Meeting meeting) throws SQLException;	//metodo che aggiorna le informazioni di un meeting nel DB
	public ArrayList<Meeting> getMeetingsBySala(SalaRiunione sala) throws SQLException;	//metodo che restituisce una lista di meeting che avvengono in una specifica sala
	public ArrayList<Meeting> getMeetingsByPiattaforma(String platf) throws SQLException;//metodo che restituisce i meeting organizzati su una specifica piattaforma telematica
	public String getProgettoRelativo(int idMeeting)throws SQLException; //Metodo che restituisce il progetto discusso in quel meeting
	
	public ArrayList<String> getPiattaforme() throws SQLException;	//metodo che restituisce tutte le piattaforme disponibili
}
