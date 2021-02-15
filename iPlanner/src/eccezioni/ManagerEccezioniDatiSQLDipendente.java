/*Implementazione dell'handler di eccezioni SQL più comuni per operazioni con i dipendenti nel database.
 *Permette una gestione rapida e chiara delle eccezioni oltre che una rapida costumizzazione
 *dei messaggi di errore e dei consigli se necessario. 
*******************************************************************************************************/

package eccezioni;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ManagerEccezioniDatiSQLDipendente implements ManagerEccezioniDatiSQL {

	private String messaggioErrore = "";
	private String titoloFinestraErrore = "";
	private String hint = "";
	
	private boolean casoDefault = false;
	
	private final int lunghezzaMaxNomeCognome = 30;
	private final int lunghezzaMaxIndirizzoEmail = 100;
	private final int lunghezzaNumeroTelefonico = 10;
	private final int lunghezzaMaxPassword = 50;
	
	public ManagerEccezioniDatiSQLDipendente(SQLException eccezione) {
		switch(eccezione.getSQLState()) {
		//TODO: verificare se ci sono altre eccezioni non contemplati
		case VIOLAZIONE_PKEY_UNIQUE:
			setTitoloFinestraErrore("Errore Valori Duplicati");
			setMessaggioErrore("Ci sono valori duplicati.");
			setHint("Verificare che il dipendente non esista già\noppure che un altro dipendente non abbia la stessa email\no lo stesso numero di cellulare.");
			break;
		case VIOLAZIONE_NOT_NULL:
			setTitoloFinestraErrore("Errore Valori Nulli");
			setMessaggioErrore("Ci sono valori nulli.");
			setHint("Verificare che i dati anagrafici, email, password\ne salario non siano nulli.");
			break;
		case VIOLAZIONE_VINCOLI_TABELLA:
			setTitoloFinestraErrore("Errore Valori Non Validi");
			setMessaggioErrore("Ci sono valori non validi.");
			setHint("Verificare che:\n"
					+ "1) L'email sia del formato corretto.\n"
					+ "2) Nome e cognome non contengano numeri.\n"
					+ "3) Telefono di casa e cellulare non contengono lettere.\n"
					+ "4) Il dipendente sia maggiorenne.\n"
					+ "5) Il salario stabilito sia un valore positivo.");
			break;
		case VIOLAZIONE_LUNGHEZZA_STRINGA:
			setTitoloFinestraErrore("Errore Testo Troppo Lungo");
			setMessaggioErrore("Ci sono valori testuali troppo lunghi.");
			setHint("Verificare che:\n"
					+ "1) Nome e cognome non contengano più di " + lunghezzaMaxNomeCognome + " caratteri.\n"
					+ "2) Indirizzo e email non contengano più di " + lunghezzaMaxIndirizzoEmail + " caratteri.\n"
					+ "3) Numero di telefono e cellulare sono esattamente di " + lunghezzaNumeroTelefonico + " numeri.\n"
					+ "4) La password non superi i " + lunghezzaMaxPassword + " caratteri.");
			break;
		case VIOLAZIONE_DATA_INESISTENTE:
			setTitoloFinestraErrore("Errore Data Inesistente");
			setMessaggioErrore("La data di nascita è errata.");
			setHint("Selezionare una data di nascita esistente.");
			break;
		default:
			setTitoloFinestraErrore("Errore #" + eccezione.getSQLState());
			setMessaggioErrore(eccezione.getMessage());
			setHint("Verificare che il programma sia aggiornato \noppure contattare uno sviluppatore.");
			casoDefault = true;
		}
	}

	public void setMessaggioErrore(String messaggio) {
		this.messaggioErrore = messaggio;
	}

	public void setTitoloFinestraErrore(String titolo) {
		this.titoloFinestraErrore = titolo;
	}
	
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public boolean isDefault() {
		return casoDefault;
	}
	
	public int getLunghezzaMaxNomeCognome() {
		return lunghezzaMaxNomeCognome;
	}
	
	public int getLunghezzaMaxIndirizzoEmail() {
		return lunghezzaMaxIndirizzoEmail;
	}
	
	public int getLunghezzaNumeroTelefonico() {
		return lunghezzaNumeroTelefonico;
	}

	public int getLunghezzaMaxPassword() {
		return lunghezzaMaxPassword;
	}
	
	@Override
	public void mostraErrore() {
		JOptionPane.showMessageDialog(null,
				messaggioErrore + "\n"+ hint,
				titoloFinestraErrore,
				JOptionPane.ERROR_MESSAGE);
	}
}
