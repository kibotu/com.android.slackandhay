package com.android.slackandhay.input;

import android.util.Log;
import android.view.KeyEvent;

/**
 * not implemented yet (should provide keyboard input for android devices that
 * support it)
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public class InputKeyboard {
	private static final String TAG = InputKeyboard.class.getSimpleName();

	public boolean onKey(final KeyEvent event) {
		if (KeyEvent.ACTION_DOWN == event.getAction()) {
			Log.d(TAG, "Key was pressed: " + event.getUnicodeChar());
		}
		return false;
	}

}
