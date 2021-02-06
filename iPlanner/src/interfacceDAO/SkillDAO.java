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

	public ArrayList<Skill> getSkills() throws SQLException;	//metodo che interroga il DB per ottenere tutte le skill nella tabella
	public boolean addSkill(Skill skill) throws SQLException;	//metodo che inserisce una nuova skill nel DB
	public boolean addSkillDipendente(Skill skill, Dipendente dip) throws SQLException;	//metodo che inserisce in Abilità l'associazione tra un dipendente e un una sua skill
	public ArrayList<Skill> getSkillDipendente(String cfDipendente) throws SQLException;	//metodo che restituisce le skill di un dipendente
}
