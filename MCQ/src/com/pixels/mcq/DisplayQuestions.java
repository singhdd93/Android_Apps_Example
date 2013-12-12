package com.pixels.mcq;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DisplayQuestions extends Activity {
	
	int qNo = 1;
	int max;
	int correct = 0, incorrect = 0 ;
	DBA db;
	Cursor c;
	TextView quesTextView;
	RadioGroup ansRadioGroup;
	RadioButton op1RadioButton,op2RadioButton,op3RadioButton,op4RadioButton;
	private static final String COLUMN_QUESTION = "question";
	private static final String COLUMN_ANS = "ans";
	private static final String COLUMN_OP1 = "option1";
	private static final String COLUMN_OP2 = "option2";
	private static final String COLUMN_OP3 = "option3";
	private static final String COLUMN_OP4 = "option4";
	Button next;
	
	String quesString,op1String,op2String,op3String,op4String,ansString,ans_temp;
	
	private void setQuestion()
	{
		quesTextView.setText(quesString);
		op1RadioButton.setText(op1String);
		op2RadioButton.setText(op2String);
		op3RadioButton.setText(op3String);
		op4RadioButton.setText(op4String);
	}
	
	private void getQuestion()
	{
		c = db.getEntry(qNo);
		c.moveToFirst();
		quesString = c.getString(c.getColumnIndex(COLUMN_QUESTION));
		op1String = c.getString(c.getColumnIndex(COLUMN_OP1));
		op2String = c.getString(c.getColumnIndex(COLUMN_OP2));
		op3String = c.getString(c.getColumnIndex(COLUMN_OP3));
		op4String = c.getString(c.getColumnIndex(COLUMN_OP4));
		ansString = c.getString(c.getColumnIndex(COLUMN_ANS));
	}
	
	private void displayQuestion()
	{
		db.open();
		getQuestion();
		db.close();
		setQuestion();
	}
	
	private void resetRadioButtons()
	{
				
		op4RadioButton.setChecked(false);
		op1RadioButton.setChecked(false);
		op2RadioButton.setChecked(false);
		op3RadioButton.setChecked(false);
		
		
		op1RadioButton.setBackgroundColor(Color.TRANSPARENT);
		op2RadioButton.setBackgroundColor(Color.TRANSPARENT);
		op3RadioButton.setBackgroundColor(Color.TRANSPARENT);
		op4RadioButton.setBackgroundColor(Color.TRANSPARENT);
		
		op1RadioButton.setEnabled(true);
		op2RadioButton.setEnabled(true);
		op3RadioButton.setEnabled(true);
		op4RadioButton.setEnabled(true);
		
	}
	
	private void disableRadioButtons()
	{
		op1RadioButton.setEnabled(false);
		op2RadioButton.setEnabled(false);
		op3RadioButton.setEnabled(false);
		op4RadioButton.setEnabled(false);
	}
	
	private void correctAnswer()
	{
		String o1 = op1RadioButton.getText().toString();
		String o2 = op2RadioButton.getText().toString();
		String o3 = op3RadioButton.getText().toString();
		String o4 = op4RadioButton.getText().toString();
		
		if(ansString.equals(o1))
		{
			op1RadioButton.setBackgroundColor(Color.GREEN);
		}
		else if(ansString.equals(o2))
		{
			op2RadioButton.setBackgroundColor(Color.GREEN);
		}
		else if(ansString.equals(o3))
		{
			op3RadioButton.setBackgroundColor(Color.GREEN);
		}
		else if(ansString.equals(o4))
		{
			op4RadioButton.setBackgroundColor(Color.GREEN);
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ques_display_activity);
		
		quesTextView = (TextView)findViewById(R.id.question_display);
		ansRadioGroup  = (RadioGroup)findViewById(R.id.radioGroup_ans);
		op1RadioButton = (RadioButton)findViewById(R.id.op1_display);
		op2RadioButton = (RadioButton)findViewById(R.id.op2_display);
		op3RadioButton = (RadioButton)findViewById(R.id.op3_display);
		op4RadioButton = (RadioButton)findViewById(R.id.op4_display);
		next = (Button)findViewById(R.id.next_button);
		
		db = new DBA(this);
		db.open();
		c = db.getAll();
		max = c.getCount();
		db.close();
		correct = getIntent().getIntExtra("correct", 0);
		incorrect = getIntent().getIntExtra("incorrect", 0);
		qNo = getIntent().getIntExtra("val", 1);
		displayQuestion();
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(qNo < max)
				{
				qNo++;
				//ansRadioGroup.setEnabled(true);
				finish();
				Intent i = new Intent(DisplayQuestions.this, DisplayQuestions.class);
				i.putExtra("val", qNo);
				i.putExtra("correct", correct);
				i.putExtra("incorrect", incorrect);
				startActivity(i);
				}
				else
				{
					Intent i = new Intent(DisplayQuestions.this, Result.class);
					i.putExtra("correct", correct);
					i.putExtra("incorrect", incorrect);
					startActivity(i);
				}
				
				
			}
		});
		
		ansRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
			
				switch (arg1) {
				case R.id.op1_display:
					
					ans_temp = op1RadioButton.getText().toString();
					if(ans_temp.equals(ansString))
					{
						op1RadioButton.setBackgroundColor(Color.GREEN);
						correct++;
						disableRadioButtons();
					}
					else
					{
						op1RadioButton.setBackgroundColor(Color.RED);
						incorrect++;
						disableRadioButtons();
						correctAnswer();
					}
					break;
				case R.id.op2_display:
					
					ans_temp = op2RadioButton.getText().toString();
					if(ans_temp.equals(ansString))
					{
						op2RadioButton.setBackgroundColor(Color.GREEN);
						correct++;
						disableRadioButtons();
						
					}
					else
					{
						op2RadioButton.setBackgroundColor(Color.RED);
						incorrect++;
						disableRadioButtons();
						correctAnswer();
					}
					break;
				case R.id.op3_display:
					
					ans_temp = op3RadioButton.getText().toString();
					if(ans_temp.equals(ansString))
					{
						op3RadioButton.setBackgroundColor(Color.GREEN);
						correct++;
						disableRadioButtons();
					}
					else
					{
						op3RadioButton.setBackgroundColor(Color.RED);
						incorrect++;
						disableRadioButtons();
						correctAnswer();
					}
					break;
				case R.id.op4_display:
					
					ans_temp = op4RadioButton.getText().toString();
					if(ans_temp.equals(ansString))
					{
						op4RadioButton.setBackgroundColor(Color.GREEN);
						correct++;
						disableRadioButtons();
					}
					else
					{
						op4RadioButton.setBackgroundColor(Color.RED);
						incorrect++;
						disableRadioButtons();
						correctAnswer();
					}
					break;
					

				default:
					break;
				}
				
			}
		});
		
		
		
	}

}
