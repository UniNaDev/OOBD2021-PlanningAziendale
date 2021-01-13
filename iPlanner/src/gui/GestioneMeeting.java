package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;

public class GestioneMeeting extends JFrame {

	private JPanel contentPane;
	private JTextField idMeetingTextField;
	private JTable progettoTable;
	private JTextField cercaTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
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
	private JRadioButton onlineRadioButton;
	private JRadioButton fisicoRadioButton;
	private JComboBox piattaformaComboBox;


	/**
	 * Create the frame.
	 */
	public GestioneMeeting() {
		setMinimumSize(new Dimension(1150, 700));
		setTitle("GestioneMeeting");
		
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		panel_3.setFont(new Font("Tahoma", Font.PLAIN, 36));
		panel_3.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane meetingScrollPane = new JScrollPane();
		meetingScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		meetingScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTable meetingTable = new JTable();
		meetingTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meetingTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingTable.setBackground(Color.WHITE);
		meetingTable.setSelectionBackground(Color.LIGHT_GRAY);
		meetingTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, "", null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"ProgettoID", "Nome", "Ambito/i", "Tipologia", "Scadenza", "Descrizione", "Creatore"
			}
		));
		meetingTable.getColumnModel().getColumn(4).setPreferredWidth(77);
		meetingScrollPane.setViewportView(meetingTable);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(192, 192, 192)));
		
		JButton pulisciButton = new JButton("Pulisci Campi");
		pulisciButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				pulisciButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				pulisciButton.setBackground(Color.WHITE);
			}
		});
		pulisciButton.addActionListener(new ActionListener() {
			
			//quando si preme sul tasto pulisci imposta i combobox con valori in base alla data e l'ora attuali
			public void actionPerformed(ActionEvent e) 
			{
				//ricava l'ora e la data attuali
				LocalDate dataAttuale = LocalDate.now();
				LocalTime oraAttuale = LocalTime.now();
				
				//IMPOSTA LA DATA DI INIZIO COME IL GIORNO ATTUALE
				dataInizioGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1); //-1 perche gli indici iniziano da 0
				dataInizioMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
				dataInizioAnnoComboBox.setSelectedIndex(dataAttuale.getYear() -1900);// -1900 perche nella comboBox gli anni vanno dal 1900 all anno attuale ,quindi 2021
																					//sarà il (2021 - 1900) 121 esimo indice
				//IMPOSTA ANCHE LA DATA DI FINE COME IL GIORNO ATTUALE
				dataFineGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
				dataFineMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
				dataFineAnnoComboBox.setSelectedIndex(dataAttuale.getYear() -1900);
				
				//IMPOSTA L'ORA DI INIZIO COME L'ORA ATTUALE
				oraInizioComboBox.setSelectedIndex(oraAttuale.getHourOfDay());
				minutoInizioComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
				
				//IMPOSTA DI DEFAULT L'ORA DI FINE COME 2 ORE DOPO 
				oraFineComboBox.setSelectedIndex(oraAttuale.getHourOfDay() +2);
				minutoFineComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
				
			}
		});
		pulisciButton.setPreferredSize(new Dimension(150, 30));
		pulisciButton.setMaximumSize(new Dimension(150, 150));
		pulisciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciButton.setBackground(Color.WHITE);
		pulisciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pulisciButton.setAlignmentX(0.5f);
		pulisciButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		pulisciButton.setMargin(new Insets(2, 20, 2, 20));
		
		JButton eliminaButton = new JButton("Elimina");
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
		
		JButton inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
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
		
		JButton modificaButton = new JButton("Conferma Modifiche");
		modificaButton.addActionListener(new ActionListener() {
			
			//quando si preme sul tasto modifica i campi diventano editabili 
			public void actionPerformed(ActionEvent e) 
			{
				//TODO andrà a fare l'update sul meeting
			}
		});
		modificaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				modificaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				modificaButton.setBackground(Color.WHITE);
			}
		});
		modificaButton.setPreferredSize(new Dimension(170, 30));
		modificaButton.setMaximumSize(new Dimension(150, 150));
		modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setMargin(new Insets(2, 20, 2, 20));
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		modificaButton.setAlignmentX(0.5f);
		
		JButton salvaButton = new JButton("Crea Nuovo");
		salvaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				//TODO andrà a fare l'insert di un nuovo meeting
			}
		});
		salvaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				salvaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				salvaButton.setBackground(Color.WHITE);
			}
		});
		salvaButton.setPreferredSize(new Dimension(130, 30));
		salvaButton.setMaximumSize(new Dimension(150, 150));
		salvaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvaButton.setBackground(Color.WHITE);
		salvaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salvaButton.setMargin(new Insets(2, 20, 2, 20));
		salvaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		salvaButton.setAlignmentX(0.5f);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel cercaLabel = new JLabel("Cerca");
		cercaLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		panel_4.add(cercaLabel);
		
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		panel_4.add(cercaTextField);
		cercaTextField.setColumns(10);
		panel_4.add(inserisciPartecipanteButton);
		panel_4.add(modificaButton);
		panel_4.add(salvaButton);
		panel_4.add(eliminaButton);
		panel_4.add(pulisciButton);
		panel_3.add(panel_4);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		JLabel idMeetingLabel = new JLabel("MeetingID");
		idMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		idMeetingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		idMeetingTextField = new JTextField();
		idMeetingTextField.setEditable(false);
		idMeetingTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		idMeetingTextField.setColumns(10);
		
		JLabel dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel oraInizioLabel = new JLabel("Ora inizio");
		oraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		oraInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel oraFineLabel = new JLabel("Ora fine");
		oraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		oraFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel modalitaLabel = new JLabel("Modalit\u00E0");
		modalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		modalitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel creatoreProgettoLabel = new JLabel("Piattaforma");
		creatoreProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		creatoreProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		
		
		onlineRadioButton = new JRadioButton("Online");
		onlineRadioButton.setSelected(true);
		onlineRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		onlineRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		buttonGroup.add(onlineRadioButton);
		
		fisicoRadioButton = new JRadioButton("Fisico");
		fisicoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fisicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		buttonGroup.add(fisicoRadioButton);
		
		dataInizioMeseComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		dataInizioMeseComboBox.setUI(new BasicComboBoxUI());
		
		dataInizioMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataInizioMeseComboBox.setSelectedIndex(0);
		dataInizioMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioMeseComboBox.setBackground(Color.WHITE);
		
		dataInizioGiornoComboBox = new JComboBox();
		
		//modifica lo stile della combo box
		dataInizioGiornoComboBox.setUI(new BasicComboBoxUI());
		
		dataInizioGiornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioGiornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dataInizioGiornoComboBox.setSelectedIndex(0);
		dataInizioGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioGiornoComboBox.setBackground(Color.WHITE);
		
		dataInizioAnnoComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		dataInizioAnnoComboBox.setUI(new BasicComboBoxUI());
		
		dataInizioAnnoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioAnnoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioAnnoComboBox.setBackground(Color.WHITE);
		dataInizioAnnoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioAnnoComboBox.setBounds(358, 235, 62, 22);
		//CREO UN MODELLO PERSONALIZZATO PER L'ANNO DEL COMBO BOX //////////
		DefaultComboBoxModel myModel = new DefaultComboBoxModel();
		dataInizioAnnoComboBox.setModel(myModel);
		
		for(int i=1900;i<= 2021;i++)
			myModel.addElement(i);
		
		dataInizioAnnoComboBox.setSelectedIndex(100); //mette di default il 100esimo indice cioè l'anno 2000
