package com.dds.rview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Search extends Activity {
	
	Button next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		
		
		next=(Button) findViewById(R.id.nextsea);
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent nex=new Intent(Search.this, Search_display.class);
				Search.this.startActivity(nex);
				
			}
		});
	}

}
