/*Classe Skill
*Classe che rappresenta le skill lavorative dei dipendenti.
******************************************************************/

package Entità;

public class Skill {

	//ATTRIBUTI
	
	private String nomeSkill;	//nome della skill

	//METODI
	
	//Costruttore totale
	public Skill( String nomeSkill) {
		this.nomeSkill = nomeSkill;	//not null
	}
	
	//Getter e Setter
	public String getNomeSkill() {
		return nomeSkill;
	}

	public void setNomeSkill(String nomeSkill) {
		this.nomeSkill = nomeSkill;
	}
	
	//toString
	@Override
	public String toString() {
		return nomeSkill;
	}
}
