package com.example.navigationdrawer;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	DrawerLayout dl;
	ListView lv_menu;
	
	String abc[] = {
		"one",
		"two",
		"three"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dl = (DrawerLayout)findViewById(R.id.drawer1);
		lv_menu = (ListView)findViewById(R.id.list);
		
		lv_menu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, abc));
		lv_menu.setOnItemClickListener(new dcl());
		
	}

	private class dcl implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           Toast.makeText(getBaseContext(), abc[position], Toast.LENGTH_SHORT).show();
        }
    }
 
}


