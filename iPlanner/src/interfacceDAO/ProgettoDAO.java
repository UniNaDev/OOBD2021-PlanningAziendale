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

	public ArrayList<Progetto> getProgetti() throws SQLException;	//metodo che restituisce tutti i progetti
//	public ArrayList<CollaborazioneProgetto> getPartecipanti(int codiceProgetto) throws SQLException;	//metodo che restituisce i partecipanti a un progetto
	public ArrayList<Meeting> getMeetingRelativi(int codProgettoSelezionato) throws SQLException;	//metodo che restituisce i meeting relativi ad un progetto
	public ArrayList<CollaborazioneProgetto> getProgettiByDipendente(Dipendente dip) throws SQLException;	//metodo che restituisce i progetti a cui partecipa un dipendente
	public ArrayList<Progetto> getProgettiByCreatore(Dipendente dip) throws SQLException;	//metodo che restituisce i progetti creati da un dipendente
	public ArrayList<Progetto> getProgettiByAmbito(AmbitoProgetto ambito) throws SQLException;	//metodo che restituisce i progetti di un certo ambito
	public ArrayList<Progetto> getProgettiByTipo(String tipologia) throws SQLException;	//metodo che restituisce i progetti di un certo tipo
	public boolean addProgetto(Progetto proj) throws SQLException;	//metodo che inserisce un nuovo progetto nel DB
	public boolean removeProgetto(Progetto proj) throws SQLException;	//metodo che rimuove un progetto dal DB
	public boolean addPartecipante(String cf,int codiceProgetto2, String ruolo) throws SQLException;	//metodo che aggiunge un dipendente ai partecipanti a un progetto
	public boolean deletePartecipante(Dipendente dip, int codiceProgetto2) throws SQLException;	//metodo che rimuove un partecipante a un progetto
	public boolean updateProgetto(Progetto proj) throws SQLException; //metodo che aggiorna un progetto nel DB
	public Progetto getProgettoByCod(int codProgetto) throws SQLException;	//metodo che recupera un progetto partendo dal suo codice nel DB
	public ArrayList<String> getTipologie() throws SQLException;	//metodo che ottiene le tipologie possibili di un progetto
	public ArrayList<Dipendente> getPartecipantiSenzaRuolo(int codiceProgetto) throws SQLException; //metodo che restituisce tutti i partecipanti ad un progetto senza specificare il ruolo
	public int getCodProgetto(Progetto proj) throws SQLException; //metodo che ottiene il codice di un progetto
	public boolean removeProgettoByCod(int codProgetto) throws SQLException; //metodo che rimuove un progetto dal db prendendo in input solo il codice
	public ArrayList<String> getRuoliDipendenti() throws SQLException; //Metodo che ritorna tutti i ruoli presenti nel DB
	public Progetto getProgettoInserito(String nomeProgetto) throws SQLException;
//	public ArrayList<CollaborazioneProgetto> getPartecipanti(Progetto progetto, int codiceProgetto) throws SQLException;
	public ArrayList<CollaborazioneProgetto> getPartecipanti(int codiceProgetto) throws SQLException;

}
