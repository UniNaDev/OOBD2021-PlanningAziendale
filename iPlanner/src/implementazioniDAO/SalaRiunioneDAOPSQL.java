//Implementazione dell'interfaccia SalaRiunioneDAO per PostgreSQL con relativi PreparedStatement.

package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import entita.SalaRiunione;
import interfacceDAO.SalaRiunioneDAO;

public class SalaRiunioneDAOPSQL implements SalaRiunioneDAO {
	private Connection connection;
	private PreparedStatement getSalePS,addSalaPS,updateSalaPS,removeSalaPS,getSalaByCodPS;
	
	public SalaRiunioneDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		getSalePS = connection.prepareStatement("SELECT * FROM SalaRiunione ORDER BY CodSala");
		addSalaPS = connection.prepareStatement("INSERT INTO SalaRiunione VALUES (?,?,?,?)");
		updateSalaPS = connection.prepareStatement("UPDATE SalaRiunione SET CodSala = ?, Capienza = ?, Indirizzo = ?, Piano = ? WHERE CodSala = ?");
		removeSalaPS = connection.prepareStatement("DELETE FROM SalaRiunione WHERE CodSala = ?");
		getSalaByCodPS = connection.prepareStatement("SELECT * FROM SalaRiunione AS sr WHERE sr.CodSala = ?");
	}
	
	@Override
	public ArrayList<SalaRiunione> getSale() throws SQLException {
		ResultSet risultato = getSalePS.executeQuery();
		ArrayList<SalaRiunione> sale = new ArrayList<SalaRiunione>();
		
		while(risultato.next()) {
			SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"), risultato.getInt("Piano"));	//crea l'oggetto sala temporaneo da aggiungere alla lista
			sale.add(salaTemp);
		}
		risultato.close();
		
		return sale;
	}

	@Override
	public boolean creaSala(SalaRiunione sala) throws SQLException {
		addSalaPS.setString(1, sala.getCodiceSala());
		addSalaPS.setInt(2, sala.getCapienza());
		addSalaPS.setString(3, sala.getIndirizzoSede());
		addSalaPS.setInt(4, sala.getPiano());
		
		int record = addSalaPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean aggiornaSala(SalaRiunione sala, String nuovoCodSala) throws SQLException {
		String vecchioCodSala = sala.getCodiceSala();
		updateSalaPS.setString(1, nuovoCodSala);
		updateSalaPS.setInt(2, sala.getCapienza());
		updateSalaPS.setString(3, sala.getIndirizzoSede());
		updateSalaPS.setInt(4, sala.getPiano());
		updateSalaPS.setString(5, vecchioCodSala);
		
		int record = updateSalaPS.executeUpdate();
		
		if (record == 1) {
			sala.setCodiceSala(nuovoCodSala);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean deleteSala(SalaRiunione sala) throws SQLException {
		removeSalaPS.setString(1, sala.getCodiceSala());
		
		int record = removeSalaPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public SalaRiunione getSalaByCod(String codSala) throws SQLException {
		if (codSala != null) {
			getSalaByCodPS.setString(1, codSala);
		
		ResultSet risultato = getSalaByCodPS.executeQuery();
		
		risultato.next();
		SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"),risultato.getInt("Piano"));
		
		return salaTemp;
		}
		else
			return null;
	}
}
