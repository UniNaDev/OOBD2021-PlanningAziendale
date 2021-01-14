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
import entita.Skill;

import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTextPane;


public class NuovoDipendente extends JFrame {

	
	//Attributi GUI
	private JPanel contentPane;
	
	//Label
	private JLabel signInLabel;
	private JLabel nomeLabel;
	private JLabel cognomeLabel;
	private JLabel sessoLabel;
	private JLabel indirizzoLabel;
	private JLabel emailLabel;
	private JLabel dataNascitaLabel;
	private JLabel provNascitaLabel;
	private JLabel telefonoFissoLabel;
	private JLabel cellulareLabel;
	private JLabel passwordLabel;
	private JLabel confermaPasswordLabel;
	private JLabel iconaNomeLabel;
	private JLabel iconaEmailLabel;
	private JLabel iconaIndirizzoLabel;
	private JLabel iconaCellulareLabel;
	private JLabel iconaDataNascitaLabel;
	private JLabel iconaPasswordLabel;
	private JLabel euroLabel;
	private JLabel salarioLabel;
	private JLabel iconaSalarioLabel;
	private JLabel cittàDiNascitaLabel;
	private JLabel skillsLabel;
	private JLabel iconaSkillsLabel;
	
	//TextField
	private JTextField nomeTextField;
	private JTextField cognomeTextField;
	private JTextField emailTextField;
	private JRadioButton uomoRadioButton;
	private JRadioButton donnaRadioButton;
	private JComboBox<?> giornoComboBox;
	private JComboBox<?> meseComboBox;
	private JComboBox<String> annoComboBox;
	private JComboBox<?> provinciaComboBox;
	private JComboBox<String> cittaComboBox;
	private JTextField indirizzoTextField;
	private JTextField cellulareTextField;
	private JTextField telefonoFissoTextField;
	private JPasswordField passwordField;
	private JPasswordField confermaPasswordField;
	
	private JScrollPane skillsScrollPane;
	private JList skillsList;
;
	private JButton nuovaSkillButton;
	private JTextField nuovaSkillTextField;
	private JTextField salarioTextField;

	
	
	//Button
	private JButton creaAccountButton;
	private JButton annullaButton;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private ControllerScelta theController;
	
	private JLabel showPasswordLabel;
	private JLabel showConfirmPasswordLabel;
	
	private ArrayList<String> anni = new ArrayList<String>();	//lista di anni per la data di nascita (1900-oggi)

	
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
		nomeLabel = new JLabel("Nome*");
		nomeLabel.setBounds(176, 148, 66, 14);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Cognome*"
		cognomeLabel = new JLabel("Cognome*");
		cognomeLabel.setBounds(312, 148, 84, 14);
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		cognomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Sesso*"
		sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(173, 244, 46, 14);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Indirizzo*"
		indirizzoLabel = new JLabel("Indirizzo*");
		indirizzoLabel.setBounds(175, 416, 84, 14);
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Email*"
		emailLabel = new JLabel("Email*");
		emailLabel.setBounds(177, 191, 66, 14);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Data di nascita*"
		dataNascitaLabel = new JLabel("Data di nascita*");
		dataNascitaLabel.setBounds(174, 282, 117, 14);
		dataNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Luogo di nascita*"
		provNascitaLabel = new JLabel("Prov. di nascita*");
		provNascitaLabel.setBounds(175, 315, 128, 14);
		provNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		provNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Telefono fisso"
		telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setBounds(174, 491, 103, 14);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Cellulare"
		cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setBounds(174, 460, 84, 14);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Label "Password*"
		passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(177, 539, 66, 14);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Conferma Password*"
		confermaPasswordLabel = new JLabel("Conferma Password*");
		confermaPasswordLabel.setBounds(177, 564, 128, 14);
		confermaPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		confermaPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//IconLabel
		signInLabel = new JLabel("Sign In");
		signInLabel.setBounds(384, 11, 226, 75);
		signInLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/registration_64.png")));
		signInLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setBounds(119, 129, 46, 14);
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/nome_16.png")));
		
		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setBounds(119, 170, 46, 14);
		iconaEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaEmailLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/email_16.png")));
		
