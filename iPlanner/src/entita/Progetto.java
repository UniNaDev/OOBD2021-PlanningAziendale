//Classe che rappresenta i progetti dell'azienda.
/*Ogni progetto dell'azienda è composto da un suo codice identificativo,
*da un nome del progetto, da uno o più ambiti, da un tipo di progetto,
*da una sua descrizione breve, da una data di creazione,
*da un'eventuale data di scadenza e
*da una data in cui è terminato (se è terminato).
*************************************************************************************************/

package entita;

import java.util.ArrayList;

import org.joda.time.LocalDate;

public class Progetto {
	private int idProgettto;
	private String nomeProgetto;
	private String tipoProgetto;
	private String descrizioneProgetto;
	private LocalDate dataCreazione;
	private LocalDate scadenza;
	private LocalDate dataTerminazione;
	
	private ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
	private ArrayList<Dipendente> comprende = new ArrayList<Dipendente>();
	private ArrayList<Meeting> meetingsRelativi = new ArrayList<Meeting>();
	private ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();
	
	//TODO: si può eliminare (basta cambiare nel DAO come si comporta rispetto a dataTerminazione)
	public Progetto(int idProgettto, String nomeProgetto, String tipoProgetto, String descrizioneProgetto,
			LocalDate dataCreazione, LocalDate scadenza) {
		this.idProgettto = idProgettto;
		this.nomeProgetto = nomeProgetto;
		this.tipoProgetto = tipoProgetto;
		this.descrizioneProgetto = descrizioneProgetto;
		this.dataCreazione = dataCreazione;
		this.scadenza = scadenza;

	}
	
	public Progetto(int idProgettto, String nomeProgetto, String tipoProgetto, String descrizioneProgetto,
			LocalDate dataCreazione, LocalDate scadenza,LocalDate dataTerminazione) {
		this.idProgettto = idProgettto;
		this.nomeProgetto = nomeProgetto;
		this.tipoProgetto = tipoProgetto;
		this.descrizioneProgetto = descrizioneProgetto;
		this.dataCreazione = dataCreazione;
		this.scadenza = scadenza;
		this.dataTerminazione=dataTerminazione;
	}
	
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

	public ArrayList<AmbitoProgetto> getAmbiti() {
		return ambiti;
	}

	public void setAmbiti(ArrayList<AmbitoProgetto> ambiti) {
		this.ambiti = ambiti;
	}


	public ArrayList<CollaborazioneProgetto> getCollaborazioni() {
		return collaborazioni;
	}

	public void setCollaborazioni(ArrayList<CollaborazioneProgetto> collaborazioni) {
		this.collaborazioni = collaborazioni;
	}
	
	public void setIdProgettto(int idProgettto) {
		this.idProgettto = idProgettto;
	}

	public ArrayList<Dipendente> getComprende() {
		return comprende;
	}

	public void setComprende(ArrayList<Dipendente> comprende) {
		this.comprende = comprende;
	}

	public ArrayList<Meeting> getMeetingsRelativi() {
		return meetingsRelativi;
	}
	
	
	public void setMeetingsRelativi(ArrayList<Meeting> meetingsRelativi) {
		this.meetingsRelativi = meetingsRelativi;
	}
	
	@Override
	public String toString() {
		return nomeProgetto;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Progetto other = (Progetto) obj;
		if (idProgettto != other.idProgettto)
			return false;
		return true;
	}
}
