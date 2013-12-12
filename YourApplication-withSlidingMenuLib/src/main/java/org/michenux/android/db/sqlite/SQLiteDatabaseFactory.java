package org.michenux.android.db.sqlite;

import org.michenux.android.info.VersionUtils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Michenux
 * 
 */
public class SQLiteDatabaseFactory {

	/**
	 * Sqlite database factory
	 */
	private static final SQLiteDatabaseFactory instance = new SQLiteDatabaseFactory();
	
	/**
	 * SQLite Open Helper
	 */
	private SQLiteDatabaseHelper helper;

	/**
	 * SQLite Database
	 */
	private SQLiteDatabase database ;

	private SQLiteDatabaseFactory() {
		
	}
	
	public static SQLiteDatabaseFactory getInstance() {
		return instance ;
	}
	
	/**
	 * @throws NameNotFoundException
	 */
	public void init(Context context, boolean writable, boolean enableFK) throws NameNotFoundException {
		this.helper = new SQLiteDatabaseHelper(context, "database", null,
				VersionUtils.getVersionCode(context));
		// open and close database to init database (creation/upgrade)
		if ( writable ) {
			this.database = this.helper.getWritableDatabase();
			if ( enableFK ) {
				this.database.execSQL("PRAGMA foreign_keys = ON");
			}
		}
		else {
			this.database = this.helper.getReadableDatabase();
		}
	}

	/**
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return this.database;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if ( this.database != null && this.database.isOpen()) {
			this.database.close();
		}
	} 
	
	/**
	 * 
	 */
	public void closeDatabase() {
		this.database.close();
	}
}
