package com.pixels.wallpapers;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Picker extends Activity {
	
	int recievedID;
	ImageView imageView;
	Button setWallpaper;
	InputStream ims;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picker_activity);
		
		imageView = (ImageView)findViewById(R.id.displayFinalImage);
		
		recievedID = getIntent().getIntExtra("id", 1);
		
		Toast.makeText(this, Integer.toString(recievedID), Toast.LENGTH_SHORT).show();
		
		String imageFile = Model.GetbyId(recievedID + 1).IconFile;
		setWallpaper = (Button)findViewById(R.id.button1);
		
		
		
		
		ims = null;
        try {
            ims = getAssets().open(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        // set image to ImageView
        imageView.setImageDrawable(d);
        
        
        setWallpaper.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bitmap mBitmap = BitmapFactory.decodeStream(ims);

	            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());

	        
	            try {
	                myWallpaperManager.setBitmap(mBitmap);
	                Toast.makeText(getBaseContext(), "Wallpaper set", Toast.LENGTH_SHORT).show();
	            } catch (IOException e) {
	                Toast.makeText(getBaseContext(), "Error setting wallpaper", Toast.LENGTH_SHORT).show();
	            }
			}
		});
	}

}
