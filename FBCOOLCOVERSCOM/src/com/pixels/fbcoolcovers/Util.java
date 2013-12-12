package com.pixels.fbcoolcovers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;

public class Util {
	
	//Universal Strings
	static final String COLUMN_ID = "_id";
	static final String COLUMN_ID_FETCHED = "fetched";
	static final String COLUMN_CLINK = "curl";
	static final String COLUMN_CTITLE = "ctitle";
	static final String COLUMN_CATEGORY = "category";
	static String url = "http://fbcoolcovers.com/fetch2.php";
	static final String TAG = "fbstatus";
	static final String TAG_PID = "id";
	static final String TAG_TITLE = "title";
	static final String TAG_URL = "coverurl";
	static final String TAG_CATEGORY = "category";
	static final String COMPELTE_URL1 = "http://www.fbcoolcovers.com/wp-content/uploads/";
	
	
	
	//Universally used functions
	public static int getScreenOrientation(Context ctx)
    {
        Display getOrient = ((Activity) ctx).getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{ 
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else { 
                 orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

}
