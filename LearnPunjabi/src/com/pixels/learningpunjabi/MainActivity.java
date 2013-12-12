package com.pixels.learningpunjabi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.pixels.learningpunjabi.R;

public class MainActivity extends Activity {

	Button moolMantar,punjabiAlphabets;
	
	
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
		        menu.add(0, 1, 0, "Share on Facebook");
				menu.add(0, 2, 1, "Item 2");
				menu.add(1, 10, 2, "Item3");
				menu.add(1, 11, 3, "Share");
				menu.add(1, 12, 4, "Exit");
	}

	
	private boolean MenuChoice(MenuItem item)
	{
		switch(item.getItemId())
		{
		case 1:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			// add the app link
			intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.dds.learningpunjabi");
			startActivity(Intent.createChooser(intent, "Share with Facebook"));
					return true;
					
		case 2:
			Toast.makeText(this, "Item 2 CLicked", Toast.LENGTH_SHORT).show();
			return true;
			
		case 10:
			Toast.makeText(this, "Item 3 CLicked", Toast.LENGTH_SHORT).show();
			return true;
			
		case 11:
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			  shareIntent.setType("text/plain");
			  shareIntent.putExtra(Intent.EXTRA_TEXT, "URLyouWantToShare");
			  startActivity(Intent.createChooser(shareIntent, "Share..."));
			return true;
			
		case 12:
			finish();
			Log.d("Exit", "We are exiting the app");
			return true;
		}
		return false;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    //WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		moolMantar = (Button)findViewById(R.id.mool_Mantar);
		punjabiAlphabets = (Button)findViewById(R.id.punjabi_Alphabets);
		
		moolMantar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent m = new Intent(MainActivity.this, Numbers.class);
			startActivity(m);
			}
		});
		
		punjabiAlphabets.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent m = new Intent(MainActivity.this, Alphabets.class);
			startActivity(m);
			}
		});
	}

}
