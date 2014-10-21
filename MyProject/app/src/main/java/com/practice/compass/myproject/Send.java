package com.practice.compass.myproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Send extends Activity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    TextView question,test;
    Button returnData;
    RadioGroup selection;
    String getData,setData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        initialize();
        returnData.setOnClickListener(this);
        selection.setOnCheckedChangeListener(this);

        SharedPreferences getPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String et = getPreferences.getString("name","Travis is...");
        String values = getPreferences.getString("list","4");

        if(values.contentEquals("1")){
            question.setText(et+" is...");
        }

//        Bundle getBundle = getIntent().getExtras();
//        getData = getBundle.getString("sentData");
//        question.setText(getData+" is");
    }

    private void initialize(){

        question = (TextView) findViewById(R.id.tvquestion);
        test = (TextView) findViewById(R.id.tvtest);
        returnData = (Button) findViewById(R.id.reurn_button);
        selection = (RadioGroup) findViewById(R.id.selection);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("answer",setData);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.rcrazy: setData = "Probably Right!";
                              break;
            case R.id.rsexy:  setData = "Definitely Right!";
                              break;
            case R.id.rboth:  setData = "Spot On!";
                              break;
        }
        test.setText(setData);
    }
}
