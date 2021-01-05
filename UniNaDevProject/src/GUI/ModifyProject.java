package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class ModifyProject extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public ModifyProject() {
		setTitle("ModifyProject");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Schermata che permette la modifica di un progetto gi\u00E0 esistente");
		lblNewLabel.setBounds(5, 5, 424, 251);
		contentPane.add(lblNewLabel);
	}

}
