package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class OllActivity extends AppCompatActivity {

    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oll);

        home = (ImageButton)findViewById(R.id.ollHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(OllActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}