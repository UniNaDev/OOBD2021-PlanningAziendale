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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import controller.dipendente.ControllerMeeting;
import controller.dipendente.ControllerPartecipantiMeeting;
import controller.dipendente.ControllerPartecipantiProgetto;
import controller.segreteria.ControllerDipendentiSegreteria;
import entita.Dipendente;
import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.Progetto;
import entita.SalaRiunione;
import entita.Skill;
import gui.cellRenderers.PartecipantiListRenderer;
import gui.customUI.CustomScrollBarUI;
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
import entita.CollaborazioneProgetto;

import javax.swing.JSeparator;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JSplitPane;



public class InserisciPartecipantiProgetto extends JFrame {

	//ATTRIBUTI
	//---------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JLabel gestionePartecipantiProgettoLabel;
	private JLabel infoDipendenteLabel;
	private JLabel nomeLabel;
	private JTextField nomeTextField;
	private JLabel cognomeLabel;
	private JTextField cognomeTextField;
	private JLabel sessoLabel;
	private final ButtonGroup modalitàButtonGroup = new ButtonGroup();
	private JRadioButton uomoRadioButton;
	private JRadioButton donnaRadioButton;
	private JLabel etàLabel;
	private JTextField etàTextField;
	private JLabel valutazioneLabel;
	private JTextField valutazioneTextField;
	private JLabel salarioLabel;
	private JTextField salarioTextField;
	private JLabel ruoloLabel;
	private JComboBox ruoloComboBox;
	private JList skillList;
	private JLabel skillLabel;
	private DefaultListModel listaSkillModel;
	private JScrollPane skillScrollPane;
	
	private JSeparator infoDipendenteProgettoSeparator;
	
	private JLabel infoProgettoLabel;
	private JLabel nomeProgettoLabel;
	private JTextArea nomeProgettotextArea;
	private JLabel ambitiProgettoLabel;
	private JTextArea ambitiTextArea;
	private JLabel tipologiaProgettoLabel;
	private JTextField tipologiaProgettoTextField;
	private JList partecipantiList;
	private JLabel partecipantiLabel;
	private JScrollPane partecipantiScrollPane;
	private PartecipantiListRenderer partecipantiListRenderer;
	private DefaultListModel partecipantiListModel;
	
	private JButton aggiornaRuoloButton;
	private JButton inserisciPartecipanteButton;
	private JButton eliminaPartecipanteButton;
	
	private JButton filtraButton;
	private JTextField cercaTextField;
	private JSeparator ricercaEtàSeparator;
	private JTextField etàMinimaTextField;
	private JLabel etàFiltroLabel;
	private JTextField etàMassimaTextField;
	private JSeparator etàSalarioSeparator;
	private JTextField salarioMinimoTextField;
	private JLabel salarioFiltroLabel;
	private JTextField salarioMassimoTextField;
	private JSeparator salarioValutazioneSeparator;
	private JTextField valutazioneMinimaTextField;
	private JLabel valutazioneFiltroLabel;
	private JTextField valutazioneMassimaTextField;
	private JSeparator valutazioneSkillSeparator;
	private JLabel filtroSkillLabel;
	private JComboBox skillFiltroComboBox;
	private JSeparator skillTipologiaProgettoSeparator;
	private JLabel tipologiaProgettoFiltroLabel;
	private DefaultComboBoxModel tipologiaProgettoModel;
	private JComboBox tipologiaProgettoComboBox;
	

	
	
	private JTable progettoTable;
	private JTable dipendenteTable;

	private PartecipantiTableModel dataModelDipendente;





	private TableRowSorter<TableModel> sorterDipendente;

	private Progetto progettoSelezionato;