		iconaIndirizzoLabel = new JLabel("");
		iconaIndirizzoLabel.setBounds(119, 383, 46, 30);
		iconaIndirizzoLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/indirizzo_16.png")));
		iconaIndirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaCellulareLabel = new JLabel("");
		iconaCellulareLabel.setBounds(119, 450, 46, 30);
		iconaCellulareLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/cellulare_16.png")));
		iconaCellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaDataNascitaLabel = new JLabel("");
		iconaDataNascitaLabel.setBounds(119, 269, 46, 30);
		iconaDataNascitaLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/dataNascita_16.png")));
		iconaDataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iconaPasswordLabel = new JLabel("");
		iconaPasswordLabel.setBounds(121, 527, 46, 30);
		iconaPasswordLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/Icone/password_16.png")));
		iconaPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Text Field per il nome
		nomeTextField = new JTextField();
		nomeTextField.setBounds(175, 126, 130, 20);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomeTextField.setColumns(10);
		
		//Text Field per il cognome
		cognomeTextField = new JTextField();
		cognomeTextField.setBounds(312, 126, 130, 20);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cognomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cognomeTextField.setColumns(10);
		
		//Text Field per l'email
		emailTextField = new JTextField();
		emailTextField.setBounds(175, 167, 267, 20);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		//Radio Button per uomo
		uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setBounds(239, 240, 61, 23);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		
		//Radio Button per donna
		donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setBounds(311, 240, 66, 23);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(donnaRadioButton);
		
		//Combo Box giorni del mese
		giornoComboBox = new JComboBox<Object>();
		
		//modifica lo stille della combo box
		giornoComboBox.setUI(new BasicComboBoxUI());
		
		giornoComboBox.setBounds(306, 274, 44, 22);
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		giornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoComboBox.setSelectedIndex(0);
		//Combo Box mesi dell'anno
		meseComboBox = new JComboBox<Object>();
		
		//modifica lo stille della combo box
		meseComboBox.setUI(new BasicComboBoxUI());
		
		meseComboBox.setBounds(360, 274, 44, 22);
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Combo Box anni
		for (int i = 1900; i < LocalDate.now().getYear(); i++)
			anni.add(String.valueOf(i));
		annoComboBox = new JComboBox(anni.toArray());
		
		//modifica lo stille della combo box
		annoComboBox.setUI(new BasicComboBoxUI());
		
		annoComboBox.setBounds(414, 274, 66, 22);
		annoComboBox.setBackground(Color.WHITE);
		annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		annoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoComboBox.setSelectedIndex((int) annoComboBox.getItemCount()/2);
		
		//ComboBox comuni
		cittaComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		cittaComboBox.setUI(new BasicComboBoxUI());
		
