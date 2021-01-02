//Interfaccia DAO di SalaRiunione per le varie operazioni che coinvolgono il DB.

package InterfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDateTime;

import Entità.SalaRiunione;

public interface SalaRiunioneDAO {
	public ArrayList<SalaRiunione> GetSale() throws SQLException;	//meotodo che restituisce tutte le sale dell'azienda
	public ArrayList<SalaRiunione> GetSaleByCap(int cap) throws SQLException;	//metodo che restituisce le sale con capienza superiore a quanto indicato come parametro
	public ArrayList<SalaRiunione> GetSaleLibere(LocalDateTime inizio, LocalDateTime fine) throws SQLException;	//metodo che restituisce le sale libere nell'intervallo temporale definito dai parametri inizio e fine
	public boolean AddSala(SalaRiunione sala) throws SQLException;	//metodo che aggiunge una nuova sala al DB
	public boolean UpdateSala(SalaRiunione sala) throws SQLException;	//metodo che modifica e aggiorna una sala nel DB
	public boolean RemoveSala(SalaRiunione sala) throws SQLException;	//metodo che rimuove una sala dal DB
}
