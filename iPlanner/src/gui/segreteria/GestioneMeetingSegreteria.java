/*Gui per la gestione dei meeting aziendali.
 *Qui è possibile visualizzare tutte le informazioni
 *dei meeting e filtrare tra essi.
 ***********************************************/

package gui.segreteria;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.Meeting;
import entita.SalaRiunione;
import gui.ErroreDialog;
import gui.cellRenderers.InvitatiListRenderer;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.DataComparator;
import gui.tableModels.MeetingTableModel;
import gui.tableModels.OrarioComparator;

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
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;

public class GestioneMeetingSegreteria extends JFrame {
	private JPanel contentPane;
	private JLabel inizioLabel;
	private JLabel fineLabel;
	private JLabel modalitàLabel;
	private JLabel titoloLabel;
	private JButton esciButton;
	private JTextArea progettoDiscussoTextArea;
	private JLabel refreshFiltriLabel;
	private JScrollPane tabellaScrollPanel;
	private JTable tabellaMeeting;
	private DefaultListModel invitatiListModel;
	private JButton gestisciSaleButton;
	private JLabel filtroSalaLabel;
	private JComboBox filtroSaleComboBox;
	private MeetingTableModel modelloTabellaMeeting;
	private JLabel filtroPiattaformaLabel;
	private JComboBox filtroPiattaformaComboBox;
	private JRadioButton fisicoRadioButton, telematicoRadioButton;
	private JLabel invitatiLabel;
	private JScrollPane partecipantiScrollPanel;
	private JSeparator separator;
	private JList invitatiList;
	
	private DateTimeFormatter formatoDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	private DateTimeFormatter formatoTime = DateTimeFormat.forPattern("HH:mm");
	
