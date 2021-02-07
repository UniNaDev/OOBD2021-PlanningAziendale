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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class iPlanner extends JFrame {

	//ATTRIBUTI
	//-----------------------------------------------------------------
	
	//Attrubuti GUI
	private JPanel contentPane;
	private JLabel loginLabel;	//label clickabile per effettuare login
	private JLabel gestisciDipendentiLabel;	//label clickabile per creare nuovi dipendenti
 
	//Altri attributi
	private final String devs = "UninaDevs";	//stringa sviluppatori
	private final String versione = "0.0.1";	//stringa versione del software
	
	//Creazione Frame
	//-----------------------------------------------------------------
	
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
		
		//Label "Welcome to iPlanner"
		JLabel benvenutoLabel = new JLabel("Benvenuto su iPlanner");
		benvenutoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		benvenutoLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/planner_128.png")));
		benvenutoLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		//Icona Label Welcome
		JLabel iconaDipendenteLabel = new JLabel("");
		iconaDipendenteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconaDipendenteLabel.setIcon(new ImageIcon(iPlanner.class.getResource("/Icone/employee_64.png")));
		
		//Label "Created by"
		JLabel creatoDaLabel = new JLabel("Creato da: " + devs);
		creatoDaLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		//Label versione del software
		JLabel versioneLabel = new JLabel("v " + versione);
		versioneLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		versioneLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		//Se l'autorizzazione è di tipo dipendente mostra nella finestra solo l'opzione "Esegui login"
		if (!segreteria) {
			//Label "Esegui login"
		loginLabel = new JLabel("Esegui login");
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLabel.setForeground(Color.BLACK);
		loginLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Eventi connessi alla label "Esegui login"
		loginLabel.addMouseListener(new MouseAdapter() {
			//click sinistro del mouse
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.apriLogin();	//va alla finestra di login
			}
			//mouse sopra la label
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				loginLabel.setForeground(Color.GRAY);	//evidenzia la label cambiando colore
			}
			//mouse fuori dalla label
			@Override
			public void mouseExited(MouseEvent e) 
			{
				loginLabel.setForeground(Color.BLACK);	//smette di evidenziarla resettando il colore della label
			}
		});
		
		//Group Layout per il posizionamento delle componenti nel frame
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
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(creatoDaLabel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 1090, Short.MAX_VALUE)
					.addComponent(versioneLabel))
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(creatoDaLabel)
						.addComponent(versioneLabel)))
		);
		contentPane.setLayout(gl_contentPane);
		}
		//Se l'autorizzazione è di tipo segreteria mostra la finestra con la sola opzione "Aggiungi nuovo dipendente"
		else {
		
			//Label "Gestisci dipendenti"
			gestisciDipendentiLabel = new JLabel("Gestisci dipendenti");
			gestisciDipendentiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			gestisciDipendentiLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
			gestisciDipendentiLabel.setHorizontalAlignment(SwingConstants.CENTER);
			//Eventi connessi alla label "Aggiungi nuovo dipendente"
			gestisciDipendentiLabel.addMouseListener(new MouseAdapter() {
				//click sinistro del mouse
				@Override
				public void mouseClicked(MouseEvent e) {	
					controller.vaiAGestioneDipendenti();
				}
				//mouse sopra la label
				@Override
				public void mouseEntered(MouseEvent e) 
				{
					gestisciDipendentiLabel.setForeground(Color.GRAY);	//evidenzia la label cambiando colore
				}
				//mouse fuori dalla label
				@Override
				public void mouseExited(MouseEvent e) 
				{
					gestisciDipendentiLabel.setForeground(Color.BLACK);	//smette di evidenziarla resettandone il colore
				}
		});
		
		//Label Gestisci Meeting
		JLabel gestisciMeetingLabel = new JLabel("Gestisci meeting");
		gestisciMeetingLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciMeetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gestisciMeetingLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		//Eventi connessi alla label "Gestisci sale"
		gestisciMeetingLabel.addMouseListener(new MouseAdapter() {
			//click sinistro del mouse
			@Override
			public void mouseClicked(MouseEvent e) {	
				controller.vaiAGestioneMeeting();
			}
			//mouse sopra la label
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				gestisciMeetingLabel.setForeground(Color.GRAY);	//evidenzia la label cambiando colore
			}
			//mouse fuori dalla label
			@Override
			public void mouseExited(MouseEvent e) 
			{
				gestisciMeetingLabel.setForeground(Color.BLACK);	//smette di evidenziarla resettandone il colore
			}
	});
		
		//Label Gestisci Progetti
		JLabel gestisciProgettiLabel = new JLabel("Gestisci progetti");
		gestisciProgettiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestisciProgettiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gestisciProgettiLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		//Eventi connessi alla label "Gestisci ambiti e tipologie"
		gestisciProgettiLabel.addMouseListener(new MouseAdapter() {
			//click sinistro del mouse
			@Override
			public void mouseClicked(MouseEvent e) {	
				//Apre finestra gestione dei progetti
				controller.vaiAGestioneProgetti();
			}
			//mouse sopra la label
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				gestisciProgettiLabel.setForeground(Color.GRAY);	//evidenzia la label cambiando colore
			}
			//mouse fuori dalla label
			@Override
			public void mouseExited(MouseEvent e) 
			{
				gestisciProgettiLabel.setForeground(Color.BLACK);	//smette di evidenziarla resettandone il colore
			}
	});
		
		//Group Layout per l'inserimento delle componenti nel frame
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(167)
					.addComponent(benvenutoLabel, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
					.addGap(158))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(creatoDaLabel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 1074, Short.MAX_VALUE)
					.addComponent(versioneLabel))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(131)
					.addComponent(gestisciDipendentiLabel, GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
					.addGap(130))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(222)
					.addComponent(iconaDipendenteLabel, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
					.addGap(221))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(199)
					.addComponent(gestisciMeetingLabel, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
					.addGap(198))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(155)
					.addComponent(gestisciProgettiLabel, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(creatoDaLabel)
								.addComponent(versioneLabel)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(gestisciProgettiLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		}	
	}
}
