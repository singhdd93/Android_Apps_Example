package org.michenux.yourappidea.activity;

import javax.inject.Inject;

import org.michenux.android.eula.Eula;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.YourApplication;
import org.michenux.yourappidea.airport.AirportFragment;
import org.michenux.yourappidea.controller.NavigationController;
import org.michenux.yourappidea.fragment.MainFragment;
import org.michenux.yourappidea.friends.FriendMainFragment;
import org.michenux.yourappidea.navdrawer.AbstractNavDrawerActivity;
import org.michenux.yourappidea.navdrawer.NavDrawerActivityConfiguration;
import org.michenux.yourappidea.navdrawer.NavDrawerAdapter;
import org.michenux.yourappidea.navdrawer.NavDrawerItem;
import org.michenux.yourappidea.navdrawer.NavMenuItem;
import org.michenux.yourappidea.navdrawer.NavMenuSection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class YourAppMainActivity extends AbstractNavDrawerActivity {
	
	@Inject NavigationController navController ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((YourApplication) getApplication()).inject(this);
		
		if ( savedInstanceState == null ) {
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
			Eula.show(this, R.string.eula_title, R.string.eula_accept, R.string.eula_refuse);
		}
	}
	
	@Override
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		
		NavDrawerItem[] menu = new NavDrawerItem[] {
				NavMenuSection.create( 100, "Demos"),
				NavMenuItem.create(101,"List/Detail", "navdrawer_friends", true, this),
				NavMenuItem.create(102, "Airport", "navdrawer_airport", true, this), 
				NavMenuSection.create(200, "General"),
				NavMenuItem.create(202, "Rate this app", "navdrawer_rating", false, this),
				NavMenuItem.create(203, "Eula", "navdrawer_eula", false, this), 
				NavMenuItem.create(204, "Quit", "navdrawer_quit", false, this)};
		
		NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
		navDrawerActivityConfiguration.setMainLayout(R.layout.main);
		navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
		navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
		navDrawerActivityConfiguration.setNavItems(menu);
		navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);		
		navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
		navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_close);
		navDrawerActivityConfiguration.setBaseAdapter(
			new NavDrawerAdapter(this, R.layout.navdrawer_item, menu ));
		return navDrawerActivityConfiguration;
	}
	
	@Override
	protected void onNavItemSelected(int id) {
		switch ((int)id) {
		case 101:
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FriendMainFragment()).commit();
			//NavigationController.getInstance().startFriendsActivity(this);
			break;
		case 102:
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AirportFragment()).commit();
			break;
		case 201:
			this.navController.showSettings(this);
			break;
		case 202:
			this.navController.startAppRating(this);
			break;
		case 203:
			this.navController.showEula(this);
			break;
		case 204:
			this.navController.showExitDialog(this);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RequestCodes.PREFERENCES_RESULTCODE) {
			Toast.makeText(this, "Back from preferences", Toast.LENGTH_SHORT)
					.show();

		}
	}
}