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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;

public class ErroreDialog extends JDialog {
	
    private JPanel contentPane = new JPanel();
    
    private boolean mostratoErrore = false;

	public ErroreDialog(Exception eccezione, String titolo, String messaggio, boolean fatale) {
		generaGUI(eccezione, titolo, messaggio, fatale);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErroreDialog(Exception eccezione, boolean fatale) {
	    String messaggio = "Si è verificato un errore imprevisto. Si prega di riavviare l'applicazione.";
	    String titolo = "Errore Fatale";
	    generaGUI(eccezione, titolo, messaggio, fatale);
	}
	
	//Altri metodi
	//--------------------------------------------
	private String ottieniStackTrace(Exception e) {
		String dettagliErrore = "";
		
		for (StackTraceElement stringa: e.getStackTrace())
			dettagliErrore += stringa.toString() + "\n";
		
		return dettagliErrore;
	}
	
	private void comportamentoOkButton(boolean fatale) {
		if (fatale)
			System.exit(0);
		else
			this.dispose();
	}
	
	private void generaGUI(Exception eccezione, String titolo, String messaggio, boolean fatale) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ErroreDialog.class.getResource("/icone/fatalError.png")));
		setTitle(titolo);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				comportamentoOkButton(fatale);
			}
		});
		setBounds(100, 100, 700, 366);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(700, 366));
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JTextArea stackTraceTextArea = new JTextArea();
		stackTraceTextArea.setLineWrap(true);
		stackTraceTextArea.setEditable(false);
		stackTraceTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(stackTraceTextArea);
		
		JTextArea messaggioTextArea = new JTextArea();
		messaggioTextArea.setEditable(false);
		messaggioTextArea.setLineWrap(true);
		messaggioTextArea.setText(titolo + "\n\n" + messaggio + "\n\nSe l'errore non si risolve verificare che il programma sia aggiornato oppure contattare\ngli sviluppatori (preferibilmente inviando i dettagli dell'errore).");
		messaggioTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comportamentoOkButton(fatale);
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
		
		JLabel mostraDettagliLabel = new JLabel("Mostra Dettagli");
		mostraDettagliLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mostraDettagliLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!mostratoErrore) {
					stackTraceTextArea.setText(ottieniStackTrace(eccezione));
					mostraDettagliLabel.setText("Nascondi Dettagli");
					mostratoErrore = true;
				}
				else {
					stackTraceTextArea.setText("");
					mostraDettagliLabel.setText("Mostra Dettagli");
					mostratoErrore = false;
				}
			}
		});
		mostraDettagliLabel.setFont(new Font("Consolas", Font.BOLD, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(messaggioTextArea, 0, 0, Short.MAX_VALUE)
							.addGap(31))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mostraDettagliLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane))
							.addGap(31))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(messaggioTextArea, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(mostraDettagliLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(okButton)
					.addGap(7))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
