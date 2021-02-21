/*Finestra home del dipendente.
 *Qui può visualizzare rapidamente progetti e meeting che gli riguardano.
 *Può fare logout oppure può gestire le informazioni del suo account in Mio Account,
 *i suoi progetti in Miei Progetti e i suoi meeting in Miei Meeting.
 **********************************************************************************/

package gui.dipendente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.Progetto;
import gui.ErroreDialog;
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

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import controller.dipendente.ControllerGestioneProfilo;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.DefaultListModel;

import java.awt.Cursor;
import javax.swing.border.MatteBorder;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Home extends JFrame {
	private JPanel contentPane;
	private JButton mioAccountButton;
	private JButton mieiProgettiButton;
	private JButton mieiMeetingButton;
	private JButton logoutButton;
	private JLabel nomeUtenteLabel;
	private JLabel emailUtenteLabel;
	private JLabel iconaEmailLabel;
	private JLabel dataAttualeLabel;
	private JLabel oraAttualeLabel;
	private JLabel meetingLabel;
	private JLabel progettiLabel;
	private JScrollPane progettiScrollPanel;
	private JScrollPane meetingScrollPanel;
	private JList<Meeting> meetingList;
	private MeetingListRenderer meetingCellRenderer;
	private ProgettoListRenderer progettoCellRenderer;
	private DefaultListModel modelloListaMeeting;
	private JList<Progetto> progettiList;
	private DefaultListModel modelloListaProgetti;

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

		mioAccountButton = new JButton("Mio Account");
		mioAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mioAccountButton.setBackground(Color.WHITE);
		mioAccountButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		mioAccountButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		mioAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGUIMioAccount();
			}
		});
		mioAccountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mioAccountButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mioAccountButton.setBackground(Color.WHITE);
			}
		});

		mieiProgettiButton = new JButton("Miei Progetti");
		mieiProgettiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiProgettiButton.setBackground(Color.WHITE);
		mieiProgettiButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		mieiProgettiButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		mieiProgettiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGUIMieiProgetti();
			}
		});
		mieiProgettiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mieiProgettiButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mieiProgettiButton.setBackground(Color.WHITE);
			}
		});

		mieiMeetingButton = new JButton("Miei Meeting");
		mieiMeetingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiMeetingButton.setBackground(Color.WHITE);
		mieiMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		mieiMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		mieiMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGUIMieiMeeting();
			}
		});
		mieiMeetingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mieiMeetingButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mieiMeetingButton.setBackground(Color.WHITE);
			}

		});

		logoutButton = new JButton("Esci");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.logout();
			}
		});
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logoutButton.setBackground(Color.WHITE);
			}

		});
		logoutButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		logoutButton.setBackground(Color.WHITE);
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		progettiScrollPanel = new JScrollPane();
		progettiScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		progettiScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		progettiScrollPanel.setBorder(new LineBorder(Color.GRAY, 2, true));

		meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		meetingScrollPanel.setBorder(new LineBorder(Color.GRAY, 2));

		nomeUtenteLabel = new JLabel(dipendente.getNome() + " " + dipendente.getCognome());
		nomeUtenteLabel.setIcon(new ImageIcon(Home.class.getResource("/Icone/employee_64.png")));
		nomeUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 30));

		emailUtenteLabel = new JLabel(dipendente.getEmail());
		emailUtenteLabel.setIcon(null);
		emailUtenteLabel.setForeground(Color.DARK_GRAY);
		emailUtenteLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

		iconaEmailLabel = new JLabel("");
		iconaEmailLabel.setIcon(new ImageIcon(Home.class.getResource("/Icone/email_32.png")));

		LocalDate dataAttuale = LocalDate.now();
		String giornoAttuale = dataAttuale.dayOfWeek().getAsText();
		int giornoAttualeInt = dataAttuale.getDayOfMonth();
		String meseAttuale = dataAttuale.monthOfYear().getAsText();
		int annoAttuale = dataAttuale.getYear();
		String dataInStringa = giornoAttuale + " " + String.valueOf(giornoAttualeInt) + " " + meseAttuale + " " + String.valueOf(annoAttuale);

		dataAttualeLabel = new JLabel(String.valueOf(dataInStringa));
		dataAttualeLabel.setForeground(Color.BLACK);
		dataAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 25));

		oraAttualeLabel = new JLabel("Updating...");
		oraAttualeLabel.setForeground(Color.BLACK);
		oraAttualeLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalTime oraAttuale = LocalTime.now();
				String oraAttualeStringa = oraAttuale.toString().substring(0, 8);
				oraAttualeLabel.setText(oraAttualeStringa);
			}
		});
		timer.start();

		meetingLabel = new JLabel("Meeting");
		meetingLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inizializzaListaMeeting(controller);
			}
		});
		meetingLabel.setToolTipText("Aggiorna lista");
		meetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		meetingLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingLabel.setIcon(new ImageIcon(Home.class.getResource("/icone/refresh.png")));
		meetingLabel.setForeground(Color.DARK_GRAY);
		meetingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		meetingScrollPanel.setColumnHeaderView(meetingLabel);

		meetingList = new JList();
		modelloListaMeeting = new DefaultListModel();
		meetingCellRenderer = new MeetingListRenderer();
		meetingList.setCellRenderer(meetingCellRenderer);
		meetingList.setSelectionBackground(Color.WHITE);
		meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		meetingList.setFixedCellHeight(80);
		meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
		inizializzaListaMeeting(controller);
		meetingScrollPanel.setViewportView(meetingList);

		progettiLabel = new JLabel("Progetti");
		progettiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inizializzaListaProgetti(controller);
			}
		});
		progettiLabel.setToolTipText("Aggiorna lista");
		progettiLabel.setIcon(new ImageIcon(Home.class.getResource("/icone/refresh.png")));
		progettiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		progettiLabel.setForeground(Color.DARK_GRAY);
		progettiLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		progettiScrollPanel.setColumnHeaderView(progettiLabel);

		progettiList = new JList();
		modelloListaProgetti = new DefaultListModel();
		progettoCellRenderer = new ProgettoListRenderer();
		progettiList.setCellRenderer(progettoCellRenderer);
		progettiList.setSelectionBackground(Color.WHITE);
		progettiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		progettiList.setFixedCellHeight(80);
		progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		inizializzaListaProgetti(controller);
		progettiScrollPanel.setViewportView(progettiList);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
							.addGap(210)
							.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
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
									.addComponent(mieiMeetingButton, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
								.addComponent(oraAttualeLabel)))
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
						.addComponent(progettiScrollPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
						.addComponent(meetingScrollPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
					.addGap(32)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		contentPane.setLayout(gl_contentPane);
	}

	private void inizializzaListaMeeting(ControllerGestioneProfilo controller) {
		modelloListaMeeting.clear();
		try {
			modelloListaMeeting.addAll(controller.ottieniMeetingDipendente());
			meetingList.setModel(modelloListaMeeting);
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}

	private void inizializzaListaProgetti(ControllerGestioneProfilo controller) {
		modelloListaProgetti.clear();
		try {
			ArrayList<Progetto> progetti = new ArrayList<Progetto>();
			for (CollaborazioneProgetto collaborazione : controller.ottieniProgettiDipendente())
				progetti.add(collaborazione.getProgetto());
			modelloListaProgetti.addAll(progetti);
			progettiList.setModel(modelloListaProgetti);
		} catch (SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}

	private void impostaProgettoCellRenderer() {
		progettoCellRenderer = new ProgettoListRenderer();
		progettiList.setCellRenderer(progettoCellRenderer);
	}

	private void impostaMeetingCellRenderer() {
		meetingCellRenderer = new MeetingListRenderer();
		meetingList.setCellRenderer(meetingCellRenderer);
	}
}
