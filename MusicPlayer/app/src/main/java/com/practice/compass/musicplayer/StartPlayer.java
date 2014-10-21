package com.practice.compass.musicplayer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import static com.practice.compass.musicplayer.Convert.convertToMinutes;

public class StartPlayer extends AsyncTask<String,Void,Void> implements SeekBar.OnSeekBarChangeListener{

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    TextView songLength,songDetails,currentSongPosition ;
    ImageView smallAlbumArt,largeAlbumArt;
    SeekBar songProgress;
    static String songs[],songSources[],albumArts[];
    Context context;
    int count = 0;

    public int songPosition = 0;

    public void setPlayer(SeekBar songProgress,AudioManager audioManager,TextView songLength,TextView songDetails,
                          TextView currentSongPosition,ImageView smallAlbumArt,ImageView largeAlbumArt){

        this.audioManager = audioManager;
        this.songProgress = songProgress;
        this.songLength = songLength;
        this.songDetails = songDetails;
        this.currentSongPosition = currentSongPosition;
        this.smallAlbumArt = smallAlbumArt;
        this.largeAlbumArt = largeAlbumArt;
    }

    public void setupResources(Context context,String songs[],String songSource[], String albumArtSource[]) {

        this.context = context;
        this.songs = songs;
        songSources = songSource;
        albumArts = albumArtSource;

        for(int i=0;i<albumArts.length;i++){
            Log.v(songs[i],albumArts[i]);
        }
    }

    private void setPlayerView(){

        Log.v("PlayerView",String.valueOf(songPosition));

        songDetails.setText(songs[songPosition]);

        if(!albumArts[songPosition].equals("R.drawable.albumart")){
            smallAlbumArt.setImageURI(Uri.parse(albumArts[songPosition]));
            largeAlbumArt.setImageURI(Uri.parse(albumArts[songPosition]));
        }
        else{
            smallAlbumArt.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.albumart));
            largeAlbumArt.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.albumart));
        }
    }

    public void setSong (int position){

        try {
            count = 0;
            if(mediaPlayer!=null){
                mediaPlayer.reset();
            }
            else{
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(audioManager.STREAM_MUSIC);
            }
            songPosition = position;
            Log.v("Setsong", String.valueOf(songPosition));
            mediaPlayer.setDataSource(songSources[songPosition]);
            mediaPlayer.prepare();
            Thread.sleep(800);
            mediaPlayer.start();
            songProgress.setMax(mediaPlayer.getDuration());
            songProgress.refreshDrawableState();
            songProgress.setOnSeekBarChangeListener(this);

        } catch (Exception e) {}
    }

    public void play(){
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }

    public void next(){
        if(songPosition<songs.length-1) {
            mediaPlayer.reset();
            songPosition++;
            setSong(songPosition);
        }
        else{
            Toast.makeText(context, "Sorry End of the List", Toast.LENGTH_LONG).show();
        }
    }

    public void prev(){

        if(songPosition>0) {
            mediaPlayer.reset();
            songPosition--;
            setSong(songPosition);
        }
        else{
            Toast.makeText(context,"Sorry This is starting of the List",Toast.LENGTH_LONG).show();
        }
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
    protected Void doInBackground(String... params) {

            setSong(Integer.parseInt(params[0]));

        try {
            while (true) {

                if(mediaPlayer.getCurrentPosition()>= mediaPlayer.getDuration()-500){
                        next();
                }
                publishProgress();
                Thread.sleep(100);
            }
        }catch(InterruptedException e){}
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        songProgress.setProgress(mediaPlayer.getCurrentPosition());
        currentSongPosition.setText(convertToMinutes(mediaPlayer.getCurrentPosition()));

        if(count == 0) {
            count++;
            songLength.setText(convertToMinutes(mediaPlayer.getDuration()));
            setPlayerView();
        }

    }
 }



