package com.dds.actionbartabswithabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag1 extends Fragment{
	
	public static final String EXTRA_TITLE = "title";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView txt = new TextView( inflater.getContext() );
        txt.setGravity( Gravity.CENTER );
        txt.setText( "Fragment" );
 
        if ( getArguments() != null && getArguments().containsKey( EXTRA_TITLE ) ) {
            txt.setText( getArguments().getString( EXTRA_TITLE ) );
        }
        return txt;
        
	}
	
	public static Bundle createBundle( String title ) {
        Bundle bundle = new Bundle();
        bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }
}