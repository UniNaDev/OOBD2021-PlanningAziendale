package implementazioniDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Meeting;
import entita.SalaRiunione;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;

public class MeetingDAOPSQL implements MeetingDAO {
	
	//ATTRIBUTI
	//----------------------------------------
	private Connection connection;
	private PreparedStatement getMeetingsByDataPS,getMeetingsOrganizzatiPS,getMeetingsByInvitatoPS,getInvitatiPS,addMeetingPS,removeMeetingPS,updateMeetingPS,getMeetingsBySalaPS,getMeetingsByPiattaformaPS,getPiattaformePS,getProgettoDiscussoPS,addPresenzaOrganizzatorePS,getIdProgettoDiscussoPS,lastIdMeetingPS,removePresenzeMeetingEliminato;

	//METODI
	//----------------------------------------
	
	//Costruttore
	public MeetingDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione dal ManagerConnessioneDB quando viene chiamato
		
		//inizializza i prepared statement
		getMeetingsByDataPS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.DataInizio >= ? ORDER BY m.DataInizio,m.OrarioInizio");	//?=DataInizio minima
		getMeetingsOrganizzatiPS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.IDMeeting IN (SELECT p.IDMeeting FROM Presenza AS p WHERE p.CF = ? AND p.organizzatore = TRUE)");	//?=codice fiscale dell'organizzatore
		getInvitatiPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF IN (SELECT p.CF FROM Presenza AS p WHERE p.IDMeeting = ?)");	//?=ID del meeting di cui si vogliono gli invitati
		getMeetingsByInvitatoPS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.IDMeeting IN (SELECT p.IDMeeting FROM Presenza AS p WHERE p.CF = ?) ORDER BY m.DataInizio, m.OrarioInizio");	//?=codice fiscale del dipendente di cui si vogliono i meeting a cui è invitato
		addMeetingPS = connection.prepareStatement("INSERT INTO Meeting (DataInizio, DataFine, OrarioInizio, OrarioFine, Modalità, Piattaforma, CodSala,CodProgetto) VALUES (?,?,?,?,?,?,?,?)");
		removeMeetingPS = connection.prepareStatement("DELETE FROM Meeting AS m WHERE m.IDMeeting = ?");	//?=ID del meeting da eliminare
		updateMeetingPS = connection.prepareStatement("UPDATE Meeting SET DataInizio = ?, DataFine = ?, OrarioInizio = ?, OrarioFine = ?, Modalità = ?, Piattaforma = ?, CodSala = ? WHERE IDMeeting = ?");
		getMeetingsBySalaPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.CodSala = ?");	//?=Codice della sala di cui si vogliono i meeting
		getMeetingsByPiattaformaPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.Piattaforma = ?");	//?=Piattaforma di cui si cercano i meeting
		getPiattaformePS = connection.prepareStatement("SELECT unnest(enum_range(NULL::piattaforma))::text AS piattaforma");
		getProgettoDiscussoPS = connection.prepareStatement("SELECT nomeprogetto from Progetto NATURAL JOIN Meeting WHERE idMeeting= ?");
		getIdProgettoDiscussoPS= connection.prepareStatement("SELECT codProgetto FROM Progetto WHERE nomeProgetto ILIKE ?");
		addPresenzaOrganizzatorePS= connection.prepareStatement("INSERT INTO Presenza(CF,IdMeeting,Presente,Organizzatore) VALUES(?,?,true,true)");
		lastIdMeetingPS=connection.prepareStatement("SELECT idMeeting FROM Meeting ORDER BY idMeeting DESC LIMIT 1");
		removePresenzeMeetingEliminato=connection.prepareStatement("DELETE FROM Presenza WHERE IdMeeting =?");
	}
	
	//Metodo getMeetingsByData.
	/*Metodo che restituisce una lista di meeting (temp) che hanno una data di inizio
	*successiva a quella richiesta nell'argomento.*/
	@Override
	public ArrayList<Meeting> getMeetingsByData(LocalDate data) throws SQLException {
		ArrayList<Meeting> temp = new ArrayList<Meeting>();	//inizializza la lista da restituire
		getMeetingsByDataPS.setDate(1, new Date(data.toDateTimeAtStartOfDay().getMillis()));	//inserisce la data nella query
		
		ResultSet risultato = getMeetingsByDataPS.executeQuery();	//esegue la query e ottiene il Resultset
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.getProgettoByCod(risultato.getInt("CodProgetto")));
			temp.add(meetingTemp);
		}
		risultato.close();
		
		return temp;
	}

	//Metodo getMeetingsOrganizzati.
	/*Metodo che restituisce una lista (temp) di meeting che sono organizzati
	*da uno specifico dipendente.*/
	@Override
	public ArrayList<Meeting> getMeetingsOrganizzati(Dipendente org) throws SQLException {
		ArrayList<Meeting> temp = new ArrayList<Meeting>();	//inizializza la lista da restituire
		getMeetingsOrganizzatiPS.setString(1, org.getCf()); //inserisce il codice fiscale dell'organizzatore nella query
		
		ResultSet risultato = getMeetingsOrganizzatiPS.executeQuery();	//esegue la query e ottiene il Resultset
	
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.getProgettoByCod(risultato.getInt("CodProgetto")));
			temp.add(meetingTemp);
		}
		risultato.close();
		
		return temp;
	}

	//Meotdo getMeetingsByInvitato.
	/*Metodo che restituisce una lista di meeting (temp) a cui un dipendente è invitato.*/
	@Override
	public ArrayList<Meeting> getMeetingsByInvitato(Dipendente dip) throws SQLException {
		ArrayList<Meeting> temp = new ArrayList<Meeting>();	//inizializza la lista da restituire dopo
		getMeetingsByInvitatoPS.setString(1,dip.getCf());	//inserisce il codice fiscale del dipendente nella query
		
		ResultSet risultato = getMeetingsByInvitatoPS.executeQuery();	//esegue la query e ottiene il Resultset
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);
		
		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.getProgettoByCod(risultato.getInt("CodProgetto")));
			temp.add(meetingTemp);
		}
		risultato.close();
		
		return temp;
	}

	//Metodo getInvitati.
	/*Metodo che restituisce una lista di dipendenti (temp) invitati a un meeting specifico.*/
	@Override
	public ArrayList<Dipendente> getInvitati(int idMeeting) throws SQLException {
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		getInvitatiPS.setInt(1, idMeeting); 	//inserisce l'id del meeting di cui si vogliono gli invitati nella query
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(connection);	//inizializza il DAO per luogonascita
		DipendenteDAO dipDAO = new DipendenteDAOPSQL(connection);
		
		ResultSet risultato = getInvitatiPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("CF"), 
					risultato.getString("Nome"),
					risultato.getString("Cognome"),
					risultato.getString("Sesso").charAt(0),
					new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp,
					risultato.getString("Indirizzo"),
					risultato.getString("Email"),
					risultato.getString("TelefonoCasa"),
					risultato.getString("Cellulare"),
					risultato.getFloat("Salario"),
					risultato.getString("Password"),
					dipDAO.getValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
			
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();
		
		return temp;
	}

	//Metodo addMeeting.
	/*Metodo che inserisce un nuovo meeting  nel DB.*/
	@Override
	public boolean addMeeting(Meeting meeting,String nomeProgettoDiscusso) throws SQLException {
		addMeetingPS.setDate(1, new Date(meeting.getDataInizio().toDateTimeAtStartOfDay().getMillis()));	//data inizio
		addMeetingPS.setDate(2, new Date(meeting.getDataFine().toDateTimeAtStartOfDay().getMillis()));	//data fine
		addMeetingPS.setTime(3, new Time(meeting.getOraInizio().getHourOfDay(),meeting.getOraInizio().getMinuteOfHour(),meeting.getOraInizio().getSecondOfMinute()));	//ora inizio
		addMeetingPS.setTime(4, new Time(meeting.getOraFine().getHourOfDay(),meeting.getOraFine().getMinuteOfHour(),meeting.getOraFine().getSecondOfMinute()));	//ora fine
		addMeetingPS.setObject(5, meeting.getModalita(),Types.OTHER);	//modalità
		addMeetingPS.setObject(6, meeting.getPiattaforma(), Types.OTHER);	//piattaforma
		if (meeting.getSala() != null)
			addMeetingPS.setString(7, meeting.getSala().getCodSala());	//codice sala
		else
			addMeetingPS.setNull(7, Types.CHAR);	//codice sala null
		
		getIdProgettoDiscussoPS.setString(1,nomeProgettoDiscusso);
		ResultSet risultato=getIdProgettoDiscussoPS.executeQuery();
		risultato.next();
		
		addMeetingPS.setInt(8, risultato.getInt("CodProgetto"));	//codcie progetto
		
		int record = addMeetingPS.executeUpdate();	//esegue l'insert e salva il numero di record aggiunti (1=inserito,0=non inserito)
		
		if (record == 1)
			return true;
		else
			
			return false;
	}

	//Metodo removeMeeting.
	/*Metodo che rimuove un meeting specifico dal DB usando il suo ID.*/
	@Override
	public boolean removeMeeting(int idMeeting) throws SQLException {
		removeMeetingPS.setInt(1, idMeeting); 	//inserisce l'id nella delete
		
		int record = removeMeetingPS.executeUpdate();	//esegue la delete e salva il numero di record eliminati (1=eliminato,0=non eliminato)
		
		removePresenzeMeetingEliminato.setInt(1, idMeeting);
		removePresenzeMeetingEliminato.executeUpdate();
		
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che aggiorna un meeting.
	@Override
	public boolean updateMeeting(Meeting meeting) throws SQLException {
		updateMeetingPS.setDate(1, new Date(meeting.getDataInizio().toDateTimeAtStartOfDay().getMillis()));//data inizio
		updateMeetingPS.setDate(2, new Date(meeting.getDataFine().toDateTimeAtStartOfDay().getMillis()));//data fine
		updateMeetingPS.setTime(3, new Time(meeting.getOraInizio().getHourOfDay(),meeting.getOraInizio().getMinuteOfHour(),meeting.getOraInizio().getSecondOfMinute()));//ora inizio
		updateMeetingPS.setTime(4, new Time(meeting.getOraFine().getHourOfDay(),meeting.getOraFine().getMinuteOfHour(),meeting.getOraFine().getSecondOfMinute()));//ora fine
		updateMeetingPS.setObject(5, meeting.getModalita(), Types.OTHER);//modalità
		if (meeting.getPiattaforma() != null)
			updateMeetingPS.setObject(6, meeting.getPiattaforma(), Types.OTHER);//piattaforma
		else
			updateMeetingPS.setNull(6, Types.OTHER);
		if (meeting.getSala() != null)
			updateMeetingPS.setString(7, meeting.getSala().getCodSala());//codSala
		else
			updateMeetingPS.setNull(7, Types.CHAR);
		updateMeetingPS.setInt(8, meeting.getIdMeeting());//idMeeting da modificare
		
		int record = updateMeetingPS.executeUpdate();	//esegue l'update e salva il numero di record modificati
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che ottiene i meeting che avvengono in una specifica sala.
	@Override
	public ArrayList<Meeting> getMeetingsBySala(SalaRiunione sala) throws SQLException {
		getMeetingsBySalaPS.setString(1, sala.getCodSala()); //inserisce il codice della sala di cui si vogliono i meeting
		
		ArrayList<Meeting> temp = new ArrayList<Meeting>();	//inizializza la lista da restituire dopo
		
		ResultSet risultato = getMeetingsBySalaPS.executeQuery();	//esegue la query e ottiene il Resultset
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.getProgettoByCod(risultato.getInt("CodProgetto")));
			temp.add(meetingTemp);
		}
		risultato.close();
		
		return temp;
	}

	//Metodo che ottiene i meeting che avvengono su una specifica piattaforma.
	@Override
	public ArrayList<Meeting> getMeetingsByPiattaforma(String platf) throws SQLException {
		getMeetingsByPiattaformaPS.setObject(1, platf, Types.OTHER);	//inserisce la piattaforma nella query
		
		ArrayList<Meeting> temp = new ArrayList<Meeting>();	//inizializza la lista da restituire dopo
		
		ResultSet risultato = getMeetingsByPiattaformaPS.executeQuery();	//esegue la query e ottiene il Resultset
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.getProgettoByCod(risultato.getInt("CodProgetto")));
			temp.add(meetingTemp);
		}
		risultato.close();
		
		return temp;
	}

	@Override
	public ArrayList<String> getPiattaforme() throws SQLException {
		ArrayList<String> temp = new ArrayList<String>();
		
		ResultSet risultato = getPiattaformePS.executeQuery();
		
		while(risultato.next()) {
			temp.add(risultato.getString("piattaforma"));
		}
		risultato.close();
		
		return temp;
	}

	@Override
	public String getProgettoRelativo(int idMeeting) throws SQLException {
		// TODO Auto-generated method stub
		String temp=null;
		
		getProgettoDiscussoPS.setInt(1,idMeeting);
		ResultSet risultato=getProgettoDiscussoPS.executeQuery();
		
		while(risultato.next())
			temp = risultato.getString("nomeprogetto");
		
		return temp;
	}
	
	public boolean addOrganizzatore(String CF) throws SQLException {
		
	
		
		ResultSet risultato=lastIdMeetingPS.executeQuery();
		risultato.next();
		
		addPresenzaOrganizzatorePS.setString(1, CF);
		addPresenzaOrganizzatorePS.setInt(2, risultato.getInt("idMeeting"));
		
		int record= addPresenzaOrganizzatorePS.executeUpdate();
		
		if(record==1)
			return true;
		else
			return false;
	
	}

}
