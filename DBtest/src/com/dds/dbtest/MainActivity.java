package com.dds.dbtest;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DBA db = new DBA(this);
		db.open();
		
		
		///db.insert("1", "Daman", 9988);
		//db.insert("2", "Aman", 22334);
		//db.insert("3", "Gurpreet", 00223);
		
		
		
		Cursor c = db.getAll();
		if (c.moveToFirst())
		{
		do {
		DisplayContact(c);
		} while (c.moveToNext());
		}
		db.close();
		
	

	}
	
	public void DisplayContact(Cursor c)
	{
	Toast.makeText(this,
	"id: " + c.getString(0) + "\n" +
	"Name: " + c.getString(1) + "\n" +
	"PhoneNo: " + c.getString(2) + "\n" + "Email: " + c.getString(3)+ "\n" + "DOB: " + c.getString(4),
	Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
