package com.example.passingdata;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
public String value1;
EditText tv1;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	
	Button but1=(Button) findViewById(R.id.button1);
	tv1=(EditText) findViewById(R.id.getText_main);
	
	
	but1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			value1=tv1.getText().toString();	
			
			Intent set=new Intent(MainActivity.this, Act2.class);
			set.putExtra("abc", value1);
			MainActivity.this.startActivity(set);
			
		}
	});
	
}

}
