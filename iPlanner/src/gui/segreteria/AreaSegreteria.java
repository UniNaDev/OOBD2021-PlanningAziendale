package gui.segreteria;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import controller.segreteria.ControllerAreaSegreteria;
import gui.iPlanner;

public class AreaSegreteria extends JFrame {
	private JPanel contentPane;	
	private JLabel gestisciDipendentiLabel;
	private JLabel benvenutoLabel;
	private JLabel iconaDipendenteLabel;
	private JLabel gestisciMeetingLabel;
	private JLabel gestisciProgettiLabel;
	private JLabel esciLabel;

	public AreaSegreteria(ControllerAreaSegreteria controller) {
		setMinimumSize(new Dimension(850, 500));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(iPlanner.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - Area Segreteria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		benvenutoLabel = new JLabel("Area Segreteria");
		benvenutoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		benvenutoLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/planner_128.png")));
		benvenutoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaDipendenteLabel = new JLabel("");
		iconaDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconaDipendenteLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/employee_64.png")));
		
		
		gestisciDipendentiLabel = new JLabel("Gestisci dipendenti");
		gestisciDipendentiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciDipendentiLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		gestisciDipendentiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gestisciDipendentiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.vaiAGestioneDipendenti();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				gestisciDipendentiLabel.setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gestisciDipendentiLabel.setForeground(Color.BLACK);
			}
		});

		gestisciMeetingLabel = new JLabel("Gestisci meeting");
		gestisciMeetingLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciMeetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gestisciMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		gestisciMeetingLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.vaiAGestioneMeeting();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				gestisciMeetingLabel.setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gestisciMeetingLabel.setForeground(Color.BLACK);
			}
		});

		gestisciProgettiLabel = new JLabel("Gestisci progetti");
		gestisciProgettiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciProgettiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gestisciProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		gestisciProgettiLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.vaiAGestioneProgetti();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				gestisciProgettiLabel.setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gestisciProgettiLabel.setForeground(Color.BLACK);
			}
		});
		
		esciLabel = new JLabel("Esci");
		esciLabel.setHorizontalAlignment(SwingConstants.CENTER);
		esciLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esciLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		esciLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				esciLabel.setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				esciLabel.setForeground(Color.BLACK);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(167)
					.addComponent(benvenutoLabel, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
					.addGap(158))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(136)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(gestisciDipendentiLabel, GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(91)
							.addComponent(iconaDipendenteLabel, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
							.addGap(91))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(68)
							.addComponent(gestisciMeetingLabel, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
							.addGap(68))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(esciLabel, GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
									.addGap(23))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(gestisciProgettiLabel, GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
									.addGap(23)))))
					.addGap(125))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addComponent(benvenutoLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
					.addComponent(iconaDipendenteLabel)
					.addGap(18)
					.addComponent(gestisciDipendentiLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gestisciMeetingLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(gestisciProgettiLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(esciLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(52))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
