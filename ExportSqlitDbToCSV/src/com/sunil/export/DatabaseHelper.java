package com.sunil.export;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
 
	private static final String TAG="DatabaseHelper";
	private static final String DATABASE_NAME = "person.db";
	private static final int DATABASE_VERSION = 1;
	private RuntimeExceptionDao<Person, String> personRuntimeDao=null;
 
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.v(TAG, "DatabaseHelper constructor call");
	}
 

   public RuntimeExceptionDao<Person, String> getPersonDataDao() {
		
		Log.v(TAG, "getTimeDataDao call");
		
		if (personRuntimeDao == null) {
			personRuntimeDao = getRuntimeExceptionDao(Person.class);
		}
		return personRuntimeDao;
  	}
 	public int addPersonData(Person project)
 	{
 		Log.v(TAG, "addPersonData call");
 		RuntimeExceptionDao<Person, String> dao = getPersonDataDao();
 		int i = dao.create(project);
 		return i;
 	}
     
 	public List<Person> GetDataPerson()
	{
		Log.v(TAG, "GetDataPerson call");
		RuntimeExceptionDao<Person, String> simpleDao = getPersonDataDao();
		List<Person> list = simpleDao.queryForAll();
		return list;
	}
 	
 	public void deleteAllPerson()
	{
		Log.v(TAG, "deleteAllPerson call");
		RuntimeExceptionDao<Person, String> dao = getPersonDataDao();
		List<Person> list = dao.queryForAll();
		dao.delete(list);
	}
 
 	public UpdateBuilder<Person, String> updatePersonData() throws SQLException
	{
	RuntimeExceptionDao<Person, String> simpleDao = getPersonDataDao();
	UpdateBuilder<Person, String> updateBuilder = simpleDao.updateBuilder();
	return updateBuilder;
	}
 
	
	@Override
	public void close() {
		super.close();
		Log.v(TAG, "close call");
		personRuntimeDao=null;
	}
 
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.v(TAG, "onCreate call");
			TableUtils.createTable(connectionSource, Person.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.v(TAG, "onUpgrade call");
			TableUtils.dropTable(connectionSource, Person.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
}