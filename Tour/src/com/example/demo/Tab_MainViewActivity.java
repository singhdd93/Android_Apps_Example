package com.example.demo;

import com.example.tabActivity.Tab2;
import com.example.tabActivity.Tab3;
import com.example.tabActivity.Tab4;
import com.example.tabActivity.TourMainActivity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Tab_MainViewActivity extends TabActivity {
	TabHost tabHost; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab_view);
		Resources ressources = getResources(); 
		tabHost = getTabHost(); 

		//==================================================================================================================================================
		// Tour tab 
		//==================================================================================================================================================		
		Intent intentPlacement = new Intent().setClass(this, TourMainActivity.class);
		intentPlacement.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec tabSpecPlacement = tabHost
				.newTabSpec("Tour")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_tour))
				.setContent(intentPlacement);

		//==================================================================================================================================================
		// Exam tab
		//==================================================================================================================================================		
		Intent ExamIntent = new Intent(this,Tab2.class);
		ExamIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec tabSpecExam = tabHost
				.newTabSpec("Tab2")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_light_changing))
				.setContent(ExamIntent);
		//==================================================================================================================================================
		// Progress tab
		//==================================================================================================================================================		
		Intent intentProgress = new Intent().setClass(this, Tab3.class);
		intentProgress.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentProgress.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec tabSpecProgress = tabHost
				.newTabSpec("Tab3")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_light_changing))
				.setContent(intentProgress);

		//==================================================================================================================================================
		// Sync tab 
		//==================================================================================================================================================		
		final Intent intentSync = new Intent().setClass(this, Tab4.class);
		intentSync.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec tabSpecSync = tabHost
				.newTabSpec("Tab4")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_light_changing))
				.setContent(intentSync);



		// add all tabs 
		tabHost.addTab(tabSpecPlacement);
		tabHost.addTab(tabSpecExam);
		tabHost.addTab(tabSpecProgress);
		tabHost.addTab(tabSpecSync);


		tabHost.setCurrentTab(0);

	     //Changing tab height===============================
	       tabHost.getTabWidget().getChildAt(0).getLayoutParams().height =80;
	       tabHost.getTabWidget().getChildAt(1).getLayoutParams().height =80;
	       tabHost.getTabWidget().getChildAt(2).getLayoutParams().height =80;
	       tabHost.getTabWidget().getChildAt(3).getLayoutParams().height =80;
	     //Changing tab height===============================


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
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();

		//finish();
		//Toast.makeText( getApplicationContext(),"Back pressed",Toast.LENGTH_SHORT).show();
		
		//==================================================================================================================
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this); 
		  
        alertDialog.setTitle("Confirm Exit ..."); 
        alertDialog.setMessage("Are you sure to exit ?"); 
        alertDialog.setIcon(R.drawable.tick); 
  
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog,int which) {
        		finish();
        		Intent intent = new Intent(Intent.ACTION_MAIN);
        		intent.addCategory(Intent.CATEGORY_HOME);
        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(intent);
            } 
        }); 
  
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int which) { 
            dialog.cancel(); 
            } 
        }); 
  
        alertDialog.show();
		//==================================================================================================================
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