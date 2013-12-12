package org.michenux.yourappidea.adapters;

import java.util.List;

import org.michenux.yourappidea.R;
import org.michenux.yourappidea.model.Flight;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AirportAdapter extends ArrayAdapter<Flight> {
	
	public AirportAdapter(Context context, int textViewResourceId,
			List<Flight> objects) {
		super(context, textViewResourceId, objects);
	}

	public View getView(int position, View view, ViewGroup viewGroup) {

		View updateView = null;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			updateView = inflater.inflate(R.layout.airport_listitem, null);
		} else {
			updateView = view;
		}

		Flight flight = (Flight) getItem(position);

		//Name
		TextView flightNameView = (TextView) updateView
				.findViewById(R.id.flight_name);
		flightNameView.setText(flight.getFlight());

		//From
		TextView flightFromView = (TextView) updateView
				.findViewById(R.id.flight_from);
		flightFromView.setText(flight.getName());
		
		// ETA
		TextView flightEtaView = (TextView) updateView
				.findViewById(R.id.flight_eta);
		java.text.DateFormat df = DateFormat.getDateFormat(this.getContext());
		java.text.DateFormat tf = DateFormat.getTimeFormat(this.getContext());
		flightEtaView.setText(this.getContext().getString(R.string.airport_eta, df.format(flight.getEta()) + " " + tf.format(flight.getEta())));
		
		// Type
		TextView flightTypeView = (TextView) updateView
				.findViewById(R.id.flight_type);
		flightTypeView.setText(flight.getType());
		
		//Speed
		TextView flightSpeedView = (TextView) updateView
				.findViewById(R.id.flight_speed);
		flightSpeedView.setText(this.getContext().getString(R.string.airport_speed, Double.toString( Math.round(flight.getSpeed() * 1.852 ))));

		//Alt
		TextView flightAltView = (TextView) updateView
				.findViewById(R.id.flight_alt);
		flightAltView.setText(this.getContext().getString(R.string.airport_altitude, Double.toString( Math.round(flight.getAltitude() / 3.2808))));
		
		return updateView;
	}
}
