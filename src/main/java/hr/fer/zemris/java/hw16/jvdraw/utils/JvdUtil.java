package hr.fer.zemris.java.hw16.jvdraw.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * JvdUtil is a utility class used for manipulation with .jvd files by this 
 * application.
 * 
 * @author Filip Klepo
 *
 */
public final class JvdUtil {

	/**
	 * The default constructor.
	 */
	private JvdUtil() {}

	/**
	 * Saves current {@link JVDraw}.
	 * 
	 * @param jvd current JVDraw
	 * @param model current drawing model
	 */
	public static void save(JVDraw jvd, DrawingModel model) {
		if(Objects.isNull(jvd.getJvdFile())) {
			saveAs(jvd, model);
			return;
		}

		File file = jvd.getJvdFile();
		String text = getModelAsText(model);


		try {
			Files.write(file.toPath(), text.getBytes(StandardCharsets.UTF_8));
			jvd.setEdited(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jvd, "Saving error", 
					"Error while saving file.", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves current {@link JVDraw} as new file.
	 * 
	 * @param jvd current JVDraw
	 * @param model current drawing model
	 */
	public static void saveAs(JVDraw jvd, DrawingModel model) {
		JFileChooser jfc = new JFileChooser();
		int res = jfc.showSaveDialog(jvd);

		if(res != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = jfc.getSelectedFile();
		if(Files.isReadable(file.toPath())) {
			if(!extensionIsJvd(file.toString())) {
				JOptionPane.showMessageDialog(jvd, 
						"Only .jvd format of a file is supported.", 
						"Unsupported format", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int res2 = JOptionPane.showConfirmDialog(jvd, 
					"Do you want to overwrite "+file.getName()+"?", 
					"Overwrite", 
					JOptionPane.YES_NO_OPTION);
			if(res2 != JOptionPane.OK_OPTION) {
				return;
			}
		}

		//append the adequate extension if user did not
		if(!file.toString().endsWith(".jvd")) {
			file = new File(file.toString() +".jvd");
		}

		jvd.setJvdFile(file);
		save(jvd, model);
	}

	/**
	 * Gets textual representation of given {@link DrawingModel}.
	 * 
	 * @param model drawing model
	 * @return textual representation
	 */
	private static String getModelAsText(DrawingModel model) {
		StringBuilder sb = new StringBuilder();
		int size = model.getSize();
		for(int i = 0; i < size; ++i) {
			GeometricalObject obj = model.getObject(i);
			switch(obj.getType()) {
			case LINE:
				sb.append("LINE ");
				sb.append(obj.getStartX()).append(" ");
				sb.append(obj.getStartY()).append(" ");
				sb.append(obj.getEndX()).append(" ");
				sb.append(obj.getEndY()).append(" ");
				sb.append(obj.getForegroundColor().getRed()).append(" ");
				sb.append(obj.getForegroundColor().getGreen()).append(" ");
				sb.append(obj.getForegroundColor().getBlue());

				break;
			case CIRCLE:
				Circle circle = (Circle)obj;
				sb.append("CIRCLE ");
				sb.append(obj.getStartX()).append(" ");
				sb.append(obj.getStartY()).append(" ");
				sb.append(circle.getRadius()).append(" ");
				sb.append(obj.getForegroundColor().getRed()).append(" ");
				sb.append(obj.getForegroundColor().getGreen()).append(" ");
				sb.append(obj.getForegroundColor().getBlue());

				break;
			case FILLED_CIRCLE:
				FilledCircle filledCircle = (FilledCircle)obj;
				sb.append("FCIRCLE ");
				sb.append(obj.getStartX()).append(" ");
				sb.append(obj.getStartY()).append(" ");
				sb.append(filledCircle.getRadius()).append(" ");
				sb.append(obj.getForegroundColor().getRed()).append(" ");
				sb.append(obj.getForegroundColor().getGreen()).append(" ");
				sb.append(obj.getForegroundColor().getBlue()).append(" ");
				sb.append(obj.getBackgroundColor().getRed()).append(" ");
				sb.append(obj.getBackgroundColor().getGreen()).append(" ");
				sb.append(obj.getBackgroundColor().getBlue());

				break;
			default:
			}
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	/**
	 * Checks if given path has .jvd extension.
	 * 
	 * @param path path
	 * @return <b>true</b> if path has .jvd extension
	 */
	public static boolean extensionIsJvd(String path) {
		String extension = path.substring(path.lastIndexOf(".") + 1, path.length());

		return extension.equals("jvd");
	}
	
	/**
	 * Exits the JVDraw.
	 * 
	 * @param jvd JVDraw
	 * @param model drawing model
	 */
	public static void exit(JVDraw jvd, DrawingModel model) {
		if(jvd.isEdited()) {
			save(jvd, model);
		}
	}

}
