package playground.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import playground.controller.Controller;

public class LeftPanel extends JPanel{

	private static final long serialVersionUID = 2513913534233365705L;

	public LeftPanel(final Controller controller) {
		super();
		setLayout(new BorderLayout());
		final ObjectPanel objectmanager = new ObjectPanel(controller);
		controller.addChangeEventListener(objectmanager);
		add(objectmanager, BorderLayout.NORTH);
		final PropertyPanel properties = new PropertyPanel(controller,objectmanager.getList());
		controller.addChangeEventListener(properties);
		add(properties, BorderLayout.CENTER);
	}
}