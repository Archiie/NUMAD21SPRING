package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ClickyClicky extends AppCompatActivity {
    TextView txtView;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);

        //action listener to the button
        Button buttonA = (Button)findViewById(R.id.buttonA);
        buttonA.setOnClickListener(v -> {
            txtView = (TextView) findViewById(R.id.greetings);
            message = "Pressed: A";
            txtView.setText(message);
        });

        Button buttonB = findViewById(R.id.buttonB);
        buttonB.setOnClickListener(v -> {
            txtView = (TextView) findViewById(R.id.greetings);
            message = "Pressed: B";
            txtView.setText(message);
        });

        Button buttonC = findViewById(R.id.buttonC);
        buttonC.setOnClickListener(v -> {
            txtView = (TextView) findViewById(R.id.greetings);
            message = "Pressed: C";
            txtView.setText(message);
        });

        Button buttonD = findViewById(R.id.buttonD);
        buttonD.setOnClickListener(v -> {
            txtView = (TextView) findViewById(R.id.greetings);
            message = "Pressed: D";
            txtView.setText(message);
        });

        Button buttonE = findViewById(R.id.buttonE);
        buttonE.setOnClickListener(v -> {
            txtView = (TextView) findViewById(R.id.greetings);
            message = "Pressed: E";
            txtView.setText(message);
        });

        Button buttonF = findViewById(R.id.buttonF);
        buttonF.setOnClickListener(v -> {
            txtView = (TextView) findViewById(R.id.greetings);
            message = "Pressed: F";
            txtView.setText(message);
        });

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClickyClicky.this, MainActivity.class);
            startActivity(intent);
        });
    }
}