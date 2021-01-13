/*Implementazione dell'interfaccia DipendenteDAO per PostgreSQL.
*Contiene le definizioni dei metodi già presenti nell'interfaccia per operare sulla tabella
*Dipendente del DB, i relativi PreparedStatement per operare più rapidamente sul DB e
*il costruttore del DAO.
******************************************************************************************/

package implementazioniDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;

public class DipendenteDAOPSQL implements DipendenteDAO {

	//ATTRIBUTI
	
	private Connection connection;	//connessione al DB
	private PreparedStatement getDipendentiPS,getDipendentiByEtaPS,getValutazionePS,getDipendentiByValutazionePS,getDipendentiBySalarioPS,getDipendentiBySkillPS,addDipendentePS,updateDipendentePS,loginCheckPS,getDipendenteByCFPS;
	
	//METODI
	
	//Costruttore
	public DipendenteDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione dal ManagerConnessioneDB
		
		getDipendentiPS = connection.prepareStatement("SELECT * FROM Dipendente");
		getDipendentiByEtaPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE EXTRACT (YEAR FROM AGE(d.DataNascita)) >= ?");
		getValutazionePS = connection.prepareStatement("SELECT Valutazione(?)");	//? = CF del Dipendente
		getDipendentiByValutazionePS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE Valutazione(d.CF) >= ?");
		getDipendentiBySalarioPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.Salario BETWEEN ? AND ?");
		getDipendentiBySkillPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF IN (SELECT Dipendente.CF FROM Dipendente NATURAL JOIN (Abilità NATURAL JOIN Skill) WHERE Skill.NomeSkill = ?)");	//? = NomeSkill
		addDipendentePS = connection.prepareStatement("INSERT INTO Dipendente VALUES (?,?,?,?,?,?,?,?,?,?,?, ?)");
		updateDipendentePS = connection.prepareStatement("UPDATE Dipendente SET CF = ?, Nome = ?, Cognome = ?, Sesso = ?, DataNascita = ?, Indirizzo = ?, Email = ?, TelefonoCasa = ?, Cellulare = ?, Salario = ?, Password = ?, CodComune = ? WHERE CF = ?");
		
