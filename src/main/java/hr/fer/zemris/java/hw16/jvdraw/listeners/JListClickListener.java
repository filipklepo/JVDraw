package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * JListClickListener is a {@link MouseAdapter} which listens for double clicks
 * on its {@link JList} parent.
 * 
 * @author Filip Klepo
 *
 */
public class JListClickListener extends MouseAdapter {

	/**
	 * The drawing model-
	 */
	private DrawingModel model;
	/**
	 * The list parent.
	 */
	private JList<GeometricalObject> list;

	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param model drawing model
	 * @param list list of geometrical objects
	 */
	public JListClickListener(DrawingModel model, JList<GeometricalObject> list) {
		this.model = model;
		this.list = list;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getClickCount() != 2 || !(e.getSource() instanceof JList<?>) || model.getSize() == 0) {
			return;
		}

		int index = list.getSelectedIndex();
		if(index==-1) return;
		GeometricalObject object = model.getObject(index);

		if(object != null) {
			//get user's dimensions request and evaluate it if possible
			UserDimensionsRequest request = getUsersDimensionRequest(object);
			if(request != null) {
				request.updateGeometricObject(object);
				model.update();
			}
		}
	}

	/**
	 * Gets user's dimensions request.
	 * 
	 * @param object geometrical object
	 * @return user's dimensions request
	 */
	private UserDimensionsRequest getUsersDimensionRequest(GeometricalObject object) {
		//used for collecting start point
		JTextField tfFirst1 = new JTextField();
		JTextField tfFirst2 = new JTextField();
		//used to collect end point, or radius (only first used in that case)
		JTextField tfSecond1 = new JTextField();
		JTextField tfSecond2 = new JTextField();
		//used for collecting background color (used only if needed)
		JColorChooser firstColorChooser = new JColorChooser();
		//used for collecting foreground color
		JColorChooser secondColorChooser = new JColorChooser();
		JPanel panel = new JPanel();
		JPanel selectionPanel = new JPanel();
		int res;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		UserDimensionsRequest request = new UserDimensionsRequest();

		switch(object.getType()) {
		case LINE:
			Line line = (Line)object;
			panel.add(selectionPanel);
			selectionPanel.setLayout(new GridLayout(2, 2));
			selectionPanel.add(new JLabel("Start x"));
			tfFirst1.setText(line.getStartX()+"");
			selectionPanel.add(tfFirst1);
			selectionPanel.add(new JLabel("Start y"));
			tfFirst2.setText(line.getStartY()+"");
			selectionPanel.add(tfFirst2);
			selectionPanel.add(new JLabel("End x"));
			tfSecond1.setText(line.getEndX()+"");
			selectionPanel.add(tfSecond1);
			selectionPanel.add(new JLabel("End y"));
			tfSecond2.setText(line.getEndY()+"");
			selectionPanel.add(tfSecond2);
			panel.add(new JLabel("Color"));
			firstColorChooser.setColor(line.getForegroundColor());
			panel.add(firstColorChooser);

			res = JOptionPane.showConfirmDialog(
					list, 
					panel, 
					"Edit line attributes", 
					JOptionPane.OK_CANCEL_OPTION);
			if(res != JOptionPane.OK_OPTION) {
				break;
			}

			request.startX = tfFirst1.getText();
			request.startY = tfFirst2.getText();
			request.endX = tfSecond1.getText();
			request.endY = tfSecond2.getText();
			request.type = object.getType();
			request.fgColor = firstColorChooser.getColor();
			return request;
		case CIRCLE:
			Circle circle = (Circle)object;

			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(selectionPanel);
			selectionPanel.setLayout(new GridLayout(3, 2));
			selectionPanel.add(new JLabel("Center x"));
			tfFirst1.setText(circle.getStartX()+"");
			selectionPanel.add(tfFirst1);
			selectionPanel.add(new JLabel("Center y"));
			tfFirst2.setText(circle.getStartY()+"");
			selectionPanel.add(tfFirst2);
			selectionPanel.add(new JLabel("Radius"));
			tfSecond1.setText(circle.getRadius()+"");
			selectionPanel.add(tfSecond1);
			panel.add(new JLabel("Color"));
			firstColorChooser.setColor(circle.getForegroundColor());
			panel.add(firstColorChooser);

			res = JOptionPane.showConfirmDialog(
					list, 
					panel, 
					"Edit circle attributes", 
					JOptionPane.OK_CANCEL_OPTION);
			if(res != JOptionPane.OK_OPTION) {
				break;
			}

			request.startX = tfFirst1.getText();
			request.startY = tfFirst2.getText();
			request.radius = tfSecond1.getText();
			request.type = object.getType();
			request.fgColor = firstColorChooser.getColor();
			return request;
		case FILLED_CIRCLE:
			FilledCircle filledCircle = (FilledCircle)object;

			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(selectionPanel);
			selectionPanel.setLayout(new GridLayout(3, 2));
			selectionPanel.add(new JLabel("Center x"));
			tfFirst1.setText(filledCircle.getStartX()+"");
			selectionPanel.add(tfFirst1);
			selectionPanel.add(new JLabel("Center y"));
			tfFirst2.setText(filledCircle.getStartY()+"");
			selectionPanel.add(tfFirst2);
			selectionPanel.add(new JLabel("Radius"));
			tfSecond1.setText(filledCircle.getRadius()+"");
			selectionPanel.add(tfSecond1);
			panel.add(new JLabel("Outline Color"));
			firstColorChooser.setColor(filledCircle.getForegroundColor());
			panel.add(firstColorChooser);
			panel.add(new JLabel("Area Color"));
			secondColorChooser.setColor(filledCircle.getBackgroundColor());
			panel.add(secondColorChooser);

			int res2 = JOptionPane.showConfirmDialog(
					list, 
					panel, 
					"Edit circle attributes", 
					JOptionPane.OK_CANCEL_OPTION);
			if(res2 != JOptionPane.OK_OPTION) {
				break;
			}

			request.startX = tfFirst1.getText();
			request.startY = tfFirst2.getText();
			request.radius = tfSecond1.getText();
			request.type = object.getType();
			request.fgColor = firstColorChooser.getColor();
			request.bgColor = secondColorChooser.getColor();
			return request;
		default:
			break;
		}


		return null;
	}

	/**
	 * The class which models a user's request for new dimensions of a 
	 * {@link GeometricalObject}.
	 * 
	 * @author Filip Klepo
	 *
	 */
	private class UserDimensionsRequest {

		/**
		 * Start x-Coordinate.
		 */
		String startX;
		/**
		 * Start y-Coordinate.
		 */
		String startY;
		/**
		 * End x-Coordinate.
		 */
		String endX;
		/**
		 * End y-Coordinate.
		 */
		String endY;
		/**
		 * Radius of object.
		 */
		String radius;
		/**
		 * Shape type.
		 */
		ShapeType type;
		/**
		 * Foreground color.
		 */
		Color fgColor;
		/**
		 * Background color.
		 */
		Color bgColor;


		/**
		 * The default constructor.
		 */
		public UserDimensionsRequest() {}

		/**
		 * Updates geometrical object with user's requests. If request is valid
		 * and object is updated, <b>true</b> is returned.
		 * 
		 * @param object geometrical object
		 * @return <b>true</b> if request is valid and object is updated
		 */
		public boolean updateGeometricObject(GeometricalObject object) {
			switch(type) {
			case LINE:
				try {
					Integer startXNum = Integer.parseInt(startX);
					Integer startYNum = Integer.parseInt(startY);
					Integer endXNum = Integer.parseInt(endX);
					Integer endYNum = Integer.parseInt(endY);
					object.setStartPoint(startXNum, startYNum);
					object.setEndPoint(endXNum, endYNum);
					object.setForegroundColor(fgColor);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(list, 
							e.getMessage(), 
							"Invalid parameter format", 
							JOptionPane.ERROR_MESSAGE);
					return false;
				}


				break;
			case CIRCLE:
				try {
					Integer centerXNum = Integer.parseInt(startX);
					Integer centerYNum = Integer.parseInt(startY);
					Integer radiusNum = Integer.parseInt(radius);
					object.setStartPoint(centerXNum, centerYNum);
					object.setEndPoint(centerXNum, centerYNum + radiusNum);
					object.setForegroundColor(fgColor);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(list, 
							e.getMessage(), 
							"Invalid parameter format", 
							JOptionPane.ERROR_MESSAGE);
					return false;
				}

				break;
			case FILLED_CIRCLE:
				try {
					Integer centerXNum = Integer.parseInt(startX);
					Integer centerYNum = Integer.parseInt(startY);
					Integer radiusNum = Integer.parseInt(radius);
					object.setStartPoint(centerXNum, centerYNum);
					object.setEndPoint(centerXNum, centerYNum + radiusNum);
					object.setForegroundColor(fgColor);
					object.setBackgroundColor(bgColor);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(list, 
							e.getMessage(), 
							"Invalid parameter format", 
							JOptionPane.ERROR_MESSAGE);
					return false;
				}

				break;
			default:
				break;

			}
			return true;
		}

	}

}
