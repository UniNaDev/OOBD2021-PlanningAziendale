package Debug;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManager.ManagerConnessioneDB;
import Entità.Skill;
import ImplementazioneDAO.SkillDAOPSQL;
import InterfacceDAO.SkillDAO;

public class SkillDebug {

	public static void main(String[] args)  {
		
		ManagerConnessioneDB connDB = null;
		Connection connection = null;
		
		SkillDAO skillDAO = null;
		
		ArrayList<Skill> skills = new ArrayList<Skill>();
		Skill skill;
		

		try {
			connDB = ManagerConnessioneDB.getInstance();
			connection = connDB.getConnection();
			System.out.println("Connessione riuscita");
	
			skillDAO = new SkillDAOPSQL(connection);
			
			//Ottieni tutte le skill
			skills = skillDAO.getSkills();
			System.out.println("Tutte le skill:");
			for(Skill s: skills)
				System.out.println(s);
			
			//Ottieni la skill di nome Group
			String nomeSkill = "Group";
			System.out.println("\nSkill che hanno un nome come " + nomeSkill);
			skill = skillDAO.getSkillByNome(nomeSkill);
			System.out.println(skill.toString());
			
			//Inserisci una nuova skill
			skill = new Skill("Ennesima Skill");
			if (skillDAO.addSkill(skill))
				System.out.println("\n" + skill + " inserita con successo.");

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
}

