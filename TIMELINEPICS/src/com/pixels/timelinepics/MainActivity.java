package com.pixels.timelinepics;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pixels.timelinepics.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static String url = "http://fbtimelinepics.com/fetch2.php";

	private ProgressDialog pDialog;
	ConnectionDetector cd;
	boolean isInternetPresent;
	DBA db;
	Cursor c;
	JSONParser jParser = new JSONParser();
	private static final String TAG = "quotespics";
	private static final String TAG_PID = "id";
	private static final String TAG_TITLE = "posttitle";
	private static final String TAG_URL = "imgurl";
	private static final String TAG_CATEGORY = "cid";
	
	JSONArray messages = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		db = new DBA(getApplicationContext());
		db.open();
		c= db.getAll();
		int count = c.getCount();
		db.close();
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if(count < 2)
		{
		if(isInternetPresent == true)
		{
		new LoadAll().execute();
		}
		else
		{
			AlertDialog.Builder warningIC = new AlertDialog.Builder(this);
			warningIC.setTitle("No Internet Connection");
			//warningIC.setIcon(R.drawable.warning);
			warningIC.setMessage("Please Connect to Internet for Sycing Messages.");
			warningIC.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			warningIC.show();
			
		}
        
	}
		else
		{
			finish();
			Intent i = new Intent(MainActivity.this , ImageGridActivity.class);
			startActivity(i);
			
		}
	}

	
	
	class LoadAll extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Intial Covers Sync\nMay take few minutes.\nPlease wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			Log.d("All Messages: ", json.toString());

			try {

					
					messages = json.getJSONArray(TAG);


					db.open();
					for (int i = 0; i < messages.length(); i++) {
						JSONObject get = messages.getJSONObject(i);

						String id_str = get.getString(TAG_PID);
						String title = get.getString(TAG_TITLE);
						String url = get.getString(TAG_URL);
						String cat = get.getString(TAG_CATEGORY);
						//String curl = "http://www.fbcoolcovers.com/wp-content/uploads/"+url;
						long id = Long.parseLong(id_str);
						
									
									db.insert(id,title,url,cat);
									
							} 
					db.close();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		protected void onPostExecute(String file_url) {

			pDialog.dismiss();
			
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					finish();
					Intent i = new Intent(MainActivity.this , ImageGridActivity.class);
					startActivity(i);
					
					
				}
			});


		}

	}
}


