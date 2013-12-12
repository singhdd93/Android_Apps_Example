package com.dds.dbapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button submit,show,delete,update;
	EditText name,email,phone,dob;
	DBA db;
	String nameIn,emailIn,dateOfBirthIn;
	long phoneNoIn;
	Cursor c ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
		LayoutInflater li = LayoutInflater.from(this);
		final View updateView = li.inflate(R.layout.update_dialog, null);
		
	    final EditText input = new EditText(this);

	    
		
		db = new DBA(this);
		db.open();
		db.close();
		submit = (Button)findViewById(R.id.submit_But);
		show = (Button)findViewById(R.id.show_But);
		name = (EditText)findViewById(R.id.name_input);
		email = (EditText)findViewById(R.id.email_input);
		phone = (EditText)findViewById(R.id.phoneNo_input);
		delete = (Button)findViewById(R.id.delete_But);
		update = (Button)findViewById(R.id.edit_But);
		dob = (EditText)findViewById(R.id.dob_input);
		
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				nameIn = name.getText().toString();
				emailIn = email.getText().toString();
				dateOfBirthIn = dob.getText().toString();
				phoneNoIn = Long.parseLong(phone.getText().toString());
				
				db.open();
				db.insert(nameIn, phoneNoIn, emailIn, dateOfBirthIn);
				db.close();
				
				name.setText("");
				email.setText("");
				dob.setText("");
				phone.setText("");
			}
		});
		
		show.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db.open();
				c = db.getAll();
				if (c.moveToFirst())
				{
				do {
				DisplayContact(c);
				} while (c.moveToNext());
				}
				db.close();
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db.open();
				c = db.getAll();
				int count = c.getCount();
				Toast.makeText(getBaseContext(), "Total No of Entries: "+Integer.toString(count), Toast.LENGTH_SHORT).show();
				
			    alert.setView(input);
			    alert.setTitle("Delete a Contact");			   
			    alert.setMessage("Enter the id of Entry \nTo know the id use Show Button");
			    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String value = input.getText().toString().trim();
			            long val= Long.parseLong(value);
			            if(db.delete(val))
			            {
			            	Toast.makeText(getBaseContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
			            	c = db.getAll();			            	
			            	int aftercount = c.getCount();
							Toast.makeText(getBaseContext(), "Total No of Entries: "+Integer.toString(aftercount), Toast.LENGTH_SHORT).show();
			            }
			            else
			            {
			            	Toast.makeText(getBaseContext(), "ERROR: Unable to Delete Contact", Toast.LENGTH_SHORT).show();
			            	c = db.getAll();
			            	int aftercount = c.getCount();
							Toast.makeText(getBaseContext(), "Total No of Entries: "+Integer.toString(aftercount), Toast.LENGTH_SHORT).show();
			            }
			            db.close();
			            
			        }
			    });

			    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            dialog.cancel();
			            db.close();
			        }
			    });
			    
			    
			    alert.show();
			}
		});
		
		update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alert.setView(input);
			    alert.setTitle("Delete a Contact");			   
			    alert.setMessage("Enter the id of Entry \nTo know the id use Show Button");
			    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String value = input.getText().toString().trim();
			            final long val= Long.parseLong(value);
			            db.open();
			            alert1.setTitle("Update Entry Info");
			            alert1.setMessage("Change the details as you want them to be");
			            alert1.setView(updateView);
			            final EditText newName = (EditText)updateView.findViewById(R.id.name_update);
			            final EditText newEmail = (EditText)updateView.findViewById(R.id.email_update);
			            final EditText newPhone = (EditText)updateView.findViewById(R.id.phoneNo_update);
			            final EditText newDOB = (EditText)updateView.findViewById(R.id.dob_update);
			            c= db.getEntry(val);
			            newName.setText(c.getString(1).toString());
			            newEmail.setText(c.getString(3));
			            newPhone.setText(c.getString(2));
			            newDOB.setText(c.getString(4));
			            db.close();
			            
			            alert1.setPositiveButton("Update", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								
								String nameInUpdate = newName.getText().toString();
								String emailInUpdate = newEmail.getText().toString();
								String dateOfBirthInUpdate = newDOB.getText().toString();
								Long phoneNoInUpdate = Long.parseLong(newPhone.getText().toString());
								db.open();
								db.update(val, nameInUpdate, phoneNoInUpdate, emailInUpdate, dateOfBirthInUpdate);
								db.close();
							}
						});
			            
			            alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								arg0.cancel();
								
							}
						});
			            
			            alert1.show();
			            
			        }
			    });

			    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            dialog.cancel();
			            db.close();
			        }
			    });
			    
			    
			    alert.show();
			}
		});
		
		
	}
	
	public void DisplayContact(Cursor c)
	{
	Toast.makeText(this,
	"id: " + c.getString(0) + "\n" +
	"Name: " + c.getString(1) + "\n" +
	"PhoneNo: " + c.getString(2) + "\n" + "Email: " + c.getString(3)+ "\n" + "DOB: " + c.getString(4),
	Toast.LENGTH_LONG).show();
	}
}



