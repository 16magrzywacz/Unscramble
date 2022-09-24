package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.catalinjurjiu.animcubeandroid.AnimCube;
import com.catalinjurjiu.animcubeandroid.AnimCubeDebug;

import java.util.ArrayList;
import java.util.Arrays;

public class SolutionActivity extends AppCompatActivity {

    public static Handler handler = new Handler();
    public static final String ANIM_CUBE_SAVE_STATE_BUNDLE_ID = "animCube";

    public static AnimCube animCube;
    ImageButton home;
    Button nextStep, replayStep, finish;
    TextView stepCounter;
    @SuppressLint("StaticFieldLeak")
    public static TextView moveText, instructions;

    public static String currentSection, state, move;

    public static int whiteCrossThresh, whiteCornersThresh, middleEdgesThresh, yellowCrossThresh,
    ollThresh, pllThresh, totalSteps, currentStep;

    public static ArrayList<String> sunflowerEdges, sunflowerCode, whiteCrossEdges, whiteCrossCode,
            whiteCornersCorners, whiteCornersCode, middleEdgesEdges, middleEdgesCode, yellowCrossCode,
            ollCode, pllCode;

    public static ArrayList<ArrayList<String>> sunflowerMoves, whiteCrossMoves, whiteCornersMoves,
            middleEdgesMoves, yellowCrossMoves, ollMoves, pllMoves;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(ANIM_CUBE_SAVE_STATE_BUNDLE_ID, animCube.saveState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        animCube.restoreState(savedInstanceState.getBundle(ANIM_CUBE_SAVE_STATE_BUNDLE_ID));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        //Ensures variables are set to zero or cleared when the activity starts
        currentStep = 0;
        whiteCrossThresh = 0;
        whiteCornersThresh = 0;
        middleEdgesThresh = 0;
        yellowCrossThresh = 0;
        ollThresh = 0;
        pllThresh = 0;
        sunflowerEdges = new ArrayList<>();
        sunflowerCode = new ArrayList<>();
        whiteCrossEdges = new ArrayList<>();
        whiteCrossCode = new ArrayList<>();
        whiteCornersCorners = new ArrayList<>();
        whiteCornersCode  = new ArrayList<>();
        middleEdgesEdges = new ArrayList<>();
        middleEdgesCode = new ArrayList<>();
        yellowCrossCode = new ArrayList<>();
        ollCode = new ArrayList<>();
        pllCode = new ArrayList<>();
        sunflowerMoves = new ArrayList<>();
        whiteCrossMoves = new ArrayList<>();
        whiteCornersMoves = new ArrayList<>();
        middleEdgesMoves = new ArrayList<>();
        yellowCrossMoves = new ArrayList<>();
        ollMoves = new ArrayList<>();
        pllMoves = new ArrayList<>();
        move = "";

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

        //Links finish button and initially sets it to invisible
        finish = (Button) findViewById(R.id.finishSolveButton);
        finish.setVisibility(View.GONE);

        //Switches view to the solve menu
        finish.setOnClickListener(v -> {
            Intent intent = new Intent(SolutionActivity.this, SolveMenuActivity.class);
            startActivity(intent);
        });

        stepCounter = (TextView) findViewById(R.id.stepNumber);
        instructions = (TextView) findViewById(R.id.instructionText);
        moveText = (TextView) findViewById(R.id.moveListText);

        nextStep = (Button) findViewById(R.id.nextStepButton);
        replayStep = (Button) findViewById(R.id.replayStepButton);

        replayStep.setEnabled(false);

        replayStep.setOnClickListener(v -> {
            replayStep.setEnabled(false);
            //Resets cube to state before move
            animCube.setCubeModel(state);

            //Short delay
            try {
                Thread.sleep(600);
            }catch(Exception e) {
                e.printStackTrace();
            }

            //Applies the move again
            ArrayList<String> moveList = retrieveNextMoveAndUpdate();
            moveText.setText(String.join(" ", moveList));
            animCube.setMoveSequence(String.join(" ", moveList));
            Runnable runnable = () -> handler.post(() -> animCube.animateMoveSequence());
            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            replayStep.setEnabled(true);
        });

        nextStep.setOnClickListener(v -> {
            nextStep.setEnabled(false);
            //Sets the text for certain variables
            if(currentStep == 0) {
                nextStep.setText("Next Step");
                //enables the previous step button, as the user will be on at least step 1
                replayStep.setEnabled(true);
            }
            //Increments current step, and then animates cube
            currentStep+=1;

            //Disables button if the last step is reached
            if(currentStep == totalSteps) {
                nextStep.setEnabled(false);
                finish.setVisibility(View.VISIBLE);
            }

            //Updates text boxes
            stepCounter.setText(currentStep + "/" + totalSteps);
            ArrayList<String> moveList = retrieveNextMoveAndUpdate();
            moveText.setText(String.join(" ", moveList));
            System.out.println(currentSection);
            //Sets up cube and animates it
            move = String.join(" ", moveList);
            animCube.setMoveSequence(move);
            Runnable runnable = () -> handler.post(() -> animCube.animateMoveSequence());
            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!(currentStep == totalSteps)) {
                nextStep.setEnabled(true);
            }
        });

