package com.example.tabviewwithholdingstate;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressWarnings("deprecation")
public class ProgressActivity extends ActivityGroup 
{
	private ArrayList<View> history;
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_tabview);
		this.history = new ArrayList<View>();
	}
	public void showProgress(View v) 
	{
		Intent intMyProg = new Intent(this,ChildActivityShowProgress.class);
		//startActivity(intMyProg);

//		This will help to open a new activity within existing TabView
//		Need to extend ActivityGroup
//		Need to declare "private ArrayList<View> history;"
//		Need to Define "this.history = new ArrayList<View>();" within onCreate
		
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
	
//	Problem: when clicking Back button from child Activity it calling this (parent activity) OnBackButton
//	Logic: checking current activity name if it is child activity then call onBackPressed of child Activity
	
	//@Override  
	public void onBackPressed() 
	{  
		LocalActivityManager localManager = getLocalActivityManager();
	    Activity currentActivity = null;
	    if (localManager!=null)
	    {
	       currentActivity = localManager.getCurrentActivity();
	    }

	    String activityName = null;
	    if (currentActivity!=null)
	    {
	        activityName = currentActivity.toString();
	    }

	    if(activityName!=null && activityName.contains("ShowProgress"))
	    {
	        getCurrentActivity().onBackPressed();
	    }
	    else
	    {
	        finish();
	    }
	}
}