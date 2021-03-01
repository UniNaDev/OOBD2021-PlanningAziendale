/*Finestra per la gestione dei partecipanti a un meeting.
Qui un utente può aggiungere o rimuovere i partecipanti e può
segnare le presenze e le assenze di ciascuno di loro.
****************************************************************/

package gui.dipendente;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerPartecipantiMeeting;
import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import gui.ErroreDialog;
import gui.cellRenderers.InvitatiListRenderer;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.DipendentiTableModel;

import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class InserisciPartecipantiMeeting extends JFrame {

	private JPanel contentPane;
	private JLabel gestioneInvitatiMeetingLabel;
	private JLabel infoDipendenteLabel;
	private JLabel nomeLabel;
	private JTextField nomeTextField;
	private JLabel cognomeLabel;
	private JTextField cognomeTextField;
	private JLabel etàLabel;
	private JTextField etàTextField;
	private JLabel sessoLabel;
	private JRadioButton uomoRadioButton;
	private JRadioButton donnaRadioButton;
	private ButtonGroup modalitàButtonGroup = new ButtonGroup();
	private JLabel valutazioneLabel;
	private JTextField valutazioneTextField;
	private JLabel salarioLabel;
	private JTextField salarioTextField;
	private JList skillList;
	private JScrollPane skillScrollPane;
	private JLabel skillListLabel;
	private DefaultListModel skillModel;
	private DefaultListModel modelloListaInvitati;
	private JSeparator separator;
	private JLabel infoMeetingLabel;
	private JLabel dataInizioLabel;
	private JTextField dataInizioTextField;
	private JLabel dataFineLabel;
	private JTextField dataFineTextField;
	private JLabel oraInizioLabel;
	private JTextField oraInizioTextField;
	private JLabel oraFineLabel;
	private JTextField oraFineTextField;
	private JCheckBox presenzaCheckBox;
	private JLabel presenzaLabel;
	private JList invitatiList;
	private JScrollPane invitatiScrollPane;
	private InvitatiListRenderer invitatiListRenderer;
	private JLabel invitatiLabel;
	private JButton eliminaPartecipanteButton;
	private JButton aggiornaPartecipantiButton;
	private JButton inserisciPartecipanteButton;
	private DipendentiTableModel modelloTabellaDipendenti;
	private JScrollPane dipendenteScrollPane;
	private JTable dipendenteTable;
	private DefaultTableCellRenderer renderTabella;
	private TableRowSorter<TableModel> sorterDipendente;
	
	private Meeting meetingSelezionato;
	private Dipendente dipendenteSelezionato;
	
	private final String VIOLAZIONE_ONNIPRESENZA_DIPENDENTE = "P0003";
	private final String VIOLAZIONE_CAPIENZA_SALA = "P0002";
	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	
	private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	private DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");

	public InserisciPartecipantiMeeting(ControllerPartecipantiMeeting controller, Meeting meetingSelezionato) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				for (Frame frame: Frame.getFrames()) {
					if (!frame.isVisible() && frame.getClass().equals(GestioneMeetingDipendente.class)) {
						frame.setVisible(true);
						setVisible(false);
					}
				}
			}
		});
		this.meetingSelezionato = meetingSelezionato;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1280, 720));
		setTitle("iPlanner - Gestione Partecipanti Meeting");
		
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();	
		
		JPanel comandiPanel = new JPanel();	
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		
		gestioneInvitatiMeetingLabel = new JLabel("Gestione partecipanti meeting");
		gestioneInvitatiMeetingLabel.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/Icone/meeting_64.png")));
		gestioneInvitatiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		infoMeetingLabel = new JLabel("Info Meeting");
		infoMeetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		infoDipendenteLabel = new JLabel("Info Dipendente");
		infoDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoDipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		nomeTextField = new JTextField();
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		nomeTextField.setEditable(false);
		nomeTextField.setColumns(10);
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		cognomeLabel = new JLabel("Cognome");
		cognomeLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		cognomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cognomeTextField = new JTextField();
		cognomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		cognomeTextField.setEditable(false);
		cognomeTextField.setColumns(10);
		cognomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		sessoLabel = new JLabel("Sesso");
		sessoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		sessoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		uomoRadioButton = new JRadioButton("M");
		uomoRadioButton.setEnabled(false);
		uomoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uomoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modalitàButtonGroup.add(uomoRadioButton);
			
		donnaRadioButton = new JRadioButton("F");
		donnaRadioButton.setEnabled(false);
		donnaRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		donnaRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modalitàButtonGroup.add(donnaRadioButton);
		
		etàLabel = new JLabel("Età");
		etàLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		etàLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		etàTextField = new JTextField();
		etàTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàTextField.setEditable(false);
		etàTextField.setColumns(10);
		etàTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		valutazioneLabel = new JLabel("Valutazione");
		valutazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		valutazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		valutazioneTextField = new JTextField();
		valutazioneTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneTextField.setEditable(false);
		valutazioneTextField.setColumns(10);
		valutazioneTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		salarioLabel = new JLabel("Salario");
		salarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		salarioTextField = new JTextField();
		salarioTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioTextField.setEditable(false);
		salarioTextField.setColumns(10);
		salarioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		skillScrollPane = new JScrollPane();
		skillScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		skillScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		skillScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		skillList = new JList();
		skillModel=new DefaultListModel();
		skillList.setFont(new Font("Consolas", Font.PLAIN, 12));
		skillList.setSelectionBackground(Color.WHITE);
		skillList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		skillScrollPane.setViewportView(skillList);
		
		skillListLabel = new JLabel("Skill");
		skillListLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		skillScrollPane.setColumnHeaderView(skillListLabel);
		
		separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		
		dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

		dataInizioTextField = new JTextField(meetingSelezionato.getDataInizio().toString(formatDate));
		dataInizioTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		dataInizioTextField.setEditable(false);
		dataInizioTextField.setColumns(10);
		dataInizioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		dataFineTextField = new JTextField(meetingSelezionato.getDataFine().toString(formatDate));
		dataFineTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		dataFineTextField.setEditable(false);
		dataFineTextField.setColumns(10);
		dataFineTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		oraInizioLabel = new JLabel("Ora inizio");
		oraInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		oraInizioTextField = new JTextField(meetingSelezionato.getOraInizio().toString(formatHour));
		oraInizioTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		oraInizioTextField.setEditable(false);
		oraInizioTextField.setColumns(10);
		oraInizioTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		oraFineLabel = new JLabel("Ora fine");
		oraFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		oraFineTextField = new JTextField(meetingSelezionato.getOraFine().toString(formatHour));
		oraFineTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		oraFineTextField.setEditable(false);
		oraFineTextField.setColumns(10);
		oraFineTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		presenzaLabel = new JLabel("Presente");
		presenzaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		presenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		presenzaCheckBox = new JCheckBox("");
		presenzaCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		invitatiScrollPane = new JScrollPane();
		invitatiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		invitatiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		invitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiScrollPane.setColumnHeaderView(invitatiLabel);
		
		invitatiList = new JList();
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		invitatiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		impostaRendererListaInvitati();
		inizializzaListaInvitati();
		invitatiScrollPane.setViewportView(invitatiList);
		invitatiList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!invitatiList.isSelectionEmpty())
					presenzaCheckBox.setEnabled(true);
				
				impostaInfoDipendenteDaLista(controller);
				if (dipendenteTable.getSelectedRow() < 0 || !invitatiList.isSelectionEmpty()) {
					dipendenteSelezionato = null;
					dipendenteTable.clearSelection();
				}
			}		
		});
		
		JPanel comandiPanel2 = new JPanel();	
		comandiPanel2.setBorder(new LineBorder(new Color(192, 192, 192)));
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		comandiPanel.add(comandiPanel2);
		
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
					JOptionPane.showMessageDialog(null, "Selezionare un partecipante dalla lista da eliminare.", "Eliminazione Fallita", JOptionPane.INFORMATION_MESSAGE);
					invitatiLabel.setForeground(Color.RED);
				}
				else {
					eliminaInvitato(controller);
					svuotaCampi();
				}
			}
		});

		inserisciPartecipanteButton = new JButton("Inserisci partecipante");
		inserisciPartecipanteButton.setPreferredSize(new Dimension(190, 30));
		inserisciPartecipanteButton.setMaximumSize(new Dimension(150, 150));
		inserisciPartecipanteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inserisciPartecipanteButton.setBackground(Color.WHITE);
		inserisciPartecipanteButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		inserisciPartecipanteButton.setMargin(new Insets(2, 20, 2, 20));
		inserisciPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		inserisciPartecipanteButton.setAlignmentX(0.5f);
		inserisciPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dipendenteSelezionato == null)
					JOptionPane.showMessageDialog(null, "Selezionare un dipendente dalla tabella da aggiungere.", "Inserimento Fallito", JOptionPane.INFORMATION_MESSAGE);
				else {
					inserisciInvitatoMeeting(controller);
					aggiornaTabellaDipendenti(controller);
					svuotaCampi();
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
		aggiornaPartecipantiButton.setPreferredSize(new Dimension(190, 30));
		aggiornaPartecipantiButton.setMaximumSize(new Dimension(150, 150));
		aggiornaPartecipantiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aggiornaPartecipantiButton.setBackground(Color.WHITE);
		aggiornaPartecipantiButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		aggiornaPartecipantiButton.setMargin(new Insets(2, 20, 2, 20));
		aggiornaPartecipantiButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		aggiornaPartecipantiButton.setAlignmentX(0.5f);
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
				if(invitatiList.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla lista da aggiornare.", "Aggiornamento Fallito", JOptionPane.INFORMATION_MESSAGE);
					invitatiLabel.setForeground(Color.RED);
				}
				else {
					aggiornaPresenzaInvitato(controller);
					svuotaCampi();
				}
			}	
		});
		comandiPanel2.add(aggiornaPartecipantiButton);
		comandiPanel2.add(inserisciPartecipanteButton);
		comandiPanel2.add(eliminaPartecipanteButton);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		dipendenteScrollPane = new JScrollPane();
		dipendenteScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		dipendenteScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		modelloTabellaDipendenti = new DipendentiTableModel();
		inizializzaTabellaDipendente(controller);
		dipendenteTable = new JTable(modelloTabellaDipendenti);
		dipendenteTable.setFont(new Font("Consolas", Font.PLAIN, 12));
		dipendenteTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dipendenteTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteTable.setBackground(Color.WHITE);
		dipendenteTable.setSelectionBackground(Color.LIGHT_GRAY);
		impostaProprietàTabellaDipendente();
		dipendenteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dipendenteTable.getTableHeader().setReorderingAllowed(false);
		dipendenteTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				impostaInfoDipendenteDaTabella(controller);
				invitatiList.clearSelection();
				
				if(event.getClickCount() == 2) {	
					inserisciInvitatoMeeting(controller);
					aggiornaTabellaDipendenti(controller);
					svuotaCampi();
				} 
			}
		});
		
		dipendenteScrollPane.setViewportView(dipendenteTable);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(dipendenteScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(comandiPanel, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
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
					.addComponent(dipendenteScrollPane, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
		);
		comandiPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(184)
							.addComponent(infoDipendenteLabel, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
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
							.addComponent(infoMeetingLabel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addGap(230))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(presenzaLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
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
					.addGap(25)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(infoMeetingLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(infoDipendenteLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(skillScrollPane, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
						.addComponent(invitatiScrollPane, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(64, Short.MAX_VALUE))
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(91)
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
						.addComponent(presenzaLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addGap(87))
		);
		gl_infoPanel2.linkSize(SwingConstants.VERTICAL, new Component[] {invitatiScrollPane, skillScrollPane});
		gl_infoPanel2.linkSize(SwingConstants.VERTICAL, new Component[] {dataInizioLabel, dataFineLabel, oraInizioLabel, oraFineLabel, presenzaLabel});
		gl_infoPanel2.linkSize(SwingConstants.HORIZONTAL, new Component[] {oraInizioTextField, oraFineTextField, oraInizioLabel, oraFineLabel});
		gl_infoPanel2.linkSize(SwingConstants.HORIZONTAL, new Component[] {dataInizioTextField, dataFineTextField, dataInizioLabel, dataFineLabel});
		
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		panel.setLayout(gl_panel);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(gestioneInvitatiMeetingLabel)
					.addContainerGap(1208, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestioneInvitatiMeetingLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);	
	}

	//Altri metodi
	//-----------------------------------------------------------
	
	private void inizializzaListaInvitati() {
		modelloListaInvitati = new DefaultListModel();
		ArrayList<PartecipazioneMeeting> partecipanti = meetingSelezionato.getPartecipantiAlMeeting();
		for (PartecipazioneMeeting partecipante: partecipanti)
			if (partecipante.isOrganizzatore()) {
				partecipanti.remove(partecipante);
				break;
			}
		modelloListaInvitati.addAll(partecipanti);
		invitatiList.setModel(modelloListaInvitati);
	}

	private void impostaRendererListaInvitati() {
		invitatiListRenderer = new InvitatiListRenderer();
		invitatiList.setCellRenderer(invitatiListRenderer);
	}

	private void inizializzaTabellaDipendente(ControllerPartecipantiMeeting controller) {
		try {
			modelloTabellaDipendenti.setDipendenteTabella(controller.ottieniDipendentiNonInvitatiMeeting(meetingSelezionato));
		} catch (SQLException eccezioneSQL) {
			ErroreDialog errore = new ErroreDialog(eccezioneSQL,true);
			errore.setVisible(true);
		}
	}
	
	private void impostaProprietàTabellaDipendente() {
		impostaCellRendererTabellaDipendente();
		impostaModelloColonneTabellaDipendente();
		impostaSorterTabellaDipendente();
	}

	private void impostaCellRendererTabellaDipendente() {
		renderTabella = new DefaultTableCellRenderer();
        renderTabella.setHorizontalAlignment(SwingConstants.CENTER);
        renderTabella.setVerticalAlignment(SwingConstants.CENTER);
		dipendenteTable.getColumnModel().getColumn(0).setCellRenderer(renderTabella);
		dipendenteTable.getColumnModel().getColumn(1).setCellRenderer(renderTabella);
		dipendenteTable.getColumnModel().getColumn(2).setCellRenderer(renderTabella);
		dipendenteTable.getColumnModel().getColumn(3).setCellRenderer(renderTabella);
		dipendenteTable.getColumnModel().getColumn(4).setCellRenderer(renderTabella);
		dipendenteTable.getColumnModel().getColumn(5).setCellRenderer(renderTabella);
	}
	
	private void impostaModelloColonneTabellaDipendente() {
		dipendenteTable.getColumnModel().getColumn(0).setMinWidth(150);
		dipendenteTable.getColumnModel().getColumn(1).setMinWidth(150);
		dipendenteTable.getColumnModel().getColumn(2).setMinWidth(300);
		dipendenteTable.getColumnModel().getColumn(3).setMinWidth(50);
		dipendenteTable.getColumnModel().getColumn(4).setMinWidth(100);
		dipendenteTable.getColumnModel().getColumn(5).setMinWidth(100);
	}
	
	private void impostaSorterTabellaDipendente() {
		sorterDipendente = new TableRowSorter<>(modelloTabellaDipendenti);
		dipendenteTable.setRowSorter(sorterDipendente);
	}
	
	private void inserisciInvitatoMeeting(ControllerPartecipantiMeeting controller) {
		boolean presenza = presenzaCheckBox.isSelected();
		PartecipazioneMeeting partecipazioneMeeting = new PartecipazioneMeeting(meetingSelezionato, dipendenteSelezionato, presenza, false);
		try {
			controller.inserisciInvitatoMeeting(partecipazioneMeeting);
			JOptionPane.showMessageDialog(null, "Dipendente inserito correttamente.", "Inserimento Riuscito", JOptionPane.INFORMATION_MESSAGE);
			modelloListaInvitati.addElement(partecipazioneMeeting);
			aggiornaSorter();
		} catch(SQLException eccezioneSQL) {
			ErroreDialog errore = null;
			switch (eccezioneSQL.getSQLState()) {
			case VIOLAZIONE_PKEY_UNIQUE:
				errore = new ErroreDialog(eccezioneSQL,
						"Inserimento Fallito",
						"L'inserimento del dipendente è fallito perchè già partecipa al meeting.", false);
				break;
			case VIOLAZIONE_ONNIPRESENZA_DIPENDENTE:
				errore = new ErroreDialog(eccezioneSQL,
						"Inserimento Fallito",
						"L'inserimento del dipendente è fallito perchè " + dipendenteSelezionato + " ha altri meeting che si accavallano con questo.", false);
				break;
			case VIOLAZIONE_CAPIENZA_SALA:
				errore = new ErroreDialog(eccezioneSQL,
						"Inserimento Fallito",
						"L'inserimento del dipendente è fallito perchè così il numero di partecipanti supera la capienza della sala scelta per il meeting.\n"
						+ "Si consiglia di cambiare sala se è necessario aggiungere altri partecipanti.", false);
				break;
			default:
				errore = new ErroreDialog(eccezioneSQL,true);
			}
			errore.setVisible(true);
		}
	}
	
	private void aggiornaPresenzaInvitato(ControllerPartecipantiMeeting controller) {
		invitatiLabel.setForeground(Color.BLACK);
		boolean presenza = presenzaCheckBox.isSelected();
		PartecipazioneMeeting partecipazione = (PartecipazioneMeeting) invitatiList.getSelectedValue();
		partecipazione.setPresente(presenza);
		try {
			controller.aggiornaPresenzaInvitato(partecipazione);
			JOptionPane.showMessageDialog(null, "Modifica effettuata con successo.", "Aggiornamento Riuscito", JOptionPane.INFORMATION_MESSAGE);
			modelloListaInvitati.removeElementAt(invitatiList.getSelectedIndex()); 
			modelloListaInvitati.addElement(partecipazione);
		} catch(SQLException eccezioneSQL) {
			ErroreDialog errore = new ErroreDialog(eccezioneSQL,true);
			errore.setVisible(true);
		}
	}
	
	private void eliminaInvitato(ControllerPartecipantiMeeting controller){
		invitatiLabel.setForeground(Color.BLACK);
		try {
			controller.eliminaInvitato((PartecipazioneMeeting)invitatiList.getSelectedValue());
			JOptionPane.showMessageDialog(null, "Partecipante eliminato con successo.", "Eliminazione Riuscita", JOptionPane.INFORMATION_MESSAGE);
			aggiornaListaInvitati();
			aggiornaTabellaDipendenti(controller);
			aggiornaSorter();
		} catch(SQLException eccezioneSQL) {
			ErroreDialog errore = new ErroreDialog(eccezioneSQL,true);
			errore.setVisible(true);
		}
	}
	
	private void aggiornaListaInvitati() {
		modelloListaInvitati.removeElementAt(invitatiList.getSelectedIndex());
		invitatiList.clearSelection();
	}

	private void aggiornaTabellaDipendenti(ControllerPartecipantiMeeting controller) {
		try {
			modelloTabellaDipendenti.setDipendenteTabella(controller.ottieniDipendentiNonInvitatiMeeting(meetingSelezionato));
			modelloTabellaDipendenti.fireTableDataChanged();
			aggiornaSorter();
		} catch (SQLException eccezioneSQL) {
			ErroreDialog errore = new ErroreDialog(eccezioneSQL,true);
			errore.setVisible(true);
		}
	}
	
	private void aggiornaSorter() {
		if(dipendenteTable.getRowCount() != 0)
			sorterDipendente.setModel(modelloTabellaDipendenti);
	}
	
	private void impostaInfoDipendenteDaTabella(ControllerPartecipantiMeeting controller) {
		int rigaSelezionata = dipendenteTable.getSelectedRow();
		rigaSelezionata = dipendenteTable.convertRowIndexToModel(rigaSelezionata);
		dipendenteSelezionato = modelloTabellaDipendenti.getSelected(rigaSelezionata);
		
		nomeTextField.setText(dipendenteSelezionato.getNome());
		cognomeTextField.setText(dipendenteSelezionato.getCognome());
		if(dipendenteSelezionato.getSesso() == 'M') {
			uomoRadioButton.setSelected(true);
			donnaRadioButton.setSelected(false);
		}
		else {
			uomoRadioButton.setSelected(false);
			donnaRadioButton.setSelected(true);	
		}
		etàTextField.setText(String.valueOf(dipendenteSelezionato.getEtà()));
		salarioTextField.setText(String.valueOf(dipendenteSelezionato.getSalario()));
		valutazioneTextField.setText(String.format("%.2f",dipendenteSelezionato.getValutazione()));
		skillModel.removeAllElements();
		try {
			skillModel.addAll(controller.ottieniSkillDipendente(dipendenteSelezionato.getCf()));
		} catch (SQLException eccezioneSQL) {
			ErroreDialog errore = new ErroreDialog(eccezioneSQL,true);
			errore.setVisible(true);
		}
		skillList.setModel(skillModel);
	}
	
	private void impostaInfoDipendenteDaLista(ControllerPartecipantiMeeting controller) {
		PartecipazioneMeeting partecipazione = (PartecipazioneMeeting) invitatiList.getSelectedValue();
		if(partecipazione != null) {
			if(partecipazione.isPresente() == true)
				presenzaCheckBox.setSelected(true);
			else
				presenzaCheckBox.setSelected(false);
			nomeTextField.setText(partecipazione.getPartecipante().getNome());
			cognomeTextField.setText(partecipazione.getPartecipante().getCognome());
			etàTextField.setText(String.valueOf(partecipazione.getPartecipante().getEtà()));
			valutazioneTextField.setText(String.format("%.2f", partecipazione.getPartecipante().getValutazione()));
			salarioTextField.setText(String.format("%.2f", partecipazione.getPartecipante().getSalario()));
			if(partecipazione.getPartecipante().getSesso() == 'M')
				uomoRadioButton.setSelected(true);
			else 
				donnaRadioButton.setSelected(true);
			skillModel.removeAllElements();
			try {
				skillModel.addAll(controller.ottieniSkillDipendente(partecipazione.getPartecipante().getCf()));
			} catch (SQLException eccezioneSQL) {
				ErroreDialog errore = new ErroreDialog(eccezioneSQL,true);
				errore.setVisible(true);
			}
			skillList.setModel(skillModel);
		}
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
		dipendenteSelezionato = null;
	}
}
