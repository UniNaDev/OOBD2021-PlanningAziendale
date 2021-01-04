package Controller;

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
	

	public void linkToCreationFrame() {
		
	  iPlannerFrame.setVisible(false);
	  
	  nuovoDipendenteFrame= new NuovoDipendente(this);
	  nuovoDipendenteFrame.setVisible(true);
	   
	
	
	}
	
	public void linkToLoginFrame() {
		
		iPlannerFrame.setVisible(false);
		
		ControllerAccesso controll=new ControllerAccesso();
		controll.createLoginFrame();
		

		
	}
	
	public void reLinkToLoginFrame() {
		
		ControllerAccesso controll=new ControllerAccesso();
		controll.createLoginFrame();
		
		nuovoDipendenteFrame.setVisible(false);
		
	}
	
	public void annulla() {
		nuovoDipendenteFrame.setVisible(false);
		iPlannerFrame.setVisible(true);
	}




//	public void viewAccount() {
//	userProfileFrame=new UserProfile();
//	userProfileFrame.setVisible(true);
//	}
//	
	
	
	
}
