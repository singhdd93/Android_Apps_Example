package com.samir;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

public class DetailActivity extends Activity {
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	private ProgressBar pbar;
	private TextView tvTitle, tvDesc;
	private ImageView imgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		pbar = (ProgressBar) findViewById(R.id.pbardesc);
		tvTitle = (TextView) findViewById(R.id.tvtitle);
		tvDesc = (TextView) findViewById(R.id.tvdesc);
		imgView = (ImageView) findViewById(R.id.imgdesc);

		Bundle b = getIntent().getExtras();

		String title = b.getString("title");
		String desc = b.getString("desc");

		tvTitle.setText(title);
		tvDesc.setText(desc);

		String url = b.getString("url");
		loadImageFromURL(url);

	}

	private void loadImageFromURL(String url) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.profile)
				.showImageForEmptyUrl(R.drawable.profile).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imgView, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingComplete() {
						pbar.setVisibility(View.INVISIBLE);

					}

					@Override
					public void onLoadingFailed() {

						pbar.setVisibility(View.INVISIBLE);
					}

					@Override
					public void onLoadingStarted() {
						pbar.setVisibility(View.VISIBLE);
					}
				});

	}
}
