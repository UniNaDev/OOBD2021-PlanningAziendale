/*Finestra Miei Progetti dove il dipendente può visualizzare i dettagli di ogni
 * progetto a cui partecipa.
 * Da qui può anche spostarsi nella finestra Gestione Progetti dove può modificare
 * o creare altri progetti.
 **********************************************************************************/

package gui.dipendente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entita.AmbitoProgetto;
import entita.Dipendente;
import entita.Progetto;
import gui.cellRenderers.ProgettoListRenderer;
import gui.customUI.CustomScrollBarUI;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.dipendente.ControllerProgetto;

import javax.swing.event.ListSelectionEvent;

public class MieiProgetti extends JFrame {

	private JPanel contentPane;
	private JButton nuovoProgettoBotton;
	private JList<Progetto> progettiList;
	private DefaultListModel modelloListaProgetti;
	private JLabel mieiProgettiLabel;
	private JLabel nomeProgettoLabel;
	private JLabel tipologiaLabel;
	private JLabel dataCreazioneLabel;
	private JLabel dataScadenzaLabel;
	private JLabel dataTerminazioneLabel;
	private JLabel ambitiLabel;
	private JLabel descrizioneLabel;
	private JLabel infoLabel;
	private JTextArea descrizioneProgettoTextArea;
	private JScrollPane descrizioneProgettoScrollPane;
	private JScrollPane progettiScrollPane;
	private JPanel infoProgettoPanel;
	private ProgettoListRenderer progettoCellRenderer;

