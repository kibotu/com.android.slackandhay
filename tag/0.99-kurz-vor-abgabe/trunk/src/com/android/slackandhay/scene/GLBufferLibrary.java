package com.android.slackandhay.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

/**
 * handles all the vbo texture ids and offsets
 * 
 * TODO allocate only once vertice and index buffers too
 *
 */
public class GLBufferLibrary {

	private static final String TAG = GLBufferLibrary.class.getSimpleName();
	private final static GLBufferLibrary INSTANCE = new GLBufferLibrary();
	private final Vector<FloatBuffer> _textureFloatBuffers;
	private final List<Float> _floatList;
	private final Vector<GLTexture> glTexture;

	private GLBufferLibrary() {
		_textureFloatBuffers = new Vector<FloatBuffer>();
		_floatList = new LinkedList<Float>();
		glTexture = new Vector<GLTexture>();
		// default texture id 1
		addTextureCoord(0,0,1f,1f);
		addCronoTextureCoords();
		addDoggCoords();
		addRepeatingTextures();
		addSoldierCoords();
		addGUICoords();
		addHousesCoords();
		addDecorationCoords();
		allocateAll();
	}
	
	private void addDecorationCoords() {
		final float c = 1024f;
		Log.i(TAG, "Decoration Offset "+(_floatList.size()-1)/8);
		addTextureCoord(902f/c,  99f/c, 50f/c, 48f/c);	//  tree 1
		addTextureCoord(865f/c,  138f/c, 30f/c, 61f/c);	//  tree 2
		addTextureCoord(863f/c,  99f/c, 34f/c, 35f/c);	//  fountain
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	// flower
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  
	}

	private void addHousesCoords() {
		final float c = 1024f;
		Log.i(TAG, "Houses Offset "+(_floatList.size()-1)/8);
		addTextureCoord(872f/c,  0f/c, 76f/c, 90f/c);	//  house 1
		addTextureCoord(783f/c,  9f/c, 85f/c, 74f/c);	//  house 2
		addTextureCoord(699f/c,  4f/c, 79f/c, 79f/c);	//  house 3
		addTextureCoord(778f/c,  87f/c, 75f/c, 80f/c);	//  house 4
		addTextureCoord(688f/c,  86f/c, 78f/c, 82f/c);	//  house 5
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  house 6
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  house 7 
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  house 8 
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  house 9
		addTextureCoord(0f/c,  0f/c, 0f/c, 0f/c);	//  house 10
	}

	private void addGUICoords() {
		final float c = 1024f;
		Log.i(TAG, "GUI Offset "+(_floatList.size()-1)/8);
		addTextureCoord(959f/c,  8f/c, 62f/c, 49f/c);	//  sword 
		addTextureCoord(966f/c,  61f/c, 50f/c, 60f/c); 	//  shield
		addTextureCoord(967f/c,  127f/c, 49f/c, 64f/c);	//  legs
	}

	private void addSoldierCoords() {
		final float c = 512f;
		Log.i(TAG, "Soldier Offset "+(_floatList.size()-1)/8);

		// 8x7 sprites
		for(int x = 0; x < 8*64; x += 64) {
			for(int y = 0; y < 7*64; y += 64) {
				addTextureCoord(x/c,  y/c, 64f/c, 64f/c);
			}
		}
	}

	private void addDoggCoords() {
		final float c = 512f;
		Log.i(TAG, "Dog Offset "+(_floatList.size()-1)/8);

		// 8x5 sprites
		for(int x = 0; x < 8*64; x += 64) {
			for(int y = 0; y < 5*64; y += 64) {
				addTextureCoord(x/c,  y/c, 64f/c, 64f/c);
			}
		}
	}
	
