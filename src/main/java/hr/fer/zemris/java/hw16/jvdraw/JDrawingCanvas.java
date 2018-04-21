package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * JDrawingCanvas is the central {@link JVDraw} component. Its task is to render
 * {@link GeometricalObject}s stored in {@link DrawingModel} and to render
 * currently drawed {@link GeometricalObject} each time user clicks and drags
 * mouse pointer.
 * 
 * @author Filip Klepo
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The drawed component.
	 */
	private GeometricalObject drawedComponent;
	/**
	 * The shape provider.
	 */
	@SuppressWarnings("unused")
	private IShapeProvider shapeProvider;
	/**
	 * The background color provider.
	 */
	@SuppressWarnings("unused")
	private IColorProvider bgColorProvider;
	/**
	 * The foreground color provider.
	 */
	@SuppressWarnings("unused")
	private IColorProvider fgColorProvider;
	/**
	 * The drawing model.
	 */
	private DrawingModel drawingModel;

	/**
	 * Instantiates this class with given arguments.
	 * 
	 * @param drawingModel the drawing model
	 * @param shapeProvider the shape provider
	 * @param bgColorProvider the background color provider
	 * @param fgColorProvider the foreground color provider
	 */
	public JDrawingCanvas(DrawingModel drawingModel, IShapeProvider shapeProvider, IColorProvider bgColorProvider, IColorProvider fgColorProvider) {
		this.shapeProvider = shapeProvider;
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider = fgColorProvider;
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				switch(shapeProvider.getShapeType()) {
				case LINE:
					drawedComponent = new Line(e.getX(), e.getY(), fgColorProvider.getCurrentColor(), drawingModel.getNewLineName());
					drawedComponent.setEndPoint(e.getX(), e.getY());
					repaint();
					break;
				case CIRCLE:
					drawedComponent = new Circle(e.getX(), e.getY(), fgColorProvider.getCurrentColor(), drawingModel.getNewCircleName());
					drawedComponent.setEndPoint(e.getX(), e.getY());
					repaint();
					break;
				case FILLED_CIRCLE:
					drawedComponent = new FilledCircle(e.getX(), e.getY(), bgColorProvider.getCurrentColor(), fgColorProvider.getCurrentColor(), drawingModel.getNewFilledCircleName());
					drawedComponent.setEndPoint(e.getX(), e.getY());
					repaint();
					break;
				case NONE:
					break;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(drawedComponent != null) {
					drawingModel.add(drawedComponent);
					drawedComponent = null;
				}
			}

		});
		addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(drawedComponent != null) {
					drawedComponent.setEndPoint(e.getX(), e.getY());
					repaint();
				}
			}

		});
		
	}


	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		int size = drawingModel.getSize();
		for(int i = 0; i < size; ++i) {
			drawingModel.getObject(i).paintObject(g);
		}

		if(!Objects.isNull(drawedComponent)) {
			drawedComponent.paintObject(g);
		}
	}


	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}


	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}


	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
