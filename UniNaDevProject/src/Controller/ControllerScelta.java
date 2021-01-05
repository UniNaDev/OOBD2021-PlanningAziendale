package Controller;

import GUI.*;

public class ControllerScelta {

	iPlanner iPlannerFrame;
	NuovoDipendente nuovoDipendenteFrame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ControllerScelta controllerScelta=new ControllerScelta();
		
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
		
		ControllerAccesso controller=new ControllerAccesso();
		controller.createLoginFrame();
		
	}
	
	public void reLinkToLoginFrame() {
		
		ControllerAccesso controller=new ControllerAccesso();
		controller.createLoginFrame();
		
		nuovoDipendenteFrame.setVisible(false);
		
	}
	
	public void annulla() {
		nuovoDipendenteFrame.setVisible(false);
		iPlannerFrame.setVisible(true);
	}

	public void reLinkToIplannerFrame() {
		// TODO Auto-generated method stub
		iPlannerFrame.setVisible(true);
	}

	
}
