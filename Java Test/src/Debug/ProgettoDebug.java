package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import DBManager.ManagerConnessioneDB;
import Entita.Dipendente;
import Entita.Meeting;
import Entita.Progetto;
import ImplementazioneDAO.AmbitoProgettoDAOPSQL;
import ImplementazioneDAO.ProgettoDAOPSQL;
import InterfacceDAO.AmbitoProgettoDAO;
import InterfacceDAO.ProgettoDAO;

public class ProgettoDebug {

	public static void main(String[] args) {
		
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		ProgettoDAO projDAO = null;
		AmbitoProgettoDAO ambitoDAO = null;
		
		ArrayList<Progetto> progetti = new ArrayList<Progetto>();
		ArrayList<Dipendente> partecipanti = new ArrayList<Dipendente>();
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		
		String nome = "Nuovo Progetto";
		String tipo = "Ricerca sperimentale";
		String descrizione = null;
		LocalDate creazione = LocalDate.now();
		LocalDate scadenza = null;
		Dipendente loggedIN = null;
		
		try {
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			
			projDAO = new ProgettoDAOPSQL(connection);
			ambitoDAO = new AmbitoProgettoDAOPSQL(connection);
			
			//Ottieni tutti i progetti
			System.out.println("\nTutti i progetti");
			progetti = projDAO.getProgetti();
			for (Progetto p: progetti)
				System.out.println(p + "\n");
			
			//Ottieni partecipanti a un progetto
			System.out.println("\nPartecipanti al progetto " + progetti.get(0));
			partecipanti = projDAO.getPartecipanti(progetti.get(0));
			for (Dipendente dip: partecipanti)
				System.out.println(dip);
			
			//Ottiene progetti di un dipendente
			System.out.println("\nProgetti del dipendente " + partecipanti.get(0));
			progetti = projDAO.getProgettiByDipendente(partecipanti.get(0));
			for (Progetto p: progetti)
				System.out.println(p + "\n");
			
			//Ottiene progetti creati da un dipendente
			System.out.println("\nProgetti creati da " + partecipanti.get(0));
			progetti = projDAO.getProgettiByCreatore(partecipanti.get(0));
			for (Progetto p: progetti)
				System.out.println(p + "\n");
			
			//Ottiene i progetti di un certo ambito
			System.out.println("\nProgetti di ambito " + progetti.get(0).getAmbiti().get(0));
			progetti = projDAO.getProgettiByAmbito(progetti.get(0).getAmbiti().get(0));
			for (Progetto p: progetti)
				System.out.println(p + "\n");
			
			//Ottieni i progetti di un certo tipo
			System.out.println("\nProgetti di tipo " + progetti.get(0).getTipoProgetto());
			progetti = projDAO.getProgettiByTipo(progetti.get(0).getTipoProgetto());
			for (Progetto p: progetti)
				System.out.println(p + "\n");
			
			//Inserisci un nuovo progetto
			System.out.println("\nInserimento di un altro progetto");
			loggedIN = partecipanti.get(0);
			Progetto progetto = new Progetto(nome,tipo,descrizione,creazione,scadenza,loggedIN);
			progetto.setAmbiti(ambitoDAO.getAmbiti());
//			if (projDAO.addProgetto(progetto))
//				System.out.println(progetto + " inserito");
			
			//Rimuovi un progetto dal DB
			System.out.println("\nRimozione del progetto");
//			progetti = projDAO.getProgetti();
//			if (projDAO.removeProgetto(progetti.get(progetti.size() - 1)))
//				System.out.println(progetti.get(progetti.size() -1 ) + " rimosso");
			
			//Aggiunge un partecipante al progetto
			System.out.println("\nAggiunto partecipante " + loggedIN + " al progetto " + progetti.get(0));
			if (projDAO.addPartecipante(loggedIN, progetti.get(0), "Team Member"))
				System.out.println("Inserito");
			
			//Rimuove un partecipante dal progetto
			System.out.println("\nRimozione di " + loggedIN + " dal progetto " + progetti.get(0));
			if (projDAO.deletePartecipante(loggedIN, progetti.get(0)))
				System.out.println("Rimosso");
			
			//Update di un progetto
			System.out.println("\nAggiornamento del progetto " + progetti.get(0) + " con data terminazione oggi");
			progetti.get(0).setDataTerminazione(LocalDate.now());
			if (projDAO.updateProgetto(progetti.get(0)))
				System.out.println("Aggiornato");
			
			//Ottiene tutti i meeting relativi a un progetto
			progetti = projDAO.getProgetti();
			System.out.println("\nTutti i meeting relativi a " + progetti.get(0));
			meetings = projDAO.getMeetingRelativi(progetti.get(0));
			for (Meeting m: meetings)
				System.out.println(m);
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
