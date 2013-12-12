package com.dds.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Register3 extends Activity{
	
	TextView setEmail,setDetails;
	Button back,conf;
	String morf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register3);
		
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(BitmapFactory.decodeFile(Register2.picturePath));
		
		//Setting emailid
				setEmail=(TextView)findViewById(R.id.setEmailr3);
				String email=MainActivity.emailid;
				setEmail.setText(email);
				
				if(Register2.sex == true)
				{
					morf="Male";
				}
				if(Register2.sex==false)
				{
					morf="Female";
				}
				
		//Setting other details
				setDetails=(TextView)findViewById(R.id.detailsView);
				setDetails.setText("\n \nName : "+Register2.name+"\n \n");
				setDetails.append("DOB : "+Register2.dob+"\n \nPhone No : "+Register2.phnoneNo+"\n \nSex : "+morf);
				
				
				back=(Button)findViewById(R.id.back);
				back.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						Intent bac= new Intent(Register3.this, Register2.class);
						Register3.this.startActivity(bac);
						
					}
				});
				
				conf=(Button)findViewById(R.id.confirm);
				conf.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent confirm=new Intent(Register3.this, Register4.class);
						Register3.this.startActivity(confirm);
					}
				});
				
	}

}
