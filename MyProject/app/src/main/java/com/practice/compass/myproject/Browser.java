package com.practice.compass.myproject;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SlidingDrawer;

public class Browser extends Activity implements View.OnClickListener{

    WebView browser;
    SlidingDrawer slider;
    Button webGo,handle,backButton,forWardButton,refreshButton,clearHistoryButton,closeButton;
    EditText sliderURL;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider);

        slider = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        webGo = (Button) findViewById(R.id.webgo);
        handle = (Button) findViewById(R.id.handle);
        backButton = (Button) findViewById(R.id.back_button);
        forWardButton = (Button) findViewById(R.id.forward_button);
        refreshButton = (Button) findViewById(R.id.refresh_button);
        clearHistoryButton = (Button) findViewById(R.id.historyClear_button);
        closeButton = (Button) findViewById(R.id.close_button);

        sliderURL = (EditText) findViewById(R.id.url);
        progressBar = (ProgressBar) findViewById(R.id.page_load_progress);
        progressBar.setVisibility(View.INVISIBLE);

        browser = (WebView) findViewById(R.id.browser);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.setWebViewClient(new MyWebView(progressBar));
        
        webGo.setOnClickListener(this);
        handle.setOnClickListener(this);
        backButton.setOnClickListener(this);
        forWardButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        clearHistoryButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.webgo){
            String url = "http://www."+sliderURL.getText().toString().toLowerCase();
            slider.animateClose();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(sliderURL.getWindowToken(),0);
            progressBar.setVisibility(View.VISIBLE);
            browser.loadUrl(url);
        }

        else if(v.getId()==R.id.back_button){
            if(browser.canGoBack()){
                browser.goBack();
            }
        }

        else if(v.getId()==R.id.forward_button){
            if(browser.canGoForward()){
                browser.goForward();
            }
        }

        else if(v.getId()==R.id.refresh_button){
            browser.reload();
        }

        else if(v.getId()==R.id.historyClear_button){
            browser.clearHistory();
        }

        else if(v.getId()==R.id.close_button){
            finish();
        }
    }
}

class MyWebView extends WebViewClient{

    ProgressBar progress;

   MyWebView(ProgressBar pbar){
       progress = pbar;
   }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progress.setVisibility(View.INVISIBLE);
    }
}
