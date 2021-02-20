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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;

public class ErroreDialog extends JDialog {
	
    private JPanel contentPane = new JPanel();
    
    private final boolean chiudiProgramma = true;
    
    private boolean mostratoErrore = false;

	/**
	 * @wbp.parser.constructor
	 */
	public ErroreDialog(Frame frameParente, Exception eccezione, String titolo, String messaggio, boolean chiudiProgramma) {
		setMinimumSize(new Dimension(555, 273));
		generaGUI(frameParente, eccezione, titolo, messaggio, chiudiProgramma);
	}
	
	public ErroreDialog(Frame frameParente, Exception eccezione, boolean chiudiProgramma) {
	    String messaggio = "Errore Fatale!\r\n\r\nVerificare che il programma sia aggiornato altrimenti contattare gli sviluppatori (preferibilmente inviando i dettagli dell'errore).";
	    String titolo = "Errore Fatale";
	    generaGUI(frameParente, eccezione, titolo, messaggio, chiudiProgramma);
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
	
	private void generaGUI(Frame frameParente, Exception eccezione, String titolo, String messaggio, boolean chiudiProgramma) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ErroreDialog.class.getResource("/icone/fatalError.png")));
		setTitle(titolo);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 555, 273);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
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
		messaggioTextArea.setText(titolo + "\n\n" + messaggio);
		messaggioTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		
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
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
							.addGap(31))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(messaggioTextArea, 0, 0, Short.MAX_VALUE)
							.addGap(31))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mostraDettagliLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(messaggioTextArea, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(mostraDettagliLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(12))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
