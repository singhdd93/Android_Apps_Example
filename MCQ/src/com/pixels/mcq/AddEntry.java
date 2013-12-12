package com.pixels.mcq;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEntry extends Activity{
	
	Button add;
	EditText ques_EditText,op1_EditText,op2_EditText,op3_EditText,op4_EditText,ans_EditText;
	String ques_String,op1_String,op2_String,op3_String,op4_String,ans_String,ans_final;
	DBA db;
	
	private void clearFields()
	{
		ques_EditText.setText("");
		op1_EditText.setText("");
		op2_EditText.setText("");
		op3_EditText.setText("");
		op4_EditText.setText("");
		ans_EditText.setText("");
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_entry_activity);
		db = new DBA(this);
		db.open();
		db.close();
		ques_EditText = (EditText)findViewById(R.id.question_field);
		op1_EditText = (EditText)findViewById(R.id.op1_feild);
		op2_EditText = (EditText)findViewById(R.id.op2_feild);
		op3_EditText = (EditText)findViewById(R.id.op3_feild);
		op4_EditText = (EditText)findViewById(R.id.op4_feild);
		ans_EditText = (EditText)findViewById(R.id.ans_field);
		add = (Button)findViewById(R.id.add_button);
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ques_String = ques_EditText.getText().toString();
				op1_String = op1_EditText.getText().toString();
				op2_String = op2_EditText.getText().toString();
				op3_String = op3_EditText.getText().toString();
				op4_String = op4_EditText.getText().toString();
				ans_String = ans_EditText.getText().toString();
				if(ans_String.equals("1"))
				{
					ans_final = op1_String;
				}
				else if(ans_String.equals("2"))
				{
					ans_final = op2_String;
				}
				else if(ans_String.equals("3"))
				{
					ans_final = op3_String;
				}
				else if(ans_String.equals("4"))
				{
					ans_final = op4_String;
				}
				else
				{
					ans_final = "0";
					Toast.makeText(getBaseContext(), "Enter proper Option Number", Toast.LENGTH_SHORT).show();
				}
				
				if(! ques_String.isEmpty() && ! op1_String.isEmpty() && ! op2_String.isEmpty() && ! op3_String.isEmpty() && ! op4_String.isEmpty() && ! ans_final.equals("0"))
				{
					db.open();
					db.insert(ques_String, op1_String, op2_String, op3_String, op4_String, ans_final);
					Toast.makeText(getBaseContext(), "Instered into Table", Toast.LENGTH_SHORT).show();
					db.close();
					clearFields();
				}
				else
				{
					Toast.makeText(getBaseContext(), "Values not Inserted\nThere's a problem somewhere", Toast.LENGTH_SHORT);
				}
				
			}
		});
		
	}

}
