package com.bumba27.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class SendData extends Service {

	double lat;
	double lng;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		LocationManager mlocManager   = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
		        10000,          
		        10,            
		        mlocListener);
		

		if(haveNetworkConnection())
		{
			WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			List<ScanResult> results = wifiManager.getScanResults();

			if (results != null) 
			{
				final int size = results.size();
				if (size == 0) 	
				{
					Log.d("LOG", "No access points in range");
				}
				else 
				{
					for (ScanResult result : results) 
					{
						TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String imei = tm.getDeviceId();
//						Toast.makeText(this, "Wifi Mac: " + result.BSSID + 
//											 "\nWifi Signal Strength: " + result.level + 
//											 "\nIMEI No (Phone Mac): " + imei + 
//											 "\nLatitude: " + lat + 
//											 "\nLongitude: " + lng,Toast.LENGTH_LONG).show();
						
						new MyAsyncTaskToSendData().execute(imei+"",result.BSSID+"",result.level+"",lat+"",lng+"");
					}
				}
			}
			else {
				Log.d("LOG", "Make sure your wifi is on.\nIt required for searchig other wifi.");
			}
		}
		else
		{
			Log.d("LOG", "Sorry! No data connection found.\nTurn it on and try again.");
		}
	
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean haveNetworkConnection() 
	{
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) 
		{
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
	
	
	public class MyLocationListener implements LocationListener	
	{
		@Override
		public void onLocationChanged(Location loc)
		{
			lat = loc.getLatitude();
			lng = loc.getLongitude();
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			Log.d("LOG", "GPS is OFF!");
		}
		@Override
		public void onProviderEnabled(String provider)
		{
			Log.d("LOG", "Thanks for enabling GPS !");
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	}
	
	//===================================================================================================================================
	//sending Data to server
	//===================================================================================================================================
	private class MyAsyncTaskToSendData extends AsyncTask<String, Integer, Double>{

		String responseBody = null;
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0],params[1],params[2],params[3],params[4]);
			return null;
		}

		protected void onPostExecute(Double result){

			if(responseBody!=null)
			{
				Log.d("result", responseBody);
				if(responseBody.equalsIgnoreCase("TRUE"))
				{
					Log.d("LOG", "Data saved to server.");
				}
				else
				{
					Log.d("LOG", "SERVER ERROR: Data not being saved.");
				}
			}
			else
			{
				Log.d("LOG", "Empty Responce from server.");
			}

		}
		protected void onProgressUpdate(Integer... progress){
		}

		public void postData(String phoneIMEI,String wifiMAC,String signals,String lat,String lon) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://stud.if.ktu.lt/~paugrin/save_data.php");

			try {
				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("phoneIMEI", phoneIMEI));
				nameValuePairs.add(new BasicNameValuePair("wifiMAC", wifiMAC));
				nameValuePairs.add(new BasicNameValuePair("signals", signals));
				nameValuePairs.add(new BasicNameValuePair("lat", lat));
				nameValuePairs.add(new BasicNameValuePair("lon", lon));
				
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				responseBody = EntityUtils.toString(response.getEntity());
			} 
			catch (Throwable t ) 
			{
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END sending Data to server
	//===================================================================================================================================


}
