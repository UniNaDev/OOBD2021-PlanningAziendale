package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControllerGestioneProfilo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Frame;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Cursor;

public class User extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public User(ControllerGestioneProfilo theController) {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(User.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton accountButton = new JButton("Account");
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.viewAccount();
			}
		});
		
		JButton btnNewButton = new JButton("Progetti");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.linkToProjectFrame();
			}
		});
		
		JButton btnNewButton_1 = new JButton("Meeting");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.linkToMeetingFrame();
			}
		});
		
		JButton btnNewButton_2 = new JButton("Logout");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.logout();
			}
		});
		
		JScrollPane progettiScrollPanel = new JScrollPane();
		progettiScrollPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		JScrollPane meetingScrollPanel = new JScrollPane();
		meetingScrollPanel.setBorder(new LineBorder(Color.GRAY, 2));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(accountButton, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1636, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1829, Short.MAX_VALUE)
					.addComponent(btnNewButton_2))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
					.addGap(240)
					.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(accountButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(143)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(meetingScrollPanel, GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
						.addComponent(progettiScrollPanel, GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
					.addGap(28)
					.addComponent(btnNewButton_2))
		);
		
		JLabel meetingLabel = new JLabel("Meeting Programmati");
		meetingLabel.setForeground(Color.DARK_GRAY);
		meetingLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		meetingScrollPanel.setColumnHeaderView(meetingLabel);
		
		JList meetingList = new JList();
		meetingList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		meetingList.setFixedCellHeight(40);
		meetingList.setFont(new Font("Consolas", Font.PLAIN, 15));
		meetingList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Meeting 1", "Meeting 2", "Meeting 3", "Discord 3", "Riunione Teams", "Leo Pardo Cisco Webex", "Riunione ", "Prova ", "Prova 27"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		meetingScrollPanel.setViewportView(meetingList);
		
		JLabel progettiLabel = new JLabel("Progetti Aziendali");
		progettiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		progettiLabel.setForeground(Color.DARK_GRAY);
		progettiLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
		progettiScrollPanel.setColumnHeaderView(progettiLabel);
		
		JList progettiList = new JList();
		progettiList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progettiList.setFixedCellHeight(40);
		progettiList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Progetto 1", "Progetto 2", "Progetto 3", "Lol", "Ciao", "iPlanner", "Windows 20", "Fortnite", "Rino Ceronte", "Cyberpunk 2077", "Test", "Test1", "Parole a caso per ridimensionare", "bho"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		progettiList.setFont(new Font("Consolas", Font.PLAIN, 15));
		progettiScrollPanel.setViewportView(progettiList);
		contentPane.setLayout(gl_contentPane);
	}
}