        //Links AnimCube object to the one in the layout
        animCube = (AnimCube)findViewById(R.id.animCube);

        //Sets the cube to the user scramble
        animCube.setCubeModel(cubeState);

        SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
        String choice = sharedPreferences.getString("anim_speed", "1x");
        //Cube library uses 10 as default speed, and a higher number is a slower speed
        //Obtains a value by taking the choice, removing the 'x' at the end and converting to float
        float speed = 10 / (Float.valueOf(choice.substring(0, choice.length() - 1)));
        animCube.setSingleRotationSpeed(Math.round(speed));
        animCube.setDoubleRotationSpeed(Math.round(speed));

        //Solves the cube, and extracts the solution
        Solver.solve(MainActivity.usercube);

        //Sets the created list variables
        setListVariables("sunflower", Solver.sunflower);
        setListVariables("whiteCross", Solver.whiteCross);
        setListVariables("whiteCorners", Solver.whiteCorners);
        setListVariables("middleEdges", Solver.middleEdges);
        setListVariables("yellowCross", Solver.yellowCross);
        setListVariables("oll", Solver.oll);
        setListVariables("pll", Solver.pll);

        //+2 because starts at zero, then the flip step 1, and sunflower starts step 2
        whiteCrossThresh = sunflowerCode.size() + 2;
        //+1 because of flip
        whiteCornersThresh = whiteCrossThresh + whiteCrossCode.size() + 1;
        middleEdgesThresh = whiteCornersThresh + whiteCornersCode.size();
        //+1 because of flip
        yellowCrossThresh = middleEdgesThresh + middleEdgesCode.size() + 1;
        ollThresh = yellowCrossThresh + yellowCrossCode.size();
        pllThresh = ollThresh + ollCode.size();

        //Updates the step counter
        totalSteps = pllThresh + pllCode.size() - 1;

        stepCounter.setText("0/" + totalSteps);

