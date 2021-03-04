package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AtYourService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
    }

    public void startLaunchWebActivity(View view) {
        startActivity(new Intent(AtYourService.this, LaunchWebActivity.class));
    }

    public void startWebViewActivity(View view) {
        startActivity(new Intent(AtYourService.this, WebViewActivity.class));
    }
}