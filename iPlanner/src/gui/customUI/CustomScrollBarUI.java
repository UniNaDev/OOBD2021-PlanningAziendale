package gui.customUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI {
	
	//metodo che crea un bottone trasparente da mettere al posto delle frecce
	protected JButton creaBottoneVuoto() {
	    JButton button = new JButton();
	    
	    //imposta una dimensione di 0,0 e la assegna al bottone
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    
	    return button;
	}
	
    @Override
    //metodo per disegnare il percorso della scrollBar
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        
        //setta il colore dello sfondo e dei bordi come bianco
        g.setColor(Color.WHITE);
        g2d.fill(trackBounds);
        g2d.draw(trackBounds);
    }
    @Override
    //Metodo per disegnare la scrollBar stessa
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        
        //imposta il colore come light gray
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(thumbBounds);
    }
    
    //metodo che imposta come bottone di decremento un bottone vuoto
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return creaBottoneVuoto();
    }
    
    //metodo che imposta come bottone di incremento un bottone vuoto
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return creaBottoneVuoto();
    }
}
