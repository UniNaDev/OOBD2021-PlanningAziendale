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

public class Project extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public Project(ControllerProgetto theController) {
		setMinimumSize(new Dimension(1200, 700));
		setTitle("Progetti");
		setBounds(100, 100, 1440, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton modificaProgettoBotton = new JButton("Modifica Progetto");
		modificaProgettoBotton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaProgettoBotton.setBackground(Color.WHITE);
		modificaProgettoBotton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaProgettoBotton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modificaProgettoBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createModifyProjectFrame();
			}
		});
		
		JButton okButton = new JButton("OK");
		okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		okButton.setBackground(Color.WHITE);
		okButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		okButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton nuovoProgettoBotton = new JButton("Nuovo Progetto");
		nuovoProgettoBotton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoProgettoBotton.setBackground(Color.WHITE);
		nuovoProgettoBotton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovoProgettoBotton.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		JScrollPane mieiProgettiScrollPane = new JScrollPane();
		mieiProgettiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JLabel mieiProgettiLabel = new JLabel("I Miei Progetti");
		mieiProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JPanel infoProgettoPanel = new JPanel();
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBackground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(modificaProgettoBotton, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(mieiProgettiLabel)
								.addComponent(mieiProgettiScrollPane, GroupLayout.PREFERRED_SIZE, 527, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(infoProgettoPanel, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
							.addGap(10))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(infoProgettoPanel, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(mieiProgettiLabel)
									.addGap(18)
									.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nuovoProgettoBotton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(modificaProgettoBotton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(92)
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
							.addGap(96))))
		);
		infoProgettoPanel.setLayout(null);
		
		JLabel infoLabel = new JLabel("Info");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		infoLabel.setBounds(251, 11, 89, 36);
		infoProgettoPanel.add(infoLabel);
		
		JLabel nomeProgettoLabel = new JLabel("Progetto 1 [ECOB2]");
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		nomeProgettoLabel.setBounds(149, 67, 295, 38);
		infoProgettoPanel.add(nomeProgettoLabel);
		
		JLabel ambitiLabel = new JLabel("Ambiti:");
		ambitiLabel.setForeground(Color.DARK_GRAY);
		ambitiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ambitiLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		ambitiLabel.setBounds(10, 116, 295, 38);
		infoProgettoPanel.add(ambitiLabel);
		
		JLabel valoreTipologieLabel = new JLabel("Economia , Medicina");
		valoreTipologieLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreTipologieLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valoreTipologieLabel.setBounds(149, 151, 295, 38);
		infoProgettoPanel.add(valoreTipologieLabel);
		
		JLabel valoreTipologiaLabel = new JLabel("Tipologia:");
		valoreTipologiaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		valoreTipologiaLabel.setForeground(Color.DARK_GRAY);
		valoreTipologiaLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		valoreTipologiaLabel.setBounds(10, 231, 295, 38);
		infoProgettoPanel.add(valoreTipologiaLabel);
		
		JLabel lblRicercaSperimentale = new JLabel("Ricerca Sperimentale");
		lblRicercaSperimentale.setHorizontalAlignment(SwingConstants.CENTER);
		lblRicercaSperimentale.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblRicercaSperimentale.setBounds(149, 269, 295, 38);
		infoProgettoPanel.add(lblRicercaSperimentale);
		
		JLabel projectManagerLabel = new JLabel("Project Manager:");
		projectManagerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		projectManagerLabel.setForeground(Color.DARK_GRAY);
		projectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		projectManagerLabel.setBounds(10, 356, 295, 38);
		infoProgettoPanel.add(projectManagerLabel);
		
		JLabel valoreProjectManagerLabel = new JLabel("Mario Rossi");
		valoreProjectManagerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreProjectManagerLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valoreProjectManagerLabel.setBounds(149, 393, 295, 38);
		infoProgettoPanel.add(valoreProjectManagerLabel);
		
		JLabel dataCreazioneLabel = new JLabel("Data Creazione:");
		dataCreazioneLabel.setForeground(Color.DARK_GRAY);
		dataCreazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		dataCreazioneLabel.setBounds(10, 503, 172, 24);
		infoProgettoPanel.add(dataCreazioneLabel);
		
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione:");
		dataTerminazioneLabel.setForeground(Color.DARK_GRAY);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		dataTerminazioneLabel.setBounds(376, 500, 202, 30);
		infoProgettoPanel.add(dataTerminazioneLabel);
		
		JLabel dataScadenzaLabel = new JLabel("Data Scadenza:");
		dataScadenzaLabel.setForeground(Color.DARK_GRAY);
		dataScadenzaLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		dataScadenzaLabel.setBounds(207, 503, 169, 24);
		infoProgettoPanel.add(dataScadenzaLabel);
		
		JLabel lblNewLabel = new JLabel("07/01/2021");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 22));
		lblNewLabel.setBounds(10, 537, 161, 24);
		infoProgettoPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("07/01/2022");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Consolas", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(207, 537, 161, 24);
		infoProgettoPanel.add(lblNewLabel_1);
		
		JLabel lblNa = new JLabel("N/A");
		lblNa.setHorizontalAlignment(SwingConstants.CENTER);
		lblNa.setFont(new Font("Consolas", Font.PLAIN, 22));
		lblNa.setBounds(386, 541, 161, 24);
		infoProgettoPanel.add(lblNa);
		
		JList progettiList = new JList();
		progettiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettiList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Progetto 1", "Progetto 2", "Progetto 3", "Lol", "Ciao", "iPlanner", "Windows 20", "Fortnite", "Rino Ceronte", "Cyberpunk 2077", "Test", "Test1", "Parole a caso per ridimensionare", "bho"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		progettiList.setSelectionBackground(Color.LIGHT_GRAY);
		progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettiList.setFixedCellHeight(40);
		mieiProgettiScrollPane.setViewportView(progettiList);
		contentPane.setLayout(gl_contentPane);
	}
}
