//Interfaccia di AmbitoProgetto per operare sul DB. Contiene tutti i metodi che operano sul DB.

package InterfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Entità.AmbitoProgetto;

public interface AmbitoProgettoDAO {
	
	//METODI
	
	public ArrayList<AmbitoProgetto> getAmbiti() throws SQLException;	//metodo che ottiene una lista di tutti gli ambiti esistenti nel DB
	public boolean addAmbito(AmbitoProgetto ambito) throws SQLException;	//metodo che inserisce un nuovo ambito nel DB
	public boolean removeAmbito(AmbitoProgetto ambito) throws SQLException;	//metodo che rimuove un ambito esistente nel DB
}
