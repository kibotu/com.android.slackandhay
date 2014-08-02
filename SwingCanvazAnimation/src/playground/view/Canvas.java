package playground.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import playground.model.Model;


public class Canvas extends JPanel {

	private static final long serialVersionUID = -3692338615710052482L;
	protected Model model;

	public Canvas(final Model model) {
		this.model = model;
		setBackground(new Color(0x66aa66));
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);

		for(Drawable d: model.getDrawables()) {
			d.draw(g);
		}
	}
}