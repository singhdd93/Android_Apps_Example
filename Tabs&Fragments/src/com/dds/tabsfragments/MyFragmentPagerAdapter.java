package com.dds.tabsfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	
	final int PAGE_COUNT = 3;
	
	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int arg0) {
		Bundle data = new Bundle();
		switch(arg0){
		
			case 0:
				Fragment1 fragments1 = new Fragment1();				
				data.putInt("current_page", arg0+1);
				fragments1.setArguments(data);
				return fragments1;
				
			case 1:
				Fragment2 fragments2 = new Fragment2();
				data.putInt("current_page", arg0+1);
				fragments2.setArguments(data);
				return fragments2;
				
			case 2:
				Fragment3 fragments3 = new Fragment3();
				data.putInt("current_page", arg0+1);
				fragments3.setArguments(data);
				return fragments3;
		}
		
		return null;
	}

	/** Returns the number of pages */
	@Override
	public int getCount() {		
		return PAGE_COUNT;
	}
	
}