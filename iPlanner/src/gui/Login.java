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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	private JButton accessoButton;
	private JButton annullaButton;


	

	
	/**
	 * Create the frame.
	 */
	public Login(ControllerAccesso theController) {
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
		
		emailTextField = new JTextField();
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setBounds(285, 133, 173, 26);
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(300, 120, 57, 14);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(301, 182, 70, 14);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		passwordField = new JPasswordField();

		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setBounds(285, 193, 173, 26);
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		
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
	
		
		annullaButton = new JButton("Annulla");
		annullaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				annullaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				annullaButton.setBackground(Color.WHITE);
			}
		});
		annullaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annullaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annullaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.annulla();
			}
		});
		

		annullaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		annullaButton.setBackground(Color.WHITE);
		annullaButton.setBounds(10, 364, 121, 26);
		contentPane.add(annullaButton);
		
		accessoButton = new JButton("Login");
		accessoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				accessoButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				accessoButton.setBackground(Color.WHITE);
			}
		});
		accessoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		accessoButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		accessoButton.setBounds(311, 267, 121, 26);
		accessoButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		accessoButton.setBackground(new Color(255, 255, 255));
		
		//Quando enter viene premuto all'interno dell' passwordField controlla che i campi vengano inseriti correttamente
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String username = emailTextField.getText();
				if(username.equals("") && passwordField.getText().equals("") && e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					JOptionPane.showMessageDialog(null, "Inserire Email e password per login");
					
				}
				else if(username.equals("") && e.getKeyCode()==KeyEvent.VK_ENTER) {
					JOptionPane.showMessageDialog(null, "Inserire Email per login");
				}
				else if(passwordField.getText().equals("") && !username.equals("") && e.getKeyCode()==KeyEvent.VK_ENTER) {
					JOptionPane.showMessageDialog(null, "Inserire password per login");
					
				}
				else if(!username.equals("") && !passwordField.getText().equals("") && e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						theController.verificaCredenziali(emailTextField.getText(), passwordField.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
			}
		});
		
		//Quando enter viene premuto all'interno dell' email textfield controlla che i campi vengano inseriti correttamente
		emailTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String password = passwordField.getText();
				if(emailTextField.getText().equals("") && password.equals("") && e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					JOptionPane.showMessageDialog(null, "Inserire Email e password per login");
					
				}
				else if(password.equals("") && e.getKeyCode()==KeyEvent.VK_ENTER) {
					JOptionPane.showMessageDialog(null, "Inserire password per login");
				}
			
				else if(!password.equals("") && emailTextField.getText().equals("") && e.getKeyCode()==KeyEvent.VK_ENTER) {
					JOptionPane.showMessageDialog(null, "Inserire email per login");
					
				}
				else if(!password.equals("") && !emailTextField.getText().equals("") && e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						theController.verificaCredenziali(emailTextField.getText(), passwordField.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
			}
		});
		
		//Quando viene premuto il button accedi controlla che i campi vengano inseriti correttamente
		accessoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = emailTextField.getText();
				String password = passwordField.getText();
			
				if(username.equals("") && password.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Inserire Email e password per login");
					
				}
				
				else if(username.equals("")) {
					JOptionPane.showMessageDialog(null, "Inserire Email per effettuare login");
				}
				else if(password.equals("")) {
					
					JOptionPane.showMessageDialog(null, "Inserire password per effettuare login");
					
				}
				else {
					try {
						theController.verificaCredenziali(emailTextField.getText(),passwordField.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		

		
		contentPane.add(accessoButton);
	}


	//Si occupa di svuotare i campi una volta effettuato il login(Viene chiamato dal controllerAccesso)
	public void SvuotaCampi() {
		
		emailTextField.setText(null);
		passwordField.setText(null);
	}
	

	
}
