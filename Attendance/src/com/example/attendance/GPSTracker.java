package com.example.attendance;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {

	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	private Location Nlocation; // Nlocation
	private Location Glocation; // Glocation
	double Nlatitude  = 0; // latitude
	double Nlongitude = 0; // longitude
	double Glatitude  = 0; // latitude
	double Glongitude = 0; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		getLocation();
	}

	public void getLocation() {
		try {
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) 
			{
				// no network no GPS provider is enabled
			} 
			
			//location access enabled using GPS and Network both
			else 
			{
				this.canGetLocation = true;
				
				//getting lat/lng using Network
				if (isNetworkEnabled) 
				{
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					
					Log.d("Network", "Getting Lat Lng using Network");

					if (locationManager != null) 
					{
						for(int i = 0; i<=3; i++)
						{
							setNlocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
						}

						if (getNlocation() != null) 
						{
							Nlatitude = getNlocation().getLatitude();
							Nlongitude = getNlocation().getLongitude();
							
							setNlocation(Nlocation);
						}
					}
				}
				
				// getting lat/long using GPS
				if (isGPSEnabled) 
				{
					if (getGlocation() == null) 
					{
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						
						Log.d("GPS Enabled", "Getting Lat lng using GPS");
//						Toast.makeText(mContext, "Getting Lat lng using GPS" ,Toast.LENGTH_SHORT).show();
						
						if (locationManager != null) 
						{
							for(int i = 0; i<=3; i++)
							{
								setGlocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
							}
							
							if (getGlocation() != null) 
							{
								
								Glatitude = getGlocation().getLatitude();
								Glongitude = getGlocation().getLongitude();
								
								setGlocation(Glocation);
							}
						}
					}
				}
				//Stop using GPS
				stopUsingGPS();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app
	 * */
	public void stopUsingGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}		
	}
	
	/**
	 * Function to get GPSlatitude
	 * */
	public double getGPSLatitude(){
		if(getGlocation() != null){
			Glatitude = getGlocation().getLatitude();
		}
		return Glatitude;
	}
	
	/**
	 * Function to get GPSlongitude
	 * */
	public double getGPSLongitude(){
		if(getGlocation() != null){
			Glongitude = getGlocation().getLongitude();
		}
		return Glongitude;
	}
	
	/**
	 * Function to get Networklatitude
	 * */
	public double getNetworkLatitude(){
		if(getNlocation() != null){
			Nlatitude = getNlocation().getLatitude();
		}
		return Nlatitude;
	}
	
	/**
	 * Function to get Networklongitude
	 * */
	public double getNetworkLongitude(){
		if(getNlocation() != null){
			Nlongitude = getNlocation().getLongitude();
		}
		return Nlongitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}
	
	/**
	 * Function to show settings alert dialog
	 * On pressing Settings button will lauch Settings Options
	 * */
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
   	 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            	mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}





	Location getNlocation() {
		return Nlocation;
	}

	void setNlocation(Location nlocation) {
		Nlocation = nlocation;
	}

	Location getGlocation() {
		return Glocation;
	}

	void setGlocation(Location glocation) {
		Glocation = glocation;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
