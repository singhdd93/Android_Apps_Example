package com.dds.smspassing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Row extends ArrayAdapter<String>{
	
	private final Context context;
	private final String[] values1;
	
	public Row(Context context, String[] values)
	{
		super(context, R.layout.sms_row, values);
		this.context = context;
		this.values1 = values;
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.sms_row, parent, false);
		
		TextView tv1 = (TextView)rowView.findViewById(R.id.textView_main);
		TextView tv2 = (TextView) rowView.findViewById(R.id.textView_sub);
		tv1.setText(values1[position]);
		
		if(position == 0)
		{
			tv2.setText("Funny SMS found Inside");
		}
		if(position == 1)
		{
			tv2.setText("Hilarious Jokes SMS Inside");
		}
		if(position == 2)
		{
			tv2.setText("Flirty SMS Inside");
		}
		
		return rowView;
	}

}
