package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerMeeting;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Cursor;
import javax.swing.AbstractListModel;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;

public class Meeting extends JFrame {

	private JPanel contentPane;
	private JButton modificaButton;
	private JButton okButton;
	private JScrollPane mieiProgettiScrollPane;
	private JLabel mieiMeetingLabel;
	private JPanel panel;
	private JLabel infoLabel;
	private JList list;
	private JLabel nomeMettingLabel;
	private JLabel inizioLabel;
	private JLabel fineLabel;
	private JLabel valoreInizioLabel;
	private JLabel valoreFineLabel;
	private JSeparator separator;


	/**
	 * Create the frame.
	 */
	public Meeting(ControllerMeeting theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Meeting.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Meeting");
		setBounds(650, 150, 1440, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton nuovoMeetingButton = new JButton("Nuovo Meeting");
		nuovoMeetingButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nuovoMeetingButton.setBackground(Color.WHITE);
		nuovoMeetingButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		nuovoMeetingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createInsertMeetingFrame();
			}
		});
		
		modificaButton = new JButton("Modifica");
		modificaButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		modificaButton.setBackground(Color.WHITE);
		modificaButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.createModifyMeetingFrame();
			}
		});
		
		okButton = new JButton("OK");
		okButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		okButton.setBackground(Color.WHITE);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		okButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		mieiProgettiScrollPane = new JScrollPane();
		mieiProgettiScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		mieiMeetingLabel = new JLabel("I Miei Meeting");
		mieiMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		panel = new JPanel();
		
		infoLabel = new JLabel("Info");
		infoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(mieiMeetingLabel)
								.addComponent(mieiProgettiScrollPane, GroupLayout.PREFERRED_SIZE, 570, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 660, GroupLayout.PREFERRED_SIZE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(infoLabel)
									.addGap(290))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(mieiMeetingLabel)
						.addComponent(infoLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
						.addComponent(mieiProgettiScrollPane, GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE))
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(nuovoMeetingButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(101)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
					.addGap(97))
		);
		
		list = new JList();
		list.setSelectionBackground(Color.LIGHT_GRAY);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Meeting 1", "Meeting 2", "Meeting 3", "Discord 3", "Riunione Teams", "Leo Pardo Cisco Webex", "Riunione ", "Prova ", "Prova 27"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setFixedCellHeight(40);
		list.setFont(new Font("Consolas", Font.PLAIN, 15));
		list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mieiProgettiScrollPane.setViewportView(list);
		panel.setLayout(null);
		
		nomeMettingLabel = new JLabel("Meeting 1 [01]");
		nomeMettingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		nomeMettingLabel.setBounds(243, 11, 196, 36);
		panel.add(nomeMettingLabel);
		
		inizioLabel = new JLabel("Inizio");
		inizioLabel.setForeground(Color.DARK_GRAY);
		inizioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		inizioLabel.setBounds(59, 84, 196, 36);
		panel.add(inizioLabel);
		
		fineLabel = new JLabel("Fine");
		fineLabel.setForeground(Color.DARK_GRAY);
		fineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		fineLabel.setBounds(425, 84, 196, 36);
		panel.add(fineLabel);
		
		valoreInizioLabel = new JLabel("07-01-2021 14:00");
		valoreInizioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreInizioLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valoreInizioLabel.setBounds(44, 119, 233, 36);
		panel.add(valoreInizioLabel);
		
		valoreFineLabel = new JLabel("07-01-2021 17:00");
		valoreFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valoreFineLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		valoreFineLabel.setBounds(405, 119, 233, 36);
		panel.add(valoreFineLabel);
		contentPane.setLayout(gl_contentPane);
	}
}
