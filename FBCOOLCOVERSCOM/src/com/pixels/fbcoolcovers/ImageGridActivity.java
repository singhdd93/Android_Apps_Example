
package com.pixels.fbcoolcovers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
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
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


public class ImageGridActivity extends BaseActivity {
	
	SlidingMenu slidingMenu ;

	AbsListView listView;
	public String[] imageUrls, imageTitles;
	 int temp = 0;
	 int orien;
	 int imageViewHeight =0;
		
		int Measuredwidth = 0;  
		int Measuredheight = 0; 
		Point size ;
		WindowManager w;
	
	EditText searchBar;
	ConnectionDetector cd;
	boolean isInternetPresent;

	JSONParser jParser = new JSONParser();
	
	JSONArray messages = null;
	boolean searchbarVisible;
	DBA db;
	Cursor c;
	DisplayImageOptions options;

	
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	
		int index = listView.getFirstVisiblePosition();
		String[] u = imageUrls;
		String[] t = imageTitles;
		outState.putStringArray("URL", u);
		outState.putStringArray("TITLE", t);
		
		outState.putInt("INDEX", index);
		
	}
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_grid);
		 searchBar = (EditText)findViewById(R.id.search);
		 listView = (GridView) findViewById(R.id.gridview);
		 searchBar.setVisibility(View.GONE);
		 searchbarVisible = false;
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 size = new Point();
			w = getWindowManager();
			orien = Util.getScreenOrientation(this);

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
			
			
		 cd = new ConnectionDetector(getApplicationContext());
			isInternetPresent = cd.isConnected();
		db = new DBA(this);
		
		
		
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
        
       
        
       
       
        if(slidingMenu.isMenuShowing() == false)
		{slidingMenu.toggle();
		
		}
        
        if (savedInstanceState != null) {
			int i = savedInstanceState.getInt("INDEX");
			imageTitles = savedInstanceState.getStringArray("TITLE");
			imageUrls = savedInstanceState.getStringArray("URL");
			listView.smoothScrollToPosition(i);
			if ( slidingMenu.isMenuShowing()) {
	            slidingMenu.toggle();
	        }
		}
		else
		{
			db.open();
			c = db.getAllDistinct();
			int countTotal = c.getCount();
			imageTitles = new String [countTotal];
			imageUrls = new String [countTotal];
			if (c.moveToFirst())
			{
			do {
				String tit = c.getString(c.getColumnIndex(Util.COLUMN_CTITLE));
				String urL = c.getString(c.getColumnIndex(Util.COLUMN_CLINK));
				String cate = Util.COMPELTE_URL1+urL;
			   
			    imageTitles[temp] = tit;
			    imageUrls[temp] = cate;
			  
			    temp++;
			} while (c.moveToNext());
			}
			db.close();
			
		}

		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();

		
		((GridView) listView).setAdapter(new ImageAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		
		
		
		searchBar.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			
				temp =0 ;
	        	db.open();
	    		c = db.getSearchedMessagesAll(arg0.toString());
	    		int countT = c.getCount();
	    		imageTitles = new String [countT];
	    		imageUrls = new String [countT];
	    		if (c.moveToFirst())
	    		{
	    		do {
	    			String tit = c.getString(c.getColumnIndex(Util.COLUMN_CTITLE));
	    			String urL = c.getString(c.getColumnIndex(Util.COLUMN_CLINK));
	    			String cate = Util.COMPELTE_URL1+urL;
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
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
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
	       
	        if(orien == Configuration.ORIENTATION_LANDSCAPE && (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
	        {
	        	RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams((Measuredwidth/2),(imageViewHeight/2));
				
				holder.image.setLayoutParams(parms);
	        	
	        }
	        else
	        {
				RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(Measuredwidth,imageViewHeight);
			
				holder.image.setLayoutParams(parms);
	        }
			

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

			JSONObject json = jParser.makeHttpRequest(Util.url, "POST", params);

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

					
					messages = json.getJSONArray(Util.TAG);


					db.open();
					for (int i = 0; i < messages.length(); i++) {
						JSONObject get = messages.getJSONObject(i);

						String id_str = get.getString(Util.TAG_PID);
						String title = get.getString(Util.TAG_TITLE);
						String url = get.getString(Util.TAG_URL);
						String cat = get.getString(Util.TAG_CATEGORY);
						
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