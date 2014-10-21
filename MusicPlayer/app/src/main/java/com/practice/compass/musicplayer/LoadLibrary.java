package com.practice.compass.musicplayer;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

public class LoadLibrary extends ListActivity implements View.OnClickListener{

    AudioManager audioManager;
    TextView songLength,songDetails,currentSongPosition ;
    ImageView smallAlbumArt,largeAlbumArt;
    SeekBar songProgress;
    Button musicPlay,musicPause,musicStop,musicForward,musicBackward;
    NotificationManager notificationManager;
    Notification notification;
    private boolean stopUpdate = false;
    private int songPosition = 0,uniqueId = 98765;
    Cursor audio,album;
    StartPlayer startPlayer;
    String songs[],songSources[],albumArts[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        setContent();
        setUpSongs();

        setListAdapter(new ArrayAdapter<String>(LoadLibrary.this, android.R.layout.simple_list_item_1, songs));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        setNotification(startPlayer.songPosition);
//        startPlayer.cancel(true);
        finish();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        songPosition = position;
        if(startPlayer==null){
            startPlayer = new StartPlayer();
            startPlayer.setPlayer(songProgress,audioManager,songLength,songDetails,currentSongPosition,
                    smallAlbumArt,largeAlbumArt);
            startPlayer.setupResources(this,songs,songSources,albumArts);
            startPlayer.execute(String.valueOf(songPosition),String.valueOf(stopUpdate));
        }
        else{
            startPlayer.setSong(songPosition);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.musicplay:   startPlayer.play();
                                    break;

            case R.id.musicpause:  startPlayer.pause();
                                     break;

            case R.id.musicstop:   startPlayer.stop();
                                   break;

            case R.id.musicback:   startPlayer.prev();
                                   break;

            case R.id.musicforward: startPlayer.next();
                                    break;
        }
    }

    private void setContent(){

        smallAlbumArt = (ImageView) findViewById(R.id.albumart1);
        largeAlbumArt = (ImageView) findViewById(R.id.albumart2);
        songProgress = (SeekBar) findViewById(R.id.songprogress);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        currentSongPosition = (TextView) findViewById(R.id.currentsongposition);
        songLength = (TextView) findViewById(R.id.songlength);
        songDetails = (TextView) findViewById(R.id.songdetails);
        musicPlay = (Button) findViewById(R.id.musicplay);
        musicPause = (Button) findViewById(R.id.musicpause);
        musicStop = (Button) findViewById(R.id.musicstop);
        musicBackward = (Button) findViewById(R.id.musicback);
        musicForward = (Button) findViewById(R.id.musicforward);
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(uniqueId);

        musicPlay.setOnClickListener(this);
        musicPause.setOnClickListener(this);
        musicStop.setOnClickListener(this);
        musicForward.setOnClickListener(this);
        musicBackward.setOnClickListener(this);
    }


    private void setUpSongs(){

       String audioProjection[] = {MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM};
       String albumProjection[] = {MediaStore.Audio.Albums.ALBUM_ART,MediaStore.Audio.Albums.ALBUM};
       audio = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioProjection, null, null, MediaStore.Audio.Media.TITLE);
       album = managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumProjection, null, null, null);
       int nameId = audio.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
       int sourceId = audio.getColumnIndexOrThrow(String.valueOf(MediaStore.Audio.Media.DATA));
       int imageId = album.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART);
       int mediaAlbumId = audio.getColumnIndexOrThrow(String.valueOf(MediaStore.Audio.Media.ALBUM));
       int albumId = album.getColumnIndexOrThrow(String.valueOf(MediaStore.Audio.Albums.ALBUM));

       String names[] = new String[audio.getCount()];
       String sources[] = new String[audio.getCount()];
       String mediaAlbums[] = new String[audio.getCount()];
       String albums[] = new String[album.getCount()];
       String albumArtURIs[] = new String[album.getCount()];
       //album.moveToNext();
       while(audio.moveToNext()){
           names[audio.getPosition()] = audio.getString(nameId);
           sources[audio.getPosition()] = audio.getString(sourceId);
           mediaAlbums[audio.getPosition()] = audio.getString(mediaAlbumId);
       }

       while(album.moveToNext()){
           albums[album.getPosition()] = album.getString(albumId);
           albumArtURIs[album.getPosition()] = album.getString(imageId);
       }

       albumArts = new String[audio.getCount()];

       for(int i=0;i<albumArts.length;i++){
           for(int j=0;j<albums.length;j++){
               if(mediaAlbums[i].equals(albums[j])){
                       albumArts[i] = albumArtURIs[j];
                       break;
               }
           }
       }


       for (int i=0;i<albumArts.length;i++){
           try {
               Log.d("Final", albumArts[i]);
           }catch(Exception e){ albumArts[i] = "R.drawable.albumart" ; Log.v("Final", albumArts[i]);}
       }

       songs = names;
       songSources = sources;
    }

    private void setNotification(int position){

        notification = new Notification(R.drawable.appicon,"Abcd",System.currentTimeMillis());
        RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.notification);
        contentView.setImageViewUri(R.id.albumart1,Uri.parse(albumArts[position]));
        contentView.setTextViewText(R.id.songdetails,songs[position]);
        notification.contentView = contentView;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,LoadLibrary.class), 0);
        notification.defaults = Notification.DEFAULT_ALL;
//        notification.setLatestEventInfo(this,"Music","player",pendingIntent);
        notification.contentIntent = pendingIntent;
        notificationManager.notify(uniqueId,notification);
    }
}
