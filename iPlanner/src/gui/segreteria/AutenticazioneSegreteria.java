//Finestra di autenticazione per entrare nell'area segreteria

package gui.segreteria;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerAccesso;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AutenticazioneSegreteria extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextArea istruzioniTextArea;
	private JButton autenticaButton;
	private JButton annullaButton;

	public AutenticazioneSegreteria(ControllerAccesso controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutenticazioneSegreteria.class.getResource("/icone/WindowIcon_16.png")));
		setTitle("iPlanner - Autenticazione Segreteria");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 396, 165);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		istruzioniTextArea = new JTextArea("Inserire la password amministrativa per accedere all'area segreteria.");
		istruzioniTextArea.setEditable(false);
		istruzioniTextArea.setLineWrap(true);
		istruzioniTextArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		istruzioniTextArea.setBounds(10, 11, 330, 43);
		contentPane.add(istruzioniTextArea);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					if (!passwordField.getText().isBlank())
						controller.eseguiLoginSegreteria(passwordField.getText());
					else
						JOptionPane.showMessageDialog(null,
								"Inserire la password amministrativa.",
								"Errore Autenticazione",
								JOptionPane.ERROR_MESSAGE);
			}
		});
		passwordField.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordField.setBounds(88, 65, 203, 20);
		contentPane.add(passwordField);
		
		autenticaButton = new JButton("Verifica");
		autenticaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!passwordField.getText().isBlank())
					controller.eseguiLoginSegreteria(passwordField.getText());
				else
					JOptionPane.showMessageDialog(null,
							"Inserire la password amministrativa.",
							"Errore Autenticazione",
							JOptionPane.ERROR_MESSAGE);
			}
			
		});
		autenticaButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	autenticaButton.setBackground(Color.LIGHT_GRAY);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	autenticaButton.setBackground(Color.WHITE);
		    }
		});
		autenticaButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		autenticaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		autenticaButton.setBounds(282, 101, 89, 23);
		contentPane.add(autenticaButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tornaAdIPlanner();
			}
		});
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
		annullaButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		annullaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annullaButton.setBounds(10, 101, 89, 23);
		contentPane.add(annullaButton);
	}
}
