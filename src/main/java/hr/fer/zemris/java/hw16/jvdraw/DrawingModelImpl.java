package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * DrawingModelImpl is a {@link DrawingModel} implementation. One can add new
 * objects to this model, register itself as a listener, deregister, and so on.
 * 
 * @author Filip Klepo
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * Current {@link GeometricalObject}s.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * Current {@link DrawingModelListener}s.
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);

		listeners.forEach(t -> t.objectsAdded(this, getSize()-1, getSize()-1));
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void remove(int index) {
		objects.remove(index);

		listeners.forEach(t -> t.objectsRemoved(this, index, index));
	}

	/**
	 * Generates name for a new line.
	 * 
	 * @return new line name
	 */
	public String getNewLineName() {
		List<GeometricalObject> lines = getObjectsSortedByName(ShapeType.LINE);
		int size = lines.size();
		Integer index = 1;
		for(int i = 0; i < size; ++i) {
			if(!lines.get(i).toString().endsWith(index.toString())) {
				break;
			} else {
				index +=1;
			}
		}
		return "Line "+index;
	}

	/**
	 * Generates name for a new circle.
	 * 
	 * @return new circle name
	 */
	public String getNewCircleName() {
		List<GeometricalObject> circles = getObjectsSortedByName(ShapeType.CIRCLE);
		int size = circles.size();
		Integer index = 1;
		for(int i = 0; i < size; ++i) {
			if(!circles.get(i).toString().endsWith(index.toString())) {
				break;
			} else {
				index +=1;
			}
		}
		return "Circle "+index;
	}

	/**
	 * Generates name for a new filled circle.
	 * 
	 * @return new filled circle name
	 */
	public String getNewFilledCircleName() {
		List<GeometricalObject> filledCircles = getObjectsSortedByName(ShapeType.FILLED_CIRCLE);
		int size = filledCircles.size();
		Integer index = 1;
		for(int i = 0; i < size; ++i) {
			if(!filledCircles.get(i).toString().endsWith(index.toString())) {
				System.out.println(filledCircles.get(i));
				break;
			} else {
				index +=1;
			}
		}
		return "Filled circle "+index;
	}
	
	/**
	 * Gets current objects sorted by name in ascending order, filtered by 
	 * given shape types.
	 * 
	 * @param types allowed types of objects
	 * @return current sorted filtered objects
	 */
	private List<GeometricalObject> getObjectsSortedByName(ShapeType... types) {
		List<ShapeType> typesList = Arrays.asList(types);
		
		return objects
				.stream()
				.filter(t -> typesList.contains(t.getType()))
				.sorted(new GeomObjsComparator())
				.collect(Collectors.toList());
	}
	
	/**
	 * Comparator used for sorting of {@link GeometricalObject}s.
	 * 
	 * @author Filip Klepo
	 *
	 */
	private class GeomObjsComparator implements Comparator<GeometricalObject> {
		
		@Override
		public int compare(GeometricalObject o1, GeometricalObject o2) {
			Integer index1 = Integer.parseInt(o1.toString().replaceAll("[^0-9]", ""));
			Integer index2 = Integer.parseInt(o2.toString().replaceAll("[^0-9]", ""));
			
			return index1.compareTo(index2);
		}
		
	}

	@Override
	public void update() {
		listeners.forEach(t -> t.objectsChanged(this, 0, objects.size()));
	}
	
}
