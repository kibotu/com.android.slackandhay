package com.android.slackandhay.scene;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.util.Scanner;

public class ObjectLoader {

	private static final String TAG = GLMeshNode.class.getSimpleName();

	private float[] vertices;
	private float[] vertice_indices;
	private float[] normals;
	private float[] normal_indices;
	private float[] texture;
	private float[] texture_indices;

	public ObjectLoader() {
	}

	public void loadObject(Context context, int resourceId) {

		// parse xml
		Scanner reader = new Scanner(new DataInputStream(new BufferedInputStream(context.getResources().openRawResource(resourceId))));
		String currentLine = null;
		while (reader.hasNextLine()) {
			currentLine = reader.nextLine();
			if (currentLine.startsWith("v")) {
				Log.i(TAG, "vertice");
			} else if (currentLine.startsWith("vt")) {
				Log.i(TAG, "texture");
			} else if (currentLine.startsWith("vn")) {
				Log.i(TAG, "normal");
			} else if (currentLine.startsWith("f")) {
				Log.i(TAG, "face");
			}
		}

		// vertices

		// indices

		// normals

		// normal indices

		// texture coords

		// texture indices
	}
}