	public MieiProgetti(ControllerProgetto controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MieiProgetti.class.getResource("/Icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1440, 900));
		setTitle("iPlanner - I Miei Progetti");
		setBounds(100, 100, 1440, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		progettiScrollPane = new JScrollPane();
		progettiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		progettiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		progettiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		mieiProgettiLabel = new JLabel("Miei Progetti");
		mieiProgettiLabel.setIcon(new ImageIcon(MieiProgetti.class.getResource("/Icone/progetto_64.png")));
		mieiProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 30));

		infoProgettoPanel = new JPanel();
		infoProgettoPanel.setFont(new Font("Consolas", Font.PLAIN, 21));
		infoProgettoPanel.setBackground(Color.WHITE);

		TitledBorder titleBorder = new TitledBorder(null, "Info", DO_NOTHING_ON_CLOSE, DO_NOTHING_ON_CLOSE,
				new Font("Consolas", Font.PLAIN, 30));
		titleBorder.setTitlePosition(TitledBorder.CENTER);
		infoProgettoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		nomeProgettoLabel = new JLabel("N/A");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 26));

		ambitiLabel = new JLabel("Ambiti: N/A");
		ambitiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		tipologiaLabel = new JLabel("Tipologia: N/A");
		tipologiaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		dataCreazioneLabel = new JLabel("Data Creazione: N/A");
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		dataTerminazioneLabel = new JLabel("Data Terminazione: N/A");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		dataScadenzaLabel = new JLabel("Data Scadenza: N/A");
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));

		JPanel panel = new JPanel();

		infoLabel = new JLabel("Info");
		panel.add(infoLabel);
		infoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		descrizioneProgettoScrollPane = new JScrollPane();
		descrizioneProgettoScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
		descrizioneProgettoScrollPane.setColumnHeaderView(descrizioneLabel);

		descrizioneProgettoTextArea = new JTextArea();
		descrizioneProgettoTextArea.setEditable(false);
		descrizioneProgettoTextArea.setWrapStyleWord(true);
		descrizioneProgettoTextArea.setLineWrap(true);
		descrizioneProgettoTextArea.setFont(new Font("Consolas", Font.PLAIN, 16));
		descrizioneProgettoScrollPane.setViewportView(descrizioneProgettoTextArea);
		
		nuovoProgettoBotton = new JButton("Inserisci/Modifica Progetto");
		nuovoProgettoBotton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoProgettoBotton.setBackground(Color.WHITE);
		nuovoProgettoBotton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovoProgettoBotton.setFont(new Font("Consolas", Font.PLAIN, 11));
		nuovoProgettoBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGUIGestioneProgetti();
			}
		});
		nuovoProgettoBotton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nuovoProgettoBotton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nuovoProgettoBotton.setBackground(Color.WHITE);
			}
		});
		
		progettiList=new JList<>();
		progettiList.setSelectedIndex(0);
		progettiList.setSelectionBackground(Color.LIGHT_GRAY);
		progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettiList.setFixedCellHeight(60);
		modelloListaProgetti=new DefaultListModel<>();
		impostaCellRendererProgetti();
		inizializzaListaProgetti(controller);
		progettiList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				impostaInfoProgettoSelezionato();
			}
		});
		progettiScrollPane.setViewportView(progettiList);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(762, Short.MAX_VALUE)
										.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 195,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(36)
										.addComponent(progettiScrollPane, GroupLayout.DEFAULT_SIZE, 619,
												Short.MAX_VALUE)
										.addGap(107).addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 614,
												Short.MAX_VALUE)))
						.addGap(38))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(mieiProgettiLabel).addContainerGap(1115, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(mieiProgettiLabel).addGap(29)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(progettiScrollPane, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
								.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 690, Short.MAX_VALUE))
						.addGap(18).addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 50,
								GroupLayout.PREFERRED_SIZE)));
		
		GroupLayout gl_infoProgettoPanel = new GroupLayout(infoProgettoPanel);
		gl_infoProgettoPanel.setHorizontalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_infoProgettoPanel.createSequentialGroup()
							.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoProgettoPanel.createSequentialGroup()
									.addGap(4)
									.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(dataCreazioneLabel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
										.addComponent(tipologiaLabel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
										.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
										.addComponent(dataScadenzaLabel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
										.addComponent(dataTerminazioneLabel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_infoProgettoPanel.createSequentialGroup()
									.addGap(10)
									.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
									.addGap(10)))
							.addContainerGap())
						.addGroup(gl_infoProgettoPanel.createSequentialGroup()
							.addGap(12)
							.addComponent(descrizioneProgettoScrollPane, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
							.addGap(12))))
		);
		gl_infoProgettoPanel.setVerticalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nomeProgettoLabel)
					.addGap(78)
					.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
					.addGap(34)
					.addComponent(tipologiaLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(dataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(dataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(descrizioneProgettoScrollPane, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addGap(23))
		);

		infoProgettoPanel.setLayout(gl_infoProgettoPanel);
		contentPane.setLayout(gl_contentPane);
	}

	private void inizializzaListaProgetti(ControllerProgetto controller) {
		try {
			modelloListaProgetti.addAll(controller.ottieniProgettiDipendente());
		    progettiList.setModel(modelloListaProgetti);
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null,
				    e.getMessage()
					    + "\nVerificare che il programma sia aggiornato\noppure contattare uno sviluppatore.",
				    "Errore #" + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void impostaCellRendererProgetti() {
		progettoCellRenderer = new ProgettoListRenderer();
		progettiList.setCellRenderer(progettoCellRenderer);
	}
	
	private void impostaInfoProgettoSelezionato() {
		Progetto progettoSelezionato = progettiList.getSelectedValue();

		nomeProgettoLabel
				.setText("<html><center>" + progettoSelezionato.getNomeProgetto() + "</center></html>");
		nomeProgettoLabel.setToolTipText(progettoSelezionato.getNomeProgetto());

		String ambiti = "";
		for (AmbitoProgetto ambito : progettoSelezionato.getAmbiti())
			ambiti += ambito.toString() + " ";
		ambitiLabel.setText("<html><center>" + "Ambiti: " + ambiti + "</center></html>");
		ambitiLabel.setToolTipText(ambiti);

		tipologiaLabel.setText("Tipologia: " + progettoSelezionato.getTipoProgetto());
		tipologiaLabel.setToolTipText(progettoSelezionato.getTipoProgetto());

		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
		dataCreazioneLabel
				.setText("Data Creazione: " + progettoSelezionato.getDataCreazione().toString(formatDate));
		dataCreazioneLabel.setToolTipText(progettoSelezionato.getDataCreazione().toString(formatDate));

		if (progettoSelezionato.getDataTerminazione() != null) {
			dataTerminazioneLabel.setText(
					"Data Terminazione: " + progettoSelezionato.getDataTerminazione().toString(formatDate));
			dataTerminazioneLabel
					.setToolTipText(progettoSelezionato.getDataTerminazione().toString(formatDate));
		} else
			dataTerminazioneLabel.setText("Non terminato.");

		if (progettoSelezionato.getScadenza() != null) {
			dataScadenzaLabel
					.setText("Data Scadenza: " + progettoSelezionato.getScadenza().toString(formatDate));
			dataScadenzaLabel.setToolTipText(progettoSelezionato.getScadenza().toString(formatDate));
		} else
			dataScadenzaLabel.setText("Senza scadenza.");

		descrizioneProgettoTextArea.setText(progettoSelezionato.getDescrizioneProgetto());
	}
}
