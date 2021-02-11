package gui.segreteria;
import controller.*;
import controller.segreteria.ControllerDipendentiSegreteria;

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
import org.joda.time.Period;
import org.joda.time.PeriodType;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.DipendentiTableModel;

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
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


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
	private JComboBox giornoComboBox;
	private JComboBox meseComboBox;
	private JComboBox<String> annoComboBox;
	private JComboBox provinciaComboBox;
	private JComboBox<String> cittaComboBox;
	private JTextField indirizzoTextField;
	private JTextField cellulareTextField;
	private JTextField telefonoFissoTextField;
	private JPasswordField passwordField;
	private JPasswordField confermaPasswordField;
	private JTextField nuovaSkillTextField;
	private JTextField salarioTextField;
	private JScrollPane skillsScrollPane;
	private JList<Skill> skillsList;
	private JButton nuovaSkillButton;
	private JButton creaAccountButton;
	private JButton esciButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel comandiPanel;
	private JScrollPane tableScrollPanel;
	private JTextField cercaTextField;
	private JTextField etàMinimaTextField;
	private JLabel etàFiltroLabel;
	private JTextField etàMassimaTextField;
	private JTextField salarioMinimoTextField;
	private JLabel salarioFiltroLabel;
	private JTextField salarioMassimoTextField;
	private JTextField valutazioneMinimaTextField;
	private JLabel valutazioneFiltroLabel;
	private JTextField valutazioneMassimaTextField;
	private JButton salvaModificheButton;
	private DipendentiTableModel dataModelDipendente;
	private JTable dipendentiTable;
	private JComboBox<Skill> skillFiltroComboBox;
	
	//Altri attributi	
	private ArrayList<String> anni = new ArrayList<String>();	//lista di anni per la data di nascita (1900-oggi)
	
	private String nome;	//nome dipendente
	private String cognome;	//cognome dipendente
	private char sesso = 'M';	//sesso del dipendente (default = maschio)
	private LocalDate dataNascita = new LocalDate(1900,1,1);	//data di nascita del dipendente
	private LuogoNascita luogoNascita = null;	//luogo di nascita del dipendente
	private String email;	//email del dipendente
	private String password = null;	//password del dipendente
	private String telefono = null;	//numero di telefono di casa
	private String cellulare = null;	//cellulare
	private String indirizzo;	//indirizzo
	private float salario; //salario
	
	private Dipendente selectedDip; //dipendente selezionato nella tabella
	private JLabel pulisciCampiLabel;
	
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
		setBounds(100, 100, 1358, 944);
		contentPane = new JPanel();
		setContentPane(contentPane);
		//Viene visualizzata al centro dello schermo
		setLocationRelativeTo(null);
		
		contentPane.setLayout(null);
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoPanel.setBounds(28, 11, 1286, 650);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//Label Titolo
		dipendenteLabel = new JLabel("Dipendente");
		dipendenteLabel.setBounds(517, 11, 251, 75);
		infoPanel.add(dipendenteLabel);
		dipendenteLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/employee_64.png")));
		dipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//Label Icona
		iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setBounds(41, 128, 46, 14);
		infoPanel.add(iconaNomeLabel);
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/nome_16.png")));
		
		//Text Field per il nome
		nomeTextField = new JTextField();
		nomeTextField.setBounds(97, 128, 162, 20);
		infoPanel.add(nomeTextField);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomeTextField.setColumns(10);
		
		//Text Field per il cognome
		cognomeTextField = new JTextField();
		cognomeTextField.setBounds(282, 128, 168, 20);
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
		cognomeLabel.setBounds(282, 148, 84, 14);
		infoPanel.add(cognomeLabel);
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		cognomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Icona Email
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
		sessoLabel.setBounds(97, 236, 46, 14);
		infoPanel.add(sessoLabel);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Radio Button per uomo
		uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setBounds(163, 232, 61, 23);
		infoPanel.add(uomoRadioButton);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		
		//Radio Button per donna
		donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setBounds(235, 232, 66, 23);
		infoPanel.add(donnaRadioButton);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(donnaRadioButton);
		
		//Icona data di nascita
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
		giornoComboBox.setUI(new BasicComboBoxUI());
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		giornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoComboBox.setSelectedIndex(0);
		giornoComboBox.setBounds(228, 276, 44, 22);
		infoPanel.add(giornoComboBox);
		
		//Combo Box mesi dell'anno
		meseComboBox = new JComboBox<Object>();
		meseComboBox.setBounds(282, 276, 44, 22);
		meseComboBox.setUI(new BasicComboBoxUI());
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		infoPanel.add(meseComboBox);
		
		//ComboBox anni
		for (int i = 1900; i < LocalDate.now().getYear(); i++)
			anni.add(String.valueOf(i));
		annoComboBox = new JComboBox(anni.toArray());
		annoComboBox.setBounds(336, 276, 66, 22);
		annoComboBox.setUI(new BasicComboBoxUI());
		annoComboBox.setBackground(Color.WHITE);
		annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		annoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoComboBox.setSelectedIndex((int) annoComboBox.getItemCount()/2);
		infoPanel.add(annoComboBox);
		
		//Label "Luogo di nascita*"
		provNascitaLabel = new JLabel("Prov. di nascita*");
		provNascitaLabel.setBounds(97, 327, 128, 14);
		infoPanel.add(provNascitaLabel);
		provNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		provNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Combo Box province
		try {
			provinciaComboBox = new JComboBox(controller.ottieniProvince().toArray());
			provinciaComboBox.setUI(new BasicComboBoxUI());
			provinciaComboBox.setBackground(Color.WHITE);
			provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			provinciaComboBox.setBounds(229, 319, 210, 22);
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
						//errore select per tutti i comuni
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i comuni di tale provincia dal database.\nControllare che la connessione al database sia stabilita.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		} catch (SQLException e2) {
			//errore select per tutte le province
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le province dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		//ComboBox comuni
		cittaComboBox = new JComboBox();
		cittaComboBox.setBounds(229, 350, 210, 22);
		infoPanel.add(cittaComboBox);
		cittaComboBox.setUI(new BasicComboBoxUI());
		cittaComboBox.setBackground(Color.WHITE);
		cittaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cittaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		cittaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Icona indirizzo
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
		
		//Icona cellulare
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
		
		//Icona password
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
		passwordField.setBounds(239, 530, 185, 20);
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
		confermaPasswordField.setBounds(239, 561, 185, 20);
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
		skillsScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		skillsScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		skillsScrollPane.setBounds(810, 128, 318, 268);
		infoPanel.add(skillsScrollPane);
		skillsScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		try {
			skillsList = new JList(controller.ottieniSkill().toArray());
			skillsList.setToolTipText("");
			skillsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			skillsList.setFont(new Font("Consolas", Font.PLAIN, 12));
			skillsScrollPane.setViewportView(skillsList);
		} catch (SQLException e2) {
			//errore select per tutte le skill
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le skill dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		//Label "Skills"
		skillsLabel = new JLabel("Skills");
		skillsLabel.setBounds(745, 222, 47, 14);
		infoPanel.add(skillsLabel);
		skillsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		skillsLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Icona delle skill
		iconaSkillsLabel = new JLabel("");
		iconaSkillsLabel.setBounds(750, 181, 31, 39);
		infoPanel.add(iconaSkillsLabel);
		iconaSkillsLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/skills_32.png")));
		iconaSkillsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Button "Crea Nuova Skill"
		nuovaSkillButton = new JButton("Crea Nuova Skill");
		nuovaSkillButton.setBounds(989, 404, 139, 23);
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
		nuovaSkillTextField.setBounds(811, 405, 168, 22);
		infoPanel.add(nuovaSkillTextField);
		nuovaSkillTextField.setColumns(10);
		nuovaSkillTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Text Field per il salario
		salarioTextField = new JTextField("0.00");
		salarioTextField.setBounds(832, 489, 141, 22);
		infoPanel.add(salarioTextField);
		salarioTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioTextField.setColumns(10);
		salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Label "€"
		euroLabel = new JLabel("€");
		euroLabel.setBounds(982, 483, 66, 36);
		infoPanel.add(euroLabel);
		euroLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		//Label "Salario"
		salarioLabel = new JLabel("Salario");
		salarioLabel.setBounds(832, 516, 66, 14);
		infoPanel.add(salarioLabel);
		salarioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Icona salario
		iconaSalarioLabel = new JLabel("");
		iconaSalarioLabel.setBounds(774, 483, 46, 30);
		infoPanel.add(iconaSalarioLabel);
		iconaSalarioLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/salario_16.png")));
		iconaSalarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Icona Mostra password interagibile
		mostraPasswordLabel = new JLabel("");
		mostraPasswordLabel.setBounds(448, 527, 46, 26);
		infoPanel.add(mostraPasswordLabel);
		mostraPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mostraPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/showpass_24.png")));
		//Eventi legati all'icona
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
		
		//Icona di mostra conferma password
		mostraConfirmPasswordLabel = new JLabel("");
		mostraConfirmPasswordLabel.setBounds(448, 557, 47, 29);
		infoPanel.add(mostraConfirmPasswordLabel);
		mostraConfirmPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mostraConfirmPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/showpass_24.png")));
		//Eventi legati all'icona
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
		
		//Label tip per selezionare più skill
		inserireSkillLabel = new JLabel("N.B. Per inserire più skill contemporanemante");
		inserireSkillLabel.setBounds(838, 435, 290, 20);
		infoPanel.add(inserireSkillLabel);
		inserireSkillLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		inserireSkillLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label continuo tip per selezionare più skill
		premereCtrlLabel = new JLabel("premere CTRL+clic tasto sx mouse sulla skill");
		premereCtrlLabel.setBounds(838, 452, 290, 20);
		infoPanel.add(premereCtrlLabel);
		premereCtrlLabel.setHorizontalAlignment(SwingConstants.LEFT);
		premereCtrlLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		
		//Label campi obbligatori
		campiObbligatoriLabel = new JLabel("* Campi obbligatori");
		campiObbligatoriLabel.setBounds(1119, 619, 128, 20);
		infoPanel.add(campiObbligatoriLabel);
		campiObbligatoriLabel.setHorizontalAlignment(SwingConstants.LEFT);
		campiObbligatoriLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		
		//Label città di nascita
		cittàDiNascitaLabel = new JLabel("Città di nascita*");
		cittàDiNascitaLabel.setBounds(97, 354, 128, 14);
		infoPanel.add(cittàDiNascitaLabel);
		cittàDiNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cittàDiNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Separatore verticale
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(601, 100, 2, 527);
		infoPanel.add(separator);
		
		//Label Valutazione
		JLabel valutazioneLabel = new JLabel("Valutazione");
		valutazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valutazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		valutazioneLabel.setBounds(832, 545, 185, 14);
		infoPanel.add(valutazioneLabel);
		
		//Label per pulire campi
		pulisciCampiLabel = new JLabel("");
		//Click mouse
		pulisciCampiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pulisciCampi();	//pulisce i campi
			}
		});
		pulisciCampiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciCampiLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/refresh.png")));
		pulisciCampiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pulisciCampiLabel.setBounds(41, 100, 16, 14);
		infoPanel.add(pulisciCampiLabel);
		
		//Button "Esci"
		esciButton = new JButton("Esci");
		esciButton.setBounds(25, 871, 97, 23);
		contentPane.add(esciButton);
		esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		esciButton.setBackground(Color.WHITE);
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		//Eventi connessi al button "Esci"
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
		
		//Panel dei comandi
		comandiPanel = new JPanel();
		comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel.setBounds(28, 672, 1289, 38);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		//Button "Crea Account"
		creaAccountButton = new JButton("Crea");
		creaAccountButton.setToolTipText("Crea un nuovo dipendente con le informazioni inserite");
		creaAccountButton.setBounds(1204, 7, 67, 23);
		comandiPanel.add(creaAccountButton);
		creaAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaAccountButton.setBackground(Color.WHITE);
		creaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		//Eventi connessi al pulsante "Crea Account"
		creaAccountButton.addActionListener(new ActionListener() {
			//click del pulsante
			public void actionPerformed(ActionEvent e) {
				campiObbligatoriNeri();	//resetta il colore dei campi
				
				//se le password non coincidono
				if(!passwordField.getText().equals(confermaPasswordField.getText())) {	
					JOptionPane.showMessageDialog(null,
							"Le password inserite sono diverse",
							"Errore Conferma Password",
							JOptionPane.ERROR_MESSAGE);
					passwordLabel.setForeground(Color.RED);	//rende rossi i campi
					confermaPasswordLabel.setForeground(Color.RED);
				}
				//se le password coincidono
				else if (confermaPasswordField.getText().equals(passwordField.getText()))
					creaDipendente(controller);	//crea il nuovo account con i valori inseriti
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
		
		//Text Field ricerca per nome/cognome/email
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cercaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		cercaTextField.setBounds(87, 9, 162, 20);
		comandiPanel.add(cercaTextField);
		cercaTextField.setColumns(10);
		
		//Button per filtrare la tabella di dipendenti
		JButton filtraButton = new JButton("Filtra");
		filtraButton.setToolTipText("Clicca per applicare i filtri");
		filtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltri(controller);	//applica i filtri
			}
		});
		filtraButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtraButton.setBackground(Color.WHITE);
		filtraButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtraButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtraButton.setBounds(10, 9, 67, 20);
		filtraButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				filtraButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				filtraButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		comandiPanel.add(filtraButton);
		
		//Text Field età minima filtro
		etàMinimaTextField = new JTextField();
		etàMinimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		etàMinimaTextField.setText("min");
		etàMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		etàMinimaTextField.setBounds(266, 9, 39, 20);
		comandiPanel.add(etàMinimaTextField);
		etàMinimaTextField.setColumns(10);
		
		//Label "Età"
		etàFiltroLabel = new JLabel("Età");
		etàFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		etàFiltroLabel.setBounds(308, 12, 28, 14);
		comandiPanel.add(etàFiltroLabel);
		
		//Text Field età massima filtro
		etàMassimaTextField = new JTextField();
		etàMassimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		etàMassimaTextField.setText("max");
		etàMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
		etàMassimaTextField.setColumns(10);
		etàMassimaTextField.setBounds(333, 9, 39, 20);
		comandiPanel.add(etàMassimaTextField);
		
		//Text Field salario minimo filtro
		salarioMinimoTextField = new JTextField();
		salarioMinimoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salarioMinimoTextField.setText("min");
		salarioMinimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioMinimoTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioMinimoTextField.setColumns(10);
		salarioMinimoTextField.setBounds(402, 9, 67, 20);
		comandiPanel.add(salarioMinimoTextField);
		
		//Label "Salario"
		salarioFiltroLabel = new JLabel("Salario");
		salarioFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		salarioFiltroLabel.setBounds(472, 12, 57, 14);
		comandiPanel.add(salarioFiltroLabel);
		
		//Text Field salario massimo filtro
		salarioMassimoTextField = new JTextField();
		salarioMassimoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salarioMassimoTextField.setText("max");
		salarioMassimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioMassimoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		salarioMassimoTextField.setColumns(10);
		salarioMassimoTextField.setBounds(524, 9, 80, 20);
		comandiPanel.add(salarioMassimoTextField);
		
		//Text Field valutazione minima
		valutazioneMinimaTextField = new JTextField();
		valutazioneMinimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		valutazioneMinimaTextField.setText("min");
		valutazioneMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		valutazioneMinimaTextField.setColumns(10);
		valutazioneMinimaTextField.setBounds(636, 9, 39, 20);
		comandiPanel.add(valutazioneMinimaTextField);
		
		//Label "Valutazione"
		valutazioneFiltroLabel = new JLabel("Valutazione");
		valutazioneFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		valutazioneFiltroLabel.setBounds(678, 12, 77, 14);
		comandiPanel.add(valutazioneFiltroLabel);
		
		//Text Field valutazione massima filtro
		valutazioneMassimaTextField = new JTextField();
		valutazioneMassimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		valutazioneMassimaTextField.setText("max");
		valutazioneMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
		valutazioneMassimaTextField.setColumns(10);
		valutazioneMassimaTextField.setBounds(757, 9, 39, 20);
		comandiPanel.add(valutazioneMassimaTextField);
		
		//Button salva le modifiche fatte
		salvaModificheButton = new JButton("<html> <center> Salva <br> Modifiche <html>");
		salvaModificheButton.setToolTipText("<html>Clicca per salvare le modifiche <br>delle informazioni del dipendente<html>");
		salvaModificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				campiObbligatoriNeri();	//resetta il colore dei campi
				
				if (confermaPasswordField.getText().equals(passwordField.getText())) {
					salvaModifiche(controller, selectedDip);	//crea il nuovo account con i valori inseriti
				}
				//se le password inserite sono diverse -> errore conferma password
				else if(!passwordField.getText().equals(confermaPasswordField.getText()))
				{	
					JOptionPane.showMessageDialog(null,
							"Le password inserite sono diverse",
							"Errore Conferma Password",
							JOptionPane.ERROR_MESSAGE);
					passwordLabel.setForeground(Color.RED);	//rende rossi i campi
					confermaPasswordLabel.setForeground(Color.RED);
				}
			}
		});
		salvaModificheButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		salvaModificheButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salvaModificheButton.setBackground(Color.WHITE);
		salvaModificheButton.setBounds(1099, 2, 95, 34);
		salvaModificheButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				salvaModificheButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				salvaModificheButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		comandiPanel.add(salvaModificheButton);
		
		//Button per eliminare gli account
		JButton eliminaAccountButton = new JButton("Elimina");
		//Click mouse
		eliminaAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricavaInfoDipendente(controller);	//ricava tutte le principali informazioni per il dipendente
				
				try {
					//crea il dipendente temporaneo da eliminare in base ai dati ricavati
					Dipendente temp = new Dipendente(null, nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,salario,password, 0f);
					temp.setCf(temp.generaCF());
					//elimina
					controller.eliminaDipendente(temp);
					
					pulisciCampi();	//azzera tutti i campi
					
					aggiornaTabella(controller);	//aggiorna la tabella dei dipendenti
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							e1.getMessage() + " Contattare uno sviluppatore",
							"Errore #" + e1.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);	//errore durante la creazione account
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null,
							"Il dipendente selezionato è vuoto.",
							"Errore Dipendente Nullo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		eliminaAccountButton.setToolTipText("Elimina il dipendente");
		eliminaAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		eliminaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaAccountButton.setBackground(Color.WHITE);
		eliminaAccountButton.setBounds(1022, 7, 67, 23);
		eliminaAccountButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				eliminaAccountButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				eliminaAccountButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		comandiPanel.add(eliminaAccountButton);
		
		//Label "Skill" in filtri
		JLabel skillFiltroLabel = new JLabel("Skill");
		skillFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		skillFiltroLabel.setBounds(814, 12, 39, 14);
		comandiPanel.add(skillFiltroLabel);
		
		//ComboBox skill per filtri
		try {
			ArrayList<Skill> skills = controller.ottieniSkill();
			skills.add(0,null);
			skillFiltroComboBox = new JComboBox(skills.toArray());
			skillFiltroComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			skillFiltroComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			skillFiltroComboBox.setBackground(Color.WHITE);
			skillFiltroComboBox.setBounds(856, 6, 157, 22);
			comandiPanel.add(skillFiltroComboBox);
		} catch (SQLException e2) {
			//errore select per tutte le skill
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le skill dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		//Scroll Panel per tabella dipendenti
		tableScrollPanel = new JScrollPane();
		tableScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		tableScrollPanel.setBounds(25, 721, 1289, 139);
		contentPane.add(tableScrollPanel);
		
		//Tabella dipendenti
		dataModelDipendente = new DipendentiTableModel();
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());
			dipendentiTable = new JTable(dataModelDipendente);
			dipendentiTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dipendentiTable.getModel());	//sorter
			dipendentiTable.setRowSorter(sorter);
			
			//Click con mouse
			dipendentiTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					campiObbligatoriNeri();	//resetta il colore dei campi
					
					int row = dipendentiTable.getSelectedRow();	//ottiene la riga selezionata
					row = dipendentiTable.convertRowIndexToModel(row);	//converte la riga correttamente in caso di sorting
					selectedDip = dataModelDipendente.getSelected(row);	//ottiene il dipendente selezionato
					
					pulisciCampi();	//pulisce i campi prima di riaggiornarli
					
					//Aggiorna la GUI
					nomeTextField.setText(selectedDip.getNome());	//nome
					cognomeTextField.setText(selectedDip.getCognome());	//cognome
					emailTextField.setText(selectedDip.getEmail()); 	//email
					//sesso
					if(selectedDip.getSesso() == 'M') {
						uomoRadioButton.setSelected(true);
						donnaRadioButton.setSelected(false);
					}
					else {
						uomoRadioButton.setSelected(false);		
						donnaRadioButton.setSelected(true);
					}
					//data nascita
					giornoComboBox.setSelectedIndex(selectedDip.getDataNascita().getDayOfMonth()-1);
					meseComboBox.setSelectedIndex(selectedDip.getDataNascita().getMonthOfYear()-1);
					annoComboBox.setSelectedIndex(selectedDip.getDataNascita().getYear()-1900);
					//luogo di nascita
					provinciaComboBox.setSelectedItem(selectedDip.getLuogoNascita().getNomeProvincia());
					cittaComboBox.setSelectedItem(selectedDip.getLuogoNascita().getNomeComune());
					cittaComboBox.setEnabled(false);
					indirizzoTextField.setText(selectedDip.getIndirizzo());	//indirizzo
					cellulareTextField.setText(selectedDip.getCellulare());	//cellulare
					telefonoFissoTextField.setText(selectedDip.getTelefonoCasa()); //telefono casa
					passwordField.setText(selectedDip.getPassword()); //password
					confermaPasswordField.setText(selectedDip.getPassword()); //conferma password
					salarioTextField.setText(Float.toString(selectedDip.getSalario()));	//salario
					//skills
					ArrayList<Skill> skills = selectedDip.getSkills();
					int[] indici = new int[skills.size()];
					int i = 0;
					for (Skill skill: skills) {
						try {
							indici[i] = controller.ottieniSkill().indexOf(skill);
							i++;
							skillsList.setSelectedIndices(indici);
						} catch (SQLException e1) {
							//errore select per tutte le skill
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere tutte le skill dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
						}
					}
					valutazioneLabel.setText("Valutazione: " + String.format("%.2f",selectedDip.getValutazione())); //valutazione	
				}
			});
			dipendentiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dipendentiTable.setFont(new Font("Consolas", Font.PLAIN, 11));
			tableScrollPanel.setViewportView(dipendentiTable);
		} catch (SQLException e1) {
			//errore select per tutti i dipendenti
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i dipendenti dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che ricava le informazioni essenziali per la creazione di un dipendente temporaneo
	private void ricavaInfoDipendente(ControllerDipendentiSegreteria controller) {
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
		try {
			luogoNascita = controller.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cittaComboBox.getSelectedIndex());
		}
		catch (SQLException e1) {
			//errore select per comuni
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i comuni dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		email = emailTextField.getText(); //email
		if (!passwordField.getText().isBlank())
			password = passwordField.getText();	//password
		if (!telefonoFissoTextField.getText().equals(""))
			telefono = telefonoFissoTextField.getText();	//telefono
		if (!cellulareTextField.getText().isBlank())
			cellulare = cellulareTextField.getText();    //cellulare
		indirizzo = indirizzoTextField.getText();	//indirizzo
		salario = parseFloat(salarioTextField.getText(), 0f);	//salario
	}
	
	//Metodo che aggiorna la tabella dei dipendenti
	private void aggiornaTabella(ControllerDipendentiSegreteria controller) {
		//aggiorna tabella dipendenti
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());
			dipendentiTable.setModel(dataModelDipendente);
			dataModelDipendente.fireTableDataChanged();
		} 
		catch (SQLException e1) {
			//errore select per tutti i dipendenti
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i dipendenti dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Metodo che colora i campi obbligatori vuoti di rosso
	private void campiObbligatoriRossi() {
		if (nomeTextField.getText() == null)	//nome
			nomeLabel.setForeground(Color.RED);
		if (cognomeTextField.getText() == null)	//cognome
			cognomeLabel.setForeground(Color.RED);
		if (emailTextField.getText() == null)	//email
			emailLabel.setForeground(Color.RED);
		if (!uomoRadioButton.isSelected() && !donnaRadioButton.isSelected())	//sesso
			sessoLabel.setForeground(Color.RED);
		if (giornoComboBox.getSelectedItem() == null || meseComboBox.getSelectedItem() == null || annoComboBox.getSelectedItem() == null)	//data di nascita
			dataNascitaLabel.setForeground(Color.RED);
		if (indirizzoTextField.getText() == null)	//indirizzo	
			indirizzoLabel.setForeground(Color.RED);
		if (provinciaComboBox.getSelectedItem() == null || cittaComboBox.getSelectedItem() == null) //luogo di nascita
			cittàDiNascitaLabel.setForeground(Color.RED);
		if (salarioTextField.getText() == null)	//salario
			salarioLabel.setForeground(Color.RED);
		//password
		if (passwordField.getText().isBlank() || confermaPasswordField.getText().isBlank()) {
			passwordLabel.setForeground(Color.RED);
			confermaPasswordLabel.setForeground(Color.RED);
		}
	}
	
	//Metodo che colora di nero i campi
	private void campiObbligatoriNeri() {
		nomeLabel.setForeground(Color.BLACK);
		cognomeLabel.setForeground(Color.BLACK);
		emailLabel.setForeground(Color.BLACK);
		passwordLabel.setForeground(Color.BLACK);
		confermaPasswordLabel.setForeground(Color.BLACK);
		sessoLabel.setForeground(Color.BLACK);
		indirizzoLabel.setForeground(Color.BLACK);
		dataNascitaLabel.setForeground(Color.BLACK);
		cittàDiNascitaLabel.setForeground(Color.BLACK);
		provNascitaLabel.setForeground(Color.BLACK);
		salarioLabel.setForeground(Color.BLACK);
	}
	
	//Metodo che crea un dipendente nel DB
	private void creaDipendente(ControllerDipendentiSegreteria controller) {
		ricavaInfoDipendente(controller);	//ricava tutte le principali informazioni per il dipendente
		//ottiene le skill selezionate
		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills.addAll(skillsList.getSelectedValuesList());
		//crea un dipendente temporaneo con i parametri in input
		Dipendente dipendente = new Dipendente(null, nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,salario,password, 0f);
		dipendente.setSkills(skills);
		try {
			controller.creaAccount(dipendente);	//prova a creare il nuovo dipendente
			
			pulisciCampi();	//azzera tutti i campi
			
			aggiornaTabella(controller);	//aggiorna la tabella dei dipendenti
		}
		catch(SQLException e1) {
			//violazione not null
			if (e1.getSQLState().equals("23502")) {
				JOptionPane.showMessageDialog(null,
						"Alcuni campi obbligatori per la creazione sono vuoti.",
						"Errore Campi Obbligatori Vuoti",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				campiObbligatoriRossi(); //colora di rosso i campi obbligatori vuoti
			}
			//violazione primary key/unique
			else if (e1.getSQLState().equals("23505")) {
				JOptionPane.showMessageDialog(null,
						"Il dipendente che intendi creare esiste già.",
						"Errore Dipendente Esistente",
						JOptionPane.ERROR_MESSAGE);
			}
			//violazione vincolo tabella
			else if(e1.getSQLState().equals("23514")) {
				JOptionPane.showMessageDialog(null,
						"I valori inseriti sono errati.\n"
						+ "Controlla che:\n"
						+ "1)Il formato dell'email sia corretto\n"
						+ "2)Il dipendente sia maggiorenne e la sua data di nascita sia corretta\n"
						+ "3)Il salario previsto per lui sia positivo\n"
						+ "4)Nome e Cognome non sono del formato corretto o vuoto\n"
						+ "Contattare gli sviluppatori se non è nessuno dei seguenti casi.",
						"Errore Vincoli",
						JOptionPane.ERROR_MESSAGE);	
				//caso 1
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailTextField.getText());
				if (!matcher.find())
					emailLabel.setForeground(Color.RED);
				//caso 2
				dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() +1, giornoComboBox.getSelectedIndex() + 1);
				Period period = new Period(dataNascita, LocalDate.now(), PeriodType.yearMonthDay());
				int età = period.getYears();
				if (età < 18)
					dataNascitaLabel.setForeground(Color.RED);
				//caso 3
				if (Float.parseFloat(salarioTextField.getText()) < 0 || salarioTextField.getText().isEmpty())
					salarioLabel.setForeground(Color.RED);
			}
			//violazione definizione dati
			else if(e1.getSQLState().equals("22001")) {
				JOptionPane.showMessageDialog(null,
						"I dati inseriti sono errati.\n"
						+ "Controlla che:\n"
						+ "1)Nome e Cognome abbiano meno di 30 caratteri\n"
						+ "2)Email e Indirizzo abbiano meno di 100 caratteri\n"
						+ "3)Numero di telefono e Cellulare abbiano esattamente 10 caratteri\n"
						+ "4)La Password non superi i 50 caratteri e non sia vuota\n"
						+ "Contattare gli sviluppatori se non è nessuno dei seguenti casi.",
						"Errore Dati Inseriti",
						JOptionPane.ERROR_MESSAGE);
				//caso 1
				if (nomeTextField.getText().length() > 30)
					nomeLabel.setForeground(Color.RED);
				if (cognomeTextField.getText().length() > 30)
					cognomeLabel.setForeground(Color.RED);
				//caso 2
				if (emailTextField.getText().length() > 100)
					emailLabel.setForeground(Color.RED);
				if (indirizzoTextField.getText().length() > 100)
					indirizzoLabel.setForeground(Color.RED);
				//caso 3
				if (telefonoFissoTextField.getText().length() != 10)
					telefonoFissoLabel.setForeground(Color.RED);
				if (cellulareTextField.getText().length() != 10)
					cellulareLabel.setForeground(Color.RED);
				//caso 4
				if (passwordField.getText().length() > 50 || confermaPasswordField.getText().length() > 50 ||passwordField.getText().isBlank() || confermaPasswordField.getText().isBlank()) {
					passwordLabel.setForeground(Color.RED);
					confermaPasswordLabel.setForeground(Color.RED);
				}
			}
			//altri errori non contemplati
			else {
				JOptionPane.showMessageDialog(null,
						e1.getMessage() + "\nContattare uno sviluppatore.",
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Metodo che crea una nuova skill e aggiorna la lista delle skill disponibili
	private void creaSkill(ControllerDipendentiSegreteria controller) {
		try {
			controller.creaNuovaSkill(nuovaSkillTextField.getText());	//inserisce la nuova skill nel database
			DefaultListModel<Skill> skillModel = new DefaultListModel<Skill>();	//aggiorna la lista delle skill
			skillModel.addAll(controller.ottieniSkill());
			skillsList.setModel(skillModel);
			//aggiorna la combobox dei filtri
			skillFiltroComboBox.removeAllItems();
			skillFiltroComboBox.addItem(null);
			for (Skill skill: controller.ottieniSkill())
				skillFiltroComboBox.addItem(skill);
			nuovaSkillTextField.setText(""); //svuota il campo
			} 
		catch (SQLException e1) {
			//violazione primary key/unique
			if (e1.getSQLState().equals("23505")) {
				JOptionPane.showMessageDialog(null,
						"La skill " + nuovaSkillTextField.getText() + " esiste già.",
						"Errore Skill Esistente",
						JOptionPane.ERROR_MESSAGE);
			}
			//violazione not null
			else if (e1.getSQLState().equals("23502")) {
				JOptionPane.showMessageDialog(null,
						"Per favore inserici il nome della skill.",
						"Errore Nome Skill",
						JOptionPane.ERROR_MESSAGE);
			}
			//violazione vincolo tabella
			else if(e1.getSQLState().equals("23514")) {
				JOptionPane.showMessageDialog(null,
						"Il formato del nome della skill non è corretto\noppure il nome è vuoto.",
						"Errore Nome Skill",
						JOptionPane.ERROR_MESSAGE);
			}
			//violazione definizione dati
			else if(e1.getSQLState().equals("22001")) {
				JOptionPane.showMessageDialog(null,
						"Il nome della skill è troppo lungo./nControlla che non superi i 50 caratteri.",
						"Errore Skill Lunga",
						JOptionPane.ERROR_MESSAGE);
			}
			//altri errori non contemplati
			else {
				JOptionPane.showMessageDialog(null,
						e1.getMessage() + "\nContattare uno sviluppatore.",
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Metodo che applica i filtri alla tabella
	private void applicaFiltri(ControllerDipendentiSegreteria controller) {
		//stringa per cercare in nome, cognome ed email
		String nomeCognomeEmail = "%";
		if (!cercaTextField.getText().isBlank())
			nomeCognomeEmail = cercaTextField.getText();
		//età minima
		int etàMinima = 0;
		if (!etàMinimaTextField.getText().isBlank())
			etàMinima = parseInteger(etàMinimaTextField.getText(), etàMinima);
		//età massima
		int etàMassima = LocalDate.now().getYear() - 1900;
		if (!etàMassimaTextField.getText().isBlank())
			etàMassima = parseInteger(etàMassimaTextField.getText(), etàMassima);
		//salario minimo
		float salarioMinimo = 0f;
		if (!salarioMinimoTextField.getText().isBlank())
			parseFloat(salarioMinimoTextField.getText(), salarioMinimo);
		//salario massimo
		float salarioMassimo = controller.ottieniMaxStipendio();
		if (!salarioMassimoTextField.getText().isBlank())
			parseFloat(salarioMassimoTextField.getText(), salarioMassimo);
		//valutazione minima
		float valutazioneMinima = 0f;
		if (!valutazioneMinimaTextField.getText().isBlank())
			parseFloat(valutazioneMinimaTextField.getText(), valutazioneMinima);
		//valutazione massima
		float valutazioneMassima = 10f;
		if (!valutazioneMassimaTextField.getText().isBlank())
			parseFloat(valutazioneMassimaTextField.getText(), valutazioneMassima);
		//skill posseduta
		Skill skillCercata = null;
		skillCercata = (Skill) skillFiltroComboBox.getSelectedItem();
		//filtra dipendenti e aggiorna la tabella
		try {
			dataModelDipendente.setDipendenteTabella(controller.filtraDipendenti(nomeCognomeEmail, etàMinima, etàMassima, salarioMinimo, salarioMassimo, valutazioneMinima, valutazioneMassima, skillCercata));
			dipendentiTable.setModel(dataModelDipendente);
			dataModelDipendente.fireTableDataChanged();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Filtro fallito.",
					"Errore #"+e.getErrorCode(),
					JOptionPane.ERROR_MESSAGE);
			dataModelDipendente.fireTableDataChanged();;	//se fallisce il filtro allora non cambia nulla
		}
	}
	
	//Metodo che esegue un parseInteger con valore di default in caso di eccezione
	private int parseInteger(String numero, int valoreDefault) {
		try {
			return Integer.parseInt(numero);
		}
		catch(NumberFormatException e) {
			return valoreDefault;
		}
	}
	
	//Metodo che esegue un parseFloat con valore di default in caso di eccezione
	private float parseFloat(String numero, float valoreDefault) {
		try {
			return Float.parseFloat(numero);
			}
		catch(NumberFormatException e) {
			return valoreDefault;
		}
	}
	
	//Metodo che esegue l'update di un dipendente nel DB
	private void salvaModifiche(ControllerDipendentiSegreteria controller, Dipendente dipendenteModificato) {
		//setta le nuove proprietà al dipendente
		dipendenteModificato.setNome(nomeTextField.getText());	//nome
		dipendenteModificato.setCognome(cognomeTextField.getText()); //cognome
		//sesso	
		if (uomoRadioButton.isSelected())
			dipendenteModificato.setSesso('M');
		else
			dipendenteModificato.setSesso('F');
		//data di nascita
		dipendenteModificato.setDataNascita(new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() + 1, giornoComboBox.getSelectedIndex()+1));
		//luogo di nascita
		try {
			dipendenteModificato.setLuogoNascita(controller.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cittaComboBox.getSelectedIndex()));
		}
		catch(SQLException e1) {
			//errore select per comuni
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i comuni dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		dipendenteModificato.setEmail(emailTextField.getText()); //email
		dipendenteModificato.setPassword(passwordField.getText());	//password
		if (!telefonoFissoTextField.getText().equals(""))
			dipendenteModificato.setTelefonoCasa(telefonoFissoTextField.getText());	//telefono
		if (!cellulareTextField.getText().isBlank())
			dipendenteModificato.setCellulare(cellulareTextField.getText());    //cellulare
		dipendenteModificato.setIndirizzo(indirizzoTextField.getText());	//indirizzo
		//ottiene le skill selezionate
		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills.addAll(skillsList.getSelectedValuesList());
		dipendenteModificato.setSkills(skills);
		dipendenteModificato.setSalario(parseFloat(salarioTextField.getText(), 0f));	//ottieni il salario
		
		try {
			controller.aggiornaDipendente(dipendenteModificato); //aggiorna le info nel DB
			
			pulisciCampi();	//pulisce i campi
			
			aggiornaTabella(controller);	//aggiorna la tabella dei dipendenti
		}
		catch(SQLException e1) {
			//violazione not null
			if (e1.getSQLState().equals("23502")) {
				JOptionPane.showMessageDialog(null,
						"Alcuni campi obbligatori per l'aggiornamento sono vuoti.",
						"Errore Campi Obbligatori Vuoti",
						JOptionPane.ERROR_MESSAGE);
				campiObbligatoriRossi();	//colora di rosso i campi vuoti
				}
			//violazione primary key/unique
			else if (e1.getSQLState().equals("23505")) {
				JOptionPane.showMessageDialog(null,
						"Il dipendente che intendi aggiornare è uguale a uno già esistente.\nControllare email o codice fiscale.",
						"Errore Dipendente Esistente",
						JOptionPane.ERROR_MESSAGE);
			}
			//violazione vincolo tabella
			else if(e1.getSQLState().equals("23514")) {
				JOptionPane.showMessageDialog(null,
						"I valori inseriti sono errati.\n"
						+ "Controlla che:\n"
						+ "1)Il formato dell'email sia corretto\n"
						+ "2)Il dipendente sia maggiorenne e la sua data di nascita sia corretta\n"
						+ "3)Il salario previsto per lui sia positivo\n"
						+ "4)Nome e Cognome non sono del formato corretto o vuoto\n"
						+ "Contattare gli sviluppatori se non è nessuno dei seguenti casi.",
						"Errore Vincoli",
						JOptionPane.ERROR_MESSAGE);	
				//caso 1
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailTextField.getText());
				if (!matcher.find())
					emailLabel.setForeground(Color.RED);
				//caso 2
				LocalDate dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() +1, giornoComboBox.getSelectedIndex() + 1);
				Period period = new Period(dataNascita, LocalDate.now(), PeriodType.yearMonthDay());
				int età = period.getYears();
				if (età < 18)
					dataNascitaLabel.setForeground(Color.RED);
				//caso 3
				if (Float.parseFloat(salarioTextField.getText()) < 0)
					salarioLabel.setForeground(Color.RED);
			}
			//violazione definizione dati
			else if(e1.getSQLState().equals("22001")) {
				JOptionPane.showMessageDialog(null,
						"I dati inseriti sono errati.\n"
						+ "Controlla che:\n"
						+ "1)Nome e Cognome abbiano meno di 30 caratteri\n"
						+ "2)Email e Indirizzo abbiano meno di 100 caratteri\n"
						+ "3)Numero di telefono e Cellulare abbiano esattamente 10 caratteri\n"
						+ "4)La Password non superi i 50 caratteri o non sia vuota\n"
						+ "Contattare gli sviluppatori se non è nessuno dei seguenti casi.",
						"Errore Dati Inseriti",
						JOptionPane.ERROR_MESSAGE);
				//caso 1
				if (nomeTextField.getText().length() > 30)
					nomeLabel.setForeground(Color.RED);
				if (cognomeTextField.getText().length() > 30)
					cognomeLabel.setForeground(Color.RED);
				//caso 2
				if (emailTextField.getText().length() > 100)
					emailLabel.setForeground(Color.RED);
				if (indirizzoTextField.getText().length() > 100)
					indirizzoLabel.setForeground(Color.RED);
				//caso 3
				if (telefonoFissoTextField.getText().length() != 10)
					telefonoFissoLabel.setForeground(Color.RED);
				if (cellulareTextField.getText().length() != 10)
					cellulareLabel.setForeground(Color.RED);
				//caso 4
				if (passwordField.getText().length() > 50 || confermaPasswordField.getText().length() > 50 ||passwordField.getText().isBlank() || confermaPasswordField.getText().isBlank()) {
					passwordLabel.setForeground(Color.RED);
					confermaPasswordLabel.setForeground(Color.RED);
				}
			}
			//dipendente inesistente/errato
			else if (e1.getErrorCode() == 0) {
				JOptionPane.showMessageDialog(null,
						"Fallito nel modificare il dipendente.\nImpossibile trovarlo nel database.",
						"Errore Dipendente Non Trovato",
						JOptionPane.ERROR_MESSAGE);
			}
			//altri errori non contemplati
			else {
				JOptionPane.showMessageDialog(null,
						e1.getMessage() + "\nContattare uno sviluppatore.",
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Metodo che pulsice tutti i campi
	private void pulisciCampi() {
		nomeTextField.setText(""); //nome
		cognomeTextField.setText("");	//cognome
		emailTextField.setText("");	//email
		//sesso
		uomoRadioButton.setSelected(false);;
		donnaRadioButton.setSelected(false);
		//data di nascita
		giornoComboBox.setSelectedIndex(0);
		meseComboBox.setSelectedIndex(0);
		annoComboBox.setSelectedIndex((int) annoComboBox.getItemCount()/2);;
		//luogo di nascita
		provinciaComboBox.setSelectedIndex(0);
		indirizzoTextField.setText(""); //indirizzo
		cellulareTextField.setText(""); //cellulare
		telefonoFissoTextField.setText(""); //telefono casa
		passwordField.setText(""); //password
		confermaPasswordField.setText(""); //conferma password
		salarioTextField.setText(""); //salario
		skillsList.clearSelection(); //skills
	}
}
