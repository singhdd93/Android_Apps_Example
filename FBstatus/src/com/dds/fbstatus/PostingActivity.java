package com.dds.fbstatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.ads.*;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphUser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PostingActivity extends Activity{
	
	TextView messageViewer,messageCount_post;
	private ProgressDialog pDialog;
	private AdView adView;
	public String message, category, postid;
	long id;
	int curloc = 0;
	int countTotal,temp = 0;
	DBA db;
	Cursor c;
	boolean isInternetPresent;
	ConnectionDetector cd;
	String[] arrayMessages;
	private static final String APP_ID = "173088796201980";
	
	private static final String[] PERMISSIONS = new String[] {"publish_stream"};

	private static final String TOKEN = "access_token";
        private static final String EXPIRES = "expires_in";
        private static final String KEY = "facebook-credentials";

        
	private Facebook facebook;
	private String messageToPost;

	public void openWebURL( String inURL ) {
	    Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );

	    startActivity( browse );	
	}
	
	
	
	Button postToFB,sendAsSMS,next,previous,share,editAndPost,like;
	
	
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
			//Intent i = new Intent(PostingActivity.this, StatusList.class);
			//PostingActivity.this.startActivity(i);
		}
		else
		{
			finish();
			//Intent i = new Intent(PostingActivity.this, StatusList_OldAPI.class);
			//PostingActivity.this.startActivity(i);
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
		setContentView(R.layout.post_activity);
	
		//adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
		//LinearLayout adds = (LinearLayout)findViewById(R.id.adView);

		//adds.addView(adView);
		//adView.loadAd(new AdRequest());

		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
	       
			
		}
		
		db = new DBA(getApplicationContext());
		db.open();
		db.close();
		
		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);
		messageViewer = (TextView)findViewById(R.id.messageDisplay);
		messageCount_post = (TextView)findViewById(R.id.categoryDisplay_Post_Count);
		postToFB = (Button)findViewById(R.id.post_to_FB);
		sendAsSMS = (Button)findViewById(R.id.post_to_Messages);
		next = (Button)findViewById(R.id.next);
		previous = (Button)findViewById(R.id.previous);
		share = (Button)findViewById(R.id.share);
		like = (Button)findViewById(R.id.likeus);
		editAndPost = (Button)findViewById(R.id.edit);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		message = getIntent().getStringExtra("m1");
		postid = getIntent().getStringExtra("id");
		id = Long.parseLong(postid);
		category = getIntent().getStringExtra("cat");
		//Toast.makeText(this, Long.toString(id), Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, category , Toast.LENGTH_SHORT).show();
		TextView categoryDisplay = (TextView)findViewById(R.id.categoryDisplay_Post);
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "bebas.ttf");
		categoryDisplay.setTypeface(myTypeface);
		categoryDisplay.setText(category);
		
		
		final AlertDialog.Builder warningIC = new AlertDialog.Builder(this);
		messageViewer.setText(message);
		db.open();
		if(category.equals("All"))
		{
			c = db.getAllDistinct();
		}
		else
		{
		c = db.getMessagesOfACategory(category);
		}
		countTotal = c.getCount();
		arrayMessages = new String[countTotal];
		
		if (c.moveToFirst())
		{
		do {
			String mes = c.getString(2);
		    //Toast.makeText(getBaseContext(), mes, Toast.LENGTH_SHORT).show();
		    arrayMessages[temp] = mes;
		   // Toast.makeText(getBaseContext(), arrayMessages[temp], Toast.LENGTH_SHORT).show();
		    temp++;
		} while (c.moveToNext());
		}
		
		//Toast.makeText(getBaseContext(), arrayMessages[0], Toast.LENGTH_SHORT).show();
		
		db.close();
		

			curloc = Arrays.asList(arrayMessages).indexOf(message);
			messageCount_post.setText((curloc+1)+" / "+(countTotal) );
			
			//Toast.makeText(getBaseContext(), Integer.toString(curloc), Toast.LENGTH_SHORT).show();

			like.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					openWebURL("https://www.facebook.com/lookstatus");
				}
			});
			
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				messageToPost = messageViewer.getText().toString();
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				  shareIntent.setType("text/plain");
				  shareIntent.putExtra(Intent.EXTRA_TEXT, messageToPost);
				  startActivity(Intent.createChooser(shareIntent, "Share..."));
				
			}
		});
			
			
		sendAsSMS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				messageToPost = messageViewer.getText().toString();
				Intent i = new
						Intent(android.content.Intent.ACTION_VIEW);
						i.putExtra("address", "");
						i.putExtra("sms_body", messageToPost);
						i.setType("vnd.android-dir/mms-sms");
						startActivity(i);
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(curloc != (countTotal-1))
				{curloc++;
				
				}
				else
				{
					Toast.makeText(getBaseContext(), "No More Message", Toast.LENGTH_SHORT).show();
				}
				messageCount_post.setText((curloc+1)+" / "+(countTotal) );
				messageViewer.setText(arrayMessages[curloc]);
			}
		});
		
		editAndPost.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(PostingActivity.this, EditPost.class);
				String m = messageViewer.getText().toString();
				String c = category;
				i.putExtra("message", m);
				i.putExtra("category", c);
				startActivity(i);
				
			}
		});
		
		previous.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(curloc != 0)
				{
				curloc--;
				}
				else
				{
					Toast.makeText(getBaseContext(), "No More Message", Toast.LENGTH_SHORT).show();
				}
				messageCount_post.setText((curloc+1)+" / "+(countTotal) );
				messageViewer.setText(arrayMessages[curloc]);
			}
		});
		
		postToFB.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				messageToPost = messageViewer.getText().toString();
				
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
			pDialog = new ProgressDialog(PostingActivity.this);
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
  		Intent next = new Intent(PostingActivity.this, PostedSuccess.class);
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


