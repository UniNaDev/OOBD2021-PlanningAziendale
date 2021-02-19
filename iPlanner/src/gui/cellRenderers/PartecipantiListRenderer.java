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


import entita.CollaborazioneProgetto;

public class PartecipantiListRenderer implements ListCellRenderer<CollaborazioneProgetto>{

	  	private JPanel p;
	    private JTextArea ta;
	
	public PartecipantiListRenderer() {
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("Consolas", Font.PLAIN, 15));
        ta.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(ta, BorderLayout.CENTER);
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends CollaborazioneProgetto> list, CollaborazioneProgetto collaborazione, int index, boolean isSelected,
			boolean cellHasFocus) {
        ta.setText(collaborazione.toString());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (isSelected)
        	ta.setBackground(list.getSelectionBackground());
        else
        	ta.setBackground(list.getBackground());
        
		return p;
	}
}
