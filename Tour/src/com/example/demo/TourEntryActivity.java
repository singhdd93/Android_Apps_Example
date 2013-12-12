package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.DatabaseHandler;

public class TourEntryActivity extends Activity {

	String TourID;
	TextView NameOfTour;
	TextView TourByBranch;
	TextView TourByMktBoy;
	TextView MktBoyMobileNo;
	TextView TourStartDate;
	TableLayout TourListDetail;
	TableRow TourListDetailRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_entry);


		NameOfTour     = (TextView)findViewById(R.id.textViewNameOfTourValue);
		TourByBranch   = (TextView)findViewById(R.id.textViewTourByBranchValue);
		TourByMktBoy   = (TextView)findViewById(R.id.textViewTourByMktBoyValue);
		MktBoyMobileNo = (TextView)findViewById(R.id.textViewMktBoyMobileNoValue);
		TourStartDate  = (TextView)findViewById(R.id.textViewTourStartDateValue);
		
		TourID = getFromPreference("list_id");

		//----------------------------------------------------------------------------------------------
		//get all Records from tour_list_table
		//----------------------------------------------------------------------------------------------

		DatabaseHandler db = new DatabaseHandler(this);
		db.open();
		Cursor c = db.getRecordFromTourList(TourID);
		if (c.moveToFirst())
		{
			do 
			{        
				NameOfTour.setText(c.getString(2));
				TourByBranch.setText(c.getString(7));
				TourByMktBoy.setText(c.getString(4));
				MktBoyMobileNo.setText(c.getString(5));

				String[] tempDateArray = c.getString(6).toString().split(" ");
				TourStartDate.setText(tempDateArray[0]);

			} while (c.moveToNext());
		}
		db.close();

		//----------------------------------------------------------------------------------------------
		//get all Records from tour_list_table
		//----------------------------------------------------------------------------------------------

		//==========================Tour List Detail table=================================================

		TourListDetail=(TableLayout) findViewById(R.id.Tour_List_Detail_Tbl);

		//--------------------------------------Table Header-----------------------------------------------
		TableRow tour_details_head = new TableRow(this);
		tour_details_head.setId(10);
		tour_details_head.setBackgroundResource(R.drawable.tour_detail_tbl_header);
		tour_details_head.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));


		final TextView city = new TextView(this);
		city.setId(20);
		city.setText("City");
		city.setTextColor(Color.WHITE);
		city.setPadding(5,5,5,5);
		tour_details_head.addView(city);// add the column to the table row here
		city.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);    

		final TextView date = new TextView(this);
		date.setId(20);
		date.setText("Date");
		date.setTextColor(Color.WHITE);
		date.setPadding(5,5,0,5);
		tour_details_head.addView(date);// add the column to the table row here
		date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);    

		final TextView expense_entry = new TextView(this);
		expense_entry.setId(20);
		expense_entry.setText("Expense Entry");
		expense_entry.setTextColor(Color.WHITE);
		expense_entry.setPadding(5,5,5,5);
		tour_details_head.addView(expense_entry);// add the column to the table row here
		LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)expense_entry.getLayoutParams();
		params2.gravity = Gravity.RIGHT;
		expense_entry.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);    

		final TextView blank = new TextView(this);
		blank.setId(20);
		blank.setText(" ");
		blank.setTextColor(Color.WHITE);
		blank.setPadding(5,5,5,5);
		tour_details_head.addView(blank);// add the column to the table row here
		blank.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		blank.setVisibility(View.GONE);

		TourListDetail.addView(tour_details_head, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		//------------------------------------- Table Header-----------------------------------------------
		
		createTableBody();

	}
	
	@Override
	public void onBackPressed() {
		Intent showList = new Intent(this, Tab_MainViewActivity.class);
		showList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(showList);
		super.onBackPressed();
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
		Log.d("TourID", TourID);
		final Cursor c = db.getRecordFromTourListDetail(TourID);
		if (c.moveToFirst())
		{
			do 
			{        
				final String[] tempDateArray = c.getString(3).toString().split(" ");
				
				//----------------table body------------------------------------------
				TourListDetailRow = new TableRow(this);
				TourListDetailRow.setId(10);
				TourListDetailRow.setBackgroundResource(R.drawable.tour_list_detail_tbl_row_bg);
				TourListDetailRow.setLayoutParams(new LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));


				final TextView City = new TextView(this);
				City.setId(20);
				City.setText(c.getString(2));
				City.setTextColor(Color.BLACK);
				City.setPadding(5,5,5,5);
				City.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				TourListDetailRow.addView(City);// add the column to the table row here

				final TextView Date = new TextView(this);
				Date.setId(20);
				Date.setText(tempDateArray[0]);
				Date.setTextColor(Color.BLACK);
				Date.setPadding(5,5,0,5);
				Date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				TourListDetailRow.addView(Date);// add the column to the table row here

				final TextView ExpenseEntry = new TextView(this);
				ExpenseEntry.setId(20);
				ExpenseEntry.setText(c.getString(4));
				ExpenseEntry.setTextColor(Color.BLACK);
				ExpenseEntry.setPadding(5,5,5,5);
				ExpenseEntry.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				TourListDetailRow.addView(ExpenseEntry);// add the column to the table row here
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)ExpenseEntry.getLayoutParams();
				params.gravity = Gravity.RIGHT;
				
				final TextView ExpenseId = new TextView(this);
				ExpenseId.setId(20);
				ExpenseId.setText(c.getString(5) + "##@@" + c.getString(4));
				ExpenseId.setTextColor(Color.BLACK);
				ExpenseId.setPadding(5,5,5,5);
				ExpenseId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				TourListDetailRow.addView(ExpenseId);// add the column to the table row here
				ExpenseId.setVisibility(View.GONE);

				TourListDetail.addView(TourListDetailRow, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				
				//----------------------On click table row---------------------------------------
				TourListDetailRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) 
					{
						saveInPreference("City", City.getText()+"");
						saveInPreference("Date", Date.getText()+"");
						
						String[] tempIdAndExpenseArray = ExpenseId.getText().toString().split("##@@");
						saveInPreference("ExpenseId", tempIdAndExpenseArray[0]);
						saveInPreference("TotalAmt", tempIdAndExpenseArray[1]);
						Log.d("Amt", tempIdAndExpenseArray[1]);
						saveInPreference("tourIdForSaving", TourID);
						

						Intent detalTabView = new Intent(TourEntryActivity.this, Tab_PaymetCustomerToCatchExpense.class);
						startActivity(detalTabView);

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
