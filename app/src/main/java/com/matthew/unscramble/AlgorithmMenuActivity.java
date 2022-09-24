package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AlgorithmMenuActivity extends AppCompatActivity {

    ImageButton home;
    Button oll, pll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_menu);

        Store.resetScrambleVariables();

        oll = (Button)findViewById(R.id.ollButton);

        //Switches view to OLL algorithms
        oll.setOnClickListener(view -> {
            Intent intent = new Intent(AlgorithmMenuActivity.this, OllActivity.class);
            startActivity(intent);
        });

        pll = (Button)findViewById(R.id.pllButton);

        //Switches view to PLL algorithms
        pll.setOnClickListener(view -> {
            Intent intent = new Intent(AlgorithmMenuActivity.this, PllActivity.class);
            startActivity(intent);
        });

        home = (ImageButton)findViewById(R.id.algorithmMenuHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(AlgorithmMenuActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}