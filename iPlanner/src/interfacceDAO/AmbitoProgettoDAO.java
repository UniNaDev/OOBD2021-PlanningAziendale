//Interfaccia di AmbitoProgetto per operare sul DB. Contiene tutti i metodi che operano sul DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.AmbitoProgetto;
import entita.Progetto;


public interface AmbitoProgettoDAO {
	public ArrayList<AmbitoProgetto> ottieniAmbiti() throws SQLException;
	
	public boolean insertAmbito(AmbitoProgetto ambito) throws SQLException;
	
	public ArrayList<AmbitoProgetto> getAmbitiOfProgetto(Progetto proj) throws SQLException;
	
	public boolean insertAmbitiOfProgetto(Progetto proj) throws SQLException;
	
	//TODO: eliminare e sostituire le chiamate con getAmbitiOfProgetto
	public ArrayList<AmbitoProgetto> getAmbitiProgettoByCodice(int codProgetto) throws SQLException;
	
	public boolean deleteAmbitiProgetto (Progetto proj) throws SQLException;
}
