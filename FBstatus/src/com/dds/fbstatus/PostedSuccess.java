package com.dds.fbstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.ads.*;



public class PostedSuccess extends Activity{
	
	
	
	
	private AdView adView;
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (adView != null) {
		      adView.destroy();
		    }
		    super.onDestroy();
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
	public void openWebURL( String inURL ) {
	    Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );

	    startActivity( browse );	
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	finish();
            //Intent main = new Intent(PostedSuccess.this, StatusList.class);
            //startActivity(main);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	private boolean appInstalledOrNot(String uri)
    {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try
        {
               pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
               app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
               app_installed = false;
        }
        return app_installed ;
}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			finish();
			//Intent i = new Intent(PostedSuccess.this, StatusList.class);
			//PostedSuccess.this.startActivity(i);
		}
		else
		{
			finish();
			//Intent i = new Intent(PostedSuccess.this, StatusList_OldAPI.class);
			//PostedSuccess.this.startActivity(i);
		}
	}
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.successfully_posted_activity);
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
	       
			
		}
		Button goToProfile = (Button)findViewById(R.id.go_to_profile);
		final boolean fbInstalled = appInstalledOrNot("com.facebook.katana");
		goToProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(fbInstalled)
				{
					Log.d("FB Installed", "Using Facebook app");
					String inte = "facebook://facebook.com/wall";
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(inte));
					startActivity(intent);
				}
				else
				{
					Log.d("FB Not Installed", "Using Browser");
				openWebURL("https://www.facebook.com/profile.php");
				}
			}
		});
	}

}
