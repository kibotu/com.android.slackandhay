package com.android.slackandhay.scene;

import com.android.slackandhay.input.InputSystem;

import android.content.Context;

public class GLSurfaceView extends android.opengl.GLSurfaceView {

	private final GLRenderer _renderer; 
	
	public GLSurfaceView(Context context) {
		super(context);
		
		// Renderer erzeugen und setzen
		_renderer = new GLRenderer(context);
		setRenderer(_renderer);
		
		// Rendern nur auf Anforderung
		//setRenderMode(RENDERMODE_WHEN_DIRTY);
	}

	public void setInputSystem(InputSystem inputSystem) {
		// TODO Auto-generated method stub
		
	}
}