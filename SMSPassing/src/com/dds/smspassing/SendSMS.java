package com.dds.smspassing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SendSMS extends Activity{
	
	TextView smsView;
	Button sendSMS;
	String sms, phoneNumbers,listType;
	String smsList1 [],smsList2[],smsList3[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		smsList1=getResources().getStringArray(R.array.detailed_sms_list1);
		smsList2=getResources().getStringArray(R.array.detailed_sms_list2);
		smsList3=getResources().getStringArray(R.array.detailed_sms_list3);
		
		smsView =(TextView)findViewById(R.id.smsView);
		sendSMS = (Button)findViewById(R.id.butSendSMS);
		
		listType = getIntent().getStringExtra("listType");		
		int pos = getIntent().getExtras().getInt("postion");
		//int pos = Integer.parseInt(position);
		
		if(listType.equals("smsList1"))
		{
			sms = smsList1[pos];
		}
		else if (listType.equals("smsList2"))
		{
			sms = smsList2[pos];
		}
		else if (listType.equals("smsList3"))
		{
			sms = smsList3[pos];
		}
		
		smsView.setText(sms);
		
		sendSMS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new
						Intent(android.content.Intent.ACTION_VIEW);
						i.putExtra("address", "");
						i.putExtra("sms_body", sms);
						i.setType("vnd.android-dir/mms-sms");
						startActivity(i);
			}
		});
		
	}

}
