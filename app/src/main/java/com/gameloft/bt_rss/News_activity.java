package com.gameloft.bt_rss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class News_activity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_activity);

        webView = (WebView)findViewById(R.id.webview);
        Intent intent = getIntent();
        String link = intent.getStringExtra("LinkTinTuc");

        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}
