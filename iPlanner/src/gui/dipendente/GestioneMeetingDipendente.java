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
import java.awt.HeadlessException;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerMeeting;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;
import gui.cellRenderers.InvitatiListRenderer;
import gui.cellRenderers.ProgettoDiscussoListRenderer;
import gui.cellRenderers.ProgettoListRenderer;
import gui.tableModels.MeetingTableModel;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;



public class GestioneMeetingDipendente extends JFrame {

	//ATTRIBUTI
	//---------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTable progettoTable;
	private JTextField cercaTextField;
	private JLabel modalitaLabel;
	private JLabel piattaformaSalaLabel;
	private final ButtonGroup modalitàButtonGroup = new ButtonGroup();
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
	private JComboBox progettoDiscussoComboBox;
	private JRadioButton onlineRadioButton;
	private JRadioButton fisicoRadioButton;
	private JComboBox piattaformaSalaComboBox;
	private JTable meetingTable;
	private MeetingTableModel dataModelMeeting;
	private DefaultListModel listmodelProgetti;
	private JButton pulisciButton;
	private JButton eliminaButton;
	private JButton modificaButton;
	private JButton creaNuovoMeetingButton;
	private JButton inserisciPartecipanteButton;
	private JList invitatiList;
	private JList progettoDiscussoList;
	private JLabel infoProgettoDiscussoLabel;
	private JLabel progettoDiscussoLabel;
	private int codiceMeeting;
	private TableRowSorter<TableModel> sorterMeeting;
	
	//ricava l'ora e la data attuali
	LocalDate dataAttuale = LocalDate.now();
	LocalTime oraAttuale = LocalTime.now();

