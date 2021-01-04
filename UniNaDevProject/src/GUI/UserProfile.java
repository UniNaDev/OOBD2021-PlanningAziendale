package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JList;

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
	public UserProfile() {
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
				
			}
		});
		
		JButton btnAnnulla = new JButton("Chiudi");
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Chiudi finestra
				
				
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
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(btnAnnulla, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 1085, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(493, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addGap(474))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(454)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCognome, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSesso, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataDiNascita))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(textField_1, Alignment.LEADING)
							.addComponent(textField_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE))
					.addGap(528))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnAnnulla, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addGap(11)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
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
