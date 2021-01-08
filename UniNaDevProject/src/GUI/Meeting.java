package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerMeeting;
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
import javax.swing.ImageIcon;


public class Meeting extends JFrame {

	private JPanel contentPane;
	private JLabel mieiMeetingLabel;

	private JPanel panel;
	private JList meetingList;




	/**
	 * Create the frame.
	 */
	public Meeting(ControllerMeeting theController) {

		setMinimumSize(new Dimension(1300, 770));

		setIconImage(Toolkit.getDefaultToolkit().getImage(Meeting.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner -Meeting");
		setBounds(650, 150, 1440, 900);

		

		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
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
		mieiMeetingLabel.setIcon(new ImageIcon(Meeting.class.getResource("/Icone/meeting_64.png")));
		mieiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JPanel infoProgettoPanel = new JPanel();
		infoProgettoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,new Color(255, 255, 255), new Color(160, 160, 160)), "Info", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		infoProgettoPanel.setBackground(Color.WHITE);
		
		JLabel modalitaLabel = new JLabel("Modalit\u00E0:");
		modalitaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		modalitaLabel.setForeground(Color.DARK_GRAY);
		modalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel piattaformaaLabel = new JLabel("Piattaforma:");
		piattaformaaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		piattaformaaLabel.setForeground(Color.DARK_GRAY);
		piattaformaaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel oraFineLabel = new JLabel("Ora fine:");
		oraFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oraFineLabel.setForeground(Color.DARK_GRAY);
		oraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel oraInizioLabel = new JLabel("Ora inizio:");
		oraInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oraInizioLabel.setForeground(Color.DARK_GRAY);
		oraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel dataInizioLabel = new JLabel("Data inizio:");
		dataInizioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataInizioLabel.setForeground(Color.DARK_GRAY);
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel dataFineLabel = new JLabel("Data fine:");
		dataFineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataFineLabel.setForeground(Color.DARK_GRAY);
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valorePiattaformaLabel = new JLabel("N/A");
		valorePiattaformaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valorePiattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreDataFineLabel = new JLabel("10/10/2020");
		valoreDataFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreDataInizioLabel = new JLabel("10/10/2020");
		valoreDataInizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreOraInizioLabel = new JLabel("10.00");
		valoreOraInizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreOraInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreOraFineLabel = new JLabel("10.30");
		valoreOraFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreOraFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreModalitaLabel = new JLabel("Fisico");
		valoreModalitaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreModalitaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel nomeMeetingLabel = new JLabel("Meeting 1");
		nomeMeetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		GroupLayout gl_infoProgettoPanel = new GroupLayout(infoProgettoPanel);
		gl_infoProgettoPanel.setHorizontalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(modalitaLabel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addComponent(piattaformaaLabel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addComponent(oraFineLabel, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(oraInizioLabel, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
						.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_infoProgettoPanel.createSequentialGroup()
								.addComponent(valorePiattaformaLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_infoProgettoPanel.createSequentialGroup()
										.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(valoreDataFineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGroup(gl_infoProgettoPanel.createSequentialGroup()
												.addComponent(valoreDataInizioLabel, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGap(98))
									.addGroup(gl_infoProgettoPanel.createSequentialGroup()
										.addComponent(valoreOraInizioLabel, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))
								.addGroup(gl_infoProgettoPanel.createSequentialGroup()
									.addComponent(valoreOraFineLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())))
						.addGroup(gl_infoProgettoPanel.createSequentialGroup()
							.addComponent(valoreModalitaLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, gl_infoProgettoPanel.createSequentialGroup()
					.addGap(148)
					.addComponent(nomeMeetingLabel, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
					.addGap(135))
		);
		gl_infoProgettoPanel.setVerticalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(nomeMeetingLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(55)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataInizioLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataFineLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataFineLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreOraInizioLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreOraFineLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(oraFineLabel))
					.addGap(18)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreModalitaLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(modalitaLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valorePiattaformaLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(piattaformaaLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(169))
		);
		infoProgettoPanel.setLayout(gl_infoProgettoPanel);
		
		JScrollPane mieiProgettiScrollPane = new JScrollPane();
		mieiProgettiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(44)
					.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(179)
							.addComponent(infoProgettoPanel, GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)))
					.addGap(52))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiMeetingLabel, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1083, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiMeetingLabel)
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 425, Short.MAX_VALUE)
							.addGap(265)
							.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
							.addGap(37)))
					.addContainerGap())
		);
		

		meetingList = new JList();
		meetingList.setSelectionBackground(Color.LIGHT_GRAY);
		meetingList.setModel(new AbstractListModel() {

			String[] values = new String[] {"Meeting 1", "Meeting 2", "Meeting 3", "Discord 3", "Riunione Teams", "Leo Pardo Cisco Webex", "Riunione ", "Prova ", "Prova 27"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}

		});	
		meetingList.setFixedCellHeight(40);
		meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiProgettiScrollPane.setViewportView(meetingList);
		



		contentPane.setLayout(gl_contentPane);



	}
}
