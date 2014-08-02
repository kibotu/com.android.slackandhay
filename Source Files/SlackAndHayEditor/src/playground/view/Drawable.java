package playground.view;

import java.awt.Graphics;
import java.util.Vector;

import org.jdom.Element;

public interface Drawable extends Comparable<Drawable> {
	public void draw(Graphics g);
	public Element toXml();
	public Vector<Object> toVector();
	public int getUID();
	public int getZindex();
	public void setZindex(int index);
	public void setVisible(boolean isVisible);
	public boolean isVisible();
	public String getName();
	public void setVector(Vector<Vector<Object>> dataVector);
}
