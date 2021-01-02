//Implementazione dell'interfaccia AmbitoProgettoDAO per PostgreSQL

package ImplementazioneDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entità.AmbitoProgetto;
import InterfacceDAO.AmbitoProgettoDAO;

public class AmbitoProgettoDAOPSQL implements AmbitoProgettoDAO {

	//ATTRIBUTI
	
	private Connection connection;
	private PreparedStatement getAmbitiPS, addAmbitoPS, removeAmbitoPS;
	
	//METODI
	
	//Costruttore
	public AmbitoProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione dal ManagerConnessioneDB quando richiamato
		
		//inizializza gli statement preparati
		getAmbitiPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto");
		addAmbitoPS = connection.prepareStatement("INSERT INTO AmbitoProgetto(NomeAmbito) VALUES (?)"); //? = nome dell'ambito da inserire
		removeAmbitoPS = connection.prepareStatement("DELETE FROM AmbitoProgetto WHERE NomeAmbito = ?");	//? = nome dell'ambito da rimuovere
	}
	
	//Metodo che restituisce una lista di tutti gli ambiti esistenti nel DB
	@Override
	public ArrayList<AmbitoProgetto> getAmbiti() throws SQLException {
		ArrayList<AmbitoProgetto> temp = new ArrayList<AmbitoProgetto>();	//inizializza la lista da poi restituire
		ResultSet risultato = getAmbitiPS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt(1),risultato.getString(2));	//1=ID, 2=NomeAmbito
			temp.add(ambitoTemp);
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

	//Metodo che inserisce un nuovo ambito nel DB
	@Override
	public boolean addAmbito(AmbitoProgetto ambito) throws SQLException {
		addAmbitoPS.setString(1, ambito.getNome()); 	//inserisce il nome dell'ambito nuovo nell'insert
		
		int record = addAmbitoPS.executeUpdate();	//esegue l'insert e salva il numero di record modificati (1=aggiunto, 0=non aggiunto)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che rimuove un ambito dal DB
	@Override
	public boolean removeAmbito(AmbitoProgetto ambito) throws SQLException {
		removeAmbitoPS.setString(1, ambito.getNome());	//inserisce il nome dell'ambito nella delete
		
		int record = removeAmbitoPS.executeUpdate();	//esegue la delete e salva il numero di record eliminati (1=eliminato, 0=non eliminato)
		
		if (record == 1)
			return true;
		else
			return false;
	}

}
