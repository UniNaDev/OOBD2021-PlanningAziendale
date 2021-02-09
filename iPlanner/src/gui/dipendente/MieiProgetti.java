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
		setMinimumSize(new Dimension(1200, 900));
		setTitle("iPlanner - I miei Progetti");
		setBounds(100, 100, 1440, 900);
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
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
					.addGap(80)
					.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 604, Short.MAX_VALUE)
					.addGap(38))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1219, Short.MAX_VALUE)
					.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(mieiProgettiLabel)
					.addContainerGap(1125, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiProgettiLabel)
					.addGap(18)
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
		
		//Label "Ambiti:"
		JLabel ambitiLabel = new JLabel("Ambiti:");
		ambitiLabel.setForeground(Color.DARK_GRAY);
		ambitiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		
		//Label per ambiti del progetto
		JLabel valoreAmbitiLabel = new JLabel("N/A");
		valoreAmbitiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreAmbitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label "Tipologia:"
		JLabel tipologiaLabel = new JLabel("Tipologia:");
		tipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tipologiaLabel.setForeground(Color.DARK_GRAY);
		tipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		
		//Label per tipologia di progetto
		JLabel valoreTipologiaLabel = new JLabel("N/A");
		valoreTipologiaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label "Project Manager:"
		JLabel projectManagerLabel = new JLabel("Project Manager:");
		projectManagerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		projectManagerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		projectManagerLabel.setForeground(Color.DARK_GRAY);
		projectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		
		//Label per project manager
		JLabel valoreProjectManagerLabel = new JLabel("N/A");
		valoreProjectManagerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		valoreProjectManagerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreProjectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label "Data Creazione:"
		JLabel dataCreazioneLabel = new JLabel("Data Creazione:");
		dataCreazioneLabel.setIconTextGap(40);
		dataCreazioneLabel.setIcon(null);
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataCreazioneLabel.setForeground(Color.DARK_GRAY);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		
		//Label per data di creazione
		JLabel valoreDataCreazioneLabel = new JLabel("N/A");
		valoreDataCreazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label "Data Terminazione"
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione:");
		dataTerminazioneLabel.setIconTextGap(40);
		dataTerminazioneLabel.setIcon(null);
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataTerminazioneLabel.setForeground(Color.DARK_GRAY);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		
		//Label per data di terminazione
		JLabel valoreDataTerminazioneLabel = new JLabel("N/A");
		valoreDataTerminazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		//Label "Data Scadenza"
		JLabel dataScadenzaLabel = new JLabel("Data Scadenza:");
		dataScadenzaLabel.setIconTextGap(40);
		dataScadenzaLabel.setIcon(null);
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataScadenzaLabel.setForeground(Color.DARK_GRAY);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		
		//Label per data scadenza
		JLabel valoreDataScadenzaLabel = new JLabel("N/A");
		valoreDataScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JPanel panel = new JPanel();
		
		JScrollPane descrizioneScrollPane = new JScrollPane();
		descrizioneScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout gl_infoProgettoPanel = new GroupLayout(infoProgettoPanel);
		gl_infoProgettoPanel.setHorizontalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(507, Short.MAX_VALUE))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(98)
					.addComponent(descrizioneScrollPane, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
					.addGap(69))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(31)
					.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
					.addGap(26))
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(47)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(ambitiLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(tipologiaLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(projectManagerLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(dataCreazioneLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(dataScadenzaLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(dataTerminazioneLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(valoreAmbitiLabel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(valoreTipologiaLabel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(valoreProjectManagerLabel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(valoreDataCreazioneLabel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(valoreDataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 224, Short.MAX_VALUE)
						.addComponent(valoreDataTerminazioneLabel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
					.addGap(43))
		);
		gl_infoProgettoPanel.setVerticalGroup(
			gl_infoProgettoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valoreAmbitiLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
						.addComponent(ambitiLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(tipologiaLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(valoreTipologiaLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(projectManagerLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(valoreProjectManagerLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(dataCreazioneLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(valoreDataCreazioneLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(dataScadenzaLabel)
						.addComponent(valoreDataScadenzaLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_infoProgettoPanel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(dataTerminazioneLabel)
						.addComponent(valoreDataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addComponent(descrizioneScrollPane, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
					.addGap(23))
		);
		gl_infoProgettoPanel.linkSize(SwingConstants.VERTICAL, new Component[] {dataTerminazioneLabel, valoreDataTerminazioneLabel});
		gl_infoProgettoPanel.linkSize(SwingConstants.VERTICAL, new Component[] {dataScadenzaLabel, valoreDataScadenzaLabel});
		
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
					nomeProgettoLabel.setText("<html><p style=\"width:400px\">"+progettoSelezionato.getNomeProgetto()+"</p></html>");	//nome del progetto
					
					//Ambiti
					String ambiti = "";
					for (AmbitoProgetto ambito : progettoSelezionato.getAmbiti())
						ambiti += ambito.toString() + " ";
					valoreAmbitiLabel.setText("<html><p style=\"width:200px\">"+ambiti+"</p></html>");
					
					valoreTipologiaLabel.setText(progettoSelezionato.getTipoProgetto());	//tipologia del progetto
					
					valoreProjectManagerLabel.setText(dipendente.getNome() + " " + dipendente.getCognome());	//project manager
					
					DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");	//data creazione
					valoreDataCreazioneLabel.setText(progettoSelezionato.getDataCreazione().toString(formatDate));
					
					
					if (progettoSelezionato.getDataTerminazione() !=null)	//data terminazione
						valoreDataTerminazioneLabel.setText(progettoSelezionato.getDataTerminazione().toString(formatDate));
					else
						valoreDataTerminazioneLabel.setText("In corso.");
					
					if (progettoSelezionato.getScadenza()!=null)	//data scadenza
						valoreDataScadenzaLabel.setText(progettoSelezionato.getScadenza().toString(formatDate));
					else
						valoreDataScadenzaLabel.setText("Senza scadenza.");
					
					descrizioneProgettoTextArea.setText(progettoSelezionato.getDescrizioneProgetto());	//descrizione del progetto
				}
			});
			
			progettiList.setSelectedIndex(0);
			progettiList.setSelectionBackground(Color.LIGHT_GRAY);
			progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
			progettiList.setFixedCellHeight(40);
			mieiProgettiScrollPane.setViewportView(progettiList);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}


		//Label "Info"
		JLabel lblNewLabel = new JLabel("Info");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		infoProgettoPanel.setLayout(gl_infoProgettoPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
