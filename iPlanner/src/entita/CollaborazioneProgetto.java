//Classe di associazione tra entità di tipo Dipendente e Progetto.
//Contiene il progetto, il dipendente che ci lavora e il ruolo che egli assume in esso.

package entita;

public class CollaborazioneProgetto {
	private Progetto progetto;
	private Dipendente collaboratore;
	private String ruoloCollaboratore;
	
	public CollaborazioneProgetto(Progetto progetto, Dipendente collaboratore, String ruoloCollaboratore) {
		this.progetto = progetto;
		this.collaboratore = collaboratore;
		this.ruoloCollaboratore = ruoloCollaboratore;
	}

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

	public String getRuoloCollaboratore() {
		return ruoloCollaboratore;
	}

	public void setRuoloCollaboratore(String ruoloCollaboratore) {
		this.ruoloCollaboratore = ruoloCollaboratore;
	}

	/*Formato toString:
	 *Nome Cognome
	 *(Ruolo)
	 */
	@Override
	public String toString() {
		return getCollaboratore().getNome()+" "+getCollaboratore().getCognome()+"\n("+ruoloCollaboratore + ")";
	}
	
	
	
}
