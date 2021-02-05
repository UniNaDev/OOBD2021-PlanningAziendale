package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerMeetingSegreteria;
import entita.Meeting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.awt.Color;
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
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestioneMeetingSegreteria extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTable tabellaMeeting;
	private DefaultListModel invitatiListModel;
	
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
		setBounds(100, 100, 867, 784);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Info Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		infoPanel.setBounds(74, 94, 702, 193);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//Label inizio meeting
		JLabel inizioLabel = new JLabel("Inizio: 16/12/1018 12:33");
		inizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		inizioLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		inizioLabel.setBounds(30, 11, 219, 23);
		infoPanel.add(inizioLabel);
		
		//Label fine meeting
		JLabel fineLabel = new JLabel("Fine: 16/12/2018 13:00");
		fineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fineLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		fineLabel.setBounds(30, 45, 219, 23);
		infoPanel.add(fineLabel);
		
		//Label modalità del progetto (sala o piattaforma)
		JLabel modalitàLabel = new JLabel("Sala: Sala 109 (200)");
		modalitàLabel.setHorizontalAlignment(SwingConstants.LEFT);
		modalitàLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		modalitàLabel.setBounds(30, 79, 219, 23);
		infoPanel.add(modalitàLabel);
		
		//Separatore
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(345, 11, 12, 171);
		infoPanel.add(separator);
		
		//Scroll Panel per gli invitati
		JScrollPane partecipantiScrollPanel = new JScrollPane();
		partecipantiScrollPanel.setBounds(404, 11, 277, 171);
		infoPanel.add(partecipantiScrollPanel);
		
		//List invitati
		invitatiListModel = new DefaultListModel();
		JList invitatiList = new JList();
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setViewportView(invitatiList);
		
		//Label "Invitati" in lista
		JLabel invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		partecipantiScrollPanel.setColumnHeaderView(invitatiLabel);
		
		//Button gestione delle sale
		JButton gestisciSaleButton = new JButton("Gestisci Sale");
		gestisciSaleButton.setEnabled(false);
		gestisciSaleButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		gestisciSaleButton.setBounds(29, 103, 121, 23);
		infoPanel.add(gestisciSaleButton);
		
		//Text Area progetto discusso nel meeting
		JTextArea progettoDiscussoTextArea = new JTextArea();
		progettoDiscussoTextArea.setLineWrap(true);
		progettoDiscussoTextArea.setEditable(false);
		progettoDiscussoTextArea.setText("Progetto Discusso: Progetto Abstergo");
		progettoDiscussoTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		progettoDiscussoTextArea.setBounds(30, 137, 277, 45);
		infoPanel.add(progettoDiscussoTextArea);
		
		//Panel per comandi e filtri
		JPanel comandiPanel = new JPanel();
		comandiPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comandiPanel.setBounds(74, 319, 702, 61);
		contentPane.add(comandiPanel);
		comandiPanel.setLayout(null);
		
		//Scroll Panel per la tabella del meeting
		JScrollPane tabellaScrollPanel = new JScrollPane();
		tabellaScrollPanel.setBounds(74, 417, 702, 274);
		contentPane.add(tabellaScrollPanel);
		
		//Tabella del meeting
		MeetingTableModel dataModelTabella = new MeetingTableModel();
		try {
			dataModelTabella.setMeetingTabella(controller.ottieniMeeting());
			tabellaMeeting = new JTable(dataModelTabella);
			//Click mouse sinistro
			tabellaMeeting.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = tabellaMeeting.getSelectedRow();
					Meeting meeting = dataModelTabella.getSelected(row);	//prende il meeting selezionato in base alla riga
					
					//Aggiorna GUI
					inizioLabel.setText("Inizio: " + meeting.getDataInizio().toString(formatDate) + " " + meeting.getOraInizio().toString(formatHour));	//inizio meeting
					fineLabel.setText("Fine: " + meeting.getDataFine().toString(formatDate) + " " + meeting.getOraFine().toString(formatHour));	//fine meeting
					//sala o piattaforma
					if (meeting.getModalita().equals("Telematico")) {
						gestisciSaleButton.setEnabled(false);
						modalitàLabel.setText("Piattaforma: " + meeting.getPiattaforma());
					}
					else {
						gestisciSaleButton.setEnabled(true);
						modalitàLabel.setText("Sala: " + meeting.getSala().toString());
					}
					//progetto discusso
					progettoDiscussoTextArea.setText("Progetto Discusso: " + meeting.getProgettoDiscusso().getNomeProgetto());
					//partecipanti
					try {
						invitatiListModel.addAll(controller.ottieniPartecipanti(meeting));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabellaMeeting.getModel());	//sorter
			tabellaMeeting.setRowSorter(sorter);
			tabellaMeeting.getRowSorter().toggleSortOrder(0);
			tabellaScrollPanel.setViewportView(tabellaMeeting);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Label "Gestione Meeting" titolo
		JLabel titoloLabel = new JLabel("Gestione Meeting");
		titoloLabel.setIcon(new ImageIcon(GestioneMeetingSegreteria.class.getResource("/icone/meeting_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(65, 22, 275, 61);
		contentPane.add(titoloLabel);
	}
}
