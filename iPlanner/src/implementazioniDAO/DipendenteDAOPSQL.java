/*Implementazione dell'interfaccia DipendenteDAO per PostgreSQL.
*Contiene le definizioni dei metodi già presenti nell'interfaccia per operare sulla tabella
*Dipendente del DB.
******************************************************************************************/

package implementazioniDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.print.attribute.standard.MediaSize.Other;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Meeting;
import entita.Progetto;
import entita.Skill;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SkillDAO;

public class DipendenteDAOPSQL implements DipendenteDAO {
	private Connection connection;
	
	private PreparedStatement 
		getDipendentiNonInvitatiPS,
		getDipendentiPS,
		getDipendentiNonPartecipantiPS,
		getTipologieProgettoDipendentePS,
		getValutazionePS,
		addDipendentePS,
		updateDipendentePS,
		deleteDipendentePS,
		loginCheckPS,
		getDipendenteByCFPS,
		getMaxSalarioPS,
		getDipendentiFiltratiPS,
		getDipendentiNonPartecipantiFiltratiPS,
		getDipendenteBySkillPS,
		getDipendentiNonPartecipantiBySkillPS,
		getDipendentiNonPartecipantiByTipologiaProgetto;
	
	private LuogoNascitaDAOPSQL luogoDAO = null;
	private MeetingDAO meetDAO = null;
	private ProgettoDAO projDAO = null;
	private SkillDAO skillDAO = null;
	
	public DipendenteDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		this.meetDAO=new MeetingDAOPSQL(connection);
		this.luogoDAO = new LuogoNascitaDAOPSQL(connection);
		this.skillDAO = new SkillDAOPSQL(connection);
		
