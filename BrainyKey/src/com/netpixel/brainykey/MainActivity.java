package com.netpixel.brainykey;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends SherlockFragmentActivity {
	
	WebView wb;
	ProgressBar pBar;
	TextView loadingView;
	
	String TAG = "Brainy Key";
	SlidingMenu slidingMenu ;
	
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (CheckService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
      //  slidingMenu.setSe
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        slidingMenu.setMenu(R.layout.slidingmenu);
        
        if(isMyServiceRunning() == false)
        {
        	Intent myIntent = new Intent(this, CheckService.class);
            this.startService(myIntent);
        }
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		wb = (WebView)findViewById(R.id.webViewer);
		pBar = (ProgressBar)findViewById(R.id.pgBar);
		loadingView = (TextView)findViewById(R.id.loadingText);
		
		WebSettings ws = wb.getSettings();
		//ws.setAllowContentAccess(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);	
		ws.setJavaScriptEnabled(true);
		ws.setSupportMultipleWindows(true);
		ws.setDomStorageEnabled(true);
		wb.setWebViewClient(new WebViewClient() 
		{ public boolean shouldOverrideUrlLoading(WebView view, String url) 
		{ Log.i(TAG, "Processing webview url click...");
		pBar.setVisibility(View.VISIBLE);
		loadingView.setVisibility(View.VISIBLE);
		wb.setVisibility(View.GONE);
		view.loadUrl(url); 
		return true; 
		}  
		
		
	    
		public void onPageFinished(WebView view, String url) 
		{ Log.i(TAG, "Finished loading URL: " +url);
		pBar.setVisibility(View.GONE);
		loadingView.setVisibility(View.GONE);
		wb.setVisibility(View.VISIBLE);
		//if (progressBar.isShowing()) 
		//{ progressBar.dismiss(); 
		//} 
		}  

		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) 
		{ Log.e(TAG, "Error: " + description); 
		
		} 
		});
		
		wb.loadUrl("http://www.brainykey.com/");
		
		
		
	}

	@Override
    public void onBackPressed() {
        if ( slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
        else {
            super.onBackPressed();
        }
    }
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            this.slidingMenu.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		

		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            this.slidingMenu.toggle();
            return true;
		
        	
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
