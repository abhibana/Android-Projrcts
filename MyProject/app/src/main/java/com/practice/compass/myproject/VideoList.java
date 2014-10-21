package com.practice.compass.myproject;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

public class VideoList extends ListActivity{
    File external,dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String songs[]=listOfSongs();
        setListAdapter(new ArrayAdapter<String>(VideoList.this, android.R.layout.simple_list_item_1, songs));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String songs[] = listOfSongs();
        super.onListItemClick(l, v, position, id);

        Intent video = new Intent(VideoList.this,Video.class);
        video.putExtra("Path",dir.getAbsolutePath()+"/"+songs[position]);
        // sound.putExtra("Path",dir.getAbsolutePath()+"/");
        startActivity(video);
    }

    private  String[] listOfSongs(){
        String fileNames[]={};
        try{
            external = Environment.getExternalStorageDirectory();
            dir = new File(external,"/Videos/");
            fileNames = dir.list();
            return  fileNames;
        }catch (Exception e){}

        return fileNames;
    }
}
