package com.android.camera.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.android.camera.CropImageIntentBuilder;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private static int REQUEST_PICTURE = 1;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {
            Uri uri = Uri.fromFile(new File("/tmp/test"));

            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, uri);
            cropImage.setSourceImage(data.getData());

            startActivity(cropImage.getIntent(this));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(button)) {
            startActivityForResult(MediaStoreUtils.getPickImageIntent(this), REQUEST_PICTURE);
        }
    }
}
