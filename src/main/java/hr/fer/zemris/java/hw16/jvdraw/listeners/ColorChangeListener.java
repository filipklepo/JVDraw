package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

/**
 * Listener which is used for listening to color change events.
 * 
 * @author Filip Klepo
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Invoked when selection of new color occured.
	 * 
	 * @param source source object
	 * @param oldColor value of old color
	 * @param newColor value of new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}
