package com.example.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler {
	public static final String TAG 			 = "DBAdapter";
	public static final String DATABASE_NAME = "TourDB";
	private static final int DATABASE_VERSION   = 2;
	private final Context context;    
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	//-----------------------------------------------------------------------------------------
	public static final String KEY_ROWID      		  = "id";
	public static final String KEY_TOUR_ID	          = "TourID";
	public static final String KEY_TOUR_NAME          = "TourName";
	public static final String KEY_TOUR_BRANCH_ID 	  = "TourBranchID";
	public static final String KEY_TOUR_MKT_BOY 	  = "TourMktBoy";
	public static final String KEY_TOUR_MKT_MOB 	  = "TourMktMob";
	public static final String KEY_TOUR_START_DT 	  = "TourStartDt";
	public static final String KEY_TOUR_BRANCH 	  	  = "Branch";
	
	private static final String TOUR_LIST_TABLE = "TourListTable";

	private static final String TOUR_LIST_TABLE_DROP ="drop table if exists "+ TOUR_LIST_TABLE +";";

	private static final String TOUR_LIST_TABLE_CREATE ="create table if not exists "+ TOUR_LIST_TABLE +
					" (id integer primary key autoincrement, " +
					"TourID VARCHAR, TourName VARCHAR, TourBranchID VARCHAR, " +
					"TourMktBoy VARCHAR, TourMktMob VARCHAR, TourStartDt VARCHAR, Branch VARCHAR);";
	
	//-----------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public static final String KEY_TOUR_DETAIL_ROWID    = "id";
	public static final String KEY_TOUR_DETAIL_ID	    = "TourID";
	public static final String KEY_TOUR_DETAIL_CITY	    = "City";
	public static final String KEY_TOUR_DT          	= "Dt";
	public static final String KEY_TOUR_EXPENSE    	    = "Expense";
	public static final String KEY_TOUR_EXPENSE_ID 	    = "TourMktBoy";
	
	private static final String TOUR_LIST_DETAIL_TABLE = "TourListDetailTable";

	private static final String TOUR_LIST_DETAIL_TABLE_DROP ="drop table if exists "+ TOUR_LIST_DETAIL_TABLE +";";

	private static final String TOUR_LIST_DETAIL_TABLE_CREATE ="create table if not exists "+ TOUR_LIST_DETAIL_TABLE +
					" (id integer primary key autoincrement, " +
					"TourID VARCHAR, City VARCHAR, Dt VARCHAR, " +
					"Expense VARCHAR, TourMktBoy VARCHAR);";
	//-----------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------
	public static final String KEY_EXPENSE_ROWID      		  			  = "id";
	public static final String KEY_EXPENSE_TOUR_ID	          			  = "TourID";
	public static final String KEY_TOUR_EXPENSE_ID_FOR_EXPENSE_TBL        = "TourExpenseID";
	public static final String KEY_TOUR_EXPENSE_CITY	  	  			  = "City";
	public static final String KEY_TOUR_EXPENSE_HOTEL 	     			  = "Hotel";
	public static final String KEY_TOUR_EXPENSE_HOTEL_PH 	  			  = "Ph";
	public static final String KEY_TOUR_EXPENSE_HOTEL_DT 	  			  = "Dt";
	
	private static final String TOUR_EXPENSE_TABLE = "Tour_Expense_Table";

	private static final String TOUR_EXPENSE_TABLE_DROP ="drop table if exists "+ TOUR_EXPENSE_TABLE +";";

	private static final String TOUR_EXPENSE_TABLE_CREATE ="create table if not exists "+ TOUR_EXPENSE_TABLE +
					" (id integer primary key autoincrement, " +
					"TourID VARCHAR, TourExpenseID VARCHAR, City VARCHAR, " +
					"Hotel VARCHAR, Ph VARCHAR, Dt VARCHAR);";
	
	//-----------------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------------
	public static final String KEY_EXPENSE_DETAIL_ROWID       = "id";
	public static final String KEY_TOUR_EXPENSE_ID_DETAIL	  = "TourExpenseID";
	public static final String KEY_TP					      = "TP";
	public static final String KEY_AMT					  	  = "Amt";
	public static final String KEY_REMARKS 	     			  = "Remarks";
	
	private static final String TOUR_EXPENSE_DETAIL_TABLE = "Tour_Expense_Detail_Table";

	private static final String TOUR_EXPENSE_EXPENSE_TABLE_DROP ="drop table if exists "+ TOUR_EXPENSE_DETAIL_TABLE +";";

	private static final String TOUR_EXPENSE_DETAIL_TABLE_CREATE ="create table if not exists "+ TOUR_EXPENSE_DETAIL_TABLE +
					" (id integer primary key autoincrement, TourExpenseID VARCHAR, TP VARCHAR, Amt VARCHAR, Remarks VARCHAR);";
	
	//-----------------------------------------------------------------------------------------


	public DatabaseHandler(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			try 
			{
				db.execSQL(TOUR_LIST_TABLE_DROP);	
				db.execSQL(TOUR_LIST_TABLE_CREATE);	
				db.execSQL(TOUR_LIST_DETAIL_TABLE_DROP);	
				db.execSQL(TOUR_LIST_DETAIL_TABLE_CREATE);	
				db.execSQL(TOUR_EXPENSE_TABLE_DROP);	
				db.execSQL(TOUR_EXPENSE_TABLE_CREATE);	
				db.execSQL(TOUR_EXPENSE_EXPENSE_TABLE_DROP);	
				db.execSQL(TOUR_EXPENSE_DETAIL_TABLE_CREATE);	

			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}    

	//---opens the database---
	public DatabaseHandler open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}

	//--------------------------------------------------------------------------------------------
	//Query for TOUR_LIST_TABLE
	//--------------------------------------------------------------------------------------------

	//---insert a record into the TOUR_LIST_TABLE---
	public long insertRecordToTourList(String TourID, String TourName, String TourBranchID, String TourMktBoy, String TourMktMob, String TourStartDt, String Branch) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TOUR_ID, TourID);
		initialValues.put(KEY_TOUR_NAME, TourName);
		initialValues.put(KEY_TOUR_BRANCH_ID, TourBranchID);
		initialValues.put(KEY_TOUR_MKT_BOY, TourMktBoy);
		initialValues.put(KEY_TOUR_MKT_MOB, TourMktMob);
		initialValues.put(KEY_TOUR_START_DT, TourStartDt);
		initialValues.put(KEY_TOUR_BRANCH, Branch);
		
		return db.insert(TOUR_LIST_TABLE, null, initialValues);
	}
	
	//---deletes all  record---
	public boolean deleteRecordFromTourList() 
	{
		return db.delete(TOUR_LIST_TABLE, KEY_ROWID + ">0", null) > 0;
	}
	
	//---retrieves all the records---
	public Cursor getAllRecordsFromTourList() 
	{
		return db.query(TOUR_LIST_TABLE, new String[] {KEY_ROWID, KEY_TOUR_ID,
				KEY_TOUR_NAME, KEY_TOUR_BRANCH_ID,KEY_TOUR_MKT_BOY,KEY_TOUR_MKT_MOB,KEY_TOUR_START_DT,KEY_TOUR_BRANCH}, null, null, null, null, null);
	}

	
	//---retrieves a particular record---
	public Cursor getRecordFromTourList(String tourId) throws SQLException 
	{
		Cursor mCursor =
				db.query(true, TOUR_LIST_TABLE, new String[] {KEY_ROWID, KEY_TOUR_ID,
						KEY_TOUR_NAME, KEY_TOUR_BRANCH_ID,KEY_TOUR_MKT_BOY,KEY_TOUR_MKT_MOB,KEY_TOUR_START_DT,KEY_TOUR_BRANCH}, 
						KEY_TOUR_ID + "=" + tourId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	//--------------------------------------------------------------------------------------------
	//END Query for TOUR_LIST_TABLE
	//--------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------
	//Query for TOUR_LIST_DETAIL_TABLE
	//--------------------------------------------------------------------------------------------
	
	//---insert a record into the TOUR_LIST_DETAIL_TABLE---
	public long insertRecordToTourListDetail(String TourID, String City, String Dt, String Expense, String TourExpenseID) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TOUR_DETAIL_ID, TourID);
		initialValues.put(KEY_TOUR_DETAIL_CITY, City);
		initialValues.put(KEY_TOUR_DT, Dt);
		initialValues.put(KEY_TOUR_EXPENSE, Expense);
		initialValues.put(KEY_TOUR_EXPENSE_ID, TourExpenseID);
		return db.insert(TOUR_LIST_DETAIL_TABLE, null, initialValues);
	}
	
	//---deletes all  record---
	public boolean deleteRecordFromTourListDetail() 
	{
		return db.delete(TOUR_LIST_DETAIL_TABLE, KEY_ROWID + ">0", null) > 0;
	}
		
	//---retrieves a particular record---
	public Cursor getRecordFromTourListDetail(String tourId) throws SQLException 
	{
		Cursor mCursor =
				db.query(true, TOUR_LIST_DETAIL_TABLE, new String[] {KEY_TOUR_DETAIL_ROWID, KEY_TOUR_DETAIL_ID,
						KEY_TOUR_DETAIL_CITY, KEY_TOUR_DT,KEY_TOUR_EXPENSE,KEY_TOUR_EXPENSE_ID}, 
						KEY_TOUR_DETAIL_ID + "=" + tourId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	//--------------------------------------------------------------------------------------------
	//END Query for TOUR_LIST_DETAIL_TABLE
	//--------------------------------------------------------------------------------------------

	
	
	
	//--------------------------------------------------------------------------------------------
	//Query for TOUR_EXPENSE_DETAIL_TABLE
	//--------------------------------------------------------------------------------------------
	
	//---insert a record into the TOUR_LIST_DETAIL_TABLE---
	public long insertRecordToTourExpenseDetail(String TourExpenseID,String TP,String Amt,String Remarks) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TOUR_EXPENSE_ID_DETAIL, TourExpenseID);
		initialValues.put(KEY_TP, TP);
		initialValues.put(KEY_AMT, Amt);
		initialValues.put(KEY_REMARKS, Remarks);
		return db.insert(TOUR_EXPENSE_DETAIL_TABLE, null, initialValues);
	}
	
	//---deletes all  record---
	public boolean deleteRecordFromTourExpenseDetail() 
	{
		return db.delete(TOUR_EXPENSE_DETAIL_TABLE, KEY_ROWID + ">0", null) > 0;
	}
		
	//---retrieves a particular record---
	public Cursor getRecordFromTourExpenseDetail(String tourId) throws SQLException 
	{
		Cursor mCursor =
				db.query(true, TOUR_EXPENSE_DETAIL_TABLE, new String[] {KEY_EXPENSE_DETAIL_ROWID, KEY_TOUR_EXPENSE_ID_DETAIL,
						KEY_TP, KEY_AMT,KEY_REMARKS}, 
						KEY_TOUR_EXPENSE_ID_DETAIL + "=" + tourId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	//--------------------------------------------------------------------------------------------
	//END Query for TOUR_EXPENSE_DETAIL_TABLE
	//--------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------
	//Query for TOUR_EXPENSE_TABLE
	//--------------------------------------------------------------------------------------------

	//---insert a record into the TOUR_LIST_TABLE---
	public long insertRecordToTourExpense(String TourID, String TourExpenseID, String City, String Hotel, String Ph, String Dt) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_EXPENSE_TOUR_ID, TourID);
		initialValues.put(KEY_TOUR_EXPENSE_ID_FOR_EXPENSE_TBL, TourExpenseID);
		initialValues.put(KEY_TOUR_EXPENSE_CITY, City);
		initialValues.put(KEY_TOUR_EXPENSE_HOTEL, Hotel);
		initialValues.put(KEY_TOUR_EXPENSE_HOTEL_PH, Ph);
		initialValues.put(KEY_TOUR_EXPENSE_HOTEL_DT, Dt);
		return db.insert(TOUR_EXPENSE_TABLE, null, initialValues);
	}
	
	//---deletes all  record---
	public boolean deleteRecordFromTourExpense() 
	{
		return db.delete(TOUR_EXPENSE_TABLE, KEY_EXPENSE_ROWID + ">0", null) > 0;
	}

	
	//---retrieves a particular record---
	public Cursor getRecordFromTourExpense(String tourExpenseId) throws SQLException 
	{
		Cursor mCursor =
				db.query(true, TOUR_EXPENSE_TABLE, new String[] {KEY_EXPENSE_ROWID, KEY_EXPENSE_TOUR_ID,
						KEY_TOUR_EXPENSE_ID_FOR_EXPENSE_TBL, KEY_TOUR_EXPENSE_CITY,KEY_TOUR_EXPENSE_HOTEL,KEY_TOUR_EXPENSE_HOTEL_PH,KEY_TOUR_EXPENSE_HOTEL_DT}, 
						KEY_TOUR_EXPENSE_ID_FOR_EXPENSE_TBL + "=" + tourExpenseId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	//--------------------------------------------------------------------------------------------
	//END Query for TOUR_LIST_TABLE
	//--------------------------------------------------------------------------------------------

//	//---deletes a particular record---
//	public boolean deleteRecord(long rowId) 
//	{
//		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
//	}
//
//	//---retrieves all the records---
//	public Cursor getAllRecords() 
//	{
//		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_FIRST_NAME,
//				KEY_LAST_NAME, KEY_ADDRESS}, null, null, null, null, null);
//	}
//
//	//---retrieves a particular record---
//	public Cursor getRecord(long rowId) throws SQLException 
//	{
//		Cursor mCursor =
//				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
//						KEY_FIRST_NAME, KEY_LAST_NAME, KEY_ADDRESS }, 
//						KEY_ROWID + "=" + rowId, null, null, null, null, null);
//		if (mCursor != null) {
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	}
//
//	//---updates a record---
//	public boolean updateRecord(long rowId, String firstName, String lastName, String address) 
//	{
//		ContentValues args = new ContentValues();
//		args.put(KEY_FIRST_NAME, firstName);
//		args.put(KEY_LAST_NAME, lastName);
//		args.put(KEY_ADDRESS, address);
//		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
//	}
}
