package gui.segreteria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.AmbitoProgetto;
import entita.Meeting;
import entita.Progetto;
import gui.cellRenderers.MeetingListRenderer;
import gui.cellRenderers.PartecipantiListRenderer;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.ProgettoTableModel;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.segreteria.ControllerProgettiSegreteria;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.event.ListSelectionEvent;

public class GestioneProgettiSegreteria extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//GUI
	private JPanel contentPane;
	private JTextField nomeTextField;
	private JTextField tipologiaTextField;
	private JTable tabellaProgetti;
	private JTextField ambitoNuovoTextField;
	private DefaultListModel ambitiModel;
	private DefaultListModel meetingRelativiModel;
	private DefaultListModel partecipantiModel;
	private JTextField filtroNomeTextField;
	private JComboBox ambitoComboBox;
	private JComboBox tipologiaComboBox;
	private JComboBox scadutoComboBox;
	private JComboBox terminatoComboBox;
	private ProgettoTableModel dataModelTabella;
	
	//Altri attributi
	private String[] siNoComboBox = {null, "Si", "No"};	//filtri yes/no
	
	//METODI
	//-----------------------------------------------------------------
	
	public GestioneProgettiSegreteria(ControllerProgettiSegreteria controller) {
		//Evento finestra che si sta chiudendo
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
		//Viene visualizzata al centro dello schermo
		setLocationRelativeTo(null);
		
		//Info Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		infoPanel.setBounds(42, 91, 1283, 306);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//Label "Nome"
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeLabel.setBounds(48, 34, 39, 23);
		infoPanel.add(nomeLabel);
		
		//TextField del nome del progetto
		nomeTextField = new JTextField();
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setEditable(false);
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeTextField.setBounds(103, 33, 176, 20);
		infoPanel.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		//Label "Descrizione"
		JLabel descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descrizioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		descrizioneLabel.setBounds(10, 65, 77, 23);
		infoPanel.add(descrizioneLabel);
		
		//TextArea descrizione del progetto
		JTextArea descrizioneTextArea = new JTextArea();
		descrizioneTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		descrizioneTextArea.setLineWrap(true);
		descrizioneTextArea.setEditable(false);
		descrizioneTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		descrizioneTextArea.setBounds(103, 65, 204, 67);
		infoPanel.add(descrizioneTextArea);
		
		//Label "Tipologia"
		JLabel tipologiaLabel = new JLabel("Tipologia");
		tipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		tipologiaLabel.setBounds(18, 144, 69, 23);
		infoPanel.add(tipologiaLabel);
		
		//TextField tipologia del progetto
		tipologiaTextField = new JTextField();
		tipologiaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaTextField.setEditable(false);
		tipologiaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		tipologiaTextField.setColumns(10);
		tipologiaTextField.setBounds(104, 145, 176, 20);
		infoPanel.add(tipologiaTextField);
		
		//Label Data Creazione
		JLabel dataCreazioneLabel = new JLabel("Data Creazione: ");
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataCreazioneLabel.setBounds(88, 178, 219, 23);
		infoPanel.add(dataCreazioneLabel);
		
		//Label Data Scadenza
		JLabel dataScadenzaLabel = new JLabel("Data Scadenza: ");
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataScadenzaLabel.setBounds(88, 212, 219, 23);
		infoPanel.add(dataScadenzaLabel);
		
		//Label Data Terminazione
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione: ");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		dataTerminazioneLabel.setBounds(88, 246, 219, 23);
		infoPanel.add(dataTerminazioneLabel);
		
		//Separator
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(381, 34, 2, 235);
		infoPanel.add(separator);
		
		//Scroll Panel per lista ambiti
		JScrollPane ambitiScrollPanel = new JScrollPane();
		ambitiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		ambitiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		ambitiScrollPanel.setBounds(434, 36, 197, 151);
		infoPanel.add(ambitiScrollPanel);
		
		//Label "Ambiti"
		JLabel ambitiLabel = new JLabel("Ambiti");
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		ambitiScrollPanel.setColumnHeaderView(ambitiLabel);
		
		//List ambiti del progetto
		ambitiModel = new DefaultListModel();
		JList <AmbitoProgetto> ambitiList = new JList();
		ambitiList.setBorder(null);
		ambitiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ambitiList.setSelectionBackground(Color.WHITE);
		ambitiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		ambitiScrollPanel.setViewportView(ambitiList);
		
		//Scroll Panel dei partecipanti
		JScrollPane partecipantiScrollPanel = new JScrollPane();
		partecipantiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		partecipantiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.setBounds(681, 36, 244, 235);
		infoPanel.add(partecipantiScrollPanel);
		
		//Label "Partecipanti"
		JLabel partecipantiLabel = new JLabel("Partecipanti");
		partecipantiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setColumnHeaderView(partecipantiLabel);
		
		//List partecipanti al progetto
		partecipantiModel = new DefaultListModel();
		JList partecipantiList = new JList();
		partecipantiList.setSelectionBackground(Color.WHITE);
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		PartecipantiListRenderer partecipantiRenderer = new PartecipantiListRenderer();
		partecipantiList.setCellRenderer(partecipantiRenderer);
		partecipantiScrollPanel.setViewportView(partecipantiList);
		
		//Scroll Panel meeting relativi al progetto
		JScrollPane meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		meetingScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.setBounds(972, 36, 288, 235);
		infoPanel.add(meetingScrollPanel);
		
		//Label "Meeting Relativi"
		JLabel meetingRelativiLabel = new JLabel("Meeting Relativi");
		meetingRelativiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		meetingScrollPanel.setColumnHeaderView(meetingRelativiLabel);
		
		//List di meeting relativi al progetto
		meetingRelativiModel = new DefaultListModel();
		JList <Meeting> meetingRelativiList = new JList();
		meetingRelativiList.setSelectionBackground(Color.WHITE);
		meetingRelativiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		MeetingListRenderer meetingRenderer = new MeetingListRenderer();
		meetingRelativiList.setCellRenderer(meetingRenderer);
		meetingScrollPanel.setViewportView(meetingRelativiList);
		
		//Text Field per nuovi ambiti
		ambitoNuovoTextField = new JTextField();
		ambitoNuovoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		ambitoNuovoTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		ambitoNuovoTextField.setColumns(10);
		ambitoNuovoTextField.setBounds(434, 198, 197, 20);
		infoPanel.add(ambitoNuovoTextField);
		
		//Button creazione nuovi ambiti
		JButton creaAmbitoButton = new JButton("Crea Ambito");
		//Click mouse
		creaAmbitoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//crea l'ambito e lo inserisce nel DB
				if (!ambitoNuovoTextField.getText().isBlank())
					try {
						controller.creaAmbitoProgetto(ambitoNuovoTextField.getText()); //crea l'ambito inserito nella TextField
					} catch (SQLException e1) {
						//violazione vincolo primary key/unique
						if (e1.getSQLState().equals("23505")) {
							JOptionPane.showMessageDialog(null,
									"Impossibile inserire il nuovo ambito perchè esiste già.",
									"Errore Ambito Esistente",
									JOptionPane.ERROR_MESSAGE);
						}
						//violazione vincolo sui dati (es: troppo lungo il nome dell'ambito da creare)
						else if (e1.getSQLState().equals("22001")) {
							JOptionPane.showMessageDialog(null,
									"Impossibile creare l'ambito inserito perchè il suo nome supera i 20 caratteri.",
									"Errore Dati Non Validi",
									JOptionPane.ERROR_MESSAGE);
						}
						//errori non contemplati
						else {
							JOptionPane.showMessageDialog(null,
									e1.getMessage() + "\nContattare uno sviluppatore.",
									"Errore #" + e1.getErrorCode(),
									JOptionPane.ERROR_MESSAGE);
						}
					}
			}
		});
		creaAmbitoButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaAmbitoButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaAmbitoButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		creaAmbitoButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		creaAmbitoButton.setBounds(480, 229, 104, 23);
		creaAmbitoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaAmbitoButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaAmbitoButton.setBackground(Color.WHITE);
		infoPanel.add(creaAmbitoButton);
		
		//Label "Gestione Progetti"
		JLabel titoloLabel = new JLabel("Gestione Progetti");
		titoloLabel.setIcon(new ImageIcon(GestioneProgettiSegreteria.class.getResource("/icone/progetto_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(42, 11, 290, 64);
		contentPane.add(titoloLabel);
		
		//Panel Comandi
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		comandiPanel.setBounds(42, 412, 1283, 45);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		//TextField per nome progetti in filtri
		filtroNomeTextField = new JTextField();
		filtroNomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroNomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		filtroNomeTextField.setBounds(133, 12, 185, 20);
		comandiPanel.add(filtroNomeTextField);
		filtroNomeTextField.setColumns(10);
		
		//Button Filtra
		JButton filtraButton = new JButton("Filtra");
		filtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Filtra
				applicaFiltri(controller);
			}
		});
		filtraButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				filtraButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				filtraButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}
		});
		filtraButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		filtraButton.setBounds(34, 12, 89, 20);
		filtraButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filtraButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtraButton.setBackground(Color.WHITE);
		comandiPanel.add(filtraButton);
		
		//ComboBox ambiti
		try {
			ambitoComboBox = new JComboBox(controller.ottieniTuttiAmbiti().toArray());
			ambitoComboBox.setUI(new BasicComboBoxUI());
			ambitoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			ambitoComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
			ambitoComboBox.setBounds(414, 11, 168, 22);
			ambitoComboBox.setSelectedItem(null);
			comandiPanel.add(ambitoComboBox);
		} catch (SQLException e2) {
			//errore query per tutti gli ambiti
			JOptionPane.showMessageDialog(null,
					"Impossibile ottenere tutti gli ambiti dal database.\nControllare che sia stabilita la connessione al database.",
					"Errore Interrogazione Database",
					JOptionPane.ERROR_MESSAGE);
		}

		//ComboBox tipologie
		try {
			tipologiaComboBox = new JComboBox(controller.ottieniTipologie().toArray());
			tipologiaComboBox.setUI(new BasicComboBoxUI());
			tipologiaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			tipologiaComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
			tipologiaComboBox.setBounds(696, 11, 197, 23);
			tipologiaComboBox.setSelectedItem(null);
			comandiPanel.add(tipologiaComboBox);
		} catch (SQLException e2) {
			//errore query per tutte le tipologie
			JOptionPane.showMessageDialog(null,
					"Impossibile ottenere tutte le tipologie dal database.\nControllare che sia stabilita la connessione al database.",
					"Errore Interrogazione Database",
					JOptionPane.ERROR_MESSAGE);
		}

		//Label "Ambito" in filtri
		JLabel filtroAmbitoLabel = new JLabel("Ambito");
		filtroAmbitoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroAmbitoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroAmbitoLabel.setBounds(362, 11, 42, 23);
		comandiPanel.add(filtroAmbitoLabel);
		
		//Label "Tipologia" in filtri
		JLabel filtroTipologiaLabel = new JLabel("Tipologia");
		filtroTipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroTipologiaLabel.setBounds(617, 10, 69, 24);
		comandiPanel.add(filtroTipologiaLabel);
		
		//Label "Scaduto" in filtri
		JLabel filtroScadutoLabel = new JLabel("Scaduto");
		filtroScadutoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroScadutoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroScadutoLabel.setBounds(923, 11, 49, 23);
		comandiPanel.add(filtroScadutoLabel);
		
		//Label "Terminato" in filtri
		JLabel filtroTerminatoLabel = new JLabel("Terminato");
		filtroTerminatoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filtroTerminatoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroTerminatoLabel.setBounds(1084, 11, 69, 23);
		comandiPanel.add(filtroTerminatoLabel);
		
		//ComboBox scaduto si/no/entrambi per filtri
		scadutoComboBox = new JComboBox(siNoComboBox);
		scadutoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		scadutoComboBox.setUI(new BasicComboBoxUI());
		scadutoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		scadutoComboBox.setBounds(982, 9, 49, 22);
		comandiPanel.add(scadutoComboBox);
		
		//ComboBox terminato si/no/entrambi per filtri
		terminatoComboBox = new JComboBox(siNoComboBox);
		terminatoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		terminatoComboBox.setUI(new BasicComboBoxUI());
		terminatoComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		terminatoComboBox.setBounds(1163, 9, 49, 22);
		comandiPanel.add(terminatoComboBox);
		
		//Scroll Panel per tabella progetti
		JScrollPane tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tabellaScrollPanel.setBounds(42, 468, 1283, 268);
		contentPane.add(tabellaScrollPanel);
		
		//Table progetti aziendali
		dataModelTabella = new ProgettoTableModel();
		try {
			dataModelTabella.setProgettiTabella(controller.ottieniProgetti());
			tabellaProgetti = new JTable(dataModelTabella);
			tabellaProgetti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//scroller tabella
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaProgetti.getModel());	//sorter
			DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
			sorter.setComparator(5, new Comparator<String>() {
			       @Override
			       public int compare(String data1, String data2) {
			           LocalDate date1 = LocalDate.parse(data1,formatDate);
			           LocalDate date2= LocalDate.parse(data2,formatDate);
			           if (date1.equals(date2))
			            return 0;
			           else if (date1.isBefore(date2))
			            return -1;
			           else
			            return 1;
			       }
			   });
			tabellaProgetti.setRowSorter(sorter);
			tabellaProgetti.getRowSorter().toggleSortOrder(0);
			tabellaProgetti.addMouseListener(new MouseAdapter() {
				//Mouse clicked
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = tabellaProgetti.getSelectedRow();	//ottiene la riga selezionata
					row = tabellaProgetti.convertRowIndexToModel(row);	//converte la riga tenendo conto del sort
					Progetto progettoSelezionato = dataModelTabella.getSelected(row);	//ottiene il progetto selezionato
					
					//Aggiorna GUI
					nomeTextField.setText(progettoSelezionato.getNomeProgetto()); //nome
					descrizioneTextArea.setText(progettoSelezionato.getDescrizioneProgetto()); //descrizione
					tipologiaTextField.setText(progettoSelezionato.getTipoProgetto()); //tipologia progetto
					DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
					dataCreazioneLabel.setText("Data Creazione: " + progettoSelezionato.getDataCreazione().toString(formatDate)); //data creazione
					//data scadenza
					if (progettoSelezionato.getScadenza() != null) {
						dataScadenzaLabel.setText("Data Scadenza: " + progettoSelezionato.getScadenza().toString(formatDate));
					}
					else {
						dataScadenzaLabel.setText("Senza Scadenza");
					}
					//data terminazione
					if (progettoSelezionato.getDataTerminazione() != null) {
						dataTerminazioneLabel.setText("Data Terminazione: " + progettoSelezionato.getDataTerminazione().toString(formatDate));
					}
					else {
						dataTerminazioneLabel.setText("Non Terminato");
					}
					//Ambiti progetto
					ambitiModel.clear();
					try {
						ambitiModel.addAll(controller.ottieniAmbitiProgetto(progettoSelezionato));
						ambitiList.setModel(ambitiModel);
					} catch (SQLException e1) {
						//errore query per tutti gli ambiti
						JOptionPane.showMessageDialog(null,
								"Impossibile ottenere tutti gli ambiti del progetto dal database.\nControllare che sia stabilita la connessione al database.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
					}
					//Meeting relativi
					meetingRelativiModel.clear();
					meetingRelativiModel.addAll(progettoSelezionato.getMeetingsRelativi());
					meetingRelativiList.setModel(meetingRelativiModel);
					//Partecipanti
					partecipantiModel.clear();
					try {
						partecipantiModel.addAll(controller.ottieniCollaborazioni(progettoSelezionato));
						partecipantiList.setModel(partecipantiModel);
					} catch (SQLException e1) {
						//errore query per tutti i partecipanti
						JOptionPane.showMessageDialog(null,
								"Impossibile ottenere tutti i partecipanti al progetto dal database.\nControllare che sia stabilita la connessione al database.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			tabellaProgetti.setFont(new Font("Consolas", Font.PLAIN, 11));
			tabellaScrollPanel.setViewportView(tabellaProgetti);
			
			//Button per uscire
			JButton esciButton = new JButton("Esci");
			//click mouse
			esciButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.tornaAiPlanner();	//torna alla finestra principale
				}
			});
			esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
			esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			esciButton.setBackground(Color.WHITE);
			esciButton.addMouseListener(new MouseAdapter() {
				//mouse sopra il pulsante
				@Override
				public void mouseEntered(MouseEvent e) 
				{
					esciButton.setBackground(Color.LIGHT_GRAY);	//evidenzia il pulsante
				}
				//mouse fuori dal pulsante
				@Override
				public void mouseExited(MouseEvent e) 
				{
					esciButton.setBackground(Color.WHITE);	//smette di evidenziarlo
				}
			});
			esciButton.setBounds(42, 747, 69, 29);
			contentPane.add(esciButton);
		} catch (SQLException e) {
			//errore query per tutti i progetti
			JOptionPane.showMessageDialog(null,
					"Impossibile ottenere tutti i progetti dal database.\nControllare che sia stabilita la connessione al database.",
					"Errore Interrogazione Database",
					JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che applica tutti i filtri
	private void applicaFiltri(ControllerProgettiSegreteria controller) {
		//ottiene tutti gli elementi dei filtri
		//nome progetto
		String nomeCercato = "%";
		if (!filtroNomeTextField.getText().isBlank())
			nomeCercato = filtroNomeTextField.getText();
		//ambito progetto
		AmbitoProgetto ambitoCercato = null;
		if (ambitoComboBox.getSelectedItem() != null)
			ambitoCercato= (AmbitoProgetto) ambitoComboBox.getSelectedItem();
		//tipologia progetto
		String tipologiaCercata = null;
		if (tipologiaComboBox.getSelectedItem() != null)
			tipologiaCercata=  tipologiaComboBox.getSelectedItem().toString();
		//scaduto
		String scaduto = "Entrambi";
		if (scadutoComboBox.getSelectedItem() != null)
			if (scadutoComboBox.getSelectedItem().toString().equals("Si"))
				scaduto = "Si";
			else
				scaduto = "No";
		//terminato
		String terminato = "Entrambi";
		if (terminatoComboBox.getSelectedItem() != null)
			if (terminatoComboBox.getSelectedItem().toString().equals("Si"))
				terminato = "Si";
			else
				terminato = "No";
		//richiama il controller
		try {
			dataModelTabella.setProgettiTabella(controller.ottieniProgettiFiltrati(nomeCercato,ambitoCercato,tipologiaCercata, scaduto, terminato));
			dataModelTabella.fireTableDataChanged();
		} catch (SQLException e) {
			//errore query per tutti i progetti filtrati
			JOptionPane.showMessageDialog(null,
					"Impossibile filtrare i progetti dal database.\nControllare che sia stabilita la connessione al database.",
					"Errore Interrogazione Database",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