	//Creazione frame
	//---------------------------------------------
	public GestioneMeetingDipendente(ControllerMeeting theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1300, 700));
		
		setTitle("GestioneMeeting");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();	//main panel
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();
		
		JPanel comandiPanel = new JPanel();
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane meetingScrollPane = new JScrollPane();
		meetingScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		meetingScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		comandiPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel comandiPanel2 = new JPanel();	//panel interno a quello dei comandi
		comandiPanel2.setBorder(new LineBorder(new Color(192, 192, 192)));
		
		//Button "Pulisci Campi"
		pulisciButton = new JButton("Pulisci Campi");
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
		//Click sul pulsante
		pulisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				

				//Imposta data di inizio a oggi
				dataInizioGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1); //-1 perche gli indici iniziano da 0
				dataInizioMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
				dataInizioAnnoComboBox.setSelectedIndex(dataAttuale.getYear() -1900);// -1900 perche nella comboBox gli anni vanno dal 1900 all anno attuale //sarà il (2021 - 1900) 121 esimo indice
				//Imposta data di fine a oggi
				dataFineGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
				dataFineMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
				dataFineAnnoComboBox.setSelectedIndex(dataAttuale.getYear() -1900);
				//Imposta orario di inizio a ora
				oraInizioComboBox.setSelectedIndex(oraAttuale.getHourOfDay());
				minutoInizioComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
				//Imposta orario di fine a 2 ore da ora
				oraFineComboBox.setSelectedIndex(oraAttuale.getHourOfDay() +2);
				minutoFineComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
				//Imposta sia online che fisico a falso
				onlineRadioButton.setSelected(false);
				fisicoRadioButton.setSelected(false);
				//setta la combobox della piattaforma/sala a null
				piattaformaSalaComboBox.setSelectedItem(null);	
				
				progettoDiscussoComboBox.setSelectedItem(null);

				
				DefaultListModel listmodel=new DefaultListModel();
				invitatiList.setModel(listmodel);
				listmodel.removeAllElements();
				
				DefaultListModel listmodelProgetti=new DefaultListModel();
				progettoDiscussoList.setModel(listmodelProgetti);
				listmodelProgetti.removeAllElements();
			
			
				
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
		
		//Button "Elimina"
		eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=meetingTable.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(null, "Seleziona una riga dalla tabella");
					
				}
				else {
					
					Meeting meeting=dataModelMeeting.getMeetingTabella().get(row);
					
					try {
						theController.rimuoviMeeting(meeting.getIdMeeting());
						JOptionPane.showMessageDialog(null, "Meeting Eliminato Correttamente");
						
						//Aggiorna i meeting nella tabella
						dataModelMeeting.fireTableDataChanged();
						dataModelMeeting.setMeetingTabella(theController.ottieniMeeting()); 
						
						 //Aggiorna il modello del sorterMeeting in seguito alle modifiche
						sorterMeeting.setModel(dataModelMeeting);
						
					} catch (SQLException e1) {
						
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					
				}
			
			}
		});
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
		
		
		//Button "Conferma Modifiche"
		modificaButton = new JButton("Conferma Modifiche");
		//Click sul pulsante
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				modalitaLabel.setForeground(Color.BLACK);
				piattaformaSalaLabel.setForeground(Color.BLACK);
				progettoDiscussoLabel.setForeground(Color.BLACK);
				int row=meetingTable.getSelectedRow();
				
				if(row==-1) {
					JOptionPane.showMessageDialog(null, "Selezionare un meeting da modificare dalla tabella");
					
				}
			
				else if(!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected() || piattaformaSalaComboBox.getSelectedItem()==null || progettoDiscussoComboBox.getSelectedItem()==null)
				{
				
					
					JOptionPane.showMessageDialog(null, "Controllare i campi inseriti");
					
					if(!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected()){
						modalitaLabel.setForeground(Color.RED);
					}
			
					if(piattaformaSalaComboBox.getSelectedItem()==null) {
						piattaformaSalaLabel.setForeground(Color.RED);
					}
					
					if(progettoDiscussoComboBox.getSelectedItem()==null) {
						progettoDiscussoLabel.setForeground(Color.RED);
					}
					
				}
				
			
				//Update del meeting
				else {
				Meeting meeting=dataModelMeeting.getMeetingTabella().get(row);
				LocalDate dataInizio = new LocalDate(Integer.valueOf(dataInizioAnnoComboBox.getSelectedItem().toString()), Integer.valueOf(dataInizioMeseComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));	//data inizio
				LocalDate dataFine = new LocalDate(Integer.valueOf(dataFineAnnoComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineMeseComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));	//data fine
				LocalTime oraInizio = new LocalTime(Integer.valueOf(oraInizioComboBox.getSelectedIndex()), Integer.valueOf(minutoInizioComboBox.getSelectedIndex()), 0);	//ora inizio
				LocalTime oraFine = new LocalTime(Integer.valueOf(oraFineComboBox.getSelectedIndex()), Integer.valueOf(minutoFineComboBox.getSelectedIndex()), 0);	//ora fine
				String modalita = "";
				String piattaforma = null;				
				SalaRiunione sala = null;
				//modalità online e piattaforma
				if (onlineRadioButton.isSelected()) {
					modalita = "Telematico";
					piattaforma = piattaformaSalaComboBox.getSelectedItem().toString();
				}
				//modalità fisica e sala
				else if (fisicoRadioButton.isSelected()) {
					modalita = "Fisico";
					sala = (SalaRiunione) piattaformaSalaComboBox.getSelectedItem();
				}
				
				String nomeProgettoSelezionato=(String) progettoDiscussoComboBox.getSelectedItem();
				
				Meeting meetingAggiornato = new Meeting(meeting.getIdMeeting(),dataInizio,dataFine,oraInizio,oraFine,modalita,piattaforma,sala);	//crea il meeting modificato
				
					
				
				try {
					theController.aggiornaMeeting(meetingAggiornato,nomeProgettoSelezionato);	//tenta di fare l'update nel DB del meeting
					JOptionPane.showMessageDialog(null, "Meeting Modificato");
					
					//Aggiorna i dati nella tabella con le modifiche effettuate
					dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());
					
					//Aggiorna il modello del sorterMeeting in seguito alle modifiche effettuate
					sorterMeeting.setModel(dataModelMeeting);
					
					meetingScrollPane.setViewportView(meetingTable);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
				
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
		
		//Butotn "Crea Nuovo"
		creaNuovoMeetingButton = new JButton("Crea Nuovo");
		//Click sul pulsante
		creaNuovoMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				modalitaLabel.setForeground(Color.BLACK);
				piattaformaSalaLabel.setForeground(Color.BLACK);
				progettoDiscussoLabel.setForeground(Color.BLACK);
				
				if((onlineRadioButton.isSelected() || fisicoRadioButton.isSelected()) && piattaformaSalaComboBox.getSelectedItem()!=null && progettoDiscussoComboBox.getSelectedItem()!=null)
				{
				
					
					try {
						//richiama la funzione insertMeeting
						insertMeeting(theController);
						
					} catch (SQLException e1) {
						
						JOptionPane.showMessageDialog(null, "Controlla di aver inserito il progetto discusso");
					}
					
				}
				else if((!onlineRadioButton.isSelected() || !fisicoRadioButton.isSelected()) || piattaformaSalaComboBox.getSelectedItem()==null ||progettoDiscussoComboBox.getSelectedItem()!=null)
				{
					
				
					
					JOptionPane.showMessageDialog(null, "Controllare i campi inseriti");
					
					if(!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected()){
						modalitaLabel.setForeground(Color.RED);
					}
			
					if(piattaformaSalaComboBox.getSelectedItem()==null) {
						piattaformaSalaLabel.setForeground(Color.RED);
					}
					
					if(progettoDiscussoComboBox.getSelectedItem()==null) {
						progettoDiscussoLabel.setForeground(Color.RED);
					}
				}
				
				
				
			}
		});
		creaNuovoMeetingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaNuovoMeetingButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaNuovoMeetingButton.setBackground(Color.WHITE);
			}
		});
		
		
	
	
		creaNuovoMeetingButton.setPreferredSize(new Dimension(130, 30));
		creaNuovoMeetingButton.setMaximumSize(new Dimension(150, 150));
		creaNuovoMeetingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaNuovoMeetingButton.setBackground(Color.WHITE);
		creaNuovoMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaNuovoMeetingButton.setMargin(new Insets(2, 20, 2, 20));
		creaNuovoMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		creaNuovoMeetingButton.setAlignmentX(0.5f);
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Label "Cerca"
		JLabel cercaLabel = new JLabel("Cerca");
		cercaLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		comandiPanel2.add(cercaLabel);
		
		//TextField per cercare un meeting
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel2.add(cercaTextField);
		cercaTextField.setColumns(10);
		
		comandiPanel2.add(modificaButton);
		comandiPanel2.add(creaNuovoMeetingButton);
		comandiPanel2.add(eliminaButton);
		comandiPanel2.add(pulisciButton);
		comandiPanel.add(comandiPanel2);
		
		//panel interno a quello delle info
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		//Label "Data inizio"
		JLabel dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//ComboBox giorno data di inizio
		dataInizioGiornoComboBox = new JComboBox();
		dataInizioGiornoComboBox.setUI(new BasicComboBoxUI());
		dataInizioGiornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioGiornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dataInizioGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
		dataInizioGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioGiornoComboBox.setBackground(Color.WHITE);
		
		//ComboBox mese data di inizio
		dataInizioMeseComboBox = new JComboBox();
		dataInizioMeseComboBox.setUI(new BasicComboBoxUI());
		dataInizioMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataInizioMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
		dataInizioMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioMeseComboBox.setBackground(Color.WHITE);
		
		//ComboBox anno data di inizio
		dataInizioAnnoComboBox = new JComboBox();
		dataInizioAnnoComboBox.setUI(new BasicComboBoxUI());
		dataInizioAnnoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioAnnoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioAnnoComboBox.setBackground(Color.WHITE);
		dataInizioAnnoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioAnnoComboBox.setBounds(358, 235, 62, 22);
		DefaultComboBoxModel myModel = new DefaultComboBoxModel();	//definizione modello combobox
		dataInizioAnnoComboBox.setModel(myModel);
		for(int i=1900;i<= 2021;i++)
			myModel.addElement(i);
		dataInizioAnnoComboBox.setSelectedIndex(dataAttuale.getYear() -1900);
		infoPanel2.add(dataInizioAnnoComboBox);
		
		//Label "Data fine"
		JLabel dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//ComboBox giorno data di fine
		dataFineGiornoComboBox = new JComboBox();
		dataFineGiornoComboBox.setUI(new BasicComboBoxUI());
		dataFineGiornoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineGiornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dataFineGiornoComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
		dataFineGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineGiornoComboBox.setBackground(Color.WHITE);
		
		//ComboBox mese data di fine
		dataFineMeseComboBox = new JComboBox();
		dataFineMeseComboBox.setUI(new BasicComboBoxUI());
		dataFineMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataFineMeseComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);
		dataFineMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineMeseComboBox.setBackground(Color.WHITE);
		
		//ComboBoc anno data di fine
		dataFineAnnoComboBox = new JComboBox();
		dataFineAnnoComboBox.setUI(new BasicComboBoxUI());
		dataFineAnnoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineAnnoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineAnnoComboBox.setBackground(Color.WHITE);
		dataFineAnnoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineAnnoComboBox.setBounds(358, 235, 62, 22);
		DefaultComboBoxModel myModel2 = new DefaultComboBoxModel();
		dataFineAnnoComboBox.setModel(myModel2);
		for(int i=1900;i<= 2021;i++)
			myModel2.addElement(i);
		dataFineAnnoComboBox.setSelectedIndex(dataAttuale.getYear() -1900);
		infoPanel2.add(dataFineAnnoComboBox);
		
		//Label "Ora inizio"
		JLabel oraInizioLabel = new JLabel("Ora inizio");
		oraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		oraInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//ComboBox ora orario inizio
		oraInizioComboBox = new JComboBox();
		oraInizioComboBox.setUI(new BasicComboBoxUI());
		oraInizioComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		oraInizioComboBox.setBackground(Color.WHITE);
		oraInizioComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		oraInizioComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		oraInizioComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		oraInizioComboBox.setSelectedIndex(oraAttuale.getHourOfDay());
		
		//Label ":"
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		//ComboBox minuti orario inizio
		minutoInizioComboBox = new JComboBox();
		minutoInizioComboBox.setUI(new BasicComboBoxUI());
		minutoInizioComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		minutoInizioComboBox.setBackground(Color.WHITE);
		minutoInizioComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minutoInizioComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		minutoInizioComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		minutoInizioComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
		
		//Label "Ora fine"
		JLabel oraFineLabel = new JLabel("Ora fine");
		oraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		oraFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//ComboBox ora orario fine
		oraFineComboBox = new JComboBox();
		oraFineComboBox.setUI(new BasicComboBoxUI());
		oraFineComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		oraFineComboBox.setBackground(Color.WHITE);
		oraFineComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		oraFineComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		oraFineComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		oraFineComboBox.setSelectedIndex(oraAttuale.getHourOfDay() +2);
		
		//Label ":"
		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		//ComboBox minuti orario fine
		minutoFineComboBox = new JComboBox();
		minutoFineComboBox.setUI(new BasicComboBoxUI());
		minutoFineComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		minutoFineComboBox.setBackground(Color.WHITE);
		minutoFineComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minutoFineComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		minutoFineComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		minutoFineComboBox.setSelectedIndex(oraAttuale.getMinuteOfHour());
		
		//Label "Modalità"
		modalitaLabel = new JLabel("Modalità");
		modalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		modalitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Label "Piattaforma"/"Sala"
		piattaformaSalaLabel = new JLabel("Piattaforma");
		piattaformaSalaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		piattaformaSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//ComboBox piattaforma/sala
		piattaformaSalaComboBox = new JComboBox();
		piattaformaSalaComboBox.setUI(new BasicComboBoxUI());
		piattaformaSalaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		piattaformaSalaComboBox.setBackground(Color.WHITE);
		piattaformaSalaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		piattaformaSalaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		//RadioButton "Online"
		onlineRadioButton = new JRadioButton("Online");
		
		onlineRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		onlineRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		onlineRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(onlineRadioButton.isSelected()==true)
				{
					piattaformaSalaLabel.setText("Piattaforma");
					try {
						piattaformaSalaComboBox.setModel(new DefaultComboBoxModel(theController.ottieniPiattaforme().toArray()));
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					fisicoRadioButton.setSelected(false);
				}
			}
		});
		modalitàButtonGroup.add(onlineRadioButton);
		
		//RadioButton "Fisico"
		fisicoRadioButton = new JRadioButton("Fisico");
		fisicoRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fisicoRadioButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		fisicoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fisicoRadioButton.isSelected()==true)
				{
					piattaformaSalaLabel.setText("Sala");
					try {
						piattaformaSalaComboBox.setModel(new DefaultComboBoxModel(theController.ottieniSale().toArray()));
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					onlineRadioButton.setSelected(false);
				}
			}
		});
		modalitàButtonGroup.add(fisicoRadioButton);
		
		//scroll pane invitati
		JScrollPane invitatiScrollPane = new JScrollPane();
		invitatiList=new JList();
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		invitatiList.setSelectionBackground(Color.WHITE);
		invitatiList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		InvitatiListRenderer invitatiListRenderer=new InvitatiListRenderer();
		invitatiList.setCellRenderer(invitatiListRenderer);
		
		invitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		invitatiScrollPane.setViewportView(invitatiList);
		
		progettoDiscussoComboBox = new JComboBox();
		progettoDiscussoComboBox.setUI(new BasicComboBoxUI());
		progettoDiscussoComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		progettoDiscussoComboBox.setBackground(Color.WHITE);
		progettoDiscussoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettoDiscussoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		//A seconda del progetto selezionato nella ComboBox aggiorna la lista con le info del progetto selezionato
		progettoDiscussoComboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(progettoDiscussoComboBox.getSelectedItem()!=null)
		        {
		            try {
						Progetto progetto=theController.ottieniProgettoInserito((Progetto)progettoDiscussoComboBox.getSelectedItem());
						listmodelProgetti=new DefaultListModel();
						progettoDiscussoList.setModel(listmodelProgetti);
						listmodelProgetti.removeAllElements();
						listmodelProgetti.add(0,progetto);
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
		        	
		        }
		   
		        
		        
		    }
		});
		
		try {
			
			progettoDiscussoComboBox.setModel(new DefaultComboBoxModel(theController.ottieniNomiProgetti().toArray()));
			progettoDiscussoComboBox.setSelectedItem(null);
		} catch (SQLException e2) {
			
			e2.printStackTrace();
		}
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	
		
		progettoDiscussoLabel = new JLabel("Progetto discusso");
		progettoDiscussoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		progettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JScrollPane progettoDiscussoScrollPane = new JScrollPane();
		progettoDiscussoScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addContainerGap()
							.addComponent(progettoDiscussoLabel, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_infoPanel2.createSequentialGroup()
								.addContainerGap()
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addContainerGap()
									.addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addContainerGap()
									.addComponent(piattaformaSalaLabel, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(43)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addComponent(modalitaLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addContainerGap()
									.addComponent(oraInizioLabel, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_infoPanel2.createSequentialGroup()
										.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
									.addGroup(Alignment.LEADING, gl_infoPanel2.createSequentialGroup()
										.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNewLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(onlineRadioButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(fisicoRadioButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(piattaformaSalaComboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(progettoDiscussoComboBox, Alignment.LEADING, 0, 132, Short.MAX_VALUE)))
					.addGap(112)
					.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(progettoDiscussoScrollPane, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
					.addGap(53))
		);
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
							.addComponent(invitatiScrollPane, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
							.addComponent(progettoDiscussoScrollPane, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
									.addGap(16))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addGap(11)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel)
										.addComponent(minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
										.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_infoPanel2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
										.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(modalitaLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(onlineRadioButton)
								.addComponent(fisicoRadioButton))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(piattaformaSalaLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(piattaformaSalaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(progettoDiscussoComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(progettoDiscussoLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))))
					.addGap(36))
		);
		
		progettoDiscussoList = new JList();
		ProgettoDiscussoListRenderer progettoDiscussoCellRenderer = new ProgettoDiscussoListRenderer();
		progettoDiscussoList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		progettoDiscussoList.setFont(new Font("Consolas", Font.PLAIN, 12));
		progettoDiscussoList.setSelectionBackground(Color.WHITE);
		progettoDiscussoScrollPane.setViewportView(progettoDiscussoList);
		progettoDiscussoList.setCellRenderer(progettoDiscussoCellRenderer);
		
		infoProgettoDiscussoLabel = new JLabel("Info progetto discusso");
		infoProgettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettoDiscussoScrollPane.setColumnHeaderView(infoProgettoDiscussoLabel);
		
		//Label "Invitati"
		JLabel invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiScrollPane.setColumnHeaderView(invitatiLabel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		
		//Label "Gestione Meeting"
		JLabel gestioneMeetingLabel = new JLabel("Gestione Meeting");
		gestioneMeetingLabel.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/Icone/meeting_64.png")));
		gestioneMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestioneMeetingLabel)
					.addContainerGap(914, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestioneMeetingLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		//Table dei meeting
		dataModelMeeting=new MeetingTableModel();
		meetingTable = new JTable(dataModelMeeting);
		meetingTable.setFont(new Font("Consolas", Font.PLAIN, 11));
		meetingTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meetingTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingTable.setBackground(Color.WHITE);
		meetingTable.setSelectionBackground(Color.LIGHT_GRAY);
		
		//Setta i meeting nella tabella
		try {
			dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());	
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		
		//Sorter tabella meeting
		sorterMeeting=new TableRowSorter<>(dataModelMeeting);
		meetingTable.setRowSorter(sorterMeeting);
		
		
		
		meetingTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//Ottiene l'indice di riga selezionata
				int row= meetingTable.getSelectedRow();	
				
				//Formatter Data e Ora
				DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
				DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
				
				//Data Inizio
				LocalDate dataInizio= formatDate.parseLocalDate(meetingTable.getValueAt(row, 0).toString());
				dataInizioAnnoComboBox.setSelectedItem(dataInizio.getYear());
				dataInizioMeseComboBox.setSelectedIndex(dataInizio.getMonthOfYear()-1);
				dataInizioGiornoComboBox.setSelectedIndex(dataInizio.getDayOfMonth()-1);
				
				//Data fine
				LocalDate dataFine=formatDate.parseLocalDate(meetingTable.getValueAt(row, 1).toString());	
				dataFineAnnoComboBox.setSelectedItem(dataFine.getYear());
				dataFineMeseComboBox.setSelectedIndex(dataFine.getMonthOfYear()-1);
				dataFineGiornoComboBox.setSelectedIndex(dataFine.getDayOfMonth()-1);
				
				//Orario inizio
				LocalTime oraInizio=formatHour.parseLocalTime(meetingTable.getValueAt(row, 2).toString());	
				oraInizioComboBox.setSelectedIndex(oraInizio.getHourOfDay());
				minutoInizioComboBox.setSelectedIndex(oraInizio.getMinuteOfHour());
				
				//Orario fine
				LocalTime oraFine=formatHour.parseLocalTime(meetingTable.getValueAt(row, 3).toString());		
				oraFineComboBox.setSelectedIndex(oraFine.getHourOfDay());
				minutoFineComboBox.setSelectedIndex(oraFine.getMinuteOfHour());
				
				//Modalità
				try {
					if(theController.ottieniPiattaforme().toString().contains(meetingTable.getValueAt(row, 4).toString()))
					{
						piattaformaSalaLabel.setText("Piattaforma");
						try {
							piattaformaSalaComboBox.setModel(new DefaultComboBoxModel(theController.ottieniPiattaforme().toArray()));
							onlineRadioButton.setSelected(true);
							fisicoRadioButton.setSelected(false);
							piattaformaSalaComboBox.setSelectedItem(meetingTable.getValueAt(row, 4));
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					//modalità fisica e sala
					else {
						piattaformaSalaLabel.setText("Sala");
						try {
							piattaformaSalaComboBox.setModel(new DefaultComboBoxModel(theController.ottieniSale().toArray()));
							piattaformaSalaComboBox.setSelectedItem(meetingTable.getValueAt(row, 4));
							fisicoRadioButton.setSelected(true);
							onlineRadioButton.setSelected(false);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				} catch (HeadlessException e2) {
					
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} catch (SQLException e2) {
					
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				
				
				Meeting meetingRiga=dataModelMeeting.getMeetingTabella().get(row);
				
				
				//Progetto discusso
				
				progettoDiscussoComboBox.setSelectedItem(meetingRiga.toString()); //Setta come elemento selezionato il progetto relativo al meeting

					DefaultListModel listmodel=new DefaultListModel();
					invitatiList.setModel(listmodel);
				
					try {
						dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//Prende una riga della tabella
					Meeting meeting=dataModelMeeting.getMeetingTabella().get(row); 
					
					//Aggiunge alla lista i partecipanti al meeting selezionato
					listmodel.removeAllElements();
					listmodel.addAll(meeting.getPartecipantiAlMeeting()); 
					
			
	

			}		
		});
	
		meetingScrollPane.setViewportView(meetingTable);
		
		//Button "Inserisci partecipanti"
				inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
				inserisciPartecipanteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int row=meetingTable.getSelectedRow();
						if(row==-1) {
							JOptionPane.showMessageDialog(null, "Seleziona una riga dalla tabella");
							
						}
						else {
							//Prende il meeting selezionato e lo passa al controller
							Meeting meeting=dataModelMeeting.getMeetingTabella().get(row);
							theController.apriInserisciPartecipantiMeeting(meeting,codiceMeeting);
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
				inserisciPartecipanteButton.setPreferredSize(new Dimension(190, 30));
				inserisciPartecipanteButton.setMaximumSize(new Dimension(150, 150));
				inserisciPartecipanteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				inserisciPartecipanteButton.setBackground(Color.WHITE);
				inserisciPartecipanteButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
				inserisciPartecipanteButton.setMargin(new Insets(2, 20, 2, 20));
				inserisciPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
				inserisciPartecipanteButton.setAlignmentX(0.5f);
				comandiPanel2.add(inserisciPartecipanteButton);
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
								.addComponent(comandiPanel, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
								.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
							.addGap(10))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(23)
							.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
							.addGap(11))
				);
				panel.setLayout(gl_panel);
				
	
		
	}
	public void insertMeeting(ControllerMeeting theController) throws SQLException {
	
		infoProgettoDiscussoLabel.setBackground(Color.BLACK);
		LocalDate dataInizio = null;
		LocalDate dataFine = null;
		try {
			dataInizio = new LocalDate(Integer.valueOf(dataInizioAnnoComboBox.getSelectedItem().toString()), Integer.valueOf(dataInizioMeseComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));
			dataFine = new LocalDate(Integer.valueOf(dataFineAnnoComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineMeseComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));
		} catch (IllegalFieldValueException e) {
		
			JOptionPane.showMessageDialog(null, "Data inserita non valida");
		}
		LocalTime oraInizio = new LocalTime(Integer.valueOf(oraInizioComboBox.getSelectedIndex()), Integer.valueOf(minutoInizioComboBox.getSelectedIndex()), 0);	//ora inizio
		LocalTime oraFine = new LocalTime(Integer.valueOf(oraFineComboBox.getSelectedIndex()), Integer.valueOf(minutoFineComboBox.getSelectedIndex()), 0);	//ora fine
		String modalita = "";
		String piattaforma = null;				
		SalaRiunione sala = null;
		
	
		//modalità online e piattaforma
		if (onlineRadioButton.isSelected()) {
			modalita = "Telematico";
			piattaforma = piattaformaSalaComboBox.getSelectedItem().toString();	
		}
		//modalità fisica e sala
		else if (fisicoRadioButton.isSelected()) {
			modalita = "Fisico";
			sala = (SalaRiunione) piattaformaSalaComboBox.getSelectedItem();
		}
		Meeting meetingInserito = new Meeting(dataInizio,dataFine,oraInizio,oraFine,modalita,piattaforma,sala);	//crea il meeting inserito
		try {
			Progetto progetto=theController.ottieniProgettoInserito((Progetto)progettoDiscussoComboBox.getSelectedItem());
			theController.inserisciMeetingCompleto(meetingInserito,progetto);	//tenta di fare l'insert nel DB del meeting
			JOptionPane.showMessageDialog(null, "Meeting Inserito Correttamente");
			
			//Aggiorna i dati nella tabella in seguito all'inserimento effettuato
			dataModelMeeting.fireTableDataChanged();
			dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());
			
			
			//Aggiorna il modello del sorterMeeting in seguito all'inserimento effettuato
			sorterMeeting.setModel(dataModelMeeting);
		
		
		} catch (SQLException e1) {
			
			
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		
		
	}
}
