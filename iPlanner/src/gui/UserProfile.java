package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerGestioneProfilo;
import entita.LuogoNascita;


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
import javax.swing.JTextPane;

public class UserProfile extends JFrame {

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
	private JComboBox <LuogoNascita> comuneComboBox;
	private JComboBox provinciaComboBox;
	private JTextField indirizzoTextField;
	private JPasswordField passwordField;
	
	
	
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

	
	private ControllerGestioneProfilo theController;
	private JTextField emailTextField;

	/**
	 * Create the frame.
	 */
	public UserProfile(ControllerGestioneProfilo theController) {
		
		this.theController=theController;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserProfile.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Il mio Account");
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);
	
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		

		
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
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO  UPDATE dei dati se si clicca su conferma

				
				try {
					updateAccount();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				theController.closeWindow();
				theController.reLinkToUserFrame();
			}

		});


		
		
		nomeTextField = new JTextField();
		nomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomeTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		nomeTextField.setText(theController.getLoggedUser().getNome());
		nomeTextField.setEditable(false);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setBounds(239, 119, 187, 24);
		nomeTextField.setColumns(10);
		
		JLabel informazioniPersonaliLabel = new JLabel("Informazioni Personali");
		informazioniPersonaliLabel.setBounds(125, 11, 386, 37);
		informazioniPersonaliLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		cfTextField = new JTextField();
		cfTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cfTextField.setText(theController.getLoggedUser().getCf());
		cfTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		cfTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cfTextField.setEditable(false);
		cfTextField.setBounds(239, 83, 187, 24);
		cfTextField.setColumns(10);
		
		cognomeTextField = new JTextField();
		cognomeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cognomeTextField.setText(theController.getLoggedUser().getCognome());
		cognomeTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		cognomeTextField.setEditable(false);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cognomeTextField.setBounds(239, 150, 187, 24);
		cognomeTextField.setColumns(10);
		
		JLabel cfLabel = new JLabel("Codice Fiscale");
		cfLabel.setBounds(85, 87, 144, 19);
		cfLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		cfLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(167, 123, 62, 19);
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JLabel cognomeLabel = new JLabel("Cognome");
		cognomeLabel.setBounds(167, 154, 62, 19);
		cognomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JLabel sessoLabel = new JLabel("Sesso");
		sessoLabel.setBounds(167, 198, 62, 19);
		sessoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JLabel dataNascitaLabel = new JLabel("Data di nascita");
		dataNascitaLabel.setBounds(103, 236, 126, 21);
		dataNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
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
			public void actionPerformed(ActionEvent e) {
				
				//QUANDO VIENE PREMUTO IL TASTO DI MODIFICA QUESTI CAMPI DIVENTANO EDITABILI							
				
				nomeTextField.setEditable(true);
				cognomeTextField.setEditable(true);
				//radio buttons sesso
				uomoRadioButton.setEnabled(true);				
				donnaRadioButton.setEnabled(true);
				//combo box data e luogo di nascita
				giornoComboBox.setEnabled(true);				
				meseComboBox.setEnabled(true);
				annoComboBox.setEnabled(true);
				comuneComboBox.setEnabled(true);
				provinciaComboBox.setEnabled(true);
				//
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
                                  theController.closeWindow();
                            }
                            if(res==JOptionPane.NO_OPTION) {
                            	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                            	
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
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBackground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(631, 97, 2, 510);
		contentPane.add(separator);
		
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
		
		if (theController.getLoggedUser().getSesso() == 'M') {
			uomoRadioButton.setSelected(true);
			donnaRadioButton.setSelected(false);
		}
		else {
			uomoRadioButton.setSelected(false);
			donnaRadioButton.setSelected(true);
		}
		
		giornoComboBox = new JComboBox();
		giornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
				
		giornoComboBox.setUI(new BasicComboBoxUI());
		
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoComboBox.setEnabled(false);
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setSelectedIndex(theController.getLoggedUser().getDataNascita().getDayOfMonth() - 1);
		giornoComboBox.setBounds(239, 236, 47, 22);
		contentPane.add(giornoComboBox);
		
		annoComboBox = new JComboBox();
		annoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		annoComboBox.setUI(new BasicComboBoxUI());
		
		annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoComboBox.setEnabled(false);
		annoComboBox.setBackground(Color.WHITE);
		annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoComboBox.setBounds(353, 236, 62, 22);
		
		
		//CREO UN MODELLO PERSONALIZZATO PER L'ANNO DEL COMBO BOX //////////
				DefaultComboBoxModel myModel = new DefaultComboBoxModel();
				annoComboBox.setModel(myModel);
				
				for(int i=1900;i<= 2021;i++)
					myModel.addElement(i);
				
				annoComboBox.setSelectedIndex(theController.getLoggedUser().getDataNascita().getYear() - 1900); //mette di default il 100esimo indice cioè l'anno 2000
		//////////////////////////////////////////////////////////
				
		contentPane.add(annoComboBox);
		
		meseComboBox = new JComboBox();
		meseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		meseComboBox.setUI(new BasicComboBoxUI());
		
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setEnabled(false);
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setSelectedIndex(theController.getLoggedUser().getDataNascita().getMonthOfYear() - 1);
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseComboBox.setBounds(296, 236, 47, 22);
		contentPane.add(meseComboBox);
		
		JLabel provinciaNascitaLabel = new JLabel("Prov. di nascita");
		provinciaNascitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		provinciaNascitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		provinciaNascitaLabel.setBounds(85, 269, 144, 21);
		contentPane.add(provinciaNascitaLabel);
		
		
		
			comuneComboBox = new JComboBox();
			comuneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			
			comuneComboBox.setUI(new BasicComboBoxUI());
			
			comuneComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			comuneComboBox.setEnabled(false);
			comuneComboBox.setEditable(false);
			comuneComboBox.setBackground(Color.WHITE);
			comuneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
			comuneComboBox.setSelectedItem(theController.getLoggedUser().getLuogoNascita());
			comuneComboBox.setBounds(239, 301, 201, 22);
			contentPane.add(comuneComboBox);
		
			try {
				//Combo Box province
				provinciaComboBox = new JComboBox(theController.ottieniProvince().toArray());
				provinciaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
				provinciaComboBox.addActionListener(new ActionListener() {
					//Action performed selezione
					public void actionPerformed(ActionEvent e) {
						if(provinciaComboBox.isEnabled())
						comuneComboBox.setEnabled(true); //attiva il menù dei comuni
						comuneComboBox.removeAllItems();	//pulisce la lista del menù
						try {
							//prova a ottenere i comune dal DB e inserirli nella corrispondente combo box
							for(LuogoNascita comune: theController.ottieniComuni(provinciaComboBox.getSelectedItem().toString()))
									comuneComboBox.addItem(comune);
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
			provinciaComboBox.setSelectedItem(theController.getLoggedUser().getLuogoNascita().getNomeProvincia());


		contentPane.add(provinciaComboBox);
		

		
		JLabel indirizzoLabel = new JLabel("Indirizzo");
		indirizzoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		indirizzoLabel.setBounds(85, 337, 144, 20);
		contentPane.add(indirizzoLabel);
		
		JLabel cellulareLabel = new JLabel("Cellulare");
		cellulareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cellulareLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		cellulareLabel.setBounds(85, 447, 144, 19);
		contentPane.add(cellulareLabel);
		
		JLabel telefonoFissoLabel = new JLabel("Telefono Fisso");
		telefonoFissoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		telefonoFissoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		telefonoFissoLabel.setBounds(85, 484, 144, 19);
		contentPane.add(telefonoFissoLabel);
		
		cellulareTextField = new JTextField();
		cellulareTextField.setHorizontalAlignment(SwingConstants.LEFT);
		cellulareTextField.setText(theController.getLoggedUser().getCellulare());
		cellulareTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		cellulareTextField.setEditable(false);
		cellulareTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cellulareTextField.setColumns(10);
		cellulareTextField.setBounds(239, 444, 187, 24);
		contentPane.add(cellulareTextField);
		
		telefonoFissoTextField = new JTextField();
		telefonoFissoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		telefonoFissoTextField.setText(theController.getLoggedUser().getTelefonoCasa());
		telefonoFissoTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		telefonoFissoTextField.setEditable(false);
		telefonoFissoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		telefonoFissoTextField.setColumns(10);
		telefonoFissoTextField.setBounds(239, 479, 187, 24);
		contentPane.add(telefonoFissoTextField);
		
		JLabel informazioniAziendaliLabel = new JLabel("Informazioni Aziendali");
		informazioniAziendaliLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		informazioniAziendaliLabel.setBounds(772, 11, 386, 37);
		contentPane.add(informazioniAziendaliLabel);
		
		JLabel laMiaValutazioneLabel = new JLabel("La mia Valutazione:");
		laMiaValutazioneLabel.setForeground(Color.DARK_GRAY);
		laMiaValutazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		laMiaValutazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		laMiaValutazioneLabel.setBounds(772, 136, 386, 37);
		contentPane.add(laMiaValutazioneLabel);
		
		JLabel valoreValutazioneLabel = new JLabel(String.format("%.2f", theController.getLoggedUser().getValutazione()));
		valoreValutazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreValutazioneLabel.setForeground(Color.BLACK);
		valoreValutazioneLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
		valoreValutazioneLabel.setBounds(772, 184, 386, 37);
		contentPane.add(valoreValutazioneLabel);
		
		JLabel IlMioSalarioLabel = new JLabel("Il mio Salario:");
		IlMioSalarioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IlMioSalarioLabel.setForeground(Color.DARK_GRAY);
		IlMioSalarioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		IlMioSalarioLabel.setBounds(772, 293, 386, 37);
		contentPane.add(IlMioSalarioLabel);
		
		JLabel valoreSalarioLabel = new JLabel(String.format("%.2f", theController.getLoggedUser().getSalario()) + "€");
		valoreSalarioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreSalarioLabel.setForeground(Color.BLACK);
		valoreSalarioLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
		valoreSalarioLabel.setBounds(772, 336, 386, 37);
		contentPane.add(valoreSalarioLabel);
		
		JLabel leMieSkillsLabel = new JLabel("Le mie Skills:");
		leMieSkillsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leMieSkillsLabel.setForeground(Color.DARK_GRAY);
		leMieSkillsLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		leMieSkillsLabel.setBounds(772, 462, 386, 37);
		contentPane.add(leMieSkillsLabel);
		
		JLabel iconaValutazioneLabel = new JLabel("");
		iconaValutazioneLabel.setForeground(Color.DARK_GRAY);
		iconaValutazioneLabel.setIcon(new ImageIcon(UserProfile.class.getResource("/Icone/valutazione_32.png")));
		iconaValutazioneLabel.setBounds(743, 123, 32, 51);
		contentPane.add(iconaValutazioneLabel);
		
		JLabel iconaSalarioLabel = new JLabel("");
		iconaSalarioLabel.setIcon(new ImageIcon(UserProfile.class.getResource("/Icone/salary_32.png")));
		iconaSalarioLabel.setForeground(Color.DARK_GRAY);
		iconaSalarioLabel.setBounds(743, 274, 32, 51);
		contentPane.add(iconaSalarioLabel);
		
		JLabel iconaSkillsLabel = new JLabel("");
		iconaSkillsLabel.setIcon(new ImageIcon(UserProfile.class.getResource("/Icone/skills_32.png")));
		iconaSkillsLabel.setForeground(Color.DARK_GRAY);
		iconaSkillsLabel.setBounds(743, 447, 32, 51);
		contentPane.add(iconaSkillsLabel);
		
		indirizzoTextField = new JTextField(theController.getLoggedUser().getIndirizzo());
		indirizzoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		indirizzoTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		indirizzoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		indirizzoTextField.setEditable(false);
		indirizzoTextField.setBounds(239, 334, 201, 24);
		contentPane.add(indirizzoTextField);
		indirizzoTextField.setColumns(10);
		
		passwordField = new JPasswordField(theController.getLoggedUser().getPassword());
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(239, 409, 187, 24);
		passwordField.setEditable(false);
		contentPane.add(passwordField);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
		emailLabel.setBounds(85, 376, 144, 19);
		contentPane.add(emailLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		passwordLabel.setBounds(85, 414, 144, 19);
		contentPane.add(passwordLabel);
		
		emailTextField = new JTextField(theController.getLoggedUser().getEmail());
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		emailTextField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		emailTextField.setEditable(false);
		emailTextField.setColumns(10);
		emailTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTextField.setBounds(239, 374, 187, 24);
		contentPane.add(emailTextField);
		
		JLabel provinciaNascitaLabel_1 = new JLabel("Prov. di nascita");
		provinciaNascitaLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		provinciaNascitaLabel_1.setFont(new Font("Consolas", Font.PLAIN, 14));
		provinciaNascitaLabel_1.setBounds(85, 299, 144, 24);
		contentPane.add(provinciaNascitaLabel_1);
		
		JScrollPane skillsScrollPane = new JScrollPane();
		skillsScrollPane.setOpaque(false);
		skillsScrollPane.setBorder(null);
		skillsScrollPane.setBounds(753, 510, 427, 107);
		contentPane.add(skillsScrollPane);
		
		JTextArea valoreSkillsTextArea = new JTextArea();
		valoreSkillsTextArea.setOpaque(false);
		valoreSkillsTextArea.setBorder(null);
		valoreSkillsTextArea.setEditable(false);
		//stesso colore dello sfondo
		valoreSkillsTextArea.setBackground(new Color(240,240,240));
		//quando va a capo prende tutta la stringa e non la spezza
		valoreSkillsTextArea.setWrapStyleWord(true);
		valoreSkillsTextArea.setLineWrap(true);
		valoreSkillsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 28));
		
		//prende le skill dell utente loggato
		valoreSkillsTextArea.setText(theController.getLoggedUser().getSkills().toString());
		
		skillsScrollPane.setViewportView(valoreSkillsTextArea);
	}

	//Metodo che salva i dati del nuovo account e li manda al controller per creare il nuovo account nel DB
	private void updateAccount() throws SQLException {
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
		luogoNascita = theController.ottieniComuni((String) provinciaComboBox.getSelectedItem()).get(comuneComboBox.getSelectedIndex());
		email = emailTextField.getText(); //email
		password = passwordField.getText();	//password
		telefono = telefonoFissoTextField.getText();	//telefono
		cellulare = cellulareTextField.getText();	//cellulare
		indirizzo = indirizzoTextField.getText();	//indirizzo
	
		theController.update(nome, cognome, sesso, dataNascita, luogoNascita, email, password, telefono, cellulare, indirizzo);	//mandali al controller che prova a creare il nuovo dipendente con il dao
	}
}
