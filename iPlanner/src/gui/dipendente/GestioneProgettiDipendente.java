package gui.dipendente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerProgetto;
import entita.AmbitoProgetto;
import entita.Dipendente;
import entita.Progetto;
import gui.cellRenderers.MeetingListRenderer;
import gui.cellRenderers.PartecipantiListRenderer;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.DataComparator;
import gui.tableModels.ProgettoTableModel;
import java.awt.BorderLayout;

public class GestioneProgettiDipendente extends JFrame {
	private JPanel contentPane;
	private JLabel nomeProgettoLabel;
	private JLabel descrizioneProgettoLabel;
	private JLabel tipologiaProgettoLabel;
	private JLabel dataTerminazioneLabel;
	private JLabel progettoTerminatoLabel;
	private JLabel ambitoProgettoLabel;
	private JLabel meetingRelativiLabel;
	private JLabel gestioneProgettoLabel;
	private JLabel scadenzaProgettoLabel;
	private JTable progettiTable;
	private JTextField cercaTextField;
	private JScrollPane descrizioneProgettoScrollPane;
	private JComboBox giornoScadenzaComboBox;
	private JComboBox meseScadenzaComboBox;
	private JComboBox annoScadenzaComboBox;
	private JComboBox giornoTerminazioneComboBox;
	private JComboBox meseTerminazioneComboBox;
	private JComboBox annoTerminazioneComboBox;
	private JComboBox tipologiaComboBox;
	private DefaultComboBoxModel modelloComboBoxTipologie;
	private ProgettoTableModel modelloTabellaProgetti;
	private TableRowSorter<TableModel> sorterProgetti;
	private JCheckBox progettoTerminatoCheckBox;
	private DefaultListModel modelloListaPartecipanti;
	private DefaultListModel modelloListaMeetingRelativi;
	private JList ambitiList;
	private DefaultListModel<AmbitoProgetto> modelloListaAmbiti;
	private JList partecipantiList;
	private JList meetingRelativiList;
	private JTextField nomeTextField;
	private JTextArea descrizioneTextArea;
	private JLabel filtroScadutoLabel;
	private JComboBox filtroScadutoComboBox;
	private JLabel filtroTerminatoLabel;
	private JComboBox filtroTerminatoComboBox;
	private JComboBox filtroAmbitiComboBox;
	private DefaultComboBoxModel modelloComboBoxFiltroAmbiti;
	private JComboBox filtroTipologieComboBox;
	private DefaultComboBoxModel modelloComboBoxFiltroTipologie;
	private JButton inserisciPartecipanteButton;
	private JButton eliminaButton;
	private JButton confermaModificheButton;
	
	private LocalDate dataAttuale = LocalDate.now();
	private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	
	
	private Progetto progettoSelezionato;
	private String nomeProgetto, descrizioneProgetto;
	private LocalDate dataCreazione, dataScadenza, dataTerminazione;
	private ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
	private String tipologia;
	
	private String[] siNoOpzioni = {null, "Si", "No"};
	
