package gui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class ErroreFatale extends JFrame {

	private JPanel contentPane;

	public ErroreFatale(Exception eccezione) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ErroreFatale.class.getResource("/icone/fatalError.png")));
		setResizable(false);
		setTitle("Errore Fatale");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 108, 529, 83);
		contentPane.add(scrollPane);
		
		JTextArea stackTraceTextArea = new JTextArea();
		stackTraceTextArea.setLineWrap(true);
		stackTraceTextArea.setEditable(false);
		stackTraceTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(stackTraceTextArea);
		
		JTextArea messaggioTextArea = new JTextArea();
		messaggioTextArea.setEditable(false);
		messaggioTextArea.setLineWrap(true);
		messaggioTextArea.setText("Errore Fatale!\r\n\r\nVerificare che il programma sia aggiornato altrimenti contattare gli sviluppatori (preferibilmente inviando i dettagli dell'errore).");
		messaggioTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		messaggioTextArea.setBounds(14, 11, 530, 66);
		contentPane.add(messaggioTextArea);
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		okButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	okButton.setBackground(Color.LIGHT_GRAY);
		    }
	
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	okButton.setBackground(Color.WHITE);
		    }
		});
		okButton.setFont(new Font("Consolas", Font.PLAIN, 13));
		okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		okButton.setBounds(497, 202, 46, 20);
		contentPane.add(okButton);
		
		JLabel mostraDettagliLabel = new JLabel("Mostra Dettagli");
		mostraDettagliLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stackTraceTextArea.setText(ottieniStackTrace(eccezione));
			}
		});
		mostraDettagliLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mostraDettagliLabel.setFont(new Font("Consolas", Font.BOLD, 11));
		mostraDettagliLabel.setBounds(14, 88, 98, 14);
		contentPane.add(mostraDettagliLabel);
		
		setVisible(true);
	}
	
	//Altri metodi
	//--------------------------------------------
	private String ottieniStackTrace(Exception e) {
		String messaggio = "";
		
		for (StackTraceElement stringa: e.getStackTrace())
			messaggio += stringa.toString() + "\n";
		
		return messaggio;
	}
}
