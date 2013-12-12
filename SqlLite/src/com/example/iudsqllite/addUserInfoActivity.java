package com.example.iudsqllite;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addUserInfoActivity extends Activity {

	DataBaseAdapter db = new DataBaseAdapter(this);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_info);
	}

	public void addUserInfo(View v)
	{
		Log.d("test", "adding");
		//get data from form
		EditText firstNameTxt = (EditText)findViewById(R.id.firstNameTxt);
		EditText lastNameTxt = (EditText)findViewById(R.id.lastNameTxt);
		EditText address = (EditText)findViewById(R.id.address);

		DataBaseAdapter db = new DataBaseAdapter(this);
      //===========================================================================================================
	  //---insert a Records---
	  //===========================================================================================================
		db.open();        
		long id = db.insertRecord(firstNameTxt.getText().toString(), lastNameTxt.getText().toString(), address.getText().toString());        
		db.close();
	  //===========================================================================================================
	  //---END insert a Records---
	  //===========================================================================================================

		if(id>0)
		{
			firstNameTxt.setText("");
			lastNameTxt.setText("");
			address.setText("");
			Toast.makeText(this,"User Added", Toast.LENGTH_LONG).show();  
		}
		else
		{
			Toast.makeText(this,"User adding unsuccessful.", Toast.LENGTH_LONG).show();  
		}

	}

}
