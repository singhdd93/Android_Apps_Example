package com.dds.alphabets;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button butA,butB,butC,butD,butE,butF,butG,butH,butI,butJ,butK,butL,butM,butN,butO,butP,butQ,butR,butS,butT,butU,butV,butW,butX,butY,butZ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		butA=(Button)findViewById(R.id.button1);
		butB=(Button)findViewById(R.id.button2);
		butC=(Button)findViewById(R.id.button3);
		butD=(Button)findViewById(R.id.button4);
		butE=(Button)findViewById(R.id.button5);
		butF=(Button)findViewById(R.id.button6);
		butG=(Button)findViewById(R.id.button7);
		butH=(Button)findViewById(R.id.button8);
		butI=(Button)findViewById(R.id.button9);
		butJ=(Button)findViewById(R.id.button10);
		butK=(Button)findViewById(R.id.button11);
		butL=(Button)findViewById(R.id.button12);
		butM=(Button)findViewById(R.id.button13);
		butN=(Button)findViewById(R.id.button14);
		butO=(Button)findViewById(R.id.button15);
		butP=(Button)findViewById(R.id.button16);
		butQ=(Button)findViewById(R.id.button17);
		butR=(Button)findViewById(R.id.button18);
		butS=(Button)findViewById(R.id.button19);
		butT=(Button)findViewById(R.id.button20);
		butU=(Button)findViewById(R.id.button21);
		butV=(Button)findViewById(R.id.button22);
		butW=(Button)findViewById(R.id.button23);
		butX=(Button)findViewById(R.id.button24);
		butY=(Button)findViewById(R.id.button25);
		butZ=(Button)findViewById(R.id.button26);
		
		
		butA.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "A");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "B");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butC.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "C");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butD.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "D");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butE.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "E");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butF.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "F");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butG.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "G");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butH.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "H");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		
		butI.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "I");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butJ.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "J");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "K");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		
		butL.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "L");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butM.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "M");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		
		butN.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "N");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butO.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "O");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "P");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butQ.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "Q");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butR.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "R");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "S");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "T");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butU.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "U");
				MainActivity.this.startActivity(nex);
				
			}
		});
		butV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "V");
				MainActivity.this.startActivity(nex);
				
			}
		});
		butW.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "W");
				MainActivity.this.startActivity(nex);
				
			}
		});
		butX.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "X");
				MainActivity.this.startActivity(nex);
				
			}
		});
		butY.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "Y");
				MainActivity.this.startActivity(nex);
				
			}
		});
		
		butZ.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nex= new Intent(MainActivity.this, DisplayAlphabets.class);
				nex.putExtra("stval", "Z");
				MainActivity.this.startActivity(nex);
				
			}
		});
	}

}
