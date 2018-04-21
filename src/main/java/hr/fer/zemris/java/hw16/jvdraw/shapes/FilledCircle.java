package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;

/**
 * Represents a filled circle. Filled circle as {@link GeometricalObject} has name, type,
 * center coordinate and radius.
 * 
 * @author Filip Klepo
 *
 */
public class FilledCircle extends GeometricalObject {

	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param startX x-Coordinate of center
	 * @param startY y-Coordinate of center
	 * @param bgColor background color
	 * @param fgColor foreground color
	 * @param name name
	 */
	public FilledCircle(int startX, int startY,  Color bgColor, Color fgColor, String name) {
		Point center = new Point(startX, startY);
		this.startPoint = center;
		this.endPoint = center;
		this.backgroundColor = bgColor;
		this.foregroundColor = fgColor;
		this.name = name;
	}
	
	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param startX x-Coordinate of center
	 * @param startY y-Coordinate of center
	 * @param radius radius of this filled circle
	 * @param bgColor background color
	 * @param fgColor foreground color
	 * @param name name
	 */
	public FilledCircle(int startX, int startY, int radius,  Color bgColor, Color fgColor, String name) {
		this.startPoint = new Point(startX, startY);
		this.endPoint = new Point(startX + radius, startY);
		this.backgroundColor = bgColor;
		this.foregroundColor = fgColor;
		this.name = name;
	}
	
	@Override
	public void paintObject(Graphics g) {
		int radius = Double.valueOf(startPoint.distance(endPoint)).intValue();
		
		g.setColor(backgroundColor);
		g.fillOval(startPoint.x - radius, startPoint.y - radius, 2*radius, 2*radius);
		
		g.setColor(foregroundColor);
		g.drawOval(startPoint.x - radius, startPoint.y - radius, 2*radius, 2*radius);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public ShapeType getType() {
		return ShapeType.FILLED_CIRCLE;
	}
	
	/**
	 * Gets radius of this filled circle.
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return Double.valueOf(endPoint.distance(startPoint)).intValue();
	}
	
	@Override
	public GeometricalObject copy() {
		return new FilledCircle(startPoint.x, startPoint.y, getRadius(), 
				backgroundColor, foregroundColor, name);
	}

}