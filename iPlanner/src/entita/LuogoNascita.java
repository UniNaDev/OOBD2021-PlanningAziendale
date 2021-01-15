/*Classe LuogoNascita.
*Classe che rappresenta il luogo di nascita dei dipendenti aziendali.
*La classe è composta da un codice del comune di 4 caratteri (utile alla generazione del CF del dipendente) codiceComune:String,
*dal nome del comune nomeComune:String e  dal nome della provincia di appartenenza nomeProvincia:String.
*******************************************************************************************************************************/

package entita;

public class LuogoNascita {

	//ATTRIBUTI
	
	private String codiceComune;	//codice di 4 caratteri del comune (es: F839)
	private String nomeComune;		//nome del comune (es: Napoli)
	private String nomeProvincia;	//nome della provincia di appartenenza (es: Napoli)
	
	//METODI
	
	//Costruttore totale
	public LuogoNascita(String codiceComune, String nomeComune, String nomeProvincia) {
		this.codiceComune = codiceComune;	//primary Key
		this.nomeComune = nomeComune;	//not null
		this.nomeProvincia = nomeProvincia;	//not null
	}

	//Getters banali per ogni attributo
	public String getCodiceComune() {
		return codiceComune;
	}

	public String getNomeComune() {
		return nomeComune;
	}

	public String getNomeProvincia() {
		return nomeProvincia;
	}
	
	//Metodo toString
	@Override
	public String toString() {
		return nomeComune + ", " + nomeProvincia;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LuogoNascita other = (LuogoNascita) obj;
		if (codiceComune == null) {
			if (other.codiceComune != null)
				return false;
		} else if (!codiceComune.equals(other.codiceComune))
			return false;
		return true;
	}
	
	
}
