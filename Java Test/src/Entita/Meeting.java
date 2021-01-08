//Classe Meeting che rappresenta i meeting aziendali.
/*Un meeting è caratterizzato da un id interno al DB per distinguerlo, una data di inizio, un orario di inizio, una data di fine,
*un orario di fine, una modalità (Fisico/Telematico), un'eventuale piattaforma qualora fosse telematico e un dipendente organizzatore
*del meeting.*/

package Entita;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Meeting {
	
	//ATTRIBUTI
	
	private int idMeeting;	//id nel DB che distingue il meeting
	private LocalDate dataInizio;	//data di inizio del meeting
	private LocalDate dataFine;	//data di fine del meeting
	private LocalTime oraInizio;	//orario di inizio del meeting
	private LocalTime oraFine;	//orario di fine del meeting
	private String modalita;	//modalità del meeting ("Fisico" oppure "Telematico")
	private String piattaforma;	//piattaforma su cui eventualmente verrà tenuto il meeting
	private Dipendente organizzatore;	//dipendente organizzatore del meeting
	
	private SalaRiunione sala;	//sala in cui si può tenere il meeting
	private Progetto progettoDiscusso;	//progetto discusso nel meeting
	private ArrayList<PartecipazioneMeeting> partecipazioniDipendenti = new ArrayList<PartecipazioneMeeting>();	//partecipazioni dei dipendenti al meeting
	
	//METODI
	
	//Costruttore con tutti gli attributi eccetto la piattaforma che può essere NULL nel caso dei meeting fisici
	public Meeting(LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita, Dipendente organizzatore) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
		this.organizzatore = organizzatore;
	}

	//Costruttore con tutti gli attributi, compresa piattaforma
	public Meeting(LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita, String piattaforma, Dipendente organizzatore) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
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

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalità) {
		this.modalita = modalità;
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
	
	public Progetto getProgettoDiscusso() {
		return progettoDiscusso;
	}

	public void setProgettoDiscusso(Progetto progettoDiscusso) {
		this.progettoDiscusso = progettoDiscusso;
	}

	public ArrayList<PartecipazioneMeeting> getPartecipazioniDipendenti() {
		return partecipazioniDipendenti;
	}

	public void setPartecipazioniDipendenti(ArrayList<PartecipazioneMeeting> partecipazioniDipendenti) {
		this.partecipazioniDipendenti = partecipazioniDipendenti;
	}

	//toString:
	//Nome Progetto
	//01/12/2020 13:00 - 01/12/2020 17:00
	//Sala 1 (oppure Microsoft Teams)
	@Override
	public String toString() {
		String temp = "";
		//String temp = progettoDiscusso.getNomeProgetto();
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
		temp += "\n" + dataInizio.toString(formatDate) + " " + oraInizio.toString(formatHour) + " - " + dataFine.toString(formatDate) + " " + oraFine.toString(formatHour);
		if (modalita.equals("Telematico"))
			temp += "\n" + piattaforma;
		else
			temp += "\n" + sala.getCodSala();
		return temp;
	}
}
