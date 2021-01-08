package Controller;

import GUI.*;


public class ControllerMeeting {

	Meeting meetingFrame;
	GestioneMeeting insertMeetingFrame;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void createMeetingFrame() {
		// TODO Auto-generated method stub
		meetingFrame=new Meeting(this);
		meetingFrame.setVisible(true);
	}

	public void createInsertMeetingFrame() {
		insertMeetingFrame= new GestioneMeeting();
		
		insertMeetingFrame.setVisible(true);
		
		
	}
	

}
