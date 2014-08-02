package com.android.slackandhay.scene;

import javax.microedition.khronos.opengles.GL10;

public class GLCameraSceneNode extends GLNullSceneNode {

	public void setActive(GL10 gl) {
		// TODO: Perpektive und LookAt
		// TODO: Kamera im Graphen wird nicht aktualisiert, wenn Elternknoten unsichtbar!
		// TODO: Von Kamera ausgehend nach oben marschieren, Transformationen anwenden und pushen, dann Transformationsmatrizen in umgekehrter Reihenfolge wieder popen? ... ohje
		
		//GLU.gluPerspective(gl, 45, 1, 0.1f, 100f);
	    //GLU.gluLookAt(gl, 0.5f, 0, -10, 0, 0, 0, 0, 1, 0);
	}	
}