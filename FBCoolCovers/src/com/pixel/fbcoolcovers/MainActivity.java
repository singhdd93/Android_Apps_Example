package com.pixel.fbcoolcovers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static String url = "http://www.fbcoolcovers.com/fetch.php";

	Button toggleDrawer;
	private static final String TAG = "fbcovers";
	private static final String TAG_SLUG = "slug";
	private static final String TAG_CATEGORYNAME = "name";
	boolean firstRun = true;
	ConnectionDetector cd;
	boolean isInternetPresent;
	private ProgressDialog pDialog;
	public String[] catName;
	public String[] slug;
	JSONParser jParser = new JSONParser();

	ListView lv_menu;
	JSONArray categories = null;
	DrawerLayout dl;
	WebView wb1;
	ProgressBar pBar;
	//public String TAG = "FB COOL COVERS";
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			if(wb1.canGoBack())
			{
				
				Log.d(TAG, "In onKeyDown");
			wb1.goBack();
			return super.onKeyDown(keyCode, event);
			}
			else
			{
				return super.onKeyDown(keyCode, event);
			}
			
			
			default:
				return super.onKeyDown(keyCode, event);
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	if(firstRun)
	{
		Log.d(TAG, "In onBackPressed");
		Toast.makeText(getBaseContext(), "Long Press to exit.", Toast.LENGTH_LONG).show();
		firstRun = false;
	}
	else
	{
		//super.onBackPressed();
	}
		
	}
	
	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			Log.d(TAG, "In onKeyLongPress");
				finish();
				return true;
			
			default:
				return super.onKeyLongPress(keyCode, event);
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if(isInternetPresent)
		{
		new LoadAll().execute();
		}
		else
		{
			AlertDialog.Builder waring = new AlertDialog.Builder(this);
				waring.setTitle("No Internet Connection")
				.setMessage("Please Connect to Internet...")
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
			
			
		}
		pBar = (ProgressBar)findViewById(R.id.progress_bar_old_api);
		wb1 =(WebView)findViewById(R.id.web_View);
		dl = (DrawerLayout)findViewById(R.id.drawer1);
		lv_menu = (ListView)findViewById(R.id.list);
		toggleDrawer = (Button)findViewById(R.id.toggle_navigation_drawer_old_api);
		lv_menu.setOnItemClickListener(new dcl());
		toggleDrawer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
					dl.openDrawer(lv_menu);
				
			}
		});
		
		
		
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create(); 
		//progressBar = ProgressDialog.show(MainActivity.this, "FB Cool Covers", "Loading...");
		WebSettings ws = wb1.getSettings();
		ws.setAllowContentAccess(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		
		ws.setJavaScriptEnabled(true);
		ws.setSupportMultipleWindows(true);
		ws.setDomStorageEnabled(true);
		wb1.setWebViewClient(new WebViewClient() 
		{ public boolean shouldOverrideUrlLoading(WebView view, String url) 
		{ Log.i(TAG, "Processing webview url click...");
		pBar.setVisibility(View.VISIBLE);
		view.loadUrl(url); 
		return true; 
		}  
		
		
	    
		public void onPageFinished(WebView view, String url) 
		{ Log.i(TAG, "Finished loading URL: " +url);
		pBar.setVisibility(View.GONE);
		//if (progressBar.isShowing()) 
		//{ progressBar.dismiss(); 
		//} 
		}  

		@SuppressWarnings("deprecation")
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) 
		{ Log.e(TAG, "Error: " + description); 
		Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show(); 
		alertDialog.setTitle("Error"); 
		alertDialog.setMessage(description); 
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{ public void onClick(DialogInterface dialog, int which) 
		{ return; 
		} 
		}); 
		alertDialog.show(); 
		} 
		});
		if(isInternetPresent)
		{
		wb1.loadUrl("http://m.fbcoolcovers.com/");
		}
		else
		{
			wb1.loadUrl("file:///android_asset/internet.html");
		}
		
	}
	
	
	
	
	
	
	
	
	class LoadAll extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading Categories.\nPlease wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			//Log.d("All Messages: ", json.toString());

			try {

					
					categories = json.getJSONArray(TAG);
					catName = new String[categories.length()];
					slug = new String[categories.length()];


					for (int i = 0; i < categories.length(); i++) {
						JSONObject get = categories.getJSONObject(i);

						
						catName[i] = get.getString(TAG_CATEGORYNAME);
						slug[i] = get.getString(TAG_SLUG);	
						
						
					
				} 
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
					
					try{
						Thread.sleep(2000);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					//printList();
					//printDrawer();
					Log.d(TAG, "Loaded Categories");
					lv_menu.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.drawer_list_item, catName));
					
				}
			});


		}

	}
	
	
	
	private class dcl implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           String abc = slug[position];
           Log.d(TAG, abc);
           pBar.setVisibility(View.GONE);
           pBar.setVisibility(View.VISIBLE);
           wb1.loadUrl("http://m.fbcoolcovers.com/?cat="+abc);
           dl.closeDrawer(lv_menu);
           
        }
    }

}
