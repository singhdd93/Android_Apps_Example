package com.db.alternatinglistview;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class CarListAdapter extends SimpleAdapter {
	private List<Car> cars;
	/*
	 * Alternating color list -- you could initialize this from anywhere.
	 * Note that the colors make use of the alpha here, otherwise they would be
	 * opaque and wouldn't give good results!
	 */
	private int[] colors = new int[] { 0x30ffffff, 0x30ff2020, 0x30808080 };

	@SuppressWarnings("unchecked")
	public CarListAdapter(Context context, List<? extends Map<String, String>> cars, int resource, String[] from,
			int[] to) {
		super(context, cars, resource, from, to);
		this.cars = (List<Car>) cars;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		int colorPos = position % colors.length;
		view.setBackgroundColor(colors[colorPos]);

		return view;
	}

}
