package com.example.androidhive;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	// All xml labels
	TextView txtName;
	TextView txtEmail;
	TextView txtMobile;
	TextView txtAddress;
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	
	// Profile json object
	JSONObject profile;
	
	// Profile JSON url
	private static final String PROFILE_URL = "http://api.androidhive.info/mail/profile.json";
	
	// ALL JSON node names
	private static final String TAG_PROFILE = "profile";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_MOBILE = "mobile";
	private static final String TAG_ADDRESS = "address";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		txtName = (TextView) findViewById(R.id.name);
		txtEmail = (TextView) findViewById(R.id.email);
		txtMobile = (TextView) findViewById(R.id.mobile);
		txtAddress = (TextView) findViewById(R.id.address);
		
        // Loading Profile in Background Thread
        new LoadProfile().execute();
	}

	/**
	 * Background Async Task to Load profile by making HTTP Request
	 * */
	class LoadProfile extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ProfileActivity.this);
			pDialog.setMessage("Loading profile ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Profile JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// getting JSON string from URL
			JSONObject json = jsonParser.makeHttpRequest(PROFILE_URL, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Profile JSON: ", json.toString());

			try {
				// profile json object
				profile = json.getJSONObject(TAG_PROFILE);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					// Storing each json item in variable
					try {
						String id = profile.getString(TAG_ID);
						String name = profile.getString(TAG_NAME);
						String email = profile.getString(TAG_EMAIL);
						String mobile = profile.getString(TAG_MOBILE);
						String address = profile.getString(TAG_ADDRESS);
						
						// displaying all data in textview
						txtName.setText(name);
						txtEmail.setText("Email: " + email);
						txtMobile.setText("Mobile: " + mobile);
						txtAddress.setText("Add: " + address);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});

		}

	}
}
