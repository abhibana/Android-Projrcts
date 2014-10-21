package com.practice.compass.myproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

public class Photo extends Activity implements View.OnClickListener{

    ImageButton takePicture;
    Button setWallpaper;
    ImageView imageView;
    Bitmap bitMap;
    final static int cameraData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        takePicture = (ImageButton) findViewById(R.id.take_picture);
        setWallpaper = (Button) findViewById(R.id.set_wallpaper);
        imageView = (ImageView) findViewById(R.id.image_view);
        takePicture.setOnClickListener(this);
        setWallpaper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.set_wallpaper:
                try {
                    getApplicationContext().setWallpaper(bitMap);
                } catch (IOException e) {}
                break;
            case R.id.take_picture: Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(takePic,cameraData);
                                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                bitMap = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitMap);
        }
    }
}
