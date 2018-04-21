package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;

/**
 * DrawingModelListener is a listener used in events in {@link DrawingModel}'s 
 * context.
 * 
 * @author Filip Klepo
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Invoked when new objects have been added.
	 * 
	 * @param source object source
	 * @param index0 start index
	 * @param index1 end index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Invoked when objects have been removed.
	 * 
	 * @param source object source
	 * @param index0 start index
	 * @param index1 end index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Invoked when objects have been changed.
	 * 
	 * @param source object source
	 * @param index0 start index
	 * @param index1 end index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
