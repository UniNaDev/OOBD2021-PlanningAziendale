package GUI;
import Controller.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JComboBox;


import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NuovoDipendente extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField surnameTextField;
	private JLabel surnameLabel;
	private JLabel emailLabel;
	private JLabel bornDateLabel;
	private JComboBox<?> monthComboBox;
	private JComboBox<?> yearComboBox;
	private JLabel bornPlaceLabel;
	private JComboBox<?> provinciaComboBox;
	private JComboBox<?> cityComboBox;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	private JLabel confirmPasswordLabel;
	private JPasswordField confirmPasswordField;
	private JButton createAccountButton;
	private JButton cancelCreationButton;

	ControllerScelta theController;
	
	/**
	 * Create the frame.
	 */
	public NuovoDipendente(ControllerScelta theController) {
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setTitle("Creazione Dipendente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Label
		JLabel nameLabel = new JLabel("Nome");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setBounds(78, 28, 66, 14);
		contentPane.add(nameLabel);
		
		surnameLabel = new JLabel("Cognome");
		surnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		surnameLabel.setBounds(78, 59, 66, 14);
		contentPane.add(surnameLabel);
		
		emailLabel = new JLabel("Email");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setBounds(78, 90, 66, 14);
		contentPane.add(emailLabel);
		
		bornDateLabel = new JLabel("Data di nascita");
		bornDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		bornDateLabel.setBounds(59, 121, 85, 14);
		contentPane.add(bornDateLabel);
		
		bornPlaceLabel = new JLabel("Luogo di nascita");
		bornPlaceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		bornPlaceLabel.setBounds(27, 150, 117, 14);
		contentPane.add(bornPlaceLabel);
		
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setBounds(78, 182, 66, 14);
		contentPane.add(passwordLabel);
		
		confirmPasswordLabel = new JLabel("Conferma Password");
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		confirmPasswordLabel.setBounds(27, 212, 117, 14);
		contentPane.add(confirmPasswordLabel);
		
		
		//Text Field
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nameTextField.setBounds(154, 25, 125, 20);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		surnameTextField = new JTextField();
		surnameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		surnameTextField.setColumns(10);
		surnameTextField.setBounds(154, 56, 125, 20);
		contentPane.add(surnameTextField);
		
		emailTextField = new JTextField();
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		emailTextField.setBounds(154, 87, 125, 20);
		contentPane.add(emailTextField);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(154, 179, 125, 20);
		contentPane.add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		confirmPasswordField.setBounds(154, 210, 125, 20);
		contentPane.add(confirmPasswordField);
		
		//Combo Box
		JComboBox<?> dayComboBox = new JComboBox<Object>();
		dayComboBox.setBounds(153, 118, 30, 22);
		contentPane.add(dayComboBox);
		
		monthComboBox = new JComboBox<Object>();
		monthComboBox.setBounds(193, 117, 65, 22);
		contentPane.add(monthComboBox);
		
		yearComboBox = new JComboBox<Object>();
		yearComboBox.setBounds(268, 117, 44, 22);
		contentPane.add(yearComboBox);
		
		provinciaComboBox = new JComboBox<Object>();
		provinciaComboBox.setBounds(154, 146, 44, 22);
		contentPane.add(provinciaComboBox);
		
		cityComboBox = new JComboBox<Object>();
		cityComboBox.setBounds(203, 146, 65, 22);
		contentPane.add(cityComboBox);
		

		//Button
		createAccountButton = new JButton("Crea Account");
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//INSERT Dei dati inseriti nella tabella
				theController.reLinkToLoginFrame();
			}
		});
		createAccountButton.setBounds(345, 242, 97, 23);
		contentPane.add(createAccountButton);
		
		cancelCreationButton = new JButton("Annulla");
		cancelCreationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				theController.annulla();
				
				
			}
		});
		cancelCreationButton.setBounds(10, 242, 97, 23);
		contentPane.add(cancelCreationButton);
	}
}
