/*Classe Skill
*Classe che rappresenta le skill lavorative dei dipendenti.
*Oltre a un id:int per riconoscerle univocamente all'interno del DB,
*sono costituite da un nome:String.
******************************************************************/

package Entità;

public class Skill {

	//ATTRIBUTI
	
	int id;	//IDSkill che ha nel DB
	
	String nomeSkill;	//nome della skill

	//METODI
	
	//Costruttore banale
	public Skill(int id, String nomeSkill) {
		this.id = id;
		this.nomeSkill = nomeSkill;
	}
	
	//Vari getters e setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeSkill() {
		return nomeSkill;
	}

	public void setNomeSkill(String nomeSkill) {
		this.nomeSkill = nomeSkill;
	}
	
	//ToString override
	@Override
	public String toString() {
		return "Skill " + nomeSkill;
	}
}
