package Controller;

import java.awt.Window;

import Viste.ErrorWindow;

public class ControllerErrori {

	private ErrorWindow errorWindow;	//finestra per messaggi di errore
	
	private String errore;
	private boolean fatale;

	
	//Costruttore
	public ControllerErrori(String errore, boolean fatale) {
		this.errore = errore;
		this.fatale = fatale;
		this.errorWindow = new ErrorWindow(this);	//inizializza la finestra di errore
		errorWindow.setVisible(true);	//mostra la finestra di errore
		errorWindow.setAlwaysOnTop(true); //pone la finestra di errore sempre sopra
		
		if (fatale)//rende le finestre visibili sottostanti non interagibili
			for (Window w: Window.getWindows())
				if (w.isShowing() && !w.equals(errorWindow))
					w.setEnabled(false);
	}

	//Getter
	public String getErrore() {
		return errore;
	}

	public boolean isFatale() {
		return fatale;
	}
}
