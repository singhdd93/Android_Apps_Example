package com.example.attendance;

import com.ica.database.DataBaseAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AttendanceActivity extends Activity implements LocationListener{
	private final static String DEBUG_TAG = "MakePhotoActivity";
	private Camera camera;
	private int cameraId = 0;
	private LocationManager GpslocationManager;
	public static ProgressDialog pgLogin;
	String code;
	String pwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_activity);

		// do we have a camera?
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) 
		{
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
		} 
		else 
		{
			cameraId = findFrontFacingCamera();
			//cameraId = findBackFacingCamera();
			if (cameraId < 0) 
			{
				Toast.makeText(this, "Sorry you don't have secondary camera",Toast.LENGTH_LONG).show();
				//Exit from application
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} 
			else 
			{
				camera = Camera.open(cameraId);
			}
		}
	}

	public void in(View v) {
		String value = "I";
		hitServer(value);
	}
	
	public void out(View v) {
		String value = "O";
		hitServer(value);
	}
	
	public void absent(View v) {
		String value = "A";
		hitServer(value);
	}
	
	public void hitServer(String InOutAb) 
	{
		boolean isGPS;
		try {
			GpslocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			GpslocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,1.0f, this);
			isGPS = GpslocationManager.isProviderEnabled (LocationManager.GPS_PROVIDER);

			if(!isGPS)
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttendanceActivity.this);

				alertDialog.setTitle("Make your location accessible ...");
				alertDialog.setMessage("Your Location is not accessible.To give attendance you have to enable it.");
				alertDialog.setIcon(R.drawable.warning);

				alertDialog.setNegativeButton("Enable", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
					}
				});

				alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						Toast.makeText(getApplicationContext(), "Remember to give attandance you have to eanable it !", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

				alertDialog.show();
			}
			else 
			{
				pgLogin = new ProgressDialog(AttendanceActivity.this);
				pgLogin.setMessage("Please wait while giving attendance...");
				pgLogin.setIndeterminate(true);
				pgLogin.setCancelable(false);
				pgLogin.setCanceledOnTouchOutside(false);

				pgLogin.show();
				
				Bundle extras = getIntent().getExtras();
				if (extras == null) 
				{
					return;
				}
				code = extras.getString("code");
				pwd = extras.getString("pwd");
				
				
				DataBaseAdapter db = new DataBaseAdapter(this);
				db.open();
		        Cursor c = db.getAllRecords();
		        String serverPort = null;
		        //If data exist into database
		        if (c.moveToFirst())
		        {
					do 
		            {          
		            	serverPort = c.getString(1) + ":" + c.getString(2);
		            	Log.d("url", "http://" + serverPort + "/ServiceAttendance.asmx/saveAttendance");
		           
		            } while (c.moveToNext());

		        }
		        
				camera.takePicture(null, null,new PhotoHandler(getApplicationContext(), code, pwd, InOutAb, serverPort));
			}
		} 
		catch (Throwable e) 
		{
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
		}


	}

	private int findFrontFacingCamera() 
	{
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) 
		{
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) 
			{
				Log.d(DEBUG_TAG, "Front Facing Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}
	
	private int findBackFacingCamera() 
	{
		int cameraId = -1;
		// Search for the back facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) 
		{
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK) 
			{
				Log.d(DEBUG_TAG, "Back facing Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	protected void onDestroy() {
		if (camera != null) 
		{
			camera.release();
			camera = null;
		}
		
		if(GpslocationManager != null){
			GpslocationManager.removeUpdates(AttendanceActivity.this);
		}
		super.onDestroy();
	}
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
} 