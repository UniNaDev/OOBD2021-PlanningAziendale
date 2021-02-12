//Interfaccia DAO di SalaRiunione per le varie operazioni che coinvolgono il DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.SalaRiunione;

public interface SalaRiunioneDAO {
	public ArrayList<SalaRiunione> getSale() throws SQLException;
	
	public boolean insertSala(SalaRiunione sala) throws SQLException;
	
	public boolean updateSala(SalaRiunione sala) throws SQLException;
	
	public boolean deleteSala(SalaRiunione sala) throws SQLException;
	
	public SalaRiunione getSalaByCod(String codSala) throws SQLException;
}
