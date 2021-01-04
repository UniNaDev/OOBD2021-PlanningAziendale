package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Project extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public Project() {
		setTitle("Progetti");
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 493, 665);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(523, 11, 671, 429);
		contentPane.add(panel_1);
		
		JButton modifyProject = new JButton("Modifica Progetto");
		modifyProject.setBounds(800, 481, 156, 39);
		contentPane.add(modifyProject);
		
		JButton insertProject = new JButton("Inserisci Progetto");
		insertProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		insertProject.setBounds(800, 531, 156, 53);
		contentPane.add(insertProject);
	}

}
