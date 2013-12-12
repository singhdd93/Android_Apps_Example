package com.dds.smspassing;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.dds.smspassing.Row;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity{
	String smsTypes [];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		smsTypes=getResources().getStringArray(R.array.sms_types);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		//ListView smsTypeList = getListView();
		//smsTypeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		//smsTypeList.setTextFilterEnabled(true);
		//ArrayAdapter<String> d=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsTypes);
		setListAdapter(new Row(this, smsTypes));
		
	}
	
	@Override
	protected void onListItemClick(ListView smsTypeList, View v, int position, long id) {
		// TODO Auto-generated method stub
		String tempVar=smsTypes[position];
		
		if(tempVar.equals("Funny"))
		{
			Intent i =new Intent (MainActivity.this, FunnySMS.class);
			startActivity(i);
		}
		else if(tempVar.equals("Jokes"))
		{
			Intent i =new Intent (MainActivity.this, JokesSMS.class);
			startActivity(i);
		}
		else if(tempVar.equals("Flirt"))
		{
			Intent i =new Intent (MainActivity.this, FlirtSMS.class);
			startActivity(i);
		}
		
	}
}
