//Classe AmbitoProgetto.
//Rappresenta gli ambiti possibili di un progetto. Sono caratterizzati da un id per distinguerli nel DB e da un nome.
package Entità;

public class AmbitoProgetto {
	
	//ATTRIBUTI
	
	private int id;	//id per distinguerli
	private String nome;	//nome dell'ambito
	
	//METODI
	
	//Costruttore con tutti gli attributi
	public AmbitoProgetto(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	//Getter e setter
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	//Manca setId perchè l'id è gestito dal DBMS con una sequence
	public int getId() {
		return id;
	}

	
	//toString
	@Override
	public String toString() {
		return nome;
	}
		
}
