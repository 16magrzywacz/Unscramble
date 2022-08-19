package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.catalinjurjiu.animcubeandroid.AnimCube;

import java.util.Arrays;
import java.util.List;

public class SolutionActivity extends AppCompatActivity {

    AnimCube animCube;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        //Generates a string that is used to set the state of the 3d cube, base on the user scramble
        String cubeState = getScrambleCode();

        home = (ImageButton)findViewById(R.id.solutionHome);

        //Returns to home when pressed
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SolutionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Links AnimCube object to the one in the layout
        animCube = (AnimCube)findViewById(R.id.animCube);

        //Sets the cube to the user scramble
        animCube.setCubeModel(cubeState);

        //Solves the cube, and extracts the solution
        //Solver.solve(MainActivity.usercube);
        //List<String> solutionList = Solver.solution;
        //Converts the list to a single string, with items separated with a space
        //String solution = String.join(" ", solutionList);

        //animCube.setMoveSequence(solution);
        //animCube.animateMoveSequence();

        System.out.println(Solver.solve(MainActivity.usercube));

        Store.resetScrambleVariables();
    }


    public static String getScrambleCode() {
        //Creates an appendable string object
        StringBuilder ret = new StringBuilder();

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    //Every time i increments, a different side is extracted from
                    switch (i) {
                        //Appends to the string the stickers on each side.
                        //Due to how the library works, some take [k][j] and others take a different
                        //orientation
                        case 0: ret.append(MainActivity.usercube.getWhiteFace()[2-j][k]); continue;
                        case 1: ret.append(MainActivity.usercube.getYellowFace()[k][j]); continue;
                        case 2: ret.append(MainActivity.usercube.getGreenFace()[k][j]); continue;
                        case 3: ret.append(MainActivity.usercube.getBlueFace()[k][j]); continue;
                        case 4: ret.append(MainActivity.usercube.getOrangeFace()[j][2-k]); continue;
                        case 5: ret.append(MainActivity.usercube.getRedFace()[k][j]);
                    }
                }
            }
        }

        //Replaces all of the colours with integers, as this is what the library uses
        for(int i = 0; i < ret.length(); i++) {
            switch (ret.charAt(i)) {
                case 'W': ret.setCharAt(i, '0'); continue;
                case 'Y': ret.setCharAt(i, '1'); continue;
                case 'O': ret.setCharAt(i, '2'); continue;
                case 'R': ret.setCharAt(i, '3'); continue;
                case 'B': ret.setCharAt(i, '4'); continue;
                case 'G': ret.setCharAt(i, '5');
            }
        }

        //returns the string
        return ret.toString();
    }
}