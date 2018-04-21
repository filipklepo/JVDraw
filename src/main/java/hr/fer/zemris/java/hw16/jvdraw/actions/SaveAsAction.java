package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.utils.JvdUtil;

/**
 * SaveAsAction is a {@link javax.swing.Action} whose task is to save currently shown
 * GeometricalObjects  in form of a.jvd file.
 * 
 * @author Filip Klepo
 */
public class SaveAsAction extends AbstractAction {

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
	 * @param model the model
	 */
	public SaveAsAction(JVDraw frame, DrawingModel model) {
		this.frame = frame;
		this.model = model;

		putValue(AbstractAction.NAME, "Save As");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JvdUtil.saveAs(frame, model);
	}

}
