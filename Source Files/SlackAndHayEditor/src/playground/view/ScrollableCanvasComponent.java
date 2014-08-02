package playground.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import playground.Config;
import playground.controller.ChangeEvent;
import playground.controller.ChangeEventListener;
import playground.controller.ChangeState;
import playground.controller.Controller;

public class ScrollableCanvasComponent extends JPanel implements ChangeEventListener {

	private static final long serialVersionUID = -5836878675609634075L;
	private JPanel canvas;
	private JScrollPane scrollpane;
	private Controller context;
	
	public ScrollableCanvasComponent(Controller controller) {
		setLayout(new BorderLayout());
		this.context = controller;
		controller.addChangeEventListener(this);
		canvas = new Canvas();
		canvas.addMouseMotionListener(controller);
		canvas.addMouseListener(controller);
		canvas.addKeyListener(controller);
		scrollpane = new JScrollPane();
		scrollpane.setViewportView(canvas);
		add(scrollpane, "Center");
	}

	@Override
	public void changeEventReceived(ChangeEvent event) {
		if(event.getEventName() == ChangeState.NEW_WORLD) {
			Dimension dim = context.getWorldSize();
			canvas.setPreferredSize(new Dimension((int)(dim.width*Config.GRID_ZOOM), (int)(dim.height*Config.GRID_ZOOM)));
			int inset = 50;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.width - inset * 2, screenSize.height - inset * 2);
			scrollpane.setViewportView(canvas);
		}
	}
	
	@Override
	public Rectangle getVisibleRect() {
		return canvas.getVisibleRect();
	}
}
