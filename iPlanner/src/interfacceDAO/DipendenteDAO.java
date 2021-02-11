/*Interfaccia DipendenteDAO.
*Implementa i principali metodi di interrogazione del DB per la tabella Dipendente
*in relazione all'entità Dipendente.
*********************************************************************************/

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.Skill;

public interface DipendenteDAO {

	public ArrayList<Dipendente> getDipendentiNonInvitati(Meeting meetingSelezionato) throws SQLException;	//metodo che restituisce tutti i dipendenti nel DB
	public float getValutazione(String cf) throws SQLException;	//metodo che restituisce la valutazione di un dipendente
	public boolean addDipendente(Dipendente dipendente) throws SQLException;	//metodo che aggiunge un nuovo dipendente al DB
	public boolean updateDipendente(Dipendente dipendente) throws SQLException;	//metodo che aggiorna le informazioni di un dipendente
	public boolean deleteDipendente(Dipendente dipendente) throws SQLException;	//metodo che elimina un dipendente dal database
	public Dipendente loginCheck(String email, String password) throws SQLException;	//metodo che controlla se le credenziali per il login sono corrette e restituisce nel caso il dipendente che ha fatto accesso
	public Dipendente getDipendenteByCF(String cf) throws SQLException;	//metodo che ottiene il dipendente con codice fiscale uguale a quello del parametro in input
	public float getMaxStipendio() throws SQLException;	//metodo che ottiene il massimo stipendio presente nel DB
	public ArrayList<Dipendente> getDipendentiFiltrati(String nomeCognomeEmail, int etàMinima, int etàMassima, float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima) throws SQLException;	//metodo che ottiene i dipendenti filtrati
	public ArrayList<Dipendente> getDipendenti() throws SQLException;
	public ArrayList<Dipendente> getDipendentiNonPartecipanti(Progetto progettoSelezionato) throws SQLException;
	public String organizzatoreCheck(Meeting meeting) throws SQLException;
	public ArrayList<Dipendente> getDipendenteBySkill(Skill skill) throws SQLException;	//metodo che restituisce i dipendenti che hanno una specifica skill
}