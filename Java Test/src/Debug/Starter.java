package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManager.ManagerConnessioneDB;
import Entità.LuogoNascita;
import Entità.Skill;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import ImplementazioneDAO.SkillDAOPSQL;
import InterfacceDAO.LuogoNascitaDAO;
import InterfacceDAO.SkillDAO;

public class Starter {

	public static void main(String[] args) {
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		SkillDAO skillDAO = null;
		LuogoNascitaDAO luogoDAO = null;
		
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
			
			//Ottieni le skill con un certo nome
			String nome = "Group";
			System.out.println("Skill che hanno un nome come " + nome);
			skills = skillDAO.GetSkillsByNome(nome);
			for(Skill s: skills)
				System.out.println(s.toString());
			
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
			/***************************************************/
		}
		catch(SQLException e) 
		{
			System.out.println("Errore SQL. " + e.getMessage());
		}
	}

}
