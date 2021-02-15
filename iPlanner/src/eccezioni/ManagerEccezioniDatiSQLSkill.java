/*Implementazione dell'handler di eccezioni SQL più comuni per operazioni con skill nel database.
 *Permette una gestione rapida e chiara delle eccezioni oltre che una rapida costumizzazione
 *dei messaggi di errore e dei consigli se necessario. 
**************************************************************************************************************/

package eccezioni;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ManagerEccezioniDatiSQLSkill implements ManagerEccezioniDatiSQL {
	
	private String messaggioErrore = "";
	private String titoloFinestraErrore = "";
	private String hint = "";
	
	private boolean casoDefault = false;
	
	private final int lunghezzaMaxNomeSkill = 50;
	
	public ManagerEccezioniDatiSQLSkill(SQLException eccezione) {
		switch(eccezione.getSQLState()) {
		//TODO: servono altri casi?
		case VIOLAZIONE_PKEY_UNIQUE:
			setTitoloFinestraErrore("Errore Skill Duplicate");
			setMessaggioErrore("Non è possibile avere due skill identiche nel database.");
			setHint("Verificare che la skill sia già presente nel database.");
			break;
		case  VIOLAZIONE_NOT_NULL:
			setTitoloFinestraErrore("Errore Nome Vuoto");
			setMessaggioErrore("Non è possibile avere una skill senza nome.");
			setHint("Verificare che il nome della skill non sia vuoto.");
			break;
		case VIOLAZIONE_LUNGHEZZA_STRINGA:
			setTitoloFinestraErrore("Errore Nome Skill Troppo Lungo");
			setMessaggioErrore("Il nome della skill è troppo lungo.");
			setHint("Verificare che il nome della skill non abbia più di " + lunghezzaMaxNomeSkill + " caratteri.");
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
	
	public int getLunghezzaMaxNomeSkill() {
		return lunghezzaMaxNomeSkill;
	}
	
	public boolean isDefault() {
		return casoDefault;
	}
	
	@Override
	public void mostraErrore() {
		JOptionPane.showMessageDialog(null,
				messaggioErrore + "\n"+ hint,
				titoloFinestraErrore,
				JOptionPane.ERROR_MESSAGE);
	}
}
