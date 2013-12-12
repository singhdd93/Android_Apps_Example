package com.example.iudsqllite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainPageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
	}

	public void addUser(View v) {
		Intent i = new Intent(this, addUserInfoActivity.class);
		startActivity(i);
	}
	
	public void showUser(View v) {
		Intent i = new Intent(this, showUserInfoToastActivity.class);
		startActivity(i);
	}
	
	public void showList(View v) {
		Intent i = new Intent(this, showUserInfoListActivity.class);
		startActivity(i);
	}
	
	public void showSpiner(View v) {
		Intent i = new Intent(this, showUserInfoSpinerActivity.class);
		startActivity(i);
	}
}
