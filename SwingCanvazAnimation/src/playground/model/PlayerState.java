package playground.model;

public enum PlayerState {

		/** iddling **/
		IDLE_FRONT ("IDLE_FRONT"),
		IDLE_RIGHT ("IDLE_RIGHT"),
		IDLE_BACK ("IDLE_BACK"),
		IDLE_LEFT ("IDLE_LEFT"),
		
		/** walking **/
		WALK_FRONT ("WALK_TOP"),
		WALK_RIGHT ("WALK_RIGHT"),
		WALK_BACK ("WALK_BOTTOM"),
		WALK_LEFT ("WALK_LEFT"),
		
		/** hitting **/
		HIT_FRONT ("HIT_TOP"),
		HIT_RIGHT ("HIT_RIGHT"),
		HIT_BACK ("HIT_BOTTOM"),
		HIT_LEFT ("HIT_LEFT"),
		
		/** hurting **/
		HURTING_FRONT ("HURTING_TOP"),
		HURTING_RIGHT ("HURTING_RIGHT"),
		HURTING_BACK ("HURTING_BOTTOM"),
		HURTING_LEFT ("HURTING_LEFT"),
		
		/** being dead **/
		DEAD ("DEAD"),
		
		/** blinking **/
		BLINK_FRONT ("BLINK_FRONT"),
		
		/** blocking **/
		BLOCK_FRONT ("BLOCK_TOP"),
		BLOCK_RIGHT ("BLOCK_RIGHT"),
		BLOCK_BACK ("BLOCK_BOTTOM"),
		BLOCK_LEFT ("BLOCK_LEFT"),
		
		/** running **/
		RUN_FRONT ("RUN_TOP"),
		RUN_RIGHT ("RUN_RIGHT"),
		RUN_BACK ("RUN_BOTTOM"),
		RUN_LEFT("RUN_LEFT");
		
		private final String name;
		
		private PlayerState(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
