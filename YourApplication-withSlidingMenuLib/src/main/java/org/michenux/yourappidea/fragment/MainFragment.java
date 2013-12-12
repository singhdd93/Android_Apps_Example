package org.michenux.yourappidea.fragment;

import org.michenux.android.listener.StartActivityOnClickListener;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.activity.AirportActivity;
import org.michenux.yourappidea.activity.FriendsActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View mainView = inflater
				.inflate(R.layout.main_fragment, container, false);
		Button oButton1 = (Button) mainView.findViewById(R.id.mainmenu_button1);
		oButton1.setOnClickListener( new StartActivityOnClickListener(this, FriendsActivity.class));
		
		Button oButton2 = (Button) mainView.findViewById(R.id.mainmenu_button2);
		oButton2.setOnClickListener( new StartActivityOnClickListener(this, AirportActivity.class));

		return mainView;
	}
}
