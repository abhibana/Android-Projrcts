package com.practice.compass.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_splash);

        Thread t = new Thread(){

            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                   startActivity(new Intent(Splash.this,LoadLibrary.class));
                }catch(InterruptedException e){}
            }
        };

        t.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
