package com.pixels.mcq;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends Activity{

	TextView cor, incor;
	int c,ic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_activity);
		cor = (TextView)findViewById(R.id.correctAnswered);
		incor = (TextView)findViewById(R.id.incorrectAnswered);
		
		c = getIntent().getIntExtra("correct", 0);
		ic = getIntent().getIntExtra("incorrect", 0);
		
		cor.setText(Integer.toString(c));
		incor.setText(Integer.toString(ic));
		
		
		
		
		
	}
}
