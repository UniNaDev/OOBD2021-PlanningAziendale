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


public class NuovoDipendente extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField surnameTextField;
	private JLabel surnameLabel;
	private JLabel emailLabel;
	private JLabel bornDateLabel;
	private JComboBox<?> monthComboBox;
	private JComboBox<?> yearComboBox;
	private JLabel bornPlaceLabel;
	private JComboBox<?> provinciaComboBox;
	private JComboBox<?> cityComboBox;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	private JLabel confirmPasswordLabel;
	private JPasswordField confirmPasswordField;
	private JButton createAccountButton;
	private JButton cancelCreationButton;

	ControllerScelta theController;
	private JTextField cellulareTextField;
	private JTextField telefonoFissoTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblNewLabel;
	private JLabel iconaEmailLabel;
	private JLabel iconaIndirizzoLabel;
	private JLabel iconaCellulareLabel;
	private JLabel iconaDataNascitaLabel;
	private JLabel iconaPasswordLabel;
	
	/**
	 * Create the frame.
	 */
	public NuovoDipendente(ControllerScelta theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuovoDipendente.class.getResource("/Icone/WindowIcon_16.png")));
		setResizable(false);
		
		setTitle("iPlanner - Sing In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Label
		JLabel nameLabel = new JLabel("Nome*");
		nameLabel.setBounds(352, 147, 66, 14);
		nameLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(nameLabel);
		
		surnameLabel = new JLabel("Cognome*");
		surnameLabel.setBounds(488, 147, 84, 14);
		surnameLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		surnameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(surnameLabel);
		
		emailLabel = new JLabel("Email*");
		emailLabel.setBounds(353, 190, 66, 14);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(emailLabel);
		
		bornDateLabel = new JLabel("Data di nascita*");
		bornDateLabel.setBounds(351, 282, 117, 14);
		bornDateLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		bornDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(bornDateLabel);
		
		bornPlaceLabel = new JLabel("Luogo di nascita*");
		bornPlaceLabel.setBounds(351, 324, 128, 14);
		bornPlaceLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		bornPlaceLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(bornPlaceLabel);
		
		
		passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(351, 550, 66, 14);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(passwordLabel);
		
		confirmPasswordLabel = new JLabel("Conferma Password*");
		confirmPasswordLabel.setBounds(351, 575, 128, 14);
		confirmPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(confirmPasswordLabel);
		
		
		//Text Field
		nameTextField = new JTextField();
		nameTextField.setBounds(351, 125, 130, 20);
		nameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		surnameTextField = new JTextField();
		surnameTextField.setBounds(488, 125, 130, 20);
		surnameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		surnameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		surnameTextField.setColumns(10);
		contentPane.add(surnameTextField);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(351, 166, 267, 20);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		contentPane.add(emailTextField);

		passwordField = new JPasswordField();
		passwordField.setBounds(491, 539, 125, 20);
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(491, 570, 125, 20);
		confirmPasswordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confirmPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(confirmPasswordField);
		
		//Combo Box
		JComboBox<?> dayComboBox = new JComboBox<Object>();
		dayComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dayComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		dayComboBox.setBounds(483, 274, 44, 22);
		dayComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(dayComboBox);
		
		monthComboBox = new JComboBox<Object>();
		monthComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		monthComboBox.setBounds(537, 274, 44, 22);
		monthComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(monthComboBox);
		
		yearComboBox = new JComboBox<Object>();
		yearComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		yearComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		yearComboBox.setBounds(591, 274, 66, 22);
		yearComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		
		//CREO UN MODELLO PERSONALIZZATO PER L'ANNO DEL COMBO BOX //////////
		DefaultComboBoxModel myModel = new DefaultComboBoxModel();
		yearComboBox.setModel(myModel);
		
		for(int i=1900;i<= 2021;i++)
			myModel.addElement(i);
		
		yearComboBox.setSelectedIndex(100); //mette di default il 100esimo indice cioè l'anno 2000
		//////////////////////////////////////////////////////////
		
		contentPane.add(yearComboBox);
		
		provinciaComboBox = new JComboBox<Object>();
		provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		provinciaComboBox.setBounds(484, 316, 44, 22);
		provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(provinciaComboBox);
		
		cityComboBox = new JComboBox<Object>();
		cityComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cityComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		cityComboBox.setBounds(538, 316, 65, 22);
		cityComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(cityComboBox);
		

		//Button
		createAccountButton = new JButton("Crea Account");
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
		createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		createAccountButton.setBackground(Color.WHITE);
		createAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		createAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		createAccountButton.setBounds(840, 627, 134, 23);
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//INSERT Dei dati inseriti nella tabella
				theController.reLinkToLoginFrame();
			}
		});
		contentPane.add(createAccountButton);
		
		cancelCreationButton = new JButton("Annulla");
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
		cancelCreationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelCreationButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cancelCreationButton.setBackground(Color.WHITE);
		cancelCreationButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		cancelCreationButton.setBounds(10, 627, 97, 23);
		cancelCreationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				theController.annulla();
			}
		});
		contentPane.add(cancelCreationButton);
		
		JRadioButton uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBounds(417, 240, 61, 23);
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		contentPane.add(uomoRadioButton);
		
		JRadioButton donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setBounds(489, 240, 66, 23);
		donnaRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(donnaRadioButton);
		contentPane.add(donnaRadioButton);
		
		JLabel sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(351, 244, 46, 14);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		contentPane.add(sessoLabel);
		
		JLabel idirizzoLabel = new JLabel("Indirizzo*");
		idirizzoLabel.setBounds(352, 428, 84, 14);
		idirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		contentPane.add(idirizzoLabel);
		
		JScrollPane indirizzoScrollPane = new JScrollPane();
		indirizzoScrollPane.setBounds(350, 380, 267, 43);
		indirizzoScrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(indirizzoScrollPane);
		
		JTextArea indirizzoTextArea = new JTextArea();
		indirizzoTextArea.setLineWrap(true);
		indirizzoScrollPane.setViewportView(indirizzoTextArea);
		
		cellulareTextField = new JTextField();
		cellulareTextField.setBounds(464, 453, 86, 20);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(cellulareTextField);
		cellulareTextField.setColumns(10);
		
		JLabel cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setBounds(351, 458, 84, 14);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		contentPane.add(cellulareLabel);
		
		telefonoFissoTextField = new JTextField();
		telefonoFissoTextField.setBounds(464, 484, 86, 20);
		telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		contentPane.add(telefonoFissoTextField);
		telefonoFissoTextField.setColumns(10);
		
		JLabel telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setBounds(351, 489, 103, 14);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		contentPane.add(telefonoFissoLabel);
		
		lblNewLabel = new JLabel("Sing In");
		lblNewLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/registration_64.png")));
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		lblNewLabel.setBounds(377, 11, 226, 75);
		contentPane.add(lblNewLabel);
		
		JLabel iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/nome_16.png")));
		iconaNomeLabel.setBounds(295, 128, 46, 14);
		contentPane.add(iconaNomeLabel);
		
		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaEmailLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/email_16.png")));
		iconaEmailLabel.setBounds(295, 169, 46, 14);
		contentPane.add(iconaEmailLabel);
		
		iconaIndirizzoLabel = new JLabel("");
		iconaIndirizzoLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/indirizzo_16.png")));
		iconaIndirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaIndirizzoLabel.setBounds(295, 386, 46, 30);
		contentPane.add(iconaIndirizzoLabel);
		
		iconaCellulareLabel = new JLabel("");
		iconaCellulareLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/cellulare_16.png")));
		iconaCellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaCellulareLabel.setBounds(296, 448, 46, 30);
		contentPane.add(iconaCellulareLabel);
		
		iconaDataNascitaLabel = new JLabel("");
		iconaDataNascitaLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/dataNascita_16.png")));
		iconaDataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaDataNascitaLabel.setBounds(296, 269, 46, 30);
		contentPane.add(iconaDataNascitaLabel);
		
		iconaPasswordLabel = new JLabel("");
		iconaPasswordLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/password_16.png")));
		iconaPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaPasswordLabel.setBounds(295, 538, 46, 30);
		contentPane.add(iconaPasswordLabel);
	}
}
