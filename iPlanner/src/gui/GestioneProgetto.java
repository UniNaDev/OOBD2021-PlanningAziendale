package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestioneProgetto extends JFrame {

	private JPanel contentPane;
	private JTextField idProgettoTextField;
	private JTextField nomeTextField;
	private JTextField ambitoTextField;
	private JTextField tipologiaTextField;
	private JTextField creatoreTextField;
	private JTable progettoTable;
	private JTextField cercaTextField;


	/**
	 * Create the frame.
	 */
	public GestioneProgetto() {
		setTitle("iPlanner-Gestione progetto");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		panel_3.setFont(new Font("Tahoma", Font.PLAIN, 36));
		panel_3.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setFont(new Font("Consolas", Font.PLAIN, 11));
		scrollPane_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		progettoTable = new JTable();
		progettoTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		progettoTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettoTable.setBackground(Color.WHITE);
		progettoTable.setSelectionBackground(Color.LIGHT_GRAY);
		progettoTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, "", null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"ProgettoID", "Nome", "Ambito/i", "Tipologia", "Scadenza", "Descrizione", "Creatore"
			}
		));
		progettoTable.getColumnModel().getColumn(4).setPreferredWidth(77);
		scrollPane_1.setViewportView(progettoTable);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(192, 192, 192)));
		
		JButton pulisciButton = new JButton("Pulisci");
		pulisciButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				pulisciButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				pulisciButton.setBackground(Color.WHITE);
			}
		});
		pulisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pulisciButton.setPreferredSize(new Dimension(90, 30));
		pulisciButton.setMaximumSize(new Dimension(150, 150));
		pulisciButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pulisciButton.setBackground(Color.WHITE);
		pulisciButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		pulisciButton.setAlignmentX(0.5f);
		pulisciButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		pulisciButton.setMargin(new Insets(2, 20, 2, 20));
		
		JButton eliminaButton = new JButton("Elimina");
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
		
		JButton inserisciPartecipanteButton = new JButton("Inserisci partecipanti");
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
		
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				modificaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				modificaButton.setBackground(Color.WHITE);
			}
		});
		modificaButton.setPreferredSize(new Dimension(90, 30));
		modificaButton.setMaximumSize(new Dimension(150, 150));
		modificaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setMargin(new Insets(2, 20, 2, 20));
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		modificaButton.setAlignmentX(0.5f);
		
		JButton salvaButton = new JButton("Salva");
		salvaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				salvaButton.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				salvaButton.setBackground(Color.WHITE);
			}
		});
		salvaButton.setPreferredSize(new Dimension(90, 30));
		salvaButton.setMaximumSize(new Dimension(150, 150));
		salvaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvaButton.setBackground(Color.WHITE);
		salvaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		salvaButton.setMargin(new Insets(2, 20, 2, 20));
		salvaButton.setFont(new Font("Consolas", Font.PLAIN, 17));
		salvaButton.setAlignmentX(0.5f);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel cercaLbel = new JLabel("Cerca");
		cercaLbel.setFont(new Font("Consolas", Font.PLAIN, 18));
		panel_4.add(cercaLbel);
		
		cercaTextField = new JTextField();
		cercaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		panel_4.add(cercaTextField);
		cercaTextField.setColumns(10);
		panel_4.add(inserisciPartecipanteButton);
		panel_4.add(modificaButton);
		panel_4.add(salvaButton);
		panel_4.add(eliminaButton);
		panel_4.add(pulisciButton);
		panel_3.add(panel_4);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		
		JLabel idProgettoLabel = new JLabel("ProgettoID");
		idProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		idProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel nomeProgettoLabel = new JLabel("Nome");
		nomeProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		idProgettoTextField = new JTextField();
		idProgettoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		idProgettoTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		nomeTextField = new JTextField();
		nomeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nomeTextField.setColumns(10);
		
		JLabel ambitoProgettoLabel = new JLabel("Ambito/i");
		ambitoProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		ambitoProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		ambitoTextField = new JTextField();
		ambitoTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		ambitoTextField.setColumns(10);
		
		JLabel tipologiaProgettoLabel = new JLabel("Tipologia");
		tipologiaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		tipologiaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tipologiaTextField = new JTextField();
		tipologiaTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		tipologiaTextField.setColumns(10);
		
		JLabel scadenzaProgettoLabel = new JLabel("Scadenza");
		scadenzaProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		scadenzaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel descrizioneProgettoLabel = new JLabel("Descrizione");
		descrizioneProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		descrizioneProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel creatoreProgettoLabel = new JLabel("Creatore");
		creatoreProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		creatoreProgettoLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		creatoreTextField = new JTextField();
		creatoreTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		creatoreTextField.setColumns(10);
		
		JComboBox giornoComboBox = new JComboBox();
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setSelectedIndex(0);
		giornoComboBox.setBounds(244, 235, 47, 22);
		panel_2.add(giornoComboBox);
		
		JComboBox meseComboBox = new JComboBox();
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setSelectedIndex(0);
		meseComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		meseComboBox.setBounds(301, 235, 47, 22);
		panel_2.add(meseComboBox);
		
		JComboBox annoComboBox = new JComboBox();
		annoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		annoComboBox.setBackground(Color.WHITE);
		annoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		annoComboBox.setBounds(358, 235, 62, 22);
		//CREO UN MODELLO PERSONALIZZATO PER L'ANNO DEL COMBO BOX //////////
		DefaultComboBoxModel myModel = new DefaultComboBoxModel();
		annoComboBox.setModel(myModel);
		
		for(int i=1900;i<= 2021;i++)
			myModel.addElement(i);
		
		annoComboBox.setSelectedIndex(100); //mette di default il 100esimo indice cioè l'anno 2000
//////////////////////////////////////////////////////////
		
		panel_2.add(annoComboBox);
		
		
		JTextArea descrizioneTextArea = new JTextArea();
		descrizioneTextArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(ambitoProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addComponent(creatoreProgettoLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
							.addComponent(tipologiaProgettoLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(idProgettoLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(idProgettoTextField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addComponent(ambitoTextField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addComponent(nomeTextField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addComponent(tipologiaTextField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(descrizioneTextArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(giornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(meseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(annoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
								.addComponent(creatoreTextField, Alignment.LEADING))
							.addGap(4)))
					.addGap(275)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
					.addGap(59))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(idProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(idProgettoTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(nomeTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(ambitoProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(ambitoTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(tipologiaTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(meseComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(giornoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(annoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(descrizioneTextArea, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(creatoreProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(creatoreTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
							.addGap(23))))
		);
		
		JList list = new JList();
		list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane.setViewportView(list);
		panel_2.setLayout(gl_panel_2);
		panel_1.add(panel_2);
		panel.setLayout(gl_panel);
		
		JLabel gestioneProgettoLabel = new JLabel("Gestione Progetto");
		gestioneProgettoLabel.setIcon(new ImageIcon(GestioneProgetto.class.getResource("/Icone/progetto_64.png")));
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
	}
}
