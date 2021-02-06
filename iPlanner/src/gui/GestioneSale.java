package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import controller.ControllerMeetingSegreteria;
import entita.SalaRiunione;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class GestioneSale extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTextField nomeSalaTextField;
	private JTextField capienzaTextField;
	private JTextArea indirizzoTextArea;
	private JTextField pianoTextField;

	//METODI
	//-----------------------------------------------------------------
	
	public GestioneSale(ControllerMeetingSegreteria controller) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.chiudiGestioneSale();
			}
		});
		setResizable(false);
		setTitle("Gestione Sale");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 678, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Info Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		infoPanel.setBounds(25, 85, 304, 219);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		//Label "Sala"
		JLabel nomeSalaLabel = new JLabel("Sala");
		nomeSalaLabel.setBounds(10, 29, 28, 16);
		nomeSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		infoPanel.add(nomeSalaLabel);
		
		//TextField codice sala
		nomeSalaTextField = new JTextField();
		nomeSalaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeSalaTextField.setBounds(48, 27, 129, 20);
		infoPanel.add(nomeSalaTextField);
		nomeSalaTextField.setColumns(10);
		
		//Label "Capienza"
		JLabel capienzaLabel = new JLabel("Capienza");
		capienzaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		capienzaLabel.setBounds(10, 78, 56, 16);
		infoPanel.add(capienzaLabel);
		
		//TextField capienza
		capienzaTextField = new JTextField();
		capienzaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		capienzaTextField.setColumns(10);
		capienzaTextField.setBounds(70, 76, 56, 20);
		infoPanel.add(capienzaTextField);
		
		//Label "Indirizzo"
		JLabel indirizzoLabel = new JLabel("Indirizzo");
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		indirizzoLabel.setBounds(10, 125, 63, 16);
		infoPanel.add(indirizzoLabel);
		
		//TextArea indirizzo della sala
		indirizzoTextArea = new JTextArea();
		indirizzoTextArea.setLineWrap(true);
		indirizzoTextArea.setBorder(UIManager.getBorder("TextField.border"));
		indirizzoTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		indirizzoTextArea.setColumns(10);
		indirizzoTextArea.setBounds(80, 123, 187, 34);
		infoPanel.add(indirizzoTextArea);
		
		//Label "Piano"
		JLabel pianoLabel = new JLabel("Piano");
		pianoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		pianoLabel.setBounds(10, 170, 35, 16);
		infoPanel.add(pianoLabel);
		
		//TextField piano della sala
		pianoTextField = new JTextField();
		pianoTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		pianoTextField.setColumns(10);
		pianoTextField.setBounds(48, 168, 35, 20);
		infoPanel.add(pianoTextField);
		
		//Label per pulizia campi
		JLabel resetCampiLabel = new JLabel("");
		resetCampiLabel.setIcon(new ImageIcon(GestioneSale.class.getResource("/icone/refresh.png")));
		resetCampiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		resetCampiLabel.setBounds(268, 11, 16, 16);
		infoPanel.add(resetCampiLabel);
		
		JButton eliminaSalaButton = new JButton("Elimina");
		eliminaSalaButton.setBounds(213, 185, 81, 23);
		infoPanel.add(eliminaSalaButton);
		eliminaSalaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		//Scroll Panel per lista sale
		JScrollPane saleListScrollPanel = new JScrollPane();
		saleListScrollPanel.setBounds(410, 85, 225, 219);
		contentPane.add(saleListScrollPanel);
		
		//List sale nel DB
		DefaultListModel saleListModel = new DefaultListModel();
		JList saleList = new JList();
		saleList.setFont(new Font("Consolas", Font.PLAIN, 13));
		try {
			saleListModel.addAll(controller.ottieniSale());
			saleList.setModel(saleListModel);
			saleListScrollPanel.setViewportView(saleList);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//selezione nella lista di una sala
		saleList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//Ottiene la sala selezionata
				SalaRiunione salaSelezionata = (SalaRiunione) saleList.getSelectedValue();
				//Aggiorna GUI
				nomeSalaTextField.setText(salaSelezionata.getCodSala()); //codice sala
				capienzaTextField.setText(Integer.toString(salaSelezionata.getCap())); //capienza
				indirizzoTextArea.setText(salaSelezionata.getIndirizzo()); //indirizzo
				pianoTextField.setText(Integer.toString(salaSelezionata.getPiano())); //piano
			}
		});
		
		//Label "Sale" della lista
		JLabel saleLabel = new JLabel("Sale");
		saleLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		saleListScrollPanel.setColumnHeaderView(saleLabel);
		
		//Label "Gestione Sale"
		JLabel titoloLabel = new JLabel("Gestione Sale");
		titoloLabel.setIcon(new ImageIcon(GestioneSale.class.getResource("/icone/meeting_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(10, 11, 234, 63);
		contentPane.add(titoloLabel);
		
		//Separator
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(368, 85, 17, 219);
		contentPane.add(separator);
		
		//Button per creare sale nuove
		JButton creaSalaButton = new JButton("Crea");
		//Click pulsante
		creaSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				capienzaLabel.setForeground(Color.BLACK);
				indirizzoLabel.setForeground(Color.BLACK);
				pianoLabel.setForeground(Color.BLACK);
				//crea sala nuova
				if (!nomeSalaTextField.getText().isBlank() && !capienzaTextField.getText().isBlank() && !indirizzoTextArea.getText().isBlank() && !pianoTextField.getText().isBlank()) {
					SalaRiunione salaNuova = new SalaRiunione(nomeSalaTextField.getText(), Integer.parseInt(capienzaTextField.getText()), indirizzoTextArea.getText(), Integer.parseInt(pianoTextField.getText()));
					try {
						controller.creaSala(salaNuova);
						saleListModel.addElement(salaNuova);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					if (nomeSalaTextField.getText().isBlank())
						nomeSalaLabel.setForeground(Color.RED);
					if (capienzaTextField.getText().isBlank())
						capienzaLabel.setForeground(Color.RED);
					if (indirizzoTextArea.getText().isBlank())
						indirizzoLabel.setForeground(Color.RED);
					if (pianoTextField.getText().isBlank())
						pianoLabel.setForeground(Color.RED);
					
					JOptionPane.showMessageDialog(null,
							"Alcuni campi obbligatori sono vuoti",
							"Errore campi vuoti",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		creaSalaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		creaSalaButton.setBounds(560, 315, 77, 23);
		contentPane.add(creaSalaButton);
		
		//Button per salvare modifiche a una sala
		JButton salvaModificheButton = new JButton("Salva Modifiche");
		salvaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		salvaModificheButton.setBounds(410, 315, 139, 23);
		contentPane.add(salvaModificheButton);
		
		//Button per uscire dalla finestra
		JButton esciButton = new JButton("Esci");
		//Click pulsante
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chiude la finestra
				controller.chiudiGestioneSale();
			}
		});
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		esciButton.setBounds(25, 315, 71, 23);
		contentPane.add(esciButton);
	}
}
