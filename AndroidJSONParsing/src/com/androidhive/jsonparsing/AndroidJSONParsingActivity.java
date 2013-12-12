package com.androidhive.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidJSONParsingActivity extends ListActivity {

	// url to make request
	private static String url = "http://www.facebookstatus4u.com/fetch.php";
	
	// JSON Node names
	private static final String TAG_CONTACTS = "fbstatus";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "message";
	//private static final String TAG_EMAIL = "email";
	//private static final String TAG_ADDRESS = "address";
	//private static final String TAG_GENDER = "gender";
	//private static final String TAG_PHONE = "phone";
	//private static final String TAG_PHONE_MOBILE = "mobile";
	//private static final String TAG_PHONE_HOME = "home";
	//private static final String TAG_PHONE_OFFICE = "office";

	// contacts JSONArray
	JSONArray contacts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			// Getting Array of Contacts
			contacts = json.getJSONArray(TAG_CONTACTS);
			
			// looping through All Contacts
			for(int i = 0; i < contacts.length(); i++){
				JSONObject c = contacts.getJSONObject(i);
				
				// Storing each json item in variable
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAME);
				//String email = c.getString(TAG_EMAIL);
				//String address = c.getString(TAG_ADDRESS);
				//String gender = c.getString(TAG_GENDER);
				
				// Phone number is agin JSON Object
				//JSONObject phone = c.getJSONObject(TAG_PHONE);
				//String mobile = phone.getString(TAG_PHONE_MOBILE);
				//String home = phone.getString(TAG_PHONE_HOME);
				//String office = phone.getString(TAG_PHONE_OFFICE);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_NAME, name);
				//map.put(TAG_EMAIL, email);
				//map.put(TAG_PHONE_MOBILE, mobile);

				// adding HashList to ArrayList
				contactList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item,
				new String[] { TAG_NAME,}, new int[] {
						R.id.name });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				//String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
				//String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_NAME, name);
				//in.putExtra(TAG_EMAIL, cost);
				//.putExtra(TAG_PHONE_MOBILE, description);
				startActivity(in);

			}
		});
		
		

	}

}