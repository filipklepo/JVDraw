package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * JColorArea is a {@link JComponent} which enables to users the setting of a new
 * color. JColorArea provides that functionality through {@link JColorChooser}.
 * 
 * @author Filip Klepo
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Serial version UID of this class.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Default width of this component.
	 */
	private final static int DEFAULT_WIDTH = 15;
	/**
	 * Default height of this component.
	 */
	private final static int DEFAULT_HEIGHT = 15;
	/**
	 * Currently selected painting color.
	 */
	private Color selectedColor;
	/**
	 * Currently registered color change listeners.
	 */
	private List<ColorChangeListener> colorChangeListeners = new ArrayList<>();

	/**
	 * Constructs instance of this class with given parameters.
	 * 
	 * @param selectedColor initially selected color of this component
	 */
	public JColorArea(Color selectedColor) {
		Objects.requireNonNull(selectedColor);
		this.selectedColor = selectedColor;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chooseNewColor();
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_WIDTH);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Adds color change listener.
	 * 
	 * @param l new color change listener
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l);
		colorChangeListeners.add(l);
	}

	/**
	 * Removes color change listener.
	 * 
	 * @param l removed color change listener
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.remove(l);
	}
	
	/**
	 * Fires color change event-
	 * 
	 * @param oldColor old color
	 * @param newColor new color
	 */
	private void fireColorChangeEvent(Color oldColor, Color newColor) {
		colorChangeListeners.forEach(t -> {
		t.newColorSelected(JColorArea.this, oldColor, newColor);
		});
	}

	/**
	 * Chooses new color based on user's selection.
	 */
	protected void chooseNewColor() {
		Color newColor = JColorChooser.showDialog(
				null,
				"Select New Color",
				selectedColor);
		
		if(newColor != null) {
			Color oldColor = selectedColor;
			selectedColor = newColor;
			repaint();
			fireColorChangeEvent(oldColor, newColor);
		}
	}

}
