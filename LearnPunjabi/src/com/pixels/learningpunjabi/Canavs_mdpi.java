package com.pixels.learningpunjabi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.pixels.learningpunjabi.R;

public class Canavs_mdpi extends Activity {
	
	LinearLayout canvasAlphabets;
	CanvasView myView;
	Button reset,backBut;
	ImageView alphabetMainImage1;
	
	public void setDisplay(int number)
	{
		switch(number)
		{
		case 1:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f01_01);
			//alphabetMainImage2.setImageResource(R.drawable.f01_02);
			//alphabetMainImage3.setImageResource(R.drawable.f01_03);
			//d = getResources().getDrawable(R.drawable.l1);
			//canvasAlphabets.setBackground(d);
			//mp = MediaPlayer.create(this, R.raw.s01);
			//mp.start();
			//curValue = 1;
			
			//myView.clearCanvas();
			break;
		case 2:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f02_01);
			//alphabetMainImage2.setImageResource(R.drawable.f02_02);
			//alphabetMainImage3.setImageResource(R.drawable.f02_03);
			//mp = MediaPlayer.create(this, R.raw.s02);
			//mp.start();
			//d = getResources().getDrawable(R.drawable.l2);
			//canvasAlphabets.setBackground(d);
			//myView.clearCanvas();
			//curValue = 2;
			break;
		case 3:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f03_01);
			//alphabetMainImage2.setImageResource(R.drawable.f03_02);
			//alphabetMainImage3.setImageResource(R.drawable.f03_03);
			//mp = MediaPlayer.create(this, R.raw.s03);
			//myView.clearCanvas();
			//mp.start();
			//curValue = 3;
			break;
		case 4:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f04_01);
			//alphabetMainImage2.setImageResource(R.drawable.f04_02);
			//alphabetMainImage3.setImageResource(R.drawable.f04_03);
			//mp = MediaPlayer.create(this, R.raw.s04);
			//myView.clearCanvas();
			//mp.start();
			//curValue = 4;
			break;
		case 5:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f05_01);
			//alphabetMainImage2.setImageResource(R.drawable.f05_02);
			//alphabetMainImage3.setImageResource(R.drawable.f05_03);
			//curValue = 5;
			//mp = MediaPlayer.create(this, R.raw.s05);
			//mp.start();
			//myView.clearCanvas();
			break;
		case 6:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f06_01);
			//alphabetMainImage2.setImageResource(R.drawable.f06_02);
			//alphabetMainImage3.setImageResource(R.drawable.f06_03);
			//curValue = 6;
			//mp = MediaPlayer.create(this, R.raw.s06);
			//mp.start();
			//myView.clearCanvas();
			break;
		case 7:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f07_01);
			//alphabetMainImage2.setImageResource(R.drawable.f07_02);
			//alphabetMainImage3.setImageResource(R.drawable.f07_03);
			//curValue = 7;
			//mp = MediaPlayer.create(this, R.raw.s07);
			//mp.start();
			//myView.clearCanvas();
			break;
		case 8:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f08_01);
			//alphabetMainImage2.setImageResource(R.drawable.f08_02);
			//alphabetMainImage3.setImageResource(R.drawable.f08_03);
			//curValue = 8;
			//mp = MediaPlayer.create(this, R.raw.s08);
			//mp.start();
			//myView.clearCanvas();
			break;
		case 9:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f09_01);
			//alphabetMainImage2.setImageResource(R.drawable.f09_02);
			//alphabetMainImage3.setImageResource(R.drawable.f09_03);
			//curValue = 9;
			//mp = MediaPlayer.create(this, R.raw.s09);
			//mp.start();
			//myView.clearCanvas();
			break;
		case 10:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f10_01);
			//alphabetMainImage2.setImageResource(R.drawable.blank);
			//alphabetMainImage3.setImageResource(R.drawable.blank);
			//curValue = 10;
			//mp = MediaPlayer.create(this, R.raw.s10);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 11:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f11_01);
			//alphabetMainImage2.setImageResource(R.drawable.f11_02);
			//alphabetMainImage3.setImageResource(R.drawable.f11_03);
			//curValue = 11;
			//mp = MediaPlayer.create(this, R.raw.s11);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 12:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f12_01);
			//alphabetMainImage2.setImageResource(R.drawable.f12_02);
			//alphabetMainImage3.setImageResource(R.drawable.f12_03);
			//curValue = 12;
			//mp = MediaPlayer.create(this, R.raw.s12);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 13:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f13_01);
			//alphabetMainImage2.setImageResource(R.drawable.f13_02);
			//alphabetMainImage3.setImageResource(R.drawable.f13_03);
			//curValue = 13;
			//mp = MediaPlayer.create(this, R.raw.s13);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 14:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f14_01);
			//alphabetMainImage2.setImageResource(R.drawable.f14_02);
			//alphabetMainImage3.setImageResource(R.drawable.f14_03);
			//curValue = 14;
			//mp = MediaPlayer.create(this, R.raw.s14);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 15:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f15_01);
			//alphabetMainImage2.setImageResource(R.drawable.blank);
			//alphabetMainImage3.setImageResource(R.drawable.blank);
			//curValue = 15;
			//mp = MediaPlayer.create(this, R.raw.s15);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 16:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f16_01);
			//alphabetMainImage2.setImageResource(R.drawable.f16_02);
			//alphabetMainImage3.setImageResource(R.drawable.f16_03);
			//curValue = 16;
			//mp = MediaPlayer.create(this, R.raw.s16);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 17:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f17_01);
			//alphabetMainImage2.setImageResource(R.drawable.f17_02);
			//alphabetMainImage3.setImageResource(R.drawable.f17_03);
			//curValue = 17;
			//mp = MediaPlayer.create(this, R.raw.s17);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 18:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f18_01);
			//alphabetMainImage2.setImageResource(R.drawable.f18_02);
			//alphabetMainImage3.setImageResource(R.drawable.f18_03);
			//curValue = 18;
			//mp = MediaPlayer.create(this, R.raw.s18);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 19:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f19_01);
			//alphabetMainImage2.setImageResource(R.drawable.f19_02);
			//alphabetMainImage3.setImageResource(R.drawable.f19_03);
			//curValue = 19;
			//mp = MediaPlayer.create(this, R.raw.s19);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 20:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f20_01);
			//alphabetMainImage2.setImageResource(R.drawable.blank);
			//alphabetMainImage3.setImageResource(R.drawable.blank);
			//curValue = 20;
			//mp = MediaPlayer.create(this, R.raw.s20);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 21:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f21_01);
			//alphabetMainImage2.setImageResource(R.drawable.f21_02);
			//alphabetMainImage3.setImageResource(R.drawable.f21_03);
			//curValue = 21;
			//mp = MediaPlayer.create(this, R.raw.s21);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 22:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f22_01);
			//alphabetMainImage2.setImageResource(R.drawable.f22_02);
			//alphabetMainImage3.setImageResource(R.drawable.f22_03);
			//curValue = 22;
			//mp = MediaPlayer.create(this, R.raw.s22);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 23:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f23_01);
			//alphabetMainImage2.setImageResource(R.drawable.f23_02);
			//alphabetMainImage3.setImageResource(R.drawable.f23_03);
			//curValue = 23;
			//mp = MediaPlayer.create(this, R.raw.s23);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 24:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f24_01);
			//alphabetMainImage2.setImageResource(R.drawable.f24_02);
			//alphabetMainImage3.setImageResource(R.drawable.f24_03);
			//curValue = 24;
			//mp = MediaPlayer.create(this, R.raw.s24);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 25:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f25_01);
			//alphabetMainImage2.setImageResource(R.drawable.f25_02);
			//alphabetMainImage3.setImageResource(R.drawable.f25_03);
			//curValue = 25;
			//mp = MediaPlayer.create(this, R.raw.s25);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 26:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f26_01);
			//alphabetMainImage2.setImageResource(R.drawable.f26_02);
			//alphabetMainImage3.setImageResource(R.drawable.f26_03);
			//curValue = 26;
			//mp = MediaPlayer.create(this, R.raw.s26);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 27:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f27_01);
			//alphabetMainImage2.setImageResource(R.drawable.f27_02);
			//alphabetMainImage3.setImageResource(R.drawable.f27_03);
			//curValue = 27;
			//mp = MediaPlayer.create(this, R.raw.s27);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 28:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f28_01);
			//alphabetMainImage2.setImageResource(R.drawable.f28_02);
			//alphabetMainImage3.setImageResource(R.drawable.f28_03);
			//curValue = 28;
			//mp = MediaPlayer.create(this, R.raw.s28);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 29:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f29_01);
			//alphabetMainImage2.setImageResource(R.drawable.f29_02);
			//alphabetMainImage3.setImageResource(R.drawable.f29_03);
			//curValue = 29;
			//mp = MediaPlayer.create(this, R.raw.s29);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 30:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f30_01);
			//alphabetMainImage2.setImageResource(R.drawable.f30_02);
			//alphabetMainImage3.setImageResource(R.drawable.f30_03);
			//curValue = 30;
			//mp = MediaPlayer.create(this, R.raw.s30);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 31:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f31_01);
			//alphabetMainImage2.setImageResource(R.drawable.f31_02);
			//alphabetMainImage3.setImageResource(R.drawable.f31_03);
			//curValue = 31;
			//mp = MediaPlayer.create(this, R.raw.s31);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 32:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f32_01);
			//alphabetMainImage2.setImageResource(R.drawable.f32_02);
			//alphabetMainImage3.setImageResource(R.drawable.f32_03);
			//curValue = 32;
			//mp = MediaPlayer.create(this, R.raw.s32);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 33:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f33_01);
			//alphabetMainImage2.setImageResource(R.drawable.f33_02);
			//alphabetMainImage3.setImageResource(R.drawable.f33_03);
			//curValue = 33;
			//mp = MediaPlayer.create(this, R.raw.s33);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 34:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f34_01);
			//alphabetMainImage2.setImageResource(R.drawable.f34_02);
			//alphabetMainImage3.setImageResource(R.drawable.f34_03);
			//curValue = 34;
			//mp = MediaPlayer.create(this, R.raw.s34);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 35:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f35_01);
			//alphabetMainImage2.setImageResource(R.drawable.blank);
			//alphabetMainImage3.setImageResource(R.drawable.blank);
			//curValue = 35;
			//mp = MediaPlayer.create(this, R.raw.s35);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 36:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f36_01);
			//alphabetMainImage2.setImageResource(R.drawable.f36_02);
			//alphabetMainImage3.setImageResource(R.drawable.f36_03);
			//curValue = 36;
			//mp = MediaPlayer.create(this, R.raw.s36);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 37:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f37_01);
			//alphabetMainImage2.setImageResource(R.drawable.f37_02);
			//alphabetMainImage3.setImageResource(R.drawable.f37_03);
			//curValue = 37;
			//mp = MediaPlayer.create(this, R.raw.s37);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 38:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f38_01);
			//alphabetMainImage2.setImageResource(R.drawable.f38_02);
			//alphabetMainImage3.setImageResource(R.drawable.f38_03);
			//curValue = 38;
			//mp = MediaPlayer.create(this, R.raw.s38);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 39:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f39_01);
			//alphabetMainImage2.setImageResource(R.drawable.f39_02);
			//alphabetMainImage3.setImageResource(R.drawable.f39_03);
			//curValue = 39;
			//mp = MediaPlayer.create(this, R.raw.s39);
			//mp.start();    
			//myView.clearCanvas();
			break;
		case 40:
			//mp.stop();
			alphabetMainImage1.setImageResource(R.drawable.f40_01);
			//alphabetMainImage2.setImageResource(R.drawable.f40_02);
			//alphabetMainImage3.setImageResource(R.drawable.f40_03);
			////curValue = 40;
			//mp = MediaPlayer.create(this, R.raw.s40);
			//mp.start();    
			//myView.clearCanvas();
			break;	
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    //WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.canvas_activity);
		backBut = (Button)findViewById(R.id.backButton_CanavsMDPI);
		reset = (Button)findViewById(R.id.reset_canvas);
		
		myView = new CanvasView(this, null);
		canvasAlphabets = (LinearLayout)findViewById(R.id.canvas_Alphabets);
		canvasAlphabets.addView(myView);
		alphabetMainImage1 = (ImageView)findViewById(R.id.alphabetView_CanvasMDPI);
		int recievedNo = getIntent().getExtras().getInt("val");
		setDisplay(recievedNo);
				backBut.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		reset.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myView.clearCanvas();
				
			}
		});
	}

}
