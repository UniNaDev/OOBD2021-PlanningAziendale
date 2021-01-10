package gui;
import controller.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	//Attributi GUI
	private JPanel contentPane;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	private JButton accessoButton;
	private JButton annullaButton;
	
	private ControllerAccesso theController;
	

	
	/**
	 * Create the frame.
	 */
	public Login(ControllerAccesso theController) {
		this.theController = theController;
		
		setResizable(false);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Icone/WindowIcon_16.png")));
		
		setLocationRelativeTo(null);
		setTitle("iPlanner - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 150, 750, 440);
		
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Text Field per l'email
		emailTextField = new JTextField();
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setBounds(285, 133, 173, 26);
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		//Label "Email"
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(300, 120, 57, 14);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Label "Password"
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(301, 182, 70, 14);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Password Field per la password
		passwordField = new JPasswordField();
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setBounds(285, 193, 173, 26);
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		//Icone
		JLabel loginIconLabel = new JLabel("Login");
		loginIconLabel.setBounds(289, 5, 165, 88);
		loginIconLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		loginIconLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/employee_64.png")));
		
		JLabel emailIconLabel = new JLabel("");
		emailIconLabel.setBounds(286, 108, 16, 26);
		emailIconLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/username_16.png")));
		
		JLabel passwordIconLabel = new JLabel("");
		passwordIconLabel.setBounds(286, 170, 16, 26);
		passwordIconLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/password_16.png")));
		contentPane.setLayout(null);
		contentPane.add(loginIconLabel);
		contentPane.add(emailIconLabel);
		contentPane.add(emailTextField);
		contentPane.add(emailLabel);
		contentPane.add(passwordIconLabel);
		contentPane.add(passwordField);
		contentPane.add(passwordLabel);
	
		//Button "Annulla"
		annullaButton = new JButton("Annulla");
		//Eventi annessi al button "Annulla"
		annullaButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				annullaButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				annullaButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		annullaButton.addActionListener(new ActionListener() {
			//click del pulsante
			public void actionPerformed(ActionEvent e) {
				theController.annulla();	//annulla tutto e torna alla finestra iniziale di scelta
			}
		});
		annullaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annullaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annullaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		annullaButton.setBackground(Color.WHITE);
		annullaButton.setBounds(10, 364, 121, 26);
		contentPane.add(annullaButton);
		
		//Button per eseguire il login
		accessoButton = new JButton("Login");
		//Eventi connessi al button "Login"
		accessoButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				accessoButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				accessoButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		//click del pulsante
		accessoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(emailLabel, passwordLabel);
			}
		});
		accessoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		accessoButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		accessoButton.setBounds(311, 267, 121, 26);
		accessoButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		accessoButton.setBackground(new Color(255, 255, 255));
		contentPane.add(accessoButton);
		
		//Eventi connessi al Password Field della password
		passwordField.addKeyListener(new KeyAdapter() {
			//Premuto Enter
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					login(emailLabel, passwordLabel);
			}
		});
		
		//Eventi connessi al Text Field dell'email
		emailTextField.addKeyListener(new KeyAdapter() {
			//Premuto Enter
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					login(emailLabel, passwordLabel);
			}
		});
	}

	//Metodo che tenta di fare il login richiamando il controller
	private void login(JLabel emailLabel, JLabel passwordLabel) {
		//resetta i colori delle label
		emailLabel.setForeground(Color.BLACK);
		passwordLabel.setForeground(Color.BLACK);
		
		//se uno dei due campi email e password è vuoto
		if (emailTextField.getText().isBlank() || passwordField.getText().isBlank()) {
			//se il text field dell'email è vuoto
			if (emailTextField.getText().isBlank()) {
				emailLabel.setForeground(Color.RED);	//rende rossa la label
				svuotaCampi();	//svuota i campi
			}
			//se il password field della password è vuoto
			if (passwordField.getText().isBlank()) {
				passwordLabel.setForeground(Color.RED);	//rende rossa la label
				svuotaCampi();	//svuota i campi
			}
			
			JOptionPane.showMessageDialog(null,
					"Inserire Email e Password per login.",
					"Credenziali vuote",
					JOptionPane.OK_OPTION);	//mostra un errore
		}
		//se invece i campi sono pieni
		else {
			//ottiene email e password inseriti
			String username = emailTextField.getText();
			String password = passwordField.getText();
			
			try {
				//verifica le credenziali e tenta di effettuare il login
				theController.verificaCredenziali(emailTextField.getText(),passwordField.getText());
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,
						e1.getMessage(),
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);	//finestra di errore
				svuotaCampi();
			}
		}
	}
	

	//Metodo che svuota i campi di email e password
	private void svuotaCampi() {
		
		emailTextField.setText("");
		passwordField.setText("");
	}
	

	
}
