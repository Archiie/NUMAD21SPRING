package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class LinkBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_browser);

        WebView webView = (WebView) findViewById(R.id.webview);
        int urlId = R.id.item_desc;
        webView.loadUrl(String.valueOf(urlId));
    }
}