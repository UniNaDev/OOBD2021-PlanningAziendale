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

public class ProgettoDiscussoListRenderer implements ListCellRenderer<Progetto> {

	  	private JPanel panel;
	    private JTextArea textArea;
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	
	public ProgettoDiscussoListRenderer() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(textArea, BorderLayout.CENTER);
    }
	
	

	@Override
	public Component getListCellRendererComponent(JList<? extends Progetto> list, Progetto progetto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		String progettoDiscussoTesto="Nome:"+progetto.getNomeProgetto()
				+"\n Data creazione:"+progetto.getDataCreazione().toString(formatDate)
				+"\n Data scadenza:"+progetto.getScadenza().toString(formatDate)+"\n Data terminazione:";
		
		if(progetto.getDataTerminazione()!=null)
			progettoDiscussoTesto+=progetto.getDataTerminazione().toString(formatDate);
		else
			progettoDiscussoTesto+="In corso";
		
		textArea.setText(progettoDiscussoTesto);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (isSelected)
        	textArea.setBackground(list.getSelectionBackground());
        else
        	textArea.setBackground(list.getBackground());
        return panel;
	}
}