	private void addRepeatingTextures() {
		
		Log.i(TAG, "Repeating Textures Offset "+(_floatList.size()-1)/8);
		
		addTextureCoord(0,0,1f,1f); // 266
		addTextureCoord(0,0,2f,1f); // 267
		addTextureCoord(0,0,3f,3f); // 268
		addTextureCoord(0,0,4f,4f); // 269
		addTextureCoord(0,0,5f,5f); // 270
		addTextureCoord(0,0,6f,6f); // 271 
		addTextureCoord(0,0,7f,7f); // 272
		addTextureCoord(0,0,8f,8f); // 273
		addTextureCoord(0,0,9f,9f); // 274
		addTextureCoord(0,0,10.0f,10.0f); // 275
	}

	/**
	 * allocates all texture vbo coordinates and its offsets
	 * 
	 * TODO convert from list to array[] more efficiently
	 */
	private void allocateAll() {
		final Float [] floats = _floatList.toArray(new Float[_floatList.size()]);
		final float [] floatCoords = new float[_floatList.size()];
		for(int i = 0; i < floatCoords.length; i++) {
			floatCoords [i] = floats[i].floatValue();
		}
		addTextureCoordinates(floatCoords);
		Log.i(TAG, "T-VBOs allocated: " + _textureFloatBuffers.size());
		Log.i(TAG, "T-VBOs Offsets allocated: " + _floatList.size()/8);
		_floatList.clear();
	}

