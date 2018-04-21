package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;

/**
 * Provides users with current {@link ShapeType}.
 * 
 * @author Filip Klepo
 *
 */
public interface IShapeProvider {

	/**
	 * Gets current shape type.
	 * 
	 * @return shape type
	 */
	public ShapeType getShapeType();
	
}
