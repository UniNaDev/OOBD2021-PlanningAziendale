package gui.dipendente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import entita.Progetto;
import gui.cellRenderers.MeetingListRenderer;
import gui.cellRenderers.ProgettoListRenderer;
import gui.customUI.CustomScrollBarUI;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import controller.dipendente.ControllerGestioneProfilo;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Home extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JButton mioAccountButton;
	private JButton mieiProgettiButton;
	private JButton mieiMeetingButton;
	

	//Crezione frame
	//-----------------------------------------------------------------
	
	public Home(ControllerGestioneProfilo controller, Dipendente dipendente) {
		setMinimumSize(new Dimension(1000, 700));
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Button "Mio Account"
		mioAccountButton = new JButton("Mio Account");
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
		//Click sul pulsante
		mioAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriMioAccount();	//apre la finestra dei dettagli dell'account
			}
		});
		
		//Button "Miei Progetti"
		mieiProgettiButton = new JButton("Miei Progetti");
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
		//Click sul pulsante
		mieiProgettiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriMieiProgetti();	//apre la finestra dei progetti del dipendente
			}
		});
		
		//Button "Miei Meeting"
		mieiMeetingButton = new JButton("Miei Meeting");
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
		//Click del pulsante
		mieiMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriMieiMeeting();	//apre la finestra dei meeting del dipendente
			}
		});
		
		//Button "Esci"
		JButton logoutButton = new JButton("Esci");
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
		//Click pulsante
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.logout();	//esegue il logout
			}
		});
		
		JScrollPane progettiScrollPanel = new JScrollPane();
		progettiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		progettiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		progettiScrollPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		JScrollPane meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.setBorder(new LineBorder(Color.GRAY, 2));
		
		//Label nome dell'utente
		JLabel nomeUtenteLabel = new JLabel(dipendente.getNome() + " " + dipendente.getCognome());
		nomeUtenteLabel.setIcon(new ImageIcon(Home.class.getResource("/Icone/employee_64.png")));
		nomeUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//Label email del dipendente
		JLabel emailUtenteLabel = new JLabel(dipendente.getEmail());
		emailUtenteLabel.setIcon(null);
		emailUtenteLabel.setForeground(Color.DARK_GRAY);
		emailUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		//Icona email
		JLabel iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setIcon(new ImageIcon(Home.class.getResource("/Icone/email_32.png")));
		
		//Calcolo della data
		LocalDate dataAttuale = LocalDate.now();	//prende la data attuale
		
		String giornoAttuale = dataAttuale.dayOfWeek().getAsText();	//seleziona il giorno della settimana
		int giornoAttualeInt = dataAttuale.getDayOfMonth();	//seleziona il giorno del mese
		String meseAttuale = dataAttuale.monthOfYear().getAsText();	//seleziona il mese
		int annoAttuale = dataAttuale.getYear();	//seleziona l'anno
		
		//Crea la stringa della data completa
		String dataInStringa = giornoAttuale + " " + String.valueOf(giornoAttualeInt) + " " + meseAttuale + " " + String.valueOf(annoAttuale);
		
		//Label data attuale
		JLabel dataAttualeLabel = new JLabel(String.valueOf(dataInStringa));
		dataAttualeLabel.setForeground(Color.BLACK);
		dataAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
				
		//Calcolo dell'orario attuale
		JLabel oraAttualeLabel = new JLabel("Updating...");
		oraAttualeLabel.setForeground(Color.BLACK);
		oraAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
						
		//Timer per l'aggiornamento dell'orario in tempo reale
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
		
		//Label "Meeting Programmati"
		JLabel meetingLabel = new JLabel("Meeting");
		meetingLabel.setForeground(Color.DARK_GRAY);
		meetingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		meetingScrollPanel.setColumnHeaderView(meetingLabel);
		
		//List dei meeting del dipendente
		JList<Meeting> meetingList;
		try {
			MeetingListRenderer renderer = new MeetingListRenderer();
			meetingList = new JList();
			DefaultListModel listmodel=new DefaultListModel();
			meetingList.setModel(listmodel);
			listmodel.addAll(controller.ottieniMeeting());
			meetingList.setSelectionBackground(Color.WHITE);
			meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			meetingList.setFixedCellHeight(60);
			meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
			meetingScrollPanel.setViewportView(meetingList);
			meetingList.setCellRenderer(renderer);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//Progetti Label
		JLabel progettiLabel = new JLabel("Progetti");
		progettiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		progettiLabel.setForeground(Color.DARK_GRAY);
		progettiLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		progettiScrollPanel.setColumnHeaderView(progettiLabel);
		
		//List progetti
		JList<Progetto> progettiList;
		try {
			ProgettoListRenderer progettoCellRenderer = new ProgettoListRenderer();
			ArrayList<Progetto> progetti = new ArrayList<Progetto>();
			for (CollaborazioneProgetto collaborazione: controller.ottieniProgetti())
				progetti.add(collaborazione.getProgetto());
			progettiList = new JList(progetti.toArray());
			progettiList.setSelectionBackground(Color.WHITE);
			progettiList.setCellRenderer(progettoCellRenderer);
			progettiList.setFixedCellHeight(60);
			progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
			
			progettiScrollPanel.setViewportView(progettiList);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
							.addGap(210)
							.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
						.addComponent(oraAttualeLabel, Alignment.TRAILING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(iconaEmailLabel)
									.addGap(18)
									.addComponent(emailUtenteLabel))
								.addComponent(nomeUtenteLabel))
							.addPreferredGap(ComponentPlacement.RELATED, 1042, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(dataAttualeLabel)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(mioAccountButton, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(mieiProgettiButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(mieiMeetingButton, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))))
						.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(progettiScrollPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
						.addComponent(meetingScrollPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE))
					.addGap(32)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		contentPane.setLayout(gl_contentPane);
	}


}
