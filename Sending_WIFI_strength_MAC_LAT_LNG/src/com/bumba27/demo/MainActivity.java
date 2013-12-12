package com.bumba27.demo;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Context context = MainActivity.this;

		//This intent for AlarmManager
		Intent myIntent = new Intent(MainActivity.this, SendData.class);
		pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);

		//For version control because Switch button implemented from ICS
		Switch switchOnOffBtn = (Switch) findViewById(R.id.switchBtn);
		if (switchOnOffBtn != null) 
		{
			switchOnOffBtn.setOnCheckedChangeListener(this);
			
			//Checking last Position of switch
			if(getFromPreference("switchLastPos").equalsIgnoreCase("ON"))
			{
				switchOnOffBtn.setChecked(true);
			}
			else if(getFromPreference("switchLastPos").equalsIgnoreCase("OFF"))
			{
				switchOnOffBtn.setChecked(false);
			}
		}
	}

	//======================================================================================================
	//Function for switch button
	//======================================================================================================
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if(isChecked)
		{
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 10);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*5, pendingIntent);

			Toast.makeText(MainActivity.this, "Service Started", Toast.LENGTH_SHORT).show();
			saveInPreference("switchLastPos", "ON");
		}
		else
		{
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);

			Toast.makeText(this, "Service is OFF",Toast.LENGTH_SHORT).show();
			saveInPreference("switchLastPos", "OFF");
		}
	}
	//======================================================================================================
	//Function for switch button
	//======================================================================================================
	
	
	
	//===================================================================================================================================
	//Preference variable
	//===================================================================================================================================

	//--------------------------------------------
	// method to save variable in preference
	//--------------------------------------------
	public void saveInPreference(String name, String content) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name, content);
		editor.commit();
	}

	//--------------------------------------------
	// getting content from preferences
	//--------------------------------------------
	public String getFromPreference(String variable_name) {
		String preference_return;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		preference_return = preferences.getString(variable_name, "");

		return preference_return;
	}


	//===================================================================================================================================
	//Preference variable
	//===================================================================================================================================

}