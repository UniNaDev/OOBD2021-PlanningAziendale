package Viste;


import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Controller.ControllerLogged;
import Entita.Meeting;
import Entita.Progetto;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

	private JPanel contentPane;


	//Crea il frame
	public MainWindow(ControllerLogged controller) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel emailLabel = new JLabel(controller.getLoggedUser().getEmail());
		emailLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		emailLabel.setBounds(38, 111, 215, 20);
		contentPane.add(emailLabel);
		

		DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		JLabel oggiLabel = new JLabel(LocalDateTime.now().toString(formatDate));
		oggiLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		oggiLabel.setBounds(38, 134, 134, 20);
		contentPane.add(oggiLabel);
		
		JLabel benvenutoLabel = new JLabel("Benvenuto " + controller.getLoggedUser().getCognome());
		benvenutoLabel.setFont(new Font("Calibri", Font.BOLD, 32));
		benvenutoLabel.setBounds(38, 71, 405, 40);
		contentPane.add(benvenutoLabel);
		
		JLabel progettiScadenzaLabel = new JLabel("Progetti in scadenza");
		progettiScadenzaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		progettiScadenzaLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		progettiScadenzaLabel.setBounds(38, 165, 134, 17);
		contentPane.add(progettiScadenzaLabel);


		try {
			ArrayList<String> progetti = new ArrayList<String>();
			formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
			for (Progetto proj : controller.ottieniProgetti())
				progetti.add(proj.getNomeProgetto() + " " + proj.getScadenza().toString(formatDate));
			JList progettiList = new JList(progetti.toArray());
			progettiList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			progettiList.setFont(new Font("Calibri", Font.PLAIN, 16));
			progettiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			progettiList.setBounds(38, 186, 247, 306);
			contentPane.add(progettiList);
			
			JLabel meetingScadenzaLabel = new JLabel("Meeting più vicini");
			meetingScadenzaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			meetingScadenzaLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
			meetingScadenzaLabel.setBounds(531, 164, 134, 17);
			contentPane.add(meetingScadenzaLabel);
			
			MeetingListRenderer renderer = new MeetingListRenderer();
			
			ArrayList<Meeting> meetings = controller.ottieniMeeting();
			JList meetingList = new JList(meetings.toArray());
			meetingList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			meetingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			meetingList.setFont(new Font("Calibri", Font.PLAIN, 16));
			meetingList.setFixedCellHeight(-1);
			meetingList.setCellRenderer(renderer);
			meetingList.setBounds(388, 186, 277, 306);
			contentPane.add(meetingList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
