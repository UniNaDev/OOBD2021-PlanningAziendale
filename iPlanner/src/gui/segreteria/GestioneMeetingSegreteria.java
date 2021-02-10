package gui.segreteria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.Meeting;
import entita.PartecipazioneMeeting;
import entita.SalaRiunione;
import gui.cellRenderers.InvitatiListRenderer;
import gui.tableModels.MeetingTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.segreteria.ControllerMeetingSegreteria;
import customUI.CustomScrollBarUI;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class GestioneMeetingSegreteria extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTable tabellaMeeting;
	private DefaultListModel invitatiListModel;
	private JComboBox filtroSaleComboBox;
	private MeetingTableModel dataModelTabella;
	private JComboBox filtroPiattaformaComboBox;
	private JRadioButton fisicoRadioButton;
	
	//Altri attributi
	private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");	//formato date
	private DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");	//formato orari
	
	//METODI
	//-----------------------------------------------------------------
	
	public GestioneMeetingSegreteria(ControllerMeetingSegreteria controller) {
		setResizable(false);
		setTitle("Gestione Meeting");
		//Chiusura finestra
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.tornaAiPlanner();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 794, 769);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Viene visualizzata al centro dello schermo
		setLocationRelativeTo(null);
		
		//Info Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoPanel.setBounds(38, 94, 702, 164);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//Label inizio meeting
		JLabel inizioLabel = new JLabel("Inizio: N/A");
		inizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		inizioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		inizioLabel.setBounds(29, 10, 219, 23);
		infoPanel.add(inizioLabel);
		
		//Label fine meeting
		JLabel fineLabel = new JLabel("Fine: N/A");
		fineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fineLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		fineLabel.setBounds(29, 43, 219, 23);
		infoPanel.add(fineLabel);
		
		//Label modalità del progetto (sala o piattaforma)
		JLabel modalitàLabel = new JLabel("Sala: N/A");
		modalitàLabel.setHorizontalAlignment(SwingConstants.LEFT);
		modalitàLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		modalitàLabel.setBounds(29, 76, 219, 23);
		infoPanel.add(modalitàLabel);
		
		//Separatore
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(352, 10, 2, 146);
		infoPanel.add(separator);
		
		//Scroll Panel per gli invitati
		JScrollPane partecipantiScrollPanel = new JScrollPane();
		partecipantiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		partecipantiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.setBounds(404, 9, 277, 146);
		infoPanel.add(partecipantiScrollPanel);
		
		//List invitati
		invitatiListModel = new DefaultListModel();
		JList invitatiList = new JList();
		InvitatiListRenderer invitatiRenderer = new InvitatiListRenderer();
		invitatiList.setCellRenderer(invitatiRenderer);
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setViewportView(invitatiList);
		
		//Label "Invitati" in lista
		JLabel invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setColumnHeaderView(invitatiLabel);
		
		//Text Area progetto discusso nel meeting
		JTextArea progettoDiscussoTextArea = new JTextArea();
		progettoDiscussoTextArea.setLineWrap(true);
		progettoDiscussoTextArea.setEditable(false);
		progettoDiscussoTextArea.setText("Progetto Discusso: N/A");
		progettoDiscussoTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		progettoDiscussoTextArea.setBounds(29, 109, 277, 45);
		infoPanel.add(progettoDiscussoTextArea);
		
		//Panel per comandi e filtri
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel.setBounds(38, 291, 702, 41);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		//RadioButton per filtro meeting telematici
		JRadioButton telematicoRadioButton = new JRadioButton("Telematico");
		//RadioButton selezionato/non selezionato
		telematicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//telematico radio button = true
				if (telematicoRadioButton.isSelected())
					try {
						fisicoRadioButton.setSelected(false);	//fisico radio button = false
						applicaFiltroTelematico(controller);	//applica il filtro e ottiene la lista di meeting solo telematici
					} catch (SQLException e2) {
						//errore enumerazione modalità (valori diversi da "Fisico" o "Telematico")
						if (e2.getSQLState().equals("22P02")) {
							JOptionPane.showMessageDialog(null,
								"Errore applicazione filtro. Contattare uno sviluppatore.",
								"Errore Enumerazioni Filtri",
								JOptionPane.ERROR_MESSAGE);
						}
						//altri errori non contemplati
						else {
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere meeting fisici dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
						}
					}
				//telematico radio button = false
				else
					try {
						dataModelTabella.setMeetingTabella(controller.ottieniMeeting());	//ottiene tutti i meeting
					} catch (SQLException e1) {
						//errore select per tutti i meeting nel database
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i meeting dal database.\nControllare che la connessione al database sia stabilita.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		telematicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		telematicoRadioButton.setBounds(74, 9, 101, 23);
		comandiPanel.add(telematicoRadioButton);
		
		//RadioButton per filtro meeting fisici
		fisicoRadioButton = new JRadioButton("Fisico");
		//RadioButton selezionato/non selezionato
		fisicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fisico radio button = true
				if (fisicoRadioButton.isSelected()) {
					try {
						fisicoRadioButton.setSelected(false); //telematico radio button = false
						applicaFiltroFisico(controller);	//applica il filtro e ottiene tutti i meeting fisici
					} catch (SQLException e1) {
						//errore enumerazione modalità (valori diversi da Fisico o Telematico)
						if (e1.getSQLState().equals("22P02")) {
							JOptionPane.showMessageDialog(null,
								"Errore applicazione filtro. Contattare uno sviluppatore.",
								"Errore Enumerazioni Filtri",
								JOptionPane.ERROR_MESSAGE);
						}
						//altri errori non contemplati
						else {
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere meeting fisici dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				//fisico radio button = false
				else
					try {
						dataModelTabella.setMeetingTabella(controller.ottieniMeeting());	//ottiene tutti i meeting
					} catch (SQLException e1) {
						//errore select per tutti i meeting nel database
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i meeting dal database.\nControllare che la connessione al database sia stabilita.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
					}
				}
		});
		fisicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		fisicoRadioButton.setBounds(177, 9, 75, 23);
		comandiPanel.add(fisicoRadioButton);
		
		//ComboBox per filtro sale
		try {
			filtroSaleComboBox = new JComboBox(controller.ottieniSale().toArray());	//ottiene tutte le sale per il filtro
			filtroSaleComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			//Selezione combo box
			filtroSaleComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						//se l'oggetto selezionato è una sala esistente
						if (!(filtroSaleComboBox.getSelectedItem() == null))
							applicaFiltroSala(controller, (SalaRiunione) filtroSaleComboBox.getSelectedItem());	//applica il filtro e ottiene i meeting in quella sala
						//se l'oggetto selezionato è null
						else {
							dataModelTabella.setMeetingTabella(controller.ottieniMeeting());	//ottiene tutti i meeting senza filtri
							dataModelTabella.fireTableDataChanged();	//aggiorna la tabella
						}
					} catch (SQLException e1) {
						//errore select per tutti i meeting in una sala nel database
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i meeting nella sala selezionata dal database.\nControllare che la connessione al database sia stabilita.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			filtroSaleComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			filtroSaleComboBox.setBounds(308, 9, 108, 22);
			filtroSaleComboBox.setUI(new BasicComboBoxUI());
			comandiPanel.add(filtroSaleComboBox);
		} catch (SQLException e2) {
			//errore select per tutte le sale nel database
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le sale dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		//Label "Sala:" in filtri
		JLabel filtroSalaLabel = new JLabel("Sala:");
		filtroSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroSalaLabel.setBounds(274, 13, 35, 14);
		comandiPanel.add(filtroSalaLabel);
		
		//ComboBox per filtro piattaforma
		try {
			filtroPiattaformaComboBox = new JComboBox(controller.ottieniPiattaforme().toArray());	//ottiene tutte le piattaforme per il filtro
			filtroPiattaformaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
			//Selezione nella combobox
			filtroPiattaformaComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						//se la piattaforma selezionata esiste
						if (!(filtroPiattaformaComboBox.getSelectedItem() == null))
							applicaFiltroPiattaforma(controller, filtroPiattaformaComboBox.getSelectedItem().toString());	//applica il filtro e ottiene i meeting su quella piattaforma
						//se la piattaforma selezionata è null
						else {
							dataModelTabella.setMeetingTabella(controller.ottieniMeeting());	//ottiene tutti i meeting
							dataModelTabella.fireTableDataChanged();	//aggiorna la tabella
						}
							
					} catch (SQLException e1) {
						//errore select per tutti i meeting su una piattaforma nel database
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i meeting sulla piattaforma selezionata dal database.\nControllare che la connessione al database sia stabilita.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			filtroPiattaformaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			filtroPiattaformaComboBox.setBounds(517, 9, 151, 22);
			filtroPiattaformaComboBox.setUI(new BasicComboBoxUI());
			comandiPanel.add(filtroPiattaformaComboBox);
		} catch (SQLException e2) {
			//errore select per tutte le piattaforme nel database
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le piattaforme dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}

		//Label "Piattaforma:" in filtri
		JLabel filtroPiattaformaLabel = new JLabel("Piattaforma:");
		filtroPiattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroPiattaformaLabel.setBounds(434, 13, 84, 14);
		comandiPanel.add(filtroPiattaformaLabel);
		
		//Label reset dei filtri
		JLabel refreshFiltriLabel = new JLabel("");
		refreshFiltriLabel.setToolTipText("Reset dei filtri");
		//Click sulla label
		refreshFiltriLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//setta tutti i filtri a null/false
				telematicoRadioButton.setSelected(false);
				fisicoRadioButton.setSelected(false);
				filtroSaleComboBox.setSelectedItem(null);
				filtroPiattaformaComboBox.setSelectedItem(null);
			}
		});
		refreshFiltriLabel.setIcon(new ImageIcon(GestioneMeetingSegreteria.class.getResource("/icone/refresh.png")));
		refreshFiltriLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		refreshFiltriLabel.setBounds(25, 11, 21, 18);
		comandiPanel.add(refreshFiltriLabel);
		
		//Scroll Panel per la tabella del meeting
		JScrollPane tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		tabellaScrollPanel.setBounds(38, 343, 702, 348);
		contentPane.add(tabellaScrollPanel);
		
		//Tabella del meeting
		dataModelTabella = new MeetingTableModel();
		try {
			dataModelTabella.setMeetingTabella(controller.ottieniMeeting());	//ottiene tutti i meeting
			tabellaMeeting = new JTable(dataModelTabella);	//crea la tabella
			tabellaMeeting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabellaMeeting.setFont(new Font("Consolas", Font.PLAIN, 11));
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaMeeting.getModel());	//sorter
			tabellaMeeting.setRowSorter(sorter);
			tabellaMeeting.getRowSorter().toggleSortOrder(0);	//sort by data inizio di default
			//Click mouse sinistro
			tabellaMeeting.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = tabellaMeeting.getSelectedRow();	//ottiene la riga selezionata
					row = tabellaMeeting.convertRowIndexToModel(row);	//converte la riga correttamente in caso di sorting
					Meeting meeting = dataModelTabella.getSelected(row);	//prende il meeting selezionato in base alla riga
					
					//Aggiorna GUI
					inizioLabel.setText("Inizio: " + meeting.getDataInizio().toString(formatDate) + " " + meeting.getOraInizio().toString(formatHour));	//inizio meeting
					fineLabel.setText("Fine: " + meeting.getDataFine().toString(formatDate) + " " + meeting.getOraFine().toString(formatHour));	//fine meeting
					//sala o piattaforma
					if (meeting.getModalita().equals("Telematico")) {
						modalitàLabel.setText("Piattaforma: " + meeting.getPiattaforma());	//piattaforma
					}
					else {
						modalitàLabel.setText("Sala: " + meeting.getSala().toString());	//sala
					}
					progettoDiscussoTextArea.setText("Progetto Discusso: " + meeting.getProgettoDiscusso().getNomeProgetto()); 	//progetto discusso
					//partecipanti
					try {
						invitatiListModel.clear();	//pulisce la lista di partecipanti
						invitatiListModel.addAll(controller.ottieniPartecipanti(meeting)); //riempie la lista di partecipanti con quelli ottenuti dal DB
						invitatiList.setModel(invitatiListModel);	//aggiorna la lista
					} catch (SQLException e1) {
						//errore select per tutti gli invitati a un meeting nel database
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere invitati al meeting dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			tabellaScrollPanel.setViewportView(tabellaMeeting);
		} catch (SQLException e1) {
			//errore select per tutti i meeting nel database
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i meeting dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
		
		//Label "Gestione Meeting" titolo
		JLabel titoloLabel = new JLabel("Gestione Meeting");
		titoloLabel.setIcon(new ImageIcon(GestioneMeetingSegreteria.class.getResource("/icone/meeting_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(38, 21, 275, 61);
		contentPane.add(titoloLabel);
		
		//Button gestione delle sale
		JButton gestisciSaleButton = new JButton("Gestisci Sale");
		//Click pulsante
		gestisciSaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//apre finestra gestione sale
				controller.apriGestioneSale();
			}
		});
		//Eventi connessi al button
		gestisciSaleButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				gestisciSaleButton.setBackground(Color.LIGHT_GRAY);	//lo evidenzia
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) 
			{
				gestisciSaleButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}	
		});
		gestisciSaleButton.setBounds(624, 702, 117, 23);
		gestisciSaleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciSaleButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		gestisciSaleButton.setBackground(Color.WHITE);
		gestisciSaleButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(gestisciSaleButton);
		
		//Button per tornare alla schermata principale
		JButton esciButton = new JButton("Esci");
		//Click pulsante
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tornaAiPlanner(); //torna alla schermata principale
			}
		});
		//Eventi connessi al button
		esciButton.addMouseListener(new MouseAdapter() {
			//mouse sopra il pulsante
			@Override
			public void mouseEntered(MouseEvent e) {
				esciButton.setBackground(Color.LIGHT_GRAY);	//lo evidenzia
			}
			//mouse fuori dal pulsante
			@Override
			public void mouseExited(MouseEvent e) {
				esciButton.setBackground(Color.WHITE);	//smette di evidenziarlo
			}	
		});
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		esciButton.setBackground(Color.WHITE);
		
		esciButton.setBounds(38, 702, 66, 23);
		contentPane.add(esciButton);
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	
	//Metodo che filtra i meeting prendendo solo quelli telematici
	private void applicaFiltroTelematico(ControllerMeetingSegreteria controller) throws SQLException {
		dataModelTabella.setMeetingTabella(controller.filtraMeetingTelematici());
		dataModelTabella.fireTableDataChanged();
	}
	
	//Metodo che filtra i meeting prendendo solo quelli di una certa piattaforma
	private void applicaFiltroPiattaforma(ControllerMeetingSegreteria controller, String piattaforma) throws SQLException {
		dataModelTabella.setMeetingTabella(controller.filtraMeetingPiattaforma(piattaforma));
		dataModelTabella.fireTableDataChanged();
	}
	
	//Metodo che filtra i meeting prendendo solo quelli fisici
	private void applicaFiltroFisico(ControllerMeetingSegreteria controller) throws SQLException {
		dataModelTabella.setMeetingTabella(controller.filtraMeetingFisici());
		dataModelTabella.fireTableDataChanged();
	}
	
	//Metodo che filtra i meeting prendendo solo quelli in una certa sala
	private void applicaFiltroSala(ControllerMeetingSegreteria controller, SalaRiunione sala) throws SQLException {
		dataModelTabella.setMeetingTabella(controller.filtraMeetingSala(sala));
		dataModelTabella.fireTableDataChanged();
	}
	
	//Metodo che aggiorna le sale nella combobox
	public void aggiornaSale(ControllerMeetingSegreteria controller) {
		filtroSaleComboBox.removeAllItems();	//rimuove tutte le sale dalla combobox
		try {
			for (SalaRiunione sala: controller.ottieniSale())
				filtroSaleComboBox.addItem(sala);	//aggiunge ogni sala del database
		} catch (SQLException e) {
			//errore select per tutte le sale nel database
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutte le sale dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}
	}
}
