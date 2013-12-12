package com.ddsformelements;

import java.security.PublicKey;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.CheckBox;
import android.widget.DigitalClock;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	CheckBox computer,mobile,tablet;
	TextView tv1,tv2;
	String com,mob,tab,english,hindi;
	RadioButton eng,hin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 computer=(CheckBox)findViewById(R.id.checkBox1);
		 mobile=(CheckBox)findViewById(R.id.checkBox2);
		 tablet=(CheckBox)findViewById(R.id.checkBox3);
		 tv1=(TextView)findViewById(R.id.tv2);
		 
		 tab=getResources().getString(R.string.tab);
		 com=getResources().getString(R.string.com);
		 mob=getResources().getString(R.string.mob);
		 english=getResources().getString(R.string.eng);
		 hindi=getResources().getString(R.string.hindi);
		 
		 eng=(RadioButton)findViewById(R.id.radioButton2);
		 hin=(RadioButton)findViewById(R.id.radioButton1);
		 
		 AnalogClock a=(AnalogClock)findViewById(R.id.analogClock1);
		 DigitalClock d=(DigitalClock)findViewById(R.id.digitalClock1);
				
	}
	public void onCBC(View v)
	{
		if(v.getId()==R.id.checkBox1)
		{if(computer.isChecked())
			tv1.append(com+" is On \n");
		else
			tv1.append(com+" is Off \n");
		}
		if(v.getId()==R.id.checkBox2)
		{if(mobile.isChecked())
			tv1.append(mob+" is On \n");
		else
			tv1.append(mob+" is Off \n");
		}
		if(v.getId()==R.id.checkBox3)
		{
			if(tablet.isChecked())
				tv1.append(tab+" is On \n");
			else
				tv1.append(tab+" is Off \n");
		}
	}
	
	public void onRBC(View v)
	{if(v.getId()==R.id.radioButton2)
	{
		if(eng.isChecked())
			Toast.makeText(this, "You have selected : "+english, Toast.LENGTH_SHORT).show();
	}
	if(v.getId()==R.id.radioButton1)
	{
		if(hin.isChecked())
			Toast.makeText(this, "You have selected : "+hindi, Toast.LENGTH_SHORT).show();
	}
		
	}
}
