package com.dds.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBA {
	
	private static final String DATABASE_NAME = "db1";
	private static final String TABLE_NAME = "table1";
	private static final int DATABASE_VERSION = 1;
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PHNO = "phone";
	SQLiteDatabase db;
	DBHelper databaseHelper;
	final Context context;
	
	public DBA(Context ctx)
	{
		this.context = ctx;
		
		databaseHelper = new DBHelper(context);
	}
	
	private static final String DATABASE_CREATE= "create table table1(id primary key, name text not null, phone int not null);";
	
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
	
	
	public void insert(String id, String name, int phone)
	{
		ContentValues cv =new ContentValues();
		cv.put(COLUMN_ID, id);
		cv.put(COLUMN_NAME, name);
		cv.put(COLUMN_PHNO, phone);
		db.insert(TABLE_NAME, null, cv);
		
	}
	
	public Cursor getAll()
	{
	return db.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME,
	COLUMN_PHNO}, null, null, null, null, null);
	}

	public Cursor getContact(long rowId) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_NAME, COLUMN_PHNO}, COLUMN_ID + "=" + rowId, null, null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	
}


