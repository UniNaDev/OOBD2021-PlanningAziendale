//Interfaccia del DAO relativo ai progetti aziendali con i metodi che operano sul DB.

package interfacceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;

public interface ProgettoDAO {
	public ArrayList<Progetto> ottieniProgetti() throws SQLException;
	
	public ArrayList<Progetto> ottieniProgettiSegreteriaFiltratiPerNome(String nomeCercato) throws SQLException;
	
	public ArrayList<Progetto> ottieniProgettiFiltratiPerAmbito(AmbitoProgetto ambito) throws SQLException;
	
	public ArrayList<Progetto> ottieniProgettiFiltratiPerTipo(String tipologia) throws SQLException;
	
	public ArrayList<Meeting> ottieniMeetingRelativiProgetto(int codProgettoSelezionato) throws SQLException;
	
	public ArrayList<CollaborazioneProgetto> ottieniProgettiDipendente(Dipendente dip) throws SQLException; //OK
	
	public boolean creaProgetto(Progetto proj) throws SQLException;
	
	public boolean rimuoviProgetto(Progetto proj) throws SQLException;
	
	public boolean inserisciPartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	
	public boolean aggiornaRuoloCollaboratore(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	
	public boolean eliminaPartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	
	public boolean aggiornaProgetto(Progetto proj) throws SQLException;
	
	public Progetto ottieniProgettoDaCodiceProgetto(int codProgetto) throws SQLException;
	
	public ArrayList<String> ottieniTipologie() throws SQLException;
	
	public String ottieniCFProjectManager(Progetto progetto) throws SQLException;
	
	public ArrayList<Dipendente> ottieniPartecipantiProgettoSenzaRuolo(int codiceProgetto) throws SQLException;
	
	public int ottieniCodiceProgetto(Progetto proj) throws SQLException;
	
	public ArrayList<String> ottieniRuoli() throws SQLException;
	
	public ArrayList<CollaborazioneProgetto> ottieniPartecipantiProgetto(int codiceProgetto) throws SQLException;
	
	public boolean inserisciProjectManager(String cf, Progetto tmp, String string) throws SQLException;
	
	public ArrayList<Progetto> ottieniProgettiDipendentePerNome(String nomeCercato, Dipendente dipendente)throws SQLException;

	

}
