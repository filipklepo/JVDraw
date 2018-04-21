package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;

/**
 * Represents a circle. Circle as {@link GeometricalObject} has name, type,
 * center coordinate and radius.
 * 
 * @author Filip Klepo
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param startX x-Coordinate of center
	 * @param startY y-Coordinate of center
	 * @param fgColor foreground color
	 * @param name name
	 */
	public Circle(int startX, int startY,  Color fgColor, String name) {
		Point center = new Point(startX, startY);
		this.startPoint = center;
		this.endPoint = center;
		this.foregroundColor = fgColor;
		this.name = name;
	}
	
	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param startX x-Coordinate of center
	 * @param startY y-Coordinate of center
	 * @param radius radius of circle
	 * @param fgColor foreground color
	 * @param name name
	 */
	public Circle(int startX, int startY, int radius,  Color fgColor, String name) {
		this.startPoint = new Point(startX, startY);
		this.endPoint = new Point(startX + radius, startY);
		this.foregroundColor = fgColor;
		this.name = name;
	}

	@Override
	public void paintObject(Graphics g) {
		int radius = Double.valueOf(startPoint.distance(endPoint)).intValue();
		g.setColor(foregroundColor);
		g.drawOval(startPoint.x - radius, startPoint.y - radius, 2*radius, 2*radius);
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public ShapeType getType() {
		return ShapeType.CIRCLE;
	}
	
	/**
	 * Gets value of circle's radius.
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return Double.valueOf(endPoint.distance(startPoint)).intValue();
	}

	@Override
	public GeometricalObject copy() {
		return new Circle(startPoint.x, startPoint.y, getRadius(), foregroundColor, name);
	}
	
}
