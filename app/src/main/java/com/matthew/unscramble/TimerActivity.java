package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    ImageButton home;
    TextView timer, scrambleText;
    ImageView timerButton, statsButton;
    Button scrambleButton;

    Handler handler;

    long tMilliSec, tStart = 0L;

    boolean started;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Used for timer
        handler = new Handler();

        //Sets up scramble button and text to generate and show random scrambles
        scrambleButton = (Button) findViewById(R.id.genScramble);
        scrambleText = (TextView) findViewById(R.id.scramble);
        scrambleText.setText(String.join(" ", Cube.generateScramble()));

        scrambleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrambleText.setText(String.join(" ", Cube.generateScramble()));
            }
        });

        //Switches view to statistics page
        statsButton = (ImageView) findViewById(R.id.statsButton);

        statsButton.setOnClickListener(v -> {
            Intent intent = new Intent(TimerActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });


        //Sets up timer button and text
        timer = (TextView) findViewById(R.id.timerText);
        timerButton = (ImageView) findViewById(R.id.timerHoldButton);

        timerButton.setOnTouchListener((v, event) -> {
            //When user presses down on image
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if(started) {
                    //set to 'hold to start'
                    timerButton.setImageResource(R.drawable.hold);
                    //stop timer, reset variables, save time
                    handler.removeCallbacks(runnable);
                    //Obtains current list of times
                    SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
                    String json = sharedPreferences.getString("times", "");
                    //Coverts the json to an arraylist
                    Type type = new TypeToken<List<Integer>>() {}.getType();
                    Gson gson = new Gson();
                    ArrayList<Integer> times;
                    if(json.equals("")) {
                        times = new ArrayList<Integer>();
                    }else {
                        times = gson.fromJson(json, type);
                    }
                    //adds the current time to the list
                    times.add((int) tMilliSec);
                    System.out.println(times);
                    //adds back the new list of times
                    String newJson = gson.toJson(times);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("times", newJson);
                    editor.apply();
                }else {
                    //set to 'release when ready' and resets variables
                    tMilliSec = 0L;
                    tStart = 0L;
                    timer.setText("00:00:00");
                    timerButton.setImageResource(R.drawable.release);
                }
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                if(!started) {
                    //set to 'press to stop'
                    timerButton.setImageResource(R.drawable.stop);
                    started = true;
                    //start timer
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                }else {
                    started = false;
                }
            }
            return true;
        });

        home = (ImageButton)findViewById(R.id.timerHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Constantly updating time in milliseconds since the start time
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            //sets secs, mins, and milliseconds to be used for changing text
            int secs = (int) tMilliSec/1000;
            int mins = secs/60;
            int millis = (int) tMilliSec%1000;
            //Converting milliseconds to string
            String mill;
            if(millis < 10) {
                mill = "00";
            }else if(millis < 100) {
                mill = "0" + String.valueOf(millis).charAt(0);
            }else {
                mill= String.valueOf(millis).substring(0,2);
            }

            secs %=60;
            timer.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + mill);
            //Updates every 8 milliseconds, so for a 120hz screen, every frame
            handler.postDelayed(this, 10);
        }
    };

}