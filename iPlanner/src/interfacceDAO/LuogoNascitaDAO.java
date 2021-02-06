/*Interfaccia DAO per l'entità LuogoNascita.
*Comprende tutti i metodi da implementare in seguito per interfacciarsi con il DB
*e operare nella tabella LuogoNascita. 
********************************************************************************/
package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.LuogoNascita;

public interface LuogoNascitaDAO {
	
	//METODI
	
	public ArrayList<String> getProvince() throws SQLException;	//metodo che restituisce la lista di tutte le province nel DB
	public ArrayList<LuogoNascita> getLuoghiByProvincia(String provincia) throws SQLException;	//metodo che restituisce la lista di luoghi:LuogoNascita di una provincia presenti nel DB
	public LuogoNascita getLuogoByCod(String codComune) throws SQLException;	//ottiene il luogo di nascita con il codice comune (primary key) uguale la parametro della function
}
