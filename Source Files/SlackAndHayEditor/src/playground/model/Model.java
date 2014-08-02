package playground.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import playground.Config;
import playground.controller.Controller;
import playground.view.Drawable;
import playground.view.Grid;
import playground.view.ImageShape;

public enum Model {

	INSTANCE;
	
	private final ArrayList<Drawable> drawables;

	private Model() {
		drawables = new ArrayList<Drawable>();
	}
	
	public synchronized ArrayList<Drawable> getDrawables() {
		return drawables;
	}
	
	public ArrayList<Drawable> getBackgrounds() {
		ArrayList<Drawable> list = new ArrayList<Drawable>(); 
		for(Controller.BackgroundObject bg: Controller.BackgroundObject.values()) {
			for(Drawable drawable: drawables) {
				if(drawable.getName().equalsIgnoreCase(bg.getValue().name())) {
					list.add(drawable);
				}
			}
		}
		return list;
	}
	
	public ArrayList<Drawable> getGameObjects() {
		ArrayList<Drawable> list = new ArrayList<Drawable>(); 
		for(Controller.BackgroundObject bg: Controller.BackgroundObject.values()) {
			for(Drawable drawable: drawables) {
				if(drawable.getName().equalsIgnoreCase(bg.getValue().name())) {
					list.add(drawable);
				}
			}
		}
		ArrayList<Drawable> liste = new ArrayList<Drawable>();
		liste.addAll(drawables);
		liste.removeAll(list);
		return liste;
	}

	public static Model getInstance() {
		return INSTANCE;
	}

	public void reset() {
		drawables.clear();
		Config.resetUID();
	}

	public ListModel getListModel() {
		final DefaultListModel listmodel = new DefaultListModel();
		for (final Drawable drawable : drawables) {
			listmodel.addElement(drawable);
		}
		return listmodel;
	}

	public void save(final File file) {
		if (file == null) {
			throw new IllegalArgumentException(file + " must not be null!");
		}
		final Document doc = new Document();
		final Element root = new Element("gameobjects");
		doc.setRootElement(root);
		for (final Drawable drawable : drawables) {
			root.addContent(drawable.toXml());
		}
		final XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat().setIndent("  "));
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath().endsWith(".xml") ? file : new File(file.getAbsolutePath() + ".xml")), "UTF-8")));
			xmlOut.output(doc, fout);
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			fout.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void load(final File file) {
		final SAXBuilder saxBuilder = new SAXBuilder();
		try {
			final Document doc = saxBuilder.build(file);
			List<Element> list = doc.getRootElement().getChildren("world");
			reset();
			if(!list.isEmpty()) {
				drawables.add(new Grid(list.get(0)));
			}
			list = doc.getRootElement().getChildren("gameobject");
			for(final Element e: list) {
				drawables.add(new ImageShape(e));
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final JDOMException e) {
			e.printStackTrace();
		}
	}

	public void sort() {
		Collections.sort(drawables);
	}
}