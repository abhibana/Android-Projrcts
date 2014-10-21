package com.practice.compass.myproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class MyGFXView  extends View{

    Bitmap myBitmap;

    public MyGFXView(Context context) {
        super(context);
        myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rainbow);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(myBitmap,0,0,null);
    }
}
