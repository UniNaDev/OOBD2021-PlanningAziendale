/*Classe LuogoNascita.
*Classe che rappresenta il luogo di nascita dei dipendenti aziendali.
*La classe è composta da un codice del comune di 4 caratteri (utile alla generazione del CF del dipendente) codiceComune:String,
*dal nome del comune nomeComune:String e  dal nome della provincia di appartenenza nomeProvincia:String.
*******************************************************************************************************************************/

package entita;

public class LuogoNascita {
	private String codiceComune;
	private String nomeComune;
	private String nomeProvincia;
	
	public LuogoNascita(String codiceComune, String nomeComune, String nomeProvincia) {
		this.codiceComune = codiceComune;
		this.nomeComune = nomeComune;
		this.nomeProvincia = nomeProvincia;
	}

	public String getCodiceComune() {
		return codiceComune;
	}

	public String getNomeComune() {
		return nomeComune;
	}

	public String getNomeProvincia() {
		return nomeProvincia;
	}
	
	
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
