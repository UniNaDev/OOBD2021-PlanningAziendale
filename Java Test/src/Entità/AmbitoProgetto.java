//Classe AmbitoProgetto.
//Rappresenta gli ambiti possibili di un progetto. Sono caratterizzati da un id per distinguerli nel DB e da un nome.
package Entità;

public class AmbitoProgetto {
	
	//ATTRIBUTI
	
	private int idAmbito;	//id dell'ambito
	private String nome;	//nome dell'ambito
	
	//METODI
	
	//Costruttore con gli attributi essenziali per un nuovo oggetto
	public AmbitoProgetto(String nome) {
		this.nome = nome;
	}
	
	//Costruttore con tutti gli attributi per le query
	public AmbitoProgetto(int idAmbito, String nome) {
		this.idAmbito = idAmbito;
		this.nome = nome;
	}

	//Getter e setter
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getIdAmbito() {
		return idAmbito;
	}

	public void setIdAmbito(int idAmbito) {
		this.idAmbito = idAmbito;
	}

	//toString
	@Override
	public String toString() {
		return nome;
	}
		
}
