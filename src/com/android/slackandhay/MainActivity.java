package com.android.slackandhay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * The Main Activity that is started once the app itself is run on the device.
 * It runs the actual slacknhay activity
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 *
 */
public class MainActivity extends Activity {

	private Intent slackandhay;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("MainActivity: ", "starting slackandhaymain");
		setContentView(R.layout.main);
		slackandhay = new Intent(this, SlackAndHay.class);
		startActivity(slackandhay);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		startActivity(slackandhay);
	}
}