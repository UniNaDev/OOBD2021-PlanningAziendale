package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controller.ControllerProgetto;
import entita.AmbitoProgetto;
import entita.Progetto;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.event.ListSelectionEvent;

public class MieiProgetti extends JFrame {

	private JPanel contentPane;
	private JButton nuovoProgettoBotton;


	/**
	 * Create the frame.
	 */
	public MieiProgetti(ControllerProgetto theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MieiProgetti.class.getResource("/Icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1200, 780));
		setTitle("iPlanner - I miei Progetti");
		setBounds(100, 100, 1440, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		nuovoProgettoBotton = new JButton("Inserisci/Modifica Progetto");
		nuovoProgettoBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createInsertProjectFrame();
			}
		});
		nuovoProgettoBotton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				nuovoProgettoBotton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				nuovoProgettoBotton.setBackground(Color.WHITE);
			}
		});
		nuovoProgettoBotton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoProgettoBotton.setBackground(Color.WHITE);
		nuovoProgettoBotton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovoProgettoBotton.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		JScrollPane mieiProgettiScrollPane = new JScrollPane();
		mieiProgettiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		

		
		JLabel mieiProgettiLabel = new JLabel("I Miei Progetti");
		mieiProgettiLabel.setIcon(new ImageIcon(MieiProgetti.class.getResource("/Icone/progetto_64.png")));
		mieiProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
	
		JPanel infoProgettoPanel = new JPanel();
		infoProgettoPanel.setFont(new Font("Consolas", Font.PLAIN, 21));
		
		infoProgettoPanel.setBackground(Color.WHITE);
