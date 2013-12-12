package com.dds.alertbox;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	Button but1,but2,but3,but4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		but1=(Button)findViewById(R.id.alertBox1Button);
		but2=(Button)findViewById(R.id.alertBox2Button);
		but3=(Button)findViewById(R.id.alertBox3Button);
		but4=(Button)findViewById(R.id.exit111);

		
		but2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder al =new AlertDialog.Builder(MainActivity.this);
				al.setTitle("Alert!");
				al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "You pressed OK ", Toast.LENGTH_SHORT).show();
					}
				});
				al.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "You pressed Cancel ", Toast.LENGTH_SHORT).show();

					}
				});
				al.show();
				
			}
		});
		
		
		but3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
				al.setIcon(R.drawable.ic_launcher);
				al.setTitle("Alert!!!");
				al.setMessage(" What are you doing ????");
				al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "You pressed OK ", Toast.LENGTH_SHORT).show();
					}
				});
				al.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "You pressed Cancel ", Toast.LENGTH_SHORT).show();

					}
				});
				al.setNeutralButton("BACK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "You pressed BACK ", Toast.LENGTH_SHORT).show();
					}
				});
				
				al.setOnDismissListener(new DialogInterface.OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "You dismissed the AlertBox ", Toast.LENGTH_SHORT).show();
					}
				});
				al.show();
						
					}
				});
		
		
		
		but4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder al =new AlertDialog.Builder(MainActivity.this);
				al.setTitle("Exit");
				al.setMessage("Do you want to exit ?");
				
				al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				
				al.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
				al.show();
				
			}
		});
				

		
		
	}
	
	@SuppressWarnings("deprecation")
	public void onClickAL1(View view)
	{ AlertDialog al = new AlertDialog.Builder(MainActivity.this).create();
	
	al.setButton("OK", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getBaseContext(), "OK!!!!", Toast.LENGTH_SHORT).show();	
		}	
		
	});
	
	al.setTitle("Alert!");
	al.setMessage("Lets See...");
	al.show();
		
	}
	


}
