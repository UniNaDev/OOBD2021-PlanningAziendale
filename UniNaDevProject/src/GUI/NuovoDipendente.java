package GUI;
import Controller.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JComboBox;


import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Frame;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import javax.swing.JFormattedTextField;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;


public class NuovoDipendente extends JFrame {

	private JPanel contentPane;
	
	//Label
	private JLabel signInLabel;
	private JLabel nameLabel;
	private JLabel surnameLabel;
	private JLabel sessoLabel;
	private JLabel indirizzoLabel;
	private JLabel emailLabel;
	private JLabel bornDateLabel;
	private JLabel bornPlaceLabel;
	private JLabel telefonoFissoLabel;
	private JLabel cellulareLabel;
	private JLabel passwordLabel;
	private JLabel confirmPasswordLabel;
	private JLabel iconaNomeLabel;
	private JLabel iconaEmailLabel;
	private JLabel iconaIndirizzoLabel;
	private JLabel iconaCellulareLabel;
	private JLabel iconaDataNascitaLabel;
	private JLabel iconaPasswordLabel;
	
	//TextField
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JTextField emailTextField;
	private JRadioButton uomoRadioButton;
	private JRadioButton donnaRadioButton;
	private JComboBox<?> dayComboBox;
	private JComboBox<?> monthComboBox;
	private JComboBox<?> yearComboBox;
	private JComboBox<?> provinciaComboBox;
	private JComboBox<?> cityComboBox;
	private JTextField indirizzoTextField;
	private JTextField cellulareTextField;
	private JTextField telefonoFissoTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	
	//Button
	private JButton createAccountButton;
	private JButton cancelCreationButton;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel iconaIndirizzoLabel_2;
	private JLabel iconaIndirizzoLabel_1;



	
	/**
	 * Create the frame.
	 */
	public NuovoDipendente(ControllerScelta theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuovoDipendente.class.getResource("/Icone/WindowIcon_16.png")));
		setResizable(false);
	
		setTitle("iPlanner - Sign in");
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		//Viene visualizzata al centro dello schermo
		setLocationRelativeTo(null);
		
		//Label
		nameLabel = new JLabel("Nome*");
		nameLabel.setBounds(178, 145, 66, 14);
		nameLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		surnameLabel = new JLabel("Cognome*");
		surnameLabel.setBounds(314, 145, 84, 14);
		surnameLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		surnameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(177, 250, 46, 14);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		indirizzoLabel = new JLabel("Indirizzo*");
		indirizzoLabel.setBounds(177, 423, 84, 14);
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		emailLabel = new JLabel("Email*");
		emailLabel.setBounds(179, 188, 66, 14);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		bornDateLabel = new JLabel("Data di nascita*");
		bornDateLabel.setBounds(176, 288, 117, 14);
		bornDateLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		bornDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		bornPlaceLabel = new JLabel("Luogo di nascita*");
		bornPlaceLabel.setBounds(177, 321, 128, 14);
		bornPlaceLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		bornPlaceLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setBounds(176, 489, 103, 14);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setBounds(176, 458, 84, 14);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(177, 552, 66, 14);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		confirmPasswordLabel = new JLabel("Conferma Password*");
		confirmPasswordLabel.setBounds(177, 577, 128, 14);
		confirmPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//IconLabel
		signInLabel = new JLabel("Sign In");
		signInLabel.setBounds(384, 11, 226, 75);
		signInLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/registration_64.png")));
		signInLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setBounds(121, 126, 46, 14);
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/nome_16.png")));
		
		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setBounds(121, 167, 46, 14);
		iconaEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaEmailLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/email_16.png")));
		
		iconaIndirizzoLabel = new JLabel("");
		iconaIndirizzoLabel.setBounds(121, 390, 46, 30);
		iconaIndirizzoLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/indirizzo_16.png")));
		iconaIndirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaCellulareLabel = new JLabel("");
		iconaCellulareLabel.setBounds(121, 448, 46, 30);
		iconaCellulareLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/cellulare_16.png")));
		iconaCellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaDataNascitaLabel = new JLabel("");
		iconaDataNascitaLabel.setBounds(121, 275, 46, 30);
		iconaDataNascitaLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/dataNascita_16.png")));
		iconaDataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaPasswordLabel = new JLabel("");
		iconaPasswordLabel.setBounds(121, 540, 46, 30);
		iconaPasswordLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/password_16.png")));
		iconaPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Text Field
		nameTextField = new JTextField();
		nameTextField.setBounds(177, 123, 130, 20);
		nameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nameTextField.setColumns(10);
		
		surnameTextField = new JTextField();
		surnameTextField.setBounds(314, 123, 130, 20);
		surnameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		surnameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		surnameTextField.setColumns(10);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(177, 164, 267, 20);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setBounds(243, 246, 61, 23);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		
		donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setBounds(315, 246, 66, 23);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(donnaRadioButton);

		//Combo Box
		dayComboBox = new JComboBox<Object>();
		dayComboBox.setBounds(308, 280, 44, 22);
		dayComboBox.setBackground(Color.WHITE);
		dayComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dayComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		dayComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		monthComboBox = new JComboBox<Object>();
		monthComboBox.setBounds(362, 280, 44, 22);
		monthComboBox.setBackground(Color.WHITE);
		monthComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		monthComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		yearComboBox = new JComboBox<Object>();
		yearComboBox.setBounds(416, 280, 66, 22);
		yearComboBox.setBackground(Color.WHITE);
		yearComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		yearComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		yearComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//CREO UN MODELLO PERSONALIZZATO PER L'ANNO DEL COMBO BOX //////////
		DefaultComboBoxModel myModel = new DefaultComboBoxModel();
		yearComboBox.setModel(myModel);
		
		for(int i=1900;i<= 2021;i++)
			myModel.addElement(i);
		
		yearComboBox.setSelectedIndex(100);
		
		provinciaComboBox = new JComboBox<Object>();
		provinciaComboBox.setBounds(310, 313, 195, 22);
		provinciaComboBox.setBackground(Color.WHITE);
		provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		cityComboBox = new JComboBox<Object>();
		cityComboBox.setBounds(310, 346, 195, 22);
		cityComboBox.setBackground(Color.WHITE);
		cityComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cityComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		cityComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		indirizzoTextField = new JTextField();
		indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		indirizzoTextField.setColumns(10);
		indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		indirizzoTextField.setBounds(177, 400, 267, 20);
		
		telefonoFissoTextField = new JTextField();
		telefonoFissoTextField.setBounds(289, 484, 86, 20);
		telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		telefonoFissoTextField.setColumns(10);
		
		cellulareTextField = new JTextField();
		cellulareTextField.setBounds(289, 453, 86, 20);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cellulareTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(317, 541, 125, 20);
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(317, 572, 125, 20);
		confirmPasswordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confirmPasswordField.setHorizontalAlignment(SwingConstants.LEFT);

		//Button
		createAccountButton = new JButton("Crea Account");
		createAccountButton.setBounds(840, 627, 134, 23);
		createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		createAccountButton.setBackground(Color.WHITE);
		createAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		createAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//INSERT Dei dati inseriti nella tabella
				theController.reLinkToLoginFrame();
			}
		});
		createAccountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				createAccountButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				createAccountButton.setBackground(Color.WHITE);
			}
		});
		
		cancelCreationButton = new JButton("Annulla");
		cancelCreationButton.setBounds(10, 627, 97, 23);
		cancelCreationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelCreationButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cancelCreationButton.setBackground(Color.WHITE);
		cancelCreationButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		cancelCreationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				theController.annulla();
			}
		});
		cancelCreationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				cancelCreationButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				cancelCreationButton.setBackground(Color.WHITE);
			}
		});
		
		contentPane.setLayout(null);
		contentPane.add(signInLabel);
		contentPane.add(iconaNomeLabel);
		contentPane.add(nameTextField);
		contentPane.add(surnameTextField);
		contentPane.add(nameLabel);
		contentPane.add(surnameLabel);
		contentPane.add(iconaEmailLabel);
		contentPane.add(emailTextField);
		contentPane.add(emailLabel);
		contentPane.add(sessoLabel);
		contentPane.add(uomoRadioButton);
		contentPane.add(donnaRadioButton);
		contentPane.add(iconaDataNascitaLabel);
		contentPane.add(bornDateLabel);
		contentPane.add(dayComboBox);
		contentPane.add(monthComboBox);
		contentPane.add(yearComboBox);
		contentPane.add(bornPlaceLabel);
		contentPane.add(provinciaComboBox);
		contentPane.add(cityComboBox);
		contentPane.add(iconaIndirizzoLabel);
		contentPane.add(indirizzoLabel);
		contentPane.add(iconaCellulareLabel);
		contentPane.add(cellulareLabel);
		contentPane.add(cellulareTextField);
		contentPane.add(telefonoFissoLabel);
		contentPane.add(telefonoFissoTextField);
		contentPane.add(iconaPasswordLabel);
		contentPane.add(passwordLabel);
		contentPane.add(passwordField);
		contentPane.add(confirmPasswordLabel);
		contentPane.add(confirmPasswordField);
		contentPane.add(cancelCreationButton);
		contentPane.add(createAccountButton);
		contentPane.add(indirizzoTextField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		scrollPane.setBounds(593, 124, 290, 164);
		contentPane.add(scrollPane);
		
		JList list = new JList();
		list.setFixedCellHeight(20);
		list.setFont(new Font("Consolas", Font.PLAIN, 12));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Skill 1", "Skill 1", "Skill 1", "Skill 1", "Skill 1", "Skill 1", "Skill 1", "Skill 1", "Skill 1", "Skill 1"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblSkills = new JLabel("Skills");
		lblSkills.setIcon(null);
		lblSkills.setHorizontalAlignment(SwingConstants.LEFT);
		lblSkills.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblSkills.setBounds(593, 107, 66, 14);
		contentPane.add(lblSkills);
		
		textField = new JTextField();
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		textField.setBounds(593, 300, 141, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Crea Nuova Skill");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnNewButton.setBounds(744, 299, 139, 23);
		contentPane.add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		textField_1.setColumns(10);
		textField_1.setBounds(593, 367, 141, 22);
		contentPane.add(textField_1);
		
		lblNewLabel = new JLabel("Salario");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblNewLabel.setBounds(593, 394, 66, 14);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("€");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(743, 361, 66, 36);
		contentPane.add(lblNewLabel_1);
		
		iconaIndirizzoLabel_2 = new JLabel("");
		iconaIndirizzoLabel_2.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/skills_32.png")));
		iconaIndirizzoLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaIndirizzoLabel_2.setBounds(524, 183, 60, 39);
		contentPane.add(iconaIndirizzoLabel_2);
		
		iconaIndirizzoLabel_1 = new JLabel("");
		iconaIndirizzoLabel_1.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/money_16.png")));
		iconaIndirizzoLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaIndirizzoLabel_1.setBounds(536, 362, 46, 30);
		contentPane.add(iconaIndirizzoLabel_1);
	}
}
