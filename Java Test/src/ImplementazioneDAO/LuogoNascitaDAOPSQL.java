/*Implementazione dell'interfaccia LuogoNascitaDAO per PostgreSQL.
*Contiene le definizioni dei metodi già presenti nell'interfaccia per operare sulla tabella
*LuogoNascita del DB, i relativi PreparedStatement per operare più rapidamente sul DB e
*il costruttore del DAO.
*Struttura tabella LuogoNascita:
*|(1) NomeComune:String|(2) NomeProvincia:String|(3) CodComune:String|
*|_____________________|________________________|____________________|
******************************************************************************************/
package ImplementazioneDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entità.LuogoNascita;
import InterfacceDAO.LuogoNascitaDAO;

public class LuogoNascitaDAOPSQL implements LuogoNascitaDAO {

	//ATTRIBUTI
	
	private Connection connection;	//connessione al DB necessaria per operare
	private PreparedStatement getProvince,getLuoghiByProvincia;	//PreparedStatemtn delle operazioni più comuni da compiere nel DB
	
	//METODI
	
	//Costruttore
	public LuogoNascitaDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione al DB dal manager
		
		getProvince = connection.prepareStatement("SELECT DISTINCT l.NomeProvincia FROM LuogoNascita AS l ORDER BY l.NomeProvincia");	//inizializza PreparedStatement per ottenere le province
		getLuoghiByProvincia = connection.prepareStatement("SELECT * FROM LuogoNascita AS l WHERE l.NomeProvincia LIKE ?");	//inizializza PreparedStatement per ottenere i luoghi di una specifica provincia
	}
	
	//Metodo GetProvince.
	/*Metodo che interroga il DB per ottenere tutte le province presenti.
	*Restituisce un ArrayList<String> di province se tutto va a buon fine.*/
	@Override
	public ArrayList<String> GetProvince() throws SQLException {
		ResultSet risultato = getProvince.executeQuery();	//esegue la query per ottenere un ResultSet
		ArrayList<String> temp = new ArrayList<String>();	//inizializza la lista temporanea da restituire alla fine
		
		//finchè ci sono ancora province nel ResultSet le aggiunge alla lista
		while(risultato.next()) {
			temp.add(risultato.getString(1));
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//MetodoGetLuoghiByProvincia.
	/*Metodo che interroga il DB per ottenere tutti i luoghi di una specifica provincia.
	Restituisce un ArrayList<LuogoNascita> di luoghi che appartengono a quella specifica provincia.*/
	@Override
	public ArrayList<LuogoNascita> GetLuoghiByProvincia(String provincia) throws SQLException {
		getLuoghiByProvincia.setString(1, provincia);	//inserisce la provincia richiesta nello statement
		ResultSet risultato = getLuoghiByProvincia.executeQuery();	//esegue la query
		ArrayList<LuogoNascita> temp = new ArrayList<LuogoNascita>();	//inizializza la lista dei luoghi da restituire
		
		//finchè il ResultSet contiene ancora luoghi di quella provincia li aggiunge alla lista
		while (risultato.next()) {
			LuogoNascita tempLuogo = new LuogoNascita(risultato.getString(3),risultato.getString(1),risultato.getString(2));
			temp.add(tempLuogo);
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

}
