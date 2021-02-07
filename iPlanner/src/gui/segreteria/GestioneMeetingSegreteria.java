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
import javax.swing.event.ListSelectionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

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
	private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	private DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
	
	//METODI
	//-----------------------------------------------------------------
	
	public GestioneMeetingSegreteria(ControllerMeetingSegreteria controller) {
		setResizable(false);
		setTitle("Gestione Meeting");
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
		
		//Info Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		infoPanel.setBounds(38, 94, 702, 164);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//Label inizio meeting
		JLabel inizioLabel = new JLabel("Inizio: 16/12/1018 12:33");
		inizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		inizioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		inizioLabel.setBounds(29, 10, 219, 23);
		infoPanel.add(inizioLabel);
		
		//Label fine meeting
		JLabel fineLabel = new JLabel("Fine: 16/12/2018 13:00");
		fineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fineLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		fineLabel.setBounds(29, 43, 219, 23);
		infoPanel.add(fineLabel);
		
		//Label modalità del progetto (sala o piattaforma)
		JLabel modalitàLabel = new JLabel("Sala: Sala 109 (200)");
		modalitàLabel.setHorizontalAlignment(SwingConstants.LEFT);
		modalitàLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		modalitàLabel.setBounds(29, 76, 219, 23);
		infoPanel.add(modalitàLabel);
		
		//Separatore
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(345, 9, 12, 146);
		infoPanel.add(separator);
		
		//Scroll Panel per gli invitati
		JScrollPane partecipantiScrollPanel = new JScrollPane();
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
		progettoDiscussoTextArea.setText("Progetto Discusso: Progetto Abstergo");
		progettoDiscussoTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		progettoDiscussoTextArea.setBounds(29, 109, 277, 45);
		infoPanel.add(progettoDiscussoTextArea);
		
		//Panel per comandi e filtri
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comandiPanel.setBounds(38, 291, 702, 41);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		//RadioButton per filtro meeting telematici
		JRadioButton telematicoRadioButton = new JRadioButton("Telematico");
		//RadioButton selezionato/non selezionato
		telematicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (telematicoRadioButton.isSelected())
					try {
						fisicoRadioButton.setSelected(false);
						applicaFiltroTelematico(controller);
					} catch (SQLException e2) {
						//errore enumerazione modalità (valori diversi da Fisico o Telematico)
						if (e2.getSQLState().equals("22P02")) {
							JOptionPane.showMessageDialog(null,
								"Errore applicazione filtro. Contattare uno sviluppatore.",
								"Errore Enumerazioni Filtri",
								JOptionPane.ERROR_MESSAGE);
						}
						//altri errori non contemplati (es: ResultSet vuoto)
						else {
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere meeting fisici dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
						}
					}
				else
					try {
						dataModelTabella.setMeetingTabella(controller.ottieniMeeting());
					} catch (SQLException e1) {
						//errore select per tutti i meeting nel database (es: ResultSet vuoto)
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i meeting dal database.\nControllare che la connessione al database sia stabilita\naltrimenti creare prima un meeting.",
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
				if (telematicoRadioButton.isSelected()) {
					try {
						telematicoRadioButton.setSelected(false);
						applicaFiltroFisico(controller);
					} catch (SQLException e1) {
						//errore enumerazione modalità (valori diversi da Fisico o Telematico)
						if (e1.getSQLState().equals("22P02")) {
							JOptionPane.showMessageDialog(null,
								"Errore applicazione filtro. Contattare uno sviluppatore.",
								"Errore Enumerazioni Filtri",
								JOptionPane.ERROR_MESSAGE);
						}
						//altri errori non contemplati (es: ResultSet vuoto)
						else {
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere meeting fisici dal database.\nControllare che la connessione al database sia stabilita.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else
					try {
						dataModelTabella.setMeetingTabella(controller.ottieniMeeting());
					} catch (SQLException e1) {
						//errore select per tutti i meeting nel database (es: ResultSet vuoto)
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere tutti i meeting dal database.\nControllare che la connessione al database sia stabilita\naltrimenti creare prima un meeting.",
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
			filtroSaleComboBox = new JComboBox(controller.ottieniSale().toArray());
			//Selezione combo box
			filtroSaleComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						applicaFiltroSala(controller, (SalaRiunione) filtroSaleComboBox.getSelectedItem());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			filtroSaleComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			filtroSaleComboBox.setBounds(308, 9, 108, 22);
			comandiPanel.add(filtroSaleComboBox);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//Label "Sala:" in filtri
		JLabel filtroSalaLabel = new JLabel("Sala:");
		filtroSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroSalaLabel.setBounds(274, 13, 35, 14);
		comandiPanel.add(filtroSalaLabel);
		
		//ComboBox per filtro piattaforma
		try {
			filtroPiattaformaComboBox = new JComboBox(controller.ottieniPiattaforme().toArray());
			//Selezione nella combobox
			filtroPiattaformaComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						applicaFiltroPiattaforma(controller, filtroPiattaformaComboBox.getSelectedItem().toString());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			filtroPiattaformaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
			filtroPiattaformaComboBox.setBounds(517, 9, 151, 22);
			comandiPanel.add(filtroPiattaformaComboBox);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
				try {
					dataModelTabella.setMeetingTabella(controller.ottieniMeeting());
					dataModelTabella.fireTableDataChanged();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		refreshFiltriLabel.setIcon(new ImageIcon(GestioneMeetingSegreteria.class.getResource("/icone/refresh.png")));
		refreshFiltriLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		refreshFiltriLabel.setBounds(25, 11, 21, 18);
		comandiPanel.add(refreshFiltriLabel);
		
		//Scroll Panel per la tabella del meeting
		JScrollPane tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBounds(38, 343, 702, 348);
		contentPane.add(tabellaScrollPanel);
		
		//Tabella del meeting
		dataModelTabella = new MeetingTableModel();
		try {
			dataModelTabella.setMeetingTabella(controller.ottieniMeeting());
			tabellaMeeting = new JTable(dataModelTabella);
			tabellaMeeting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabellaMeeting.setFont(new Font("Consolas", Font.PLAIN, 11));
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaMeeting.getModel());	//sorter
			tabellaMeeting.setRowSorter(sorter);
			tabellaMeeting.getRowSorter().toggleSortOrder(0);
			//Click mouse sinistro
			tabellaMeeting.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = tabellaMeeting.getSelectedRow();
					row = tabellaMeeting.convertRowIndexToModel(row);	//converte la riga correttamente in caso di sorting
					Meeting meeting = dataModelTabella.getSelected(row);	//prende il meeting selezionato in base alla riga
					
					//Aggiorna GUI
					inizioLabel.setText("Inizio: " + meeting.getDataInizio().toString(formatDate) + " " + meeting.getOraInizio().toString(formatHour));	//inizio meeting
					fineLabel.setText("Fine: " + meeting.getDataFine().toString(formatDate) + " " + meeting.getOraFine().toString(formatHour));	//fine meeting
					//sala o piattaforma
					if (meeting.getModalita().equals("Telematico")) {
						modalitàLabel.setText("Piattaforma: " + meeting.getPiattaforma());
					}
					else {
						modalitàLabel.setText("Sala: " + meeting.getSala().toString());
					}
					//progetto discusso
					progettoDiscussoTextArea.setText("Progetto Discusso: " + meeting.getProgettoDiscusso().getNomeProgetto());
					//partecipanti
					try {
						invitatiListModel.clear();
						invitatiListModel.addAll(controller.ottieniPartecipanti(meeting));
						invitatiList.setModel(invitatiListModel);
					} catch (SQLException e1) {
						//errore select per tutti gli invitati a un meeting nel database (es: ResultSet vuoto)
							JOptionPane.showMessageDialog(null,
								"Impossibile ottenere invitati al meeting dal database.\nControllare che la connessione al database sia stabilita\naltrimenti aggiungere prima un partecipante al meeting.",
								"Errore Interrogazione Database",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			tabellaScrollPanel.setViewportView(tabellaMeeting);
		} catch (SQLException e1) {
			//errore select per tutti i meeting nel database (es: ResultSet vuoto)
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i meeting dal database.\nControllare che la connessione al database sia stabilita\naltrimenti creare prima un meeting.",
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
		filtroSaleComboBox.removeAllItems();
		try {
			for (SalaRiunione sala: controller.ottieniSale())
				filtroSaleComboBox.addItem(sala);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
