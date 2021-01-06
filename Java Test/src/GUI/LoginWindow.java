//Finestra di login del software
package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JPasswordField;

public class LoginWindow extends JFrame {

	//ATTRIBUTI
	//attributi GUI
	private JPanel contentPane;	//panel principale
	private JTextField emailTextField;
	private JPasswordField passwordField;
	
	//altri attributi


	//METODI
	
	//Crea il frame
	public LoginWindow(ControllerGUI controller) {
		setForeground(Color.WHITE);
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 420);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label "Login" titolo
		JLabel loginLabel = new JLabel("Login");
		//proprietà
		loginLabel.setFont(new Font("Calibri", Font.BOLD, 36));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setBounds(248, 71, 102, 49);
		contentPane.add(loginLabel);
		
		//TextField email
		emailTextField = new JTextField();
		//proprietà
		emailTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		emailTextField.setBounds(228, 176, 166, 20);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);
		
		//Label "Email"
		JLabel emailLabel = new JLabel("Email");
		//proprietà
		emailLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setBounds(183, 176, 37, 20);
		contentPane.add(emailLabel);
		
		//Label "Password"
		JLabel passwordLabel = new JLabel("Password");
		//proprietà
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		passwordLabel.setBounds(158, 219, 62, 20);
		contentPane.add(passwordLabel);
		
		//Button "Login"
		JButton loginButton = new JButton("Login");
		//EVENTI
		loginButton.addMouseListener(new MouseAdapter() {
			//click mouse destro
			@Override
			public void mouseClicked(MouseEvent e) {
				//chiama metodo del controller per fare il login
				String email = emailTextField.getText();
				String password = passwordField.getText();
				try {
					controller.login(email, password);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//proprietà
		loginButton.setFont(new Font("Calibri", Font.PLAIN, 16));
		loginButton.setBounds(255, 285, 89, 23);
		contentPane.add(loginButton);
		
		//Password Field
		passwordField = new JPasswordField();
		//proprietà
		passwordField.setFont(new Font("Calibri", Font.PLAIN, 16));
		passwordField.setBounds(228, 217, 166, 20);
		contentPane.add(passwordField);
	}
}
