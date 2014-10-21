package com.practice.compass.myproject;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Sound extends Activity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    Button musicPlay,musicPause,musicStop,musicForward,musicBackward;
    TextView songLength,songDetails,currentSongPosition ;
    SeekBar songProgress;
    private boolean stopUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setPlayer();
        songProgress.setMax(mediaPlayer.getDuration());
        songProgress.setOnSeekBarChangeListener(this);

        mediaPlayer.start();
        update();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stopUpdate = true;
            startActivity(new Intent(this,ListSongs.class));
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser) {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.musicplay:   mediaPlayer.start();
                                   break;

            case R.id.musicpause:  mediaPlayer.pause();
                                   break;

            case R.id.musicstop:   mediaPlayer.seekTo(0);
                                   mediaPlayer.pause();
                                   break;

            case R.id.musicback:   mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                                   break;

            case R.id.musicforward:mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                                   break;
        }

    }

    private void setPlayer(){
        songProgress = (SeekBar) findViewById(R.id.songprogress);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getIntent().getStringExtra("Path"));
            mediaPlayer.prepare();
        }catch(Exception e){}

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        musicPlay = (Button) findViewById(R.id.musicplay);
        musicPause = (Button) findViewById(R.id.musicpause);
        musicStop = (Button) findViewById(R.id.musicstop);
        musicBackward = (Button) findViewById(R.id.musicback);
        musicForward = (Button) findViewById(R.id.musicforward);
        currentSongPosition = (TextView) findViewById(R.id.currentsongposition);
        songLength = (TextView) findViewById(R.id.songlength);
        songDetails = (TextView) findViewById(R.id.songdetails);

        songLength.setText(convertToMinutes(mediaPlayer.getDuration()));
        songDetails.setText(String.valueOf(mediaPlayer.getAudioSessionId()));
        musicPlay.setOnClickListener(this);
        musicPause.setOnClickListener(this);
        musicStop.setOnClickListener(this);
        musicForward.setOnClickListener(this);
        musicBackward.setOnClickListener(this);

    }

    private String convertToMinutes(int millis){

        int second = (millis/1000)%60;

        if(second<10) {
            return (millis / 60000) + ".0" + second;
        }
        else{
            return (millis / 60000) + "." + second;
        }
    }

    private void update(){
        Thread t = new Thread(){

            @Override
            public void run() {

                try {
                    while (mediaPlayer!=null && (mediaPlayer.getDuration() >= mediaPlayer.getCurrentPosition())) {

                        if(stopUpdate){
                            System.exit(0);
                        }
                            songProgress.setProgress(mediaPlayer.getCurrentPosition());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    currentSongPosition.setText(convertToMinutes(mediaPlayer.getCurrentPosition()));
                                }
                            });
                    }
                }catch (Exception e){}
            }
        };
        t.start();
    }
}

