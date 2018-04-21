package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * DrawingModel represents the context in which {@link GeometricalObject}s are
 * added and removed. It provides the functionality for registration of interested
 * listeners which want to keep track of current objects.
 * 
 * @author Filip Klepo
 *
 */
public interface DrawingModel {
	
	/**
	 * Gets size of drawing model's {@link GeometricalObject}s.
	 * 
	 * @return size
	 */
	public int getSize();
	
	/**
	 * Gets object at given index.
	 * 
	 * @param index index of object
	 * @return object
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Adds a geometrical object.
	 * 
	 * @param object new object
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes object at given index.
	 * 
	 * @param index index of object
	 */
	public void remove(int index);
	
	/**
	 * Adds drawing model listener.
	 * 
	 * @param l new listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes drawing model listener.
	 * 
	 * @param l removed listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Gets new line name.
	 * 
	 * @return new line name
	 */
	public String getNewLineName();
	
	/**
	 * Gets new circle name.
	 * 
	 * @return new circle name
	 */
	public String getNewCircleName();
	
	/**
	 * Gets new filled circle name.
	 * 
	 * @return filled circle name
	 */
	public String getNewFilledCircleName();
	
	/**
	 * Updates this model.
	 */
	public void update();

}