
package com.pixels.timelinepics;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.internal.widget.ActionBarView.HomeView;
import com.actionbarsherlock.view.Menu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pixels.timelinepics.R;


public class ImageGridActivity extends AbsListViewBaseActivity {
	
	SlidingMenu slidingMenu ;

	public String[] imageUrls, imageTitles;
	 int temp = 0;
	 int orien;
		
		int Measuredwidth = 0;  
		int Measuredheight = 0; 
		Point size ;
		WindowManager w;
		 static final String COLUMN_ID = "_id";
		 static final String COLUMN_ID_FETCHED = "fetched";
		 static final String COLUMN_PIC = "pic";
		 static final String COLUMN_TITLE = "title";
		 static final String COLUMN_CATEGORY = "category";
	private static String url = "http://fbtimelinepics.com/fetch2.php";
	EditText searchBar;
	ConnectionDetector cd;
	boolean isInternetPresent;

	JSONParser jParser = new JSONParser();
	private static final String TAG = "quotespics";
	private static final String TAG_PID = "id";
	private static final String TAG_TITLE = "posttitle";
	private static final String TAG_URL = "imgurl";
	private static final String TAG_CATEGORY = "cid";
	JSONArray messages = null;
	boolean searchbarVisible;
	DBA db;
	Cursor c;
	DisplayImageOptions options;

	
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
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		int index = listView.getFirstVisiblePosition();
		
		outState.putInt("INDEX", index);
		
	}
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 ImageLoader imageLoader = ImageLoader.getInstance();
		 searchBar = (EditText)findViewById(R.id.search);
		 searchBar.setVisibility(View.GONE);
		 searchbarVisible = false;
		 size = new Point();
			w = getWindowManager();
			orien = getScreenOrientation();

			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
			    w.getDefaultDisplay().getSize(size);
			    Measuredwidth = size.x;
			    Measuredheight = size.y; 
			}else{
			    Display d = w.getDefaultDisplay(); 
			    Measuredwidth = d.getWidth(); 
			    Measuredheight = d.getHeight(); 
			}
			
			
			
			
		 cd = new ConnectionDetector(getApplicationContext());
			isInternetPresent = cd.isConnectingToInternet();
		db = new DBA(this);
		
		db.open();
		c = db.getAllDistinct();
		int countTotal = c.getCount();
		imageTitles = new String [countTotal];
		imageUrls = new String [countTotal];
		if (c.moveToFirst())
		{
		do {
			String tit = c.getString(c.getColumnIndex(COLUMN_TITLE));
			String urL = c.getString(c.getColumnIndex(COLUMN_PIC));
			String cate = "http://fbtimelinepics.com/admin/Uploads/"+urL;
		   
		    imageTitles[temp] = tit;
		    imageUrls[temp] = cate;
		  
		    temp++;
		} while (c.moveToNext());
		}
		db.close();
		
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
        
       
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       
       
        if(slidingMenu.isMenuShowing() == false)
		{slidingMenu.toggle();
		
		}

		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();

		listView = (GridView) findViewById(R.id.gridview);
		((GridView) listView).setAdapter(new ImageAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		
		if (savedInstanceState != null) {
			int i = savedInstanceState.getInt("INDEX");
		
			listView.smoothScrollToPosition(i+1);
			if ( slidingMenu.isMenuShowing()) {
	            slidingMenu.toggle();
	        }
		}
		
		searchBar.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				temp =0 ;
	        	db.open();
	    		c = db.getSearchedMessagesAll(arg0.toString());
	    		int countT = c.getCount();
	    		imageTitles = new String [countT];
	    		imageUrls = new String [countT];
	    		if (c.moveToFirst())
	    		{
	    		do {
	    			String tit = c.getString(c.getColumnIndex(COLUMN_TITLE));
	    			String urL = c.getString(c.getColumnIndex(COLUMN_PIC));
	    			String cate = "http://fbtimelinepics.com/admin/Uploads/"+urL;
	    		    //Toast.makeText(getBaseContext(), mes, Toast.LENGTH_SHORT).show();
	    		    imageTitles[temp] = tit;
	    		    imageUrls[temp] = cate;
	    		   // Toast.makeText(getBaseContext(), arrayMessages[temp], Toast.LENGTH_SHORT).show();
	    		    temp++;
	    		} while (c.moveToNext());
	    		}
	    		db.close();
	    		((GridView) listView).setAdapter(new ImageAdapter());
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		if(isInternetPresent)
		{
			new LoadUpdate().execute();
		}
	}



	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra("IURL", imageUrls);
		intent.putExtra("ITITLE", imageTitles);
		intent.putExtra("IPOS", position);
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {

		

		private class ViewHolder {
			public TextView text;
			public ImageView image;
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        final ViewHolder holder;
	        if (convertView == null) {
	            view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
	            holder = new ViewHolder();
	            holder.text = (TextView) view.findViewById(R.id.imageNameDisplay);
	            holder.image = (ImageView) view.findViewById(R.id.image);
	            view.setTag(holder);
	        } else {
	            holder = (ViewHolder) view.getTag();
	        }
	       
	       
	        	RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams((Measuredwidth/2),(Measuredwidth/2));
				
				holder.image.setLayoutParams(parms);
	        	
	     
			

	        holder.text.setText(imageTitles[position]);

	        imageLoader.displayImage(imageUrls[position], holder.image, options);

	        return view;
	    }
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
		case R.id.search:
			if(searchbarVisible == false)
			{
        	searchBar.setVisibility(View.VISIBLE);
        	searchbarVisible = true;
        	return true;
			}
        	if(searchbarVisible == true)
        	{
        		searchBar.setVisibility(View.GONE);
            	searchbarVisible = false;
            	return true;
        	}
        	
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	class LoadUpdate extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			db.open();
			long max = 0;
			c = db.maxFetched_ID();
			if(c.moveToFirst())
			{
			String max_fetID = c.getString(0);
			Log.d("MAX CUR ID", "Max id : "+max_fetID);
			max = Long.parseLong(max_fetID);
			
			}
			db.close();
			//Log.d("All Messages: ", json.toString());

			try {

					
					messages = json.getJSONArray(TAG);


					db.open();
					for (int i = 0; i < messages.length(); i++) {
						JSONObject get = messages.getJSONObject(i);

						String id_str = get.getString(TAG_PID);
						String title = get.getString(TAG_TITLE);
						String url = get.getString(TAG_URL);
						String cat = get.getString(TAG_CATEGORY);
						
						long id = Long.parseLong(id_str);
						if(id > max)
						{	
									db.insert(id,title,url,cat);
						}
									
							} 
					db.close();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		protected void onPostExecute(String file_url) {

		
			
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					
					
				}
			});


		}

	}

}