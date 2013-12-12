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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerToCatch_Tab extends Activity {

	TableLayout customerToCatchTbl;
	TableRow customerToCatchTblRow;

	private ProgressDialog pgLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_to_catch_tab);
		//==========================Tour List Detail table=================================================

		customerToCatchTbl=(TableLayout) findViewById(R.id.Tour_Expense_List_Detail_Tbl);

		//--------------------------------------Table Header-----------------------------------------------
		TableRow tour_details_head = new TableRow(this);
		tour_details_head.setId(10);
		tour_details_head.setBackgroundResource(R.drawable.tour_detail_tbl_header);
		tour_details_head.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		final TextView Visit = new TextView(this);
		Visit.setId(20);
		Visit.setText("Visit");
		Visit.setTextColor(Color.WHITE);
		Visit.setPadding(5,5,5,5);
		tour_details_head.addView(Visit);// add the column to the table row here
		Visit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);    

		final TextView customerName = new TextView(this);
		customerName.setId(20);
		customerName.setText("Customer");
		customerName.setTextColor(Color.WHITE);
		customerName.setPadding(5,5,5,5);
		tour_details_head.addView(customerName);// add the column to the table row here
		customerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 

		final TextView visited = new TextView(this);
		visited.setId(20);
		visited.setText("Visited");
		visited.setTextColor(Color.WHITE);
		visited.setPadding(5,5,5,5);
		tour_details_head.addView(visited);// add the column to the table row here
		visited.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 

		final TextView timeVisited = new TextView(this);
		timeVisited.setId(20);
		timeVisited.setText("Date Visited");
		timeVisited.setTextColor(Color.WHITE);
		timeVisited.setPadding(5,5,5,5);
		tour_details_head.addView(timeVisited);// add the column to the table row here
		timeVisited.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 


		final TextView personVisited = new TextView(this);
		personVisited.setId(20);
		personVisited.setText("Person Visited");
		personVisited.setTextColor(Color.WHITE);
		personVisited.setPadding(5,5,5,5);
		tour_details_head.addView(personVisited);// add the column to the table row here
		personVisited.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 

		final TextView remarks = new TextView(this);
		remarks.setId(20);
		remarks.setText("Remarks");
		remarks.setTextColor(Color.WHITE);
		remarks.setPadding(5,5,5,5);
		tour_details_head.addView(remarks);// add the column to the table row here
		remarks.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 
		
		final TextView csCode = new TextView(this);
		csCode.setId(20);
		csCode.setText(" ");
		csCode.setTextColor(Color.WHITE);
		csCode.setPadding(5,5,5,5);
		csCode.setVisibility(View.GONE);
		tour_details_head.addView(csCode);// add the column to the table row here
		csCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 

		customerToCatchTbl.addView(tour_details_head, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		//------------------------------------- Table Header-----------------------------------------------

		pgLogin = new ProgressDialog(CustomerToCatch_Tab.this);
		pgLogin.setMessage("Please wait ...");
		pgLogin.setIndeterminate(true);
		pgLogin.setCancelable(true);
		pgLogin.setCanceledOnTouchOutside(false);

		pgLogin.show();

		new MyAsyncTaskForTableBody().execute(getFromPreference("stringLoginUser"), getFromPreference("stringLoginPwd"), getFromPreference("Type"), 
											  getFromPreference("City"), getFromPreference("tourIdForSaving"), getFromPreference("Date"));
	}



	//===================================================================================================================================
	//sending userID,Password,userType to server for TOUR EXPENSE
	//===================================================================================================================================
	private class MyAsyncTaskForTableBody extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			postData(params[0],params[1],params[2],params[3],params[4],params[5]);
			return null;
		}

		protected void onPostExecute(Double result){
			if(responseCode == 200)
			{
				processCustomerToVisiteResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(CustomerToCatch_Tab.this, "Sorry. Internet Connection problem.", Toast.LENGTH_LONG).show();
			}
		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username,String pwd,String Type,String City,String TourID,String Dt) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/CustomerToCatch");

			try {
				
				Log.d("username", username);
				Log.d("pwd", pwd);
				Log.d("Type", Type);
				Log.d("City", City);
				Log.d("TourID", TourID);
				Log.d("Dt", Dt);

				// Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
				nameValuePairs.add(new BasicNameValuePair("Type", Type));
				nameValuePairs.add(new BasicNameValuePair("City", City));
				nameValuePairs.add(new BasicNameValuePair("TourID", TourID));
				nameValuePairs.add(new BasicNameValuePair("Dt", Dt));

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
	private void processCustomerToVisiteResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("processCustomerToCatchResponceData.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("processCustomerToCatchResponceData.xml");
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
				NodeList CustomerToCatch = doc.getElementsByTagName("CustomerToCatch");


				for (int i=0;i<CustomerToCatch.getLength();i++) 
				{
					String Visit		 = "" + ((Element)CustomerToCatch.item(i)).getAttribute("Visit");
					String Visited    	 = "" + ((Element)CustomerToCatch.item(i)).getAttribute("Visited");
					String Tm 			 = "" + ((Element)CustomerToCatch.item(i)).getAttribute("Tm");	
					String PersonVisited = "" + ((Element)CustomerToCatch.item(i)).getAttribute("PersonVisited");	
					String Remarks 		 = "" + ((Element)CustomerToCatch.item(i)).getAttribute("Remarks");	
					String CS_NAME 		 = "" + ((Element)CustomerToCatch.item(i)).getAttribute("Customer");	
					String csCode 		 = "" + ((Element)CustomerToCatch.item(i)).getAttribute("CTCID");	

					createTableBody(Visit,Visited,Tm,PersonVisited,Remarks,CS_NAME,csCode); 
				}
				
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
			}
			else if(loginStatus.equalsIgnoreCase("F"))
			{
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Sorry No record found.",Toast.LENGTH_SHORT).show();
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

	//============================================================================================================
	//Creating table body
	//============================================================================================================
	private void createTableBody(String Visit, String Visited, String Tm, String PersonVisited, String Remarks, String CS_NAME, String csCode) 
	{
		Log.d("from", "Inside createTableBody");
		//----------------table body------------------------------------------
		customerToCatchTblRow = new TableRow(this);
		customerToCatchTblRow.setId(10);
		customerToCatchTblRow.setBackgroundResource(R.drawable.tour_list_detail_tbl_row_bg);
		customerToCatchTblRow.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		

		final CheckBox visit = new CheckBox(this);
		visit.setId(20);
		visit.setTextColor(Color.BLACK);
		visit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		customerToCatchTblRow.addView(visit);// add the column to the table row here
		visit.setEnabled(false);
		
		if(Visit.equalsIgnoreCase("False"))
		{
			visit.setChecked(false);
		}
		else if(Visit.equalsIgnoreCase("True"))
		{
			visit.setChecked(true);
		}
		

		final TextView customerName = new TextView(this);
		customerName.setId(20);
		customerName.setText(CS_NAME);
		customerName.setTextColor(Color.BLACK);
		customerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		customerName.setHint("Customer Name");
		customerName.setPadding(5,5,5,5);
		customerName.setSingleLine(false);
		customerName.setLines(1);
		customerToCatchTblRow.addView(customerName);// add the column to the table row here
		LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)customerName.getLayoutParams();
		params3.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		customerName.setLayoutParams(params3);

		final CheckBox visited = new CheckBox(this);
		visited.setId(20);
		visited.setTextColor(Color.BLACK);
		visited.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		visited.setPadding(5,5,5,5);
		customerToCatchTblRow.addView(visited);// add the column to the table row here
		LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams)visited.getLayoutParams();
		params4.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		visited.setLayoutParams(params4);
		
		if(Visited.equalsIgnoreCase("False"))
		{
			visited.setChecked(false);
		}
		else if(Visited.equalsIgnoreCase("True"))
		{
			visited.setChecked(true);
		}

		final EditText timeVisited = new EditText(this);
		timeVisited.setId(20);
		timeVisited.setText(Tm);
		timeVisited.setTextColor(Color.BLACK);
		timeVisited.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		timeVisited.setHint("eg. 14:30 for 2:30PM");
		timeVisited.setPadding(5,5,5,5);
		timeVisited.setSingleLine(false);
		timeVisited.setLines(1);
		timeVisited.setBackgroundResource(R.drawable.text_bg);
		customerToCatchTblRow.addView(timeVisited);// add the column to the table row here
		LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams)timeVisited.getLayoutParams();
		params5.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		timeVisited.setLayoutParams(params5);
		
		//it checks max input is 5
		int maxLength = 5;
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(maxLength);
		timeVisited.setFilters(FilterArray);

		//It allow only numbers and : 
		InputFilter filter = new InputFilter() 
		{ 
			@Override
			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) 
			{
				for (int i = start; i < end; i++) 
				{
					if (!Character.isDigit(source.charAt(i)) && source.charAt(i) != ':') 
					{
						return "";
					}
				}
				return null;
			} 
		}; 

		timeVisited.setFilters(new InputFilter[]{filter}); 
				

		final EditText personVisited = new EditText(this);
		personVisited.setId(20);
		personVisited.setText(PersonVisited);
		personVisited.setTextColor(Color.BLACK);
		personVisited.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		personVisited.setHint("Person Visited");
		personVisited.setPadding(5,5,5,5);
		personVisited.setSingleLine(false);
		personVisited.setLines(1);
		personVisited.setBackgroundResource(R.drawable.text_bg);
		customerToCatchTblRow.addView(personVisited);// add the column to the table row here
		LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams)personVisited.getLayoutParams();
		params6.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		personVisited.setLayoutParams(params6);

		final EditText remarks = new EditText(this);
		remarks.setId(20);
		remarks.setText(Remarks);
		remarks.setTextColor(Color.BLACK);
		remarks.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		remarks.setHint("Remarks");
		remarks.setPadding(5,5,5,5);
		remarks.setSingleLine(false);
		remarks.setLines(2);
		remarks.setBackgroundResource(R.drawable.text_bg);
		customerToCatchTblRow.addView(remarks);// add the column to the table row here
		LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams)remarks.getLayoutParams();
		params7.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		remarks.setLayoutParams(params7);
		
		final EditText csCodeValue = new EditText(this);
		csCodeValue.setId(20);
		csCodeValue.setText(csCode);
		csCodeValue.setTextColor(Color.BLACK);
		csCodeValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		csCodeValue.setHint("csCodeValue");
		csCodeValue.setPadding(5,5,5,5);
		csCodeValue.setSingleLine(false);
		csCodeValue.setLines(2);
		csCodeValue.setBackgroundResource(R.drawable.text_bg);
		csCodeValue.setVisibility(View.GONE);
		customerToCatchTblRow.addView(csCodeValue);// add the column to the table row here
		LinearLayout.LayoutParams params8 = (LinearLayout.LayoutParams)csCodeValue.getLayoutParams();
		params8.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		csCodeValue.setLayoutParams(params8);




		customerToCatchTbl.addView(customerToCatchTblRow, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		//----------------------On click table row---------------------------------------
		customerToCatchTblRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{

			}
		});
		//----------------------On click table row---------------------------------------
	}
	//============================================================================================================
	//END Creating table body
	//============================================================================================================


	
	
	//============================================================================================================
	//Save data
