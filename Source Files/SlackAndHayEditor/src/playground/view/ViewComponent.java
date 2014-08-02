package playground.view;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;

import playground.controller.Controller;

public class ViewComponent extends JPanel {

	private static final long serialVersionUID = -1555700608171168268L;
	private final ScrollableCanvasComponent scrollableCanvas;

	public ViewComponent(final Controller controller) {
		super();
		setLayout(new BorderLayout());
		scrollableCanvas = new ScrollableCanvasComponent(controller);
		add(new MenuView(controller), BorderLayout.NORTH);
		add(new LeftPanel(controller), BorderLayout.WEST);
		add(scrollableCanvas, BorderLayout.CENTER);
	}
	
	@Override
	public Rectangle getVisibleRect() {
		return scrollableCanvas.getVisibleRect();
	}
}
