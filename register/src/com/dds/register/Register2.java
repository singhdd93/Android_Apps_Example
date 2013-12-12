package com.dds.register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Register2 extends Activity {
	
	EditText nameGet,dobGet,phoneNoGet;
	//true for male false for female
	public static boolean sex;
	public static String name,dob,phnoneNo,picturePath;
	
	TextView setEmail;
	Button con;
	RadioButton male,female;
	ImageView iv;
	private static int RESULT_LOAD_IMAGE = 1;
	void compdet()
	{
		Toast.makeText(this, "Please fill your details", Toast.LENGTH_SHORT).show();
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register2);
		
		//Setting emailid
		setEmail=(TextView)findViewById(R.id.setEmail);
		String email=MainActivity.emailid;
		setEmail.setText(email);
		
		//Attach Textfields
		nameGet=(EditText)findViewById(R.id.name);
		dobGet=(EditText)findViewById(R.id.dateofb);
		phoneNoGet=(EditText)findViewById(R.id.phno);
		
		//Attach Button
		con=(Button)findViewById(R.id.cont);
		male=(RadioButton)findViewById(R.id.rbmale);
		female=(RadioButton)findViewById(R.id.rbfemale);
		
		//if(name.isEmpty()==false && dob.isEmpty()==false && phnoneNo.isEmpty()==false)
		//{
			//nameGet.setText(name);
			//dobGet.setText(dob);
			//phoneNoGet.setText(phnoneNo);
			//
//		}
		
		iv=(ImageView)findViewById(R.id.imageView1);
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
		});
		
		male.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(male.isChecked())
				{
					sex=true;
				}
				
			}
		});
		
		female.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(female.isChecked())
				{
					sex=false;
				}
				
			}
		}); 
		
		con.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name=nameGet.getText().toString();
				dob=dobGet.getText().toString();
				phnoneNo=phoneNoGet.getText().toString();
				
				if(name.isEmpty()==false && dob.isEmpty()==false && phnoneNo.isEmpty()==false)
				{
				Intent nex = new Intent(Register2.this, Register3.class);
				Register2.this.startActivity(nex);
				}
				else
				{
					compdet();
				}
			}
		});
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();
			
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		
		}
	}
}
