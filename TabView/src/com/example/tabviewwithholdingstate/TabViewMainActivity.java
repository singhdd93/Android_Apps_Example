package com.example.tabviewwithholdingstate;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

@SuppressWarnings({ "deprecation", "unused" })
public class TabViewMainActivity extends TabActivity {
	TabHost tabHost; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tabview);
		Resources ressources = getResources(); 
		tabHost = getTabHost(); 

		//==================================================================================================================================================
		// Placement tab
		//==================================================================================================================================================		
		Intent intentPlacement = new Intent().setClass(this, PlacementActivity.class);
		TabSpec tabSpecPlacement = tabHost
				.newTabSpec("Placement")
				.setIndicator("Placement", ressources.getDrawable(R.drawable.new_icon_placement))
				.setContent(intentPlacement);

		//==================================================================================================================================================
		// Exam tab
		//==================================================================================================================================================		
		Intent ExamIntent = new Intent(this,ExamActivity.class);
		TabSpec tabSpecExam = tabHost
				.newTabSpec("Exam")
				.setIndicator("Exam", ressources.getDrawable(R.drawable.new_icon_exam))
				.setContent(ExamIntent);
		//==================================================================================================================================================
		// Progress tab
		//==================================================================================================================================================		
		Intent intentProgress = new Intent().setClass(this, ProgressActivity.class);
		//FLAG_ACTIVITY_CLEAR_TOP will help to recreate the activity every time u hit that tab
		intentProgress.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec tabSpecProgress = tabHost
				.newTabSpec("Progress")
				.setIndicator("Progress", ressources.getDrawable(R.drawable.new_icon_progress))
				.setContent(intentProgress);

		//==================================================================================================================================================
		// Sync tab
		//==================================================================================================================================================		
		final Intent intentSync = new Intent().setClass(this, SyncActivity.class);

		TabSpec tabSpecSync = tabHost
				.newTabSpec("Sync")
				.setIndicator("Sync", ressources.getDrawable(R.drawable.new_icon_sync))
				.setContent(intentSync);

		//==================================================================================================================================================
		// Home tab
		//==================================================================================================================================================		
		Intent home = new Intent().setClass(this, HomeActivity.class);
		TabSpec tabHome = tabHost
				.newTabSpec("Home")
				.setIndicator("Home", ressources.getDrawable(R.drawable.new_icon_home))
				.setContent(home);

		// add all tabs 
		tabHost.addTab(tabHome);
		tabHost.addTab(tabSpecPlacement);
		tabHost.addTab(tabSpecExam);
		tabHost.addTab(tabSpecProgress);
		tabHost.addTab(tabSpecSync);


		tabHost.setCurrentTab(2);
		
//	     //Changing tab height===============================
//	       tabHost.getTabWidget().getChildAt(0).getLayoutParams().height =85;
//	       tabHost.getTabWidget().getChildAt(1).getLayoutParams().height =85;
//	       tabHost.getTabWidget().getChildAt(2).getLayoutParams().height =85;
//	       tabHost.getTabWidget().getChildAt(3).getLayoutParams().height =85;
//	       tabHost.getTabWidget().getChildAt(4).getLayoutParams().height =85;
//	     //Changing tab height===============================


//		//Tab Changed Event
//		tabHost.setOnTabChangedListener(new OnTabChangeListener(){
//			@Override
//			public void onTabChanged(String tabId) {
//				Log.i("TabId :", tabId);
//				setSelectedTabColor(); 
//			}
//		});
//
//		setSelectedTabColor();
	}

//	//changing background tabview colors 
//	private void setSelectedTabColor() 
//	{
//		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)  
//		{  
//			//unselected color
//			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#6E6E6E"));
//		}  
//		//selected color
//		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#E0E0F8")); 
//	}
	
}

