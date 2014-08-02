package com.android.slackandhay.scene;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import java.nio.*;
import java.util.Vector;

/**
 * Specific Scene Node
 * 
 * - represents a plane which holds a texture, which an be changed at runtime
 * 
 * 
 * @author Til Börner, Tom Wallroth, Jan Rabe
 *
 */
public class GLObjectNode extends GLSceneNode{

	@SuppressWarnings("unused")
	private static final String TAG = GLObjectNode.class.getSimpleName();

	private short indicesSize;

	/**
	 * Die ID des VBO
	 */
	private int _vertexVboId;

	/**
	 * Die ID des Index-VBO
	 */
	private int _indexVboId;
	
	/**
	 * Die ID des Normal-VBO
	 */
	private int _normalVboId;

	private int _currentTextureId;

	private int _currentTextureVboId;

	private int _currentTextureVboiOffset;

	private int _proposedTextureId;

	private int _proposedTextureVboId;


	public GLObjectNode() {
		super();
	}

	@Override
	public boolean renderInternal(final GL10 gl) {

		// OpenGL 1.1-Instanz beziehen
		final GL11 gl11 = (GL11)gl;
		assert gl11 != null;

		// enable texture
		gl.glEnable(GL10.GL_TEXTURE_2D);
//		gl.glEnable(GL11.GL_COLOR_MATERIAL);

		// set active texture
//		gl.glActiveTexture(_currentTextureId);
		bindTexture(gl11);

		// alpha blending
		gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		gl.glEnable(GL11.GL_BLEND);

		// kill alpha fragments
		gl.glAlphaFunc(GL11.GL_GREATER,0.1f);
		gl.glEnable(GL11.GL_ALPHA_TEST);
		
		// Vertex Array-State aktivieren und Vertexttyp setzen
		gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        gl11.glEnableClientState(GL11.GL_NORMAL_ARRAY);

		// Vertex binden
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _vertexVboId);
		
		// Zeichne Vertices!
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		
		// binde normal vbo id
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _normalVboId);
		
		// zeichne normals
//		gl11.glNormalPointer(GL11.GL_FLOAT,0,0);
		gl11.glNormalPointer(GL11.GL_FIXED,0,0);
		

		// bind texture vbo id
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _currentTextureVboId);

		// Zeichne Texturkoordinaten
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, _currentTextureVboiOffset);
		
		// indices
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, _indexVboId);
		
		// Zeichne Triangles
		assert indicesSize > 0;
		gl11.glDrawElements(GL10.GL_TRIANGLES, indicesSize, GL11.GL_UNSIGNED_SHORT, 0);

		// disable texture
		gl.glDisable(GL11.GL_TEXTURE_2D);

		// disable blending
		gl.glDisable(GL11.GL_BLEND);
		gl.glDisable(GL11.GL_ALPHA_TEST);
		gl.glDisable(GL11.GL_COLOR_MATERIAL);

		// Vertex Array-State deaktivieren
		gl11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);

		// Puffer abw�hlen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		
