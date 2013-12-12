package com.dds.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Register4 extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register4);
		
		//Setting emailid
		TextView setEmail=(TextView)findViewById(R.id.setEmailr3);
		String email=MainActivity.emailid;
		setEmail.setText(email);
		
		Handler a=new Handler();
		a.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, 4000);
	}
	

}
