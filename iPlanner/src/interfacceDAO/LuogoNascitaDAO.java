/*Interfaccia DAO per l'entità LuogoNascita.
*Comprende tutti i metodi da implementare in seguito per interfacciarsi con il DB
*e operare nella tabella LuogoNascita. 
********************************************************************************/
package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.LuogoNascita;

public interface LuogoNascitaDAO {
	public ArrayList<String> ottieniProvince() throws SQLException;
	
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException;
	
	public LuogoNascita ottieniLuogoNascitaDaCodComune(String codComune) throws SQLException;
}
