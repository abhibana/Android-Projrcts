package com.practice.compass.myproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

public class TextPlay extends Activity implements View.OnClickListener{

    Button tryCommand;
    ToggleButton toggleButton;
    EditText enteredText;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        tryCommand = (Button) findViewById(R.id.try_command);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        enteredText = (EditText) findViewById(R.id.entered_command);
        display = (TextView) findViewById(R.id.status);

        tryCommand.setOnClickListener(this);
        toggleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.try_command: String command = enteredText.getText().toString();
                                   display.setText(command);
                                   if(command.equals("left")){ display.setGravity(Gravity.LEFT); }
                                   else if(command.equals("center")){ display.setGravity(Gravity.CENTER); }
                                   else if(command.equals("right")){ display.setGravity(Gravity.RIGHT); }
                                   else if(command.equals("WTF")) {
                                       Random crazy = new Random();
                                           display.setText("WTF!!!");
                                           display.setTextSize(crazy.nextInt(75));
                                           display.setTextColor(Color.rgb(crazy.nextInt(265),crazy.nextInt(265),crazy.nextInt(265)));
                                           switch(crazy.nextInt(3)) {

                                               case 0:
                                                   display.setGravity(Gravity.LEFT);
                                                   break;
                                               case 1:
                                                   display.setGravity(Gravity.CENTER);
                                                   break;
                                               case 2:
                                                   display.setGravity(Gravity.RIGHT);
                                                   break;
                                           }
                                       }
                                   else {  display.setText("Invalid");  display.setGravity(Gravity.CENTER);}
                                   break;

            case R.id.toggleButton: if(toggleButton.isChecked()) { enteredText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);}
                                    else {enteredText.setInputType(InputType.TYPE_CLASS_TEXT);}
                                   break;
        }

    }
}
