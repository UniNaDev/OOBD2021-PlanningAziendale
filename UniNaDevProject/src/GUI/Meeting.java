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
	private JButton modificaButton;
	private JLabel mieiMeetingLabel;

	private JPanel panel;
	private JLabel infoLabel;
	private JList progettiList;
	private JLabel nomeMettingLabel;
	private JLabel inizioLabel;
	private JLabel fineLabel;
	private JLabel valoreInizioLabel;
	private JLabel valoreFineLabel;
	private JSeparator separator;
	private JScrollPane InvitatiScrollPane;
	private JList invitatiList;
	private JLabel InvitatiLabel;
	private JLabel piattaformaLabel;
	private JLabel valorePiattaformaLabel;



	/**
	 * Create the frame.
	 */
	public Meeting(ControllerMeeting theController) {

		setMinimumSize(new Dimension(1300, 770));

		setIconImage(Toolkit.getDefaultToolkit().getImage(Meeting.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Meeting");
		setBounds(650, 150, 1440, 900);

		

		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton nuovoMeetingButton = new JButton("Nuovo Meeting");
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



		
		modificaButton = new JButton("Modifica meeting");
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
		modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createModifyMeetingFrame();
			}
		});


		

		mieiMeetingLabel = new JLabel("I Miei Meeting");
		mieiMeetingLabel.setIcon(new ImageIcon(Meeting.class.getResource("/Icone/meeting_64.png")));
		mieiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JPanel infoProgettoPanel = new JPanel();
		infoProgettoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,new Color(255, 255, 255), new Color(160, 160, 160)), "Info", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		infoProgettoPanel.setBackground(Color.WHITE);
		
		JLabel dataScadenzaLabel = new JLabel("Modalit\u00E0:");
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataScadenzaLabel.setForeground(Color.DARK_GRAY);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel dataTerminazioneLabel = new JLabel("Piattaforma:");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataTerminazioneLabel.setForeground(Color.DARK_GRAY);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel dataCreazioneLabel = new JLabel("Ora fine:");
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataCreazioneLabel.setForeground(Color.DARK_GRAY);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel projectManagerLabel = new JLabel("Ora inizio:");
		projectManagerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		projectManagerLabel.setForeground(Color.DARK_GRAY);
		projectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel ambitiLabel = new JLabel("Data inizio:");
		ambitiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ambitiLabel.setForeground(Color.DARK_GRAY);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreTipologiaLabel = new JLabel("Data fine:");
		valoreTipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		valoreTipologiaLabel.setForeground(Color.DARK_GRAY);
		valoreTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreDataTerminazioneLabel = new JLabel("N/A");
		valoreDataTerminazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel lblRicercaSperimentale = new JLabel("10/10/2020");
		lblRicercaSperimentale.setHorizontalAlignment(SwingConstants.LEFT);
		lblRicercaSperimentale.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreTipologieLabel = new JLabel("10/10/2020");
		valoreTipologieLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreTipologieLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreProjectManagerLabel = new JLabel("10.00");
		valoreProjectManagerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreProjectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreDataCreazioneLabel = new JLabel("10.30");
		valoreDataCreazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreDataScadenzaLabel = new JLabel("Fisico");
		valoreDataScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel nomeProgettoLabel = new JLabel("Meeting");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		GroupLayout gl_infoProgettoPanel = new GroupLayout(infoProgettoPanel);
		gl_infoProgettoPanel.setHorizontalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(dataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(projectManagerLabel, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
						.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
						.addComponent(valoreTipologiaLabel, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_infoProgettoPanel.createSequentialGroup()
								.addComponent(valoreDataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_infoProgettoPanel.createSequentialGroup()
										.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(lblRicercaSperimentale, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGroup(gl_infoProgettoPanel.createSequentialGroup()
												.addComponent(valoreTipologieLabel, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGap(98))
									.addGroup(gl_infoProgettoPanel.createSequentialGroup()
										.addComponent(valoreProjectManagerLabel, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))
								.addGroup(gl_infoProgettoPanel.createSequentialGroup()
									.addComponent(valoreDataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())))
						.addGroup(gl_infoProgettoPanel.createSequentialGroup()
							.addComponent(valoreDataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, gl_infoProgettoPanel.createSequentialGroup()
					.addGap(148)
					.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
					.addGap(135))
		);
		gl_infoProgettoPanel.setVerticalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(55)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreTipologieLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRicercaSperimentale, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(valoreTipologiaLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreProjectManagerLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addComponent(projectManagerLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataCreazioneLabel))
					.addGap(18)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataScadenzaLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
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
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(169)
							.addComponent(infoProgettoPanel, GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
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
							.addGap(277)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
							.addGap(37)))
					.addContainerGap())
		);
		

		JList list = new JList();
		list.setSelectionBackground(Color.LIGHT_GRAY);
		list.setFont(new Font("Consolas", Font.PLAIN, 15));
		list.setFixedCellHeight(40);

		progettiList = new JList();
		progettiList.setSelectionBackground(Color.LIGHT_GRAY);
		progettiList.setModel(new AbstractListModel() {

			String[] values = new String[] {"Meeting 1", "Meeting 2", "Meeting 3", "Discord 3", "Riunione Teams", "Leo Pardo Cisco Webex", "Riunione ", "Prova ", "Prova 27"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}

		});	
		mieiProgettiScrollPane.setViewportView(list);

		
		progettiList.setFixedCellHeight(40);
		progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiProgettiScrollPane.setViewportView(progettiList);
		panel.setLayout(null);
		
		nomeMettingLabel = new JLabel("Meeting 1 [01]");
		nomeMettingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		nomeMettingLabel.setBounds(248, 49, 196, 36);
		panel.add(nomeMettingLabel);
		
		inizioLabel = new JLabel("Inizio:");
		inizioLabel.setForeground(Color.DARK_GRAY);
		inizioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		inizioLabel.setBounds(59, 129, 196, 36);
		panel.add(inizioLabel);
		
		fineLabel = new JLabel("Fine:");
		fineLabel.setForeground(Color.DARK_GRAY);
		fineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		fineLabel.setBounds(425, 129, 196, 36);
		panel.add(fineLabel);
		
		valoreInizioLabel = new JLabel("07-01-2021 14:00");
		valoreInizioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valoreInizioLabel.setBounds(44, 178, 233, 36);
		panel.add(valoreInizioLabel);
		
		valoreFineLabel = new JLabel("07-01-2021 17:00");
		valoreFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valoreFineLabel.setBounds(405, 178, 233, 36);
		panel.add(valoreFineLabel);
		
		InvitatiScrollPane = new JScrollPane();
		InvitatiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		InvitatiScrollPane.setBounds(44, 297, 286, 273);
		panel.add(InvitatiScrollPane);
		
		invitatiList = new JList();
		invitatiList.setSelectionBackground(Color.LIGHT_GRAY);
		invitatiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		invitatiList.setFixedCellHeight(40);
		invitatiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		invitatiList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Mario Rossi", "Luca Bianchi", "Franco Verdi", "Luigi Gialli", "Leo Pardo", "Rino Ceronte", "Marco Antonio"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		InvitatiScrollPane.setViewportView(invitatiList);
		
		InvitatiLabel = new JLabel("Invitati:");
		InvitatiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		InvitatiLabel.setForeground(Color.DARK_GRAY);
		InvitatiLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		InvitatiScrollPane.setColumnHeaderView(InvitatiLabel);
		
		piattaformaLabel = new JLabel("Piattaforma / Sala:");
		piattaformaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		piattaformaLabel.setForeground(Color.DARK_GRAY);
		piattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		piattaformaLabel.setBounds(364, 296, 286, 36);
		panel.add(piattaformaLabel);
		
		valorePiattaformaLabel = new JLabel("Microsoft Teams");
		valorePiattaformaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valorePiattaformaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valorePiattaformaLabel.setBounds(388, 355, 233, 36);
		panel.add(valorePiattaformaLabel);

		contentPane.setLayout(gl_contentPane);



	}
}
