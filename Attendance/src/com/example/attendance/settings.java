package com.example.attendance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ica.database.DataBaseAdapter;

public class settings extends Activity {

	DataBaseAdapter db;
	EditText ip,port;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		db = new DataBaseAdapter(this);
		
		ip = (EditText)findViewById(R.id.editTextIP);
		port = (EditText)findViewById(R.id.editTextPort);
		
		//-------------------------------------------------------------------------------------------
		//set database value into textbox
		//-------------------------------------------------------------------------------------------
		
		try {
			db.open();
			Cursor c = db.getAllRecords();

			//If data exist into database
			if (c.moveToFirst())
			{
				do 
			    {          
			    	ip.setText(c.getString(1));
			    	port.setText(c.getString(2));
			   
			    } while (c.moveToNext());

			}
			db.close();
		} catch (Throwable e) {
			Toast.makeText(this, "Error while retriving data from local database:\n" + e, Toast.LENGTH_LONG).show();
		}
		//-------------------------------------------------------------------------------------------
		//END set database value into textbox  
		//-------------------------------------------------------------------------------------------

	}

	public void saveSetings(View v) {

		try {
			if(ip.getText().toString().length()<1 || port.getText().toString().length()<1)
			{
				Toast.makeText(this, "Please provide all information.", Toast.LENGTH_LONG).show();
			}
			else 
			{
				int countDots = fn_findcount(ip.getText().toString(),'.');
				
				if(countDots == 3 && ip.getText().toString().length()<=15 && port.getText().toString().length()<=4)
				{
			    //If data exist into database
				db.open();
				Cursor c = db.getAllRecords();
					
			    if (c.moveToFirst())
			    {
			    	//delete all records
			    	db.deleteAllRecord();
			    	
			    	//Insert to database
					long id = db.insertRecord(ip.getText().toString(), port.getText().toString());  
					
					//check response
					if(id>0)
					{
						ip.setText("");
						port.setText("");
						Toast.makeText(this,"Server settings added.", Toast.LENGTH_LONG).show();  
						
						finish();
						Intent i = new Intent(this, AttendanceLoginMainActivity.class);
						startActivity(i);
					}
					else
					{
						Toast.makeText(this,"Sorry setting not saved.", Toast.LENGTH_LONG).show();  
					}
			    }
			    //If no data into database
			    else
			    {
			    	//insert to database
					long id = db.insertRecord(ip.getText().toString(), port.getText().toString());  
					
					//check response
					if(id>0)
					{
//					ip.setText("");
//					port.setText("");
						Toast.makeText(this,"Server settings added.", Toast.LENGTH_LONG).show();  
						
//					do 
//		            {          
//		            	Toast.makeText(this, 
//		                        "id: " + c.getString(0) + "\n" +
//		                        "IP" + c.getString(1) + "\n" +
//		                        "Port" + c.getString(2) ,
//		                        Toast.LENGTH_SHORT).show();        
//		            } while (c.moveToNext());
					}
					else
					{
						Toast.makeText(this,"Sorry setting not saved.", Toast.LENGTH_LONG).show();  
					}
			    }
			    db.close();
			    
				}
				else
				{
					Toast.makeText(this, "Please provide valid information.", Toast.LENGTH_LONG).show();
				}
				
			}
		} catch (Throwable e) {
			Toast.makeText(this, "Error while saving: " + e, Toast.LENGTH_LONG).show();
		}
	}
	
	//count "."
	public int fn_findcount(String str_message, char  chr)
	{
		int icount = 0;
		for(int i = 0;i<str_message.length();i++)
		{
			if(str_message.charAt(i) == chr)
			{
				icount++; 
			} 
		}
		
		return icount;
	}

}
