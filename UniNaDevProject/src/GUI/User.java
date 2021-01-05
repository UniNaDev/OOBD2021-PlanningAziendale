package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerGestioneProfilo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Frame;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import java.awt.Dimension;

public class User extends JFrame {

	private JPanel contentPane;
	private JButton mioAccountButton;
	private JButton mieiProgettiButton;
	private JButton mieiMeetingButton;


	/**
	 * Create the frame.
	 */
	public User(ControllerGestioneProfilo theController) {
		setMinimumSize(new Dimension(1000, 700));
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(User.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		mioAccountButton = new JButton("Il Mio Account");
		mioAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mioAccountButton.setBackground(Color.WHITE);
		mioAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		mioAccountButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		mioAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.viewAccount();
			}
		});
		
		mieiProgettiButton = new JButton("I Miei Progetti");
		mieiProgettiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiProgettiButton.setBackground(Color.WHITE);
		mieiProgettiButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		mieiProgettiButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		mieiProgettiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.linkToProjectFrame();
			}
		});
		
		mieiMeetingButton = new JButton("I Miei Meeting");
		mieiMeetingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiMeetingButton.setBackground(Color.WHITE);
		mieiMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		mieiMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		mieiMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.linkToMeetingFrame();
			}
		});
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.WHITE);
		logoutButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		logoutButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.logout();
			}
		});
		
		JScrollPane progettiScrollPanel = new JScrollPane();
		progettiScrollPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		JScrollPane meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.setBorder(new LineBorder(Color.GRAY, 2));
		
		JLabel nomeUtenteLabel = new JLabel("Mario Rossi");
		nomeUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JLabel emailUtenteLabel = new JLabel("m.rossi@gmail.com");
		emailUtenteLabel.setForeground(Color.DARK_GRAY);
		emailUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		//QUESTA PARTE DI CODICE SI OCCUPA DI OTTENERE LA DATA ATTUALE E LA STAMPA COME LABEL
		
		LocalDate dataAttuale = LocalDate.now();
		
		String giornoAttuale = dataAttuale.dayOfWeek().getAsText();
		int giornoAttualeInt = dataAttuale.getDayOfMonth();
		String meseAttuale = dataAttuale.monthOfYear().getAsText();
		int annoAttuale = dataAttuale.getYear();
		
		String dataInStringa = giornoAttuale + " " + String.valueOf(giornoAttualeInt) + " " + meseAttuale + " " + String.valueOf(annoAttuale);
		
		JLabel dataAttualeLabel = new JLabel(String.valueOf(dataInStringa));
		dataAttualeLabel.setForeground(Color.DARK_GRAY);
				
		/////////////////////////////////////////////////////////////////////////////////////
		
		dataAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(mioAccountButton, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(mieiProgettiButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(mieiMeetingButton, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1453, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
					.addGap(240)
					.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(nomeUtenteLabel)
					.addContainerGap(1697, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(emailUtenteLabel)
					.addContainerGap(1697, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(dataAttualeLabel)
					.addContainerGap(1708, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1800, Short.MAX_VALUE)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(mioAccountButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(mieiProgettiButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(mieiMeetingButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(nomeUtenteLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailUtenteLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(dataAttualeLabel)
					.addGap(66)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
						.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel meetingLabel = new JLabel("Meeting Programmati");
		meetingLabel.setForeground(Color.DARK_GRAY);
		meetingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		meetingScrollPanel.setColumnHeaderView(meetingLabel);
		
		JList meetingList = new JList();
		meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingList.setFixedCellHeight(40);
		meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Meeting 1", "Meeting 2", "Meeting 3", "Discord 3", "Riunione Teams", "Leo Pardo Cisco Webex", "Riunione ", "Prova ", "Prova 27"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		meetingScrollPanel.setViewportView(meetingList);
		
		JLabel progettiLabel = new JLabel("Progetti Aziendali");
		progettiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		progettiLabel.setForeground(Color.DARK_GRAY);
		progettiLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		progettiScrollPanel.setColumnHeaderView(progettiLabel);
		
		JList progettiList = new JList();
		progettiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettiList.setFixedCellHeight(40);
		progettiList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Progetto 1", "Progetto 2", "Progetto 3", "Lol", "Ciao", "iPlanner", "Windows 20", "Fortnite", "Rino Ceronte", "Cyberpunk 2077", "Test", "Test1", "Parole a caso per ridimensionare", "bho"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettiScrollPanel.setViewportView(progettiList);
		contentPane.setLayout(gl_contentPane);
	}
}
