package com.sunil.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class MainActivity extends Activity implements OnClickListener{

	private static final String TAG="MainActivity";
	private Button btnexport=null;
	private Button btnimport=null;
	private Button btninsertdata=null;
	private Button btnsendmail=null;
	private EditText txtfirtsname;
	private EditText txtlastname;
	private EditText txtaddress;
	private EditText txtto;
	private EditText txtsubj;
	private EditText txttextmsg;
	private EditText txtemail;
	DatabaseHelper dbhelper=null;
	 File file=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.v(TAG, "onCreate called");
		txtfirtsname = (EditText)findViewById(R.id.editText_fname);
		txtlastname = (EditText)findViewById(R.id.editText_lname);
		txtaddress = (EditText)findViewById(R.id.editText_address);
		txtemail = (EditText)findViewById(R.id.editText_email);
		txtto = (EditText) findViewById(R.id.editText_to);
		txtsubj = (EditText) findViewById(R.id.editText_subj);
		txttextmsg = (EditText) findViewById(R.id.editText_text);
		
		btnexport=(Button)findViewById(R.id.button_export);
		btnexport.setOnClickListener(this);
		btninsertdata=(Button)findViewById(R.id.button_Insert);
		btninsertdata.setOnClickListener(this);
		btnsendmail=(Button) findViewById(R.id.button_sendmail);
		btnsendmail.setOnClickListener(this);
		btnimport=(Button)findViewById(R.id.button_import);
		btnimport.setOnClickListener(this);
		
	    dbhelper=new DatabaseHelper(getApplicationContext());
	}
	@Override
	public void onClick(View arg0) {
	
		Log.v(TAG, "onClick called");
		if(arg0==btnexport){
			
			ExportDatabaseCSVTask task=new ExportDatabaseCSVTask();
			task.execute();
		}
		
		else if(arg0==btnimport){
			

	        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
	        if (!exportDir.exists()) {
	            exportDir.mkdirs();
	        }

	        file = new File(exportDir, "PersonCSV.csv");
			  try {
				CSVReader reader = new CSVReader(new FileReader(file));
				String [] nextLine;
				try {
					while ((nextLine = reader.readNext()) != null) {
						
					    // nextLine[] is an array of values from the line
					   
					    String fname=nextLine[0];
					    String lname=nextLine[1];
					    String address=nextLine[2];
					    String email=nextLine[3];
					    
					    if(fname.equalsIgnoreCase("First Name"))
					    {
					    	
					    }
					    else
					    {
					    	int value=dbhelper.addPersonData(new Person(fname,lname,address,email));
					    	if(value==1)
					    	{
					    		Toast.makeText(getApplicationContext(), "Data inerted into table", Toast.LENGTH_LONG).show();
					    	}
					    }
					    
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (arg0==btninsertdata) {
			
			String firstname=txtfirtsname.getText().toString().trim();
			String lastname=txtlastname.getText().toString().trim();
			String addresss=txtaddress.getText().toString().trim();
			String email=txtemail.getText().toString().trim();
			
			if(firstname.length() < 1)
			{
				Toast.makeText(getApplicationContext(), "Please Enter First name", Toast.LENGTH_LONG).show();
			}
			else if (lastname.length() < 1) {
				Toast.makeText(getApplicationContext(), "Please Enter Last name", Toast.LENGTH_LONG).show();
			}
			else if (addresss.length() < 1) {
				Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
			}
			else if (email.length() < 1) {
				Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_LONG).show();
			}
			else {
				
				Person person=new Person(firstname, lastname, addresss, email);
				int status=dbhelper.addPersonData(person);
				if(status==1)
				{
					Toast.makeText(getApplicationContext(), "Data inserted successfully.", Toast.LENGTH_LONG).show();
					txtfirtsname.setText("");
					txtlastname.setText("");
					txtaddress.setText("");
					txtemail.setText("");
				}	
			}	
		}
		
		else if (arg0==btnsendmail) {
			
			String to=txtto.getText().toString().trim();
			String subj=txtsubj.getText().toString().trim();
			String msg=txttextmsg.getText().toString().trim();
			
			if(to.length() < 1)
			{
				Toast.makeText(getApplicationContext(), "Please Enter Recipent Email", Toast.LENGTH_LONG).show();
			}
			else if (subj.length() < 1) {
				Toast.makeText(getApplicationContext(), "Please Enter Subject", Toast.LENGTH_LONG).show();
			}
			else if (msg.length() < 1) {
				Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_LONG).show();
			}
			else {
				
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
			    emailIntent.setType("image/jpeg");
			    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{to}); 
			    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subj); 
			    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg); 
			    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
			    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}	
		}
	}
	
	private class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>{
	    private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
	    
	    @Override
	    protected void onPreExecute() {

	        this.dialog.setMessage("Exporting database...");
	        this.dialog.show();

	    }
	    protected Boolean doInBackground(final String... args){

	      File dbFile=getDatabasePath("person.db");
	      Log.v(TAG, "Db path is: "+dbFile);  //get the path of db
	  
	        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
	        if (!exportDir.exists()) {
	            exportDir.mkdirs();
	        }

	        file = new File(exportDir, "PersonCSV.csv");
	        try {
	        	
	            file.createNewFile();
	            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
	           
	            //ormlite core method	     
	            List<Person> listdata=dbhelper.GetDataPerson();
	            Person person=null;
	            
	            // this is the Column of the table and same for Header of CSV file
	            String arrStr1[] ={"First Name", "Last Name", "Address", "Email"};
           	    csvWrite.writeNext(arrStr1);
           	    
	            if(listdata.size() > 1)
	            {
	            	for(int index=0; index < listdata.size(); index++)
	            	{
		            	 person=listdata.get(index);
		            	 String arrStr[] ={person.getFirtname(), person.getLastname(), person.getAddress(), person.getEmail()};
		            	 csvWrite.writeNext(arrStr);
	            	}
	            }
	           // sqlite core query
	            
	            /* SQLiteDatabase db = DBob.getReadableDatabase();
	            //Cursor curCSV=mydb.rawQuery("select * from " + TableName_ans,null);
	            Cursor curCSV = db.rawQuery("SELECT * FROM table_ans12",null);
	            csvWrite.writeNext(curCSV.getColumnNames());

	           while(curCSV.moveToNext())  {

	                String arrStr[] ={curCSV.getString(0),curCSV.getString(1)};
	                curCSV.getString(2),curCSV.getString(3),curCSV.getString(4)
	                csvWrite.writeNext(arrStr);

	            }
           */
	            csvWrite.close();
	            return true;
	        }
	        catch (IOException e){
	            Log.e("MainActivity", e.getMessage(), e);
	            return false;
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(final Boolean success)	{

	        if (this.dialog.isShowing()){
	            this.dialog.dismiss();
	        }
	        if (success){
	            Toast.makeText(MainActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
	        }
	        else {
	            Toast.makeText(MainActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
}
