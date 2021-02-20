/*Implementazione dell'interfaccia SkillDAO.
*Implementa l'interfaccia SkillDAO definendo i vari metodi dell'interfaccia
*e sfruttando dei PreparedStatements per operare rapidamente sul DB.
***************************************************************************/

package implementazioniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Skill;
import interfacceDAO.SkillDAO;

public class SkillDAOPSQL implements SkillDAO {
	private Connection conn;
	private PreparedStatement ottieniSkillPS,addSkillPS, addSkillDipendentePS, ottieniSkillDipendentePS;
	
	public SkillDAOPSQL(Connection connection) throws SQLException {
		this.conn = connection;
		
		ottieniSkillPS = conn.prepareStatement("SELECT * FROM Skill ORDER BY Skill.NomeSkill");
		addSkillPS = conn.prepareStatement("INSERT INTO Skill(NomeSkill) VALUES (?)");
		addSkillDipendentePS = conn.prepareStatement("INSERT INTO Abilità VALUES (?,?)");
		ottieniSkillDipendentePS = conn.prepareStatement("SELECT * FROM Skill AS s WHERE s.IDSkill IN (SELECT a.IDSkill FROM Abilità AS a WHERE a.CF = ?)");
	}
	
	@Override  //Ok
	public ArrayList<Skill> ottieniSkill() throws SQLException {
		ResultSet risultato = ottieniSkillPS.executeQuery();
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		while(risultato.next()) {
			Skill tempSkill = new Skill(risultato.getInt(1), risultato.getString(2));
			skills.add(tempSkill);
		}
		risultato.close();
		
		return skills;
	}

	@Override
	public boolean insertSkill(Skill skill) throws SQLException {
		addSkillPS.setString(1, skill.getNomeSkill());
		
		int record = addSkillPS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean insertSkillDipendente(Skill skill, Dipendente dipendente) throws SQLException {
		addSkillDipendentePS.setInt(1, skill.getIdSkill());
		addSkillDipendentePS.setString(2, dipendente.getCf());
		
		int record = addSkillDipendentePS.executeUpdate();
		
		if (record == 1)
			return true;
		else
			return false;
	}
 
	@Override  //Ok
	public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		ottieniSkillDipendentePS.setString(1, cfDipendente);
		ResultSet risultato = ottieniSkillDipendentePS.executeQuery();
		
		while (risultato.next()) {
			Skill tempSkill = new Skill(risultato.getInt(1), risultato.getString(2));
			skills.add(tempSkill);
		}
		risultato.close();
		
		return skills;
	}

}
