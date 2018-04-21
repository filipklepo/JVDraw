package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;

/**
 * Line is a {@link GeometricalObject} which has its start coordinate, end 
 * coordinate and name.
 * 
 * @author Filip Klepo
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param startX x-Coordinate of start
	 * @param startY y-Coordinate of start
	 * @param fgColor foreground color
	 * @param name name
	 */
	public Line(int startX, int startY, Color fgColor, String name) {
		this(startX, startY, startX, startY, fgColor, name);
	}

	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param startX x-Coordinate of start
	 * @param startY y-Coordinate of start
	 * @param endX x-Coordinate of end
	 * @param endY y-Coordinate of end
	 * @param fgColor foreground color
	 * @param name name
	 */
	public Line(int startX, int startY, int endX, int endY, Color fgColor, String name) {
		this.startPoint = new Point(startX, startY);
		this.endPoint = new Point(endX, endY);
		this.foregroundColor = fgColor;
		this.name = name;
	}

	@Override
	public void paintObject(Graphics g) {
		g.setColor(foregroundColor);

		g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public ShapeType getType() {
		return ShapeType.LINE;
	}

	@Override
	public GeometricalObject copy() {
		return new Line(getStartX(), getStartY(), getEndX(), 
				getEndY(), foregroundColor, name);
	}

}
