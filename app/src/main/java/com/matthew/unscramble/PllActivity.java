package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class PllActivity extends AppCompatActivity {

    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pll);

        home = (ImageButton)findViewById(R.id.pllHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(PllActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}