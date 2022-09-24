package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ImageButton home;
    Button calibrate;
    TextView noneSet;
    Spinner spinner;

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
            noneSet.setText("Calibrated!");
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

        //Array of speed options
        String[] speeds = {"0.25x", "0.5x", "1x", "2x", "4x"};

        //Links spinner to variable
        spinner = (Spinner) findViewById(R.id.spinner);
        //Sets a listened for when the user chooses an item
        spinner.setOnItemSelectedListener(this);

        //Sets the list of items to be used in the drop-down
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, speeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Obtains current anim speed set by user (1x by default)
        String val = sharedPreferences.getString("anim_speed", "1x");
        //Obtains index in array of that item
        int index = Arrays.asList(speeds).indexOf(val);
        //Sets the selected item to that index
        spinner.setSelection(index);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //If the spinner item was selected
        if(parent.getId() == R.id.spinner) {
            //Obtains selected value
            String value = spinner.getItemAtPosition(position).toString();
            //Updates the preference with the selected variable
            SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("anim_speed", value);
            editor.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}