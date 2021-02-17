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
	public ArrayList<Progetto> getProgetti() throws SQLException;
	
	public ArrayList<Progetto> getProgettiByNome(String nomeCercato) throws SQLException;
	
	public ArrayList<Progetto> getProgettiByAmbito(AmbitoProgetto ambito) throws SQLException;
	
	public ArrayList<Progetto> getProgettiByTipo(String tipologia) throws SQLException;
	
	public ArrayList<Meeting> getMeetingRelativiProgetto(int codProgettoSelezionato) throws SQLException;
	
	public ArrayList<CollaborazioneProgetto> getProgettiByDipendente(Dipendente dip) throws SQLException;
	
	public boolean insertProgetto(Progetto proj) throws SQLException;
	
	public boolean deleteProgetto(Progetto proj) throws SQLException;
	
	public boolean insertPartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	
	public boolean updatePartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	
	public boolean deletePartecipanteProgetto(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	
	public boolean updateProgetto(Progetto proj) throws SQLException;
	
	public Progetto getProgettoByCod(int codProgetto) throws SQLException;
	
	public ArrayList<String> getTipologie() throws SQLException;
	
	//TODO: probabilmente inutile
	public ArrayList<Dipendente> getPartecipantiProgettoSenzaRuolo(int codiceProgetto) throws SQLException;
	
	//TODO: probabilmente inutile
	public int getCodProgetto(Progetto proj) throws SQLException;
	
	public ArrayList<String> getRuoliDipendenti() throws SQLException;
	
	//TODO: probabilmente inutile
	public ArrayList<CollaborazioneProgetto> getPartecipantiProgetto(int codiceProgetto) throws SQLException;
	
	//TODO: probabilmente inutile
	public boolean insertProjectManager(String cf, Progetto tmp, String string) throws SQLException;

	//TODO: probabilmente inutile
	public String getProjectManager(Progetto progetto) throws SQLException;
	public ArrayList<CollaborazioneProgetto> getPartecipanti(int codiceProgetto) throws SQLException;
	public boolean addProjectManager(String cf, Progetto tmp, String string) throws SQLException;
	public boolean aggiornaPartecipante(CollaborazioneProgetto collaborazioneProgetto) throws SQLException;
	public String ottieniProjectManager(Progetto progetto) throws SQLException;
	public ArrayList<Progetto> getProgettiDipendenteByNome(String nomeCercato, Dipendente dipendente)throws SQLException;

	

}
