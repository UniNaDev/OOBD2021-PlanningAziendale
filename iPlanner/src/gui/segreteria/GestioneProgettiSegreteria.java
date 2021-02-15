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
import entita.Meeting;
import entita.Progetto;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.segreteria.ControllerProgettiSegreteria;
import eccezioni.ManagerEccezioniDatiSQLAmbito;
import eccezioni.ManagerEccezioniDatiSQLProgetto;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.plaf.basic.BasicComboBoxUI;

public class GestioneProgettiSegreteria extends JFrame {
	private JPanel contentPane;
	private JTextField nomeTextField;
	private JTextField tipologiaTextField;
	private JTable tabellaProgetti;
	private JTextField ambitoNuovoTextField;
	private DefaultListModel ambitiListModel;
	private DefaultListModel meetingRelativiModel;
	private DefaultListModel partecipantiListModel;
	private JTextField filtroNomeTextField;
	private JComboBox ambitoComboBox;
	private JComboBox tipologiaComboBox;
	private JComboBox scadutoComboBox;
	private JComboBox terminatoComboBox;
	private ProgettoTableModel dataModelTabella;
	private JList <AmbitoProgetto> ambitiList;
	private JList partecipantiList;
	private JList <Meeting> meetingRelativiList;
	private JTextArea descrizioneTextArea;
	private JLabel dataCreazioneLabel, dataScadenzaLabel, dataTerminazioneLabel;
	
	private ManagerEccezioniDatiSQLProgetto eccezioniSQLProgetto;
	private ManagerEccezioniDatiSQLAmbito eccezioniSQLAmbito;
	
	private DateTimeFormatter formatoDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	private String[] siNoComboBox = {null, "Si", "No"};

	public GestioneProgettiSegreteria(ControllerProgettiSegreteria controller) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.tornaAiPlanner();
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
		
		JLabel nomeLabel = new JLabel("Nome");
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
		
		JLabel descrizioneLabel = new JLabel("Descrizione");
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
		
		JLabel tipologiaLabel = new JLabel("Tipologia");
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
		
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(381, 34, 2, 235);
		infoPanel.add(separator);
		
		JScrollPane ambitiScrollPanel = new JScrollPane();
		ambitiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		ambitiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPanel.setBounds(434, 36, 197, 151);
		infoPanel.add(ambitiScrollPanel);
		
		JLabel ambitiLabel = new JLabel("Ambiti");
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		ambitiScrollPanel.setColumnHeaderView(ambitiLabel);
		
		ambitiListModel = new DefaultListModel();
		ambitiList = new JList();
		ambitiList.setSelectionBackground(Color.WHITE);
		ambitiList.setBorder(null);
		ambitiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ambitiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		ambitiScrollPanel.setViewportView(ambitiList);
		
		JScrollPane partecipantiScrollPanel = new JScrollPane();
		partecipantiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		partecipantiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.setBounds(681, 36, 244, 235);
		infoPanel.add(partecipantiScrollPanel);
		
		JLabel partecipantiLabel = new JLabel("Partecipanti");
		partecipantiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setColumnHeaderView(partecipantiLabel);
		
		partecipantiListModel = new DefaultListModel();
		partecipantiList = new JList();
		partecipantiList.setSelectionBackground(Color.WHITE);
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		PartecipantiListRenderer partecipantiRenderer = new PartecipantiListRenderer();
		partecipantiList.setCellRenderer(partecipantiRenderer);
		partecipantiScrollPanel.setViewportView(partecipantiList);
		
		JScrollPane meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		meetingScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.setBounds(972, 36, 288, 235);
		infoPanel.add(meetingScrollPanel);
		
		JLabel meetingRelativiLabel = new JLabel("Meeting Relativi");
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
		