//////////////////////////////////////////////////////////
		
		panel_2.add(dataInizioAnnoComboBox);
		
		dataFineGiornoComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		dataFineGiornoComboBox.setUI(new BasicComboBoxUI());
		
		dataFineGiornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineGiornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dataFineGiornoComboBox.setSelectedIndex(0);
		dataFineGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineGiornoComboBox.setBackground(Color.WHITE);
		
		dataFineMeseComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		dataFineMeseComboBox.setUI(new BasicComboBoxUI());
		
		dataFineMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataFineMeseComboBox.setSelectedIndex(0);
		dataFineMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineMeseComboBox.setBackground(Color.WHITE);
		
		dataFineAnnoComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		dataFineAnnoComboBox.setUI(new BasicComboBoxUI());
		
		dataFineAnnoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineAnnoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineAnnoComboBox.setBackground(Color.WHITE);
		dataFineAnnoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineAnnoComboBox.setBounds(358, 235, 62, 22);
		//CREO UN MODELLO PERSONALIZZATO PER L'ANNO DEL COMBO BOX //////////
		DefaultComboBoxModel myModel2 = new DefaultComboBoxModel();
		dataFineAnnoComboBox.setModel(myModel2);
		
		for(int i=1900;i<= 2021;i++)
			myModel2.addElement(i);
		
		dataFineAnnoComboBox.setSelectedIndex(100); //mette di default il 100esimo indice cioè l'anno 2000
