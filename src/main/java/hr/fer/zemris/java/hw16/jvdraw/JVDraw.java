package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.enums.ShapeType;
import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.JListClickListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.utils.JvdUtil;

/**
 * JVDraw is a simple GUI application for vector graphics. One can draw shapes on
 * the painting canvas, change colors of drawn shapes and so on. <p>In order for
 * users to save their work, in menu File there are several saving options:
 * <ul><li>Save - Generates a .jvd file</li><li>Save As - Generates a .jvd file
 * </li><li>Export - Generates a image</li></ul>
 * 
 * @author Filip Klepo
 *
 */
public class JVDraw extends JFrame implements IShapeProvider {

	/**
	 * Serial version UID of this class.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The line.
	 */
	private static final String LINE = "Line";
	/**
	 * The circle.
	 */
	private static final String CIRCLE = "Circle";
	/**
	 * The filled circle.
	 */
	private static final String FILLED_CIRCLE = "Filled Circle";
	/**
	 * Buttons arranged in button group which are used for selection of drawing 
	 * mode.
	 */
	private List<JToggleButton> drawingModeButtons = new ArrayList<>();
	/**
	 * Jvd file associated with this object.
	 */
	private File curJvdFile;
	/**
	 * Flag which indicates whether this object was edited.
	 */
	private boolean edited;

	/**
	 * The default constructor.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setBounds(200, 200, 700, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		
		JPanel pageStartPanel = new JPanel();
		pageStartPanel.setLayout(new BoxLayout(pageStartPanel, 
				BoxLayout.PAGE_AXIS));
		add(pageStartPanel, BorderLayout.PAGE_START);JMenuBar menuBar = new JMenuBar();
		pageStartPanel.add(menuBar);

		
		DrawingModel drawingModel = new DrawingModelImpl();
		drawingModel.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				setEdited(true);
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				setEdited(true);
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				setEdited(true);
			}
		});
		DrawingObjectListModel drawingListModel = new DrawingObjectListModel(drawingModel);
		

		// MENU BAR INITIALIZATION -------------------------------------------
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		pageStartPanel.add(menuBar);
		JMenuItem open = new JMenuItem(new OpenAction(this, drawingModel));
		JMenuItem save = new JMenuItem(new SaveAction(this, drawingModel));
		JMenuItem saveAs = new JMenuItem(new SaveAsAction(this, drawingModel));
		JMenuItem export = new JMenuItem(new ExportAction(this, drawingModel));
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(export);
		pageStartPanel.add(menuBar);


		// TOOLBAR INITIALIZATION --------------------------------------------
		JToolBar toolbar = new JToolBar("JVDraw properties");
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.setBorder(BorderFactory.createEtchedBorder());
		JColorArea backgroundColorArea = new JColorArea(Color.RED);
		JColorArea foregroundColorArea = new JColorArea(Color.BLUE);
		toolbar.add(backgroundColorArea);
		toolbar.add(foregroundColorArea);
		pageStartPanel.add(toolbar);



		// DRAWING CANVAS INITIALIZATION ------------------------------------
		JDrawingCanvas canvas = new JDrawingCanvas(drawingModel, this, 
				backgroundColorArea, foregroundColorArea);
		add(canvas, BorderLayout.CENTER);



		JList<GeometricalObject> objectsList = new JList<>(drawingListModel);
		objectsList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!(e.getKeyCode() == KeyEvent.VK_DELETE)) {
					return;
				}

				int index = objectsList.getSelectedIndex();
				if(index != -1) {
					drawingModel.remove(index);
					repaint();
				}
			}
		});
		objectsList.addMouseListener(
				new JListClickListener(drawingModel, objectsList));
		JScrollPane scrollingObjectsList = new JScrollPane(objectsList);
		Dimension preferred = objectsList.getPreferredSize();
		preferred.width = 180;
		scrollingObjectsList.setPreferredSize(preferred);
		objectsList.setLayoutOrientation(JList.VERTICAL);
		objectsList.setVisible(true);
		add(scrollingObjectsList, BorderLayout.EAST);


		// STATUS BAR INITIALIZATION -----------------------------------------
		CurrentColorsLabel colorsLabel = 
				new CurrentColorsLabel(
						backgroundColorArea.getCurrentColor(), 
						foregroundColorArea.getCurrentColor());
		backgroundColorArea.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, 
					Color oldColor, Color newColor) {
				colorsLabel.setBackgroundColor(newColor);
			}
		});
		foregroundColorArea.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, 
					Color oldColor, Color newColor) {
				colorsLabel.setForegroundColor(newColor);
			}
		});
		add(colorsLabel, BorderLayout.PAGE_END);


		// BUTTON GROUP INITIALIZATION ---------------------------------------
		ButtonGroup buttonGroup = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton(LINE);
		JToggleButton circleButton = new JToggleButton(CIRCLE);
		JToggleButton filledCircleButton = new JToggleButton(FILLED_CIRCLE);
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);
		toolbar.add(lineButton);
		toolbar.add(circleButton);
		toolbar.add(filledCircleButton);
		drawingModeButtons.add(lineButton);
		drawingModeButtons.add(circleButton);
		drawingModeButtons.add(filledCircleButton);
		
		
		//save before exit
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JvdUtil.exit(JVDraw.this, drawingModel);
			}
		});
	}

	/**
	 * The main method. Runs when program is started.
	 * 
	 * @param args arguments from the command line, not used by method
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	@Override
	public ShapeType getShapeType() {
		for(JToggleButton button : drawingModeButtons) {
			if(button.isSelected()) {
				switch(button.getText()) {
				case LINE:
					return ShapeType.LINE;
				case CIRCLE:
					return ShapeType.CIRCLE;
				case FILLED_CIRCLE:
					return ShapeType.FILLED_CIRCLE;
				}
			}
		}

		return ShapeType.NONE;
	}

	/**
	 * Gets .jvd file associated with this object.
	 * 
	 * @return .jvd file
	 */
	public File getJvdFile() {
		return curJvdFile;
	}

	/**
	 * Sets .jvd file associated with this object.
	 * 
	 * @param file new .jvd file
	 */
	public void setJvdFile(File file) {
		if(file == null) {
			setTitle("JVDraw");
		} else {
			setTitle("JVDraw - " + file.toString());
		}
		curJvdFile = file;
	}

	/**
	 * Checks if JVDraw is edited.
	 * 
	 * @return edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets value of edited.
	 * 
	 * @param edited new edited value
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

}
