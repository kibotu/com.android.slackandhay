package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOGameObjectFactory;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.scene.GLCameraSceneNode;
import com.android.slackandhay.scene.GLMeshNode;
import com.android.slackandhay.scene.GLSceneNode;

public class CameraComponentGraphics extends GOComponentGraphics{

	private float lastPointX = Float.MAX_VALUE; 
	private float lastPointY = Float.MAX_VALUE;
	
	@SuppressWarnings("unused")
	private static final String TAG = GOGameObjectFactory.class.getSimpleName();
	private GLCameraSceneNode _camera;
	
	public CameraComponentGraphics(final GOGameObject parent) {
		super(parent);
		_camera = new GLCameraSceneNode();
		_surfaceHolder.add(_camera);
	}

	@Override
	public void update(final int dt){
		final GOComponentSpatial2D spatial = (GOComponentSpatial2D) parent.getComponent(GOComponentType.SPATIAL);
		assert spatial != null;
		if (!spatial.hasPosition())
			return;
		final float pointx = spatial.getPositionX();
		final float pointy = spatial.getPositionY();
		
		if(lastPointX == pointx && lastPointY == pointy)
			return;
		
		moveV(pointy-lastPointY);
		moveH(pointx-lastPointX);
		zoom(0.8f);
		setPosition(pointx,pointy-1f,0,pointx,pointy,-1,0,1,0);
		
		lastPointX = pointx;
		lastPointY = pointy;
	}
	
	public void rotate(float angle, float dx, float dy, float dz) {
		_camera.rotate(angle, dx, dy, dz);
	}
	
	public void setPosition(float eyeX, float eyeY, float eyeZ, float focusX, float focusY, float focusZ, float upX, float upY, float upZ) {
		_camera.setPosition(eyeX, eyeY, eyeZ, focusX, focusY, focusZ, upX, upY, upZ);
	}
	
	public void moveV(float speed) {
		_camera.move(speed);
	}
	
	public void moveH(float speed) {
		_camera.strafe(speed);
	}
	
	public void lookVertically(float speed) {
		_camera.lookVertically(speed);
	}
	
	public void zoom(float distance) {
		_camera.zoom(distance);
	}
}