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
	private PreparedStatement getMeetingsPS, getMeetingByIdPS, ottieniMeetingDipendendentePS, ottieniInvitatiMeetingPS, addMeetingPS,
			rimuoviMeeting, aggiornaMeetingPS, getMeetingsBySalaPS, getMeetingsByPiattaformaPS, ottieniPiattaformePS,
			inserisciOrganizzatorePS, getIdProgettoDiscussoPS, ottieniUltimoIdMeetingInseritoPS, removePresenzeMeetingEliminato,
			inserisciInvitatoMeetingPS, eliminaInvitatoPS, aggiornaPresenzaInvitatoPS,
			getMeetingsByModalitàPS, filtraMeetingDipendentePerModalitàPS, filtraMeetingDipendentiSalaPS,
			filtraMeetingDipendentePiattaformaPS, ottieniCFOrganizzatorePS;

	public MeetingDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;

		getMeetingsPS = connection.prepareStatement("SELECT * FROM Meeting AS m ORDER BY m.DataInizio,m.OrarioInizio");
		ottieniInvitatiMeetingPS = connection.prepareStatement("SELECT * FROM Dipendente NATURAL JOIN Presenza AS p WHERE p.IDMeeting = ?");
		getMeetingByIdPS = connection.prepareStatement("SELECT * FROM Meeting WHERE idMeeting=?");
		ottieniMeetingDipendendentePS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.IDMeeting IN (SELECT p.IDMeeting FROM Presenza AS p WHERE p.CF = ?) ORDER BY m.DataInizio, m.OrarioInizio");
		addMeetingPS = connection.prepareStatement("INSERT INTO Meeting (DataInizio, DataFine, OrarioInizio, OrarioFine, Modalità, Piattaforma, CodSala,CodProgetto) VALUES (?,?,?,?,?,?,?,?)");
		rimuoviMeeting = connection.prepareStatement("DELETE FROM Meeting AS m WHERE m.IDMeeting = ?");
		aggiornaMeetingPS = connection.prepareStatement("UPDATE Meeting SET DataInizio = ?, DataFine = ?, OrarioInizio = ?, OrarioFine = ?, Modalità = ?, Piattaforma = ?, CodSala = ? , CodProgetto= ? WHERE IDMeeting = ?");
		getMeetingsBySalaPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.CodSala = ?");
		getMeetingsByPiattaformaPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.Piattaforma = ?");
		ottieniPiattaformePS = connection.prepareStatement("SELECT unnest(enum_range(NULL::piattaforma))::text AS piattaforma");
		getIdProgettoDiscussoPS = connection.prepareStatement("SELECT codProgetto FROM Progetto WHERE nomeProgetto ILIKE ?");
		inserisciOrganizzatorePS = connection.prepareStatement("INSERT INTO Presenza(CF,IdMeeting,Presente,Organizzatore) VALUES(?,?,true,true)");
		inserisciInvitatoMeetingPS = connection.prepareStatement("INSERT INTO Presenza(CF,IdMeeting,Presente,Organizzatore) VALUES(?,?,?,false)");
		eliminaInvitatoPS = connection.prepareStatement("DELETE FROM Presenza WHERE CF=? AND idMeeting=?");
		aggiornaPresenzaInvitatoPS = connection.prepareStatement("UPDATE Presenza as pr SET presente=? WHERE pr.CF ILIKE ? AND pr.idMeeting =?");
		ottieniUltimoIdMeetingInseritoPS = connection.prepareStatement("SELECT idMeeting FROM Meeting ORDER BY idMeeting DESC LIMIT 1");
		removePresenzeMeetingEliminato = connection.prepareStatement("DELETE FROM Presenza WHERE IdMeeting =?");
		getMeetingsByModalitàPS = connection.prepareStatement("SELECT * FROM Meeting AS m WHERE m.Modalità = ?");
		filtraMeetingDipendentePerModalitàPS = connection.prepareStatement("SELECT * FROM Meeting AS m NATURAL JOIN Presenza AS p WHERE m.Modalità = ? AND p.CF=?");
		filtraMeetingDipendentiSalaPS = connection.prepareStatement("SELECT * FROM Meeting NATURAL JOIN Presenza AS p WHERE Meeting.CodSala = ? AND p.CF=?");
		filtraMeetingDipendentePiattaformaPS = connection.prepareStatement("SELECT * FROM Meeting NATURAL JOIN Presenza AS p WHERE Meeting.Piattaforma = ? AND p.CF=?");
		ottieniCFOrganizzatorePS = connection.prepareStatement("SELECT cf FROM Dipendente NATURAL JOIN Presenza WHERE idMeeting=? AND organizzatore=true");
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
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));

			meetingTemp.setProgettoDiscusso(projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));
			meetings.add(meetingTemp);
		}
		risultato.close();

		return meetings;
	}

	@Override  //Ok
	public ArrayList<Meeting> ottieniMeetingDipendente(Dipendente dip) throws SQLException {
		ArrayList<Meeting> meetingDipendente = new ArrayList<Meeting>();
		ottieniMeetingDipendendentePS.setString(1, dip.getCf());
		ResultSet risultato = ottieniMeetingDipendendentePS.executeQuery();

		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio")), new LocalDate(risultato.getDate("DataFine")),
					new LocalTime(risultato.getTime("OrarioInizio")), new LocalTime(risultato.getTime("OrarioFine")),
					risultato.getString("Modalità"), risultato.getString("Piattaforma"),
					salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));
			meetingTemp.setPartecipazioniDipendenti(ottieniInvitatiMeetingSenzaRuolo(risultato.getInt("idMeeting")));
			meetingTemp.setPartecipantiAlMeeting(ottieniInvitatiMeeting(risultato.getInt("idMeeting")));
			meetingDipendente.add(meetingTemp);
		}
		risultato.close();

		return meetingDipendente;
	}

	//Ok
	public ArrayList<PartecipazioneMeeting> ottieniInvitatiMeeting(int idMeeting) throws SQLException {
		ArrayList<PartecipazioneMeeting> partecipazioniDipendenti = new ArrayList<PartecipazioneMeeting>();
		dipDAO = new DipendenteDAOPSQL(connection);
		ottieniInvitatiMeetingPS.setInt(1, idMeeting);

		ResultSet risultato = ottieniInvitatiMeetingPS.executeQuery();

		while (risultato.next()) {
			Dipendente partecipante = dipDAO.ottieniDipendenteDaCF(risultato.getString("CF"));
			PartecipazioneMeeting tempPartecipazione = new PartecipazioneMeeting(getMeetingById(idMeeting),
					partecipante, risultato.getBoolean("Presente"), risultato.getBoolean("organizzatore"));
			partecipazioniDipendenti.add(tempPartecipazione);
		}
		risultato.close();

		return partecipazioniDipendenti;

	}

	private Meeting getMeetingById(int idMeeting) throws SQLException {
		getMeetingByIdPS.setInt(1, idMeeting);
		ResultSet risultato = getMeetingByIdPS.executeQuery();

		risultato.next();

		dipDAO = new DipendenteDAOPSQL(connection);
		salaDAO = new SalaRiunioneDAOPSQL(connection);

		Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"), new LocalDate(risultato.getDate("DataInizio")),
				new LocalDate(risultato.getDate("DataFine")), new LocalTime(risultato.getTime("OrarioInizio")),
				new LocalTime(risultato.getTime("OrarioFine")), risultato.getString("Modalità"),
				risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));

		risultato.close();

		return meetingTemp;
	}

	@Override //Ok
	public ArrayList<Dipendente> ottieniInvitatiMeetingSenzaRuolo(int idMeeting) throws SQLException {
		ArrayList<Dipendente> invitati = new ArrayList<Dipendente>();
		ottieniInvitatiMeetingPS.setInt(1, idMeeting);

		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(connection);
		DipendenteDAO dipDAO = new DipendenteDAOPSQL(connection);

		ResultSet risultato = ottieniInvitatiMeetingPS.executeQuery();

		while (risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));

			Dipendente dipendenteTemp = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"),
					risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0),
					new LocalDate(risultato.getDate("DataNascita")), luogoTemp, risultato.getString("Indirizzo"),
					risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"),
					dipDAO.ottieniValutazione(risultato.getString("CF")));

			invitati.add(dipendenteTemp);
		}
		risultato.close();

		return invitati;
	}

	@Override  //Ok
	public boolean creaMeeting(Meeting meeting) throws SQLException {
		addMeetingPS.setDate(1, new Date(meeting.getDataInizio().toDateTimeAtStartOfDay().getMillis()));
		addMeetingPS.setDate(2, new Date(meeting.getDataFine().toDateTimeAtStartOfDay().getMillis()));
		addMeetingPS.setTime(3,
				new Time(meeting.getOraInizio().getHourOfDay(), meeting.getOraInizio().getMinuteOfHour(), 0));
		addMeetingPS.setTime(4,
				new Time(meeting.getOraFine().getHourOfDay(), meeting.getOraFine().getMinuteOfHour(), 0));
		addMeetingPS.setObject(5, meeting.getModalita(), Types.OTHER);
		addMeetingPS.setObject(6, meeting.getPiattaforma(), Types.OTHER);
		if (meeting.getSala() != null)
			addMeetingPS.setString(7, meeting.getSala().getCodiceSala());
		else
			addMeetingPS.setNull(7, Types.CHAR);

		addMeetingPS.setInt(8, meeting.getProgettoDiscusso().getIdProgettto());

		int record = addMeetingPS.executeUpdate();

		if (record == 1)
			return true;
		else

			return false;
	}

	@Override  //Ok
	public boolean rimuoviMeeting(int idMeeting) throws SQLException {
		rimuoviMeeting.setInt(1, idMeeting);

		int record = rimuoviMeeting.executeUpdate();

		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  //Ok
	public boolean aggiornaMeeting(Meeting meeting) throws SQLException {
		aggiornaMeetingPS.setDate(1, new Date(meeting.getDataInizio().toDateTimeAtStartOfDay().getMillis()));
		aggiornaMeetingPS.setDate(2, new Date(meeting.getDataFine().toDateTimeAtStartOfDay().getMillis()));
		aggiornaMeetingPS.setTime(3, new Time(meeting.getOraInizio().getHourOfDay(),
				meeting.getOraInizio().getMinuteOfHour(), meeting.getOraInizio().getSecondOfMinute()));
		aggiornaMeetingPS.setTime(4, new Time(meeting.getOraFine().getHourOfDay(), meeting.getOraFine().getMinuteOfHour(),
				meeting.getOraFine().getSecondOfMinute()));
		aggiornaMeetingPS.setObject(5, meeting.getModalita(), Types.OTHER);
		if (meeting.getPiattaforma() != null)
			aggiornaMeetingPS.setObject(6, meeting.getPiattaforma(), Types.OTHER);
		else
			aggiornaMeetingPS.setNull(6, Types.OTHER);
		if (meeting.getSala() != null)
			aggiornaMeetingPS.setString(7, meeting.getSala().getCodiceSala());
		else
			aggiornaMeetingPS.setNull(7, Types.CHAR);
		aggiornaMeetingPS.setInt(9, meeting.getIdMeeting());

		aggiornaMeetingPS.setInt(8, meeting.getProgettoDiscusso().getIdProgettto());

		int record = aggiornaMeetingPS.executeUpdate();

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
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));

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
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso((projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto"))));

			meetings.add(meetingTemp);
		}
		risultato.close();

		return meetings;
	}

	@Override //Ok
	public ArrayList<String> ottieniPiattaforme() throws SQLException {
		ArrayList<String> piattaforme = new ArrayList<String>();
		ResultSet risultato = ottieniPiattaformePS.executeQuery();

		while (risultato.next()) {
			piattaforme.add(risultato.getString("piattaforma"));
		}
		risultato.close();

		return piattaforme;
	}

	@Override  //Ok
	public boolean inserisciOrganizzatore(String CF) throws SQLException {
		ResultSet risultato = ottieniUltimoIdMeetingInseritoPS.executeQuery();
		risultato.next();
		inserisciOrganizzatorePS.setString(1, CF);
		inserisciOrganizzatorePS.setInt(2, risultato.getInt("idMeeting"));

		int record = inserisciOrganizzatorePS.executeUpdate();

		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  //Ok
	public boolean inserisciInvitatoMeeting(PartecipazioneMeeting partecipante) throws SQLException {
		inserisciInvitatoMeetingPS.setString(1, partecipante.getPartecipante().getCf());
		inserisciInvitatoMeetingPS.setInt(2, partecipante.getMeeting().getIdMeeting());
		inserisciInvitatoMeetingPS.setBoolean(3, partecipante.isPresente());

		int record = inserisciInvitatoMeetingPS.executeUpdate();

		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  //Ok
	public boolean eliminaInvitato(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		eliminaInvitatoPS.setString(1, partecipazioneMeeting.getPartecipante().getCf());
		eliminaInvitatoPS.setInt(2, partecipazioneMeeting.getMeeting().getIdMeeting());

		int record = eliminaInvitatoPS.executeUpdate();

		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  //Ok
	public boolean aggiornaPresenzaInvitato(PartecipazioneMeeting partecipazioneMeeting) throws SQLException {
		aggiornaPresenzaInvitatoPS.setBoolean(1, partecipazioneMeeting.isPresente());
		aggiornaPresenzaInvitatoPS.setString(2, partecipazioneMeeting.getPartecipante().getCf());
		aggiornaPresenzaInvitatoPS.setInt(3, partecipazioneMeeting.getMeeting().getIdMeeting());

		int record = aggiornaPresenzaInvitatoPS.executeUpdate();

		if (record == 1)
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
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));
			meetings.add(meetingTemp);
		}
		risultato.close();

		return meetings;
	}

	@Override  //Ok
	public ArrayList<Meeting> filtraMeetingDipendentePerModalità(String modalità, Dipendente dipendente)
			throws SQLException {
		ArrayList<Meeting> temp = new ArrayList<Meeting>();
		filtraMeetingDipendentePerModalitàPS.setObject(1, modalità, Types.OTHER);
		filtraMeetingDipendentePerModalitàPS.setString(2, dipendente.getCf());
		ResultSet risultato = filtraMeetingDipendentePerModalitàPS.executeQuery();

		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));
			temp.add(meetingTemp);
		}
		risultato.close();

		return temp;
	}

	@Override //Ok
	public ArrayList<Meeting> filtraMeetingDipendentiSala(SalaRiunione sala, Dipendente dipendente)
			throws SQLException {
		filtraMeetingDipendentiSalaPS.setString(1, sala.getCodiceSala());
		filtraMeetingDipendentiSalaPS.setString(2, dipendente.getCf());
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		ResultSet risultato = filtraMeetingDipendentiSalaPS.executeQuery();

		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso(projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));
			meetings.add(meetingTemp);
		}
		risultato.close();

		return meetings;

	}

	@Override //ok
	public ArrayList<Meeting> filtraMeetingDipendentePiattaforma(String piattaforma, Dipendente dipendente)
			throws SQLException {
		filtraMeetingDipendentePiattaformaPS.setObject(1, piattaforma, Types.OTHER);
		filtraMeetingDipendentePiattaformaPS.setString(2, dipendente.getCf());
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();

		ResultSet risultato = filtraMeetingDipendentePiattaformaPS.executeQuery();

		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		ProgettoDAO projDAO = new ProgettoDAOPSQL(connection);

		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()), risultato.getString("Modalità"),
					risultato.getString("Piattaforma"), salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
			meetingTemp.setProgettoDiscusso((projDAO.ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto"))));
			meetings.add(meetingTemp);
		}
		risultato.close();

		return meetings;
	}

	@Override //Ok
	public int ottieniIdUltimoMeetingInserito() throws SQLException {
		ResultSet risultato = ottieniUltimoIdMeetingInseritoPS.executeQuery();
		risultato.next();

		int idMeeting = risultato.getInt("IdMeeting");
		return idMeeting;
	}

	@Override //Ok
	public String ottieniCFOrganizzatore(Meeting meeting) throws SQLException {
		String cf;
		ottieniCFOrganizzatorePS.setInt(1, meeting.getIdMeeting());
		ResultSet risultato = ottieniCFOrganizzatorePS.executeQuery();

		risultato.next();
		cf = risultato.getString(1);
		risultato.close();

		return cf;
	}
}
