package com.dds.rview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Search_display extends Activity {
	
	
	Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_dispaly);
		
		
		
		
		back=(Button) findViewById(R.id.backseadis);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				Intent bak=new Intent(Search_display.this, Search.class);
				Search_display.this.startActivity(bak);
				
			}
		});
	}

}
