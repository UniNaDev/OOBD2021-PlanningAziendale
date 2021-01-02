package Controller;
import java.awt.event.MouseEvent;

import GUI.*;

public class ControllerScelta {

	
	iPlanner iPlannerFrame;
	NuovoDipendente nuovoDipendenteFrame;
	Login loginFrame;
	User userFrame;
	UserProfile userProfileFrame;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ControllerScelta controller=new ControllerScelta();
		
	}

	public ControllerScelta() {
		
		iPlannerFrame=new iPlanner(this);
		iPlannerFrame.setVisible(true);
	}
	
	public void LinkToLoginFrame() {
		
		iPlannerFrame.setVisible(false);
		
		loginFrame=new Login(this);
		loginFrame.setVisible(true);
		
	}
	
	public void LinkToCreationFrame() {
		
	  iPlannerFrame.setVisible(false);
	  
	  nuovoDipendenteFrame= new NuovoDipendente(this);
	  nuovoDipendenteFrame.setVisible(true);
	  
		
	}
	
	public void annulla() {
		
		nuovoDipendenteFrame.setVisible(false);
		iPlannerFrame.setVisible(true);
		
		
	}

	public void verificaCredenziali(String user, String pass) {
	
		//TODO Se le credenziali sono corrette esegui l'accesso altrimenti JDialog utente non presente o dati incorretti
		loginFrame.setVisible(false);
		userFrame=new User(this);
		userFrame.setVisible(true);
		
	}

	public void viewAccount() {
		// TODO Auto-generated method stub
		
		userProfileFrame=new UserProfile();
		userProfileFrame.setVisible(true);
		
	}

	
	
	
	
}
