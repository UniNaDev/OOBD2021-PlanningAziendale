package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import DBManager.ManagerConnessioneDB;
import Entità.Dipendente;
import Entità.Meeting;
import ImplementazioneDAO.DipendenteDAOPSQL;
import ImplementazioneDAO.MeetingDAOPSQL;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.MeetingDAO;

public class MeetingDebug {

	public static void main(String[] args) {
		
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		MeetingDAO meetDAO = null;
		DipendenteDAO dipDAO = null;
		
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		try 
		{
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			
			meetDAO = new MeetingDAOPSQL(connection);
			dipDAO = new DipendenteDAOPSQL(connection);
			
			//Ottieni i meeting a cui è invitato un dipendente
			Dipendente dipendente = dipDAO.getDipendenteByCF("SPSNDR02L01L259V");
			System.out.println("\nMeeting a cui è stato invitato " + dipendente);
			meetings = meetDAO.getMeetingsByInvitato(dipendente);
			for (Meeting m : meetings)
				System.out.println(m);
			
			//Ottieni i meeting con data successiva a 21/10/2020
			LocalDate day = new LocalDate(2020,10,21);
			System.out.println("\nTutti i meeting dal " + day);
			meetings = meetDAO.getMeetingsByData(day);
			for (Meeting m : meetings)
				System.out.println(m);
			
			//Ottieni meeting organizzati da un dipendente
			System.out.println("\nMeeting organizzati da " + dipendente);
			meetings = meetDAO.getMeetingsOrganizzati(dipendente);
			for (Meeting m : meetings)
				System.out.println(m);
			
			//Ottieni invitati ad un meeting
			System.out.println("\nInvitati:");
			dipendenti = meetDAO.getInvitati(meetings.get(0));
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Inserisci un nuovo meeting
			LocalDate inizioM = new LocalDate(2020,9,16);
			LocalDate fineM = new LocalDate(2020,9,17);
			LocalTime oraInizio = new LocalTime(9,0,0);
			LocalTime oraFine = new LocalTime(11,0,0);
			Meeting meeting = new Meeting(inizioM,fineM,oraInizio,oraFine,"Telematico","Microsoft Teams",dipendente);
//			if (meetDAO.addMeeting(meeting))
//				System.out.println("\n" + meeting + " inserito");
			
			//Rimuovi un meeting esistente
//			meeting.setIdMeeting(12);
//			if (meetDAO.removeMeeting(meeting))
//				System.out.println("Rimosso");
			
		}
		catch(SQLException e) 
		{
			System.out.println("Errore SQL. " + e.getMessage());
		}

	}

}