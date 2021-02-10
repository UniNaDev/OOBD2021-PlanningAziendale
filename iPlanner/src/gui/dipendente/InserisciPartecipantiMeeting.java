package gui.dipendente;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerMeeting;
import controller.dipendente.ControllerPartecipantiMeeting;
import customUI.CustomScrollBarUI;
import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Progetto;
import entita.SalaRiunione;
import entita.Skill;
import gui.cellRenderers.InvitatiListRenderer;
import gui.tableModels.DipendentiTableModel;
import gui.tableModels.PartecipantiTableModel;

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
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;



public class InserisciPartecipantiMeeting extends JFrame {

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
	private JButton aggiornaPartecipantiButton;
	private JButton inserisciPartecipanteButton;
	private PartecipantiTableModel dataModelDipendente;
	private JTextField nomeTextField;
	private JTextField cognomeTextField;
	private JTextField etàTextField;
	private JTextField valutazioneTextField;
	private JTextField salarioTextField;
	private JTextField dataInizioTextField;
	private JTextField dataFineTextField;
	private JTextField oraInizioTextField;
	private JTextField oraFineTextField;
	private JList invitatiList;
	private JLabel invitatiLabel;
	private JList skillList;
	private DefaultListModel skillModel;
	private TableRowSorter<TableModel> sorterDipendente;