//		infoProgettoPanel.setBorder(new TitledBorder(null, "Info", DO_NOTHING_ON_CLOSE, DO_NOTHING_ON_CLOSE, new Font("Consolas",Font.PLAIN,30)));
		TitledBorder titleBorder = new TitledBorder(null, "Info", DO_NOTHING_ON_CLOSE, DO_NOTHING_ON_CLOSE, new Font("Consolas",Font.PLAIN,30));
		
		titleBorder.setTitlePosition(TitledBorder.CENTER);
		
		infoProgettoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
	
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
					.addGap(183)
					.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 604, Short.MAX_VALUE)
					.addGap(55))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1219, Short.MAX_VALUE)
					.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(mieiProgettiLabel)
					.addContainerGap(1091, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiProgettiLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(infoProgettoPanel, GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
						.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE))
					.addGap(45)
					.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
		);
		
		
		JLabel nomeProgettoLabel = new JLabel("Progetto 1 [01]");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 27));
		
		JLabel ambitiLabel = new JLabel("Ambiti:");
		ambitiLabel.setForeground(Color.DARK_GRAY);
		ambitiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreAmbitiLabel = new JLabel("Economia,Medicina");
		valoreAmbitiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreAmbitiLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel tipologiaLabel = new JLabel("Tipologia:");
		tipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tipologiaLabel.setForeground(Color.DARK_GRAY);
		tipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreTipologiaLabel = new JLabel("Ricerca Sperimentale");
		valoreTipologiaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel projectManagerLabel = new JLabel("Project Manager:");
		projectManagerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		projectManagerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		projectManagerLabel.setForeground(Color.DARK_GRAY);
		projectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreProjectManagerLabel = new JLabel("Mario Rossi");
		valoreProjectManagerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		valoreProjectManagerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreProjectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel dataCreazioneLabel = new JLabel("Data Creazione:");
		dataCreazioneLabel.setIconTextGap(40);
		dataCreazioneLabel.setIcon(new ImageIcon(MieiProgetti.class.getResource("/Icone/dataCreazione_32.png")));
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataCreazioneLabel.setForeground(Color.DARK_GRAY);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione:");
		dataTerminazioneLabel.setIconTextGap(40);
		dataTerminazioneLabel.setIcon(new ImageIcon(MieiProgetti.class.getResource("/Icone/dataTerminazione_32.png")));
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataTerminazioneLabel.setForeground(Color.DARK_GRAY);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel dataScadenzaLabel = new JLabel("Data Scadenza:");
		dataScadenzaLabel.setIconTextGap(40);
		dataScadenzaLabel.setIcon(new ImageIcon(MieiProgetti.class.getResource("/Icone/dataScadenza_32.png")));
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataScadenzaLabel.setForeground(Color.DARK_GRAY);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreDataCreazioneLabel = new JLabel("07/01/2021");
		valoreDataCreazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreDataScadenzaLabel = new JLabel("07/01/2022");
		valoreDataScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreDataTerminazioneLabel = new JLabel("N/A");
		valoreDataTerminazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JPanel panel = new JPanel();
		
		JTextArea descrizioneProgettoTextArea = new JTextArea();
		descrizioneProgettoTextArea.setBackground(Color.WHITE);
		descrizioneProgettoTextArea.setEditable(false);
		descrizioneProgettoTextArea.setFont(new Font("Consolas", Font.PLAIN, 22));
		GroupLayout gl_infoProgettoPanel = new GroupLayout(infoProgettoPanel);
		gl_infoProgettoPanel.setHorizontalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(114)
					.addComponent(dataCreazioneLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(valoreDataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addGap(127))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(projectManagerLabel, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(valoreProjectManagerLabel, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(tipologiaLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
						.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(valoreTipologiaLabel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
						.addComponent(valoreAmbitiLabel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
					.addGap(98))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(131)
					.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
					.addGap(152))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(573, Short.MAX_VALUE))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(105)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoProgettoPanel.createSequentialGroup()
							.addComponent(descrizioneProgettoTextArea, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_infoProgettoPanel.createSequentialGroup()
								.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(valoreDataTerminazioneLabel, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(gl_infoProgettoPanel.createSequentialGroup()
								.addComponent(dataScadenzaLabel, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(valoreDataScadenzaLabel, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
								.addGap(144)))))
		);
		gl_infoProgettoPanel.setVerticalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(nomeProgettoLabel)
					.addGap(48)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreAmbitiLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreTipologiaLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(tipologiaLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(projectManagerLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
						.addComponent(valoreProjectManagerLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
					.addGap(40)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(dataCreazioneLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(valoreDataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(dataScadenzaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(valoreDataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreDataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
						.addComponent(dataTerminazioneLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(37)
					.addComponent(descrizioneProgettoTextArea, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addGap(84))
		);
		
		
		JList <Progetto> progettiList;
		try {
			progettiList = new JList(theController.ottieniProgetti().toArray());
			progettiList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					//Aggiorna le label delle info del progetto
					Progetto progettoSelezionato = progettiList.getSelectedValue();
					nomeProgettoLabel.setText(progettoSelezionato.getNomeProgetto());
					
					String ambiti = "";
					for (AmbitoProgetto ambito : progettoSelezionato.getAmbiti())
						ambiti += ambito.toString() + " ";
					valoreAmbitiLabel.setText(ambiti);
					
					valoreTipologiaLabel.setText(progettoSelezionato.getTipoProgetto());
					
					valoreProjectManagerLabel.setText(theController.getLoggedUser().getNome() + " " + theController.getLoggedUser().getCognome());
					
					DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy"); 
					valoreDataCreazioneLabel.setText(progettoSelezionato.getDataTerminazione().toString(formatDate));
					
					if (progettoSelezionato.getDataTerminazione() != null)
						valoreDataTerminazioneLabel.setText(progettoSelezionato.getDataTerminazione().toString(formatDate));
					else
						valoreDataTerminazioneLabel.setText("In corso.");
					
					if (progettoSelezionato.getScadenza() != null)
						valoreDataScadenzaLabel.setText(progettoSelezionato.getScadenza().toString(formatDate));
					else
						valoreDataScadenzaLabel.setText("Senza scadenza.");
					
					descrizioneProgettoTextArea.setText(progettoSelezionato.getDescrizioneProgetto());
				}
			});
			
			progettiList.setSelectedIndex(0);
			progettiList.setSelectionBackground(Color.LIGHT_GRAY);
			progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
			progettiList.setFixedCellHeight(40);
			mieiProgettiScrollPane.setViewportView(progettiList);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		JLabel lblNewLabel = new JLabel("Info");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		infoProgettoPanel.setLayout(gl_infoProgettoPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
