//Interfaccia di AmbitoProgetto per operare sul DB. Contiene tutti i metodi che operano sul DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.AmbitoProgetto;
import entita.Progetto;


public interface AmbitoProgettoDAO {
	public ArrayList<AmbitoProgetto> ottieniAmbiti() throws SQLException;
	
	public boolean creaAmbitoProgetto(AmbitoProgetto ambito) throws SQLException;
	
	public ArrayList<AmbitoProgetto> ottieniAmbitiDelProgetto(Progetto proj) throws SQLException;
	
	public boolean inserisciAmbitiProgetto(Progetto proj) throws SQLException;
	
	public boolean eliminaAmbitiProgetto (Progetto proj) throws SQLException;
}
