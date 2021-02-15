package implementazioniDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.time.OffsetTime;
import java.util.ArrayList;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.LuogoNascita;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Progetto;
import entita.SalaRiunione;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;

public class MeetingDAOPSQL implements MeetingDAO {
	private Connection connection;

	private DipendenteDAO dipDAO = null;
	private SalaRiunioneDAO salaDAO = null;
	private PreparedStatement getMeetingsPS,
	getMeetingByIdPS,
	getMeetingsByInvitatoPS,
	getInvitatiPS,
	addMeetingPS,
	removeMeetingPS,
	updateMeetingPS,
	getMeetingsBySalaPS,
	getMeetingsByPiattaformaPS,
	getPiattaformePS,
	addPresenzaOrganizzatorePS,
	getIdProgettoDiscussoPS,
	lastIdMeetingPS,
	removePresenzeMeetingEliminato,
	aggiungiPartecipanteMeetingPS,
	eliminaPartecipanteMeetingPS,
	aggiornaPresenzaMeetingPS,
	getMeetingsByModalitàPS,
	getMeetingDipendenteByModalitàPS,
	getMeetingsDipendenteBySalaPS,
	getMeetingsDipendenteByPiattaformaPS;

	public MeetingDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		getMeetingsPS = connection.prepareStatement("SELECT * FROM Meeting AS m ORDER BY m.DataInizio,m.OrarioInizio");
		getInvitatiPS = connection.prepareStatement("SELECT * FROM Dipendente NATURAL JOIN Presenza AS p WHERE p.IDMeeting = ?");
		getMeetingByIdPS=connection.prepareStatement("SELECT * FROM Meeting WHERE idMeeting=?");
		getMeetingsByInvitatoPS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.IDMeeting IN (SELECT p.IDMeeting FROM Presenza AS p WHERE p.CF = ?) ORDER BY m.DataInizio, m.OrarioInizio");
		addMeetingPS = connection.prepareStatement("INSERT INTO Meeting (DataInizio, DataFine, OrarioInizio, OrarioFine, Modalità, Piattaforma, CodSala,CodProgetto) VALUES (?,?,?,?,?,?,?,?)");
		removeMeetingPS = connection.prepareStatement("DELETE FROM Meeting AS m WHERE m.IDMeeting = ?");
		updateMeetingPS = connection.prepareStatement("UPDATE Meeting SET DataInizio = ?, DataFine = ?, OrarioInizio = ?, OrarioFine = ?, Modalità = ?, Piattaforma = ?, CodSala = ? , CodProgetto= ? WHERE IDMeeting = ?");
		getMeetingsBySalaPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.CodSala = ?");
		getMeetingsByPiattaformaPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.Piattaforma = ?");
		getPiattaformePS = connection.prepareStatement("SELECT unnest(enum_range(NULL::piattaforma))::text AS piattaforma");
		getIdProgettoDiscussoPS= connection.prepareStatement("SELECT codProgetto FROM Progetto WHERE nomeProgetto ILIKE ?");
		addPresenzaOrganizzatorePS= connection.prepareStatement("INSERT INTO Presenza(CF,IdMeeting,Presente,Organizzatore) VALUES(?,?,true,true)");
		aggiungiPartecipanteMeetingPS=connection.prepareStatement("INSERT INTO Presenza(CF,IdMeeting,Presente,Organizzatore) VALUES(?,?,?,false)");
		eliminaPartecipanteMeetingPS=connection.prepareStatement("DELETE FROM Presenza WHERE CF=? AND idMeeting=?");
		aggiornaPresenzaMeetingPS=connection.prepareStatement("UPDATE Presenza as pr SET presente=? WHERE pr.CF ILIKE ? AND pr.idMeeting =?");
		lastIdMeetingPS=connection.prepareStatement("SELECT idMeeting FROM Meeting ORDER BY idMeeting DESC LIMIT 1");
		removePresenzeMeetingEliminato=connection.prepareStatement("DELETE FROM Presenza WHERE IdMeeting =?");
		getMeetingsByModalitàPS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.Modalità = ?");
		getMeetingDipendenteByModalitàPS=connection.prepareStatement("SELECT * FROM Meeting AS m NATURAL JOIN Presenza AS p WHERE m.Modalità = ? AND p.CF=?");
		getMeetingsDipendenteBySalaPS = connection.prepareStatement("SELECT * FROM Meeting NATURAL JOIN Presenza AS p WHERE Meeting.CodSala = ? AND p.CF=?");
		getMeetingsDipendenteByPiattaformaPS = connection.prepareStatement("SELECT * FROM Meeting NATURAL JOIN Presenza AS p WHERE Meeting.Piattaforma = ? AND p.CF=?");	
	}
	
	@Override
	public ArrayList<Meeting> getMeetings() throws SQLException {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		ResultSet risultato = getMeetingsPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);
		
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
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
	}

	@Override
	public ArrayList<Meeting> getMeetingsByInvitato(Dipendente dip) throws SQLException {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		getMeetingsByInvitatoPS.setString(1,dip.getCf());
		ResultSet risultato = getMeetingsByInvitatoPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio")),
					new LocalDate(risultato.getDate("DataFine")),
					new LocalTime(risultato.getTime("OrarioInizio")),
					new LocalTime(risultato.getTime("OrarioFine")),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.getProgettoByCod(risultato.getInt("CodProgetto")));
			meetingTemp.setPartecipazioniDipendenti(getInvitati(risultato.getInt("idMeeting")));
			meetingTemp.setPartecipantiAlMeeting(getInvitatiPartecipazioneMeeting(risultato.getInt("idMeeting")));
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
	}

	public ArrayList<PartecipazioneMeeting> getInvitatiPartecipazioneMeeting(int idMeeting) throws SQLException {
		ArrayList<PartecipazioneMeeting> partecipazioni = new ArrayList<PartecipazioneMeeting>();
		dipDAO = new DipendenteDAOPSQL(connection);
		getInvitatiPS.setInt(1, idMeeting);
		
		ResultSet risultato = getInvitatiPS.executeQuery();
		
		while(risultato.next()) {
			Dipendente partecipante = dipDAO.getDipendenteByCF(risultato.getString("CF"));
			PartecipazioneMeeting tempPartecipazione = new PartecipazioneMeeting(getMeetingById(idMeeting), partecipante, risultato.getBoolean("Presente"), risultato.getBoolean("organizzatore"));
			partecipazioni.add(tempPartecipazione);
		}
		risultato.close();
		
		return partecipazioni;
		
	}

	private Meeting getMeetingById(int idMeeting) throws SQLException {
		getMeetingByIdPS.setInt(1, idMeeting);
		ResultSet risultato = getMeetingByIdPS.executeQuery();
		
		risultato.next();
		
		dipDAO = new DipendenteDAOPSQL(connection);
		salaDAO = new SalaRiunioneDAOPSQL(connection);
		
		Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
				new LocalDate(risultato.getDate("DataInizio")),
				new LocalDate(risultato.getDate("DataFine")),
				new LocalTime(risultato.getTime("OrarioInizio")),
				new LocalTime(risultato.getTime("OrarioFine")),
				risultato.getString("Modalità"),
				risultato.getString("Piattaforma"),
				salaDAO.getSalaByCod(risultato.getString("CodSala")));
		
		risultato.close();
		
		return meetingTemp;
	}

	@Override
	public ArrayList<Dipendente> getInvitati(int idMeeting) throws SQLException {
		ArrayList<Dipendente> invitati = new ArrayList<Dipendente>();
		getInvitatiPS.setInt(1, idMeeting);
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(connection);
		DipendenteDAO dipDAO = new DipendenteDAOPSQL(connection);
		
		ResultSet risultato = getInvitatiPS.executeQuery();
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));
			
			Dipendente dipendenteTemp = new Dipendente(risultato.getString("CF"), 
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
					dipDAO.getValutazione(risultato.getString("CF")));
			
			invitati.add(dipendenteTemp);
		}
		risultato.close();
		
		return invitati;
	}

	@Override
	public boolean insertMeeting(Meeting meeting,String nomeProgettoDiscusso) throws SQLException {
		addMeetingPS.setDate(1, new Date(meeting.getDataInizio().toDateTimeAtStartOfDay().getMillis()));
		addMeetingPS.setDate(2, new Date(meeting.getDataFine().toDateTimeAtStartOfDay().getMillis()));
		addMeetingPS.setTime(3, new Time(meeting.getOraInizio().getHourOfDay(),meeting.getOraInizio().getMinuteOfHour(), 0));
		addMeetingPS.setTime(4, new Time(meeting.getOraFine().getHourOfDay(),meeting.getOraFine().getMinuteOfHour(),0));
		addMeetingPS.setObject(5, meeting.getModalita(),Types.OTHER);
		addMeetingPS.setObject(6, meeting.getPiattaforma(), Types.OTHER);
		if (meeting.getSala() != null)
			addMeetingPS.setString(7, meeting.getSala().getCodiceSala());
		else
			addMeetingPS.setNull(7, Types.CHAR);
		
		getIdProgettoDiscussoPS.setString(1,nomeProgettoDiscusso);
		ResultSet risultato = getIdProgettoDiscussoPS.executeQuery();
		risultato.next();
		
		addMeetingPS.setInt(8, risultato.getInt("CodProgetto"));
		
		int record = addMeetingPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			
			return false;
	}
	
	@Override
	public boolean insertMeetingCompleto(Meeting meetingInserito, Progetto progetto) throws SQLException {
		addMeetingPS.setDate(1, new Date(meetingInserito.getDataInizio().toDateTimeAtStartOfDay().getMillis()));
		addMeetingPS.setDate(2, new Date(meetingInserito.getDataFine().toDateTimeAtStartOfDay().getMillis()));
		addMeetingPS.setTime(3, new Time(meetingInserito.getOraInizio().getHourOfDay(),meetingInserito.getOraInizio().getMinuteOfHour(), 0));
		addMeetingPS.setTime(4, new Time(meetingInserito.getOraFine().getHourOfDay(),meetingInserito.getOraFine().getMinuteOfHour(),meetingInserito.getOraFine().getSecondOfMinute()));
		addMeetingPS.setObject(5, meetingInserito.getModalita(),Types.OTHER);
		addMeetingPS.setObject(6, meetingInserito.getPiattaforma(), Types.OTHER);
		if (meetingInserito.getSala() != null)
			addMeetingPS.setString(7, meetingInserito.getSala().getCodiceSala());
		else
			addMeetingPS.setNull(7, Types.CHAR);
		
		getIdProgettoDiscussoPS.setString(1,progetto.getNomeProgetto());
		ResultSet risultato = getIdProgettoDiscussoPS.executeQuery();
		risultato.next();
		
		addMeetingPS.setInt(8, risultato.getInt("CodProgetto"));
		
		int record = addMeetingPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteMeeting(int idMeeting) throws SQLException {
		removeMeetingPS.setInt(1, idMeeting);
		
		int record = removeMeetingPS.executeUpdate();
		
		removePresenzeMeetingEliminato.setInt(1, idMeeting);
		removePresenzeMeetingEliminato.executeUpdate();

		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateMeeting(Meeting meeting,Progetto progettoSelezionato) throws SQLException {
		updateMeetingPS.setDate(1, new Date(meeting.getDataInizio().toDateTimeAtStartOfDay().getMillis()));
		updateMeetingPS.setDate(2, new Date(meeting.getDataFine().toDateTimeAtStartOfDay().getMillis()));
		updateMeetingPS.setTime(3, new Time(meeting.getOraInizio().getHourOfDay(),meeting.getOraInizio().getMinuteOfHour(),meeting.getOraInizio().getSecondOfMinute()));
		updateMeetingPS.setTime(4, new Time(meeting.getOraFine().getHourOfDay(),meeting.getOraFine().getMinuteOfHour(),meeting.getOraFine().getSecondOfMinute()));
		updateMeetingPS.setObject(5, meeting.getModalita(), Types.OTHER);
		if (meeting.getPiattaforma() != null)
			updateMeetingPS.setObject(6, meeting.getPiattaforma(), Types.OTHER);
		else
			updateMeetingPS.setNull(6, Types.OTHER);
		if (meeting.getSala() != null)
			updateMeetingPS.setString(7, meeting.getSala().getCodiceSala());
		else
			updateMeetingPS.setNull(7, Types.CHAR);
		updateMeetingPS.setInt(9, meeting.getIdMeeting());
		
		updateMeetingPS.setInt(8, progettoSelezionato.getIdProgettto());
		
		int record = updateMeetingPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<Meeting> getMeetingsBySala(SalaRiunione sala) throws SQLException {
		getMeetingsBySalaPS.setString(1, sala.getCodiceSala());
		
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		
		ResultSet risultato = getMeetingsBySalaPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

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
			
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
	}

	@Override
	public ArrayList<Meeting> getMeetingsByPiattaforma(String piattaforma) throws SQLException {
		getMeetingsByPiattaformaPS.setObject(1, piattaforma, Types.OTHER);
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		ResultSet risultato = getMeetingsByPiattaformaPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso((projDAO.getProgettoByCod(risultato.getInt("CodProgetto"))));
			
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
	}

	@Override
	public ArrayList<String> getPiattaforme() throws SQLException {
		ArrayList<String> piattaforme = new ArrayList<String>();
		ResultSet risultato = getPiattaformePS.executeQuery();
		
		while(risultato.next()) {
			piattaforme.add(risultato.getString("piattaforma"));
		}
		risultato.close();
		
		return piattaforme;
	}
	
	@Override
	public boolean insertOrganizzatore(String CF) throws SQLException {
		ResultSet risultato=lastIdMeetingPS.executeQuery();
		risultato.next();
		addPresenzaOrganizzatorePS.setString(1, CF);
		addPresenzaOrganizzatorePS.setInt(2, risultato.getInt("idMeeting"));
		
		int record = addPresenzaOrganizzatorePS.executeUpdate();
		
		if(record==1)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean insertPartecipanteMeeting(PartecipazioneMeeting partecipante) throws SQLException {
		aggiungiPartecipanteMeetingPS.setString(1, partecipante.getPartecipante().getCf());
		aggiungiPartecipanteMeetingPS.setInt(2, partecipante.getMeeting().getIdMeeting());
		aggiungiPartecipanteMeetingPS.setBoolean(3, partecipante.isPresente());
		
		int record = aggiungiPartecipanteMeetingPS.executeUpdate();

		if(record==1)
			return true;
		else
			return false;
	}

	@Override
	public boolean deletePartecipanteMeeting(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		eliminaPartecipanteMeetingPS.setString(1, partecipazioneMeeting.getPartecipante().getCf());
		eliminaPartecipanteMeetingPS.setInt(2, partecipazioneMeeting.getMeeting().getIdMeeting());
		
		int record=eliminaPartecipanteMeetingPS.executeUpdate();
		
		if(record==1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updatePresenzaPartecipante(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		aggiornaPresenzaMeetingPS.setBoolean(1, partecipazioneMeeting.isPresente());
		aggiornaPresenzaMeetingPS.setString(2, partecipazioneMeeting.getPartecipante().getCf());
		aggiornaPresenzaMeetingPS.setInt(3, partecipazioneMeeting.getMeeting().getIdMeeting());
		
		int record = aggiornaPresenzaMeetingPS.executeUpdate();

		if(record==1)
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<Meeting> getMeetingsByModalità(String modalità) throws SQLException {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		getMeetingsByModalitàPS.setObject(1, modalità, Types.OTHER);
		ResultSet risultato = getMeetingsByModalitàPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

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
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
	}

	@Override
	public ArrayList<Meeting> getMeetingDipendenteByModalità(String modalità, Dipendente dipendente) throws SQLException {
		ArrayList<Meeting> temp = new ArrayList<Meeting>();
		getMeetingDipendenteByModalitàPS.setObject(1, modalità, Types.OTHER);
		getMeetingDipendenteByModalitàPS.setString(2, dipendente.getCf());
		ResultSet risultato = getMeetingDipendenteByModalitàPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);
		
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
	public ArrayList<Meeting> getMeetingsDipendenteBySala(SalaRiunione sala, Dipendente dipendente)
			throws SQLException {
		getMeetingsDipendenteBySalaPS.setString(1, sala.getCodiceSala());
		getMeetingsDipendenteBySalaPS.setString(2, dipendente.getCf());
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		ResultSet risultato = getMeetingsDipendenteBySalaPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

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
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
		
	}

	@Override
	public ArrayList<Meeting> getMeetingsDipendenteByPiattaforma(String piattaforma, Dipendente dipendente)
			throws SQLException {
		getMeetingsDipendenteByPiattaformaPS.setObject(1, piattaforma, Types.OTHER);
		getMeetingsDipendenteByPiattaformaPS.setString(2, dipendente.getCf());
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		
		ResultSet risultato = getMeetingsDipendenteByPiattaformaPS.executeQuery();
		
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso((projDAO.getProgettoByCod(risultato.getInt("CodProgetto"))));
			meetings.add(meetingTemp);
		}
		risultato.close();
		
		return meetings;
	}

	@Override
	public int getLastIdMeeting() throws SQLException {
		
		ResultSet risultato=lastIdMeetingPS.executeQuery();
		risultato.next();
		
		int idMeeting=risultato.getInt("IdMeeting");
		return idMeeting;
	}
}
