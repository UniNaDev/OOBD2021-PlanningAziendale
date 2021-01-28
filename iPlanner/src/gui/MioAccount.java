package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerGestioneProfilo;
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
import java.util.ArrayList;
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

import org.joda.time.LocalDate;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MioAccount extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTextField nomeTextField;
	private JTextField cfTextField;
	private JTextField cognomeTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField cellulareTextField;
	private JTextField telefonoFissoTextField;
	private JRadioButton uomoRadioButton;
	private JRadioButton donnaRadioButton;
	private JComboBox giornoComboBox;
	private JComboBox meseComboBox;
	private JComboBox annoComboBox;
	private JComboBox<LuogoNascita> comuneComboBox;
	private JComboBox provinciaComboBox;
	private JTextField indirizzoTextField;
	private JPasswordField passwordField;
	private JTextField emailTextField;
	
	//Altri attributi
	private String nome;	//nome nuovo dipendente
	private String cognome;	//cognome nuovo dipendente
	private char sesso = 'M';	//sesso del nuovo dipendente (default = maschio)
	private LocalDate dataNascita = new LocalDate(1900,1,1);	//data di nascita del nuovo dipendente
	private LuogoNascita luogoNascita;	//luogo di nascita del nuovo dipendente
	private String email;	//email del dipendente
	private String password;	//password del dipendente
	private String telefono;	//numero di telefono di casa
	private String cellulare;	//cellulare
	private String indirizzo;	//indirizzo


	//Creazione frame
	//-----------------------------------------------------------------
	
	public MioAccount(ControllerGestioneProfilo controller, Dipendente dipendente) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MioAccount.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Il mio Account");
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Button "Conferma"
		JButton confermaButton = new JButton("Conferma");
		confermaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				confermaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				confermaButton.setBackground(Color.WHITE);
			}
		});
		confermaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confermaButton.setBackground(Color.WHITE);
		confermaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confermaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		confermaButton.setBounds(1136, 628, 118, 42);
		//Click sul pulsante
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateAccount(controller); //Aggiorna info account
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		
		//Label "Informazioni Personali"
		JLabel informazioniPersonaliLabel = new JLabel("Informazioni Personali");
		informazioniPersonaliLabel.setBounds(125, 11, 386, 37);
		informazioniPersonaliLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//Label "Nome"
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(167, 123, 62, 19);
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//TextField del nome
		nomeTextField = new JTextField();
		nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomeTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		nomeTextField.setText(dipendente.getNome());
		nomeTextField.setEditable(false);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setBounds(239, 119, 187, 24);
		nomeTextField.setColumns(10);
		
		//Label "Codice Fiscale"
		JLabel cfLabel = new JLabel("Codice Fiscale");
		cfLabel.setBounds(85, 87, 144, 19);
		cfLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		cfLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//TextField codice fiscale
		cfTextField = new JTextField();
		cfTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cfTextField.setText(dipendente.getCf());
		cfTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		cfTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cfTextField.setEditable(false);
		cfTextField.setBounds(239, 83, 187, 24);
		cfTextField.setColumns(10);
		
		//Label "Cognome"
		JLabel cognomeLabel = new JLabel("Cognome");
		cognomeLabel.setBounds(167, 154, 62, 19);
		cognomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//TextField cognome
		cognomeTextField = new JTextField();
		cognomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cognomeTextField.setText(dipendente.getCognome());
		cognomeTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		cognomeTextField.setEditable(false);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cognomeTextField.setBounds(239, 150, 187, 24);
		cognomeTextField.setColumns(10);
		
		//Label "Sesso"
		JLabel sessoLabel = new JLabel("Sesso");
		sessoLabel.setBounds(167, 198, 62, 19);
		sessoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//RadioButton Uomo/Donna
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
		
		if (dipendente.getSesso() == 'M') {
			uomoRadioButton.setSelected(true);
			donnaRadioButton.setSelected(false);
		}
		else {
			uomoRadioButton.setSelected(false);
			donnaRadioButton.setSelected(true);
		}
		
		//Label "Data di nascita"
		JLabel dataNascitaLabel = new JLabel("Data di nascita");
		dataNascitaLabel.setBounds(103, 236, 126, 21);
		dataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//ComboBox giorno di nascita
		giornoComboBox = new JComboBox();
		giornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));	
		giornoComboBox.setUI(new BasicComboBoxUI());
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoComboBox.setEnabled(false);
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setSelectedIndex(dipendente.getDataNascita().getDayOfMonth() - 1);
		giornoComboBox.setBounds(239, 236, 47, 22);
		contentPane.add(giornoComboBox);
		
		//ComboBox mese di nascita
		meseComboBox = new JComboBox();
		meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meseComboBox.setUI(new BasicComboBoxUI());
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setEnabled(false);
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setSelectedIndex(dipendente.getDataNascita().getMonthOfYear() - 1);
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseComboBox.setBounds(296, 236, 47, 22);
		contentPane.add(meseComboBox);
		
		//ComboBox anno di nascita
		annoComboBox = new JComboBox();
		annoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoComboBox.setUI(new BasicComboBoxUI());
		annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoComboBox.setEnabled(false);
		annoComboBox.setBackground(Color.WHITE);
		annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoComboBox.setBounds(353, 236, 62, 22);
		DefaultComboBoxModel myModel = new DefaultComboBoxModel();
		annoComboBox.setModel(myModel);
		//Genera e aggiunge alla combobox gli anni dal 1900 a oggi
		for(int i = 1900;i <= LocalDate.now().getYear(); i++)
			myModel.addElement(i);
		annoComboBox.setSelectedIndex(dipendente.getDataNascita().getYear() - 1900); //mette di default il 100esimo indice cioè l'anno 2000	
		contentPane.add(annoComboBox);
		
		//Button "Modifica"
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				modificaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				modificaButton.setBackground(Color.WHITE);
			}
		});
		modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		modificaButton.setBounds(275, 541, 126, 42);
		modificaButton.addActionListener(new ActionListener() {
			//Click del pulsante
			public void actionPerformed(ActionEvent e) {
				//Setta tutti i campi modificabili come editabili
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
			}
		});
		
		
		addWindowListener(new WindowAdapter() {
			//Quando si vuole uscire chiede all'utente quale scelta vuole effettuare
			public void windowClosing(WindowEvent evt) {
                int res=JOptionPane.showConfirmDialog(null,
                        "Sei sicuro di uscire? Le modifiche non verranno salvate");
                if(res==JOptionPane.YES_OPTION){
                      controller.chiudiMioAccount();	//chiude la finestra
                }
                if(res==JOptionPane.NO_OPTION) {
                	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	//non la chiude
                	
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
		
		//Separatore verticale
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBackground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(631, 97, 2, 510);
		contentPane.add(separator);
		
		//Label "Prov. di nascita"
		JLabel provinciaNascitaLabel = new JLabel("Prov. di nascita");
		provinciaNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		provinciaNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		provinciaNascitaLabel.setBounds(85, 269, 144, 21);
		contentPane.add(provinciaNascitaLabel);
		
		//Label "Comune di nascita"
		JLabel comuneNascitaLabel = new JLabel("Comune di nascita");
		comuneNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		comuneNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		comuneNascitaLabel.setBounds(85, 299, 144, 24);
		contentPane.add(comuneNascitaLabel);
		
		//ComboBox comune di nascita
		comuneComboBox = new JComboBox();
		comuneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comuneComboBox.setUI(new BasicComboBoxUI());
		comuneComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comuneComboBox.setEnabled(false);
		comuneComboBox.setEditable(false);
		comuneComboBox.setBackground(Color.WHITE);
		comuneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
	
		comuneComboBox.setBounds(239, 301, 201, 22);
		contentPane.add(comuneComboBox);
		try {
				//ComboBox province di nascita
				provinciaComboBox = new JComboBox(controller.ottieniProvince().toArray());
				provinciaComboBox.setEnabled(false);
				provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
				provinciaComboBox.addActionListener(new ActionListener() {
					//Action performed selezione
					public void actionPerformed(ActionEvent e) {
						if(provinciaComboBox.isEnabled())
							comuneComboBox.setEnabled(true); //attiva il menù dei comuni
						comuneComboBox.removeAllItems();	//pulisce la lista del menù
						try {
							//prova a ottenere i comune dal DB e inserirli nella corrispondente combo box
							comuneComboBox.setModel(new DefaultComboBoxModel(controller.ottieniComuni(provinciaComboBox.getSelectedItem().toString()).toArray()));
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
		
		provinciaComboBox.setUI(new BasicComboBoxUI());
		provinciaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		provinciaComboBox.setEnabled(false);
		provinciaComboBox.setBackground(Color.WHITE);
		provinciaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		provinciaComboBox.setBounds(239, 268, 144, 22);
		provinciaComboBox.setSelectedItem(dipendente.getLuogoNascita().getNomeProvincia());
		comuneComboBox.setSelectedItem(dipendente.getLuogoNascita());
		contentPane.add(provinciaComboBox);
		
		//Label "Indirizzo"
		JLabel indirizzoLabel = new JLabel("Indirizzo");
		indirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		indirizzoLabel.setBounds(85, 337, 144, 20);
		contentPane.add(indirizzoLabel);
		
		//TextField indirizzo
		indirizzoTextField = new JTextField(dipendente.getIndirizzo());
		indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		indirizzoTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		indirizzoTextField.setEditable(false);
		indirizzoTextField.setBounds(239, 334, 201, 24);
		contentPane.add(indirizzoTextField);
		indirizzoTextField.setColumns(10);
		
		//Label "Cellulare"
		JLabel cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		cellulareLabel.setBounds(85, 447, 144, 19);
		contentPane.add(cellulareLabel);
		
		//TextField cellulare dipendente
		cellulareTextField = new JTextField();
		cellulareTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cellulareTextField.setText(dipendente.getCellulare());
		cellulareTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		cellulareTextField.setEditable(false);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cellulareTextField.setColumns(10);
		cellulareTextField.setBounds(239, 444, 187, 24);
		contentPane.add(cellulareTextField);
		
		//Label "Telefono fisso"
		JLabel telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		telefonoFissoLabel.setBounds(85, 484, 144, 19);
		contentPane.add(telefonoFissoLabel);
		
		//TextField telefono fisso
		telefonoFissoTextField = new JTextField();
		telefonoFissoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		telefonoFissoTextField.setText(dipendente.getTelefonoCasa());
		telefonoFissoTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		telefonoFissoTextField.setEditable(false);
		telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		telefonoFissoTextField.setColumns(10);
		telefonoFissoTextField.setBounds(239, 479, 187, 24);
		contentPane.add(telefonoFissoTextField);
		
		//Label "Informazioni Aziendali"
		JLabel informazioniAziendaliLabel = new JLabel("Informazioni Aziendali");
		informazioniAziendaliLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		informazioniAziendaliLabel.setBounds(772, 11, 386, 37);
		contentPane.add(informazioniAziendaliLabel);
		
		//Label "La mia Valutazione:"
		JLabel laMiaValutazioneLabel = new JLabel("La mia Valutazione:");
		laMiaValutazioneLabel.setForeground(Color.DARK_GRAY);
		laMiaValutazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		laMiaValutazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		laMiaValutazioneLabel.setBounds(772, 136, 386, 37);
		contentPane.add(laMiaValutazioneLabel);
		
		//Label valutazione del dipendente
		JLabel valoreValutazioneLabel = new JLabel(String.format("%.2f", dipendente.getValutazione()));
		valoreValutazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreValutazioneLabel.setForeground(Color.BLACK);
		valoreValutazioneLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
		valoreValutazioneLabel.setBounds(772, 184, 386, 37);
		contentPane.add(valoreValutazioneLabel);
		
		//Label "Il mio Salario:"
		JLabel IlMioSalarioLabel = new JLabel("Il mio Salario:");
		IlMioSalarioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IlMioSalarioLabel.setForeground(Color.DARK_GRAY);
		IlMioSalarioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		IlMioSalarioLabel.setBounds(772, 293, 386, 37);
		contentPane.add(IlMioSalarioLabel);
		
		//Label salario del dipendente
		JLabel valoreSalarioLabel = new JLabel(String.format("%.2f", dipendente.getSalario()) + "€");
		valoreSalarioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreSalarioLabel.setForeground(Color.BLACK);
		valoreSalarioLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
		valoreSalarioLabel.setBounds(772, 336, 386, 37);
		contentPane.add(valoreSalarioLabel);
		
		//Label "Le mie Skill:"
		JLabel leMieSkillLabel = new JLabel("Le mie Skill:");
		leMieSkillLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leMieSkillLabel.setForeground(Color.DARK_GRAY);
		leMieSkillLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		leMieSkillLabel.setBounds(772, 462, 386, 37);
		contentPane.add(leMieSkillLabel);
		
		//Icona valutazione
		JLabel iconaValutazioneLabel = new JLabel("");
		iconaValutazioneLabel.setForeground(Color.DARK_GRAY);
		iconaValutazioneLabel.setIcon(new ImageIcon(MioAccount.class.getResource("/Icone/valutazione_32.png")));
		iconaValutazioneLabel.setBounds(743, 123, 32, 51);
		contentPane.add(iconaValutazioneLabel);
		
		//Icona salario
		JLabel iconaSalarioLabel = new JLabel("");
		iconaSalarioLabel.setIcon(new ImageIcon(MioAccount.class.getResource("/Icone/salary_32.png")));
		iconaSalarioLabel.setForeground(Color.DARK_GRAY);
		iconaSalarioLabel.setBounds(743, 274, 32, 51);
		contentPane.add(iconaSalarioLabel);
		
		//Icona skill
		JLabel iconaSkillsLabel = new JLabel("");
		iconaSkillsLabel.setIcon(new ImageIcon(MioAccount.class.getResource("/Icone/skills_32.png")));
		iconaSkillsLabel.setForeground(Color.DARK_GRAY);
		iconaSkillsLabel.setBounds(743, 447, 32, 51);
		contentPane.add(iconaSkillsLabel);
		
		//Label "Email"
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
		emailLabel.setBounds(85, 376, 144, 19);
		contentPane.add(emailLabel);
		
		//TextField email
		emailTextField = new JTextField(dipendente.getEmail());
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		emailTextField.setEditable(false);
		emailTextField.setColumns(10);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setBounds(239, 374, 187, 24);
		contentPane.add(emailTextField);
		
		//Label "Password"
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		passwordLabel.setBounds(85, 414, 144, 19);
		contentPane.add(passwordLabel);
		
		//PasswordField della password
		passwordField = new JPasswordField(dipendente.getPassword());
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(239, 409, 187, 24);
		passwordField.setEditable(false);
		contentPane.add(passwordField);
		
		//ScrollPane per le skill
		JScrollPane skillsScrollPane = new JScrollPane();
		skillsScrollPane.setOpaque(false);
		skillsScrollPane.setBorder(null);
		skillsScrollPane.setBounds(753, 510, 427, 107);
		contentPane.add(skillsScrollPane);
		
		//TextArea per le skill
		JTextArea valoreSkillTextArea = new JTextArea();
		valoreSkillTextArea.setOpaque(false);
		valoreSkillTextArea.setBorder(null);
		valoreSkillTextArea.setEditable(false);
		valoreSkillTextArea.setBackground(new Color(240,240,240));
		valoreSkillTextArea.setWrapStyleWord(true);
		valoreSkillTextArea.setLineWrap(true);
		valoreSkillTextArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		String skillDipendente = "";
		for(Skill skill: dipendente.getSkills())
			skillDipendente += skill.toString() + "\n";
		valoreSkillTextArea.setText(skillDipendente);
		skillsScrollPane.setViewportView(valoreSkillTextArea);
	}

	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che salva i dati del nuovo account e li manda al controller per creare il nuovo account nel DB
	private void updateAccount(ControllerGestioneProfilo controller) throws SQLException {
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
		luogoNascita = controller.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(comuneComboBox.getSelectedIndex());
		email = emailTextField.getText(); //email
		password = passwordField.getText();	//password
		telefono = telefonoFissoTextField.getText();	//telefono
		if (!cellulareTextField.getText().isBlank())
			cellulare = cellulareTextField.getText();    //cellulare
		else cellulare=null;
		indirizzo = indirizzoTextField.getText();	//indirizzo
	
		controller.aggiornaInfoDipendente(nome, cognome, sesso, dataNascita, luogoNascita, email, password, telefono, cellulare, indirizzo);	//mandali al controller che prova a creare il nuovo dipendente con il dao
	}
}