	public GestioneMeetingSegreteria(ControllerMeetingSegreteria controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingSegreteria.class.getResource("/icone/WindowIcon_16.png")));
		setResizable(false);
		setTitle("Gestione Meeting");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.tornaASegreteria();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 794, 769);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoPanel.setBounds(38, 94, 702, 164);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		inizioLabel = new JLabel("Inizio: N/A");
		inizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		inizioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		inizioLabel.setBounds(29, 10, 219, 23);
		infoPanel.add(inizioLabel);
		
		fineLabel = new JLabel("Fine: N/A");
		fineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fineLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		fineLabel.setBounds(29, 43, 219, 23);
		infoPanel.add(fineLabel);
		
		modalitàLabel = new JLabel("Sala: N/A");
		modalitàLabel.setHorizontalAlignment(SwingConstants.LEFT);
		modalitàLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		modalitàLabel.setBounds(29, 76, 219, 23);
		infoPanel.add(modalitàLabel);
		
		separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(352, 10, 2, 146);
		infoPanel.add(separator);
		
		partecipantiScrollPanel = new JScrollPane();
		partecipantiScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		partecipantiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		partecipantiScrollPanel.setBounds(404, 9, 277, 146);
		infoPanel.add(partecipantiScrollPanel);
		
		invitatiListModel = new DefaultListModel();
		invitatiList = new JList();
		InvitatiListRenderer invitatiRenderer = new InvitatiListRenderer();
		invitatiList.setCellRenderer(invitatiRenderer);
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setViewportView(invitatiList);
		
		invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setColumnHeaderView(invitatiLabel);
		
		progettoDiscussoTextArea = new JTextArea();
		progettoDiscussoTextArea.setLineWrap(true);
		progettoDiscussoTextArea.setEditable(false);
		progettoDiscussoTextArea.setText("Progetto Discusso: N/A");
		progettoDiscussoTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		progettoDiscussoTextArea.setBounds(29, 109, 277, 45);
		infoPanel.add(progettoDiscussoTextArea);
		
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel.setBounds(38, 291, 702, 41);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		telematicoRadioButton = new JRadioButton("Telematico");
		telematicoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		telematicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (telematicoRadioButton.isSelected()) {
					applicaFiltroTelematico(controller);
				} else {
					setModelloTabellaTuttiMeeting(controller);
					modelloTabellaMeeting.fireTableDataChanged();
				}
			}
		});
		telematicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		telematicoRadioButton.setBounds(74, 9, 101, 23);
		comandiPanel.add(telematicoRadioButton);

		fisicoRadioButton = new JRadioButton("Fisico");
		fisicoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fisicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fisicoRadioButton.isSelected()) {
					applicaFiltroFisico(controller);
				} else {
					setModelloTabellaTuttiMeeting(controller);
					modelloTabellaMeeting.fireTableDataChanged();
				}
			}
		});
		fisicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		fisicoRadioButton.setBounds(177, 9, 75, 23);
		comandiPanel.add(fisicoRadioButton);
		
		inizializzaFiltroSaleComboBox(controller);
		filtroSaleComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroSaleComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filtroSaleComboBox.getSelectedItem() != null)
					applicaFiltroSala(controller, (SalaRiunione) filtroSaleComboBox.getSelectedItem());
				else {
					setModelloTabellaTuttiMeeting(controller);
					modelloTabellaMeeting.fireTableDataChanged();
				}
			}
		});
		filtroSaleComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroSaleComboBox.setBounds(308, 9, 108, 22);
		filtroSaleComboBox.setUI(new BasicComboBoxUI());
		comandiPanel.add(filtroSaleComboBox);

		filtroSalaLabel = new JLabel("Sala:");
		filtroSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroSalaLabel.setBounds(274, 13, 35, 14);
		comandiPanel.add(filtroSalaLabel);
		
		inizializzaFiltroPiattaformaComboBox(controller);
		filtroPiattaformaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroPiattaformaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filtroPiattaformaComboBox.getSelectedItem() != null)
					applicaFiltroPiattaforma(controller, filtroPiattaformaComboBox.getSelectedItem().toString());
				else {
					setModelloTabellaTuttiMeeting(controller);
					modelloTabellaMeeting.fireTableDataChanged();
				}
			}
		});
		filtroPiattaformaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroPiattaformaComboBox.setBounds(517, 9, 151, 22);
		filtroPiattaformaComboBox.setUI(new BasicComboBoxUI());
		comandiPanel.add(filtroPiattaformaComboBox);

		filtroPiattaformaLabel = new JLabel("Piattaforma:");
		filtroPiattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroPiattaformaLabel.setBounds(434, 13, 84, 14);
		comandiPanel.add(filtroPiattaformaLabel);
		
		refreshFiltriLabel = new JLabel("");
		refreshFiltriLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshFiltriLabel.setToolTipText("Reset dei filtri");
		refreshFiltriLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		
		tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		tabellaScrollPanel.setBounds(38, 343, 702, 348);
		contentPane.add(tabellaScrollPanel);
		
		modelloTabellaMeeting = new MeetingTableModel();
		setModelloTabellaTuttiMeeting(controller);
		tabellaMeeting = new JTable(modelloTabellaMeeting);
		tabellaMeeting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabellaMeeting.setFont(new Font("Consolas", Font.PLAIN, 11));
		tabellaMeeting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaMeeting.getModel());
		DataComparator comparatorDate = new DataComparator();
		OrarioComparator comparatorOrari = new OrarioComparator();
		sorter.setComparator(0,comparatorDate);
		sorter.setComparator(1,comparatorDate);
		sorter.setComparator(2, comparatorOrari);
		sorter.setComparator(3, comparatorOrari);
		tabellaMeeting.setRowSorter(sorter);
		tabellaMeeting.getRowSorter().toggleSortOrder(0);
		tabellaMeeting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rigaSelezionata = tabellaMeeting.getSelectedRow();
				rigaSelezionata = tabellaMeeting.convertRowIndexToModel(rigaSelezionata);
				Meeting meetingSelezionato = modelloTabellaMeeting.getSelected(rigaSelezionata);
				
				inizioLabel.setText("Inizio: " + meetingSelezionato.getDataInizio().toString(formatoDate) + " " + meetingSelezionato.getOraInizio().toString(formatoTime));
				fineLabel.setText("Fine: " + meetingSelezionato.getDataFine().toString(formatoDate) + " " + meetingSelezionato.getOraFine().toString(formatoTime));
				if (meetingSelezionato.getModalita().equals("Telematico")) {
					modalitàLabel.setText("Piattaforma: " + meetingSelezionato.getPiattaforma());
				}
				else {
					modalitàLabel.setText("Sala: " + meetingSelezionato.getSala().toString());
				}
				progettoDiscussoTextArea.setText("Progetto Discusso: " + meetingSelezionato.getProgettoDiscusso().getNomeProgetto());
				invitatiListModel.clear();
				setModelloInvitatiList(controller,meetingSelezionato);
			}
		});
		tabellaScrollPanel.setViewportView(tabellaMeeting);
		
		titoloLabel = new JLabel("Gestione Meeting");
		titoloLabel.setIcon(new ImageIcon(GestioneMeetingSegreteria.class.getResource("/icone/meeting_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(38, 21, 275, 61);
		contentPane.add(titoloLabel);
		
		gestisciSaleButton = new JButton("Gestisci Sale");
		gestisciSaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGestioneSale();
			}
		});
		gestisciSaleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				gestisciSaleButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				gestisciSaleButton.setBackground(Color.WHITE);
			}	
		});
		gestisciSaleButton.setBounds(624, 702, 117, 23);
		gestisciSaleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciSaleButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		gestisciSaleButton.setBackground(Color.WHITE);
		gestisciSaleButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(gestisciSaleButton);
		
		esciButton = new JButton("Esci");
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.tornaASegreteria();
			}
		});
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
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		esciButton.setBackground(Color.WHITE);
		
		esciButton.setBounds(38, 702, 66, 23);
		contentPane.add(esciButton);
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	private void applicaFiltroTelematico(ControllerMeetingSegreteria controller) {
		try {
			fisicoRadioButton.setSelected(false);
			filtroPiattaformaComboBox.setSelectedIndex(0);
			filtroSaleComboBox.setSelectedIndex(0);
			modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingTelematici());
			modelloTabellaMeeting.fireTableDataChanged();
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void applicaFiltroPiattaforma(ControllerMeetingSegreteria controller, String piattaforma) {
		try {
			fisicoRadioButton.setSelected(false);
			telematicoRadioButton.setSelected(false);
			filtroSaleComboBox.setSelectedIndex(0);
			modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingPiattaforma(piattaforma));
			modelloTabellaMeeting.fireTableDataChanged();
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void applicaFiltroFisico(ControllerMeetingSegreteria controller) {
		try {
			telematicoRadioButton.setSelected(false);
			filtroSaleComboBox.setSelectedIndex(0);
			filtroPiattaformaComboBox.setSelectedIndex(0);
			modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingFisici());
			modelloTabellaMeeting.fireTableDataChanged();
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void applicaFiltroSala(ControllerMeetingSegreteria controller, SalaRiunione sala) {
		try {
			fisicoRadioButton.setSelected(false);
			telematicoRadioButton.setSelected(false);
			filtroPiattaformaComboBox.setSelectedIndex(0);
			modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingSala(sala));
			modelloTabellaMeeting.fireTableDataChanged();
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	public void aggiornaFiltroSale(ControllerMeetingSegreteria controller) {
		filtroSaleComboBox.removeAllItems();
		try {
			for (SalaRiunione sala: controller.ottieniSale())
				filtroSaleComboBox.addItem(sala);
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void setModelloTabellaTuttiMeeting(ControllerMeetingSegreteria controller) {
		try {
			modelloTabellaMeeting.setMeetingTabella(controller.ottieniMeeting());
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void inizializzaFiltroSaleComboBox(ControllerMeetingSegreteria controller) {
		try {
			filtroSaleComboBox = new JComboBox(controller.ottieniSale().toArray());
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void inizializzaFiltroPiattaformaComboBox(ControllerMeetingSegreteria controller) {
		try {
			filtroPiattaformaComboBox = new JComboBox(controller.ottieniPiattaforme().toArray());
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void setModelloInvitatiList(ControllerMeetingSegreteria controller, Meeting meeting) {
		try {
			invitatiListModel.addAll(controller.ottieniInvitatiMeeting(meeting));
			invitatiList.setModel(invitatiListModel);
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
}
