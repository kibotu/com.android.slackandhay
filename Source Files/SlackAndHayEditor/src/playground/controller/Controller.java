package playground.controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import playground.Config;
import playground.model.Model;
import playground.utils.Key;
import playground.view.Drawable;
import playground.view.Grid;
import playground.view.ImageShape;
import playground.view.ViewComponent;

public class Controller extends JPanel implements ChangeEventObserver, KeyListener, Runnable, MouseListener, MouseMotionListener, ActionListener, TableModelListener{

	private static final long serialVersionUID = -4516326294293644989L;

	public enum BackgroundObject {
		
		Grass(AddCommand.Grass),
		Stone(AddCommand.Stone),
		Vintage(AddCommand.Vintage);
		
		private final AddCommand value;
		
		private BackgroundObject(final AddCommand value) {
			this.value = value;
		}
		
		public AddCommand getValue() {
			return value;
		}
	}
	
	public enum AddCommand {
		
		Camera("files/camera.png",0.3f),
		Player("files/crono.png",1),
		Soldier("files/soldat512.png",0.8f),
		Dog("files/hund512.png",0.8f),
		House1("files/house1.png",0.8f),
		House2("files/house2.png",0.8f),
		House3("files/house3.png",0.8f),
		House4("files/house4.png",0.8f),
		House5("files/house5.png",0.8f),
		Decoration1("files/decoration_1.png",0.8f),
		Decoration2("files/decoration_2.png",0.8f),
		Decoration3("files/decoration_3.png",0.8f),
		Decoration4("files/decoration_4.png",0.8f),
		Grass("files/grassbg.jpg",0.1f),
		Stone("files/stonebg.png",0.1f),
		Vintage("files/vintagebg.png",0.1f);
		
		final private String filepath;
		final private float scale;
		
		private AddCommand(final String filepath, final float scale) {
			this.filepath = filepath;
			this.scale = scale;
		}
		public float getScale() {
			return scale;
		}
		public String getFilePath() {
			return filepath;
		}
	}

	public enum FileCommand {
		New, Open, Save, Exit
	}
	
	public enum ViewCommand {
		Switch_Grid, Switch_Game_Objects, Switch_Background 
	}

	private int lastSelected;

	private final Model model;
	private final ViewComponent view;
	private boolean isRunning;
	private Drawable mouseOverShape;
	private RootPaneContainer context;
	private final List<ChangeEventListener> listeners = new ArrayList<ChangeEventListener>();

