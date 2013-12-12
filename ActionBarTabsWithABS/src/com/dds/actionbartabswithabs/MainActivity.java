package com.dds.actionbartabswithabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends TabSwipeActivity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		addTab("t1", Frag1.class, Frag1.createBundle("This"));
		add
	}	
	
 
}