//Classe AmbitoProgetto.
//Rappresenta gli ambiti possibili di un progetto. Sono caratterizzati da un id per distinguerli nel DB e da un nome.

package entita;

public class AmbitoProgetto {
	private int idAmbito;
	private String nomeAmbito;
	
	public AmbitoProgetto(String nome) {
		this.nomeAmbito = nome;
	}
	
	public AmbitoProgetto(int idAmbito, String nome) {
		this.idAmbito = idAmbito;
		this.nomeAmbito = nome;
	}

	public String getNomeAmbito() {
		return nomeAmbito;
	}

	public void setNomeAmbito(String nomeAmbito) {
		this.nomeAmbito = nomeAmbito;
	}
	
	public int getIdAmbito() {
		return idAmbito;
	}

	public void setIdAmbito(int idAmbito) {
		this.idAmbito = idAmbito;
	}

	@Override
	public String toString() {
		return nomeAmbito;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmbitoProgetto other = (AmbitoProgetto) obj;
		if (idAmbito != other.idAmbito)
			return false;
		if (nomeAmbito == null) {
			if (other.nomeAmbito != null)
				return false;
		} else if (!nomeAmbito.equals(other.nomeAmbito))
			return false;
		return true;
	}
}
