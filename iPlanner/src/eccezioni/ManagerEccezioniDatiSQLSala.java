/*Implementazione dell'handler di eccezioni SQL più comuni in caso di INSERT UPDATE o DELETE di sale nel database.
 *Permette una gestione rapida e chiara delle eccezioni oltre che una rapida costumizzazione
 *dei messaggi di errore e dei consigli se necessario. 
*****************************************************************************************************************/

package eccezioni;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ManagerEccezioniDatiSQLSala implements ManagerEccezioniDatiSQL {
	
	private String messaggioErrore = "";
	private String titoloFinestraErrore = "";
	private String hint = "";
	
	public ManagerEccezioniDatiSQLSala(SQLException eccezione) {
		switch(eccezione.getSQLState()) {
		case VIOLAZIONE_PKEY_UNIQUE:
			setTitoloFinestraErrore("Errore Sale Identiche");
			setMessaggioErrore("Non possono esistere sale duplicate.");
			setHint("Controllare il codice della sala.");
			break;
		case VIOLAZIONE_NOT_NULL:
			setTitoloFinestraErrore("Errore Valori Nulli");
			setMessaggioErrore("Alcuni valori obbligatori sono nulli.");
			setHint("Controllare il codice, la capienza, l'indirizzo e il piano della sala.");
			break;
		case VIOLAZIONE_LUNGHEZZA_STRINGA:
			setTitoloFinestraErrore("Errore Valori Troppo Lunghi");
			setMessaggioErrore("Alcuni valori sono troppo lunghi.");
			setHint("Controllare che il codice della sala non superi i 10 caratteri e che il suo indirizzo non superi i 50 caratteri.");
			break;
		case VIOLAZIONE_VINCOLI_TABELLA:
			setTitoloFinestraErrore("Errore Valori Errati");
			setMessaggioErrore("Alcuni valori sono errati.");
			setHint("Controllare che piano e capienza non siano valori negativi.");
			break;
		//TODO: vincolo accavallamento sala
		default:
			setTitoloFinestraErrore("Errore #" + eccezione.getSQLState());
			setMessaggioErrore(eccezione.getMessage());
			setHint("Contattare uno sviluppatore.");
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

	@Override
	public void mostraErrore() {
		JOptionPane.showMessageDialog(null,
				messaggioErrore + "\n"+ hint,
				titoloFinestraErrore,
				JOptionPane.ERROR_MESSAGE);
	}
}
