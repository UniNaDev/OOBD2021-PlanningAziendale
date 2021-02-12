package gui.dipendente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import entita.AmbitoProgetto;
import entita.Dipendente;
import entita.Progetto;
import gui.cellRenderers.ProgettoListRenderer;
import gui.customUI.CustomScrollBarUI;

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
import javax.swing.JOptionPane;
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

import controller.dipendente.ControllerProgetto;

import javax.swing.event.ListSelectionEvent;
import java.awt.Component;

public class MieiProgetti extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JButton nuovoProgettoBotton;

	//Creazione frame
	//-----------------------------------------------------------------
	
	public MieiProgetti(ControllerProgetto controller, Dipendente dipendente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MieiProgetti.class.getResource("/Icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1440, 900));
		setTitle("iPlanner - I miei Progetti");
		setBounds(100, 100, 1419, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Button "Inserisci/Modifica Progetto"
		nuovoProgettoBotton = new JButton("Inserisci/Modifica Progetto");
		//Click sul pulsante
		nuovoProgettoBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriGestioneProgetti();	//apre la finestra per la gestione dei progetti
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
		
		//ScrollPane progetti
		JScrollPane mieiProgettiScrollPane = new JScrollPane();
		mieiProgettiScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		mieiProgettiScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		mieiProgettiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		//Label "Miei Progetti"
		JLabel mieiProgettiLabel = new JLabel("Miei Progetti");
		mieiProgettiLabel.setIcon(new ImageIcon(MieiProgetti.class.getResource("/Icone/progetto_64.png")));
		mieiProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//Panel per info progetto
		JPanel infoProgettoPanel = new JPanel();
		infoProgettoPanel.setFont(new Font("Consolas", Font.PLAIN, 21));
		infoProgettoPanel.setBackground(Color.WHITE);
		TitledBorder titleBorder = new TitledBorder(null, "Info", DO_NOTHING_ON_CLOSE, DO_NOTHING_ON_CLOSE, new Font("Consolas",Font.PLAIN,30));
		titleBorder.setTitlePosition(TitledBorder.CENTER);
		infoProgettoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(762, Short.MAX_VALUE)
							.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(36)
							.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
							.addGap(107)
							.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 614, Short.MAX_VALUE)))
					.addGap(38))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiProgettiLabel)
					.addContainerGap(1115, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(mieiProgettiLabel)
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
						.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 690, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
		);
		
		//Label per nome del progetto
		JLabel nomeProgettoLabel = new JLabel("N/A");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 26));
		
		//Label per ambiti del progetto
		JLabel ambitiLabel = new JLabel("Ambiti: N/A");
		ambitiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per tipologia di progetto
		JLabel tipologiaLabel = new JLabel("Tipologia: N/A");
		tipologiaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per data di creazione
		JLabel dataCreazioneLabel = new JLabel("Data Creazione: N/A");
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per data di terminazione
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione: N/A");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label per data scadenza
		JLabel dataScadenzaLabel = new JLabel("Data Scadenza: N/A");
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JPanel panel = new JPanel();
		
		JScrollPane descrizioneScrollPane = new JScrollPane();
		descrizioneScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout gl_infoProgettoPanel = new GroupLayout(infoProgettoPanel);
		gl_infoProgettoPanel.setHorizontalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_infoProgettoPanel.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dataCreazioneLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
								.addComponent(tipologiaLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
								.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
								.addComponent(dataScadenzaLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
								.addComponent(dataTerminazioneLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
								.addComponent(descrizioneScrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)))
						.addGroup(Alignment.LEADING, gl_infoProgettoPanel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
							.addGap(10)))
					.addGap(12))
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
					.addComponent(descrizioneScrollPane, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addGap(23))
		);
		
		//Label "Descrizione"
		JLabel descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
		descrizioneScrollPane.setColumnHeaderView(descrizioneLabel);
		
		//TextArea descrizione del progetto
		JTextArea descrizioneProgettoTextArea = new JTextArea();
		descrizioneProgettoTextArea.setEditable(false);
		descrizioneProgettoTextArea.setWrapStyleWord(true);
		descrizioneProgettoTextArea.setLineWrap(true);
		descrizioneProgettoTextArea.setFont(new Font("Consolas", Font.PLAIN, 16));
		descrizioneScrollPane.setViewportView(descrizioneProgettoTextArea);
		
		//List di progetti
		JList <Progetto> progettiList;
		ProgettoListRenderer progettoCellRenderer = new ProgettoListRenderer();
		
		try {
			progettiList = new JList(controller.ottieniProgetti().toArray());
			progettiList.setCellRenderer(progettoCellRenderer);
			//Selezione nella lista
			progettiList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					//Aggiorna le label delle info del progetto
					Progetto progettoSelezionato = progettiList.getSelectedValue();
					
					nomeProgettoLabel.setText("<html><center>"+progettoSelezionato.getNomeProgetto()+"</center></html>");	//nome del progetto
					nomeProgettoLabel.setToolTipText(progettoSelezionato.getNomeProgetto());
					
					//Ambiti
					String ambiti = "";
					for (AmbitoProgetto ambito : progettoSelezionato.getAmbiti())
						ambiti += ambito.toString() + " ";
					ambitiLabel.setText("<html><center>"+"Ambiti: " + ambiti+ "</center></html>");
					ambitiLabel.setToolTipText(ambiti);
					
					tipologiaLabel.setText("Tipologia: " + progettoSelezionato.getTipoProgetto());	//tipologia del progetto
					tipologiaLabel.setToolTipText(progettoSelezionato.getTipoProgetto());
					
					
					
					DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");	//data creazione
					dataCreazioneLabel.setText("Data Creazione: " + progettoSelezionato.getDataCreazione().toString(formatDate));
					dataCreazioneLabel.setToolTipText(progettoSelezionato.getDataCreazione().toString(formatDate));
					
					
					if (progettoSelezionato.getDataTerminazione() !=null) {	//data terminazione
						dataTerminazioneLabel.setText("Data Terminazione: " + progettoSelezionato.getDataTerminazione().toString(formatDate));
						dataTerminazioneLabel.setToolTipText(progettoSelezionato.getDataTerminazione().toString(formatDate));
					}
					else
						dataTerminazioneLabel.setText("Non terminato.");
					
					if (progettoSelezionato.getScadenza()!=null) {	//data scadenza
						dataScadenzaLabel.setText("Data Scadenza: " + progettoSelezionato.getScadenza().toString(formatDate));
						dataScadenzaLabel.setToolTipText(progettoSelezionato.getScadenza().toString(formatDate));
					}
					else
						dataScadenzaLabel.setText("Senza scadenza.");
					
					descrizioneProgettoTextArea.setText(progettoSelezionato.getDescrizioneProgetto());	//descrizione del progetto
				}
			});
			progettiList.setSelectedIndex(0);
			progettiList.setSelectionBackground(Color.LIGHT_GRAY);
			progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
			progettiList.setFixedCellHeight(40);
			mieiProgettiScrollPane.setViewportView(progettiList);
		} catch (SQLException e1) {
			//errore select per tutti i progetti nel database
			JOptionPane.showMessageDialog(null,
				"Impossibile ottenere tutti i progetti dal database.\nControllare che la connessione al database sia stabilita.",
				"Errore Interrogazione Database",
				JOptionPane.ERROR_MESSAGE);
		}


		//Label "Info"
		JLabel lblNewLabel = new JLabel("Info");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		infoProgettoPanel.setLayout(gl_infoProgettoPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