	//Creazione frame
	//---------------------------------------------
	public InserisciPartecipantiMeeting(ControllerPartecipantiMeeting controller,Meeting meetingSelezionato) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1150, 700));
		
		
		setTitle("iPlanner-Gestione invitati meeting");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();	//main panel
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();	//panel info
		
		JPanel comandiPanel = new JPanel();	//panel per i comandi
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		
		JScrollPane DipendenteScrollPane = new JScrollPane();	//scroll pane per la tabella meeting
		DipendenteScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		DipendenteScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DipendenteScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comandiPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(infoPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(DipendenteScrollPane, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
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
		uomoRadioButton.setEnabled(false);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modalitàButtonGroup.add(uomoRadioButton);
		
		//RadioButton donna
		donnaRadioButton = new JRadioButton("F");
		donnaRadioButton.setEnabled(false);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modalitàButtonGroup.add(donnaRadioButton);
		
		nomeTextField = new JTextField();
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		nomeTextField.setEditable(false);
		nomeTextField.setColumns(10);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		cognomeTextField = new JTextField();
		cognomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		cognomeTextField.setEditable(false);
		cognomeTextField.setColumns(10);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		etàTextField = new JTextField();
		etàTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàTextField.setEditable(false);
		etàTextField.setColumns(10);
		etàTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		valutazioneTextField = new JTextField();
		valutazioneTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneTextField.setEditable(false);
		valutazioneTextField.setColumns(10);
		valutazioneTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		salarioTextField = new JTextField();
		salarioTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioTextField.setEditable(false);
		salarioTextField.setColumns(10);
		salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");

		dataInizioTextField = new JTextField(meetingSelezionato.getDataInizio().toString(formatDate));
		dataInizioTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		dataInizioTextField.setEditable(false);
		dataInizioTextField.setColumns(10);
		dataInizioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		dataFineTextField = new JTextField(meetingSelezionato.getDataFine().toString(formatDate));
		dataFineTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		dataFineTextField.setEditable(false);
		dataFineTextField.setColumns(10);
		dataFineTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JLabel dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JLabel dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
		oraInizioTextField = new JTextField(meetingSelezionato.getOraInizio().toString(formatHour));
		oraInizioTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		oraInizioTextField.setEditable(false);
		oraInizioTextField.setColumns(10);
		oraInizioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		oraFineTextField = new JTextField(meetingSelezionato.getOraFine().toString(formatHour));
		oraFineTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		oraFineTextField.setEditable(false);
		oraFineTextField.setColumns(10);
		oraFineTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JLabel oraInizioLabel = new JLabel("Ora inizio");
		oraInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JLabel oraFineLabel = new JLabel("Ora fine");
		oraFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JScrollPane invitatiScrollPane = new JScrollPane();
		invitatiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		invitatiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		invitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JLabel lblNewLabel = new JLabel("Info Meeting");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel lblInfoDipendente = new JLabel("Info Dipendente");
		lblInfoDipendente.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoDipendente.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JScrollPane skillScrollPane = new JScrollPane();
		skillScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		skillScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		skillScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JLabel lblPresenza = new JLabel("Presenza");
		lblPresenza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPresenza.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JCheckBox presenzaCheckBox = new JCheckBox("");

		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(184)
							.addComponent(lblInfoDipendente, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
							.addGap(148))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(43)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(etàLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(valutazioneLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(sessoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(nomeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cognomeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(salarioLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_infoPanel2.createSequentialGroup()
										.addComponent(uomoRadioButton)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(donnaRadioButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
									.addComponent(etàTextField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
									.addComponent(valutazioneTextField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
									.addComponent(nomeTextField)
									.addComponent(cognomeTextField))
								.addComponent(salarioTextField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(skillScrollPane, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
							.addGap(33)))
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addGap(230))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPresenza, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(oraFineTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(presenzaCheckBox))
							.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
							.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
							.addGap(22))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(130)
							.addComponent(oraInizioTextField, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(360, Short.MAX_VALUE))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(130)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataFineTextField, 90, 90, 90)
								.addComponent(dataInizioTextField, 90, 90, 90))
							.addGap(360))))
		);
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(73)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(nomeTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(nomeLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(cognomeTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(cognomeLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(sessoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(uomoRadioButton)
						.addComponent(donnaRadioButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(etàTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(etàLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(valutazioneTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(valutazioneLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(salarioTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(salarioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addGap(68))
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addContainerGap(63, Short.MAX_VALUE)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addGap(59))
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(70)
					.addComponent(skillScrollPane, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
					.addGap(65))
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInfoDipendente, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(37)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataInizioTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataFineTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(oraInizioTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(oraFineTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(presenzaCheckBox)
								.addComponent(lblPresenza, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(13)
							.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(64, Short.MAX_VALUE))
		);
		gl_infoPanel2.linkSize(SwingConstants.VERTICAL, new Component[] {dataInizioLabel, dataFineLabel, oraInizioLabel, oraFineLabel, lblPresenza});
		gl_infoPanel2.linkSize(SwingConstants.HORIZONTAL, new Component[] {dataInizioTextField, dataFineTextField, dataInizioLabel, dataFineLabel});
		gl_infoPanel2.linkSize(SwingConstants.HORIZONTAL, new Component[] {oraInizioTextField, oraFineTextField, oraInizioLabel, oraFineLabel});
		
		skillList = new JList();
		skillModel=new DefaultListModel();
		skillList.setFont(new Font("Consolas", Font.PLAIN, 12));
		skillList.setSelectionBackground(Color.WHITE);
		skillList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		skillScrollPane.setViewportView(skillList);
		
		JLabel Skill = new JLabel("Skill");
		Skill.setFont(new Font("Consolas", Font.PLAIN, 15));
		skillScrollPane.setColumnHeaderView(Skill);
		
		invitatiList = new JList();
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		InvitatiListRenderer invitatiListRenderer=new InvitatiListRenderer();
		invitatiList.setCellRenderer(invitatiListRenderer);
		invitatiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		invitatiList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				
				PartecipazioneMeeting partecipaz=(PartecipazioneMeeting) invitatiList.getSelectedValue();
				if(partecipaz!=null) {
					
					if(partecipaz.isPresenza()==true)
						presenzaCheckBox.setSelected(true);
						else
							presenzaCheckBox.setSelected(false);
					
					
					nomeTextField.setText(partecipaz.getPartecipante().getNome());
					cognomeTextField.setText(partecipaz.getPartecipante().getCognome());
					etàTextField.setText(String.valueOf(partecipaz.getPartecipante().getEtà()));
					valutazioneTextField.setText(String.format("%.2f", partecipaz.getPartecipante().getValutazione()));
					salarioTextField.setText(String.format("%.2f", partecipaz.getPartecipante().getSalario()));
					if(partecipaz.getPartecipante().getSesso()=='M')
						uomoRadioButton.setSelected(true);
					else 
						donnaRadioButton.setSelected(true);
					
					skillList.setModel(skillModel);
					skillModel.removeAllElements();
					try {
						skillModel.addAll(controller.ottieniSkillDipendente(partecipaz.getPartecipante().getCf()));
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					dipendenteTable.clearSelection();
				}
					
					
					
				}
					
				}
				
			
		);
		
		DefaultListModel listmodel=new DefaultListModel();
		invitatiList.setModel(listmodel);
		listmodel.addAll(meetingSelezionato.getPartecipantiAlMeeting());

		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		invitatiScrollPane.setViewportView(invitatiList);
		
		eliminaPartecipanteButton = new JButton("Elimina partecipante");
		eliminaPartecipanteButton.setPreferredSize(new Dimension(190, 30));
		eliminaPartecipanteButton.setMaximumSize(new Dimension(150, 150));
		eliminaPartecipanteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaPartecipanteButton.setBackground(Color.WHITE);
		eliminaPartecipanteButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaPartecipanteButton.setMargin(new Insets(2, 20, 2, 20));
		eliminaPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		eliminaPartecipanteButton.setAlignmentX(0.5f);
		eliminaPartecipanteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				eliminaPartecipanteButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				eliminaPartecipanteButton.setBackground(Color.WHITE);
			}
		});
		eliminaPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(invitatiList.isSelectionEmpty()) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante da eliminare");
					invitatiLabel.setForeground(Color.RED);
					
				}
				else {
					
					try {
						invitatiLabel.setForeground(Color.BLACK);
						
						controller.eliminaPartecipante((PartecipazioneMeeting)invitatiList.getSelectedValue());
						JOptionPane.showMessageDialog(null, "Partecipante eliminato");
						
						//Rimuove dalla lista l'elemento eliminato
						listmodel.removeElementAt(invitatiList.getSelectedIndex());
						
						//Pone il valore selezionato a null
						invitatiList.setSelectedValue(null,false);
						
						//Aggiorna i dipendenti disponibili
						dataModelDipendente.fireTableDataChanged();
						dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(meetingSelezionato));
						
						//Aggiorna il modello del sorterDipendente in seguito alle modifiche
						
						sorterDipendente.setModel(dataModelDipendente);
						
						svuotaCampi();
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}
					
				}
				
			
		});
		
		//Button "Inserisci partecipanti"
		inserisciPartecipanteButton = new JButton("Inserisci partecipante");
		inserisciPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(dipendenteTable.getSelectedRow()==-1) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla tabella");
					
				}
				else {
					
					int row= dipendenteTable.getSelectedRow();
					try {
						
						
						boolean presenza;
						Dipendente dipendenteSelezionato=dataModelDipendente.getSelected(dipendenteTable.convertRowIndexToModel(dipendenteTable.getSelectedRow()));
						if(presenzaCheckBox.isSelected())
						{
							presenza=true;
						}
						else
							presenza=false;
						
						//Crea la partecipazione al meeting
						PartecipazioneMeeting partecipazioneMeeting=new PartecipazioneMeeting(meetingSelezionato, dipendenteSelezionato, presenza, false);
					
						//Prova a fare l'inserimento del partecipante al meeting
						controller.inserisciPartecipante(partecipazioneMeeting);
						JOptionPane.showMessageDialog(null, "Invitato inserito correttamente");
						
						//Aggiorna i dipendenti disponibili
						dataModelDipendente.fireTableDataChanged();
						dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(meetingSelezionato));
					
						//Aggiunge l'elemento inserito alla lista
						listmodel.addElement(partecipazioneMeeting);  
					
						//Aggiorna il modello del sorterDipendente in seguito alle modifiche
						if(dipendenteTable.getRowCount()!=0)
						sorterDipendente.setModel(dataModelDipendente);
						
						//Svuota i campi
						svuotaCampi();
					
					} catch (SQLException e1) {
						
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
		
		aggiornaPartecipantiButton = new JButton("Aggiorna partecipante");
		aggiornaPartecipantiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				aggiornaPartecipantiButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				aggiornaPartecipantiButton.setBackground(Color.WHITE);
			}
		});
		aggiornaPartecipantiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(invitatiList.getSelectedValue()==null) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla lista");
					invitatiLabel.setForeground(Color.RED);
				}
				
				else {
					
					invitatiLabel.setForeground(Color.BLACK);
					boolean presenza;
					PartecipazioneMeeting partecipazione=(PartecipazioneMeeting) invitatiList.getSelectedValue();
					
					if(presenzaCheckBox.isSelected())
					{
						presenza=true;
					}
					else
						presenza=false;
					
					PartecipazioneMeeting partecipazioneMeeting=new PartecipazioneMeeting(meetingSelezionato, partecipazione.getPartecipante(), presenza, false);
					
					
					try {
						controller.aggiornaPresenzaPartecipante(partecipazioneMeeting);
						JOptionPane.showMessageDialog(null, "Modifica effettuata con successo");
						listmodel.removeElementAt(invitatiList.getSelectedIndex()); //rimuove il vecchio elemento
						listmodel.addElement(partecipazioneMeeting); //lo aggiorna con il nuovo
						
						 //Aggiorna il modello del sorterDipendente in seguito alle modifiche
						if(dipendenteTable.getRowCount()!=0)
						sorterDipendente.setModel(dataModelDipendente);
						
						//Svuota i campi
						svuotaCampi();
						
					} catch (SQLException e1) {
						
						
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		aggiornaPartecipantiButton.setPreferredSize(new Dimension(190, 30));
		aggiornaPartecipantiButton.setMaximumSize(new Dimension(150, 150));
		aggiornaPartecipantiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aggiornaPartecipantiButton.setBackground(Color.WHITE);
		aggiornaPartecipantiButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		aggiornaPartecipantiButton.setMargin(new Insets(2, 20, 2, 20));
		aggiornaPartecipantiButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		aggiornaPartecipantiButton.setAlignmentX(0.5f);
		comandiPanel2.add(aggiornaPartecipantiButton);
		
		
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
		
		invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiScrollPane.setColumnHeaderView(invitatiLabel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		panel.setLayout(gl_panel);
		
		//Label "Gestione Meeting"
		JLabel gestioneMeetingLabel = new JLabel("Gestione invitati meeting");
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
		dipendenteTable.setFont(new Font("Consolas", Font.PLAIN, 12));
		dipendenteTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dipendenteTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteTable.setBackground(Color.WHITE);
		dipendenteTable.setSelectionBackground(Color.LIGHT_GRAY);
		
		
		//Modello delle colonne personalizzato
		dipendenteTable.getColumnModel().getColumn(0).setMinWidth(150);
		dipendenteTable.getColumnModel().getColumn(1).setMinWidth(200);
		dipendenteTable.getColumnModel().getColumn(2).setMinWidth(200);
		dipendenteTable.getColumnModel().getColumn(3).setMinWidth(50);
		dipendenteTable.getColumnModel().getColumn(4).setMinWidth(50);
		dipendenteTable.getColumnModel().getColumn(5).setMinWidth(300);
		dipendenteTable.getColumnModel().getColumn(6).setMinWidth(100);
		dipendenteTable.getColumnModel().getColumn(7).setMinWidth(100);
		
		
		
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(meetingSelezionato));	//setta il modello di dati della tabella
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		
		//Sorter tabella
		sorterDipendente = new TableRowSorter<TableModel>(dataModelDipendente);
		dipendenteTable.setRowSorter(sorterDipendente);
		
		//Seleziona singola
		dipendenteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Le colonne non possono essere riordinate
		dipendenteTable.getTableHeader().setReorderingAllowed(false);
	

		//Click sulla tabella
		dipendenteTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//quando viene cliccata una riga della tabella,viene deselezionata elemento selezionato della lista
				invitatiList.clearSelection();
				
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
				
			
				
				skillList.setModel(skillModel);
				skillModel.removeAllElements();
				try {
					skillModel.addAll(controller.ottieniSkillDipendente(dipendenteTable.getValueAt(row, 0).toString()));
				} catch (SQLException e2) {
					
					e2.printStackTrace();
				}
				
					if(e.getClickCount()==2) {
					
					try {
						
						//Controllo la checkbox
						boolean presenza;
						if(presenzaCheckBox.isSelected())
						{
							presenza=true;
						}
						else
							presenza=false;
						
						//Riceve il dipendente selezionato
						Dipendente dipendente=dataModelDipendente.getSelected(dipendenteTable.convertRowIndexToModel(dipendenteTable.getSelectedRow()));
						
						//Creo la partecipazione al meeting
						PartecipazioneMeeting partecipazioneMeeting=new PartecipazioneMeeting(meetingSelezionato, dipendente, presenza, false);
						
						//Inserisce invitato al meeting
						controller.inserisciPartecipante(partecipazioneMeeting);
						JOptionPane.showMessageDialog(null, "Invitato inserito correttamente");
						
						//Aggiorna la lista e la tabella
						listmodel.addElement(partecipazioneMeeting);
						dataModelDipendente.fireTableDataChanged();
						dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(meetingSelezionato)); //Per aggiornare tabella
					
						 //Aggiorna il modello del sorterDipendente in seguito alle modifiche
					
						sorterDipendente.setModel(dataModelDipendente);
						
						//Svuota i campi
						svuotaCampi();
	
		
					} catch (SQLException e1) {
						
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				
				}
			
					

			}
		
			
		});
		
		DipendenteScrollPane.setViewportView(dipendenteTable);

		
	}
	
	private void svuotaCampi() {
		
		nomeTextField.setText(null);
		cognomeTextField.setText(null);
		etàTextField.setText(null);
		valutazioneTextField.setText(null);
		salarioTextField.setText(null);
		
		if(uomoRadioButton.isSelected()) 
			uomoRadioButton.setSelected(false);
		else
			donnaRadioButton.setSelected(false);
		
		skillModel.removeAllElements();
		
		
		
	}
}