	private void addCronoTextureCoords() {

		Log.i(TAG, "Crono Offset "+(_floatList.size()-1)/8);
		
		final float c = 1024f;

		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 1 done
		addTextureCoord(28f/c, 11f/c, 16f/c, 35f/c); // 2 done
		addTextureCoord(48f/c, 11f/c, 16f/c, 35f/c); // 3 done
		addTextureCoord(85f/c,  11f/c, 18f/c, 35f/c); // 4 done
		addTextureCoord(107f/c, 12f/c, 16f/c, 34f/c); // 5 done
		addTextureCoord(128f/c, 14f/c, 17f/c, 32f/c); // 6 done
		addTextureCoord(148f/c, 11f/c, 18f/c, 35f/c); // 7 done
		addTextureCoord(168f/c, 12f/c, 16f/c, 34f/c); // 8 done
		addTextureCoord(188f/c, 14f/c, 17f/c, 32f/c); // 9 done
		addTextureCoord(227f/c,  11f/c, 15f/c, 32f/c); // 10 done
		addTextureCoord(248f/c,  9f/c, 22f/c, 34f/c); // 11 done
		addTextureCoord(248f/c, 9f/c, 22f/c, 34f/c); // 12 done
		addTextureCoord(306f/c,  11f/c, 17f/c, 32f/c); // 13 done
		addTextureCoord(338f/c,  9f/c, 18f/c, 34f/c); // 14 done
		addTextureCoord(361f/c,  9f/c, 18f/c, 34f/c); // 15 done
		addTextureCoord(390f/c, 12f/c, 17f/c, 31f/c); // 16 done
		addTextureCoord(412f/c, 12f/c, 18f/c, 31f/c); // 17 done
		addTextureCoord(390f/c,  12f/c, 17f/c, 31f/c); // 18 done
		addTextureCoord(457f/c,  15f/c, 16f/c, 28f/c); // 19 done
		addTextureCoord(488f/c,  8f/c, 16f/c, 35f/c); // 20 done
		addTextureCoord(508f/c,  8f/c, 16f/c, 35f/c); // 21 done
		addTextureCoord(9f/c,  52f/c, 16f/c, 55f/c); // 22 done
		addTextureCoord(32f/c, 52f/c, 19f/c, 55f/c); // 23 done
		addTextureCoord(58f/c, 52f/c, 19f/c, 55f/c); // 24 done
		addTextureCoord(85f/c, 52f/c, 27f/c, 55f/c); // 25 done
		addTextureCoord(116f/c,52f/c, 35f/c, 55f/c); // 26 done
		addTextureCoord(167f/c,  68f/c, 18f/c, 32f/c); // 27 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 28
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 29
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 30
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 31
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 32
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 33
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 34
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 35
		addTextureCoord(433f/c, 67f/c, 28f/c, 32f/c); // 36 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 37
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 38
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 39
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 40
		addTextureCoord(11f/c,  123f/c, 15f/c, 34f/c); // 41 done
		addTextureCoord(40f/c,  128f/c, 16f/c, 29f/c); // 42 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 43
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 44
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 45
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 46
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 47
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 48
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 49
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 50
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 51
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 52
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 53
		addTextureCoord(365f/c,  123f/c, 19f/c, 40f/c); // 54 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 55
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 56
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 57
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 58
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 59
		addTextureCoord(11f/c,  181f/c, 14f/c, 34f/c); // 60 done
		addTextureCoord(30f/c,  181f/c, 14f/c, 34f/c); // 61 done
		addTextureCoord(49f/c,  181f/c, 14f/c, 34f/c); // 62 done
		addTextureCoord(77f/c,  182f/c, 15f/c, 34f/c); // 63 done
		addTextureCoord(101f/c, 184f/c, 21f/c, 32f/c); // 64 done
		addTextureCoord(127f/c, 183f/c, 15f/c, 33f/c); // 65 done
		addTextureCoord(150f/c, 182f/c, 14f/c, 34f/c); // 66 done
		addTextureCoord(172f/c, 184f/c, 22f/c, 32f/c); // 67 done
		addTextureCoord(201f/c, 183f/c, 14f/c, 33f/c); // 68 done
		addTextureCoord(226f/c,  185f/c, 24f/c, 32f/c); // 69 done
		addTextureCoord(256f/c,  185f/c, 30f/c, 32f/c); // 70 done
		addTextureCoord(291f/c,  185f/c, 28f/c, 32f/c); // 71 done
		addTextureCoord(325f/c,  186f/c, 29f/c, 31f/c); // 72 done
		addTextureCoord(369f/c, 187f/c, 28f/c, 32f/c); // 73 done
		addTextureCoord(401f/c, 187f/c, 28f/c, 32f/c); // 74 done
		addTextureCoord(440f/c,  206f/c, 32f/c, 14f/c); // 75 done
		addTextureCoord(482f/c, 170f/c, 32f/c, 50f/c); // 76 done
		addTextureCoord(519f/c, 170f/c, 32f/c, 50f/c); // 77 done
		addTextureCoord(14f/c,  228f/c, 16f/c, 50f/c); // 78 done
		addTextureCoord(40f/c,  228f/c, 31f/c, 50f/c); // 79 done
		addTextureCoord(78f/c,  228f/c, 29f/c, 50f/c); // 80 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 81
		addTextureCoord(155f/c, 228f/c, 22f/c, 50f/c); // 82 done
		addTextureCoord(192f/c,  247f/c, 20f/c, 30f/c); // 83 done
		addTextureCoord(228f/c,  242f/c, 26f/c, 32f/c); // 84 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 85
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 86
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 87
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 88
		addTextureCoord(402f/c,  241f/c, 14f/c, 32f/c); // 89 done
		addTextureCoord(455f/c,  239f/c, 14f/c, 34f/c); // 90 done
		addTextureCoord(436f/c,  239f/c, 14f/c, 34f/c); // 91 done
		addTextureCoord(484f/c, 223f/c, 31f/c, 50f/c); // 92 done
		addTextureCoord(13f/c,  294f/c, 14f/c, 34f/c); // 93 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 94
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 95
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 96
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 97
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 98
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 99
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 100
		addTextureCoord(274/c,  300f/c, 56f/c, 16f/c); // 101 done
		addTextureCoord(342f/c,  293f/c, 29f/c, 30f/c); // 102 done
		addTextureCoord(325f/c,  186f/c, 29f/c, 31f/c); // 103 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 104
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 105
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 106
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 107
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 108
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 109
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 110
		addTextureCoord(13f/c,  347f/c, 16f/c, 33f/c); // 111 done
		addTextureCoord(47f/c,  347f/c, 18f/c, 32f/c); // 112 done
		addTextureCoord(73f/c,  345f/c, 16f/c, 35f/c); // 113 done
		addTextureCoord(95f/c,  348f/c, 18f/c, 32f/c); // 114 done
		addTextureCoord(117f/c, 347f/c, 18f/c, 33f/c); // 115 done
		addTextureCoord(140f/c, 345f/c, 16f/c, 35f/c); // 116 done
		addTextureCoord(161f/c, 348f/c, 18f/c, 32f/c); // 117 done
		addTextureCoord(203f/c,  349f/c, 15f/c, 30f/c); // 118 done
		addTextureCoord(226f/c,  348f/c, 16f/c, 31f/c); // 119 done
		addTextureCoord(247f/c, 348f/c, 22f/c, 31f/c); // 120 done
		addTextureCoord(301f/c,  345f/c, 17f/c, 38f/c); // 121 done
		addTextureCoord(322f/c,  345f/c, 17f/c, 38f/c); // 122 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 123
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 124
		addTextureCoord(400f/c,  353f/c, 16f/c, 33f/c); // 125 done
		addTextureCoord(432f/c,  353f/c, 16f/c, 33f/c); // 126 done
		addTextureCoord(453f/c,  352f/c, 16f/c, 34f/c); // 127 done
		addTextureCoord(12f/c,  393f/c, 17f/c, 48f/c); // 128 done
		addTextureCoord(40f/c,  393f/c, 16f/c, 48f/c); // 129 done
		addTextureCoord(63f/c,  393f/c, 20f/c, 48f/c); // 130 done
		addTextureCoord(89f/c,  393f/c, 17f/c, 48f/c); // 131 done
		addTextureCoord(113f/c, 393f/c, 27f/c, 48f/c); // 132 done
		addTextureCoord(147f/c, 393f/c, 32f/c, 48f/c); // 133 done
		addTextureCoord(188f/c,  408f/c, 22f/c, 32f/c); // 134 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 135
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 136
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 137
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 138
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 139
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 140
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 141
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 142
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 143
		addTextureCoord(470f/c,  409f/c, 24f/c, 32f/c); // 144 done
		addTextureCoord(505f/c,  408f/c, 21f/c, 31f/c); // 145 done
		addTextureCoord(13f/c,  468f/c, 16f/c, 32f/c); // 146 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 147
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 148
		addTextureCoord(114f/c,  462f/c, 16f/c, 40f/c); // 149 done
		addTextureCoord(141f/c,  468f/c, 15f/c, 34f/c); // 150 done
		addTextureCoord(166f/c, 458f/c, 16f/c, 48f/c); // 151 done
		addTextureCoord(195f/c,  473f/c, 18f/c, 29f/c); // 152 done
		addTextureCoord(217f/c,  473f/c, 17f/c, 29f/c); // 153 done
		addTextureCoord(243f/c,  470f/c, 21f/c, 34f/c); // 154 done
		addTextureCoord(271f/c,  470f/c, 20f/c, 32f/c); // 155 done
		addTextureCoord(302f/c,  471f/c, 15f/c, 32f/c); // 156 done
		addTextureCoord(320f/c,  471f/c, 15f/c, 32f/c); // 157 done
		addTextureCoord(10f/c,  518f/c, 25f/c, 32f/c); // 158 done
		addTextureCoord(42f/c,  518f/c, 28f/c, 32f/c); // 159 done
		addTextureCoord(79f/c,  518f/c, 22f/c, 32f/c); // 160 done
		addTextureCoord(109f/c,  518f/c, 22f/c, 32f/c); // 161 done
		addTextureCoord(150f/c,  519f/c, 16f/c, 38f/c); // 162 done
		addTextureCoord(172f/c,  519f/c, 31f/c, 32f/c); // 163 done
		addTextureCoord(209f/c,  519f/c, 32f/c, 32f/c); // 164 done
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 165
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 166
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 167
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 168
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 169
		addTextureCoord(8f/c,  11f/c, 16f/c, 35f/c); // 170

		// mirrored
		addTextureCoordMirroredOnYAxe(77f/c,  182f/c, 15f/c, 34f/c); // 171 (63) done
		addTextureCoordMirroredOnYAxe(101f/c, 184f/c, 21f/c, 32f/c); // 172 (64) done
		addTextureCoordMirroredOnYAxe(127f/c, 183f/c, 15f/c, 33f/c); // 173 (65) done
		addTextureCoordMirroredOnYAxe(150f/c, 182f/c, 14f/c, 34f/c); // 174 (66) done
		addTextureCoordMirroredOnYAxe(172f/c, 184f/c, 22f/c, 32f/c); // 175 (67) done
		addTextureCoordMirroredOnYAxe(201f/c, 183f/c, 14f/c, 33f/c); // 176 (68) done
		addTextureCoordMirroredOnYAxe(9f/c,  52f/c, 16f/c, 55f/c); // 177 (22) done
		addTextureCoordMirroredOnYAxe(116f/c,52f/c, 35f/c, 55f/c); // 178 (26) done
		addTextureCoordMirroredOnYAxe(433f/c,52f/c, 28f/c, 55f/c); // 179 (36) done
		addTextureCoordMirroredOnYAxe(150f/c,506f/c,18f/c, 55f/c); // 180 (162) done
		addTextureCoordMirroredOnYAxe(32f/c, 52f/c, 19f/c, 55f/c); // 181 (23) done
		addTextureCoordMirroredOnYAxe(58f/c, 52f/c, 19f/c, 55f/c); // 182 (24) done
		addTextureCoordMirroredOnYAxe(85f/c, 52f/c, 27f/c, 55f/c); // 183 (25) done
		addTextureCoordMirroredOnYAxe(14f/c,  228f/c, 16f/c, 50f/c); // 184 (78) done
		addTextureCoordMirroredOnYAxe(484f/c, 223f/c, 31f/c, 50f/c); // 185 (92) done
		addTextureCoordMirroredOnYAxe(482f/c, 170f/c, 32f/c, 50f/c); // 186 (76) done
		addTextureCoordMirroredOnYAxe(519f/c, 170f/c, 32f/c, 50f/c); // 187 (77) done
		addTextureCoordMirroredOnYAxe(40f/c,  228f/c, 31f/c, 50f/c); // 188 (79) done
		addTextureCoordMirroredOnYAxe(78f/c,  228f/c, 29f/c, 50f/c); // 189 (80) done
		addTextureCoordMirroredOnYAxe(325f/c, 167f/c, 29f/c, 50f/c); // 190 (72) done
		addTextureCoordMirroredOnYAxe(155f/c, 228f/c, 22f/c, 50f/c); // 191 (82) done
		addTextureCoordMirroredOnYAxe(247f/c, 335f/c, 22f/c, 44f/c); // 192 (120) done
		addTextureCoordMirroredOnYAxe(40f/c,  393f/c, 16f/c, 48f/c); // 193 (129) done
		addTextureCoordMirroredOnYAxe(12f/c,  393f/c, 17f/c, 48f/c); // 194 (128) done
		addTextureCoordMirroredOnYAxe(166f/c, 458f/c, 16f/c, 48f/c); // 195 (151) done
		addTextureCoordMirroredOnYAxe(147f/c, 393f/c, 32f/c, 48f/c); // 196 (133) done
		addTextureCoordMirroredOnYAxe(63f/c,  393f/c, 20f/c, 48f/c); // 197 (130) done
		addTextureCoordMirroredOnYAxe(89f/c,  393f/c, 17f/c, 48f/c); // 198 (131) done
		addTextureCoordMirroredOnYAxe(113f/c, 393f/c, 27f/c, 48f/c); // 199 (132) done
		addTextureCoordMirroredOnYAxe(77f/c,  182f/c, 15f/c, 34f/c);  // 200 (63) done
		addTextureCoordMirroredOnYAxe(101f/c, 184f/c, 21f/c, 32f/c);  // 201 (64) done
		addTextureCoordMirroredOnYAxe(369f/c, 187f/c, 28f/c, 32f/c);  // 202 (73) done
		addTextureCoordMirroredOnYAxe(401f/c, 187f/c, 28f/c, 32f/c);  // 203 (74) done
		addTextureCoordMirroredOnYAxe(172f/c, 184f/c, 22f/c, 32f/c);  // 204 (67) done
		addTextureCoordMirroredOnYAxe(201f/c, 183f/c, 14f/c, 33f/c);  // 205 (68) done
		addTextureCoordMirroredOnYAxe(440f/c,  206f/c, 32f/c, 14f/c); // 206 (75) done
		addTextureCoordMirroredOnYAxe(167f/c,  68f/c, 18f/c, 32f/c); // 207 (27) done
		addTextureCoordMirroredOnYAxe(188f/c,  408f/c, 22f/c, 32f/c); // 208 (134) done
		addTextureCoordMirroredOnYAxe(192f/c,  247f/c, 20f/c, 30f/c); // 209 (83) done
		addTextureCoordMirroredOnYAxe(11f/c,  181f/c, 14f/c, 34f/c); // 210 (60) done
		addTextureCoordMirroredOnYAxe(30f/c,  181f/c, 14f/c, 34f/c); // 211 (61) done
		addTextureCoordMirroredOnYAxe(49f/c,  181f/c, 14f/c, 34f/c); // 212 (62) done
		addTextureCoordMirroredOnYAxe(13f/c,  294f/c, 14f/c, 34f/c); // 213 (93) done
		addTextureCoordMirroredOnYAxe(402f/c,  241f/c, 14f/c, 32f/c); // 214 (89) done
		addTextureCoordMirroredOnYAxe(455f/c,  239f/c, 14f/c, 34f/c); // 215 (90) done
		addTextureCoordMirroredOnYAxe(436f/c,  239f/c, 14f/c, 34f/c); // 216 (91) done
		addTextureCoordMirroredOnYAxe(325f/c,  186f/c, 29f/c, 31f/c); // 217 (72) done
		addTextureCoordMirroredOnYAxe(342f/c,  293f/c, 29f/c, 30f/c); // 218 (102) done
		addTextureCoordMirroredOnYAxe(325f/c,  186f/c, 29f/c, 31f/c); // 219 (103) done
		addTextureCoordMirroredOnYAxe(226f/c,  348f/c, 16f/c, 31f/c); // 220 (119) done
		addTextureCoordMirroredOnYAxe(247f/c, 348f/c, 22f/c, 31f/c); // 221 (120) done
		addTextureCoordMirroredOnYAxe(470f/c,  409f/c, 24f/c, 32f/c); // 222 (144) done
		addTextureCoordMirroredOnYAxe(306f/c,  11f/c, 17f/c, 32f/c); // 223 (13) done
		addTextureCoordMirroredOnYAxe(390f/c, 12f/c, 17f/c, 31f/c); // 224 (16) done
		addTextureCoordMirroredOnYAxe(412f/c, 12f/c, 18f/c, 31f/c); // 225 (17) done
	}