        Store.resetScrambleVariables();
    }

    private void setListVariables(String name, ArrayList<ArrayList<Object>> list) {
        //Obtains size of list
        int size = list.size();

        //Loops through each item in list
        for(int i = 0; i < size; i++) {
            switch (name) {
                //Adds each item in list to each list depending on the input name
                case "sunflower": {
                    sunflowerEdges.add((String) list.get(i).get(0));
                    sunflowerCode.add((String) list.get(i).get(1));
                    sunflowerMoves.add((ArrayList<String>) list.get(i).get(2));
                    break;
                }
                case "whiteCross": {
                    whiteCrossEdges.add((String) list.get(i).get(0));
                    whiteCrossCode.add((String) list.get(i).get(1));
                    whiteCrossMoves.add((ArrayList<String>) list.get(i).get(2));
                    break;
                }
                case "whiteCorners": {
                    whiteCornersCorners.add((String) list.get(i).get(0));
                    whiteCornersCode.add((String) list.get(i).get(1));
                    whiteCornersMoves.add((ArrayList<String>) list.get(i).get(2));
                    break;
                }
                case "middleEdges": {
                    middleEdgesEdges.add((String) list.get(i).get(0));
                    middleEdgesCode.add((String) list.get(i).get(1));
                    middleEdgesMoves.add((ArrayList<String>) list.get(i).get(2));
                    break;
                }
                case "yellowCross": {
                    yellowCrossCode.add((String) list.get(i).get(0));
                    yellowCrossMoves.add((ArrayList<String>) list.get(i).get(1));
                    break;
                }
                case "oll": {
                    ollCode.add((String) list.get(i).get(0));
                    ollMoves.add((ArrayList<String>) list.get(i).get(1));
                    break;
                }
                case "pll": {
                    pllCode.add((String) list.get(i).get(0));
                    pllMoves.add((ArrayList<String>) list.get(i).get(1));
                    break;
                }
            }
        }
    }

    public static ArrayList<String> retrieveNextMoveAndUpdate() {
        //Stores state of cube before move is applied
        state = getCubeState(animCube);

        //creates temporary variable of the current step, which will be used later
        int newStep = currentStep;
        //Depending on how large the current step is, it will change the current section
        if(currentStep >= pllThresh) {
            currentSection = "pll";
            newStep = currentStep - pllThresh;
        }else if(currentStep >= ollThresh) {
            currentSection = "oll";
            newStep = currentStep - ollThresh;
        }else if(currentStep >= yellowCrossThresh) {
            currentSection = "yellowCross";
            newStep = currentStep - yellowCrossThresh;
        }else if(currentStep == yellowCrossThresh - 1) {
            currentSection = "postMiddleEdgesFlip";
        }else if(currentStep >= middleEdgesThresh) {
            currentSection = "middleEdges";
            newStep = currentStep - middleEdgesThresh;
        }else if(currentStep >= whiteCornersThresh) {
            currentSection = "whiteCorners";
            newStep = currentStep - whiteCornersThresh;
        }else if(currentStep == whiteCornersThresh - 1) {
            currentSection = "postWhiteCrossFlip";
        }else if(currentStep >= whiteCrossThresh) {
            currentSection = "whiteCross";
            newStep = currentStep - whiteCrossThresh;
        }else if(currentStep == 1) {
            currentSection = "initialFlip";
        }else {
            currentSection = "sunflower";
        }

        ArrayList<String> moves = new ArrayList<>();
        switch (currentSection) {
            case "initialFlip":
            case "postWhiteCrossFlip":
            case "postMiddleEdgesFlip":
                moves = new ArrayList<>();
                moves.add("Z2");
                break;
            case "sunflower":
                moves = sunflowerMoves.get(newStep - 2);
                break;
            case "whiteCross":
                moves = whiteCrossMoves.get(newStep);
                break;
            case "whiteCorners":
                moves = whiteCornersMoves.get(newStep);
                break;
            case "middleEdges":
                moves = middleEdgesMoves.get(newStep);
                break;
            case "yellowCross":
                moves = yellowCrossMoves.get(newStep);
                break;
            case "oll":
                moves = ollMoves.get(newStep);
                break;
            case "pll":
                moves = pllMoves.get(newStep);
                break;
        }

        //Updates instruction text box
        updateInstructionText(newStep);

        return moves;
    }

    @SuppressLint("SetTextI18n")
    public static void updateInstructionText(int index) {
        String firstPart, finalPart;
        //Switch case for the current stage of the solve, as the text will be set differently for each
        switch (currentSection) {
            //Can just set the text to tell the user to flip cube
            case "initialFlip": instructions.setText("Start by ensuring that your cube has the " +
                    "yellow centre face up, and the green centre facing forward."); break;
            case "postWhiteCrossFlip": instructions.setText("We now need to flip the cube over, " +
                    "so that the white centre faces up, and the green centre faces forward."); break;
            case "postMiddleEdgesFlip": instructions.setText("We now need to flip the cube over, " +
                    "so that the yellow centre faces up, and the green centre faces forward."); break;

            case "sunflower": {
                //Different indexes require a different starting sentence
                if(index - 2 == 0) {
                    firstPart = "The first step is to create a sunflower, where the four white edges " +
                            "surround the yellow centre. We will start with the " + sunflowerEdges.get(index - 2)
                    + " edge.";
                }else if(index - 2 == sunflowerEdges.size() - 1) {
                    firstPart = "Finally, let's move the " + sunflowerEdges.get(index - 2) + " edge.";
                }else {
                    firstPart = "Next, let's move the " + sunflowerEdges.get(index - 2) + " edge.";
                }
                //Sets last sentence based on the code for the edge
                switch (sunflowerCode.get(index - 2)) {
                    case "bottom": finalPart = "It is located face-down on the bottom layer, so we can " +
                            "simply rotate it up."; break;
                    case "bottom align": finalPart = "It is located face-down on the bottom layer, but we " +
                            "need to rotate the upper layer first before we move the edge up, so that we " +
                            "don't remove an existing solved edge."; break;
                    case "middle": finalPart = "It is located in the middle layer, so we can simply move it up"; break;
                    case "middle align": finalPart = "It is located in the middle layer, but first we must " +
                            "rotate the upper face before we move the edge up, so that we " +
                            "don't remove an existing solved edge."; break;
                    case "forward": finalPart = "It is located face-forward in the bottom layer, so " +
                            "we need to bring it to the middle layer first and then insert it."; break;
                    case "forward align": finalPart = "It is located face-forward in the bottom layer, " +
                            "so we need to bring it to the middle layer first. But before we do that, " +
                            "we must move the upper layer to avoid removing any existing solved edges."; break;
                    case "top": finalPart = "This edge is in the top layer already, but is flipped the " +
                            "wrong way up. We must move the edge out of the top layer and into the " +
                            "middle layer, then bring the top face towards it, and then a final move " +
                            "to insert it."; break;
                    default: finalPart = ""; break;
                }
                //Sets the text to a combination of the two sentences
                instructions.setText(firstPart + " " + finalPart);
                break;
            }

            case "whiteCross": {
                if(index == 0) {
                    if(currentStep == 2) {
                        firstPart = "The first step is to create a sunflower, but we already have one! " +
                                "Therefore we can start the next stage of creating a white cross. We will " +
                                "start with the " + whiteCrossEdges.get(index) + " edge.";
                    }else {
                        firstPart = "The next stage is to create a white cross. Let's start with the " +
                                whiteCrossEdges.get(index) + " edge.";
                    }
                }else if(index == whiteCrossEdges.size() - 1) {
                    firstPart = "Finally, let's move the " + whiteCrossEdges.get(index) + " edge.";
                }else {
                    firstPart = "Next, let's move the " + whiteCrossEdges.get(index) + " edge.";
                }

                switch (whiteCrossCode.get(index)) {
                    case "match": finalPart = "This edge lines up with the corresponding centre colour, " +
                            "so we can just move it down."; break;
                    case "align": finalPart = "We first need to rotate the upper face so that it matches " +
                            "with its corresponding centre colour, and then move it down."; break;
                    default: finalPart = "";
                }

                instructions.setText(firstPart + " " + finalPart);
                break;
            }

            case "whiteCorners": {
                if(index == 0) {
                    firstPart = "The next stage is to solve the white corners. We will start " +
                            "with the " + whiteCornersCorners.get(index) + " corner.";
                }else if(index == whiteCornersCorners.size() - 1) {
                    firstPart = "Finally, let's solve the " + whiteCornersCorners.get(index) + " corner.";
                }else {
                    firstPart = "Next, let's move the " + whiteCornersCorners.get(index) + " corner.";
                }

                switch (whiteCornersCode.get(index)) {
                    case "bottom forward": finalPart = "This corner is facing forward in the bottom " +
                            "layer and in between the right centre pieces. We can insert the corner " +
                            "as follows:"; break;
                    case "bottom forward align": finalPart = "This corner is facing forward in the " +
                            "bottom layer, but needs to be moved to be between the right centre pieces, " +
                            "and then inserted. This is achieved as follows:"; break;
                    case "top forward": finalPart = "This corner is in the top layer, facing forward. " +
                            "We need to remove it and place it in the bottom layer."; break;
                    case "top up": finalPart = "This corner is in the top layer facing up, but in " +
                            "the wrong place. We need to remove it and place it in the bottom layer."; break;
                    case "bottom down": finalPart = "This corner is in the bottom layer, but facing down. " +
                            "We need to adjust it so that it stays in the bottom layer, but faces forward."; break;
                    case "bottom down align": finalPart = "This corner is facing down in the bottom layer. " +
                            "We need to rotate the upper face so that there is a gap directly above this corner, " +
                            "fix the corner so that it faces forward, and then re-adjust the top layer."; break;
                    default: finalPart = ""; break;
                }

                instructions.setText(firstPart + " " + finalPart);
                break;
            }

            case "middleEdges": {
                if(index == 0) {
                    firstPart = "The next stage is to solve the edges in the middle layer. We will start " +
                            "with the " + middleEdgesEdges.get(index) + " edge.";
                }else if(index == middleEdgesEdges.size() - 1) {
                    firstPart = "Finally, let's solve the " + middleEdgesEdges.get(index) + " edge.";
                }else {
                    firstPart = "Next, let's move the " + middleEdgesEdges.get(index) + " edge.";
                }

                switch (middleEdgesCode.get(index)) {
                    case "normal": finalPart = "This edge is in the bottom layer, and lines up with " +
                            "the right centre piece. We apply the following move, which joins up the " +
                            "edge with the white corner, and then re-inserts the corner."; break;
                    case "normal align": finalPart = "This edge is in the bottom layer, but needs to be " +
                            "in the correct position before it is paired with its matching white corner, " +
                            "and then inserted."; break;
                    case "remove": finalPart = "This edge is in the middle layer, but in the wrong place. " +
                            "We need to remove the edge, and then re-insert the white corner that we removed " +
                            "in the process."; break;
                    default: finalPart = "";
                }

                instructions.setText(firstPart + " " + finalPart);
                break;
            }

            case "yellowCross": {
                switch (yellowCrossCode.get(index)) {
                    case "done": instructions.setText("The next step is to create a yellow cross, " +
                            "but we already have one!"); break;
                    case "zero": instructions.setText("The next step is to create a yellow cross. " +
                            "Currently we have no yellow edges face up, so we need to apply the " +
                            "following algorithm. This can be viewed/learned in the 'algorithms' section " +
                            "of this app."); break;
                    case "adj": instructions.setText("The next step is to create a yellow cross. " +
                            "Currently there are two yellow edges adjacent to each other, so we " +
                            "will apply the following algorithm. This can be viewed/learned in the " +
                            "'algorithms' section of this app."); break;
                    case "adj align": instructions.setText("The next step is to create a yellow cross. " +
                            "Currently there are two yellow edges adjacent to each other, but they need" +
                            "to be rotated first to be in a certain position. Then we " +
                            "will apply the following algorithm. This can be viewed/learned in the " +
                            "'algorithms' section of this app."); break;
                    case "line": instructions.setText("The next step is to create a yellow cross. " +
                            "Currently there are two yellow edges opposite each other, so we " +
                            "will apply the following algorithm. This can be viewed/learned in the " +
                            "'algorithms' section of this app."); break;
                    case "line align": instructions.setText("The next step is to create a yellow cross. " +
                            "Currently there are two yellow edges opposite each other, but they need" +
                            "to be rotated first to be in a certain position. Then we " +
                            "will apply the following algorithm. This can be viewed/learned in the " +
                            "'algorithms' section of this app."); break;
                    default: break;
                }

                break;
            }

            case "oll": {
                if(ollCode.get(0).equals("done")) {
                    instructions.setText("The next stage is orienting the yellow stickers, so " +
                            "that the entire upper face is yellow. We already have that, so we can " +
                            "move on!");
                }else if(ollCode.get(0).contains("align")) {
                    instructions.setText("The next stage is orienting the yellow stickers, so " +
                            "that the entire upper face is yellow. There are 7 possibles cases at " +
                            "this point, and we have the '" + ollCode.get(0).substring(0, ollCode.get(0).length() - 6)
                            + "' case. First we rotate the upper face so it is in the right position, and then " +
                            "apply an algorithm, which can also be viewed in the algorithm section of the app.");
                }else {
                    instructions.setText("The next stage is orienting the yellow stickers, so " +
                            "that the entire upper face is yellow. There are 7 possibles cases at " +
                            "this point, and we have the '" + ollCode.get(0).substring(0, ollCode.get(0).length() - 6)
                            + "' case. We must apply the following algorithm, which can also be viewed in the " +
                            "algorithm section of the app.");
                }

                break;
            }

            case "pll": {
                if(index == 0) {
                    switch (pllCode.get(0)) {
                        case "done": instructions.setText("The next stage is permuting all of the corners " +
                                "in the top layer so that they are in the right positions, but ours are " +
                                "already completed!"); break;
                        case "zero": instructions.setText("The next stage is permuting all of the corners " +
                                "in the top layer so that they are in the right positions. Currently we have " +
                                "no corners correctly positioned, so we will perform the following algorithm. " +
                                "This can be viewed in the algorithms section of the app."); break;
                        case "1": instructions.setText("The next stage is permuting all of the corners " +
                                "in the top layer so that they are in the right positions. Currently we have " +
                                "2 corners correctly positioned, so we will perform the following algorithm. " +
                                "This can be viewed in the algorithms section of the app."); break;
                        case "1 align": instructions.setText("The next stage is permuting all of the corners " +
                                "in the top layer so that they are in the right positions. Currently we have " +
                                "2 corners correctly positioned, but first they need to be positioned to the " +
                                "left of the cube. Then we will perform the following algorithm. " +
                                "This can be viewed in the algorithms section of the app."); break;
                    }
                }else if(index == 1) {
                    if(pllCode.get(1).contains("complete")) {
                        instructions.setText("The next stage is permuting all of the edges " +
                                "in the top layer, but all of our edges are already permuted!");
                    }else if(pllCode.get(1).contains("align")) {
                        instructions.setText("The next stage is permuting all of the edges " +
                                "in the top layer. There are 4 possibles cases at this point, and " +
                                "we have the '" + pllCode.get(1).substring(0, pllCode.get(1).length() - 6) +
                                "' case, but first we need to rotate the upper face to move it to the right " +
                                "position. Then, we apply the following algorithm. This can be viewed in " +
                                "the algorithms section of this app.");
                    }else {
                        instructions.setText("The next stage is permuting all of the edges " +
                                "in the top layer. There are 4 possibles cases at this point, and " +
                                "we have the '" + pllCode.get(1) +
                                "' case, and so we apply the following algorithm. This can be viewed in " +
                                "the algorithms section of this app.");
                    }
                }else {
                    if(pllMoves.get(2).size() == 0) {
                        instructions.setText("The cube has been solved!");
                    }else {
                        instructions.setText("One final move is needed to align the top face with " +
                                "the rest of the cube, and then the cube has been solved!");
                    }
                }
                break;
            }
        }
    }

    public static String getCubeState(AnimCube cube) {
        //Creates new appendable string
        StringBuilder state = new StringBuilder();
        //Appends each sticker from cube model to the string
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 9; j++) {
                state.append(cube.getCubeModel()[i][j]);
            }
        }
        //returns the string
        return state.toString();
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