	public GestioneProgettiDipendente(ControllerProgetto controller, Dipendente dipendente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneProgettiDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1600,900));
		setTitle("iPlanner - Gestione progetto");
		setBounds(100, 100, 1600, 900);
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();
		
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane tabellaScrollPane = new JScrollPane();
		tabellaScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		tabellaScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabellaScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comandiPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1551, Short.MAX_VALUE)
						.addComponent(infoPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1551, Short.MAX_VALUE)
						.addComponent(tabellaScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1551, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(tabellaScrollPane, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel comandiPanel2 = new JPanel();
		comandiPanel2.setBorder(null);
		
		JButton pulisciCampiButton = new JButton("Pulisci Campi");
		pulisciCampiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				pulisciCampiButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				pulisciCampiButton.setBackground(Color.WHITE);
			}
		});
		pulisciCampiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					pulisciCampi();
			}
		});
		pulisciCampiButton.setPreferredSize(new Dimension(150, 30));
		pulisciCampiButton.setMaximumSize(new Dimension(150, 150));
		pulisciCampiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciCampiButton.setBackground(Color.WHITE);
		pulisciCampiButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pulisciCampiButton.setAlignmentX(0.5f);
		pulisciCampiButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		pulisciCampiButton.setMargin(new Insets(2, 20, 2, 20));
		
		eliminaButton = new JButton("Elimina");
		eliminaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				eliminaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				eliminaButton.setBackground(Color.WHITE);
			}
		});
		eliminaButton.setPreferredSize(new Dimension(90, 30));
		eliminaButton.setMaximumSize(new Dimension(150, 150));
		eliminaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaButton.setBackground(Color.WHITE);
		eliminaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaButton.setMargin(new Insets(2, 20, 2, 20));
		eliminaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		eliminaButton.setAlignmentX(0.5f);
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare il progetto selezionato?");
				if(conferma == JOptionPane.YES_OPTION) {
					eliminaProgetto(controller);
				}
			}
		});
		
		inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
		inserisciPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					controller.apriInserisciPartecipantiProgetto(progettoSelezionato);
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
		
		confermaModificheButton = new JButton("Conferma Modifiche");
		confermaModificheButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				confermaModificheButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				confermaModificheButton.setBackground(Color.WHITE);
			}
		});
		confermaModificheButton.setPreferredSize(new Dimension(170, 30));
		confermaModificheButton.setMaximumSize(new Dimension(150, 150));
		confermaModificheButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confermaModificheButton.setBackground(Color.WHITE);
		confermaModificheButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confermaModificheButton.setMargin(new Insets(2, 20, 2, 20));
		confermaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		confermaModificheButton.setAlignmentX(0.5f);
		confermaModificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int conferma = JOptionPane.showConfirmDialog(null, "Vuoi Confermare le modifiche effettuate?");
				
				if(conferma == JOptionPane.YES_OPTION)
					if(campiObbligatoriVuoti())
						JOptionPane.showMessageDialog(null, "Alcuni campi obbligatori sono vuoti.\nDare un nome al progetto,\n selezionare una tipologia e\n assegnargli almeno un ambito.",
								"Campi Obbligatori Vuoti",
								JOptionPane.ERROR_MESSAGE);
					else
						aggiornaProgetto(controller);
				}
		});
		
		JButton creaNuovoButton = new JButton("Crea Nuovo");
		creaNuovoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaNuovoButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaNuovoButton.setBackground(Color.WHITE);
			}
		});
		comandiPanel.setLayout(new BorderLayout(0, 0));
		creaNuovoButton.setPreferredSize(new Dimension(130, 30));
		creaNuovoButton.setMaximumSize(new Dimension(150, 150));
		creaNuovoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaNuovoButton.setBackground(Color.WHITE);
		creaNuovoButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaNuovoButton.setMargin(new Insets(2, 20, 2, 20));
		creaNuovoButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		creaNuovoButton.setAlignmentX(0.5f);
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		creaNuovoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler creare un nuovo progetto?");
				if (conferma == JOptionPane.YES_OPTION)	
					if(campiObbligatoriVuoti()) {
						JOptionPane.showMessageDialog(null, "Alcuni campi obbligatori sono vuoti.\nDare un nome al progetto,\n selezionare una tipologia e\n assegnargli almeno un ambito.",
								"Campi Obbligatori Vuoti",
								JOptionPane.ERROR_MESSAGE);
					}
					else
						creaProgetto(controller);
				}
		});
		
		comandiPanel2.add(inserisciPartecipanteButton);
		comandiPanel2.add(confermaModificheButton);
		comandiPanel2.add(creaNuovoButton);
		comandiPanel2.add(eliminaButton);
		comandiPanel2.add(pulisciCampiButton);
		comandiPanel.add(comandiPanel2, BorderLayout.NORTH);
		
		JPanel filtriPanel = new JPanel();
		filtriPanel.setBorder(null);
		comandiPanel.add(filtriPanel, BorderLayout.SOUTH);
		
		JButton filtraButton = new JButton("Filtra");
		filtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltri(controller);
			}
		});
		filtraButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		filtraButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtraButton.setBackground(Color.WHITE);
		filtraButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
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
		
		cercaTextField = new JTextField();
		cercaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		cercaTextField.setColumns(10);
		
		JLabel filtroTipologiaLabel = new JLabel("Tipologia");
		filtroTipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		filtroTipologieComboBox = new JComboBox();
		inizializzaComboBoxFiltroTipologie(controller);
		filtroTipologieComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		filtroTipologieComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroTipologieComboBox.setBackground(Color.WHITE);
		filtroTipologieComboBox.setUI(new BasicComboBoxUI());
		filtroTipologieComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtroTipologieComboBox.setBackground(Color.WHITE);
		filtroTipologieComboBox.setSelectedItem(null);
		
		JLabel filtroAmbitoLabel = new JLabel("Ambito");
		filtroAmbitoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroAmbitoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		filtroAmbitiComboBox = new JComboBox();
		inizializzaComboBoxFiltroAmbiti(controller);
		filtroAmbitiComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		filtroAmbitiComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroAmbitiComboBox.setBackground(Color.WHITE);
		filtroAmbitiComboBox.setUI(new BasicComboBoxUI());
		filtroAmbitiComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtroAmbitiComboBox.setSelectedItem(null);
		
		filtroScadutoLabel = new JLabel("Scaduto");
		filtroScadutoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroScadutoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		filtroScadutoComboBox = new JComboBox(siNoOpzioni);
		filtroScadutoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		filtroScadutoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroScadutoComboBox.setBackground(Color.WHITE);
		filtroScadutoComboBox.setUI(new BasicComboBoxUI());
		filtroScadutoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtroScadutoComboBox.setSelectedItem(null);
		
		filtroTerminatoLabel = new JLabel("Terminato");
		filtroTerminatoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTerminatoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		filtroTerminatoComboBox = new JComboBox(siNoOpzioni);
		filtroTerminatoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		filtroTerminatoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroTerminatoComboBox.setBackground(Color.WHITE);
		filtroTerminatoComboBox.setUI(new BasicComboBoxUI());
		filtroTerminatoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtroTerminatoComboBox.setSelectedItem(null);
		
		JLabel resetFiltri = new JLabel("");
		resetFiltri.setHorizontalAlignment(SwingConstants.CENTER);
		resetFiltri.setIcon(new ImageIcon(GestioneProgettiDipendente.class.getResource("/icone/refresh.png")));
		resetFiltri.setFont(new Font("Consolas", Font.PLAIN, 13));
		resetFiltri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				filtroAmbitiComboBox.setSelectedItem(null);
				filtroTipologieComboBox.setSelectedItem(null);
				filtroTerminatoComboBox.setSelectedItem(null);
				filtroScadutoComboBox.setSelectedItem(null);
				aggiornaTabella(controller);
			}
			});
		
		GroupLayout gl_filtriPanel = new GroupLayout(filtriPanel);
		gl_filtriPanel.setHorizontalGroup(
			gl_filtriPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(resetFiltri, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(filtraButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cercaTextField, GroupLayout.PREFERRED_SIZE, 498, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(filtroTipologiaLabel, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(filtroTipologieComboBox, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(filtroAmbitoLabel, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(filtroAmbitiComboBox, 0, 129, Short.MAX_VALUE)
					.addGap(43)
					.addComponent(filtroScadutoLabel, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(filtroScadutoComboBox, 0, 45, Short.MAX_VALUE)
					.addGap(37)
					.addComponent(filtroTerminatoLabel, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(filtroTerminatoComboBox, 0, 44, Short.MAX_VALUE)
					.addGap(44))
		);
		gl_filtriPanel.setVerticalGroup(
			gl_filtriPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(17)
					.addComponent(filtraButton, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
					.addGap(18))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(cercaTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(17)
					.addComponent(resetFiltri, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
					.addGap(18))
				.addGroup(Alignment.TRAILING, gl_filtriPanel.createSequentialGroup()
					.addGap(19)
					.addComponent(filtroTerminatoComboBox, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
					.addGap(20))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(16)
					.addComponent(filtroScadutoLabel, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
					.addGap(16))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(filtroScadutoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(22)
					.addComponent(filtroTerminatoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(22))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(filtroAmbitiComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(15)
					.addComponent(filtroAmbitoLabel, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
					.addGap(16))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(filtroTipologieComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
				.addGroup(gl_filtriPanel.createSequentialGroup()
					.addGap(22)
					.addComponent(filtroTipologiaLabel)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		filtriPanel.setLayout(gl_filtriPanel);
		
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		JScrollPane meetingScrollPane = new JScrollPane();
		meetingScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		tipologiaProgettoLabel = new JLabel("Tipologia");
		tipologiaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		tipologiaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		scadenzaProgettoLabel = new JLabel("Scadenza");
		scadenzaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		scadenzaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		giornoScadenzaComboBox = new JComboBox();
		giornoScadenzaComboBox.setUI(new BasicComboBoxUI());
		giornoScadenzaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoScadenzaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoScadenzaComboBox.setBackground(Color.WHITE);
		giornoScadenzaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoScadenzaComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoScadenzaComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
		giornoScadenzaComboBox.setBounds(244, 235, 47, 22);
		infoPanel2.add(giornoScadenzaComboBox);
		
		meseScadenzaComboBox = new JComboBox();
		meseScadenzaComboBox.setUI(new BasicComboBoxUI());
		meseScadenzaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meseScadenzaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseScadenzaComboBox.setBackground(Color.WHITE);
		meseScadenzaComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseScadenzaComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
		meseScadenzaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseScadenzaComboBox.setBounds(301, 235, 47, 22);
		infoPanel2.add(meseScadenzaComboBox);
		
		annoScadenzaComboBox = new JComboBox();
		annoScadenzaComboBox.setUI(new BasicComboBoxUI());
		annoScadenzaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoScadenzaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoScadenzaComboBox.setBackground(Color.WHITE);
		annoScadenzaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoScadenzaComboBox.setBounds(358, 235, 62, 22);
		DefaultComboBoxModel annoModel = new DefaultComboBoxModel();
		annoScadenzaComboBox.setModel(annoModel);
		int annoAttuale = LocalDate.now().getYear();
		for(int i= annoAttuale;i<= annoAttuale + 20; i++)
			annoModel.addElement(i);
		infoPanel2.add(annoScadenzaComboBox);
		
		descrizioneProgettoLabel = new JLabel("Descrizione");
		descrizioneProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		descrizioneProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		dataTerminazioneLabel = new JLabel("Data Terminazione");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		giornoTerminazioneComboBox = new JComboBox();
		giornoTerminazioneComboBox.setEnabled(false);
		giornoTerminazioneComboBox.setUI(new BasicComboBoxUI());
		giornoTerminazioneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoTerminazioneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoTerminazioneComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));		
		giornoTerminazioneComboBox.setSelectedItem(null);
		giornoTerminazioneComboBox.setBackground(Color.WHITE);
		
		meseTerminazioneComboBox = new JComboBox();
		meseTerminazioneComboBox.setEnabled(false);
		meseTerminazioneComboBox.setUI(new BasicComboBoxUI());
		meseTerminazioneComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseTerminazioneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseTerminazioneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meseTerminazioneComboBox.setSelectedItem(null);
		meseTerminazioneComboBox.setBackground(Color.WHITE);
		
		annoTerminazioneComboBox = new JComboBox();
		annoTerminazioneComboBox.setEnabled(false);
		annoTerminazioneComboBox.setUI(new BasicComboBoxUI());
		
		DefaultComboBoxModel annoTerminazioneModel = new DefaultComboBoxModel();
		for(int i = annoAttuale; i <= annoAttuale + 50; i++)	
			annoTerminazioneModel.addElement(i);
		annoTerminazioneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoTerminazioneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoTerminazioneComboBox.setBackground(Color.WHITE);
		annoTerminazioneComboBox.setModel(annoTerminazioneModel);
		annoTerminazioneComboBox.setSelectedItem(null);
		
		JScrollPane partecipantiScrollPane = new JScrollPane();
		partecipantiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
	
		
		progettoTerminatoLabel = new JLabel("Progetto Terminato");
		progettoTerminatoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		progettoTerminatoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		progettoTerminatoCheckBox = new JCheckBox("");
		progettoTerminatoCheckBox.setFont(new Font("Consolas", Font.PLAIN, 14));
		progettoTerminatoCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(progettoTerminatoCheckBox.isSelected()){
					giornoTerminazioneComboBox.setEnabled(true);
					meseTerminazioneComboBox.setEnabled(true);	
					annoTerminazioneComboBox.setEnabled(true);
					
					giornoTerminazioneComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
					meseTerminazioneComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
					annoTerminazioneComboBox.setSelectedIndex(0);
				}
				else {	
					giornoTerminazioneComboBox.setSelectedItem(null);
					meseTerminazioneComboBox.setSelectedItem(null);
					annoTerminazioneComboBox.setSelectedItem(null);
					
					giornoTerminazioneComboBox.setEnabled(false);
					meseTerminazioneComboBox.setEnabled(false);
					annoTerminazioneComboBox.setEnabled(false);
				}				
			}
		});
		
		JScrollPane ambitiScrollPane = new JScrollPane();
		ambitiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		tipologiaComboBox = new JComboBox();
		inizializzaComboBoxTipologie(controller);
		tipologiaComboBox.setBackground(Color.WHITE);
		tipologiaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		tipologiaComboBox.setUI(new BasicComboBoxUI());
		tipologiaComboBox.setSelectedItem(null);
		
		nomeTextField = new JTextField();
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		nomeProgettoLabel = new JLabel("Nome");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		descrizioneProgettoScrollPane = new JScrollPane();
		descrizioneProgettoScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		descrizioneProgettoScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		descrizioneProgettoScrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(176)
							.addComponent(giornoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(meseTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(annoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(progettoTerminatoLabel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addComponent(tipologiaProgettoLabel)
								.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
										.addComponent(progettoTerminatoCheckBox)
										.addComponent(tipologiaComboBox, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(descrizioneProgettoScrollPane, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(nomeTextField, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)))
									.addGap(66)
									.addComponent(ambitiScrollPane, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
									.addGap(18))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(giornoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(meseScadenzaComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(annoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addGap(242)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(partecipantiScrollPane, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(meetingScrollPane, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)))
					.addGap(20))
		);
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(nomeTextField)
								.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(descrizioneProgettoScrollPane, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(7)
									.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(11)
									.addComponent(tipologiaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(progettoTerminatoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(14)
									.addComponent(progettoTerminatoCheckBox)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
									.addComponent(giornoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addComponent(meseTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addComponent(annoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(10)
									.addComponent(giornoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(10)
									.addComponent(meseScadenzaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(10)
									.addComponent(annoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE, false)
							.addComponent(meetingScrollPane, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
							.addComponent(ambitiScrollPane, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
							.addComponent(partecipantiScrollPane, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)))
					.addGap(11))
		);
		
		descrizioneTextArea = new JTextArea();
		descrizioneTextArea.setWrapStyleWord(true);
		descrizioneTextArea.setLineWrap(true);
		descrizioneTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		descrizioneTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		descrizioneProgettoScrollPane.setViewportView(descrizioneTextArea);
		
		ambitoProgettoLabel = new JLabel("Ambito/i");
		ambitiScrollPane.setColumnHeaderView(ambitoProgettoLabel);
		ambitoProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		ambitoProgettoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		ambitiList = new JList<AmbitoProgetto>();
		modelloListaAmbiti = new DefaultListModel<AmbitoProgetto>();		
		inizializzaListaAmbiti(controller);
		ambitiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		ambitiScrollPane.setViewportView(ambitiList);	
		
		JLabel lblNewLabel = new JLabel("Partecipanti");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		partecipantiScrollPane.setColumnHeaderView(lblNewLabel);
		
		modelloListaPartecipanti = new DefaultListModel<>();
		PartecipantiListRenderer partecipantiListRenderer=new PartecipantiListRenderer();
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		partecipantiList = new JList();
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		partecipantiList.setSelectionBackground(Color.WHITE);
		partecipantiList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		partecipantiList.setModel(modelloListaPartecipanti);
		partecipantiList.setCellRenderer(partecipantiListRenderer);
		partecipantiScrollPane.setViewportView(partecipantiList);
		
		meetingRelativiLabel = new JLabel("Meeting Relativi");
		meetingRelativiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingScrollPane.setColumnHeaderView(meetingRelativiLabel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		panel.setLayout(gl_panel);
		
		meetingRelativiList = new JList();
		modelloListaMeetingRelativi = new DefaultListModel();
		meetingRelativiList.setModel(modelloListaMeetingRelativi);
		MeetingListRenderer renderer = new MeetingListRenderer();
		meetingRelativiList.setFixedCellHeight(80);
		meetingRelativiList.setSelectionBackground(Color.WHITE);
		meetingRelativiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingRelativiList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		meetingRelativiList.setCellRenderer(renderer);
		meetingScrollPane.setViewportView(meetingRelativiList);
		
		gestioneProgettoLabel = new JLabel("Gestione Progetto");
		gestioneProgettoLabel.setIcon(new ImageIcon(GestioneProgettiDipendente.class.getResource("/Icone/progetto_64.png")));
		gestioneProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(gestioneProgettoLabel)
					.addContainerGap(907, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1271, Short.MAX_VALUE)
					.addGap(3))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(gestioneProgettoLabel)
					.addGap(11)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		modelloTabellaProgetti = new ProgettoTableModel();
		progettiTable = new JTable(modelloTabellaProgetti);
		inizializzaTabella(controller);
		progettiTable.setFont(new Font("Consolas", Font.PLAIN, 11));
		progettiTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		progettiTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettiTable.setBackground(Color.WHITE);
		progettiTable.setSelectionBackground(Color.LIGHT_GRAY);
		DefaultTableCellRenderer renderTabella = new DefaultTableCellRenderer();
        renderTabella.setHorizontalAlignment(SwingConstants.CENTER);
        renderTabella.setVerticalAlignment(SwingConstants.CENTER);
		progettiTable.getColumnModel().getColumn(0).setCellRenderer(renderTabella);
		progettiTable.getColumnModel().getColumn(1).setCellRenderer(renderTabella);
		progettiTable.getColumnModel().getColumn(2).setCellRenderer(renderTabella);
		progettiTable.getColumnModel().getColumn(3).setCellRenderer(renderTabella);
		progettiTable.getColumnModel().getColumn(4).setCellRenderer(renderTabella);
		progettiTable.getColumnModel().getColumn(5).setCellRenderer(renderTabella);
		progettiTable.getColumnModel().getColumn(0).setMinWidth(500);
		progettiTable.getColumnModel().getColumn(1).setMinWidth(400);
		progettiTable.getColumnModel().getColumn(2).setMinWidth(200);
		progettiTable.getColumnModel().getColumn(3).setMinWidth(150);
		progettiTable.getColumnModel().getColumn(4).setMinWidth(150);
		progettiTable.getColumnModel().getColumn(5).setMinWidth(145);
		sorterProgetti = new TableRowSorter<>(modelloTabellaProgetti);
		progettiTable.setRowSorter(sorterProgetti);
		DataComparator comparatorDate = new DataComparator();
		sorterProgetti.setComparator(3, comparatorDate);
		sorterProgetti.setComparator(4, comparatorDate);
		sorterProgetti.setComparator(5, comparatorDate);
		progettiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		progettiTable.getTableHeader().setReorderingAllowed(false);
		progettiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rigaSelezionata = progettiTable.getSelectedRow();	
				rigaSelezionata = progettiTable.convertRowIndexToModel(rigaSelezionata);
				progettoSelezionato = modelloTabellaProgetti.getSelected(rigaSelezionata);
				
				nomeTextField.setText(progettoSelezionato.getNomeProgetto());
				descrizioneTextArea.setText(progettoSelezionato.getDescrizioneProgetto());
				
				if(progettoSelezionato.getDataTerminazione() != null) {
						annoTerminazioneComboBox.setSelectedItem(progettoSelezionato.getDataTerminazione().getYear());
						meseTerminazioneComboBox.setSelectedIndex(progettoSelezionato.getDataTerminazione().getMonthOfYear()-1);
						giornoTerminazioneComboBox.setSelectedIndex(progettoSelezionato.getDataTerminazione().getDayOfMonth()-1);
						progettoTerminatoCheckBox.setSelected(true);
						annoTerminazioneComboBox.setEnabled(false);
						meseTerminazioneComboBox.setEnabled(false);
						giornoTerminazioneComboBox.setEnabled(false);
					}
					else {
						annoTerminazioneComboBox.setSelectedItem(null);
						meseTerminazioneComboBox.setSelectedItem(null);
						giornoTerminazioneComboBox.setSelectedItem(null);
						progettoTerminatoCheckBox.setSelected(false);
					}
				
				if (progettoSelezionato.getScadenza() != null) {
					annoScadenzaComboBox.setSelectedItem(progettoSelezionato.getScadenza().getYear());
					meseScadenzaComboBox.setSelectedIndex(progettoSelezionato.getScadenza().getMonthOfYear()-1);
					giornoScadenzaComboBox.setSelectedIndex(progettoSelezionato.getScadenza().getDayOfMonth()-1);
				}
				else {
					annoScadenzaComboBox.setSelectedItem(null);
					meseScadenzaComboBox.setSelectedItem(null);
					giornoScadenzaComboBox.setSelectedItem(null);
				}
					
				int[] ambitiSelezionati = new int[progettoSelezionato.getAmbiti().size()];
				for (int i = 0; i < ambitiSelezionati.length; i++) {
					ambitiSelezionati[i] = modelloListaAmbiti.indexOf(progettoSelezionato.getAmbiti().get(i));
				}
				ambitiList.setSelectedIndices(ambitiSelezionati);
				
				tipologiaComboBox.setSelectedItem(progettoSelezionato.getTipoProgetto());
				
				modelloListaPartecipanti.clear();
				modelloListaPartecipanti.addAll(progettoSelezionato.getCollaborazioni());
				partecipantiList.setModel(modelloListaPartecipanti);
				
				modelloListaMeetingRelativi.clear();
				modelloListaMeetingRelativi.addAll(progettoSelezionato.getMeetingsRelativi());
				meetingRelativiList.setModel(modelloListaMeetingRelativi);
				
				checkProjectManager(controller);
			}
		});
		tabellaScrollPane.setViewportView(progettiTable);
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	private void inizializzaTabella(ControllerProgetto controller) {
		try {
			modelloTabellaProgetti.setProgettiTabella(controller.ottieniProgetti());
			modelloTabellaProgetti.fireTableDataChanged();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void inizializzaComboBoxTipologie(ControllerProgetto controller) {
		try {
			modelloComboBoxTipologie = new DefaultComboBoxModel();
			modelloComboBoxTipologie.addAll(controller.ottieniTipologie());
			tipologiaComboBox.setModel(modelloComboBoxTipologie);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void inizializzaListaAmbiti(ControllerProgetto controller) {
		try {
			modelloListaAmbiti.addAll(controller.ottieniAmbiti());
			ambitiList.setModel(modelloListaAmbiti);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void checkProjectManager(ControllerProgetto controller) {
		try {
			if (controller.isProjectManager(progettoSelezionato)) {
				inserisciPartecipanteButton.setEnabled(true);
				eliminaButton.setEnabled(true);
				confermaModificheButton.setEnabled(true);	
			}
			else {
				inserisciPartecipanteButton.setEnabled(false);
				eliminaButton.setEnabled(false);
				confermaModificheButton.setEnabled(false);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean campiObbligatoriVuoti() {
		if (nomeTextField.getText().isBlank() || tipologiaComboBox.getSelectedItem()== null || ambitiList.isSelectionEmpty()) {
			if(nomeTextField.getText().isBlank())					
				nomeProgettoLabel.setForeground(Color.RED);
			else if(!nomeTextField.getText().isBlank())
				nomeProgettoLabel.setForeground(Color.BLACK);
			
			if(tipologiaComboBox.getSelectedItem()== null)
				tipologiaProgettoLabel.setForeground(Color.RED);
			else if(tipologiaComboBox.getSelectedItem()!= null)
				tipologiaProgettoLabel.setForeground(Color.BLACK);
			
			if(ambitiList.isSelectionEmpty())
				ambitoProgettoLabel.setForeground(Color.RED);
			else if(!ambitiList.isSelectionEmpty())
				ambitoProgettoLabel.setForeground(Color.BLACK);
			
			return true;
		}
		return false;
	}
	
	private void creaProgetto(ControllerProgetto controller) {
		try {
			ricavaInfoProgetto();
			dataCreazione = dataAttuale;
			
			Progetto nuovoProgetto = new Progetto(-1,nomeProgetto, tipologia, descrizioneProgetto, dataCreazione, dataScadenza, dataTerminazione);
			nuovoProgetto.setAmbiti(ambiti);
			
			if (controller.creaProgetto(nuovoProgetto)) {
				JOptionPane.showMessageDialog(null, "Progetto creato con successo");
				aggiornaTabella(controller);
				
				tipologiaProgettoLabel.setForeground(Color.BLACK);
				ambitoProgettoLabel.setForeground(Color.BLACK);
				nomeProgettoLabel.setForeground(Color.BLACK);
			}
		} catch(IllegalFieldValueException nfve) {
			JOptionPane.showMessageDialog(null, 
					"Inserire una data valida.",
					"Data Non Valida",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void aggiornaProgetto(ControllerProgetto controller) {
		try {
			ricavaInfoProgetto();
			dataCreazione = progettoSelezionato.getDataCreazione();
			
			if (dataScadenza != null)
				progettoSelezionato.setScadenza(dataScadenza);
			if (dataTerminazione != null)
				progettoSelezionato.setDataTerminazione(dataTerminazione);
			progettoSelezionato.setNomeProgetto(nomeProgetto);
			progettoSelezionato.setDescrizioneProgetto(descrizioneProgetto);
			progettoSelezionato.setAmbiti(ambiti);
			
			if (controller.aggiornaProgetto(progettoSelezionato)) {
				JOptionPane.showMessageDialog(null, "Modifiche effettuate correttamente");
				aggiornaTabella(controller);							
				tipologiaProgettoLabel.setForeground(Color.BLACK);
				ambitoProgettoLabel.setForeground(Color.BLACK);
				nomeProgettoLabel.setForeground(Color.BLACK);
			}
		} catch (IllegalFieldValueException e) {
			JOptionPane.showMessageDialog(null, 
					"Inserire una data valida.",
					"Data Non Valida",
					JOptionPane.ERROR_MESSAGE);
			}
	}
	
	private void eliminaProgetto(ControllerProgetto controller) {
		if (controller.rimuoviProgetto(progettoSelezionato)) {	
			JOptionPane.showMessageDialog(null, "Progetto Eliminato con successo");
			aggiornaTabella(controller);
			pulisciCampi();
		}
	}
	
	private void ricavaInfoProgetto() throws IllegalFieldValueException{
		nomeProgetto = nomeTextField.getText();
		
		if (!descrizioneTextArea.getText().isBlank())
			descrizioneProgetto = descrizioneTextArea.getText();	
		
		if (annoScadenzaComboBox.getSelectedItem() != null && meseScadenzaComboBox.getSelectedItem() != null && giornoScadenzaComboBox.getSelectedItem() != null)
			dataScadenza = new LocalDate((int)annoScadenzaComboBox.getSelectedItem(), meseScadenzaComboBox.getSelectedIndex()+1 , giornoScadenzaComboBox.getSelectedIndex()+1);
		
		if(!progettoTerminatoCheckBox.isSelected())
			dataTerminazione = null;
		
		if(annoTerminazioneComboBox.getSelectedItem()!=null && meseTerminazioneComboBox.getSelectedItem()!=null && giornoTerminazioneComboBox.getSelectedItem()!=null)
			dataTerminazione = new LocalDate((int)annoTerminazioneComboBox.getSelectedItem(), meseTerminazioneComboBox.getSelectedIndex()+1 , giornoTerminazioneComboBox.getSelectedIndex()+1);
		
		tipologia = tipologiaComboBox.getSelectedItem().toString();
		
		ambiti.clear();
		int[] ambitiSelezionati = ambitiList.getSelectedIndices();			
		for(int i : ambitiSelezionati)
			ambiti.add(modelloListaAmbiti.getElementAt(i));
	}
	
	private void aggiornaTabella(ControllerProgetto controller) {
		try {
			modelloTabellaProgetti.setProgettiTabella(controller.ottieniProgetti());
			modelloTabellaProgetti.fireTableDataChanged();
			pulisciCampi();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void pulisciCampi() {
		nomeProgettoLabel.setForeground(Color.BLACK);
		descrizioneProgettoLabel.setForeground(Color.BLACK);
		tipologiaProgettoLabel.setForeground(Color.BLACK);
		ambitoProgettoLabel.setForeground(Color.BLACK);
		progettoTerminatoLabel.setForeground(Color.BLACK);
		dataTerminazioneLabel.setForeground(Color.BLACK);

		nomeTextField.setText("");
		descrizioneTextArea.setText("");	
		progettoTerminatoCheckBox.setSelected(false);
		ambitiList.clearSelection();
		annoTerminazioneComboBox.setSelectedItem(null);
		meseTerminazioneComboBox.setSelectedItem(null);
		giornoTerminazioneComboBox.setSelectedItem(null);
		giornoScadenzaComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
		meseScadenzaComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);				
		annoScadenzaComboBox.setSelectedIndex(1); 
		tipologiaComboBox.setSelectedItem(null);
		if (modelloListaPartecipanti.isEmpty())
			modelloListaPartecipanti.clear();
		if(!modelloListaMeetingRelativi.isEmpty())
			modelloListaMeetingRelativi.clear();
		
		progettiTable.clearSelection();
	}
	
	private void inizializzaComboBoxFiltroTipologie(ControllerProgetto controller) {
		try {
			modelloComboBoxFiltroTipologie = new DefaultComboBoxModel();
			modelloComboBoxFiltroTipologie.addAll(controller.ottieniTipologie());
			filtroTipologieComboBox.setModel(modelloComboBoxFiltroTipologie);;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void inizializzaComboBoxFiltroAmbiti(ControllerProgetto controller) {
		try {
			modelloComboBoxFiltroAmbiti = new DefaultComboBoxModel<AmbitoProgetto>();
			modelloComboBoxFiltroAmbiti.addAll(controller.ottieniAmbiti());
			filtroAmbitiComboBox.setModel(modelloComboBoxFiltroAmbiti);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void applicaFiltri(ControllerProgetto controller) {
		String nomeCercato = "%";
		if (!cercaTextField.getText().isBlank())
			nomeCercato = cercaTextField.getText();
		AmbitoProgetto ambitoCercato = null;
		if (filtroAmbitiComboBox.getSelectedItem() != null)
			ambitoCercato= (AmbitoProgetto) filtroAmbitiComboBox.getSelectedItem();
		String tipologiaCercata = null;
		if (filtroTipologieComboBox.getSelectedItem() != null)
			tipologiaCercata =  filtroTipologieComboBox.getSelectedItem().toString();
		String scaduto = "Entrambi";
		if (filtroScadutoComboBox.getSelectedItem() != null)
			if (filtroScadutoComboBox.getSelectedItem().toString().equals("Si"))
				scaduto = "Si";
			else
				scaduto = "No";
		String terminato = "Entrambi";
		if (filtroTerminatoComboBox.getSelectedItem() != null)
			if (filtroTerminatoComboBox.getSelectedItem().toString().equals("Si"))
				terminato = "Si";
			else
				terminato = "No";
		try {
			modelloTabellaProgetti.setProgettiTabella(controller.ottieniProgettiFiltrati(nomeCercato,ambitoCercato,tipologiaCercata, scaduto, terminato));
			modelloTabellaProgetti.fireTableDataChanged();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
}
