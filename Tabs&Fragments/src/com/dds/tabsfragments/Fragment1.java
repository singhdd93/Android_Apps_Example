package com.dds.tabsfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SlidingDrawer;

import com.actionbarsherlock.app.SherlockFragment;

public class Fragment1 extends SherlockFragment{
	
	String[] abc= { "A1", "A2", "A3", "A4"};
	ListView lv1;
	ArrayAdapter<String> ar;
	Button hsd;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


	        return inflater.inflate(R.layout.frag1, container, false);
	        
	        

	    }
	    
	    @SuppressWarnings("deprecation")
		@Override
	    public void onStart() {
	    	
	    	super.onStart();
	    	/** Setting the multiselect choice mode for the listview */
	    	lv1=(ListView) getActivity().findViewById(R.id.listView1);
	    	ar= new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, abc);
	    	lv1.setAdapter(ar);
	    	
	    	SlidingDrawer sd = (SlidingDrawer) getActivity().findViewById(R.id.slidingDrawer1);
	    	hsd =(Button)getActivity().findViewById(R.id.handle);
	    	
	    	sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
				
				@Override
				public void onDrawerClosed() {
					// TODO Auto-generated method stub
					
					hsd.setText("Open");
					
				}
			});
	    	sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
				
				@Override
				public void onDrawerOpened() {
					// TODO Auto-generated method stub
					hsd.setText("Close");
				}
			});

	    }
	    }
