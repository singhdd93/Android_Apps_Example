package org.michenux.android.init;

import org.michenux.android.db.sqlite.SQLiteDatabaseFactory;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author Michenux
 *
 */
public class AppInit {

	/**
	 * App init
	 */
	private static final AppInit instance = new AppInit();
	
	private AppInit() {
		
	}
	
	public static AppInit getInstance() {
		return instance;
	}
	
	/**
	 * @param context
	 * @throws NameNotFoundException
	 */
	public void init( Context context ) throws NameNotFoundException {
		SQLiteDatabaseFactory.getInstance().init( context, true, true );
	}
}