	private void addTextureCoord(final float x, final float y, final float width, final float height) {
		glTexture.add(new GLTexture(width, height, _floatList.size()));
		_floatList.add(x+width);	_floatList.add(y); 			// oben rechts
		_floatList.add(x);			_floatList.add(y); 			// oben links
		_floatList.add(x);			_floatList.add(y+height); 	// unten links
		_floatList.add(x+width);	_floatList.add(y+height); 	// unten rechts
	}

	private void addTextureCoordMirroredOnYAxe(final float x, final float y, final float width, final float height) {
		glTexture.add(new GLTexture(width, height, _floatList.size()));
		_floatList.add(x);			_floatList.add(y); 			// oben links
		_floatList.add(x+width);	_floatList.add(y); 			// oben rechts
		_floatList.add(x+width);	_floatList.add(y+height); 	// unten rechts
		_floatList.add(x);			_floatList.add(y+height);	// unten links
	}
	
	private void addTextureCoordMirroredOnXAxe(final float x, final float y, final float width, final float height) {
		glTexture.add(new GLTexture(width, height, _floatList.size()));
		_floatList.add(x);			_floatList.add(y+height); 	// unten links
		_floatList.add(x+width);	_floatList.add(y+height); 	// unten rechts
		_floatList.add(x+width);	_floatList.add(y); 			// oben rechts
		_floatList.add(x);			_floatList.add(y); 			// oben links
	}

