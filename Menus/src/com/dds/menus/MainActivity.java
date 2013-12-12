package com.dds.menus;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	
	    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		return true;
		}
	
	

		public boolean onOptionsItemSelected(MenuItem item)
		{
		return MenuChoice(item);
		}
	
	public void CreateMenu (Menu menu)
	{
		MenuItem m1 = menu.add(0, 1, 0, "Item 1");
				{
			       m1.setIcon(R.drawable.ic_launcher);
			       m1.setNumericShortcut('1');
			
				}
				menu.add(0, 2, 1, "Item 2");
				menu.add(1, 10, 2, "Item3");
				menu.add(1, 11, 3, "Item4");
				menu.add(1, 12, 4, "Exit");
	}

	
	private boolean MenuChoice(MenuItem item)
	{
		switch(item.getItemId())
		{
		case 1:
			Toast.makeText(this, "Item 1 CLicked", Toast.LENGTH_SHORT).show();
					return true;
					
		case 2:
			Toast.makeText(this, "Item 2 CLicked", Toast.LENGTH_SHORT).show();
			return true;
			
		case 10:
			Toast.makeText(this, "Item 3 CLicked", Toast.LENGTH_SHORT).show();
			return true;
			
		case 11:
			Toast.makeText(this, "Item 4 CLicked", Toast.LENGTH_SHORT).show();
			return true;
			
		case 12:
			finish();
			Log.d("Exit", "We are exiting the app");
			return true;
		}
		return false;
	}
}
