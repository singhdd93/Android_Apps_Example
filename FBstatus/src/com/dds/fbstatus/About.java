package com.dds.fbstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;


public class About extends Activity{
	
	TextView aboutViewer;
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
	       
			
		}
		setContentView(R.layout.about_activity);
		
		aboutViewer = (TextView)findViewById(R.id.about_text_view);
		aboutViewer.setMovementMethod(new ScrollingMovementMethod());
	}
}
