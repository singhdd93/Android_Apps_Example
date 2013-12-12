package com.example.iudsqllite;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class showUserInfoListActivity extends ListActivity {

	ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
	static final String KEY_ID 		   = "id";
	static final String KEY_FIRST_NAME = "first_name";
	static final String KEY_LAST_NAME  = "last_name";
	static final String KEY_ADDRESS    = "address";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_info_list);

		//---get all Records---
		DataBaseAdapter db = new DataBaseAdapter(this);
		db.open();
		Cursor c = db.getAllRecords();
		if (c.moveToFirst())
		{
			do 
			{          
				HashMap<String, String> map = new HashMap<String, String>();
				// adding each child node to HashMap key => value
				map.put(KEY_ID, c.getString(0));
				map.put(KEY_FIRST_NAME, c.getString(1));
				map.put(KEY_LAST_NAME, " "+c.getString(2));
				map.put(KEY_ADDRESS, c.getString(3));

				// adding HashList to ArrayList
				menuItems.add(map);
			} while (c.moveToNext());
		}
		db.close();
		
		// Adding menuItems to ListView
		// All filed data are not shown in the list KEY_ID is hidden
		ListAdapter adapter = new SimpleAdapter(this, menuItems,R.layout.user_info_list_item,
							  new String[] { KEY_FIRST_NAME, KEY_LAST_NAME, KEY_ADDRESS, KEY_ID }, 
							  new int[] {R.id.first_name , R.id.last_name, R.id.address});
		setListAdapter(adapter);
	}


	//On select from the list show data
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		HashMap<String, String> return_data = (HashMap<String, String>) o;
		Toast.makeText(this,  ""+return_data.get("first_name"), Toast.LENGTH_LONG).show();
	}
}
