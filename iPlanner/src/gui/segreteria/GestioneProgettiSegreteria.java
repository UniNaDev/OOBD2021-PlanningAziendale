/*Gui per la gestione deli progetti aziendali.
 *Qui è possibile visualizzare tutte le loro informazioni
 *e filtrare tra essi.
 *Qui è anche possibile creare nuovi ambiti per i progetti
 *in base alle nuove politiche aziendali.
 **********************************************************/

package gui.segreteria;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Meeting;
import entita.Progetto;
import gui.ErroreDialog;
import gui.cellRenderers.MeetingListRenderer;
import gui.cellRenderers.PartecipantiListRenderer;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.DataComparator;
import gui.tableModels.ProgettoTableModel;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.segreteria.ControllerProgettiSegreteria;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.Toolkit;

public class GestioneProgettiSegreteria extends JFrame {
	private JPanel contentPane;
	private JLabel ambitiLabel;
	private JScrollPane ambitiScrollPanel;
	private JButton creaAmbitoButton;
	private JLabel descrizioneLabel;
	private JButton esciButton;
	private JButton filtraButton;
	private JLabel filtroScadutoLabel;
	private JLabel filtroTerminatoLabel;
	private JLabel filtroTipologiaLabel;
	private JLabel filtroAmbitoLabel;
	private JLabel meetingRelativiLabel;
	private JScrollPane meetingScrollPanel;
	private JScrollPane partecipantiScrollPanel;
	private JLabel resetFiltriLabel;
	private JSeparator separator;
	private JScrollPane tabellaScrollPanel;
	private JLabel tipologiaLabel;
	private JLabel nomeLabel;
	private JLabel partecipantiLabel;
	private JLabel titoloLabel;
	private JTextField nomeTextField;
	private JTextField tipologiaTextField;
	private JTable tabellaProgetti;
	private JTextField ambitoNuovoTextField;
	private DefaultListModel<AmbitoProgetto> ambitiListModel;
	private DefaultListModel<Meeting> meetingRelativiModel;
	private DefaultListModel<CollaborazioneProgetto> partecipantiListModel;
	private JTextField filtroNomeTextField;
	private JComboBox<AmbitoProgetto> ambitoComboBox;
	private DefaultComboBoxModel<AmbitoProgetto> modelloFiltroComboBox = new DefaultComboBoxModel();
	private JComboBox<String> tipologiaComboBox;
	private JComboBox<String> scadutoComboBox;
	private JComboBox<String> terminatoComboBox;
	private ProgettoTableModel dataModelTabella;
	private JList <AmbitoProgetto> ambitiList;
	private JList<CollaborazioneProgetto> partecipantiList;
	private JList <Meeting> meetingRelativiList;
	private JTextArea descrizioneTextArea;
	private JLabel dataCreazioneLabel, dataScadenzaLabel, dataTerminazioneLabel;
	
	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	private final String VIOLAZIONE_VINCOLI_TABELLA = "23514";
	private final String VIOLAZIONE_LUNGHEZZA_STRINGA = "22001";
	
	private DateTimeFormatter formatoDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	private String[] siNoComboBox = {null, "Si", "No"};

