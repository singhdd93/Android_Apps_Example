package net.viralpatel.android.imagegalleray;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageGalleryDemoActivity extends Activity {
    
	
	private static int RESULT_LOAD_IMAGE = 1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				i.putExtra("crop", "true");
				i.putExtra("aspectX", 1);
				i.putExtra("aspectY", 2);
				i.putExtra("outputX", 100);
				i.putExtra("outputY", 200);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		//if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
		//	Uri selectedImage = data.getData();
		//	String[] filePathColumn = { MediaStore.Images.Media.DATA };

			if (requestCode == RESULT_LOAD_IMAGE) {
				Bundle extras2 = data.getExtras();
				if (extras2 != null) {
				Bitmap photo = extras2.getParcelable("data");
				//imgview.setImageBitmap(photo);
			/**Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();**/
			
			ImageView imageView = (ImageView) findViewById(R.id.imgView);
			imageView.setImageBitmap(photo);
		
		}
    
			}
    
    }
}