package GUI;
import Controller.*;
import java.awt.*;


import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;



public class iPlanner extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel dipendenteIscrittoLabel;
	private JLabel nuovoDipendenteLabel;
 
	
    
    
	/**
	 * Create the frame.
	 */
	public iPlanner(ControllerScelta theController) {
		setMinimumSize(new Dimension(850, 500));
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(iPlanner.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - HomePage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		dipendenteIscrittoLabel = new JLabel("Sei un dipendente gi\u00E0 iscritto?");
		dipendenteIscrittoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteIscrittoLabel.setForeground(Color.BLACK);
		dipendenteIscrittoLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		dipendenteIscrittoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		

		
		nuovoDipendenteLabel = new JLabel("Sei un nuovo dipendente?");
		nuovoDipendenteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoDipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		nuovoDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel welcomeLabel = new JLabel("Welcome to iPlanner");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/planner_128.png")));
		welcomeLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		JLabel employeeIconLabel = new JLabel("");
		employeeIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		employeeIconLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/employee_64.png")));
		
		JLabel createdByLabel = new JLabel("Created by UniNaDev");
		createdByLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		JLabel versionLabel = new JLabel("v 1.0.0.0");
		versionLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		versionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(295)
					.addComponent(nuovoDipendenteLabel, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
					.addGap(285))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(167)
					.addComponent(welcomeLabel, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
					.addGap(158))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(258)
					.addComponent(dipendenteIscrittoLabel, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
					.addGap(248))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(369)
					.addComponent(employeeIconLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(360))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(createdByLabel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 1090, Short.MAX_VALUE)
					.addComponent(versionLabel))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addComponent(welcomeLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
					.addComponent(employeeIconLabel)
					.addGap(18)
					.addComponent(dipendenteIscrittoLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(nuovoDipendenteLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(120)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(createdByLabel)
						.addComponent(versionLabel)))
		);
		contentPane.setLayout(gl_contentPane);
		
		
		//Quando clicco sull'etichetta con il mouse mi porta alla finestra per fare il login
		dipendenteIscrittoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.linkToLoginFrame();
			}
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				//quando passa sopra con il mouse il colore cambia in grigio
				dipendenteIscrittoLabel.setForeground(Color.GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				//quando il mouse si sposta il colore ritorna nero
				dipendenteIscrittoLabel.setForeground(Color.BLACK);
			}
		});
		
		
		//Quando clicco sull'etichetta con il mouse mi porta alla finestra per creare un nuovo utente
		nuovoDipendenteLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.linkToCreationFrame();
			}
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				//quando passa sopra con il mouse il colore cambia in grigio
				nuovoDipendenteLabel.setForeground(Color.GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				//quando il mouse si sposta il colore ritorna nero
				nuovoDipendenteLabel.setForeground(Color.BLACK);
			}
		});

	

		
	
		
	}
}
