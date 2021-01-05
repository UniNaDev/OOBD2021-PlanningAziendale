package Controller;

import GUI.*;


public class ControllerProgetto {

	Project projectFrame;
	InsertProject insertProjectFrame;
	ModifyProject modifyProjectFrame;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerProgetto controllerProgetto=new ControllerProgetto();
	}

	
	public void createProjectFrame() {
		// TODO Auto-generated method stub
		projectFrame=new Project(this);
		projectFrame.setVisible(true);
	}


	public void createInsertProjectFrame() {
		// TODO Auto-generated method stub
		insertProjectFrame=new InsertProject();
		insertProjectFrame.setVisible(true);
	}
	
	public void createModifyProjectFrame() {
		modifyProjectFrame=new ModifyProject();
		modifyProjectFrame.setVisible(true);
	}
}
