package com.dds.tabhosttut;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
	
	ActionBar ab;
	String[] list={"View 1", "View 2", "View 3"};
	
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
					
		setContentView(R.layout.activity_main);
		ab=getActionBar();
		Resources r =getResources();
		Drawable d = r.getDrawable(R.drawable.action_bar);
		ab.setBackgroundDrawable(d);
		ab.setTitle("Tabs & ActionBar");
		ab.setSubtitle("Just Testing");
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		
		ab.setListNavigationCallbacks(ad, new ActionBar.OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub
				FragmentManager fm =getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				if (itemPosition == 0)
				{
					Fragment1 f1 = new Fragment1();
					ft.replace(android.R.id.content, f1);
				}
				if(itemPosition == 1)
				{
					Fragment2 f2 =new Fragment2();
					ft.replace(android.R.id.content, f2);
				}
				if(itemPosition == 2)
				{
					Fragment3 f3 =new Fragment3();
					ft.replace(android.R.id.content, f3);
				}
				ft.commit();
				return true;
			}
		});
		
		
		
		
		}



}
