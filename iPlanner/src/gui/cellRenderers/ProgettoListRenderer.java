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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entita.Meeting;
import entita.Progetto;

public class ProgettoListRenderer implements ListCellRenderer<Progetto> {

	  	private JPanel panel;
	    private JTextArea textArea;
	
	public ProgettoListRenderer() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // text
        textArea = new JTextArea();
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(textArea, BorderLayout.CENTER);
    }
	
	

	@Override
	public Component getListCellRendererComponent(JList<? extends Progetto> list, Progetto progetto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		String temp ="Nome:"+progetto.getNomeProgetto();
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		temp += "\nData creazione:" + progetto.getDataCreazione().toString(formatDate) + " Data scadenza:" +progetto.getScadenza().toString(formatDate)+" Data terminazione:";
		if (progetto.getDataTerminazione() !=null)
			temp += progetto.getDataTerminazione().toString(formatDate);
		else
			temp += "In corso";
		
		textArea.setText(temp);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (isSelected)
        	textArea.setBackground(list.getSelectionBackground());
        else
        	textArea.setBackground(list.getBackground());
        return panel;
	}
}
