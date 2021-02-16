//Manager che si occupa di inizializzare alcune proprietà estetiche di default del programma.

package gui.customUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

public class DefaultLookManager {
    private final Font defaultStandardFont = new Font("Consolas", Font.PLAIN, 15);
    
    public void setDefaultLook() {
	UIManager.put("OptionPane.messageFont", defaultStandardFont);
	UIManager.put("OptionPane.buttonFont", defaultStandardFont);

	UIManager.put("Button.background", Color.WHITE);
	UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	UIManager.put("Button.border", new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
    }
}
