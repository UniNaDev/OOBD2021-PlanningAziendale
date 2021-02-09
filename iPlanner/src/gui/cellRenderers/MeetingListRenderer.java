package gui.cellRenderers;

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

	  	private JPanel panel;
	    private JTextArea textArea;
	
	public MeetingListRenderer() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // text
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(textArea, BorderLayout.CENTER);
    }
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Meeting> list, Meeting meeting, int index,
	        boolean isSelected, boolean cellHasFocus) {
		
        textArea.setText(meeting.toString());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (isSelected)
        	textArea.setBackground(list.getSelectionBackground());
        else
        	textArea.setBackground(list.getBackground());
        
        if (meeting.getDataInizio().isBefore(LocalDate.now()))
        	textArea.setForeground(Color.RED);
        return panel;
	}
}
