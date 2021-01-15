package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;

import org.joda.time.LocalDate;

import entita.Meeting;

public class MeetingListRenderer implements ListCellRenderer<Meeting> {

	  	private JPanel p;
	    private JTextArea ta;
	
	public MeetingListRenderer() {
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        // text
        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("Consolas", Font.PLAIN, 15));
        ta.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(ta, BorderLayout.CENTER);
    }
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Meeting> list, Meeting meeting, int index,
	        boolean isSelected, boolean cellHasFocus) {
		
        ta.setText(meeting.toString());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (isSelected)
        	ta.setBackground(list.getSelectionBackground());
        else
        	ta.setBackground(list.getBackground());
        
        if (meeting.getDataInizio().isBefore(LocalDate.now()))
        	ta.setForeground(Color.RED);
        return p;
	}

}
