/*Implementazione dell'interfaccia LuogoNascitaDAO per PostgreSQL.
*Contiene le definizioni dei metodi già presenti nell'interfaccia per operare sulla tabella
*LuogoNascita del DB, i relativi PreparedStatement per operare più rapidamente sul DB e
*il costruttore del DAO.
*Struttura tabella LuogoNascita:
*|(1) NomeComune:String|(2) NomeProvincia:String|(3) CodComune:String|
*|_____________________|________________________|____________________|
******************************************************************************************/
package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.LuogoNascita;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;

public class LuogoNascitaDAOPSQL implements LuogoNascitaDAO {

	//ATTRIBUTI
	
	private Connection connection;	//connessione al DB necessaria per operare
	private PreparedStatement getProvincePS,getLuoghiByProvinciaPS, getLuogoByCodPS, getDipendentiByLuogoPS;	//PreparedStatemtn delle operazioni più comuni da compiere nel DB
	
	//METODI
	
	//Costruttore
	public LuogoNascitaDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;	//ottiene la connessione al DB dal manager
		
		getProvincePS = connection.prepareStatement("SELECT DISTINCT l.NomeProvincia FROM LuogoNascita AS l ORDER BY l.NomeProvincia");	//inizializza PreparedStatement per ottenere le province
		getLuoghiByProvinciaPS = connection.prepareStatement("SELECT * FROM LuogoNascita AS l WHERE l.NomeProvincia LIKE ?");	//inizializza PreparedStatement per ottenere i luoghi di una specifica provincia
		getLuogoByCodPS = connection.prepareStatement("SELECT * FROM LuogoNascita AS l WHERE l.CodComune = ?");	//inizializza PreparedStatement per ottenere un luogo tramite il codice del comune
		getDipendentiByLuogoPS = connection.prepareCall("SELECT * FROM Dipendente AS d WHERE d.CodComune = ?");	//inizializza il PreparedStatement per ottenere i dipendenti nati in un certo luogo
	}
	
	//Metodo GetProvince.
	/*Metodo che interroga il DB per ottenere tutte le province presenti.
	*Restituisce un ArrayList<String> di province se tutto va a buon fine.*/
	@Override
	public ArrayList<String> getProvince() throws SQLException {
		ResultSet risultato = getProvincePS.executeQuery();	//esegue la query per ottenere un ResultSet
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
	public ArrayList<LuogoNascita> getLuoghiByProvincia(String provincia) throws SQLException {
		getLuoghiByProvinciaPS.setString(1, provincia);	//inserisce la provincia richiesta nello statement
		ResultSet risultato = getLuoghiByProvinciaPS.executeQuery();	//esegue la query
		ArrayList<LuogoNascita> temp = new ArrayList<LuogoNascita>();	//inizializza la lista dei luoghi da restituire
		
		//finchè il ResultSet contiene ancora luoghi di quella provincia li aggiunge alla lista
		while (risultato.next()) {
			LuogoNascita tempLuogo = new LuogoNascita(risultato.getString(3),risultato.getString(1),risultato.getString(2));
			temp.add(tempLuogo);
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo GetLuogoByCod.
	/*Metodo che interroga il DB per ottenere il LuogoNascita con CodComune uguale a quello inserito come parametro.
	Restituisce un oggetto LuogoNascita con codice comune pari alla stringa in input.*/
	@Override
	public LuogoNascita getLuogoByCod(String codComune) throws SQLException {
		getLuogoByCodPS.setString(1, codComune.toUpperCase());	//inserisce il parametro nella query
		ResultSet risultato = getLuogoByCodPS.executeQuery();	//esegue la query per ottenere il ResultSet
		risultato.next();
		LuogoNascita temp = new LuogoNascita(risultato.getString("CodComune").toUpperCase(), risultato.getString("NomeComune"), risultato.getString("NomeProvincia"));	//crea il luogonascita temporaneo
		risultato.close(); //chiude il ResultSet
		return temp;
	}

	//Metodo GetDipendentiByLuogo.
	/*Metodo che interroga il DB per ottenere una lista di dipendenti
	*che vivono nello stesso luogo inserito come parametro in input.*/
	@Override
	public ArrayList<Dipendente> getDipendentiByLuogo(LuogoNascita luogo) throws SQLException {
		getDipendentiByLuogoPS.setString( 1, luogo.getCodiceComune());	//inserisce il codice del comune come parametro nella query
		ResultSet risultato = getDipendentiByLuogoPS.executeQuery();	//esegue la query per ottere il ResultSet
		ArrayList<Dipendente> temp = new ArrayList<Dipendente>();	//inizializza la lista da restituire alla terminazione
		
		DipendenteDAO dipDAO = new DipendenteDAOPSQL(this.connection);	//inizializza il DipendenteDAO necessario per ottenere la sua valutazione
		
		//finchè il ResultSet possiede record validi
		while(risultato.next()) {
			Dipendente tempDip = new Dipendente(risultato.getString("Nome"), risultato.getString("Cognome"), risultato.getString("Sesso").charAt(0), new LocalDate(risultato.getDate("DataNascita")),
					luogo, risultato.getString("Indirizzo"), risultato.getString("Email"), risultato.getString("TelefonoCasa"), risultato.getString("Cellulare"),
					risultato.getFloat("Salario"), risultato.getString("Password"));	//crea il dipendente temporaneo
			tempDip.setCf(risultato.getString("CF"));	//salva il codice fiscale del dipendente
			tempDip.setValutazione(dipDAO.getValutazione(tempDip.getCf()));	//recupera la sua valutazione
			temp.add(tempDip);	//lo aggiunge alla lista
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}
}
