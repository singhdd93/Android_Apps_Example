package com.dds.sms;

import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

public class Activity1 extends Activity {
	
	String message;
	EditText text1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		
		Button sendSMS = (Button)findViewById(R.id.butSendSMS);
		text1 = (EditText)findViewById(R.id.editText1);
		
		sendSMS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				message = text1.getText().toString();
				sendsms("5556",message);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void sendsms(String phNo, String mesg)
	{
		SmsManager sms =SmsManager.getDefault();
		sms.sendTextMessage(phNo, null, mesg, null, null);
	}


}
