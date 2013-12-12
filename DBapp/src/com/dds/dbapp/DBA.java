package com.dds.dbapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA {
	
	private static final String DATABASE_NAME = "db1";
	private static final String TABLE_NAME = "table1";
	private static final int DATABASE_VERSION = 1;
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PHNO = "phone";
	private static final String COLUMN_EMAIL = "email";
	private static final String COLUMN_DOB = "dob";
	SQLiteDatabase db;
	DBHelper databaseHelper;
	final Context context;
	
	public DBA(Context ctx)
	{
		this.context = ctx;
		
		databaseHelper = new DBHelper(context);
	}
	
	private static final String DATABASE_CREATE= "create table table1(id integer primary key autoincrement, name text not null, phone intger(13) not null, email text(50), dob date);";
	
	private static class DBHelper extends SQLiteOpenHelper
	{

		public DBHelper(Context context) {
			
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			try{
				db.execSQL(DATABASE_CREATE);
				Log.d("CREATETABLE", "Table being created now");
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS table1");
			onCreate(db);
		}
		
	}
	
	public DBA open() throws SQLException
	{
		db = databaseHelper.getWritableDatabase();
		return this;
		
	}
	
	public void close()
	{
		databaseHelper.close();
	}
	
	
	public void insert(String name, long phone, String email, String dob)
	{
		ContentValues cv =new ContentValues();
		cv.put(COLUMN_NAME, name);
		cv.put(COLUMN_PHNO, phone);
		cv.put(COLUMN_EMAIL, email);
		cv.put(COLUMN_DOB, dob);
		db.insert(TABLE_NAME, null, cv);
		
	}
	
	public boolean delete(long rowId)
	{
	return db.delete(TABLE_NAME, COLUMN_ID + "=" + rowId, null) > 0;
	}
	public boolean update(long rowID, String name, long phone, String email, String dob)
	{
		ContentValues cv =new ContentValues();
		cv.put(COLUMN_NAME, name);
		cv.put(COLUMN_PHNO, phone);
		cv.put(COLUMN_EMAIL, email);
		cv.put(COLUMN_DOB, dob);
		return db.update(TABLE_NAME, cv, COLUMN_ID + "=" + rowID, null) > 0;
	}
	
	public Cursor getAll()
	{
	return db.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME,
	COLUMN_PHNO,COLUMN_EMAIL,COLUMN_DOB}, null, null, null, null, null);
	}

	public Cursor getEntry(long rowId) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_NAME, COLUMN_PHNO, COLUMN_EMAIL, COLUMN_DOB}, COLUMN_ID + "=" + rowId, null, null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	
}


