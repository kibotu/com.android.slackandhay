package com.android.slackandhay.level;

import android.graphics.Point;

public class SpawnPoint extends Point {
	private final GameObjectType got;
	public final float width;
	public final float height;
	
	public static enum GameObjectType {
		CAMERA, PLAYER, DOG, SOLDIER, HOUSE1, HOUSE2, HOUSE3, HOUSE4, HOUSE5, HOUSE6, HOUSE7, HOUSE8, HOUSE9, HOUSE10, DECORATION1, DECORATION2, DECORATION3, DECORATION4, DECORATION5, DECORATION6, DECORATION7, DECORATION8, DECORATION9, DECORATION10, GRASS, VINTAGE, STONE;
	}

	public SpawnPoint(final int x, final int y, final float width, final float height, final GameObjectType got) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.got = got;
	}

	public GameObjectType getType() {
		return got;
	}
}
