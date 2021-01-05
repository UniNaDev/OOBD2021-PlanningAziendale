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
		setTitle("Profilo Utente");
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);
	
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		

		
		JButton btnNewButton = new JButton("Conferma");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO  UPDATE dei dati se si clicca su conferma
				theController.closeWindow();
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Informazioni personali");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		
		lblNewLabel = new JLabel("CF");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblCognome = new JLabel("Cognome");
		lblCognome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblSesso = new JLabel("Sesso");
		lblSesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSesso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblDataDiNascita = new JLabel("Data di nascita");
		lblDataDiNascita.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDiNascita.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton modifyButton = new JButton("Modifica");
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
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(539)
					.addComponent(modifyButton)
					.addPreferredGap(ComponentPlacement.RELATED, 556, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(454)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(27)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(27)
							.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
						.addComponent(lblCognome, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSesso, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataDiNascita))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, 187, 187, 187)
						.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
					.addGap(528))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(485)
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(482))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(55)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 510, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addComponent(modifyButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(5)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
									.addGap(11)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(5)
									.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCognome, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDataDiNascita, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblSesso, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addGap(389))))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
