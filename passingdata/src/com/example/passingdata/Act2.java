package com.example.passingdata;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Act2 extends Activity{
	TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act2);
		
		tv1=(TextView)findViewById(R.id.setText_act2);
		
		String a=getIntent().getStringExtra("abc");
		
		tv1.setText(a);
	}

}