	/**
	 * gets Buffer Library Instance
	 * @return
	 */
	public static GLBufferLibrary getInstance() {
		return INSTANCE;
	}

	/**
	 * adds a texture coordinates to the gpu
	 * saves texture vbo id
	 * 
	 * @param coords
	 * @return textureVBOid
	 */
	public void addTextureCoordinates(final float[] coords) {
		// texture float buffer for coords
		final ByteBuffer byteBuf = ByteBuffer.allocateDirect(coords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		final FloatBuffer textureBuffers = byteBuf.asFloatBuffer();
		textureBuffers.put(coords);
		textureBuffers.position(0);
		_textureFloatBuffers.add(textureBuffers);
	}

	/**
	 * runs through all available texture coordinates and binds them
	 * 
	 * @param gl
	 */
	public Vector<Integer> bindTextureVBOids(final GL10 gl) {
		final GL11 gl11 = (GL11)gl;
		assert gl11 != null;

		final Vector<Integer> _textureVBOid =  new Vector<Integer>();

		// texture vbo id
		final IntBuffer _buffer = IntBuffer.allocate(_textureFloatBuffers.size());
		gl11.glGenBuffers(_textureFloatBuffers.size(), _buffer);

		final int textureFloatBuffersSize = _textureFloatBuffers.size();
		for(int i = 0; i < textureFloatBuffersSize; i++) {
			final int textureVBOId = _buffer.get(i);

			//			Log.i(TAG, "bound: " +_textureFloatBuffers.get(i).toString() + " to tvbo id "+textureVBOId);

			// binding for later re-use
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, textureVBOId);
			gl11.glBufferData(GL11.GL_ARRAY_BUFFER, _textureFloatBuffers.get(i).capacity() * 4, _textureFloatBuffers.get(i), GL11.GL_STATIC_DRAW);

			// saves texture vbo id
			_textureVBOid.add(textureVBOId);
		}

		// Puffer abwaehlen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

		return _textureVBOid;
	}

	/**
	 * gets a texture by its offset id
	 * 
	 * @param textureVboOffset
	 * @return
	 */
	public GLTexture getGLTexture(final int textureVboOffset) {
		GLTexture result = null;
		for(int i = 0; i < glTexture.size(); i++) {
			if(glTexture.get(i).offsetId == textureVboOffset) {
				result = glTexture.get(i);
				break;
			}
		}
		return result;
	}

	/**
	 * cleanse of hardware buffers
	 */
	//	public void cleanUp() {
	//		// get gl instance
	//		GL11 gl11 = (GL11)EGLContext.getEGL();
	//		assert gl11 != null;
	//
	//		for(int i = 0; i < _textureVBOid.size(); i++) {
	//			gl11.glDeleteBuffers(1, new int [] { _textureVBOid.get(i)}, 0);
	//		}
	//	}
}

