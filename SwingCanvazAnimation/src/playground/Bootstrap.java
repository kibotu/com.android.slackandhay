package playground;

import java.awt.Container;
import javax.swing.JApplet;
import playground.controller.Controller;

public class Bootstrap extends JApplet {

	private static final long serialVersionUID = 4895015725301923346L;

	@Override
	public void init() {
		final Controller controller = new Controller();
		final Container container = getContentPane();
		container.add(controller.getView());
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setVisible(true);
		controller.start();
	}
}