		getDipendentiPS = connection.prepareStatement("SELECT * FROM Dipendente");
		getDipendentiNonPartecipantiPS=connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ? )");
		getTipologieProgettoDipendentePS=connection.prepareStatement("SELECT DISTINCT tipoprogetto FROM Progetto NATURAL JOIN Partecipazione WHERE CF ILIKE ?");
		getDipendentiNonInvitatiPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.cf NOT IN(SELECT pre.cf FROM Meeting NATURAL JOIN Presenza AS pre WHERE pre.idMeeting=?)");
		getValutazionePS = connection.prepareStatement("SELECT Valutazione(?)");
		addDipendentePS = connection.prepareStatement("INSERT INTO Dipendente VALUES (?,?,?,?,?,?,?,?,?,?,?, ?)");
		updateDipendentePS = connection.prepareStatement("UPDATE Dipendente SET CF = ?, Nome = ?, Cognome = ?, Sesso = ?, DataNascita = ?, Indirizzo = ?, Email = ?, TelefonoCasa = ?, Cellulare = ?, Salario = ?, Password = ?, CodComune = ? WHERE CF = ?");
		deleteDipendentePS = connection.prepareStatement("DELETE FROM Dipendente WHERE CF = ?");
		loginCheckPS = connection.prepareStatement("SELECT * FROM Dipendente WHERE Email ILIKE ? AND Password = ?");
		getDipendenteByCFPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF = ?");
		getMaxSalarioPS = connection.prepareStatement("SELECT MAX(Salario) FROM Dipendente");

		getDipendentiFiltratiPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE (d.Nome LIKE '%' || ? || '%' OR d.Cognome LIKE '%' || ? || '%' OR d.Email LIKE '%' || ? || '%') AND (EXTRACT (YEAR FROM AGE(d.DataNascita)) BETWEEN ? AND ?) AND (d.Salario BETWEEN ? AND ?) AND (Valutazione(d.CF) BETWEEN ? AND ?)");
		
		getDipendentiNonPartecipantiFiltratiPS = connection.prepareStatement("SELECT * FROM Dipendente AS d "
				+ "WHERE (d.Nome ILIKE '%' || ? || '%' OR d.Cognome ILIKE '%' || ? || '%' OR d.Email ILIKE '%' || ? || '%')"
				+ " AND (EXTRACT (YEAR FROM AGE(d.DataNascita)) BETWEEN ? AND ?)"
				+ " AND (d.Salario BETWEEN ? AND ?) AND (Valutazione(d.CF) BETWEEN ? AND ?)"
				+ " AND d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ?)");
		getDipendenteBySkillPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF IN (SELECT a.CF FROM Abilità AS a WHERE a.IDSkill = ?)");
		getDipendentiNonPartecipantiBySkillPS = connection.prepareStatement("SELECT * FROM Dipendente AS d "
				+ "WHERE d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ?)"
				+ "AND d.CF IN (SELECT a.CF FROM Abilità AS a WHERE a.IDSkill = ?)");
		getDipendentiNonPartecipantiByTipologiaProgetto=connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ? )"
				+ "AND d.cf IN(SELECT cf FROM Progetto NATURAL JOIN Partecipazione WHERE tipoprogetto = ?)");
	}

	@Override
	public ArrayList<Dipendente> getDipendenti() throws SQLException {
		ResultSet risultato = getDipendentiPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
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
					this.getValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override
	public ArrayList<Dipendente> getDipendentiNonInvitati(Meeting meetingSelezionato) throws SQLException {
		getDipendentiNonInvitatiPS.setInt(1, meetingSelezionato.getIdMeeting());
		ResultSet risultato = getDipendentiNonInvitatiPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
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
					this.getValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}

	@Override
	public float getValutazione(String cf) throws SQLException {
		getValutazionePS.setString(1, cf);
		ResultSet risultato = getValutazionePS.executeQuery();
		
		risultato.next();
		float valutazione = risultato.getFloat(1);
		risultato.close();
		
		return valutazione;
	}
	
	@Override
	public boolean insertDipendente(Dipendente dipendente) throws SQLException {
		addDipendentePS.setString(1, dipendente.generaCF());
		addDipendentePS.setString(2, dipendente.getNome());
		addDipendentePS.setString(3, dipendente.getCognome());
		addDipendentePS.setString(4, Character.toString(dipendente.getSesso()));
		addDipendentePS.setDate(5, new Date( dipendente.getDataNascita().toDate().getTime()));
		addDipendentePS.setString(6, dipendente.getIndirizzo());
		addDipendentePS.setString(7, dipendente.getEmail());
		addDipendentePS.setString(8, dipendente.getTelefonoCasa());
		addDipendentePS.setString(9, dipendente.getCellulare());
		addDipendentePS.setFloat(10, dipendente.getSalario());
		addDipendentePS.setString(11, dipendente.getPassword());
		addDipendentePS.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		
		int record = addDipendentePS.executeUpdate();
		
		if (record == 1) {
			dipendente.setCf(dipendente.generaCF());
			return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean aggiornaDipendente(Dipendente dipendente) throws SQLException {
		String oldCF = dipendente.getCf();
		
		updateDipendentePS.setString(1, dipendente.generaCF());
		updateDipendentePS.setString(2, dipendente.getNome());
		updateDipendentePS.setString(3, dipendente.getCognome());
		updateDipendentePS.setString(4, Character.toString(dipendente.getSesso()));
		updateDipendentePS.setDate(5, new Date(dipendente.getDataNascita().toDate().getTime()));
		updateDipendentePS.setString(6, dipendente.getIndirizzo());
		updateDipendentePS.setString(7, dipendente.getEmail());
		updateDipendentePS.setString(8, dipendente.getTelefonoCasa());
		updateDipendentePS.setString(9, dipendente.getCellulare());
		updateDipendentePS.setFloat(10, dipendente.getSalario());
		updateDipendentePS.setString(11, dipendente.getPassword());
		updateDipendentePS.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		updateDipendentePS.setString(13, oldCF);
		
		int record = updateDipendentePS.executeUpdate();
		
		if (record == 1) {
			dipendente.setCf(dipendente.generaCF());
			return true;
		}
		else
			return false;
	}

	@Override
	public Dipendente eseguiLoginDipendente(String email, String password) throws SQLException {
		loginCheckPS.setString(1, email);
		loginCheckPS.setString(2, password);
		ResultSet risultato = loginCheckPS.executeQuery();
		risultato.next();
		
		LuogoNascita luogoNascitaDipendente = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));
		
		Dipendente dipendenteLoggato = new Dipendente(risultato.getString("CF"), 
				risultato.getString("Nome"),
				risultato.getString("Cognome"),
				risultato.getString("Sesso").charAt(0),
				new LocalDate(risultato.getDate("DataNascita")),
				luogoNascitaDipendente,
				risultato.getString("Indirizzo"),
				risultato.getString("Email"),
				risultato.getString("TelefonoCasa"),
				risultato.getString("Cellulare"),
				risultato.getFloat("Salario"),
				risultato.getString("Password"),
				this.getValutazione(risultato.getString("CF")));
		
		dipendenteLoggato.setSkills(skillDAO.getSkillsDipendente(dipendenteLoggato.getCf()));
		
		risultato.close();
		
		return dipendenteLoggato;
	}

	@Override
	public Dipendente getDipendenteByCF(String cf) throws SQLException {
		getDipendenteByCFPS.setString(1, cf);
		ResultSet risultato = getDipendenteByCFPS.executeQuery();
		risultato.next();
		
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
				this.getValutazione(risultato.getString("CF")));
		
		risultato.close();
		
		return dipendenteTemp;
	}

	@Override
	public float getMaxStipendio() throws SQLException {
		float stipendio = 0;
		
		ResultSet risultato = getMaxSalarioPS.executeQuery();
		risultato.next();
		stipendio = risultato.getFloat(1);
		risultato.close();
		return stipendio;
	}

	@Override
	public ArrayList<Dipendente> getDipendentiFiltrati(String nomeCognomeEmail, int etàMinima, int etàMassima,
			float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima)
			throws SQLException {
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		getDipendentiFiltratiPS.setString(1, nomeCognomeEmail);
		getDipendentiFiltratiPS.setString(2, nomeCognomeEmail);
		getDipendentiFiltratiPS.setString(3, nomeCognomeEmail);
		getDipendentiFiltratiPS.setInt(4, etàMinima);
		getDipendentiFiltratiPS.setInt(5, etàMassima);
		getDipendentiFiltratiPS.setFloat(6, salarioMinimo);
		getDipendentiFiltratiPS.setFloat(7, salarioMassimo);
		getDipendentiFiltratiPS.setFloat(8, valutazioneMinima);
		getDipendentiFiltratiPS.setFloat(9, valutazioneMassima);
		
		ResultSet risultato = getDipendentiFiltratiPS.executeQuery();
		
		while (risultato.next()) {
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
					
					this.getValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setSkills(skillDAO.getSkillsDipendente(dipendenteTemp.getCf()));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override
	public ArrayList<Dipendente> getDipendentiNonPartecipantiFiltrati(String nomeCognomeEmail, int etàMinima, int etàMassima,
			float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima,Progetto progettoSelezionato)
			throws SQLException {
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		getDipendentiNonPartecipantiFiltratiPS.setString(1, nomeCognomeEmail);
		getDipendentiNonPartecipantiFiltratiPS.setString(2, nomeCognomeEmail);
		getDipendentiNonPartecipantiFiltratiPS.setString(3, nomeCognomeEmail);
		getDipendentiNonPartecipantiFiltratiPS.setInt(4, etàMinima);
		getDipendentiNonPartecipantiFiltratiPS.setInt(5, etàMassima);
		getDipendentiNonPartecipantiFiltratiPS.setFloat(6, salarioMinimo);
		getDipendentiNonPartecipantiFiltratiPS.setFloat(7, salarioMassimo);
		getDipendentiNonPartecipantiFiltratiPS.setFloat(8, valutazioneMinima);
		getDipendentiNonPartecipantiFiltratiPS.setFloat(9, valutazioneMassima);
		getDipendentiNonPartecipantiFiltratiPS.setInt(10, progettoSelezionato.getIdProgettto());
		
		ResultSet risultato = getDipendentiNonPartecipantiFiltratiPS.executeQuery();
		
		while (risultato.next()) {
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
					
					this.getValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			dipendenteTemp.setTipologieProgetto(getTipologieProgettoDipendente(risultato.getString("CF")));
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override
	public ArrayList<Dipendente> getDipendentiNonPartecipanti(Progetto progettoSelezionato) throws SQLException {
		getDipendentiNonPartecipantiPS.setInt(1, progettoSelezionato.getIdProgettto());
		ResultSet risultato = getDipendentiNonPartecipantiPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		projDAO = new ProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));
			
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
					this.getValutazione(risultato.getString("CF")));
			
			tempDip.setPartecipa(meetDAO.ottieniMeetingDipendente(tempDip));
			tempDip.setCollaborazioni(projDAO.getPartecipantiProgetto(progettoSelezionato.getIdProgettto()));
			tempDip.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			tempDip.setTipologieProgetto(getTipologieProgettoDipendente(tempDip.getCf()));
			
			dipendenti.add(tempDip);
		}
		risultato.close();
		
		return dipendenti;
	}

	private String getTipologieProgettoDipendente(String cf) throws SQLException {
		String tipologie = "";
		getTipologieProgettoDipendentePS.setString(1,cf);
		ResultSet risultato=getTipologieProgettoDipendentePS.executeQuery();
		
		while(risultato.next()) {
			tipologie = tipologie.concat(","+risultato.getString("tipoProgetto"));
		}
		
		if(tipologie.length() > 0)
			return tipologie.substring(1, tipologie.length());
		else
			return null;
	}
	
	@Override
	public boolean eliminaDipendente(Dipendente dipendente) throws SQLException {
		deleteDipendentePS.setString(1, dipendente.getCf());
		int record = deleteDipendentePS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<Dipendente> getDipendenteBySkill(Skill skill) throws SQLException {
		getDipendenteBySkillPS.setInt(1, skill.getIdSkill());
		ResultSet risultato = getDipendenteBySkillPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
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
					this.getValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override
	public ArrayList<Dipendente> getDipendenteNonPartecipantiBySkill(Progetto progettoSelezionato,Skill skill) throws SQLException {
		getDipendentiNonPartecipantiBySkillPS.setInt(1, progettoSelezionato.getIdProgettto());
		getDipendentiNonPartecipantiBySkillPS.setInt(2, skill.getIdSkill());
		ResultSet risultato = getDipendentiNonPartecipantiBySkillPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
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
					this.getValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}

	@Override
	public ArrayList<Dipendente> getDipendentiNonPartecipantiByTipologieProgetto(Progetto progettoSelezionato,String tipologiaProgetto) throws SQLException {
		getDipendentiNonPartecipantiByTipologiaProgetto.setInt(1, progettoSelezionato.getIdProgettto());
		getDipendentiNonPartecipantiByTipologiaProgetto.setObject(2, tipologiaProgetto, Types.OTHER);
		ResultSet risultato = getDipendentiNonPartecipantiByTipologiaProgetto.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		projDAO = new ProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));
			
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
					this.getValutazione(risultato.getString("CF")));
			
			tempDip.setSkills(skillDAO.getSkillsDipendente(risultato.getString("CF")));
			tempDip.setTipologieProgetto(getTipologieProgettoDipendente(tempDip.getCf()));
			
			dipendenti.add(tempDip);
		}
		risultato.close();
		
		return dipendenti;
	}

}
