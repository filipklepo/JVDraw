package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * CurrentColorsLabel is a {@link JLabel} which shows r,g and b values of both
 * current background and current foreground color.
 * 
 * @author Filip Klepo
 *
 */
public class CurrentColorsLabel extends JLabel {

	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The background color.
	 */
	private Color background;
	/**
	 * The foreground color.
	 */
	private Color foreground;
	
	/**
	 * Instantiates this class with given color.
	 * 
	 * @param background background color
	 * @param foreground foreground color
	 */
	public CurrentColorsLabel(Color background, Color foreground) {
		this.background = background;
		this.foreground = foreground;
		setText();
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	/**
	 * Sets text of this label.
	 */
	private void setText() {
		setText("Foreground color : "+getColorString(foreground)
		+", background color: "+getColorString(background)+".");
	}
	
	/**
	 * Gets desired textual form of given {@link Color}.
	 * 
	 * @param color color
	 * @return desired textual form
	 */
	private static String getColorString(Color color) {
		return "("+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+")";
	}
	
	/**
	 * Sets background color.
	 * 
	 * @param newBackground new background color
	 */
	protected void setBackgroundColor(Color newBackground) {
		background = newBackground;
		setText();
	}
	
	/**
	 * Sets foreground color.
	 * 
	 * @param newForeground new foreground color
	 */
	protected void setForegroundColor(Color newForeground) {
		foreground = newForeground;
		setText();
	}
	
}
