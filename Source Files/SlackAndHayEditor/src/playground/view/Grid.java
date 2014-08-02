package playground.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;


import org.jdom.DataConversionException;
import org.jdom.Element;

import playground.Config;

public class Grid implements Drawable {
	
	private int UID = Config.getUID();
	private int width;
	private int height;
	private float scale;
	private boolean isVisible;
	private String name;

	public Grid(int width, int height, float scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		isVisible = true;
		name = "World";
	}
	
	public Grid(Element e) throws DataConversionException {
		this(e.getAttribute("width").getIntValue(), e.getAttribute("height").getIntValue(),e.getAttribute("scale").getFloatValue());
	}
	
	public Dimension getDimension() {
		return new Dimension(width, height);
	}

	@Override
	public void draw(Graphics g) {
		if(!isVisible) {
			return;
		}
		Color save = g.getColor();
		g.setColor(Color.BLACK);
		
		// cols
		for(float x = 0; x < width; x+=Config.GRID_SCALE_EDITOR) {
			g.drawLine((int) (x*Config.GRID_ZOOM),0,(int) (x*Config.GRID_ZOOM),(int)(height*Config.GRID_ZOOM));
		}
		
		// rows
		for(float y = 0; y < height; y+=Config.GRID_SCALE_EDITOR) {
			g.drawLine(0,(int) (y*Config.GRID_ZOOM),(int) (width*Config.GRID_ZOOM),(int)(y*Config.GRID_ZOOM));
		}
		
		g.draw3DRect(0, 0, (int)(width*Config.GRID_ZOOM), (int)(height*Config.GRID_ZOOM), true);
		g.setColor(save);
	}

	@Override
	public Element toXml() {
		final Element e = new Element("world");
		e.setAttribute("uid", ""+UID);
		e.setAttribute("width", ""+width);
		e.setAttribute("height", ""+height);
		e.setAttribute("scale", ""+scale);
		return e;
	}

	@Override
	public Vector<Object> toVector() {
		final Vector<Object> v = new Vector<Object>();
		v.add("type"); 		v.add(name);
		v.add("uid"); 		v.add(UID);
		v.add("width"); 	v.add(width);
		v.add("height"); 	v.add(height);
		v.add("scale");		v.add(scale);
		return v;
	}

	@Override
	public int getUID() {
		return UID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + UID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Grid)) {
			return false;
		}
		Grid other = (Grid) obj;
		if (UID != other.UID) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("[ %d ]  %s",UID,name);
	}
	public String getName() {
		return name;
	}
	
	@Override
	public int getZindex() {
		return -1;
	}

	@Override
	public int compareTo(Drawable other) {
		return getZindex() - other.getZindex();
	}
	
	public void setZindex(int index) {
	}

	@Override
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public void setVector(Vector<Vector<Object>> v) {
		this.name = v.get(0).get(1).toString();
		this.width = Integer.parseInt(v.get(2).get(1).toString());
		this.height = Integer.parseInt(v.get(3).get(1).toString());
		this.scale = Float.parseFloat(v.get(4).get(1).toString());
	}
}
