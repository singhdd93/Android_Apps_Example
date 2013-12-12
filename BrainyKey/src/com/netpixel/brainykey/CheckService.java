package com.netpixel.brainykey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

@SuppressLint("NewApi")
public class CheckService extends Service {
	


	private static String url = "http://fbcoolcovers.com/fetch2.php";

	ConnectionDetector cd;
	boolean isInternetPresent;
	JSONParser jParser = new JSONParser();
	private static final String TAG = "fbstatus";
	private static final String TAG_PID = "id";
	static final String ID = "id";
	static final String ID_PREF = "ids";
	public int check_id = 0;
	private NotificationManager notificationManager;
	private Notification myNotification;
	private static final int MY_NOTIFICATION_ID=1;
	JSONArray messages = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("BrainyKeyService", "Service Started");
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

		// This schedule a runnable task every 15 minutes
		scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
		  public void run() {
			  Log.d("BrainyKeyService", "Running Service repeat part");
			  if(isInternetPresent)
				{
					new LoadAll().execute();
				}
				
				SharedPreferences sp = getSharedPreferences(ID_PREF, Activity.MODE_PRIVATE);
				 int myIntValue = sp.getInt(ID, -1);
				 if(check_id > myIntValue)
				 {
					 Log.d("BrainyKeyService", "No notification");
					 notificationManager =
							 (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
							myNotification = new Notification(R.drawable.ic_launcher,
							  "Notification!",
							  System.currentTimeMillis());
							Context context = getApplicationContext();
							String notificationTitle = "Brainy Key";
							String notificationText = "New Info Added";
							Intent myIntent = new Intent(context, MainActivity.class);
							PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, myIntent,  Intent.FLAG_ACTIVITY_NEW_TASK);
							myNotification.defaults |= Notification.DEFAULT_SOUND;
							myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
							myNotification.setLatestEventInfo(context,
							   notificationTitle,
							   notificationText,
							   pendingIntent);
							notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
					 
					 SharedPreferences sp1 = getSharedPreferences(ID_PREF, Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sp1.edit();
						editor.putInt(ID, check_id);
						editor.commit();
					 
				 }
			  
		  }
		}, 0, 7, TimeUnit.MINUTES);
		
		
		return Service.START_STICKY;
	}
	

	class LoadAll extends AsyncTask<String, String, String> {
		int last_id;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			Log.d("All Messages: ", json.toString());

			try {

					
					messages = json.getJSONArray(TAG);


					
					for (int i = 0; i < messages.length(); i++) {
						JSONObject get = messages.getJSONObject(i);

						String id_str = get.getString(TAG_PID);
						
						last_id=Integer.parseInt(id_str);
						
						
									
									
									
							} 
					
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		protected void onPostExecute(String file_url) {

			check_id = last_id;
			
			//SharedPreferences sp = getSharedPreferences(ID_PREF, Activity.MODE_PRIVATE);
			//SharedPreferences.Editor editor = sp.edit();
			//editor.putInt(ID, last_id);
			//editor.commit();
			


		}

	}
	
	
	
	
	
	
	public class JSONParser {

		InputStream is = null;
		JSONObject jObj = null;
		String json = "";

		public JSONParser() {

		}

		public JSONObject makeHttpRequest(String url, String method,
				List<NameValuePair> params) {

			try {
				
				if(method == "POST"){

					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(params));

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
					
				}else if(method == "GET"){
					
					DefaultHttpClient httpClient = new DefaultHttpClient();
					String paramString = URLEncodedUtils.format(params, "UTF-8");
					url += "?" + paramString;
					HttpGet httpGet = new HttpGet(url);

					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				}			
				

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				json = sb.toString();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}

			
			try {
				jObj = new JSONObject(json);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			
			return jObj;

		}
	}






	
}


