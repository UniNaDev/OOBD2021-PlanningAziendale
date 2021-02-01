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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class GestioneDipendenti extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	
	private JLabel dipendenteLabel;
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
	private JLabel mostraPasswordLabel;
	private JLabel mostraConfirmPasswordLabel;
	private JLabel campiObbligatoriLabel;
	private JLabel premereCtrlLabel;
	private JLabel inserireSkillLabel;
	
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
	private JTextField nuovaSkillTextField;
	private JTextField salarioTextField;

	private JScrollPane skillsScrollPane;
	private JList skillsList;

	private JButton nuovaSkillButton;
	private JButton creaAccountButton;
	private JButton esciButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	//Altri attributi	
	private ArrayList<String> anni = new ArrayList<String>();	//lista di anni per la data di nascita (1900-oggi)
	private JPanel comandiPanel;
	private JScrollPane tableScrollPanel;
	private JTable dipendentiTable;
	private JTextField cercaTextField;
	private JTextField etàMinimaTextField;
	private JLabel etàFiltroLabel;
	private JTextField etàMassimaLabel;
	private JTextField salarioMinimoTextField;
	private JLabel salarioFiltroLabel;
	private JTextField salarioMassimoTextField;
	private JTextField valutazioneMinimaTextField;
	private JLabel valutazioneFiltroLabel;
	private JTextField valutazioneMassimaTextField;
	private JButton eliminaAccountButton;

	
	//Creazione del frame
	//-----------------------------------------------------------------
	
	public GestioneDipendenti(ControllerDipendentiSegreteria controller) {
		//Evento finestra che si sta chiudendo
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.tornaAiPlanner();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneDipendenti.class.getResource("/Icone/WindowIcon_16.png")));
		setResizable(false);
		setTitle("iPlanner - Dipendenti");
		setBounds(100, 100, 1161, 944);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		//Viene visualizzata al centro dello schermo
		setLocationRelativeTo(null);
		
		//Combo Box anni
		for (int i = 1900; i < LocalDate.now().getYear(); i++)
			anni.add(String.valueOf(i));
		
		contentPane.setLayout(null);
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		infoPanel.setBounds(25, 11, 1094, 668);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//IconLabel
		dipendenteLabel = new JLabel("Dipendente");
		dipendenteLabel.setBounds(384, 11, 251, 75);
		infoPanel.add(dipendenteLabel);
		dipendenteLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/employee_64.png")));
		dipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setBounds(41, 128, 46, 14);
		infoPanel.add(iconaNomeLabel);
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/nome_16.png")));
		
		//Text Field per il nome
		nomeTextField = new JTextField();
		nomeTextField.setBounds(97, 128, 130, 20);
		infoPanel.add(nomeTextField);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomeTextField.setColumns(10);
		
		//Text Field per il cognome
		cognomeTextField = new JTextField();
		cognomeTextField.setBounds(234, 128, 130, 20);
		infoPanel.add(cognomeTextField);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cognomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cognomeTextField.setColumns(10);
		
		//Label "Nome*"
		nomeLabel = new JLabel("Nome*");
		nomeLabel.setBounds(97, 148, 66, 14);
		infoPanel.add(nomeLabel);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Cognome*"
		cognomeLabel = new JLabel("Cognome*");
		cognomeLabel.setBounds(234, 148, 84, 14);
		infoPanel.add(cognomeLabel);
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		cognomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setBounds(41, 172, 46, 14);
		infoPanel.add(iconaEmailLabel);
		iconaEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaEmailLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/email_16.png")));
		
		//Text Field per l'email
		emailTextField = new JTextField();
		emailTextField.setBounds(97, 169, 267, 20);
		infoPanel.add(emailTextField);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		//Label "Email*"
		emailLabel = new JLabel("Email*");
		emailLabel.setBounds(99, 193, 66, 14);
		infoPanel.add(emailLabel);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label "Sesso*"
		sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(95, 246, 46, 14);
		infoPanel.add(sessoLabel);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Radio Button per uomo
		uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setBounds(161, 242, 61, 23);
		infoPanel.add(uomoRadioButton);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		
		//Radio Button per donna
		donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setBounds(233, 242, 66, 23);
		infoPanel.add(donnaRadioButton);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(donnaRadioButton);
		
		iconaDataNascitaLabel = new JLabel("");
		iconaDataNascitaLabel.setBounds(41, 271, 46, 30);
		infoPanel.add(iconaDataNascitaLabel);
		iconaDataNascitaLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/dataNascita_16.png")));
		iconaDataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Label "Data di nascita*"
		dataNascitaLabel = new JLabel("Data di nascita*");
		dataNascitaLabel.setBounds(96, 284, 117, 14);
		infoPanel.add(dataNascitaLabel);
		dataNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Combo Box giorni del mese
		giornoComboBox = new JComboBox<Object>();
		giornoComboBox.setBounds(228, 276, 44, 22);
		infoPanel.add(giornoComboBox);
		//modifica lo stile della combo box
		giornoComboBox.setUI(new BasicComboBoxUI());
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		giornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoComboBox.setSelectedIndex(0);
		//Combo Box mesi dell'anno
		meseComboBox = new JComboBox<Object>();
		meseComboBox.setBounds(282, 276, 44, 22);
		infoPanel.add(meseComboBox);
		
		//modifica lo stile della combo box
		meseComboBox.setUI(new BasicComboBoxUI());
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoComboBox = new JComboBox(anni.toArray());
		annoComboBox.setBounds(336, 276, 66, 22);
		infoPanel.add(annoComboBox);
		
		//modifica lo stille della combo box
		annoComboBox.setUI(new BasicComboBoxUI());
		annoComboBox.setBackground(Color.WHITE);
		annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		annoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoComboBox.setSelectedIndex((int) annoComboBox.getItemCount()/2);
		
		//Label "Luogo di nascita*"
		provNascitaLabel = new JLabel("Prov. di nascita*");
		provNascitaLabel.setBounds(97, 317, 128, 14);
		infoPanel.add(provNascitaLabel);
		provNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		provNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		//Combo Box province
		try {
			provinciaComboBox = new JComboBox(controller.ottieniProvince().toArray());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		provinciaComboBox.setBounds(229, 309, 210, 22);
		infoPanel.add(provinciaComboBox);
		provinciaComboBox.addActionListener(new ActionListener() {
			//Action performed selezione
			public void actionPerformed(ActionEvent e) {
				cittaComboBox.setEnabled(true); //attiva il menù dei comuni
				cittaComboBox.removeAllItems();	//pulisce la lista del menù
				try {
					//prova a ottenere i comune dal DB e inserirli nella corrispondente combo box
					for(LuogoNascita comune: controller.ottieniComuni(provinciaComboBox.getSelectedItem().toString()))
							cittaComboBox.addItem(comune.getNomeComune());
				} 
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							"Non sono stati trovati i comuni nel DB",
							"Errore #" + e1.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//modifica lo stille della combo box
		provinciaComboBox.setUI(new BasicComboBoxUI());
		provinciaComboBox.setBackground(Color.WHITE);
		provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//ComboBox comuni
		cittaComboBox = new JComboBox();
		cittaComboBox.setBounds(229, 340, 210, 22);
		infoPanel.add(cittaComboBox);
		
			//modifica lo stille della combo box
			cittaComboBox.setUI(new BasicComboBoxUI());
			cittaComboBox.setEnabled(false);
			cittaComboBox.setBackground(Color.WHITE);
			cittaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			cittaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			cittaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			
			iconaIndirizzoLabel = new JLabel("");
			iconaIndirizzoLabel.setBounds(41, 385, 46, 30);
			infoPanel.add(iconaIndirizzoLabel);
			iconaIndirizzoLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/indirizzo_16.png")));
			iconaIndirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			//Label "Indirizzo*"
			indirizzoLabel = new JLabel("Indirizzo*");
			indirizzoLabel.setBounds(97, 418, 84, 14);
			infoPanel.add(indirizzoLabel);
			indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
			
			iconaCellulareLabel = new JLabel("");
			iconaCellulareLabel.setBounds(41, 452, 46, 30);
			infoPanel.add(iconaCellulareLabel);
			iconaCellulareLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/cellulare_16.png")));
			iconaCellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			//Label "Cellulare"
			cellulareLabel = new JLabel("Cellulare");
			cellulareLabel.setBounds(96, 462, 84, 14);
			infoPanel.add(cellulareLabel);
			cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
			
			//Text Field cellulare
			cellulareTextField = new JTextField();
			cellulareTextField.setBounds(209, 457, 86, 20);
			infoPanel.add(cellulareTextField);
			cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			cellulareTextField.setColumns(10);
			
			//Label "Telefono fisso"
			telefonoFissoLabel = new JLabel("Telefono Fisso");
			telefonoFissoLabel.setBounds(96, 493, 103, 14);
			infoPanel.add(telefonoFissoLabel);
			telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
			
			//Text Field telefono di casa
			telefonoFissoTextField = new JTextField();
			telefonoFissoTextField.setBounds(209, 488, 86, 20);
			infoPanel.add(telefonoFissoTextField);
			telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			telefonoFissoTextField.setColumns(10);
			
			iconaPasswordLabel = new JLabel("");
			iconaPasswordLabel.setBounds(43, 529, 46, 30);
			infoPanel.add(iconaPasswordLabel);
			iconaPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/password_16.png")));
			iconaPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			//Label "Password*"
			passwordLabel = new JLabel("Password*");
			passwordLabel.setBounds(99, 541, 66, 14);
			infoPanel.add(passwordLabel);
			passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
			passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
			
			//Password Field per la password
			passwordField = new JPasswordField();
			passwordField.setBounds(239, 530, 125, 20);
			infoPanel.add(passwordField);
			passwordField.setEchoChar('*');
			passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			passwordField.setHorizontalAlignment(SwingConstants.LEFT);
			
			//Label "Conferma Password*"
			confermaPasswordLabel = new JLabel("Conferma Password*");
			confermaPasswordLabel.setBounds(99, 566, 128, 14);
			infoPanel.add(confermaPasswordLabel);
			confermaPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
			confermaPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
			
			//Password Field per conferma password
			confermaPasswordField = new JPasswordField();
			confermaPasswordField.setBounds(239, 561, 125, 20);
			infoPanel.add(confermaPasswordField);
			confermaPasswordField.setEchoChar('*');
			confermaPasswordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			confermaPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
					
					//Text Field indirizzo
					indirizzoTextField = new JTextField();
					indirizzoTextField.setBounds(97, 395, 267, 20);
					infoPanel.add(indirizzoTextField);
					indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
					indirizzoTextField.setColumns(10);
					indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
					
					//Skills
					skillsScrollPane = new JScrollPane();
					skillsScrollPane.setBounds(680, 128, 318, 268);
					infoPanel.add(skillsScrollPane);
					skillsScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
					try {
						skillsList = new JList(controller.ottieniSkill().toArray());
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					skillsList.setToolTipText("");
					skillsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					skillsList.setFont(new Font("Consolas", Font.PLAIN, 12));
					skillsScrollPane.setViewportView(skillsList);
					
					//Label "Skills"
					skillsLabel = new JLabel("Skills");
					skillsLabel.setBounds(680, 103, 66, 14);
					infoPanel.add(skillsLabel);
					skillsLabel.setHorizontalAlignment(SwingConstants.LEFT);
					skillsLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
					
					//Icona delle skill
					iconaSkillsLabel = new JLabel("");
					iconaSkillsLabel.setBounds(590, 181, 60, 39);
					infoPanel.add(iconaSkillsLabel);
					iconaSkillsLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/skills_32.png")));
					iconaSkillsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					
					//Button "Crea Nuova Skill"
					nuovaSkillButton = new JButton("Crea Nuova Skill");
					nuovaSkillButton.setBounds(858, 404, 139, 23);
					infoPanel.add(nuovaSkillButton);
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
							creaSkill(controller);	//aggiunge la nuova skill nel DB
						}
					});
					nuovaSkillButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					nuovaSkillButton.setFont(new Font("Consolas", Font.PLAIN, 11));
					nuovaSkillButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
					nuovaSkillButton.setBackground(Color.WHITE);
					
					//Text Field per il nome della nuova skill
					nuovaSkillTextField = new JTextField();
					nuovaSkillTextField.setBounds(680, 405, 168, 22);
					infoPanel.add(nuovaSkillTextField);
					nuovaSkillTextField.setColumns(10);
					nuovaSkillTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
					
					//Text Field per il salario
					salarioTextField = new JTextField("0.00");
					salarioTextField.setBounds(701, 489, 141, 22);
					infoPanel.add(salarioTextField);
					salarioTextField.setHorizontalAlignment(SwingConstants.RIGHT);
					salarioTextField.setColumns(10);
					salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
					
					//Label "€"
					euroLabel = new JLabel("€");
					euroLabel.setBounds(851, 483, 66, 36);
					infoPanel.add(euroLabel);
					euroLabel.setFont(new Font("Arial", Font.PLAIN, 18));
					
					//Label "Salario"
					salarioLabel = new JLabel("Salario");
					salarioLabel.setBounds(701, 516, 66, 14);
					infoPanel.add(salarioLabel);
					salarioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
					
					//Icona salario
					iconaSalarioLabel = new JLabel("");
					iconaSalarioLabel.setBounds(643, 483, 46, 30);
					infoPanel.add(iconaSalarioLabel);
					iconaSalarioLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/salario_16.png")));
					iconaSalarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					
					mostraPasswordLabel = new JLabel("");
					mostraPasswordLabel.setBounds(404, 528, 46, 26);
					infoPanel.add(mostraPasswordLabel);
					mostraPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					mostraPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/showpass_24.png")));
					
					//Icona di mostra conferma password
					mostraConfirmPasswordLabel = new JLabel("");
					mostraConfirmPasswordLabel.setBounds(404, 558, 47, 29);
					infoPanel.add(mostraConfirmPasswordLabel);
					mostraConfirmPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					mostraConfirmPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/showpass_24.png")));
					
					inserireSkillLabel = new JLabel("N.B. Per inserire più skill contemporanemante");
					inserireSkillLabel.setBounds(707, 435, 290, 20);
					infoPanel.add(inserireSkillLabel);
					inserireSkillLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
					inserireSkillLabel.setHorizontalAlignment(SwingConstants.LEFT);
					
					premereCtrlLabel = new JLabel("premere CTRL+clic tasto sx mouse sulla skill");
					premereCtrlLabel.setBounds(707, 452, 290, 20);
					infoPanel.add(premereCtrlLabel);
					premereCtrlLabel.setHorizontalAlignment(SwingConstants.LEFT);
					premereCtrlLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
					
					campiObbligatoriLabel = new JLabel("* Campi obbligatori");
					campiObbligatoriLabel.setBounds(738, 558, 290, 20);
					infoPanel.add(campiObbligatoriLabel);
					campiObbligatoriLabel.setHorizontalAlignment(SwingConstants.LEFT);
					campiObbligatoriLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
					
					cittàDiNascitaLabel = new JLabel("Città di nascita*");
					cittàDiNascitaLabel.setBounds(131, 342, 128, 14);
					infoPanel.add(cittàDiNascitaLabel);
					cittàDiNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
					cittàDiNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
					
					JSeparator separator = new JSeparator();
					separator.setOrientation(SwingConstants.VERTICAL);
					separator.setBounds(542, 98, 8, 527);
					infoPanel.add(separator);
					
					//Button "Annulla"
					esciButton = new JButton("Esci");
					esciButton.setBounds(25, 871, 97, 23);
					contentPane.add(esciButton);
					esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
					esciButton.setBackground(Color.WHITE);
					esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
					
					comandiPanel = new JPanel();
					comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
					comandiPanel.setBounds(25, 690, 1094, 38);
					contentPane.add(comandiPanel);
					comandiPanel.setLayout(null);
					
					JButton modificaAccountButton = new JButton("Modifica");
					modificaAccountButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					modificaAccountButton.setBounds(912, 7, 95, 23);
					comandiPanel.add(modificaAccountButton);
					modificaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
					modificaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
					modificaAccountButton.setBackground(Color.WHITE);
					
							//Button "Crea Account"
							creaAccountButton = new JButton("Crea");
							creaAccountButton.setBounds(1017, 7, 67, 23);
							comandiPanel.add(creaAccountButton);
							creaAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							creaAccountButton.setBackground(Color.WHITE);
							creaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
							creaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
							
							cercaTextField = new JTextField();
							cercaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
							cercaTextField.setBounds(109, 9, 152, 20);
							comandiPanel.add(cercaTextField);
							cercaTextField.setColumns(10);
							
							JButton filtraButton = new JButton("Filtra");
							filtraButton.setFont(new Font("Consolas", Font.PLAIN, 13));
							filtraButton.setBounds(10, 9, 89, 20);
							comandiPanel.add(filtraButton);
							
							etàMinimaTextField = new JTextField();
							etàMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
							etàMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
							etàMinimaTextField.setBounds(271, 9, 39, 20);
							comandiPanel.add(etàMinimaTextField);
							etàMinimaTextField.setColumns(10);
							
							etàFiltroLabel = new JLabel("< Età <");
							etàFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
							etàFiltroLabel.setBounds(318, 12, 51, 14);
							comandiPanel.add(etàFiltroLabel);
							
							etàMassimaLabel = new JTextField();
							etàMassimaLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
							etàMassimaLabel.setHorizontalAlignment(SwingConstants.LEFT);
							etàMassimaLabel.setColumns(10);
							etàMassimaLabel.setBounds(372, 9, 39, 20);
							comandiPanel.add(etàMassimaLabel);
							
							salarioMinimoTextField = new JTextField();
							salarioMinimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
							salarioMinimoTextField.setHorizontalAlignment(SwingConstants.RIGHT);
							salarioMinimoTextField.setColumns(10);
							salarioMinimoTextField.setBounds(427, 9, 39, 20);
							comandiPanel.add(salarioMinimoTextField);
							
							salarioFiltroLabel = new JLabel("< Salario <");
							salarioFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
							salarioFiltroLabel.setBounds(474, 12, 90, 14);
							comandiPanel.add(salarioFiltroLabel);
							
							salarioMassimoTextField = new JTextField();
							salarioMassimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
							salarioMassimoTextField.setHorizontalAlignment(SwingConstants.LEFT);
							salarioMassimoTextField.setColumns(10);
							salarioMassimoTextField.setBounds(557, 9, 39, 20);
							comandiPanel.add(salarioMassimoTextField);
							
							valutazioneMinimaTextField = new JTextField();
							valutazioneMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
							valutazioneMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
							valutazioneMinimaTextField.setColumns(10);
							valutazioneMinimaTextField.setBounds(606, 9, 39, 20);
							comandiPanel.add(valutazioneMinimaTextField);
							
							valutazioneFiltroLabel = new JLabel("< Valutazione <");
							valutazioneFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
							valutazioneFiltroLabel.setBounds(653, 12, 115, 14);
							comandiPanel.add(valutazioneFiltroLabel);
							
							valutazioneMassimaTextField = new JTextField();
							valutazioneMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
							valutazioneMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
							valutazioneMassimaTextField.setColumns(10);
							valutazioneMassimaTextField.setBounds(768, 9, 39, 20);
							comandiPanel.add(valutazioneMassimaTextField);
							
							eliminaAccountButton = new JButton("Elimina");
							eliminaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
							eliminaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
							eliminaAccountButton.setBackground(Color.WHITE);
							eliminaAccountButton.setBounds(813, 7, 89, 23);
							comandiPanel.add(eliminaAccountButton);
							
							tableScrollPanel = new JScrollPane();
							tableScrollPanel.setBounds(25, 743, 1094, 117);
							contentPane.add(tableScrollPanel);
							
							dipendentiTable = new JTable();
							dipendentiTable.setFont(new Font("Consolas", Font.PLAIN, 11));
							dipendentiTable.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Nome", "Cognome", "Email", "Data di Nascita", "Salario", "Valutazione"
								}
							));
							dipendentiTable.getColumnModel().getColumn(1).setPreferredWidth(85);
							dipendentiTable.getColumnModel().getColumn(2).setPreferredWidth(110);
							dipendentiTable.getColumnModel().getColumn(3).setPreferredWidth(89);
							dipendentiTable.getColumnModel().getColumn(4).setPreferredWidth(66);
							tableScrollPanel.setViewportView(dipendentiTable);
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
											creaAccount(controller);	//crea il nuovo account con i valori inseriti
										} catch (SQLException e1) {
											JOptionPane.showMessageDialog(null,
													e1.getMessage(),
													"Errore #" + e1.getErrorCode(),
													JOptionPane.ERROR_MESSAGE);	//errore durante la creazione account
										}
									//se le password inserite sono diverse
									else if(!passwordField.getText().equals(confermaPasswordField.getText()))
									{	
										JOptionPane.showMessageDialog(null,"Le password inserite sono diverse","Errore",JOptionPane.ERROR_MESSAGE);	//errore password non confermata
										
										passwordLabel.setForeground(Color.RED);	//rende rossi i campi
										confermaPasswordLabel.setForeground(Color.RED);
										
									}
									//se uno dei campi obbligatori è vuoto colora la rispettiva label di rosso
									else if ((nomeTextField.getText().isBlank() || cognomeTextField.getText().isBlank() || emailTextField.getText().isBlank() || passwordField.getText().isBlank()) || confermaPasswordField.getText().equals(passwordField.getText()) || !cittaComboBox.isEnabled()) {
										
										JOptionPane.showMessageDialog(null,
												"Compilare i campi vuoti",
												"Errore",
												JOptionPane.ERROR_MESSAGE);	//errore campi obbligatori vuoti
										
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
					//Eventi connessi al button "Annulla"
					esciButton.addActionListener(new ActionListener() {
						//click sinistro del mouse
						public void actionPerformed(ActionEvent e) {
							controller.tornaAiPlanner();	//annulla tutto e torna alla schermata principale
						}
					});
					esciButton.addMouseListener(new MouseAdapter() {
						//mouse sopra il pulsante
						@Override
						public void mouseEntered(MouseEvent e) 
						{
							esciButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
						}
						//mouse fuori dal pulsante
						@Override
						public void mouseExited(MouseEvent e) 
						{
							esciButton.setBackground(Color.WHITE);	//smette di evidenziare il pulsante
						}
					});
					mostraConfirmPasswordLabel.addMouseListener(new MouseAdapter() {
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
					mostraPasswordLabel.addMouseListener(new MouseAdapter() {
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
	}
		
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che salva i dati del nuovo account e li manda al controller per creare il nuovo account nel DB
	private void creaAccount(ControllerDipendentiSegreteria controller) throws SQLException {
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
		luogoNascita = controller.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cittaComboBox.getSelectedIndex());
		email = emailTextField.getText(); //email
		password = passwordField.getText();	//password
		if (!telefonoFissoTextField.getText().equals(""))
			telefono = telefonoFissoTextField.getText();	//telefono
		if (!cellulareTextField.getText().isBlank())
			cellulare = cellulareTextField.getText();    //cellulare
		else cellulare=null;
		
		indirizzo = indirizzoTextField.getText();	//indirizzo
		//ottiene le skill selezionate
		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills.addAll(skillsList.getSelectedValuesList());
		float salario = Float.valueOf(salarioTextField.getText());	//ottieni il salario
		controller.creaAccount(nome, cognome, sesso, dataNascita, luogoNascita, email, password, telefono, cellulare, indirizzo, skills, salario);	//mandali al controller che prova a creare il nuovo dipendente con il dao
	}
	
	//Metodo che crea una nuova skill e aggiorna la lista delle skill disponibili
	private void creaSkill(ControllerDipendentiSegreteria controller) {
		if (!nuovaSkillTextField.getText().isBlank()) {
			try {
				controller.creaNuovaSkill(nuovaSkillTextField.getText());	//inserisce la nuova skill nel database
				DefaultListModel<Skill> skillModel = new DefaultListModel<Skill>();	//aggiorna la lista delle skill
				skillModel.addAll(controller.ottieniSkill());
				skillsList.setModel(skillModel);
				nuovaSkillTextField.setText(""); //svuota il campo
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Sono stati inseriti caratteri non corretti",
						"Errore #"+e.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
