package com.practice.compass.myproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Email extends Activity implements View.OnClickListener{

    EditText emailAddress,hatefulInfo,personName,stupidThing,hatefulAction,hatefulOutro;
    Button sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        setVariables();
        sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        StringBuilder  sb = new StringBuilder();
        String email[] = {emailAddress.getText().toString()};
        sb.append("This is my android app email to you. If you like it then please tell me");
        Intent emailIntent = new Intent (Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"My App Generated Email");
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT,sb.toString());
        startActivity(emailIntent);
    }

    private void setVariables(){

        emailAddress = (EditText) findViewById(R.id.email_address);
        hatefulInfo = (EditText) findViewById(R.id.hateful_info);
        personName = (EditText) findViewById(R.id.person_name);
        stupidThing = (EditText) findViewById(R.id.stupid_thing);
        hatefulAction = (EditText) findViewById(R.id.hateful_action);
        hatefulOutro = (EditText) findViewById(R.id.hateful_outro);
        sendEmail = (Button) findViewById(R.id.send_email);
    }
}
