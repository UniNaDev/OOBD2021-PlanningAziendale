/*Classe LuogoNascita.
*Classe che rappresenta il luogo di nascita dei dipendenti aziendali.
*La classe è composta da un codice del comune di 4 caratteri (utile alla generazione del CF del dipendente) codiceComune:String,
*dal nome del comune nomeComune:String e  dal nome della provincia di appartenenza nomeProvincia:String.
*******************************************************************************************************************************/

package Entità;

public class LuogoNascita {

	//ATTRIBUTI
	
	private String codiceComune;	//codice di 4 caratteri del comune (es: F839)
	private String nomeComune;		//nome del comune (es: Napoli)
	private String nomeProvincia;	//nome della provincia di appartenenza (es: Napoli)
	
	//METODI
	
	//Costruttore banale con tutti gli attributi come parametri
	public LuogoNascita(String codiceComune, String nomeComune, String nomeProvincia) {
		this.codiceComune = codiceComune;
		this.nomeComune = nomeComune;
		this.nomeProvincia = nomeProvincia;
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
	
	//Metodo ToString
	@Override
	public String toString() {
		return "[" + codiceComune + "] " + nomeComune  + " (" + nomeProvincia + ")";
	}
}
