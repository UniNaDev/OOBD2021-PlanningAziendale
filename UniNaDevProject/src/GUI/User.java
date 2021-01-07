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
import javax.swing.Timer;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Frame;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

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
		mioAccountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				mioAccountButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				mioAccountButton.setBackground(Color.WHITE);
			}
		});
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
		mieiProgettiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				mieiProgettiButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				mieiProgettiButton.setBackground(Color.WHITE);
			}
		});
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
		mieiMeetingButton.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				mieiMeetingButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				mieiMeetingButton.setBackground(Color.WHITE);
			}
			
		});
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
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				logoutButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				logoutButton.setBackground(Color.WHITE);
			}
		});
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
		nomeUtenteLabel.setIcon(new ImageIcon(User.class.getResource("/Icone/employee_64.png")));
		nomeUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JLabel emailUtenteLabel = new JLabel("m.rossi@gmail.com");
		emailUtenteLabel.setIcon(null);
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
		dataAttualeLabel.setForeground(Color.BLACK);
				
		/////////////////////////////////////////////////////////////////////////////////////
		
		dataAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setIcon(new ImageIcon(User.class.getResource("/Icone/email_32.png")));
				
		JLabel oraAttualeLabel = new JLabel("Updating...");
		oraAttualeLabel.setForeground(Color.BLACK);

		
		oraAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
							.addGap(210)
							.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
						.addComponent(oraAttualeLabel)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
									.addComponent(iconaEmailLabel)
									.addGap(18)
									.addComponent(emailUtenteLabel))
								.addComponent(nomeUtenteLabel))
							.addPreferredGap(ComponentPlacement.RELATED, 1171, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(dataAttualeLabel)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(mioAccountButton, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(mieiProgettiButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(mieiMeetingButton, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(mioAccountButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(mieiProgettiButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(mieiMeetingButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(dataAttualeLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(nomeUtenteLabel)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(iconaEmailLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(emailUtenteLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(oraAttualeLabel)))
					.addGap(55)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
						.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE))
					.addGap(32)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		//PARTE DI CODICE CHE SI OCCUPA DI AGGIORNARE L'ORA IN TEMPO REALE
						
		Timer t = new Timer(1000, new ActionListener(){ //un timer ripete l'azione ogni tot di tempo, in questo caso ogni 1000ms
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	LocalTime oraAttuale = LocalTime.now();		//prende l'ora attuale
		    	String oraAttualeStringa = oraAttuale.toString().substring(0, 8); //taglia la parte dei millisecondi che non ci serve
		    	
		    	oraAttualeLabel.setText(oraAttualeStringa);
		    }
		});
		t.start(); // fa partire il timer
				
		/////////////////////////////////////////////////////////////////
		
		JLabel meetingLabel = new JLabel("Meeting Programmati");
		meetingLabel.setForeground(Color.DARK_GRAY);
		meetingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		meetingScrollPanel.setColumnHeaderView(meetingLabel);
		
		JList meetingList = new JList();
		meetingList.setSelectionBackground(Color.LIGHT_GRAY);
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
		progettiList.setSelectionBackground(Color.LIGHT_GRAY);
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
