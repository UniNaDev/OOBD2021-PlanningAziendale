import org.joda.time.LocalDate;

public class StarterTest {

	public static void main(String[] args) {
		
		Dipendente dip = new Dipendente();
		
		//Creo un attimo un luogo di nascita e setto in nomeComune manualmente per testare
		LuogoNascita luogoNascita =  new LuogoNascita();
		
		//MODIFICARE QUESTI DATI PER TESTARE IL CODICE FISCALE
		luogoNascita.setNomeComune("Vico Equense");				
		dip.setNome("Davide");
		dip.setCognome("Soldatini");
		dip.setDataNascita(new LocalDate(1999,10,06));
		dip.setSesso('M');
		dip.setLuogoNascita(luogoNascita);
		
		//QUESTO È IL METODO CHE VA A GENERARE EFFETTIVAMENTE IL CF CON I DATI PRENSENTI NEL DIPENDENTE
		String codiceFiscale = dip.GeneraCF();
		
		
		System.out.println(codiceFiscale);
	}

}
