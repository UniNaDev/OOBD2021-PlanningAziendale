package Controller;

import GUI.Project;

public class ControllerProgetto {

	Project projectFrame;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerProgetto controllerProgetto=new ControllerProgetto();
	}

	
	public void createProjectFrame() {
		// TODO Auto-generated method stub
		projectFrame=new Project();
		projectFrame.setVisible(true);
	}
	
	
}
