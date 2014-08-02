package com.android.slackandhay.sound;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

import com.android.slackandhay.R;

public class SoundEngine implements OnLoadCompleteListener  {

	private static final String TAG = SoundEngine.class.getSimpleName();

	public enum Sounds{
		HIT_CONCRETE, HIT_SWORD, HIT_FLESH, FIGHT_BG
	};

	public static final int PRIORITY_ENVIRONMENT = 0;
	public static final int PRIORITY_INTERACTION = 1;
	public static final int PRIORITY_HIGH = 2;
	public static final int PRIORITY_NOTIFICATIONS = 3;

	private static SoundEngine instance = new SoundEngine();

	private final SoundPool soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
	private final HashMap<Sounds, SoundEffect> soundPoolMap = new HashMap<Sounds, SoundEffect>();

	// singeleton
	private SoundEngine(){
	}

	
	public void loadSound(final Context context)  {
		//	AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE); TODO benutz mich :)
		soundPoolMap.put(Sounds.HIT_CONCRETE, new SoundEffect(soundPool.load(context, R.raw.sword1, PRIORITY_ENVIRONMENT), 0.2f, PRIORITY_ENVIRONMENT));
		soundPoolMap.put(Sounds.HIT_SWORD,	new SoundEffect(soundPool.load(context, R.raw.sword2, PRIORITY_ENVIRONMENT), 0.3f, PRIORITY_INTERACTION));
		soundPoolMap.put(Sounds.HIT_FLESH,new SoundEffect(soundPool.load(context, R.raw.sword3, PRIORITY_ENVIRONMENT),0.1f, PRIORITY_INTERACTION));
//		soundPoolMap.put(Sounds.FIGHT_BG,new SoundEffect(soundPool.load(context, R.raw.fightbg, PRIORITY_ENVIRONMENT),0.1f, PRIORITY_INTERACTION));
		soundPool.setOnLoadCompleteListener(this);
	}

	public static SoundEngine getInstance(){
		return instance;
	}

	@Override
	public void onLoadComplete(final SoundPool soundPool, final int sampleID, final int status) {
		for(final SoundEffect se : soundPoolMap.values()){
			if(se.getSoundPoolID() == sampleID){
				Log.d(TAG, "Loaded Sound number: "+sampleID);
				se.setLoaded();
			}
		}
	}

	public void playSound(final Sounds sound){
		final SoundEffect se = soundPoolMap.get(sound);
		if(se.isLoaded()){
			soundPool.play(se.getSoundPoolID(), 1f, 1f, se.getPriority(), 0, se.getPlaySpeed());
		}
	}
}
