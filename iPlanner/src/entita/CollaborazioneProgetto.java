//Classe di associazione tra entità di tipo Dipendente e Progetto.
//Contiene il progetto, il dipendente che ci lavora e il ruolo che egli assume in esso.
package entita;

public class CollaborazioneProgetto {

	//ATTRIBUTI
	
	private Progetto progetto;	//progetto a cui il dipendente collabora
	private Dipendente collaboratore;	//dipendente che collabora al progetto
	private String ruolo;	//ruolo del dipendente nel progetto
	
	//METODI
	
	//Costruttore della collaborazione
	public CollaborazioneProgetto(Progetto progetto, Dipendente collaboratore, String ruolo) {
		this.progetto = progetto;
		this.collaboratore = collaboratore;
		this.ruolo = ruolo;
	}

	//Getter e setter
	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

	public Dipendente getCollaboratore() {
		return collaboratore;
	}

	public void setCollaboratore(Dipendente collaboratore) {
		this.collaboratore = collaboratore;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
}
