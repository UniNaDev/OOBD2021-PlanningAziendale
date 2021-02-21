package implementazioniDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SalaRiunioneDAO;

public class ProgettoDAOPSQL implements ProgettoDAO {
	private Connection  connection;

	private DipendenteDAO dipDAO;
	private AmbitoProgettoDAO ambitoDAO;
	
	private PreparedStatement ottieniProgettiPS,
	ottieniProgettiSegreteriaFiltratiPerNomePS,
	ottieniProgettiDipendentiPerNomePS,
	ottieniPartecipantiProgettoPS,
	ottieniProgettiDipendentePS,
	ottieniProgettiFiltratiPerAmbitoPS,
	ottieniProgettiFiltratiPerTipoPS,
	creaProgettoPS,
	rimuoviProgettoPS,
	inserisciPartecipanteProgettoPS,
	eliminaPartecipanteProgettoPS,
	projectManagerPS,
	aggiornaRuoloCollaboratorePS,
	aggiornaProgettoPS,
	ottieniMeetingRelativiProgettoPS,
	ottieniProgettoDaCodiceProgettoPS,
	ottieniTipologiePS,
	ottieniRuoliPS,
	ottieniCodiceProgettoPS;

	public ProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;

		ottieniProgettiPS = connection.prepareStatement("SELECT * FROM Progetto");
		ottieniProgettiSegreteriaFiltratiPerNomePS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.NomeProgetto ILIKE '%' || ? || '%'");
		ottieniProgettiDipendentiPerNomePS=connection.prepareStatement("SELECT * FROM Progetto AS p NATURAL JOIN Partecipazione AS par WHERE par.CF = ? AND p.NomeProgetto ILIKE '%' || ? || '%'");
		ottieniPartecipantiProgettoPS = connection.prepareStatement("SELECT * FROM Partecipazione AS p WHERE p.CodProgetto = ?");
		ottieniProgettiDipendentePS = connection.prepareStatement("SELECT * FROM Progetto AS p NATURAL JOIN Partecipazione AS par WHERE par.CF = ? ORDER BY p.DataTerminazione DESC, p.DataScadenza ASC");
		ottieniProgettiFiltratiPerAmbitoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.CodProgetto IN (SELECT a.CodProgetto FROM AmbitoProgettoLink AS a WHERE a.IDAmbito = ?)");
		ottieniProgettiFiltratiPerTipoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.TipoProgetto = ?");
		creaProgettoPS = connection.prepareStatement("INSERT INTO Progetto (NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione) VALUES (?,?,?,?,?,?)");
		rimuoviProgettoPS = connection.prepareStatement("DELETE FROM Progetto AS p WHERE p.CodProgetto = ?");
		inserisciPartecipanteProgettoPS = connection.prepareStatement("INSERT INTO Partecipazione VALUES (?,?,?)");
		eliminaPartecipanteProgettoPS = connection.prepareStatement("DELETE FROM Partecipazione WHERE CF = ? AND CodProgetto = ?");
		projectManagerPS = connection.prepareStatement("SELECT CF FROM Dipendente NATURAL JOIN Partecipazione WHERE CodProgetto = ? AND RuoloDipendente = 'Project Manager'");
		aggiornaRuoloCollaboratorePS=connection.prepareStatement("UPDATE Partecipazione SET RuoloDipendente=? WHERE CF=? AND CodProgetto=?");
		aggiornaProgettoPS = connection.prepareStatement("UPDATE Progetto SET NomeProgetto = ?, TipoProgetto = ?, DescrizioneProgetto = ?,  DataScadenza = ?, DataTerminazione = ? WHERE CodProgetto = ?");
		ottieniMeetingRelativiProgettoPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.CodProgetto = ?");
		ottieniProgettoDaCodiceProgettoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.CodProgetto = ?");
		ottieniTipologiePS = connection.prepareStatement("SELECT unnest(enum_range(NULL::tipologia))");
		ottieniRuoliPS=connection.prepareStatement("SELECT unnest(enum_range(NULL::ruolo))");
		ottieniCodiceProgettoPS = connection.prepareStatement("SELECT codprogetto FROM progetto WHERE nomeprogetto = ?");
		connection.prepareStatement("SELECT nomeProgetto,tipoProgetto,descrizioneProgetto,dataCreazione,datascadenza FROM Progetto WHERE NomeProgetto= ?");
	}

	@Override
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		ResultSet risultato = ottieniProgettiPS.executeQuery();
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while (risultato.next()) {
			LocalDate dataTerminazione = null;
			if (risultato.getDate("DataTerminazione") != null)
				dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					dataTerminazione);
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(projTemp);
			projTemp.setMeetingsRelativi(ottieniMeetingRelativiProgetto(projTemp.getIdProgettto()));
			projTemp.setCollaborazioni(ottieniPartecipantiProgetto(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}
	
	@Override  
	public ArrayList<Dipendente> ottieniPartecipantiProgettoSenzaRuolo(int codiceProgetto) throws SQLException {
		ArrayList<Dipendente> partecipanti = new ArrayList<Dipendente>();
		dipDAO = new DipendenteDAOPSQL(connection);
		ottieniPartecipantiProgettoPS.setInt(1, codiceProgetto);
		
		ResultSet risultato = ottieniPartecipantiProgettoPS.executeQuery();
		
		while(risultato.next()) {
			Dipendente partecipanteTemp = dipDAO.ottieniDipendenteDaCF(risultato.getString("CF"));
			partecipanti.add(partecipanteTemp);
		}
		risultato.close();
		
		return partecipanti;
	}
	
	@Override  
	public ArrayList<CollaborazioneProgetto> ottieniPartecipantiProgetto(int codiceProgetto) throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();
		dipDAO = new DipendenteDAOPSQL(connection);
		ottieniPartecipantiProgettoPS.setInt(1, codiceProgetto);
		
		ResultSet risultato = ottieniPartecipantiProgettoPS.executeQuery();
		
		while(risultato.next()) {
			Dipendente partecipanteTemp = dipDAO.ottieniDipendenteDaCF(risultato.getString("CF"));
			CollaborazioneProgetto partecipazioneTemp = new CollaborazioneProgetto(ottieniProgettoDaCodiceProgetto(codiceProgetto), partecipanteTemp, risultato.getString("RuoloDipendente"));
			collaborazioni.add(partecipazioneTemp);
		}
		risultato.close();
		
		return collaborazioni;
	}

	@Override  
	public ArrayList<Meeting> ottieniMeetingRelativiProgetto(int codiceProgettoSelezionato) throws SQLException {
		ArrayList<Meeting> meetingsRelativi = new ArrayList<Meeting>();
		ottieniMeetingRelativiProgettoPS.setInt(1, codiceProgettoSelezionato);
		
		ResultSet risultato = ottieniMeetingRelativiProgettoPS.executeQuery();
		
		dipDAO = new DipendenteDAOPSQL(connection);
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.ottieniSalaDaCodSala(risultato.getString("CodSala")));
					
					meetingTemp.setProgettoDiscusso(ottieniProgettoDaCodiceProgetto(risultato.getInt("CodProgetto")));
			
			meetingsRelativi.add(meetingTemp);
		}
		risultato.close();
		
		return meetingsRelativi;
	}

	@Override 
	public ArrayList<CollaborazioneProgetto> ottieniProgettiDipendente(Dipendente dipendente) throws SQLException {
		ottieniProgettiDipendentePS.setString(1, dipendente.getCf());
		ArrayList<CollaborazioneProgetto> collaborazioniDipendente = new ArrayList<CollaborazioneProgetto>();
		
		ResultSet risultato = ottieniProgettiDipendentePS.executeQuery();
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LocalDate dataTerminazione = null;
			if (risultato.getDate("DataTerminazione") != null)
				dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
			
			Progetto progettoTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					dataTerminazione);
			
			if(risultato.getDate("DataTerminazione") != null){
				progettoTemp.setDataTerminazione(new LocalDate(risultato.getDate("DataTerminazione")));
			}
			else {
				progettoTemp.setDataTerminazione(null);
			}
	
			progettoTemp.setMeetingsRelativi(ottieniMeetingRelativiProgetto(risultato.getInt("CodProgetto")));
			progettoTemp.setCollaborazioni(ottieniPartecipantiProgetto(risultato.getInt("CodProgetto")));
			progettoTemp.setComprende(ottieniPartecipantiProgettoSenzaRuolo(risultato.getInt("CodProgetto")));
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(progettoTemp);
			progettoTemp.setAmbiti(ambiti);
			
			CollaborazioneProgetto collaborazioneTemp = new CollaborazioneProgetto(progettoTemp,dipendente,risultato.getString("RuoloDipendente"));
			
			collaborazioniDipendente.add(collaborazioneTemp);
		}
		risultato.close();
		
		return collaborazioniDipendente;
	}

	@Override 
	public ArrayList<Progetto> ottieniProgettiFiltratiPerAmbito(AmbitoProgetto ambito) throws SQLException {		
		ottieniProgettiFiltratiPerAmbitoPS.setInt(1, ambito.getIdAmbito());
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		
		ResultSet risultato = ottieniProgettiFiltratiPerAmbitoPS.executeQuery();
		
		dipDAO = new DipendenteDAOPSQL(connection);
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LocalDate dataTerminazione = null;
			if (risultato.getDate("DataTerminazione") != null)
				dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					dataTerminazione);
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(projTemp);
			projTemp.setAmbiti(ambiti);
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}

	@Override  
	public ArrayList<Progetto> ottieniProgettiFiltratiPerTipo(String tipologia) throws SQLException {		
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		ottieniProgettiFiltratiPerTipoPS.setObject(1, tipologia, Types.OTHER);
		
		ResultSet risultato = ottieniProgettiFiltratiPerTipoPS.executeQuery();
		
		dipDAO = new DipendenteDAOPSQL(connection);
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LocalDate dataTerminazione = null;
			if (risultato.getDate("DataTerminazione") != null)
				dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					dataTerminazione);
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(projTemp);
			projTemp.setAmbiti(ambiti);
			progetti.add(projTemp);
		}
		risultato.close();
			
		return progetti;
	}

	@Override 
	public boolean creaProgetto(Progetto proj) throws SQLException {
		creaProgettoPS.setString(1, proj.getNomeProgetto());
		creaProgettoPS.setObject(2, proj.getTipoProgetto(), Types.OTHER);
		creaProgettoPS.setString(3, proj.getDescrizioneProgetto());
		creaProgettoPS.setDate(4, new Date(proj.getDataCreazione().toDateTimeAtStartOfDay().getMillis()));
		if (proj.getScadenza() != null)
			creaProgettoPS.setDate(5, new Date(proj.getScadenza().toDateTimeAtStartOfDay().getMillis()));
		else
			creaProgettoPS.setNull(5, Types.DATE);
		if (proj.getDataTerminazione() != null)
			creaProgettoPS.setDate(6, new Date(proj.getDataTerminazione().toDateTimeAtStartOfDay().getMillis()));
		else
			creaProgettoPS.setNull(6, Types.DATE);
		
		int record = creaProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override 
	public boolean rimuoviProgetto(Progetto proj) throws SQLException {
		rimuoviProgettoPS.setInt(1, proj.getIdProgettto());
		
		int record = rimuoviProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override 
	public boolean inserisciPartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		inserisciPartecipanteProgettoPS.setInt(1, collaborazioneProgetto.getProgetto().getIdProgettto());
		inserisciPartecipanteProgettoPS.setString(2, collaborazioneProgetto.getCollaboratore().getCf());
		inserisciPartecipanteProgettoPS.setObject(3, collaborazioneProgetto.getRuoloCollaboratore(),Types.OTHER);
		
		int record = inserisciPartecipanteProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override 
	public boolean eliminaPartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		eliminaPartecipanteProgettoPS.setString(1, collaborazioneProgetto.getCollaboratore().getCf());
		eliminaPartecipanteProgettoPS.setInt(2, collaborazioneProgetto.getProgetto().getIdProgettto());
		
		int record = eliminaPartecipanteProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  
	public boolean aggiornaProgetto(Progetto proj) throws SQLException {
		aggiornaProgettoPS.setString(1, proj.getNomeProgetto());
		aggiornaProgettoPS.setObject(2, proj.getTipoProgetto(),Types.OTHER);
		aggiornaProgettoPS.setString(3, proj.getDescrizioneProgetto());
		if (proj.getScadenza() != null)
			aggiornaProgettoPS.setDate(4, new Date(proj.getScadenza().toDateTimeAtStartOfDay().getMillis()));
		else
			aggiornaProgettoPS.setNull(4, Types.DATE);
		if (proj.getDataTerminazione() != null) 
			aggiornaProgettoPS.setDate(5, new Date(proj.getDataTerminazione().toDateTimeAtStartOfDay().getMillis()));
		else
			aggiornaProgettoPS.setNull(5,Types.DATE);
		aggiornaProgettoPS.setInt(6, proj.getIdProgettto());
		
		int record = aggiornaProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override 
	public Progetto ottieniProgettoDaCodiceProgetto(int codProgetto) throws SQLException {
		ottieniProgettoDaCodiceProgettoPS.setInt(1, codProgetto);
		ResultSet risultato = ottieniProgettoDaCodiceProgettoPS.executeQuery();
		risultato.next();
		
		dipDAO = new DipendenteDAOPSQL(connection);
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		LocalDate dataTerminazione = null;
		if (risultato.getDate("DataTerminazione") != null)
			dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
		Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
				risultato.getString("NomeProgetto"),
				risultato.getString("TipoProgetto"),
				risultato.getString("DescrizioneProgetto"),
				new LocalDate(risultato.getDate("DataCreazione")),
				new LocalDate(risultato.getDate("DataScadenza")),
				dataTerminazione);
		ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(projTemp);
		projTemp.setAmbiti(ambiti);
		
		risultato.close();
		
		return projTemp;
	}

	@Override  
	public ArrayList<String> ottieniTipologie() throws SQLException {
		ArrayList<String> tipologie = new ArrayList<String>();
		
		ResultSet risultato = ottieniTipologiePS.executeQuery();
		
		while (risultato.next())
			tipologie.add(risultato.getString(1));
		
		risultato.close();
		
		return tipologie;
	}
	
	@Override 
	public int ottieniCodiceProgetto(Progetto proj) throws SQLException {
		int codProgetto;
		
		ottieniCodiceProgettoPS.setString(1, proj.getNomeProgetto());
		
		ResultSet risultato = ottieniCodiceProgettoPS.executeQuery();
		
		risultato.next();
		
		codProgetto = risultato.getInt("CodProgetto");
		
		return codProgetto;
	}
	
	@Override  
	public ArrayList<String> ottieniRuoli() throws SQLException {
		ArrayList<String> ruoli = new ArrayList<String>();
		ResultSet risultato = ottieniRuoliPS.executeQuery();
		
		while (risultato.next())
			ruoli.add(risultato.getString(1));
		
		risultato.close();
		
		return ruoli;
	}

	@Override 
	public boolean inserisciProjectManager(String cf, Progetto progetto, String ruolo) throws SQLException {
		inserisciPartecipanteProgettoPS.setInt(1, progetto.getIdProgettto());
		inserisciPartecipanteProgettoPS.setString(2, cf);
		inserisciPartecipanteProgettoPS.setObject(3, ruolo,Types.OTHER);
		
		int record = inserisciPartecipanteProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
		
	}

	@Override  
	public boolean aggiornaRuoloCollaboratore(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		aggiornaRuoloCollaboratorePS.setObject(1, collaborazioneProgetto.getRuoloCollaboratore(),Types.OTHER);
		aggiornaRuoloCollaboratorePS.setString(2, collaborazioneProgetto.getCollaboratore().getCf());
		aggiornaRuoloCollaboratorePS.setInt(3, collaborazioneProgetto.getProgetto().getIdProgettto());
		
		int record = aggiornaRuoloCollaboratorePS.executeUpdate();

		if(record==1)
			return true;
		else
			return false;
		
	}

	@Override 
	public ArrayList<Progetto> ottieniProgettiSegreteriaFiltratiPerNome(String nomeCercato) throws SQLException {
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		ottieniProgettiSegreteriaFiltratiPerNomePS.setString(1, nomeCercato);
		ResultSet risultato = ottieniProgettiSegreteriaFiltratiPerNomePS.executeQuery();
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while (risultato.next()) {
			LocalDate dataTerminazione = null;
			if (risultato.getDate("DataTerminazione") != null)
				dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					dataTerminazione);
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(projTemp);
			projTemp.setMeetingsRelativi(ottieniMeetingRelativiProgetto(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}

	@Override
	public String ottieniCFProjectManager(Progetto progetto) throws SQLException {
		String cf;
		projectManagerPS.setInt(1, progetto.getIdProgettto());
		ResultSet risultato=projectManagerPS.executeQuery();
		risultato.next();
		
		cf = risultato.getString("cf");
		
		risultato.close();
		
		return cf;
	}

	@Override  
	public ArrayList<Progetto> ottieniProgettiDipendentePerNome(String nomeCercato, Dipendente dipendente) throws SQLException {
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		ottieniProgettiDipendentiPerNomePS.setString(1, dipendente.getCf());
		ottieniProgettiDipendentiPerNomePS.setString(2, nomeCercato);
		ResultSet risultato = ottieniProgettiDipendentiPerNomePS.executeQuery();
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while (risultato.next()) {
			LocalDate dataTerminazione = null;
			if (risultato.getDate("DataTerminazione") != null)
				dataTerminazione = new LocalDate(risultato.getDate("DataTerminazione"));
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					dataTerminazione);
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.ottieniAmbitiDelProgetto(projTemp);
			projTemp.setMeetingsRelativi(ottieniMeetingRelativiProgetto(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}


}

