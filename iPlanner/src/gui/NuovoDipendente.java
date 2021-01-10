package gui;
import controller.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JComboBox;


import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import org.joda.time.LocalDate;

import entita.LuogoNascita;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Toolkit;


public class NuovoDipendente extends JFrame {

	
	//Attributi GUI
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
	private JComboBox<String> yearComboBox;
	private JComboBox<?> provinciaComboBox;
	private JComboBox<String> cityComboBox;
	private JTextField indirizzoTextField;
	private JTextField cellulareTextField;
	private JTextField telefonoFissoTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	
	//Button
	private JButton createAccountButton;
	private JButton cancelCreationButton;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private ControllerScelta theController;
	
	//Altri attributi
	private String nome;	//nome nuovo dipendente
	private String cognome;	//cognome nuovo dipendente
	private char sesso = 'M';	//sesso del nuovo dipendente (default = maschio)
	private LocalDate dataNascita = new LocalDate(1900,1,1);	//data di nascita del nuovo dipendente
	private ArrayList<String> anni = new ArrayList<String>();	//lista di anni per la data di nascita (1900-oggi)
	private LuogoNascita luogoNascita;	//luogo di nascita del nuovo dipendente
	private String email;	//email del dipendente
	private String password;	//password del dipendente
	private String telefono;	//numero di telefono di casa
	private String cellulare;	//cellulare
	private String indirizzo;	//indirizzo

	
	/**
	 * Create the frame.
	 */
	public NuovoDipendente(ControllerScelta theController) {
		this.theController = theController;	//ottiene il controller scelta
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuovoDipendente.class.getResource("/Icone/WindowIcon_16.png")));
		setResizable(false);
	
		setTitle("iPlanner - Sign in");
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		//Viene visualizzata al centro dello schermo
		setLocationRelativeTo(null);
		
		//Label "Nome*"
		nameLabel = new JLabel("Nome*");
		nameLabel.setBounds(352, 147, 66, 14);
		nameLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Cognome*"
		surnameLabel = new JLabel("Cognome*");
		surnameLabel.setBounds(488, 147, 84, 14);
		surnameLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		surnameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Sesso*"
		sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(351, 244, 46, 14);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Indirizzo*"
		indirizzoLabel = new JLabel("Indirizzo*");
		indirizzoLabel.setBounds(351, 419, 84, 14);
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Email*"
		emailLabel = new JLabel("Email*");
		emailLabel.setBounds(353, 190, 66, 14);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Data di nascita*"
		bornDateLabel = new JLabel("Data di nascita*");
		bornDateLabel.setBounds(351, 282, 117, 14);
		bornDateLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		bornDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Luogo di nascita*"
		bornPlaceLabel = new JLabel("Luogo di nascita*");
		bornPlaceLabel.setBounds(351, 324, 128, 14);
		bornPlaceLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		bornPlaceLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Telefono fisso"
		telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setBounds(351, 489, 103, 14);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Cellulare"
		cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setBounds(351, 458, 84, 14);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Password*"
		passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(351, 550, 66, 14);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Conferma Password*"
		confirmPasswordLabel = new JLabel("Conferma Password*");
		confirmPasswordLabel.setBounds(351, 575, 128, 14);
		confirmPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//IconLabel
		signInLabel = new JLabel("Sign In");
		signInLabel.setBounds(384, 11, 226, 75);
		signInLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/registration_64.png")));
		signInLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setBounds(295, 128, 46, 14);
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/nome_16.png")));
		
		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setBounds(295, 169, 46, 14);
		iconaEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaEmailLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/email_16.png")));
		
		iconaIndirizzoLabel = new JLabel("");
		iconaIndirizzoLabel.setBounds(295, 386, 46, 30);
		iconaIndirizzoLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/indirizzo_16.png")));
		iconaIndirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaCellulareLabel = new JLabel("");
		iconaCellulareLabel.setBounds(296, 448, 46, 30);
		iconaCellulareLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/cellulare_16.png")));
		iconaCellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaDataNascitaLabel = new JLabel("");
		iconaDataNascitaLabel.setBounds(296, 269, 46, 30);
		iconaDataNascitaLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/dataNascita_16.png")));
		iconaDataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaPasswordLabel = new JLabel("");
		iconaPasswordLabel.setBounds(295, 538, 46, 30);
		iconaPasswordLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/password_16.png")));
		iconaPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Text Field per il nome
		nameTextField = new JTextField();
		nameTextField.setBounds(351, 125, 130, 20);
		nameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nameTextField.setColumns(10);
		
		//Text Field per il cognome
		surnameTextField = new JTextField();
		surnameTextField.setBounds(488, 125, 130, 20);
		surnameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		surnameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		surnameTextField.setColumns(10);
		
		//Text Field per l'email
		emailTextField = new JTextField();
		emailTextField.setBounds(351, 166, 267, 20);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		//Radio Button per uomo
		uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setBounds(417, 240, 61, 23);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		
		//Radio Button per donna
		donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setBounds(489, 240, 66, 23);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(donnaRadioButton);
		
		//Combo Box giorni del mese
		dayComboBox = new JComboBox<Object>();
		dayComboBox.setBounds(483, 274, 44, 22);
		dayComboBox.setBackground(Color.WHITE);
		dayComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dayComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		dayComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Combo Box mesi dell'anno
		monthComboBox = new JComboBox<Object>();
		monthComboBox.setBounds(537, 274, 44, 22);
		monthComboBox.setBackground(Color.WHITE);
		monthComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		monthComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Combo Box anni
		for (int i = 1900; i < LocalDate.now().getYear(); i++)
			anni.add(String.valueOf(i));
		yearComboBox = new JComboBox(anni.toArray());
		yearComboBox.setBounds(591, 274, 66, 22);
		yearComboBox.setBackground(Color.WHITE);
		yearComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		yearComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		yearComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		yearComboBox.setSelectedIndex((int) yearComboBox.getItemCount()/2);
		
		//ComboBox comuni
		cityComboBox = new JComboBox();
		cityComboBox.setEnabled(false);
		cityComboBox.setBounds(483, 347, 210, 22);
		cityComboBox.setBackground(Color.WHITE);
		cityComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cityComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		cityComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		try {
			//Combo Box province
			provinciaComboBox = new JComboBox(theController.ottieniProvince().toArray());
			provinciaComboBox.addActionListener(new ActionListener() {
				//Action performed selezione
				public void actionPerformed(ActionEvent e) {
					cityComboBox.setEnabled(true); //attiva il menù dei comuni
					cityComboBox.removeAllItems();	//pulisce la lista del menù
					try {
						//prova a ottenere i comune dal DB e inserirli nella corrispondente combo box
						for(LuogoNascita comune: theController.ottieniComuni(provinciaComboBox.getSelectedItem().toString()))
								cityComboBox.addItem(comune.getNomeComune());
					} 
					catch (SQLException e1) {
						JOptionPane.showMessageDialog(cityComboBox,
								e1.getMessage(),
								"Errore #" + e1.getErrorCode(),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(provinciaComboBox,
					e1.getMessage(),
					"Errore #" + e1.getErrorCode(),
					JOptionPane.ERROR_MESSAGE);
		}
		provinciaComboBox.setBounds(483, 316, 210, 22);
		provinciaComboBox.setBackground(Color.WHITE);
		provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Text Field indirizzo
		indirizzoTextField = new JTextField();
		indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		indirizzoTextField.setColumns(10);
		indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		indirizzoTextField.setBounds(351, 396, 267, 20);
		
		//Text Field telefono di casa
		telefonoFissoTextField = new JTextField();
		telefonoFissoTextField.setBounds(464, 484, 86, 20);
		telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		telefonoFissoTextField.setColumns(10);
		
		//Text Field cellulare
		cellulareTextField = new JTextField();
		cellulareTextField.setBounds(464, 453, 86, 20);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cellulareTextField.setColumns(10);
		
		//Password Field per la password
		passwordField = new JPasswordField();
		passwordField.setBounds(491, 539, 125, 20);
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Password Field per conferma password
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(491, 570, 125, 20);
		confirmPasswordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confirmPasswordField.setHorizontalAlignment(SwingConstants.LEFT);

		//Button "Crea Account"
		createAccountButton = new JButton("Crea Account");
		createAccountButton.setBounds(840, 627, 134, 23);
		createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		createAccountButton.setBackground(Color.WHITE);
		createAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		createAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		//Eventi connessi al pulsante "Crea Account"
		createAccountButton.addActionListener(new ActionListener() {
			//click del pulsante
			public void actionPerformed(ActionEvent e) {
				//resetta i colori delle label per i valori not null
				nameLabel.setForeground(Color.BLACK);
				surnameLabel.setForeground(Color.BLACK);
				emailLabel.setForeground(Color.BLACK);
				passwordLabel.setForeground(Color.BLACK);
				indirizzoLabel.setForeground(Color.BLACK);
				//se tutti i campi essenziali sono pieni e la password è confermata
				if ((!nameTextField.getText().isBlank() && !surnameTextField.getText().isBlank() && !emailTextField.getText().isBlank() && !passwordField.getText().isBlank() && !indirizzoTextField.getText().isBlank()) && confirmPasswordField.getText().equals(passwordField.getText()))
					try {
						creaAccount();	//crea il nuovo account con i valori inseriti
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(createAccountButton,
								e1.getMessage(),
								"Errore #" + e1.getErrorCode(),
								JOptionPane.ERROR_MESSAGE);
					}
				else //se invece uno dei valori essenziali è vuoto colora la rispettiva label di rosso
				{
					if (nameTextField.getText().isBlank()) {
						nameLabel.setForeground(Color.RED);
					}
					if (surnameTextField.getText().isBlank())
						surnameLabel.setForeground(Color.RED);
					if (emailTextField.getText().isBlank())
						emailLabel.setForeground(Color.RED);
					if (passwordField.getText().isBlank())
						passwordLabel.setForeground(Color.RED);
					if (indirizzoTextField.getText().isBlank())
						indirizzoLabel.setForeground(Color.RED);
					//se le due password non corrispondono colora le label e svuota il campo di conferma password
					if (passwordField.getText().equals(confirmPasswordField.getText())) {
						passwordLabel.setForeground(Color.RED);
						confirmPasswordLabel.setForeground(Color.RED);
						confirmPasswordLabel.setText("");
					}
				}
			}
		});
		createAccountButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				createAccountButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				createAccountButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		
		//Button "Annulla"
		cancelCreationButton = new JButton("Annulla");
		cancelCreationButton.setBounds(10, 627, 97, 23);
		cancelCreationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelCreationButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cancelCreationButton.setBackground(Color.WHITE);
		cancelCreationButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		//Eventi connessi al button "Annulla"
		cancelCreationButton.addActionListener(new ActionListener() {
			//click sinistro del mouse
			public void actionPerformed(ActionEvent e) {
				
				theController.annulla();	//annulla tutto e torna alla schermata principale
			}
		});
		cancelCreationButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				cancelCreationButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				cancelCreationButton.setBackground(Color.WHITE);	//smette di evidenziare il pulsante
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
	}
		
		//Metodo che salva i dati del nuovo account e li manda al controller per creare il nuovo account nel DB
		private void creaAccount() throws SQLException {
			//prende i dati dagli input della GUI
			nome = nameTextField.getText();	//nome
			cognome = surnameTextField.getText();	//cognome
			//sesso
			if (uomoRadioButton.isSelected())
				sesso = 'M';
			else
				sesso = 'F';
			//data di nascita
			dataNascita = new LocalDate(yearComboBox.getSelectedIndex() + 1900, monthComboBox.getSelectedIndex() + 1, dayComboBox.getSelectedIndex() + 1);
			//luogo di nascita
			luogoNascita = theController.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cityComboBox.getSelectedIndex());
			email = emailTextField.getText(); //email
			password = passwordField.getText();	//password
			if (!telefonoFissoTextField.getText().equals(""))
				telefono = telefonoFissoTextField.getText();	//telefono
			if (!cellulareTextField.getText().equals(""))
				telefono = cellulareTextField.getText();	//cellulare
			indirizzo = indirizzoTextField.getText();	//indirizzo
			theController.creaAccount(nome, cognome, sesso, dataNascita, luogoNascita, email, password, telefono, cellulare, indirizzo);	//mandali al controller che prova a creare il nuovo dipendente con il dao
		}	
}
