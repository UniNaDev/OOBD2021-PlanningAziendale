package Controller;

import GUI.*;


public class ControllerMeeting {

	Meeting meetingFrame;
	InsertMeeting insertMeetingFrame;
	ModifyMeeting modifyMeetingFrame;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void createMeetingFrame() {
		// TODO Auto-generated method stub
		meetingFrame=new Meeting(this);
		meetingFrame.setVisible(true);
	}

	public void createInsertMeetingFrame() {
		insertMeetingFrame= new InsertMeeting();
		
		insertMeetingFrame.setVisible(true);
		
		
	}
	
	public void createModifyMeetingFrame() {
		modifyMeetingFrame= new ModifyMeeting();
		
		modifyMeetingFrame.setVisible(true);
		
		
	}
}
