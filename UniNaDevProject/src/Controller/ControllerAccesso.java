package Controller;

import GUI.*;

public class ControllerAccesso {
	
	Login loginFrame;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerAccesso controllerAccesso= new ControllerAccesso();
	}

	public void createLoginFrame()
	{
		
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}

	public void verificaCredenziali(String user, String pass) {
		// TODO Auto-generated method stub
		
		//TODO Se le credenziali sono corrette esegui l'accesso altrimenti JDialog utente non presente o dati incorretti
		loginFrame.setVisible(false);
		ControllerGestioneProfilo controller=new ControllerGestioneProfilo();
		controller.createUserFrame();
		
		loginFrame.SvuotaCampi();
	}

	public void annulla() {
		// TODO Auto-generated method stub
		loginFrame.setVisible(false);
		ControllerScelta controller=new ControllerScelta();
		controller.reLinkToIplannerFrame();
	}





}
