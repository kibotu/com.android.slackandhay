package com.android.slackandhay.input;


import android.util.Log;
import android.view.KeyEvent;

public class InputKeyboard extends InputType{
	private static final String TAG = "InputKeyboard";
	public boolean onKey(KeyEvent event) {
		if(KeyEvent.ACTION_DOWN == event.getAction()){
			Log.d(TAG, "Key was pressed: "+event.getUnicodeChar());
		}
		return false;
	}

}
