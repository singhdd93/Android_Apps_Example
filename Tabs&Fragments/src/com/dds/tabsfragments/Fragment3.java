package com.dds.tabsfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;

public class Fragment3 extends SherlockFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag3, container, false);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		WebView wv1 = (WebView) getActivity().findViewById(R.id.webView1);
		
		WebSettings ws =wv1.getSettings();
		ws.setBuiltInZoomControls(true);
		wv1.setWebViewClient(new Callback());
		wv1.loadUrl("http://www.google.com");
		
		
	}
	
	private class Callback extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(
		WebView view, String url) {
		return(false);
		}
	}

}
