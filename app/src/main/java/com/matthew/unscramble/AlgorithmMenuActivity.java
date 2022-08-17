package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AlgorithmMenuActivity extends AppCompatActivity {

    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_menu);

        Store.resetScrambleVariables();

        home = (ImageButton)findViewById(R.id.solutionHome);

        //Returns to home when pressed
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlgorithmMenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}