	public Controller(RootPaneContainer context) {
		try {
			UIManager.setLookAndFeel(Config.DEFAULT_WINDOW_THEME);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.context = context;
		model = Model.getInstance();
		model.getDrawables().add(new Grid(20,20,0.5f));
		view = new ViewComponent(this);
		view.setFocusable(true);
		view.addKeyListener(this);
		mouseOverShape = null;
		lastSelected = 0;
	}

	public JPanel getView() {
		start();
		dispatchEvent(lastSelected, ChangeState.OBJECTMANAGER_LIST_CLICKED);
		return view;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if(e.getSource() instanceof JList) {
			lastSelected = ((JList)e.getSource()).locationToIndex(e.getPoint());
			dispatchEvent(lastSelected, ChangeState.OBJECTMANAGER_LIST_CLICKED);
		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		mouseOverShape = hitTest(e.getPoint());
		if(mouseOverShape != null) {
			lastSelected = mouseOverShape.getUID();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		mouseOverShape = null;
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {

	}

	@Override
	public void mouseMoved(final MouseEvent e) {

	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String actionCommand = e.getActionCommand();
		
		// add menu
		for(AddCommand addCommand: AddCommand.values()) {
			if (actionCommand.equals(addCommand.name())) {
				ImageShape shape = new ImageShape(addCommand);
				shape.x = (float) (view.getVisibleRect().width/2+view.getVisibleRect().x);
				shape.y = (float) (view.getVisibleRect().height/2+view.getVisibleRect().y);
				model.getDrawables().add(shape);
			}
		}
		
		// file menu
		if (actionCommand.equals(FileCommand.New.name())) {
			new NewWorldDialog("Create New World", 250,110,context);
			lastSelected = 0;
			dispatchEvent(this, ChangeState.NEW_WORLD);
		}
		if (actionCommand.equals(FileCommand.Exit.name())) {
			System.exit(JApplet.ABORT);
		}
		if (actionCommand.equals(FileCommand.Open.name())) {
			final JFileChooser fc = new JFileChooser(new File(Config.DEFAULT_SAVE_DIR));

			fc.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(final File f) {
					return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
				}
				@Override
				public String getDescription() {
					return "GameObjects (*.xml)";
				}
			});

			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				final File file = fc.getSelectedFile();
				model.load(file);
			}
			dispatchEvent(this, ChangeState.NEW_WORLD);
		}
		if (actionCommand.equals(FileCommand.Save.name())) {
			final JFileChooser fc = new JFileChooser(new File(Config.DEFAULT_SAVE_DIR));

			fc.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(final File f) {
					return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
				}
				@Override
				public String getDescription() {
					return "GameObjects (*.xml)";
				}
			});

			if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				final File file = fc.getSelectedFile();
				model.save(file);
			}
		}
		
		// view menu
		if(actionCommand.equals(ViewCommand.Switch_Grid.name())) {
			if(!model.getDrawables().get(0).isVisible()) {
				model.getDrawables().get(0).setVisible(true);
			} else {
				model.getDrawables().get(0).setVisible(false);
			}
		}
		if(actionCommand.equals(ViewCommand.Switch_Game_Objects.name())) {
			ArrayList<Drawable> list = model.getGameObjects();
			for(int i = 1; i < list.size(); i++) {
				list.get(i).setVisible(!list.get(i).isVisible());
			}
		}
		
		if(actionCommand.equals(ViewCommand.Switch_Background.name())) {
			ArrayList<Drawable> list = model.getBackgrounds();
			for(int i = 0; i < list.size(); i++) {
				list.get(i).setVisible(!list.get(i).isVisible());
			}
		}
		
		dispatchEvent(lastSelected, ChangeState.OBJECTMANAGER_LIST_CLICKED);
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (mouseOverShape!=null){
			if(mouseOverShape instanceof Rectangle2D.Float){
				final Rectangle2D.Float shape = (Rectangle2D.Float)mouseOverShape;
				final float width = (float)shape.getWidth();
				final float height = (float)shape.getHeight();
				shape.setFrame(new Rectangle2D.Float(e.getX()-width/2, e.getY()-height/2, width, height));
			}
		}
		dispatchEvent(lastSelected, ChangeState.OBJECTMANAGER_LIST_CLICKED);
	}

	private Drawable hitTest(final Point p){
		Shape hitShape = null;
		final List<Drawable> drawables = Model.getInstance().getDrawables();
		for(int i = 0; i < drawables.size(); i++) {
			final Drawable temp = drawables.get(i);
			if(temp instanceof ImageShape) {
				if (((ImageShape)temp).contains(p)){
					hitShape = (ImageShape)temp;
					break;
				}
			}
		}
		if(hitShape == null || !((Drawable)hitShape).isVisible()) {
			return null;
		}
		return (Drawable)hitShape;
	}

	@Override
	public void run() {
		double startTime = System.currentTimeMillis();
		while (isRunning) {
			/** compute passed time since last frame **/
			final long finalDelta = (long) (System.currentTimeMillis() - startTime);
			/** logic **/
//			doLogic();
			/** reset startTime **/
			startTime = System.currentTimeMillis();
			/** sleep **/
			if (finalDelta < Config.FPS) {
				try {
					Thread.sleep(Config.FPS - finalDelta);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			/** repaint **/
			view.repaint();
		}
	}

	public void start() {
		isRunning = true;
		new Thread(this).start();
	}

	public void stop() {
		isRunning = false;
	}

	private synchronized void dispatchEvent(final Object src, final ChangeState state) {
		for (int i = 0; i < listeners.size(); ++i) {
			listeners.get(i).changeEventReceived(new ChangeEvent(src, state));
		}
		model.sort();
	}

	@Override
	public synchronized void addChangeEventListener(final ChangeEventListener listener) {
		listeners.add(listener);
	}

	@Override
	public synchronized void removeChangeEventListener(final ChangeEventListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(Key.DELETE.equals(e.getKeyCode())) {
			if(mouseOverShape != null) {
//				model.getDrawables().remove(mouseOverShape);
				mouseOverShape = null;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	public Dimension getWorldSize() {
		return ((Grid) Model.getInstance().getDrawables().get(0)).getDimension();
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		switch(e.getType()) {
		case TableModelEvent.INSERT: 
			break;
		case TableModelEvent.UPDATE: 
			DefaultTableModel tablemodel = (DefaultTableModel)e.getSource();
			try {
				Drawable drawable = model.getDrawables().get(Integer.parseInt(tablemodel.getValueAt(1, 1).toString()));
				drawable.setVector(tablemodel.getDataVector());
				dispatchEvent(this, ChangeState.NEW_WORLD);
			} catch(IndexOutOfBoundsException exc) {
				// uid exception
				exc.printStackTrace();
			}
			break;
		case TableModelEvent.DELETE: 
			break;
		}
	}
}
