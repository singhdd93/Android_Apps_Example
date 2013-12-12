package in.wptrafficanalyzer.actionbarsherlocknavtabswipe;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends SherlockFragmentActivity {
	ActionBar mActionBar;
	ViewPager mPager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /** Getting a reference to action bar of this activity */
        mActionBar = getSupportActionBar();
        
        /** Set tab navigation mode */
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        /** Getting a reference to ViewPager from the layout */
        mPager = (ViewPager) findViewById(R.id.pager);
        
        /** Getting a reference to FragmentManager */
        FragmentManager fm = getSupportFragmentManager();      
        
        /** Defining a listener for pageChange */
        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
        	@Override
        	public void onPageSelected(int position) {        		
        		super.onPageSelected(position);
        		mActionBar.setSelectedNavigationItem(position);        		
        	}
        	
        };
        
        /** Setting the pageChange listner to the viewPager */
        mPager.setOnPageChangeListener(pageChangeListener);
        
        /** Creating an instance of FragmentPagerAdapter */
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(fm);
        
        /** Setting the FragmentPagerAdapter object to the viewPager object */
        mPager.setAdapter(fragmentPagerAdapter);

        mActionBar.setDisplayShowTitleEnabled(true);
        
        /** Defining tab listener */
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mPager.setCurrentItem(tab.getPosition());
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		};

		/** Creating Android Tab */
        Tab tab = mActionBar.newTab()
                           .setText("Android")
                           .setIcon(R.drawable.android)
                           .setTabListener(tabListener);
        
        mActionBar.addTab(tab);

        /** Creating Apple Tab */
        tab = mActionBar.newTab()
                       .setText("Apple")
                       .setIcon(R.drawable.apple)
                       .setTabListener(tabListener);                               

        mActionBar.addTab(tab);        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}