package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerMeeting;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Meeting extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton_1;


	/**
	 * Create the frame.
	 */
	public Meeting(ControllerMeeting theController) {
		setTitle("Meeting");
		setBounds(650, 150, 1280, 720);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Inserisci");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createInsertMeetingFrame();
			}
		});
		btnNewButton.setBounds(299, 258, 89, 23);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Modifica");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createModifyMeetingFrame();
			}
		});
		btnNewButton_1.setBounds(578, 273, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}
