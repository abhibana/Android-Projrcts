package com.practice.compass.myproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class Tabs  extends Activity implements View.OnClickListener{

    TabHost tabHost;
    Button addTab,startButton,stopButton;
    TextView showResults;
    long start,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Home");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Home");
        tabHost.addTab(tabSpec);
        addTab = (Button) findViewById(R.id.addTabButton);
        addTab.setOnClickListener(this);

        tabSpec = tabHost.newTabSpec("About");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Stop Watch");
        tabHost.addTab(tabSpec);
        startButton = (Button) findViewById(R.id.start_clock);
        stopButton = (Button) findViewById(R.id.stop_clock);
        showResults = (TextView) findViewById(R.id.stopwatch);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        tabSpec = tabHost.newTabSpec("News");
        tabSpec.setContent(R.id.tab3);
        tabSpec.setIndicator("News");
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onClick(View v) {

         if(v.getId()==R.id.addTabButton) {
             TabHost.TabSpec newSpec = tabHost.newTabSpec("Play");
             newSpec.setContent(new TabHost.TabContentFactory() {
                 @Override
                 public View createTabContent(String tag) {

                     TextView textView = new TextView(Tabs.this);
                     textView.setText("You've created a new Tab");
                     textView.setTextColor(Color.YELLOW);
                     textView.setTextSize(20);
                     textView.setGravity(1);
                     return textView;
                 }
             });

             newSpec.setIndicator("Play");
             tabHost.addTab(newSpec);
         }

        else if(v.getId()==R.id.start_clock){
             start = System.currentTimeMillis();
         }

         else if(v.getId()==R.id.stop_clock){
             stop = System.currentTimeMillis();
             if(start!=0) {
                 int millis = (int) (stop - start) ;
                 int seconds = (millis/1000);
                 int minutes = seconds / 60;
                 millis = millis % 100;
                 seconds = seconds % 60;
                 showResults.setText(String.format("%d : %02d : % 02d", minutes, seconds, millis));
             }
         }
    }
}
