package com.practice.compass.myproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends Activity {

    MediaController mediaController;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video);
        setVideoPlayer();
        videoView.start();

    }

    private void setVideoPlayer(){
        videoView = (VideoView) findViewById(R.id.video);
        mediaController = new MediaController(this);
        videoView.setVideoPath(getIntent().getStringExtra("Path"));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
    }
}
