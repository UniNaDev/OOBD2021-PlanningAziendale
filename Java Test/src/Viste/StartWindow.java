//Finestra principale all'avvio del software
package Viste;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerStart;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class StartWindow extends JFrame {

	//ATTRIBUTI
	
	//Attributi GUI
	private JPanel contentPane;	//jPanel principale
	
	//Altri attributi
	private final String versione = "v. 0.0.1";	//versione software
	private final String devs = "UninaDevs";	//developers

	//METODI
	
	//Crea frame
	public StartWindow(ControllerStart controller) {
		setResizable(false);
		setTitle("iPlanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 420);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label "Sei già iscritto?"
		JLabel giàIscrittoLabel = new JLabel("Sei già iscritto?");
		giàIscrittoLabel.setForeground(new Color(0, 0, 0));
		//EVENTI
		giàIscrittoLabel.addMouseListener(new MouseAdapter() {
			//click destro sulla label
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.startTOlogin(); //chiama metodo del controller che avvia la finestra di login
			}
			//mouse destro entrato
			@Override
			public void mouseEntered(MouseEvent e) {
				highlightLabel(giàIscrittoLabel);	//evidenzia
			}
			//mouse destro uscito
			@Override
			public void mouseExited(MouseEvent e) {
				removeHighlightLabel(giàIscrittoLabel);	//rimuovi highlight
			}
		});
		//proprietà
		giàIscrittoLabel.setFont(new Font("Calibri Light", Font.PLAIN, 21));
		giàIscrittoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		giàIscrittoLabel.setBounds(212, 230, 174, 26);
		contentPane.add(giàIscrittoLabel);
		
		//Label "Crea un nuovo account"
		JLabel nuovoAccountLabel = new JLabel("Crea un nuovo account");
		//EVENTI
		nuovoAccountLabel.addMouseListener(new MouseAdapter() {
			//mouse destro entrato
			@Override
			public void mouseEntered(MouseEvent e) {
				highlightLabel(nuovoAccountLabel);	//evidenzia
			}
			//mouse destro uscito
			@Override
			public void mouseExited(MouseEvent e) {
				removeHighlightLabel(nuovoAccountLabel);	//rimuovi highlight
			}
			//click mouse destro
			@Override
			public void mouseClicked(MouseEvent e) {
				//metodo che richiama la nuova finestra tramite controller
				try {
					controller.startTOcreaAccount();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//proprietà
		nuovoAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nuovoAccountLabel.setFont(new Font("Calibri Light", Font.PLAIN, 21));
		nuovoAccountLabel.setBounds(183, 267, 233, 26);
		contentPane.add(nuovoAccountLabel);
		
		//Label "Benvenuto su iPlanner"
		JLabel benvenutoLabel = new JLabel("Benvenuto su iPlanner");
		//proprietà
		benvenutoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		benvenutoLabel.setFont(new Font("Calibri Light", Font.BOLD, 36));
		benvenutoLabel.setBounds(112, 76, 374, 71);
		contentPane.add(benvenutoLabel);
		
		//Label versione del software
		JLabel lblNewLabel = new JLabel(versione);
		//proprietà
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(536, 367, 53, 14);
		contentPane.add(lblNewLabel);
		
		//Label sviluppatori del software
		JLabel lblNewLabel_1 = new JLabel("Creato da " + devs);
		//proprietà
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(0, 367, 108, 14);
		contentPane.add(lblNewLabel_1);
	}
	
	//Metodo che evidenzia il testo di una label
	public void highlightLabel(JLabel label) {
		label.setForeground(Color.GRAY);
	}
	
	//Metodo che rimuove l'highlight di una label
	public void removeHighlightLabel(JLabel label) {
		label.setForeground(Color.BLACK);
	}
}
