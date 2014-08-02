package com.android.slackandhay.scene;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.android.slackandhay.scene.util.Vector3;

/**
 * Camera class
 * <p/>
 * represents the camera for a specific scene graph
 */
public class GLCameraSceneNode extends GLNullSceneNode {

    @SuppressWarnings("unused")
	private static final String TAG = GLCameraSceneNode.class.getSimpleName();

    // eye vector
    private Vector3 eye;

    // focus vector
    private Vector3 focus;

    // up vector
    private Vector3 up;

    // view angle
    private final float angle;

    // zoom factor
    private float zoom;

    // near plane
    private final float zNear;

    // far plane
    private final float zFar;

    // strafe vector
    private Vector3 strafe;

    public GLCameraSceneNode() {
        eye = Vector3.createNew(0f, 0f, 0f);
        focus = Vector3.createNew(0f, 0f, -1f);
        up = Vector3.createNew(0f, 1f, 0f);
        strafe = Vector3.createNew(0f,0f,0f);
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
//        switchToOrthView(gl);
        switchToPerspView(gl);
    }

    /**
     * inits to perspective view
     */
    public void switchToPerspView(final GL10 gl) {
        final GL11 gl11 = (GL11) gl;
        assert gl11 != null;
        gl.glViewport(-4, -4, (int) _width + 4, -(int) _height - 4); // widened viewport
        gl.glMatrixMode(GL11.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, angle * zoom, (float) _width / (float) _height, 1, zFar - zNear);
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
        gl.glViewport(-4, -4, (int) _width + 4, -(int) _height - 4); // widened viewport
        gl.glOrthof(-(float) (_width / _height), (float) (_width / _height), -1, 1, -1, 1);
        gl.glMatrixMode(GL11.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * updates glulookat
     */
    private void update(GL10 gl) {
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, focus.x, focus.y, focus.z, up.x, up.y, up.z);
        strafe.set(focus);
        strafe.subInPlace(eye);
        strafe.crossInPlace(up);
        strafe.normalize();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    /**
     * Sets the position of the camera.
     *
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param focusX
     * @param focusY
     * @param focusZ
     * @param upX
     * @param upY
     * @param upZ
     */
    public void setPosition(float eyeX, float eyeY, float eyeZ, float focusX,
                            float focusY, float focusZ, float upX, float upY, float upZ) {
        eye.set(eyeX,eyeY,eyeZ);
        focus.set(focusX,focusY,focusZ);
        up.set(upX,upY,upZ);
    }

    public void setPosition(Vector3 eye, Vector3 focus, Vector3 up) {
        this.eye.recycle();
        this.eye = eye;
        this.focus.recycle();
        this.focus = focus;
        this.up.recycle();
        this.up = up;
    }

    /**
     * moves camera
     * <p/>
     * positiv speed = forward negative speed = backwards
     *
     * @param speed
     */
    public void move(float speed) {
        Vector3 temp = focus.sub(eye);
        temp.normalize();
        eye.x += temp.x * speed;
        eye.z += temp.z * speed;
        focus.x += temp.x * speed;
        focus.z += temp.z * speed;
        temp.recycle();
    }

    /**
     * strafes camera
     * <p/>
     * positiv speed = right negative speed = left
     *
     * @param speed
     */
    public void strafe(float speed) {
    	strafe.set(focus).subInPlace(eye);
    	strafe.crossInPlace(up);
        strafe.normalize();
        eye.x += strafe.x * speed;
        eye.z += strafe.z * speed;
        focus.x += strafe.x * speed;
        focus.z += strafe.z * speed;
    }

    /**
     * looks up and down
     * <p/>
     * positive speed = up negative speed = down
     *
     * @param speed
     */
    public void lookVertically(float speed) {
        // setPosition(dx, dy, dz, dx, dy, dz-1, 0, 1, 0);
        strafe.recycle();
        strafe = focus.sub(eye).cross(up);
        strafe.normalize();
        focus.y += speed;
    }

    /**
     * rotates
     *
     * @param dangle
     * @param dx
     * @param dy
     * @param dz
     */
    public void rotate(float dangle, float dx, float dy, float dz) {

        Vector3 newView = Vector3.createNew(0,0,0);
        Vector3 vView = focus.sub(eye);

        float cosTheta = (float) Math.cos(dangle);
        float sinTheta = (float) Math.sin(dangle);

        newView.x = (cosTheta + (1 - cosTheta) * dx * dx) * vView.x;
        newView.x += ((1 - cosTheta) * dx * dy - dz * sinTheta) * vView.y;
        newView.x += ((1 - cosTheta) * dx * dz + dy * sinTheta) * vView.z;

        newView.y = ((1 - cosTheta) * dx * dy + dz * sinTheta) * vView.x;
        newView.y += (cosTheta + (1 - cosTheta) * dy * dy) * vView.y;
        newView.y += ((1 - cosTheta) * dy * dz - dx * sinTheta) * vView.z;

        newView.z = ((1 - cosTheta) * dx * dz - dy * sinTheta) * vView.x;
        newView.z += ((1 - cosTheta) * dy * dz + dx * sinTheta) * vView.y;
        newView.z += (cosTheta + (1 - cosTheta) * dz * dz) * vView.z;

        focus.recycle();
        focus = eye.add(newView);
        newView.recycle();
        vView.recycle();
    }

    public Vector3 getFocus() {
        return focus;
    }

    public void zoom(float distance) {
        zoom = distance;
    }
}