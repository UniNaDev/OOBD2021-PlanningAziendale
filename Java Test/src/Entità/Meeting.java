//Classe Meeting che rappresenta i meeting aziendali.
/*Un meeting è caratterizzato da un id interno al DB per distinguerlo, una data di inizio, un orario di inizio, una data di fine,
*un orario di fine, una modalità (Fisico/Telematico), un'eventuale piattaforma qualora fosse telematico e un dipendente organizzatore
*del meeting.*/

package Entità;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class Meeting {
	
	//ATTRIBUTI
	
	private int idMeeting;	//id nel DB che distingue il meeting
	private LocalDate dataInizio;	//data di inizio del meeting
	private LocalDate dataFine;	//data di fine del meeting
	private LocalTime oraInizio;	//orario di inizio del meeting
	private LocalTime oraFine;	//orario di fine del meeting
	private String modalità;	//modalità del meeting ("Fisico" oppure "Telematico")
	private String piattaforma;	//piattaforma su cui eventualmente verrà tenuto il meeting
	private Dipendente organizzatore;	//dipendente organizzatore del meeting
	
	private SalaRiunione sala;	//sala in cui si può tenere il meeting
	
	//METODI
	
	//Costruttore con tutti gli attributi eccetto la piattaforma che può essere NULL nel caso dei meeting fisici
	public Meeting(LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalità, Dipendente organizzatore) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalità = modalità;
		this.organizzatore = organizzatore;
	}

	//Costruttore con tutti gli attributi, compresa piattaforma
	public Meeting(LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalità, String piattaforma, Dipendente organizzatore) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalità = modalità;
		this.piattaforma = piattaforma;
		this.organizzatore = organizzatore;
	}

	//Getter e Setter
	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public String getModalità() {
		return modalità;
	}

	public void setModalità(String modalità) {
		this.modalità = modalità;
	}

	public String getPiattaforma() {
		return piattaforma;
	}

	public void setPiattaforma(String piattaforma) {
		this.piattaforma = piattaforma;
	}

	public Dipendente getOrganizzatore() {
		return organizzatore;
	}

	public void setOrganizzatore(Dipendente organizzatore) {
		this.organizzatore = organizzatore;
	}

	public void setIdMeeting(int idMeeting) {
		this.idMeeting = idMeeting;
	}
	public int getIdMeeting() {
		return idMeeting;
	}
	
	
	public SalaRiunione getSala() {
		return sala;
	}

	public void setSala(SalaRiunione sala) {
		this.sala = sala;
	}

	//toString
	@Override
	public String toString() {
		return dataInizio.toString() + oraInizio.toString() + " - " + dataFine.toString() + oraFine.toString() + " [" + modalità + "]" + piattaforma + organizzatore;
	}
}
