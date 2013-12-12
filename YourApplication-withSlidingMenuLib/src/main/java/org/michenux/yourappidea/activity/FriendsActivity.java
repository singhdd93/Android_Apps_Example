package org.michenux.yourappidea.activity;

import org.michenux.android.ui.fragment.MasterDetailFragmentHelper;
import org.michenux.android.ui.fragment.MasterDetailFragments;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.fragment.FriendDetailFragment;
import org.michenux.yourappidea.fragment.FriendListFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author Michenux
 *
 */
public class FriendsActivity extends FragmentActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.friends);
		
		FragmentManager fm = getSupportFragmentManager();
		MasterDetailFragments currentFragments = MasterDetailFragmentHelper
				.getCurrentFragments(R.id.friendmain_fragment,
						R.id.frienddetail_fragment, FriendDetailFragment.class,
						fm);
		if ( currentFragments.master == null ) {
			currentFragments.master = FriendListFragment.newInstance();
		}

		MasterDetailFragmentHelper.initFragments(currentFragments, R.id.friendmain_fragment, 
				R.id.frienddetail_fragment, getResources().getConfiguration(), fm);
	}
}
