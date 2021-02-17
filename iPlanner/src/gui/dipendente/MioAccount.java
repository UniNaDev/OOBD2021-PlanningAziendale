/*Finestra Mio Account con tutti i dati personali del dipendente.
 *Qui può visualizzare le sue informazioni e modificare i suoi dati anagrafici.
 *****************************************************************************/

package gui.dipendente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.Dipendente;
import entita.LuogoNascita;
import entita.Skill;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import controller.dipendente.ControllerGestioneProfilo;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MioAccount extends JFrame {
    private JPanel contentPane;
    private JTextField nomeTextField, cfTextField, cognomeTextField, cellulareTextField, telefonoFissoTextField,
	    indirizzoTextField, emailTextField;
    private JButton confermaButton;
    private JButton modificaButton;
    private JRadioButton uomoRadioButton, donnaRadioButton;
    private JComboBox giornoComboBox;
    private JComboBox meseComboBox;
    private JComboBox annoComboBox;
    private JComboBox<LuogoNascita> comuneComboBox;
    private DefaultComboBoxModel modelloComboBoxComuni;
    private JComboBox provinciaComboBox;
    private DefaultComboBoxModel modelloComboBoxProvince;
    private JPasswordField passwordField;
    private JLabel nomeLabel, cognomeLabel, sessoLabel, dataNascitaLabel, provinciaNascitaLabel, comuneNascitaLabel,
	    indirizzoLabel, cellulareLabel, telefonoFissoLabel, emailLabel, passwordLabel;
    private JLabel informazioniPersonaliLabel;
    private JLabel cfLabel;
    private JLabel informazioniAziendaliLabel;
    private JLabel valutazioneDipendenteLabel;
    private JLabel valoreValutazioneDipendenteLabel;
    private JLabel salarioDipendenteLabel;
    private JLabel valoreSalarioDipendenteLabel;
    private JLabel iconaSalarioLabel;
    private JLabel iconaSkillsLabel;
    private JLabel iconaValutazioneLabel;
    private JLabel skillDipendenteLabel;
    private JTextArea valoreSkillTextArea;
    private JSeparator separator;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    private String nome;
    private String cognome;
    private char sesso = 'M';
    private LocalDate dataNascita = new LocalDate(1900, 1, 1);
    private LuogoNascita luogoNascita;
    private String email;
    private String password;
    private String telefono;
    private String cellulare;
    private String indirizzo;

    private boolean modificheEffettuate = false;

    private final String VIOLAZIONE_LUNGHEZZA_STRINGA = "22001";
    private final String VIOLAZIONE_VINCOLI_TABELLA = "23514";

    public MioAccount(ControllerGestioneProfilo controller, Dipendente dipendenteLogged) {

	setResizable(false);
	setIconImage(Toolkit.getDefaultToolkit().getImage(MioAccount.class.getResource("/Icone/WindowIcon_16.png")));
	setTitle("iPlanner - Il mio Account");
	setBounds(100, 100, 1280, 720);
	setLocationRelativeTo(null);
	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent evt) {
		if (modificheEffettuate) {
		    int risposta = JOptionPane.showConfirmDialog(null,
			    "Sei sicuro di uscire? Le modifiche non verranno salvate.");
		    if (risposta == JOptionPane.YES_OPTION) {
			controller.chiudiMioAccount();
		    } else if (risposta == JOptionPane.NO_OPTION || risposta == JOptionPane.CANCEL_OPTION) {
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		    }
		} else
		    controller.chiudiMioAccount();
	    }
	});
	
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);

	informazioniPersonaliLabel = new JLabel("Informazioni Personali");
	informazioniPersonaliLabel.setBounds(125, 11, 386, 37);
	informazioniPersonaliLabel.setFont(new Font("Consolas", Font.PLAIN, 30));

	nomeLabel = new JLabel("Nome");
	nomeLabel.setBounds(167, 123, 62, 19);
	nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

	nomeTextField = new JTextField();
	nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
	nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	nomeTextField.setText(dipendenteLogged.getNome());
	nomeTextField.setEditable(false);
	nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	nomeTextField.setBounds(239, 119, 187, 24);
	nomeTextField.setColumns(10);

	cfLabel = new JLabel("Codice Fiscale");
	cfLabel.setBounds(85, 87, 144, 19);
	cfLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	cfLabel.setHorizontalAlignment(SwingConstants.RIGHT);

	cfTextField = new JTextField();
	cfTextField.setHorizontalAlignment(SwingConstants.LEFT);
	cfTextField.setText(dipendenteLogged.getCf());
	cfTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	cfTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	cfTextField.setEditable(false);
	cfTextField.setBounds(239, 83, 187, 24);
	cfTextField.setColumns(10);

	cognomeLabel = new JLabel("Cognome");
	cognomeLabel.setBounds(167, 154, 62, 19);
	cognomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

	cognomeTextField = new JTextField();
	cognomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
	cognomeTextField.setText(dipendenteLogged.getCognome());
	cognomeTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	cognomeTextField.setEditable(false);
	cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	cognomeTextField.setBounds(239, 150, 187, 24);
	cognomeTextField.setColumns(10);

	sessoLabel = new JLabel("Sesso");
	sessoLabel.setBounds(167, 198, 62, 19);
	sessoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

	uomoRadioButton = new JRadioButton("Uomo");
	uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	uomoRadioButton.setEnabled(false);
	buttonGroup.add(uomoRadioButton);
	uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 12));
	uomoRadioButton.setBounds(248, 195, 62, 23);
	contentPane.add(uomoRadioButton);

	donnaRadioButton = new JRadioButton("Donna");
	donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	donnaRadioButton.setEnabled(false);
	buttonGroup.add(donnaRadioButton);
	donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 12));
	donnaRadioButton.setBounds(312, 195, 62, 23);
	contentPane.add(donnaRadioButton);

	if (dipendenteLogged.getSesso() == 'M') {
	    uomoRadioButton.setSelected(true);
	    donnaRadioButton.setSelected(false);
	} else {
	    uomoRadioButton.setSelected(false);
	    donnaRadioButton.setSelected(true);
	}

	dataNascitaLabel = new JLabel("Data di nascita");
	dataNascitaLabel.setBounds(103, 236, 126, 21);
	dataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	dataNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

	giornoComboBox = new JComboBox();
	giornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	giornoComboBox.setUI(new BasicComboBoxUI());
	giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	giornoComboBox.setEnabled(false);
	giornoComboBox.setBackground(Color.WHITE);
	giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
	giornoComboBox.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08",
		"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
		"26", "27", "28", "29", "30", "31" }));
	giornoComboBox.setSelectedIndex(dipendenteLogged.getDataNascita().getDayOfMonth() - 1);
	giornoComboBox.setBounds(239, 236, 47, 22);
	contentPane.add(giornoComboBox);

	meseComboBox = new JComboBox();
	meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	meseComboBox.setUI(new BasicComboBoxUI());
	meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	meseComboBox.setEnabled(false);
	meseComboBox.setBackground(Color.WHITE);
	meseComboBox.setModel(new DefaultComboBoxModel(
		new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
	meseComboBox.setSelectedIndex(dipendenteLogged.getDataNascita().getMonthOfYear() - 1);
	meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
	meseComboBox.setBounds(296, 236, 47, 22);
	contentPane.add(meseComboBox);

	annoComboBox = new JComboBox();
	annoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	annoComboBox.setUI(new BasicComboBoxUI());
	annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	annoComboBox.setEnabled(false);
	annoComboBox.setBackground(Color.WHITE);
	annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
	annoComboBox.setBounds(353, 236, 62, 22);
	DefaultComboBoxModel anniComboBoxModel = new DefaultComboBoxModel();
	annoComboBox.setModel(anniComboBoxModel);
	for (int i = 1900; i <= LocalDate.now().getYear(); i++)
	    anniComboBoxModel.addElement(i);
	annoComboBox.setSelectedIndex(dipendenteLogged.getDataNascita().getYear() - 1900);
	contentPane.add(annoComboBox);

	separator = new JSeparator();
	separator.setForeground(Color.LIGHT_GRAY);
	separator.setBackground(Color.WHITE);
	separator.setOrientation(SwingConstants.VERTICAL);
	separator.setBounds(631, 97, 2, 510);
	contentPane.add(separator);

	provinciaNascitaLabel = new JLabel("Prov. di nascita");
	provinciaNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	provinciaNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	provinciaNascitaLabel.setBounds(85, 269, 144, 21);
	contentPane.add(provinciaNascitaLabel);

	comuneNascitaLabel = new JLabel("Comune di nascita");
	comuneNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	comuneNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	comuneNascitaLabel.setBounds(85, 299, 144, 24);
	contentPane.add(comuneNascitaLabel);

	comuneComboBox = new JComboBox();
	modelloComboBoxComuni = new DefaultComboBoxModel();
	comuneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	comuneComboBox.setUI(new BasicComboBoxUI());
	comuneComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	comuneComboBox.setEnabled(false);
	comuneComboBox.setEditable(false);
	comuneComboBox.setBackground(Color.WHITE);
	comuneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));

	comuneComboBox.setBounds(239, 301, 201, 22);
	contentPane.add(comuneComboBox);

	provinciaComboBox = new JComboBox();
	provinciaComboBox.setEnabled(false);
	provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	provinciaComboBox.setUI(new BasicComboBoxUI());
	provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	provinciaComboBox.setEnabled(false);
	provinciaComboBox.setBackground(Color.WHITE);
	provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
	provinciaComboBox.setBounds(239, 268, 144, 22);
	inizializzaComboBoxProvince(controller);
	provinciaComboBox.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (provinciaComboBox.isEnabled())
		    comuneComboBox.setEnabled(true);
		aggiornaComboBoxComuni(controller);
	    }
	});
	provinciaComboBox.setSelectedItem(dipendenteLogged.getLuogoNascita().getNomeProvincia());
	comuneComboBox.setSelectedItem(dipendenteLogged.getLuogoNascita());
	contentPane.add(provinciaComboBox);

	indirizzoLabel = new JLabel("Indirizzo");
	indirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	indirizzoLabel.setBounds(85, 337, 144, 20);
	contentPane.add(indirizzoLabel);

	indirizzoTextField = new JTextField(dipendenteLogged.getIndirizzo());
	indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
	indirizzoTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	indirizzoTextField.setEditable(false);
	indirizzoTextField.setBounds(239, 334, 201, 24);
	contentPane.add(indirizzoTextField);
	indirizzoTextField.setColumns(10);

	cellulareLabel = new JLabel("Cellulare");
	cellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	cellulareLabel.setBounds(85, 447, 144, 19);
	contentPane.add(cellulareLabel);

	cellulareTextField = new JTextField();
	cellulareTextField.setHorizontalAlignment(SwingConstants.LEFT);
	cellulareTextField.setText(dipendenteLogged.getCellulare());
	cellulareTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	cellulareTextField.setEditable(false);
	cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	cellulareTextField.setColumns(10);
	cellulareTextField.setBounds(239, 444, 187, 24);
	contentPane.add(cellulareTextField);

	telefonoFissoLabel = new JLabel("Telefono Fisso");
	telefonoFissoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	telefonoFissoLabel.setBounds(85, 484, 144, 19);
	contentPane.add(telefonoFissoLabel);

	telefonoFissoTextField = new JTextField();
	telefonoFissoTextField.setHorizontalAlignment(SwingConstants.LEFT);
	telefonoFissoTextField.setText(dipendenteLogged.getTelefonoCasa());
	telefonoFissoTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	telefonoFissoTextField.setEditable(false);
	telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	telefonoFissoTextField.setColumns(10);
	telefonoFissoTextField.setBounds(239, 479, 187, 24);
	contentPane.add(telefonoFissoTextField);
	
	modificaButton = new JButton("Modifica");
	modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	modificaButton.setBackground(Color.WHITE);
	modificaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
	modificaButton.setBounds(275, 541, 126, 42);
	modificaButton.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseEntered(MouseEvent e) {
		modificaButton.setBackground(Color.LIGHT_GRAY);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		modificaButton.setBackground(Color.WHITE);
	    }
	});
	modificaButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		nomeTextField.setEditable(true);
		cognomeTextField.setEditable(true);
		uomoRadioButton.setEnabled(true);
		donnaRadioButton.setEnabled(true);
		giornoComboBox.setEnabled(true);
		meseComboBox.setEnabled(true);
		annoComboBox.setEnabled(true);
		provinciaComboBox.setEnabled(true);
		emailTextField.setEditable(true);
		passwordField.setEditable(true);
		indirizzoTextField.setEditable(true);
		cellulareTextField.setEditable(true);
		telefonoFissoTextField.setEditable(true);
		modificaButton.setEnabled(false);
		modificheEffettuate = true;
	    }
	});

	informazioniAziendaliLabel = new JLabel("Informazioni Aziendali");
	informazioniAziendaliLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
	informazioniAziendaliLabel.setBounds(772, 11, 386, 37);
	contentPane.add(informazioniAziendaliLabel);

	valutazioneDipendenteLabel = new JLabel("La mia Valutazione:");
	valutazioneDipendenteLabel.setForeground(Color.DARK_GRAY);
	valutazioneDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
	valutazioneDipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
	valutazioneDipendenteLabel.setBounds(772, 136, 386, 37);
	contentPane.add(valutazioneDipendenteLabel);

	valoreValutazioneDipendenteLabel = new JLabel(String.format("%.2f", dipendenteLogged.getValutazione()));
	valoreValutazioneDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
	valoreValutazioneDipendenteLabel.setForeground(Color.BLACK);
	valoreValutazioneDipendenteLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
	valoreValutazioneDipendenteLabel.setBounds(772, 184, 386, 37);
	contentPane.add(valoreValutazioneDipendenteLabel);

	salarioDipendenteLabel = new JLabel("Il mio Salario:");
	salarioDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
	salarioDipendenteLabel.setForeground(Color.DARK_GRAY);
	salarioDipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
	salarioDipendenteLabel.setBounds(772, 293, 386, 37);
	contentPane.add(salarioDipendenteLabel);

	valoreSalarioDipendenteLabel = new JLabel(String.format("%.2f", dipendenteLogged.getSalario()) + "€");
	valoreSalarioDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
	valoreSalarioDipendenteLabel.setForeground(Color.BLACK);
	valoreSalarioDipendenteLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
	valoreSalarioDipendenteLabel.setBounds(772, 336, 386, 37);
	contentPane.add(valoreSalarioDipendenteLabel);

	skillDipendenteLabel = new JLabel("Le mie Skill:");
	skillDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
	skillDipendenteLabel.setForeground(Color.DARK_GRAY);
	skillDipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
	skillDipendenteLabel.setBounds(772, 462, 386, 37);
	contentPane.add(skillDipendenteLabel);

	iconaValutazioneLabel = new JLabel("");
	iconaValutazioneLabel.setForeground(Color.DARK_GRAY);
	iconaValutazioneLabel.setIcon(new ImageIcon(MioAccount.class.getResource("/Icone/valutazione_32.png")));
	iconaValutazioneLabel.setBounds(743, 122, 32, 51);
	contentPane.add(iconaValutazioneLabel);

	iconaSalarioLabel = new JLabel("");
	iconaSalarioLabel.setIcon(new ImageIcon(MioAccount.class.getResource("/Icone/salary_32.png")));
	iconaSalarioLabel.setForeground(Color.DARK_GRAY);
	iconaSalarioLabel.setBounds(743, 279, 32, 51);
	contentPane.add(iconaSalarioLabel);

	iconaSkillsLabel = new JLabel("");
	iconaSkillsLabel.setIcon(new ImageIcon(MioAccount.class.getResource("/Icone/skills_32.png")));
	iconaSkillsLabel.setForeground(Color.DARK_GRAY);
	iconaSkillsLabel.setBounds(743, 448, 32, 51);
	contentPane.add(iconaSkillsLabel);

	emailLabel = new JLabel("Email");
	emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	emailLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
	emailLabel.setBounds(85, 376, 144, 19);
	contentPane.add(emailLabel);

	emailTextField = new JTextField(dipendenteLogged.getEmail());
	emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
	emailTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
	emailTextField.setEditable(false);
	emailTextField.setColumns(10);
	emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	emailTextField.setBounds(239, 374, 187, 24);
	contentPane.add(emailTextField);

	passwordLabel = new JLabel("Password");
	passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
	passwordLabel.setBounds(85, 414, 144, 19);
	contentPane.add(passwordLabel);

	passwordField = new JPasswordField(dipendenteLogged.getPassword());
	passwordField.setFont(new Font("Consolas", Font.PLAIN, 12));
	passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	passwordField.setHorizontalAlignment(SwingConstants.LEFT);
	passwordField.setBounds(239, 409, 187, 24);
	passwordField.setEditable(false);
	contentPane.add(passwordField);

	JScrollPane skillsScrollPane = new JScrollPane();
	skillsScrollPane.setOpaque(false);
	skillsScrollPane.setBorder(null);
	skillsScrollPane.setBounds(753, 510, 427, 107);
	contentPane.add(skillsScrollPane);

	valoreSkillTextArea = new JTextArea();
	valoreSkillTextArea.setOpaque(false);
	valoreSkillTextArea.setBorder(null);
	valoreSkillTextArea.setEditable(false);
	valoreSkillTextArea.setBackground(new Color(240, 240, 240));
	valoreSkillTextArea.setWrapStyleWord(true);
	valoreSkillTextArea.setLineWrap(true);
	valoreSkillTextArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
	String skillDipendente = "";
	for (Skill skill : dipendenteLogged.getSkills())
	    skillDipendente += skill.toString() + "\n";
	valoreSkillTextArea.setText(skillDipendente);
	skillsScrollPane.setViewportView(valoreSkillTextArea);
	
	confermaButton = new JButton("Conferma");
	confermaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	confermaButton.setBackground(Color.WHITE);
	confermaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
	confermaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
	confermaButton.setBounds(1136, 628, 118, 42);
	confermaButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseEntered(MouseEvent e) {
		confermaButton.setBackground(Color.LIGHT_GRAY);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		confermaButton.setBackground(Color.WHITE);
	    }
	});
	confermaButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		campiObbligatoriNeri();
		
		if (!checkCampiObbligatoriVuoti() && lunghezzaTelefonoValida(telefonoFissoTextField.getText())
			&& lunghezzaTelefonoValida(cellulareTextField.getText()))
		    aggiornaAccount(controller, dipendenteLogged);
		else if (!lunghezzaTelefonoValida(telefonoFissoTextField.getText())) {
		    JOptionPane.showMessageDialog(null,
			    "Numero di telefono non valido.\nVerificare che sia composto da 10 cifre\no che non contenga lettere.",
			    "Numero di Telefono Non Valido", JOptionPane.ERROR_MESSAGE);
		    telefonoFissoLabel.setForeground(Color.RED);
		} else if (!lunghezzaTelefonoValida(cellulareTextField.getText())) {
		    JOptionPane.showMessageDialog(null,
			    "Numero di telefono non valido.\nVerificare che sia composto da 10 cifre\no che non contenga lettere.",
			    "Numeto di Telefono Non Valido", JOptionPane.ERROR_MESSAGE);
		    cellulareLabel.setForeground(Color.RED);
		} else {
		    JOptionPane.showMessageDialog(null, "Alcuni campi obbligatori per l'aggiornamento sono vuoti.",
			    "Errore Campi Obbligatori Vuoti", JOptionPane.ERROR_MESSAGE);
		    campiObbligatoriRossi();
		}
	  }

	});
	
	contentPane.setLayout(null);
	contentPane.add(modificaButton);
	contentPane.add(confermaButton);
	contentPane.add(cfLabel);
	contentPane.add(nomeLabel);
	contentPane.add(cognomeLabel);
	contentPane.add(sessoLabel);
	contentPane.add(dataNascitaLabel);
	contentPane.add(nomeTextField);
	contentPane.add(cognomeTextField);
	contentPane.add(cfTextField);
	contentPane.add(informazioniPersonaliLabel);
    }
    

    // Altri metodi
    // ------------------------------------------------------------------------
    private void inizializzaComboBoxProvince(ControllerGestioneProfilo controller) {
		try {
		    modelloComboBoxProvince = new DefaultComboBoxModel();
		    modelloComboBoxProvince.addAll(controller.ottieniProvince());
		    provinciaComboBox.setModel(modelloComboBoxProvince);
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null,
			    e.getMessage()
				    + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
			    "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
    }

    private void aggiornaComboBoxComuni(ControllerGestioneProfilo controller) {
		comuneComboBox.removeAllItems();
		try {
		    modelloComboBoxComuni.addAll(controller.ottieniComuni(provinciaComboBox.getSelectedItem().toString()));
		    comuneComboBox.setModel(modelloComboBoxComuni);
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null,
			    e.getMessage()
				    + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
			    "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
    }

    private void aggiornaAccount(ControllerGestioneProfilo controller, Dipendente dipendente) {
		ricavaInfoDipendente(controller);
		dipendente.setNome(nome);
		dipendente.setCognome(cognome);
		dipendente.setSesso(sesso);
		dipendente.setDataNascita(dataNascita);
		dipendente.setLuogoNascita(luogoNascita);
		dipendente.setEmail(email);
		dipendente.setPassword(password);
		dipendente.setTelefonoCasa(telefono);
		dipendente.setCellulare(cellulare);
		dipendente.setIndirizzo(indirizzo);
		try {
		    controller.aggiornaInfoDipendente(dipendente);
		    if (modificheEffettuate)
			JOptionPane.showMessageDialog(null, "Modifica Effettuata con successo.");
		    controller.chiudiMioAccount();
		    controller.tornaAHome();
		} catch (SQLException e1) {
		    switch (e1.getSQLState()) {
		    case VIOLAZIONE_VINCOLI_TABELLA:
			JOptionPane.showMessageDialog(null, "Verificare che:\n" + "1) L'email sia del formato corretto.\n"
				+ "2) Nome e cognome non contengano numeri.\n"
				+ "3) Telefono e cellulare non contengano lettere.\n" + "4) Il dipendente sia maggiorenne.",
				"Errore Creazione Account", JOptionPane.ERROR_MESSAGE);
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailTextField.getText());
			if (!matcher.find())
			    emailLabel.setForeground(Color.RED);
	
			if (!nonHaCifre(nome))
			    nomeLabel.setForeground(Color.RED);
	
			if (!nonHaCifre(cognome))
			    cognomeLabel.setForeground(Color.RED);
	
			if (!isNumero(telefono))
			    telefonoFissoLabel.setForeground(Color.RED);
	
			if (!isNumero(cellulare))
			    cellulareLabel.setForeground(Color.RED);
	
			dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() + 1,
				giornoComboBox.getSelectedIndex() + 1);
			Period period = new Period(dataNascita, LocalDate.now(), PeriodType.yearMonthDay());
			int età = period.getYears();
			if (età < 18)
			    dataNascitaLabel.setForeground(Color.RED);
	
			break;
		    case VIOLAZIONE_LUNGHEZZA_STRINGA:
			JOptionPane.showMessageDialog(null,
				"Verificare che:\n" + "1) Nome e cognome non contengano più di 30 caratteri.\n"
					+ "2) Indirizzo e email non contengano più di 100 caratteri.\n"
					+ "3) La password non superi i 50 caratteri.",
				"Errore Creazione Account", JOptionPane.ERROR_MESSAGE);
			if (nomeTextField.getText().length() > 30)
			    nomeLabel.setForeground(Color.RED);
			if (cognomeTextField.getText().length() > 30)
			    cognomeLabel.setForeground(Color.RED);
			if (emailTextField.getText().length() > 100)
			    emailLabel.setForeground(Color.RED);
			if (indirizzoTextField.getText().length() > 100)
			    indirizzoLabel.setForeground(Color.RED);
			if (passwordField.getText().length() > 50 || passwordField.getText().isBlank()) {
			    passwordLabel.setForeground(Color.RED);
			    break;
			}
		    default:
			JOptionPane.showMessageDialog(null,
				e1.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
				"Errore #" + e1.getSQLState(), JOptionPane.ERROR_MESSAGE);
		    }
	}
    }

    private void ricavaInfoDipendente(ControllerGestioneProfilo controller) {
		nome = nomeTextField.getText();
		cognome = cognomeTextField.getText();
		if (uomoRadioButton.isSelected())
		    sesso = 'M';
		else
		    sesso = 'F';
		try {
		    dataNascita = new LocalDate(annoComboBox.getSelectedIndex() + 1900, meseComboBox.getSelectedIndex() + 1,
			    giornoComboBox.getSelectedIndex() + 1);
		} catch (IllegalFieldValueException ifve) {
		    JOptionPane.showMessageDialog(null, "La data inserita non esiste.", "Errore Data Inesistente",
			    JOptionPane.ERROR_MESSAGE);
		}
		try {
		    luogoNascita = controller.ottieniComuni((String) provinciaComboBox.getSelectedItem())
			    .get(comuneComboBox.getSelectedIndex());
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null,
			    e.getMessage()
				    + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
			    "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
		email = emailTextField.getText();
		password = passwordField.getText();
		if (!telefonoFissoTextField.getText().isBlank())
		    telefono = telefonoFissoTextField.getText();
		if (!cellulareTextField.getText().isBlank())
		    cellulare = cellulareTextField.getText();
		indirizzo = indirizzoTextField.getText();
	    }
	
	    private boolean nonHaCifre(String stringa) {
			for (char c : stringa.toCharArray())
			    if (Character.isDigit(c))
				return false;
			return true;
    }

    private boolean isNumero(String stringa) {
		if (stringa == null || stringa == "")
		    return true;
		for (char c : stringa.toCharArray())
		    if (!Character.isDigit(c))
			return false;
		return true;

    }

    private boolean lunghezzaTelefonoValida(String numero) {
		if (numero.length() == 10 || numero.isBlank())
		    return true;
		else
		    return false;
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
		if (giornoComboBox.getSelectedItem() == null || meseComboBox.getSelectedItem() == null
			|| annoComboBox.getSelectedItem() == null)
		    dataNascitaLabel.setForeground(Color.RED);
		if (indirizzoTextField.getText() == null)
		    indirizzoLabel.setForeground(Color.RED);
		if (provinciaComboBox.getSelectedItem() == null || comuneComboBox.getSelectedItem() == null) {
		    provinciaNascitaLabel.setForeground(Color.RED);
		    comuneNascitaLabel.setForeground(Color.RED);
		}
		if (passwordField.getText().isBlank()) {
		    passwordLabel.setForeground(Color.RED);
		}
    }

    private void campiObbligatoriNeri() {
		nomeLabel.setForeground(Color.BLACK);
		cognomeLabel.setForeground(Color.BLACK);
		emailLabel.setForeground(Color.BLACK);
		passwordLabel.setForeground(Color.BLACK);
		sessoLabel.setForeground(Color.BLACK);
		indirizzoLabel.setForeground(Color.BLACK);
		dataNascitaLabel.setForeground(Color.BLACK);
		comuneNascitaLabel.setForeground(Color.BLACK);
		provinciaNascitaLabel.setForeground(Color.BLACK);
    }

    private boolean checkCampiObbligatoriVuoti() {
		if (nomeTextField.getText().isBlank())
		    return true;
		else if (cognomeTextField.getText().isBlank())
		    return true;
		else if (!uomoRadioButton.isSelected() && !donnaRadioButton.isSelected())
		    return true;
		else if (giornoComboBox.getSelectedItem() == null || meseComboBox.getSelectedItem() == null
			|| annoComboBox.getSelectedItem() == null)
		    return true;
		else if (provinciaComboBox.getSelectedItem() == null || comuneComboBox.getSelectedItem() == null)
		    return true;
		else if (indirizzoTextField.getText().isBlank())
		    return true;
		else if (emailTextField.getText().isBlank())
		    return true;
		else if (passwordField.getText().isBlank())
		    return true;
		else
		    return false;
    }
}
