/*Interfaccia DAO per l'entità LuogoNascita.
*Comprende tutti i metodi da implementare in seguito per interfacciarsi con il DB
*e operare nella tabella LuogoNascita. 
********************************************************************************/
package InterfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Entità.LuogoNascita;

public interface LuogoNascitaDAO {
	
	//METODI
	
	public ArrayList<String> GetProvince() throws SQLException;	//metodo che restituisce la lista di tutte le province nel DB
	public ArrayList<LuogoNascita> GetLuoghiByProvincia(String provincia) throws SQLException;	//metodo che restituisce la lista di luoghi:LuogoNascita di una provincia presenti nel DB
	//TODO: metodo che restituisce i dipendenti nati in un luogo specifico e sua implementazione
}
