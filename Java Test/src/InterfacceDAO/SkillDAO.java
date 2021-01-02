/*Interfaccia DAO per le Skill
*Interfaccia contenente i vari metodi che svolgono
*operazioni sul DB per l'entità Skill. 
*************************************************/

package InterfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import Entità.Skill;

public interface SkillDAO {

	public ArrayList<Skill> getSkills() throws SQLException;	//metodo che interroga il DB per ottenere tutte le skill nella tabella
	public Skill getSkillByNome(String nome) throws SQLException;	//metodo che interroga il DB per ottenere la skill che ha un certo nome
	public boolean addSkill(Skill skill) throws SQLException;	//metodo che inserisce una nuova skill nel DB
}
