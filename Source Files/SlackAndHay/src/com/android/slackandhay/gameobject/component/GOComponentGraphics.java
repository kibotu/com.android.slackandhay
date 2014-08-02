package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.scene.Animation;
import com.android.slackandhay.scene.GLMeshNode;
import com.android.slackandhay.scene.GLSceneNode;
import com.android.slackandhay.scene.Texture;

/**
 * This Component lets game objects be rendered on the screen
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */

public abstract class GOComponentGraphics extends GOComponent {

	@SuppressWarnings("unused")
	private final String TAG = GOComponentGraphics.class.getSimpleName();

	protected GLSceneNode _surfaceHolder;
	private float lastPercentage;

	public GOComponentGraphics(final GOGameObject parent) {
		super(GOComponentType.GRAPHICS, parent);
		_surfaceHolder = new GLMeshNode();
	}

	/**
	 * This method has to be overridden when a subclass of this class is
	 * created. The following method block only shows how the graphics of a game
	 * object could react to different states, for instance.
	 * 
	 * @Override public void update(final int dt) { switch
	 *           (parent.getStateMananger().getActiveState().getStateType()) {
	 *           case IDLE: break; case WALKING: break; case RUNNING: break;
	 *           case ATTACKING: break; case BLOCKING: break; case STRUCK:
	 *           break; case DEAD: break; case DESTROYED: break; } }
	 */

	public GLSceneNode getNode() {
		return _surfaceHolder;
	}

	/**
	 * sets current texture id based on progress percentage and variable array
	 * length does nothing, when percentages haven't changed
	 * 
	 * @param ids
	 */
	protected void animate(final Animation animation) {
		float percentage = parent.getStateMananger().getActiveState().getPercentage();
		if (lastPercentage == percentage)
			return;
		lastPercentage = percentage;
		percentage *= 100;
		percentage %= 100;
		final int index = (int) (animation.values.length / 100f * percentage);
		setImage(animation.values[index]);
	}

	/**
	 * Sets position of the node
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * @return
	 */
	public GLSceneNode setAbsPosition(final float dx, final float dy, final float dz) {
		return _surfaceHolder.setAbsPosition(dx, dy, dz);
	}

	/**
	 * Sets Dimension of the node
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 */
	public GLSceneNode setDimension(final float width, final float height, final float depth) {
		return _surfaceHolder.setDimension(width, height, depth);
	}

	/**
	 * Sets Rotation of the node
	 * 
	 * @param angle
	 * @param rx
	 * @param ry
	 * @param rz
	 */
	public GLSceneNode setRotation(final float angle, final float rx, final float ry, final float rz) {
		return _surfaceHolder.setRotation(angle, rx, ry, rz);
	}

	/**
	 * Sets the color of the node
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public GLSceneNode setColor(final float r, final float g, final float b) {
		assert 0 >= r && r <= 1;
		assert 0 >= g && g <= 1;
		assert 0 >= b && b <= 1;
		return _surfaceHolder.setColor(r, g, b);
	}

	/**
	 * Sets visibility of the node
	 * 
	 * @param isVisible
	 */
	public void setVisible(final boolean isVisible) {
		_surfaceHolder.setVisible(isVisible);
	}

	/**
	 * Appends current graphic object to <code>goParent</code>
	 * 
	 * @param glParent
	 */
	public void setParent(final GOGameObject goParent) {
		final GOComponentGraphics goGraphics = (GOComponentGraphics) goParent.getComponent(GOComponentType.GRAPHICS);
		_surfaceHolder.setParent(goGraphics._surfaceHolder);
	}

	/**
	 * Sets current Texture VBO id
	 * 
	 * @param textureVBOid
	 */
	protected void setTextureVboId(final int textureId) {
		((GLMeshNode) _surfaceHolder).setTextureVboId(textureId);
	}

	/**
	 * sets texture vbo offset
	 * 
	 * @param textureOffset
	 */
	protected void setTextureVboOffset(final int offset) {
		((GLMeshNode) _surfaceHolder).setTextureVboOffset(offset);
	}

	/**
	 * Sets the Texture VBO offset within the vbo floatbuffer right
	 * 
	 * @param id
	 */
	public void setImage(final int id) {
		// TODO Avoid Magic numbers!
		setTextureVboOffset(id * 32); // 32 = 4 x * 4 y texture coords * 4 bytes
										// each quad
	}

	/**
	 * propose a texture
	 * 
	 * @param id
	 */
	public void proposeTexture(final Texture texture) {
		((GLMeshNode) _surfaceHolder).proposeTexture(texture);
	}

	/**
	 * scales the mesh
	 * 
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 */
	public void setScale(final float scaleX, final float scaleY, final float scaleZ) {
		_surfaceHolder.setScale(scaleX, scaleY, scaleZ);
	}

	/**
	 * @return _width
	 */
	public float getWidth() {
		return _surfaceHolder.getWidth();
	}

	/**
	 * @return _height
	 */
	public float getHeight() {
		return _surfaceHolder.getHeight();
	}

	/**
	 * @return _depth
	 */
	public float getDepth() {
		return _surfaceHolder.getDepth();
	}

	/**
	 * sets Position fixed
	 */
	public void setPositionFixed(boolean isFixed) {
		_surfaceHolder.setPositionFixed(isFixed);
	}
}
