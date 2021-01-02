package GUI;
import Controller.ControllerScelta;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class iPlanner extends JFrame {

	private JPanel contentPane;
 

    
    
	/**
	 * Create the frame.
	 */
	public iPlanner(ControllerScelta theController) {
		
		setTitle("iPlanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel LabelDipendenteIscritto = new JLabel("Sei un dipendente gi\u00E0 iscritto?");
		LabelDipendenteIscritto.setHorizontalAlignment(SwingConstants.CENTER);
		LabelDipendenteIscritto.setBounds(127, 174, 200, 14);
		contentPane.add(LabelDipendenteIscritto);
		
		
		//Quando clicco sull'etichetta con il mouse mi porta alla finestra per fare il login
		LabelDipendenteIscritto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.LinkToLoginFrame();
			}
		});
		
		
		JLabel LabelNuovoDipendente = new JLabel("Sei un nuovo dipendente?");
		LabelNuovoDipendente.setHorizontalAlignment(SwingConstants.CENTER);
		LabelNuovoDipendente.setBounds(127, 197, 200, 14);
		contentPane.add(LabelNuovoDipendente);
		
		//Quando clicco sull'etichetta con il mouse mi porta alla finestra per creare un nuovo utente
		LabelNuovoDipendente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.LinkToCreationFrame();
			}
		});

	

		
	
		
	}
	
	
	
}
