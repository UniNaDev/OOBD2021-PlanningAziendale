/*Finestra Gestione Meeting per il dipendente.
 * Qui può creare nuovi meeting, modificare o eliminare quelli di cui è organizzatore,
 * filtrare la lista di meeting in base a vari criteri
 * e passare alle finestra per gestire e inserire i partecipanti al meeting selezionato.
 *******************************************************************************************/

package gui.dipendente;

import java.awt.BorderLayout;
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
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import controller.dipendente.ControllerMeeting;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import gui.cellRenderers.InvitatiListRenderer;
import gui.cellRenderers.ProgettoDiscussoListRenderer;
import gui.customUI.CustomScrollBarUI;
import gui.tableModels.DataComparator;
import gui.tableModels.MeetingTableModel;
import gui.tableModels.OrarioComparator;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;

public class GestioneMeetingDipendente extends JFrame {

	private JPanel contentPane;
	private JLabel gestioneMeetingLabel;
	private JComboBox dataInizioGiornoComboBox;
	private JComboBox dataInizioMeseComboBox;
	private JComboBox dataInizioAnnoComboBox;
	private JComboBox dataFineGiornoComboBox;
	private JComboBox dataFineMeseComboBox;
	private JComboBox dataFineAnnoComboBox;
	private JComboBox oraInizioComboBox;
	private JComboBox minutoInizioComboBox;
	private JComboBox oraFineComboBox;
	private JComboBox minutoFineComboBox;
	private JLabel modalitaLabel;
	private JRadioButton onlineRadioButton;
	private JRadioButton fisicoRadioButton;
	private ButtonGroup modalitàButtonGroup = new ButtonGroup();
	private JLabel piattaformaSalaLabel;
	private JComboBox piattaformaSalaComboBox;
	private JLabel infoProgettoDiscussoLabel;
	private JLabel progettoDiscussoLabel;
	private JPanel comandiPanel1;
	private JComboBox<Progetto> progettoDiscussoComboBox;
	private DefaultComboBoxModel modelloComboBoxProgettoDiscusso = new DefaultComboBoxModel();
	private DefaultComboBoxModel modelloComboBoxPiattaformaSala = new DefaultComboBoxModel();
	private JTable meetingTable;
	private MeetingTableModel modelloTabellaMeeting;
	private DefaultListModel modelloListaInfoProgetto = new DefaultListModel();
	private DefaultListModel modelloListaInvitati;
	private JButton pulisciButton;
	private JButton eliminaButton;
	private JButton modificaButton;
	private JButton creaNuovoMeetingButton;
	private JButton inserisciPartecipanteButton;
	private JList invitatiList;
	private JList progettoDiscussoList;
	private JRadioButton filtroMeetingTelematicoRadioButton;
	private JRadioButton filtroMeetingFisicoRadioButton;
	private JComboBox filtroSaleComboBox = new JComboBox<SalaRiunione>();
	private DefaultComboBoxModel modelloComboBoxFiltroSala = new DefaultComboBoxModel<SalaRiunione>();
	private JLabel filtroSalaLabel;
	private JComboBox filtroPiattaformaComboBox;
	private DefaultComboBoxModel modelloComboBoxFiltroPiattaforma = new DefaultComboBoxModel();
	private JLabel filtroPiattaformaLabel;
	private JLabel refreshFiltriLabel;
	private JLabel dataInizioLabel;
	private JLabel dataFineLabel;
	private JLabel oraInizioLabel;
	private JLabel oraFineLabel;
	private JScrollPane invitatiScrollPane;
	private JScrollPane meetingScrollPane;
	private JScrollPane progettoDiscussoScrollPane;
	private DefaultTableCellRenderer tabellaMeetingCellRenderer;
	private JTable progettoTable;
	private JLabel invitatiLabel;
	private TableRowSorter<TableModel> sorterMeeting;

	private Meeting meetingSelezionato;
	private LocalDate dataInizio, dataFine;
	private LocalTime oraInizio, oraFine;
	private String modalita;
	private String piattaforma;
	private SalaRiunione sala;
	private Progetto progettoDiscusso;

	
	private final String VIOLAZIONE_SALA_OCCUPATA = "P0001";
	private final String VIOLAZIONE_CAPIENZA_SALA = "P0002";
	private final String VIOLAZIONE_ONNIPRESENZA_DIPENDENTE = "P0003";
	
	private LocalDate dataAttuale = LocalDate.now();
	private LocalTime oraAttuale = LocalTime.now();
	private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	private DateTimeFormatter formatOra = DateTimeFormat.forPattern("HH:mm");

