package com.matthew.unscramble;

import android.app.Application;

public class Store extends Application {

    // stored in this colour order R G B W Y O
    public static int[][] calibratedColours = {{164, 7, 9}, {0, 213, 0}, {17, 99, 169}, {207, 207, 203}, {175 ,184, 8}, {210, 13, 2}};

    public static int sidesDone = 0;

    public static String[] actualCols = {"W", "W", "W", "W", "W", "W", "W", "W", "W"};

    public static boolean usingCamera = false;


    public static String[][] whiteSide = Utils.completeface1;
    public static String[][] greenSide = Utils.completeface2;
    public static String[][] redSide = Utils.completeface3;
    public static String[][] blueSide = Utils.completeface4;
    public static String[][] orangeSide = Utils.completeface5;
    public static String[][] yellowSide = Utils.completeface6;

    public static void resetScrambleVariables() {
        //Resets all of the store variables to default once a scramble has been inputted
        sidesDone = 0;
        usingCamera = false;
        whiteSide = Utils.completeface1;
        greenSide = Utils.completeface2;
        redSide = Utils.completeface3;
        blueSide = Utils.completeface4;
        orangeSide = Utils.completeface5;
        yellowSide = Utils.completeface6;
        actualCols = new String[]{"W", "W", "W", "W", "W", "W", "W", "W", "W"};
    }
}
