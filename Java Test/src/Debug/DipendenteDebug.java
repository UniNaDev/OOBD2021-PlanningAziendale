package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import DBManager.ManagerConnessioneDB;
import Entita.Dipendente;
import Entita.LuogoNascita;
import Entita.Skill;
import ImplementazioneDAO.DipendenteDAOPSQL;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;

public class DipendenteDebug {

	public static void main(String[] args) {
		
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		DipendenteDAO dipDAO = null;
		LuogoNascitaDAO luogoDAO = null;
		
		ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
		int età = 40;
		Skill skill = new Skill("Group");
		float valutazione = 5;
		float minSalario = 1000;
		float maxSalario = 1100;
		String cf = "SPSNDR02L01L259V";
		String email = "m.rossi@unina.it";
		String password = "pass";
		
		String nome = "Gastani";
		String cognome = "Frinzi";
		char sesso = 'F';
		LuogoNascita luogo;
		LocalDate dataNascita = new LocalDate(1972,8,12); 
		String indirizzo = "via Di Qui, 52 80127 NA";
		String email2 = "g.frinzi@gmail.com";
		float salario = 1700;
		String password2 = "frinzi72";
		
		try {
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			
			dipDAO = new DipendenteDAOPSQL(connection);
			luogoDAO = new LuogoNascitaDAOPSQL(connection);
			
			//Ottieni tutti i dipendenti
			dipendenti = dipDAO.getDipendenti();
			System.out.println("\nTutti i dipendenti:");
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con più di 40 anni
			System.out.println("\nDipendenti con età maggiore di " + età + " anni");
			dipendenti = dipDAO.getDipendentiByEta(età);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con valutazione superiore a 5
			System.out.println("\nDipendenti con valutazione superiore a " + valutazione);
			dipendenti = dipDAO.getDipendentiByValutazione(valutazione);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con salario compreso tra i 1000 e i 1100
			System.out.println("\nDipendenti con salario compreso tra " + minSalario + " e " + maxSalario + ":");
			dipendenti = dipDAO.getDipendentiBySalario(minSalario,maxSalario);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con la skill Group
			System.out.println("\nDipendenti con la skill " + skill + ":");
			dipendenti = dipDAO.getDipendentiBySkill(skill);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni un dipendente con CF uguale a SPSNDR02L01L259V
			System.out.println("\nDipendente con CF " + cf);
			Dipendente dipendente = null;
			dipendente = dipDAO.getDipendenteByCF(cf);
			System.out.println(dipendente);
			
			//Controlla login con email = m.rossi@unina.it e password=pass
			System.out.println("\nDipendente che ha fatto login con email: " + email + " e password: " + password);
			dipendente = dipDAO.loginCheck(email, password);
			System.out.println(dipendente);
			
			//Inserisci un nuovo dipendente Giacomo Poretti
			luogo = luogoDAO.getLuogoByCod("f839");
//			dipendente = new Dipendente(nome,cognome,sesso,dataNascita,luogo,indirizzo,email2,salario,password2);
//			dipDAO.addDipendente(dipendente);
//			System.out.println(dipendente + "\naggiunto");
			
			//Modifica un dipendente
			dipendente = dipDAO.getDipendenteByCF("FRNGST72M52F839A");
			dipendente.setSalario(5000);
			dipDAO.updateDipendente(dipendente);
			System.out.println("\nModificato il salario del dipendente " + dipendente);
			
			//Genera CF del dipendente Mario Rossi
			System.out.println(dipendente.generaCF());
			/****************************************************/
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
