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
	private JLabel areaSegreteriaLabel;
	private JLabel benvenutoLabel;
	private JLabel iconaDipendenteLabel;

	private final String devs = "UninaDevs";
	private final String versione = "0.0.1";

	public iPlanner(ControllerStart controller) {
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
		
		loginLabel = new JLabel("Area Dipendente");
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLabel.setForeground(Color.BLACK);
		loginLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.apriGUILoginDipendente();
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
		
		areaSegreteriaLabel = new JLabel("Area Segreteria");
		areaSegreteriaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		areaSegreteriaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		areaSegreteriaLabel.setForeground(Color.BLACK);
		areaSegreteriaLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		areaSegreteriaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.apriGUIAutenticazioneSegreteria();
			}
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				areaSegreteriaLabel.setForeground(Color.GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				areaSegreteriaLabel.setForeground(Color.BLACK);
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(140)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(27)
							.addComponent(benvenutoLabel, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
							.addGap(158))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(234)
									.addComponent(iconaDipendenteLabel, GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
									.addGap(226))
								.addComponent(loginLabel, GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE))
							.addGap(129))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(80)
					.addComponent(areaSegreteriaLabel, GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
					.addGap(70))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addComponent(benvenutoLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
					.addComponent(iconaDipendenteLabel)
					.addGap(18)
					.addComponent(areaSegreteriaLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(loginLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(82))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
