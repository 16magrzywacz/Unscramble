package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SettingsActivity extends AppCompatActivity{

    ImageButton home;
    Button calibrate;
    TextView noneSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Store.resetScrambleVariables();

        //Sets the none set text to invisible if no colours are calibrated
        noneSet = (TextView) findViewById(R.id.noneSetText);
        SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
        String calibrated = sharedPreferences.getString("calibrated_colours", "");
        if(!calibrated.equals("")) {
            noneSet.setText("Colours successfully calibrated!");
        }

        calibrate = (Button) findViewById(R.id.calibrateButtonSettings);

        calibrate.setOnClickListener(v -> {
            //Sets a preference so that the app knows where to redirect the user to
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cal_redirect", "settings");
            editor.apply();
            Intent intent = new Intent(SettingsActivity.this, CalibrateActivity.class);
            startActivity(intent);
        });

        home = (ImageButton)findViewById(R.id.settingsHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

}