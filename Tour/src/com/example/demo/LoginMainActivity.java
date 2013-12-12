package com.example.demo;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utils.DatabaseHandler;
import com.example.utils.MyDataForSpinner;

public class LoginMainActivity extends Activity {

	EditText userName;
	EditText password;
	String userType;
	Context context;
	private ProgressDialog pgLogin;

	Spinner userTypeSpiner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_main);
		
		context = LoginMainActivity.this;
		spinner();
	}
	
	public void quit(View v) {
		finish();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	//===================================================================================================================================================
	//Spinner User Type
	//===================================================================================================================================================

	public void spinner() 
	{
		userTypeSpiner = (Spinner)findViewById(R.id.UserType);
		final MyDataForSpinner items[] = new MyDataForSpinner[5];
		items[0] = new MyDataForSpinner( "Tour", "Admin");
		items[1] = new MyDataForSpinner( "Director", "Director");
		items[2] = new MyDataForSpinner( "Supplier", "Supplier");
		items[3] = new MyDataForSpinner( "Customer", "Customer");
		items[4] = new MyDataForSpinner( "Marketboy", "Marketboy");

		ArrayAdapter<MyDataForSpinner> adapter = new ArrayAdapter<MyDataForSpinner>( this,android.R.layout.simple_spinner_item,items );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		userTypeSpiner.setAdapter(adapter);

		//on value select from that dropdown it will get that id of Corresponding value
		userTypeSpiner.setOnItemSelectedListener
		(
				new AdapterView.OnItemSelectedListener() 
				{
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
					{
						MyDataForSpinner d = items[position];
						userType = d.getValue() + "";
					}

					public void onNothingSelected(AdapterView<?> parent) 
					{
						Toast.makeText(LoginMainActivity.this,"No value selected in User type.",Toast.LENGTH_LONG).show();
					}
				}
		);
	}

	//===================================================================================================================================================
	//END Spinner User Type
	//===================================================================================================================================================



	//===================================================================================================================================================
	//Login function
	//===================================================================================================================================================
	public void login(View v) {
		userName = (EditText)findViewById(R.id.userNameEditText);
		password = (EditText)findViewById(R.id.passwordEditText);

		if(haveNetworkConnection())
		{
			if(userName.getText().toString().length()<1 || password.getText().toString().length()<1 || userType.toString().length()<1)
			{
				Toast.makeText(this, "Please provide all information.", Toast.LENGTH_LONG).show();
			}
			else 
			{
				pgLogin = new ProgressDialog(context);
				pgLogin.setMessage("Please wait while authenticating ...");
				pgLogin.setIndeterminate(true);
				pgLogin.setCancelable(true);
				pgLogin.setCanceledOnTouchOutside(false);

				pgLogin.show();
				
				new MyAsyncTaskForLogin().execute(userName.getText().toString(), password.getText().toString(), userType.toString());
			}
		}
		else
		{
			Toast.makeText(this, "Sorry no internet connection.", Toast.LENGTH_LONG).show();
		}
	}
	//===================================================================================================================================================
	//Login function
	//===================================================================================================================================================





	//===================================================================================================================================
	//Sending userName, Password, UserType to server and checking login successful or not
	//===================================================================================================================================
	private class MyAsyncTaskForLogin extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0],params[1],params[2]);
			return null;
		}

		protected void onPostExecute(Double result){

			if(responseCode == 200)
			{
				processLoginResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(context, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}

		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username,String passwrd, String userType) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/login");

			try {
				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("stringLoginUser", username));
				nameValuePairs.add(new BasicNameValuePair("stringLoginPwd", passwrd));
				nameValuePairs.add(new BasicNameValuePair("Type", userType));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());
			} 
			catch (Throwable t ) {
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END Sending userName, Password, UserType to server and checking login successful or not
	//===================================================================================================================================





	//===================================================================================================================================
	//processing the XML got from server for ENQUIRY LIST
	//===================================================================================================================================
	private void processLoginResponce(String responceFromServer) 
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
			String loginStatus = null;
			for (int i=0;i<root.getLength();i++) 
			{
				loginStatus 		= "" + ((Element)root.item(i)).getAttribute("status");
			}

			//If data matched with server
			if(loginStatus.equalsIgnoreCase("Y"))
			{
				new MyAsyncTaskForTourList().execute(userName.getText().toString(), password.getText().toString(),userType.toString());
			}
			//if not matched
			else if(loginStatus.equalsIgnoreCase("F"))
			{
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Login failed. Please check your credentials and try again.",Toast.LENGTH_LONG).show();
			}
		} 
		catch (Throwable t) 
		{
			Log.d("Error On Saving and reading", t+"");
		}
	}
	//===================================================================================================================================
	//processing the XML got from server for ENQUIRY LIST
	//===================================================================================================================================

	
	
	
	
	//===================================================================================================================================
	//sending userID,Password,userType to server for TOUR LIST
	//===================================================================================================================================
	private class MyAsyncTaskForTourList extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			postData(params[0],params[1],params[2]);
			return null;
		}

		protected void onPostExecute(Double result){
			if(responseCode == 200)
			{
				processTourListResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(context, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}
		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username,String pwd,String Type) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/TourList");

			try {
				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
				nameValuePairs.add(new BasicNameValuePair("Type", Type));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());

				Log.d("result", responseBody);
			} 
			catch (Throwable t ) {
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END sending userID,Password,userType to server for TOUR LIST
	//===================================================================================================================================




	//===================================================================================================================================
	//processing the XML got from server for TOUR LIST
	//===================================================================================================================================
	private void processTourListResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("tourListData.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("tourListData.xml");
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
			String loginStatus = null;

			for (int i=0;i<root.getLength();i++) 
			{
				loginStatus = "" + ((Element)root.item(i)).getAttribute("status");
			}

			//If Email and Pass match with server
			if(loginStatus.equalsIgnoreCase("Y"))
			{
				NodeList TourData =doc.getElementsByTagName("TourData");
				
				//==============================================================================
				//deleting local Tour_list_table
				//==============================================================================
				DatabaseHandler DbForTourListDelete = new DatabaseHandler(this);
				
				DbForTourListDelete.open();        
				boolean deletedId = DbForTourListDelete.deleteRecordFromTourList();        
				DbForTourListDelete.close();
				//==============================================================================
				//deleting to local database
				//==============================================================================

				for (int i=0;i<TourData.getLength();i++) 
				{
					String TourID  		 = "" + ((Element)TourData.item(i)).getAttribute("TourID");	
					String TourName 	 = "" + ((Element)TourData.item(i)).getAttribute("TourName");
					String TourBranchID  = "" + ((Element)TourData.item(i)).getAttribute("TourBranchID");	
					String TourMktBoy    = "" + ((Element)TourData.item(i)).getAttribute("TourMktBoy");
					String TourMktMob    = "" + ((Element)TourData.item(i)).getAttribute("TourMktMob");	
					String TourStartDt   = "" + ((Element)TourData.item(i)).getAttribute("TourStartDt");
					String Branch   	 = "" + ((Element)TourData.item(i)).getAttribute("Branch");
//					String Branch   	 = "Demo Branch";
					//==============================================================================
					//insert to local Tour_list_table
					//==============================================================================
					DatabaseHandler DbForTourList = new DatabaseHandler(this);
					
					DbForTourList.open();        
					long id = DbForTourList.insertRecordToTourList(TourID, TourName, TourBranchID, TourMktBoy, TourMktMob, TourStartDt, Branch);        
					DbForTourList.close();
					//==============================================================================
					//insert to local database
					//==============================================================================
				}
				new MyAsyncTaskForTourListDetail().execute(userName.getText().toString(), password.getText().toString(),userType.toString());
			}
			else if(loginStatus.equalsIgnoreCase("F"))
			{
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Internet connection problem.\nTry again after some time.",Toast.LENGTH_SHORT).show();
			}
		} 
		catch (Throwable t) 
		{
			Log.d("Error On Saving and reading", t+"");
		}
	}
	//===================================================================================================================================
	//processing the XML got from server for TOUR LIST
	//===================================================================================================================================

	
	
	
	
	//===================================================================================================================================
	//sending userID,Password,userType to server for TOUR LIST DETAIL
	//===================================================================================================================================
	private class MyAsyncTaskForTourListDetail extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			postData(params[0],params[1],params[2]);
			return null;
		}

		protected void onPostExecute(Double result){
			if(responseCode == 200)
			{
				processTourListDetailResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(context, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}
		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username,String pwd,String Type) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/TourList2");

			try {
				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
				nameValuePairs.add(new BasicNameValuePair("Type", Type));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());

				Log.d("result", responseBody);
			} 
			catch (Throwable t ) {
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END sending userID,Password,userType to server for TOUR LIST DETAIL
	//===================================================================================================================================

	
	
	
	
	//===================================================================================================================================
	//processing the XML got from server for TOUR LIST DETAIL
	//===================================================================================================================================
	private void processTourListDetailResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("tourListDetailData.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("tourListDetailData.xml");
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
			String loginStatus = null;

			for (int i=0;i<root.getLength();i++) 
			{
				loginStatus = "" + ((Element)root.item(i)).getAttribute("status");
			}

			//If Email and Pass match with server
			if(loginStatus.equalsIgnoreCase("Y"))
			{
				NodeList TourData2 =doc.getElementsByTagName("TourData2");

				//==============================================================================
				//deleting local Tour_list_table
				//==============================================================================
				DatabaseHandler DbForTourListDelete = new DatabaseHandler(this);
				
				DbForTourListDelete.open();        
				boolean deletedId = DbForTourListDelete.deleteRecordFromTourListDetail();        
				DbForTourListDelete.close();
				//==============================================================================
				//deleting to local database
				//==============================================================================

				for (int i=0;i<TourData2.getLength();i++) 
				{
					String TourID  		 = "" + ((Element)TourData2.item(i)).getAttribute("TourID");	
					String City 	 	 = "" + ((Element)TourData2.item(i)).getAttribute("City");
					String Dt  			 = "" + ((Element)TourData2.item(i)).getAttribute("Dt");	
					String Expense    	 = "" + ((Element)TourData2.item(i)).getAttribute("Expense");
					String TourExpenseID = "" + ((Element)TourData2.item(i)).getAttribute("TourExpenseID");	
					//==============================================================================
					//insert to local Tour_list_table
					//==============================================================================
					DatabaseHandler DbForTourList = new DatabaseHandler(this);
					
					DbForTourList.open();        
					long id = DbForTourList.insertRecordToTourListDetail(TourID, City, Dt, Expense, TourExpenseID);        
					DbForTourList.close();
					//==============================================================================
					//insert to local database
					//==============================================================================
				}
				new MyAsyncTaskForTourExpense().execute(userName.getText().toString(), password.getText().toString(),userType.toString());
			}
			else if(loginStatus.equalsIgnoreCase("F"))
			{
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Internet connection problem.\nTry again after some time.",Toast.LENGTH_SHORT).show();
			}
		} 
		catch (Throwable t) 
		{
			Log.d("Error On Saving and reading", t+"");
		}
	}
	//===================================================================================================================================
	//processing the XML got from server for TOUR LIST DETAIL
	//===================================================================================================================================

	
	
	
	//===================================================================================================================================
	//sending userID,Password,userType to server for TOUR EXPENSE
	//===================================================================================================================================
	private class MyAsyncTaskForTourExpense extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			postData(params[0],params[1],params[2]);
			return null;
		}

		protected void onPostExecute(Double result){
			if(responseCode == 200)
			{
				processTourExpenseResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(context, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}
		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username,String pwd,String Type) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/TourExpense");

			try {
				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
				nameValuePairs.add(new BasicNameValuePair("Type", Type));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());

				Log.d("result", responseBody);
			} 
			catch (Throwable t ) {
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END sending userID,Password,userType to server for TOUR EXPENSE
	//===================================================================================================================================

	



	//===================================================================================================================================
	//processing the XML got from server for TOUR EXPENSE
	//===================================================================================================================================
	private void processTourExpenseResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("tourExpenseData.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("tourExpenseData.xml");
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
			String loginStatus = null;

			for (int i=0;i<root.getLength();i++) 
			{
				loginStatus = "" + ((Element)root.item(i)).getAttribute("status");
			}

			//If Email and Pass match with server
			if(loginStatus.equalsIgnoreCase("Y"))
			{
				NodeList TourExpenseData =doc.getElementsByTagName("TourExpenseData");

				//==============================================================================
				//deleting local Tour_Expense_Table
				//==============================================================================
				DatabaseHandler DbForTourListDelete = new DatabaseHandler(this);
				
				DbForTourListDelete.open();        
				boolean deletedId = DbForTourListDelete.deleteRecordFromTourExpense();        
				DbForTourListDelete.close();
				//==============================================================================
				//END deleting local Tour_Expense_Table
				//==============================================================================

				for (int i=0;i<TourExpenseData.getLength();i++) 
				{
					String TourID  		 = "" + ((Element)TourExpenseData.item(i)).getAttribute("TourID");	
					String TourExpenseID = "" + ((Element)TourExpenseData.item(i)).getAttribute("TourExpenseID");
					String City  		 = "" + ((Element)TourExpenseData.item(i)).getAttribute("City");	
					String Hotel    	 = "" + ((Element)TourExpenseData.item(i)).getAttribute("Hotel");
					String Ph 			 = "" + ((Element)TourExpenseData.item(i)).getAttribute("Ph");	
					String Dt 			 = "" + ((Element)TourExpenseData.item(i)).getAttribute("Dt");	
					//==============================================================================
					//insert to local Tour_Expense_Table
					//==============================================================================
					DatabaseHandler DbForTourList = new DatabaseHandler(this);
					
					DbForTourList.open();        
					long id = DbForTourList.insertRecordToTourExpense(TourID, TourExpenseID, City, Hotel, Ph, Dt);        
					DbForTourList.close();
					//==============================================================================
					//END insert to local Tour_Expense_Table
					//==============================================================================
				}

				new MyAsyncTaskForTourExpenseDetail().execute(userName.getText().toString(), password.getText().toString(),userType.toString());
			}
			else if(loginStatus.equalsIgnoreCase("F"))
			{
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Internet connection problem.\nTry again after some time.",Toast.LENGTH_SHORT).show();
			}
		} 
		catch (Throwable t) 
		{
			Log.d("Error On Saving and reading", t+"");
		}
	}
	//===================================================================================================================================
	//processing the XML got from server for TOUR LIST
	//===================================================================================================================================


	
	
	//===================================================================================================================================
	//sending userID,Password,userType to server for TOUR EXPENSE DETAIL
	//===================================================================================================================================
	private class MyAsyncTaskForTourExpenseDetail extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			postData(params[0],params[1],params[2]);
			return null;
		}

		protected void onPostExecute(Double result){
			if(responseCode == 200)
			{
				processTourExpenseDetailResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(context, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}
		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username,String pwd,String Type) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/TourExpense2");

			try {
				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
				nameValuePairs.add(new BasicNameValuePair("Type", Type));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());

				Log.d("result", responseBody);
			} 
			catch (Throwable t ) {
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END sending userID,Password,userType to server for TOUR EXPENSE DETAIL
	//===================================================================================================================================

	
	
	
	//===================================================================================================================================
	//processing the XML got from server for TOUR EXPENSE
	//===================================================================================================================================
	private void processTourExpenseDetailResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("tourExpenseDetailData.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("tourExpenseDetailData.xml");
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
			String loginStatus = null;

			for (int i=0;i<root.getLength();i++) 
			{
				loginStatus = "" + ((Element)root.item(i)).getAttribute("status");
			}

			//If Email and Pass match with server
			if(loginStatus.equalsIgnoreCase("Y"))
			{
				NodeList TourExpenseData2 =doc.getElementsByTagName("TourExpenseData2");

				//==============================================================================
				//deleting local Tour_Expense_Table
				//==============================================================================
				DatabaseHandler DbForTourListDelete = new DatabaseHandler(this);
				
				DbForTourListDelete.open();        
				boolean deletedId = DbForTourListDelete.deleteRecordFromTourExpenseDetail();        
				DbForTourListDelete.close();
				//==============================================================================
				//END deleting local Tour_Expense_Table
				//==============================================================================

				for (int i=0;i<TourExpenseData2.getLength();i++) 
				{
					String TourExpenseID = "" + ((Element)TourExpenseData2.item(i)).getAttribute("TourExpenseID");	
					String TP 			 = "" + ((Element)TourExpenseData2.item(i)).getAttribute("TP");
					String Amt  		 = "" + ((Element)TourExpenseData2.item(i)).getAttribute("Amt");	
					String Remarks    	 = "" + ((Element)TourExpenseData2.item(i)).getAttribute("Remarks");
					//==============================================================================
					//insert to local Tour_Expense_Table
					//==============================================================================
					DatabaseHandler DbForTourList = new DatabaseHandler(this);
					
					DbForTourList.open();        
					long id = DbForTourList.insertRecordToTourExpenseDetail(TourExpenseID, TP, Amt, Remarks);        
					DbForTourList.close();
					//==============================================================================
					//END insert to local Tour_Expense_Table
					//==============================================================================
				}

				saveInPreference("stringLoginUser", userName.getText().toString());
				saveInPreference("stringLoginPwd", password.getText().toString());
				saveInPreference("Type", userType.toString());

				Intent showList = new Intent(this, Tab_MainViewActivity.class);
				finish();
				startActivity(showList);
			}
			else if(loginStatus.equalsIgnoreCase("F"))
			{
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Internet connection problem.\nTry again after some time.",Toast.LENGTH_SHORT).show();
			}
		} 
		catch (Throwable t) 
		{
			Log.d("Error On Saving and reading", t+"");
		}
	}
	//===================================================================================================================================
	//processing the XML got from server for TOUR LIST
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