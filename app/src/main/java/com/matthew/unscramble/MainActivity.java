package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button solveButton, timerButton, algoButton, settingsButton; //creating button variables

    public static Cube usercube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Store.resetScrambleVariables();

        SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);

        solveButton = (Button)findViewById(R.id.solveButton); //linking each button to the buttons in the layout xml
        timerButton = (Button)findViewById(R.id.timerButton);
        algoButton = (Button)findViewById(R.id.algoButton);
        settingsButton = (Button)findViewById(R.id.settingsButton);

        solveButton.setOnClickListener(view -> { //Code inside function will execute when button is pressed
            Intent intent = new Intent(MainActivity.this, SolveMenuActivity.class); //Creates an action that switches the view from this page to the solve menu page
            startActivity(intent);//Starts the action
        });

        timerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        });

        algoButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AlgorithmMenuActivity.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}