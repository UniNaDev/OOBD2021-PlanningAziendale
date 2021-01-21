/*Classe Skill
*Classe che rappresenta le skill lavorative dei dipendenti.
******************************************************************/

package entita;

public class Skill {

	//ATTRIBUTI
	//----------------------------------------
	private int idSkill;	//id della skill
	private String nomeSkill;	//nome della skill

	//METODI
	//----------------------------------------
	
	//Costruttore parziale
	public Skill(String nomeSkill) {
		this.nomeSkill = nomeSkill;
	}
	
	public Skill(int idSKill, String nomeSkill) {
		this.idSkill = idSKill;
		this.nomeSkill = nomeSkill;
	}
	
	//Getter e Setter
	public String getNomeSkill() {
		return nomeSkill;
	}

	public void setNomeSkill(String nomeSkill) {
		this.nomeSkill = nomeSkill;
	}
	
	public int getIdSkill() {
		return idSkill;
	}

	//toString
	@Override
	public String toString() {
		return nomeSkill;
	}
}
