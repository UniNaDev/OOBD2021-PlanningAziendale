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
	private PreparedStatement getAmbitiPS, addAmbitoPS, getAmbitiProgettoPS, addAmbitiProgettoPS,removeAmbitiProgettoPS;
	
	public AmbitoProgettoDAOPSQL(Connection connection) throws SQLException {
		this.connection = connection;
		
		getAmbitiPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto");
		addAmbitoPS = connection.prepareStatement("INSERT INTO AmbitoProgetto(NomeAmbito) VALUES (?)");
		getAmbitiProgettoPS = connection.prepareStatement("SELECT * FROM AmbitoProgetto AS ap WHERE ap.IDAmbito IN (SELECT l.IDAmbito FROM AmbitoProgettoLink AS l WHERE l.CodProgetto = ?)");
		addAmbitiProgettoPS = connection.prepareStatement("INSERT INTO AmbitoProgettoLink VALUES (?,?)");
		removeAmbitiProgettoPS = connection.prepareStatement("DELETE FROM AmbitoProgettoLink WHERE codprogetto = ?");
	}
	
	@Override
	public ArrayList<AmbitoProgetto> getAmbiti() throws SQLException {
		ResultSet risultato = getAmbitiPS.executeQuery();
		ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
		
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt(1), risultato.getString(2));
			ambiti.add(ambitoTemp);
		}
		risultato.close();
		
		return ambiti;
	}

	@Override
	public boolean insertAmbito(AmbitoProgetto ambito) throws SQLException {
		addAmbitoPS.setString(1, ambito.getNomeAmbito());
		
		int record = addAmbitoPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<AmbitoProgetto> getAmbitiOfProgetto(Progetto progetto) throws SQLException {
		getAmbitiProgettoPS.setInt(1, progetto.getIdProgettto());
		ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
		ResultSet risultato = getAmbitiProgettoPS.executeQuery();
		
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt("IDAmbito"), risultato.getString("NomeAmbito"));
			ambiti.add(ambitoTemp);
		}
		risultato.close();
		
		return ambiti;
	}

	@Override
	public boolean insertAmbitiOfProgetto(Progetto progetto) throws SQLException {
		int record = 0;
		
		for (AmbitoProgetto ambito : progetto.getAmbiti()) {
			addAmbitiProgettoPS.setInt(1, ambito.getIdAmbito());
			addAmbitiProgettoPS.setInt(2, progetto.getIdProgettto());
			
			record += addAmbitiProgettoPS.executeUpdate();
		}
		
		if (record == progetto.getAmbiti().size())
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<AmbitoProgetto> getAmbitiProgettoByCodice(int codProgetto) throws SQLException {
		getAmbitiProgettoPS.setInt(1, codProgetto);
		ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
		ResultSet risultato = getAmbitiProgettoPS.executeQuery();
		
		while (risultato.next()) {
			AmbitoProgetto ambitoTemp = new AmbitoProgetto(risultato.getInt("IDAmbito"), risultato.getString("NomeAmbito"));
			ambiti.add(ambitoTemp);
		}
		risultato.close();
		
		return ambiti;
	}

	@Override
	public boolean deleteAmbitiProgetto (Progetto progetto) throws SQLException {
		removeAmbitiProgettoPS.setInt(1, progetto.getIdProgettto());
		
		int risultato = removeAmbitiProgettoPS.executeUpdate();
		
		if(risultato ==1)
			return true;		
		else 
			return false;		
	}
}
