package com.dd.simplecal;

import android.os.Bundle;
import android.app.Activity;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	void reset()
	{num1=0;
	num2=0;
	result=0;
	sym='a';
	}
	void scrollTobottom()
	{
		final Layout layout = t2.getLayout();
        if(layout != null){
            int scrollDelta = layout.getLineBottom(t2.getLineCount() - 1) 
                - t2.getScrollY() - t2.getHeight();
            if(scrollDelta > 0)
                t2.scrollBy(0, scrollDelta);
            
        }
	}
	
	Button but0,but00,but1,but2,but3,but4,but5,but6,but7,but8,but9,butadd,butminus,butmultiply,butresult,butAC,butdecimal,butdivide,butplusminus,butpercent,butdelete;
	TextView t1,t2;
	char sym;
	double num1,num2;
	double result;
	String t="."; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		but0=(Button) findViewById(R.id.b0);
		but00=(Button) findViewById(R.id.b00);
		but1=(Button) findViewById(R.id.b1);
		but2=(Button) findViewById(R.id.b2);
		but3=(Button) findViewById(R.id.b3);
		but4=(Button) findViewById(R.id.b4);
		but5=(Button) findViewById(R.id.b5);
		but6=(Button) findViewById(R.id.b6);
		but7=(Button) findViewById(R.id.b7);
		but8=(Button) findViewById(R.id.b8);
		but9=(Button) findViewById(R.id.b9);
		butadd=(Button) findViewById(R.id.badd);
		butminus=(Button) findViewById(R.id.bminus);
		butmultiply=(Button) findViewById(R.id.bmultiply);
		butresult=(Button) findViewById(R.id.bequal);
		butAC=(Button) findViewById(R.id.bAC);
		butdivide=(Button) findViewById(R.id.bdivide);
		butdecimal=(Button) findViewById(R.id.bdec);
		butpercent=(Button) findViewById(R.id.bpercent);
		butplusminus=(Button) findViewById(R.id.bpusminus);
		butdelete=(Button) findViewById(R.id.bdelete);
		t1=(TextView) findViewById(R.id.numbersview);
		t2=(TextView) findViewById(R.id.resultview);
		t2.setMovementMethod(new ScrollingMovementMethod());
		
		    
		butdelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String number=t1.getText().toString();
				if(number.isEmpty()==false)
				{
				int initialLength = number.length();
				String finalNumber = number.substring(0, initialLength-1);
				t1.setText(finalNumber);
			}}
		});    
		
		
		butAC.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reset();
				t1.setText("");
				t2.setText("");
				t2.scrollTo(0, 0);
				
			}
		});
		
		but0.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t1.append("0");
				//t2.append("0");
				
			}
		});
		but00.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t1.append("00");
				//t2.append("00");
			}
		});
		but1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t1.append("1");
				//t2.append("1");
			}
		});
		but2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t1.append("2");
				//t2.append("2");
			}
		});
		but3.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("3");
				//t2.append("3");
			}
		});

		but4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("4");
				//t2.append("4");
			}
		});

		but5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("5");
		//		t2.append("5");
			}
		});
		but6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("6");
			//	t2.append("6");
			}
		});
		but7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("7");
			//	t2.append("7");
			}
		});

		but8.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("8");
				//t2.append("8");
			}
		});
		
		but9.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 	TODO Auto-generated method stub
				t1.append("9");
		//		t2.append("9");
			}
		});
		
		butdecimal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				t1.append(".");
			//	t2.append(".");
				butdecimal.setClickable(false);
			}
		});
		
		butadd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String test=t1.getText().toString();
				
				if(test.isEmpty()== false)
				{
					if(test.equalsIgnoreCase(t)==false)
					{
			 String number1=t1.getText().toString();
				num1=Double.parseDouble(number1);
				//num1=Integer.parseInt((String) t1.getText());
				t1.setText("");
				t2.append(number1);
				t2.append(" + ");
				sym='+';
			butadd.setClickable(false);
			butminus.setClickable(false);
			butmultiply.setClickable(false);
			butdivide.setClickable(false);
			butpercent.setClickable(false);
			butdecimal.setClickable(true);
			}}
			}
		});
		
		butminus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String test=t1.getText().toString();
				if(test.isEmpty()== false)
				{
					if(test.equalsIgnoreCase(t)==false)
					{
				String number1=t1.getText().toString();
				num1=Double.parseDouble(number1);
				//num1=Integer.parseInt((String) t1.getText());
				t1.setText("");
				t2.append(number1);
				t2.append(" - ");
				sym='-';
				butadd.setClickable(false);
				butminus.setClickable(false);
				butmultiply.setClickable(false);
				butdivide.setClickable(false);
				butpercent.setClickable(false);
				butdecimal.setClickable(true);
				}}
				
			}
		});
		
		butmultiply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String test=t1.getText().toString();
				if(test.isEmpty()== false)
				{
					if(test.equalsIgnoreCase(t)==false)
					{
				String number1=t1.getText().toString();
				num1=Double.parseDouble(number1);
				//num1=Integer.parseInt((String) t1.getText());
				t1.setText("");
				t2.append(number1);
				t2.append(" X ");
				sym='*';
				butadd.setClickable(false);
				butminus.setClickable(false);
				butmultiply.setClickable(false);
				butdivide.setClickable(false);
				butpercent.setClickable(false);
				butdecimal.setClickable(true);
				}}
			}
		});
		
		butdivide.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String test=t1.getText().toString();
				if(test.isEmpty()==false)
				{
					if(test.equalsIgnoreCase(t)==false)
					{
					String number1=t1.getText().toString();
				num1=Double.parseDouble(number1);
				t1.setText("");
				t2.append(number1);
				t2.append(" / ");
				sym= '/';
				butadd.setClickable(false);
				butminus.setClickable(false);
				butmultiply.setClickable(false);
				butdivide.setClickable(false);
				butdecimal.setClickable(true);
				//butpercent.setClickable(false);
				}}
			}
		});
		
		butplusminus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String a=t1.getText().toString();
				if(a.isEmpty()==false)
				{
					if(a.equalsIgnoreCase(t)==false)
					{
					double temp=Double.parseDouble(a);
					long temp1=(long)temp;
					double decpart=(temp-temp1);
					if(decpart != 0)
					{
						double temp2=(0-temp);
						t1.setText(""+temp2);
					}
					else
					{
						long temp2=(0-temp1);
						t1.setText(""+temp2);
					}
					
					
					}	
				}
				
			}
		});
		
		butpercent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String test=t1.getText().toString();
				if(test.isEmpty()==false)
				{
					if(test.equalsIgnoreCase(t)==false)
					{
				if(sym == '/')
				{num2=Double.parseDouble(test);
					if(num2 != 0)
					{
						result = (num1/num2)*100;
					}
					else
					{
						result=0;
					}
					
				}
				else
				{
					result=0;
				}
				t2.append(test);
				t2.append(" = ");
				String z=Double.toString(result);
				t2.append(z+" %"+"\n");
				t1.setText("");
				reset();
				scrollTobottom();
				butadd.setClickable(true);
				butminus.setClickable(true);
				butmultiply.setClickable(true);
				butdivide.setClickable(true);
				butpercent.setClickable(true);
				butdecimal.setClickable(true);
				
				}
				}
				
			}
		});
		
		butresult.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//num2=Integer.parseInt((String) t1.getText());
				String test=t1.getText().toString();
				if(test.isEmpty()== false)
				{
					if(test.equalsIgnoreCase(t)==false)
					{
				String number2=t1.getText().toString();
				num2=Double.parseDouble(number2);
				
				switch (sym)
				{case'+':
					result=num1+num2;
					
					break;
				case'-':
					result=num1-num2;
					
					break;
				case'*':
					result=num1*num2;
					
					break;
				case'/':
					if(num2 == 0)
					{
						result=0;
					}
					else
						{result=(num1/num2);}
					
					break;
				default:
						result=num2;					
						break;
				} 
				t2.append(number2);
				t2.append(" = ");
				String z=Double.toString(result);
				t2.append(z+"\n");
				t1.setText("");
				reset();
				scrollTobottom();
				butadd.setClickable(true);
				butminus.setClickable(true);
				butmultiply.setClickable(true);
				butdivide.setClickable(true);
				butpercent.setClickable(true);
				butdecimal.setClickable(true);
				}}
			}
		});
		
		
		
	}

}
