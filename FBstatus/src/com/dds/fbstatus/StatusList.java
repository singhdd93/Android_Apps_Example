package com.dds.fbstatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;




@SuppressLint("NewApi")
public class StatusList extends Activity{

	
	String category_name;

	private ProgressDialog pDialog;
	ConnectionDetector cd;
	boolean isInternetPresent;
	boolean firstRun = false;
	String max_fetID;
	public long max_fetchedID;
	TextView displayCategoryOnMain;
	DBA db;
	Cursor c;
	DrawerLayout dl;
	ListView lv_menu;
	JSONParser jParser = new JSONParser();
	ListView lv ;
	ArrayList<HashMap<String, String>> messagesList;
	ActionBarDrawerToggle dt;

	private MenuItem menuItem;
	EditText search;


	Typeface myTypeface;
	private static String url = "http://www.facebookstatus4u.com/fetch.php";

	private static final String TAG = "fbstatus";
	private static final String TAG_PID = "id";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_CATEGORY = "category";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_ID_FETCHED = "fetched";
	private static final String COLUMN_STATUS = "message";
	private static final String COLUMN_CATEGORY = "category";

	MyCursorAdapter adap;

	JSONArray messages = null;
	
	private class MyCursorAdapter extends SimpleCursorAdapter{
		 
		  public MyCursorAdapter(Context context, int layout, Cursor c,
		    String[] from, int[] to, int flags) {
		   super(context, layout, c, from, to, flags);
		  } 
		 
