package com.android.slackandhay.grid;

public enum GridDirection {
	
		// define directions in clockwise order
		// the first one defined is implicitly taken to be
		// the "neutral direction" which leaves you were you
		// started
		NEUTRAL (0, 0),
		NORTH (0, -1),
		NORTHEAST(1, -1),
		EAST(1, 0),
		SOUTHEAST(1, 1),
		SOUTH(0, 1),
		SOUTHWEST(-1, 1),
		WEST(-1,0),
		NORTHWEST(-1,-1);
		
		private static final GridDirection[] clockwise = 
			new GridDirection[GridDirection.values().length - 1]; 
		static {
			GridDirection[] values = GridDirection.values();
			for (int i = 0; i < clockwise.length; i++) {
				clockwise[i] = values[i + 1];
			}
		};
			
		final int xModifier;
		final int yModifier;
		
		private GridDirection(int xModifier, int yModifier) {
			this.xModifier = xModifier;
			this.yModifier = yModifier;
		}

		public GridDirection nextClockwise() {
			if (this == NEUTRAL) {
				return NEUTRAL;
			}
			return clockwise[this.ordinal() % clockwise.length];
		}
		
		public GridDirection nextCounterClockwise() {
			if (this == NEUTRAL) {
				return NEUTRAL;
			}
			return clockwise[(this.ordinal()-2) % clockwise.length];
		}

}
