package com.dds.register;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




@SuppressLint("NewApi")
public class MainActivity extends Activity {

	public static String emailid;
	
	String password,rePassword;
	CheckBox termsConditions;
	EditText emailGet,passwordGet,rePasswordGet;
	Button registerBut;
	TextView pwdstrength;
	
	void pdm()
	{
		Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
	}
	void ptoos()
	{
		Toast.makeText(this, "Please Enter min 6 charcters", Toast.LENGTH_SHORT).show();
	}
	void emailempty()
	{
		Toast.makeText(this, "Please Enter Required Email ID", Toast.LENGTH_SHORT).show();
	}
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	//Attach text fields
	emailGet=(EditText)findViewById(R.id.emailEnter);
	passwordGet=(EditText)findViewById(R.id.passwordEnter);
	rePasswordGet=(EditText)findViewById(R.id.passwordREenter);
	
	pwdstrength=(TextView)findViewById(R.id.pwdstr);
	
	
passwordGet.addTextChangedListener(new TextWatcher() {
	
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		pwdstrength.setText("Password too short");
		String a=passwordGet.getText().toString();
		int temp=a.length();
		if(temp >=6 && temp<=8)
		{
			pwdstrength.setText("Weak");
		}
		else if(temp >=9)
		{
			pwdstrength.setText("Strong");
		}
		
	}
});
	
	//Attach Button
	registerBut=(Button)findViewById(R.id.registerBut);
	
	//Attach CheckBoxes
	termsConditions=(CheckBox)findViewById(R.id.cbTerms);
	
	//Code on CheckBox for terms & conditions
	termsConditions.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(termsConditions.isChecked())
			{
				registerBut.setEnabled(true);
			}
			else
			{
				registerBut.setEnabled(false);
			}
			
		}
	});
	
	
	//Code on Button Click
	registerBut.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			emailid=emailGet.getText().toString();
			password=passwordGet.getText().toString();
			rePassword=rePasswordGet.getText().toString();
			int len=password.length();
						
			if(emailid.isEmpty()==false)
			{
			if(len>=6)
			{
			if(password.equals(rePassword))
			{
				finish();
				Intent reg = new Intent(MainActivity.this, Register2.class);
				MainActivity.this.startActivity(reg);
				
				
			}
			else 
			{
				pdm();
			}
			}
			else
			{
				ptoos();
			}
			}
			else
			{
				emailempty();
			}
			
		}
	});
	

	
	
}



}
