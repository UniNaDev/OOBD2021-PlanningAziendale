import org.joda.time.LocalDate;

public class Dipendente {

	//ATTRIBUTI
	private String cf;
	private String nome;
	private String cognome;
	private char sesso;
	private LocalDate dataNascita;
	private LuogoNascita luogoNascita;
	private String indrizzo;
	private String email;
	private String telefonoCasa;
	private String cellulare;
	private float salario;
	private String password;
	private float valutazione;
	
	//METODI
	public String GeneraCF()
	{
		CFgenerator cfGenerator = new CFgenerator();
	
		//Compone le prime 15 cifre del codice fiscale utilizzando i metodi di CFgenerator
		String cf15 = cfGenerator.FirstSixCharacters(this.getNome(), this.getCognome()) +
					cfGenerator.SeventhToEleventhChar(getDataNascita(), getSesso()) +
					cfGenerator.TwelfthToFifteenthChar(luogoNascita.getNomeComune());
		
		//Genera il carattere di controllo a partire dalle prime 15 cifre appena generate
		char carattereControllo = cfGenerator.LastChar(cf15);
		
		//Aggiunge alle 15 cifre il carattere di controllo finale
		String codiceFiscale = cf15 + carattereControllo;
		
		return codiceFiscale;
	}
	
	
	//GETTERS E SETTERS
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public char getSesso() {
		return sesso;
	}
	public void setSesso(char sesso) {
		this.sesso = sesso;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public LuogoNascita getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(LuogoNascita luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getIndrizzo() {
		return indrizzo;
	}
	public void setIndrizzo(String indrizzo) {
		this.indrizzo = indrizzo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefonoCasa() {
		return telefonoCasa;
	}
	public void setTelefonoCasa(String telefonoCasa) {
		this.telefonoCasa = telefonoCasa;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public float getSalario() {
		return salario;
	}
	public void setSalario(float salario) {
		this.salario = salario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public float getValutazione() {
		return valutazione;
	}
	public void setValutazione(float valutazione) {
		this.valutazione = valutazione;
	}

	
}
