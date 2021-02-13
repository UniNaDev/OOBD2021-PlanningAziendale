//Frame per la gestione dei dipendenti lato segreteria.

package gui.segreteria;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.ListSelectionModel;


public class GestioneDipendenti extends JFrame {
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
	private JLabel pulisciCampiLabel;
	
	private ArrayList<String> anni = new ArrayList<String>();
	
	private String nome;
	private String cognome;
	private char sesso = 'M';
	private LocalDate dataNascita = new LocalDate(1900,1,1);
	private LuogoNascita luogoNascita = null;
	private String email;
	private String password = null;
	private String telefono = null;
	private String cellulare = null;
	private String indirizzo;
	private float salario;
	
	private Dipendente dipendenteSelezionato;
	
	private final String VIOLAZIONE_NOT_NULL = "23502";
	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	private final String VIOLAZIONE_VINCOLI_TABELLA = "23514";
	private final String VIOLAZIONE_LUNGHEZZA_STRINGA = "22001";
	private final String VIOLAZIONE_DATA_INESISTENTE = "22008";
	
	public GestioneDipendenti(ControllerDipendentiSegreteria controller) {
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
		setLocationRelativeTo(null);
		
		contentPane.setLayout(null);
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoPanel.setBounds(28, 11, 1286, 650);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		dipendenteLabel = new JLabel("Dipendente");
		dipendenteLabel.setBounds(517, 11, 251, 75);
		infoPanel.add(dipendenteLabel);
		dipendenteLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/employee_64.png")));
		dipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaNomeLabel = new JLabel("");
		iconaNomeLabel.setBounds(41, 128, 46, 14);
		infoPanel.add(iconaNomeLabel);
		iconaNomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaNomeLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/nome_16.png")));
		
		nomeTextField = new JTextField();
		nomeTextField.setBounds(97, 128, 162, 20);
		infoPanel.add(nomeTextField);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomeTextField.setColumns(10);

		cognomeTextField = new JTextField();
		cognomeTextField.setBounds(282, 128, 168, 20);
		infoPanel.add(cognomeTextField);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cognomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cognomeTextField.setColumns(10);
		
		nomeLabel = new JLabel("Nome*");
		nomeLabel.setBounds(97, 148, 66, 14);
		infoPanel.add(nomeLabel);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		cognomeLabel = new JLabel("Cognome*");
		cognomeLabel.setBounds(282, 148, 84, 14);
		infoPanel.add(cognomeLabel);
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		cognomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setBounds(41, 172, 46, 14);
		infoPanel.add(iconaEmailLabel);
		iconaEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		iconaEmailLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/email_16.png")));
		
		emailTextField = new JTextField();
		emailTextField.setBounds(97, 169, 267, 20);
		infoPanel.add(emailTextField);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setColumns(10);
		
		emailLabel = new JLabel("Email*");
		emailLabel.setBounds(99, 193, 66, 14);
		infoPanel.add(emailLabel);
		emailLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		sessoLabel = new JLabel("Sesso*");
		sessoLabel.setBounds(97, 236, 46, 14);
		infoPanel.add(sessoLabel);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		uomoRadioButton = new JRadioButton("Uomo");
		uomoRadioButton.setBounds(163, 232, 61, 23);
		infoPanel.add(uomoRadioButton);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		buttonGroup.add(uomoRadioButton);
		
		donnaRadioButton = new JRadioButton("Donna");
		donnaRadioButton.setBounds(235, 232, 66, 23);
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
		
		dataNascitaLabel = new JLabel("Data di nascita*");
		dataNascitaLabel.setBounds(96, 284, 117, 14);
		infoPanel.add(dataNascitaLabel);
		dataNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
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
		
		meseComboBox = new JComboBox<Object>();
		meseComboBox.setBounds(282, 276, 44, 22);
		meseComboBox.setUI(new BasicComboBoxUI());
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		infoPanel.add(meseComboBox);
		
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
		
		provNascitaLabel = new JLabel("Prov. di nascita*");
		provNascitaLabel.setBounds(97, 327, 128, 14);
		infoPanel.add(provNascitaLabel);
		provNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		provNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
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
				public void actionPerformed(ActionEvent e) {
					cittaComboBox.setEnabled(true);
					cittaComboBox.removeAllItems();
					try {
						for(LuogoNascita comune: controller.ottieniComuni(provinciaComboBox.getSelectedItem().toString()))
								cittaComboBox.addItem(comune.getNomeComune());
					} 
					catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i comuni di tale provincia dal database.\nControllare che la connessione al database sia stabilita.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le province dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		cittaComboBox = new JComboBox();
		cittaComboBox.setBounds(229, 350, 210, 22);
		infoPanel.add(cittaComboBox);
		cittaComboBox.setUI(new BasicComboBoxUI());
		cittaComboBox.setBackground(Color.WHITE);
		cittaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cittaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		cittaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		iconaIndirizzoLabel = new JLabel("");
		iconaIndirizzoLabel.setBounds(41, 385, 46, 30);
		infoPanel.add(iconaIndirizzoLabel);
		iconaIndirizzoLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/indirizzo_16.png")));
		iconaIndirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		indirizzoLabel = new JLabel("Indirizzo*");
		indirizzoLabel.setBounds(97, 418, 84, 14);
		infoPanel.add(indirizzoLabel);
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		iconaCellulareLabel = new JLabel("");
		iconaCellulareLabel.setBounds(41, 452, 46, 30);
		infoPanel.add(iconaCellulareLabel);
		iconaCellulareLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/Icone/cellulare_16.png")));
		iconaCellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setBounds(96, 462, 84, 14);
		infoPanel.add(cellulareLabel);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		cellulareTextField = new JTextField();
		cellulareTextField.setBounds(209, 457, 86, 20);
		infoPanel.add(cellulareTextField);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cellulareTextField.setColumns(10);
		
		telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setBounds(96, 493, 103, 14);
		infoPanel.add(telefonoFissoLabel);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
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
		
		passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(99, 541, 66, 14);
		infoPanel.add(passwordLabel);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(239, 530, 185, 20);
		infoPanel.add(passwordField);
		passwordField.setEchoChar('*');
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		
		confermaPasswordLabel = new JLabel("Conferma Password*");
		confermaPasswordLabel.setBounds(99, 566, 128, 14);
		infoPanel.add(confermaPasswordLabel);
		confermaPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		confermaPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		confermaPasswordField = new JPasswordField();
		confermaPasswordField.setBounds(239, 561, 185, 20);
		infoPanel.add(confermaPasswordField);
		confermaPasswordField.setEchoChar('*');
		confermaPasswordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confermaPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
				
		indirizzoTextField = new JTextField();
		indirizzoTextField.setBounds(97, 395, 267, 20);
		infoPanel.add(indirizzoTextField);
		indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		indirizzoTextField.setColumns(10);
		indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
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
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le skill dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		skillsLabel = new JLabel("Skills");
		skillsLabel.setBounds(745, 222, 47, 14);
		infoPanel.add(skillsLabel);
		skillsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		skillsLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		iconaSkillsLabel = new JLabel("");
		iconaSkillsLabel.setBounds(750, 181, 31, 39);
		infoPanel.add(iconaSkillsLabel);
		iconaSkillsLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/skills_32.png")));
		iconaSkillsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		nuovaSkillButton = new JButton("Crea Nuova Skill");
		nuovaSkillButton.setBounds(989, 404, 139, 23);
		infoPanel.add(nuovaSkillButton);
		nuovaSkillButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				nuovaSkillButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				nuovaSkillButton.setBackground(Color.WHITE);
			}	
		});
		nuovaSkillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaSkill(controller);
			}
		});
		nuovaSkillButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovaSkillButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		nuovaSkillButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovaSkillButton.setBackground(Color.WHITE);
		
		nuovaSkillTextField = new JTextField();
		nuovaSkillTextField.setBounds(811, 405, 168, 22);
		infoPanel.add(nuovaSkillTextField);
		nuovaSkillTextField.setColumns(10);
		nuovaSkillTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		salarioTextField = new JTextField("0.00");
		salarioTextField.setBounds(832, 489, 141, 22);
		infoPanel.add(salarioTextField);
		salarioTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioTextField.setColumns(10);
		salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		euroLabel = new JLabel("€");
		euroLabel.setBounds(982, 483, 66, 36);
		infoPanel.add(euroLabel);
		euroLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		salarioLabel = new JLabel("Salario");
		salarioLabel.setBounds(832, 516, 66, 14);
		infoPanel.add(salarioLabel);
		salarioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		iconaSalarioLabel = new JLabel("");
		iconaSalarioLabel.setBounds(774, 483, 46, 30);
		infoPanel.add(iconaSalarioLabel);
		iconaSalarioLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/salario_16.png")));
		iconaSalarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		mostraPasswordLabel = new JLabel("");
		mostraPasswordLabel.setBounds(448, 527, 46, 26);
		infoPanel.add(mostraPasswordLabel);
		mostraPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mostraPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/showpass_24.png")));
		mostraPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				passwordField.setEchoChar((char)0);
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				passwordField.setEchoChar('*');
			}
		});
		
		mostraConfirmPasswordLabel = new JLabel("");
		mostraConfirmPasswordLabel.setBounds(448, 557, 47, 29);
		infoPanel.add(mostraConfirmPasswordLabel);
		mostraConfirmPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mostraConfirmPasswordLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/showpass_24.png")));
		mostraConfirmPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				confermaPasswordField.setEchoChar((char)0);
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				confermaPasswordField.setEchoChar('*');
			}
		});
		
		inserireSkillLabel = new JLabel("N.B. Per inserire più skill contemporanemante");
		inserireSkillLabel.setBounds(838, 435, 290, 20);
		infoPanel.add(inserireSkillLabel);
		inserireSkillLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		inserireSkillLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		premereCtrlLabel = new JLabel("premere CTRL+clic tasto sx mouse sulla skill");
		premereCtrlLabel.setBounds(838, 452, 290, 20);
		infoPanel.add(premereCtrlLabel);
		premereCtrlLabel.setHorizontalAlignment(SwingConstants.LEFT);
		premereCtrlLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		
		campiObbligatoriLabel = new JLabel("* Campi obbligatori");
		campiObbligatoriLabel.setBounds(1119, 619, 128, 20);
		infoPanel.add(campiObbligatoriLabel);
		campiObbligatoriLabel.setHorizontalAlignment(SwingConstants.LEFT);
		campiObbligatoriLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
		
		cittàDiNascitaLabel = new JLabel("Città di nascita*");
		cittàDiNascitaLabel.setBounds(97, 354, 128, 14);
		infoPanel.add(cittàDiNascitaLabel);
		cittàDiNascitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cittàDiNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(601, 100, 2, 527);
		infoPanel.add(separator);
		
		JLabel valutazioneLabel = new JLabel("Valutazione");
		valutazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valutazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		valutazioneLabel.setBounds(832, 545, 185, 14);
		infoPanel.add(valutazioneLabel);
		
		pulisciCampiLabel = new JLabel("");
		pulisciCampiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pulisciCampi();
				
				dipendenteSelezionato = null;
				dipendentiTable.clearSelection();
			}
		});
		pulisciCampiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciCampiLabel.setIcon(new ImageIcon(GestioneDipendenti.class.getResource("/icone/refresh.png")));
		pulisciCampiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pulisciCampiLabel.setBounds(41, 100, 16, 14);
		infoPanel.add(pulisciCampiLabel);
		
		esciButton = new JButton("Esci");
		esciButton.setBounds(25, 871, 97, 23);
		contentPane.add(esciButton);
		esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		esciButton.setBackground(Color.WHITE);
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tornaAiPlanner();
			}
		});
		esciButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				esciButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				esciButton.setBackground(Color.WHITE);
			}
		});
		
		comandiPanel = new JPanel();
		comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel.setBounds(28, 672, 1289, 38);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		creaAccountButton = new JButton("Crea");
		creaAccountButton.setToolTipText("Crea un nuovo dipendente con le informazioni inserite");
		creaAccountButton.setBounds(1204, 7, 67, 23);
		comandiPanel.add(creaAccountButton);
		creaAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaAccountButton.setBackground(Color.WHITE);
		creaAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaAccountButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		creaAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				campiObbligatoriNeri();
				
				if (!checkCampiObbligatoriVuoti() && passwordField.getText().equals(confermaPasswordField.getText()))
					creaDipendente(controller);
				else if(!passwordField.getText().equals(confermaPasswordField.getText())) {	
					JOptionPane.showMessageDialog(null,
							"Le password inserite sono diverse",
							"Errore Conferma Password",
							JOptionPane.ERROR_MESSAGE);
					passwordLabel.setForeground(Color.RED);
					confermaPasswordLabel.setForeground(Color.RED);
				}
				else {
					JOptionPane.showMessageDialog(null,
							"Alcuni campi obbligatori sono vuoti.",
							"Errore Campi Obbligatori Vuoti",
							JOptionPane.ERROR_MESSAGE);
					campiObbligatoriRossi();
				}
			}
		});
		creaAccountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaAccountButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaAccountButton.setBackground(Color.WHITE);			}
		});
		
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cercaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		cercaTextField.setBounds(87, 9, 162, 20);
		comandiPanel.add(cercaTextField);
		cercaTextField.setColumns(10);
		
		JButton filtraButton = new JButton("Filtra");
		filtraButton.setToolTipText("Clicca per applicare i filtri");
		filtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltri(controller);
			}
		});
		filtraButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtraButton.setBackground(Color.WHITE);
		filtraButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtraButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtraButton.setBounds(10, 9, 67, 20);
		filtraButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				filtraButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				filtraButton.setBackground(Color.WHITE);
			}
		});
		comandiPanel.add(filtraButton);
		
		etàMinimaTextField = new JTextField();
		etàMinimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		etàMinimaTextField.setText("min");
		etàMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		etàMinimaTextField.setBounds(266, 9, 39, 20);
		comandiPanel.add(etàMinimaTextField);
		etàMinimaTextField.setColumns(10);
		
		etàFiltroLabel = new JLabel("Età");
		etàFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		etàFiltroLabel.setBounds(308, 12, 28, 14);
		comandiPanel.add(etàFiltroLabel);
		
		etàMassimaTextField = new JTextField();
		etàMassimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		etàMassimaTextField.setText("max");
		etàMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
		etàMassimaTextField.setColumns(10);
		etàMassimaTextField.setBounds(333, 9, 39, 20);
		comandiPanel.add(etàMassimaTextField);
		
		salarioMinimoTextField = new JTextField();
		salarioMinimoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salarioMinimoTextField.setText("min");
		salarioMinimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioMinimoTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioMinimoTextField.setColumns(10);
		salarioMinimoTextField.setBounds(402, 9, 67, 20);
		comandiPanel.add(salarioMinimoTextField);
		
		salarioFiltroLabel = new JLabel("Salario");
		salarioFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		salarioFiltroLabel.setBounds(472, 12, 57, 14);
		comandiPanel.add(salarioFiltroLabel);
		
		salarioMassimoTextField = new JTextField();
		salarioMassimoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salarioMassimoTextField.setText("max");
		salarioMassimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioMassimoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		salarioMassimoTextField.setColumns(10);
		salarioMassimoTextField.setBounds(524, 9, 80, 20);
		comandiPanel.add(salarioMassimoTextField);
		
		valutazioneMinimaTextField = new JTextField();
		valutazioneMinimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		valutazioneMinimaTextField.setText("min");
		valutazioneMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		valutazioneMinimaTextField.setColumns(10);
		valutazioneMinimaTextField.setBounds(636, 9, 39, 20);
		comandiPanel.add(valutazioneMinimaTextField);
		
		valutazioneFiltroLabel = new JLabel("Valutazione");
		valutazioneFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		valutazioneFiltroLabel.setBounds(678, 12, 77, 14);
		comandiPanel.add(valutazioneFiltroLabel);
		
		valutazioneMassimaTextField = new JTextField();
		valutazioneMassimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		valutazioneMassimaTextField.setText("max");
		valutazioneMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
		valutazioneMassimaTextField.setColumns(10);
		valutazioneMassimaTextField.setBounds(757, 9, 39, 20);
		comandiPanel.add(valutazioneMassimaTextField);
		
		salvaModificheButton = new JButton("<html> <center> Salva <br> Modifiche <html>");
		salvaModificheButton.setToolTipText("<html>Clicca per salvare le modifiche <br>delle informazioni del dipendente<html>");
		salvaModificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				campiObbligatoriNeri();
				if (dipendenteSelezionato != null) {
					if (!checkCampiObbligatoriVuoti() && passwordField.getText().equals(confermaPasswordField.getText())) {
						salvaModifiche(controller, dipendenteSelezionato);
					}
					else if(!passwordField.getText().equals(confermaPasswordField.getText()))
					{	
						JOptionPane.showMessageDialog(null,
								"Le password inserite sono diverse",
								"Errore Conferma Password",
								JOptionPane.ERROR_MESSAGE);
						passwordLabel.setForeground(Color.RED);
						confermaPasswordLabel.setForeground(Color.RED);
					}
					else {
						JOptionPane.showMessageDialog(null,
								"Alcuni campi obbligatori sono vuoti.",
								"Errore Campi Obbligatori Vuoti",
								JOptionPane.ERROR_MESSAGE);
						campiObbligatoriRossi();
					}
				}
			}
		});
		salvaModificheButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		salvaModificheButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salvaModificheButton.setBackground(Color.WHITE);
		salvaModificheButton.setBounds(1099, 2, 95, 34);
		salvaModificheButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				salvaModificheButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				salvaModificheButton.setBackground(Color.WHITE);
			}
		});
		comandiPanel.add(salvaModificheButton);
		
		JButton eliminaAccountButton = new JButton("Elimina");
		eliminaAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricavaInfoDipendente(controller);
				
				try {
					Dipendente temp = new Dipendente(null, nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,salario,password, 0f);
					temp.setCf(temp.generaCF());
					controller.eliminaDipendente(temp);
					
					pulisciCampi();
					
					aggiornaTabella(controller);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							e1.getMessage() + "\nContattare uno sviluppatore",
							"Errore #" + e1.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
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
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				eliminaAccountButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				eliminaAccountButton.setBackground(Color.WHITE);
			}
		});
		comandiPanel.add(eliminaAccountButton);
		
		JLabel skillFiltroLabel = new JLabel("Skill");
		skillFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		skillFiltroLabel.setBounds(814, 12, 39, 14);
		comandiPanel.add(skillFiltroLabel);
		
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
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le skill dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		tableScrollPanel = new JScrollPane();
		tableScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		tableScrollPanel.setBounds(25, 721, 1289, 139);
		contentPane.add(tableScrollPanel);
		
		dataModelDipendente = new DipendentiTableModel();
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());
			dipendentiTable = new JTable(dataModelDipendente);
			dipendentiTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dipendentiTable.getModel());
			dipendentiTable.setRowSorter(sorter);
			
			dipendentiTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					campiObbligatoriNeri();
					
					int row = dipendentiTable.getSelectedRow();
					row = dipendentiTable.convertRowIndexToModel(row);
					dipendenteSelezionato = dataModelDipendente.getSelected(row);
					
					pulisciCampi();
					
					nomeTextField.setText(dipendenteSelezionato.getNome());
					cognomeTextField.setText(dipendenteSelezionato.getCognome());
					emailTextField.setText(dipendenteSelezionato.getEmail());
					if(dipendenteSelezionato.getSesso() == 'M') {
						uomoRadioButton.setSelected(true);
						donnaRadioButton.setSelected(false);
					}
					else {
						uomoRadioButton.setSelected(false);		
						donnaRadioButton.setSelected(true);
					}
					giornoComboBox.setSelectedIndex(dipendenteSelezionato.getDataNascita().getDayOfMonth()-1);
					meseComboBox.setSelectedIndex(dipendenteSelezionato.getDataNascita().getMonthOfYear()-1);
					annoComboBox.setSelectedIndex(dipendenteSelezionato.getDataNascita().getYear()-1900);
					provinciaComboBox.setSelectedItem(dipendenteSelezionato.getLuogoNascita().getNomeProvincia());
					cittaComboBox.setSelectedItem(dipendenteSelezionato.getLuogoNascita().getNomeComune());
					cittaComboBox.setEnabled(false);
					indirizzoTextField.setText(dipendenteSelezionato.getIndirizzo());
					cellulareTextField.setText(dipendenteSelezionato.getCellulare());
					telefonoFissoTextField.setText(dipendenteSelezionato.getTelefonoCasa());
					passwordField.setText(dipendenteSelezionato.getPassword());
					confermaPasswordField.setText(dipendenteSelezionato.getPassword());
					salarioTextField.setText(Float.toString(dipendenteSelezionato.getSalario()));
					ArrayList<Skill> skills = dipendenteSelezionato.getSkills();
					int[] indici = new int[skills.size()];
					int i = 0;
					for (Skill skill: skills) {
						try {
							indici[i] = controller.ottieniSkill().indexOf(skill);
							i++;
							skillsList.setSelectedIndices(indici);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere tutte le skill dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
						}
					}
					valutazioneLabel.setText("Valutazione: " + String.format("%.2f",dipendenteSelezionato.getValutazione()));	
				}
			});
			dipendentiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dipendentiTable.setFont(new Font("Consolas", Font.PLAIN, 11));
			tableScrollPanel.setViewportView(dipendentiTable);
		} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i dipendenti dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	
	private void ricavaInfoDipendente(ControllerDipendentiSegreteria controller) {
		nome = nomeTextField.getText();
		cognome = cognomeTextField.getText();
		if (uomoRadioButton.isSelected())
			sesso = 'M';
		else
			sesso = 'F';
		dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() + 1, giornoComboBox.getSelectedIndex()+1);
		try {
			luogoNascita = controller.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cittaComboBox.getSelectedIndex());
		}
		catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i comuni dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		email = emailTextField.getText();
		if (!passwordField.getText().isBlank())
			password = passwordField.getText();
		if (!telefonoFissoTextField.getText().equals(""))
			telefono = telefonoFissoTextField.getText();
		if (!cellulareTextField.getText().isBlank())
			cellulare = cellulareTextField.getText();
		indirizzo = indirizzoTextField.getText();
		salario = parseFloat(salarioTextField.getText(), 0f);
	}
	
	private void aggiornaTabella(ControllerDipendentiSegreteria controller) {
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());
			dipendentiTable.setModel(dataModelDipendente);
			dataModelDipendente.fireTableDataChanged();
		} 
		catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i dipendenti dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void campiObbligatoriRossi() {
		if (nomeTextField.getText().isBlank())
			nomeLabel.setForeground(Color.RED);
		if (cognomeTextField.getText().isBlank())
			cognomeLabel.setForeground(Color.RED);
		if (emailTextField.getText().isBlank())
			emailLabel.setForeground(Color.RED);
		if (!uomoRadioButton.isSelected() && !donnaRadioButton.isSelected())
			sessoLabel.setForeground(Color.RED);
		if (giornoComboBox.getSelectedItem() == null || meseComboBox.getSelectedItem() == null || annoComboBox.getSelectedItem() == null)
			dataNascitaLabel.setForeground(Color.RED);
		if (indirizzoTextField.getText().isBlank())
			indirizzoLabel.setForeground(Color.RED);
		if (provinciaComboBox.getSelectedItem() == null || cittaComboBox.getSelectedItem() == null)
			cittàDiNascitaLabel.setForeground(Color.RED);
		if (salarioTextField.getText().isBlank())
			salarioLabel.setForeground(Color.RED);
		if (passwordField.getText().isBlank() || confermaPasswordField.getText().isBlank()) {
			passwordLabel.setForeground(Color.RED);
			confermaPasswordLabel.setForeground(Color.RED);
		}
	}
	
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
	
	private void creaDipendente(ControllerDipendentiSegreteria controller) {
		ricavaInfoDipendente(controller);
		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills.addAll(skillsList.getSelectedValuesList());
		Dipendente dipendente = new Dipendente(null, nome,cognome,sesso,dataNascita,luogoNascita,indirizzo,email,telefono,cellulare,salario,password, 0f);
		dipendente.setSkills(skills);
		try {
			controller.creaDipendente(dipendente);
			
			pulisciCampi();
			
			aggiornaTabella(controller);
		}
		catch(SQLException e1) {
			if (e1.getSQLState().equals(VIOLAZIONE_NOT_NULL)) {
				JOptionPane.showMessageDialog(null,
						"Alcuni campi obbligatori per la creazione sono vuoti.",
						"Errore Campi Obbligatori Vuoti",
						JOptionPane.ERROR_MESSAGE);
				campiObbligatoriRossi();
			}
			else if (e1.getSQLState().equals(VIOLAZIONE_PKEY_UNIQUE)) {
				JOptionPane.showMessageDialog(null,
						"Il dipendente che intendi creare esiste già.",
						"Errore Dipendente Esistente",
						JOptionPane.ERROR_MESSAGE);
			}
			else if(e1.getSQLState().equals(VIOLAZIONE_VINCOLI_TABELLA)) {
				JOptionPane.showMessageDialog(null,
						"I valori inseriti sono errati.\n"
						+ "Controlla che:\n"
						+ "1)Il formato dell'email sia corretto\n"
						+ "2)Il dipendente sia maggiorenne e la sua data di nascita sia corretta\n"
						+ "3)Il salario previsto per lui sia positivo\n"
						+ "4)Nome e Cognome non siano del formato corretto o vuoti\n"
						+ "Contattare gli sviluppatori se non è nessuno dei seguenti casi.",
						"Errore Vincoli",
						JOptionPane.ERROR_MESSAGE);	
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailTextField.getText());
				if (!matcher.find())
					emailLabel.setForeground(Color.RED);
				dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() +1, giornoComboBox.getSelectedIndex() + 1);
				Period period = new Period(dataNascita, LocalDate.now(), PeriodType.yearMonthDay());
				int età = period.getYears();
				if (età < 18)
					dataNascitaLabel.setForeground(Color.RED);
				if (Float.parseFloat(salarioTextField.getText()) < 0 || salarioTextField.getText().isEmpty())
					salarioLabel.setForeground(Color.RED);
			}
			else if(e1.getSQLState().equals(VIOLAZIONE_LUNGHEZZA_STRINGA)) {
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
				if (nomeTextField.getText().length() > 30)
					nomeLabel.setForeground(Color.RED);
				if (cognomeTextField.getText().length() > 30)
					cognomeLabel.setForeground(Color.RED);
				if (emailTextField.getText().length() > 100)
					emailLabel.setForeground(Color.RED);
				if (indirizzoTextField.getText().length() > 100)
					indirizzoLabel.setForeground(Color.RED);
				if (telefonoFissoTextField.getText().length() != 10)
					telefonoFissoLabel.setForeground(Color.RED);
				if (cellulareTextField.getText().length() != 10)
					cellulareLabel.setForeground(Color.RED);
				if (passwordField.getText().length() > 50 || confermaPasswordField.getText().length() > 50 ||passwordField.getText().isBlank() || confermaPasswordField.getText().isBlank()) {
					passwordLabel.setForeground(Color.RED);
					confermaPasswordLabel.setForeground(Color.RED);
				}
			}
			else if (e1.getSQLState().equals(VIOLAZIONE_DATA_INESISTENTE)){
				JOptionPane.showMessageDialog(null,
						"Accertarsi che la data di nascita esista davvero.",
						"Data Inesistente",
						JOptionPane.ERROR_MESSAGE);
				dataNascitaLabel.setForeground(Color.RED);
			}
			else {
				JOptionPane.showMessageDialog(null,
						e1.getMessage() + "\nContattare uno sviluppatore.",
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void creaSkill(ControllerDipendentiSegreteria controller) {
		try {
			controller.creaNuovaSkill(nuovaSkillTextField.getText());
			DefaultListModel<Skill> skillModel = new DefaultListModel<Skill>();
			skillModel.addAll(controller.ottieniSkill());
			skillsList.setModel(skillModel);
			skillFiltroComboBox.removeAllItems();
			skillFiltroComboBox.addItem(null);
			for (Skill skill: controller.ottieniSkill())
				skillFiltroComboBox.addItem(skill);
			nuovaSkillTextField.setText("");
			} 
		catch (SQLException e1) {
			if (e1.getSQLState().equals(VIOLAZIONE_PKEY_UNIQUE)) {
				JOptionPane.showMessageDialog(null,
						"La skill " + nuovaSkillTextField.getText() + " esiste già.",
						"Errore Skill Esistente",
						JOptionPane.ERROR_MESSAGE);
			}
			else if (e1.getSQLState().equals(VIOLAZIONE_NOT_NULL)) {
				JOptionPane.showMessageDialog(null,
						"Per favore inserici il nome della skill.",
						"Errore Nome Skill",
						JOptionPane.ERROR_MESSAGE);
			}
			else if(e1.getSQLState().equals(VIOLAZIONE_VINCOLI_TABELLA)) {
				JOptionPane.showMessageDialog(null,
						"Il formato del nome della skill non è corretto\noppure il nome è vuoto.",
						"Errore Nome Skill",
						JOptionPane.ERROR_MESSAGE);
			}
			else if(e1.getSQLState().equals(VIOLAZIONE_LUNGHEZZA_STRINGA)) {
				JOptionPane.showMessageDialog(null,
						"Il nome della skill è troppo lungo./nControlla che non superi i 50 caratteri.",
						"Errore Skill Lunga",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null,
						e1.getMessage() + "\nContattare uno sviluppatore.",
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void applicaFiltri(ControllerDipendentiSegreteria controller) {
		String nomeCognomeEmail = "%";
		if (!cercaTextField.getText().isBlank())
			nomeCognomeEmail = cercaTextField.getText();
		int etàMinima = 0;
		if (!etàMinimaTextField.getText().isBlank())
			etàMinima = parseInteger(etàMinimaTextField.getText(), etàMinima);
		int etàMassima = LocalDate.now().getYear() - 1900;
		if (!etàMassimaTextField.getText().isBlank())
			etàMassima = parseInteger(etàMassimaTextField.getText(), etàMassima);
		float salarioMinimo = 0f;
		if (!salarioMinimoTextField.getText().isBlank())
			parseFloat(salarioMinimoTextField.getText(), salarioMinimo);
		float salarioMassimo = controller.ottieniMaxStipendio();
		if (!salarioMassimoTextField.getText().isBlank())
			parseFloat(salarioMassimoTextField.getText(), salarioMassimo);
		float valutazioneMinima = 0f;
		if (!valutazioneMinimaTextField.getText().isBlank())
			parseFloat(valutazioneMinimaTextField.getText(), valutazioneMinima);
		float valutazioneMassima = 10f;
		if (!valutazioneMassimaTextField.getText().isBlank())
			parseFloat(valutazioneMassimaTextField.getText(), valutazioneMassima);
		Skill skillCercata = null;
		skillCercata = (Skill) skillFiltroComboBox.getSelectedItem();
		try {
			dataModelDipendente.setDipendenteTabella(controller.filtraDipendenti(nomeCognomeEmail, etàMinima, etàMassima, salarioMinimo, salarioMassimo, valutazioneMinima, valutazioneMassima, skillCercata));
			dipendentiTable.setModel(dataModelDipendente);
			dataModelDipendente.fireTableDataChanged();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Filtro fallito.\nControllare che la connessione al database sia stabilita",
					"Errore #" + e.getErrorCode(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private int parseInteger(String numero, int valoreDefault) {
		try {
			return Integer.parseInt(numero);
		}
		catch(NumberFormatException e) {
			return valoreDefault;
		}
	}
	
	private float parseFloat(String numero, float valoreDefault) {
		try {
			return Float.parseFloat(numero);
			}
		catch(NumberFormatException e) {
			return valoreDefault;
		}
	}
	
	private void salvaModifiche(ControllerDipendentiSegreteria controller, Dipendente dipendenteModificato) {
		dipendenteModificato.setNome(nomeTextField.getText());
		dipendenteModificato.setCognome(cognomeTextField.getText());
		if (uomoRadioButton.isSelected())
			dipendenteModificato.setSesso('M');
		else
			dipendenteModificato.setSesso('F');
		dipendenteModificato.setDataNascita(new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() + 1, giornoComboBox.getSelectedIndex()+1));
		try {
			dipendenteModificato.setLuogoNascita(controller.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(cittaComboBox.getSelectedIndex()));
		}
		catch(SQLException e1) {
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i comuni dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		dipendenteModificato.setEmail(emailTextField.getText());
		dipendenteModificato.setPassword(passwordField.getText());
		if (!telefonoFissoTextField.getText().equals(""))
			dipendenteModificato.setTelefonoCasa(telefonoFissoTextField.getText());
		if (!cellulareTextField.getText().isBlank())
			dipendenteModificato.setCellulare(cellulareTextField.getText());
		dipendenteModificato.setIndirizzo(indirizzoTextField.getText());
		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills.addAll(skillsList.getSelectedValuesList());
		dipendenteModificato.setSkills(skills);
		dipendenteModificato.setSalario(parseFloat(salarioTextField.getText(), 0f));
		
		try {
			controller.aggiornaDipendente(dipendenteModificato);
			
			pulisciCampi();
			
			aggiornaTabella(controller);
		}
		catch(SQLException e1) {
			if (e1.getSQLState().equals(VIOLAZIONE_NOT_NULL)) {
				JOptionPane.showMessageDialog(null,
						"Alcuni campi obbligatori per l'aggiornamento sono vuoti.",
						"Errore Campi Obbligatori Vuoti",
						JOptionPane.ERROR_MESSAGE);
				campiObbligatoriRossi();
				}
			else if (e1.getSQLState().equals(VIOLAZIONE_PKEY_UNIQUE)) {
				JOptionPane.showMessageDialog(null,
						"Il dipendente che intendi aggiornare è uguale a uno già esistente.\nControllare email o codice fiscale.",
						"Errore Dipendente Esistente",
						JOptionPane.ERROR_MESSAGE);
			}
			else if(e1.getSQLState().equals(VIOLAZIONE_VINCOLI_TABELLA)) {
				JOptionPane.showMessageDialog(null,
						"I valori inseriti sono errati.\n"
						+ "Controlla che:\n"
						+ "1)Il formato dell'email sia corretto\n"
						+ "2)Il dipendente sia maggiorenne e la sua data di nascita sia corretta\n"
						+ "3)Il salario previsto per lui sia positivo\n"
						+ "4)Nome e Cognome non siano del formato corretto o vuoti\n"
						+ "Contattare gli sviluppatori se non è nessuno dei seguenti casi.",
						"Errore Vincoli",
						JOptionPane.ERROR_MESSAGE);	
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailTextField.getText());
				if (!matcher.find())
					emailLabel.setForeground(Color.RED);
				LocalDate dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() +1, giornoComboBox.getSelectedIndex() + 1);
				Period period = new Period(dataNascita, LocalDate.now(), PeriodType.yearMonthDay());
				int età = period.getYears();
				if (età < 18)
					dataNascitaLabel.setForeground(Color.RED);
				if (Float.parseFloat(salarioTextField.getText()) < 0)
					salarioLabel.setForeground(Color.RED);
			}
			else if(e1.getSQLState().equals(VIOLAZIONE_LUNGHEZZA_STRINGA)) {
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
				if (nomeTextField.getText().length() > 30)
					nomeLabel.setForeground(Color.RED);
				if (cognomeTextField.getText().length() > 30)
					cognomeLabel.setForeground(Color.RED);
				if (emailTextField.getText().length() > 100)
					emailLabel.setForeground(Color.RED);
				if (indirizzoTextField.getText().length() > 100)
					indirizzoLabel.setForeground(Color.RED);
				if (telefonoFissoTextField.getText().length() != 10)
					telefonoFissoLabel.setForeground(Color.RED);
				if (cellulareTextField.getText().length() != 10)
					cellulareLabel.setForeground(Color.RED);
				if (passwordField.getText().length() > 50 || confermaPasswordField.getText().length() > 50 ||passwordField.getText().isBlank() || confermaPasswordField.getText().isBlank()) {
					passwordLabel.setForeground(Color.RED);
					confermaPasswordLabel.setForeground(Color.RED);
				}
			}
			else if (e1.getSQLState().equals(VIOLAZIONE_DATA_INESISTENTE)) {
				JOptionPane.showMessageDialog(null,
						"Accertarsi che la data di nascita esista davvero.",
						"Data Inesistente",
						JOptionPane.ERROR_MESSAGE);
				dataNascitaLabel.setForeground(Color.RED);
			}
			else {
				JOptionPane.showMessageDialog(null,
						e1.getMessage() + "\nContattare uno sviluppatore.",
						"Errore #" + e1.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean checkCampiObbligatoriVuoti() {
		if (nomeTextField.getText().isBlank())
			return true;
		else if (cognomeTextField.getText().isBlank())
			return true;
		else if (!uomoRadioButton.isSelected() && !donnaRadioButton.isSelected())
			return true;
		else if (giornoComboBox.getSelectedItem() == null || meseComboBox.getSelectedItem() == null || annoComboBox.getSelectedItem() == null)
			return true;
		else if (provinciaComboBox.getSelectedItem() == null || cittaComboBox.getSelectedItem() == null)
			return true;
		else if (indirizzoTextField.getText().isBlank())
			return true;
		else if (emailTextField.getText().isBlank())
			return true;
		else if (passwordField.getText().isBlank())
			return true;
		else if (confermaPasswordField.getText().isBlank())
			return true;
		else
			return false;
	}
	
	private void pulisciCampi() {
		nomeTextField.setText("");
		cognomeTextField.setText("");
		emailTextField.setText("");
		uomoRadioButton.setSelected(false);;
		donnaRadioButton.setSelected(false);
		giornoComboBox.setSelectedIndex(0);
		meseComboBox.setSelectedIndex(0);
		annoComboBox.setSelectedIndex((int) annoComboBox.getItemCount()/2);;
		provinciaComboBox.setSelectedIndex(0);
		indirizzoTextField.setText("");
		cellulareTextField.setText("");
		telefonoFissoTextField.setText("");
		passwordField.setText("");
		confermaPasswordField.setText("");
		salarioTextField.setText("");
		skillsList.clearSelection();
	}
}
