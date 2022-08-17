package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SolutionActivity extends AppCompatActivity {

    TextView moveList;

    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        home = (ImageButton)findViewById(R.id.solutionHome);

        //Returns to home when pressed
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SolutionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Store.resetScrambleVariables();

        moveList = (TextView)findViewById(R.id.movesList);

        MainActivity.usercube.move("solve");
        List<String> solution = Solver.solution;

        moveList.setText(solution.toString());
    }
}