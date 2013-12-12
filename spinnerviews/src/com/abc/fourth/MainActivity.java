package com.abc.fourth;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

String[] abc;


@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		abc=getResources().getStringArray(R.array.abc);
		
		Spinner spin = (Spinner) findViewById(R.id.spinner1);
		
		ArrayAdapter<String> d= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,abc);
		spin.setAdapter(d);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				int index=arg0.getSelectedItemPosition();
				Toast.makeText(getBaseContext(), abc[index], Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}


}
