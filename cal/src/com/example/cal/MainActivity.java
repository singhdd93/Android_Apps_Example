package com.example.cal;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	
	double numberArray[];
	Button b1;
	int i=0;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	numberArray[1]=10;
	
	
	b1=(Button) findViewById(R.id.button1);
	
	b1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		i++;	
		
		t1.getText();
		}
	});
}
}
