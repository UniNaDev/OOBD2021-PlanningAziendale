package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import DBManager.ManagerConnessioneDB;
import Entità.Dipendente;
import Entità.LuogoNascita;
import Entità.Skill;
import ImplementazioneDAO.DipendenteDAOPSQL;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import ImplementazioneDAO.SkillDAOPSQL;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;
import InterfacceDAO.SkillDAO;

public class Starter {

	public static void main(String[] args) {
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		SkillDAO skillDAO = null;
		LuogoNascitaDAO luogoDAO = null;
		DipendenteDAO dipDAO = null;
		
		try 
		{
			//Test connessione******************************
			connDB = ManagerConnessioneDB.GetInstance();
			connection = connDB.GetConnection();
			System.out.println("Connessione riuscita");
			/**********************************************/
			
			//Test Skill**********************************************
			skillDAO = new SkillDAOPSQL(connection);
			ArrayList<Skill> skills = new ArrayList<Skill>();
			
			//Ottieni tutte le skill
			skills = skillDAO.GetSkills();
			System.out.println("Tutte le skill:");
			for(Skill s: skills)
				System.out.println(s.toString());
			
			//Ottieni la skill di nome Group
			String nomeSkill = "Group";
			System.out.println("Skill che hanno un nome come " + nomeSkill);
			Skill skill = skillDAO.GetSkillByNome(nomeSkill);
			System.out.println(skill.toString());
			
			//Inserisci una nuova skill
//			Skill skill = new Skill(1,"Nuova Skill");
//			if (skillDAO.AddSkill(skill))
//				System.out.println(skill + " inserita con successo.");
			/********************************************************/
			
			//Test LuogoNascita*********************************
			luogoDAO = new LuogoNascitaDAOPSQL(connection);
			String provincia = "Napoli";
			ArrayList<String> province = new ArrayList<String>();
			ArrayList<LuogoNascita> luoghi = new ArrayList<LuogoNascita>();
			
			//Ottieni tutte le province
			province = luogoDAO.GetProvince();
			System.out.println("Tutte le province:");
			for (String pro : province)
				System.out.println(pro);
			
			//Ottieni tutti i luoghi di una provincia
			luoghi = luogoDAO.GetLuoghiByProvincia(provincia);
			System.out.println("Tutti i luoghi in provincia di " + provincia);
			for (LuogoNascita luogo: luoghi)
				System.out.println(luogo);
			
			//Ottieni dipendenti che sono nati a Napoli (NA)
			LuogoNascita luogo = luogoDAO.GetLuogoByCod("f839");
			ArrayList<Dipendente> dipendenti2 = new ArrayList<Dipendente>();
			dipendenti2 = luogoDAO.GetDipendentiByLuogo(luogo);
			System.out.println("Tutti i dipendenti nati a " + luogo);
			for (Dipendente d: dipendenti2)
				System.out.println(d);
			/***************************************************/
			
			//Test Dipendente*************************************
			dipDAO = new DipendenteDAOPSQL(connection);
			ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
			
			//Ottieni tutti i dipendenti
			dipendenti = dipDAO.GetDipendenti();
			System.out.println("Tutti i dipendenti:");
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con più di 40 anni
			int eta = 40;
			System.out.println("Dipendenti con età maggiore di " + eta + " anni");
			dipendenti = dipDAO.GetDipendentiByEta(eta);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con valutazione superiore a 7
			float val = 7;
			System.out.println("Dipendenti con valutazione superiore a " + val);
			dipendenti = dipDAO.GetDipendentiByValutazione(val);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con salario compreso tra i 1000 e i 1100
			float min = 1000;
			float max = 1100;
			System.out.println("Dipendenti con salario compreso tra " + min + " e " + max + ":");
			dipendenti = dipDAO.GetDipendentiBySalario(min,max);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con la skill Group
			System.out.println("Dipendenti con la skill " + skill + ":");
			dipendenti = dipDAO.GetDipendentiBySkill(skill);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni un dipendente con CF uguale a SPSNDR02L01L259V
			String cf = "SPSNDR02L01L259V";
			System.out.println("Dipendente con CF " + cf);
			Dipendente dipendente = null;
			dipendente = dipDAO.GetDipendenteByCF(cf);
			System.out.println(dipendente);
			
			//Controlla login con email = m.rossi@unina.it e password=pass
			String email = "m.rossi@unina.it";
			String password = "pass";
			System.out.println("Dipendente che ha fatto login con email: " + email + " e password: " + password);
			dipendente = dipDAO.LoginCheck(email, password);
			System.out.println(dipendente);
			
			//Inserisci un nuovo dipendente Giacomo Poretti
//			cf = "PRTGCM90H08F839G";
//			String nome = "Giacomo";
//			String cognome = "Poretti";
//			char sesso = 'M';
//			luogo = luogoDAO.GetLuogoByCod("F839");
//			LocalDate dataNascita = new LocalDate(1990,6,8); 
//			String indirizzo = "via Procopio, 52 80127 NA";
//			String email2 = "g.poretti@gmail.com";
//			float salario = 2500;
//			String password2 = "giacomino90";
//			float valutazione = 7;
//			
//			dipendente = new Dipendente(cf,nome,cognome,sesso,dataNascita,luogo,indirizzo,email2,salario,password2,valutazione);
//			dipDAO.AddDipendente(dipendente);
//			System.out.println(dipendente + "\naggiunto");
			
//			//Modifica un dipendente
//			dipendente.setSalario(500000);
//			dipDAO.UpdateDipendente(dipendente);
//			System.out.println("Modificato il salario del dipendente " + dipendente);
			
			//Genera CF del dipendente Mario Rossi
			System.out.println(dipendente.GeneraCF());
			/****************************************************/
		}
		catch(SQLException e) 
		{
			System.out.println("Errore SQL. " + e.getMessage());
		}
	}

}