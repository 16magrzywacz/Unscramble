package com.matthew.unscramble;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Verify {

    public static String verify(String[][][] sides) {
        //First check
        String check1 = checkNineStickers(sides);
        if(check1 != null) {
            return check1;
        }

        //Creates a cube with the input sides
        Cube testCube = new Cube(sides[4], sides[2], sides[0], sides[3], sides[1], sides[5]);

        //second check (opposite colours on corners/edges)
        String check2 = checkNoOppColEdgesCorners(testCube);
        if(check2 != null) {
            return check2;
        }

        //third check (twisted corners)
        String check3 = checkNoTwistedCorners(testCube);
        if(check3 != null) {
            return check3;
        }

        //fourth check (flipped edges, parity errors)
        String check4 = otherChecks(testCube);
        if(check4 != null) {
            return check4;
        }
        //If all checks are okay, return null
        return null;
    }

    //Checking to see if there are 9 of each colour
    public static String checkNineStickers(String[][][] sides) {
        //Creates a variable holding number of stickers of each colour
        int[] count = new int[6];

        //goes through each sticker on each side
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    switch (sides[i][j][k]) {
                        //adds to the relevant index depending on the colour
                        case "R": count[0]+=1; continue;
                        case "O": count[1]+=1; continue;
                        case "G": count[2]+=1; continue;
                        case "B": count[3]+=1; continue;
                        case "W": count[4]+=1; continue;
                        case "Y": count[5]+=1;
                    }
                }
            }
        }
        //If any count item is greater than nine then return 'error' code 1
        for(int i = 0; i < 6; i ++) {
            if(count[i] > 9) {
                return "1";
            }
        }
        return null;
    }

    //checks to see if any corners or edges contain colour that are opposite each other
    public static String checkNoOppColEdgesCorners(Cube cube) {
        //Creates a dictionary of opposite colours
        Map<String, String> opposites = new HashMap<String, String>();
        opposites.put("G", "B");
        opposites.put("B", "G");
        opposites.put("R", "O");
        opposites.put("O", "R");
        opposites.put("W", "Y");
        opposites.put("Y", "W");

        //Creates list of letters used for retrieving edges/corners
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"};

        for(String s : letters) {
            //Gets the current corner/edge
            String corner = Utils.getCurrentCorner(s, cube);
            String edge = Utils.getCurrentEdge(s, cube);
            //If the first sticker is the opposite of the second sticker, return 'error' code 2
            if(edge.substring(0,1).equals(opposites.get(edge.substring(1,2)))) {
                return "2";
            //If any of the stickers are opposite to each other, return 'error' code 2
            }else if(corner.substring(0,1).equals(opposites.get(corner.substring(1,2)))
                    || corner.substring(0,1).equals(opposites.get(corner.substring(2,3)))
                    || corner.substring(1,2).equals(opposites.get(corner.substring(2,3)))) {
                return "2";
            }
        }

        return null;
    }

    public static String checkNoTwistedCorners(Cube cube) {

        //Creates array of all the corners
        String[] corners = {Utils.getCurrentCorner("A", cube), Utils.getCurrentCorner("B", cube), Utils.getCurrentCorner("C", cube),
                Utils.getCurrentCorner("D", cube), Utils.getCurrentCorner("U", cube), Utils.getCurrentCorner("V", cube),
                Utils.getCurrentCorner("W", cube), Utils.getCurrentCorner("X", cube)};

        int count = 0;

        //Depending on position of white/yellow sticker on each corner, modify the count variable
        for (String corner : corners) {
            //If first sticker is white/yellow
            if (corner.charAt(0) == 'W' || corner.charAt(0) == 'Y') {
                count += 0;
            //If second sticker is white/yellow
            } else if (corner.charAt(1) == 'W' || corner.charAt(1) == 'Y') {
                count += 2;
            //If third sticker is white/yellow
            } else {
                count += 1;
            }
        }

        //If the count variable is a multiple of 3, it is a regular scramble
        if(count % 3 == 0) {
            return null;
        //If not then it is an invalid scramble and fails the test
        }else {
            return "3";
        }
    }

    public static String otherChecks(Cube cube) {
        //Creates a duplicate cube object
        Cube tempCube = cube;

        //Attempts to solve cube, and returns a message stating if it has solved successfully or not
        String check = Solver.solve(tempCube);

        //If the solve encounters either of these cases, then return either a 4 or a 5
        if(check.equals("flippedEdge")) {
            return "4";
        }else if(check.equals("parity")) {
            return "5";
        }
        return null;
    }

}
