package com.example.kuldeep.promisepaperproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class Paperview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperview);
        WebView webview = (WebView) findViewById(R.id.pdfviewer);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://drive.google.com/gview?embedded=true&url=" + getIntent().getExtras().getString("url"));
    }

    public void ShareClick(View view){}

    public void DeleteClick(View view){}

    public void LoadAndViewPDF(ListItem listItem)
    {

    }

}
