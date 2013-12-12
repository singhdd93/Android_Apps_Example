package com.dds.alphabets;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DisplayAlphabets extends Activity{
	TextView tv1;
	ImageView iv1;
	Button back ; 
	ToggleButton playPause;



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		tv1=(TextView)findViewById(R.id.textView_display);
		iv1=(ImageView)findViewById(R.id.imageView_display);
		playPause=(ToggleButton)findViewById(R.id.togglePlayPause);
		
		MediaPlayer mp = new MediaPlayer();

		
		String text=getIntent().getStringExtra("stval");
		
		//tv1.setText(text);
		
		if(text.equals("A"))
		{
		tv1.setText("A - Apple");
		iv1.setImageResource(R.drawable.a);
		}
		else if(text.equals("B"))
		{
			tv1.setText("B - Banana");
			iv1.setImageResource(R.drawable.b);
		}
		else if(text.equals("C"))
		{
			tv1.setText("C - Cat");
			iv1.setImageResource(R.drawable.c);
		}
		else if(text.equals("D"))
		{
			tv1.setText("D - Dog");
			iv1.setImageResource(R.drawable.d);
		}
		else if(text.equals("E"))
		{
			tv1.setText("E - Eagle");
			iv1.setImageResource(R.drawable.e);
		}
		else if(text.equals("F"))
		{
			tv1.setText("F - Fan");
			iv1.setImageResource(R.drawable.f);
		}
		else if(text.equals("G"))
		{
			tv1.setText("G - Goat");
			iv1.setImageResource(R.drawable.g);
		}
		else if(text.equals("H"))
		{
			tv1.setText("H - Horse");
			iv1.setImageResource(R.drawable.h);
		}
		else if(text.equals("I"))
		{
			tv1.setText("I - India");
			iv1.setImageResource(R.drawable.j);
		}
		else if(text.equals("J"))
		{
			tv1.setText("J - Joker");
			iv1.setImageResource(R.drawable.j);
		}
		else if(text.equals("K"))
		{
			tv1.setText("K - Kangroo");
			iv1.setImageResource(R.drawable.k);
		}
		else if(text.equals("L"))
		{
			tv1.setText("L - Lion");
			iv1.setImageResource(R.drawable.l);
		}
		else if(text.equals("M"))
		{
			tv1.setText("M - Mango");
			iv1.setImageResource(R.drawable.m);
		}
		else if(text.equals("N"))
		{
			tv1.setText("N - Nuts");
			iv1.setImageResource(R.drawable.n);
		}
		else if(text.equals("O"))
		{
			tv1.setText("O - Orange");
			iv1.setImageResource(R.drawable.o);
		}
		else if(text.equals("P"))
		{
			tv1.setText("P - Papaya");
			iv1.setImageResource(R.drawable.p);
		}
		else if(text.equals("Q"))
		{
			tv1.setText("Q - Queen");
			iv1.setImageResource(R.drawable.q);
		}
		else if(text.equals("R"))
		{
			tv1.setText("R - Rabbit");
			iv1.setImageResource(R.drawable.r);
		}
		else if(text.equals("S"))
		{
			tv1.setText("S - SpiderMan");
			iv1.setImageResource(R.drawable.s);
		}
		else if(text.equals("T"))
		{
			tv1.setText("T - Television");
			iv1.setImageResource(R.drawable.t);
		}
		else if(text.equals("U"))
		{
			tv1.setText("U - Umbrella");
			iv1.setImageResource(R.drawable.u);
		}
		else if(text.equals("V"))
		{
			tv1.setText("V - Van");
			iv1.setImageResource(R.drawable.v);
		}
		else if(text.equals("W"))
		{
			tv1.setText("W - Wagon");
			iv1.setImageResource(R.drawable.w);
		}
		else if(text.equals("X"))
		{
			tv1.setText("X - Xylophone");
			iv1.setImageResource(R.drawable.w);
		}
		else if(text.equals("Y"))
		{
			tv1.setText("Y - Yak");
			iv1.setImageResource(R.drawable.y);
		}
		else if(text.equals("Z"))
		{
			tv1.setText("Z - Zebra");
			iv1.setImageResource(R.drawable.z);
		}
		
		
		back=(Button)findViewById(R.id.back_display);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				//Intent backIntent = new Intent(DisplayAlphabets.this, MainActivity.class);
				//DisplayAlphabets.this.startActivity(backIntent);
				
			}
		});
	}

	

	
}
