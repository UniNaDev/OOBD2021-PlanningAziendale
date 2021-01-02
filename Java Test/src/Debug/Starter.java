package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import DBManager.ManagerConnessioneDB;
import Entità.Dipendente;
import Entità.LuogoNascita;
import Entità.SalaRiunione;
import Entità.Skill;
import ImplementazioneDAO.DipendenteDAOPSQL;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import ImplementazioneDAO.SalaRiunioneDAOPSQL;
import ImplementazioneDAO.SkillDAOPSQL;
import InterfacceDAO.DipendenteDAO;
import InterfacceDAO.LuogoNascitaDAO;
import InterfacceDAO.SalaRiunioneDAO;
import InterfacceDAO.SkillDAO;

public class Starter {

	public static void main(String[] args) {
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		SkillDAO skillDAO = null;
		LuogoNascitaDAO luogoDAO = null;
		DipendenteDAO dipDAO = null;
		SalaRiunioneDAO salaDAO = null;
		
		try 
		{
			//Test connessione******************************
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
			/**********************************************/
			
			//Test Skill**********************************************
			skillDAO = new SkillDAOPSQL(connection);
			ArrayList<Skill> skills = new ArrayList<Skill>();
			
			//Ottieni tutte le skill
			skills = skillDAO.getSkills();
			System.out.println("Tutte le skill:");
			for(Skill s: skills)
				System.out.println(s.toString());
			
			//Ottieni la skill di nome Group
			String nomeSkill = "Group";
			System.out.println("Skill che hanno un nome come " + nomeSkill);
			Skill skill = skillDAO.getSkillByNome(nomeSkill);
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
			province = luogoDAO.getProvince();
			System.out.println("Tutte le province:");
			for (String pro : province)
				System.out.println(pro);
			
			//Ottieni tutti i luoghi di una provincia
			luoghi = luogoDAO.getLuoghiByProvincia(provincia);
			System.out.println("Tutti i luoghi in provincia di " + provincia);
			for (LuogoNascita luogo: luoghi)
				System.out.println(luogo);
			
			//Ottieni dipendenti che sono nati a Napoli (NA)
			LuogoNascita luogo = luogoDAO.getLuogoByCod("f839");
			ArrayList<Dipendente> dipendenti2 = new ArrayList<Dipendente>();
			dipendenti2 = luogoDAO.getDipendentiByLuogo(luogo);
			System.out.println("Tutti i dipendenti nati a " + luogo);
			for (Dipendente d: dipendenti2)
				System.out.println(d);
			/***************************************************/
			
			//Test Dipendente*************************************
			dipDAO = new DipendenteDAOPSQL(connection);
			ArrayList<Dipendente> dipendenti = new ArrayList<Dipendente>();
			
			//Ottieni tutti i dipendenti
			dipendenti = dipDAO.getDipendenti();
			System.out.println("Tutti i dipendenti:");
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con più di 40 anni
			int eta = 40;
			System.out.println("Dipendenti con età maggiore di " + eta + " anni");
			dipendenti = dipDAO.getDipendentiByEta(eta);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con valutazione superiore a 7
			float val = 7;
			System.out.println("Dipendenti con valutazione superiore a " + val);
			dipendenti = dipDAO.getDipendentiByValutazione(val);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con salario compreso tra i 1000 e i 1100
			float min = 1000;
			float max = 1100;
			System.out.println("Dipendenti con salario compreso tra " + min + " e " + max + ":");
			dipendenti = dipDAO.getDipendentiBySalario(min,max);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni tutti i dipendenti con la skill Group
			System.out.println("Dipendenti con la skill " + skill + ":");
			dipendenti = dipDAO.getDipendentiBySkill(skill);
			for (Dipendente d: dipendenti)
				System.out.println(d);
			
			//Ottieni un dipendente con CF uguale a SPSNDR02L01L259V
			String cf = "SPSNDR02L01L259V";
			System.out.println("Dipendente con CF " + cf);
			Dipendente dipendente = null;
			dipendente = dipDAO.getDipendenteByCF(cf);
			System.out.println(dipendente);
			
			//Controlla login con email = m.rossi@unina.it e password=pass
			String email = "m.rossi@unina.it";
			String password = "pass";
			System.out.println("Dipendente che ha fatto login con email: " + email + " e password: " + password);
			dipendente = dipDAO.loginCheck(email, password);
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
			System.out.println(dipendente.generaCF());
			/****************************************************/
			
			//Test SalaRiunione***********************************
			salaDAO = new SalaRiunioneDAOPSQL(connection);
			ArrayList<SalaRiunione> sale = new ArrayList<SalaRiunione>();
			
			//Ottieni tutte le sale nel DB
			sale = salaDAO.getSale();
			System.out.println("Tutte le sale riunioni:");
			for (SalaRiunione sr : sale)
				System.out.println(sr);
			
			//Ottieni sale con capienza minima 120 posti
			int cap = 120;
			sale = salaDAO.getSaleByCap(cap);
			System.out.println("Tutte le sale riunioni con capienza minima " + cap);
			for (SalaRiunione sr : sale)
				System.out.println(sr);
			
			//Inserisci una nuova sala
			String codSala = "SalaNuova";
			String indirizzo = "via Di Qui, 22";
			int piano = 2;
			SalaRiunione salaNew = new SalaRiunione(codSala,cap,indirizzo,piano);
			if (salaDAO.addSala(salaNew))
				System.out.println(salaNew + " inserita");
			
			//Modifica della nuova sala
			salaNew.setCap(50);
			if (salaDAO.updateSala(salaNew))
				System.out.println(salaNew + " modificata");
			
			//Rimuovi la sala appena aggiunta
			if (salaDAO.removeSala(salaNew))
				System.out.println(salaNew + " rimossa");
			
			//Ottieni sale libere il 21/10/2020 dalle 13:00 alle 15:00
			LocalDateTime inizio = new LocalDateTime(2020,10,21,13,0);
			LocalDateTime fine = new LocalDateTime(2020,10,21,15,0);
			
			sale = salaDAO.getSaleLibere(inizio, fine);
			System.out.println("Tutte le sale riunioni libere dal " + inizio + " a " + fine);
			for (SalaRiunione sr : sale)
				System.out.println(sr);
			/****************************************************/
		}
		catch(SQLException e) 
		{
			System.out.println("Errore SQL. " + e.getMessage());
		}
	}

}
