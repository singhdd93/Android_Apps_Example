package com.dds.rview;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity {

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash);
	
	Handler splash=new Handler();
	splash.postDelayed(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			finish();
			Intent tof1=new Intent(SplashActivity.this, F1.class);
			SplashActivity.this.startActivity(tof1);
		}
	}, 4000);
	
}
	
	
}