//////////////////////////////////////////////////////////
		
		panel_2.add(dataFineAnnoComboBox);
		
		piattaformaComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		piattaformaComboBox.setUI(new BasicComboBoxUI());
		
		piattaformaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		piattaformaComboBox.setBackground(Color.WHITE);
		piattaformaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		piattaformaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		piattaformaComboBox.setModel(new DefaultComboBoxModel(new String[] {"","Microsoft Teams","Discord","Skype"}));
		
		oraInizioComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		oraInizioComboBox.setUI(new BasicComboBoxUI());
		
		oraInizioComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		oraInizioComboBox.setBackground(Color.WHITE);
		oraInizioComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		oraInizioComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		oraInizioComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		minutoInizioComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		minutoInizioComboBox.setUI(new BasicComboBoxUI());
		
		minutoInizioComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		minutoInizioComboBox.setBackground(Color.WHITE);
		minutoInizioComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minutoInizioComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		minutoInizioComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		
		oraFineComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		oraFineComboBox.setUI(new BasicComboBoxUI());
		
		oraFineComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		oraFineComboBox.setBackground(Color.WHITE);
		oraFineComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		oraFineComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		oraFineComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		minutoFineComboBox = new JComboBox();
		
		//modifica lo stille della combo box
		minutoFineComboBox.setUI(new BasicComboBoxUI());
		
		minutoFineComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		minutoFineComboBox.setBackground(Color.WHITE);
		minutoFineComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minutoFineComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		minutoFineComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		JScrollPane invitatiScrollPane = new JScrollPane();
		invitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JScrollPane progettoDiscussoScrollPane = new JScrollPane();
		progettoDiscussoScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(modalitaLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(oraInizioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(idMeetingLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(creatoreProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(idMeetingTextField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
							.addComponent(piattaformaComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panel_2.createSequentialGroup()
								.addComponent(onlineRadioButton)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(fisicoRadioButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
					.addGap(89)
					.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
					.addGap(54)
					.addComponent(progettoDiscussoScrollPane, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
					.addGap(48))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(idMeetingLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(idMeetingTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel)
								.addComponent(minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
								.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(modalitaLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(onlineRadioButton)
								.addComponent(fisicoRadioButton))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(creatoreProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(piattaformaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(invitatiScrollPane, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
						.addComponent(progettoDiscussoScrollPane))
					.addGap(26))
		);
		
		JTextArea ProgettoDiscussoTextArea = new JTextArea();
		progettoDiscussoScrollPane.setViewportView(ProgettoDiscussoTextArea);
		
		JLabel progettoDiscussoLabel = new JLabel("Progetto Discusso");
		progettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettoDiscussoScrollPane.setColumnHeaderView(progettoDiscussoLabel);
		
		JList invitatiList = new JList();
		invitatiScrollPane.setViewportView(invitatiList);
		
		JLabel invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiScrollPane.setColumnHeaderView(invitatiLabel);
		panel_2.setLayout(gl_panel_2);
		panel_1.add(panel_2);
		panel.setLayout(gl_panel);
		
		JLabel gestioneMeetingLabel = new JLabel("Gestione Meeting");
		gestioneMeetingLabel.setIcon(new ImageIcon(GestioneMeeting.class.getResource("/Icone/meeting_64.png")));
		gestioneMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(gestioneMeetingLabel)
					.addContainerGap(1208, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestioneMeetingLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
