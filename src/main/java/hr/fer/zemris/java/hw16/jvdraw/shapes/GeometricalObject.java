package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;

/**
 * GeometricalObject is a class which represents a abstract geometric object.
 * Each instance of this abstract class has its coordinates and drawing colors.
 * Also, it can paint itself on given {@link Graphics}.
 * 
 * @author Filip Klepo
 *
 */
public abstract class GeometricalObject {

	/**
	 * Start point.
	 */
	Point startPoint;
	/**
	 * End point.
	 */
	Point endPoint;
	/**
	 * Background color.
	 */
	Color backgroundColor;
	/**
	 * Foreground color.
	 */
	Color foregroundColor;
	/**
	 * Name.
	 */
	String name;
	
	/**
	 * Paints itself on given graphics.
	 * 
	 * @param g graphics used for painting
	 */
	public abstract void paintObject(Graphics g);
	
	/**
	 * Gets shape type.
	 * 
	 * @return shape type
	 */
	public abstract ShapeType getType();
	
	/**
	 * Gets start point.
	 * 
	 * @return start point
	 */
	public Point getStartPoint() {
		return new Point(startPoint);
	}
	
	/**
	 * Gets end point.
	 * 
	 * @return end point
	 */
	public Point getEndPoint() {
		return new Point(endPoint);
	}
	
	/**
	 * Gets start x-Coordinate.
	 * 
	 * @return start x-Coordinate
	 */
	public int getStartX() {
		return doubleToInt(startPoint.getX());
	}
	
	/**
	 * Gets start y-Coordinate.
	 * 
	 * @return start y-Coordinate
	 */
	public int getStartY() {
		return doubleToInt(startPoint.getY());
	}
	
	/**
	 * Gets end x-Coordinate.
	 * 
	 * @return end x-Coordinate
	 */
	public int getEndX() {
		return doubleToInt(endPoint.getX());
	}
	
	/**
	 * Gets end y-Coordinate.
	 * 
	 * @return end y-Coordinate
	 */
	public int getEndY() {
		return doubleToInt(endPoint.getY());
	}
	
	/**
	 * Sets start point-
	 * 
	 * @param x new x 
	 * @param y new y
	 */
	public void setStartPoint(int x, int y) {
		startPoint = new Point(x, y);
	}
	
	/**
	 * Sets end point-
	 * 
	 * @param x new x 
	 * @param y new y
	 */
	public void setEndPoint(int x, int y) {
		endPoint = new Point(x, y);
	}
	
	/**
	 * Gets background color.
	 * 
	 * @return background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Gets foreground color.
	 * 
	 * @return foreground color
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}
	
	/**
	 * Sets background color.
	 * 
	 * @param color background color
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}
	
	/**
	 * Sets foreground color.
	 * 
	 * @param color foreground color
	 */
	public void setForegroundColor(Color color) {
		foregroundColor = color;
	}
	
	/**
	 * Gets distance in integers between given points.
	 * 
	 * @param p1 first point
	 * @param p2 second point
	 * @return distance
	 */
	public int distance(Point p1, Point p2) {
		return Double.valueOf(p1.distance(p2)).intValue();
	}
	
	/**
	 * Transforms given double value to int value.
	 * 
	 * @param num double value
	 * @return int value
	 */
	private int doubleToInt(double num) {
		return Double.valueOf(num).intValue();
	}
	
	/**
	 * Gets exact copy of this geometrical object.
	 * 
	 * @return copy
	 */
	public abstract GeometricalObject copy();
	
}
