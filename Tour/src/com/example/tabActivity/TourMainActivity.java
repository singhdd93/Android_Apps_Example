package com.example.tabActivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.demo.LoginMainActivity;
import com.example.demo.Tab_MainViewActivity;
import com.example.demo.R;
import com.example.demo.TourEntryActivity;
import com.example.utils.DatabaseHandler;

public class TourMainActivity extends ListActivity {

	ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
	static final String KEY_ID 		   = "id";
	static final String KEY_TOUR_NAME  = "first_name";
	static final String KEY_NAME       = "last_name";
	static final String KEY_DATE       = "address";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_main);

		//----------------------------------------------------------------------------------------------
		//get all Records from tour_list_table
		//----------------------------------------------------------------------------------------------

		DatabaseHandler db = new DatabaseHandler(this);
		db.open();
		Cursor c = db.getAllRecordsFromTourList();
		if (c.moveToFirst())
		{
			do 
			{         
				HashMap<String, String> map = new HashMap<String, String>();
				// adding each child node to HashMap key => value
				map.put(KEY_ID, c.getString(1));
				map.put(KEY_TOUR_NAME, c.getString(2));
				map.put(KEY_NAME, c.getString(4));

				String[] tempDateArray = c.getString(6).toString().split(" ");
				map.put(KEY_DATE, tempDateArray[0]);

				// adding HashList to ArrayList
				menuItems.add(map);
			} while (c.moveToNext());
		}
		db.close();

		//----------------------------------------------------------------------------------------------
		//get all Records from tour_list_table
		//----------------------------------------------------------------------------------------------


		// Adding menuItems to ListView
		// All filed data are not shown in the list KEY_ID is hidden
		ListAdapter adapter = new SimpleAdapter(this, menuItems,R.layout.list_layout,
				new String[] { KEY_TOUR_NAME, KEY_NAME, KEY_DATE, KEY_ID }, 
				new int[] {R.id.tour_name , R.id.name, R.id.date});
		setListAdapter(adapter);
	}
	@Override
	  public void onBackPressed() {
	    this.getParent().onBackPressed();   
	  }


	//On select from the list show data
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		HashMap<String, String> return_data = (HashMap<String, String>) o;
			
		saveInPreference("list_id", ""+return_data.get("id"));
		
		Intent showList = new Intent(this, TourEntryActivity.class);
		startActivity(showList);
	}
	
	//===================================================================================================================================
	//Preference variable
	//===================================================================================================================================

	//--------------------------------------------
	// method to save variable in preference
	//--------------------------------------------
	public void saveInPreference(String name, String content) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name, content);
		editor.commit();
	}

	//--------------------------------------------
	// getting content from preferences
	//--------------------------------------------
	public String getFromPreference(String variable_name) {
		String preference_return;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		preference_return = preferences.getString(variable_name, "");

		return preference_return;
	}


	//===================================================================================================================================
	//Preference variable
	//===================================================================================================================================


}
