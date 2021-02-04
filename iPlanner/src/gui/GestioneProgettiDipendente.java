package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ControllerProgetto;
import entita.AmbitoProgetto;
import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Progetto;
import entita.Skill;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.joda.time.LocalDate;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class GestioneProgettiDipendente extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attributi GUI
	private JPanel contentPane;
	private JTextField nomeTextField;
	private JTable progettoTable;
	private JTextField cercaTextField;
	private JTextArea descrizioneTextArea;
	private JComboBox giornoScadenzaComboBox;
	private JComboBox meseScadenzaComboBox;
	private JComboBox annoScadenzaComboBox;
	private JComboBox giornoTerminazioneComboBox;
	private JComboBox meseTerminazioneComboBox;
	private JComboBox annoTerminazioneComboBox;
	private JComboBox tipologiaComboBox;
	private ProgettoTableModel dataModel;
	private JCheckBox progettoTerminatoCheckBox;
	private JList ambitiList;
	private JList partecipantiList;
	private JList meetingRelativiList;

	//Creazione frame
	//-----------------------------------------------------------------
	
	public GestioneProgettiDipendente(ControllerProgetto controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneProgettiDipendente.class.getResource("/icone/WindowIcon_16.png")));
		setMinimumSize(new Dimension(1150, 700));
		setLocationRelativeTo(null);
		setTitle("iPlanner-Gestione progetto");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel infoPanel = new JPanel();	//panel delle info
		
		JPanel comandiPanel = new JPanel();	//panel dei comandi
		comandiPanel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		comandiPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane tabellaScrollPane = new JScrollPane();	//scroll pane della tabella
		tabellaScrollPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		tabellaScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabellaScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comandiPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(infoPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(tabellaScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comandiPanel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(tabellaScrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		
		comandiPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//panel interno dei comandi
		JPanel comandiPanel2 = new JPanel();
		comandiPanel2.setBorder(new LineBorder(new Color(192, 192, 192)));
		
		//Button "Pulisci Campi"
		JButton pulisciCampiButton = new JButton("Pulisci Campi");
		pulisciCampiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				pulisciCampiButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				pulisciCampiButton.setBackground(Color.WHITE);
			}
		});
		
		pulisciCampiButton.setPreferredSize(new Dimension(150, 30));
		pulisciCampiButton.setMaximumSize(new Dimension(150, 150));
		pulisciCampiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciCampiButton.setBackground(Color.WHITE);
		pulisciCampiButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pulisciCampiButton.setAlignmentX(0.5f);
		pulisciCampiButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		pulisciCampiButton.setMargin(new Insets(2, 20, 2, 20));
		
		//Button "Elimina"
		JButton eliminaButton = new JButton("Elimina");
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//chiede conferma all utente 
				int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare il progetto selezionato?");
				
				if(conferma == JOptionPane.YES_OPTION && progettoTable.getSelectedRow()!=-1)
				{
					//ricava il codice del progetto selezionato dalla tabella
					int codProgetto = (int)progettoTable.getValueAt(progettoTable.getSelectedRow(), 0);
				
					try 
					{
						boolean risultato = controller.removeProgettoByCod(codProgetto);
						
						
						JOptionPane.showMessageDialog(null, "Progetto Eliminato con successo");
						dataModel.fireTableDataChanged();
						dataModel.setProgettiTabella(controller.ottieniProgetti());
					
					
					} catch (SQLException e1) 
					{
						JOptionPane.showMessageDialog(null, e1.getMessage());
					
					}
				}
				else {
					
					JOptionPane.showMessageDialog(null, "Selezionare una riga dalla tabella");
				}
			}
		});
		eliminaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				eliminaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				eliminaButton.setBackground(Color.WHITE);
			}
		});
		eliminaButton.setPreferredSize(new Dimension(90, 30));
		eliminaButton.setMaximumSize(new Dimension(150, 150));
		eliminaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaButton.setBackground(Color.WHITE);
		eliminaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaButton.setMargin(new Insets(2, 20, 2, 20));
		eliminaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		eliminaButton.setAlignmentX(0.5f);
		
		//Button "Inserisci partecipanti"
		JButton inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
		inserisciPartecipanteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				int row= progettoTable.getSelectedRow();
