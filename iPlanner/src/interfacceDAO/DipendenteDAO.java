/*Interfaccia DipendenteDAO.
*Implementa i principali metodi di interrogazione del DB
*in relazione all'entità Dipendente.
********************************************************/

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.Skill;

public interface DipendenteDAO {
	public ArrayList<Dipendente> getDipendenti() throws SQLException;
	
	public ArrayList<Dipendente> getDipendentiNonInvitati(Meeting meetingSelezionato) throws SQLException;
	
	public Dipendente getDipendenteByCF(String cf) throws SQLException;
	
	public ArrayList<Dipendente> getDipendenteBySkill(Skill skill) throws SQLException;
	
	public ArrayList<Dipendente> getDipendentiFiltrati(String nomeCognomeEmail, int etàMinima, int etàMassima, float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima) throws SQLException;
	
	public Dipendente getLoggedDipendente(String email, String password) throws SQLException;
	
	public float getValutazione(String cf) throws SQLException;
	
	public boolean insertDipendente(Dipendente dipendente) throws SQLException;
	
	public boolean updateDipendente(Dipendente dipendente) throws SQLException;
	
	public boolean deleteDipendente(Dipendente dipendente) throws SQLException;
	
	public float getMaxStipendio() throws SQLException;
	
	public ArrayList<Dipendente> getDipendentiNonPartecipanti(Progetto progettoSelezionato) throws SQLException;
	
	public String organizzatoreCheck(Meeting meeting) throws SQLException;
}