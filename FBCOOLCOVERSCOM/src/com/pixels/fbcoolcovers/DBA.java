package com.pixels.fbcoolcovers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA {
	
	private static final String DATABASE_NAME = "fb";
	private static final String TABLE_NAME = "covers";
	private static final int DATABASE_VERSION = 1;
	
	SQLiteDatabase db;
	DBHelper databaseHelper;
	final Context context;
	
	public DBA(Context ctx)
	{
		this.context = ctx;
		
		databaseHelper = new DBHelper(context);
	}
	
	private static final String DATABASE_CREATE= "create table covers(_id integer primary key autoincrement, fetched integer not null, curl text not null, ctitle text not null, category text not null);";
	
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
			db.execSQL("DROP TABLE IF EXISTS covers");
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
	
	
	public void insert(long idFetched, String title, String url, String category)
	{
		ContentValues cv =new ContentValues();
		cv.put(Util.COLUMN_ID_FETCHED, idFetched);
		cv.put(Util.COLUMN_CLINK, url);
		cv.put(Util.COLUMN_CTITLE, title);
		cv.put(Util.COLUMN_CATEGORY, category);
		db.insert(TABLE_NAME, null, cv);
		
	}
	
	
	public Cursor getAll()
	{
	return db.query(TABLE_NAME, new String[] {Util.COLUMN_ID, Util.COLUMN_ID_FETCHED,
			Util.COLUMN_CTITLE,Util.COLUMN_CLINK,Util.COLUMN_CATEGORY}, null, null, null, null, null);
	
	}
	
	public Cursor getAllDistinct()
	{
	return db.query(true, TABLE_NAME, new String[]{Util.COLUMN_ID, Util.COLUMN_ID_FETCHED,
			Util.COLUMN_CTITLE,Util.COLUMN_CLINK,Util.COLUMN_CATEGORY}, null, null, Util.COLUMN_CLINK, null, Util.COLUMN_ID_FETCHED + " DESC", null);
		
	}

	public Cursor maxFetched_ID()
	{
		
		return db.query(TABLE_NAME, new String[]{"MAX("+Util.COLUMN_ID_FETCHED+")"}, null, null, null, null, null);
	}

	public Cursor getCoversOfACategory(String category) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {Util.COLUMN_ID,
						Util.COLUMN_ID_FETCHED, Util.COLUMN_CLINK, Util.COLUMN_CTITLE, Util.COLUMN_CATEGORY}, Util.COLUMN_CATEGORY + "=" + "'"+category+"'", null, null, null, Util.COLUMN_ID_FETCHED + " DESC", null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	public Cursor getSearchedMessagesAll(String title ) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {Util.COLUMN_ID,
						Util.COLUMN_ID_FETCHED, Util.COLUMN_CTITLE, Util.COLUMN_CLINK, Util.COLUMN_CATEGORY}, Util.COLUMN_CTITLE+" LIKE "+"'%"+title+"%'", null, Util.COLUMN_CLINK, null, Util.COLUMN_ID_FETCHED + " DESC", null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
}


