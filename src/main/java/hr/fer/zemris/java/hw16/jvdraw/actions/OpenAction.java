package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.utils.JvdUtil;

/**
 * OpenAction is {@link javax.swing.Action} whose task is to open and load a
 * .jvd file into {@link JVDraw}.
 * 
 * @author Filip Klepo
 *
 */
public class OpenAction extends AbstractAction {

	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The frame.
	 */
	private JVDraw frame;
	/**
	 * The drawing model.
	 */
	private DrawingModel model;

	/**
	 * Instantiates this class with given parameters.
	 * 
	 * @param frame the frame
	 * @param model the drawing model
	 */
	public OpenAction(JVDraw frame, DrawingModel model) {
		this.frame = frame;
		this.model = model;

		putValue(AbstractAction.NAME, "Open");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(frame);
		File file = jfc.getSelectedFile();
		if(!JvdUtil.extensionIsJvd(file.toString())) {
			JOptionPane.showMessageDialog(frame, 
					"Only .jvd format of a file is supported.", 
					"Unsupported format", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<GeometricalObject> oldOnes = clearDrawingModel();
		List<GeometricalObject> objects = parseFile(file);
		if(!Objects.isNull(objects)) {
			updateDrawingModel(objects);
		} else {
			JOptionPane.showMessageDialog(frame, "Unable to parse file.", 
					"Parsing error", JOptionPane.ERROR_MESSAGE);
			objects = oldOnes;
		}
		updateDrawingModel(objects);
	}

	/**
	 * Clears drawing model and returns list of removed objects.
	 * 
	 * @return removed objects
	 */
	private List<GeometricalObject> clearDrawingModel() {
		List<GeometricalObject> oldOnes = new ArrayList<>();

		while(model.getSize() > 0) {
			oldOnes.add(model.getObject(0));
			model.remove(0);
		}
		
		return oldOnes;
	}

	/**
	 * Updates drawing model with given list of {@link GeometricalObject}s.
	 * 
	 * @param objects objects
	 */
	private void updateDrawingModel(List<GeometricalObject> objects) {
		for(GeometricalObject obj : objects) {
			model.add(obj);
		}
	}

	/**
	 * Parses given readable file for {@link GeometricalObject}s which are
	 * in textual form in file. If error occurs, null is returned.
	 * 
	 * @param file input file
	 * @return list of objects, or <b>null</b> if error occurs
	 */
	private List<GeometricalObject> parseFile(File file) {
		String[] lines = null;
		try {
			lines = new String(Files.readAllBytes(file.toPath()), "UTF-8")
					.split(System.lineSeparator());
		} catch (IOException e) {
			return null;
		}

		List<GeometricalObject> objects = new ArrayList<>();
		for(String line : lines) {
			String[] objParts = line.split(" ");
			if(objParts[0].equals("LINE")) {
				if(objParts.length != 8) continue;

				try {
					Integer startX = Integer.parseInt(objParts[1]);
					Integer startY = Integer.parseInt(objParts[2]);
					Integer endX = Integer.parseInt(objParts[3]);
					Integer endY = Integer.parseInt(objParts[4]);
					Integer r = Integer.parseInt(objParts[5]);
					Integer g = Integer.parseInt(objParts[6]);
					Integer b = Integer.parseInt(objParts[7]);

					objects.add(
							new Line(startX, startY, endX, endY, 
									new Color(r, g, b), model.getNewLineName())
							);
				} catch (NumberFormatException e) {
					return null;
				}

			} else if(objParts[0].equals("CIRCLE")) {
				if(objParts.length != 7) continue;

				try {
					Integer centerX = Integer.parseInt(objParts[1]);
					Integer centerY = Integer.parseInt(objParts[2]);
					Integer radius = Integer.parseInt(objParts[3]);
					Integer r = Integer.parseInt(objParts[4]);
					Integer g = Integer.parseInt(objParts[5]);
					Integer b = Integer.parseInt(objParts[6]);

					objects.add(
							new Circle(centerX, centerY, radius, 
									new Color(r, g, b), model.getNewCircleName())
							);
				} catch (NumberFormatException e) {
					return null;
				}
			} else if(objParts[0].equals("FCIRCLE")) {
				if(objParts.length != 10) continue;

				try {
					Integer centerX = Integer.parseInt(objParts[1]);
					Integer centerY = Integer.parseInt(objParts[2]);
					Integer radius = Integer.parseInt(objParts[3]);
					Integer fgR = Integer.parseInt(objParts[4]);
					Integer fgG = Integer.parseInt(objParts[5]);
					Integer fgB = Integer.parseInt(objParts[6]);
					Integer bgR = Integer.parseInt(objParts[7]);
					Integer bgG = Integer.parseInt(objParts[8]);
					Integer bgB = Integer.parseInt(objParts[9]);

					objects.add(
							new FilledCircle(centerX, centerY, radius, 
									new Color(fgR, fgG, fgB), new Color(bgR, bgG, bgB), 
									model.getNewFilledCircleName())
							);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}

		return objects;
	}

}
