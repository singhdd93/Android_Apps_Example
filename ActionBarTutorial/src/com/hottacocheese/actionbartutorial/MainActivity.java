package com.hottacocheese.actionbartutorial;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // get the ActionBar from our layout
        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        // set its title
	    actionBar.setTitle(R.string.app_name);
	    // set what the home action does
	    actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_launcher));
	    // add the refresh action
	    actionBar.addAction(new RefreshAction());
	    // add the save action
	    actionBar.addAction(new SaveAction());
	    
	    // add a listener to the title
	    actionBar.setOnTitleClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            makeToast("Title clicked...");
	        }
	    });
    }
    
    // the refresh action
    private class RefreshAction implements Action {

        @Override
        public int getDrawable() {
            return R.drawable.ic_menu_refresh;
        }

		@Override
		public void performAction(View view) {
			makeToast("Refreshing...");
		}

    }
    
    // the save action
    private class SaveAction implements Action {

        @Override
        public int getDrawable() {
            return R.drawable.ic_menu_save;
        }

		@Override
		public void performAction(View view) {
			makeToast("Saving...");
		}

    }
    
    // a simple method to create an intent
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
    
    public void makeToast(String message) {
    	// with jam obviously
    	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
}