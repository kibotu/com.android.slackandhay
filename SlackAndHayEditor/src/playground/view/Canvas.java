package playground.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import playground.model.Model;


public class Canvas extends JPanel {

	private static final long serialVersionUID = -3692338615710052482L;
	public static int x = 0;
	public static int y = 0;

	public Canvas() {
		setBackground(new Color(0x66aa66));
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		for(final Drawable drawable: Model.getInstance().getDrawables()) {
			drawable.draw(g2);
		}
		
//	    Font font = new Font("Serif", Font.PLAIN, 96);
//	    g2.setFont(font);
//	    g2.drawString("jade", 40, 120); 
	}
}