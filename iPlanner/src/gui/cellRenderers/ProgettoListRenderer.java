package gui.cellRenderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entita.Progetto;

public class ProgettoListRenderer implements ListCellRenderer<Progetto> {

  	private JPanel panel;
    private JTextArea textArea;
    
	public ProgettoListRenderer() {
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
		
		String infoProgetto ="Nome:" + progetto.getNomeProgetto();
		
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		infoProgetto += "\nScade:" + progetto.getScadenza().toString(formatDate);
		
		if (progetto.isScaduto() && !progetto.isTerminato())
			textArea.setForeground(Color.RED);
		else
			textArea.setForeground(Color.BLACK);
		
		if (progetto.isTerminato())
			textArea.setFont(new Font("Consolas", Font.ITALIC, 15));
		else
	        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
		
		textArea.setText(infoProgetto);
        
        if (isSelected)
        	textArea.setBackground(list.getSelectionBackground());
        else
        	textArea.setBackground(list.getBackground());
        return panel;
	}
}
