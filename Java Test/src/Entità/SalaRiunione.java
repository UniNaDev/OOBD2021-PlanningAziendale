//Classe SalaRiunione.
/*Classe che rappresenta le sale riunioni delle sedi aziendali.
*Ognuna di loro ha un codice identificativo usato in azienda, una capienza massima,
*un indirizzo della sede e il piano in cui si trova. Ad essa possono essere associati
*i meeting che avvengono al suo interno.*/

package Entità;

public class SalaRiunione {
	
	//ATTRIBUTI
	
	private String codSala;	//codice sala
	private int cap;	//capienza della sala
	private String indirizzo;	//indirizzo della sede dove si trova
	private int piano;	//piano in cui si trova la sala
	
	//METODI
	
	//Costruttore della sala con tutti gli attributi
	public SalaRiunione(String codSala, int cap, String indirizzo, int piano) {
		this.codSala = codSala;
		this.cap = cap;
		this.indirizzo = indirizzo;
		this.piano = piano;
	}

	//Getter e setter di ogni attributo
	public String getCodSala() {
		return codSala;
	}

	public void setCodSala(String codSala) {
		this.codSala = codSala;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getPiano() {
		return piano;
	}

	public void setPiano(int piano) {
		this.piano = piano;
	}

	//Metodo ToString
	@Override
	public String toString() {
		return codSala + " (" + cap + ")";
	}
	
	
}
