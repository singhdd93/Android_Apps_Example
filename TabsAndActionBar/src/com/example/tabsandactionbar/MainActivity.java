package com.example.tabsandactionbar;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {
	
	TabHost tabhost1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tabhost1 = (TabHost)findViewById(R.id.tabhost);
		tabhost1.setup();
		
		TabSpec tab1 = tabhost1.newTabSpec("Tab1");
		tab1.setIndicator("Tab 1");
		tab1.setContent(R.id.tab1);
		
		TabSpec tab2 = tabhost1.newTabSpec("Tab2");
		tab2.setIndicator("Tab 2");
		tab2.setContent(R.id.tab2);
		
		TabSpec tab3 = tabhost1.newTabSpec("Tab3");
		tab3.setIndicator("Tab 3");
		tab3.setContent(R.id.tab3);
		
		tabhost1.addTab(tab1);
		tabhost1.addTab(tab2);
		tabhost1.addTab(tab3);
		
		


	}
}
