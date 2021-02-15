/*Implementazione dell'handler di eccezioni SQL più comuni per operazioni con i progetti nel database.
 *Permette una gestione rapida e chiara delle eccezioni oltre che una rapida costumizzazione
 *dei messaggi di errore e dei consigli se necessario. 
*****************************************************************************************************************/

package eccezioni;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ManagerEccezioniDatiSQLProgetto implements ManagerEccezioniDatiSQL {

	private String messaggioErrore = "";
	private String titoloFinestraErrore = "";
	private String hint = "";
	
	private boolean casoDefault = false;
	
//	private final int lunghezzaMaxCodSala = 10;
//	private final int lunghezzaMaxIndirizzo = 50;
	
	public ManagerEccezioniDatiSQLProgetto(SQLException eccezione) {
		switch(eccezione.getSQLState()) {
		//TODO: implementare altri casi
		
		default:
			setTitoloFinestraErrore("Errore #" + eccezione.getSQLState());
			setMessaggioErrore(eccezione.getMessage());
			setHint("Verificate che il programma sia aggiornato \\noppure contattare uno sviluppatore.");
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
	
//	public int getLunghezzaMaxCodSala() {
//		return lunghezzaMaxCodSala;
//	}
//	
//	public int getLunghezzaMaxIndirizzo() {
//		return lunghezzaMaxIndirizzo;
//	}

	@Override
	public void mostraErrore() {
		JOptionPane.showMessageDialog(null,
				messaggioErrore + "\n"+ hint,
				titoloFinestraErrore,
				JOptionPane.ERROR_MESSAGE);
	}
}