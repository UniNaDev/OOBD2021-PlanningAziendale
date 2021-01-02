//Implementazione dell'interfaccia SalaRiunioneDAO per PostgreSQL con relativi PreparedStatement.

package ImplementazioneDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.joda.time.LocalDateTime;

import Entità.SalaRiunione;
import InterfacceDAO.SalaRiunioneDAO;

public class SalaRiunioneDAOPSQL implements SalaRiunioneDAO {

	//ATTRIBUTI
	private Connection connection;	//connessione al DB
	
	private PreparedStatement getSalePS,getSaleByCapPS,getSaleLiberePS,addSalaPS,updateSalaPS,removeSalaPS;
	
	//METODI
	
	//Costruttore
	public SalaRiunioneDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		getSalePS = connection.prepareStatement("SELECT * FROM SalaRiunione");
		getSaleByCapPS = connection.prepareStatement("SELECT * FROM SalaRiunione AS sr WHERE sr.capienza >= ?");	//? = capienza minima richiesta
		getSaleLiberePS = connection.prepareStatement("SELECT * FROM SalaRiunione AS sr WHERE sr.CodSala NOT IN (SELECT m.CodSala FROM Meeting AS m WHERE (DataInizio+OrarioInizio,DataFine+OrarioFine) OVERLAPS (?,?) AND m.modalità = 'Fisico')");	//? = timelapse inizio e fine
		addSalaPS = connection.prepareStatement("INSERT INTO SalaRiunione VALUES (?,?,?,?)");	//? = codice sala, capienza, indirizzo, piano
		updateSalaPS = connection.prepareStatement("UPDATE SalaRiunione SET Capienza = ?, Indirizzo = ?, Piano = ? WHERE CodSala = ?");	//?1 = nuovo codice, ?5 = vecchio codice
		removeSalaPS = connection.prepareStatement("DELETE FROM SalaRiunione WHERE CodSala = ?");	//? = codice sala da cancellare
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

	//Metodo che restituisce la lista (temp) di tutte le sale nel DB che hanno capienza superiore a quanto indicato nel parametro
	@Override
	public ArrayList<SalaRiunione> getSaleByCap(int cap) throws SQLException {
		getSaleByCapPS.setInt(1, cap);	//inserisce la capienza minima richiesta nella query
		
		ResultSet risultato = getSaleByCapPS.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<SalaRiunione> temp = new ArrayList<SalaRiunione>();	//inizializza la lista da restituire in seguito
		
		//finchè ci sono record di sale nel ResultSet
		while(risultato.next()) {
			SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"), risultato.getInt("Piano"));	//crea l'oggetto sala temporaneo da aggiungere alla lista
			temp.add(salaTemp);	//aggiunge la sala alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo che restituisce la lista (temp) di sale nel DB che sono libere nell'intervallo di tempo specificato dai parametri
	@Override
	public ArrayList<SalaRiunione> getSaleLibere(LocalDateTime inizio, LocalDateTime fine) throws SQLException {
		Timestamp inizioTM = new Timestamp(inizio.toDateTime().getMillis());	//conversione in Timestamp del LocalDateTime inizio per SQL
		Timestamp fineTM = new Timestamp(fine.toDateTime().getMillis());	//conversione in Timestamp del LocalDateTime fine per SQL
		
		//inserisce inizio e fine come Timestamp nella query
		getSaleLiberePS.setTimestamp(1, inizioTM);
		getSaleLiberePS.setTimestamp(2, fineTM);
		
		ArrayList<SalaRiunione> temp = new ArrayList<SalaRiunione>();	//inizializza la lista di sale da restituire in seguito
		ResultSet risultato = getSaleLiberePS.executeQuery();	//esegue la query per ottenere il ResultSet
		
		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"), risultato.getInt("Piano"));	//crea l'oggetto sala temporaneo da aggiungere alla lista
			temp.add(salaTemp);	//aggiunge la sala alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo che aggiunge una sala alla tabella SalaRiunione nel DB
	@Override
	public boolean addSala(SalaRiunione sala) throws SQLException {
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
	public boolean removeSala(SalaRiunione sala) throws SQLException {
		removeSalaPS.setString(1, sala.getCodSala());	//inserisce il codice della sala da rimuovere nella delete
		
		int record = removeSalaPS.executeUpdate();	//esegue la delete e salva il numero di record eliminati (1=eliminato, 0=non eliminato)
		
		if (record == 1)
			return true;
		else
			return false;
	}

}
