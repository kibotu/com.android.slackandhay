package playground.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import playground.model.Model;


public class ViewComponent extends JPanel {

	private static final long serialVersionUID = -1555700608171168268L;

	public ViewComponent(final Model model) {
		super();
		setLayout(new BorderLayout());
		add(new Canvas(model), BorderLayout.CENTER);
	}
}
