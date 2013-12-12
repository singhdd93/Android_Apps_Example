package com.dd.listviews;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	String[] abc = {
		"Aman",
		"Bhavneet",
		"Daman",
		"Harpreet",
		"Ishmeet",
		"Jasbir"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ListView one = getListView();
		one.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		one.setTextFilterEnabled(true);
		ArrayAdapter<String> d=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, abc);
		setListAdapter(d);
	}

	@Override
	protected void onListItemClick(ListView one, View v, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Selected :  "+abc[position], Toast.LENGTH_SHORT).show();
	}


}
