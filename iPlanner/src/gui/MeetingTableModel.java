package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entita.Meeting;

public class MeetingTableModel extends AbstractTableModel {
	
	private ArrayList<Meeting> meetingTabella=new ArrayList<Meeting>();
	DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd-MM-yyyy");
	DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
	
	String[] colnames= {"MeetingID", "Data inizio", "Data fine","Orario inizio", "Orario fine", "Modalità", "Piattaforma", "Sala", "Progetto"};

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
		
		return 9;
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
			return meeting.getIdMeeting();
		case 1:
			LocalDate dataInizio=meeting.getDataInizio();
			
			
			return dataInizio.parse(dataInizio.toString(formatDate), formatDate);
		case 2:
			LocalDate dataFine=meeting.getDataFine();
			
			return dataFine.parse(dataFine.toString(formatDate), formatDate);
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
			return meeting.getProgettoDiscusso().getNomeProgetto();

		
		
		}
		return null;
		
		
	}






}
