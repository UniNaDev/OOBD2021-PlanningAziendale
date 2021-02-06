package gui.cellRenderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;

import org.joda.time.LocalDate;

import entita.Dipendente;
import entita.Meeting;

public class DipendenteInvitatoListRenderer implements ListCellRenderer<Dipendente> {

	  	private JPanel p;
	    private JTextArea ta;
	
	public DipendenteInvitatoListRenderer() {
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
	public Component getListCellRendererComponent(JList<? extends Dipendente> list, Dipendente dipendente, int index,
			boolean isSelected, boolean cellHasFocus) {
		ta.setText("Nome: "+dipendente.getNome()+" "+"Cognome: "+dipendente.getCognome());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (isSelected)
        	ta.setBackground(list.getSelectionBackground());
        else
        	ta.setBackground(list.getBackground());
        
 
        return p;
	}

}