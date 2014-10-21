package com.practice.compass.myproject;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import java.util.Locale;
import java.util.Random;

public class SpeakOut extends Activity implements View.OnClickListener{

    static final String[] speechOut = {"Fuck you","Shut the fuck up","Go to hell","U r an Asshole","Gandu","Bokachoda",
            "U r a gay","Bakchod","Bichiri","Jata"};
    TextToSpeech textToSpeech;
    Button speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speakout);
        speak = (Button) findViewById(R.id.speak);
        speak.setOnClickListener(this);
        textToSpeech = new TextToSpeech(this,new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                        textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onClick(View v) {

        Random random = new Random();
        textToSpeech.speak(speechOut[random.nextInt(10)],TextToSpeech.QUEUE_FLUSH,null);
     }

  }
