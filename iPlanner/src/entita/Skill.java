/*Classe Skill
*Classe che rappresenta le skill lavorative dei dipendenti.
*Si distinguono per un id interno al database e da un nome.
******************************************************************/

package entita;

public class Skill {
	private int idSkill;
	private String nomeSkill;

	public Skill(int idSKill, String nomeSkill) {
		this.idSkill = idSKill;
		this.nomeSkill = nomeSkill;
	}

	public String getNomeSkill() {
		return nomeSkill;
	}

	public void setNomeSkill(String nomeSkill) {
		this.nomeSkill = nomeSkill;
	}
	
	public int getIdSkill() {
		return idSkill;
	}
	
	public void setIdSkill(int idSkill) {
		this.idSkill = idSkill;
	}

	@Override
	public String toString() {
		return nomeSkill;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skill other = (Skill) obj;
		if (idSkill != other.idSkill)
			return false;
		if (nomeSkill == null) {
			if (other.nomeSkill != null)
				return false;
		} else if (!nomeSkill.equals(other.nomeSkill))
			return false;
		return true;
	}	
}
