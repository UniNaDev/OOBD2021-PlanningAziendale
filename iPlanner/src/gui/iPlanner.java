/*Frame della finestra principale
 *Qui in base al tipo di autorizzazione (segreteria/dipendente)
 *vengono visualizzate opzioni differenti.
 *Dipendente -> Login
 *Segreteria -> Gestione dipendenti, meeting o progetti
 *************************************************************/

package gui;
import controller.*;
import java.awt.*;


import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;



public class iPlanner extends JFrame {
	private JPanel contentPane;
	private JLabel loginLabel;	
	private JLabel gestisciDipendentiLabel;
	private JLabel benvenutoLabel;
	private JLabel iconaDipendenteLabel;
	private JLabel gestisciMeetingLabel;
	private JLabel gestisciProgettiLabel;
 
	private final String devs = "UninaDevs";
	private final String versione = "0.0.1";

	public iPlanner(ControllerStart controller, boolean segreteria) {
		setMinimumSize(new Dimension(850, 500));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(iPlanner.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - HomePage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		benvenutoLabel = new JLabel("Benvenuto su iPlanner");
		benvenutoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		benvenutoLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/planner_128.png")));
		benvenutoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		iconaDipendenteLabel = new JLabel("");
		iconaDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconaDipendenteLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/employee_64.png")));
		
		//FINESTRA DIPENDENTE
		if (!segreteria) {
			loginLabel = new JLabel("Esegui login");
			loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			loginLabel.setForeground(Color.BLACK);
			loginLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
			loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			loginLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					controller.apriLogin();
				}
				@Override
				public void mouseEntered(MouseEvent e) 
				{
					loginLabel.setForeground(Color.GRAY);
				}
				@Override
				public void mouseExited(MouseEvent e) 
				{
					loginLabel.setForeground(Color.BLACK);
				}
			});
			
			GroupLayout gl_contentPane = new GroupLayout(contentPane);
			gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(167)
						.addComponent(benvenutoLabel, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
						.addGap(158))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(258)
						.addComponent(loginLabel, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addGap(248))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(369)
						.addComponent(iconaDipendenteLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(360))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(29)
						.addComponent(benvenutoLabel)
						.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
						.addComponent(iconaDipendenteLabel)
						.addGap(18)
						.addComponent(loginLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)))
			);
			contentPane.setLayout(gl_contentPane);
		}
		//FINESTRA SEGRETERIA 
		else {
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

			GroupLayout gl_contentPane = new GroupLayout(contentPane);
			gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(167)
						.addComponent(benvenutoLabel, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
						.addGap(158))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(131)
						.addComponent(gestisciDipendentiLabel, GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
						.addGap(130))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(222)
						.addComponent(iconaDipendenteLabel, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
						.addGap(221))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(199)
						.addComponent(gestisciMeetingLabel, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addGap(198))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(155)
						.addComponent(gestisciProgettiLabel, GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
						.addGap(153))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(29)
						.addComponent(benvenutoLabel)
						.addPreferredGap(ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
						.addComponent(iconaDipendenteLabel)
						.addGap(18)
						.addComponent(gestisciDipendentiLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(gestisciMeetingLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGap(6)
						.addComponent(gestisciProgettiLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
			contentPane.setLayout(gl_contentPane);
		}	
	}
}
