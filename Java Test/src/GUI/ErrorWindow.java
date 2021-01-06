package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ErrorWindow extends JFrame {

	//ATTRIBUTI
	//Attributi GUI
	private JPanel contentPane;
	
	//Altri attributi
	
	//METODI
	
	//Crea frame
	public ErrorWindow(String errore) {
		setTitle("Errore!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Button "Conferma"
		JButton confirmButton = new JButton("Conferma");
		//EVENTI
		confirmButton.addMouseListener(new MouseAdapter() {
			//click mouse destro
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);	//chiude il programma
			}
		});
		//proprietà
		confirmButton.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//TextArea messaggio di errore
		JTextArea errorTextArea = new JTextArea();
		errorTextArea.setEditable(false);
		errorTextArea.setLineWrap(true);
		errorTextArea.setFont(new Font("Calibri", Font.PLAIN, 16));
		errorTextArea.setText(errore);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(58, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(confirmButton)
							.addGap(159))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(errorTextArea, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
							.addGap(47))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(errorTextArea, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(confirmButton)
					.addGap(7))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
