package com.android.slackandhay.scene;

import java.util.Arrays;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLU;

public class GLCameraSceneNode extends GLNullSceneNode {

	private static final String TAG = GLCameraSceneNode.class.getSimpleName();
	private float[] eye;
	private float[] focus;
	private float[] up;
	private final float angle;
	private float zoom;
	private final float zNear;
	private final float zFar;
	private float[] strafe;

	public GLCameraSceneNode() {
		eye = new float[] { 0f, 0f, 0f };
		focus = new float[] { 0f, 0f, -1f };
		up = new float[] { 0f, 1f, 0f };
		angle = 45;
		zoom = 1;
		zNear = 0.001f;
		zFar = 100.0f;
	}

	/**
	 * render process of the camera
	 * 
	 * @param gl
	 */
	public void setActive(final GL10 gl) {
		switchToPerspView(gl);
	}
	
    /**
     * inits to perspective view
     */
    public void switchToPerspView(final GL10 gl) {
		final GL11 gl11 = (GL11) gl;
		assert gl11 != null;
		gl.glViewport(-4, -4, (int)_width+4, -(int)_height-4); // widened viewport
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, angle*zoom, (float) _width / (float) _height, 1,zFar-zNear);
		update(gl);
    }

    /**
     * inits to orthographic view
     */
    public void switchToOrthView(final GL10 gl) {
		final GL11 gl11 = (GL11) gl;
		assert gl11 != null;
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(-4, -4, (int)_width+4, -(int)_height-4); // widened viewport
		gl.glOrthof(-(float)( _width / _height), (float)( _width / _height), -1, 1, -1, 1);
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		gl.glLoadIdentity();
    }

    /**
     * updates glulookat
     */
    private void update(GL10 gl) {
		GLU.gluLookAt(gl,eye[0], eye[1], eye[2], focus[0], focus[1], focus[2],up[0], up[1], up[2]);
		strafe = normalize(crossProduct(sub(focus, eye), up));
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
    }
    
    /**
     * crossproduct of 2 vectors
     * 
     * @param vVector1
     * @param vVector2
     * @return crossproduct
     */
    private float[] crossProduct(float[] vVector1, float[] vVector2) {
	return new float[] {
		vVector1[1] * vVector2[2] - vVector1[2] * vVector2[1],
		vVector1[2] * vVector2[0] - vVector1[0] * vVector2[2],
		vVector1[0] * vVector2[1] - vVector1[1] * vVector2[0] };
    }

	/**
	 * sets position for the camera
	 * 
	 * @param eye
	 * @param position
	 * @param up
	 */
	public void setPosition(float eyeX, float eyeY, float eyeZ, float focusX, float focusY, float focusZ, float upX, float upY, float upZ) {
		eye[0] = eyeX;	focus[0] = focusX;	up[0] = upX;
		eye[1] = eyeY;	focus[1] = focusY;	up[1] = upY;
		eye[2] = eyeZ;	focus[2] = focusZ;	up[2] = upZ;
	}
		
	/**
     * moves camera
     * 
     * positiv speed = forward 
     * negative speed = backwards
     * 
     * @param speed
     */
    public void move(float speed) {
		float[] vVector = normalize(sub(focus, eye));
		eye[0] += vVector[0] * speed;
		eye[2] += vVector[2] * speed;
		focus[0] += vVector[0] * speed;
		focus[2] += vVector[2] * speed;
    }
    
    /**
     * strafes camera
     * 
     * positiv speed = right 
     * negative speed = left
     * 
     * @param speed
     */
    public void strafe(float speed) {
    	strafe = normalize(crossProduct(sub(focus, eye), up));
		eye[0] += strafe[0] * speed;
		eye[2] += strafe[2] * speed;
		focus[0] += strafe[0] * speed;
		focus[2] += strafe[2] * speed;
    }
    
    /**
     * looks up and down
     * 
     * positive speed = up
     * negative speed = down
     * 
     * @param speed
     */
    public void lookVertically(float speed) {
	//	setPosition(dx, dy, dz, dx, dy, dz-1, 0, 1, 0);
		strafe = normalize(crossProduct(sub(focus, eye), up));
		focus[1] += speed;
    }
    
    /**
     * substracts vectors (v1-v2)
     * 
     * @param v1
     * @param v2
     * @return substracted Vector
     */
    private float[] sub(float[] v1, float[] v2) {
    	return new float[] { v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2] };
    }
    
    /**
     * normalizes vector
     * 
     * @param vVector
     */
    private float[] normalize(float[] vVector) {
		float[] normalizedVector = new float[vVector.length];
		System.arraycopy(vVector, 0, normalizedVector, 0, vVector.length);
		float magnitude = magnitude(vVector);
		for (int i = 0; i < vVector.length; ++i) {
		    vVector[i] /= magnitude;
		}
		return normalizedVector;
    }

    /**
     * calcs magnitude of a vector;
     * 
     * @param vNormal
     * @return magnitude
     */
    private float magnitude(float[] vNormal) {
		return (float) Math.sqrt((vNormal[0] * vNormal[0]) + (vNormal[1] * vNormal[1]) + (vNormal[2] * vNormal[2]));
    }
    
    
	@Override
	public String toString() {
		return TAG +" [eye=" + Arrays.toString(eye) + ", focus="
				+ Arrays.toString(focus) + ", up=" + Arrays.toString(up)
				+ ", angle=" + angle + ", zNear=" + zNear + ", zFar=" + zFar
				+ "]";
	}

    /**
     * rotates 
     * 
     * @param dangle
     * @param dx
     * @param dy
     * @param dz
     */
    public void rotate(float dangle, float dx, float dy, float dz)  {
    	float [] newView = new float[3];
    	float [] vView = sub(focus,eye);		

    	float cosTheta = (float)Math.cos(dangle);
    	float sinTheta = (float)Math.sin(dangle);

    	newView[0]  = (cosTheta + (1 - cosTheta) * dx * dx)			* vView[0];
    	newView[0] += ((1 - cosTheta) * dx * dy - dz * sinTheta)	* vView[1];
    	newView[0] += ((1 - cosTheta) * dx * dz + dy * sinTheta)	* vView[2];

    	newView[1]  = ((1 - cosTheta) * dx * dy + dz * sinTheta)	* vView[0];
    	newView[1] += (cosTheta + (1 - cosTheta) * dy * dy)			* vView[1];
    	newView[1] += ((1 - cosTheta) * dy * dz - dx * sinTheta)	* vView[2];

    	newView[2]  = ((1 - cosTheta) * dx * dz - dy * sinTheta)	* vView[0];
    	newView[2] += ((1 - cosTheta) * dy * dz + dx * sinTheta)	* vView[1];
    	newView[2] += (cosTheta + (1 - cosTheta) * dz * dz)			* vView[2];

    	focus = add(eye,newView);
    }

    /**
     * add vectors (v1+v2)
     * 
     * @param v1
     * @param v2
     * @return
     */
    private float[] add(float[] v1, float[] v2) {
    	return new float[] { v1[0] + v2[0], v1[1] + v2[1], v1[2] + v2[2] };
    }

	public float getFocusX() {
		return focus[0];
	}
	public float getFocusY() {
		return focus[1];
	}
	public float getFocusZ() {
		return focus[2];
	}

	public void zoom(float distance) {
		zoom = distance;
	}
}