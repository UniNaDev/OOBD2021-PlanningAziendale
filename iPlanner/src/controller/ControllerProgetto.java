package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import org.joda.time.LocalDate;

import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import gui.*;
import interfacceDAO.AmbitoProgettoDAO;
import interfacceDAO.DipendenteDAO;
import interfacceDAO.LuogoNascitaDAO;
import interfacceDAO.MeetingDAO;
import interfacceDAO.ProgettoDAO;
import interfacceDAO.SkillDAO;


public class ControllerProgetto {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private MieiProgetti mieiProgetti;
	private GestioneProgettiDipendente gestioneProgetti;

	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetti
	private MeetingDAO meetDAO = null;	//dao meeting
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	private SkillDAO skillDAO=null;
	//Altri attributi
	private Dipendente dipendente = null;
	
	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore
	public ControllerProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, AmbitoProgettoDAO ambitoDAO,SkillDAO skillDAO, Dipendente dipendente) {
		//ottiene i dao
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.ambitoDAO = ambitoDAO;
		this.skillDAO=skillDAO;
		this.dipendente = dipendente;	//ottiene il dipendente che ha avuto accesso
		
		//apre la finestra Miei Progetti
		mieiProgetti=new MieiProgetti(this, this.dipendente);
		mieiProgetti.setVisible(true);
	}

	//Metodi di gestione delle GUI
	//-----------------------------------------------------------------
	
	public void apriInserisciPartecipantiProgetto(Progetto progettoSelezionato, int codiceProgetto) {
		
		ControllerPartecipantiProgetto controller=new ControllerPartecipantiProgetto(luogoDAO, dipDAO, projDAO, meetDAO, dipendente,skillDAO,progettoSelezionato,codiceProgetto);
		
	}
	
	//Metodo che apre la finestra di gestione dei progetti
	public void apriGestioneProgetti() {
		gestioneProgetti=new GestioneProgettiDipendente(this);
		gestioneProgetti.setVisible(true);
	}

	//Altri metodi
	//-----------------------------------------------------------------
	
	//Ottiene i progetti del dipendente
	public ArrayList<Progetto> ottieniProgetti() throws SQLException {
		ArrayList<CollaborazioneProgetto> collaborazioni = projDAO.getProgettiByDipendente(dipendente);
		
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (CollaborazioneProgetto collaborazione: collaborazioni)
			temp.add(collaborazione.getProgetto());
		
		return temp;
	}
	
	//Ottiene tutti gli ambiti progetto del database
	public ArrayList<AmbitoProgetto> ottieniAmbiti() throws SQLException{
		return ambitoDAO.getAmbiti();
	}
	
	//Ottiene tutte le tipologie possibili nel database
	public String [] ottieniTipologie() throws SQLException{
		String [] temp = new String [projDAO.getTipologie().size()];
		for (int i = 0; i < projDAO.getTipologie().size(); i++)
			temp[i] = projDAO.getTipologie().get(i);
		return temp;
	}

	//Ottiene tutti i partecipanti relativi ad un progetto
	public ArrayList<Dipendente> getPartecipantiProgetto (int codiceProgetto) throws SQLException
	{
		return projDAO.getPartecipantiSenzaRuolo(codiceProgetto);
	}

	//Ottiene tutti i meeting relativi ad un progetto
	public ArrayList<Meeting> getMeetingRelativiProgetto(int codProgettoSelezionato) throws SQLException
	{
		return projDAO.getMeetingRelativi(codProgettoSelezionato);
	}

	//metodo fa l'update del progetto con i nuovi campi inseriti
	public void updateProgetto(int codProgetto ,String nuovoNome ,String nuovaTipologia ,String nuovaDescrizione, LocalDate dataCreazione , LocalDate nuovaDataScadenza, LocalDate nuovaDataTerminazione ,ArrayList<AmbitoProgetto> nuoviAmbiti) throws SQLException 
	{
		//crea un progetto temporaneo con i dati del progetto aggiorati
		Progetto tmp = new Progetto(codProgetto, nuovoNome , nuovaTipologia , nuovaDescrizione , dataCreazione , nuovaDataScadenza, nuovaDataTerminazione);
		
		projDAO.updateProgetto(tmp);
		
		//rimuove i vecchi ambiti che aveva il progetto
		ambitoDAO.removeAmbitiProgetto(tmp);
		
		//setta i nuovi ambiti del progetto e li aggiunge 
		tmp.setAmbiti(nuoviAmbiti);
		ambitoDAO.addAmbitiProgetto(tmp);
		
		
		//aggiorna le finestre i miei progetti e gestione progetto per visualizzare le modifiche
		mieiProgetti.setVisible(false);
		mieiProgetti = new MieiProgetti(this,dipendente);
		mieiProgetti.setVisible(true);
		
		gestioneProgetti.setVisible(false);
		gestioneProgetti= new GestioneProgettiDipendente(this);
		gestioneProgetti.setVisible(true);
								
	}
	
	//metodo che fa l'insert di un nuovo progetto con i campi inseriti
	public void addProgetto(String nomeProgetto , String tipologia , String descrizioneProgetto , LocalDate dataCreazione , LocalDate dataScadenza,ArrayList<AmbitoProgetto> ambiti) throws SQLException 
	{
		Progetto tmp = new Progetto(nomeProgetto, tipologia, descrizioneProgetto , dataCreazione , dataScadenza);

		//aggiunge prima il progetto senza ambiti in modo che la sequence nel db crei un CodProgetto
		projDAO.addProgetto(tmp);
		
		//ricava il codProgetto del progetto appena inserto e lo setta
		tmp.setIdProgettto(projDAO.getCodProgetto(tmp));
		
		//aggiunge gli ambiti presi dalle righe seleziionate al progetto
		tmp.setAmbiti(ambiti);

		//fa un insert in ambitoProgettoLink nel db
		ambitoDAO.addAmbitiProgetto(tmp);
		
		//imposta il dipendente che ha creato il progetto come project Manager
		projDAO.addPartecipante(dipendente.getCf(), projDAO.getCodProgetto(tmp), "Project Manager");
	
		//aggiorna le finestre i miei progetti e gestione progetto per visualizzare le modifiche
		mieiProgetti.setVisible(false);
		mieiProgetti = new MieiProgetti(this,dipendente);
		mieiProgetti.setVisible(true);
		
		gestioneProgetti.setVisible(false);
		gestioneProgetti= new GestioneProgettiDipendente(this);
		gestioneProgetti.setVisible(true);
		
	}
	
	//metodo che prende in input il codprogetto e restituisce gli ambiti
	public ArrayList<AmbitoProgetto> getAmbitiProgettoByCod(int codProgetto) throws SQLException
	{
		return ambitoDAO.getAmbitiProgettoByCodice(codProgetto);
	}
	
	//metodo che prende in input il codProgetto e lo elimina dal db
	public boolean removeProgettoByCod (int codProgetto) throws SQLException
	{
		boolean risultato = projDAO.removeProgettoByCod(codProgetto);
		
		//aggiorna le finestre i miei progetti e gestione progetto per visualizzare le modifiche
		mieiProgetti.setVisible(false);
		mieiProgetti = new MieiProgetti(this,dipendente);
		mieiProgetti.setVisible(true);
		
		gestioneProgetti.setVisible(false);
		gestioneProgetti= new GestioneProgettiDipendente(this);
		gestioneProgetti.setVisible(true);
		
		return risultato;
	}


	
}
