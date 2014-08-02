package com.android.slackandhay.sound;

public class SoundEffect {

	private final int priority;
	private final int soundPoolID;
	private final float randomness;
	private boolean isLoaded = false;

	public SoundEffect(final int soundPoolID, final float randomness, final int priority) {
		this.soundPoolID = soundPoolID;
		this.randomness = randomness;
		this.priority = priority;
	}

	public int getSoundPoolID() {
		return soundPoolID;
	}

	public float getPlaySpeed() {
		return randomness != 0f ? 1f + (float) Math.random() * randomness : 1f;
	}

	public void setLoaded() {
		isLoaded = true;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public int getPriority() {
		return priority;
	}
}
