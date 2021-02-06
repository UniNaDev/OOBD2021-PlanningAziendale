//Interfaccia DAO di SalaRiunione per le varie operazioni che coinvolgono il DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDateTime;

import entita.SalaRiunione;

public interface SalaRiunioneDAO {
	public ArrayList<SalaRiunione> getSale() throws SQLException;	//meotodo che restituisce tutte le sale dell'azienda
	public boolean addSala(SalaRiunione sala) throws SQLException;	//metodo che aggiunge una nuova sala al DB
	public boolean updateSala(SalaRiunione sala) throws SQLException;	//metodo che modifica e aggiorna una sala nel DB
	public boolean removeSala(SalaRiunione sala) throws SQLException;	//metodo che rimuove una sala dal DB
	public SalaRiunione getSalaByCod(String codSala) throws SQLException;	//metodo che restituisce la sala con il codice inserito nei parametri in input
}
