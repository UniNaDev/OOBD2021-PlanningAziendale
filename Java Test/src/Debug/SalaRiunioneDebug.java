package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDateTime;

import DBManager.ManagerConnessioneDB;
import Entità.SalaRiunione;
import ImplementazioneDAO.SalaRiunioneDAOPSQL;
import InterfacceDAO.SalaRiunioneDAO;

public class SalaRiunioneDebug {

	public static void main(String[] args) {


		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		SalaRiunioneDAO salaDAO = null;
		
		String nome = "NuovoAmbito";
		
		try {
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			
			salaDAO = new SalaRiunioneDAOPSQL(connection);
			ArrayList<SalaRiunione> sale = new ArrayList<SalaRiunione>();
			
			//Ottieni tutte le sale nel DB
			sale = salaDAO.getSale();
			System.out.println("\nTutte le sale riunioni:");
			for (SalaRiunione sr : sale)
				System.out.println(sr);
			
			//Ottieni sale con capienza minima 120 posti
			int cap = 120;
			sale = salaDAO.getSaleByCap(cap);
			System.out.println("\nTutte le sale riunioni con capienza minima " + cap);
			for (SalaRiunione sr : sale)
				System.out.println(sr);
			
			//Inserisci una nuova sala
			String codSala = "SalaNuova";
			String indirizzo = "via Di Qui, 22";
			int piano = 2;
			SalaRiunione salaNew = new SalaRiunione(codSala,cap,indirizzo,piano);
			if (salaDAO.addSala(salaNew))
				System.out.println("\n" + salaNew + " inserita");
			
			//Modifica della nuova sala
			salaNew.setCap(50);
			if (salaDAO.updateSala(salaNew))
				System.out.println("\n" + salaNew + " modificata");
			
			//Rimuovi la sala appena aggiunta
			if (salaDAO.removeSala(salaNew))
				System.out.println("\n" + salaNew + " rimossa");
			
			//Ottieni sale libere il 21/10/2020 dalle 13:00 alle 15:00
			LocalDateTime inizio = new LocalDateTime(2020,10,21,13,0);
			LocalDateTime fine = new LocalDateTime(2020,10,21,15,0);
			
			sale = salaDAO.getSaleLibere(inizio, fine);
			System.out.println("\nTutte le sale riunioni libere dal " + inizio + " a " + fine);
			for (SalaRiunione sr : sale)
				System.out.println(sr);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
