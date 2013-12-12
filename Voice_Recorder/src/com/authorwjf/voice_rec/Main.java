package com.authorwjf.voice_rec;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	
	MediaRecorder mRecorder = new MediaRecorder();
	MediaPlayer mPlayer = new MediaPlayer();
	boolean isRecording = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.play_back_button).setOnClickListener(this);
        findViewById(R.id.record_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		mPlayer.stop();
		switch (v.getId()) {
		case R.id.play_back_button:
			if (!isRecording && !mPlayer.isPlaying()) {
				try {
					mPlayer.reset();
					mPlayer.setDataSource("/sdcard/audio_demo.3gp");
					mPlayer.prepare();
					mPlayer.start();
				} catch (Exception e) {
					Toast.makeText(this, "Error playing back audio.",Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.record_button:
			if (isRecording) {
				isRecording = false;
				((Button)(findViewById(R.id.record_button))).setText("record");
				mRecorder.reset();
			} else {
				try {
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile("/sdcard/audio_demo.3gp");
					mRecorder.prepare();
					mRecorder.start(); 
					((Button)(findViewById(R.id.record_button))).setText("stop");
					isRecording = true;
				} catch (Exception e) {
					Toast.makeText(this, "Error starting recorder.",Toast.LENGTH_SHORT).show();
				} 
				
			}
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		if (isRecording) {
			Toast.makeText(this, "Recorder stopped.",Toast.LENGTH_SHORT).show();
			mRecorder.stop();
		}
		mRecorder.release();
		mPlayer.stop();
		mPlayer.release();
		super.onDestroy();
	}
}