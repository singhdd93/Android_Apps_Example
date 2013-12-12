
package com.pixels.fbcoolcovers;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends SherlockFragmentActivity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();


	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
			return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
			return super.onOptionsItemSelected(item);
	}

	
}