	public GestioneMeetingDipendente(ControllerMeeting controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1300, 700));
		setTitle("iPlanner - Gestione Meeting");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JPanel infoPanel = new JPanel();

		JPanel comandiPanel = new JPanel();
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		meetingScrollPane = new JScrollPane();
		meetingScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		meetingScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		JPanel comandiPanel2 = new JPanel();
		comandiPanel2.setBorder(null);
		
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		gestioneMeetingLabel = new JLabel("Gestione Meeting");
		gestioneMeetingLabel
				.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/Icone/meeting_64.png")));
		gestioneMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		dataInizioGiornoComboBox = new JComboBox();
		dataInizioGiornoComboBox.setUI(new BasicComboBoxUI());
		dataInizioGiornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioGiornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06",
				"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25", "26", "27", "28", "29", "30", "31" }));
		dataInizioGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() - 1);
		dataInizioGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioGiornoComboBox.setBackground(Color.WHITE);

		dataInizioMeseComboBox = new JComboBox();
		dataInizioMeseComboBox.setUI(new BasicComboBoxUI());
		dataInizioMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioMeseComboBox.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		dataInizioMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() - 1);
		dataInizioMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioMeseComboBox.setBackground(Color.WHITE);

		dataInizioAnnoComboBox = new JComboBox();
		dataInizioAnnoComboBox.setUI(new BasicComboBoxUI());
		dataInizioAnnoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioAnnoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioAnnoComboBox.setBackground(Color.WHITE);
		dataInizioAnnoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioAnnoComboBox.setBounds(358, 235, 62, 22);
		DefaultComboBoxModel modelloComboBoxAnniDataInizio = new DefaultComboBoxModel();
		dataInizioAnnoComboBox.setModel(modelloComboBoxAnniDataInizio);
		for (int i = 1900; i <= 2021; i++)
			modelloComboBoxAnniDataInizio.addElement(i);
		dataInizioAnnoComboBox.setSelectedIndex(dataAttuale.getYear() - 1900);
		infoPanel2.add(dataInizioAnnoComboBox);
		
		dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		dataFineGiornoComboBox = new JComboBox();
		dataFineGiornoComboBox.setUI(new BasicComboBoxUI());
		dataFineGiornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineGiornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06",
				"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25", "26", "27", "28", "29", "30", "31" }));
		dataFineGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() - 1);
		dataFineGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineGiornoComboBox.setBackground(Color.WHITE);

		dataFineMeseComboBox = new JComboBox();
		dataFineMeseComboBox.setUI(new BasicComboBoxUI());
		dataFineMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineMeseComboBox.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		dataFineMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() - 1);
		dataFineMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineMeseComboBox.setBackground(Color.WHITE);

		dataFineAnnoComboBox = new JComboBox();
		dataFineAnnoComboBox.setUI(new BasicComboBoxUI());
		dataFineAnnoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineAnnoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineAnnoComboBox.setBackground(Color.WHITE);
		dataFineAnnoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineAnnoComboBox.setBounds(358, 235, 62, 22);
		DefaultComboBoxModel modelloComboBoxAnniDataFine = new DefaultComboBoxModel();
		dataFineAnnoComboBox.setModel(modelloComboBoxAnniDataFine);
		for (int i = 1900; i <= 2021; i++)
			modelloComboBoxAnniDataFine.addElement(i);
		dataFineAnnoComboBox.setSelectedIndex(dataAttuale.getYear() - 1900);
		infoPanel2.add(dataFineAnnoComboBox);

		oraInizioLabel = new JLabel("Ora inizio");
		oraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		oraInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		oraInizioComboBox = new JComboBox();
		oraInizioComboBox.setUI(new BasicComboBoxUI());
		oraInizioComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		oraInizioComboBox.setBackground(Color.WHITE);
		oraInizioComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		oraInizioComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		oraInizioComboBox.setModel(new DefaultComboBoxModel(new String[] { "00", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
		oraInizioComboBox.setSelectedIndex(oraAttuale.getHourOfDay());

		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		minutoInizioComboBox = new JComboBox();
		minutoInizioComboBox.setUI(new BasicComboBoxUI());
		minutoInizioComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		minutoInizioComboBox.setBackground(Color.WHITE);
		minutoInizioComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minutoInizioComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		minutoInizioComboBox.setModel(new DefaultComboBoxModel(
				new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
						"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46",
						"47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
		minutoInizioComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());

		oraFineLabel = new JLabel("Ora fine");
		oraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		oraFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		oraFineComboBox = new JComboBox();
		oraFineComboBox.setUI(new BasicComboBoxUI());
		oraFineComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		oraFineComboBox.setBackground(Color.WHITE);
		oraFineComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		oraFineComboBox.setModel(new DefaultComboBoxModel(new String[] { "00", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
		oraFineComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		oraFineComboBox.setSelectedIndex(oraAttuale.getHourOfDay() + 2);

		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		minutoFineComboBox = new JComboBox();
		minutoFineComboBox.setUI(new BasicComboBoxUI());
		minutoFineComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		minutoFineComboBox.setBackground(Color.WHITE);
		minutoFineComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minutoFineComboBox.setModel(new DefaultComboBoxModel(
				new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
						"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46",
						"47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
		minutoFineComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		minutoFineComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());

		modalitaLabel = new JLabel("Modalità");
		modalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		modalitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		onlineRadioButton = new JRadioButton("Online");
		onlineRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		onlineRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		onlineRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (onlineRadioButton.isSelected() == true) {
					piattaformaSalaLabel.setText("Piattaforma");
					setModelloComboBoxPiattaforme(controller);
					fisicoRadioButton.setSelected(false);
				}
			}
		});
		modalitàButtonGroup.add(onlineRadioButton);

		fisicoRadioButton = new JRadioButton("Fisico");
		fisicoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fisicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		fisicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fisicoRadioButton.isSelected() == true) {
					piattaformaSalaLabel.setText("Sala");
					setModelloComboBoxSale(controller);
					onlineRadioButton.setSelected(false);
				}
			}
		});
		modalitàButtonGroup.add(fisicoRadioButton);
		
		piattaformaSalaLabel = new JLabel("Piattaforma");
		piattaformaSalaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		piattaformaSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

		piattaformaSalaComboBox = new JComboBox();
		piattaformaSalaComboBox.setUI(new BasicComboBoxUI());
		piattaformaSalaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		piattaformaSalaComboBox.setBackground(Color.WHITE);
		piattaformaSalaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		piattaformaSalaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		progettoDiscussoLabel = new JLabel("Progetto discusso");
		progettoDiscussoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		progettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		progettoDiscussoComboBox = new JComboBox();
		progettoDiscussoComboBox.setUI(new BasicComboBoxUI());
		progettoDiscussoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		progettoDiscussoComboBox.setBackground(Color.WHITE);
		progettoDiscussoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettoDiscussoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		inizializzaComboBoxProgettoDiscusso(controller);
		progettoDiscussoComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (progettoDiscussoComboBox.getSelectedItem() != null) {
					selezionaProgettoDiscusso(controller);
				}

			}
		});

		invitatiScrollPane = new JScrollPane();
		invitatiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		invitatiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		invitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiScrollPane.setColumnHeaderView(invitatiLabel);
		
		invitatiList = new JList();
		modelloListaInvitati = new DefaultListModel<>();
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		invitatiList.setSelectionBackground(Color.WHITE);
		invitatiList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		InvitatiListRenderer invitatiListRenderer = new InvitatiListRenderer();
		invitatiList.setCellRenderer(invitatiListRenderer);
		invitatiScrollPane.setViewportView(invitatiList);
		
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		progettoDiscussoScrollPane = new JScrollPane();
		progettoDiscussoScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		progettoDiscussoScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		progettoDiscussoScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		infoProgettoDiscussoLabel = new JLabel("Info progetto discusso");
		infoProgettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettoDiscussoScrollPane.setColumnHeaderView(infoProgettoDiscussoLabel);
		
		progettoDiscussoList = new JList();
		ProgettoDiscussoListRenderer progettoDiscussoCellRenderer = new ProgettoDiscussoListRenderer();
		progettoDiscussoList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		progettoDiscussoList.setFont(new Font("Consolas", Font.PLAIN, 12));
		progettoDiscussoList.setSelectionBackground(Color.WHITE);
		progettoDiscussoScrollPane.setViewportView(progettoDiscussoList);
		progettoDiscussoList.setCellRenderer(progettoDiscussoCellRenderer);

		pulisciButton = new JButton("Pulisci Campi");
		pulisciButton.setPreferredSize(new Dimension(150, 30));
		pulisciButton.setMaximumSize(new Dimension(150, 150));
		pulisciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciButton.setBackground(Color.WHITE);
		pulisciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pulisciButton.setAlignmentX(0.5f);
		pulisciButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		pulisciButton.setMargin(new Insets(2, 20, 2, 20));
		pulisciButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pulisciButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				pulisciButton.setBackground(Color.WHITE);
			}
		});
		pulisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				svuotaCampiMeeting();
			}
		});

		eliminaButton = new JButton("Elimina");
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
					eliminaMeeting(controller);
				}
			});
		eliminaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				eliminaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				eliminaButton.setBackground(Color.WHITE);
			}
		});

		modificaButton = new JButton("Conferma Modifiche");
		modificaButton.setPreferredSize(new Dimension(170, 30));
		modificaButton.setMaximumSize(new Dimension(150, 150));
		modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setMargin(new Insets(2, 20, 2, 20));
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		modificaButton.setAlignmentX(0.5f);
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modalitaLabel.setForeground(Color.BLACK);
				piattaformaSalaLabel.setForeground(Color.BLACK);
				progettoDiscussoLabel.setForeground(Color.BLACK);
				if (campiObbligatoriVuoti()) {
					JOptionPane.showMessageDialog(null,
							"Alcuni campi obbligatori sono vuoti.",
							"Campi Obbligatori Vuoti", JOptionPane.ERROR_MESSAGE);
					if (!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected())
						modalitaLabel.setForeground(Color.RED);
					if (piattaformaSalaComboBox.getSelectedItem() == null)
						piattaformaSalaLabel.setForeground(Color.RED);
					if (progettoDiscussoComboBox.getSelectedItem() == null) {
						progettoDiscussoLabel.setForeground(Color.RED);
					}
				} else
					aggiornaMeeting(controller);
			}
		});
		modificaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				modificaButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				modificaButton.setBackground(Color.WHITE);
			}
		});

		creaNuovoMeetingButton = new JButton("Crea Nuovo");
		creaNuovoMeetingButton.setPreferredSize(new Dimension(130, 30));
		creaNuovoMeetingButton.setMaximumSize(new Dimension(150, 150));
		creaNuovoMeetingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaNuovoMeetingButton.setBackground(Color.WHITE);
		creaNuovoMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaNuovoMeetingButton.setMargin(new Insets(2, 20, 2, 20));
		creaNuovoMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		creaNuovoMeetingButton.setAlignmentX(0.5f);
		creaNuovoMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modalitaLabel.setForeground(Color.BLACK);
				piattaformaSalaLabel.setForeground(Color.BLACK);
				progettoDiscussoLabel.setForeground(Color.BLACK);
				if (!campiObbligatoriVuoti())
						creaMeeting(controller);
				else {
					JOptionPane.showMessageDialog(null,
							"Alcuni campi obbligatori sono vuoti.",
							"Campi Obbligatori Vuoti", JOptionPane.ERROR_MESSAGE);
					if (!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected())
						modalitaLabel.setForeground(Color.RED);
					if (piattaformaSalaComboBox.getSelectedItem() == null)
						piattaformaSalaLabel.setForeground(Color.RED);
					if (progettoDiscussoComboBox.getSelectedItem() == null) {
						progettoDiscussoLabel.setForeground(Color.RED);
					}
				}
			}
		});
		creaNuovoMeetingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				creaNuovoMeetingButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				creaNuovoMeetingButton.setBackground(Color.WHITE);
			}
		});
		
		inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
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
					if(meetingTable.getSelectedRow()!=-1) {
						controller.apriInserisciPartecipantiMeeting(meetingSelezionato);
					}
					else
						JOptionPane.showMessageDialog(null, "Selezionare un meeting dalla tabella");		
			}
		});
		inserisciPartecipanteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				inserisciPartecipanteButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				inserisciPartecipanteButton.setBackground(Color.WHITE);
			}
		});
		
		refreshFiltriLabel = new JLabel("");
		refreshFiltriLabel.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/icone/refresh.png")));
		refreshFiltriLabel.setToolTipText("Reset dei filtri");
		refreshFiltriLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		refreshFiltriLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ripristinaFiltri();
				aggiornaTabella(controller);
			}
		});
		
		filtroMeetingTelematicoRadioButton = new JRadioButton("Telematico");
		filtroMeetingTelematicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroMeetingTelematicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filtroMeetingTelematicoRadioButton.isSelected())
					filtraByTelematico(controller);
			}
		});
		
		comandiPanel1 = new JPanel();
		comandiPanel1.setBorder(null);
		comandiPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		comandiPanel1.add(refreshFiltriLabel);
		comandiPanel1.add(filtroMeetingTelematicoRadioButton);

		filtroMeetingFisicoRadioButton = new JRadioButton("Fisico");
		filtroMeetingFisicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroMeetingFisicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filtroMeetingFisicoRadioButton.isSelected()) {
					filtraByFisico(controller);
				}
			}
		});
		comandiPanel1.add(filtroMeetingFisicoRadioButton);

		filtroSalaLabel = new JLabel("Sala:");
		filtroSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		comandiPanel1.add(filtroSalaLabel);
		
		inizializzaComboBoxFiltroSala(controller);
		filtroSaleComboBox.setSelectedIndex(0);
		filtroSaleComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroSaleComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroSaleComboBox.setBounds(308, 9, 108, 22);
		filtroSaleComboBox.setUI(new BasicComboBoxUI());
		filtroSaleComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltroSala(controller);
			}
		});

		comandiPanel1.add(filtroSaleComboBox);

		filtroPiattaformaLabel = new JLabel("Piattaforma:");
		filtroPiattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		comandiPanel1.add(filtroPiattaformaLabel);

		filtroPiattaformaComboBox = new JComboBox();
		inizializzaComboBoxFiltroPiattaforma(controller);
		filtroPiattaformaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		filtroPiattaformaComboBox.setSelectedItem(null);
		filtroPiattaformaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicaFiltroPiattaforma(controller);
			}
		});
		filtroPiattaformaComboBox.setFont(new Font("Consolas", Font.PLAIN, 13));
		filtroPiattaformaComboBox.setBounds(517, 9, 151, 22);
		filtroPiattaformaComboBox.setUI(new BasicComboBoxUI());
		comandiPanel1.add(filtroPiattaformaComboBox);
		
		modelloTabellaMeeting = new MeetingTableModel();
		meetingTable = new JTable(modelloTabellaMeeting);
		meetingTable.setFont(new Font("Consolas", Font.PLAIN, 11));
		meetingTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meetingTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingTable.setBackground(Color.WHITE);
		meetingTable.setSelectionBackground(Color.LIGHT_GRAY);
		meetingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		meetingTable.getTableHeader().setReorderingAllowed(false);
		inizializzaTabellaMeeting(controller);
		impostaModelloColonneMeeting();
		impostaCellRenderTabellaMeeting();
		impostaSorterTabellaMeeting();
		meetingTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//Viene utilizzato questo metodo per permettere l'aggiornamento della lista degli invitati al meeting
				//in seguito a modifiche dei partecipanti ad esso
				inizializzaTabellaMeeting(controller);
				impostaInfoMeeting(controller);
				checkOrganizzatore(controller);
			}
		});
		meetingScrollPane.setViewportView(meetingTable);

		comandiPanel2.add(inserisciPartecipanteButton);
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		comandiPanel2.add(modificaButton);
		comandiPanel2.add(creaNuovoMeetingButton);
		comandiPanel2.add(eliminaButton);
		comandiPanel2.add(pulisciButton);

		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING).addGroup(gl_infoPanel2
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addComponent(dataInizioLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 97,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(dataFineLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 155,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(oraInizioLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 155,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(oraFineLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 65,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(modalitaLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(piattaformaSalaLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 155,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(progettoDiscussoLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 155,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
				.addGroup(gl_infoPanel2
						.createParallelGroup(Alignment.LEADING,
								false)
						.addGroup(gl_infoPanel2.createSequentialGroup()
								.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 92,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createSequentialGroup()
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
										.addComponent(piattaformaSalaComboBox, Alignment.LEADING,
												GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
										.addGroup(Alignment.LEADING,
												gl_infoPanel2.createSequentialGroup()
														.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 46,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 5,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE,
																46, GroupLayout.PREFERRED_SIZE))
										.addGroup(Alignment.LEADING, gl_infoPanel2.createSequentialGroup()
												.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, 46,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(
														minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, 46,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(Alignment.LEADING, gl_infoPanel2.createSequentialGroup()
												.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 92,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(Alignment.LEADING,
												gl_infoPanel2.createSequentialGroup().addComponent(onlineRadioButton)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(fisicoRadioButton, GroupLayout.PREFERRED_SIZE, 69,
																GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(dataFineAnnoComboBox,
										GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addComponent(progettoDiscussoComboBox, 0, 216, Short.MAX_VALUE))
				.addGap(112)
				.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
				.addGap(53)
				.addComponent(progettoDiscussoScrollPane, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
				.addGap(53)));
		gl_infoPanel2.setVerticalGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING).addGroup(gl_infoPanel2
				.createSequentialGroup().addGap(25)
				.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 270,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(
												progettoDiscussoScrollPane, GroupLayout.DEFAULT_SIZE, 270,
												Short.MAX_VALUE))
						.addGroup(gl_infoPanel2.createSequentialGroup().addGap(12).addGroup(gl_infoPanel2
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
										.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
												.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21,
														GroupLayout.PREFERRED_SIZE))
										.addGap(16))
								.addGroup(gl_infoPanel2.createSequentialGroup()
										.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)))
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 21,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_infoPanel2.createSequentialGroup().addGap(11)
												.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel).addComponent(minutoInizioComboBox,
																GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
														.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 26,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 21,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 17,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE,
																21, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_infoPanel2.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
														.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 26,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(modalitaLabel, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(onlineRadioButton).addComponent(fisicoRadioButton))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(piattaformaSalaLabel, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(piattaformaSalaComboBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(progettoDiscussoComboBox, GroupLayout.PREFERRED_SIZE, 19,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(progettoDiscussoLabel, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)))).addGap(36)));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(gestioneMeetingLabel).addContainerGap(934,
						Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGap(8)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1256, Short.MAX_VALUE).addGap(6)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(gestioneMeetingLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE).addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
				.createSequentialGroup().addGap(10)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(comandiPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1248,
								Short.MAX_VALUE)
						.addComponent(infoPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1248, Short.MAX_VALUE)
						.addComponent(meetingScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1248,
								Short.MAX_VALUE))
				.addGap(2)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(23)
						.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE).addGap(11)));
		
		GroupLayout gl_comandiPanel = new GroupLayout(comandiPanel);
		gl_comandiPanel.setHorizontalGroup(gl_comandiPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comandiPanel.createSequentialGroup().addGap(126)
						.addGroup(gl_comandiPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(comandiPanel1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 996,
										Short.MAX_VALUE)
								.addComponent(comandiPanel2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 904,
										Short.MAX_VALUE))
						.addGap(126)));
		gl_comandiPanel.setVerticalGroup(gl_comandiPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comandiPanel.createSequentialGroup().addGap(5)
						.addComponent(comandiPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(comandiPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(1)));
		comandiPanel.setLayout(gl_comandiPanel);
		panel.setLayout(gl_panel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
	}

	private void impostaInfoMeeting(ControllerMeeting controller) {
		int rigaSelezionata = meetingTable.getSelectedRow();
		rigaSelezionata = meetingTable.convertColumnIndexToModel(rigaSelezionata);
		meetingSelezionato = modelloTabellaMeeting.getSelected(rigaSelezionata);

		LocalDate dataInizio = formatDate.parseLocalDate(meetingSelezionato.getDataInizio().toString(formatDate));
		dataInizioAnnoComboBox.setSelectedItem(dataInizio.getYear());
		dataInizioMeseComboBox.setSelectedIndex(dataInizio.getMonthOfYear() - 1);
		dataInizioGiornoComboBox.setSelectedIndex(dataInizio.getDayOfMonth() - 1);

		LocalDate dataFine = formatDate.parseLocalDate(meetingSelezionato.getDataFine().toString(formatDate));
		dataFineAnnoComboBox.setSelectedItem(dataFine.getYear());
		dataFineMeseComboBox.setSelectedIndex(dataFine.getMonthOfYear() - 1);
		dataFineGiornoComboBox.setSelectedIndex(dataFine.getDayOfMonth() - 1);

		LocalTime oraInizio = formatOra.parseLocalTime(meetingSelezionato.getOraInizio().toString(formatOra));
		oraInizioComboBox.setSelectedIndex(oraInizio.getHourOfDay());
		minutoInizioComboBox.setSelectedIndex(oraInizio.getMinuteOfHour());

		LocalTime oraFine = formatOra.parseLocalTime(meetingSelezionato.getOraFine().toString(formatOra));
		oraFineComboBox.setSelectedIndex(oraFine.getHourOfDay());
		minutoFineComboBox.setSelectedIndex(oraFine.getMinuteOfHour());

		if (meetingSelezionato.getPiattaforma() != null) {
			piattaformaSalaLabel.setText("Piattaforma");
			selezionaPiattaformaMeetingSelezionato(controller);
			onlineRadioButton.setSelected(true);
			fisicoRadioButton.setSelected(false);
		} else {
			piattaformaSalaLabel.setText("Sala");
			selezionaSalaMeetingSelezionato(controller);
			fisicoRadioButton.setSelected(true);
			onlineRadioButton.setSelected(false);
		}

		progettoDiscussoComboBox.setSelectedItem(meetingSelezionato.getProgettoDiscusso());
		aggiornaListaInvitati();
	}

	private void aggiornaListaInvitati() {
		modelloListaInvitati.removeAllElements();
		modelloListaInvitati.addAll(meetingSelezionato.getPartecipantiAlMeeting());
		invitatiList.setModel(modelloListaInvitati);
	}

	private void inizializzaTabellaMeeting(ControllerMeeting controller) {
		try {
			modelloTabellaMeeting.setMeetingTabella(controller.ottieniMeetingDipendente());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void selezionaPiattaformaMeetingSelezionato(ControllerMeeting controller) {
		modelloComboBoxPiattaformaSala.removeAllElements();
		try {
			modelloComboBoxPiattaformaSala.addAll(controller.ottieniPiattaforme());
			piattaformaSalaComboBox.setModel(modelloComboBoxPiattaformaSala);
			piattaformaSalaComboBox.setSelectedItem(meetingSelezionato.getPiattaforma());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e1.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void selezionaSalaMeetingSelezionato(ControllerMeeting controller) {
		modelloComboBoxPiattaformaSala.removeAllElements();
		try {
			modelloComboBoxPiattaformaSala.addAll(controller.ottieniSale());
			piattaformaSalaComboBox.setModel(modelloComboBoxPiattaformaSala);
			piattaformaSalaComboBox.setSelectedItem(meetingSelezionato.getSala());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e1.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void selezionaProgettoDiscusso(ControllerMeeting controller) {
		try {
			Progetto progetto = controller.ottieniProgettoInserito((Progetto) progettoDiscussoComboBox.getSelectedItem());
			progettoDiscussoList.setModel(modelloListaInfoProgetto);
			modelloListaInfoProgetto.removeAllElements();
			modelloListaInfoProgetto.add(0, progetto);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e1.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void setModelloComboBoxPiattaforme(ControllerMeeting controller) {
		modelloComboBoxPiattaformaSala.removeAllElements();
		try {
			modelloComboBoxPiattaformaSala.addAll(controller.ottieniPiattaforme());
			piattaformaSalaComboBox.setModel(modelloComboBoxPiattaformaSala);
			piattaformaSalaComboBox.setSelectedIndex(0);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void setModelloComboBoxSale(ControllerMeeting controller) {
		modelloComboBoxPiattaformaSala.removeAllElements();
		try {
			modelloComboBoxPiattaformaSala.addAll(controller.ottieniSale());
			piattaformaSalaComboBox.setModel(modelloComboBoxPiattaformaSala);
			if (modelloComboBoxPiattaformaSala.getSize() > 0)
				piattaformaSalaComboBox.setSelectedIndex(0);
			else
				piattaformaSalaComboBox.setSelectedItem(null);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);		}
	}
	
	private void inizializzaComboBoxProgettoDiscusso(ControllerMeeting controller) {
		try {
			modelloComboBoxProgettoDiscusso.addAll(controller.ottieniProgetti());
			progettoDiscussoComboBox.setModel(modelloComboBoxProgettoDiscusso);
			progettoDiscussoComboBox.setSelectedItem(null);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void inizializzaComboBoxFiltroSala(ControllerMeeting controller) {
		ArrayList<SalaRiunione> sale = new ArrayList<SalaRiunione>();
		try {
			sale = controller.ottieniSale();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
		sale.add(0, null);
		modelloComboBoxFiltroSala.addAll(sale);
		filtroSaleComboBox.setModel(modelloComboBoxFiltroSala);
	}
	
	private void inizializzaComboBoxFiltroPiattaforma(ControllerMeeting controller) {
		ArrayList<String> piattaforme = new ArrayList<String>();
		try {
			piattaforme = controller.ottieniPiattaforme();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
		piattaforme.add(0, null);
		modelloComboBoxFiltroPiattaforma.addAll(piattaforme);
		filtroPiattaformaComboBox.setModel(modelloComboBoxFiltroPiattaforma);
	}
	
	private void filtraByFisico(ControllerMeeting controller) {
			filtroMeetingTelematicoRadioButton.setSelected(false);
			filtroPiattaformaComboBox.setSelectedItem(null);
		try {
			modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingFisiciDipendenti());
			sorterMeeting.setModel(modelloTabellaMeeting);
			modelloTabellaMeeting.fireTableDataChanged();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			}
	}
	
	private void filtraByTelematico(ControllerMeeting controller) {
			filtroMeetingFisicoRadioButton.setSelected(false);
			filtroSaleComboBox.setSelectedItem(null);
		try {
			modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingTelematiciDipendenti());
			modelloTabellaMeeting.fireTableDataChanged();
		} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage()
						+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
						"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void applicaFiltroSala(ControllerMeeting controller) {
		if (!(filtroSaleComboBox.getSelectedItem() == null)) {
			filtraBySala(controller, (SalaRiunione) filtroSaleComboBox.getSelectedItem());
			filtroMeetingFisicoRadioButton.setSelected(true);
			filtroMeetingTelematicoRadioButton.setSelected(false);
			filtroPiattaformaComboBox.setSelectedItem(null);
		}
	}
	
	private void filtraBySala(ControllerMeeting controller, SalaRiunione sala) {
		try {
		modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingDipendentiSala(sala));
		modelloTabellaMeeting.fireTableDataChanged();
		sorterMeeting.setModel(modelloTabellaMeeting);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void applicaFiltroPiattaforma(ControllerMeeting controller) {
			if (!(filtroPiattaformaComboBox.getSelectedItem() == null)) {
				filtraByPiattaforma(controller, filtroPiattaformaComboBox.getSelectedItem().toString());
				filtroMeetingTelematicoRadioButton.setSelected(true);
				filtroMeetingFisicoRadioButton.setSelected(false);
				filtroSaleComboBox.setSelectedItem(null);
			}
	}

	private void filtraByPiattaforma(ControllerMeeting controller, String piattaforma) {
		try {
		modelloTabellaMeeting.setMeetingTabella(controller.filtraMeetingDipendentePiattaforma(piattaforma));
		modelloTabellaMeeting.fireTableDataChanged();
		sorterMeeting.setModel(modelloTabellaMeeting);
		} catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void checkOrganizzatore(ControllerMeeting controller) {
		try {
			if (controller.isOrganizzatore(meetingSelezionato)) {
				inserisciPartecipanteButton.setEnabled(true);
				eliminaButton.setEnabled(true);
				modificaButton.setEnabled(true);
			}
			else {
				inserisciPartecipanteButton.setEnabled(false);
				eliminaButton.setEnabled(false);
				modificaButton.setEnabled(false);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e1.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void creaMeeting(ControllerMeeting controller) {
		try {
			ricavaInfoMeeting();
			Meeting nuovoMeeting = new Meeting(-1, dataInizio, dataFine, oraInizio, oraFine, modalita, piattaforma,
					sala);
			nuovoMeeting.setProgettoDiscusso(progettoDiscusso);
			controller.creaMeeting(nuovoMeeting);
			try {
				nuovoMeeting.setIdMeeting(controller.idUltimoMeetingInserito());
				controller.inserisciOrganizzatore();
				JOptionPane.showMessageDialog(null, "Meeting Inserito Correttamente");
				aggiornaTabella(controller);
				svuotaCampiMeeting();
			} catch (SQLException e) {
				//TODO: verificare altre possibili eccezioni
				switch(e.getSQLState()) {
                case VIOLAZIONE_ONNIPRESENZA_DIPENDENTE:
                    JOptionPane.showMessageDialog(null,
                            "\nImpossibile creare il meeting perchè si accavalla con altri meeting."
                            + "\nCambiare data e orario oppure modificare prima il meeting che si accavalla.",
                            "Errore Accavallamento Meeting",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, e.getMessage()
                            + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
                            "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
                }
				try {
					controller.rimuoviMeeting(nuovoMeeting.getIdMeeting());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
							"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (IllegalFieldValueException ifve) {
			JOptionPane.showMessageDialog(null, "Data inserita non valida.\nInserire una data esistente.",
					"Errore Data Non Valida", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			//TODO: verificare altre possibili eccezioni
            JOptionPane.showMessageDialog(null,
                    "Errore: Ci sono problemi di accavallamento con il meeting che si sta tentando di inserire."
                            + "\nControllare che la sala inserita non sia già occupata per le date e gli orari inseriti.",
                            "Errore Sala Occupata",
                            JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void aggiornaMeeting(ControllerMeeting controller) {
		try{
			ricavaInfoMeeting();

			meetingSelezionato.setDataInizio(dataInizio);
			meetingSelezionato.setDataFine(dataFine);
			meetingSelezionato.setOraInizio(oraInizio);
			meetingSelezionato.setOraFine(oraFine);
			meetingSelezionato.setModalita(modalita);
			if(meetingSelezionato.getModalita().equals("Fisico")) {
				meetingSelezionato.setSala(sala);
				meetingSelezionato.setPiattaforma(null);
			}
			else if(meetingSelezionato.getModalita().equals("Telematico")) {
				meetingSelezionato.setSala(null);
				meetingSelezionato.setPiattaforma(piattaforma);
			}
			meetingSelezionato.setProgettoDiscusso(progettoDiscusso);
			
			controller.aggiornaMeeting(meetingSelezionato);
			JOptionPane.showMessageDialog(null, "Meeting Modificato Correttamente");
			svuotaCampiMeeting();
			aggiornaTabella(controller);
		} catch(IllegalFieldValueException ifve) {
			JOptionPane.showMessageDialog(null, "Data inserita non valida.\nInserire una data esistente.",
					"Errore Data Non Valida", JOptionPane.ERROR_MESSAGE);
		} catch(SQLException e) {
			//TODO: verificare altre possibili eccezioni
			switch(e.getSQLState()) {
			case VIOLAZIONE_ONNIPRESENZA_DIPENDENTE:
				JOptionPane.showMessageDialog(null,
						"Ci sono problemi di accavallamento con il meeting che si sta tentando di modificare."
								+ "\nControllare che i dipendenti siano liberi per le date e orari inseriti.",
								"Errore Accavallamento Meeting",
								JOptionPane.ERROR_MESSAGE);
				break;
			case VIOLAZIONE_CAPIENZA_SALA:
				JOptionPane.showMessageDialog(null,
						"I partecipanti al meeting che si vuole modificare sono maggiori "
								+ "\nrispetto alla capienza massima della sala.\n"
								+ "Controllare che non ci siano più di " + meetingSelezionato.getSala().getCapienza() + " partecipanti.",
								"Errore Capienza Sala",
								JOptionPane.ERROR_MESSAGE);
				break;
			case VIOLAZIONE_SALA_OCCUPATA:
				JOptionPane.showMessageDialog(null,
						"Errore: Ci sono problemi di accavallamento con il meeting che si sta tentando di modificare."
								+ "\nControllare che la sala inserita non sia già occupata per le date e gli orari inseriti.",
								"Errore Sala Occupata",
								JOptionPane.ERROR_MESSAGE);
				break;
				default:
					JOptionPane.showMessageDialog(null, e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
							"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void ricavaInfoMeeting() {
		dataInizio = new LocalDate(Integer.valueOf(dataInizioAnnoComboBox.getSelectedItem().toString()),
				Integer.valueOf(dataInizioMeseComboBox.getSelectedItem().toString()),
				Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));
		dataFine = new LocalDate(Integer.valueOf(dataFineAnnoComboBox.getSelectedItem().toString()),
				Integer.valueOf(dataFineMeseComboBox.getSelectedItem().toString()),
				Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));
		oraInizio = new LocalTime(Integer.valueOf(oraInizioComboBox.getSelectedIndex()),
				Integer.valueOf(minutoInizioComboBox.getSelectedIndex()), 0);
		oraFine = new LocalTime(Integer.valueOf(oraFineComboBox.getSelectedIndex()),
				Integer.valueOf(minutoFineComboBox.getSelectedIndex()), 0);
		if (onlineRadioButton.isSelected()) {
			modalita = "Telematico";
			piattaforma = piattaformaSalaComboBox.getSelectedItem().toString();
			sala = null;
		} else if (fisicoRadioButton.isSelected()) {
			modalita = "Fisico";
			sala = (SalaRiunione) piattaformaSalaComboBox.getSelectedItem();
			piattaforma = null;
		}
		progettoDiscusso = (Progetto) progettoDiscussoComboBox.getSelectedItem();
	}
	
	private void aggiornaTabella(ControllerMeeting controller) {
		try {
			modelloTabellaMeeting.setMeetingTabella(controller.ottieniMeetingDipendente());
			modelloTabellaMeeting.fireTableDataChanged();
			sorterMeeting.setModel(modelloTabellaMeeting);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}

	}
	
	private void eliminaMeeting(ControllerMeeting controller) {
		if(meetingSelezionato != null) {
			try {
				controller.rimuoviMeeting(meetingSelezionato.getIdMeeting());
				JOptionPane.showMessageDialog(null, "Meeting Eliminato Correttamente");
				aggiornaTabella(controller);
				svuotaCampiMeeting();
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage()
						+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
						"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Selezionare un meeting dalla tabella");
	}
	
	private boolean campiObbligatoriVuoti() {
		if (!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected())
			return true;
		if (piattaformaSalaComboBox.getSelectedItem() == null)
			return true;
		if (progettoDiscussoComboBox.getSelectedItem() == null)
			return true;
		return false;
	}

	private void svuotaCampiMeeting() {
		dataInizioGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() - 1);
		dataInizioMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() - 1);
		dataInizioAnnoComboBox.setSelectedIndex(dataAttuale.getYear() - 1900);
		dataFineGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() - 1);
		dataFineMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() - 1);
		dataFineAnnoComboBox.setSelectedIndex(dataAttuale.getYear() - 1900);
		oraInizioComboBox.setSelectedIndex(oraAttuale.getHourOfDay());
		minutoInizioComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
		oraFineComboBox.setSelectedIndex(oraAttuale.getHourOfDay() + 2);
		minutoFineComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
		onlineRadioButton.setSelected(false);
		fisicoRadioButton.setSelected(false);
		piattaformaSalaComboBox.setSelectedItem(null);
		progettoDiscussoComboBox.setSelectedItem(null);
		if (!modelloListaInvitati.isEmpty())
			modelloListaInvitati.removeAllElements();
		if (!modelloListaInfoProgetto.isEmpty())
			modelloListaInfoProgetto.removeAllElements();
		meetingTable.clearSelection();
		meetingSelezionato = null;
		ripristinaFiltri();
	}

	private void ripristinaFiltri() {
		filtroMeetingFisicoRadioButton.setSelected(false);
		filtroMeetingTelematicoRadioButton.setSelected(false);
		filtroSaleComboBox.setSelectedItem(null);
		filtroPiattaformaComboBox.setSelectedItem(null);
	}
	
	private void impostaModelloColonneMeeting() {
		meetingTable.getColumnModel().getColumn(0).setMinWidth(100);
		meetingTable.getColumnModel().getColumn(1).setMinWidth(100);
		meetingTable.getColumnModel().getColumn(2).setMinWidth(100);
		meetingTable.getColumnModel().getColumn(3).setMinWidth(100);
		meetingTable.getColumnModel().getColumn(4).setMinWidth(150);
		meetingTable.getColumnModel().getColumn(5).setMinWidth(500);
	}
	
	private void impostaCellRenderTabellaMeeting() {
		tabellaMeetingCellRenderer = new DefaultTableCellRenderer();
		tabellaMeetingCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tabellaMeetingCellRenderer.setVerticalAlignment(SwingConstants.CENTER);
		meetingTable.getColumnModel().getColumn(0).setCellRenderer(tabellaMeetingCellRenderer);
		meetingTable.getColumnModel().getColumn(1).setCellRenderer(tabellaMeetingCellRenderer);
		meetingTable.getColumnModel().getColumn(2).setCellRenderer(tabellaMeetingCellRenderer);
		meetingTable.getColumnModel().getColumn(3).setCellRenderer(tabellaMeetingCellRenderer);
		meetingTable.getColumnModel().getColumn(4).setCellRenderer(tabellaMeetingCellRenderer);
		meetingTable.getColumnModel().getColumn(5).setCellRenderer(tabellaMeetingCellRenderer);
	}
	
	private void impostaSorterTabellaMeeting() {
		sorterMeeting = new TableRowSorter<>(modelloTabellaMeeting);
		meetingTable.setRowSorter(sorterMeeting);
		DataComparator comparatorDate = new DataComparator();
		OrarioComparator comparatorOrario = new OrarioComparator();
		sorterMeeting.setComparator(0, comparatorDate);
		sorterMeeting.setComparator(1, comparatorDate);
		sorterMeeting.setComparator(2, comparatorOrario);
		sorterMeeting.setComparator(3, comparatorOrario);
	}
}
