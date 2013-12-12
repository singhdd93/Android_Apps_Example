package com.dds.rview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class F1 extends Activity {
	
	
	Button cr,de,ed,se,vi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f1);
		
		cr=(Button) findViewById(R.id.createF1);
		de=(Button) findViewById(R.id.deleteF1);
		ed=(Button) findViewById(R.id.editF1);
		se=(Button) findViewById(R.id.searchF1);
		vi=(Button) findViewById(R.id.viewF1);
		
		cr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent c=new Intent(F1.this, Create.class);
				F1.this.startActivity(c);
			}
		});
		
        de.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent d=new Intent(F1.this, Delete.class);
				F1.this.startActivity(d);
			}
		});
        
        
         ed.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent e=new Intent(F1.this, Edit.class);
				F1.this.startActivity(e);
			}
		});
         
         
         se.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				Intent s=new Intent(F1.this, Search.class);
 				F1.this.startActivity(s);
 			}
 		});
         
         vi.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				Intent v=new Intent(F1.this, View_rec.class);
 				F1.this.startActivity(v);
 			}
 		});
		
		
		
	}

}
