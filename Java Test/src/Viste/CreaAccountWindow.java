package Viste;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.joda.time.LocalDate;

import Controller.ControllerErrori;
import Controller.ControllerStart;
import Entita.LuogoNascita;
import ImplementazioneDAO.LuogoNascitaDAOPSQL;
import InterfacceDAO.LuogoNascitaDAO;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.time.Month;
import java.time.DayOfWeek;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreaAccountWindow extends JFrame {

	//ATTRIBUTI
	//Attributi GUI
	private ControllerStart controller;
	private JPanel contentPane;
	private JTextField nomeTextField;
	private JTextField cognomeTextField;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	private JTextField indirizzoTextField;
	private JTextField telefonoTextField;
	private JTextField cellulareTextField;

	//Altri attributi
	private String nome;	//nome nuovo dipendente
	private String cognome;	//cognome nuovo dipendente
	private char sesso = 'M';	//sesso del nuovo dipendente (default = maschio)
	private LocalDate dataNascita = new LocalDate(1900,1,1);	//data di nascita del nuovo dipendente
	private ArrayList<String> anni = new ArrayList<String>();	//lista di anni per la data di nascita (1900-oggi)
	private LuogoNascita luogoNascita;	//luogo di nascita del nuovo dipendente
	private String email;
	private String password;
	private String telefono;
	private String cellulare;
	private String indirizzo;
	//METODI	
	
	//Crea il frame
	public CreaAccountWindow(ControllerStart controller) throws SQLException {
		this.controller = controller;
		setResizable(false);
		setTitle("Crea Account");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Label "Creazione Account" titolo
		JLabel titoloLabel = new JLabel("Creazione Account");
		titoloLabel.setBounds(34, 16, 195, 44);
		titoloLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		
		//Label "Nome*"
		JLabel nomeLabel = new JLabel("Nome*");
		nomeLabel.setBounds(34, 78, 46, 20);
		nomeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//TextField nome
		nomeTextField = new JTextField();
		//proprietà
		nomeTextField.setBounds(34, 100, 212, 23);
		nomeTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		nomeTextField.setColumns(10);
		
		//TextField cognome
		cognomeTextField = new JTextField();
		//proprietà
		cognomeTextField.setBounds(34, 163, 212, 23);
		cognomeTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		cognomeTextField.setColumns(10);
		
		//Label "Cognome*"
		JLabel cognomeLabel = new JLabel("Cognome*");
		cognomeLabel.setBounds(34, 141, 69, 20);
		cognomeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//ComboBox sesso
		JComboBox sessoComboBox = new JComboBox();
		sessoComboBox.setBounds(33, 223, 91, 26);
		sessoComboBox.setModel(new DefaultComboBoxModel(new String[] {"Maschio", "Femmina"}));
		sessoComboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//Label "Sesso*"
		JLabel sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(33, 197, 45, 20);
		sessoLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//Label "Data di nascita*"
		JLabel dataNascitaLabel = new JLabel("Data di nascita*");
		dataNascitaLabel.setBounds(33, 276, 106, 20);
		dataNascitaLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//ComboBox giorno di nascita
		JComboBox giornoComboBox = new JComboBox();
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setBounds(33, 302, 46, 26);
		giornoComboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//ComboBox mese di nascita
		JComboBox meseComboBox = new JComboBox();
		meseComboBox.setBounds(97, 302, 90, 26);
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"}));
		meseComboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//ComboBox anno di nascita
		anni.clear();	//inizializza la lista di anni disponibili (1900-ora)
		for (int i=1900; i<LocalDate.now().getYear();i++)
			anni.add(Integer.toString(i));
		JComboBox annoComboBox = new JComboBox(anni.toArray());
		annoComboBox.setSelectedIndex((int)anni.size()/2);
		annoComboBox.setBounds(197, 302, 75, 26);
		annoComboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//Label "Luogo di nascita*"
		JLabel luogoNascitaLabel = new JLabel("Luogo di nascita*");
		luogoNascitaLabel.setBounds(33, 346, 114, 20);
		luogoNascitaLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//Label "Comune"
		JLabel comuneLabel = new JLabel("Comune");
		comuneLabel.setBounds(38, 409, 53, 20);
		comuneLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//ComboBox comuni
		JComboBox comuneComboBox = new JComboBox();
		comuneComboBox.setBounds(94, 406, 201, 26);
		comuneComboBox.setEnabled(false);
		comuneComboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//Label "Prov."
		JLabel provinciaLabel = new JLabel("Prov.");
		provinciaLabel.setBounds(38, 375, 32, 20);
		provinciaLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//ComboBox province
		//inizializza i valori della combobox
		JComboBox provinciaComboBox = new JComboBox(controller.ottieniProvince().toArray());
		provinciaComboBox.addActionListener(new ActionListener() {
			//Action performed selezione
			public void actionPerformed(ActionEvent e) {
				comuneComboBox.setEnabled(true); //attiva il menù dei comuni
				comuneComboBox.removeAllItems();	//pulisce la lista del menù
				try {
					for(LuogoNascita comune: controller.ottieniComuni(provinciaComboBox.getSelectedItem().toString()))
						comuneComboBox.addItem(comune.getNomeComune());
				} catch (SQLException e1) {
					ControllerErrori errorCTRL = new ControllerErrori("Errore codice " + e1.getErrorCode() + "\nErrore raccolta informazioni dal database.", true);
				}
			}
		});
		provinciaComboBox.setBounds(73, 372, 178, 26);
		provinciaComboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//Separator separatore
		JSeparator separator = new JSeparator();
		separator.setBounds(360, 67, 10, 401);
		separator.setOrientation(SwingConstants.VERTICAL);
		
		//Label "Email*"
		JLabel emailLabel = new JLabel("Email*");
		emailLabel.setBounds(388, 76, 44, 20);
		emailLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//TextField email
		emailTextField = new JTextField();
		emailTextField.setBounds(388, 102, 225, 23);
		emailTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		emailTextField.setColumns(10);
		
		//Label "Password*"
		JLabel passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(388, 136, 70, 20);
		passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//PasswordField password account
		passwordField = new JPasswordField();
		passwordField.setBounds(388, 162, 225, 20);
		
		//Label "Indirizzo*"
		JLabel indirizzoLabel = new JLabel("Indirizzo*");
		indirizzoLabel.setBounds(388, 200, 70, 20);
		indirizzoLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//TextField indirizzo
		indirizzoTextField = new JTextField();
		indirizzoTextField.setBounds(388, 226, 252, 23);
		indirizzoTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		indirizzoTextField.setColumns(10);
		
		//Label "Telefono casa"
		JLabel telefonoLabel = new JLabel("Telefono casa");
		telefonoLabel.setBounds(388, 267, 91, 20);
		telefonoLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//TextField telefono di casa
		telefonoTextField = new JTextField();
		telefonoTextField.setBounds(388, 293, 164, 23);
		telefonoTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		telefonoTextField.setColumns(10);
		
		//Label "Cellulare"
		JLabel cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setBounds(388, 334, 58, 20);
		cellulareLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//TextField cellulare
		cellulareTextField = new JTextField();
		cellulareTextField.setBounds(388, 360, 164, 23);
		cellulareTextField.setFont(new Font("Calibri", Font.PLAIN, 16));
		cellulareTextField.setColumns(10);
		
		//Label "* = campi obbligatori"
		JLabel campiObbligatoriLabel = new JLabel("* = campi obbligatori");
		campiObbligatoriLabel.setBounds(5, 563, 104, 20);
		campiObbligatoriLabel.setFont(new Font("Calibri", Font.ITALIC, 12));
		
		//Button "Crea Account"
		JButton creaAccountButton = new JButton("Crea Account");
		//EVENTI
		creaAccountButton.addMouseListener(new MouseAdapter() {
			//click mouse destro
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					creaAccount(sessoComboBox,giornoComboBox,meseComboBox,annoComboBox,provinciaComboBox,comuneComboBox);	//Crea account
				} catch (SQLException e1) {
					ControllerErrori errorCTRL = new ControllerErrori("Errore codice " + e1.getErrorCode() + "\nErrore nel recupero informazioni dal database.", true);
				}
			}
		});
		creaAccountButton.setBounds(614, 554, 131, 29);
		creaAccountButton.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		contentPane.setLayout(null);
		contentPane.add(campiObbligatoriLabel);
		contentPane.add(creaAccountButton);
		contentPane.add(cognomeLabel);
		contentPane.add(nomeLabel);
		contentPane.add(titoloLabel);
		contentPane.add(cognomeTextField);
		contentPane.add(nomeTextField);
		contentPane.add(sessoComboBox);
		contentPane.add(sessoLabel);
		contentPane.add(dataNascitaLabel);
		contentPane.add(giornoComboBox);
		contentPane.add(meseComboBox);
		contentPane.add(annoComboBox);
		contentPane.add(luogoNascitaLabel);
		contentPane.add(provinciaLabel);
		contentPane.add(provinciaComboBox);
		contentPane.add(comuneLabel);
		contentPane.add(comuneComboBox);
		contentPane.add(separator);
		contentPane.add(emailLabel);
		contentPane.add(emailTextField);
		contentPane.add(passwordLabel);
		contentPane.add(passwordField);
		contentPane.add(indirizzoLabel);
		contentPane.add(indirizzoTextField);
		contentPane.add(telefonoLabel);
		contentPane.add(telefonoTextField);
		contentPane.add(cellulareLabel);
		contentPane.add(cellulareTextField);
	}
	
	//Metodo che salva i dati del nuovo account e li manda al controller per creare il nuovo account nel DB
	private void creaAccount(JComboBox sessoCB,JComboBox giornoCB,JComboBox meseCB,JComboBox annoCB,JComboBox provCB,JComboBox comCB) throws SQLException {
		//prende i dati dagli input della GUI
		nome = nomeTextField.getText();	//nome
		cognome = cognomeTextField.getText();	//cognome
		//sesso
		if (sessoCB.getSelectedItem().equals("Maschio")) 
			sesso = 'M';
		else
			sesso = 'F';
		//data di nascita
		dataNascita = new LocalDate(annoCB.getSelectedIndex() + 1900, meseCB.getSelectedIndex() + 1, giornoCB.getSelectedIndex() + 1);
		//luogo di nascita
		luogoNascita = controller.ottieniComuni((String) provCB.getSelectedItem()).get(comCB.getSelectedIndex());
		email = emailTextField.getText(); //email
		password = passwordField.getText();	//password
		if (!telefonoTextField.getText().equals(""))
			telefono = telefonoTextField.getText();	//telefono
		if (!cellulareTextField.getText().equals(""))
			telefono = cellulareTextField.getText();	//cellulare
		indirizzo = indirizzoTextField.getText();	//indirizzo
		
		controller.creaAccount(nome, cognome, sesso, dataNascita, luogoNascita, email, password, telefono, cellulare, indirizzo);	//mandali al controller che prova a creare il nuovo dipendente con il dao
	}	
}
