package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import controller.ControllerMeeting;
import controller.ControllerPartecipantiMeeting;
import controller.ControllerPartecipantiProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import entita.Skill;

import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;
import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;

import javax.swing.JSeparator;



public class InserisciPartecipantiProgetto extends JFrame {

	//ATTRIBUTI
	//---------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTable progettoTable;
	private JTextField cercaTextField;
	private JLabel valutazioneLabel;
	private JLabel salarioLabel;
	private final ButtonGroup modalitàButtonGroup = new ButtonGroup();
	private JRadioButton uomoRadioButton;
	private JRadioButton donnaRadioButton;
	private JTable dipendenteTable;
	private JButton eliminaPartecipanteButton;
	private PartecipantiTableModel dataModelDipendente;
	private JTextField nomeTextField;
	private JTextField cognomeTextField;
	private JTextField etàTextField;
	private JTextField valutazioneTextField;
	private JTextField salarioTextField;
	private JList partecipantiList;
	private JLabel partecipantiLabel;
	private JComboBox ruoloComboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	//Creazione frame
	//---------------------------------------------
	public InserisciPartecipantiProgetto(ControllerPartecipantiProgetto controller, Progetto progettoSelezionato, int codiceProgetto) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1150, 700));
		setLocationRelativeTo(null);
		
		setTitle("Inserisci Partecipanti Progetto");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();	//main panel
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();	//panel info
		
		JPanel comandiPanel = new JPanel();	//panel per i comandi
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane DipendenteScrollPane = new JScrollPane();	//scroll pane per la tabella meeting
		DipendenteScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		DipendenteScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DipendenteScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(infoPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(comandiPanel, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(DipendenteScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(DipendenteScrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addContainerGap())
		);
		comandiPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel comandiPanel2 = new JPanel();	//panel interno a quello dei comandi
		comandiPanel2.setBorder(new LineBorder(new Color(192, 192, 192)));
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		
		//Label "Cerca"
		JLabel cercaLabel = new JLabel("Cerca");
		cercaLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		comandiPanel2.add(cercaLabel);
		
		//TextField per cercare un meeting
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel2.add(cercaTextField);
		cercaTextField.setColumns(10);
		comandiPanel.add(comandiPanel2);
		
		//panel interno a quello delle info
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		//Label "Data inizio"
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
	
		
		//Label "Data fine"
		JLabel cognomeLabel = new JLabel("Cognome");
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		cognomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		DefaultComboBoxModel myModel2 = new DefaultComboBoxModel();
		for(int i=1900;i<= 2021;i++)
			myModel2.addElement(i);
		
		//Label "Ora inizio"
		JLabel sessoLabel = new JLabel("Sesso");
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		sessoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		//Label età
		JLabel etàLabel = new JLabel("Età");
		etàLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		etàLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		//Label valutazione
		valutazioneLabel = new JLabel("Valutazione");
		valutazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		valutazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Label Salario
		salarioLabel = new JLabel("Salario");
		salarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//RadioButton uomo
		uomoRadioButton = new JRadioButton("M");
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modalitàButtonGroup.add(uomoRadioButton);
		
		//RadioButton donna
		donnaRadioButton = new JRadioButton("F");
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modalitàButtonGroup.add(donnaRadioButton);
		
		nomeTextField = new JTextField();
		nomeTextField.setEditable(false);
		nomeTextField.setColumns(10);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		cognomeTextField = new JTextField();
		cognomeTextField.setEditable(false);
		cognomeTextField.setColumns(10);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		etàTextField = new JTextField();
		etàTextField.setEditable(false);
		etàTextField.setColumns(10);
		etàTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		valutazioneTextField = new JTextField();
		valutazioneTextField.setEditable(false);
		valutazioneTextField.setColumns(10);
		valutazioneTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		salarioTextField = new JTextField();
		salarioTextField.setEditable(false);
		salarioTextField.setColumns(10);
		salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JScrollPane skillScrollPane_1 = new JScrollPane();
		skillScrollPane_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JLabel lblNewLabel = new JLabel("Info progetto");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel lblInfoDipendente = new JLabel("Info Dipendente");
		lblInfoDipendente.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoDipendente.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JScrollPane skillScrollPane = new JScrollPane();
		skillScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		textField = new JTextField(progettoSelezionato.getNomeProgetto());
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JLabel nomeLabel_1 = new JLabel("Nome");
		nomeLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel_1.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		textField_1 = new JTextField(progettoSelezionato.getTipoProgetto());
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JLabel nomeLabel_1_1 = new JLabel("Tipologia");
		nomeLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel_1_1.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		textField_2 = new JTextField(progettoSelezionato.getAmbiti().toString());
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JLabel nomeLabel_1_1_1 = new JLabel("Ambito/i");
		nomeLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel_1_1_1.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		try {
			ruoloComboBox = new JComboBox(controller.ottieniRuoli());
			ruoloComboBox.setSelectedItem(null);
		} catch (SQLException e4) {
			ruoloComboBox.setSelectedItem(null);
			e4.printStackTrace();
		}
		
		JLabel lblRuolo = new JLabel("Ruolo");
		lblRuolo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuolo.setFont(new Font("Consolas", Font.PLAIN, 14));

		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(184)
							.addComponent(lblInfoDipendente, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
							.addGap(136))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(39)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(etàLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addComponent(valutazioneLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
									.addComponent(sessoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(nomeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cognomeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(salarioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblRuolo, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(ruoloComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(uomoRadioButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(donnaRadioButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
								.addComponent(etàTextField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addComponent(valutazioneTextField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomeTextField)
								.addComponent(salarioTextField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addComponent(cognomeTextField, 102, 102, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
							.addComponent(skillScrollPane, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(73)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(53)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(nomeLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(nomeLabel_1_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(14))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(nomeLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
									.addGap(18)))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
							.addGap(61)
							.addComponent(skillScrollPane_1, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
							.addGap(30))
						.addGroup(Alignment.TRAILING, gl_infoPanel2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addGap(235))))
		);
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblInfoDipendente, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(29)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(nomeLabel_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addComponent(nomeLabel_1_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
									.addGap(9)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addComponent(nomeLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
												.addComponent(nomeLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
												.addComponent(nomeTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
												.addComponent(cognomeLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
												.addComponent(cognomeTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_infoPanel2.createSequentialGroup()
													.addComponent(sessoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
														.addComponent(etàLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
														.addComponent(etàTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
														.addComponent(valutazioneLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
														.addComponent(valutazioneTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
												.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
													.addComponent(uomoRadioButton)
													.addComponent(donnaRadioButton)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
												.addComponent(salarioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
												.addComponent(salarioTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
												.addComponent(ruoloComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblRuolo, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
										.addComponent(skillScrollPane_1, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
										.addComponent(skillScrollPane, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)))))
						.addComponent(separator, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
					.addGap(67))
		);
		
		JList skillList = new JList();
		skillScrollPane.setViewportView(skillList);
		
		JLabel Skill = new JLabel("Skill");
		Skill.setFont(new Font("Consolas", Font.PLAIN, 15));
		skillScrollPane.setColumnHeaderView(Skill);
		
		partecipantiList = new JList();
		DefaultListModel listmodel=new DefaultListModel();
		partecipantiList.setModel(listmodel);
		listmodel.addAll(progettoSelezionato.getCollaborazioni());
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		skillScrollPane_1.setViewportView(partecipantiList);
		
		eliminaPartecipanteButton = new JButton("Elimina partecipante");
		eliminaPartecipanteButton.setPreferredSize(new Dimension(190, 30));
		eliminaPartecipanteButton.setMaximumSize(new Dimension(150, 150));
		eliminaPartecipanteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaPartecipanteButton.setBackground(Color.WHITE);
		eliminaPartecipanteButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaPartecipanteButton.setMargin(new Insets(2, 20, 2, 20));
		eliminaPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		eliminaPartecipanteButton.setAlignmentX(0.5f);
		eliminaPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(partecipantiList.isSelectionEmpty()) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante da eliminare");
					partecipantiLabel.setForeground(Color.RED);
					
				}
				else {
					
					try {
						controller.eliminaPartecipante((Dipendente)partecipantiList.getSelectedValue(),codiceProgetto);
						JOptionPane.showMessageDialog(null, "Partecipante eliminato");
				
						listmodel.removeAllElements();
						listmodel.addAll(progettoSelezionato.getCollaborazioni());
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
				}
					
				}
				
			
		});
		
		//Button "Inserisci partecipanti"
		JButton inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
		inserisciPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(dipendenteTable.getSelectedRow()==-1) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla tabella");
					
				}
				if(ruoloComboBox.getSelectedItem()==null && dipendenteTable.getSelectedRow()!=-1) {
					JOptionPane.showMessageDialog(null, "Seleziona un ruolo");
					
				}
				else {
					
					
					
					int row= dipendenteTable.getSelectedRow();
					try {
						String ruolo=ruoloComboBox.getSelectedItem().toString();
						controller.inserisciPartecipante(dipendenteTable.getValueAt(row, 0).toString(), codiceProgetto,ruolo);
						JOptionPane.showMessageDialog(null, "Dipendente inserito correttamente");
						
						ruoloComboBox.setSelectedItem(null);
						dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());
						
						//Aggiorna la lista dei partecipanti
						
						Dipendente dipendente=dataModelDipendente.getDipendenteTabella().get(row);
						listmodel.addElement(dipendente);
					} catch (SQLException e1) {
						
						ruoloComboBox.setSelectedItem(null);
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					
				}
			
				
			
			}
		});
		inserisciPartecipanteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				inserisciPartecipanteButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				inserisciPartecipanteButton.setBackground(Color.WHITE);
			}
		});
		inserisciPartecipanteButton.setPreferredSize(new Dimension(190, 30));
		inserisciPartecipanteButton.setMaximumSize(new Dimension(150, 150));
		inserisciPartecipanteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inserisciPartecipanteButton.setBackground(Color.WHITE);
		inserisciPartecipanteButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		inserisciPartecipanteButton.setMargin(new Insets(2, 20, 2, 20));
		inserisciPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		inserisciPartecipanteButton.setAlignmentX(0.5f);
		comandiPanel2.add(inserisciPartecipanteButton);
		comandiPanel2.add(inserisciPartecipanteButton);
		eliminaPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		comandiPanel2.add(eliminaPartecipanteButton);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		partecipantiLabel = new JLabel("Partecipanti");
		partecipantiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		skillScrollPane_1.setColumnHeaderView(partecipantiLabel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		panel.setLayout(gl_panel);
		
		//Label "Gestione Meeting"
		JLabel gestioneMeetingLabel = new JLabel("Gestione Meeting");
		gestioneMeetingLabel.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/Icone/meeting_64.png")));
		gestioneMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(gestioneMeetingLabel)
					.addContainerGap(1208, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestioneMeetingLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		//Table dei meeting
		dataModelDipendente=new PartecipantiTableModel();
		dipendenteTable = new JTable(dataModelDipendente);
		dipendenteTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dipendenteTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteTable.setBackground(Color.WHITE);
		dipendenteTable.setSelectionBackground(Color.LIGHT_GRAY);
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());	//setta il modello di dati della tabella
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		//Click sulla tabella
		dipendenteTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row= dipendenteTable.getSelectedRow();	//ottiene l'indice di riga selezionata
				//ricava le info del dipendente selezionato
					
				nomeTextField.setText(dipendenteTable.getValueAt(row, 1).toString());
				cognomeTextField.setText(dipendenteTable.getValueAt(row, 2).toString());
				
				if(dipendenteTable.getValueAt(row, 3).toString().equals("M")) {
					
					uomoRadioButton.setSelected(true);
					donnaRadioButton.setSelected(false);
				}
				else if(dipendenteTable.getValueAt(row, 3).toString().equals("F"))
				{
					uomoRadioButton.setSelected(false);
					donnaRadioButton.setSelected(true);
					
				}
				
				etàTextField.setText(dipendenteTable.getValueAt(row, 4).toString());
				salarioTextField.setText(dipendenteTable.getValueAt(row, 6).toString());
				valutazioneTextField.setText(dipendenteTable.getValueAt(row, 7).toString());
				
			
				DefaultListModel skillModel=new DefaultListModel();
				skillList.setModel(skillModel);
				
				try {
					skillModel.addAll(controller.ottieniSkillDipendente(dipendenteTable.getValueAt(row, 0).toString()));
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
					if(e.getClickCount()==2) {
					
						if(dipendenteTable.getSelectedRow()==-1) {
							
							JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla tabella");
							
						}
						if(ruoloComboBox.getSelectedItem()==null && dipendenteTable.getSelectedRow()!=-1) {
							JOptionPane.showMessageDialog(null, "Seleziona un ruolo");
							
						}
						else {
							try {
								String ruolo=ruoloComboBox.getSelectedItem().toString();
								controller.inserisciPartecipante(dipendenteTable.getValueAt(row, 0).toString(),codiceProgetto,ruolo);
								JOptionPane.showMessageDialog(null, "Invitato inserito correttamente");
								
								dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti());
							
								Dipendente dipendente=dataModelDipendente.getDipendenteTabella().get(row);
//								CollaborazioneProgetto collaborazioneProgetto;
//								collaborazioneProgetto=dipendente.getCollaborazioni();
//								collaborazioneProgetto.getRuolo();
								listmodel.addElement(dipendente.getCollaborazioni().toArray());
				
							} catch (SQLException e1) {
								
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
							
						}
						
				
				
				}
			
					

			}
		});
		
		DipendenteScrollPane.setViewportView(dipendenteTable);

		
	}
}
