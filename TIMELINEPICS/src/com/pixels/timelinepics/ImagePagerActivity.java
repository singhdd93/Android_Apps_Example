
package com.pixels.timelinepics;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.pixels.timelinepics.R;


@SuppressWarnings("deprecation")
public class ImagePagerActivity extends BaseActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	 
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	String fileDest = null;
    String fileName = null;
    Drawable loadedWallpaper = null;

    private final String PENDING_ACTION_BUNDLE_KEY = "com.pixels.timelinepics.ImagePagerActivity:PendingAction";


    private LoginButton loginButton;
    private TextView greeting;
    private PendingAction pendingAction = PendingAction.NONE;
    private GraphUser user;
    Button downloadAndSave, setWallpaperButton;

    ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public static final int DIALOG_DOWNLOAD_IMAGE = 1;
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
                case DIALOG_DOWNLOAD_PROGRESS:
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage("Setting Wallpaper");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    return mProgressDialog;
                case DIALOG_DOWNLOAD_IMAGE:
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage("Saving Image");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    return mProgressDialog;
            default:
                return null;
        }
    }
	

	public int getScreenOrientation()
    {
        Display getOrient = getWindowManager().getDefaultDisplay();
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
	        boolean enableButtons = (session != null && session.isOpened());

	       // postStatusUpdateButton.setEnabled(enableButtons);
	       //// postPhotoButton.setEnabled(enableButtons);
	       // pickFriendsButton.setEnabled(enableButtons);
	       // pickPlaceButton.setEnabled(enableButtons);

	        if (enableButtons && user != null) {
	           // profilePictureView.setProfileId(user.getId());
	            greeting.setText(getString(R.string.hello_user, user.getFirstName()));
	        } else {
	           // profilePictureView.setProfileId(null);
	            greeting.setText(null);
	        }
	    }

	    @SuppressWarnings("incomplete-switch")
	    private void handlePendingAction() {
	        PendingAction previouslyPendingAction = pendingAction;
	        // These actions may re-set pendingAction if they are still pending, but we assume they
	        // will succeed.
	        pendingAction = PendingAction.NONE;

	        switch (previouslyPendingAction) {
	            case POST_PHOTO:
	                postPhoto();
	                break;
	            //case POST_STATUS_UPDATE:
	             //   postStatusUpdate();
	              //  break;
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
	            String id = result.cast(GraphObjectWithId.class).getId();
	            alertMessage = "Photo Uploaded Sucessfully\nPlease go to Your Profile to Change Photo\nPress OK to goto your Profile";
	            //alertMessage = getString(R.string.successfully_posted_post, message, id);
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
							// TODO Auto-generated method stub
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
	           // Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.heart);
	            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), imageToBeUploaded,new Request.Callback() {
	                @Override
	                public void onCompleted(Response response) {
	                    showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
	                }
	            });
	            Bundle parameters = request.getParameters(); // <-- THIS IS IMPORTANT
	            parameters.putString("message",b);
	            // add more params here
	            request.setParameters(parameters);
	            
	            request.executeAsync();
	        } else {
	            pendingAction = PendingAction.POST_PHOTO;
	        }
	    }
	    
	    private void showAlert(String title, String message) {
	        new AlertDialog.Builder(this)
	                .setTitle(title)
	                .setMessage(message)
	                .setPositiveButton(R.string.ok, null)
	                .show();
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
	                // We can do the action right away.
	                handlePendingAction();
	            } else {
	                // We need to get new permissions, then complete the action when we get called back.
	                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
	            }
	        }
	    }

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

       
		setContentView(R.layout.ac_image_pager);

		loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                ImagePagerActivity.this.user = user;
                updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                handlePendingAction();
            }
        });
        
        downloadAndSave = (Button)findViewById(R.id.downloadSave);
        setWallpaperButton = (Button)findViewById(R.id.setToWallpaper);
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
		//Toast.makeText(this, "Width = "+Integer.toString(Measuredwidth), Toast.LENGTH_SHORT).show();
		orien = getScreenOrientation();
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL || (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL ) 
		{    	
			if(orien == Configuration.ORIENTATION_LANDSCAPE)
			{
			 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
		}
		File f = new File(helpers.getSvDir(getApplicationContext()));
        Log.i("TIMELINEPICS", "Check for external SD: " + f.getAbsolutePath());
        if (f.isDirectory() && f.exists()) {
        } else {
            f.mkdirs();
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
		
		downloadAndSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pOs = pager.getCurrentItem();
				String uuurl = imageUrls[pOs];
				Log.d("URL", uuurl);
				urll = uuurl.replace("Uploads", "fullimages");
				Log.d("FinalUrl", urll);
				new StoreFileAsync().execute(urll);
				//new DownloadFileAsync().execute(urll);
				
			}
		});
		
		setWallpaperButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pOs = pager.getCurrentItem();
				String uuurl = imageUrls[pOs];
				Log.d("URL", uuurl);
				urll = uuurl.replace("Uploads", "fullimages");
				Log.d("FinalUrl", urll);
				//new StoreFileAsync().execute(urll);
				new DownloadFileAsync().execute(urll);
			}
		});
		
		postTo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pOs = pager.getCurrentItem();
				String a = imageTitles[pOs];
				String uuurl = imageUrls[pOs];
				Log.d("URL", uuurl);
				urll = uuurl.replace("Uploads", "fullimages");
				b = a+" @ WWW.FBTIMELINEPICS.COM";
				Log.d("FinalUrl", urll);
				
				Session session = Session.getActiveSession();
				if(session.isOpened())
				{
				 new DownloadImageBackground().execute();
					
				 pDialog = new ProgressDialog(ImagePagerActivity.this);
					pDialog.setTitle("Uploading Photo");
					pDialog.setMessage("We are Uploading Photo for you\nPlease Wait");
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(false);
					pDialog.show();
				}
				else
				{
					AlertDialog.Builder warningIC = new AlertDialog.Builder(ImagePagerActivity
							.this);
					warningIC.setTitle("Please Login");
					//warningIC.setIcon(R.drawable.warning);
					warningIC.setMessage("Please Login to Upload Photos.");
					warningIC.setNegativeButton("Close", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					
					warningIC.show();
				}
				
				//Toast.makeText(getBaseContext(), a, Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	 class DownloadFileAsync extends AsyncTask<String, String, String> {

	        @SuppressWarnings("deprecation")
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            showDialog(DIALOG_DOWNLOAD_PROGRESS);
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

	        @Override
	        protected String doInBackground(String... aurl) {
	            WallpaperManager wallpaperManager = WallpaperManager
	                    .getInstance(getApplicationContext());
	            String url = aurl[0];
	            Bitmap abc = download_Image(url);
	            try {
	                wallpaperManager.setBitmap(abc);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            File file = new File(getDlDir() + fileName);
	            if (file.exists() == true) {
	                file.delete();
	            }
	            return null;
	        }

	        @Override
	        protected void onPostExecute(String result) {
	           mProgressDialog.cancel();
	            Toast.makeText(getApplicationContext(), "Wallpaper Set", Toast.LENGTH_LONG).show();
	        }

	        protected void onProgressUpdate(String... progress) {
	            Log.d("ANDRO_ASYNC", progress[0]);
	            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	        }

	    }

	    class StoreFileAsync extends AsyncTask<String, String, String> {

	        @SuppressWarnings("deprecation")
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            showDialog(DIALOG_DOWNLOAD_IMAGE);
	        }

	        @Override
	        protected String doInBackground(String... aurl) {
	            int count;

	            try {

	                URL url = new URL(aurl[0]);
	                URLConnection conexion = url.openConnection();
	                conexion.connect();

	                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	                Date now = new Date();
	                fileName = formatter.format(now)
	                        + aurl[0].substring(aurl[0].lastIndexOf("."), aurl[0].length());
	                // TODO better way of finding the extention

	                int lengthOfFile = conexion.getContentLength();
	                Log.d("ANDRO_ASYNC", "Lenght of file: " + lengthOfFile);

	                InputStream input = new BufferedInputStream(url.openStream());
	                OutputStream output = new FileOutputStream(getSvDir() + fileName);
	                fileDest = getSvDir() + fileName;

	                byte data[] = new byte[1024];

	                long total = 0;

	                while ((count = input.read(data)) != -1) {
	                    total += count;
	                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
	                    output.write(data, 0, count);
	                }

	                output.flush();
	                output.close();
	                input.close();
	            } catch (Exception e) {
	            }

	            return null;
	        }

	        protected void onProgressUpdate(String... progress) {
	            Log.d("ANDRO_ASYNC", progress[0]);
	            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	        }

	        @SuppressWarnings("deprecation")
	        @Override
	        protected void onPostExecute(String unused) {
	           dismissDialog(DIALOG_DOWNLOAD_IMAGE);
	            Toast.makeText(getApplicationContext(),
	                    "Saved :" + " " + getSvDir() + fileName,
	                    Toast.LENGTH_LONG).show();
	        }
	    }

	    String getDlDir() {
	        return helpers.getDlDir(getApplicationContext());
	    }

	    String getSvDir() {
	        return helpers.getSvDir(getApplicationContext());
	    }

	    
	public class DownloadImageBackground extends AsyncTask<String, Void, Bitmap> {

		
		

		@Override
		protected Bitmap doInBackground(String...strings) {
		    
		    Log.i("doInBackground", "Loading image from: "+urll.toString());
		    //TODO: Pass the correct URL to download_image
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

			/*if(orien == Configuration.ORIENTATION_PORTRAIT)
			{
				int w = Measuredwidth - 20;
				int h = (int) (w * 0.37);
				FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(w,w);
			parms.gravity = Gravity.CENTER;
			parms.rightMargin = 10;
			parms.leftMargin = 10;
			imageView.setLayoutParams(parms);
			}
			if(orien == Configuration.ORIENTATION_LANDSCAPE)
			{
				int w = Measuredwidth - 40;
				int h = (int) (w * 0.37);
				FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(h,h);
				parms.gravity = Gravity.CENTER_HORIZONTAL;
				int top = (int) getResources().getDimension(R.dimen.textDisplayHeight);
				parms.setMargins(20, top, 20, 0) ;
				imageView.setLayoutParams(parms);
			}*/
			
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