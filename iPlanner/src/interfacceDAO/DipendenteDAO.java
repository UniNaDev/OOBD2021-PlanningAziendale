/*Interfaccia DipendenteDAO.
*Implementa i principali metodi di interrogazione del DB
*in relazione all'entità Dipendente.
********************************************************/

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.Skill;

public interface DipendenteDAO {
	public ArrayList<Dipendente> ottieniDipendenti() throws SQLException;
	
	public ArrayList<Dipendente> ottieniDipendentiNonInvitatiMeeting(Meeting meetingSelezionato) throws SQLException;
	
	public Dipendente ottieniDipendenteDaCF(String cf) throws SQLException;
	
	public ArrayList<Dipendente> ottieniDipendentiPerSkill(Skill skill) throws SQLException;
	
	public ArrayList<Dipendente> ottieniDipendentiFiltrati(String nomeCognomeEmail, int etàMinima, int etàMassima, float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima) throws SQLException;
	
	public Dipendente eseguiLoginDipendente(String email, String password) throws SQLException; //OK
	
	public float ottieniValutazione(String cf) throws SQLException;
	
	public boolean inserisciDipendente(Dipendente dipendente) throws SQLException;
	
	public boolean aggiornaDipendente(Dipendente dipendente) throws SQLException;
	
	public boolean eliminaDipendente(Dipendente dipendente) throws SQLException;
	
	public String ottieniTipologieProgettoDipendente(String cf) throws SQLException;
	
	public float ottieniMaxStipendio() throws SQLException;
	
	public ArrayList<Dipendente> ottieniDipendentiNonPartecipantiProgetto(Progetto progettoSelezionato) throws SQLException;

	ArrayList<Dipendente> filtraDipendentiNonPartecipanti(String nomeCognomeEmail, int etàMinima, int etàMassima,
			float salarioMinimo, float salarioMassimo, float valutazioneMinima, float valutazioneMassima,Progetto progettoSelezionato) throws SQLException;

	ArrayList<Dipendente> filtraDipendentiNonPartecipantiPerSkill(Progetto progettoSelezionato, Skill skill) throws SQLException;

	ArrayList<Dipendente> filtraDipendentiNonPartecipantiPerTipologiaProgetto(Progetto progettoSelezionato,String tipologiaProgetto) throws SQLException;
}