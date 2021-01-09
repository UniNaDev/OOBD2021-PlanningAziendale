package Controller;

import GUI.*;

public class ControllerScelta {

	iPlanner iPlannerFrame;
	NuovoDipendente nuovoDipendenteFrame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ControllerScelta controllerScelta=new ControllerScelta();
		
	}

	//Costruttore che crea il frame iniziale
	public ControllerScelta() {
		
		iPlannerFrame=new iPlanner(this);
		iPlannerFrame.setVisible(true);
		
	}
	
	//Metodo che indirizza al frame di registrazione
	public void linkToCreationFrame() {
		
	  iPlannerFrame.setVisible(false);
	  nuovoDipendenteFrame= new NuovoDipendente(this);
	  nuovoDipendenteFrame.setVisible(true);
	  
	}
	
	//Metodo che indirizza al Login
	public void linkToLoginFrame() {
		
		iPlannerFrame.setVisible(false);
		ControllerAccesso controller=new ControllerAccesso();
		controller.createLoginFrame();
		
	}
	
	//Metodo che reindirizza al loginFrame dopo che è stato creato l'account
	public void reLinkToLoginFrame() {
		
		ControllerAccesso controller=new ControllerAccesso();
		controller.createLoginFrame();
		nuovoDipendenteFrame.setVisible(false);
		
	}
	
	//Metodo che reindirizza al loginFrame quando viene annullata la creazione dell'account
	public void annulla() {
		
		nuovoDipendenteFrame.setVisible(false);
		iPlannerFrame.setVisible(true);
		
	}

	//Metodo che reindirizza al Frame iniziale(Viene utilizzato dalla finestra di login) 
	public void reLinkToIplannerFrame() {
	
		iPlannerFrame.setVisible(true);
		
	}

	
}
