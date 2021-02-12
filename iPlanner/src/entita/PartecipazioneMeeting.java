//Classe di associazione tra entità di tipo Dipendente e di tipo Meeting.
//Contiene il meeting, il dipendente che deve partecipare, la sua presenza e
//informazioni su se sia lui l'organizzatore del meeting oppure no.

package entita;

public class PartecipazioneMeeting {
	private Meeting meeting;
	private Dipendente partecipante;
	private boolean presente = false;
	private boolean organizzatore = false;
	
	public PartecipazioneMeeting(Meeting meeting, Dipendente partecipante, boolean presente, boolean organizzatore) {
		this.meeting = meeting;
		this.partecipante = partecipante;
		this.presente = presente;
		this.organizzatore = organizzatore;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Dipendente getPartecipante() {
		return partecipante;
	}

	public void setPartecipante(Dipendente partecipante) {
		this.partecipante = partecipante;
	}

	public boolean isPresente() {
		return presente;
	}

	public void setPresente(boolean presente) {
		this.presente = presente;
	}
	
	public boolean isOrganizzatore() {
		return organizzatore;
	}
	
	public void setOrganizzatore(boolean organizzatore) {
		this.organizzatore = organizzatore;
	}

	/*Formato toString:
	 * Nome partecipante Cognome partecipante
	 * true/false se presente o assente
	 * Organizzatore se è organizzatore
	 */
	@Override
	public String toString() {
		String partecipazioneMeeting =null;
		if(isOrganizzatore()==true)
			partecipazioneMeeting = partecipante.getNome()+" "+partecipante.getCognome()+"\nPresenza: "+isPresente()+"\nOrganizzatore";
		else
			partecipazioneMeeting = partecipante.getNome()+" "+partecipante.getCognome()+"\nPresenza: "+isPresente();
		return partecipazioneMeeting;
	}	
}
