package com.practice.compass.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Get extends Activity implements View.OnClickListener{

    Button start,startFor;
    EditText editTextSend;
    TextView gotAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get);
        initialize();
        start.setOnClickListener(this);
        startFor.setOnClickListener(this);
    }

    private void initialize(){

        start = (Button) findViewById(R.id.start_activity);
        startFor = (Button) findViewById(R.id.activity_results);
        editTextSend = (EditText) findViewById(R.id.et_send);
        gotAnswer = (TextView) findViewById(R.id.show_answer);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.start_activity:   Bundle bundle = new  Bundle();
                                        bundle.putString("sentData",editTextSend.getText().toString());
                                        Intent startIntent = new Intent(Get.this,Send.class);
                                        startIntent.putExtras(bundle);
                                        startActivity(startIntent);
                                        break;

            case R.id.activity_results: Intent resultsIntent = new Intent(Get.this,Send.class);
                                        startActivityForResult(resultsIntent,0);
                                        break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bundle gotBundle = data.getExtras();
            System.out.print("Fuck "+gotBundle.getString("answer"));
            gotAnswer.setText(gotBundle.getString("answer"));
        }
    }
}