	public GestioneProgettiSegreteria(ControllerProgettiSegreteria controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneProgettiSegreteria.class.getResource("/icone/WindowIcon_16.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.tornaASegreteria();
			}
		});
		setResizable(false);
		setTitle("Gestione Progetti");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1384, 822);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		infoPanel.setBounds(42, 91, 1283, 306);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		nomeLabel = new JLabel("Nome");
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeLabel.setBounds(48, 34, 39, 23);
		infoPanel.add(nomeLabel);
		
		nomeTextField = new JTextField();
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setEditable(false);
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeTextField.setBounds(103, 33, 176, 20);
		infoPanel.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descrizioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		descrizioneLabel.setBounds(10, 65, 77, 23);
		infoPanel.add(descrizioneLabel);
		
		descrizioneTextArea = new JTextArea();
		descrizioneTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		descrizioneTextArea.setLineWrap(true);
		descrizioneTextArea.setEditable(false);
		descrizioneTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		descrizioneTextArea.setBounds(103, 65, 204, 67);
		infoPanel.add(descrizioneTextArea);
		
		tipologiaLabel = new JLabel("Tipologia");
		tipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		tipologiaLabel.setBounds(18, 144, 69, 23);
		infoPanel.add(tipologiaLabel);
		
		tipologiaTextField = new JTextField();
		tipologiaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaTextField.setEditable(false);
		tipologiaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		tipologiaTextField.setColumns(10);
		tipologiaTextField.setBounds(104, 145, 176, 20);
		infoPanel.add(tipologiaTextField);
		
		dataCreazioneLabel = new JLabel("Data Creazione: ");
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataCreazioneLabel.setBounds(88, 178, 219, 23);
		infoPanel.add(dataCreazioneLabel);
		
		dataScadenzaLabel = new JLabel("Data Scadenza: ");
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataScadenzaLabel.setBounds(88, 212, 219, 23);
		infoPanel.add(dataScadenzaLabel);
		
		dataTerminazioneLabel = new JLabel("Data Terminazione: ");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataTerminazioneLabel.setBounds(88, 246, 219, 23);
		infoPanel.add(dataTerminazioneLabel);
		
		separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(381, 34, 2, 235);
		infoPanel.add(separator);
		
		ambitiScrollPanel = new JScrollPane();
		ambitiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		ambitiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPanel.setBounds(434, 36, 197, 151);
		infoPanel.add(ambitiScrollPanel);
		
		ambitiLabel = new JLabel("Ambiti Progetto");
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		ambitiScrollPanel.setColumnHeaderView(ambitiLabel);
		
		ambitiListModel = new DefaultListModel();
		ambitiList = new JList();
		ambitiList.setSelectionBackground(Color.WHITE);
		ambitiList.setBorder(null);
		ambitiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ambitiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		ambitiScrollPanel.setViewportView(ambitiList);
		
		partecipantiScrollPanel = new JScrollPane();
		partecipantiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		partecipantiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.setBounds(681, 36, 244, 235);
		infoPanel.add(partecipantiScrollPanel);
		
		partecipantiLabel = new JLabel("Partecipanti Progetto");
		partecipantiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setColumnHeaderView(partecipantiLabel);
		
		partecipantiListModel = new DefaultListModel();
		partecipantiList = new JList();
		partecipantiList.setSelectionBackground(Color.WHITE);
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		PartecipantiListRenderer partecipantiRenderer = new PartecipantiListRenderer();
		partecipantiList.setCellRenderer(partecipantiRenderer);
		partecipantiScrollPanel.setViewportView(partecipantiList);
		
		meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		meetingScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.setBounds(972, 36, 288, 235);
		infoPanel.add(meetingScrollPanel);
		
		meetingRelativiLabel = new JLabel("Meeting Relativi");
		meetingRelativiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		meetingScrollPanel.setColumnHeaderView(meetingRelativiLabel);
		
		meetingRelativiModel = new DefaultListModel();
		meetingRelativiList = new JList();
		meetingRelativiList.setSelectionBackground(Color.WHITE);
		meetingRelativiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		MeetingListRenderer meetingRenderer = new MeetingListRenderer();
		meetingRelativiList.setCellRenderer(meetingRenderer);
		meetingScrollPanel.setViewportView(meetingRelativiList);
		
		ambitoNuovoTextField = new JTextField();
		ambitoNuovoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		ambitoNuovoTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		ambitoNuovoTextField.setColumns(10);
		ambitoNuovoTextField.setBounds(434, 198, 197, 20);
		infoPanel.add(ambitoNuovoTextField);
		
		creaAmbitoButton = new JButton("Crea Ambito");
		creaAmbitoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!ambitoNuovoTextField.getText().isBlank())
					creaAmbitoProgetto(controller);
				else
					JOptionPane.showMessageDialog(null,
							"Inserire prima il nome dell'ambito.",
							"Creazione Fallita",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		creaAmbitoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaAmbitoButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaAmbitoButton.setBackground(Color.WHITE);
			}
		});
		creaAmbitoButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		creaAmbitoButton.setBounds(480, 229, 104, 23);
		creaAmbitoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaAmbitoButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaAmbitoButton.setBackground(Color.WHITE);
		infoPanel.add(creaAmbitoButton);
		
		titoloLabel = new JLabel("Gestione Progetti");
		titoloLabel.setIcon(new ImageIcon(GestioneProgettiSegreteria.class.getResource("/icone/progetto_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(42, 11, 290, 64);
		contentPane.add(titoloLabel);
		
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		comandiPanel.setBounds(42, 412, 1283, 45);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		filtroNomeTextField = new JTextField();
		filtroNomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroNomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		filtroNomeTextField.setBounds(165, 12, 199, 20);
		comandiPanel.add(filtroNomeTextField);
		filtroNomeTextField.setColumns(10);
		
		filtraButton = new JButton("Filtra");
		filtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltri(controller);
			}
		});
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
		filtraButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		filtraButton.setBounds(66, 12, 89, 20);
		filtraButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtraButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtraButton.setBackground(Color.WHITE);
		comandiPanel.add(filtraButton);
		
		ambitoComboBox = new JComboBox();
		inizializzaFiltroAmbitiComboBox(controller);
		ambitoComboBox.setUI(new BasicComboBoxUI());
		ambitoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		ambitoComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		ambitoComboBox.setBounds(454, 11, 168, 22);
		ambitoComboBox.setSelectedItem(null);
		comandiPanel.add(ambitoComboBox);

		inizializzaFiltroTipologieComboBox(controller);
		tipologiaComboBox.setUI(new BasicComboBoxUI());
		tipologiaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		tipologiaComboBox.setBounds(739, 11, 197, 23);
		tipologiaComboBox.setSelectedItem(null);
		comandiPanel.add(tipologiaComboBox);

		filtroAmbitoLabel = new JLabel("Ambito");
		filtroAmbitoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroAmbitoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroAmbitoLabel.setBounds(402, 11, 42, 23);
		comandiPanel.add(filtroAmbitoLabel);
		
		filtroTipologiaLabel = new JLabel("Tipologia");
		filtroTipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroTipologiaLabel.setBounds(660, 10, 69, 24);
		comandiPanel.add(filtroTipologiaLabel);
		
		filtroScadutoLabel = new JLabel("Scaduto");
		filtroScadutoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroScadutoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroScadutoLabel.setBounds(974, 11, 49, 23);
		comandiPanel.add(filtroScadutoLabel);
		
		filtroTerminatoLabel = new JLabel("Terminato");
		filtroTerminatoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTerminatoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroTerminatoLabel.setBounds(1120, 11, 69, 23);
		comandiPanel.add(filtroTerminatoLabel);
		
		scadutoComboBox = new JComboBox(siNoComboBox);
		scadutoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		scadutoComboBox.setUI(new BasicComboBoxUI());
		scadutoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		scadutoComboBox.setBounds(1033, 11, 49, 22);
		comandiPanel.add(scadutoComboBox);
		
		terminatoComboBox = new JComboBox(siNoComboBox);
		terminatoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		terminatoComboBox.setUI(new BasicComboBoxUI());
		terminatoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		terminatoComboBox.setBounds(1199, 11, 49, 22);
		comandiPanel.add(terminatoComboBox);
		
		resetFiltriLabel = new JLabel("");
		resetFiltriLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetFiltri(controller);
			}
		});
		resetFiltriLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetFiltriLabel.setIcon(new ImageIcon(GestioneProgettiSegreteria.class.getResource("/icone/refresh.png")));
		resetFiltriLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		resetFiltriLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		resetFiltriLabel.setBounds(24, 11, 18, 23);
		comandiPanel.add(resetFiltriLabel);
		
		tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tabellaScrollPanel.setBounds(42, 468, 1283, 268);
		contentPane.add(tabellaScrollPanel);
		
		dataModelTabella = new ProgettoTableModel();
		impostaModelloTabellaProgettiSegreteria(controller);
		tabellaProgetti = new JTable(dataModelTabella);
		impostaProprietàTabellaProgetti();
		tabellaProgetti.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabellaProgetti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabellaProgetti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rigaSelezionata = tabellaProgetti.getSelectedRow();
				rigaSelezionata = tabellaProgetti.convertRowIndexToModel(rigaSelezionata);
				Progetto progettoSelezionato = dataModelTabella.getSelected(rigaSelezionata);

				nomeTextField.setText(progettoSelezionato.getNomeProgetto());
				descrizioneTextArea.setText(progettoSelezionato.getDescrizioneProgetto());
				tipologiaTextField.setText(progettoSelezionato.getTipoProgetto());
				dataCreazioneLabel
						.setText("Data Creazione: " + progettoSelezionato.getDataCreazione().toString(formatoDate));
				if (progettoSelezionato.getScadenza() != null) {
					dataScadenzaLabel
							.setText("Data Scadenza: " + progettoSelezionato.getScadenza().toString(formatoDate));
				} else {
					dataScadenzaLabel.setText("Senza Scadenza");
				}
				if (progettoSelezionato.getDataTerminazione() != null) {
					dataTerminazioneLabel.setText(
							"Data Terminazione: " + progettoSelezionato.getDataTerminazione().toString(formatoDate));
				} else {
					dataTerminazioneLabel.setText("Non Terminato");
				}
				ambitiListModel.clear();
				impostaAmbitiListModel(controller, progettoSelezionato);
				meetingRelativiModel.clear();
				meetingRelativiModel.addAll(progettoSelezionato.getMeetingsRelativi());
				meetingRelativiList.setModel(meetingRelativiModel);
				partecipantiListModel.clear();
				impostaPartecipantiListModel(controller, progettoSelezionato);
			}
		});
		tabellaProgetti.setFont(new Font("Consolas", Font.PLAIN, 11));
		tabellaScrollPanel.setViewportView(tabellaProgetti);

		esciButton = new JButton("Esci");
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tornaASegreteria();
			}
		});
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		esciButton.setBackground(Color.WHITE);
		esciButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				esciButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				esciButton.setBackground(Color.WHITE);
			}
		});
		esciButton.setBounds(42, 747, 69, 29);
		contentPane.add(esciButton);
	}
	
	private void impostaProprietàTabellaProgetti() {
		impostaRendererTabellaProgetti();
		impostaModelloColonneTabellaProgetti();
		impostaSortertabellaProgetti();
		
	}

	private void impostaRendererTabellaProgetti() {
		DefaultTableCellRenderer renderTabellaProgetti = new DefaultTableCellRenderer();
        renderTabellaProgetti.setHorizontalAlignment(SwingConstants.CENTER);
        renderTabellaProgetti.setVerticalAlignment(SwingConstants.CENTER);
        tabellaProgetti.getColumnModel().getColumn(0).setCellRenderer(renderTabellaProgetti);
		tabellaProgetti.getColumnModel().getColumn(1).setCellRenderer(renderTabellaProgetti);
		tabellaProgetti.getColumnModel().getColumn(2).setCellRenderer(renderTabellaProgetti);
		tabellaProgetti.getColumnModel().getColumn(3).setCellRenderer(renderTabellaProgetti);
		tabellaProgetti.getColumnModel().getColumn(4).setCellRenderer(renderTabellaProgetti);
		tabellaProgetti.getColumnModel().getColumn(5).setCellRenderer(renderTabellaProgetti);
	}

	private void impostaModelloColonneTabellaProgetti() {
        tabellaProgetti.getColumnModel().getColumn(0).setMinWidth(450);
		tabellaProgetti.getColumnModel().getColumn(1).setMinWidth(330);
		tabellaProgetti.getColumnModel().getColumn(2).setMinWidth(200);
		tabellaProgetti.getColumnModel().getColumn(3).setMinWidth(100);
		tabellaProgetti.getColumnModel().getColumn(4).setMinWidth(100);
		tabellaProgetti.getColumnModel().getColumn(5).setMinWidth(100);
	}

	private void impostaSortertabellaProgetti() {
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaProgetti.getModel());
		DataComparator comparatorDate = new DataComparator();
		sorter.setComparator(3, comparatorDate);
		sorter.setComparator(4, comparatorDate);
		sorter.setComparator(5, comparatorDate);
		tabellaProgetti.setRowSorter(sorter);
		tabellaProgetti.getRowSorter().toggleSortOrder(0);
	}

	private void resetFiltri(ControllerProgettiSegreteria controller) {
		filtroNomeTextField.setText("");
		ambitoComboBox.setSelectedIndex(0);
		tipologiaComboBox.setSelectedIndex(0);
		scadutoComboBox.setSelectedIndex(0);
		terminatoComboBox.setSelectedIndex(0);
		impostaModelloTabellaProgettiSegreteria(controller);
		dataModelTabella.fireTableDataChanged();
		tabellaProgetti.clearSelection();
		pulisciCampi();
	}
	
	private void pulisciCampi() {
		nomeTextField.setText("");
		descrizioneTextArea.setText("");
		tipologiaTextField.setText("");
		dataCreazioneLabel.setText("Data Creazione: N/A");
		dataScadenzaLabel.setText("Data Scadenza: N/A");
		dataTerminazioneLabel.setText("Data Terminazione: N/A");
		ambitiListModel.clear();
		partecipantiListModel.clear();
		meetingRelativiModel.clear();
	}
	
	private void applicaFiltri(ControllerProgettiSegreteria controller) {
		String nomeCercato = "%";
		if (!filtroNomeTextField.getText().isBlank())
			nomeCercato = filtroNomeTextField.getText();
		AmbitoProgetto ambitoCercato = null;
		if (ambitoComboBox.getSelectedItem() != null)
			ambitoCercato= (AmbitoProgetto) ambitoComboBox.getSelectedItem();
		String tipologiaCercata = null;
		if (tipologiaComboBox.getSelectedItem() != null)
			tipologiaCercata=  tipologiaComboBox.getSelectedItem().toString();
		String scaduto = "Entrambi";
		if (scadutoComboBox.getSelectedItem() != null)
			if (scadutoComboBox.getSelectedItem().toString().equals("Si"))
				scaduto = "Si";
			else
				scaduto = "No";
		String terminato = "Entrambi";
		if (terminatoComboBox.getSelectedItem() != null)
			if (terminatoComboBox.getSelectedItem().toString().equals("Si"))
				terminato = "Si";
			else
				terminato = "No";
		try {
			dataModelTabella.setProgettiTabella(controller.ottieniProgettiFiltrati(nomeCercato,ambitoCercato,tipologiaCercata, scaduto, terminato));
			dataModelTabella.fireTableDataChanged();
			tabellaProgetti.clearSelection();
			pulisciCampi();
		} catch (SQLException eccezioneSQL) {
			ErroreDialog erroreFatale = new ErroreDialog(eccezioneSQL, true);
			erroreFatale.setVisible(true);
		}
	}
	
	private void inizializzaFiltroAmbitiComboBox(ControllerProgettiSegreteria controller) {
		try {
			modelloFiltroComboBox.removeAllElements();
			modelloFiltroComboBox.addAll(controller.ottieniTuttiAmbiti());
			ambitoComboBox.setModel(modelloFiltroComboBox);
		} catch (SQLException eccezioneSQL) {
			ErroreDialog erroreFatale = new ErroreDialog(eccezioneSQL, true);
			erroreFatale.setVisible(true);
		}
	}
	
	private void inizializzaFiltroTipologieComboBox(ControllerProgettiSegreteria controller) {
		try {
			tipologiaComboBox = new JComboBox(controller.ottieniTipologie().toArray());
		} catch (SQLException eccezioneSQL) {
			ErroreDialog erroreFatale = new ErroreDialog(eccezioneSQL, true);
			erroreFatale.setVisible(true);
		}
	}
	
	private void creaAmbitoProgetto(ControllerProgettiSegreteria controller) {
		try {
			controller.creaAmbitoProgetto(ambitoNuovoTextField.getText());
			JOptionPane.showMessageDialog(null, 
					"Ambito creato con successo.\nDa ora i dipendenti potranno lavorare a progetti di questo ambito.",
					"Creazione Riuscita",
					JOptionPane.INFORMATION_MESSAGE);
			inizializzaFiltroAmbitiComboBox(controller);
			ambitoNuovoTextField.setText("");
		} catch (SQLException eccezioneSQL) {
			ErroreDialog errore = null;
			switch(eccezioneSQL.getSQLState()) {
			case VIOLAZIONE_PKEY_UNIQUE:
				errore = new ErroreDialog(eccezioneSQL,
						"Creazione Fallita",
						"Non possono esistere ambiti duplicati." +
						"\nVerificare che il nome dell'ambito non esista già.", false);
				break;
			case VIOLAZIONE_VINCOLI_TABELLA:
				errore = new ErroreDialog(eccezioneSQL,
						"Creazione Fallita",
						"Verificare che non ci siano numeri nel nome dell'ambito.", false);
				break;
			case VIOLAZIONE_LUNGHEZZA_STRINGA:
				errore = new ErroreDialog(eccezioneSQL,
						"Creazione Fallita",
						"Verificare che il nome dell'ambito non abbia più di 20 caratteri.", false);
				break;
			default:
				errore = new ErroreDialog(eccezioneSQL, true);
			}
			errore.setVisible(true);
		}
	}
	
	private void impostaAmbitiListModel(ControllerProgettiSegreteria controller, Progetto progettoSelezionato) {
			ambitiListModel.addAll(progettoSelezionato.getAmbiti());
			ambitiList.setModel(ambitiListModel);
	}
	
	private void impostaPartecipantiListModel(ControllerProgettiSegreteria controller, Progetto progettoSelezionato) {
			partecipantiListModel.addAll(progettoSelezionato.getCollaborazioni());
			partecipantiList.setModel(partecipantiListModel);
	}
	
	private void impostaModelloTabellaProgettiSegreteria(ControllerProgettiSegreteria controller) {
		try {
			dataModelTabella.setProgettiTabella(controller.ottieniProgetti());
		} catch (SQLException eccezioneSQL) {
			ErroreDialog erroreFatale = new ErroreDialog(eccezioneSQL, true);
			erroreFatale.setVisible(true);
		}
	}
}
