/*Implementazione dell'interfaccia LuogoNascitaDAO per PostgreSQL.
*Contiene le definizioni dei metodi già presenti nell'interfaccia per operare sulla tabella
*LuogoNascita del DB, i relativi PreparedStatement per operare più rapidamente sul DB e
*il costruttore del DAO.
******************************************************************************************/
package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entita.LuogoNascita;
import interfacceDAO.LuogoNascitaDAO;

public class LuogoNascitaDAOPSQL implements LuogoNascitaDAO {
	private Connection connection;
	private PreparedStatement ottieniProvincePS,ottieniComuniPS, ottieniLuogoNascitaDaCodComunePS;
	
	public LuogoNascitaDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		ottieniProvincePS = connection.prepareStatement("SELECT DISTINCT l.NomeProvincia FROM LuogoNascita AS l ORDER BY l.NomeProvincia");
		ottieniComuniPS = connection.prepareStatement("SELECT * FROM LuogoNascita AS l WHERE l.NomeProvincia LIKE ?");
		ottieniLuogoNascitaDaCodComunePS = connection.prepareStatement("SELECT * FROM LuogoNascita AS l WHERE l.CodComune = ?");
	}
	
	@Override  //Ok
	public ArrayList<String> ottieniProvince() throws SQLException {
		ResultSet risultato = ottieniProvincePS.executeQuery();
		ArrayList<String> province = new ArrayList<String>();
		
		while(risultato.next()) {
			province.add(risultato.getString(1));
		}
		risultato.close();
		
		return province;
	}

	@Override  //Ok
	public ArrayList<LuogoNascita> ottieniComuni(String provincia) throws SQLException {
		ottieniComuniPS.setString(1, provincia);
		ResultSet risultato = ottieniComuniPS.executeQuery();
		ArrayList<LuogoNascita> luoghi = new ArrayList<LuogoNascita>();
		
		while (risultato.next()) {
			LuogoNascita tempLuogo = new LuogoNascita(risultato.getString(3),risultato.getString(1),risultato.getString(2));
			luoghi.add(tempLuogo);
		}
		risultato.close();
		
		return luoghi;
	}

	@Override  //Ok
	public LuogoNascita ottieniLuogoNascitaDaCodComune(String codComune) throws SQLException {
		ottieniLuogoNascitaDaCodComunePS.setString(1, codComune.toUpperCase());
		ResultSet risultato = ottieniLuogoNascitaDaCodComunePS.executeQuery();
		risultato.next();
		LuogoNascita luogo = new LuogoNascita(risultato.getString("CodComune").toUpperCase(), risultato.getString("NomeComune"), risultato.getString("NomeProvincia"));
		risultato.close();
		return luogo;
	}
}