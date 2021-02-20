//Interfaccia DAO di SalaRiunione per le varie operazioni che coinvolgono il DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.SalaRiunione;

public interface SalaRiunioneDAO {
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException;
	
	public boolean creaSala(SalaRiunione sala) throws SQLException;
	
	public boolean aggiornaSala(SalaRiunione sala, String nuovoCodSala) throws SQLException;
	
	public boolean eliminaSala(SalaRiunione sala) throws SQLException;
	
	public SalaRiunione ottieniSalaDaCodSala(String codSala) throws SQLException;
}
