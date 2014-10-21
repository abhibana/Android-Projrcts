package com.practice.compass.myproject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notification extends Activity implements View.OnClickListener{

    Button notification;
    NotificationManager notificationManager;
    static final int uniqueID = 123456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        notification = (Button) findViewById(R.id.notification);
        notification.setOnClickListener(this);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(uniqueID);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SpeakOut.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        String body = "Click to Speak with Phone";
        String title = "Speak Out";
        android.app.Notification noti = new android.app.Notification(R.drawable.speakout,body,System.currentTimeMillis());
        noti.setLatestEventInfo(this,title,body,pendingIntent);
        noti.defaults = android.app.Notification.DEFAULT_ALL;
        notificationManager.notify(uniqueID,noti);
        finish();
    }
}
