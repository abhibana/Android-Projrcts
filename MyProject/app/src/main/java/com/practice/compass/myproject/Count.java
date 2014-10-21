package com.practice.compass.myproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Count extends Activity implements View.OnClickListener{

    Button addButton,subtractButton;
    TextView yourCount;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = (Button) findViewById(R.id.add_button);
        subtractButton = (Button) findViewById(R.id.subtract_button);
        yourCount = (TextView) findViewById(R.id.your_count);

        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.add_button:  count ++;     yourCount.setText("Count is: "+count);
                                   break;
            case R.id.subtract_button:  count --;    yourCount.setText("Count is: "+count);
                                        break;
        }
    }
}
