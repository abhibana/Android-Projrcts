package com.practice.compass.myproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Call extends Activity implements View.OnClickListener{

    private Button call;
    private EditText number;
    private String contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);
        call = (Button) findViewById(R.id.call);
        number = (EditText) findViewById(R.id.number);
        call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        contactNumber = "0"+number.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contactNumber));
        startActivity(callIntent);
        finish();
    }
}
