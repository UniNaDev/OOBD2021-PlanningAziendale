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
import eccezioni.ManagerEccezioniDatiSQL;
import eccezioni.ManagerEccezioniDatiSQLSala;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class GestioneSale extends JFrame {
	private JPanel contentPane;
	private JTextField nomeSalaTextField;
	private JTextField capienzaTextField;
	private JTextArea indirizzoTextArea;
	private JTextField pianoTextField;
	private DefaultListModel saleListModel;
	private JList saleList;
	
	private ManagerEccezioniDatiSQL eccezioniDatiSala;
	
	private final String VIOLAZIONE_NOT_NULL = "23502";
	private final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	private final String VIOLAZIONE_VINCOLI_TABELLA = "23514";
	private final String VIOLAZIONE_LUNGHEZZA_STRINGA = "22001";
	
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
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		infoPanel.setBounds(25, 85, 304, 219);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		JLabel nomeSalaLabel = new JLabel("Sala");
		nomeSalaLabel.setBounds(10, 29, 28, 16);
		nomeSalaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		infoPanel.add(nomeSalaLabel);
		
		nomeSalaTextField = new JTextField();
		nomeSalaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeSalaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		nomeSalaTextField.setBounds(48, 27, 129, 20);
		infoPanel.add(nomeSalaTextField);
		nomeSalaTextField.setColumns(10);
		
		JLabel capienzaLabel = new JLabel("Capienza");
		capienzaLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		capienzaLabel.setBounds(10, 78, 56, 16);
		infoPanel.add(capienzaLabel);
		
		capienzaTextField = new JTextField();
		capienzaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		capienzaTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		capienzaTextField.setColumns(10);
		capienzaTextField.setBounds(70, 76, 56, 20);
		infoPanel.add(capienzaTextField);
		
		JLabel indirizzoLabel = new JLabel("Indirizzo");
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
		
		JLabel pianoLabel = new JLabel("Piano");
		pianoLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		pianoLabel.setBounds(10, 170, 35, 16);
		infoPanel.add(pianoLabel);
		
		pianoTextField = new JTextField();
		pianoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pianoTextField.setFont(new Font("Consolas", Font.PLAIN, 13));
		pianoTextField.setColumns(10);
		pianoTextField.setBounds(48, 168, 35, 20);
		infoPanel.add(pianoTextField);
		
		JLabel resetCampiLabel = new JLabel("");
		resetCampiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pulisciCampi();
				saleList.setSelectedValue(null, false);
			}
			});
		resetCampiLabel.setIcon(new ImageIcon(GestioneSale.class.getResource("/icone/refresh.png")));
		resetCampiLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		resetCampiLabel.setBounds(268, 11, 16, 16);
		infoPanel.add(resetCampiLabel);
		
		JButton eliminaSalaButton = new JButton("Elimina");
		eliminaSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				if (!nomeSalaTextField.getText().isBlank()) {
					try {
						controller.eliminaSala(nomeSalaTextField.getText());
						pulisciCampi();
						saleListModel.clear();
						saleListModel.addAll(controller.ottieniSale());
						saleList.setModel(saleListModel);
						if (saleListModel.isEmpty())
							saleList.setSelectedValue(null, rootPaneCheckingEnabled);
						else
							saleList.setSelectedIndex(0);
					} catch (SQLException e1) {
						//TODO: aggiungere controlli più dettagliati
						JOptionPane.showMessageDialog(null,
							"Impossibile ottenere sale dal database.\nControllare che sia stabilita la connessione al database\noppure creare prima una sala.",
							"Errore Interrogazione Database",
							JOptionPane.ERROR_MESSAGE);
						nomeSalaLabel.setForeground(Color.RED);
					}
				}
				else {
					nomeSalaLabel.setForeground(Color.RED);
				}
			}
		});
		eliminaSalaButton.setBounds(206, 185, 88, 23);
		eliminaSalaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		eliminaSalaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		eliminaSalaButton.setBackground(Color.WHITE);
		infoPanel.add(eliminaSalaButton);
		eliminaSalaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				eliminaSalaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				eliminaSalaButton.setBackground(Color.WHITE);
			}	
		});
		eliminaSalaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		JScrollPane saleListScrollPanel = new JScrollPane();
		saleListScrollPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		saleListScrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		saleListScrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		saleListScrollPanel.setBounds(410, 85, 225, 219);
		contentPane.add(saleListScrollPanel);
		
		saleListModel = new DefaultListModel();
		saleList = new JList();
		saleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		saleList.setFont(new Font("Consolas", Font.PLAIN, 13));
		try {
			saleListModel.addAll(controller.ottieniSale());
			saleList.setModel(saleListModel);
			saleListScrollPanel.setViewportView(saleList);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,
					"Impossibile ottenere sale dal database.\nControllare che sia stabilita la connessione al database.",
					"Errore Interrogazione Database",
					JOptionPane.ERROR_MESSAGE);
		}
		saleList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				SalaRiunione salaSelezionata = (SalaRiunione) saleList.getSelectedValue();
				try {
					nomeSalaTextField.setText(salaSelezionata.getCodiceSala());
					capienzaTextField.setText(Integer.toString(salaSelezionata.getCapienza()));
					indirizzoTextArea.setText(salaSelezionata.getIndirizzoSede());
					pianoTextField.setText(Integer.toString(salaSelezionata.getPiano()));
				}
				catch(NullPointerException npe) {
					pulisciCampi();
				}
			}
		});
		
		JLabel saleLabel = new JLabel("Sale");
		saleLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		saleListScrollPanel.setColumnHeaderView(saleLabel);
		
		JLabel titoloLabel = new JLabel("Gestione Sale");
		titoloLabel.setIcon(new ImageIcon(GestioneSale.class.getResource("/icone/meeting_64.png")));
		titoloLabel.setFont(new Font("Consolas", Font.PLAIN, 21));
		titoloLabel.setBounds(10, 11, 234, 63);
		contentPane.add(titoloLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(Color.LIGHT_GRAY));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(370, 85, 2, 219);
		contentPane.add(separator);
		
		JButton creaSalaButton = new JButton("Crea");
		creaSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeSalaLabel.setForeground(Color.BLACK);
				capienzaLabel.setForeground(Color.BLACK);
				indirizzoLabel.setForeground(Color.BLACK);
				pianoLabel.setForeground(Color.BLACK);
				if (!nomeSalaTextField.getText().isBlank() && !capienzaTextField.getText().isBlank() && !indirizzoTextArea.getText().isBlank() && !pianoTextField.getText().isBlank()) {
					try {
						SalaRiunione salaNuova = new SalaRiunione(nomeSalaTextField.getText(), Integer.parseInt(capienzaTextField.getText()), indirizzoTextArea.getText(), Integer.parseInt(pianoTextField.getText()));
						try {
							controller.creaSala(salaNuova);
							saleListModel.addElement(salaNuova);
							pulisciCampi();
						} catch (SQLException e1) {
							eccezioniDatiSala = new ManagerEccezioniDatiSQLSala(e1);
							eccezioniDatiSala.mostraErrore();
						//TODO: gestione gui in base all'errore	
						
//							if (e1.getSQLState().equals(VIOLAZIONE_PKEY_UNIQUE)) {
//								JOptionPane.showMessageDialog(null,
//										"La sala di codice " + nomeSalaTextField.getText() + " esiste già.",
//										"Errore Sala Esistente",
//										JOptionPane.ERROR_MESSAGE);
//								nomeSalaLabel.setForeground(Color.RED);
//							}
//							else if (e1.getSQLState().equals(VIOLAZIONE_NOT_NULL)) {
//								JOptionPane.showMessageDialog(null,
//										"Alcuni campi obbligatori per la creazione sono vuoti.",
//										"Errore Campi Obbligatori Vuoti",
//										JOptionPane.ERROR_MESSAGE);
//								if (nomeSalaTextField.getText() == null)
//									nomeSalaLabel.setForeground(Color.RED);
//								if (capienzaTextField.getText() == null)
//									capienzaLabel.setForeground(Color.RED);
//								if (indirizzoTextArea.getText() == null)
//									indirizzoLabel.setForeground(Color.RED);
//								if (pianoTextField.getText() == null)
//									pianoLabel.setForeground(Color.RED);
//							}
//							else if(e1.getSQLState().equals(VIOLAZIONE_VINCOLI_TABELLA)) {
//								JOptionPane.showMessageDialog(null,
//										"I valori inseriti sono errati.\nControlla che piano e capienza non siano valori negativi.",
//										"Errore Vincoli",
//										JOptionPane.ERROR_MESSAGE);
//								if (Integer.parseInt(capienzaTextField.getText()) < 0)
//									capienzaLabel.setForeground(Color.RED);
//								if (Integer.parseInt(pianoTextField.getText()) < 0)
//									pianoLabel.setForeground(Color.RED);
//							}
//							else if(e1.getSQLState().equals(VIOLAZIONE_LUNGHEZZA_STRINGA)) {
//								JOptionPane.showMessageDialog(null,
//										"I valori testuali inseriti sono troppo lunghi.\nControlla che il nome della sala non superi i 10 caratteri e che il suo indirizzo non superi i 50 caratteri.",
//										"Errore Dati Inseriti",
//										JOptionPane.ERROR_MESSAGE);
//								if (nomeSalaTextField.getText().length() > 10)
//									nomeSalaLabel.setForeground(Color.RED);
//								if (indirizzoTextArea.getText().length() > 50)
//									indirizzoLabel.setForeground(Color.RED);
//							}
//							else {
//								JOptionPane.showMessageDialog(null,
//										e1.getMessage() + "\nContattare uno sviluppatore.",
//										"Errore #" + e1.getErrorCode(),
//										JOptionPane.ERROR_MESSAGE);
//							}
						}
					}catch(NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null,
								"I valori di capienza e/o piano sono errati.\nControlla che siano valori numerici.",
								"Errore Dati Inseriti",
								JOptionPane.ERROR_MESSAGE);
						capienzaLabel.setForeground(Color.RED);
						pianoLabel.setForeground(Color.RED);
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
							"Errore Campi Obbligatori Vuoti",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		creaSalaButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		creaSalaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		creaSalaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creaSalaButton.setBackground(Color.WHITE);
		creaSalaButton.setBounds(560, 315, 77, 23);
		creaSalaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				creaSalaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				creaSalaButton.setBackground(Color.WHITE);
			}	
		});
		contentPane.add(creaSalaButton);
		
		JButton salvaModificheButton = new JButton("Salva Modifiche");
		salvaModificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!nomeSalaTextField.getText().isBlank() && !capienzaTextField.getText().isBlank() && !indirizzoTextArea.getText().isBlank() && !pianoTextField.getText().isBlank()) {
					try {
						controller.aggiornaSala(nomeSalaTextField.getText(), Integer.parseInt(capienzaTextField.getText()), indirizzoTextArea.getText(), Integer.parseInt(pianoTextField.getText()));
						saleListModel.removeAllElements();
						saleListModel.addAll(controller.ottieniSale());
						saleList.setModel(saleListModel);
					} catch (SQLException e1) {
						if (e1.getSQLState().equals(VIOLAZIONE_NOT_NULL)) {
							JOptionPane.showMessageDialog(null,
									"Alcuni campi obbligatori per la creazione sono vuoti.",
									"Errore Campi Obbligatori Vuoti",
									JOptionPane.ERROR_MESSAGE);
							if (nomeSalaTextField.getText() == null)
								nomeSalaLabel.setForeground(Color.RED);
							if (capienzaTextField.getText() == null)
								capienzaLabel.setForeground(Color.RED);
							if (indirizzoTextArea.getText() == null)
								indirizzoLabel.setForeground(Color.RED);
							if (pianoTextField.getText() == null)
								pianoLabel.setForeground(Color.RED);
						}
						else if(e1.getSQLState().equals(VIOLAZIONE_VINCOLI_TABELLA)) {
							JOptionPane.showMessageDialog(null,
									"I valori inseriti sono errati.\nControlla che piano e capienza non siano valori negativi.",
									"Errore Vincoli",
									JOptionPane.ERROR_MESSAGE);
							if (Integer.parseInt(capienzaTextField.getText()) < 0)
								capienzaLabel.setForeground(Color.RED);
							if (Integer.parseInt(pianoTextField.getText()) < 0)
								pianoLabel.setForeground(Color.RED);
						}
						else if(e1.getSQLState().equals(VIOLAZIONE_LUNGHEZZA_STRINGA)) {
							JOptionPane.showMessageDialog(null,
									"I valori testuali inseriti sono troppo lunghi.\nControlla che il nome della sala non superi i 10 caratteri e che il suo indirizzo non superi i 50 caratteri.",
									"Errore Dati Inseriti",
									JOptionPane.ERROR_MESSAGE);
							if (nomeSalaTextField.getText().length() > 10)
								nomeSalaLabel.setForeground(Color.RED);
							if (indirizzoTextArea.getText().length() > 50)
								indirizzoLabel.setForeground(Color.RED);
						}
						else {
							JOptionPane.showMessageDialog(null,
									e1.getMessage() + "\nContatta uno sviluppatore.",
									"Errore #" + e1.getErrorCode(),
									JOptionPane.ERROR_MESSAGE);
						}
					} 
					catch(NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null,
								"I valori di capienza e/o piano sono errati.\nControlla che siano valori numerici.",
								"Errore Dati Inseriti",
								JOptionPane.ERROR_MESSAGE);
						capienzaLabel.setForeground(Color.RED);
						pianoLabel.setForeground(Color.RED);
					}
				}else{
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
							"Errore Campi Obbligatori Vuoti",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		salvaModificheButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		salvaModificheButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvaModificheButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salvaModificheButton.setBackground(Color.WHITE);
		salvaModificheButton.setBounds(410, 315, 139, 23);
		salvaModificheButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				salvaModificheButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				salvaModificheButton.setBackground(Color.WHITE);
			}	
		});
		contentPane.add(salvaModificheButton);
		
		JButton esciButton = new JButton("Esci");
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
			public void mouseEntered(MouseEvent e) 
			{
				esciButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				esciButton.setBackground(Color.WHITE);
			}	
		});
		contentPane.add(esciButton);
	}
	
	//Altri metodi
	//-----------------------------------------------------------------
	private void pulisciCampi() {
		nomeSalaTextField.setText("");
		capienzaTextField.setText("");
		indirizzoTextArea.setText("");
		pianoTextField.setText("");
	}
}
