package gui.dipendente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.Meeting;
import gui.cellRenderers.MeetingListRenderer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Cursor;
import javax.swing.AbstractListModel;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;

import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerMeeting;

import javax.swing.event.ListSelectionEvent;


public class MieiMeeting extends JFrame {

	//ATTRIBUTI
	//----------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JLabel mieiMeetingLabel;

	private JPanel panel;
	private JList<Meeting> meetingList;
	
	//Creazione frame
	//----------------------------------------------------
	
	public MieiMeeting(ControllerMeeting theController) {
		setMinimumSize(new Dimension(1300, 800));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MieiMeeting.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner -Meeting");
		setBounds(650, 150, 1440, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Button "Inserisci/Modifica Meeting"
		JButton nuovoMeetingButton = new JButton("Inserisci/Modifica Meeting");
		nuovoMeetingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				nuovoMeetingButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				nuovoMeetingButton.setBackground(Color.WHITE);
			}
		});
		nuovoMeetingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovoMeetingButton.setBackground(Color.WHITE);
		nuovoMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		nuovoMeetingButton.addActionListener(new ActionListener() {
			//Click sul pulsante
			public void actionPerformed(ActionEvent e) {
				theController.apriGestioneMeeting();	//apre la finestra per la gestione dei meeting
			}
		});

		//Label "Miei Meeting"
		mieiMeetingLabel = new JLabel("Miei Meeting");
		mieiMeetingLabel.setIcon(new ImageIcon(MieiMeeting.class.getResource("/Icone/meeting_64.png")));
		mieiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//ScrollPane per lista meeting
		JScrollPane mieiMeetingPanel = new JScrollPane();
		mieiMeetingPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		//Panel info del meeting
		JPanel infoMeetingPanel = new JPanel();
		infoMeetingPanel.setFont(new Font("Consolas", Font.PLAIN, 21));
		infoMeetingPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoMeetingPanel.setBackground(Color.WHITE);
		
		//Label "Piattaforma:"/"Sala:"
		JLabel piattaformaSalaLabel = new JLabel("Piattaforma:");
		piattaformaSalaLabel.setIconTextGap(40);
		piattaformaSalaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		piattaformaSalaLabel.setForeground(Color.DARK_GRAY);
		piattaformaSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label della piattaforma/della sala
		JLabel valorePiattaformaSalaLabel = new JLabel("N/A");
		valorePiattaformaSalaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valorePiattaformaSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		//Label "Modalità:"
		JLabel modalitaLabel = new JLabel("Modalità:");
		modalitaLabel.setIconTextGap(40);
		modalitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		modalitaLabel.setForeground(Color.DARK_GRAY);
		modalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per la modalità
		JLabel valoreModalitaLabel = new JLabel("N/A");
		valoreModalitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreModalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		//Label "Orario Inizio:"
		JLabel orarioInizioLabel = new JLabel("Orario Inizio:");
		orarioInizioLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		orarioInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		orarioInizioLabel.setForeground(Color.DARK_GRAY);
		orarioInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label dell'orario di inizio
		JLabel valoreOrarioInizioLabel = new JLabel("N/A");
		valoreOrarioInizioLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		valoreOrarioInizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreOrarioInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		//Label "Orario Fine:"
		JLabel orarioFineLabel = new JLabel("Orario Fine:");
		orarioFineLabel.setIconTextGap(40);
		orarioFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		orarioFineLabel.setForeground(Color.DARK_GRAY);
		orarioFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per orario di fine
		JLabel valoreOrarioFineLabel = new JLabel("N/A");
		valoreOrarioFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreOrarioFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		//Label "Data Inizio:"
		JLabel dataInizioLabel = new JLabel("Data Inizio:");
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataInizioLabel.setForeground(Color.DARK_GRAY);
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per data di inizio
		JLabel valoreDataInzioLabel = new JLabel("N/A");
		valoreDataInzioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataInzioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		//Label "Data Fine:"
		JLabel dataFineLabel = new JLabel("Data Fine:");
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataFineLabel.setForeground(Color.DARK_GRAY);
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per data di fine
		JLabel valoreDataFineLabel = new JLabel("N/A");
		valoreDataFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		//Label del progetto discusso nel meeting
		JLabel progettoDiscussoLabel = new JLabel("N/A");
		progettoDiscussoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		progettoDiscussoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		progettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 27));
		
		JPanel panel_1 = new JPanel();	//panel interno a quello delle info dove appare "Info" label
		
		//Label "Info"
		JLabel infoLabel = new JLabel("Info");
		infoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		panel_1.add(infoLabel);
		
		GroupLayout gl_infoMeetingPanel = new GroupLayout(infoMeetingPanel);
		gl_infoMeetingPanel.setHorizontalGroup(
			gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(551, Short.MAX_VALUE))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGap(92)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(orarioInizioLabel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addComponent(orarioFineLabel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addComponent(modalitaLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addComponent(piattaformaSalaLabel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(valorePiattaformaSalaLabel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(valoreModalitaLabel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(valoreOrarioFineLabel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(valoreOrarioInizioLabel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(valoreDataFineLabel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(valoreDataInzioLabel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
					.addGap(83))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGap(40)
					.addComponent(progettoDiscussoLabel, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
					.addGap(35))
		);
		gl_infoMeetingPanel.setVerticalGroup(
			gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progettoDiscussoLabel, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataInzioLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataFineLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
						.addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addGap(42)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(orarioInizioLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(valoreOrarioInizioLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(orarioFineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(valoreOrarioFineLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(53)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(modalitaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(valoreModalitaLabel, GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valorePiattaformaSalaLabel, GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE)
						.addComponent(piattaformaSalaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(68))
		);
		infoMeetingPanel.setLayout(gl_infoMeetingPanel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(44)
					.addComponent(mieiMeetingPanel, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
					.addGap(116)
					.addComponent(infoMeetingPanel, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
					.addGap(51))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiMeetingLabel, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1083, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1238, Short.MAX_VALUE)
					.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiMeetingLabel)
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mieiMeetingPanel, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
							.addGap(37))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(infoMeetingPanel, GroupLayout.PREFERRED_SIZE, 581, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
		);
		

		//List dei meeting
		try {
			meetingList = new JList(theController.ottieniMeeting().toArray());
			//Selezione di un oggetto nella lista
			meetingList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					Meeting meetingSelezionato = meetingList.getSelectedValue();	//ottiene il meeting selezionato
					
					progettoDiscussoLabel.setText("<html><p style=\"width:400px\">"+meetingSelezionato.getProgettoDiscusso().getNomeProgetto()+"</p></html>");
//					progettoDiscussoLabel.setText("<html>"+ meetingSelezionato.getProgettoDiscusso().getNomeProgetto() +"<br>"); //convertito in html in modo che il label vada a capo nel caso di un nome troppo lungo
					valoreModalitaLabel.setText(meetingSelezionato.getModalita());	//modalità
					
					if (meetingSelezionato.getModalita().equals("Fisico")) {
						piattaformaSalaLabel.setText("Sala: ");
						valorePiattaformaSalaLabel.setText(meetingSelezionato.getSala().getCodSala());	//modalità fisica con sala
					}
					else {
						piattaformaSalaLabel.setText("Piattaforma: ");
						valorePiattaformaSalaLabel.setText(meetingSelezionato.getPiattaforma());	//modalità telematica con piattaforma
					}
					
					DateTimeFormatter formatTime = DateTimeFormat.forPattern("HH:mm");
					valoreOrarioFineLabel.setText(meetingSelezionato.getOraFine().toString(formatTime));	//orario fine
					valoreOrarioInizioLabel.setText(meetingSelezionato.getOraInizio().toString(formatTime));	//orario inizio
					
					DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");	
					valoreDataFineLabel.setText(meetingSelezionato.getDataFine().toString(formatDate));	//data fine
					valoreDataInzioLabel.setText(meetingSelezionato.getDataInizio().toString(formatDate));	//data inizio
				}
			});
			MeetingListRenderer renderer = new MeetingListRenderer();	//applica renderer
			meetingList.setCellRenderer(renderer);
			meetingList.setSelectionBackground(Color.LIGHT_GRAY);
			meetingList.setFixedCellHeight(60);
			meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
			meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			meetingList.setSelectedIndex(0);
			mieiMeetingPanel.setViewportView(meetingList);
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		contentPane.setLayout(gl_contentPane);
	}
}
