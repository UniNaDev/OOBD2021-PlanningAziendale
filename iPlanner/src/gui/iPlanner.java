package gui;
import controller.*;
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

	//Attributi
	private final String devs = "UninaDev";	//stringa sviluppatori
	private final String versione = "0.0.1";	//stringa versione del software
	
	//Attrubuti GUI
	private JPanel contentPane;
	private JLabel dipendenteIscrittoLabel;
	private JLabel nuovoDipendenteLabel;
 

	/**
	 * Create the frame.
	 */
	public iPlanner(ControllerScelta theController, boolean segreteria) {
		
		setMinimumSize(new Dimension(850, 500));
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(iPlanner.class.getResource("/Icone/WindowIcon_16.png")));
		setTitle("iPlanner - HomePage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Label "Welcome to iPlanner"
		JLabel welcomeLabel = new JLabel("Welcome to iPlanner");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/planner_128.png")));
		welcomeLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//Icona Label Welcome
		JLabel employeeIconLabel = new JLabel("");
		employeeIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		employeeIconLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/employee_64.png")));
		
		//Label "Created by"
		JLabel createdByLabel = new JLabel("Created by " + devs);
		createdByLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		//Label versione del software
		JLabel versionLabel = new JLabel("v " + versione);
		versionLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		versionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		//Se l'autorizzazione è di tipo dipendente mostra nella finestra solo l'opzione "Esegui login"
		if (!segreteria) {
			//Label "Esegui login"
		dipendenteIscrittoLabel = new JLabel("Esegui login");
		dipendenteIscrittoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dipendenteIscrittoLabel.setForeground(Color.BLACK);
		dipendenteIscrittoLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		dipendenteIscrittoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Eventi connessi alla label "Esegui login"
		dipendenteIscrittoLabel.addMouseListener(new MouseAdapter() {
			//click sinistro del mouse
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.linkToLoginFrame();	//va alla finestra di login
			}
			//mouse sopra la label
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				dipendenteIscrittoLabel.setForeground(Color.GRAY);	//evidenzia la label cambiando colore
			}
			//mouse fuori dalla label
			@Override
			public void mouseExited(MouseEvent e) 
			{
				dipendenteIscrittoLabel.setForeground(Color.BLACK);	//smette di evidenziarla resettando il colore della label
			}
		});
		
		//Group Layout per il posizionamento delle componenti nel frame
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
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
					.addComponent(dipendenteIscrittoLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(createdByLabel)
						.addComponent(versionLabel)))
		);
		contentPane.setLayout(gl_contentPane);
		}
		//Se l'autorizzazione è di tipo segreteria mostra la finestra con la sola opzione "Aggiungi nuovo dipendente"
		else {
		
			//Label "Aggiungi nuovo dipendente"
		nuovoDipendenteLabel = new JLabel("Aggiungi nuovo dipendente");
		nuovoDipendenteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nuovoDipendenteLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		nuovoDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Eventi connessi alla label "Aggiungi nuovo dipendente"
		nuovoDipendenteLabel.addMouseListener(new MouseAdapter() {
			//click sinistro del mouse
			@Override
			public void mouseClicked(MouseEvent e) {
				
				theController.linkToCreationFrame();	//apre la finestra di creazione account
			}
			//mouse sopra la label
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				nuovoDipendenteLabel.setForeground(Color.GRAY);	//evidenzia la label cambiando colore
			}
			//mouse fuori dalla label
			@Override
			public void mouseExited(MouseEvent e) 
			{
				nuovoDipendenteLabel.setForeground(Color.BLACK);	//smette di evidenziarla resettandone il colore
			}
		});
		
		//Group Layout per l'inserimento delle componenti nel frame
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(258)
						.addComponent(nuovoDipendenteLabel, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addGap(248))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(167)
					.addComponent(welcomeLabel, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
					.addGap(158))
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
					.addComponent(nuovoDipendenteLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(createdByLabel)
						.addComponent(versionLabel)))
		);
		contentPane.setLayout(gl_contentPane);
		}	
	}
}
