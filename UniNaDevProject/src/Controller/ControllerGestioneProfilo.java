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
	
	
}