		loginCheckPS = connection.prepareStatement("SELECT * FROM Dipendente WHERE Email = ? AND Password = ?");
		getDipendenteByCFPS = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF = ?");
	}
	
	//Metodo getDipendenti.
	/*Ottiene tutti i dipendenti nella tabella Dipendente del DB.
	Restituisce una lista con tutti i dipendenti.*/
	@Override
	public ArrayList<Dipendente> getDipendenti() throws SQLException {
		ResultSet risultato = getDipendentiPS.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire in seguito
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
			tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
			tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo getDipendentiByEtà.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti
	* con età superiore al parametro indicato nella funzione.*/
	@Override
	public ArrayList<Dipendente> getDipendentiByEta(int minima) throws SQLException {
		getDipendentiByEtaPS.setInt(1, minima);	//inserisce il parametro nella query
		ResultSet risultato = getDipendentiByEtaPS.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
			tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
			tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo getValutazione.
	/*Interroga il DB richiamando una funzione esterna Valutazione(CF) che
	*ottenendo in input come parametro il codice fiscale di un dipendente 
	calcola la sua valutazione e la restituisce.*/
	@Override
	public float getValutazione(String cf) throws SQLException {
		getValutazionePS.setString(1, cf);	//inserisce il parametro CF nella query
		ResultSet risultato = getValutazionePS.executeQuery();	//esegue la query ottenendo il ResultSet
		
		risultato.next();	//prendi il primo (e unico) record del ResultSet
		float temp = risultato.getFloat(1);	//ottiene la valutazione dal ResultSet
		risultato.close(); //chiude il ResultSet
		return temp;
	}

	//Metodo getDipendentiByValutazione.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti che
	*hanno una valutazione superiroe a quanto inserito come parametro.*/
	@Override
	public ArrayList<Dipendente> getDipendentiByValutazione(float minima) throws SQLException {
		getDipendentiByValutazionePS.setFloat(1, minima);	//inserisce il parametro nella query
		ResultSet risultato = getDipendentiByValutazionePS.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
			tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
			tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();
		
		return temp;
	}

	//Metodo getDipendentiBySalario.
	/*Metodo che interroga il DB e restituisce una lista di dipendenti che hanno
	*salario compreso tra gli estremi inseriti come parametri.*/
	@Override
	public ArrayList<Dipendente> getDipendentiBySalario(float minimo, float massimo) throws SQLException {
		getDipendentiBySalarioPS.setFloat(1, minimo);	//inserisce i parametri nella query
		getDipendentiBySalarioPS.setFloat(2, massimo);
		ResultSet risultato = getDipendentiBySalarioPS.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
			tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
			tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();
		
		return temp;
	}

	//Metodo getDipendentiBySkill.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti
	*che hanno una specifica skill.*/
	@Override
	public ArrayList<Dipendente> getDipendentiBySkill(Skill skill) throws SQLException {
		getDipendentiBySkillPS.setString(1, skill.getNomeSkill()); 	//inserisce il parametro nella query
		ResultSet risultato = getDipendentiBySkillPS.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
			tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
			tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close(); 	//chiude il ResultSet
		
		return temp;
	}

	//Metodo addDipendente.
	/*Metodo che opera sul DB per aggiungere un nuovo dipendente alla tabella Dipendente
	*prendendo le informazioni dall'oggetto dipendente inserito come parametro in input. 
	Restituisce true se l'operazione ha successo e false se fallisce.*/
	@Override
	public boolean addDipendente(Dipendente dipendente) throws SQLException {
		//inserisce tutti i parametri nello statement INSERT
		addDipendentePS.setString(1, dipendente.generaCF());
		addDipendentePS.setString(2, dipendente.getNome());
		addDipendentePS.setString(3, dipendente.getCognome());
		addDipendentePS.setString(4, Character.toString(dipendente.getSesso()));	//conversione da char a String per rispettare il tipo CHAR del DB
		addDipendentePS.setDate(5, new Date( dipendente.getDataNascita().toDate().getTime()));	//conversione da org.joda.time.LocalDate a java.sql.Date
		addDipendentePS.setString(6, dipendente.getIndirizzo());
		addDipendentePS.setString(7, dipendente.getEmail());
		addDipendentePS.setString(8, dipendente.getTelefonoCasa());
		addDipendentePS.setString(9, dipendente.getCellulare());
		addDipendentePS.setFloat(10, dipendente.getSalario());
		addDipendentePS.setString(11, dipendente.getPassword());
		addDipendentePS.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		
		int record = addDipendentePS.executeUpdate();	//esegue l'insert e salva il numero di record inseriti in record
		
		//se record = 1 allora l'insert è avvenuto correttamente, altrimenti no
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo updateDipendente.
	/*Metodo che opera sul DB per aggiornare le informazioni su un dipendente.
	Restituisce true se l'update ha successo altrimenti false.*/
	@Override
	public boolean updateDipendente(Dipendente dipendente) throws SQLException {
		String oldCF = dipendente.getCf();	//salva da parte il vecchio codice fiscale del dipendente
		dipendente.setCf(dipendente.generaCF());	//genera e setta il nuovo codice fiscale in base alle modifiche effettuate
		
		//inserisce tutti i parametri nello statement INSERT
		updateDipendentePS.setString(1, dipendente.getCf());
		updateDipendentePS.setString(2, dipendente.getNome());
		updateDipendentePS.setString(3, dipendente.getCognome());
		updateDipendentePS.setString(4, Character.toString(dipendente.getSesso()));	//conversione da char a String per rispettare il tipo CHAR del DB
		updateDipendentePS.setDate(5, new Date( dipendente.getDataNascita().toDate().getTime()));	//conversione da org.joda.time.LocalDate a java.sql.Date
		updateDipendentePS.setString(6, dipendente.getIndirizzo());
		updateDipendentePS.setString(7, dipendente.getEmail());
		updateDipendentePS.setString(8, dipendente.getTelefonoCasa());
		updateDipendentePS.setString(9, dipendente.getCellulare());
		updateDipendentePS.setFloat(10, dipendente.getSalario());
		updateDipendentePS.setString(11, dipendente.getPassword());
		updateDipendentePS.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		updateDipendentePS.setString(13, oldCF);
		
		int record = updateDipendentePS.executeUpdate();	//esegue l'update del dipendente e restituisce il numero di record modificati (1 = modifica effettuata, 0 = fallimento)
		
		if (record == 1)
			return true;
		else
//			JOptionPane.showMessageDialog(null, "Update non eseguito");
			return false;
	}

	//Metodo loginCheck.
	/*Metodo che interroga il DB per ottenere (se esiste) il dipendente
	*con le credenziali inserite in input come parametri.*/
	@Override
	public Dipendente loginCheck(String email, String password) throws SQLException {
		loginCheckPS.setString(1, email);	//inserisce i parametri nella query
		loginCheckPS.setString(2, password);
		ResultSet risultato = loginCheckPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		risultato.next();	//prende il primo (e unico) record del ResultSet
		LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
		
		Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
				luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
				risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
		tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
		tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
		
		risultato.close();	//chiude il ResultSet
		
		return tempDip;
	}

	//Metoodo getDipendenteByCF.
	/*Metodo che interroga il DB per ottenere il dipendente con codice fiscale
	*uguale al cf in input.*/
	@Override
	public Dipendente getDipendenteByCF(String cf) throws SQLException {
		getDipendenteByCFPS.setString(1, cf);	//inserisce parametro nella query
		ResultSet risultato = getDipendenteByCFPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		risultato.next();	//prende il primo (e unico) record del ResultSet
		
		LuogoNascita luogoTemp = luogoDAO.getLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
		
		Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
				luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
				risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
		tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
		tempDip.setValutazione(getValutazione(tempDip.getCf()));	//recupera la sua valutazione
		
		risultato.close(); //chiude il ResultSet
		
		return tempDip;
	}

}
