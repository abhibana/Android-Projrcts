package com.practice.compass.myproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{

    MediaPlayer splashMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences getPreferances = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        splashMusic = MediaPlayer.create(Splash.this,R.raw.splash);
        if(getPreferances.getBoolean("music",true)) {
            splashMusic.start();
        }

        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){}
                finally {
                    Intent home = new Intent(Splash.this,Menu.class);
                    startActivity(home);
                }
            }
        };

        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
