package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.utils.JvdUtil;

/**
 * SaveAction is a {@link javax.swing.Action} whose task is to save currently
 * opened .jvd file if it has been modified.
 * 
 * @author Filip Klepo
 *
 */
public class SaveAction extends AbstractAction {

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
	public SaveAction(JVDraw frame, DrawingModel model) {
		this.frame = frame;
		this.model = model;

		putValue(AbstractAction.NAME, "Save");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JvdUtil.save(frame, model);
	}
	
}
