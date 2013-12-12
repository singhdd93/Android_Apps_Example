package com.dds.fbstatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA {
	
	private static final String DATABASE_NAME = "fb";
	private static final String TABLE_NAME = "statuses";
	private static final int DATABASE_VERSION = 1;
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_ID_FETCHED = "fetched";
	private static final String COLUMN_STATUS = "message";
	private static final String COLUMN_CATEGORY = "category";
	
	SQLiteDatabase db;
	DBHelper databaseHelper;
	final Context context;
	
	public DBA(Context ctx)
	{
		this.context = ctx;
		
		databaseHelper = new DBHelper(context);
	}
	
	private static final String DATABASE_CREATE= "create table statuses(_id integer primary key autoincrement, fetched integer not null, message text not null, category text not null);";
	
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
			db.execSQL("DROP TABLE IF EXISTS statuses");
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
	
	
	public void insert(long idFetched, String message, String category)
	{
		ContentValues cv =new ContentValues();
		cv.put(COLUMN_ID_FETCHED, idFetched);
		cv.put(COLUMN_STATUS, message);
		cv.put(COLUMN_CATEGORY, category);
		db.insert(TABLE_NAME, null, cv);
		Log.d("VALUE INSERT", COLUMN_ID_FETCHED+" : "+Long.toString(idFetched)+ "  "+COLUMN_STATUS+" : "+message+"  "+COLUMN_CATEGORY+" : "+category);
		
	}
	/**
	public boolean delete(long rowId)
	{
	return db.delete(TABLE_NAME, COLUMN_ID + "=" + rowId, null) > 0;
	}
	public boolean update(long rowID, long idFetched, String message, String category)
	{
		ContentValues cv =new ContentValues();
		cv.put(COLUMN_ID_FETCHED, idFetched);
		cv.put(COLUMN_STATUS, message);
		cv.put(COLUMN_CATEGORY, category);
		return db.update(TABLE_NAME, cv, COLUMN_ID + "=" + rowID, null) > 0;
	}**/
	
	public Cursor getAll()
	{
	return db.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_ID_FETCHED,
	COLUMN_STATUS,COLUMN_CATEGORY}, null, null, null, null, null);
	
	//return db.query(true, TABLE_NAME, new String[]{COLUMN_ID_FETCHED,
	//COLUMN_STATUS,COLUMN_CATEGORY}, null, null, COLUMN_STATUS, null, COLUMN_ID_FETCHED, null);
	}
	
	public Cursor getAllDistinct()
	{
	return db.query(true, TABLE_NAME, new String[]{COLUMN_ID, COLUMN_ID_FETCHED,
	COLUMN_STATUS,COLUMN_CATEGORY}, null, null, COLUMN_STATUS, null, COLUMN_ID_FETCHED + " DESC", null);
		
	}
	
	public Cursor getNextMessage(String category, long id)
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_ID_FETCHED, COLUMN_STATUS, COLUMN_CATEGORY}, COLUMN_ID_FETCHED+">="+id+" and "+COLUMN_CATEGORY + "=" + "'"+category+"'", null, null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	public Cursor getCategories()
	{
		return db.query(true, TABLE_NAME, new String[]{COLUMN_ID, COLUMN_CATEGORY, "COUNT("+COLUMN_STATUS+")"}, null, null, COLUMN_CATEGORY, null, COLUMN_CATEGORY, null);
	}
	
	public Cursor maxFetched_ID()
	{
		//return.db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
		return db.query(TABLE_NAME, new String[]{"MAX("+COLUMN_ID_FETCHED+")"}, null, null, null, null, null);
	}

	public Cursor getEntry(long rowId) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_ID_FETCHED, COLUMN_STATUS, COLUMN_CATEGORY}, COLUMN_ID + "=" + rowId, null, null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	public Cursor getMessagesOfACategory(String category) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_ID_FETCHED, COLUMN_STATUS, COLUMN_CATEGORY}, COLUMN_CATEGORY + "=" + "'"+category+"'", null, null, null, COLUMN_ID_FETCHED + " DESC", null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	public Cursor getSearchedMessages(String category, String message ) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_ID_FETCHED, COLUMN_STATUS, COLUMN_CATEGORY}, COLUMN_STATUS+" LIKE "+"'%"+message+"%"+"' and "+COLUMN_CATEGORY + "=" + "'"+category+"'", null, null, null, COLUMN_ID_FETCHED + " DESC", null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	public Cursor getSearchedMessagesAll(String message ) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME, new String[] {COLUMN_ID,
				COLUMN_ID_FETCHED, COLUMN_STATUS, COLUMN_CATEGORY}, COLUMN_STATUS+" LIKE "+"'%"+message+"%'", null, null, null, COLUMN_ID_FETCHED + " DESC", null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
}


