package com.pixels.learningpunjabi;




import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.pixels.learningpunjabi.R;

public class Alphabets extends Activity{
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		return true;
		}
	
	

		public boolean onOptionsItemSelected(MenuItem item)
		{
			switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	        	return MenuChoice(item);
	    }
		
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
	
	Button butLetter1,butLetter2,butLetter3,butLetter4,butLetter5,butLetter6,butLetter7,butLetter8,butLetter9,butLetter10,butLetter11,butLetter12,butLetter13,butLetter14,butLetter15,butLetter16,butLetter17,butLetter18,butLetter19,butLetter20,butLetter21,butLetter22,butLetter23,butLetter24,butLetter25,butLetter26,butLetter27,butLetter28,butLetter29,butLetter30,butLetter31,butLetter32,butLetter33,butLetter34,butLetter35,butLetter36,butLetter37,butLetter38,butLetter39,butLetter40;
	int butNumber = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    //WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alphabets_activity);
		

		
		//Attaching Buttons to Views
		butLetter1 = (Button)findViewById(R.id.letter1);
		butLetter2 = (Button)findViewById(R.id.letter2);
		butLetter3 = (Button)findViewById(R.id.letter3);
		butLetter4 = (Button)findViewById(R.id.letter4);
		butLetter5 = (Button)findViewById(R.id.letter5);
		butLetter6 = (Button)findViewById(R.id.letter6);
		butLetter7 = (Button)findViewById(R.id.letter7);
		butLetter8 = (Button)findViewById(R.id.letter8);
		butLetter9 = (Button)findViewById(R.id.letter9);
		butLetter10 = (Button)findViewById(R.id.letter10);
		butLetter11 = (Button)findViewById(R.id.letter11);
		butLetter12 = (Button)findViewById(R.id.letter12);
		butLetter13 = (Button)findViewById(R.id.letter13);
		butLetter14 = (Button)findViewById(R.id.letter14);
		butLetter15 = (Button)findViewById(R.id.letter15);
		butLetter16 = (Button)findViewById(R.id.letter16);
		butLetter17 = (Button)findViewById(R.id.letter17);
		butLetter18 = (Button)findViewById(R.id.letter18);
		butLetter19 = (Button)findViewById(R.id.letter19);
		butLetter20 = (Button)findViewById(R.id.letter20);
		butLetter21 = (Button)findViewById(R.id.letter21);
		butLetter22 = (Button)findViewById(R.id.letter22);
		butLetter23 = (Button)findViewById(R.id.letter23);
		butLetter24 = (Button)findViewById(R.id.letter24);
		butLetter25 = (Button)findViewById(R.id.letter25);
		butLetter26 = (Button)findViewById(R.id.letter26);
		butLetter27 = (Button)findViewById(R.id.letter27);
		butLetter28 = (Button)findViewById(R.id.letter28);
		butLetter29 = (Button)findViewById(R.id.letter29);
		butLetter30 = (Button)findViewById(R.id.letter30);
		butLetter31 = (Button)findViewById(R.id.letter31);
		butLetter32 = (Button)findViewById(R.id.letter32);
		butLetter33 = (Button)findViewById(R.id.letter33);
		butLetter34 = (Button)findViewById(R.id.letter34);
		butLetter35 = (Button)findViewById(R.id.letter35);
		butLetter36 = (Button)findViewById(R.id.letter36);
		butLetter37 = (Button)findViewById(R.id.letter37);
		butLetter38 = (Button)findViewById(R.id.letter38);
		butLetter39 = (Button)findViewById(R.id.letter39);
		butLetter40 = (Button)findViewById(R.id.letter40);
		
		final int screenSize = getResources().getConfiguration().screenLayout &
		        Configuration.SCREENLAYOUT_SIZE_MASK;
		final int smallScreen = Configuration.SCREENLAYOUT_SIZE_SMALL;
		final int undefScreen = Configuration.SCREENLAYOUT_LAYOUTDIR_UNDEFINED;
		
		butLetter1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 1;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
					Log.d("HINT", "USING SMALL LAYOUT");
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		
		butLetter2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 2;
				
				if(screenSize == smallScreen || screenSize == undefScreen )
			{
			Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
			next.putExtra("val",butNumber );
			startActivity(next);
			}
				else
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
				next.putExtra("val",butNumber );
				startActivity(next);
					
				}
			}
		});
		butLetter3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 3;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 4;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 5;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 6;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 7;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter8.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 8;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter9.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 9;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter10.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 10;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter11.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 11;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		
		butLetter12.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 12;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter13.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 13;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter14.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 14;
			if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			
			}
		});
		butLetter15.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 15;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter16.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 16;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter17.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 17;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter18.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 18;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter19.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 19;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter20.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 20;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter21.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 21;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		
		butLetter22.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 22;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter23.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 23;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter24.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 24;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter25.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 25;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter26.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 26;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter27.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 27;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter28.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 28;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter29.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 29;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter30.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 30;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter31.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 31;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		
		butLetter32.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 32;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter33.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 33;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter34.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 34;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter35.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 35;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter36.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 36;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter37.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 37;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter38.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 38;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter39.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 39;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		butLetter40.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				butNumber = 40;
				if(screenSize == smallScreen || screenSize == undefScreen )
				{
				Intent next = new Intent(Alphabets.this, DisplayAlphabetsMdpi.class);
				next.putExtra("val",butNumber );
				startActivity(next);
				}
					else
					{
					Intent next = new Intent(Alphabets.this, DisplayAlphabets.class);
					next.putExtra("val",butNumber );
					startActivity(next);
						
					}
			}
		});
		
	}

}
