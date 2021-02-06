package cellRenderers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import entita.PartecipazioneMeeting;

public class InvitatiListRenderer implements ListCellRenderer<PartecipazioneMeeting> {

  	private JPanel p;
    private JTextArea ta;
    private JCheckBox cb;
    
public InvitatiListRenderer() {
    p = new JPanel();
    p.setLayout(new BorderLayout());
    
    // text
    ta = new JTextArea();
    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    ta.setFont(new Font("Consolas", Font.PLAIN, 15));
    ta.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(ta, BorderLayout.CENTER);
    
    //checkbox
    cb = new JCheckBox();
    cb.setText("");
    cb.setSelected(false);
    p.add(cb, BorderLayout.EAST);
}

	@Override
	public Component getListCellRendererComponent(JList<? extends PartecipazioneMeeting> list,
			PartecipazioneMeeting value, int index, boolean isSelected, boolean cellHasFocus) {
	    if (value.isOrganizzatore()){
	    	ta.setText(value.getPartecipante().toString() + " (Organizzatore)");
		}
	    else {
	    	ta.setText(value.getPartecipante().toString());
	    }
	    
	    if (value.isPresenza()) {
	    	cb.setSelected(true);
	    }
	    
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    if (isSelected)
	    	ta.setBackground(list.getSelectionBackground());
	    else
	    	ta.setBackground(list.getBackground());
	    
		return p;
	}
}
