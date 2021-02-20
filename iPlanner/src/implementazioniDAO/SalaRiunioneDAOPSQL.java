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
	private PreparedStatement ottieniSalePS,creaSalaPS,aggiornaSalaPS,eliminaSalaPS,ottieniSalaDaCodSalaPS;
	
	public SalaRiunioneDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		ottieniSalePS = connection.prepareStatement("SELECT * FROM SalaRiunione ORDER BY CodSala");
		creaSalaPS = connection.prepareStatement("INSERT INTO SalaRiunione VALUES (?,?,?,?)");
		aggiornaSalaPS = connection.prepareStatement("UPDATE SalaRiunione SET CodSala = ? ,Capienza = ?, Indirizzo = ?, Piano = ? WHERE CodSala = ?");
		eliminaSalaPS = connection.prepareStatement("DELETE FROM SalaRiunione WHERE CodSala = ?");
		ottieniSalaDaCodSalaPS = connection.prepareStatement("SELECT * FROM SalaRiunione AS sr WHERE sr.CodSala = ?");
	}
	
	@Override 
	public ArrayList<SalaRiunione> ottieniSale() throws SQLException {
		ResultSet risultato = ottieniSalePS.executeQuery();
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
		creaSalaPS.setString(1, sala.getCodiceSala());
		creaSalaPS.setInt(2, sala.getCapienza());
		creaSalaPS.setString(3, sala.getIndirizzoSede());
		creaSalaPS.setInt(4, sala.getPiano());
		
		int record = creaSalaPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  
	public boolean aggiornaSala(SalaRiunione sala, String nuovoCodSala) throws SQLException {
		String vecchioCodSala = sala.getCodiceSala();
		aggiornaSalaPS.setString(5, vecchioCodSala);
		aggiornaSalaPS.setString(1, nuovoCodSala);
		aggiornaSalaPS.setInt(2, sala.getCapienza());
		aggiornaSalaPS.setString(3, sala.getIndirizzoSede());
		aggiornaSalaPS.setInt(4, sala.getPiano());

		int record = aggiornaSalaPS.executeUpdate();
		
		if (record == 1) {
			sala.setCodiceSala(nuovoCodSala);
			return true;
		}
		else
			return false;
	}

	@Override 
	public boolean eliminaSala(SalaRiunione sala) throws SQLException {
		eliminaSalaPS.setString(1, sala.getCodiceSala());
		
		int record = eliminaSalaPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override 
	public SalaRiunione ottieniSalaDaCodSala(String codSala) throws SQLException {
		if (codSala != null) {
			ottieniSalaDaCodSalaPS.setString(1, codSala);
		
		ResultSet risultato = ottieniSalaDaCodSalaPS.executeQuery();
		
		risultato.next();
		SalaRiunione salaTemp = new SalaRiunione(risultato.getString("CodSala"),risultato.getInt("Capienza"),risultato.getString("Indirizzo"),risultato.getInt("Piano"));
		
		return salaTemp;
		}
		else
			return null;
	}
}
