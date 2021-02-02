package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entita.Meeting;

public class MeetingTableModel extends AbstractTableModel {
	
	private ArrayList<Meeting> meetingTabella=new ArrayList<Meeting>();
	
	
	String[] colnames= {"MeetingID", "Data inizio", "Data fine","Orario inizio", "Orario fine", "Modalità", "Piattaforma", "Sala", "Progetto"};

	public void setMeetingTabella(ArrayList<Meeting> meetingTabella) {
		this.meetingTabella = meetingTabella;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return meetingTabella.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return colnames[columnIndex];
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Meeting meeting =meetingTabella.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return meeting.getIdMeeting();
		case 1:
			return meeting.getDataInizio();
		case 2:
			return meeting.getDataFine();
		case 3:
			return meeting.getOraInizio();
		case 4:
			return meeting.getOraFine();
		case 5:
			return meeting.getModalita();
		case 6:
			return meeting.getPiattaforma();
		case 7:
			return meeting.getSala();
		case 8:
			return meeting.getProgettoDiscusso().getNomeProgetto().toString();
		
		
		}
		return null;
		
		
	}




}