//	TableLayout customerToCatchTbl;
//	TableRow customerToCatchTblRow;
	//============================================================================================================
	public void SaveData(View v) {
		
		boolean checkAllField = true;
		String xmlFormat = "<orderitems>";
		for(int i = 1; i < customerToCatchTbl.getChildCount(); i++)
		{
			xmlFormat = xmlFormat + "<orderitem>";
			
            TableRow row1	   = (TableRow)customerToCatchTbl.getChildAt(i);
            
            CheckBox visit   		= (CheckBox) row1.getChildAt(0);
            TextView customer  		= (TextView) row1.getChildAt(1);
            CheckBox visited   		= (CheckBox) row1.getChildAt(2);
            EditText visitedDate    = (EditText) row1.getChildAt(3);
            EditText personVisited  = (EditText) row1.getChildAt(4);
            EditText remarks    	= (EditText) row1.getChildAt(5);
            EditText csCode         = (EditText) row1.getChildAt(6);
            
            
            String visitValue;
            String customerValue;
            String visitedValue;
            String visitedDateValue;
            String personVisitedValue;
            String remarksValue;
            String csCodeValue;
            
			if(visit.isChecked())
			{
				visitValue = "True";
			}
			else
			{
				visitValue = "False";
			}
			
			if(visited.isChecked())
			{
				visitedValue = "True";
			}
			else
			{
				visitedValue = "False";
			}
			
			customerValue 	   = customer.getText().toString();
			visitedDateValue   = visitedDate.getText().toString();
			personVisitedValue = personVisited.getText().toString();
			remarksValue       = remarks.getText().toString();
			csCodeValue		   = csCode.getText().toString();
			
			xmlFormat = xmlFormat + 
            		"<CTCID>"+ csCodeValue +"</CTCID>"+
            		"<Visit>"+ visitValue +"</Visit>"+
            		"<Visited>"+ visitedValue +"</Visited>"+
            		"<Tm>"+ visitedDateValue +"</Tm>"+
            		"<PersonVisited>"+ personVisitedValue +"</PersonVisited>"+
            		"<Remarks>"+ remarksValue +"</Remarks>"+
            		"</orderitem>";
		}            
		xmlFormat = xmlFormat + "</orderitems>";
		
		pgLogin = new ProgressDialog(CustomerToCatch_Tab.this);
		pgLogin.setMessage("Please wait while saving ...");
		pgLogin.setIndeterminate(true);
		pgLogin.setCancelable(true);
		pgLogin.setCanceledOnTouchOutside(false);

		pgLogin.show();
		new MyAsyncTaskForSaveingCustomerToVisit().execute(getFromPreference("stringLoginUser"), getFromPreference("stringLoginPwd"), getFromPreference("Type"), 
														   getFromPreference("tourIdForSaving"),getFromPreference("City"), getFromPreference("Date"),xmlFormat);
	}
	
	//============================================================================================================
	//END Save data
	//============================================================================================================

	
	
	//===================================================================================================================================
	//Sending EXPENSE Data to server
	//===================================================================================================================================
	private class MyAsyncTaskForSaveingCustomerToVisit extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0],params[1],params[2],params[3],params[4],params[5],params[6]);
			return null;
		}

		protected void onPostExecute(Double result){

			if(responseCode == 200)
			{
				processCustomerToVisitResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(CustomerToCatch_Tab.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}

		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String username, String pwd, String Type, String TourID, String City, String Dt, String ParDetailsToSend) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/CustomerToCatchEntry");

			try {
				
				Log.d("username", username);
				Log.d("pwd", pwd);
				Log.d("Type", Type);
				Log.d("TourID", TourID);
				Log.d("City", City);
				Log.d("Dt", Dt);
				Log.d("ParDetails", ParDetailsToSend);
				
				//Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
				nameValuePairs.add(new BasicNameValuePair("Type", Type));
				nameValuePairs.add(new BasicNameValuePair("TourID", TourID));
				nameValuePairs.add(new BasicNameValuePair("City", City));
				nameValuePairs.add(new BasicNameValuePair("Dt", Dt));
				nameValuePairs.add(new BasicNameValuePair("ParDetails", ParDetailsToSend));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());
				Log.d("Saving result", responseBody);
			} 
			catch (Throwable t ) {
				Log.d("Error Time of Login",t+"");
			} 
		}
	}
	//===================================================================================================================================
	//END Sending EXPENSE Data to server
	//===================================================================================================================================

	
	
	
	//===================================================================================================================================
	//processing the XML got from server for Expense saving
	//===================================================================================================================================
	private void processCustomerToVisitResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("processCustomerToCatchResponce.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("processCustomerToCatchResponce.xml");
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
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Your data saved successfully",Toast.LENGTH_LONG).show();
				
				Intent showList = new Intent(this, TourEntryActivity.class);
				finish();
				startActivity(showList);
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
	//processing the XML got from server for Expense saving
	//===================================================================================================================================

	
	
	
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
