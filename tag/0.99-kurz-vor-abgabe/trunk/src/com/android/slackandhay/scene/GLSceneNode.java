package com.android.slackandhay.scene;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public abstract class GLSceneNode {

	/**
	 * Gibt an, ob Vertex Buffer Objects verf�gbar sind
	 */
	protected static boolean VBOSupported = false;

	/**
	 *  Die Sichtbarkeit des Nodes
	 */
	protected boolean _visible = true;

	/**
	 * X-Position des Nodes
	 */
	protected float _posX = 0f;

	/**
	 * Y-Position des Nodes
	 */
	protected float _posY = 0f;

	/**
	 * Z-Position des Nodes
	 */
	protected float _posZ = 0f;

	/**
	 * X-Skallierung des Nodes
	 */
	protected float _scaleX = 1f;

	/**
	 * Y-Skallierung des Nodes
	 */
	protected float _scaleY = 1f;

	/**
	 * Z-Skallierung des Nodes
	 */
	protected float _scaleZ = 1f;
	
	/**
	 * Rotate Angle des Nodes
	 */
	protected float _roteAngle = 0f;
	
	/**
	 * X-Rotation des Nodes
	 */
	protected float _rotateX = 0f;
	
	/**
	 * Y-Rotation des Nodes
	 */
	protected float _rotateY = 0f;
	
	/**
	 * Z-Rotation des Nodes
	 */
	protected float _rotateZ = 0f;

	/**
	 * Rotkomponente der ambienten Farbe
	 */
	private float _ambientR = 1f;

	/**
	 * Gr�nkomponente der ambienten Farbe
	 */
	private float _ambientG = 1f;

	/**
	 * Blaukomponente der ambienten Farbe
	 */
	private float _ambientB = 1f;

	/**
	 * Breite des Nodes
	 */
	protected float _width = 1f;

	/**
	 * H�he des Nodes
	 */
	protected float _height = 1f;

	/**
	 * Tiefe des Nodes
	 */
	private float _depth = 1f;

	/**
	 * Liste der Kindknoten
	 */
	private List<GLSceneNode> _childNodes;

	/**
	 * Der Elternknoten
	 */
	private GLSceneNode _parent;

	/**
	 * Defines if the position can be changed
	 */
	private boolean _isPositionFixed = false;

	/**
	 * Erzeugt einen neuen Node
	 * @param parent Der Elternknoten
	 */
	public GLSceneNode() {
		this(null);
	}

	/**
	 * Erzeugt einen neuen Node
	 * @param parent Der Elternknoten
	 */
	public GLSceneNode(final GLSceneNode parent) {
		_parent = parent;
	}

	/**
	 * Liefert den Elternknoten
	 * @return
	 */
	public final GLSceneNode getParent() { return _parent; }

	/**
	 * Siefert den Elternknoten
	 * @param parent Der Elternknoten
	 */
	public final void setParent(final GLSceneNode parent) { _parent = parent; }

	/**
	 * Ermittelt, ob der Node sichtbar ist
	 * @return true, wenn sichtbar
	 */
	public final boolean isVisible() { return _visible; }

	/**
	 * Setzt die Sichtbarkeit des Nodes
	 * @param visible Die Sichtbarkeit
	 */
	public final void setVisible(final boolean visible) { _visible = visible; }

	/**
	 * Liefert die X-Koordinate der Position des Nodes
	 * @return Die X-Koordinate
	 */
	public float getX() { return _posX; }

	/**
	 * Liefert die Y-Koordinate der Position des Nodes
	 * @return Die Y-Koordinate
	 */
	public float getY() { return _posY; }

	/**
	 * Liefert die Z-Koordinate der Position des Nodes
	 * @return Die Z-Koordinate
	 */
	public float getZ() { return _posZ; }
	
	/**
	 * Setzt die Abs Position des Nodes
	 * @param x
	 * @param y
	 * @param z
	 */
	public GLSceneNode setAbsPosition(final float x, final float y, final float z) {
		if(!_isPositionFixed) {
			_posX = x; _posY = y; _posZ = z;
		}
		return this;
	}

	/**
	 * Setzt die Relative Position des Nodes
	 * @param x
	 * @param y
	 * @param z
	 */
	public GLSceneNode setRelPosition(final float x, final float y, final float z) {
		if(!_isPositionFixed) {
			_posX += x; _posY += y; _posZ += z;
		}
		return this;
	}
	
	/**
	 * Setzt die Dimension des Nodes
	 * @param width
	 * @param height
	 * @param depth
	 */
	public GLSceneNode setDimension(final float width, final float height, final float depth) {
		_width = width; _height = height; _depth = depth;
		return this;
	}
	
	/**
	 * Rotiert den Node um den angegebenen Winkel
	 * @param angle
	 * @param rx
	 * @param ry
	 * @param rz
	 */
	public GLSceneNode setRotation(final float angle, final float rx, final float ry, final float rz) {
		_roteAngle = angle; _rotateX = rx; _rotateY = ry; _rotateZ = rz;
		return this;
	}

	/**
	 * Setzt die ambiente Farbe
	 * @param r
	 * @param g
	 * @param b
	 */
	public GLSceneNode setColor(final float r, final float g, final float b) {
		_ambientR = r;
		_ambientG = g;
		_ambientB = b;
		return this;
	}

	/**
	 * Setzt die Skallierung des Nodes
	 * 
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 */
	public GLSceneNode setScale(final float scaleX, final float scaleY, final float scaleZ) {
		_scaleX = scaleX; _scaleY = scaleY; _scaleZ = scaleZ;
		return this;
	}

	/**
	 * F�gt einen Kindknoten zum Node hinzu
	 * @param child Der hinzuzuf�gende Kindknoten
	 */
	public void add(final GLSceneNode child) {
		if (_childNodes == null) {
			_childNodes = new ArrayList<GLSceneNode>();
		}
		_childNodes.add(child);
		child.setParent(this);
	}

	/**
	 * Entfernt einen Kindknoten
	 * @param child Das zu entfernende Kind
	 * @return true, wenn das Kind entfernt wurde
	 */
	public boolean remove(final GLSceneNode child) {
		if (_childNodes == null) return false;
		if( _childNodes.remove(child)) {
			child.setParent(null);
			return true;
		}
		return false;
	}

	/**
	 * Liefert die Anzahl der Kindknoten
	 * @return
	 */
	public int getChildCount() {
		if (_childNodes == null) return 0;
		return _childNodes.size();
	}

	public GLSceneNode getChild(final int index) {
		if (_childNodes == null || index < 0 || index >= _childNodes.size()) return null;
		return _childNodes.get(index);
	}

	/**
	 * Interne Logik zum Rendern des Elementes
	 * @param gl
	 * @return true, wenn Kindelemente gerendert werden d�rfen
	 */
	protected abstract boolean renderInternal(GL10 gl);

	/**
	 * Rendert das Element
	 * @param gl
	 */
	public void render(final GL10 gl) {
		if (!_visible) return;

		// Model view aktivieren
		gl.glPushMatrix();
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		// Objekt verschieben
		if(_parent != null) {
			gl.glTranslatef(_parent._posX+_posX, _parent._posY+_posY, _parent._posZ+_posZ);
		} else {
			gl.glTranslatef(_posX,_posY,_posZ);
		}

		// Object Rotieren
		gl.glRotatef(_roteAngle, _rotateX, _rotateY, _rotateZ);
		
		// Object skalieren
		gl.glScalef(_width*_scaleX, _height*_scaleY, _depth*_scaleZ);
		
		// Ambiente Farbe setzen
		gl.glColor4f(_ambientR, _ambientG, _ambientB, 1f);

		// Rendern
		final boolean renderChilds = renderInternal(gl);

		// Child nodes rendern
		if (renderChilds && _childNodes != null) {
			final int childNodeSize = _childNodes.size();
			for (int i=0; i<childNodeSize; ++i) {
				_childNodes.get(i).render(gl);
			}
		}

		// Alte Matrix wiederherstellen
		gl.glPopMatrix();
	}

	/**
	 * F�hrt ein initiales Setup des Scene Graph durch, testet auf OpenGL-Feature, etc.
	 * @param gl
	 */
	public final void setup(final GL10 gl) {

		// Test auf Vertex Buffer Objects
		final String extensions = gl.glGetString(GL10.GL_EXTENSIONS);
		VBOSupported = extensions.contains( "vertex_buffer_object" );

		// Node selbst vorbereiten
		setupNode(gl);

		// Child Nodes vorbereiten
		if (_childNodes != null) {
			final int childNodeSize = _childNodes.size();
			for (int i=0; i<childNodeSize; ++i) {
				_childNodes.get(i).setupNode(gl);
			}
		}
	}

	/**
	 * F�hrt ein Setup des Nodes durch
	 */
	protected abstract void setupNode(GL10 gl);

	/**
	 * F�hrt ein Cleanup des Scene Graph durch
	 * @param gl
	 */
	public final void cleanup(final GL10 gl) {

		// Node selbst aufr�umen
		cleanupNode(gl);

		// Child Nodes aufr�umen
		if (_childNodes != null) {
			final int childNodeSize = _childNodes.size();
			for (int i=0; i<childNodeSize; ++i) {
				_childNodes.get(i).cleanupNode(gl);
			}
		}
	}

	/**
	 * Fuehrt ein Cleanup des Nodes durch
	 */
	protected abstract void cleanupNode(GL10 gl);

	
	/**
	 * @return _width
	 */
	public float getWidth() {
		return _width;
	}

	/**
	 * @return _height
	 */
	public float getHeight() {
		return _height;
	}
	
	/**
	 * @return _depth
	 */
	public float getDepth() {
		return _depth;
	}

	public void setPositionFixed(boolean isFixed) {
		_isPositionFixed = isFixed;
	}
}
