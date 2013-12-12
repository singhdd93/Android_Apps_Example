package com.example.tabviewwithholdingstate;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
@SuppressWarnings("deprecation")
public class ChildActivityShowProgress extends ActivityGroup
{
	private ArrayList<View> history;
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		this.history = new ArrayList<View>();
		
		AlertDialog alertDialog = new AlertDialog.Builder(ChildActivityShowProgress.this.getParent()).create();
		alertDialog.setTitle("Hello ...");
		alertDialog.setMessage("Hello world this is Anirban !");
		alertDialog.setIcon(R.drawable.tick);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});
		alertDialog.show();
	}

	//@Override  
	public void onBackPressed() 
	{  
		Intent intMyProg = new Intent(this,ProgressActivity.class);
		View MyView = getLocalActivityManager()
				.startActivity("MyIntent", intMyProg
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
				.getDecorView();

		replaceView(MyView);
	}
	public void replaceView(View v) 
	{
		history.add(v);
		setContentView(v);
	}

}
