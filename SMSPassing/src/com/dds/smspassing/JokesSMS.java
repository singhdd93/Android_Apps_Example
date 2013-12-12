package com.dds.smspassing;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JokesSMS extends ListActivity{
	
	String smsList [];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		smsList=getResources().getStringArray(R.array.sms_list2);
		
		ListView smsListView = getListView();
		smsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		smsListView.setTextFilterEnabled(true);
		ArrayAdapter<String> d=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsList);
		setListAdapter(d);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		String smsListType = "smsList2";
		
		Intent i = new Intent(JokesSMS.this, SendSMS.class);
		i.putExtra("postion", position);
		i.putExtra("listType", smsListType);
		startActivity(i);
	
	}

}
