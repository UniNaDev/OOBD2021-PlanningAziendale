/*Interfaccia DipendenteDAO.
*Implementa i principali metodi di interrogazione del DB per la tabella Dipendente
*in relazione all'entità Dipendente.
*********************************************************************************/

package InterfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Entita.Dipendente;
import Entita.Skill;

public interface DipendenteDAO {

	public ArrayList<Dipendente> getDipendenti() throws SQLException;	//metodo che restituisce tutti i dipendenti nel DB
	public ArrayList<Dipendente> getDipendentiByEta(int minima) throws SQLException;	//metodo che restituisce tutti i dipendenti con età superiore al parametro inserito
	public float getValutazione(String cf) throws SQLException;	//metodo che restituisce la valutazione di un dipendente
	public ArrayList<Dipendente> getDipendentiByValutazione(float minima) throws SQLException;	//metodo che restituisce tutti i dipendenti con valutazione superiore al parametro inserito
	public ArrayList<Dipendente> getDipendentiBySalario(float minimo, float massimo) throws SQLException;	//metodo che restituisce i dipendenti con salario compreso tra i parametri inseriti 
	public ArrayList<Dipendente> getDipendentiBySkill(Skill skill) throws SQLException;	//metodo che restituisce i dipendenti con una certa skill
	public boolean addDipendente(Dipendente dipendente) throws SQLException;	//metodo che aggiunge un nuovo dipendente al DB
	public boolean updateDipendente(Dipendente dipendente) throws SQLException;	//metodo che aggiorna le informazioni di un dipendente
	public Dipendente loginCheck(String email, String password) throws SQLException;	//metodo che controlla se le credenziali per il login sono corrette e restituisce nel caso il dipendente che ha fatto accesso
	public Dipendente getDipendenteByCF(String cf) throws SQLException;	//metodo che ottiene il dipendente con codice fiscale uguale a quello del parametro in input
}
