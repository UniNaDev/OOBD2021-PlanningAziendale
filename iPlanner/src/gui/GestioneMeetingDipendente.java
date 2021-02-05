package gui;

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

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.ReadablePartial;

import controller.ControllerMeeting;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.SalaRiunione;

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



public class GestioneMeetingDipendente extends JFrame {

	//ATTRIBUTI
	//---------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTextField idMeetingTextField;
	private JTable progettoTable;
	private JTextField cercaTextField;
	private JLabel modalitaLabel;
	private JLabel piattaformaSalaLabel;
	private JLabel progettoDiscussoLabel;
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
	private JRadioButton onlineRadioButton;
	private JRadioButton fisicoRadioButton;
	private JComboBox piattaformaSalaComboBox;
	private JTable meetingTable;
	private MeetingTableModel dataModelMeeting;
	private JButton pulisciButton;
	private JButton eliminaButton;
	private JButton modificaButton;
	private JButton creaNuovoMeetingButton;
	private JList invitatiList;
	private int codiceMeeting;
	
	private JTextArea ProgettoDiscussoTextArea;

	//Creazione frame
	//---------------------------------------------
	public GestioneMeetingDipendente(ControllerMeeting theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneMeetingDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1150, 700));
		setLocationRelativeTo(null);
		
		setTitle("GestioneMeeting");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();	//main panel
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();	//panel info
		
		JPanel comandiPanel = new JPanel();	//panel per i comandi
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane meetingScrollPane = new JScrollPane();	//scroll pane per la tabella meeting
		meetingScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		meetingScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comandiPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(infoPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addContainerGap())
		);
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
				idMeetingTextField.setText("");	//id meeting vuoto
				//ricava l'ora e la data attuali
				LocalDate dataAttuale = LocalDate.now();
				LocalTime oraAttuale = LocalTime.now();
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
				
				

				
				DefaultListModel listmodel=new DefaultListModel();
				invitatiList.setModel(listmodel);
				listmodel.removeAllElements();
				
				
				ProgettoDiscussoTextArea.setText(null);
			
			
				
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
					
					int idMeeting=(int) meetingTable.getValueAt(row, 0);
					
					try {
						theController.rimuoviMeeting(idMeeting);
						JOptionPane.showMessageDialog(null, "Meeting Eliminato Correttamente");
						
						dataModelMeeting.fireTableDataChanged();
						dataModelMeeting.setMeetingTabella(theController.ottieniMeeting()); //Aggiorna i meeting nella tabella
						
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
				if(idMeetingTextField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Selezionare un meeting da modificare dalla tabella");
					
				}
				//Update del meeting
				else {
				int id = Integer.valueOf(idMeetingTextField.getText());	//id
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
				String progettoDiscusso=ProgettoDiscussoTextArea.getText();
				Progetto progetto=null;
				Meeting meetingAggiornato=null;
				try {
					progetto=theController.ottieniProgettoInserito(progettoDiscusso);
					meetingAggiornato = new Meeting(id,dataInizio,dataFine,oraInizio,oraFine,modalita,piattaforma,sala,progetto);	//crea il meeting modificato
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				try {
					theController.aggiornaMeeting(meetingAggiornato);	//tenta di fare l'update nel DB del meeting
					JOptionPane.showMessageDialog(null, "Meeting Modificato");
					dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());	//aggiorna i dati nella tabella con le modifiche fatte
					
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
				if((onlineRadioButton.isSelected() || fisicoRadioButton.isSelected()) && piattaformaSalaComboBox.getSelectedItem()!=null && !ProgettoDiscussoTextArea.getText().isBlank())
				{
				
					
					try {
						//richiama la funzione insertMeeting
						insertMeeting(theController);
						
					} catch (SQLException e1) {
						
						JOptionPane.showMessageDialog(null, "Controlla di aver inserito il progetto discusso");
					}
					
				}
				else if((!onlineRadioButton.isSelected() || !fisicoRadioButton.isSelected()) || piattaformaSalaComboBox.getSelectedItem()==null ||ProgettoDiscussoTextArea.getText().isBlank())
				{
					
					modalitaLabel.setForeground(Color.BLACK);
					piattaformaSalaLabel.setForeground(Color.BLACK);
					progettoDiscussoLabel.setForeground(Color.BLACK);
					
					JOptionPane.showMessageDialog(null, "Controllare i campi inseriti");
					
					if(!onlineRadioButton.isSelected() && !fisicoRadioButton.isSelected()){
						modalitaLabel.setForeground(Color.RED);
					}
			
					if(piattaformaSalaComboBox.getSelectedItem()==null) {
						piattaformaSalaLabel.setForeground(Color.RED);
					}
					
					if(ProgettoDiscussoTextArea.getText().isBlank()) {
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
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//panel interno a quello delle info
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		//Label "MeetingID"
		JLabel idMeetingLabel = new JLabel("MeetingID");
		idMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		idMeetingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//TextField per id meeting
		idMeetingTextField = new JTextField();
		idMeetingTextField.setEditable(false);
		idMeetingTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		idMeetingTextField.setColumns(10);
		
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
		dataInizioGiornoComboBox.setSelectedIndex(0);
		dataInizioGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioGiornoComboBox.setBackground(Color.WHITE);
		
		//ComboBox mese data di inizio
		dataInizioMeseComboBox = new JComboBox();
		dataInizioMeseComboBox.setUI(new BasicComboBoxUI());
		dataInizioMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataInizioMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataInizioMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataInizioMeseComboBox.setSelectedIndex(0);
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
		dataInizioAnnoComboBox.setSelectedIndex((int) myModel.getSize()/2); //mette di default l'anno a metà della lista
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
		dataFineGiornoComboBox.setSelectedIndex(0);
		dataFineGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineGiornoComboBox.setBackground(Color.WHITE);
		
		//ComboBox mese data di fine
		dataFineMeseComboBox = new JComboBox();
		dataFineMeseComboBox.setUI(new BasicComboBoxUI());
		dataFineMeseComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		dataFineMeseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataFineMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataFineMeseComboBox.setSelectedIndex(0);
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
		dataFineAnnoComboBox.setSelectedIndex((int) myModel.getSize()/2); //mette di default il 100esimo indice cioè l'anno 2000
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
		invitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		invitatiScrollPane.setViewportView(invitatiList);
		
		//scroll pane progetto discusso
		JScrollPane progettoDiscussoScrollPane = new JScrollPane();
		progettoDiscussoScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(modalitaLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(oraInizioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(idMeetingLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(piattaformaSalaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(idMeetingTextField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
							.addComponent(piattaformaSalaComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_infoPanel2.createSequentialGroup()
								.addComponent(onlineRadioButton)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(fisicoRadioButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_infoPanel2.createSequentialGroup()
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
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(idMeetingLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(idMeetingTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
									.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
									.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraInizioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel)
								.addComponent(minutoInizioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
									.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addComponent(oraFineComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
								.addComponent(minutoFineComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(modalitaLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(onlineRadioButton)
								.addComponent(fisicoRadioButton))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(piattaformaSalaLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(piattaformaSalaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(invitatiScrollPane, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
						.addComponent(progettoDiscussoScrollPane))
					.addGap(26))
		);
		
		//Label "Progetto Discusso"
		progettoDiscussoLabel = new JLabel("Progetto Discusso");
		progettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettoDiscussoScrollPane.setColumnHeaderView(progettoDiscussoLabel);
		
		//TextArea progetto discusso
		ProgettoDiscussoTextArea = new JTextArea();
		progettoDiscussoScrollPane.setViewportView(ProgettoDiscussoTextArea);
		
		//Label "Invitati"
		JLabel invitatiLabel = new JLabel("Invitati");
		invitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiScrollPane.setColumnHeaderView(invitatiLabel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		panel.setLayout(gl_panel);
		
		//Label "Gestione Meeting"
		JLabel gestioneMeetingLabel = new JLabel("Gestione Meeting");
		gestioneMeetingLabel.setIcon(new ImageIcon(GestioneMeetingDipendente.class.getResource("/Icone/meeting_64.png")));
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
		
		//Table dei meeting
		dataModelMeeting=new MeetingTableModel();
		meetingTable = new JTable(dataModelMeeting);
		meetingTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meetingTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingTable.setBackground(Color.WHITE);
		meetingTable.setSelectionBackground(Color.LIGHT_GRAY);
		try {
			dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());	//setta il modello di dati della tabella
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		
		//Click sulla tabella
		meetingTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row= meetingTable.getSelectedRow();	//ottiene l'indice di riga selezionata
				//ricava le info del meeting selezionato
				idMeetingTextField.setText(meetingTable.getValueAt(row, 0).toString());	//id
				LocalDate dataInizio=(LocalDate) meetingTable.getValueAt(row, 1);	//data inizio
				dataInizioAnnoComboBox.setSelectedItem(dataInizio.getYear());
				dataInizioMeseComboBox.setSelectedIndex(dataInizio.getMonthOfYear()-1);
				dataInizioGiornoComboBox.setSelectedIndex(dataInizio.getDayOfMonth()-1);
				LocalDate dataFine=(LocalDate) meetingTable.getValueAt(row, 2);	//data fine
				dataFineAnnoComboBox.setSelectedItem(dataFine.getYear());
				dataFineMeseComboBox.setSelectedIndex(dataFine.getMonthOfYear()-1);
				dataFineGiornoComboBox.setSelectedIndex(dataFine.getDayOfMonth()-1);
				LocalTime oraInizio=(LocalTime) meetingTable.getValueAt(row, 3);	//orario inizio
				oraInizioComboBox.setSelectedIndex(oraInizio.getHourOfDay());
				minutoInizioComboBox.setSelectedIndex(oraInizio.getMinuteOfHour());
				LocalTime oraFine=(LocalTime) meetingTable.getValueAt(row, 4);	//orario fine
				oraFineComboBox.setSelectedIndex(oraFine.getHourOfDay());
				minutoFineComboBox.setSelectedIndex(oraFine.getMinuteOfHour());
				//modalità online e piattaforma
				if(meetingTable.getValueAt(row, 5).equals("Telematico"))
				{
					piattaformaSalaLabel.setText("Piattaforma");
					try {
						piattaformaSalaComboBox.setModel(new DefaultComboBoxModel(theController.ottieniPiattaforme().toArray()));
						onlineRadioButton.setSelected(true);
						fisicoRadioButton.setSelected(false);
						piattaformaSalaComboBox.setSelectedItem(meetingTable.getValueAt(row, 6));
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				//modalità fisica e sala
				else {
					piattaformaSalaLabel.setText("Sala");
					try {
						piattaformaSalaComboBox.setModel(new DefaultComboBoxModel(theController.ottieniSale().toArray()));
						piattaformaSalaComboBox.setSelectedItem(meetingTable.getValueAt(row, 7));
						fisicoRadioButton.setSelected(true);
						onlineRadioButton.setSelected(false);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
					

					DefaultListModel listmodel=new DefaultListModel();
					invitatiList.setModel(listmodel);
				
					try {
						dataModelMeeting.setMeetingTabella(theController.ottieniMeeting());
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					Meeting meeting=dataModelMeeting.getMeetingTabella().get(row);
					
					listmodel.removeAllElements();
					listmodel.addAll(meeting.getPartecipazioniDipendenti());
	
				codiceMeeting=(int) meetingTable.getValueAt(row, 0);
				
				ProgettoDiscussoTextArea.setText(meetingTable.getValueAt(row, 8).toString());
				
				
			}		
		});
	
		meetingScrollPane.setViewportView(meetingTable);
		
		//Button "Inserisci partecipanti"
				JButton inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
				inserisciPartecipanteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
//						LocalDate dataInizio = new LocalDate(Integer.valueOf(dataInizioAnnoComboBox.getSelectedItem().toString()), Integer.valueOf(dataInizioMeseComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));	//data inizio
//						LocalDate dataFine = new LocalDate(Integer.valueOf(dataFineAnnoComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineMeseComboBox.getSelectedItem().toString()), Integer.valueOf(dataFineGiornoComboBox.getSelectedItem().toString()));	//data fine
//						LocalTime oraInizio = new LocalTime(Integer.valueOf(oraInizioComboBox.getSelectedIndex()), Integer.valueOf(minutoInizioComboBox.getSelectedIndex()), 0);	//ora inizio
//						LocalTime oraFine = new LocalTime(Integer.valueOf(oraFineComboBox.getSelectedIndex()), Integer.valueOf(minutoFineComboBox.getSelectedIndex()), 0);	//ora fine
//						String modalita = "";
//						String piattaforma = null;				
//						SalaRiunione sala = null;
//						
//					
//						//modalità online e piattaforma
//						if (onlineRadioButton.isSelected()) {
//							modalita = "Telematico";
//							piattaforma = piattaformaSalaComboBox.getSelectedItem().toString();	
//						}
//						//modalità fisica e sala
//						else if (fisicoRadioButton.isSelected()) {
//							modalita = "Fisico";
//							sala = (SalaRiunione) piattaformaSalaComboBox.getSelectedItem();
//						}
//						Meeting meetingSelezionato = new Meeting(dataInizio,dataFine,oraInizio,oraFine,modalita,piattaforma,sala);
						
						int row=meetingTable.getSelectedRow();
						if(row==-1) {
							JOptionPane.showMessageDialog(null, "Seleziona una riga dalla tabella");
							
						}
						else {
							
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
				
	
		
	}
	public void insertMeeting(ControllerMeeting theController) throws SQLException {
	
		progettoDiscussoLabel.setBackground(Color.BLACK);
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
		Meeting meetingInserito = new Meeting(dataInizio,dataFine,oraInizio,oraFine,modalita,piattaforma,sala);	//crea il meeting inserito
		try {
			Progetto progetto=theController.ottieniProgettoInserito(ProgettoDiscussoTextArea.getText());
			theController.inserisciMeetingCompleto(meetingInserito,progetto);	//tenta di fare l'insert nel DB del meeting
			JOptionPane.showMessageDialog(null, "Meeting Inserito Correttamente");
			dataModelMeeting.fireTableDataChanged();
			dataModelMeeting.setMeetingTabella(theController.ottieniMeeting()); //aggiorna i dati nella tabella con le modifiche fatte
		
		
		} catch (SQLException e1) {
			
			progettoDiscussoLabel.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		
		
	}

}