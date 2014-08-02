package com.android.slackandhay.sound;

import java.util.HashMap;


import com.android.slackandhay.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

public class SoundEngine implements OnLoadCompleteListener {
    public static final int PRIORITY_ENVIRONMENT = 0;
    public static final int PRIORITY_INTERACTION = 1;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_NOTIFICATIONS = 3;
    
    //TODO dies ist mega haeslich! (static singleton
    private static SoundEngine instance;
    
    private static final String TAG = "SoundEngine";
    
    public enum Sounds{
    	HIT_CONCRETE, HIT_SWORD, HIT_FLESH
    };
    
	private SoundPool soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
	private HashMap<Sounds, SoundEffect> soundPoolMap = new HashMap<Sounds, SoundEffect>();
	private AudioManager audioManager;
	//private Context context;
	public SoundEngine(Context context){
		
		//this.context = context;
		audioManager = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
		
		soundPoolMap.put(Sounds.HIT_CONCRETE,
				new SoundEffect(soundPool.load(context, R.raw.concrete_colid_c, PRIORITY_ENVIRONMENT), 0.2f, PRIORITY_ENVIRONMENT));
		soundPoolMap.put(Sounds.HIT_SWORD, 
				new SoundEffect(soundPool.load(context, R.raw.land_hard_water, PRIORITY_ENVIRONMENT), 0.3f, PRIORITY_INTERACTION));
		soundPoolMap.put(Sounds.HIT_FLESH,
				new SoundEffect(soundPool.load(context, R.raw.bullethit_character_b, PRIORITY_ENVIRONMENT),0.1f, PRIORITY_INTERACTION));
		Log.d(TAG, "SoundEngine Initialized");
		
		
		
		soundPool.setOnLoadCompleteListener(this);
		
		instance = this;
	}
	
	public static SoundEngine getInstance(){
		return instance;		
	}
	
	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleID, int status) {
		for( SoundEffect se : soundPoolMap.values()){
			if(se.getSoundPoolID() == sampleID){
				Log.d(TAG, "Loaded Sound number: "+sampleID);
				se.setLoaded();
			}
		}
		
	}
	
	public void playSound(Sounds sound){
		final SoundEffect se = soundPoolMap.get(sound);
		if(se.isLoaded()){
			soundPool.play(se.getSoundPoolID(), 1f, 1f, se.getPriority(), 0, se.getPlaySpeed());
		}
	}
}