		JButton creaAmbitoButton = new JButton("Crea Ambito");
		creaAmbitoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!ambitoNuovoTextField.getText().isBlank())
					creaAmbitoProgetto(controller);
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
		
		JLabel titoloLabel = new JLabel("Gestione Progetti");
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
		
		JButton filtraButton = new JButton("Filtra");
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

		JLabel filtroAmbitoLabel = new JLabel("Ambito");
		filtroAmbitoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroAmbitoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroAmbitoLabel.setBounds(402, 11, 42, 23);
		comandiPanel.add(filtroAmbitoLabel);
		
		JLabel filtroTipologiaLabel = new JLabel("Tipologia");
		filtroTipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroTipologiaLabel.setBounds(660, 10, 69, 24);
		comandiPanel.add(filtroTipologiaLabel);
		
		JLabel filtroScadutoLabel = new JLabel("Scaduto");
		filtroScadutoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroScadutoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroScadutoLabel.setBounds(974, 11, 49, 23);
		comandiPanel.add(filtroScadutoLabel);
		
		JLabel filtroTerminatoLabel = new JLabel("Terminato");
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
		
		JLabel resetFiltriLabel = new JLabel("");
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
		
		JScrollPane tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tabellaScrollPanel.setBounds(42, 468, 1283, 268);
		contentPane.add(tabellaScrollPanel);
		
		dataModelTabella = new ProgettoTableModel();
		setModelloTabellaProgettiTutti(controller);
		tabellaProgetti = new JTable(dataModelTabella);
		tabellaProgetti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaProgetti.getModel());
		DataComparator comparatorDate = new DataComparator();
		sorter.setComparator(3, comparatorDate);
		sorter.setComparator(4, comparatorDate);
		sorter.setComparator(5, comparatorDate);
		tabellaProgetti.setRowSorter(sorter);
		tabellaProgetti.getRowSorter().toggleSortOrder(0);
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
				setAmbitiListModel(controller, progettoSelezionato);
				meetingRelativiModel.clear();
				meetingRelativiModel.addAll(progettoSelezionato.getMeetingsRelativi());
				meetingRelativiList.setModel(meetingRelativiModel);
				partecipantiListModel.clear();
				setPartecipantiListModel(controller, progettoSelezionato);
			}
		});
		tabellaProgetti.setFont(new Font("Consolas", Font.PLAIN, 11));
		tabellaScrollPanel.setViewportView(tabellaProgetti);

		JButton esciButton = new JButton("Esci");
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tornaAiPlanner();
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
	
	//Altri metodi
	//-----------------------------------------------------------------
	private void resetFiltri(ControllerProgettiSegreteria controller) {
		filtroNomeTextField.setText("");
		ambitoComboBox.setSelectedIndex(0);
		tipologiaComboBox.setSelectedIndex(0);
		scadutoComboBox.setSelectedIndex(0);
		terminatoComboBox.setSelectedIndex(0);
		setModelloTabellaProgettiTutti(controller);
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
		} catch (SQLException e) {
			eccezioniSQLProgetto = new ManagerEccezioniDatiSQLProgetto(e);
			eccezioniSQLProgetto.mostraErrore();
		}
	}
	
	private void inizializzaFiltroAmbitiComboBox(ControllerProgettiSegreteria controller) {
		try {
			ambitoComboBox = new JComboBox(controller.ottieniTuttiAmbiti().toArray());
		} catch (SQLException e2) {
			eccezioniSQLAmbito = new ManagerEccezioniDatiSQLAmbito(e2);
			eccezioniSQLAmbito.mostraErrore();
		}
	}
	
	private void inizializzaFiltroTipologieComboBox(ControllerProgettiSegreteria controller) {
		try {
			tipologiaComboBox = new JComboBox(controller.ottieniTipologie().toArray());
		} catch (SQLException e2) {
			eccezioniSQLProgetto = new ManagerEccezioniDatiSQLProgetto(e2);
			eccezioniSQLProgetto.mostraErrore();
		}
	}
	
	private void creaAmbitoProgetto(ControllerProgettiSegreteria controller) {
		try {
			controller.creaAmbitoProgetto(ambitoNuovoTextField.getText());
		} catch (SQLException e1) {
			eccezioniSQLAmbito = new ManagerEccezioniDatiSQLAmbito(e1);
			eccezioniSQLAmbito.mostraErrore();
		}
	}
	
	private void setAmbitiListModel(ControllerProgettiSegreteria controller, Progetto progettoSelezionato) {
		try {
			ambitiListModel.addAll(controller.ottieniAmbitiProgetto(progettoSelezionato));
			ambitiList.setModel(ambitiListModel);
		} catch (SQLException e1) {
			eccezioniSQLAmbito = new ManagerEccezioniDatiSQLAmbito(e1);
			eccezioniSQLAmbito.mostraErrore();
		}
	}
	
	private void setPartecipantiListModel(ControllerProgettiSegreteria controller, Progetto progettoSelezionato) {
		try {
			partecipantiListModel.addAll(controller.ottieniCollaborazioni(progettoSelezionato));
			partecipantiList.setModel(partecipantiListModel);
		} catch (SQLException e1) {
			//TODO: trattandosi di enitità composte da dipendente e progetto hanno bisogno anche dell'handler del dipendente?
			eccezioniSQLProgetto = new ManagerEccezioniDatiSQLProgetto(e1);
			eccezioniSQLProgetto.mostraErrore();
		}
	}
	
	private void setModelloTabellaProgettiTutti(ControllerProgettiSegreteria controller) {
		try {
			dataModelTabella.setProgettiTabella(controller.ottieniProgetti());
		} catch (SQLException e) {
			eccezioniSQLProgetto = new ManagerEccezioniDatiSQLProgetto(e);
			eccezioniSQLProgetto.mostraErrore();
		}
	}
}
