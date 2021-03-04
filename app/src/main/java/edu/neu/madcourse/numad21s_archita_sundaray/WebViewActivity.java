package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private EditText mWebDestEditText;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebDestEditText = (EditText) findViewById(R.id.webView_editText);
        mWebView.getSettings().setJavaScriptEnabled(true);

        // redirects within the WebView, as opposed to being launched in a browser.
        mWebView.setWebViewClient(new WebViewClient() {
            //Api < 24
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            //Api > 24
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void loadWebsite(View view) {
        String url = mWebDestEditText.getText().toString().trim();
        try {
            url = NetworkUtil.validInput(url);
            mWebView.loadUrl(url);
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}