package com.example.attendance;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ica.database.DataBaseAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AttendanceLoginMainActivity extends Activity {

	private ProgressDialog pgLogin;
	private static String loginStatus = null;
	String serverPort =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_main);
	}

	public void signin(View V) {
		EditText user_id = (EditText)findViewById(R.id.txtEntryUserName);
		EditText password = (EditText)findViewById(R.id.txtEntryPassword);
		
		DataBaseAdapter db = new DataBaseAdapter(this);
		db.open();
        Cursor c = db.getAllRecords();
        // If data exist into database
        if (c.moveToFirst())
        {
			do 
            {          
            	serverPort = c.getString(1) + ":" + c.getString(2);

            } while (c.moveToNext());

        }
		
		if(haveNetworkConnection())
		{
			if(user_id.getText().toString().length()<1 || password.getText().toString().length()<1)
			{
				Toast.makeText(this, "Please provide all information.", Toast.LENGTH_LONG).show();
			}
			else 
			{
				if(serverPort != null)
				{
				pgLogin = new ProgressDialog(AttendanceLoginMainActivity.this);
				pgLogin.setMessage("Please wait while progress login...");
				pgLogin.setIndeterminate(true);
				pgLogin.setCancelable(true);
				pgLogin.setCanceledOnTouchOutside(false);

				pgLogin.show();
				
				new MyAsyncTask().execute(user_id.getText().toString(),password.getText().toString());	
				}
				else
				{
					Toast.makeText(this, "You need to setup configuration.", Toast.LENGTH_LONG).show();
				}
			}
		}
		else
		{
			Toast.makeText(this, "Sorry! No internet connection.", Toast.LENGTH_LONG).show();
		}
	}
	
	public void cancel(View v) {
		finish();
	}
	
	public void settings(View v) {
		Intent showList = new Intent(this, settings.class);
		startActivity(showList);
	}
		//===================================================================================================================================
		//sending EmailAddress and Password to server
		//===================================================================================================================================
		private class MyAsyncTask extends AsyncTask<String, Integer, Double>{

			String responseBody;
			int responseCode;
			@Override
			protected Double doInBackground(String... params) {
				// TODO Auto-generated method stub
				postData(params[0],params[1]);
				return null;
			}

			protected void onPostExecute(Double result){
				//Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
				
				if(responseCode == 200)
				{
					processResponce(responseBody);
				}
				
				else
				{
					if (pgLogin.isShowing()) 
					{
						pgLogin.cancel();
						pgLogin.dismiss();
					}
					Toast.makeText(AttendanceLoginMainActivity.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
				}
			}

			protected void onProgressUpdate(Integer... progress){

			}

			public void postData(String mbCode,String passwrd) {
				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://" + serverPort + "/ServiceAttendance.asmx/login");

				try {
					// Data that I am sending
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("mb_code", mbCode));
					nameValuePairs.add(new BasicNameValuePair("Pwd", passwrd));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					// Execute HTTP Post Request
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					responseBody = EntityUtils.toString(response.getEntity());
					responseCode = response.getStatusLine().getStatusCode();


					Log.d("result", responseBody);
				} 
				catch (Throwable t ) {
					//Toast.makeText( getApplicationContext(),""+t,Toast.LENGTH_LONG).show();
					Log.d("Error Time of Login",t+"");
				} 
			}
		}
		//===================================================================================================================================
		//END sending EmailAddress and Password to server 
		//===================================================================================================================================
		
		
		//===================================================================================================================================
		//processing the XML got from server
		//===================================================================================================================================

		private void processResponce(String responceFromServer) 
		{
			try {
				//saving the file as a xml
				FileOutputStream fOut = openFileOutput("loginData.xml",MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fOut);
				osw.write(responceFromServer);
				osw.flush();
				osw.close();

				//reading the file as xml
				FileInputStream fIn = openFileInput("loginData.xml");
				InputStreamReader isr = new InputStreamReader(fIn);
				char[] inputBuffer = new char[responceFromServer.length()];
				isr.read(inputBuffer);
				String readString = new String(inputBuffer);

				//getting the xml Value as per child node form the saved xml
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputStream is = new ByteArrayInputStream(readString.getBytes("UTF-8"));
				Document doc = db.parse(is);

				NodeList root=doc.getElementsByTagName("root");

				for (int i=0;i<root.getLength();i++) 
				{
					loginStatus = "" + ((Element)root.item(i)).getAttribute("status");
					//Toast.makeText( getApplicationContext(),loginStatus,Toast.LENGTH_SHORT).show();
				}

				//If Email and Pass match with server
				if(loginStatus.equalsIgnoreCase("Y"))
				{
					NodeList mb=doc.getElementsByTagName("mb");
					
					String mb_code = null;
					String pwd = null;
					for (int i=0;i<mb.getLength();i++) 
					{
						mb_code  = "" + ((Element)mb.item(i)).getAttribute("mb_code");
			    		pwd       = "" + ((Element)mb.item(i)).getAttribute("pwd");
					}
					//Toast.makeText( getApplicationContext(),"Login Successful.",Toast.LENGTH_SHORT).show();
					Intent i = new Intent(this, AttendanceActivity.class);
					i.putExtra("code", mb_code);
					i.putExtra("pwd", pwd);
					startActivity(i);
				}
				else if(loginStatus.equalsIgnoreCase("F"))
				{
					Toast.makeText( getApplicationContext(),"Your credentials could not be authenticated.\nPlease try again.",Toast.LENGTH_SHORT).show();
				}
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}

			} 
			catch (Throwable t) 
			{
				Log.d("Error On Saving and reading", t+"");
			}
		}
		//===================================================================================================================================
		//processing the XML got from server
		//===================================================================================================================================
		
		
		//===================================================================================================================================
		//check packet data and wifi
		//===================================================================================================================================
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
		//====================================================================================================================================
		//checking packet data and wifi END
		//====================================================================================================================================
}
