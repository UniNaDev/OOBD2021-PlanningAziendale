/*Gui per la gestione delle sale.
 *Qui è possibile creare nuove sale,
 *visualizzare le informazioni delle sale create
 *e modificarle.
 ***********************************************/

package gui.segreteria;

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

import entita.SalaRiunione;
import gui.ErroreDialog;
import gui.customUI.CustomScrollBarUI;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.event.ListSelectionListener;

import controller.segreteria.ControllerMeetingSegreteria;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;

public class GestioneSaleSegreteria extends JFrame {
	private JPanel contentPane;
	private JLabel titoloLabel;
	private JLabel nomeSalaLabel;
	private JTextField nomeSalaTextField;
	private JLabel capienzaLabel;
	private JTextField capienzaTextField;
	private JLabel indirizzoLabel;
	private JTextArea indirizzoTextArea;
	private JLabel pianoLabel;
	private JTextField pianoTextField;
	private JButton esciButton;
	private JButton eliminaSalaButton;
	private JLabel resetCampiLabel;
	private JSeparator separator;
	private JLabel saleLabel;
	private JScrollPane saleListScrollPanel;
	private DefaultListModel<SalaRiunione> modelloListaSale;
	private JList<SalaRiunione> saleList;
	private JButton salvaModificheButton;

	private JButton creaSalaButton;

	private SalaRiunione salaSelezionata;
	private String codSala, indirizzo;
	private int capienza, piano;

	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	private final String VIOLAZIONE_VINCOLI_TABELLA = "23514";
	private final String VIOLAZIONE_LUNGHEZZA_STRINGA = "22001";
	private final String VIOLAZIONE_CAPIENZA_SALA = "P0002";

