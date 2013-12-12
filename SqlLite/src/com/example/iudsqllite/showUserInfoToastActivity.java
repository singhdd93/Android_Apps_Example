package com.example.iudsqllite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class showUserInfoToastActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_user_info);
		
		DataBaseAdapter db = new DataBaseAdapter(this);
//===========================================================================================================
//---get all Records---
//===========================================================================================================
        db.open();
        Cursor c = db.getAllRecords();
        if (c.moveToFirst())
        {
            do 
            {          
            	Toast.makeText(this, 
                        "id: " + c.getString(0) + "\n" +
                        "First Name: " + c.getString(1) + "\n" +
                        "Last Name:  " + c.getString(2) + "\n" +
                        "Address: " + c.getString(3),
                        Toast.LENGTH_SHORT).show();        
            } while (c.moveToNext());
        }
        else
        {
            Toast.makeText(this, "No Assignments found", Toast.LENGTH_LONG).show();
        }
        db.close();
//===========================================================================================================
//---END get all Records---
//===========================================================================================================

//===========================================================================================================
//---get a Record---
//===========================================================================================================
//        db.open();
//        Cursor c = db.getRecord(2);
//        if (c.moveToFirst())    
//        {
//        	Toast.makeText(this, 
//                    "id: " + c.getString(0) + "\n" +
//                    "First Name: " + c.getString(1) + "\n" +
//                    "Last Name:  " + c.getString(2) + "\n" +
//                    "Address: " + c.getString(3),
//                    Toast.LENGTH_SHORT).show();   
//        }
//        else
//        {
//            Toast.makeText(this, "No such Assignments found", Toast.LENGTH_LONG).show();
//        }
//        db.close();
//===========================================================================================================
//---END get a Record---
//===========================================================================================================
  
//===========================================================================================================
//---update Record---
//===========================================================================================================
//        db.open();
//        if (db.updateRecord(1, "Anirban", "Jana", "23 M.S. Road,Kolkata"))
//            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
//        else
//            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();        
//        db.close();
//===========================================================================================================
//---END update Record---
//===========================================================================================================

//===========================================================================================================
//---delete a Record---
//===========================================================================================================
//        db.open();
//        if (db.deleteRecord(1))
//            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
//        else
//            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();            
//        db.close();
//===========================================================================================================
//---END delete a Record---
//===========================================================================================================

	}
	
	
}
