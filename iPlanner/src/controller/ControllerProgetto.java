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


public class ControllerProgetto {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private MieiProgetti mieiProgetti;
	private GestioneProgetti gestioneProgetti;

	//DAO
	private LuogoNascitaDAO luogoDAO = null;	//dao luogo di nascita
	private DipendenteDAO dipDAO = null;	//dao del dipendente
	private ProgettoDAO projDAO = null;	//dao progetti
	private MeetingDAO meetDAO = null;	//dao meeting
	private AmbitoProgettoDAO ambitoDAO = null;	//dao ambiti progetti
	
	//Altri attributi
	private Dipendente dipendente = null;
	
	//METODI
	//-----------------------------------------------------------------
	
	//Costruttore
	public ControllerProgetto(LuogoNascitaDAO luogoDAO, DipendenteDAO dipDAO, ProgettoDAO projDAO, MeetingDAO meetDAO, AmbitoProgettoDAO ambitoDAO, Dipendente dipendente) {
		//ottiene i dao
		this.luogoDAO = luogoDAO;
		this.dipDAO = dipDAO;
		this.projDAO = projDAO;
		this.meetDAO = meetDAO;
		this.ambitoDAO = ambitoDAO;
		
		this.dipendente = dipendente;	//ottiene il dipendente che ha avuto accesso
		
		//apre la finestra Miei Progetti
		mieiProgetti=new MieiProgetti(this, this.dipendente);
		mieiProgetti.setVisible(true);
	}

	//Metodi di gestione delle GUI
	//-----------------------------------------------------------------
	
	//Metodo che apre la finestra di gestione dei progetti
	public void apriGestioneProgetti() {
		gestioneProgetti=new GestioneProgetti(this);
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
	public ArrayList<String> ottieniTipologie() throws SQLException{
		return projDAO.getTipologie();
	}

	//Ottiene tutti i partecipanti relativi ad un progetto
	public ArrayList<Dipendente> getPartecipantiProgetto (Progetto proj) throws SQLException
	{
		return projDAO.getPartecipantiSenzaRuolo(proj);
	}

	//Ottiene tutti i meeting relativi ad un progetto
	public ArrayList<Meeting> getMeetingRelativiProgetto(Progetto proj) throws SQLException
	{
		return projDAO.getMeetingRelativi(proj);
	}

	//metodo fa l'update del progetto con i nuovi campi inseriti
	public void updateProgetto(int codProgetto ,String nuovoNome ,String nuovaTipologia ,String nuovaDescrizione, LocalDate dataCreazione , LocalDate nuovaDataScadenza, LocalDate nuovaDataTerminazione) throws SQLException 
	{
		//crea un progetto temporaneo con i dati del progetto aggiorati
		Progetto tmp = new Progetto(codProgetto, nuovoNome , nuovaTipologia , nuovaDescrizione , dataCreazione , nuovaDataScadenza, nuovaDataTerminazione);
		
		projDAO.updateProgetto(tmp);
	}
	
	//metodo che fa l'insert di un nuovo progetto con i campi inseriti
	public void addProgetto(String nomeProgetto , String tipologia , String descrizioneProgetto , LocalDate dataCreazione , LocalDate dataScadenza,ArrayList<AmbitoProgetto> ambiti) throws SQLException 
	{
		Progetto tmp = new Progetto(nomeProgetto, tipologia, descrizioneProgetto , dataCreazione , dataScadenza);

		//aggiunge prima il progetto senza ambiti in modo che la sequence nel db crei un CodProgetto
		projDAO.addProgetto(tmp);
		
		//RICAVA L ID DEL PROGETTO DAL DB E LO SETTA COME CODPROGETTO
		tmp.setIdProgettto(projDAO.getCodProgetto(tmp));
		
		//aggiunge gli ambiti presi dalle righe seleziionate al progetto
		tmp.setAmbiti(ambiti);

		//fa un insert in ambitoProgettoLink nel db
		ambitoDAO.addAmbitiProgetto(tmp);

	}
		
	
		
	
}