	public GestioneSaleSegreteria(ControllerMeetingSegreteria controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneSaleSegreteria.class.getResource("/icone/WindowIcon_16.png")));
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
		setLocationRelativeTo(null);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		infoPanel.setBounds(25, 85, 304, 219);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		titoloLabel = new JLabel("Gestione Sale");
		titoloLabel.setIcon(new ImageIcon(GestioneSaleSegreteria.class.getResource("/icone/meeting_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(10, 11, 234, 63);
		contentPane.add(titoloLabel);

		nomeSalaLabel = new JLabel("Sala");
		nomeSalaLabel.setBounds(10, 29, 28, 16);
		nomeSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		infoPanel.add(nomeSalaLabel);

		nomeSalaTextField = new JTextField();
		nomeSalaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeSalaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeSalaTextField.setBounds(48, 27, 129, 20);
		infoPanel.add(nomeSalaTextField);
		nomeSalaTextField.setColumns(10);

		capienzaLabel = new JLabel("Capienza");
		capienzaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		capienzaLabel.setBounds(10, 78, 56, 16);
		infoPanel.add(capienzaLabel);

		capienzaTextField = new JTextField();
		capienzaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		capienzaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		capienzaTextField.setColumns(10);
		capienzaTextField.setBounds(70, 76, 56, 20);
		infoPanel.add(capienzaTextField);

		indirizzoLabel = new JLabel("Indirizzo");
		indirizzoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		indirizzoLabel.setBounds(10, 125, 63, 16);
		infoPanel.add(indirizzoLabel);

		indirizzoTextArea = new JTextArea();
		indirizzoTextArea.setLineWrap(true);
		indirizzoTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		indirizzoTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		indirizzoTextArea.setColumns(10);
		indirizzoTextArea.setBounds(80, 123, 187, 34);
		infoPanel.add(indirizzoTextArea);

		pianoLabel = new JLabel("Piano");
		pianoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		pianoLabel.setBounds(10, 170, 35, 16);
		infoPanel.add(pianoLabel);

		pianoTextField = new JTextField();
		pianoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pianoTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		pianoTextField.setColumns(10);
		pianoTextField.setBounds(48, 168, 35, 20);
		infoPanel.add(pianoTextField);

		resetCampiLabel = new JLabel("");
		resetCampiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pulisciCampi();
				saleList.setSelectedValue(null, false);
			}
		});
		resetCampiLabel.setIcon(new ImageIcon(GestioneSaleSegreteria.class.getResource("/icone/refresh.png")));
		resetCampiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetCampiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		resetCampiLabel.setBounds(268, 11, 16, 16);
		infoPanel.add(resetCampiLabel);

		eliminaSalaButton = new JButton("Elimina");
		eliminaSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				if (salaSelezionata != null)
					eliminaSala(controller);
				else
					JOptionPane.showMessageDialog(null, 
							"Per favore selezionare prima una sala dalla lista.",
							"Selezionare Sala",
							JOptionPane.INFORMATION_MESSAGE);
			}
		});
		eliminaSalaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				eliminaSalaButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				eliminaSalaButton.setBackground(Color.WHITE);
			}
		});
		eliminaSalaButton.setBounds(206, 185, 88, 23);
		eliminaSalaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaSalaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaSalaButton.setBackground(Color.WHITE);
		eliminaSalaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		infoPanel.add(eliminaSalaButton);

		saleListScrollPanel = new JScrollPane();
		saleListScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		saleListScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		saleListScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		saleListScrollPanel.setBounds(410, 85, 225, 219);
		contentPane.add(saleListScrollPanel);

		modelloListaSale = new DefaultListModel();
		saleList = new JList();
		saleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		saleList.setFont(new Font("Consolas", Font.PLAIN, 13));
		saleList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setModelloListaSaleTutte(controller);
		saleListScrollPanel.setViewportView(saleList);
		saleList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				capienzaLabel.setForeground(Color.BLACK);
				indirizzoLabel.setForeground(Color.BLACK);
				pianoLabel.setForeground(Color.BLACK);
				
				salaSelezionata = (SalaRiunione) saleList.getSelectedValue();
				if (salaSelezionata != null) {
					nomeSalaTextField.setText(salaSelezionata.getCodiceSala());
					capienzaTextField.setText(Integer.toString(salaSelezionata.getCapienza()));
					indirizzoTextArea.setText(salaSelezionata.getIndirizzoSede());
					pianoTextField.setText(Integer.toString(salaSelezionata.getPiano()));
				} else 
					pulisciCampi();
			}
		});

		saleLabel = new JLabel("Sale");
		saleLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		saleListScrollPanel.setColumnHeaderView(saleLabel);

		separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(370, 85, 2, 219);
		contentPane.add(separator);

		creaSalaButton = new JButton("Crea");
		creaSalaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		creaSalaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaSalaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaSalaButton.setBackground(Color.WHITE);
		creaSalaButton.setBounds(560, 315, 77, 23);
		creaSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				capienzaLabel.setForeground(Color.BLACK);
				indirizzoLabel.setForeground(Color.BLACK);
				pianoLabel.setForeground(Color.BLACK);
				
				if (!nomeSalaTextField.getText().isBlank() && !capienzaTextField.getText().isBlank()
						&& !indirizzoTextArea.getText().isBlank() && !pianoTextField.getText().isBlank())
					creaSala(controller);
				else
					campiObbligatoriVuoti();
			}
		});
		creaSalaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				creaSalaButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				creaSalaButton.setBackground(Color.WHITE);
			}
		});
		contentPane.add(creaSalaButton);

		salvaModificheButton = new JButton("Salva Modifiche");
		salvaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		salvaModificheButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvaModificheButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salvaModificheButton.setBackground(Color.WHITE);
		salvaModificheButton.setBounds(410, 315, 139, 23);
		salvaModificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				capienzaLabel.setForeground(Color.BLACK);
				indirizzoLabel.setForeground(Color.BLACK);
				pianoLabel.setForeground(Color.BLACK);
				
				if (!nomeSalaTextField.getText().isBlank() && !capienzaTextField.getText().isBlank()
						&& !indirizzoTextArea.getText().isBlank() && !pianoTextField.getText().isBlank()) {
					if (salaSelezionata != null)
							aggiornaSala(controller);
					else
						JOptionPane.showMessageDialog(null, 
								"Per favore selezionare prima una sala dalla lista.",
								"Salvataggio Fallito",
								JOptionPane.INFORMATION_MESSAGE);
				} 
				else
					campiObbligatoriVuoti();
			}
		});
		salvaModificheButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				salvaModificheButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				salvaModificheButton.setBackground(Color.WHITE);
			}
		});
		contentPane.add(salvaModificheButton);

		esciButton = new JButton("Esci");
		esciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chiudiGestioneSale();
			}
		});
		esciButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		esciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		esciButton.setBackground(Color.WHITE);
		esciButton.setBounds(25, 315, 71, 23);
		esciButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				esciButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				esciButton.setBackground(Color.WHITE);
			}
		});
		contentPane.add(esciButton);
	}

	// Altri metodi
	// -----------------------------------------------------------------
	private void creaSala(ControllerMeetingSegreteria controller) {
		try {
			ricavaInfoSala();
			SalaRiunione salaNuova = new SalaRiunione(codSala, capienza, indirizzo, piano);
			try {
				controller.creaSala(salaNuova);
				JOptionPane.showMessageDialog(null, 
						"Sala creata con successo.",
						"Creazione Riuscita",
						JOptionPane.INFORMATION_MESSAGE);
				modelloListaSale.addElement(salaNuova);
				pulisciCampi();
			} catch (SQLException e) {
				ErroreDialog errore = null;
				switch (e.getSQLState()) {
				case VIOLAZIONE_PKEY_UNIQUE:
					errore = new ErroreDialog(e, 
							"Creazione Fallita", 
							"Non possono esistere sale duplicate.\n"
							+ "Controllare il codice della sala non esista già.", false);
					nomeSalaLabel.setForeground(Color.RED);
					break;
				case VIOLAZIONE_LUNGHEZZA_STRINGA:
					errore = new ErroreDialog(e,
							"Creazione Fallita",
							"Alcuni valori sono troppo lunghi.\n"
							+ "Controllare che il codice della sala non superi i 10 caratteri\n"
							+ "e che il suo indirizzo non superi i 50 caratteri.", false);
					if (nomeSalaTextField.getText().length() > 10)
						nomeSalaLabel.setForeground(Color.RED);
					if (indirizzoTextArea.getText().length() > 50)
						indirizzoLabel.setForeground(Color.RED);
					break;
				case VIOLAZIONE_VINCOLI_TABELLA:
					errore = new ErroreDialog(e, 
							"Creazione Fallita", 
							"Alcuni valori sono errati.\n"
							+ "Controllare che piano e capienza non siano valori negativi.", false);
					break;
				default:
					errore = new ErroreDialog(e, true);
				}
				errore.setVisible(true);
			}
		} catch (NumberFormatException nfe) {
			ErroreDialog errore = new ErroreDialog(nfe, 
					"Creazione Fallita", 
					"Controllare che i valori di capienza e piano siano numerici e non troppo grandi.", false);
			errore.setVisible(true);
		}
	}
	
	private void aggiornaSala(ControllerMeetingSegreteria controller) {
		try {
			ricavaInfoSala();
			salaSelezionata.setCapienza(capienza);
			salaSelezionata.setIndirizzoSede(indirizzo);
			salaSelezionata.setPiano(piano);
			try {
				controller.aggiornaSala(salaSelezionata, codSala);
				JOptionPane.showMessageDialog(null,
						"Sala modificata con successo.", 
						"Salvataggio Riuscito",
						JOptionPane.INFORMATION_MESSAGE);
				setModelloListaSaleTutte(controller);
				controller.aggiornaSaleFiltro();
			} catch (SQLException e) {
				ErroreDialog errore = null;
				switch (e.getSQLState()) {
				case VIOLAZIONE_LUNGHEZZA_STRINGA:
					errore = new ErroreDialog(e, 
							"Salvataggio Fallito", 
							"Alcuni valori sono troppo lunghi.\n"
							+ "Controllare che il codice della sala non superi i 10 caratteri\n"
							+ "e che il suo indirizzo non superi i 50 caratteri.", false);
					if (nomeSalaTextField.getText().length() > 10)
						nomeSalaLabel.setForeground(Color.RED);
					if (indirizzoTextArea.getText().length() > 50)
						indirizzoLabel.setForeground(Color.RED);
					break;
				case VIOLAZIONE_VINCOLI_TABELLA:
					errore = new ErroreDialog(e, 
							"Salvataggio Fallito", 
							"Alcuni valori sono errati.\n"
							+ "Controllare che piano e capienza non siano valori negativi.", false);
					if (Integer.parseInt(capienzaTextField.getText()) <= 0)
						capienzaLabel.setForeground(Color.RED);
					if (Integer.parseInt(pianoTextField.getText()) < 0)
						pianoLabel.setForeground(Color.RED);
					break;
				case VIOLAZIONE_CAPIENZA_SALA:
					errore = new ErroreDialog(e, 
							"Salvataggio Fallito", 
							"Alcuni meeting ospitati da questa sala non rispetterebbero più la sua capienza.\n"
							+ "Si consiglia di modificare prima i meeting e le relative presenze.", false);
					break;
				default:
					errore = new ErroreDialog(e, true);
				}
				errore.setVisible(true);
			}
		} catch (NumberFormatException nfe) {
			ErroreDialog errore = new ErroreDialog(nfe,
					"Salvataggio Fallito",
					"Controllare che i valori di capienza e piano siano numerici e non troppo grandi.", false);
			errore.setVisible(true);
		}
	}

	private void eliminaSala(ControllerMeetingSegreteria controller) {
		try{
			controller.eliminaSala(salaSelezionata);
			JOptionPane.showMessageDialog(null,
					"Sala eliminata con successo.", 
					"Eliminazione Riuscita",
					JOptionPane.INFORMATION_MESSAGE);
			pulisciCampi();
			setModelloListaSaleTutte(controller);
			if (modelloListaSale.isEmpty())
				saleList.setSelectedValue(null, false);
			else
				saleList.setSelectedIndex(0);
		} catch(SQLException e) {
			ErroreDialog errore = new ErroreDialog(e, true);
			errore.setVisible(true);
		}
	}
	
	private void ricavaInfoSala() throws NumberFormatException{
		codSala = nomeSalaTextField.getText();
		capienza = Integer.parseInt(capienzaTextField.getText());
		indirizzo = indirizzoTextArea.getText();
		piano = Integer.parseInt(pianoTextField.getText());
	}
	
	private void pulisciCampi() {
		nomeSalaTextField.setText("");
		capienzaTextField.setText("");
		indirizzoTextArea.setText("");
		pianoTextField.setText("");
		nomeSalaLabel.setForeground(Color.BLACK);
		capienzaLabel.setForeground(Color.BLACK);
		indirizzoLabel.setForeground(Color.BLACK);
		pianoLabel.setForeground(Color.BLACK);
		saleList.clearSelection();
	}

	private void campiObbligatoriVuoti() {
		if (nomeSalaTextField.getText().isBlank())
			nomeSalaLabel.setForeground(Color.RED);
		if (capienzaTextField.getText().isBlank())
			capienzaLabel.setForeground(Color.RED);
		if (indirizzoTextArea.getText().isBlank())
			indirizzoLabel.setForeground(Color.RED);
		if (pianoTextField.getText().isBlank())
			pianoLabel.setForeground(Color.RED);
		
		JOptionPane.showMessageDialog(null, 
				"Impossibile eseguire l'operazione\nperchè alcuni campi obbligatori sono vuoti.", 
				"Campi Obbligatori Vuoti",
				JOptionPane.ERROR_MESSAGE);
	}

	private void setModelloListaSaleTutte(ControllerMeetingSegreteria controller) {
		modelloListaSale.clear();
		try {
			modelloListaSale.addAll(controller.ottieniSale());
		} catch (SQLException e) {
			ErroreDialog erroreFatale = new ErroreDialog(e, true);
			erroreFatale.setVisible(true);
			modelloListaSale.clear();
		}
		saleList.setModel(modelloListaSale);
	}
}