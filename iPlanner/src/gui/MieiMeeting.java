package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerMeeting;
import entita.Meeting;

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

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.event.ListSelectionEvent;


public class MieiMeeting extends JFrame {

	private JPanel contentPane;
	private JLabel mieiMeetingLabel;

	private JPanel panel;
	private JList<Meeting> meetingList;




	/**
	 * Create the frame.
	 */
	public MieiMeeting(ControllerMeeting theController) {

		setMinimumSize(new Dimension(1300, 770));

		setIconImage(Toolkit.getDefaultToolkit().getImage(MieiMeeting.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner -Meeting");
		setBounds(650, 150, 1440, 900);

		

		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
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
			public void actionPerformed(ActionEvent e) {
				theController.createInsertMeetingFrame();
			}
		});


		mieiMeetingLabel = new JLabel("I Miei Meeting");
		mieiMeetingLabel.setIcon(new ImageIcon(MieiMeeting.class.getResource("/Icone/meeting_64.png")));
		mieiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JScrollPane mieiMeetingPanel = new JScrollPane();
		mieiMeetingPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JPanel infoMeetingPanel = new JPanel();
		infoMeetingPanel.setFont(new Font("Consolas", Font.PLAIN, 21));
		infoMeetingPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoMeetingPanel.setBackground(Color.WHITE);
		
		JLabel piattaformaLabel = new JLabel("Piattaforma:");
		piattaformaLabel.setIconTextGap(40);
		piattaformaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		piattaformaLabel.setForeground(Color.DARK_GRAY);
		piattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valorePiattaformaLabel = new JLabel("Microsoft Teams");
		valorePiattaformaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valorePiattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel modalitaLabel = new JLabel("Modalità:");
		modalitaLabel.setIconTextGap(40);
		modalitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		modalitaLabel.setForeground(Color.DARK_GRAY);
		modalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreModalitaLabel = new JLabel("Telematico");
		valoreModalitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreModalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel orarioFineLabel = new JLabel("Orario Fine:");
		orarioFineLabel.setIconTextGap(40);
		orarioFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		orarioFineLabel.setForeground(Color.DARK_GRAY);
		orarioFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreOrarioFineLabel = new JLabel("16:30");
		valoreOrarioFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreOrarioFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel orarioInizioLabel = new JLabel("Orario Inizio:");
		orarioInizioLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		orarioInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		orarioInizioLabel.setForeground(Color.DARK_GRAY);
		orarioInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreOrarioInizioLabel = new JLabel("14:30");
		valoreOrarioInizioLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		valoreOrarioInizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreOrarioInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel dataFineLabel = new JLabel("Data Fine:");
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataFineLabel.setForeground(Color.DARK_GRAY);
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel dataInizioLabel = new JLabel("Data Inizio:");
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataInizioLabel.setForeground(Color.DARK_GRAY);
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreDataFineLabel = new JLabel("11-01-2021");
		valoreDataFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreDataInzioLabel = new JLabel("10-01-2021");
		valoreDataInzioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataInzioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel nomeMeetingLabel = new JLabel("Meeting Progetto");
		nomeMeetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 27));
		
		JPanel panel_1 = new JPanel();
		
		JLabel infoLabel = new JLabel("Info");
		infoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		panel_1.add(infoLabel);
		GroupLayout gl_infoMeetingPanel = new GroupLayout(infoMeetingPanel);
		gl_infoMeetingPanel.setHorizontalGroup(
			gl_infoMeetingPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(592, Short.MAX_VALUE))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGap(162)
					.addComponent(piattaformaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(valorePiattaformaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(159))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGap(61)
					.addComponent(modalitaLabel, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(valoreModalitaLabel, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
					.addGap(41))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGap(151)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoMeetingPanel.createSequentialGroup()
							.addComponent(orarioInizioLabel, GroupLayout.PREFERRED_SIZE, 227, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(valoreOrarioInizioLabel, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addGap(101))
						.addGroup(gl_infoMeetingPanel.createSequentialGroup()
							.addComponent(orarioFineLabel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(valoreOrarioFineLabel, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
							.addGap(127))))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGap(180)
					.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 136, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(valoreDataFineLabel, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
					.addGap(169))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_infoMeetingPanel.createSequentialGroup()
							.addGap(173)
							.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(valoreDataInzioLabel, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
							.addGap(9))
						.addGroup(gl_infoMeetingPanel.createSequentialGroup()
							.addGap(131)
							.addComponent(nomeMeetingLabel, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)))
					.addGap(152))
		);
		gl_infoMeetingPanel.setVerticalGroup(
			gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoMeetingPanel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(nomeMeetingLabel)
					.addGap(48)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataInzioLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataFineLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(orarioInizioLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
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
						.addComponent(valorePiattaformaLabel, GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE)
						.addComponent(piattaformaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(38))
		);
		infoMeetingPanel.setLayout(gl_infoMeetingPanel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(44)
					.addComponent(mieiMeetingPanel, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
					.addGap(116)
					.addComponent(infoMeetingPanel, GroupLayout.PREFERRED_SIZE, 648, Short.MAX_VALUE)
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
							.addComponent(infoMeetingPanel, GroupLayout.PREFERRED_SIZE, 541, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
		);
		

		try {
			meetingList = new JList(theController.ottieniMeeting().toArray());
			meetingList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					Meeting meetingSelezionato = meetingList.getSelectedValue();
					//aggiorna info del meeting
					nomeMeetingLabel.setText(meetingSelezionato.getProgettoDiscusso().toString());
					
					
					valoreModalitaLabel.setText(meetingSelezionato.getModalita());
					
					if (meetingSelezionato.getModalita().equals("Fisico")) {
						piattaformaLabel.setText("Sala: ");
						valorePiattaformaLabel.setText(meetingSelezionato.getSala().getCodSala());
					}
					else {
						piattaformaLabel.setText("Piattaforma: ");
						valorePiattaformaLabel.setText(meetingSelezionato.getPiattaforma());
					}
					
					DateTimeFormatter formatDateTime = DateTimeFormat.forPattern("hh:mm");
					valoreOrarioFineLabel.setText(meetingSelezionato.getOraFine().toString(formatDateTime));
					valoreOrarioInizioLabel.setText(meetingSelezionato.getOraInizio().toString(formatDateTime));
					
					formatDateTime = DateTimeFormat.forPattern("dd/MM/yyyy");
					valoreDataFineLabel.setText(meetingSelezionato.getDataFine().toString(formatDateTime));
					valoreDataInzioLabel.setText(meetingSelezionato.getDataInizio().toString(formatDateTime));
				}
			});
			MeetingListRenderer renderer = new MeetingListRenderer();
			meetingList.setCellRenderer(renderer);
			
			meetingList.setSelectionBackground(Color.LIGHT_GRAY);
			meetingList.setFixedCellHeight(60);
			meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
			meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			meetingList.setSelectedIndex(0);
			mieiMeetingPanel.setViewportView(meetingList);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		



		contentPane.setLayout(gl_contentPane);



	}
}
