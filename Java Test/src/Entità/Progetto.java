//Classe che rappresenta i progetti dell'azienda.
/*Ogni progetto dell'azienda è composto da un suo codice identificativo idProgetto,
*da un nome del progetto nomeProgetto, da uno o più ambiti, da un tipo di progetto tipoProgetto,
*da una sua descrizione breve descrizioneProgetto, da una data di creazione dataCreazione,
*da un'eventuale data di scadenza del progetto scadenza,
*da una data in cui è terminato il progetto (se è terminato) dataTerminazione e da un dipendente
*creatore del progetto creatore.
*************************************************************************************************/
package Entità;

import java.util.ArrayList;

import org.joda.time.LocalDate;

public class Progetto {
	
	//ATTRIBUTI
	
	private int idProgettto;	//id del progetto (pk)
	private String nomeProgetto;	//nome progetto (not null)
	private String tipoProgetto;	//tipologia del progetto (not null)
	private String descrizioneProgetto;	//descrizione del progetto
	private LocalDate dataCreazione;	//data creazione del progetto (not null)
	private LocalDate scadenza;	//eventuale scadenza del progetto
	private LocalDate dataTerminazione;	//data in cui è terminato il progetto (se è terminato)
	
	private Dipendente creatore;	//creatore del progetto
	private ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();	//lista di ambiti del progetto
	private ArrayList<Dipendente> partecipanti = new ArrayList<Dipendente>();	//lista di dipendenti partecipanti al progetto
	
	private ArrayList<Meeting> meetingsRelativi = new ArrayList<Meeting>();	//lista di meeting relativi al progetto;

	//METODI
	
	//Costruttore con tutti gli attributi essenziali per una query
	public Progetto(int idProgettto, String nomeProgetto, String tipoProgetto, String descrizioneProgetto,
			LocalDate dataCreazione, LocalDate scadenza, LocalDate dataTerminazione, Dipendente creatore) {
		this.idProgettto = idProgettto;
		this.nomeProgetto = nomeProgetto;
		this.tipoProgetto = tipoProgetto;
		this.descrizioneProgetto = descrizioneProgetto;
		this.dataCreazione = dataCreazione;
		this.scadenza = scadenza;
		this.dataTerminazione = dataTerminazione;
		this.creatore = creatore;
	}

	//Costruttore con attributi per la creazione di nuovi progetti
	public Progetto(String nomeProgetto, String tipoProgetto, String descrizioneProgetto, LocalDate dataCreazione,
			LocalDate scadenza, Dipendente creatore) {
		this.nomeProgetto = nomeProgetto;
		this.tipoProgetto = tipoProgetto;
		this.descrizioneProgetto = descrizioneProgetto;
		this.dataCreazione = dataCreazione;
		this.scadenza = scadenza;
		this.creatore = creatore;
	}

	//Getter e Setter
	//Manca setIdProgetto perchè non si vuole settare ma gestire con una sequence nel DBMS
	public int getIdProgettto() {
		return idProgettto;
	}

	public String getNomeProgetto() {
		return nomeProgetto;
	}

	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}

	public String getTipoProgetto() {
		return tipoProgetto;
	}

	public void setTipoProgetto(String tipoProgetto) {
		this.tipoProgetto = tipoProgetto;
	}

	public String getDescrizioneProgetto() {
		return descrizioneProgetto;
	}

	public void setDescrizioneProgetto(String descrizioneProgetto) {
		this.descrizioneProgetto = descrizioneProgetto;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public LocalDate getScadenza() {
		return scadenza;
	}

	public void setScadenza(LocalDate scadenza) {
		this.scadenza = scadenza;
	}

	public LocalDate getDataTerminazione() {
		return dataTerminazione;
	}

	public void setDataTerminazione(LocalDate dataTerminazione) {
		this.dataTerminazione = dataTerminazione;
	}

	public Dipendente getCreatore() {
		return creatore;
	}

	public void setCreatore(Dipendente creatore) {
		this.creatore = creatore;
	}

	public ArrayList<AmbitoProgetto> getAmbiti() {
		return ambiti;
	}

	public void setAmbiti(ArrayList<AmbitoProgetto> ambiti) {
		this.ambiti = ambiti;
	}

	public ArrayList<Meeting> getMeetingsRelativi() {
		return meetingsRelativi;
	}

	public void setMeetingsRelativi(ArrayList<Meeting> meetingsRelativi) {
		this.meetingsRelativi = meetingsRelativi;
	}

	public ArrayList<Dipendente> getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(ArrayList<Dipendente> partecipanti) {
		this.partecipanti = partecipanti;
	}

	//toString
	@Override
	public String toString() {
		return nomeProgetto + ":\n" + descrizioneProgetto;
	}
	
	
	
}
