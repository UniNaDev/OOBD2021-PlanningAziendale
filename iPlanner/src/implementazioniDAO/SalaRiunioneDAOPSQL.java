//Implementazione dell'interfaccia SalaRiunioneDAO per PostgreSQL con relativi PreparedStatement.

package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.joda.time.LocalDateTime;

import entita.SalaRiunione;
import interfacceDAO.SalaRiunioneDAO;

public class SalaRiunioneDAOPSQL implements SalaRiunioneDAO {

	//ATTRIBUTI
	//----------------------------------------
	private Connection connection;	//connessione al DB
	private PreparedStatement getSalePS,addSalaPS,updateSalaPS,removeSalaPS,getSalaByCodPS;	//prepared Statements
	
	//METODI
	//----------------------------------------
	
	//Costruttore
	public SalaRiunioneDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		getSalePS = connection.prepareStatement("SELECT * FROM SalaRiunione ORDER BY CodSala");
		addSalaPS = connection.prepareStatement("INSERT INTO SalaRiunione VALUES (?,?,?,?)");	//? = codice sala, capienza, indirizzo, piano
		updateSalaPS = connection.prepareStatement("UPDATE SalaRiunione SET Capienza = ?, Indirizzo = ?, Piano = ? WHERE CodSala = ?");	//?1 = nuovo codice, ?5 = vecchio codice
		removeSalaPS = connection.prepareStatement("DELETE FROM SalaRiunione WHERE CodSala = ?");	//? = codice sala da cancellare
		getSalaByCodPS = connection.prepareStatement("SELECT * FROM SalaRiunione AS sr WHERE sr.CodSala = ?");	//? = codice della sala da cercare
	}
	
	//Metodo che restituisce la lista (temp) di tutte le sale nel DB
	@Override
	public ArrayList<SalaRiunione> getSale() throws SQLException {
		ResultSet risultato = getSalePS.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<SalaRiunione> temp = new ArrayList<SalaRiunione>();	//inizializza la lista da restituire in seguito
		
		//finchè ci sono record di sale nel ResultSet
		while(risultato.next()) {
			SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"), risultato.getInt("Piano"));	//crea l'oggetto sala temporaneo da aggiungere alla lista
			temp.add(salaTemp);	//aggiunge la sala alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo che aggiunge una sala alla tabella SalaRiunione nel DB
	@Override
	public boolean insertSala(SalaRiunione sala) throws SQLException {
		addSalaPS.setString(1, sala.getCodSala());	//inserisce il codice sala nell'insert
		addSalaPS.setInt(2, sala.getCap());	//inserisce la capienza della sala nell'insert
		addSalaPS.setString(3, sala.getIndirizzo()); 	//inserisce l'indirizzo nell'insert
		addSalaPS.setInt(4, sala.getPiano()); //inserisce il piano nell'insert
		
		int record = addSalaPS.executeUpdate();	//esegue l'insert e salva il numero di record aggiunti (1=inserito, 0=non inserito)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che modifica una sala nel DB
	@Override
	public boolean updateSala(SalaRiunione sala) throws SQLException {
		updateSalaPS.setString(4, sala.getCodSala());	//inserisce il codice della sala da modificare
		updateSalaPS.setInt(1, sala.getCap());	//inserisce la nuova capienza della sala nell'update
		updateSalaPS.setString(2, sala.getIndirizzo()); 	//inserisce il nuovo indirizzo nell'update
		updateSalaPS.setInt(3, sala.getPiano()); //inserisce il nuovo piano nell'update
		
		int record = updateSalaPS.executeUpdate();	//esegue l'update e salva il numero di record modificati (1=modificato, 0=non modificato)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che rimuove una sala specifica dal DB
	@Override
	public boolean deleteSala(SalaRiunione sala) throws SQLException {
		removeSalaPS.setString(1, sala.getCodSala());	//inserisce il codice della sala da rimuovere nella delete
		
		int record = removeSalaPS.executeUpdate();	//esegue la delete e salva il numero di record eliminati (1=eliminato, 0=non eliminato)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che restituisce la sala con il codice indicato nei parametri in input
	@Override
	public SalaRiunione getSalaByCod(String codSala) throws SQLException {
		if (codSala != null) {
		getSalaByCodPS.setString(1, codSala); 	//inserisce il codice della sala nella query
		
		ResultSet risultato = getSalaByCodPS.executeQuery();	//esegue la query e restituisce il ResultSet
		
		risultato.next();
		SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"),risultato.getInt("Piano"));
		
		return salaTemp;
		}
		else
			return null;
	}

}
