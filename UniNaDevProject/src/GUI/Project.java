package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerProgetto;

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
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;

public class Project extends JFrame {

	private JPanel contentPane;
	private JButton modificaProgettoBotton;
	private JButton nuovoProgettoBotton;


	/**
	 * Create the frame.
	 */
	public Project(ControllerProgetto theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Project.class.getResource("/Icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1200, 700));
		setTitle("iPlanner - I miei Progetti");
		setBounds(100, 100, 1440, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		modificaProgettoBotton = new JButton("Modifica Progetto");
		modificaProgettoBotton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				modificaProgettoBotton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				modificaProgettoBotton.setBackground(Color.WHITE);
			}
		});
		modificaProgettoBotton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaProgettoBotton.setBackground(Color.WHITE);
		modificaProgettoBotton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaProgettoBotton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modificaProgettoBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createModifyProjectFrame();
			}
		});
		
		nuovoProgettoBotton = new JButton("Nuovo Progetto");
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
		mieiProgettiLabel.setIcon(new ImageIcon(Project.class.getResource("/Icone/progetto_64.png")));
		mieiProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JPanel infoProgettoPanel = new JPanel();
		infoProgettoPanel.setBackground(Color.WHITE);
		infoProgettoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,new Color(255, 255, 255), new Color(160, 160, 160)), "Info", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
	
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(mieiProgettiLabel)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(36)
							.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(183)
									.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 599, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(modificaProgettoBotton, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))))
					.addGap(55))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mieiProgettiLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 425, Short.MAX_VALUE)
							.addGap(283)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(modificaProgettoBotton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
							.addGap(61))))
		);
		
		JList list = new JList();
		list.setSelectionBackground(Color.LIGHT_GRAY);
		list.setFont(new Font("Consolas", Font.PLAIN, 15));
		list.setFixedCellHeight(40);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Meeting 1", "Meeting 2", "Meeting 3", "Discord 3", "Riunione Teams", "Leo Pardo Cisco Webex", "Riunione ", "Prova ", "Prova 27"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		mieiProgettiScrollPane.setViewportView(list);
		


		
		JLabel nomeProgettoLabel = new JLabel("Progetto 1 [ECOB2]");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel ambitiLabel = new JLabel("Ambiti:");
		ambitiLabel.setForeground(Color.DARK_GRAY);
		ambitiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreTipologieLabel = new JLabel("Economia,Medicina");
		valoreTipologieLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreTipologieLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel valoreTipologiaLabel = new JLabel("Tipologia:");
		valoreTipologiaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		valoreTipologiaLabel.setForeground(Color.DARK_GRAY);
		valoreTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel lblRicercaSperimentale = new JLabel("Ricerca Sperimentale");
		lblRicercaSperimentale.setHorizontalAlignment(SwingConstants.LEFT);
		lblRicercaSperimentale.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel projectManagerLabel = new JLabel("Project Manager:");
		projectManagerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		projectManagerLabel.setForeground(Color.DARK_GRAY);
		projectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreProjectManagerLabel = new JLabel("Mario Rossi");
		valoreProjectManagerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreProjectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		JLabel dataCreazioneLabel = new JLabel("Data Creazione:");
		dataCreazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataCreazioneLabel.setForeground(Color.DARK_GRAY);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione:");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataTerminazioneLabel.setForeground(Color.DARK_GRAY);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel dataScadenzaLabel = new JLabel("Data Scadenza:");
		dataScadenzaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataScadenzaLabel.setForeground(Color.DARK_GRAY);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		JLabel valoreDataCreazioneLabel = new JLabel("07/01/2021");
		valoreDataCreazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreDataScadenzaLabel = new JLabel("07/01/2022");
		valoreDataScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		
		JLabel valoreDataTerminazioneLabel = new JLabel("N/A");
		valoreDataTerminazioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreDataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
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
				.addGroup(gl_infoProgettoPanel.createSequentialGroup()
					.addGap(130)
					.addComponent(nomeProgettoLabel, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
					.addGap(153))
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
		contentPane.setLayout(gl_contentPane);
	}
}
