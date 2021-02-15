/*Implementazione dell'handler di eccezioni SQL più comuni per operazioni con gli ambiti dei progetti nel database.
 *Permette una gestione rapida e chiara delle eccezioni oltre che una rapida costumizzazione
 *dei messaggi di errore e dei consigli se necessario. 
*****************************************************************************************************************/

package eccezioni;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ManagerEccezioniDatiSQLAmbito implements  ManagerEccezioniDatiSQL {

	private String messaggioErrore = "";
	private String titoloFinestraErrore = "";
	private String hint = "";
	
	private boolean casoDefault = false;
	
	private final int lunghezzaMaxNome = 20;
	
	public ManagerEccezioniDatiSQLAmbito(SQLException eccezione) {
		switch(eccezione.getSQLState()) {
		//TODO: controllare se ci siano altre eccezioni possibili
		case VIOLAZIONE_PKEY_UNIQUE:
			setTitoloFinestraErrore("Errore Ambiti Duplicati");
			setMessaggioErrore("Non possono esistere ambiti duplicati.");
			setHint("Verificare che il nome dell'ambito non esista già.");
			break;
		case VIOLAZIONE_NOT_NULL:
			setTitoloFinestraErrore("Errore Valori Nulli");
			setMessaggioErrore("Non possono esserci valori nulli.");
			setHint("Verificare che il nome dell'ambito non sia vuoto.");
			break;
		case VIOLAZIONE_VINCOLI_TABELLA:
			setTitoloFinestraErrore("Errore Valori Non Validi");
			setMessaggioErrore("Errori non validi.");
			setHint("Verificare che il nome dell'ambito non contenga numeri.");
			break;
		case VIOLAZIONE_LUNGHEZZA_STRINGA:
			setTitoloFinestraErrore("Errore Nome Troppo Lungo");
			setMessaggioErrore("Verificare che il nome dell'ambito non contenga più di " + lunghezzaMaxNome + " caratteri.");
			setHint("Verificare che il programma sia aggiornato \\noppure contattare uno sviluppatore.");
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
	
	public int getLunghezzaMaxNome() {
		return lunghezzaMaxNome;
	}

	@Override
	public void mostraErrore() {
		JOptionPane.showMessageDialog(null,
				messaggioErrore + "\n"+ hint,
				titoloFinestraErrore,
				JOptionPane.ERROR_MESSAGE);
	}
}
