package com.dds.fbstatus;

import com.dds.fbstatus.PostingActivity.LoginDialogListener;
import com.dds.fbstatus.PostingActivity.PostMessage;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.*;

@SuppressLint("NewApi")
public class EditPost extends Activity {
	
	String message,category,messageToPost;
	private ProgressDialog pDialog;
	EditText editMessage;
	TextView catView;
	Button postToFB,sendAsSMS,share,like;
	boolean isInternetPresent;
	ConnectionDetector cd;
	private static final String APP_ID = "173088796201980";
	AdView adView;
	private static final String[] PERMISSIONS = new String[] {"publish_stream"};

	private static final String TOKEN = "access_token";
        private static final String EXPIRES = "expires_in";
        private static final String KEY = "facebook-credentials";

        
	private Facebook facebook;
	
	public void openWebURL( String inURL ) {
	    Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );

	    startActivity( browse );	
	}
	
	public boolean saveCredentials(Facebook facebook) {
    	Editor editor = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
    	editor.putString(TOKEN, facebook.getAccessToken());
    	editor.putLong(EXPIRES, facebook.getAccessExpires());
    	return editor.commit();
	}

	public boolean restoreCredentials(Facebook facebook) {
    	SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
    	facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
    	facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
    	return facebook.isSessionValid();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (adView != null) {
		      adView.destroy();
		    }
		    super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			finish();
			//Intent i = new Intent(EditPost.this, StatusList.class);
			//EditPost.this.startActivity(i);
		}
		else
		{
			finish();
			//Intent i = new Intent(EditPost.this, StatusList_OldAPI.class);
			//EditPost.this.startActivity(i);
		}
	}
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_post_activity);
	
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
	       
			
		}
		
		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);
		editMessage = (EditText)findViewById(R.id.messageDisplay_editText);
		catView = (TextView)findViewById(R.id.categoryDisplay_Post);
		share = (Button)findViewById(R.id.share);
		like = (Button)findViewById(R.id.likeus);
		sendAsSMS = (Button)findViewById(R.id.post_to_Messages);
		postToFB = (Button)findViewById(R.id.post_to_FB);
		
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		
		message = getIntent().getStringExtra("message");
		editMessage.setText(message);
		category = getIntent().getStringExtra("category");
		catView.setText(category);
		final AlertDialog.Builder warningIC = new AlertDialog.Builder(this);
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				messageToPost = editMessage.getText().toString();
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				  shareIntent.setType("text/plain");
				  shareIntent.putExtra(Intent.EXTRA_TEXT, messageToPost);
				  startActivity(Intent.createChooser(shareIntent, "Share..."));
				
			}
		});
		
		like.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				openWebURL("https://www.facebook.com/lookstatus");
			}
		});
		
		sendAsSMS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				messageToPost = editMessage.getText().toString();
				Intent i = new
						Intent(android.content.Intent.ACTION_VIEW);
						i.putExtra("address", "");
						i.putExtra("sms_body", messageToPost);
						i.setType("vnd.android-dir/mms-sms");
						startActivity(i);
			}
		});
		
		
postToFB.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				messageToPost = editMessage.getText().toString();
				
				if(isInternetPresent == false)
				{
					
					warningIC.setTitle("No Internet Connection");
					warningIC.setIcon(R.drawable.warning);
					warningIC.setMessage("Please Connect to Internet for Posting Status.");
					warningIC.setNegativeButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
				}
				else
				{
				

					if (! facebook.isSessionValid()) {
						loginAndPostToWall();
					}
					else {
						postToWall(messageToPost);
						}

				
				}
				
			}
		});
		
	}

	@SuppressWarnings("deprecation")
	public void loginAndPostToWall(){
  		 facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
  	}

   	public void postToWall(String message)
   	{
   	new PostMessage().execute(message);
   	}

  	class LoginDialogListener implements DialogListener {
  	    public void onComplete(Bundle values) {
  	    	saveCredentials(facebook);
  	    	if (messageToPost != null){
  			postToWall(messageToPost);
  		}
  	    }
  	    public void onFacebookError(FacebookError error) {
  	    	showToast("Authentication with Facebook failed!");
  	        //finish();
  	    }
  	    public void onError(DialogError error) {
  	    	showToast("Authentication with Facebook failed!");
  	        //finish();
  	    }
  	    public void onCancel() {
  	    	showToast("Authentication with Facebook cancelled!");
  	        //finish();
  	    }
  	}

  	private void showToast(String message){
  		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  	}
  	
  	class PostMessage extends AsyncTask<String, Process, Void> {

  		int check = 0;
  		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditPost.this);
			pDialog.setMessage("Posting to Facebook\nPlease wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

  		@Override
  		protected void onPostExecute(Void result) {
  		// TODO Auto-generated method stub
  		super.onPostExecute(result);
  		pDialog.dismiss();
  		if(check == 0)
  		{
  		showToast("Blank response.");
  		}
  		else if(check == 1)
  		{
  		showToast("Message posted to your facebook profile!");
  		finish();
  		Intent next = new Intent(EditPost.this, PostedSuccess.class);
  		startActivity(next);
  		}
  		else if(check == 2)
  		{
  		showToast("Failed to post to your profile!");
  		}
  		//finish();
  		}
  		@Override
  		protected Void doInBackground(String... params) {
  			// TODO Auto-generated method stub
  			String message = params[0];
  			Bundle parameters = new Bundle();
  			parameters.putString("message", message);
  			parameters.putString("description", "topic share");
  			try {
  			facebook.request("me");
  			String response = facebook.request("me/feed", parameters, "POST");
  			Log.d("Tests", "got response: " + response);
  			if (response == null || response.equals("") ||
  			response.equals("false")) {
  			check = 0;

  			}
  			else {
  			check = 1;

  			}
  			//finish();
  			} catch (Exception e) {
  			check = 2;

  			e.printStackTrace();

  			}
  			return null;
  		}

  	}
	
	
	}
