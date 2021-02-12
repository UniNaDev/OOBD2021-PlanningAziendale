/*Implementazione dell'interfaccia SkillDAO.
*Implementa l'interfaccia SkillDAO definendo i vari metodi dell'interfaccia
*e sfruttando dei PreparedStatements per operare rapidamente sul DB.
*Le operazioni tipiche sono:
*	-richiesta di tutte le Skill nel DB
*	-richiesta di tutte le Skill nel DB con un certo nome
*Struttura nel DB della tabella Skill:
*|(1) id:int|(2) NomeSkill:String|
*|__________|____________________|
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

	//ATTRIBUTI
	//----------------------------------------
	private Connection conn;	//connessione al DB
	private PreparedStatement getSkillsPS,addSkillPS, addSkillDipendentePS, getSkillDipendentePS;	//PreparedStatement per operazioni rapide sul DB
	
	//METODI
	//----------------------------------------
	
	//Costruttore dell'implementazione di questo DAO
	/*Quando viene creato salva la connessione al DB per poter poi inizializzare gli statement
	*e poter interrogare il DB.*/
	public SkillDAOPSQL(Connection connection) throws SQLException {
		this.conn = connection;	//ottiene la connessione
		
		getSkillsPS = conn.prepareStatement("SELECT * FROM Skill ORDER BY Skill.NomeSkill");	//statement per chiedere al DB tutte le skill
		addSkillPS = conn.prepareStatement("INSERT INTO Skill(NomeSkill) VALUES (?)");	//statement per inserire una nuova Skill nel DB
		addSkillDipendentePS = conn.prepareStatement("INSERT INTO Abilità VALUES (?,?)");	//? = id della skill, ? = codice fiscale del dipendente
		getSkillDipendentePS = conn.prepareStatement("SELECT * FROM Skill AS s WHERE s.IDSkill IN (SELECT a.IDSkill FROM Abilità AS a WHERE a.CF = ?)");	//? = codice fiscale del dipendente di cui si vogliono le skill
	}
	
	//Metodo getSkills.
	/*Interroga il DB attraverso getSkills:PreparedStatement e salva il ResultSet ottenuto
	*in un ArrayList temporaneo (temp:ArrayList<Skill>) che alla fine restituisce.
	*Ottiene la lista di tutte le skill nel DB in ordine alfabetico.*/
	@Override
	public ArrayList<Skill> getSkills() throws SQLException {
		ResultSet risultato = getSkillsPS.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<Skill> temp = new ArrayList<Skill>();	//inizializza l'ArrayList di Skill
		
		//finchè ci sono elementi nel ResultSet crea un nuovo oggetto Skill con i dati ottenuti e lo aggiunge alla lista
		while(risultato.next()) {
			Skill tempSkill = new Skill(risultato.getInt(1), risultato.getString(2));
			temp.add(tempSkill);
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo addSkill.
	/*Inserisce nel DB una nuova Skill attraverso addSkill:PreparedStatement.
	*Restituisce true se l'insert ha avuto successo, altrimenti restituisce false.*/
	@Override
	public boolean insertSkill(Skill skill) throws SQLException {
		addSkillPS.setString(1, skill.getNomeSkill());	//inserisce il nome della nuova skill nello statement
		
		int record = addSkillPS.executeUpdate();	//esegue l'insert e salva il numero di record aggiunti (1)
		
		//se il record aggiunto è 1 allora l'insert ha avuto successo, altrimenti no
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che aggiunge l'associazione tra un dipendente e una sua skill
	@Override
	public boolean insertSkillDipendente(Skill skill, Dipendente dip) throws SQLException {
		addSkillDipendentePS.setInt(1, skill.getIdSkill());	//inserisce l'id della skill nell'insert
		addSkillDipendentePS.setString(2, dip.getCf());	//inserisce il codice fiscale del dipendente nell'insert
		
		int record = addSkillDipendentePS.executeUpdate();	//esegue l'insert e salva il numero di record modificati
		
		//(1 = successo, 0 = fallimento)
		if (record == 1)
			return true;
		else
			return false;
	}

	//Metodo che restituisce le skill di un dipendente
	@Override
	public ArrayList<Skill> getSkillsDipendente(String cfDipendente) throws SQLException {
		ArrayList<Skill> temp = new ArrayList<Skill>();	//inizializza la lista da restituire
		
		getSkillDipendentePS.setString(1, cfDipendente); //inserisce il codice fiscale del dipendente nella query
		
		ResultSet risultato = getSkillDipendentePS.executeQuery();	//esegue la query e ottiene il ResultSet
		
		while (risultato.next()) {
			Skill tempSkill = new Skill(risultato.getInt(1), risultato.getString(2));	//crea la skill temporanea
			temp.add(tempSkill);
		}
		risultato.close(); //chiude il ResultSet
		
		return temp;
	}

}
