/*Interfaccia DAO per le Skill
*Interfaccia contenente i vari metodi che svolgono
*operazioni sul DB per l'entità Skill. 
*************************************************/

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Skill;

public interface SkillDAO {
	public ArrayList<Skill> ottieniSkill() throws SQLException;
	
	public boolean insertSkill(Skill skill) throws SQLException;
	
	public boolean insertSkillDipendente(Skill skill, Dipendente dip) throws SQLException;
	
	public ArrayList<Skill> ottieniSkillDipendente(String cfDipendente) throws SQLException;
}
