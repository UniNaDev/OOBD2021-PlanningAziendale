package controller.dipendente;

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
import gui.dipendente.GestioneProgettiDipendente;
import gui.dipendente.MieiProgetti;
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
	
	//Metodo che apre la finestra per inserire partecipanti a un progetto
	public void apriInserisciPartecipantiProgetto(Progetto progettoSelezionato) {
		ControllerPartecipantiProgetto controller=new ControllerPartecipantiProgetto(luogoDAO, dipDAO, projDAO, meetDAO, dipendente,skillDAO,progettoSelezionato);
	}
	
	//Metodo che apre la finestra di gestione dei progetti
	public void apriGestioneProgetti() {
		gestioneProgetti = new GestioneProgettiDipendente(this,dipendente);
		gestioneProgetti.setVisible(true);
		mieiProgetti.setVisible(false);
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
	public ArrayList<Dipendente> getPartecipantiProgetto (int codiceProgetto) throws SQLException {
		return projDAO.getPartecipantiProgettoSenzaRuolo(codiceProgetto);
	}

	//Ottiene tutti i meeting relativi ad un progetto
	public ArrayList<Meeting> getMeetingRelativiProgetto(int codProgettoSelezionato) throws SQLException {
		return projDAO.getMeetingRelativiProgetto(codProgettoSelezionato);
	}

	//metodo fa l'update del progetto con i nuovi campi inseriti
	public void updateProgetto(int codProgetto ,String nuovoNome ,String nuovaTipologia ,String nuovaDescrizione,LocalDate dataCreazione, LocalDate nuovaDataTerminazione , LocalDate nuovaDataScadenza, ArrayList<AmbitoProgetto> nuoviAmbiti) throws SQLException {
		//crea un progetto temporaneo con i dati del progetto aggiorati
		Progetto tmp = new Progetto(codProgetto, nuovoNome , nuovaTipologia , nuovaDescrizione ,dataCreazione, nuovaDataScadenza, nuovaDataTerminazione);
		
		projDAO.updateProgetto(tmp);
		
		//rimuove i vecchi ambiti che aveva il progetto
		ambitoDAO.deleteAmbitiProgetto(tmp);
		
		//setta i nuovi ambiti del progetto e li aggiunge 
		tmp.setAmbiti(nuoviAmbiti);
		ambitoDAO.insertAmbitiOfProgetto(tmp);								
	}
	
	//metodo che fa l'insert di un nuovo progetto con i campi inseriti
	public void addProgetto(String nomeProgetto , String tipologia , String descrizioneProgetto , LocalDate dataCreazione , LocalDate dataScadenza,ArrayList<AmbitoProgetto> ambiti) throws SQLException {
		Progetto tmp = new Progetto(nomeProgetto, tipologia, descrizioneProgetto , dataCreazione , dataScadenza);

		//aggiunge prima il progetto senza ambiti in modo che la sequence nel db crei un CodProgetto
		projDAO.insertProgetto(tmp);
		
		//ricava il codProgetto del progetto appena inserto e lo setta
		tmp.setIdProgettto(projDAO.getCodProgetto(tmp));
		
		//aggiunge gli ambiti presi dalle righe seleziionate al progetto
		tmp.setAmbiti(ambiti);

		//fa un insert in ambitoProgettoLink nel db
		ambitoDAO.insertAmbitiOfProgetto(tmp);
		
		//imposta il dipendente che ha creato il progetto come project Manager
		projDAO.insertProjectManager(dipendente.getCf(), tmp, "Project Manager");	
	}
	
	//metodo che prende in input il codprogetto e restituisce gli ambiti
	public ArrayList<AmbitoProgetto> getAmbitiProgettoByCod(int codProgetto) throws SQLException {
		return ambitoDAO.getAmbitiProgettoByCodice(codProgetto);
	}
	
	//metodo che prende in input il codProgetto e lo elimina dal db
	public boolean rimuoviProgetto (Progetto progetto) throws SQLException {
		boolean risultato = projDAO.deleteProgetto(progetto);
		
		return risultato;
	}

	//Metodo che aggiorna un progetto nel DB
	public void updateProgetto(Progetto progetto) throws SQLException {
		
		projDAO.updateProgetto(progetto);
		
		//rimuove i vecchi ambiti che aveva il progetto
		ambitoDAO.deleteAmbitiProgetto(progetto);
		
		//setta i nuovi ambiti del progetto e li aggiunge 
		progetto.setAmbiti(progetto.getAmbiti());
		ambitoDAO.insertAmbitiOfProgetto(progetto);
		
	}
	
	//Metodo che ottiene il project manager di un progetto
	public String ottieniProjectManager(Progetto progetto) throws SQLException {
		return projDAO.getProjectManager(progetto);
	}

	//Metodo che filtra i progetti
	public ArrayList<Progetto> ottieniProgettiFiltrati(String nomeCercato, AmbitoProgetto ambitoCercato, String tipologiaCercata, String scaduto, String terminato) throws SQLException{
		ArrayList<Progetto> risultato = projDAO.getProgettiDipendenteByNome(nomeCercato,dipendente);	//ottiene i progetti filtrati per nome
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		if (tipologiaCercata != null) {
			temp = projDAO.getProgettiByTipo(tipologiaCercata);	//ottiene i progetti filtrati per tipologia
			risultato.retainAll(temp);	//interseca i progetti precedenti con quelli filtrati ora per tipologia
		}
		if (ambitoCercato != null) {
			temp = projDAO.getProgettiByAmbito(ambitoCercato); //ottiene i progetti filtrati per ambito
			risultato.retainAll(temp);
		}
		if (scaduto.equals("Si")) {
			//filtra temp prendendo solo quelli scaduti
			risultato.retainAll(filtraScaduti(risultato));
		}
		else if (scaduto.equals("No")) {
			//filtra temp prendendo solo quelli NON scaduti
			risultato.retainAll(filtraNonScaduti(risultato));
		}
		if (terminato.equals("Si")) {
			//filtra temp prendendo solo quelli terminati
			risultato.retainAll(filtraTerminati(risultato));
		}
		else if (terminato.equals("No")) {
			//filtra temp prendendo solo quelli NON terminati
			risultato.retainAll(filtraNonTerminati(risultato));
		}
		return risultato;
	}
	
	//Metodo che filtra i progetti scaduti
	private ArrayList<Progetto> filtraScaduti(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getScadenza().isBefore(LocalDate.now())) {
				temp.add(proj);
			}
		}
		return temp;
	}
	
	//Metodo che filtra i progetti NON scaduti
	private ArrayList<Progetto> filtraNonScaduti(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getScadenza().isAfter(LocalDate.now())) {
				temp.add(proj);
			}
		}
		return temp;
	}
	
	//Metodo che filtra i progetti terminati
	private ArrayList<Progetto> filtraTerminati(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getDataTerminazione() != null) {
				temp.add(proj);
			}
		}
		return temp;
	}
	
	//Metodo che filtra i progetti NON terminati
	private ArrayList<Progetto> filtraNonTerminati(ArrayList<Progetto> progetti){
		ArrayList<Progetto> temp = new ArrayList<Progetto>();
		for (Progetto proj: progetti) {
			if (proj.getDataTerminazione() == null) {
				temp.add(proj);
			}
		}
		return temp;
	}
}


	

