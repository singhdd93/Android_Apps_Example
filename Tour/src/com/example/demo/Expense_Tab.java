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
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import com.example.utils.DatabaseHandler;

public class Expense_Tab extends Activity {

	TextView textViewCityValue;
	TextView textViewDateValue;
	EditText textViewHotelValue;
	EditText textViewHotelMobileNoValue;
	TextView textViewTotalAmt;
	
	String ExpenseId;
	String TourID;
	String City;
	String Amt;
	String Hotel;
	String Ph;
	String Dt;
	String ParDetails;
	private ProgressDialog pgLogin;
	
	TableLayout TourExpenseListDetail;
	TableRow TourExpenseListDetailRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_tab);

		textViewCityValue 		   = (TextView)findViewById(R.id.textViewCityValue);
		textViewDateValue  		   = (TextView)findViewById(R.id.textViewDateValue);
		textViewHotelValue 		   = (EditText)findViewById(R.id.textViewHotelValue);
		textViewHotelMobileNoValue = (EditText)findViewById(R.id.textViewHotelMobNo);
		textViewTotalAmt  		   = (TextView)findViewById(R.id.textViewTotalAmtVal); 

		ExpenseId = getFromPreference("ExpenseId");
		TourID    = getFromPreference("tourIdForSaving");

		//----------------------------------------------------------------------------------------------
		// get a Records from tour_Expense_table
		//----------------------------------------------------------------------------------------------
		textViewCityValue.setText(getFromPreference("City"));
		textViewDateValue.setText(getFromPreference("Date"));
		
		City = getFromPreference("City");
		Dt   = getFromPreference("Date");
		Amt	 = getFromPreference("TotalAmt");
		
		textViewTotalAmt.setText("Total amount: " + getFromPreference("TotalAmt"));

		DatabaseHandler db = new DatabaseHandler(this);
		db.open();
		Cursor c = db.getRecordFromTourExpense(ExpenseId);
		if (c.moveToFirst())
		{
			do 
			{  
				textViewHotelValue.setText(c.getString(4));
				textViewHotelMobileNoValue.setText(c.getString(5));
			}while (c.moveToNext());
		}
		db.close();
		//----------------------------------------------------------------------------------------------
		//END get all Records from tour_Expense_table
		//----------------------------------------------------------------------------------------------
		
		
		//==========================Tour List Detail table=================================================

		TourExpenseListDetail=(TableLayout) findViewById(R.id.Tour_Expense_List_Detail_Tbl);

		//--------------------------------------Table Header-----------------------------------------------
		TableRow tour_details_head = new TableRow(this);
		tour_details_head.setId(10);
		tour_details_head.setBackgroundResource(R.drawable.tour_detail_tbl_header);
		tour_details_head.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));


		final TextView city = new TextView(this);
		city.setId(20);
		city.setText("TP");
		city.setTextColor(Color.WHITE);
		city.setPadding(10,5,5,5);
		tour_details_head.addView(city);// add the column to the table row here
		city.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);    

		final TextView Amt = new TextView(this);
		Amt.setId(20);
		Amt.setText("Amount");
		Amt.setTextColor(Color.WHITE);
		Amt.setPadding(5,5,5,5);
		tour_details_head.addView(Amt);// add the column to the table row here
		Amt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);    
		LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams)Amt.getLayoutParams();
		params7.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		params7.gravity = Gravity.RIGHT;
		Amt.setLayoutParams(params7);

		final TextView expense_entry = new TextView(this);
		expense_entry.setId(20);
		expense_entry.setText("Remarks");
		expense_entry.setTextColor(Color.WHITE);
		expense_entry.setPadding(5,5,5,5);
		tour_details_head.addView(expense_entry);// add the column to the table row here
		expense_entry.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);  


		int plusid = getResources().getIdentifier("com.example.demo:drawable/plus", null, null);
		
		final ImageView imagePlus = new ImageView(this);
		imagePlus.setId(20);
		imagePlus.setPadding(5,5,5,5);
		imagePlus.setImageResource(plusid);
		tour_details_head.addView(imagePlus);// add the column to the table row here
		LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams)imagePlus.getLayoutParams();
		params6.gravity = Gravity.LEFT;
		imagePlus.setLayoutParams(params6);
		
		//Add a new row
		imagePlus.setOnClickListener(new OnClickListener()
	    {
	        @Override 
	        public void onClick(View v)
	        {
	        	addNewRow();
	        }
	    });

		TourExpenseListDetail.addView(tour_details_head, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		//------------------------------------- Table Header-----------------------------------------------
		createTableBody();
	}
	
	//============================================================================================================
	//Creating table body
	//============================================================================================================
	private void createTableBody() 
	{
		//----------------------------------------------------------------------------------------------
		//get all Records from tour_list_table_detail
		//----------------------------------------------------------------------------------------------

		DatabaseHandler db = new DatabaseHandler(this);
		db.open();
		Cursor c = db.getRecordFromTourExpenseDetail(ExpenseId);
		if (c.moveToFirst())
		{
			do 
			{        
				//----------------table body------------------------------------------
				TourExpenseListDetailRow = new TableRow(this);
				TourExpenseListDetailRow.setId(10);
				TourExpenseListDetailRow.setBackgroundResource(R.drawable.tour_list_detail_tbl_row_bg);
				TourExpenseListDetailRow.setLayoutParams(new LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));


				String[] items = { "Credit Card", "Cash"};

				final Spinner TP = new Spinner(this);
				TP.setId(20);
				TP.setPadding(8,8,8,8);
				TP.setBackgroundResource(R.drawable.text_bg);
				TourExpenseListDetailRow.addView(TP);// add the column to the table row here
				LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)TP.getLayoutParams();
				params1.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
				TP.setLayoutParams(params1);
				
				ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, items);
				aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				TP.setAdapter(aa);
				
				if(c.getString(2).equalsIgnoreCase("Cash"))
				{
					TP.setSelection(1);
				}
				else if(c.getString(2).equalsIgnoreCase("Credit Card"))
				{
					TP.setSelection(0);
				}

				final EditText Amt = new EditText(this);
				Amt.setId(20);
				Amt.setText(c.getString(3));
				Amt.setTextColor(Color.BLACK);
				Amt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				Amt.setHint("Amount");
				Amt.setPadding(8,8,8,8);
				Amt.setSingleLine(false);
				Amt.setLines(1);
				Amt.setBackgroundResource(R.drawable.text_bg);
				Amt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
				Amt.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
				TourExpenseListDetailRow.addView(Amt);// add the column to the table row here
				LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)Amt.getLayoutParams();
				params2.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
				params2.gravity = Gravity.RIGHT;
				Amt.setLayoutParams(params2);

				final EditText Remarks = new EditText(this);
				Remarks.setId(20);
				Remarks.setText(c.getString(4));
				Remarks.setTextColor(Color.BLACK);
				Remarks.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				Remarks.setWidth(10);
				Remarks.setHint("Remark");
				Remarks.setPadding(8,2,8,2);
				Remarks.setSingleLine(true);
				Remarks.setLines(1);
				Remarks.setBackgroundResource(R.drawable.text_bg);
				TourExpenseListDetailRow.addView(Remarks);// add the column to the table row here
				LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)Remarks.getLayoutParams();
				params3.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
				Remarks.setLayoutParams(params3);

				
				
				int id = getResources().getIdentifier("com.example.demo:drawable/delete", null, null);
				
				final ImageView imageMinus = new ImageView(this);
				imageMinus.setId(20);
				imageMinus.setPadding(5,5,5,5);
				imageMinus.setImageResource(id);
				TourExpenseListDetailRow.addView(imageMinus);// add the column to the table row here
				LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams)imageMinus.getLayoutParams();
				params5.gravity = Gravity.LEFT;
				imageMinus.setLayoutParams(params5);
				
				//Delete a particular row
				imageMinus.setOnClickListener(new OnClickListener()
			    {
			        @Override 
			        public void onClick(View v)
			        {
			            View row = (View) v.getParent();
			            ViewGroup container = ((ViewGroup)row.getParent());
			            container.removeView(row);
			            container.invalidate();
			        }
			    });
				
				TourExpenseListDetail.addView(TourExpenseListDetailRow, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				
				//----------------------On click table row---------------------------------------
				TourExpenseListDetailRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) 
					{

					}
				});
				//----------------------On click table row---------------------------------------


			} while (c.moveToNext());
		}
		db.close();

		//----------------------------------------------------------------------------------------------
		//get all Records from tour_list_table_detail
		//----------------------------------------------------------------------------------------------
	}
	//============================================================================================================
	//END Creating table body
	//============================================================================================================

	
	
	//============================================================================================================
	//Add new row
	//============================================================================================================
	
	public void addNewRow() {
		//----------------table body------------------------------------------
		TourExpenseListDetailRow = new TableRow(this);
		TourExpenseListDetailRow.setId(10);
		TourExpenseListDetailRow.setBackgroundResource(R.drawable.tour_list_detail_tbl_row_bg);
		TourExpenseListDetailRow.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		String[] items = { "Credit Card", "Cash"};

		final Spinner TP = new Spinner(this);
		TP.setId(20);
		TP.setPadding(8,8,8,8);
		TP.setBackgroundResource(R.drawable.text_bg);
		TourExpenseListDetailRow.addView(TP);// add the column to the table row here
		LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)TP.getLayoutParams();
		params1.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
		TP.setLayoutParams(params1);
		ArrayAdapter aa = new ArrayAdapter(
				this,
				R.layout.spinner_item, 
				items);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		TP.setAdapter(aa);

		final EditText Amt = new EditText(this);
		Amt.setId(20);
		Amt.setText("");
		Amt.setTextColor(Color.BLACK);
		Amt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		Amt.setHint("Amount");
		Amt.setPadding(8,8,8,8);
		Amt.setSingleLine(false);
		Amt.setLines(1);
		Amt.setBackgroundResource(R.drawable.text_bg);
		Amt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		Amt.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		TourExpenseListDetailRow.addView(Amt);// add the column to the table row here
		LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)Amt.getLayoutParams();
		params2.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		params2.gravity = Gravity.RIGHT;
		Amt.setLayoutParams(params2);


		final EditText Remarks = new EditText(this);
		Remarks.setId(20);
		Remarks.setText("");
		Remarks.setTextColor(Color.BLACK);
		Remarks.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		Remarks.setWidth(10);
		Remarks.setHint("Remark");
		Remarks.setPadding(8,2,8,2);
		Remarks.setSingleLine(true);
		Remarks.setLines(1);
		Remarks.setBackgroundResource(R.drawable.text_bg);
		TourExpenseListDetailRow.addView(Remarks);// add the column to the table row here
		LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)Remarks.getLayoutParams();
		params3.setMargins(0, 0, 5, 0); //substitute parameters for left, top, right, bottom
		Remarks.setLayoutParams(params3);

		
		
		int id = getResources().getIdentifier("com.example.demo:drawable/delete", null, null);
		
		final ImageView imageMinus = new ImageView(this);
		imageMinus.setId(20);
		imageMinus.setPadding(5,5,5,5);
		imageMinus.setImageResource(id);
		TourExpenseListDetailRow.addView(imageMinus);// add the column to the table row here
		LinearLayout.LayoutParams params8 = (LinearLayout.LayoutParams)imageMinus.getLayoutParams();
		params8.gravity = Gravity.LEFT;
		imageMinus.setLayoutParams(params8);
		
		imageMinus.setOnClickListener(new OnClickListener()
	    {
	        @Override 
	        public void onClick(View v)
	        {
	            View row = (View) v.getParent();
	            ViewGroup container = ((ViewGroup)row.getParent());
	            container.removeView(row);
	            container.invalidate();
	        }
	    });
		
		TourExpenseListDetail.addView(TourExpenseListDetailRow, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		//----------------------On click table row---------------------------------------
		TourExpenseListDetailRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{

			}
		});
		//----------------------On click table row---------------------------------------
	}

	//============================================================================================================
	//Add new row
	//============================================================================================================

	
	
	
	
	
	//============================================================================================================
	//Save data
	//============================================================================================================
	public void SaveData(View v) {
		
		boolean checkAllField = true;
		String xmlFormat =
	            "<orderitems>";
		for(int i = 1; i < TourExpenseListDetail.getChildCount(); i++)
		{
			xmlFormat = xmlFormat + "<orderitem>";
			
            TableRow row1	   = (TableRow)TourExpenseListDetail.getChildAt(i);
            
            Spinner  tp    	   = (Spinner) row1.getChildAt(0);
            EditText amount    = (EditText) row1.getChildAt(1);
            EditText remark    = (EditText) row1.getChildAt(2);
            
            String tpValue     = tp.getSelectedItem().toString();
            String amountValue = amount.getText().toString();
            String remarkValue = remark.getText().toString();
            
            
            if(tpValue.length()<1 || amountValue.length()<1 || remarkValue.length()<1)
			{
            	checkAllField = false;
			}
            else
            {
	            xmlFormat = xmlFormat + 
	            		"<TP>"+ tpValue +"</TP>"+
	            		"<Amt>"+ amountValue +"</Amt>"+
	            		"<Remarks>"+ remarkValue +"</Remarks>"+
	            		"</orderitem>";
            }
		}
		
//		if(TourExpenseListDetail.getChildCount() == 1)
//		{
//	            xmlFormat =  "<orderitem>" + 
//			            	 "<TP></TP>"+
//			            	 "<Amt></Amt>"+
//			            	 "<Remarks></Remarks>"+
//			            	 "</orderitem>";
//		}
		
		xmlFormat = xmlFormat + "</orderitems>";
		
		if(checkAllField)
		{
			ParDetails = xmlFormat;
			Log.d("XML", ParDetails);
			
			if(textViewHotelValue.getText().length()<1 || textViewHotelMobileNoValue.getText().length()<1)
			{
				Toast.makeText(this, "Please provide Hotel Name & Phone no.", Toast.LENGTH_LONG).show();
			}
			else
			{
				pgLogin = new ProgressDialog(Expense_Tab.this);
				pgLogin.setMessage("Please wait while saving ...");
				pgLogin.setIndeterminate(true);
				pgLogin.setCancelable(true);
				pgLogin.setCanceledOnTouchOutside(false);

				pgLogin.show();
				
				Hotel = textViewHotelValue.getText().toString();
				Ph	  = textViewHotelMobileNoValue.getText().toString();

				
				new MyAsyncTaskForSaveingExpense().execute(ExpenseId,TourID,City,Amt,Hotel,Ph,Dt,ParDetails);
			}
		}
		else
			Toast.makeText(this, "Please provide all information.", Toast.LENGTH_LONG).show();
	}
	
	//============================================================================================================
	//END Save data
	//============================================================================================================

	
	
	
	//===================================================================================================================================
	//Sending EXPENSE Data to server
	//===================================================================================================================================
	private class MyAsyncTaskForSaveingExpense extends AsyncTask<String, Integer, Double>{

		String responseBody;
		int responseCode;
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
			return null;
		}

		protected void onPostExecute(Double result){

			if(responseCode == 200)
			{
				processExpenseSavingResponce(responseBody);
			}

			else
			{
				if (pgLogin.isShowing()) 
				{
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText(Expense_Tab.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
			}

		}

		protected void onProgressUpdate(Integer... progress){

		}

		public void postData(String ExpenseIdToSend, String TourIDToSend, String CityToSend, String AmtToSend, String HotelToSend, String PhToSend, String DtToSend, String ParDetailsToSend) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://61.8.158.157:89/ServiceTour.asmx/TourExpenseEntry");

			try {
				
				Log.d("username", getFromPreference("stringLoginUser"));
				Log.d("pwd", getFromPreference("stringLoginPwd"));
				Log.d("Type", getFromPreference("Type"));
				Log.d("TourExpenseID", ExpenseIdToSend);
				Log.d("TourID", TourIDToSend);
				Log.d("City", CityToSend);
				Log.d("Amt", AmtToSend);
				Log.d("Hotel", HotelToSend);
				Log.d("Ph", PhToSend);
				Log.d("Dt", DtToSend);
				Log.d("ParDetails", ParDetailsToSend);
				
				//Data that I am sending
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", getFromPreference("stringLoginUser")));
				nameValuePairs.add(new BasicNameValuePair("pwd", getFromPreference("stringLoginPwd")));
				nameValuePairs.add(new BasicNameValuePair("Type", getFromPreference("Type")));
				nameValuePairs.add(new BasicNameValuePair("TourExpenseID", ExpenseIdToSend));
				nameValuePairs.add(new BasicNameValuePair("TourID", TourIDToSend));
				nameValuePairs.add(new BasicNameValuePair("City", CityToSend));
				nameValuePairs.add(new BasicNameValuePair("Amt", AmtToSend));
				nameValuePairs.add(new BasicNameValuePair("Hotel", HotelToSend));
				nameValuePairs.add(new BasicNameValuePair("Ph", PhToSend));
				nameValuePairs.add(new BasicNameValuePair("Dt", DtToSend));
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
	private void processExpenseSavingResponce(String responceFromServer) 
	{
		try {
			//saving the file as a xml
			FileOutputStream fOut = openFileOutput("ExpenseSavingData.xml",MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(responceFromServer);
			osw.flush();
			osw.close();

			//reading the file as xml
			FileInputStream fIn = openFileInput("ExpenseSavingData.xml");
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
				new MyAsyncTaskForTourList().execute(getFromPreference("stringLoginUser"), getFromPreference("stringLoginPwd"),getFromPreference("Type"));
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
				Toast.makeText(Expense_Tab.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
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
				new MyAsyncTaskForTourListDetail().execute(getFromPreference("stringLoginUser"), getFromPreference("stringLoginPwd"),getFromPreference("Type"));
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
				Toast.makeText(Expense_Tab.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
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
				new MyAsyncTaskForTourExpense().execute(getFromPreference("stringLoginUser"), getFromPreference("stringLoginPwd"),getFromPreference("Type"));
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
				Toast.makeText(Expense_Tab.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
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

				new MyAsyncTaskForTourExpenseDetail().execute(getFromPreference("stringLoginUser"), getFromPreference("stringLoginPwd"),getFromPreference("Type"));
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
				Toast.makeText(Expense_Tab.this, "Not getting proper responce from server.\nCould be wifi problem or server.", Toast.LENGTH_LONG).show();
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
				if (pgLogin.isShowing()) {
					pgLogin.cancel();
					pgLogin.dismiss();
				}
				Toast.makeText( getApplicationContext(),"Your Expense saved successfully",Toast.LENGTH_LONG).show();
				
				Intent showList = new Intent(this, TourEntryActivity.class);
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