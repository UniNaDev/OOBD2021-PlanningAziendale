//Classe di associazione tra entità di tipo Dipendente e di tipo Meeting.
//Contiene il meeting, il dipendente che deve partecipare e la sua presenza ad esso.

package Entità;

public class PartecipazioneMeeting {
	
	//ATTRIBUTI
	private Meeting meeting;
	private Dipendente partecipante;
	private boolean presenza = false;
	
	//METODI
	
	//Costruttore della classe con tutti gli attributi
	public PartecipazioneMeeting(Meeting meeting, Dipendente partecipante, boolean presenza) {
		this.meeting = meeting;
		this.partecipante = partecipante;
		this.presenza = presenza;
	}

	//Getter e setter
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

	public boolean isPresenza() {
		return presenza;
	}

	public void setPresenza(boolean presenza) {
		this.presenza = presenza;
	}

	//toString
	@Override
	public String toString() {
		String presente = "assente";
		if (presenza)
			presente = "assente";
		
		return partecipante + presente;
	}
}
