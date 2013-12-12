package com.dds.actionbartut;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	ArrayList<CharSequence> al = new ArrayList<CharSequence>();

	ActionBar actionBar;
	Button showTitle, hideTitle, showLogo, hideLogo, customTitle1, customBG;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		al.add("Item 1");
		al.add("Item 2");
		
		actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		//actionBar.setDisplayShowTitleEnabled(false);
		
		/**
		Tab tab1 = actionBar.newTab();
		Tab tab2 = actionBar.newTab();
		
		tab1.setTag("Tab 1").setIcon(R.drawable.ic_launcher).setContentDescription("This is Tab 1");
		actionBar.addTab(tab1);
		tab2.setTag("Tab 2").setIcon(R.drawable.ic_launcher).setContentDescription("This is Tab 2");
		actionBar.addTab(tab2);
		**/
		

		
		
		ArrayAdapter dropDownAdapter =
				ArrayAdapter.createFromResource(this,
				R.array.my_dropdown_values,
				android.R.layout.simple_list_item_1);
				// Assign the callbacks to handle drop-down selections.
		actionBar.setListNavigationCallbacks(dropDownAdapter, new ActionBar.OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int arg0, long arg1) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		showTitle = (Button)findViewById(R.id.button_show_title);
		hideTitle = (Button)findViewById(R.id.button_hide_title);
		showLogo = (Button)findViewById(R.id.button_show_logo);
		hideLogo = (Button)findViewById(R.id.button_hide_logo);
		customTitle1 = (Button)findViewById(R.id.button_show_customTitle);
		customBG = (Button)findViewById(R.id.button_show_customBG);
		
		showTitle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionBar.setDisplayShowTitleEnabled(true);
			}
		});
		
		hideTitle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				actionBar.setDisplayShowTitleEnabled(false);
				
			}
		});
		
		showLogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionBar.setDisplayShowHomeEnabled(true);
			}
		});
		
		hideLogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionBar.setDisplayShowHomeEnabled(false);
			}
		});
		
		customTitle1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionBar.setTitle("Action Bar");
				actionBar.setSubtitle("Testing Action Bar Modifications");
			}
		});
		
		customBG.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Resources r =getResources();
				Drawable d = r.getDrawable(R.drawable.action_bar_bg);
				actionBar.setBackgroundDrawable(d);
			}
		});
		
		
	}

}