		  @Override 
		  public View getView(int position, View convertView, ViewGroup parent) { 
		 
		   //get reference to the row
		   View view = super.getView(position, convertView, parent);
		   //check for odd or even to set alternate colors to the row background
		   if(position % 2 == 0){ 
			   view.setBackgroundResource(R.drawable.listbg1_03);
		   }
		   else {
		    view.setBackgroundResource(R.drawable.listbg2_06);
		   }
		   return view; 
		  } 
		 
		 
		 }  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	CreateMenu(menu);
	return true;
	}

	
	private void CreateMenu(Menu menu)
	{
	MenuItem mnu1 = menu.add(0, 0, 0, "Refresh");
	{
	mnu1.setIcon(R.drawable.refresh);
	mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	menu.add(0,1,1,"About");
	}
	private boolean MenuChoice(MenuItem item)
	{
	switch (item.getItemId()) {
	case 0:
		if(isInternetPresent == true)
		{
			if(firstRun == false)
				{
				menuItem = item;
				menuItem.setActionView(R.layout.progressbar);
				menuItem.expandActionView();
					new CheckUpdate().execute();
				}
		}
		else
		{
			AlertDialog.Builder warningIC = new AlertDialog.Builder(this);
			warningIC.setTitle("No Internet Connection");
			warningIC.setIcon(R.drawable.warning);
			warningIC.setMessage("Please Connect to Internet for Sycing Messages.");
			warningIC.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			warningIC.show();
		}
		break;
	case 1:
		Intent abt = new Intent(StatusList.this, About.class);
		
		StatusList.this.startActivity(abt);
		
	return true;
	}
	return false;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (dt.onOptionsItemSelected(item)) {
            return true;
        }
        else
        	{
        	return MenuChoice(item);
        	}
        
    }
	
	
	

	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        
        dt.syncState();
    }
	
	
	
	
	
	
	

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
     
        dt.onConfigurationChanged(newConfig);
    }
    
    
    private void printList()
    {
    	db.open();
		c = db.getAllDistinct();
		adap = new MyCursorAdapter(StatusList.this, R.layout.list_item, c, new String[] {COLUMN_ID, COLUMN_ID_FETCHED, COLUMN_STATUS}, new int[] { R.id.display_id_auto, R.id.display_id, R.id.display_message },0);
		adap.setFilterQueryProvider(new FilterQueryProvider() {
			
			@Override
			public Cursor runQuery(CharSequence constraint) {
				// TODO Auto-generated method stub
				db.open();
				Cursor cursor;
				if(category_name.equals("All"))
				{
				cursor = db.getSearchedMessagesAll(constraint.toString());
				}
				else
				{
				cursor = db.getSearchedMessages(category_name, constraint.toString());
				}
				db.close();
				return cursor;
			}
		});
		lv.setAdapter(adap);

		db.close();
    }
    
    private void printDrawer()
    {
    	db.open();
		c = db.getCategories();
		SimpleCursorAdapter ad = new SimpleCursorAdapter(this, R.layout.drawer_list_item, c, new String[] {COLUMN_CATEGORY, "COUNT("+COLUMN_STATUS+")"}, new int[] {R.id.drawer_display_category, R.id.drawer_display_category_count},0);
		ad.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				// TODO Auto-generated method stub
				if(columnIndex == 1)
				{
					final TextView tv = (TextView) view;
					tv.setTypeface(myTypeface);
				
				}
				String cAT = cursor.getString(1);
				if(columnIndex == 2)
				{
				if(cAT.equals("All"))
				{
					
					db.open();
					Cursor abc = db.getAllDistinct();
					final int cOn = abc.getCount();
					TextView t = (TextView)findViewById(R.id.drawer_display_category_count);
					t =(TextView) view;
					t.setText(Integer.toString(cOn));
					db.close();
					return true;
				}
				}
					return false;
				}
			
		});
		lv_menu.setAdapter(ad);
		lv_menu.setOnItemClickListener(new dcl());
		db.close();
    }

    
    
    
    
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myTypeface = Typeface.createFromAsset(getAssets(), "bebas.ttf");
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();

		category_name = "All";
		lv = (ListView)findViewById(R.id.listView_messgaes);
		displayCategoryOnMain = (TextView)findViewById(R.id.display_Category_main);
		
		search = (EditText)findViewById(R.id.search_bar);
		displayCategoryOnMain.setTypeface(myTypeface);
		
		dl = (DrawerLayout)findViewById(R.id.drawer1);
		lv_menu = (ListView)findViewById(R.id.list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        dt = new ActionBarDrawerToggle(
                this,
                dl,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
                ) {
            public void onDrawerClosed(View view) {
           
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                
                invalidateOptionsMenu();
            }
        };
        dl.setDrawerListener(dt);


        
		

		db = new DBA(getApplicationContext());
		db.open();
		
		db.close();
		
		
		
		db.open();
		c = db.getAll();
		int count = c.getCount();
		if(count == 0)
		{
			db.insert(0,"God will never leave you empty. He will replace every thing you lost. If he asks you to put something down, it's because he wants you to pick somthing up greater..." , "All");
		}
		db.close();
		if(count<=1){
		Log.d("JSON FETCH", "DID JSON FETCH");
		if(isInternetPresent == true)
		{
		new LoadAll().execute();
		}
		else
		{
			AlertDialog.Builder warningIC = new AlertDialog.Builder(this);
			warningIC.setTitle("No Internet Connection");
			warningIC.setIcon(R.drawable.warning);
			warningIC.setMessage("Please Connect to Internet for Sycing Messages.");
			warningIC.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			warningIC.show();
			printList();
		}
		}
		else{
		
		/**ListAdapter adapter = new SimpleAdapter(
				MainActivity.this, messagesList,
				R.layout.list_item, new String[] { TAG_ID,
						TAG_MESSAGE},
				new int[] { R.id.display_id, R.id.display_message });
				**/
		
		printList();
		
		}

		lv.setTextFilterEnabled(true);
		
		printDrawer();
		
		dl.openDrawer(lv_menu);
		
		search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				adap.getFilter().filter(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub\
				lv.setAdapter(adap);
				
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String postMessage = ((TextView) view.findViewById(R.id.display_message)).getText()
						.toString();
				String postID = ((TextView) view.findViewById(R.id.display_id)).getText().toString();
				
				String postCategory = displayCategoryOnMain.getText().toString();
						
				Intent post=new Intent(StatusList.this, PostingActivity.class);
				post.putExtra("m1", postMessage);
				post.putExtra("id", postID);
				post.putExtra("cat", postCategory);
				startActivity(post);

			}
		});

	}




	class LoadAll extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(StatusList.this);
			pDialog.setMessage("Intial Message Sync\nMay take few minutes.\nPlease wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			Log.d("All Messages: ", json.toString());

			try {

					
					messages = json.getJSONArray(TAG);


					for (int i = 0; i < messages.length(); i++) {
						JSONObject get = messages.getJSONObject(i);

						String id_str = get.getString(TAG_PID);
						String mess = get.getString(TAG_MESSAGE);
						String cat = get.getString(TAG_CATEGORY);
						
						long id = Long.parseLong(id_str);
						if(mess.equals("null") == false)
						{
							if(cat.equals("Blogroll") == false)
							{
									db.open();
									db.insert(id, mess, cat);
									db.close();
							}
						}
					
				} 
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		protected void onPostExecute(String file_url) {

			pDialog.dismiss();
			firstRun = true;
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					try{
						Thread.sleep(2000);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					printList();
					printDrawer();
					
				}
			});


		}

	}
	
	
	
	class CheckUpdate extends AsyncTask<String, String, String> {
		


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}


		protected String doInBackground(String... args) {
			
			
			db.open();
			c = db.getAll();
			int cn = c.getCount();
			if(cn != 0)
			{
			c=db.maxFetched_ID();
			if(c.moveToFirst())
			{
			max_fetID = c.getString(0);
			Log.d("MAX CUR ID", "Max id : "+max_fetID);
			max_fetchedID = Long.parseLong(max_fetID);
			
			}
			}
			
			db.close();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			//Log.d("All Messages: ", json.toString());
			Log.d("UPDATE CHECK", "Checking for Updates of Messages");
			
			try {

					
					messages = json.getJSONArray(TAG);


					for (int i = 0; i < messages.length(); i++) {
						JSONObject get = messages.getJSONObject(i);

						String id_str = get.getString(TAG_PID);
						String mess = get.getString(TAG_MESSAGE);
						String cat = get.getString(TAG_CATEGORY);
						
						long id = Long.parseLong(id_str);
						if(id > max_fetchedID)
						{
							if(mess.equals("null") == false)
							{
								if(cat.equals("Blogroll") == false)
								{
										Log.d("UPDATE CHECK", "Updates were found and Updating");
										db.open();
										db.insert(id, mess, cat);
										db.close();
								}
							}
						}
					
				} 
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			menuItem.collapseActionView();
			menuItem.setActionView(null);
			Toast.makeText(getBaseContext(), "New Status Messages Added", Toast.LENGTH_LONG).show();
			
			runOnUiThread(new Runnable() {
				public void run() {
					printDrawer();
				}
			});
			

		}

	}
	
	private class dcl implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           category_name = ((TextView) view.findViewById(R.id.drawer_display_category)).getText()
					.toString();
           String display_cat_name = category_name;
           displayCategoryOnMain.setText(display_cat_name);
           db.open();
           if(category_name.equals("All"))
           {
        	   c = db.getAllDistinct();
           }
           else
           {
           c = db.getMessagesOfACategory(category_name);
           }
           adap = new MyCursorAdapter(StatusList.this, R.layout.list_item, c, new String[] {COLUMN_ID, COLUMN_ID_FETCHED, COLUMN_STATUS}, new int[] { R.id.display_id_auto, R.id.display_id, R.id.display_message },0);
   		adap.setFilterQueryProvider(new FilterQueryProvider() {
			
			@Override
			public Cursor runQuery(CharSequence constraint) {
				// TODO Auto-generated method stub
				db.open();
				Cursor cursor;
				if(category_name.equals("All"))
				{
				cursor = db.getSearchedMessagesAll(constraint.toString());
				}
				else
				{
				cursor = db.getSearchedMessages(category_name, constraint.toString());
				}
				db.close();
				return cursor;
			}
		});
   		lv.setAdapter(adap);

   		db.close();
           dl.closeDrawer(lv_menu);
        }
    }
}
