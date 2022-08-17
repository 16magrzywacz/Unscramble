package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button solveButton, timerButton, algoButton, settingsButton; //creating button variables

    public static Cube usercube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Store.resetScrambleVariables();

        solveButton = (Button)findViewById(R.id.solveButton); //linking each button to the buttons in the layout xml
        timerButton = (Button)findViewById(R.id.timerButton);
        algoButton = (Button)findViewById(R.id.algoButton);
        settingsButton = (Button)findViewById(R.id.settingsButton);

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Code inside function will execute when button is pressed
                Intent intent = new Intent(MainActivity.this, SolveMenuActivity.class); //Creates an action that switches the view from this page to the solve menu page
                startActivity(intent);//Starts the action
            }
        });

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        algoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlgorithmMenuActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}