package GUI;

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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
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

public class GestioneProgetto extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField creatoreTextField;
	private JTable table;
	private JTextField textField_5;


	/**
	 * Create the frame.
	 */
	public GestioneProgetto() {
		setTitle("iPlanner-Gestione progetto");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setFont(new Font("Tahoma", Font.PLAIN, 36));
		panel_3.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
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
		table.getColumnModel().getColumn(4).setPreferredWidth(77);
		scrollPane_1.setViewportView(table);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JButton btnNewButton = new JButton("Pulisci");
		btnNewButton.setAlignmentX(0.5f);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setMargin(new Insets(2, 20, 2, 20));
		
		JButton btnNewButton_1 = new JButton("Elimina");
		btnNewButton_1.setMargin(new Insets(2, 20, 2, 20));
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setAlignmentX(0.5f);
		
		JButton insertPartecipante = new JButton("Inserisci partecipanti");
		insertPartecipante.setMargin(new Insets(2, 20, 2, 20));
		insertPartecipante.setFont(new Font("Tahoma", Font.PLAIN, 20));
		insertPartecipante.setAlignmentX(0.5f);
		
		JButton btnNewButton_3 = new JButton("Modifica");
		btnNewButton_3.setMargin(new Insets(2, 20, 2, 20));
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_3.setAlignmentX(0.5f);
		
		JButton btnNewButton_4 = new JButton("Salva");
		btnNewButton_4.setMargin(new Insets(2, 20, 2, 20));
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_4.setAlignmentX(0.5f);
		panel_3.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_2 = new JLabel("Cerca");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblNewLabel_2);
		
		textField_5 = new JTextField();
		panel_4.add(textField_5);
		textField_5.setColumns(10);
		panel_4.add(insertPartecipante);
		panel_4.add(btnNewButton_3);
		panel_4.add(btnNewButton_4);
		panel_4.add(btnNewButton_1);
		panel_4.add(btnNewButton);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2.setBackground(Color.WHITE);
		
		JLabel idProgettoLabel = new JLabel("ProgettoID");
		idProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		idProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel nomeProgettoLabel = new JLabel("Nome");
		nomeProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		nomeProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel ambitoProgettoLabel = new JLabel("Ambito/i");
		ambitoProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		ambitoProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel tipologiaProgettoLabel = new JLabel("Tipologia");
		tipologiaProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		tipologiaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JLabel scadenzaProgettoLabel = new JLabel("Scadenza");
		scadenzaProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		scadenzaProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel descrizioneProgettoLabel = new JLabel("Descrizione");
		descrizioneProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		descrizioneProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel creatoreProgettoLabel = new JLabel("Creatore");
		creatoreProgettoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		creatoreProgettoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		creatoreTextField = new JTextField();
		creatoreTextField.setColumns(10);
		
		JComboBox giornoComboBox = new JComboBox();
		giornoComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		giornoComboBox.setBackground(Color.WHITE);
		giornoComboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		giornoComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setSelectedIndex(5);
		giornoComboBox.setBounds(244, 235, 47, 22);
		panel_2.add(giornoComboBox);
		
		JComboBox meseComboBox = new JComboBox();
		meseComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meseComboBox.setBackground(Color.WHITE);
		meseComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setSelectedIndex(9);
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
		
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(ambitoProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(idProgettoLabel)
						.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addComponent(creatoreProgettoLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(creatoreTextField, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(textArea, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(giornoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(meseComboBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(annoComboBox, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
									.addGap(4)))
							.addGap(275)))
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
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomeProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(ambitoProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tipologiaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(scadenzaProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(meseComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(giornoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(annoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(descrizioneProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(creatoreProgettoLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(creatoreTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
							.addGap(23))))
		);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		panel_2.setLayout(gl_panel_2);
		panel_1.add(panel_2);
		panel.setLayout(gl_panel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
					.addGap(4))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
