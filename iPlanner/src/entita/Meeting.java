//Classe Meeting che rappresenta i meeting aziendali.
/*Un meeting è caratterizzato da un id interno al DB per distinguerlo, una data di inizio, un orario di inizio, una data di fine,
*un orario di fine, una modalità (Fisico/Telematico), un'eventuale piattaforma qualora fosse telematico e un dipendente organizzatore
*del meeting.*/

package entita;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Meeting {
	
	//ATTRIBUTI
	//----------------------------------------
	//Attributi caratteristici
	private int idMeeting;	//id nel DB che distingue il meeting
	private LocalDate dataInizio;	//data di inizio del meeting
	private LocalDate dataFine;	//data di fine del meeting
	private LocalTime oraInizio;	//orario di inizio del meeting
	private LocalTime oraFine;	//orario di fine del meeting
	private String modalita;	//modalità del meeting ("Fisico" oppure "Telematico")
	private String piattaforma;	//piattaforma su cui eventualmente verrà tenuto il meeting
	private SalaRiunione sala;	//sala in cui si può tenere il meeting
	private Progetto releativoA;	//progetto discusso nel meeting
	
	//Attributi per associazioni
	private ArrayList<Dipendente> partecipazioniDipendenti = new ArrayList<Dipendente>();	//partecipazioni dei dipendenti al meeting
	
	//METODI
	//----------------------------------------
	
	//Costruttore con tutti gli attributi eccetto la piattaforma che può essere NULL nel caso dei meeting fisici
	public Meeting(int idMeeting, LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita) {
		this.idMeeting =idMeeting;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
	}

	//Costruttore con tutti gli attributi, compresa piattaforma
	public Meeting(int idMeeting, LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita, String piattaforma, SalaRiunione sala) {
		this.idMeeting = idMeeting;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
		this.piattaforma = piattaforma;
		this.sala = sala;
	}

	public Meeting(LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita, String piattaforma, SalaRiunione sala) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
		this.piattaforma = piattaforma;
		this.sala = sala;
	}


	public Meeting(int idMeeting,LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita, String piattaforma, SalaRiunione sala, Progetto releativoA) {
		super();
		this.idMeeting=idMeeting;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
		this.piattaforma = piattaforma;
		this.sala = sala;
		this.releativoA = releativoA;
	}


	public Meeting(LocalDate dataInizio, LocalDate dataFine, LocalTime oraInizio, LocalTime oraFine,
			String modalita, String piattaforma, SalaRiunione sala, Progetto releativoA) {
		super();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalita = modalita;
		this.piattaforma = piattaforma;
		this.sala = sala;
		this.releativoA = releativoA;
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

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public String getPiattaforma() {
		return piattaforma;
	}

	public void setPiattaforma(String piattaforma) {
		this.piattaforma = piattaforma;
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
		return releativoA;
	}

	public void setProgettoDiscusso(Progetto progetto) {
		
		
		this.releativoA = progetto;
	}

	public ArrayList<Dipendente> getPartecipazioniDipendenti() {
		return partecipazioniDipendenti;
	}

	public void setPartecipazioniDipendenti(ArrayList<Dipendente> partecipazioniDipendenti) {
		this.partecipazioniDipendenti = partecipazioniDipendenti;
	}

//	@Override
//	public String toString() {
//		return "Meeting [ dataInizio=" + dataInizio + ", dataFine=" + dataFine
//				+ ", oraInizio=" + oraInizio + ", oraFine=" + oraFine + ", modalita=" + modalita + ", piattaforma="
//				+ piattaforma + ", sala=" + sala + ", partecipazioniDipendenti="
//				+ partecipazioniDipendenti + "]";
//	}

	
	
	//toString:
	//Nome Progetto
	//01/12/2020 13:00 - 01/12/2020 17:00
	//Sala 1 (oppure Microsoft Teams)
	@Override
	public String toString() {
		String temp =getProgettoDiscusso().getNomeProgetto();
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