//				String nomeProgetto = nomeTextField.getText();
//				String tipoProgetto = tipologiaComboBox.getSelectedItem().toString();
//				String descrizioneProgetto = descrizioneTextArea.getText();
//				LocalDate scadenza = new LocalDate(Integer.valueOf(annoScadenzaComboBox.getSelectedItem().toString()), Integer.valueOf(meseScadenzaComboBox.getSelectedItem().toString()), Integer.valueOf(giornoScadenzaComboBox.getSelectedItem().toString()));	//data inizio
//				LocalDate creazione = new LocalDate(Integer.valueOf(annoScadenzaComboBox.getSelectedItem().toString()), Integer.valueOf(meseScadenzaComboBox.getSelectedItem().toString()), Integer.valueOf(giornoScadenzaComboBox.getSelectedItem().toString()));	//data inizio
//				LocalDate dataCreazione = (LocalDate) progettoTable.getValueAt(row, 6);
//				Progetto progettoSelezionato=new Progetto(nomeProgetto, tipoProgetto, descrizioneProgetto, dataCreazione, scadenza);
				
				int row=progettoTable.getSelectedRow();
				
				if(row==-1) {
					JOptionPane.showMessageDialog(null, "Seleziona un progetto dalla tabella");
					
				}
				else {
					
					int codiceProgetto = (int) progettoTable.getValueAt(row, 0);
					Progetto progettoSelezionato=dataModel.getProgettiTabella().get(row);
					controller.apriInserisciPartecipantiProgetto(progettoSelezionato, codiceProgetto);
				}
				
			}
		});
		inserisciPartecipanteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				inserisciPartecipanteButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				inserisciPartecipanteButton.setBackground(Color.WHITE);
			}
		});
		inserisciPartecipanteButton.setPreferredSize(new Dimension(190, 30));
		inserisciPartecipanteButton.setMaximumSize(new Dimension(150, 150));
		inserisciPartecipanteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inserisciPartecipanteButton.setBackground(Color.WHITE);
		inserisciPartecipanteButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		inserisciPartecipanteButton.setMargin(new Insets(2, 20, 2, 20));
		inserisciPartecipanteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		inserisciPartecipanteButton.setAlignmentX(0.5f);
		
		//Button "Conferma Modifiche"
		JButton confermaModificheButton = new JButton("Conferma Modifiche");

		confermaModificheButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				confermaModificheButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				confermaModificheButton.setBackground(Color.WHITE);
			}
		});
		confermaModificheButton.setPreferredSize(new Dimension(170, 30));
		confermaModificheButton.setMaximumSize(new Dimension(150, 150));
		confermaModificheButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confermaModificheButton.setBackground(Color.WHITE);
		confermaModificheButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		confermaModificheButton.setMargin(new Insets(2, 20, 2, 20));
		confermaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		confermaModificheButton.setAlignmentX(0.5f);
		
		//Button "Crea Nuovo"
		JButton creaNuovoButton = new JButton("Crea Nuovo");

		creaNuovoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaNuovoButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaNuovoButton.setBackground(Color.WHITE);
			}
		});
		creaNuovoButton.setPreferredSize(new Dimension(130, 30));
		creaNuovoButton.setMaximumSize(new Dimension(150, 150));
		creaNuovoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaNuovoButton.setBackground(Color.WHITE);
		creaNuovoButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaNuovoButton.setMargin(new Insets(2, 20, 2, 20));
		creaNuovoButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		creaNuovoButton.setAlignmentX(0.5f);
		comandiPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Label "Cerca"
		JLabel cercaLbel = new JLabel("Cerca");
		cercaLbel.setFont(new Font("Consolas", Font.PLAIN, 18));
		comandiPanel2.add(cercaLbel);
		
		//TextField per cercare progetti
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		comandiPanel2.add(cercaTextField);
		cercaTextField.setColumns(10);
		comandiPanel2.add(inserisciPartecipanteButton);
		comandiPanel2.add(confermaModificheButton);
		comandiPanel2.add(creaNuovoButton);
		comandiPanel2.add(eliminaButton);
		comandiPanel2.add(pulisciCampiButton);
		comandiPanel.add(comandiPanel2);
		
		//panel interno al panel info del progetto
		JPanel infoPanel2 = new JPanel();
		infoPanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		//Label "Nome"
		JLabel nomeProgettoLabel = new JLabel("Nome");
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//TextField nome progetto
		nomeTextField = new JTextField();
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setColumns(10);
		
		//ScrollPane meeting
		JScrollPane meetingScrollPane = new JScrollPane();
		meetingScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		//Label "Tipologia"
		JLabel tipologiaProgettoLabel = new JLabel("Tipologia");
		tipologiaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		tipologiaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Label "Scadenza"
		JLabel scadenzaProgettoLabel = new JLabel("Scadenza");
		scadenzaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		scadenzaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//ComboBox giorno scadenza
		giornoScadenzaComboBox = new JComboBox();
		giornoScadenzaComboBox.setUI(new BasicComboBoxUI());
		giornoScadenzaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoScadenzaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoScadenzaComboBox.setBackground(Color.WHITE);
		giornoScadenzaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoScadenzaComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoScadenzaComboBox.setSelectedIndex(0);
		giornoScadenzaComboBox.setBounds(244, 235, 47, 22);
		infoPanel2.add(giornoScadenzaComboBox);
		
		//ComboBox mese scadenza
		meseScadenzaComboBox = new JComboBox();
		meseScadenzaComboBox.setUI(new BasicComboBoxUI());
		meseScadenzaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meseScadenzaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseScadenzaComboBox.setBackground(Color.WHITE);
		meseScadenzaComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseScadenzaComboBox.setSelectedIndex(0);
		meseScadenzaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseScadenzaComboBox.setBounds(301, 235, 47, 22);
		infoPanel2.add(meseScadenzaComboBox);
		
		//ComboBox anno scadenza
		annoScadenzaComboBox = new JComboBox();
		annoScadenzaComboBox.setUI(new BasicComboBoxUI());
		annoScadenzaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoScadenzaComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoScadenzaComboBox.setBackground(Color.WHITE);
		annoScadenzaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoScadenzaComboBox.setBounds(358, 235, 62, 22);
		DefaultComboBoxModel annoScadenzaModel = new DefaultComboBoxModel();
		annoScadenzaComboBox.setModel(annoScadenzaModel);
		int annoAttuale = LocalDate.now().getYear();
		for(int i= annoAttuale;i<= annoAttuale + 20;i++)	//inserisce nella combobox gli anni da ora a 20 anni
			annoScadenzaModel.addElement(i);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		infoPanel2.add(annoScadenzaComboBox);
		
		//Label "Descrizione"
		JLabel descrizioneProgettoLabel = new JLabel("Descrizione");
		descrizioneProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		descrizioneProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//TextArea descrizione progetto
		descrizioneTextArea = new JTextArea();
		descrizioneTextArea.setWrapStyleWord(true);
		descrizioneTextArea.setLineWrap(true);
		descrizioneTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		descrizioneTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		//Label "Data Terminazione"
		JLabel dataTerminazioneLabel = new JLabel("Data Terminazione");
		dataTerminazioneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataTerminazioneLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//ComboBox giorno terminazione
		giornoTerminazioneComboBox = new JComboBox();	
		giornoTerminazioneComboBox.setEditable(true);
		giornoTerminazioneComboBox.setEnabled(false);
		giornoTerminazioneComboBox.setUI(new BasicComboBoxUI());
		giornoTerminazioneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoTerminazioneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		giornoTerminazioneComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));		
		giornoTerminazioneComboBox.setBackground(Color.WHITE);
		
		//ComboBox mese terminazione
		meseTerminazioneComboBox = new JComboBox();
		meseTerminazioneComboBox.setEditable(true);
		meseTerminazioneComboBox.setEnabled(false);
		meseTerminazioneComboBox.setUI(new BasicComboBoxUI());
		meseTerminazioneComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseTerminazioneComboBox.setSelectedIndex(0);
		meseTerminazioneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseTerminazioneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		meseTerminazioneComboBox.setBackground(Color.WHITE);
		
		//ComboBox anno terminazione
		annoTerminazioneComboBox = new JComboBox();
		annoTerminazioneComboBox.setEditable(true);
		annoTerminazioneComboBox.setEnabled(false);
		annoTerminazioneComboBox.setUI(new BasicComboBoxUI());
		DefaultComboBoxModel annoTerminazioneModel = new DefaultComboBoxModel();
		int anno = LocalDate.now().getYear();
		for(int i = 1900; i<=anno; i++ )	//inserisce gli anni dal 1900 a oggi
			annoTerminazioneModel.addElement(i);
		annoTerminazioneComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoTerminazioneComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		annoTerminazioneComboBox.setBackground(Color.WHITE);
		annoTerminazioneComboBox.setModel(annoTerminazioneModel);
		
		//ScrollPane per lista partecipanti
		JScrollPane partecipantiScrollPane = new JScrollPane();
		partecipantiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		//Label "Progetto Terminato"
		JLabel progettoTerminatoLabel = new JLabel("Progetto Terminato");
		progettoTerminatoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		progettoTerminatoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		//CheckBox progetto terminato o no
		progettoTerminatoCheckBox = new JCheckBox("");
		//quando si preme sul checkbox "progetto terminato" se viene selezionato allora i campi del giorno terminazione si attivano , mentre se non viene selezionato 
		//si disattivano
		//checkbox modificata
		progettoTerminatoCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//se il checkbox è selezionato
				if(progettoTerminatoCheckBox.isSelected())
					{
					//abilita i campi della data di terminazione
					giornoTerminazioneComboBox.setEnabled(true);
					meseTerminazioneComboBox.setEnabled(true);	
					annoTerminazioneComboBox.setEnabled(true);
					}
				
				//se il checkbox non è selezionato
				else 
					{	
					//disabilita i campi della data di terminazione
					giornoTerminazioneComboBox.setEnabled(false);
					meseTerminazioneComboBox.setEnabled(false);
					annoTerminazioneComboBox.setEnabled(false);
					}				
			}
		});
		
		progettoTerminatoCheckBox.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		JScrollPane ambitiScrollPane = new JScrollPane();
		ambitiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		//ComboBox per tipologia progetti
		try {
			tipologiaComboBox = new JComboBox(controller.ottieniTipologie());
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		tipologiaComboBox.setBackground(Color.WHITE);
		tipologiaComboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		tipologiaComboBox.setUI(new BasicComboBoxUI());
		
		GroupLayout gl_infoPanel2 = new GroupLayout(infoPanel2);
		gl_infoPanel2.setHorizontalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_infoPanel2.createSequentialGroup()
										.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_infoPanel2.createSequentialGroup()
												.addGap(93)
												.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_infoPanel2.createSequentialGroup()
												.addContainerGap()
												.addComponent(dataTerminazioneLabel, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_infoPanel2.createSequentialGroup()
												.addComponent(giornoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(meseScadenzaComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_infoPanel2.createSequentialGroup()
												.addComponent(giornoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(meseTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
											.addComponent(annoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
											.addComponent(annoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 65, Short.MAX_VALUE))
									.addGroup(gl_infoPanel2.createSequentialGroup()
										.addContainerGap()
										.addComponent(tipologiaProgettoLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(tipologiaComboBox, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
										.addGap(85)))
								.addGroup(Alignment.TRAILING, gl_infoPanel2.createSequentialGroup()
									.addGap(93)
									.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(descrizioneTextArea, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_infoPanel2.createSequentialGroup()
											.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(nomeTextField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)))
									.addGap(62)))
							.addComponent(ambitiScrollPane, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
							.addGap(14))
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(21)
							.addComponent(progettoTerminatoLabel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(progettoTerminatoCheckBox)
							.addGap(424)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(partecipantiScrollPane, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(meetingScrollPane, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
		);
		gl_infoPanel2.setVerticalGroup(
			gl_infoPanel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
									.addComponent(ambitiScrollPane, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_infoPanel2.createSequentialGroup()
										.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
											.addComponent(nomeTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
											.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
										.addGap(15)
										.addGroup(gl_infoPanel2.createParallelGroup(Alignment.LEADING)
											.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
											.addComponent(descrizioneTextArea, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
									.addComponent(meetingScrollPane, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
									.addComponent(partecipantiScrollPane, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())
						.addGroup(gl_infoPanel2.createSequentialGroup()
							.addGap(120)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(tipologiaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(progettoTerminatoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(progettoTerminatoCheckBox))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.TRAILING)
								.addComponent(meseTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
									.addComponent(giornoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addComponent(dataTerminazioneLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addComponent(annoTerminazioneComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_infoPanel2.createParallelGroup(Alignment.BASELINE)
								.addComponent(giornoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(meseScadenzaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(annoScadenzaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(33))))
		);
		
		//Label "Ambito/i"
		JLabel ambitoProgettoLabel = new JLabel("Ambito/i");
		ambitiScrollPane.setColumnHeaderView(ambitoProgettoLabel);
		ambitoProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		ambitoProgettoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		
		
		//List ambiti
		
		ambitiList=new JList<AmbitoProgetto>();
		ambitiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		DefaultListModel<AmbitoProgetto> ambitoModel = new DefaultListModel<AmbitoProgetto>();	//aggiorna la lista delle skill			
		ambitiList.setModel(ambitoModel);
		//ambitiList.setSelectedValue(anObject, shouldScroll);
			
		ambitiScrollPane.setViewportView(ambitiList);		
		
		try 
		{
			ambitoModel.addAll(controller.ottieniAmbiti());
			ambitiList.setModel(ambitoModel);
		}
		catch (SQLException e2) 
		{
			e2.printStackTrace();
		}
		
		//Label "Partecipanti"
		JLabel lblNewLabel = new JLabel("Partecipanti");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		partecipantiScrollPane.setColumnHeaderView(lblNewLabel);
		
		//List partecipanti
		DefaultListModel<Object>listaPartecipantiModel = new DefaultListModel<>();
		partecipantiList = new JList();
		partecipantiList.setFont(new Font("Consolas", Font.PLAIN, 12));
		partecipantiList.setModel(listaPartecipantiModel);
		partecipantiScrollPane.setViewportView(partecipantiList);
				
				
		
		//Label "Meeting Relativi"
		JLabel meetingRelativiLabel = new JLabel("Meeting Relativi");
		meetingRelativiLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingScrollPane.setColumnHeaderView(meetingRelativiLabel);
		infoPanel2.setLayout(gl_infoPanel2);
		infoPanel.add(infoPanel2);
		panel.setLayout(gl_panel);
		
		//List meeting relativi al progetto
		DefaultListModel<Object>listaMeetingRelativiModel = new DefaultListModel<>();
		MeetingListRenderer renderer = new MeetingListRenderer();
		meetingRelativiList = new JList();
		meetingRelativiList.setFixedCellHeight(100);
		meetingRelativiList.setModel(listaMeetingRelativiModel);
		meetingRelativiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingRelativiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingRelativiList.setCellRenderer(renderer);
		meetingScrollPane.setViewportView(meetingRelativiList);
		
		//CLICK SUL PULSANTE "CONFERMA MODIFICHE" 
		confermaModificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//QUANDO SI PREME SUL PULSANTE "CONFERMA MODIFICHE" VA A FARE UN UPDATE DI TUTTI I CAMPI SUL PROGETTO CHE HA COME CODICE QUELLO DELLA RIGA SELEZIONATA
				// NELLA JTABLE (colonna 0 della riga selezionata)
				//se uno dei campi obbligatori è vuoto non consente di fare l inserimento
				if(nomeTextField.getText().isBlank() || tipologiaComboBox.getSelectedItem()== null || ambitiList.isSelectionEmpty())
				{
										
					//fa diventare rossi i campi obbligatori che non sono stati inseriti
					if(nomeTextField.getText().isBlank())					
							nomeProgettoLabel.setForeground(Color.RED);
					else if(!nomeTextField.getText().isBlank())
						nomeProgettoLabel.setForeground(Color.BLACK);
					
					if(tipologiaComboBox.getSelectedItem()== null)
						tipologiaProgettoLabel.setForeground(Color.RED);
					else if(tipologiaComboBox.getSelectedItem()!= null)
						tipologiaProgettoLabel.setForeground(Color.BLACK);
					
					if(ambitiList.isSelectionEmpty())
						ambitoProgettoLabel.setForeground(Color.RED);
					else if(!ambitiList.isSelectionEmpty())
						ambitoProgettoLabel.setForeground(Color.BLACK);
					
					JOptionPane.showMessageDialog(null, "Controlla i campi inseriti");
				}
			else {
				
				int conferma = JOptionPane.showConfirmDialog(null, "Vuoi Confermare le modifiche effettuate?");
				//se l'utente conferma allora vengono effettivamente fatte le modifiche
				if(conferma == JOptionPane.YES_OPTION && progettoTable.getSelectedRow()!=-1)
				{
					int rigaSelezionata = progettoTable.getSelectedRow();
					
					//prende i campi dai singoli combo box e crea la data di scadenza e di terminazione
					LocalDate nuovaDataScadenza = new LocalDate((int)annoScadenzaComboBox.getSelectedItem(), meseScadenzaComboBox.getSelectedIndex()+1 , giornoScadenzaComboBox.getSelectedIndex()+1);
					LocalDate nuovaDataTerminazione = new LocalDate((int)annoTerminazioneComboBox.getSelectedItem(), meseTerminazioneComboBox.getSelectedIndex()+1 , giornoTerminazioneComboBox.getSelectedIndex()+1);
					ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
					
					//prende tutte le righe selezionate nella lista degli ambiti
					int[] selezionati = ambitiList.getSelectedIndices();
									
					//aggiunge alla lista di ambiti gli elementi nelle righe selezionate
					for(int i : selezionati)
					ambiti.add(ambitoModel.getElementAt(i));
					
					//Se il checkbox "Progetto terminato" NON è selezionato allora imposta la data di terminazione a null
					if(!progettoTerminatoCheckBox.isSelected())
						nuovaDataTerminazione = null;
					
					try 
					{
						//prende tutti i campi e fa l'update del progetto
						controller.updateProgetto((int)progettoTable.getValueAt(rigaSelezionata, 0), nomeTextField.getText(), (String)tipologiaComboBox.getSelectedItem(), descrizioneTextArea.getText(), (LocalDate)progettoTable.getValueAt(rigaSelezionata, 5), nuovaDataScadenza, nuovaDataTerminazione,ambiti);
						JOptionPane.showMessageDialog(null, "Modifiche effettuate correttamente");
						//aggiorna nuovamente la tabella con i dati aggiornati dei progetti
						dataModel.fireTableDataChanged();
						dataModel.setProgettiTabella(controller.ottieniProgetti());

					} catch (SQLException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Selezionare una riga dalla tabella");
			}
				
						
			}
		});
		
		//CLICK SUL PULSANTE "CREA NUOVO"
		creaNuovoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//se uno dei campi obbligatori è vuoto non consente di fare l inserimento
				if(nomeTextField.getText().isBlank() || tipologiaComboBox.getSelectedItem()== null || ambitiList.isSelectionEmpty())
				{
										
					//fa diventare rossi i campi obbligatori che non sono stati inseriti
					if(nomeTextField.getText().isBlank())					
							nomeProgettoLabel.setForeground(Color.RED);
					else if(!nomeTextField.getText().isBlank())
						nomeProgettoLabel.setForeground(Color.BLACK);
					
					if(tipologiaComboBox.getSelectedItem()== null)
						tipologiaProgettoLabel.setForeground(Color.RED);
					else if(tipologiaComboBox.getSelectedItem()!= null)
						tipologiaProgettoLabel.setForeground(Color.BLACK);
					
					if(ambitiList.isSelectionEmpty())
						ambitoProgettoLabel.setForeground(Color.RED);
					else if(!ambitiList.isSelectionEmpty())
						ambitoProgettoLabel.setForeground(Color.BLACK);
					
					JOptionPane.showMessageDialog(null, "Controlla i campi inseriti");
				
				}
				
				else {
				//chiede all utente la conferma della creazione
				int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler creare un nuovo progetto?");
				
				//se l'utente conferma di voler creare un nuovo progetto viene fatto l'insert
				if (conferma == JOptionPane.YES_OPTION)
					{
					LocalDate dataScadenza = new LocalDate((int)annoScadenzaComboBox.getSelectedItem(), meseScadenzaComboBox.getSelectedIndex()+1 , giornoScadenzaComboBox.getSelectedIndex()+1);
				
					ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
				
					//prende tutte le righe selezionate nella lista degli ambiti
					int[] selezionati = ambitiList.getSelectedIndices();
				
					//DA RIVEDERE
					//aggiunge alla lista di ambiti gli elementi nelle righe selezionate
					for(int i : selezionati)
					ambiti.add(ambitoModel.getElementAt(i));				 
				
					try 
						{
						controller.addProgetto(nomeTextField.getText(), (String)tipologiaComboBox.getSelectedItem(), descrizioneTextArea.getText(), LocalDate.now(), dataScadenza, ambiti);
						JOptionPane.showMessageDialog(null, "Progetto creato con successo");
						dataModel.fireTableDataChanged();
						dataModel.setProgettiTabella(controller.ottieniProgetti());
						} 
					catch (SQLException e1) 
						{
						
						JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					}
				}
		});
		
		//Click sul pulsante "Pulisci Campi"
		pulisciCampiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
				{
					//svuota i campi
					nomeTextField.setText("");
					descrizioneTextArea.setText("");
						
					progettoTerminatoCheckBox.setSelected(false);
					
					ambitiList.clearSelection();
					
					//ricava la data attuale
					LocalDate dataAttuale = LocalDate.now();
						
					annoTerminazioneComboBox.setSelectedItem(null);
					meseTerminazioneComboBox.setSelectedItem(null);
					giornoTerminazioneComboBox.setSelectedItem(null);
						
					//imposta di default giorno e mese come quelli della data attuale (-1 perche gli indici partono da 0)
					giornoScadenzaComboBox.setSelectedIndex(dataAttuale.getDayOfMonth() -1);
					meseScadenzaComboBox.setSelectedIndex(dataAttuale.getMonthOfYear() -1);				
						
					//imposta di default l'anno di scadenza come l'anno successivo 
					annoScadenzaComboBox.setSelectedIndex(1); 
						
					tipologiaComboBox.setSelectedItem(null);
					
					//svuota le liste di partecipanti e meeting
					listaPartecipantiModel.clear();
					listaMeetingRelativiModel.clear();
						
				}
		});
		
		//Label "Gestione Progetto"
		JLabel gestioneProgettoLabel = new JLabel("Gestione Progetto");
		gestioneProgettoLabel.setIcon(new ImageIcon(GestioneProgettiDipendente.class.getResource("/Icone/progetto_64.png")));
		gestioneProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(gestioneProgettoLabel)
					.addContainerGap(1208, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(gestioneProgettoLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		//Tabella progetti
		dataModel=new ProgettoTableModel();
		progettoTable = new JTable(dataModel);
		progettoTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		progettoTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettoTable.setBackground(Color.WHITE);
		progettoTable.setSelectionBackground(Color.LIGHT_GRAY);
		//Selezione riga
		progettoTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row= progettoTable.getSelectedRow();	//ottiene l'indice della riga selezionata
				
				//aggiorna i campi con le info del progetto corrispondente alla riga selezionata
				nomeTextField.setText(progettoTable.getValueAt(row, 1).toString());	//nome progetto
				descrizioneTextArea.setText(progettoTable.getValueAt(row, 2).toString());	//descrizione progetto
				LocalDate dataTerminazione=(LocalDate) progettoTable.getValueAt(row, 6);	//data terminazione
				if (dataTerminazione != null) {
				annoTerminazioneComboBox.setSelectedItem(dataTerminazione.getYear());
				meseTerminazioneComboBox.setSelectedIndex(dataTerminazione.getMonthOfYear()-1);
				giornoTerminazioneComboBox.setSelectedIndex(dataTerminazione.getDayOfMonth()-1);
				progettoTerminatoCheckBox.setSelected(true);
				}
				else {
					//pulisci i campi della data di terminazione
					annoTerminazioneComboBox.setSelectedItem(null);
					meseTerminazioneComboBox.setSelectedItem(null);
					giornoTerminazioneComboBox.setSelectedItem(null);
					progettoTerminatoCheckBox.setSelected(false);
				}
				LocalDate dataScadenza=(LocalDate) progettoTable.getValueAt(row, 7);	//data scadenza
				if (dataScadenza != null) {
				annoScadenzaComboBox.setSelectedItem(dataScadenza.getYear());
				meseScadenzaComboBox.setSelectedIndex(dataScadenza.getMonthOfYear()-1);
				giornoScadenzaComboBox.setSelectedIndex(dataScadenza.getDayOfMonth()-1);
				}
				else {
					//pulisci i campi della data di scadenza
					annoScadenzaComboBox.setSelectedItem(null);
					meseScadenzaComboBox.setSelectedItem(null);
					giornoScadenzaComboBox.setSelectedItem(null);
				}
								
				//RICAVA GLI AMBITI DEL PROGETTO E LI SELEZIONA NELLA LISTA
				ArrayList<AmbitoProgetto> ambiti = new ArrayList<AmbitoProgetto>();
				
				try 
				{
					//prende gli ambiti del progetto che ha come codice la colonna 0 della riga selezionata
					ambiti = controller.getAmbitiProgettoByCod((int)progettoTable.getValueAt(row, 0));					
				} 
				catch (SQLException e2) 
				{
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
					ArrayList<Integer> selezionati = new ArrayList<Integer>();
				    
					//scorre tutti gli ambiti presenti nella lista degli ambiti e li confronta con ogni ambito del progetto per ottenere gli indici degli ambiti da selezionare
					for(int i = 0 ;i < ambitoModel.size() ; i++) 
					{
						for(int j = 0 ; j< ambiti.size() ; j++) 
						{
							if(ambiti.get(j).getNome().equals(ambitoModel.getElementAt(i).getNome())) //confonta i nomi degli ambiti
							{								
								selezionati.add(i);	 										//aggiunge tutti gli indici ottenuti in una lista
							}
						}	
					}
						
					//(preso da stack overflow) converte l'arraylist di integer in int[]
					int[] array = selezionati.stream().mapToInt(i -> i).toArray();
					
								
					ambitiList.setSelectedIndices(array); //seleziona gli indici recuperati nella lista degli ambiti
					
				//tipologia progetto

				try {
					tipologiaComboBox.setModel(new DefaultComboBoxModel(controller.ottieniTipologie()));
					tipologiaComboBox.setSelectedItem(progettoTable.getValueAt(row, 4));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				//AGGIUNGE LA LISTA DEI PERTECIPANTI E DEI PARTECIPANTI E DEI MEETING 
				
				int riga = progettoTable.getSelectedRow(); //prende la riga attualmente selezionata della tabella dei progetti
				
				//dalla lista di progetti presenti nella tabella ne prende quello in posizione della riga selezionata (ES: se si seleziona la prima riga prende il progetto in posizione 0 dell arraylist)
				int codProgettoSelezionato = (int) progettoTable.getValueAt(row, 0);
				
				//ripulisce le liste ogni volta che si preme su una riga della tabella , in modo che non vengano aggiunti elementi ad ogni click
				listaPartecipantiModel.clear();
				listaMeetingRelativiModel.clear();
				
				
					//ottiene i partecipanti al progetto selezionato
					Progetto partecipanti =dataModel.getProgettiTabella().get(row);
					
					
					//ottiene i meeting relativi al progetto selezionato
//					ArrayList<Meeting> meetingRelativi = controller.getMeetingRelativiProgetto(codProgettoSelezionato); //Da eliminare
					Progetto progetto=dataModel.getProgettiTabella().get(row);
					
					//aggiunge meeting e partecipanti alle rispettive liste
					listaMeetingRelativiModel.addAll(progetto.getDiscusso()); //Riempe la lista con i meeting relativi al progetto
					listaPartecipantiModel.addAll(partecipanti.getCollaborazioni());  //Riempe la lista con i partecipanti al progetto
				
			}	
		});
		//inserisce i progetti nella tabella
		try {
			dataModel.setProgettiTabella(controller.ottieniProgetti());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		tabellaScrollPane.setViewportView(progettoTable);
	}
}
