package com.example.demo;

import com.example.tabActivity.Tab2;
import com.example.tabActivity.Tab3;
import com.example.tabActivity.Tab4;
import com.example.tabActivity.TourMainActivity;
import com.example.utils.DatabaseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Tab_PaymetCustomerToCatchExpense extends TabActivity {
	TabHost tabHost; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_detail_level_tab_view);
		Resources ressources = getResources(); 
		tabHost = getTabHost(); 

		//==================================================================================================================================================
		// Expense tab 
		//==================================================================================================================================================		
		Intent intentExpense = new Intent().setClass(this, Expense_Tab.class);
		intentExpense.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec tabExpense = tabHost
				.newTabSpec("Expense")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_expense))
				.setContent(intentExpense);

		//==================================================================================================================================================
		// Date tab
		//==================================================================================================================================================		
		Intent dateIntent = new Intent(this,CustomerToVisit_Tab.class);
		dateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec dateTab = tabHost
				.newTabSpec("CustomerToVisit")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_customer_to_visit))
				.setContent(dateIntent);
		//==================================================================================================================================================
		// Tab3 tab
		//==================================================================================================================================================		
		Intent intentTab3 = new Intent().setClass(this, CustomerToCatch_Tab.class);
		intentTab3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabSpec Tab3 = tabHost
				.newTabSpec("CustomerToCatch")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_customer_to_catch))
				.setContent(intentTab3);



		// add all tabs 
		tabHost.addTab(tabExpense);
		tabHost.addTab(dateTab);
		tabHost.addTab(Tab3);


		tabHost.setCurrentTab(1);

	     //Changing tab height===============================
	       tabHost.getTabWidget().getChildAt(0).getLayoutParams().height =80;
	       tabHost.getTabWidget().getChildAt(1).getLayoutParams().height =80;
	       tabHost.getTabWidget().getChildAt(2).getLayoutParams().height =80;
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
