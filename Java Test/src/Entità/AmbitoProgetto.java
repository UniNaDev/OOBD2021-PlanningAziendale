//Classe AmbitoProgetto.
//Rappresenta gli ambiti possibili di un progetto. Sono caratterizzati da un id per distinguerli nel DB e da un nome.
package Entità;

public class AmbitoProgetto {
	
	//ATTRIBUTI
	
	private String nome;	//nome dell'ambito
	
	//METODI
	
	//Costruttore con tutti gli attributi
	public AmbitoProgetto(String nome) {
		this.nome = nome;
	}

	//Getter e setter
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//toString
	@Override
	public String toString() {
		return nome;
	}
		
}
