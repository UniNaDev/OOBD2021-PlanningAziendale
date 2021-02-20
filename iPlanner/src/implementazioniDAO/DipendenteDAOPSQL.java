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
		ottieniDipendentiNonInvitatiMeetingPS,
		getDipendentiPS,
		ottieniDipendentiNonPartecipantiProgettoPS,
		ottieniTipologieProgettoDipendentePS,
		ottieniValutazionePS,
		addDipendentePS,
		aggiornaDipendentePS,
		deleteDipendentePS,
		eseguiLoginDipendentePS,
		getDipendenteByCFPS,
		ottieniMaxStipendioPS,
		getDipendentiFiltratiPS,
		filtraDipendentiNonPartecipantiPS,
		getDipendenteBySkillPS,
		filtraDipendentiNonPartecipantiPerSkillPS,
		filtraDipendentiNonPartecipantiPerTipologiaProgettoPS;
	
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
		ottieniDipendentiNonPartecipantiProgettoPS=connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ? )");
		ottieniTipologieProgettoDipendentePS=connection.prepareStatement("SELECT DISTINCT tipoprogetto FROM Progetto NATURAL JOIN Partecipazione WHERE CF ILIKE ?");
		ottieniDipendentiNonInvitatiMeetingPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.cf NOT IN(SELECT pre.cf FROM Meeting NATURAL JOIN Presenza AS pre WHERE pre.idMeeting=?)");
		ottieniValutazionePS = connection.prepareStatement("SELECT Valutazione(?)");
		addDipendentePS = connection.prepareStatement("INSERT INTO Dipendente VALUES (?,?,?,?,?,?,?,?,?,?,?, ?)");
		aggiornaDipendentePS = connection.prepareStatement("UPDATE Dipendente SET CF = ?, Nome = ?, Cognome = ?, Sesso = ?, DataNascita = ?, Indirizzo = ?, Email = ?, TelefonoCasa = ?, Cellulare = ?, Salario = ?, Password = ?, CodComune = ? WHERE CF = ?");
		deleteDipendentePS = connection.prepareStatement("DELETE FROM Dipendente WHERE CF = ?");
		eseguiLoginDipendentePS = connection.prepareStatement("SELECT * FROM Dipendente WHERE Email ILIKE ? AND Password = ?");
		getDipendenteByCFPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF = ?");
		ottieniMaxStipendioPS = connection.prepareStatement("SELECT MAX(Salario) FROM Dipendente");

		getDipendentiFiltratiPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE (d.Nome LIKE '%' || ? || '%' OR d.Cognome LIKE '%' || ? || '%' OR d.Email LIKE '%' || ? || '%') AND (EXTRACT (YEAR FROM AGE(d.DataNascita)) BETWEEN ? AND ?) AND (d.Salario BETWEEN ? AND ?) AND (Valutazione(d.CF) BETWEEN ? AND ?)");
		
		filtraDipendentiNonPartecipantiPS = connection.prepareStatement("SELECT * FROM Dipendente AS d "
				+ "WHERE (d.Nome ILIKE '%' || ? || '%' OR d.Cognome ILIKE '%' || ? || '%' OR d.Email ILIKE '%' || ? || '%')"
				+ " AND (EXTRACT (YEAR FROM AGE(d.DataNascita)) BETWEEN ? AND ?)"
				+ " AND (d.Salario BETWEEN ? AND ?) AND (Valutazione(d.CF) BETWEEN ? AND ?)"
				+ " AND d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ?)");
		getDipendenteBySkillPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF IN (SELECT a.CF FROM Abilità AS a WHERE a.IDSkill = ?)");
		filtraDipendentiNonPartecipantiPerSkillPS = connection.prepareStatement("SELECT * FROM Dipendente AS d "
				+ "WHERE d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ?)"
				+ "AND d.CF IN (SELECT a.CF FROM Abilità AS a WHERE a.IDSkill = ?)");
		filtraDipendentiNonPartecipantiPerTipologiaProgettoPS=connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.cf NOT IN(SELECT par.cf FROM Progetto NATURAL JOIN Partecipazione AS par WHERE par.codProgetto= ? )"
				+ "AND d.cf IN(SELECT cf FROM Progetto NATURAL JOIN Partecipazione WHERE tipoprogetto = ?)");
	}

	@Override
	public ArrayList<Dipendente> getDipendenti() throws SQLException {
		ResultSet risultato = getDipendentiPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					this.ottieniValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override
	public ArrayList<Dipendente> ottieniDipendentiNonInvitatiMeeting(Meeting meetingSelezionato) throws SQLException {
		ottieniDipendentiNonInvitatiMeetingPS.setInt(1, meetingSelezionato.getIdMeeting());
		ResultSet risultato = ottieniDipendentiNonInvitatiMeetingPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					this.ottieniValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}

	@Override
	public float ottieniValutazione(String cf) throws SQLException {
		ottieniValutazionePS.setString(1, cf);
		ResultSet risultato = ottieniValutazionePS.executeQuery();
		
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
	
	@Override  //Ok
	public boolean aggiornaDipendente(Dipendente dipendente) throws SQLException {
		String oldCF = dipendente.getCf();
		
		aggiornaDipendentePS.setString(1, dipendente.generaCF());
		aggiornaDipendentePS.setString(2, dipendente.getNome());
		aggiornaDipendentePS.setString(3, dipendente.getCognome());
		aggiornaDipendentePS.setString(4, Character.toString(dipendente.getSesso()));
		aggiornaDipendentePS.setDate(5, new Date(dipendente.getDataNascita().toDate().getTime()));
		aggiornaDipendentePS.setString(6, dipendente.getIndirizzo());
		aggiornaDipendentePS.setString(7, dipendente.getEmail());
		aggiornaDipendentePS.setString(8, dipendente.getTelefonoCasa());
		aggiornaDipendentePS.setString(9, dipendente.getCellulare());
		aggiornaDipendentePS.setFloat(10, dipendente.getSalario());
		aggiornaDipendentePS.setString(11, dipendente.getPassword());
		aggiornaDipendentePS.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		aggiornaDipendentePS.setString(13, oldCF);
		
		int record = aggiornaDipendentePS.executeUpdate();
		
		if (record == 1) {
			dipendente.setCf(dipendente.generaCF());
			return true;
		}
		else
			return false;
	}

	@Override   //OK
	public Dipendente eseguiLoginDipendente(String email, String password) throws SQLException {
		eseguiLoginDipendentePS.setString(1, email);
		eseguiLoginDipendentePS.setString(2, password);
		ResultSet risultato = eseguiLoginDipendentePS.executeQuery();
		risultato.next();
		
		LuogoNascita luogoNascitaDipendente = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
		
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
				this.ottieniValutazione(risultato.getString("CF")));
		
		dipendenteLoggato.setSkills(skillDAO.ottieniSkillDipendente(dipendenteLoggato.getCf()));
		
		risultato.close();
		
		return dipendenteLoggato;
	}

	@Override  //ok
	public Dipendente ottieniDipendenteDaCF(String cf) throws SQLException {
		getDipendenteByCFPS.setString(1, cf);
		ResultSet risultato = getDipendenteByCFPS.executeQuery();
		risultato.next();
		
		LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
		
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
				this.ottieniValutazione(risultato.getString("CF")));
		
		risultato.close();
		
		return dipendenteTemp;
	}

	@Override  //Ok
	public float ottieniMaxStipendio() throws SQLException {
		float stipendio = 0;
		
		ResultSet risultato = ottieniMaxStipendioPS.executeQuery();
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
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					
					this.ottieniValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setSkills(skillDAO.ottieniSkillDipendente(dipendenteTemp.getCf()));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override  //Ok
	public ArrayList<Dipendente> filtraDipendentiNonPartecipanti(String nomeCognomeEmail, int etàMinima, int etàMassima,
			float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima,Progetto progettoSelezionato)
			throws SQLException {
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		filtraDipendentiNonPartecipantiPS.setString(1, nomeCognomeEmail);
		filtraDipendentiNonPartecipantiPS.setString(2, nomeCognomeEmail);
		filtraDipendentiNonPartecipantiPS.setString(3, nomeCognomeEmail);
		filtraDipendentiNonPartecipantiPS.setInt(4, etàMinima);
		filtraDipendentiNonPartecipantiPS.setInt(5, etàMassima);
		filtraDipendentiNonPartecipantiPS.setFloat(6, salarioMinimo);
		filtraDipendentiNonPartecipantiPS.setFloat(7, salarioMassimo);
		filtraDipendentiNonPartecipantiPS.setFloat(8, valutazioneMinima);
		filtraDipendentiNonPartecipantiPS.setFloat(9, valutazioneMassima);
		filtraDipendentiNonPartecipantiPS.setInt(10, progettoSelezionato.getIdProgettto());
		
		ResultSet risultato = filtraDipendentiNonPartecipantiPS.executeQuery();
		
		while (risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					
					this.ottieniValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			dipendenteTemp.setTipologieProgetto(ottieniTipologieProgettoDipendente(risultato.getString("CF")));
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override //Ok
	public ArrayList<Dipendente> ottieniDipendentiNonPartecipantiProgetto(Progetto progettoSelezionato) throws SQLException {
		ottieniDipendentiNonPartecipantiProgettoPS.setInt(1, progettoSelezionato.getIdProgettto());
		ResultSet risultato = ottieniDipendentiNonPartecipantiProgettoPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		projDAO = new ProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					this.ottieniValutazione(risultato.getString("CF")));
			
			tempDip.setPartecipa(meetDAO.ottieniMeetingDipendente(tempDip));
			tempDip.setCollaborazioni(projDAO.ottieniPartecipantiProgetto(progettoSelezionato.getIdProgettto()));
			tempDip.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			tempDip.setTipologieProgetto(ottieniTipologieProgettoDipendente(tempDip.getCf()));
			
			dipendenti.add(tempDip);
		}
		risultato.close();
		
		return dipendenti;
	}
		//ok
	private String ottieniTipologieProgettoDipendente(String cf) throws SQLException {
		String tipologie = "";
		ottieniTipologieProgettoDipendentePS.setString(1,cf);
		ResultSet risultato=ottieniTipologieProgettoDipendentePS.executeQuery();
		
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
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					this.ottieniValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}
	
	@Override //Ok
	public ArrayList<Dipendente> filtraDipendentiNonPartecipantiPerSkill(Progetto progettoSelezionato,Skill skill) throws SQLException {
		filtraDipendentiNonPartecipantiPerSkillPS.setInt(1, progettoSelezionato.getIdProgettto());
		filtraDipendentiNonPartecipantiPerSkillPS.setInt(2, skill.getIdSkill());
		ResultSet risultato = filtraDipendentiNonPartecipantiPerSkillPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					this.ottieniValutazione(risultato.getString("CF")));
			
			dipendenteTemp.setPartecipa(meetDAO.ottieniMeetingDipendente(dipendenteTemp));
			dipendenteTemp.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			
			dipendenti.add(dipendenteTemp);
		}
		risultato.close();
		
		return dipendenti;
	}

	@Override //Ok
	public ArrayList<Dipendente> filtraDipendentiNonPartecipantiPerTipologiaProgetto(Progetto progettoSelezionato,String tipologiaProgetto) throws SQLException {
		filtraDipendentiNonPartecipantiPerTipologiaProgettoPS.setInt(1, progettoSelezionato.getIdProgettto());
		filtraDipendentiNonPartecipantiPerTipologiaProgettoPS.setObject(2, tipologiaProgetto, Types.OTHER);
		ResultSet risultato = filtraDipendentiNonPartecipantiPerTipologiaProgettoPS.executeQuery();
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		projDAO = new ProgettoDAOPSQL(connection);
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.ottieniLuogoNascitaDaCodComune(risultato.getString("CodComune"));
			
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
					this.ottieniValutazione(risultato.getString("CF")));
			
			tempDip.setSkills(skillDAO.ottieniSkillDipendente(risultato.getString("CF")));
			tempDip.setTipologieProgetto(ottieniTipologieProgettoDipendente(tempDip.getCf()));
			
			dipendenti.add(tempDip);
		}
		risultato.close();
		
		return dipendenti;
	}

}
