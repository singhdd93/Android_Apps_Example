
package com.pixels.fbcoolcovers;


import java.net.URL;
import java.util.Arrays;
import java.util.List;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


@SuppressWarnings("deprecation")
public class ImagePagerActivity extends BaseActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	 
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");


    private final String PENDING_ACTION_BUNDLE_KEY = "com.pixels.fbcoolcovers.ImagePagerActivity:PendingAction";


    private LoginButton loginButton;
    private TextView greeting;
    private PendingAction pendingAction = PendingAction.NONE;
    private GraphUser user;

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
	   

	    
	int orien;
	ImageView iv;
	String b;
	int imageViewHeight =0;
	Bitmap imageToBeUploaded;
	int Measuredwidth = 0;  
	int Measuredheight = 0; 
	Point size ;
	WindowManager w;

	String[] imageUrls ;
	String[] imageTitles;

        byte[] bitmapdata ;
        ProgressDialog pDialog;
        int pOs;
        TextView tv;
        Button postTo;
        String urll;
	DisplayImageOptions options;

	ViewPager pager;
	
    
    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }


    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        updateUI();
    }
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        uiHelper.onPause();
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        uiHelper.onDestroy();
	    }

	    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	        if (pendingAction != PendingAction.NONE &&
	                (exception instanceof FacebookOperationCanceledException ||
	                exception instanceof FacebookAuthorizationException)) {
	                new AlertDialog.Builder(ImagePagerActivity.this)
	                    .setTitle(R.string.cancelled)
	                    .setMessage(R.string.permission_not_granted)
	                    .setPositiveButton(R.string.ok, null)
	                    .show();
	            pendingAction = PendingAction.NONE;
	        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
	            handlePendingAction();
	        }
	        updateUI();
	    }

	    private void updateUI() {
	        Session session = Session.getActiveSession();
	        boolean isSessionActive = (session != null && session.isOpened());

	        if (isSessionActive && user != null) {
	          
	            greeting.setText(getString(R.string.hello_user, user.getFirstName()));
	        } else {
	           
	            greeting.setText(null);
	        }
	    }

	    @SuppressWarnings("incomplete-switch")
	    private void handlePendingAction() {
	        PendingAction previouslyPendingAction = pendingAction;
	       
	        pendingAction = PendingAction.NONE;

	        switch (previouslyPendingAction) {
	            case POST_PHOTO:
	                postPhoto();
	                break;
	            
	        }
	    }

	    private interface GraphObjectWithId extends GraphObject {
	        String getId();
	    }

	    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
	        String title = null;
	        String alertMessage = null;
	        if (error == null) {
	            title = getString(R.string.success);
	            result.cast(GraphObjectWithId.class).getId();
	            alertMessage = "Cover Uploaded Sucessfully\nPlease go to Your Profile to Change Cover\nPress OK to goto your Profile";
	         
	            if(pDialog.isShowing())
	            {
	            pDialog.dismiss();
	            }
	        } else {
	        	if(pDialog.isShowing())
	            {
	            pDialog.dismiss();
	            }
	            title = getString(R.string.error);
	            alertMessage = error.getErrorMessage();
	        }

	        new AlertDialog.Builder(this)
	                .setTitle(title)
	                .setMessage(alertMessage)
	                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							try {
							    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile")));
							} catch (android.content.ActivityNotFoundException anfe) {
							    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/profile.php")));
							}
						}
					})
					.setNegativeButton("Cancel", null)
	                .show();
	    }
	    
	    
	    private void onClickPostPhoto() {
	        performPublish(PendingAction.POST_PHOTO);
	    }

	    private void postPhoto() {
	        if (hasPublishPermission()) {
	         
	            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), imageToBeUploaded,new Request.Callback() {
	                @Override
	                public void onCompleted(Response response) {
	                    showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
	                }
	            });
	            Bundle parameters = request.getParameters(); 
	            parameters.putString("message",b);
	            request.setParameters(parameters);
	            
	            request.executeAsync();
	        } else {
	            pendingAction = PendingAction.POST_PHOTO;
	        }
	    }
	    
	    
	    private boolean hasPublishPermission() {
	        Session session = Session.getActiveSession();
	        return session != null && session.getPermissions().contains("publish_actions");
	    }

	    private void performPublish(PendingAction action) {
	        Session session = Session.getActiveSession();
	        if (session != null) {
	            pendingAction = action;
	            if (hasPublishPermission()) {
	                handlePendingAction();
	            } else {
	                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
	            }
	        }
	    }

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

       
		setContentView(R.layout.image_pager);

		loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                ImagePagerActivity.this.user = user;
                updateUI();
                handlePendingAction();
            }
        });
        
        greeting = (TextView) findViewById(R.id.greeting);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		size = new Point();
		w = getWindowManager();

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
		    w.getDefaultDisplay().getSize(size);
		    Measuredwidth = size.x;
		    Measuredheight = size.y; 
		}else{
		    Display d = w.getDefaultDisplay(); 
		    Measuredwidth = d.getWidth(); 
		    Measuredheight = d.getHeight(); 
		}
		
		imageViewHeight = (int) (Measuredwidth * 0.37);
		
		orien = Util.getScreenOrientation(this);
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL || (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL ) 
		{    	
			if(orien == Configuration.ORIENTATION_LANDSCAPE)
			{
			 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
		}
        
        
		postTo = (Button)findViewById(R.id.post);
		Bundle bundle = getIntent().getExtras();
		final String[] imageUrls = bundle.getStringArray("IURL");
		final String[] imageTitles = bundle.getStringArray("ITITLE");
		int pagerPosition = bundle.getInt("IPOS", 0);

		 if (savedInstanceState != null) {
	            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
	            pendingAction = PendingAction.valueOf(name);
	            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
	        }

		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.resetViewBeforeLoading(true)
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(imageUrls,imageTitles));
		pager.setCurrentItem(pagerPosition);
		
		postTo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pOs = pager.getCurrentItem();
				String a = imageTitles[pOs];
				String uuurl = imageUrls[pOs];
				Log.d("URL", uuurl);
				urll = uuurl.replace("-500x185", "");
				b = a+" @ WWW.FBCOOLCOVERS.COM";
				Log.d("FinalUrl", urll);
				
				Session session = Session.getActiveSession();
				if(session.isOpened())
				{
				 new DownloadImageBackground().execute();
					
				 pDialog = new ProgressDialog(ImagePagerActivity.this);
					pDialog.setTitle("Uploading Cover");
					pDialog.setMessage("We are Uploading Cover for you\nPlease Wait");
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(false);
					pDialog.show();
				}
				else
				{
					AlertDialog.Builder warningIC = new AlertDialog.Builder(ImagePagerActivity
							.this);
					warningIC.setTitle("Please Login");
					warningIC.setMessage("Please Login to Upload Covers.");
					warningIC.setNegativeButton("Close", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
							dialog.cancel();
						}
					});
					
					warningIC.show();
				}
				
				
				
			}
		});
	}
	
	
	public class DownloadImageBackground extends AsyncTask<String, Void, Bitmap> {

		
		

		@Override
		protected Bitmap doInBackground(String...strings) {
		    
		    Log.i("doInBackground", "Loading image from: "+urll.toString());
		    return download_Image(urll);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		    imageToBeUploaded = result;
		    onClickPostPhoto();
		}

		private Bitmap download_Image(String url) {
		    try {
		        
		      Bitmap b =  BitmapFactory.decodeStream(new URL(url)
                .openConnection().getInputStream());
		       return b;
		    } catch (Exception e) {
		        Log.e("download_Image", "Caught exception: "+e.getMessage());
		        return null;
		    }
		}
		}
	
	
	

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private String[] titles;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images, String[] titles) {
			this.images = images;
			this.titles = titles;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			TextView textView = (TextView) imageLayout.findViewById(R.id.imageNameDisplay);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			if(orien == Configuration.ORIENTATION_PORTRAIT)
			{
				int w = Measuredwidth - 20;
				int h = (int) (w * 0.37);
				FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(w,h);
			parms.gravity = Gravity.CENTER;
			parms.rightMargin = 10;
			parms.leftMargin = 10;
			imageView.setLayoutParams(parms);
			}
			if(orien == Configuration.ORIENTATION_LANDSCAPE)
			{
				int w = Measuredwidth - 40;
				int h = (int) (w * 0.37);
				FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(w,h);
				parms.gravity = Gravity.TOP;
				int top = (int) getResources().getDimension(R.dimen.textDisplayHeight);
				parms.setMargins(20, top, 20, 0) ;
				imageView.setLayoutParams(parms);
			}
			
			textView.setText(titles[position]);
			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
	
	
	
}