package com.example.brainsoft.sqlitetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView wv= (WebView) findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        JavaScriptInterface jsi=new JavaScriptInterface(this);
        wv.addJavascriptInterface(jsi,"android");
        wv.loadUrl("file:///android_asset/appCRUD.html");
    }
}
