package org.michenux.yourappidea.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import org.michenux.yourappidea.R;
import org.michenux.yourappidea.adapters.AirportAdapter;
import org.michenux.yourappidea.model.AirportRestResponse;
import org.michenux.yourappidea.model.Flight;
import org.michenux.yourappidea.model.FlightEtaComparator;
import org.michenux.yourappidea.rest.AirportRestService;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

// http://www.flightradar24.com/AirportInfoService.php?airport=ORY&type=in
// LFRS

public class AirportActivity extends ListActivity {

	private WeakReference<AirportAsyncTask> asyncTaskWeakRef;

	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AirportAdapter airportAdapter = new AirportAdapter(this, R.id.flight_name, new ArrayList<Flight>());
		setListAdapter(airportAdapter);
		
		this.asyncTaskWeakRef = (WeakReference<AirportAsyncTask>) getLastNonConfigurationInstance();
		this.setTitle(R.string.airport_title);
		
		if (isAsyncTaskPendingOrRunning()) {
			this.asyncTaskWeakRef.get().attach(this);
		} else {
			startNewAsyncTask();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.optionsMenu = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.airport_menu, menu);

		if (isAsyncTaskPendingOrRunning()) {
			setRefreshActionButtonState(true);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.airport_menuRefresh:

			if (!isAsyncTaskPendingOrRunning()) {
				this.startNewAsyncTask();
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu
					.findItem(R.id.airport_menuRefresh);
			if (refreshItem != null) {
				if (refreshing) {
					refreshItem
							.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
					refreshItem.setActionView(null);
				}
			}
		}
	}

	@Override
	@Deprecated
	public Object onRetainNonConfigurationInstance() {

		WeakReference<AirportAsyncTask> weakRef = null;

		if (this.isAsyncTaskPendingOrRunning()) {
			weakRef = this.asyncTaskWeakRef;
		}
		return weakRef;
	}

	private void startNewAsyncTask() {
		AirportAsyncTask oAsyncTaskArret = new AirportAsyncTask(this);
		this.asyncTaskWeakRef = new WeakReference<AirportAsyncTask>(
				oAsyncTaskArret);
		oAsyncTaskArret.execute();
	}

	private boolean isAsyncTaskPendingOrRunning() {
		return this.asyncTaskWeakRef != null
				&& this.asyncTaskWeakRef.get() != null
				&& !this.asyncTaskWeakRef.get().getStatus()
						.equals(Status.FINISHED);
	}

	private static class AirportAsyncTask extends AsyncTask<String, Void, AirportRestResponse> {

		private WeakReference<AirportActivity> activityWeakRef;

		private boolean error = false;

		private AirportAsyncTask(AirportActivity activity) {
			this.activityWeakRef = new WeakReference<AirportActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.activityWeakRef.get().setRefreshActionButtonState(true);
		}

		@Override
		protected AirportRestResponse doInBackground(String... params) {

			AirportRestResponse result = null;
			try {
				
				result = AirportRestService.getInstance().getAirportInfo("ORY", AirportRestService.IN_MODE, 
						this.activityWeakRef.get().getString(R.string.airport_rest_url));
				
			} catch (Exception e) {
				Log.e("LMI","Error retrieving data", e);
				this.error = true;
			}
			return result;
		}

		@Override
		protected void onPostExecute(AirportRestResponse airportResponse) {
			super.onPostExecute(airportResponse);

			if (this.activityWeakRef.get() != null) {

				AirportActivity activity = this.activityWeakRef.get();
				if (!this.error) {

					AirportAdapter adapter = (AirportAdapter) activity.getListAdapter();
					adapter.clear();
					Collections.sort(airportResponse.getFlights(),new FlightEtaComparator());
					adapter.addAll(airportResponse.getFlights());
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(this.activityWeakRef.get(),
							activity.getString(R.string.error_retrievingdata),
							Toast.LENGTH_SHORT).show();
				}
				activity.setRefreshActionButtonState(false);
			}
		}

		public void attach(AirportActivity activity) {
			this.activityWeakRef = new WeakReference<AirportActivity>(activity);
		}
	}
}
