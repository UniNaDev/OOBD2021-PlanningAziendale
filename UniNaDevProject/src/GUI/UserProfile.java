package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerGestioneProfilo;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class UserProfile extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel lblNewLabel;
	private JLabel lblNome;
	private JLabel lblCognome;
	private JLabel lblSesso;
	private JLabel lblDataDiNascita;


	
		


	/**
	 * Create the frame.
	 */
	public UserProfile(ControllerGestioneProfilo theController) {
		setResizable(false);
		setTitle("Profilo Utente");
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);
	
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		

		
		JButton btnNewButton = new JButton("Conferma");
		btnNewButton.setBounds(1171, 634, 96, 42);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO  UPDATE dei dati se si clicca su conferma
				theController.closeWindow();
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setBounds(552, 158, 187, 24);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Informazioni personali");
		lblNewLabel_1.setBounds(490, 60, 295, 37);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		textField = new JTextField();
		textField.setBounds(552, 123, 187, 24);
		textField.setEditable(false);
		textField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(552, 193, 187, 24);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(552, 228, 187, 24);
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(552, 263, 187, 24);
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		
		lblNewLabel = new JLabel("CF");
		lblNewLabel.setBounds(486, 128, 62, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(486, 163, 62, 19);
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(486, 194, 62, 19);
		lblCognome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblSesso = new JLabel("Sesso");
		lblSesso.setBounds(486, 228, 62, 19);
		lblSesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSesso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblDataDiNascita = new JLabel("Data di nascita");
		lblDataDiNascita.setBounds(459, 264, 89, 19);
		lblDataDiNascita.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDiNascita.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton modifyButton = new JButton("Modifica");
		modifyButton.setBounds(544, 644, 71, 23);
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Quando viene premuto il bottone modifica i campi del profilo diventano editabili
				
				textField.setEditable(true);
				textField_1.setEditable(true);
				textField_2.setEditable(true);
				textField_4.setEditable(true);
				textField_3.setEditable(true);
				
				addWindowListener(new WindowAdapter() {
					//Quando si vuole uscire chiede all'utente quale scelta vuole effettuare
					public void windowClosing(WindowEvent evt) {
		                            int res=JOptionPane.showConfirmDialog(null,
		                                    "Sei sicuro di uscire? Le modifiche non verranno salvate");
		                            if(res==JOptionPane.YES_OPTION){
		                                  theController.closeWindow();
		                            }
		                            if(res==JOptionPane.NO_OPTION) {
		                            	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		                            	
		                            }
		            }                               
		        });
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(modifyButton);
		contentPane.add(btnNewButton);
		contentPane.add(lblNewLabel);
		contentPane.add(lblNome);
		contentPane.add(lblCognome);
		contentPane.add(lblSesso);
		contentPane.add(lblDataDiNascita);
		contentPane.add(textField_4);
		contentPane.add(textField_3);
		contentPane.add(textField_1);
		contentPane.add(textField_2);
		contentPane.add(textField);
		contentPane.add(lblNewLabel_1);
	}
}
