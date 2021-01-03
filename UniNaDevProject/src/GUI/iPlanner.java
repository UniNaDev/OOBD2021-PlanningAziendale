package GUI;
import Controller.ControllerScelta;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.ComponentOrientation;
import java.awt.Component;
import java.awt.Toolkit;

public class iPlanner extends JFrame {

	private JPanel contentPane;
 

    
    
	/**
	 * Create the frame.
	 */
	public iPlanner(ControllerScelta theController) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(iPlanner.class.getResource("/Icone/WindowIcon_16.png")));
		
		setTitle("iPlanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel LabelDipendenteIscritto = new JLabel("Sei un dipendente gi\u00E0 iscritto?");
		LabelDipendenteIscritto.setFont(new Font("Consolas", Font.PLAIN, 15));
		LabelDipendenteIscritto.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Quando clicco sull'etichetta con il mouse mi porta alla finestra per fare il login
		LabelDipendenteIscritto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.LinkToLoginFrame();
			}
		});
		
		
		JLabel LabelNuovoDipendente = new JLabel("Sei un nuovo dipendente?");
		LabelNuovoDipendente.setFont(new Font("Consolas", Font.PLAIN, 14));
		LabelNuovoDipendente.setHorizontalAlignment(SwingConstants.CENTER);
		
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
					.addComponent(LabelNuovoDipendente, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
					.addGap(285))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(167)
					.addComponent(welcomeLabel, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
					.addGap(158))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(258)
					.addComponent(LabelDipendenteIscritto, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
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
					.addComponent(LabelDipendenteIscritto, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(LabelNuovoDipendente, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(120)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(createdByLabel)
						.addComponent(versionLabel)))
		);
		contentPane.setLayout(gl_contentPane);
		
		//Quando clicco sull'etichetta con il mouse mi porta alla finestra per creare un nuovo utente
		LabelNuovoDipendente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.LinkToCreationFrame();
			}
		});

	

		
	
		
	}
}
