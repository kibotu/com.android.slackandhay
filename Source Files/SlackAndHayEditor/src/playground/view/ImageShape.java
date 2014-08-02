package playground.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.jdom.DataConversionException;
import org.jdom.Element;

import playground.Config;
import playground.controller.Controller.AddCommand;

public class ImageShape extends Rectangle2D.Float implements Drawable {

	private static final long serialVersionUID = -3203207613241647492L;
	private BufferedImage bufferedImage;
	private final File imageFile;
	protected String name;
	protected float scale;
	protected int UID = Config.getUID();
	private int zindex;
	private boolean isVisible;

	public ImageShape(final String name, final String fileName, final float scale) {
		this.name = name;
		this.scale = scale;
		imageFile = new File(fileName);
		try {
			bufferedImage = ImageIO.read(imageFile);
			x = Config.WINDOW_WIDTH/3;
			y = Config.WINDOW_HEIGHT/3;
			setScale(scale);
			zindex = 0;
		} catch (final IOException e) {
			System.err.println("Error loading " + fileName);
			System.err.println(e.toString());
		}
		isVisible = true;
	}
	public void setScale(float scale) {
		this.scale = scale;
		width = bufferedImage.getWidth()*scale;
		height = bufferedImage.getHeight()*scale;
	}

	public ImageShape(final AddCommand item) {
		this(item.name(), item.getFilePath(),item.getScale());
	}

	public ImageShape(final Element e) throws DataConversionException {
		this(e.getAttributeValue("type"), Config.DEFAULT_RESOURCE_DIR + e.getAttributeValue("file"),e.getAttribute("scale").getFloatValue());
		x = e.getAttribute("x").getFloatValue()*Config.GAMEOBJECT_SCALE_EDITOR;
		y = e.getAttribute("y").getFloatValue()*Config.GAMEOBJECT_SCALE_EDITOR;
		width = e.getAttribute("width").getFloatValue()*Config.GAMEOBJECT_SCALE_EDITOR*Config.BACKGROUND_FACTOR;
		height = e.getAttribute("height").getFloatValue()*Config.GAMEOBJECT_SCALE_EDITOR*Config.BACKGROUND_FACTOR;
		zindex = e.getAttribute("zindex").getIntValue();
		UID = e.getAttribute("uid").getIntValue();
		for(AddCommand command: AddCommand.values()) {
			if(name.equals(command.name().toLowerCase())) {
				this.scale = command.getScale();
			}
		}
	}

	@Override
	public void draw(final Graphics g) {
		if(!isVisible) {
			return;
		}
		final Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bufferedImage, (int)x, (int)y, (int)width, (int)height, null);
	}

	@Override
	public Element toXml() {
		final Element e = new Element("gameobject");
		e.setAttribute("type", name.toLowerCase());
		e.setAttribute("uid", ""+UID);
		e.setAttribute("x", ""+x/Config.GAMEOBJECT_SCALE_EDITOR);
		e.setAttribute("y", ""+y/Config.GAMEOBJECT_SCALE_EDITOR);
		e.setAttribute("width", ""+width/Config.GAMEOBJECT_SCALE_EDITOR/Config.BACKGROUND_FACTOR);
		e.setAttribute("height", ""+height/Config.GAMEOBJECT_SCALE_EDITOR/Config.BACKGROUND_FACTOR);
		e.setAttribute("zindex", ""+zindex);
		e.setAttribute("scale", ""+scale);
		e.setAttribute("file", imageFile.getName());
		return e;
	}

	@Override
	public int getUID() {
		return UID;
	}
	
	public String toString() {
		return String.format("[ %d ]  %s",UID,name);
	}

	public String getName() {
		return name;
	}

	@Override
	public Vector<Object> toVector() {
		final Vector<Object> v = new Vector<Object>();
		v.add("type"); 		v.add(name);
		v.add("uid"); 		v.add(UID);
		v.add("x"); 		v.add(x/Config.GAMEOBJECT_SCALE_EDITOR);
		v.add("y"); 		v.add(y/Config.GAMEOBJECT_SCALE_EDITOR);
		v.add("zindex");	v.add(zindex);
		v.add("width"); 	v.add(width/Config.GAMEOBJECT_SCALE_EDITOR/Config.BACKGROUND_FACTOR);
		v.add("height"); 	v.add(height/Config.GAMEOBJECT_SCALE_EDITOR/Config.BACKGROUND_FACTOR);
		v.add("scale"); 	v.add(scale);
		return v;
	}
	@Override
	public int getZindex() {
		return zindex;
	}

	@Override
	public int compareTo(Drawable other) {
		return zindex-other.getZindex();
	}
	
	public void setZindex(int index) {
		zindex = index;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + UID;
		result = prime * result
				+ ((bufferedImage == null) ? 0 : bufferedImage.hashCode());
		result = prime * result
				+ ((imageFile == null) ? 0 : imageFile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + java.lang.Float.floatToIntBits(scale);
		result = prime * result + zindex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ImageShape)) {
			return false;
		}
		ImageShape other = (ImageShape) obj;
		if (UID != other.UID) {
			return false;
		}
		if (bufferedImage == null) {
			if (other.bufferedImage != null) {
				return false;
			}
		} else if (!bufferedImage.equals(other.bufferedImage)) {
			return false;
		}
		if (imageFile == null) {
			if (other.imageFile != null) {
				return false;
			}
		} else if (!imageFile.equals(other.imageFile)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (java.lang.Float.floatToIntBits(scale) != java.lang.Float
				.floatToIntBits(other.scale)) {
			return false;
		}
		if (zindex != other.zindex) {
			return false;
		}
		return true;
	}

	@Override
	public void setVector(Vector<Vector<Object>> v) {
		this.name = v.get(0).get(1).toString();
		this.x = java.lang.Float.parseFloat(v.get(2).get(1).toString())*Config.GAMEOBJECT_SCALE_EDITOR;
		this.y = java.lang.Float.parseFloat(v.get(3).get(1).toString())*Config.GAMEOBJECT_SCALE_EDITOR;
		this.zindex = Integer.parseInt(v.get(4).get(1).toString());
		this.scale = java.lang.Float.parseFloat(v.get(7).get(1).toString());
		setScale(scale);
		this.width = java.lang.Float.parseFloat(v.get(5).get(1).toString())*Config.BACKGROUND_FACTOR*Config.GAMEOBJECT_SCALE_EDITOR;
		this.height = java.lang.Float.parseFloat(v.get(6).get(1).toString())*Config.BACKGROUND_FACTOR*Config.GAMEOBJECT_SCALE_EDITOR;
	}
}