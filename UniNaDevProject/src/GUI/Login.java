package GUI;
import Controller.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField emailTextField;
	private JPasswordField passwordField;



	/**
	 * Create the frame.
	 */
	public Login(ControllerScelta theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Icone/WindowIcon_16.png")));
		setResizable(false);
		
		
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(186, 128, 173, 26);
		emailTextField.setHorizontalAlignment(SwingConstants.CENTER);
		emailTextField.setColumns(10);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setBounds(200, 116, 57, 14);
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setBounds(200, 175, 70, 14);
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(186, 188, 173, 26);
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton accessoButton = new JButton("Login");
		accessoButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		accessoButton.setBackground(new Color(255, 255, 255));
		accessoButton.setBounds(212, 262, 121, 26);
		accessoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.verificaCredenziali(emailTextField.getText(),passwordField.getText());
				
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(emailLabel);
		contentPane.add(passwordLabel);
		contentPane.add(passwordField);
		contentPane.add(emailTextField);
		contentPane.add(accessoButton);
		
		JLabel loginIconLabel = new JLabel("Login");
		loginIconLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		loginIconLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/employee_64.png")));
		loginIconLabel.setBounds(190, 0, 165, 88);
		contentPane.add(loginIconLabel);
		
		JLabel emailIconLabel = new JLabel("");
		emailIconLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/username_16.png")));
		emailIconLabel.setBounds(186, 104, 16, 26);
		contentPane.add(emailIconLabel);
		
		JLabel passwordIconLabel = new JLabel("");
		passwordIconLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/password_16.png")));
		passwordIconLabel.setBounds(186, 165, 24, 26);
		contentPane.add(passwordIconLabel);
	}
}
