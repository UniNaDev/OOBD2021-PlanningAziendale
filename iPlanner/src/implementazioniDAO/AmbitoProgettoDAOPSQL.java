//Implementazione dell'interfaccia AmbitoProgettoDAO per PostgreSQL

package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entita.AmbitoProgetto;
import entita.Progetto;
import interfacceDAO.AmbitoProgettoDAO;

public class AmbitoProgettoDAOPSQL implements AmbitoProgettoDAO {
	private Connection connection;
	private PreparedStatement ottieniAmbitiPS, inserisciAmbitoPS, ottieniAmbitiProgettoPS, inserisciAmbitiProgettoPS,eliminaAmbitiProgettoPS;
	
	public AmbitoProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		ottieniAmbitiPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto");
		inserisciAmbitoPS = connection.prepareStatement("INSERT INTO AmbitoProgetto(NomeAmbito) VALUES (?)");
		ottieniAmbitiProgettoPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto AS ap WHERE ap.IDAmbito IN (SELECT l.IDAmbito FROM AmbitoProgettoLink AS l WHERE l.CodProgetto = ?)");
		inserisciAmbitiProgettoPS = connection.prepareStatement("INSERT INTO AmbitoProgettoLink VALUES (?,?)");
		eliminaAmbitiProgettoPS = connection.prepareStatement("DELETE FROM AmbitoProgettoLink WHERE codprogetto = ?");
	}
	
	@Override  //Ok
	public ArrayList<AmbitoProgetto> ottieniAmbiti() throws SQLException {
		ResultSet risultato = ottieniAmbitiPS.executeQuery();
		ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
		
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt(1), risultato.getString(2));
			ambiti.add(ambitoTemp);
		}
		risultato.close();
		
		return ambiti;
	}

	@Override  //Ok
	public boolean inserisciAmbito(AmbitoProgetto ambito) throws SQLException {
		inserisciAmbitoPS.setString(1, ambito.getNomeAmbito());
		
		int record = inserisciAmbitoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override  //Ok
	public ArrayList<AmbitoProgetto> ottieniAmbitiDelProgetto(Progetto progetto) throws SQLException {
		ottieniAmbitiProgettoPS.setInt(1, progetto.getIdProgettto());
		ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
		ResultSet risultato = ottieniAmbitiProgettoPS.executeQuery();
		
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt("IDAmbito"), risultato.getString("NomeAmbito"));
			ambiti.add(ambitoTemp);
		}
		risultato.close();
		
		return ambiti;
	}

	@Override  //Ok
	public boolean inserisciAmbitiProgetto(Progetto progetto) throws SQLException {
		int record = 0;
		
		for (AmbitoProgetto ambito : progetto.getAmbiti()) {
			inserisciAmbitiProgettoPS.setInt(1, ambito.getIdAmbito());
			inserisciAmbitiProgettoPS.setInt(2, progetto.getIdProgettto());
			
			record += inserisciAmbitiProgettoPS.executeUpdate();
		}
		
		if (record == progetto.getAmbiti().size())
			return true;
		else
			return false;
	}

	@Override //Da verificare
	public ArrayList<AmbitoProgetto> ottieniAmbitiProgettoDaCodice(int codProgetto) throws SQLException {
		ottieniAmbitiProgettoPS.setInt(1, codProgetto);
		ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
		ResultSet risultato = ottieniAmbitiProgettoPS.executeQuery();
		
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt("IDAmbito"), risultato.getString("NomeAmbito"));
			ambiti.add(ambitoTemp);
		}
		risultato.close();
		
		return ambiti;
	}

	@Override  //Ok
	public boolean eliminaAmbitiProgetto (Progetto progetto) throws SQLException {
		eliminaAmbitiProgettoPS.setInt(1, progetto.getIdProgettto());
		
		int risultato = eliminaAmbitiProgettoPS.executeUpdate();
		
		if(risultato > 0)
			return true;		
		else 
			return false;		
	}
}