//		if(gl.glGetError() != 0) {
//			Log.e(TAG, ""+gl.glGetError());	
//		}
		
		return true;
	}

	@Override
	protected void setupNode(final GL10 gl) {

//		ObjectLoader objectLoader = new ObjectLoader
		
		// bind texture
	    bindTexture(gl);

		// Vertices erzeugen
		final float[] vertices = new float[] {
				 0.5f, 	 0.5f,  0.0f, // oben rechts
				-0.5f,   0.5f, 	0.0f, // oben links 
				-0.5f, 	-0.5f,  0.0f, // unten links
				 0.5f,	-0.5f, 	0.0f  // rechts unten
		};

		final float [] normals = new float[] {
				0f, 0f, 1f, 						
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
		};
		
		// Indizes erzeugen
		final short[] indices = new short[] {
				0,1,2,2,3,0
		};
		indicesSize = (short) indices.length;

		// Byte-Puffer f�r Vertex Buffer erzeugen
		final ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4); // 4 byte per float
		vbb.order(ByteOrder.nativeOrder());
		final FloatBuffer vertexBuffer = vbb.asFloatBuffer();
		
		// Byte-Puffer f�r Normal Buffer erzeugen
		final ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4); // 4 byte per float
		nbb.order(ByteOrder.nativeOrder());
		final FloatBuffer normalBuffer = nbb.asFloatBuffer();

		// Byte-Puffer f�r Index Buffer erzeugen
		final ByteBuffer ibb = ByteBuffer.allocateDirect(indicesSize * 2); // 4 byte per short
		ibb.order(ByteOrder.nativeOrder());
		final ShortBuffer indexBuffer = ibb.asShortBuffer();

		// Daten laden
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		indexBuffer.put(indices);
		indexBuffer.position(0);
		normalBuffer.put(normals);
		normalBuffer.position(0);

		// OpenGL 1.1-Instanz beziehen
		final GL11 gl11 = (GL11)gl;

		// VBO erzeugen, ID ermitteln
		final IntBuffer buffer = IntBuffer.allocate(3);
		gl11.glGenBuffers(3, buffer);
		_vertexVboId = buffer.get(0);
		_indexVboId = buffer.get(1);
		_normalVboId = buffer.get(2);

		
		// Vertex-VBO binden und beladen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _vertexVboId);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GL11.GL_STATIC_DRAW);
		
		// Normal-VBO binden und beladen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _normalVboId);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, normals.length * 4, vertexBuffer, GL11.GL_STATIC_DRAW);

		// Index-VBO binden und beladen
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, _indexVboId);
		gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, indices.length * 2, indexBuffer, GL11.GL_STATIC_DRAW);

		// Puffer abw�hlen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

		// Vertex Buffer wegwerfen
		vertexBuffer.clear();
		indexBuffer.clear();
		normalBuffer.clear();
	}

	@Override
	protected void cleanupNode(final GL10 gl) {

		// TODO make clean up method work and actually invoke it somewhere

		// OpenGL 1.1-Instanz beziehen
		final GL11 gl11 = (GL11)gl;

		// Puffer aufr�umen
		gl11.glDeleteBuffers(2, new int [] { _vertexVboId, _indexVboId,}, 0);
	}

	public void proposeTextureVbo(final int textureVboId) {
		_proposedTextureVboId = textureVboId;
	}

	public void setTextureVboId(final int textureVboId) {
		_currentTextureVboId = textureVboId;
	}

	public void setTextureVboOffset(final int textureVboOffset) {
		_currentTextureVboiOffset = textureVboOffset;
		final GLTexture glTexture = GLBufferLibrary.getInstance().getGLTexture(textureVboOffset/4);
		setDimension(glTexture.width, glTexture.height,0);
	}

	public void proposeTexture(final Texture texture) {
		_proposedTextureId = texture.ordinal();
	}

	/**
	 * fps expensive preferably called only once at start
	 * 
	 * @param gl
	 */
	public void bindTexture(final GL10 gl) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, _currentTextureId);
	}

	public void setTextureId(final Vector<Integer> textureIds) {
		assert _proposedTextureId < textureIds.size();
		setTextureId(textureIds.get(_proposedTextureId));
	}

	public void setTextureId(final int textureId) {
		_currentTextureId = textureId;
	}

	public void setTextureVboId(final Vector<Integer> vboIds) {
		assert _proposedTextureVboId < vboIds.size();
		setTextureVboId(vboIds.get(_proposedTextureVboId));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + _currentTextureId;
		result = prime * result + _currentTextureVboId;
		result = prime * result + _currentTextureVboiOffset;
		result = prime * result + _indexVboId;
		result = prime * result + _proposedTextureId;
		result = prime * result + _proposedTextureVboId;
		result = prime * result + _vertexVboId;
		result = prime * result + indicesSize;
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
		if (!(obj instanceof GLObjectNode)) {
			return false;
		}
		GLObjectNode other = (GLObjectNode) obj;
		if (_currentTextureId != other._currentTextureId) {
			return false;
		}
		if (_currentTextureVboId != other._currentTextureVboId) {
			return false;
		}
		if (_currentTextureVboiOffset != other._currentTextureVboiOffset) {
			return false;
		}
		if (_indexVboId != other._indexVboId) {
			return false;
		}
		if (_proposedTextureId != other._proposedTextureId) {
			return false;
		}
		if (_proposedTextureVboId != other._proposedTextureVboId) {
			return false;
		}
		if (_vertexVboId != other._vertexVboId) {
			return false;
		}
		if (indicesSize != other.indicesSize) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GLMeshNode [indicesSize=" + indicesSize + ", _vertexVboId="
				+ _vertexVboId + ", _indexVboId=" + _indexVboId
				+ ", _currentTextureId=" + _currentTextureId
				+ ", _currentTextureVboId=" + _currentTextureVboId
				+ ", _currentTextureVboiOffset=" + _currentTextureVboiOffset
				+ ", _proposedTextureId=" + _proposedTextureId
				+ ", _proposedTextureVboId=" + _proposedTextureVboId + "]";
	}
}
