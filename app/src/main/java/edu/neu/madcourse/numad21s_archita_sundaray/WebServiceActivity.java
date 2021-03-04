package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class WebServiceActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private EditText mURLEditText;
    private static final String TAG = "WebServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
    }
}