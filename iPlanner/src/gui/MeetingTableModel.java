package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entita.Meeting;

public class MeetingTableModel extends AbstractTableModel {
	
	private ArrayList<Meeting> meetingTabella=new ArrayList<Meeting>();

	DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");

	
	String[] colnames= {"Data inizio", "Data fine","Orario inizio", "Orario fine", "Sala/Piattaforma", "Progetto"};

	public void setMeetingTabella(ArrayList<Meeting> meetingTabella) {
		this.meetingTabella = meetingTabella;
	}
	
	

	public ArrayList<Meeting> getMeetingTabella() {
		return meetingTabella;
	}



	@Override
	public int getRowCount() {
		
		return meetingTabella.size();
	}

	@Override
	public int getColumnCount() {
		
		return colnames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		
		return colnames[columnIndex];
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Meeting meeting =meetingTabella.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return meeting.getDataInizio();
		case 1:
			return meeting.getDataFine();
		case 2:
			return meeting.getOraInizio();
		case 3:
			return meeting.getOraFine();
		case 4:
			if (meeting.getModalita().equals("Telematico"))
				return meeting.getPiattaforma();
			else
				return meeting.getSala();
		case 5:
			return meeting.getProgettoDiscusso().getNomeProgetto();	
		}
		return null;	
	}
	
	public Meeting getSelected(int rowIndex) {
		return meetingTabella.get(rowIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0,columnIndex).getClass();
	}
	
	
}
