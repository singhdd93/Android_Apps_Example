package com.example.iudsqllite;

import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class showUserInfoSpinerActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_spiner);

		Spinner s = (Spinner)findViewById(R.id.SpinnerUserInfo);
		
		//---get all Records from database---
		DataBaseAdapter db = new DataBaseAdapter(this);
		db.open();
		Cursor c = db.getAllRecords();
		
		//create an array of a special data type MyData
		//this class is defined bellow
		//this will help to insert name and corresponding value to a dropDown
		
		final MyData items[] = new MyData[c.getCount()];
		int i = 0;
		
		if (c.moveToFirst())
		{
			do 
			{          
				//items[i] = new MyData("name","value");
				items[i] = new MyData( c.getString(1) + " " + c.getString(2), c.getString(0));
				i++;
			} while (c.moveToNext());
		}
		db.close();
		
		ArrayAdapter<MyData> adapter = new ArrayAdapter<MyData>( this,android.R.layout.simple_spinner_item,items );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		
		//on value select from that dropdown it will get that id of Corresponding value
		s.setOnItemSelectedListener
		(
			new AdapterView.OnItemSelectedListener() 
			{
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
				{
					MyData d = items[position];
					Toast.makeText(showUserInfoSpinerActivity.this,"Selected value is: "+d.getValue() +
																   "\nSelected text is: "+d.getSpinnerText(), 
																   Toast.LENGTH_LONG).show();
				}

				public void onNothingSelected(AdapterView<?> parent) 
				{
					
				}
			}
		);
	}

	class MyData 
	{
		public MyData( String spinnerText, String value ) {
			this.spinnerText = spinnerText;
			this.value = value;
		}

		public String getSpinnerText() {
			return spinnerText;
		}

		public String getValue() {
			return value;
		}

		public String toString() {
			return spinnerText;
		}

		String spinnerText;
		String value;
	}
}
