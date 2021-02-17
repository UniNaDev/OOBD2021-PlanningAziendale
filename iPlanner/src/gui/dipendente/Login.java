/*Finestra di login per il dipendente.
 *Permette al dipendente di effettuare l'accesso con le sue credenziali: email e password. 
 ****************************************************************************************/

package gui.dipendente;

import controller.dipendente.ControllerAccesso;
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
    private JPanel contentPane;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton accessoButton;
    private JButton annullaButton;
    private JLabel mostraPasswordLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel iconaLoginLabel;
    private JLabel iconaEmailLabel;
    private JLabel iconaPasswordLabel;

    public Login(ControllerAccesso controller) {
	setResizable(false);
	setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Icone/WindowIcon_16.png")));
	setTitle("iPlanner - Login");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(650, 150, 750, 440);
	setLocationRelativeTo(null);
	
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);

	emailLabel = new JLabel("Email");
	emailLabel.setBounds(298, 116, 57, 14);
	emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
	emailLabel.setHorizontalAlignment(SwingConstants.CENTER);

	passwordLabel = new JLabel("Password");
	passwordLabel.setBounds(299, 182, 70, 14);
	passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
	passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

	emailTextField = new JTextField();
	emailTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
	emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	emailTextField.setBounds(280, 133, 173, 26);
	emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
	emailTextField.setColumns(10);
	emailTextField.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		    login(controller);
	    }
	});

	passwordField = new JPasswordField();
	passwordField.setFont(new Font("Consolas", Font.PLAIN, 11));
	passwordField.setEchoChar('*');
	passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	passwordField.setBounds(280, 193, 173, 26);
	passwordField.setHorizontalAlignment(SwingConstants.LEFT);
	passwordField.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		    login(controller);
	    }
	});

	iconaLoginLabel = new JLabel("Login");
	iconaLoginLabel.setBounds(284, 5, 165, 88);
	iconaLoginLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
	iconaLoginLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/employee_64.png")));

	iconaEmailLabel = new JLabel("");
	iconaEmailLabel.setBounds(280, 104, 16, 26);
	iconaEmailLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/username_16.png")));

	iconaPasswordLabel = new JLabel("");
	iconaPasswordLabel.setBounds(280, 170, 16, 26);
	iconaPasswordLabel.setIcon(new ImageIcon(Login.class.getResource("/Icone/password_16.png")));
	
	mostraPasswordLabel = new JLabel("");
	mostraPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	mostraPasswordLabel.setIcon(new ImageIcon(Login.class.getResource("/icone/showpass_24.png")));
	mostraPasswordLabel.setBounds(468, 199, 46, 14);
	mostraPasswordLabel.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		passwordField.setEchoChar((char) 0);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		passwordField.setEchoChar('*');
	    }
	});
	
	annullaButton = new JButton("Annulla");
	annullaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	annullaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
	annullaButton.setBounds(10, 364, 121, 26);
	annullaButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseEntered(MouseEvent e) {
		annullaButton.setBackground(Color.LIGHT_GRAY);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		annullaButton.setBackground(Color.WHITE);
	    }
	});
	annullaButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		controller.tornaAIPlanner();
	    }
	});

	accessoButton = new JButton("Login");
	accessoButton.setBounds(306, 267, 121, 26);
	accessoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	accessoButton.setFont(new Font("Consolas", Font.PLAIN, 13));
	accessoButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseEntered(MouseEvent e) {
		accessoButton.setBackground(Color.LIGHT_GRAY);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		accessoButton.setBackground(Color.WHITE);
	    }
	});
	accessoButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		login(controller);
	    }
	});

	contentPane.setLayout(null);
	contentPane.add(iconaLoginLabel);
	contentPane.add(iconaEmailLabel);
	contentPane.add(emailTextField);
	contentPane.add(emailLabel);
	contentPane.add(iconaPasswordLabel);
	contentPane.add(passwordField);
	contentPane.add(passwordLabel);
	contentPane.add(mostraPasswordLabel);
	contentPane.add(annullaButton);
	contentPane.add(accessoButton);
    }

    // Altri metodi
    // -----------------------------------------------------------------
    private void login(ControllerAccesso controller) {
	emailLabel.setForeground(Color.BLACK);
	passwordLabel.setForeground(Color.BLACK);

	if (emailTextField.getText().isBlank() || passwordField.getText().isBlank()) {
	    if (emailTextField.getText().isBlank()) {
		emailLabel.setForeground(Color.RED);
		pulisciCampi();
	    }
	    
	    if (passwordField.getText().isBlank()) {
		passwordLabel.setForeground(Color.RED);
		pulisciCampi();
	    }
	    
	    JOptionPane.showMessageDialog(null, "Inserire Email e Password per login.", "Credenziali Vuote",
		    JOptionPane.OK_OPTION);
	    
	} else {
	    String email = emailTextField.getText();
	    String password = passwordField.getText();
	    try {
		controller.eseguiLogin(emailTextField.getText(), passwordField.getText());
	    } catch (SQLException e1) {
		JOptionPane.showMessageDialog(null, "Credenziali errate.", "Login Fallito", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    private void pulisciCampi() {
	emailTextField.setText("");
	passwordField.setText("");
    }
}
