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

	//ATTRIBUTI
	//----------------------------------------
	private Connection  connection;	//connessione al DB
	//dao necessari
	private DipendenteDAO dipDAO;
	private AmbitoProgettoDAO ambitoDAO;
	
	//PreparedStatement per ogni query
	private PreparedStatement getProgettiPS,
	getPartecipantiPS,
	getProgettiByDipendentePS,
	getProgettiByAmbitoPS,
	getProgettiByTipoPS,
	addProgettoPS,
	deleteProgettoPS,
	addPartecipantePS,
	deletePartecipantePS,
	aggiornaRuoloCollaboratorePS,
	updateProgettoPS,
	getMeetingRelativiPS,
	getProgettoByCodPS,
	getTipologiePS,
	getRuoliDipendentiPS,
	getCodProgettoPS,
	getProgettoInseritoPS;
	
	//METODI
	//----------------------------------------
	
	//Costruttore del DAO
	public ProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		//inizializza i PreparedStatement
		getProgettiPS = connection.prepareStatement("SELECT * FROM Progetto");
		getPartecipantiPS = connection.prepareStatement("SELECT * FROM Partecipazione AS p WHERE p.CodProgetto = ?"); //? = codice del progetto di cui si vogliono i partecipanti 
		getProgettiByDipendentePS = connection.prepareStatement("SELECT * FROM Progetto AS p NATURAL JOIN Partecipazione AS par WHERE par.CF = ?"); //? = codice fiscale del dipendente di cui si vogliono i progetti a cui partecipa
		getProgettiByAmbitoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.CodProgetto IN (SELECT a.CodProgetto FROM AmbitoProgettoLink AS a WHERE a.IDAmbito = ?)");	//? = nome dell'ambito con cui filtrare i progetti
		getProgettiByTipoPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.TipoProgetto = ?"); //? = tipo di progetti che si cercano
		addProgettoPS = connection.prepareStatement("INSERT INTO Progetto (NomeProgetto,TipoProgetto,DescrizioneProgetto,DataCreazione,DataScadenza,DataTerminazione) VALUES (?,?,?,?,?,?)");
		deleteProgettoPS = connection.prepareStatement("DELETE FROM Progetto AS p WHERE p.CodProgetto = ?"); //? = codice del progetto da eliminare
		addPartecipantePS = connection.prepareStatement("INSERT INTO Partecipazione VALUES (?,?,?)"); //? = codice del progetto, ? = codice fiscale del partecipante, ? = ruolo
		deletePartecipantePS = connection.prepareStatement("DELETE FROM Partecipazione WHERE CF = ? AND CodProgetto = ?"); //? = codice fiscale del dipendente da rimuovere dalla partecipazione, ? = codice progetto da cui rimuoverlo
		aggiornaRuoloCollaboratorePS=connection.prepareStatement("UPDATE Partecipazione SET RuoloDipendente=? WHERE CF=? AND CodProgetto=?");
		updateProgettoPS = connection.prepareStatement("UPDATE Progetto SET NomeProgetto = ?, TipoProgetto = ?, DescrizioneProgetto = ?,  DataScadenza = ?, DataTerminazione = ? WHERE CodProgetto = ?");
		getMeetingRelativiPS = connection.prepareStatement("SELECT * FROM Meeting WHERE Meeting.CodProgetto = ?"); //? = codice del progetto di cui si vogliono i meeting
		getProgettoByCodPS = connection.prepareStatement("SELECT * FROM Progetto AS p WHERE p.CodProgetto = ?");	//? = codice progetto
		getTipologiePS = connection.prepareStatement("SELECT unnest(enum_range(NULL::tipologia))");
		getRuoliDipendentiPS=connection.prepareStatement("SELECT unnest(enum_range(NULL::ruolo))");
		getCodProgettoPS = connection.prepareStatement("SELECT codprogetto FROM progetto WHERE nomeprogetto = ? AND datacreazione =?");
		getProgettoInseritoPS=connection.prepareStatement("SELECT nomeProgetto,tipoProgetto,descrizioneProgetto,dataCreazione,datascadenza FROM Progetto WHERE NomeProgetto= ?");
	}

	//Metodo che restituisce una lista di tutti i progetti dell'azienda
	@Override
	public ArrayList<Progetto> getProgetti() throws SQLException {
		ArrayList<Progetto> temp = new ArrayList<Progetto>();	//inizializza la lista da restituire dopo
		
		ResultSet risultato = getProgettiPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		//finchè il ResultSet contiene record
		while (risultato.next()) {
			
			//crea l'oggetto progetto
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza")),
					new LocalDate(risultato.getDate("DataTerminazione")));
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiProgetto(projTemp);	//ottiene gli ambiti del progetto
			projTemp.setDiscussoIn(getMeetingRelativi(projTemp.getIdProgettto()));
			projTemp.setAmbiti(ambiti);
			
			temp.add(projTemp);	//aggiunge il progetto alla lista
		}
		risultato.close();	//chiude il ResulSet
		
		return temp;
	}
	
	//METODO DI PROVA
	//Metodo che restituisce i dipendenti partecipanti a un progetto senza specificare il ruolo.
		@Override
		public ArrayList<Dipendente> getPartecipantiSenzaRuolo(int codiceProgetto) throws SQLException {
			ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista da restituire
			
			dipDAO = new DipendenteDAOPSQL(connection);
			
			getPartecipantiPS.setInt(1, codiceProgetto); 	//inserisce il codice del progetto nella query
			
			ResultSet risultato = getPartecipantiPS.executeQuery();	//esegue la query e ottiene il ResultSet
			
			//finchè ci sono record nel ResultSet
			while(risultato.next()) {
				Dipendente partecipante = dipDAO.getDipendenteByCF(risultato.getString("CF"));
				temp.add(partecipante);	//aggiunge il dipendente alla lista
			}
			risultato.close();	//chiude il ResultSet
			
			return temp;
		}
	
	//Metodo che restituisce i dipendenti partecipanti a un progetto.
	@Override
	public ArrayList<CollaborazioneProgetto> getPartecipanti(int codiceProgetto) throws SQLException {
		ArrayList<CollaborazioneProgetto> temp = new ArrayList<CollaborazioneProgetto>();	//inizializza la lista da restituire
		
		dipDAO = new DipendenteDAOPSQL(connection);
		
		getPartecipantiPS.setInt(1, codiceProgetto); 	//inserisce il codice del progetto nella query
		
		ResultSet risultato = getPartecipantiPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		//finchè ci sono record nel ResultSet
		while(risultato.next()) {
			Dipendente partecipante = dipDAO.getDipendenteByCF(risultato.getString("CF"));
			CollaborazioneProgetto tempPartecipazione = new CollaborazioneProgetto(getProgettoByCod(codiceProgetto), partecipante, risultato.getString("RuoloDipendente"));
			temp.add(tempPartecipazione);	//aggiunge il dipendente alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo che restituisce i meeting relativi a un progetto.
	@Override
	public ArrayList<Meeting> getMeetingRelativi(int codiceProgettoSelezionato) throws SQLException {
		ArrayList<Meeting> temp = new ArrayList<Meeting>();	//inizializza la lista da restituire
		
		getMeetingRelativiPS.setInt(1, codiceProgettoSelezionato); //codice del progetto di cui si vogliono i meeting nella query
		
		ResultSet risultato = getMeetingRelativiPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		dipDAO = new DipendenteDAOPSQL(connection);
		SalaRiunioneDAO salaDAO = new SalaRiunioneDAOPSQL(connection);
		
		//finchè ci sono record nel ResutlSet
		while (risultato.next()) {
			Meeting meetingTemp = new Meeting(risultato.getInt("IDMeeting"),
					new LocalDate(risultato.getDate("DataInizio").getTime()),
					new LocalDate(risultato.getDate("DataFine").getTime()),
					new LocalTime(risultato.getTime("OrarioInizio").getTime()),
					new LocalTime(risultato.getTime("OrarioFine").getTime()),
					risultato.getString("Modalità"),
					risultato.getString("Piattaforma"),
					salaDAO.getSalaByCod(risultato.getString("CodSala")));
					
					
					//imposta proj come progetto relativo
					meetingTemp.setProgettoDiscusso(getProgettoByCod(risultato.getInt("CodProgetto")));
			
			temp.add(meetingTemp);
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo che restituisce i progetti a cui partecipa/ha partecipato un dipendente.
	@Override
	public ArrayList<CollaborazioneProgetto> getProgettiByDipendente(Dipendente dip) throws SQLException {
		getProgettiByDipendentePS.setString(1, dip.getCf()); 	//inserisce il codice fiscale del dipendente di cui si vogliono i progetti
		
		ArrayList<CollaborazioneProgetto> temp = new ArrayList<CollaborazioneProgetto>();	//inizializza la lista di progetti da restituire
		
		ResultSet risultato = getProgettiByDipendentePS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		//finchè ci sono record nel ResultSet
		while(risultato.next()) {
			//crea l'oggetto progetto
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),
					risultato.getString("NomeProgetto"),
					risultato.getString("TipoProgetto"),
					risultato.getString("DescrizioneProgetto"),
					new LocalDate(risultato.getDate("DataCreazione")),
					new LocalDate(risultato.getDate("DataScadenza"))
					);
			
			if(risultato.getDate("DataTerminazione")!=null){
				
				projTemp.setDataTerminazione(new LocalDate(risultato.getDate("DataTerminazione")));
			}
			else {
				
				projTemp.setDataTerminazione(null);
			}
			
			
			projTemp.setMeetingsRelativi(getMeetingRelativi(risultato.getInt("CodProgetto")));
			projTemp.setCollaborazioni(getPartecipanti(risultato.getInt("CodProgetto")));
			projTemp.setComprende(getPartecipantiSenzaRuolo(risultato.getInt("CodProgetto")));
			
			
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiProgetto(projTemp);	//ottiene gli ambiti del progetto
			projTemp.setAmbiti(ambiti);
			
			CollaborazioneProgetto tempPartecipazione = new CollaborazioneProgetto(projTemp,dip,risultato.getString("RuoloDipendente"));
			

			
			temp.add(tempPartecipazione);	//aggiunge il progetto alla lista
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

	//Metodo che restituisce i progetti con un certo ambito.
	@Override
	public ArrayList<Progetto> getProgettiByAmbito(AmbitoProgetto ambito) throws SQLException {		
		getProgettiByAmbitoPS.setInt(1, ambito.getIdAmbito());	//inserisce l'id dell'ambito nella query
		
		ArrayList<Progetto> temp = new ArrayList<Progetto>();	//inizializza la lista da restituire alla fine
		
		ResultSet risultato = getProgettiByAmbitoPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		dipDAO = new DipendenteDAOPSQL(connection);
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		//finchè ci sono record nel ResultSet
		while(risultato.next()) {
			//crea l'oggetto progetto
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),risultato.getString("NomeProgetto"),risultato.getString("TipoProgetto"),risultato.getString("DescrizioneProgetto"),new LocalDate(risultato.getDate("DataCreazione")), new LocalDate(risultato.getDate("DataScadenza")), new LocalDate(risultato.getDate("DataTerminazione")));
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiProgetto(projTemp);	//ottiene gli ambiti del progetto
			projTemp.setAmbiti(ambiti);
			temp.add(projTemp);	//aggiunge il progetto alla lista
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

	//Metodo che restituisce i progetti di un certo tipo.
	@Override
	public ArrayList<Progetto> getProgettiByTipo(String tipologia) throws SQLException {
		getProgettiByTipoPS.setString(1, tipologia); //inserisce la tipologia nella query
		
		ArrayList<Progetto> temp = new ArrayList<Progetto>();	//inizializza la lista da restituire alla fine
		
		ResultSet risultato = getProgettiByAmbitoPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		dipDAO = new DipendenteDAOPSQL(connection);
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		//finchè ci sono record nel ResultSet
		while(risultato.next()) {
			//crea l'oggetto progetto
			Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),risultato.getString("NomeProgetto"),risultato.getString("TipoProgetto"),risultato.getString("DescrizioneProgetto"),new LocalDate(risultato.getDate("DataCreazione")), new LocalDate(risultato.getDate("DataScadenza")), new LocalDate(risultato.getDate("DataTerminazione")));
			ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiProgetto(projTemp);	//ottiene gli ambiti del progetto
			projTemp.setAmbiti(ambiti);
			temp.add(projTemp);	//aggiunge il progetto alla lista
		}
		risultato.close(); //chiude il ResultSet
			
		return temp;
	}

	//Metodo che aggiunge un progetto al DB.
	@Override
	public boolean addProgetto(Progetto proj) throws SQLException {
		addProgettoPS.setString(1, proj.getNomeProgetto()); 	//nome del progetto
		addProgettoPS.setObject(2, proj.getTipoProgetto(), Types.OTHER); 	//tipologia del progetto
		addProgettoPS.setString(3, proj.getDescrizioneProgetto()); 	//descrizione del progetto
		addProgettoPS.setDate(4, new Date(proj.getDataCreazione().toDateTimeAtStartOfDay().getMillis())); //data creazione
		//data scadenza
		if (proj.getScadenza() != null)
			addProgettoPS.setDate(5, new Date(proj.getScadenza().toDateTimeAtStartOfDay().getMillis()));
		else
			addProgettoPS.setNull(5, Types.DATE);
		
		if (proj.getDataTerminazione() != null)
			addProgettoPS.setDate(6, new Date(proj.getDataTerminazione().toDateTimeAtStartOfDay().getMillis()));
		else
			addProgettoPS.setNull(6, Types.DATE);
		
		//se il progetto ha degli ambiti allora li inserisce nel DB
		if (!proj.getAmbiti().isEmpty()) {
			ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
			
			ambitoDAO.addAmbitiProgetto(proj);
		}
		
		int record = addProgettoPS.executeUpdate();	//esegue l'insert e salva il numero di record aggiunti (1=aggiunto,0=non aggiunto)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che rimuove un progetto dal DB.
	@Override
	public boolean removeProgetto(Progetto proj) throws SQLException {
		deleteProgettoPS.setInt(1, proj.getIdProgettto()); 	//inserisce il codice del progetto da eliminare nella delete
		
		int record = deleteProgettoPS.executeUpdate();	//esegue la delete e salva il numero di record eliminati (1=eliminato,0=non eliminato)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che inserisce un partecipante al progetto nel DB.
	@Override
	public boolean addPartecipante(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		addPartecipantePS.setInt(1, collaborazioneProgetto.getProgetto().getIdProgettto()); 	//codice del progetto
		addPartecipantePS.setString(2, collaborazioneProgetto.getCollaboratore().getCf()); 	//codice fiscale del partecipante
		addPartecipantePS.setObject(3, collaborazioneProgetto.getRuolo(),Types.OTHER); 	//ruolo del partecipante
		
		int record = addPartecipantePS.executeUpdate();	//esegue l'insert e salva il numero di record inseriti (1=inserito,0=non inserito)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che rimuove un partecipante da un progetto.
	@Override
	public boolean deletePartecipante(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		//TODO: farlo solo se il dipendente non è project manager oppure controllare i trigger
		deletePartecipantePS.setString(1, collaborazioneProgetto.getCollaboratore().getCf()); 	//codice fiscale del dipendente
		deletePartecipantePS.setInt(2, collaborazioneProgetto.getProgetto().getIdProgettto()); 	//codice del progetto
		
		int record = deletePartecipantePS.executeUpdate();	//esegue la delete e salva il numero di record eliminati (1=eliminato,0=non eliminato)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che aggiorna un progetto nel DB.

	@Override
	public boolean updateProgetto(Progetto proj) throws SQLException {
		updateProgettoPS.setString(1, proj.getNomeProgetto()); //nome progetto
		updateProgettoPS.setObject(2, proj.getTipoProgetto(),Types.OTHER); //tipo progetto
		updateProgettoPS.setString(3, proj.getDescrizioneProgetto()); //descrizione progetto
		if (proj.getScadenza() != null)
			updateProgettoPS.setDate(4, new Date(proj.getScadenza().toDateTimeAtStartOfDay().getMillis())); //data scadenza progetto
		else
			updateProgettoPS.setNull(4, Types.DATE);
		if (proj.getDataTerminazione() != null) 
			updateProgettoPS.setDate(5, new Date(proj.getDataTerminazione().toDateTimeAtStartOfDay().getMillis())); //data terminazione
		else
			updateProgettoPS.setNull(5,Types.DATE);
		updateProgettoPS.setInt(6, proj.getIdProgettto()); //codice del progetto da modificare
		
		int record = updateProgettoPS.executeUpdate();	//esegue l'update e salva il numero di record modificati
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che ottiene un progetto dal DB partendo dal suo codice.
	@Override
	public Progetto getProgettoByCod(int codProgetto) throws SQLException {
		getProgettoByCodPS.setInt(1, codProgetto);
		
		ResultSet risultato = getProgettoByCodPS.executeQuery();
		
		risultato.next();
		
		dipDAO = new DipendenteDAOPSQL(connection);
		ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
		
		//crea l'oggetto progetto
		Progetto projTemp = new Progetto(risultato.getInt("CodProgetto"),risultato.getString("NomeProgetto"),risultato.getString("TipoProgetto"),risultato.getString("DescrizioneProgetto"),new LocalDate(risultato.getDate("DataCreazione")), new LocalDate(risultato.getDate("DataScadenza")), new LocalDate(risultato.getDate("DataTerminazione")));
		ArrayList<AmbitoProgetto> ambiti = ambitoDAO.getAmbitiProgetto(projTemp);	//ottiene gli ambiti del progetto
		projTemp.setAmbiti(ambiti);
		
		risultato.close(); //chiude il ResultSet
		
		return projTemp;
	}

	//Metodo che restituisce le tipologie nel database
	@Override
	public ArrayList<String> getTipologie() throws SQLException {
		ArrayList<String> temp = new ArrayList<String>();	//inizializza la lista temporanea da restituire
		
		ResultSet risultato = getTipologiePS.executeQuery();	//esegue la query
		
		while (risultato.next())
			temp.add(risultato.getString(1));	//aggiunge i risultati alla lista
		
		risultato.close(); //chiude il result set
		
		return temp;
	}
	
	//Metodo che restituisce il codice del progetto
	@Override
	public int getCodProgetto(Progetto proj) throws SQLException
	{
		int codProgetto;
		
		getCodProgettoPS.setString(1, proj.getNomeProgetto());
		getCodProgettoPS.setDate(2, new Date(proj.getDataCreazione().toDateTimeAtStartOfDay().getMillis()));
		
		ResultSet risultato = getCodProgettoPS.executeQuery();
		
		risultato.next();
		
		codProgetto = risultato.getInt("codprogetto");
		
		return codProgetto;
	}
	
	//Metodo che rimuove un progetto dal DB prendendo in input solo il codice




	@Override
	public ArrayList<String> getRuoliDipendenti() throws SQLException {
		ArrayList<String> temp = new ArrayList<String>();
		ResultSet risultato = getRuoliDipendentiPS.executeQuery();
		
		while (risultato.next())
			temp.add(risultato.getString(1));
		
		risultato.close();
		
		return temp;
	}

	@Override
	public Progetto getProgettoInserito(String nomeProgetto) throws SQLException {
		
		Progetto progetto=null;
		
		getProgettoInseritoPS.setString(1,nomeProgetto);
		
		ResultSet risultato=getProgettoInseritoPS.executeQuery();
		risultato.next();
		
		progetto=new Progetto(risultato.getString("nomeProgetto"),
				risultato.getString("tipoProgetto"), 
				risultato.getString("descrizioneProgetto"),	
				new LocalDate(risultato.getDate("DataCreazione")),
				new LocalDate(risultato.getDate("DataScadenza")));
		
		return progetto;
	}

	@Override
	public boolean addProjectManager(String cf, Progetto tmp, String ruolo) throws SQLException {
		
		addPartecipantePS.setInt(1, tmp.getIdProgettto()); 	//codice del progetto
		addPartecipantePS.setString(2, cf); 	//codice fiscale del partecipante
		addPartecipantePS.setObject(3, ruolo,Types.OTHER); 	//ruolo del partecipante
		
		int record = addPartecipantePS.executeUpdate();	//esegue l'insert e salva il numero di record inseriti (1=inserito,0=non inserito)
		
		if (record == 1)
			return true;
		else
			return false;
		
	}

	@Override
	public boolean aggiornaPartecipante(CollaborazioneProgetto collaborazioneProgetto) throws SQLException {
		
		aggiornaRuoloCollaboratorePS.setObject(1, collaborazioneProgetto.getRuolo(),Types.OTHER);
		aggiornaRuoloCollaboratorePS.setString(2, collaborazioneProgetto.getCollaboratore().getCf());
		aggiornaRuoloCollaboratorePS.setInt(3, collaborazioneProgetto.getProgetto().getIdProgettto());
		
		int record=aggiornaRuoloCollaboratorePS.executeUpdate();

		if(record==1)
			return true;
		else
			return false;
		
	}
	

	}

