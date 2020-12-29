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

package ImplementazioneDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entità.Skill;
import InterfacceDAO.SkillDAO;

public class SkillDAOPSQL implements SkillDAO {

	//ATTRIBUTI
	
	private Connection conn;	//connessione al DB
	private PreparedStatement getSkills,getSkillsByNome,addSkill;	//PreparedStatement per operazioni rapide sul DB
	
	//METODI
	
	//Costruttore dell'implementazione di questo DAO
	/*Quando viene creato salva la connessione al DB per poter poi inizializzare gli statement
	*e poter interrogare il DB.*/
	public SkillDAOPSQL(Connection connection) throws SQLException {
		this.conn = connection;	//ottiene la connessione
		
		getSkills = conn.prepareStatement("SELECT * FROM Skill ORDER BY Skill.NomeSkill");	//statement per chiedere al DB tutte le skill
		getSkillsByNome = conn.prepareStatement("SELECT * FROM Skill WHERE Skill.NomeSkill LIKE ? ORDER BY Skill.NomeSkill");	//statement per chiedere al DB tutte le skill con un nome specifico
		addSkill = conn.prepareStatement("INSERT INTO Skill(NomeSkill) VALUES (?)");	//statement per inserire una nuova Skill nel DB
	}
	
	//Metodo GetSkills.
	/*Interroga il DB attraverso getSkills:PreparedStatement e salva il ResultSet ottenuto
	*in un ArrayList temporaneo (temp:ArrayList<Skill>) che alla fine restituisce.
	*Ottiene la lista di tutte le skill nel DB in ordine alfabetico.*/
	@Override
	public ArrayList<Skill> GetSkills() throws SQLException {
		ResultSet risultato = getSkills.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<Skill> temp = new ArrayList<Skill>();	//inizializza l'ArrayList di Skill
		
		//finchè ci sono elementi nel ResultSet crea un nuovo oggetto Skill con i dati ottenuti e lo aggiunge alla lista
		while(risultato.next()) {
			Skill tempSkill = new Skill(risultato.getInt(1),risultato.getString(2));
			temp.add(tempSkill);
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo GetSkillsByNome.
	/*Interroga il DB attraverso getSKillsByNome:PreparedStatement e salva il ResultSet ottenuto
	*in un ArrayList temporaneo (temp:ArrayList<Skill>) che alla fine restituisce.
	*Ottiene una lista di Skill che hanno nome simile a quello del parametro in input (nome:String) in ordine alfabetico.*/
	@Override
	public ArrayList<Skill> GetSkillsByNome(String nome) throws SQLException {
		getSkillsByNome.setString(1, nome);	//inserisce il parametro nome nella query
		
		ResultSet risultato = getSkillsByNome.executeQuery();	//esegue la query per ottenere il ResultSet
		ArrayList<Skill> temp = new ArrayList<Skill>();	//inizializza l'ArrayList<Skill> temporaneo
		
		//finchè ci sono record nel ResultSet crea una nuova Skill temporanea con quei dati e aggiunge la skill alla lista
		while(risultato.next()) {
			Skill tempSkill = new Skill(risultato.getInt(1),risultato.getString(2));
			temp.add(tempSkill);
		}
		risultato.close();	//chiude il ResultSet
		
		return temp;
	}

	//Metodo AddSkill.
	/*Inserisce nel DB una nuova Skill attraverso addSkill:PreparedStatement.
	*Restituisce true se l'insert ha avuto successo, altrimenti restituisce false.*/
	@Override
	public boolean AddSkill(Skill skill) throws SQLException {
		addSkill.setString(1, skill.getNomeSkill());	//inserisce il nome della nuova skill nello statement
		
		int record = addSkill.executeUpdate();	//esegue l'insert e salva il numero di record aggiunti (1)
		
		//se il record aggiunto è 1 allora l'insert ha avuto successo, altrimenti no
		if (record == 1)
			return true;
		else
			return false;
	}

}
