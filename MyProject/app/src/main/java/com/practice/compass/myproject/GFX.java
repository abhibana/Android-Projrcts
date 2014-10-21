package com.practice.compass.myproject;


import android.app.Activity;
import android.os.Bundle;

public class GFX extends Activity {

    MyGFXView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myView = new MyGFXView(this);
        setContentView(myView);
    }
}
