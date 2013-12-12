package com.kanif.importexporttocsv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatebaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "userinfo";
	private static final String TABLE_NAME = "user";
	private static final String USER_NAME = "username";
	private static final String USER_AGE = "age";
	private static final String USER_SEX = "sex";

	public DatebaseHelper(Context context, String db_name,
			CursorFactory factory, int version) {
		super(context, DB_NAME, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_table = "create table " + TABLE_NAME
				+ "(id integer primary key autoincrement," + USER_NAME + " text,"
				+ USER_AGE +" text);";
		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}

}
