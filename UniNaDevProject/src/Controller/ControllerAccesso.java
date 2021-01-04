package Controller;

import GUI.*;

public class ControllerAccesso {
	
	Login loginFrame;
	User userFrame;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerAccesso controller2= new ControllerAccesso();
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
		userFrame=new User();
		userFrame.setVisible(true);
		loginFrame.SvuotaCampi();
	}


//	public void viewAccount() {
//		// TODO Auto-generated method stub
//		userProfileFrame=new UserProfile();
//		userProfileFrame.setVisible(true);
//	}
	

}
