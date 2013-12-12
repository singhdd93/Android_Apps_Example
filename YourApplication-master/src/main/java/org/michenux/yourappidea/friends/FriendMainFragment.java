package org.michenux.yourappidea.friends;

import org.michenux.android.ui.fragment.MasterDetailFragmentHelper;
import org.michenux.android.ui.fragment.MasterDetailFragments;
import org.michenux.yourappidea.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendMainFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(false);
		
		if  ( savedInstanceState == null ) {
			FragmentManager fm = getChildFragmentManager();
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.friends, container, false);
	}
}
