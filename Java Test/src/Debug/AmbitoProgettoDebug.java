package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManager.ManagerConnessioneDB;
import Entita.AmbitoProgetto;
import ImplementazioneDAO.AmbitoProgettoDAOPSQL;
import InterfacceDAO.AmbitoProgettoDAO;

public class AmbitoProgettoDebug {

	public static void main(String[] args) {
		
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		AmbitoProgettoDAO ambitoDAO = null;
		
		String nome = "NuovoAmbito";
		
		try {
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			
			ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
			ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
			
			//Ottieni tutti gli ambiti nel DB
			System.out.println("\nTutti gli ambiti");
			ambiti = ambitoDAO.getAmbiti();
			for (AmbitoProgetto a: ambiti)
				System.out.println(a);
			
			//Inserisci un nuovo ambito nel DB
			AmbitoProgetto ambito = new AmbitoProgetto(nome);
			if (ambitoDAO.addAmbito(ambito))
				System.out.println("\n" + ambito + " inserito");
			
			//Rimuovi un ambito dal DB
			if (ambitoDAO.removeAmbito(ambito))
				System.out.println("\n" + ambito + " rimosso");
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
