package com.dds.fbstatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity{
	
	String path;
	
	public void CopyDB(InputStream inputStream,
			OutputStream outputStream) throws IOException {
			//---copy 1K bytes at a time---
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
			}
			inputStream.close();
			outputStream.close();
			}
	
	public static String getDataDir(Context context) throws Exception {
	    return context.getPackageManager()
	            .getPackageInfo(context.getPackageName(), 0)
	            .applicationInfo.dataDir;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_screen_activity);
		try{
		path = getDataDir(getApplicationContext());
		Log.d("DATA PATH", path);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try {
		String destPath = path +"/databases";
		File f = new File(destPath);
		if (!f.exists()) {
		f.mkdirs();
		f.createNewFile();
		CopyDB(getBaseContext().getAssets().open("fb"),
		new FileOutputStream(destPath + "/fb"));
		}
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
				{
					finish();
					Intent i = new Intent(Splash.this, StatusList.class);
					Splash.this.startActivity(i);
				}
				else
				{
					finish();
					Intent i = new Intent(Splash.this, StatusList_OldAPI.class);
					Splash.this.startActivity(i);
				}
			}
		}, 3000);
	}

}
