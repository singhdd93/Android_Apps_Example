package com.pixels.mcq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBA {
	
	private static final String DATABASE_NAME = "mcq";
	private static final String TABLE_NAME1 = "mcq1";
	private static final int DATABASE_VERSION = 1;
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_QUESTION = "question";
	private static final String COLUMN_ANS = "ans";
	private static final String COLUMN_OP1 = "option1";
	private static final String COLUMN_OP2 = "option2";
	private static final String COLUMN_OP3 = "option3";
	private static final String COLUMN_OP4 = "option4";
	
	SQLiteDatabase db;
	DBHelper databaseHelper;
	final Context context;
	
	public DBA(Context ctx)
	{
		this.context = ctx;
		
		databaseHelper = new DBHelper(context);
	}
	
	//private static final String DATABASE_CREATE= "create table statuses(_id integer primary key autoincrement, fetched integer not null, message text not null, category text not null);";
	private static final String TABLE1_CREATE= "create table "+TABLE_NAME1+" ("+COLUMN_ID+" integer primary key autoincrement, "+COLUMN_QUESTION+" text not null, "+COLUMN_OP1+" text not null, "+COLUMN_OP2+" text not null, "+COLUMN_OP3+" text not null, "+COLUMN_OP4+" text not null, "+COLUMN_ANS+" text not null);";
	
	
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
				db.execSQL(TABLE1_CREATE);
				Log.d("CREATETABLE", TABLE_NAME1+" being created now");
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
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
	
	
	public void insert(String question, String op1, String op2, String op3, String op4, String ans)
	{
		ContentValues cv =new ContentValues();
		cv.put(COLUMN_QUESTION, question);
		cv.put(COLUMN_OP1, op1);
		cv.put(COLUMN_OP2, op2);
		cv.put(COLUMN_OP3, op3);
		cv.put(COLUMN_OP4, op4);
		cv.put(COLUMN_ANS, ans);
		db.insert(TABLE_NAME1, null, cv);
		//Log.d("VALUE INSERT", COLUMN_ID_FETCHED+" : "+Long.toString(idFetched)+ "  "+COLUMN_STATUS+" : "+message+"  "+COLUMN_CATEGORY+" : "+category);
		
	}

	
	public Cursor getAll() throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME1, new String[] {COLUMN_ID,
				COLUMN_QUESTION,COLUMN_OP1,COLUMN_OP2,COLUMN_OP3,COLUMN_OP4,COLUMN_ANS}, null, null, null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
//	public Cursor maxFetched_ID()
//	{
//		//return.db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
//		return db.query(TABLE_NAME1, new String[]{"MAX("+COLUMN_ID_FETCHED+")"}, null, null, null, null, null);
//	}

	public Cursor getEntry(long rowId) throws SQLException
	{
		Cursor mCursor =
				db.query(true, TABLE_NAME1, new String[] {COLUMN_ID,
				COLUMN_QUESTION,COLUMN_OP1,COLUMN_OP2,COLUMN_OP3,COLUMN_OP4,COLUMN_ANS}, COLUMN_ID + "=" + rowId, null, null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	
	
}


