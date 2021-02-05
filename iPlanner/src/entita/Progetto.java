//Classe che rappresenta i progetti dell'azienda.
/*Ogni progetto dell'azienda è composto da un suo codice identificativo idProgetto,
*da un nome del progetto nomeProgetto, da uno o più ambiti, da un tipo di progetto tipoProgetto,
*da una sua descrizione breve descrizioneProgetto, da una data di creazione dataCreazione,
*da un'eventuale data di scadenza del progetto scadenza,
*da una data in cui è terminato il progetto (se è terminato) dataTerminazione e da un dipendente
*creatore del progetto creatore.
*************************************************************************************************/
package entita;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Progetto {
	
	//ATTRIBUTI
	//----------------------------------------
	
	//Attributi caratteristici
	private int idProgettto;	//id del progetto (pk)
	private String nomeProgetto;	//nome progetto (not null)
	private String tipoProgetto;	//tipologia del progetto (not null)
	private String descrizioneProgetto;	//descrizione del progetto
	private LocalDate dataCreazione;	//data creazione del progetto (not null)
	private LocalDate scadenza;	//eventuale scadenza del progetto
	private LocalDate dataTerminazione;	//data in cui è terminato il progetto (se è terminato)
	
	
	//Attributi per associazioni
	private ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();	//lista di ambiti del progetto
	private ArrayList<Dipendente> comprende=new ArrayList<Dipendente>(); //Associazione progetto-dipendente
	private ArrayList<Meeting> discussoIn = new ArrayList<Meeting>();	//lista di meeting relativi al progetto;
	private ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();	//lista di collaborazioni del progetto

	//METODI
	//----------------------------------------
	
	//Costruttore con tutti gli attributi essenziali per una query
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

	//Costruttore con attributi per la creazione di nuovi progetti
	public Progetto(String nomeProgetto, String tipoProgetto, String descrizioneProgetto, LocalDate dataCreazione,
			LocalDate scadenza) {
		this.nomeProgetto = nomeProgetto;
		this.tipoProgetto = tipoProgetto;
		this.descrizioneProgetto = descrizioneProgetto;
		this.dataCreazione = dataCreazione;
		this.scadenza = scadenza;
	}
	
	
	
	public Progetto(String nomeProgetto, String tipoProgetto, String descrizioneProgetto, LocalDate dataCreazione,
			LocalDate scadenza, LocalDate dataTerminazione, ArrayList<AmbitoProgetto> ambiti,
			ArrayList<Meeting> discussoIn) {
		super();
		this.nomeProgetto = nomeProgetto;
		this.tipoProgetto = tipoProgetto;
		this.descrizioneProgetto = descrizioneProgetto;
		this.dataCreazione = dataCreazione;
		this.scadenza = scadenza;
		this.dataTerminazione = dataTerminazione;
		this.ambiti = ambiti;
		this.discussoIn = discussoIn;
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

	public ArrayList<AmbitoProgetto> getAmbiti() {
		return ambiti;
	}

	public void setAmbiti(ArrayList<AmbitoProgetto> ambiti) {
		this.ambiti = ambiti;
	}
	
	public void setMeetingsRelativi(ArrayList<Meeting> meetingsRelativi) {
		this.discussoIn = meetingsRelativi;
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

	public ArrayList<Meeting> getDiscussoIn() {
		return discussoIn;
	}

	public void setDiscussoIn(ArrayList<Meeting> discussoIn) {
		this.discussoIn = discussoIn;
	}
	

//	@Override
//	public String toString() {
//		return "Progetto [idProgettto=" + idProgettto + ", nomeProgetto=" + nomeProgetto + ", tipoProgetto="
//				+ tipoProgetto + ", descrizioneProgetto=" + descrizioneProgetto + ", dataCreazione=" + dataCreazione
//				+ ", scadenza=" + scadenza + ", dataTerminazione=" + dataTerminazione + ", ambiti=" + ambiti
//				+ ", comprende=" + comprende + ", discussoIn=" + discussoIn + ", collaborazioni=" + collaborazioni
//				+ "]";
//	}



	//toString
	@Override
	public String toString() {
		String temp = getNomeProgetto();
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		if (!scadenza.equals(null))
			temp += " " + scadenza.toString(formatDate);
				
		return temp;
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
		if (ambiti == null) {
			if (other.ambiti != null)
				return false;
		} else if (!ambiti.equals(other.ambiti))
			return false;
		if (collaborazioni == null) {
			if (other.collaborazioni != null)
				return false;
		} else if (!collaborazioni.equals(other.collaborazioni))
			return false;
		if (comprende == null) {
			if (other.comprende != null)
				return false;
		} else if (!comprende.equals(other.comprende))
			return false;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (dataTerminazione == null) {
			if (other.dataTerminazione != null)
				return false;
		} else if (!dataTerminazione.equals(other.dataTerminazione))
			return false;
		if (descrizioneProgetto == null) {
			if (other.descrizioneProgetto != null)
				return false;
		} else if (!descrizioneProgetto.equals(other.descrizioneProgetto))
			return false;
		if (discussoIn == null) {
			if (other.discussoIn != null)
				return false;
		} else if (!discussoIn.equals(other.discussoIn))
			return false;
		if (idProgettto != other.idProgettto)
			return false;
		if (nomeProgetto == null) {
			if (other.nomeProgetto != null)
				return false;
		} else if (!nomeProgetto.equals(other.nomeProgetto))
			return false;
		if (scadenza == null) {
			if (other.scadenza != null)
				return false;
		} else if (!scadenza.equals(other.scadenza))
			return false;
		if (tipoProgetto == null) {
			if (other.tipoProgetto != null)
				return false;
		} else if (!tipoProgetto.equals(other.tipoProgetto))
			return false;
		return true;
	}
	
	
	
	
	
	
}
