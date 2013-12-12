package com.pixels.learningpunjabi;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.pixels.learningpunjabi.R;

public class Numbers extends Activity{
	
	
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
		        menu.add(0, 1, 0, "Item 1");
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
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(new Configuration());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    //WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.numbers_activity);
		
		//Typeface tf_punjabi = Typeface.createFromAsset(getAssets(), "AnmolUniBani.ttf");
		
		//TextView mm = (TextView)findViewById(R.id.mm_view);
		//mm.setTypeface(tf_punjabi);
		
		
		
		
	}

}