		cittaComboBox.setEnabled(false);
		cittaComboBox.setBounds(307, 338, 210, 22);
		cittaComboBox.setBackground(Color.WHITE);
		cittaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cittaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		cittaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		try {
			//Combo Box province
			provinciaComboBox = new JComboBox(theController.ottieniProvince().toArray());
			provinciaComboBox.addActionListener(new ActionListener() {
				//Action performed selezione
				public void actionPerformed(ActionEvent e) {
					cittaComboBox.setEnabled(true); //attiva il menù dei comuni
					cittaComboBox.removeAllItems();	//pulisce la lista del menù
					try {
						//prova a ottenere i comune dal DB e inserirli nella corrispondente combo box
						for(LuogoNascita comune: theController.ottieniComuni(provinciaComboBox.getSelectedItem().toString()))
								cittaComboBox.addItem(comune.getNomeComune());
					} 
					catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								e1.getMessage(),
								"Errore #" + e1.getErrorCode(),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,
					e1.getMessage(),
					"Errore #" + e1.getErrorCode(),
					JOptionPane.ERROR_MESSAGE);
		}
		
		//modifica lo stille della combo box
		provinciaComboBox.setUI(new BasicComboBoxUI());
		
		provinciaComboBox.setBounds(307, 307, 210, 22);
		provinciaComboBox.setBackground(Color.WHITE);
		provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Text Field indirizzo
		indirizzoTextField = new JTextField();
		indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		indirizzoTextField.setColumns(10);
		indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		indirizzoTextField.setBounds(175, 393, 267, 20);
		
		//Text Field telefono di casa
		telefonoFissoTextField = new JTextField();
		telefonoFissoTextField.setBounds(287, 486, 86, 20);
		telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		telefonoFissoTextField.setColumns(10);
		
		//Text Field cellulare
		cellulareTextField = new JTextField();
		cellulareTextField.setBounds(287, 455, 86, 20);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cellulareTextField.setColumns(10);
		
		//Password Field per la password
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(317, 528, 125, 20);
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Password Field per conferma password
		confermaPasswordField = new JPasswordField();
		confermaPasswordField.setEchoChar('*');
		confermaPasswordField.setBounds(317, 559, 125, 20);
		confermaPasswordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confermaPasswordField.setHorizontalAlignment(SwingConstants.LEFT);

		//Button "Crea Account"
		creaAccountButton = new JButton("Crea Account");
		creaAccountButton.setBounds(840, 627, 134, 23);
		creaAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaAccountButton.setBackground(Color.WHITE);
		creaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		//Eventi connessi al pulsante "Crea Account"
		creaAccountButton.addActionListener(new ActionListener() {
			//click del pulsante
			public void actionPerformed(ActionEvent e) {
				//resetta i colori delle label per i valori not null
				nomeLabel.setForeground(Color.BLACK);
				cognomeLabel.setForeground(Color.BLACK);
				emailLabel.setForeground(Color.BLACK);
				passwordLabel.setForeground(Color.BLACK);
				sessoLabel.setForeground(Color.BLACK);
				confermaPasswordLabel.setForeground(Color.BLACK);
				cittàDiNascitaLabel.setForeground(Color.BLACK);
				provNascitaLabel.setForeground(Color.BLACK);
				
				
				if ((!nomeTextField.getText().isBlank() && !cognomeTextField.getText().isBlank() && !emailTextField.getText().isBlank() && !passwordField.getText().isBlank()) && confermaPasswordField.getText().equals(passwordField.getText()))
					try {
						creaAccount();	//crea il nuovo account con i valori inseriti
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								e1.getMessage(),
								"Errore #" + e1.getErrorCode(),
								JOptionPane.ERROR_MESSAGE);
					}
				//se le password inserite sono diverse
				else if(!passwordField.getText().equals(confermaPasswordField.getText()))
				{
					JOptionPane.showMessageDialog(null, "Le password inserite sono diverse");
					passwordLabel.setForeground(Color.RED);
					confermaPasswordLabel.setForeground(Color.RED);
					
				}
				//se uno dei campi obbligatori è vuoto colora la rispettiva label di rosso
				else if ((nomeTextField.getText().isBlank() || cognomeTextField.getText().isBlank() || emailTextField.getText().isBlank() || passwordField.getText().isBlank()) || confermaPasswordField.getText().equals(passwordField.getText()) || !cittaComboBox.isEnabled()) {
					
					JOptionPane.showMessageDialog(null, "Compilare i campi obbligatori vuoti");
					if (nomeTextField.getText().isBlank())
						nomeLabel.setForeground(Color.RED);
					if (cognomeTextField.getText().isBlank())
						cognomeLabel.setForeground(Color.RED);
					if (emailTextField.getText().isBlank())
						emailLabel.setForeground(Color.RED);
					if (passwordField.getText().isBlank())
						passwordLabel.setForeground(Color.RED);
					if(!cittaComboBox.isEnabled()) {
						cittàDiNascitaLabel.setForeground(Color.RED);
						provNascitaLabel.setForeground(Color.RED);
					}
					if(!uomoRadioButton.isSelected() && !donnaRadioButton.isSelected())
						sessoLabel.setForeground(Color.RED);
					if (confermaPasswordField.getText().isBlank())
						confermaPasswordLabel.setForeground(Color.RED);
				
				}
			
			
		

			}
		});
		creaAccountButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaAccountButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaAccountButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		
		//Button "Annulla"
		annullaButton = new JButton("Annulla");
		annullaButton.setBounds(10, 627, 97, 23);
		annullaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annullaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annullaButton.setBackground(Color.WHITE);
		annullaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		//Eventi connessi al button "Annulla"
		annullaButton.addActionListener(new ActionListener() {
			//click sinistro del mouse
			public void actionPerformed(ActionEvent e) {
				
				theController.annulla();	//annulla tutto e torna alla schermata principale
			}
		});
		annullaButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				annullaButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				annullaButton.setBackground(Color.WHITE);	//smette di evidenziare il pulsante
			}
		});
		
		contentPane.setLayout(null);
		contentPane.add(signInLabel);
		contentPane.add(iconaNomeLabel);
		contentPane.add(nomeTextField);
		contentPane.add(cognomeTextField);
		contentPane.add(nomeLabel);
		contentPane.add(cognomeLabel);
		contentPane.add(iconaEmailLabel);
		contentPane.add(emailTextField);
		contentPane.add(emailLabel);
		contentPane.add(sessoLabel);
		contentPane.add(uomoRadioButton);
		contentPane.add(donnaRadioButton);
		contentPane.add(iconaDataNascitaLabel);
		contentPane.add(dataNascitaLabel);
		contentPane.add(giornoComboBox);
		contentPane.add(meseComboBox);
		contentPane.add(annoComboBox);
		contentPane.add(provNascitaLabel);
		contentPane.add(provinciaComboBox);
		contentPane.add(cittaComboBox);
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
		contentPane.add(confermaPasswordLabel);
		contentPane.add(confermaPasswordField);
		contentPane.add(annullaButton);
		contentPane.add(creaAccountButton);
		contentPane.add(indirizzoTextField);
		
		//Skills
		skillsScrollPane = new JScrollPane();
		skillsScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		skillsScrollPane.setBounds(603, 126, 290, 164);
		contentPane.add(skillsScrollPane);
		//List di skill
		try {
			skillsList = new JList(theController.ottieniSkill().toArray());
			skillsList.setToolTipText("");
			skillsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			skillsList.setFont(new Font("Consolas", Font.PLAIN, 12));
			skillsScrollPane.setViewportView(skillsList);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,
					e1.getMessage(),
					"Errore #" + e1.getErrorCode(),
					JOptionPane.ERROR_MESSAGE);
		}
		
		skillsLabel = new JLabel("Skills");
		skillsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		skillsLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		skillsLabel.setBounds(603, 109, 66, 14);
		contentPane.add(skillsLabel);
		
		iconaSkillsLabel = new JLabel("");
		iconaSkillsLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/icone/skills_32.png")));
		iconaSkillsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaSkillsLabel.setBounds(531, 182, 60, 39);
		contentPane.add(iconaSkillsLabel);
		
		//Button "Crea Nuova Skill"
		nuovaSkillButton = new JButton("Crea Nuova Skill");
		//Eventi connessi al button "Crea Nuova Skill"
		nuovaSkillButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				nuovaSkillButton.setBackground(Color.LIGHT_GRAY);	//lo evidenzia
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				nuovaSkillButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}	
		});
		//click del pulsante
		nuovaSkillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaSkill();
			}
		});
		nuovaSkillButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovaSkillButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		nuovaSkillButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovaSkillButton.setBackground(Color.WHITE);
		nuovaSkillButton.setBounds(754, 307, 139, 23);
		contentPane.add(nuovaSkillButton);
		
		//Text Field per il nome della nuova skill
		nuovaSkillTextField = new JTextField();
		nuovaSkillTextField.setColumns(10);
		nuovaSkillTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovaSkillTextField.setBounds(603, 308, 141, 22);
		contentPane.add(nuovaSkillTextField);
		
		//Text Field per il salario
		salarioTextField = new JTextField("0.00");
		salarioTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioTextField.setColumns(10);
		salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salarioTextField.setBounds(603, 389, 141, 22);
		contentPane.add(salarioTextField);
		
		//Label "€"
		euroLabel = new JLabel("€");
		euroLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		euroLabel.setBounds(753, 383, 66, 36);
		contentPane.add(euroLabel);
		
		//Label "Salario"
		salarioLabel = new JLabel("Salario");
		salarioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		salarioLabel.setBounds(603, 416, 66, 14);
		contentPane.add(salarioLabel);
		
		//Icona salario
		iconaSalarioLabel = new JLabel("");
		iconaSalarioLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/icone/salario_16.png")));
		iconaSalarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaSalarioLabel.setBounds(545, 383, 46, 30);
		contentPane.add(iconaSalarioLabel);
		
		showPasswordLabel = new JLabel("");
		showPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showPasswordLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/icone/showpass_24.png")));
		showPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				passwordField.setEchoChar((char)0); //quando si tiene premuto con il mouse sull icona mostra il testo non nascosto
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				passwordField.setEchoChar('*'); //quando si rilascia il mouse mostra di nuovo il testo nascosto
			}
		});
		showPasswordLabel.setBounds(448, 526, 46, 26);
		contentPane.add(showPasswordLabel);
		
		showConfirmPasswordLabel = new JLabel("");
		showConfirmPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showConfirmPasswordLabel.setIcon(new ImageIcon(NuovoDipendente.class.getResource("/icone/showpass_24.png")));
		showConfirmPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				confermaPasswordField.setEchoChar((char)0);	//quando si tiene premuto con il mouse sull icona mostra il testo non nascosto
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				confermaPasswordField.setEchoChar('*');	//quando si rilascia il mouse mostra di nuovo il testo nascosto
			}
		});
		showConfirmPasswordLabel.setBounds(448, 556, 47, 29);
		contentPane.add(showConfirmPasswordLabel);
		
		JLabel inserireSkillLabel = new JLabel("N.B. Per inserire più skill contemporanemante");
		inserireSkillLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		inserireSkillLabel.setHorizontalAlignment(SwingConstants.LEFT);
		inserireSkillLabel.setBounds(603, 338, 290, 20);
		contentPane.add(inserireSkillLabel);
		
		JLabel premereCtrlLabel = new JLabel("premere CTRL+clic tasto sx mouse sulla skill");
		premereCtrlLabel.setHorizontalAlignment(SwingConstants.LEFT);
		premereCtrlLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		premereCtrlLabel.setBounds(603, 355, 290, 20);
		contentPane.add(premereCtrlLabel);
		
		JLabel campiObbligatoriLabel = new JLabel("* Campi obbligatori");
		campiObbligatoriLabel.setHorizontalAlignment(SwingConstants.LEFT);
		campiObbligatoriLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		campiObbligatoriLabel.setBounds(603, 460, 290, 20);
		contentPane.add(campiObbligatoriLabel);
		
		cittàDiNascitaLabel = new JLabel("Città di nascita*");
		cittàDiNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cittàDiNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		cittàDiNascitaLabel.setBounds(175, 340, 128, 14);
		contentPane.add(cittàDiNascitaLabel);
	}
		
		//Metodo che salva i dati del nuovo account e li manda al controller per creare il nuovo account nel DB
		private void creaAccount() throws SQLException {
			String nome;	//nome nuovo dipendente
			String cognome;	//cognome nuovo dipendente
			char sesso = 'M';	//sesso del nuovo dipendente (default = maschio)
			LocalDate dataNascita = new LocalDate(1900,1,1);	//data di nascita del nuovo dipendente
			LuogoNascita luogoNascita;	//luogo di nascita del nuovo dipendente
			String email;	//email del dipendente
			String password;	//password del dipendente
			String telefono = null;	//numero di telefono di casa
			String cellulare = null;	//cellulare
			String indirizzo;	//indirizzo
			
			//prende i dati dagli input della GUI
			nome = nomeTextField.getText();	//nome
			cognome = cognomeTextField.getText();	//cognome
			//sesso
			if (uomoRadioButton.isSelected())
				sesso = 'M';
			else
				sesso = 'F';
			//data di nascita
			dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() + 1, giornoComboBox.getSelectedIndex()+1);
			//luogo di nascita
			luogoNascita = theController.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cittaComboBox.getSelectedIndex());
			email = emailTextField.getText(); //email
			password = passwordField.getText();	//password
			if (!telefonoFissoTextField.getText().equals(""))
				telefono = telefonoFissoTextField.getText();	//telefono
			if (!cellulareTextField.getText().equals(""))
				cellulare = cellulareTextField.getText();	//cellulare
			indirizzo = indirizzoTextField.getText();	//indirizzo
			//ottiene le skill selezionate
			ArrayList<Skill> skills = new ArrayList<Skill>();
			skills.addAll(skillsList.getSelectedValuesList());
			float salario = Float.valueOf(salarioTextField.getText());	//ottieni il salario
			theController.creaAccount(nome, cognome, sesso, dataNascita, luogoNascita, email, password, telefono, cellulare, indirizzo, skills, salario);	//mandali al controller che prova a creare il nuovo dipendente con il dao
		}
		
		//Metodo che crea una nuova skill e aggiorna la lista delle skill disponibili
		private void creaSkill() {
			if (!nuovaSkillTextField.getText().isBlank()) {
				try {
					theController.creaNuovaSkill(nuovaSkillTextField.getText());	//inserisce la nuova skill nel database
					DefaultListModel<Skill> skillModel = new DefaultListModel<Skill>();	//aggiorna la lista delle skill
					skillModel.addAll(theController.ottieniSkill());
					skillsList.setModel(skillModel);
					nuovaSkillTextField.setText(""); //svuota il campo
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							e.getMessage(),
							"Errore #" + e.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
}
