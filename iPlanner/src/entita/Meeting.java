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
	private int idMeeting;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	private String modalita;
	private String piattaforma;
	private SalaRiunione sala;
	
	private Progetto releativoA;
	private ArrayList<Dipendente> partecipazioniDipendenti = new ArrayList<Dipendente>();
	private ArrayList<PartecipazioneMeeting>partecipantiAlMeeting= new ArrayList<PartecipazioneMeeting>();

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

	//TODO: probabilmente inutile (basta usare l'altro e porre l'id = null)
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


	public ArrayList<PartecipazioneMeeting> getPartecipantiAlMeeting() {
		return partecipantiAlMeeting;
	}


	public void setPartecipantiAlMeeting(ArrayList<PartecipazioneMeeting> partecipantiAlMeeting) {
		this.partecipantiAlMeeting = partecipantiAlMeeting;
	}

//	Formato toString:
//	Nome Progetto
//	DataInizio (dd/MM/yyyy) OraInizio (HH:mm) - DataFine (dd/MM/yyyy) OraFine (HH:mm)
//	Sala/Piattaforma
	@Override
	public String toString() {
		String meeting = getProgettoDiscusso().getNomeProgetto();
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
		meeting += "\n" + dataInizio.toString(formatDate) + " " + oraInizio.toString(formatHour) + " - " + dataFine.toString(formatDate) + " " + oraFine.toString(formatHour);
		if (modalita.equals("Telematico"))
			meeting += "\n" + piattaforma;
		else
			meeting += "\n" + sala.getCodiceSala();
		return meeting;
	}
	
	


	
}
