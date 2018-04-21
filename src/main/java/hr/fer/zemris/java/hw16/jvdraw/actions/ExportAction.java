package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.utils.JvdUtil;

/**
 * ExportAction is a action which exports current {@link GeometricalObject}s
 * into a image.
 * 
 * @author Filip Klepo
 *
 */
public class ExportAction extends AbstractAction {

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
	 * @param model drawing model
	 */
	public ExportAction(JVDraw frame, DrawingModel model) {
		this.frame = frame;
		this.model = model;

		putValue(AbstractAction.NAME, "Export");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel panel = new JPanel();
		JMenu menu = new JMenu();
		panel.add(menu);

		JRadioButton gif = new JRadioButton("gif");
		JRadioButton png = new JRadioButton("png");
		JRadioButton jpg = new JRadioButton("jpg");
		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(gif);
		group.add(png);
		group.add(jpg);
		panel.add(png);
		panel.add(jpg);
		panel.add(gif);

		int res = JOptionPane.showConfirmDialog(
				frame, 
				panel, 
				"Choose Image Type", 
				JOptionPane.OK_CANCEL_OPTION);
		if(res == JOptionPane.CANCEL_OPTION) {
			return;
		}

		String extension = null;
		if(gif.isSelected()) {
			extension = "gif";
		} else if(png.isSelected()) {
			extension = "png";
		} else {
			extension = "jpg";
		}

		JFileChooser jfc = new JFileChooser();
		int res2 = jfc.showSaveDialog(frame);

		if(res2 != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = jfc.getSelectedFile();
		if(Files.isReadable(file.toPath())) {
			if(!JvdUtil.extensionIsJvd(file.toString())) {
				JOptionPane.showMessageDialog(frame, 
						"Only .jvd format of a file is supported.", 
						"Unsupported format", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int res3 = JOptionPane.showConfirmDialog(frame, 
					"Do you want to overwrite "+file.getName()+"?", 
					"Overwrite", 
					JOptionPane.YES_NO_OPTION);
			if(res3 != JOptionPane.OK_OPTION) {
				return;
			}
		}

		//append the adequate extension if user did not
		if(!file.toString().endsWith(extension)) {
			file = new File(file.toString() + "." + extension);
		}

		List<GeometricalObject> objs = getModelObjectsCopy();
		try {
			ImageIO.write(getMinimalImage(objs), extension, file);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(frame, "Saving error", 
					"Error while saving image.", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gets copy of model objects.
	 * 
	 * @return copy of model objects
	 */
	private List<GeometricalObject> getModelObjectsCopy() {
		int size = model.getSize();
		List<GeometricalObject> objs = new ArrayList<>(size);
		
		for(int i = 0; i < size; ++i) {
			objs.add(model.getObject(i).copy());
		}
		
		return objs;
	}

	/**
	 * Gets image of current objects which is filled optimally.
	 * 
	 * @param objs  objects for which image is generated
	 * @return image
	 */
	private BufferedImage getMinimalImage(List<GeometricalObject> objs) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int size = objs.size();
		for(int i = 0; i < size; ++i) {
			GeometricalObject obj = objs.get(i);
			ObjectBoundingBox obb = getBoundingBox(obj);
			
			minX = Math.min(minX, obb.minX);
			minY = Math.min(minY, obb.minY);
			maxX = Math.max(maxX, obb.maxX);
			maxY = Math.max(maxY, obb.maxY);
		}
		
		for(int i = 0; i < size; ++i) {
			GeometricalObject obj = objs.get(i);
			obj.setStartPoint(obj.getStartX() - minX, obj.getStartY() - minY);
			obj.setEndPoint(obj.getEndX() - minX, obj.getEndY() - minY);
		}

		BufferedImage image = new BufferedImage(
				maxX-minX, maxY-minY, BufferedImage.TYPE_3BYTE_BGR
				);

		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, maxX-minX, maxY-minY);

		for(int i = 0; i < size; ++i) {
			objs.get(i).paintObject(g);
		}
		g.dispose();

		return image;
	}
	
	/**
	 * Gets bounding box for given geometrical object.
	 * 
	 * @param obj geometrical object
	 * @return boundind box
	 */
	private ObjectBoundingBox getBoundingBox(GeometricalObject obj) {
		ObjectBoundingBox box = new ObjectBoundingBox();
		switch(obj.getType()) {
		case LINE:
			box.minX = Math.min(obj.getStartX(), obj.getEndX());
			box.minY = Math.min(obj.getStartY(), obj.getEndY());
			box.maxX = Math.max(obj.getStartX(), obj.getEndX());
			box.maxY = Math.max(obj.getStartY(), obj.getEndY());
			break;
		case CIRCLE:
			int cradius = ((Circle)obj).getRadius();
			box.minX = obj.getStartX() - cradius;
			box.minY = obj.getStartY() - cradius;
			box.maxX = obj.getStartX() + cradius;
			box.maxY = obj.getStartY() + cradius;
			break;
		case FILLED_CIRCLE:
			int fcradius = ((FilledCircle)obj).getRadius();
			box.minX = obj.getStartX() - fcradius;
			box.minY = obj.getStartY() - fcradius;
			box.maxX = obj.getStartX() + fcradius;
			box.maxY = obj.getStartY() + fcradius;
			break;
		default:
			break;
		}
		return box;
	}

	/**
	 * ObjectBoundingBox is a class which contains coordinates of a box which 
	 * surrounds, bounds, a {@link GeometricalObject}. 
	 * 
	 * @author Filip Klepo
	 *
	 */
	private class ObjectBoundingBox {
		/**
		 * Top left x coordinate of box.
		 */
		int minX;
		/**
		 * Top left y coordinate of box.
		 */
		int minY;
		/**
		 * Max x coordinate of box.
		 */
		int maxX;
		/**
		 * Max y coordinate of box.
		 */
		int maxY;
	}

}
