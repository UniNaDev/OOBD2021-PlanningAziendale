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
	
	private PreparedStatement getProgettiPS,
	getProgettiByNomePS,
	getProgettiDipendenteByNomePS,
	getPartecipantiPS,
	getProgettiByDipendentePS,
	getProgettiByAmbitoPS,
	getProgettiByTipoPS,
	addProgettoPS,
	deleteProgettoPS,
	addPartecipantePS,
	deletePartecipantePS,
	projectManagerPS,
	aggiornaRuoloCollaboratorePS,
	updateProgettoPS,
	getMeetingRelativiPS,
	getProgettoByCodPS,
	getTipologiePS,
	getRuoliDipendentiPS,
	getCodProgettoPS;

	public ProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;

		getProgettiPS = connection.prepareStatement("SELECT * FROM Progetto");
		getProgettiByNomePS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.NomeProgetto ILIKE '%' || ? || '%'");
		getProgettiDipendenteByNomePS=connection.prepareStatement("SELECT * FROM Progetto AS p NATURAL JOIN Partecipazione AS par WHERE par.CF = ? AND p.NomeProgetto ILIKE '%' || ? || '%'");
		getPartecipantiPS = connection.prepareStatement("SELECT * FROM Partecipazione AS p WHERE p.CodProgetto = ?");
		getProgettiByDipendentePS = connection.prepareStatement("SELECT * FROM Progetto AS p NATURAL JOIN Partecipazione AS par WHERE par.CF = ?");
		getProgettiByAmbitoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.CodProgetto IN (SELECT a.CodProgetto FROM AmbitoProgettoLink AS a WHERE a.IDAmbito = ?)");
		getProgettiByTipoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.TipoProgetto = ?");
		addProgettoPS = connection.prepareStatement("INSERT INTO Progetto (NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione) VALUES (?,?,?,?,?,?)");
		deleteProgettoPS = connection.prepareStatement("DELETE FROM Progetto AS p WHERE p.CodProgetto = ?");
		addPartecipantePS = connection.prepareStatement("INSERT INTO Partecipazione VALUES (?,?,?)");
		deletePartecipantePS = connection.prepareStatement("DELETE FROM Partecipazione WHERE CF = ? AND CodProgetto = ?");
		projectManagerPS = connection.prepareStatement("SELECT CF FROM Dipendente NATURAL JOIN Partecipazione WHERE CodProgetto = ? AND RuoloDipendente = 'Project Manager'");
		aggiornaRuoloCollaboratorePS=connection.prepareStatement("UPDATE Partecipazione SET RuoloDipendente=? WHERE CF=? AND CodProgetto=?");
		updateProgettoPS = connection.prepareStatement("UPDATE Progetto SET NomeProgetto = ?, TipoProgetto = ?, DescrizioneProgetto = ?,  DataScadenza = ?, DataTerminazione = ? WHERE CodProgetto = ?");
		getMeetingRelativiPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.CodProgetto = ?");
		getProgettoByCodPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.CodProgetto = ?");
		getTipologiePS = connection.prepareStatement("SELECT unnest(enum_range(NULL::tipologia))");
		getRuoliDipendentiPS=connection.prepareStatement("SELECT unnest(enum_range(NULL::ruolo))");
		getCodProgettoPS = connection.prepareStatement("SELECT codprogetto FROM progetto WHERE nomeprogetto = ?");
		connection.prepareStatement("SELECT nomeProgetto,tipoProgetto,descrizioneProgetto,dataCreazione,datascadenza FROM Progetto WHERE NomeProgetto= ?");
	}

	@Override
	public ArrayList<Progetto> getProgetti() throws SQLException {
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		ResultSet risultato = getProgettiPS.executeQuery();
		
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
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
			projTemp.setMeetingsRelativi(getMeetingRelativiProgetto(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}
	
	@Override
	public ArrayList<Dipendente> getPartecipantiProgettoSenzaRuolo(int codiceProgetto) throws SQLException {
		ArrayList<Dipendente> partecipanti = new ArrayList<Dipendente>();
		dipDAO = new DipendenteDAOPSQL(connection);
		getPartecipantiPS.setInt(1, codiceProgetto);
		
		ResultSet risultato = getPartecipantiPS.executeQuery();
		
		while(risultato.next()) {
			Dipendente partecipanteTemp = dipDAO.getDipendenteByCF(risultato.getString("CF"));
			partecipanti.add(partecipanteTemp);
		}
		risultato.close();
		
		return partecipanti;
	}
	
	@Override
	public ArrayList<CollaborazioneProgetto> getPartecipantiProgetto(int codiceProgetto) throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();
		dipDAO = new DipendenteDAOPSQL(connection);
		getPartecipantiPS.setInt(1, codiceProgetto);
		
		ResultSet risultato = getPartecipantiPS.executeQuery();
		
		while(risultato.next()) {
			Dipendente partecipanteTemp = dipDAO.getDipendenteByCF(risultato.getString("CF"));
			CollaborazioneProgetto partecipazioneTemp = new CollaborazioneProgetto(getProgettoByCod(codiceProgetto), partecipanteTemp, risultato.getString("RuoloDipendente"));
			collaborazioni.add(partecipazioneTemp);
		}
		risultato.close();
		
		return collaborazioni;
	}

	@Override
	public ArrayList<Meeting> getMeetingRelativiProgetto(int codiceProgettoSelezionato) throws SQLException {
		ArrayList<Meeting> meetingsRelativi = new ArrayList<Meeting>();
		getMeetingRelativiPS.setInt(1, codiceProgettoSelezionato);
		
		ResultSet risultato = getMeetingRelativiPS.executeQuery();
		
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
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
					
					meetingTemp.setProgettoDiscusso(getProgettoByCod(risultato.getInt("CodProgetto")));
			
			meetingsRelativi.add(meetingTemp);
		}
		risultato.close();
		
		return meetingsRelativi;
	}

	@Override
	public ArrayList<CollaborazioneProgetto> getProgettiByDipendente(Dipendente dipendente) throws SQLException {
		getProgettiByDipendentePS.setString(1, dipendente.getCf());
		ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();
		
		ResultSet risultato = getProgettiByDipendentePS.executeQuery();
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza"))
					);
			
			if(risultato.getDate("DataTerminazione") != null){
				projTemp.setDataTerminazione(new LocalDate(risultato.getDate("DataTerminazione")));
			}
			else {
				projTemp.setDataTerminazione(null);
			}
	
			projTemp.setMeetingsRelativi(getMeetingRelativiProgetto(risultato.getInt("CodProgetto")));
			projTemp.setCollaborazioni(getPartecipantiProgetto(risultato.getInt("CodProgetto")));
			projTemp.setComprende(getPartecipantiProgettoSenzaRuolo(risultato.getInt("CodProgetto")));
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
			projTemp.setAmbiti(ambiti);
			
			CollaborazioneProgetto partecipazioneTemp = new CollaborazioneProgetto(projTemp,dipendente,risultato.getString("RuoloDipendente"));
			
			collaborazioni.add(partecipazioneTemp);
		}
		risultato.close();
		
		return collaborazioni;
	}

	@Override
	public ArrayList<Progetto> getProgettiByAmbito(AmbitoProgetto ambito) throws SQLException {		
		getProgettiByAmbitoPS.setInt(1, ambito.getIdAmbito());
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		
		ResultSet risultato = getProgettiByAmbitoPS.executeQuery();
		
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
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
			projTemp.setAmbiti(ambiti);
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}

	@Override
	public ArrayList<Progetto> getProgettiByTipo(String tipologia) throws SQLException {		
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		getProgettiByTipoPS.setObject(1, tipologia, Types.OTHER);
		
		ResultSet risultato = getProgettiByTipoPS.executeQuery();
		
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
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
			projTemp.setAmbiti(ambiti);
			progetti.add(projTemp);
		}
		risultato.close();
			
		return progetti;
	}

	@Override
	public boolean insertProgetto(Progetto proj) throws SQLException {
		addProgettoPS.setString(1, proj.getNomeProgetto());
		addProgettoPS.setObject(2, proj.getTipoProgetto(), Types.OTHER);
		addProgettoPS.setString(3, proj.getDescrizioneProgetto());
		addProgettoPS.setDate(4, new Date(proj.getDataCreazione().toDateTimeAtStartOfDay().getMillis()));
		if (proj.getScadenza() != null)
			addProgettoPS.setDate(5, new Date(proj.getScadenza().toDateTimeAtStartOfDay().getMillis()));
		else
			addProgettoPS.setNull(5, Types.DATE);
		if (proj.getDataTerminazione() != null)
			addProgettoPS.setDate(6, new Date(proj.getDataTerminazione().toDateTimeAtStartOfDay().getMillis()));
		else
			addProgettoPS.setNull(6, Types.DATE);
		
		int record = addProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteProgetto(Progetto proj) throws SQLException {
		deleteProgettoPS.setInt(1, proj.getIdProgettto());
		
		int record = deleteProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean insertPartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		addPartecipantePS.setInt(1, collaborazioneProgetto.getProgetto().getIdProgettto());
		addPartecipantePS.setString(2, collaborazioneProgetto.getCollaboratore().getCf());
		addPartecipantePS.setObject(3, collaborazioneProgetto.getRuoloCollaboratore(),Types.OTHER);
		
		int record = addPartecipantePS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean deletePartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		//TODO: farlo solo se il dipendente non è project manager oppure controllare i trigger
		deletePartecipantePS.setString(1, collaborazioneProgetto.getCollaboratore().getCf());
		deletePartecipantePS.setInt(2, collaborazioneProgetto.getProgetto().getIdProgettto());
		
		int record = deletePartecipantePS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateProgetto(Progetto proj) throws SQLException {
		updateProgettoPS.setString(1, proj.getNomeProgetto());
		updateProgettoPS.setObject(2, proj.getTipoProgetto(),Types.OTHER);
		updateProgettoPS.setString(3, proj.getDescrizioneProgetto());
		if (proj.getScadenza() != null)
			updateProgettoPS.setDate(4, new Date(proj.getScadenza().toDateTimeAtStartOfDay().getMillis()));
		else
			updateProgettoPS.setNull(4, Types.DATE);
		if (proj.getDataTerminazione() != null) 
			updateProgettoPS.setDate(5, new Date(proj.getDataTerminazione().toDateTimeAtStartOfDay().getMillis()));
		else
			updateProgettoPS.setNull(5,Types.DATE);
		updateProgettoPS.setInt(6, proj.getIdProgettto());
		
		int record = updateProgettoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public Progetto getProgettoByCod(int codProgetto) throws SQLException {
		getProgettoByCodPS.setInt(1, codProgetto);
		ResultSet risultato = getProgettoByCodPS.executeQuery();
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
		ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
		projTemp.setAmbiti(ambiti);
		
		risultato.close();
		
		return projTemp;
	}

	@Override
	public ArrayList<String> getTipologie() throws SQLException {
		ArrayList<String> tipologie = new ArrayList<String>();
		
		ResultSet risultato = getTipologiePS.executeQuery();
		
		while (risultato.next())
			tipologie.add(risultato.getString(1));
		
		risultato.close();
		
		return tipologie;
	}
	
	@Override
	public int getCodProgetto(Progetto proj) throws SQLException {
		int codProgetto;
		
		getCodProgettoPS.setString(1, proj.getNomeProgetto());
		
		ResultSet risultato = getCodProgettoPS.executeQuery();
		
		risultato.next();
		
		codProgetto = risultato.getInt("CodProgetto");
		
		return codProgetto;
	}
	
	@Override
	public ArrayList<String> getRuoliDipendenti() throws SQLException {
		ArrayList<String> ruoli = new ArrayList<String>();
		ResultSet risultato = getRuoliDipendentiPS.executeQuery();
		
		while (risultato.next())
			ruoli.add(risultato.getString(1));
		
		risultato.close();
		
		return ruoli;
	}

	@Override
	public boolean insertProjectManager(String cf, Progetto progetto, String ruolo) throws SQLException {
		addPartecipantePS.setInt(1, progetto.getIdProgettto());
		addPartecipantePS.setString(2, cf);
		addPartecipantePS.setObject(3, ruolo,Types.OTHER);
		
		int record = addPartecipantePS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
		
	}

	@Override
	public boolean updatePartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
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
	public ArrayList<Progetto> getProgettiByNome(String nomeCercato) throws SQLException {
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		getProgettiByNomePS.setString(1, nomeCercato);
		ResultSet risultato = getProgettiByNomePS.executeQuery();
		
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
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
			projTemp.setMeetingsRelativi(getMeetingRelativiProgetto(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}

	@Override
	public String getCFProjectManager(Progetto progetto) throws SQLException {
		String cf;
		projectManagerPS.setInt(1, progetto.getIdProgettto());
		ResultSet risultato=projectManagerPS.executeQuery();
		risultato.next();
		
		cf = risultato.getString("cf");
		
		risultato.close();
		
		return cf;
	}

	@Override
	public ArrayList<Progetto> getProgettiDipendenteByNome(String nomeCercato, Dipendente dipendente) throws SQLException {
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		getProgettiDipendenteByNomePS.setString(1, dipendente.getCf());
		getProgettiDipendenteByNomePS.setString(2, nomeCercato);
		ResultSet risultato = getProgettiDipendenteByNomePS.executeQuery();
		
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
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiOfProgetto(projTemp);
			projTemp.setMeetingsRelativi(getMeetingRelativiProgetto(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			progetti.add(projTemp);
		}
		risultato.close();
		
		return progetti;
	}

	@Override
	public ArrayList<CollaborazioneProgetto> getPartecipanti(int codiceProgetto) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addProjectManager(String cf, Progetto tmp, String string) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean aggiornaPartecipante(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String ottieniProjectManager(Progetto progetto) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}