	//Creazione frame
	//---------------------------------------------
	public InserisciPartecipantiProgetto(ControllerPartecipantiProgetto controller, Progetto progettoSelezionato) {
		this.progettoSelezionato=progettoSelezionato;
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1600, 900));
		setTitle("Inserisci Partecipanti Progetto");
		setBounds(100, 100, 1600, 900);
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
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane DipendenteScrollPane = new JScrollPane();	//scroll pane per la tabella meeting
		DipendenteScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		DipendenteScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DipendenteScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		gestionePartecipantiProgettoLabel = new JLabel("Gestione partecipanti progetto");
		gestionePartecipantiProgettoLabel.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/Icone/meeting_64.png")));
		gestionePartecipantiProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
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
		DefaultComboBoxModel myModel2 = new DefaultComboBoxModel();
		for(int i=1900;i<= 2021;i++)
			myModel2.addElement(i);
		
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
		
		ruoloLabel = new JLabel("Ruolo");
		ruoloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ruoloLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		try {
			ruoloComboBox = new JComboBox(controller.ottieniRuoli());
			ruoloComboBox.setUI(new BasicComboBoxUI());
			ruoloComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			ruoloComboBox.setBackground(Color.WHITE);
			ruoloComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			ruoloComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
			ruoloComboBox.setSelectedItem(null);
		} catch (SQLException e4) {
			ruoloComboBox.setSelectedItem(null);
			e4.printStackTrace();
		}
		
		skillScrollPane = new JScrollPane();
		skillScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		skillScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		skillScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		listaSkillModel = new DefaultListModel<>();
		skillList = new JList();
		skillList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		skillList.setSelectionBackground(Color.WHITE);
		skillList.setFont(new Font("Consolas", Font.PLAIN, 12));
		skillList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		skillList.setModel(listaSkillModel);
		skillScrollPane.setViewportView(skillList);
		
		skillLabel = new JLabel("Skill");
		skillLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		skillScrollPane.setColumnHeaderView(skillLabel);
		
		infoDipendenteProgettoSeparator = new JSeparator();
		infoDipendenteProgettoSeparator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		infoDipendenteProgettoSeparator.setOrientation(SwingConstants.VERTICAL);
		
		infoProgettoLabel = new JLabel("Info progetto");
		infoProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		nomeProgettoLabel = new JLabel("Nome");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		nomeProgettotextArea = new JTextArea(progettoSelezionato.getNomeProgetto());
		nomeProgettotextArea.setEditable(false);
		nomeProgettotextArea.setWrapStyleWord(true);
		nomeProgettotextArea.setFont(new Font("Consolas", Font.PLAIN, 11));
		nomeProgettotextArea.setLineWrap(true);
		nomeProgettotextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		tipologiaProgettoLabel = new JLabel("Tipologia");
		tipologiaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tipologiaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		tipologiaProgettoTextField = new JTextField(progettoSelezionato.getTipoProgetto());
		tipologiaProgettoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		tipologiaProgettoTextField.setEditable(false);
		tipologiaProgettoTextField.setColumns(10);
		tipologiaProgettoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		ambitiProgettoLabel = new JLabel("Ambito/i");
		ambitiProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ambitiProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		ambitiTextArea = new JTextArea(progettoSelezionato.getAmbiti().toString());
		ambitiTextArea.setWrapStyleWord(true);
		ambitiTextArea.setEditable(false);
		ambitiTextArea.setFont(new Font("Consolas", Font.PLAIN, 11));
		ambitiTextArea.setLineWrap(true);
		ambitiTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		partecipantiScrollPane = new JScrollPane();
		partecipantiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		partecipantiList = new JList();
		partecipantiListRenderer=new PartecipantiListRenderer();
		partecipantiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		partecipantiList.setCellRenderer(partecipantiListRenderer);
		partecipantiListModel=new DefaultListModel();
		partecipantiList.setModel(partecipantiListModel);
		partecipantiListModel.addAll(progettoSelezionato.getCollaborazioni());
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		partecipantiScrollPane.setViewportView(partecipantiList);
		partecipantiList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				CollaborazioneProgetto collaborazione=(CollaborazioneProgetto) partecipantiList.getSelectedValue();
				
				if(collaborazione!=null) {
					
					ruoloComboBox.setSelectedItem(collaborazione.getRuoloCollaboratore());
					nomeTextField.setText(collaborazione.getCollaboratore().getNome());
					cognomeTextField.setText(collaborazione.getCollaboratore().getCognome());
					etàTextField.setText(String.valueOf(collaborazione.getCollaboratore().getEtà()));
					valutazioneTextField.setText(String.format("%.2f", collaborazione.getCollaboratore().getValutazione()));
					salarioTextField.setText(String.format("%.2f", collaborazione.getCollaboratore().getSalario()));
					if(collaborazione.getCollaboratore().getSesso()=='M')
						uomoRadioButton.setSelected(true);
					else 
						donnaRadioButton.setSelected(true);
					
					skillList.setModel(listaSkillModel);
					listaSkillModel.removeAllElements();
					try {
						listaSkillModel.addAll(controller.ottieniSkillDipendente(collaborazione.getCollaboratore().getCf()));
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					dipendenteTable.clearSelection();
				}
				else 
					ruoloComboBox.setSelectedItem(null);
				
			}
		});
	
		partecipantiLabel = new JLabel("Partecipanti");
		partecipantiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		partecipantiScrollPane.setColumnHeaderView(partecipantiLabel);
		
		JPanel comandiPanel2 = new JPanel();
		comandiPanel2.setBorder(null);
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//panel interno a quello delle info
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		

		aggiornaRuoloButton = new JButton("Aggiorna ruolo");
		aggiornaRuoloButton.setPreferredSize(new Dimension(150, 30));
		aggiornaRuoloButton.setMaximumSize(new Dimension(150, 150));
		aggiornaRuoloButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aggiornaRuoloButton.setMargin(new Insets(2, 20, 2, 20));
		aggiornaRuoloButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		aggiornaRuoloButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		aggiornaRuoloButton.setBackground(Color.WHITE);
		aggiornaRuoloButton.setAlignmentX(0.5f);
		aggiornaRuoloButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				aggiornaRuoloButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				aggiornaRuoloButton.setBackground(Color.WHITE);
			}
		});
		aggiornaRuoloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(partecipantiList.getSelectedValue()==null) {
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla lista");
					partecipantiLabel.setForeground(Color.RED);
				}
				else
				{
					partecipantiLabel.setForeground(Color.BLACK);
					String nuovoRuolo=ruoloComboBox.getSelectedItem().toString();
					
					//Vecchia collaborazione
					CollaborazioneProgetto collaborazione=(CollaborazioneProgetto) partecipantiList.getSelectedValue();
					
					//Nuova collaborazione
					CollaborazioneProgetto collaborazioneProgetto=new CollaborazioneProgetto(progettoSelezionato, collaborazione.getCollaboratore(), nuovoRuolo);
					
					try {
						controller.aggiornaPartecipante(collaborazioneProgetto);
						JOptionPane.showMessageDialog(null, "Modifica effettuata con successo");
						partecipantiListModel.removeElementAt(partecipantiList.getSelectedIndex()); //rimuove il vecchio elemento
						partecipantiListModel.addElement(collaborazioneProgetto); //lo aggiorna con il nuovo
						
						//Aggiorna il modello del sorterDipendente in seguito alle modifiche
						sorterDipendente.setModel(dataModelDipendente);
						
						//Svuota i campi
						svuotaCampi();
					} catch (SQLException e1) {
					
						ruoloComboBox.setSelectedItem(null);
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					
				
				}
				
			}
		});
		comandiPanel2.add(aggiornaRuoloButton);
		
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
				
				if(dipendenteTable.getSelectedRow()==-1) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante dalla tabella");
					
				}
				if(ruoloComboBox.getSelectedItem()==null && dipendenteTable.getSelectedRow()!=-1) {
					JOptionPane.showMessageDialog(null, "Seleziona un ruolo");
					
				}
				else {
					
					
					int row= dipendenteTable.getSelectedRow();
					try {
						
						//Riceve il ruolo selezionato nella combobox
						String ruolo=ruoloComboBox.getSelectedItem().toString();
						
						//Seleziona il dipendente selezionato dalla tabella
						Dipendente dipendente=dataModelDipendente.getSelected(dipendenteTable.convertRowIndexToModel(dipendenteTable.getSelectedRow()));
						
						//Crea la collaborazione al progetto
						CollaborazioneProgetto collaborazione=new CollaborazioneProgetto(progettoSelezionato, dipendente, ruolo);
						
						//Inserisce il partecipante al progetto
						controller.inserisciPartecipante(collaborazione);
						JOptionPane.showMessageDialog(null, "Dipendente inserito correttamente");
								
						
						//Aggiorna i dipendenti disponibili
						dataModelDipendente.fireTableDataChanged();
						dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(progettoSelezionato));
						
						//Aggiorna il modello del sorterDipendente in seguito all'inserimento
						sorterDipendente.setModel(dataModelDipendente);
						
						//Aggiunge l'elemento inserito alla lista
						partecipantiListModel.addElement(collaborazione);
						
						//Svuota i campi
						svuotaCampi();
					
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
		comandiPanel2.add(inserisciPartecipanteButton);
		

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
				
				if(partecipantiList.isSelectionEmpty()) {
					
					JOptionPane.showMessageDialog(null, "Seleziona un partecipante da eliminare");
					partecipantiLabel.setForeground(Color.RED);
					
				}
				else {
					
					try {
						partecipantiLabel.setForeground(Color.BLACK);
						
						controller.eliminaPartecipante((CollaborazioneProgetto)partecipantiList.getSelectedValue());
						JOptionPane.showMessageDialog(null, "Partecipante eliminato");
				
						//Rimuove dalla lista l'elemento eliminato
						partecipantiListModel.removeElementAt(partecipantiList.getSelectedIndex());
						
						//Pone il valore selezionato a null
						partecipantiList.setSelectedValue(null,false);
						
						//Aggiorna i dipendenti disponibili
						dataModelDipendente.fireTableDataChanged();
						dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(progettoSelezionato));
						
						//Aggiorna il modello del sorterDipendente in seguito all'eliminazione
						sorterDipendente.setModel(dataModelDipendente);
						
						//Svuota i campi
						svuotaCampi();
						
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
				}
					
				}
				
			
		});
		comandiPanel2.add(eliminaPartecipanteButton);
		
		filtraButton = new JButton("Filtra");
		filtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltri(controller);
			}
		});
		filtraButton.setToolTipText("Clicca per applicare i filtri");
		filtraButton.setFont(new Font("Consolas", Font.PLAIN, 16));
		filtraButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtraButton.setBackground(Color.WHITE);
	
		JPanel comandiPanel_1 = new JPanel();
		comandiPanel_1.setBorder(null);
		
		cercaTextField = new JTextField();
		cercaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		cercaTextField.setColumns(20);
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		etàMinimaTextField = new JTextField();
		etàMinimaTextField.setText("min");
		etàMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		etàMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàMinimaTextField.setColumns(10);
		etàMinimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		etàFiltroLabel = new JLabel("Età");
		etàFiltroLabel.setHorizontalAlignment(SwingConstants.CENTER);
		etàFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		etàMassimaTextField = new JTextField();
		etàMassimaTextField.setText("max");
		etàMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
		etàMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		etàMassimaTextField.setColumns(10);
		etàMassimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		salarioMinimoTextField = new JTextField();
		salarioMinimoTextField.setText("min");
		salarioMinimoTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		salarioMinimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioMinimoTextField.setColumns(10);
		salarioMinimoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		salarioFiltroLabel = new JLabel("Salario");
		salarioFiltroLabel.setHorizontalAlignment(SwingConstants.CENTER);
		salarioFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		salarioMassimoTextField = new JTextField();
		salarioMassimoTextField.setText("max");
		salarioMassimoTextField.setHorizontalAlignment(SwingConstants.LEFT);
		salarioMassimoTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		salarioMassimoTextField.setColumns(10);
		salarioMassimoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		valutazioneMinimaTextField = new JTextField();
		valutazioneMinimaTextField.setText("min");
		valutazioneMinimaTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		valutazioneMinimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneMinimaTextField.setColumns(10);
		valutazioneMinimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		valutazioneFiltroLabel = new JLabel("Valutazione");
		valutazioneFiltroLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valutazioneFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		valutazioneMassimaTextField = new JTextField();
		valutazioneMassimaTextField.setText("max");
		valutazioneMassimaTextField.setHorizontalAlignment(SwingConstants.LEFT);
		valutazioneMassimaTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		valutazioneMassimaTextField.setColumns(10);
		valutazioneMassimaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		filtroSkillLabel = new JLabel("Skill:");
		filtroSkillLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroSkillLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		skillFiltroComboBox=new JComboBox();
		try {
			DefaultComboBoxModel skillModel=new DefaultComboBoxModel();
			skillModel.addElement(null);
			skillModel.addAll(controller.ottieniSkill());
			skillFiltroComboBox.setModel(skillModel);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		skillFiltroComboBox.setUI(new BasicComboBoxUI());
		skillFiltroComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		skillFiltroComboBox.setBackground(Color.WHITE);
		skillFiltroComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		skillFiltroComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		skillFiltroComboBox.setSelectedItem(null);
		
		tipologiaProgettoFiltroLabel = new JLabel("Tipologia Progetto:");
		tipologiaProgettoFiltroLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		tipologiaProgettoComboBox = new JComboBox();
		tipologiaProgettoModel=new DefaultComboBoxModel<>();
		tipologiaProgettoComboBox.setModel(tipologiaProgettoModel);
		tipologiaProgettoComboBox.setUI(new BasicComboBoxUI());
		tipologiaProgettoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaProgettoComboBox.setBackground(Color.WHITE);
		tipologiaProgettoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tipologiaProgettoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		tipologiaProgettoComboBox.setSelectedItem(null);
		tipologiaProgettoModel.addElement(null);
		try {
			tipologiaProgettoModel.addAll(controller.ottieniTipologieProgetto());
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		ricercaEtàSeparator = new JSeparator();
		ricercaEtàSeparator.setPreferredSize(new Dimension(1, 30));
		ricercaEtàSeparator.setOrientation(SwingConstants.VERTICAL);
		
		etàSalarioSeparator = new JSeparator();
		etàSalarioSeparator.setPreferredSize(new Dimension(1, 30));
		etàSalarioSeparator.setOrientation(SwingConstants.VERTICAL);
		
		salarioValutazioneSeparator = new JSeparator();
		salarioValutazioneSeparator.setPreferredSize(new Dimension(1, 30));
		salarioValutazioneSeparator.setOrientation(SwingConstants.VERTICAL);
		
		valutazioneSkillSeparator = new JSeparator();
		valutazioneSkillSeparator.setPreferredSize(new Dimension(1, 30));
		valutazioneSkillSeparator.setOrientation(SwingConstants.VERTICAL);
		
		skillTipologiaProgettoSeparator = new JSeparator();
		skillTipologiaProgettoSeparator.setPreferredSize(new Dimension(1, 30));
		skillTipologiaProgettoSeparator.setOrientation(SwingConstants.VERTICAL);
		
		comandiPanel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		comandiPanel_1.add(filtraButton);
		comandiPanel_1.add(cercaTextField);
		comandiPanel_1.add(ricercaEtàSeparator);
		comandiPanel_1.add(etàMinimaTextField);
		comandiPanel_1.add(etàFiltroLabel);
		comandiPanel_1.add(etàMassimaTextField);
		comandiPanel_1.add(etàSalarioSeparator);
		comandiPanel_1.add(salarioMinimoTextField);
		comandiPanel_1.add(salarioFiltroLabel);
		comandiPanel_1.add(salarioMassimoTextField);
		comandiPanel_1.add(salarioValutazioneSeparator);
		comandiPanel_1.add(valutazioneMinimaTextField);
		comandiPanel_1.add(valutazioneFiltroLabel);
		comandiPanel_1.add(valutazioneMassimaTextField);
		comandiPanel_1.add(valutazioneSkillSeparator);
		comandiPanel_1.add(filtroSkillLabel);
		comandiPanel_1.add(skillFiltroComboBox);
		comandiPanel_1.add(skillTipologiaProgettoSeparator);
		comandiPanel_1.add(tipologiaProgettoFiltroLabel);
		comandiPanel_1.add(tipologiaProgettoComboBox);
		
		dataModelDipendente=new PartecipantiTableModel();
		
		dipendenteTable = new JTable(dataModelDipendente);
		dipendenteTable.setFont(new Font("Consolas", Font.PLAIN, 11));
		dipendenteTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dipendenteTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteTable.setBackground(Color.WHITE);
		dipendenteTable.setSelectionBackground(Color.LIGHT_GRAY);
		
		//Modello delle colonne personalizzato
		dipendenteTable.getColumnModel().getColumn(0).setMinWidth(150);
		dipendenteTable.getColumnModel().getColumn(1).setMinWidth(150);
		dipendenteTable.getColumnModel().getColumn(2).setMinWidth(50);
		dipendenteTable.getColumnModel().getColumn(3).setMinWidth(50);
		dipendenteTable.getColumnModel().getColumn(4).setMinWidth(300);
		dipendenteTable.getColumnModel().getColumn(5).setMinWidth(100);
		dipendenteTable.getColumnModel().getColumn(6).setMinWidth(100);
		dipendenteTable.getColumnModel().getColumn(7).setMinWidth(300);
		dipendenteTable.getColumnModel().getColumn(8).setMinWidth(300);
		
		//Modello delle colonne personalizzato(Testo allineato al centro)
		DefaultTableCellRenderer renderTabella = new DefaultTableCellRenderer();
        renderTabella.setHorizontalAlignment(SwingConstants.CENTER);
        renderTabella.setVerticalAlignment(SwingConstants.CENTER);
        
        dipendenteTable.getColumnModel().getColumn(0).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(1).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(2).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(3).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(4).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(5).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(6).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(7).setCellRenderer(renderTabella);
        dipendenteTable.getColumnModel().getColumn(8).setCellRenderer(renderTabella);
		
		//setta il modello di dati della tabella
		try {
			dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(progettoSelezionato));	
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
				
				
				//Quando viene selezionata una riga della tabella,deseleziona elemento selezionato della lista
				partecipantiList.clearSelection();
				
				int row= dipendenteTable.getSelectedRow();	//ottiene l'indice di riga selezionata
				
				//Riceve il dipendente selezionato
				Dipendente dipendente=dataModelDipendente.getSelected(dipendenteTable.convertRowIndexToModel(row));
				
				
				//ricava le info del dipendente seleziona
				nomeTextField.setText(dipendente.getNome());
				cognomeTextField.setText(dipendente.getCognome());
				
				if(dipendente.getSesso()=='M') {
					
					uomoRadioButton.setSelected(true);
					donnaRadioButton.setSelected(false);
				}
				else
				{
					uomoRadioButton.setSelected(false);
					donnaRadioButton.setSelected(true);	
				}
				
				etàTextField.setText(String.valueOf(dipendente.getEtà()));
				salarioTextField.setText(String.valueOf(dipendente.getSalario()));
				valutazioneTextField.setText(String.format("%.2f",dipendente.getValutazione()));
				

				try {
					listaSkillModel.removeAllElements();
					listaSkillModel.addAll(controller.ottieniSkillDipendente(dipendente.getCf()));
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
					if(e.getClickCount()==2) {
					
						
						if(ruoloComboBox.getSelectedItem()==null && dipendenteTable.getSelectedRow()!=-1) {
							JOptionPane.showMessageDialog(null, "Seleziona un ruolo");
							
						}
						else {
							try {
								
								//riceve il ruolo selezionato nella combo box
								String ruolo=ruoloComboBox.getSelectedItem().toString();
								
								
								//Creo la collaborazione al progetto
								CollaborazioneProgetto collaborazione=new CollaborazioneProgetto(progettoSelezionato, dipendente, ruolo);
								
								//Inserisce il partecipante al progetto
								controller.inserisciPartecipante(collaborazione);
								JOptionPane.showMessageDialog(null, "Partecipante inserito correttamente");
								
								//Aggiorna la lista e la tabella
								partecipantiListModel.addElement(collaborazione);
								dataModelDipendente.fireTableDataChanged();
								dataModelDipendente.setDipendenteTabella(controller.ottieniDipendenti(progettoSelezionato));
							
								ruoloComboBox.setSelectedItem(null);
								
								//Aggiorna il modello del sorterDipendente in seguito all'inserimento
								sorterDipendente.setModel(dataModelDipendente);
								
								//Svuota i campi
								svuotaCampi();
				
							} catch (SQLException e1) {
								
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
							
						}
						
				}
			
			}
		});
		
		DipendenteScrollPane.setViewportView(dipendenteTable);
				
		
		GroupLayout gl_comandiPanel = new GroupLayout(comandiPanel);
		gl_comandiPanel.setHorizontalGroup(
			gl_comandiPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comandiPanel.createSequentialGroup()
					.addGap(303)
					.addComponent(comandiPanel2, GroupLayout.DEFAULT_SIZE, 931, Short.MAX_VALUE)
					.addGap(302))
				.addComponent(comandiPanel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1536, Short.MAX_VALUE)
		);
		gl_comandiPanel.setVerticalGroup(
			gl_comandiPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comandiPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(comandiPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comandiPanel_1, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
		);
		comandiPanel.setLayout(gl_comandiPanel);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comandiPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1536, Short.MAX_VALUE)
						.addComponent(DipendenteScrollPane, GroupLayout.DEFAULT_SIZE, 1536, Short.MAX_VALUE)
						.addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, 1536, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(DipendenteScrollPane, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(184)
							.addComponent(infoDipendenteLabel, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
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
								.addComponent(ruoloLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
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
					.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(7)
							.addComponent(infoDipendenteProgettoSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(46)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addComponent(nomeProgettoLabel)
											.addGap(14))
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addComponent(ambitiProgettoLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
											.addGap(18))))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tipologiaProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(nomeProgettotextArea, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
								.addComponent(ambitiTextArea, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
								.addComponent(tipologiaProgettoTextField, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
							.addGap(18)
							.addComponent(partecipantiScrollPane, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
							.addGap(30))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addComponent(infoProgettoLabel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addGap(235))))
		);
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
						.addComponent(infoDipendenteLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(infoProgettoLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(nomeProgettotextArea, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(tipologiaProgettoTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(ambitiTextArea, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
								.addComponent(ambitiProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
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
										.addComponent(ruoloLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
								.addComponent(partecipantiScrollPane, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
								.addComponent(skillScrollPane, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(67, Short.MAX_VALUE))
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addContainerGap(68, Short.MAX_VALUE)
					.addComponent(infoDipendenteProgettoSeparator, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addGap(65))
		);
		
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
					.addComponent(gestionePartecipantiProgettoLabel)
					.addContainerGap(1208, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestionePartecipantiProgettoLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
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
		
		listaSkillModel.removeAllElements();
		
		ruoloComboBox.setSelectedItem(null);
		
		partecipantiLabel.setBackground(Color.BLACK);
		
		
	}
	
	private void applicaFiltri(ControllerPartecipantiProgetto controller) {
		String nomeCognomeEmail = "%";
		if (!cercaTextField.getText().isBlank())
			nomeCognomeEmail = cercaTextField.getText();
		int etàMinima = 0;
		if (!etàMinimaTextField.getText().isBlank())
			etàMinima = parseInteger(etàMinimaTextField.getText(), etàMinima);
		int etàMassima = 100;
		if (!etàMassimaTextField.getText().isBlank())
			etàMassima = parseInteger(etàMassimaTextField.getText(), etàMassima);
		float salarioMinimo = 1;
		if (!salarioMinimoTextField.getText().isBlank() && !salarioMinimoTextField.getText().equals("min"))
			salarioMinimo=parseFloat(salarioMinimoTextField.getText(),salarioMinimo);
		float salarioMassimo = controller.ottieniMaxStipendio();
		if (!salarioMassimoTextField.getText().isBlank())
			salarioMassimo=parseFloat(salarioMassimoTextField.getText(),salarioMassimo);
		float valutazioneMinima = 0;
		if (!valutazioneMinimaTextField.getText().isBlank())
			valutazioneMinima=parseFloat(valutazioneMinimaTextField.getText(), valutazioneMinima);
		float valutazioneMassima = 10;
		if (!valutazioneMassimaTextField.getText().isBlank())
			valutazioneMassima=parseFloat(valutazioneMassimaTextField.getText(), valutazioneMassima);
		Skill skillCercata = null;
		skillCercata =(Skill) skillFiltroComboBox.getSelectedItem();
		String tipologiaProgetto=null;
		tipologiaProgetto=(String) tipologiaProgettoComboBox.getSelectedItem();
		
		try {
			dataModelDipendente.setDipendenteTabella(controller.filtraDipendentiNonPartecipanti(nomeCognomeEmail, etàMinima, etàMassima, salarioMinimo, salarioMassimo, valutazioneMinima, valutazioneMassima, skillCercata,progettoSelezionato,tipologiaProgetto));
			dipendenteTable.setModel(dataModelDipendente);
			dataModelDipendente.fireTableDataChanged();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
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
}
