/*Implementazione dell'interfaccia DipendenteDAO per PostgreSQL.
*Contiene le definizioni dei metodi già presenti nell'interfaccia per operare sulla tabella
*Dipendente del DB, i relativi PreparedStatement per operare più rapidamente sul DB e
*il costruttore del DAO.
******************************************************************************************/

package ImplementazioneDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import Entità.Dipendente;
import Entità.LuogoNascita;
import Entità.Skill;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;

public class DipendenteDAOPSQL implements DipendenteDAO {

	//ATTRIBUTI
	
	private Connection connection;	//connessione al DB
	private PreparedStatement getDipendenti,getDipendentiByEta,getValutazione,getDipendentiByValutazione,getDipendentiBySalario,getDipendentiBySkill,addDipendente,updateDipendente,loginCheck,getDipendenteByCF;
	
	//METODI
	
	//Costruttore
	public DipendenteDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione dal ManagerConnessioneDB
		
		getDipendenti = connection.prepareStatement("SELECT * FROM Dipendente");
		getDipendentiByEta = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE EXTRACT (YEAR FROM AGE(d.DataNascita)) >= ?");
		getValutazione = connection.prepareStatement("SELECT Valutazione(?)");	//? = CF del Dipendente
		getDipendentiByValutazione = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE Valutazione(d.CF) >= ?");
		getDipendentiBySalario = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.Salario BETWEEN ? AND ?");
		getDipendentiBySkill = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF IN (SELECT Dipendente.CF FROM Dipendente NATURAL JOIN Abilità WHERE Abilità.IDSkill = ?)");	//? = IDSkill
		addDipendente = connection.prepareStatement("INSERT INTO Dipendente VALUES (?,?,?,?,?,?,?,?,?,?,?, ?)");
		updateDipendente = connection.prepareStatement("UPDATE Dipendente SET CF = ?, Nome = ?, Cognome = ?, Sesso = ?, DataNascita = ?, Indirizzo = ?, Email = ?, TelefonoCasa = ?, Cellulare = ?, Salario = ?, Password = ?, CodComune = ? WHERE CF = ? ");
		loginCheck = connection.prepareStatement("SELECT * FROM Dipendente WHERE Email = ? AND Password = ?");
		getDipendenteByCF = connection.prepareStatement("SELECT * FROM Dipendente AS d WHERE d.CF = ?");
	}
	
	//Metodo GetDipendenti.
	/*Ottiene tutti i dipendenti nella tabella Dipendente del DB.
	Restituisce una lista con tutti i dipendenti.*/
	@Override
	public ArrayList<Dipendente> GetDipendenti() throws SQLException {
		ResultSet risultato = getDipendenti.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire in seguito
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo GetDipendentiByEtà.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti
	* con età superiore al parametro indicato nella funzione.*/
	@Override
	public ArrayList<Dipendente> GetDipendentiByEta(int minima) throws SQLException {
		getDipendentiByEta.setInt(1, minima);	//inserisce il parametro nella query
		ResultSet risultato = getDipendentiByEta.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo GetValutazione.
	/*Interroga il DB richiamando una funzione esterna Valutazione(CF) che
	*ottenendo in input come parametro il codice fiscale di un dipendente 
	calcola la sua valutazione e la restituisce.*/
	@Override
	public float GetValutazione(String cf) throws SQLException {
		getValutazione.setString(1, cf);	//inserisce il parametro CF nella query
		ResultSet risultato = getValutazione.executeQuery();	//esegue la query ottenendo il ResultSet
		
		risultato.next();	//prendi il primo (e unico) record del ResultSet
		float temp = risultato.getFloat(1);	//ottiene la valutazione dal ResultSet
		risultato.close(); //chiude il ResultSet
		return temp;
	}

	//Metodo GetDipendentiByValutazione.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti che
	*hanno una valutazione superiroe a quanto inserito come parametro.*/
	@Override
	public ArrayList<Dipendente> GetDipendentiByValutazione(float minima) throws SQLException {
		getDipendentiByValutazione.setFloat(1, minima);	//inserisce il parametro nella query
		ResultSet risultato = getDipendentiByValutazione.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();
		
		return temp;
	}

	//Metodo GetDipendentiBySalario.
	/*Metodo che interroga il DB e restituisce una lista di dipendenti che hanno
	*salario compreso tra gli estremi inseriti come parametri.*/
	@Override
	public ArrayList<Dipendente> GetDipendentiBySalario(float minimo, float massimo) throws SQLException {
		getDipendentiBySalario.setFloat(1, minimo);	//inserisce i parametri nella query
		getDipendentiBySalario.setFloat(2, massimo);
		ResultSet risultato = getDipendentiBySalario.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();
		
		return temp;
	}

	//Metodo GetDipendentiBySkill.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti
	*che hanno una specifica skill.*/
	@Override
	public ArrayList<Dipendente> GetDipendentiBySkill(Skill skill) throws SQLException {
		getDipendentiBySkill.setInt(1, skill.getId()); 	//inserisce il parametro nella query
		ResultSet risultato = getDipendentiBySkill.executeQuery();	//esegue la query e ottiene il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista di dipendenti da restituire
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		//finchè esistono dipendenti nel ResultSet
		while(risultato.next()) {
			LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
			
			Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close(); 	//chiude il ResultSet
		
		return temp;
	}

	//Metodo AddDipendente.
	/*Metodo che opera sul DB per aggiungere un nuovo dipendente alla tabella Dipendente
	*prendendo le informazioni dall'oggetto dipendente inserito come parametro in input. 
	Restituisce true se l'operazione ha successo e false se fallisce.*/
	@Override
	public boolean AddDipendente(Dipendente dipendente) throws SQLException {
		//inserisce tutti i parametri nello statement INSERT
		addDipendente.setString(1, dipendente.getCf());
		addDipendente.setString(2, dipendente.getNome());
		addDipendente.setString(3, dipendente.getCognome());
		addDipendente.setString(4, Character.toString(dipendente.getSesso()));	//conversione da char a String per rispettare il tipo CHAR del DB
		addDipendente.setDate(5, new Date( dipendente.getDataNascita().toDate().getTime()));	//conversione da org.joda.time.LocalDate a java.sql.Date
		addDipendente.setString(6, dipendente.getIndirizzo());
		addDipendente.setString(7, dipendente.getEmail());
		addDipendente.setString(8, dipendente.getTelefonoCasa());
		addDipendente.setString(9, dipendente.getCellulare());
		addDipendente.setFloat(10, dipendente.getSalario());
		addDipendente.setString(11, dipendente.getPassword());
		addDipendente.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		
		int record = addDipendente.executeUpdate();	//esegue l'insert e salva il numero di record inseriti in record
		
		//se record = 1 allora l'insert è avvenuto correttamente, altrimenti no
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo UpdateDipendente.
	/*Metodo che opera sul DB per aggiornare le informazioni su un dipendente.
	Restituisce true se l'update ha successo altrimenti false.*/
	@Override
	public boolean UpdateDipendente(Dipendente dipendente) throws SQLException {
		String oldCF = dipendente.getCf();	//salva da parte il vecchio codice fiscale del dipendente
		dipendente.setCf(dipendente.GeneraCF());	//genera e setta il nuovo codice fiscale in base alle modifiche effettuate
		
		//inserisce tutti i parametri nello statement INSERT
		updateDipendente.setString(1, dipendente.getCf());
		updateDipendente.setString(2, dipendente.getNome());
		updateDipendente.setString(3, dipendente.getCognome());
		updateDipendente.setString(4, Character.toString(dipendente.getSesso()));	//conversione da char a String per rispettare il tipo CHAR del DB
		updateDipendente.setDate(5, new Date( dipendente.getDataNascita().toDate().getTime()));	//conversione da org.joda.time.LocalDate a java.sql.Date
		updateDipendente.setString(6, dipendente.getIndirizzo());
		updateDipendente.setString(7, dipendente.getEmail());
		updateDipendente.setString(8, dipendente.getTelefonoCasa());
		updateDipendente.setString(9, dipendente.getCellulare());
		updateDipendente.setFloat(10, dipendente.getSalario());
		updateDipendente.setString(11, dipendente.getPassword());
		updateDipendente.setString(12, dipendente.getLuogoNascita().getCodiceComune());
		updateDipendente.setString(13, oldCF);
		
		int record = updateDipendente.executeUpdate();	//esegue l'update del dipendente e restituisce il numero di record modificati (1 = modifica effettuata, 0 = fallimento)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo LoginCheck.
	/*Metodo che interroga il DB per ottenere (se esiste) il dipendente
	*con le credenziali inserite in input come parametri.*/
	@Override
	public Dipendente LoginCheck(String email, String password) throws SQLException {
		loginCheck.setString(1, email);	//inserisce i parametri nella query
		loginCheck.setString(2, password);
		ResultSet risultato = loginCheck.executeQuery();	//esegue la query e ottiene il ResultSet
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		risultato.next();	//prende il primo (e unico) record del ResultSet
		LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
		
		Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
				luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
				risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
		
		risultato.close();	//chiude il ResultSet
		
		return tempDip;
	}

	//Metoodo GetDipendenteByCF.
	/*Metodo che interroga il DB per ottenere il dipendente con codice fiscale
	*uguale al cf in input.*/
	@Override
	public Dipendente GetDipendenteByCF(String cf) throws SQLException {
		getDipendenteByCF.setString(1, cf);	//inserisce parametro nella query
		ResultSet risultato = getDipendenteByCF.executeQuery();	//esegue la query e ottiene il ResultSet
		
		LuogoNascitaDAO luogoDAO = new LuogoNascitaDAOPSQL(this.connection);	//inizializza il DAO per luogonascita
		
		risultato.next();	//prende il primo (e unico) record del ResultSet
		
		LuogoNascita luogoTemp = luogoDAO.GetLuogoByCod(risultato.getString("CodComune"));	//ottiene il luogo di nascita
		
		Dipendente tempDip = new Dipendente(risultato.getString("CF"), risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
				luogoTemp, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
				risultato.getFloat("Salario"), risultato.getString("Password"), GetValutazione(risultato.getString("CF")));	//crea il dipendente temporaneo
		
		risultato.close(); //chiude il ResultSet
		
		return tempDip;
	}

}
