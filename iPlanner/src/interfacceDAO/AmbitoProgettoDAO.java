//Interfaccia di AmbitoProgetto per operare sul DB. Contiene tutti i metodi che operano sul DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.AmbitoProgetto;
import entita.Progetto;

public interface AmbitoProgettoDAO {
	
	//METODI
	
	public ArrayList<AmbitoProgetto> getAmbiti() throws SQLException;	//metodo che ottiene una lista di tutti gli ambiti esistenti nel DB
	public boolean addAmbito(AmbitoProgetto ambito) throws SQLException;	//metodo che inserisce un nuovo ambito nel DB
	public boolean removeAmbito(AmbitoProgetto ambito) throws SQLException;	//metodo che rimuove un ambito esistente nel DB
	public ArrayList<AmbitoProgetto> getAmbitiProgetto(Progetto proj) throws SQLException;	//metodo che restituisce gli ambiti di un progetto
	public boolean addAmbitiProgetto(Progetto proj) throws SQLException;	//metodo che inserisce gli ambiti di un progetto nel DB
	public ArrayList<AmbitoProgetto> getAmbitiProgettoByCodice(int codProgetto) throws SQLException; //metodo che restituisce gli ambiti sapendo solo il codice del progetto
}
