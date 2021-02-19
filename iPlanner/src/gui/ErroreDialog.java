/*JDialog per messaggi di errori fatali e non previsti
Permette all'utente di copiare lo stacktrace per poterlo in seguito inviare agli sviluppatori con qualche commento
e riportare così l'errore.
*****************************************************************************************************************/

package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErroreDialog extends JDialog {
	
    private JPanel contentPane = new JPanel();
    
    private boolean chiudiProgramma = true;

    
	public ErroreDialog(Frame frameParente, Exception eccezione, String messaggio, String titolo, boolean chiudiProgramma) {
		generaGUI(frameParente, eccezione, messaggio, titolo, chiudiProgramma);
	}
	
	public ErroreDialog(Frame frameParente, Exception eccezione, boolean chiudiProgramma) {
	    String messaggio = "Errore Fatale!\r\n\r\nVerificare che il programma sia aggiornato altrimenti contattare gli sviluppatori (preferibilmente inviando i dettagli dell'errore).";
	    String titolo = "Errore Fatale";
	    generaGUI(frameParente, eccezione, messaggio, titolo, chiudiProgramma);
	}
	
	//Altri metodi
	//--------------------------------------------
	private String ottieniStackTrace(Exception e) {
		String dettagliErrore = "";
		
		for (StackTraceElement stringa: e.getStackTrace())
			dettagliErrore += stringa.toString() + "\n";
		
		return dettagliErrore;
	}
	
	private void comportamentoOk(boolean chiudiProgramma) {
		if (chiudiProgramma)
			System.exit(0);
		else
			this.dispose();
	}
	
	private void generaGUI(Frame frameParente, Exception eccezione, String messaggio, String titolo, boolean chiudiProgramma) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ErroreDialog.class.getResource("/icone/fatalError.png")));
		setResizable(false);
		setTitle(titolo);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 554, 272);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 108, 488, 83);
		contentPane.add(scrollPane);
		
		JTextArea stackTraceTextArea = new JTextArea();
		stackTraceTextArea.setLineWrap(true);
		stackTraceTextArea.setEditable(false);
		stackTraceTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(stackTraceTextArea);
		
		JTextArea messaggioTextArea = new JTextArea();
		messaggioTextArea.setEditable(false);
		messaggioTextArea.setLineWrap(true);
		messaggioTextArea.setText(messaggio);
		messaggioTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		messaggioTextArea.setBounds(25, 11, 488, 66);
		contentPane.add(messaggioTextArea);
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comportamentoOk(chiudiProgramma);
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
		okButton.setBounds(467, 202, 46, 20);
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
		mostraDettagliLabel.setBounds(25, 88, 98, 14);
		contentPane.add(mostraDettagliLabel);
	}
}
