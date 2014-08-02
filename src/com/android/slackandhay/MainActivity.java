package com.android.slackandhay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity: ", "starting slackandhaymain");

        setContentView(R.layout.main);
        Intent i = new Intent(this, SlackAndHayMain.class);
        startActivity(i);
    }
}