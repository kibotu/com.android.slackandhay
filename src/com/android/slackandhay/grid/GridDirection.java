package com.android.slackandhay.grid;

public enum GridDirection {

	// define directions in clockwise order
	// the first one defined is implicitly taken to be
	// the "neutral direction" which leaves you were you
	// started
	NEUTRAL (0, 0),
	NORTH (0, 1),
	NORTHEAST(1, 1),
	EAST(1, 0),
	SOUTHEAST(1, -1),
	SOUTH(0, -1),
	SOUTHWEST(-1, -1),
	WEST(-1,0),
	NORTHWEST(-1,1);

	final static GridDirection [] gd = GridDirection.values();

	private static final GridDirection[] clockwise = new GridDirection[GridDirection.values().length - 1];

	//preallocate...
	static GridDirection[] values = null;
	static {
		if(values == null ){
			values = GridDirection.values();
		}
		for (int i = 0; i < clockwise.length; i++) {
			clockwise[i] = values[i + 1];
		}
	};

	final int xModifier;
	final int yModifier;

	private GridDirection(final int xModifier, final int yModifier) {
		this.xModifier = xModifier;
		this.yModifier = yModifier;
	}

	public GridDirection nextClockwise() {
		if (this == NEUTRAL)
			return NEUTRAL;
		return clockwise[ordinal() % clockwise.length];
	}

	public GridDirection nextCounterClockwise() {
		if (this == NEUTRAL)
			return NEUTRAL;
		return clockwise[(ordinal()-2) % clockwise.length];
	}

	public static GridDirection calculateDirection(final int dx, final int dy){
		float dxf = 0f;
		float dyf = 0f;
		final float gdx = 0f;
		final float gdy = 0f;
		//shouldnt crash, because there will always be a nearest point.
		//Anyway, if it does crash, just initialize the following variable
		//instead of leaving it null. I consider null the wiser choice here.

		// TODO check if it still works (allocations are fine now)
		GridDirection nearestGD = null;
		final float nearestDistance = Float.MAX_VALUE;
		for(int i = 0; i < gd.length; i++) {
			dxf = dx-gd[i].xModifier;
			dyf = dy-gd[i].yModifier;
			if(nearestDistance>dxf*dxf+dyf*dyf){
				nearestGD = gd[i];
			}
		}
		//			for(GridDirection gd : GridDirection.values()){
		//				dxf = dx-gd.xModifier;
		//				dyf = dy-gd.yModifier;
		//				if(nearestDistance>(dxf*dxf+dyf*dyf)){
		//					nearestGD = gd;
		//				}
		//			}
		return nearestGD;
	}

}
