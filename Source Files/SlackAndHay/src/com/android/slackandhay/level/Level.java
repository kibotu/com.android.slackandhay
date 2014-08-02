package com.android.slackandhay.level;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import com.android.slackandhay.grid.GridWorld;

/**
 * 
 * This class represents the level, which contains the position and types of all
 * the game objects
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */

public class Level {
	public final Vector<SpawnPoint> spawnPoints = new Vector<SpawnPoint>();
	private GridWorld gridWorld;

	private final Context activity;

	public static final String TAG = "Level";

	public Level(final Context act) {
		activity = act;
	}

	/**
	 * This method determines whether there is a spawnpoint which is not
	 * occupied. If there is one, it will return the PointF otherwise it will
	 * return null, when all spawnPoints are occupied.
	 * 
	 * @return a pointF for spawning or null, if everything is occupied.
	 */
	public Point getUnoccupiedGridPoint() {
		for (final SpawnPoint p : spawnPoints) {
			if (!gridWorld.idIsOccupied(gridWorld.rawXYToID(p.x, p.y)))
				return p;
		}
		return null;
	}

	/**
	 * Generates a <code>Grid</code> and a Vector of <code>SpawnPoints</code>
	 * out of a given XML File resource id.
	 * 
	 * @param levelRessourceID
	 * @return GridWorld
	 */
	public GridWorld load(final int levelRessourceID) {
		final SAXBuilder saxBuilder = new SAXBuilder();
		try {
			final Document doc = saxBuilder.build(new DataInputStream(new BufferedInputStream(activity.getResources()
					.openRawResource(levelRessourceID))));
			List<Element> list = doc.getRootElement().getChildren("world");
			Element eWorld = list.get(0);
			gridWorld = new GridWorld(eWorld.getAttribute("width").getIntValue(), eWorld.getAttribute("height")
					.getIntValue(), new PointF(0, 0), eWorld.getAttribute("scale").getFloatValue());
			list = doc.getRootElement().getChildren("gameobject");
			for (final Element element : list) {
				try {
					addSpawnPoint(element);
				} catch (DataConversionException e) {
					Log.e(TAG, "DataConversionException: " + e.getMessage());
				}
			}
		} catch (final IOException e) {
			Log.e(TAG, "IOException:" + e.getMessage());
		} catch (final JDOMException e) {
			Log.e(TAG, "JDOMException: " + e.getMessage().toString());
			for (StackTraceElement ste : e.getStackTrace()) {
				Log.e(TAG, "JDOMException: " + ste.toString());
			}
		}
		return gridWorld;
	}

	/**
	 * Parses the <code>GameObjectType</code> out of an DOM Element.
	 * 
	 * @param element
	 * @throws DataConversionException
	 */
	private void addSpawnPoint(Element e) throws DataConversionException {
		for (SpawnPoint.GameObjectType type : SpawnPoint.GameObjectType.values()) {
			if (e.getAttributeValue("type").equals(type.name().toLowerCase())) {
				spawnPoints.add(new SpawnPoint((int) e.getAttribute("x").getFloatValue(), (int) e.getAttribute("y")
						.getFloatValue(), e.getAttribute("width").getFloatValue(), e.getAttribute("height")
						.getFloatValue(), type));
				Log.i(TAG, type.name() + " added");
				break;
			}
		}
	}

	/**
	 * returns the spawn points contained in the level
	 * @return
	 * the spawn points contained in the level
	 */
	public Vector<SpawnPoint> getSpawnPoints() {
		return spawnPoints;
	}
}
