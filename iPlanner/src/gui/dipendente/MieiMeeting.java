/*Finestra dove il dipendente può visualizzare i dettagli dei meeting a cui ha partecipato
 * o deve partecipare.
 * Da qui può andare poi nella finestra di Gestione Meeting dove può lavorare con essi.
 ****************************************************************************************/

package gui.dipendente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.Meeting;
import gui.cellRenderers.MeetingListRenderer;
import gui.customUI.CustomScrollBarUI;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerMeeting;

import javax.swing.event.ListSelectionEvent;

public class MieiMeeting extends JFrame {
	private JPanel contentPane;
	private JLabel mieiMeetingLabel;
	private JLabel dataInizioLabel;
	private JLabel dataFineLabel;
	private JLabel orarioInizioLabel;
	private JLabel orarioFineLabel;
	private JLabel piattaformaSalaLabel;
	private JLabel progettoDiscussoLabel;
	private JLabel infoLabel;
	private JLabel modalitàLabel;
	private JButton nuovoMeetingButton;
	private JPanel panel;
	private JPanel infoMeetingPanel;
	private JScrollPane mieiMeetingScrollPane;
	private JList<Meeting> meetingList = new JList();
	private MeetingListRenderer meetingCellRenderer;
	private DefaultListModel modelloListaMeeting = new DefaultListModel<Meeting>();
	private Meeting meetingSelezionato;

	public MieiMeeting(ControllerMeeting controller) {
		setMinimumSize(new Dimension(1440, 900));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MieiMeeting.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - I Miei Meeting");
		setBounds(650, 150, 1353, 708);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		mieiMeetingLabel = new JLabel("Miei Meeting");
		mieiMeetingLabel.setIcon(new ImageIcon(MieiMeeting.class.getResource("/Icone/meeting_64.png")));
		mieiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));

		mieiMeetingScrollPane = new JScrollPane();
		mieiMeetingScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		mieiMeetingScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		mieiMeetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		meetingList.setSelectionBackground(Color.LIGHT_GRAY);
		meetingList.setFixedCellHeight(70);
		meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingList.setSelectedIndex(0);
		mieiMeetingScrollPane.setViewportView(meetingList);
		impostaMeetingCellRenderer();
		inizializzaListaMeeting(controller);
		meetingList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				impostaInfoMeetingSelezionato();
			}
		});

		infoMeetingPanel = new JPanel();
		infoMeetingPanel.setFont(new Font("Consolas", Font.PLAIN, 21));
		infoMeetingPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		infoMeetingPanel.setBackground(Color.WHITE);
		
		piattaformaSalaLabel = new JLabel("Piattaforma/Sala: N/A");
		piattaformaSalaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		piattaformaSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		modalitàLabel = new JLabel("Modalità: N/A");
		modalitàLabel.setHorizontalAlignment(SwingConstants.CENTER);
		modalitàLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		orarioInizioLabel = new JLabel("Orario Inizio: N/A");
		orarioInizioLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		orarioInizioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orarioInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		orarioFineLabel = new JLabel("Orario Fine: N/A");
		orarioFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orarioFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		dataInizioLabel = new JLabel("Data Inizio: N/A");
		dataInizioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		dataFineLabel = new JLabel("Data Fine: N/A");
		dataFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataFineLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		progettoDiscussoLabel = new JLabel("N/A");
		progettoDiscussoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		progettoDiscussoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		progettoDiscussoLabel.setFont(new Font("Consolas", Font.PLAIN, 27));

		JPanel panel_1 = new JPanel();

		infoLabel = new JLabel("Info");
		infoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		panel_1.add(infoLabel);
		nuovoMeetingButton = new JButton("Inserisci/Modifica Meeting");
		nuovoMeetingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovoMeetingButton.setBackground(Color.WHITE);
		nuovoMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		nuovoMeetingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nuovoMeetingButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nuovoMeetingButton.setBackground(Color.WHITE);
			}
		});
		nuovoMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGUIGestioneMeeting();
			}
		});

		GroupLayout gl_infoMeetingPanel = new GroupLayout(infoMeetingPanel);
		gl_infoMeetingPanel.setHorizontalGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoMeetingPanel.createSequentialGroup().addGap(28)
						.addGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(modalitàLabel, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
								.addComponent(orarioFineLabel, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
								.addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
								.addComponent(orarioInizioLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 552,
										Short.MAX_VALUE)
								.addComponent(dataInizioLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 552,
										Short.MAX_VALUE)
								.addComponent(progettoDiscussoLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 552,
										Short.MAX_VALUE)
								.addComponent(piattaformaSalaLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 561,
										Short.MAX_VALUE))
						.addGap(28))
				.addGroup(gl_infoMeetingPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(514, Short.MAX_VALUE)));
		gl_infoMeetingPanel.setVerticalGroup(gl_infoMeetingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoMeetingPanel.createSequentialGroup().addGap(7)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(progettoDiscussoLabel).addGap(91)
						.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(orarioInizioLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGap(33).addComponent(dataFineLabel, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(orarioFineLabel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(modalitàLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addGap(18).addComponent(piattaformaSalaLabel, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
						.addGap(13)));
		infoMeetingPanel.setLayout(gl_infoMeetingPanel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(nuovoMeetingButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 176,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(mieiMeetingScrollPane, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
								.addGap(154)
								.addComponent(infoMeetingPanel, GroupLayout.PREFERRED_SIZE, 621, Short.MAX_VALUE))
						.addComponent(mieiMeetingLabel, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(mieiMeetingLabel)
						.addGap(31)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(infoMeetingPanel, GroupLayout.PREFERRED_SIZE, 688, Short.MAX_VALUE)
								.addComponent(mieiMeetingScrollPane, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)));
		
		contentPane.setLayout(gl_contentPane);
	}

	private void inizializzaListaMeeting(ControllerMeeting controller) {
		try {
			ArrayList<Meeting> meetings = controller.ottieniMeetingDipendente();
			modelloListaMeeting.addAll(meetings);
			meetingList.setModel(modelloListaMeeting);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage()
							+ "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
					"Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void impostaMeetingCellRenderer() {
		meetingCellRenderer = new MeetingListRenderer();
		meetingList.setCellRenderer(meetingCellRenderer);
	}
	
	private void impostaInfoMeetingSelezionato() {
		meetingSelezionato = meetingList.getSelectedValue();

		progettoDiscussoLabel.setText("<html><center>"
				+ meetingSelezionato.getProgettoDiscusso().getNomeProgetto() + "</html></center>");

		modalitàLabel.setText("Modalità: " + meetingSelezionato.getModalita());

		if (meetingSelezionato.getModalita().equals("Fisico"))
			piattaformaSalaLabel.setText("Sala: " + meetingSelezionato.getSala().getCodiceSala());
		else
			piattaformaSalaLabel.setText("Piattaforma: " + meetingSelezionato.getPiattaforma());

		DateTimeFormatter formatTime = DateTimeFormat.forPattern("HH:mm");
		orarioFineLabel.setText("Orario Fine: " + meetingSelezionato.getOraFine().toString(formatTime));
		orarioInizioLabel
				.setText("Orario Inizio: " + meetingSelezionato.getOraInizio().toString(formatTime));

		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		dataFineLabel.setText("Data Fine: " + meetingSelezionato.getDataFine().toString(formatDate));
		dataInizioLabel.setText("Data Inizio: " + meetingSelezionato.getDataInizio().toString(formatDate));
	}
}
