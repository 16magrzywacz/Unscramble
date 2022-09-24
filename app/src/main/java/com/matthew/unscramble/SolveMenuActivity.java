package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SolveMenuActivity extends AppCompatActivity {

    Button cameraButton, manualButton;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_menu);

        Store.resetScrambleVariables();

        //linking each button to the buttons in the layout xml
        cameraButton = (Button)findViewById(R.id.cameraInputButton);
        manualButton = (Button)findViewById(R.id.manualInputButton);

        home = (ImageButton)findViewById(R.id.solveMenuHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(SolveMenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        //Switches view to the camera input page when the button is pressed
        cameraButton.setOnClickListener(view -> {
            Store.resetScrambleVariables();
            Store.usingCamera = true;
            Intent intent = new Intent(SolveMenuActivity.this, CameraInputActivity.class);
            startActivity(intent);
        });

        //Switches view to the manual input page when the button is pressed
        manualButton.setOnClickListener(view -> {
            Store.resetScrambleVariables();
            Store.usingCamera = false;
            Intent intent = new Intent(SolveMenuActivity.this, ManualInputActivity.class);
            startActivity(intent);
        });
    }
}