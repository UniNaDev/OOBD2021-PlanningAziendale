package Controller;

import GUI.User;
import GUI.UserProfile;

public class ControllerGestioneProfilo {

	User userFrame;
	UserProfile userProfileFrame;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerGestioneProfilo controllerGestioneProfilo=new ControllerGestioneProfilo();
	}

	public void createUserFrame() {
		
		userFrame=new User(this);
		userFrame.setVisible(true);
		
	}
	
	public void viewAccount() {
		// TODO Auto-generated method stub
		userProfileFrame=new UserProfile();
		userProfileFrame.setVisible(true);
	}

	public void logout() {
		// TODO Auto-generated method stub
		
		ControllerAccesso controller= new ControllerAccesso();
		controller.createLoginFrame();
		userFrame.setVisible(false);
	}

	public void linkToProjectFrame() {
		
		ControllerProgetto controller= new ControllerProgetto();
		controller.createProjectFrame();
	}
	
	public void linkToMeetingFrame() {
		
		ControllerMeeting controller= new ControllerMeeting();
		controller.createMeetingFrame();
		
	}
}
