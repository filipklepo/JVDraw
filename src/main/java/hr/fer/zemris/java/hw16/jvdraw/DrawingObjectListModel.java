package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * DrawingObjectListModel is a ListModel used for adapting the usage of
 * {@link GeometricalObject}s in JLists.
 * 
 * @author Filip Klepo
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1;
	
	/**
	 * The drawing model.
	 */
	private DrawingModel model;
	
	/**
	 * Instantiates this class with given drawing model.
	 * 
	 * @param model the drawing model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);
	}

}
