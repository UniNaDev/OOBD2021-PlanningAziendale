//Classe SalaRiunione.
/*Classe che rappresenta le sale riunioni delle sedi aziendali.
*Ognuna di loro ha un codice identificativo usato in azienda, una capienza massima,
*un indirizzo della sede e il piano in cui si trova. Ad essa possono essere associati
*i meeting che avvengono al suo interno.*/

package entita;

import java.util.ArrayList;

public class SalaRiunione {
	private String codiceSala;
	private int capienza;
	private String indirizzoSede;
	private int piano;
	
	private ArrayList<Meeting> meetings = new ArrayList<Meeting>();

	public SalaRiunione(String codiceSala, int capienza, String indirizzoSede, int piano) {
		this.codiceSala = codiceSala;
		this.capienza = capienza;
		this.indirizzoSede = indirizzoSede;
		this.piano = piano;
	}

	public String getCodiceSala() {
		return codiceSala;
	}

	public void setCodiceSala(String codiceSala) {
		this.codiceSala = codiceSala;
	}

	public int getCapienza() {
		return capienza;
	}

	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}

	public String getIndirizzoSede() {
		return indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public int getPiano() {
		return piano;
	}

	public void setPiano(int piano) {
		this.piano = piano;
	}

	//Formato toString:
	//CodiceSala (Capienza)
	@Override
	public String toString() {
		return codiceSala + " (" + capienza + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaRiunione other = (SalaRiunione) obj;
		if (codiceSala == null) {
			if (other.codiceSala != null)
				return false;
		} else if (!codiceSala.equals(other.codiceSala))
			return false;
		return true;
	}
}
