package com.kanif.importexporttocsv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHandler {

	private static final String DB_NAME = "userinfo";
	private static final String TABLE_NAME = "user";
	private static final String USER_NAME = "username";
	private static final String USER_AGE = "age";
	private static final String USER_SEX = "sex";

	private static final int DATABASE_VERSION = 2;
	private SQLiteDatabase sqliteDB;
	private DatebaseHelper dbHelper;
	
	
	public DatabaseHandler(Context context)
	{
		
		dbHelper=new DatebaseHelper(context, DB_NAME, null, DATABASE_VERSION);
		
	}
	// Open Database 
	public void open()
	{
		sqliteDB=dbHelper.getWritableDatabase();
		
	}
	
	// Close Database 
    public void close() {
        if (sqliteDB != null)
            sqliteDB.close();
    }
    
    
	public Cursor getUserInfo() {
		
		Cursor cursor = sqliteDB.query(TABLE_NAME, null, null, null, null, null,
				null);
		cursor.moveToFirst();
//		while(cursor.moveToNext())
//		{
//		Log.e("In getUserInfo()", "name:"+cursor.getString(1)+", age:"+cursor.getString(2));
//		}
		return cursor;
	}
	public long insertUserInfo(String name, String age)
	{
		ContentValues contentValues=new ContentValues();
		sqliteDB=dbHelper.getWritableDatabase();
		contentValues.put(USER_NAME, name);
		contentValues.put(USER_AGE, age);
		
		return  sqliteDB.insert(TABLE_NAME, null, contentValues);
	}
	
	
}
