/*Interfaccia DipendenteDAO.
*Implementa i principali metodi di interrogazione del DB per la tabella Dipendente
*in relazione all'entità Dipendente.
*********************************************************************************/

package InterfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Entità.Dipendente;
import Entità.Skill;

public interface DipendenteDAO {

	public ArrayList<Dipendente> GetDipendenti() throws SQLException;	//metodo che restituisce tutti i dipendenti nel DB
	public ArrayList<Dipendente> GetDipendentiByEta(int minima) throws SQLException;	//metodo che restituisce tutti i dipendenti con età superiore al parametro inserito
	public float GetValutazione(String cf) throws SQLException;	//metodo che restituisce la valutazione di un dipendente
	public ArrayList<Dipendente> GetDipendentiByValutazione(float minima) throws SQLException;	//metodo che restituisce tutti i dipendenti con valutazione superiore al parametro inserito
	public ArrayList<Dipendente> GetDipendentiBySalario(float minimo, float massimo) throws SQLException;	//metodo che restituisce i dipendenti con salario compreso tra i parametri inseriti 
	public ArrayList<Dipendente> GetDipendentiBySkill(Skill skill) throws SQLException;	//metodo che restituisce i dipendenti con una certa skill
	public boolean AddDipendente(Dipendente dipendente) throws SQLException;	//metodo che aggiunge un nuovo dipendente al DB
	public boolean UpdateDipendente(Dipendente dipendente) throws SQLException;	//metodo che aggiorna le informazioni di un dipendente
	public Dipendente LoginCheck(String email, String password) throws SQLException;	//metodo che controlla se le credenziali per il login sono corrette e restituisce nel caso il dipendente che ha fatto accesso
	public Dipendente GetDipendenteByCF(String cf) throws SQLException;	//metodo che ottiene il dipendente con codice fiscale uguale a quello del parametro in input
}
