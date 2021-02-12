//Implementazione dell'interfaccia AmbitoProgettoDAO per PostgreSQL

package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entita.AmbitoProgetto;
import entita.Progetto;
import interfacceDAO.AmbitoProgettoDAO;

public class AmbitoProgettoDAOPSQL implements AmbitoProgettoDAO {

	//ATTRIBUTI
	//----------------------------------------
	private Connection connection;
	private PreparedStatement getAmbitiPS, addAmbitoPS, getAmbitiProgettoPS, addAmbitiProgettoPS,removeAmbitiProgettoPS;
	
	//METODI
	//----------------------------------------
	
	//Costruttore
	public AmbitoProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione dal ManagerConnessioneDB quando richiamato
		
		//inizializza gli statement preparati
		getAmbitiPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto");
		addAmbitoPS = connection.prepareStatement("INSERT INTO AmbitoProgetto(NomeAmbito) VALUES (?)"); //? = nome dell'ambito da inserire
		getAmbitiProgettoPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto AS ap WHERE ap.IDAmbito IN (SELECT l.IDAmbito FROM AmbitoProgettoLink AS l WHERE l.CodProgetto = ?)");	//?=Codice del progetto di cui si vogliono gli ambiti
		addAmbitiProgettoPS = connection.prepareStatement("INSERT INTO AmbitoProgettoLink VALUES (?,?)"); //? = id dell'ambito, ? = codice progetto
		removeAmbitiProgettoPS = connection.prepareStatement("DELETE FROM AmbitoProgettoLink WHERE codprogetto = ?");// ? = codice del progetto di cui vogliamo rimuovere tutti gli ambiti
	}
	
	//Metodo che restituisce una lista di tutti gli ambiti esistenti nel DB
	@Override
	public ArrayList<AmbitoProgetto> getAmbiti() throws SQLException {
		ResultSet risultato = getAmbitiPS.executeQuery();
		ArrayList<AmbitoProgetto> temp = new ArrayList<AmbitoProgetto>();	//inizializza la lista da poi restituire
			//esegue la query e ottiene il ResultSet
		
		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt(1), risultato.getString(2));
			temp.add(ambitoTemp);
			
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

	//Metodo che inserisce un nuovo ambito nel DB
	@Override
	public boolean insertAmbito(AmbitoProgetto ambito) throws SQLException {
		addAmbitoPS.setString(1, ambito.getNomeAmbito()); 	//inserisce il nome dell'ambito nuovo nell'insert
		
		int record = addAmbitoPS.executeUpdate();	//esegue l'insert e salva il numero di record modificati (1=aggiunto, 0=non aggiunto)
		
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che restituisce gli ambiti di un progetto
	@Override
	public ArrayList<AmbitoProgetto> getAmbitiOfProgetto(Progetto proj) throws SQLException {
		getAmbitiProgettoPS.setInt(1, proj.getIdProgettto());	//inserisce il codice del progetto nella query
		ArrayList<AmbitoProgetto> temp = new ArrayList<AmbitoProgetto>();	//inizializza la lista da restituire
		
		ResultSet risultato = getAmbitiProgettoPS.executeQuery();	//esegue la query e restituisce il ResultSet
		
		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt("IDAmbito"), risultato.getString("NomeAmbito"));
			temp.add(ambitoTemp);
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

	//Metodo che inserisce gli ambiti di un progetto
	@Override
	public boolean insertAmbitiOfProgetto(Progetto proj) throws SQLException {
		int record = 0;
		
		//per ogni ambito del progetto
		for (AmbitoProgetto ambito : proj.getAmbiti()) {
			addAmbitiProgettoPS.setInt(1, ambito.getIdAmbito()); //id ambito
			addAmbitiProgettoPS.setInt(2, proj.getIdProgettto()); //codice progetto
			
			record += addAmbitiProgettoPS.executeUpdate();	//esegue l'insert e aggiorna il numero di record inseriti
		}
		
		//se il numero di record inseriti è lo stesso del numero di ambiti del progetto allora è andato in porto tutto l'inserimento
		if (record == proj.getAmbiti().size())
			return true;
		else
			return false;
	}

	//Metodo che restituisce gli ambiti di un progetto prendendo in input solo il codice
	@Override
	public ArrayList<AmbitoProgetto> getAmbitiProgettoByCodice(int codProgetto) throws SQLException {
		getAmbitiProgettoPS.setInt(1, codProgetto);	//inserisce il codice del progetto nella query
		ArrayList<AmbitoProgetto> temp = new ArrayList<AmbitoProgetto>();	//inizializza la lista da restituire
		
		ResultSet risultato = getAmbitiProgettoPS.executeQuery();	//esegue la query e restituisce il ResultSet
		
		//finchè ci sono record nel ResultSet
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt("IDAmbito"), risultato.getString("NomeAmbito"));
			temp.add(ambitoTemp);
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

	//Metodo che rimuove tutti gli ambiti di un progetto
	@Override
	public boolean deleteAmbitiProgetto (Progetto proj) throws SQLException
	{
		removeAmbitiProgettoPS.setInt(1, proj.getIdProgettto());
		
		int risultato = removeAmbitiProgettoPS.executeUpdate();
		
		if(risultato ==1)
			return true;		
		else 
			return false;
			
	}
}
