package GUI;

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
import javax.swing.table.DefaultTableModel;
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


	/**
	 * Create the frame.
	 */
	public GestioneMeeting() {
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
		
		JButton pulisciButton = new JButton("Pulisci");
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
			public void actionPerformed(ActionEvent e) {
			}
		});
		pulisciButton.setPreferredSize(new Dimension(90, 30));
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
		
		JButton modificaButton = new JButton("Modifica");
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
		modificaButton.setPreferredSize(new Dimension(90, 30));
		modificaButton.setMaximumSize(new Dimension(150, 150));
		modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setMargin(new Insets(2, 20, 2, 20));
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		modificaButton.setAlignmentX(0.5f);
		
		JButton salvaButton = new JButton("Salva");
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
		salvaButton.setPreferredSize(new Dimension(90, 30));
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
		
		JLabel idProgettoLabel = new JLabel("MeetingID");
		idProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		idProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel nomeProgettoLabel = new JLabel("Data inizio");
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		idMeetingTextField = new JTextField();
		idMeetingTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		idMeetingTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JLabel ambitoProgettoLabel = new JLabel("Data fine");
		ambitoProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		ambitoProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel tipologiaProgettoLabel = new JLabel("Ora inizio");
		tipologiaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		tipologiaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel scadenzaProgettoLabel = new JLabel("Ora fine");
		scadenzaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		scadenzaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel descrizioneProgettoLabel = new JLabel("Modalit\u00E0");
		descrizioneProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		descrizioneProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel creatoreProgettoLabel = new JLabel("Piattaforma");
		creatoreProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		creatoreProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		
		
		JRadioButton onlineRadioButton = new JRadioButton("Online");
		buttonGroup.add(onlineRadioButton);
		
		JRadioButton fisicoRadioButton = new JRadioButton("Fisico");
		buttonGroup.add(fisicoRadioButton);
		
		JComboBox dataInizioMeseComboBox = new JComboBox();
		dataInizioMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataInizioMeseComboBox.setSelectedIndex(0);
		dataInizioMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioMeseComboBox.setBackground(Color.WHITE);
		
		JComboBox dataInizioGiornoComboBox = new JComboBox();
		dataInizioGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dataInizioGiornoComboBox.setSelectedIndex(0);
		dataInizioGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataInizioGiornoComboBox.setBackground(Color.WHITE);
		
		JComboBox dataInizioAnnoComboBox = new JComboBox();
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
		
		JComboBox dataFineGiornoComboBox = new JComboBox();
		dataFineGiornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dataFineGiornoComboBox.setSelectedIndex(0);
		dataFineGiornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineGiornoComboBox.setBackground(Color.WHITE);
		
		JComboBox dataFineMeseComboBox = new JComboBox();
		dataFineMeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		dataFineMeseComboBox.setSelectedIndex(0);
		dataFineMeseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		dataFineMeseComboBox.setBackground(Color.WHITE);
		
		JComboBox dataFineAnnoComboBox = new JComboBox();
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
		
		JComboBox piattaformaComboBox = new JComboBox();
		piattaformaComboBox.setModel(new DefaultComboBoxModel(new String[] {"","Microsoft Teams","Discord","Skype"}));
		
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(descrizioneProgettoLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(tipologiaProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(idProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(ambitoProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(creatoreProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(idMeetingTextField, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING, gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(piattaformaComboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
										.addComponent(onlineRadioButton)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(fisicoRadioButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))))
							.addGap(275))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
					.addGap(59))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(idProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(idMeetingTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(dataInizioAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataInizioMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataInizioGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(ambitoProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(dataFineMeseComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataFineGiornoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataFineAnnoComboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(onlineRadioButton)
								.addComponent(fisicoRadioButton))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(creatoreProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(piattaformaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
							.addGap(23))))
		);
		
		JList list = new JList();
		list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane.setViewportView(list);
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
