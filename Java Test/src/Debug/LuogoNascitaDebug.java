package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManager.ManagerConnessioneDB;
import Entita.Dipendente;
import Entita.LuogoNascita;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import InterfacceDAO.LuogoNascitaDAO;

public class LuogoNascitaDebug {

	public static void main(String[] args) {

		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		LuogoNascitaDAO luogoDAO = null;
		
		ArrayList<String> province = new ArrayList<String>();
		ArrayList<LuogoNascita> luoghi = new ArrayList<LuogoNascita>();
		String provincia = "Napoli";
		String codComune = "f839";
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		
		try {
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			
			
			luogoDAO = new LuogoNascitaDAOPSQL(connection);
			
			//Ottieni tutte le province
			province = luogoDAO.getProvince();
			System.out.println("\nTutte le province:");
			for (String pro : province)
				System.out.println(pro);
			
			//Ottieni tutti i luoghi di una provincia
			luoghi = luogoDAO.getLuoghiByProvincia(provincia);
			System.out.println("Tutti i luoghi in provincia di " + provincia);
			for (LuogoNascita luogo: luoghi)
				System.out.println(luogo);
			
			//Ottieni dipendenti che sono nati a Napoli (NA)
			LuogoNascita luogo = luogoDAO.getLuogoByCod(codComune);
			dipendenti = luogoDAO.getDipendentiByLuogo(luogo);
			System.out.println("Tutti i dipendenti nati a " + luogo);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			} 
		catch(SQLException e) {
			System.out.println("Errore SQL. " + e.getMessage());
		}
	}

}
