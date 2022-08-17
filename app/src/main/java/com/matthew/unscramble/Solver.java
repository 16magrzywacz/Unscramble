package com.matthew.unscramble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Solver {

    public static List<String> solution = new ArrayList<>();

    public static String solve(Cube cube) {
        solution.clear();
        makeSunflower(cube);
        makeWhiteCross(cube);
        insertWhiteCorners(cube);
        insertMiddleEdges(cube);
        String edgeFlipCheck = makeYellowCross(cube);
        if(edgeFlipCheck.equals("bad")) {
            return "flippedEdge";
        }
        orientLastLayer(cube);
        permuteLastLayer(cube);
        if(!checkSolved(cube)) {
            return "parity";
        }
        System.out.println(solution);
        return "solved";
    }

    public static boolean checkSolved(Cube cube) {
        cube.move("Z2");
        solution.add("Z2");
        //Goes through each sticker on cube and checks against each colour, to see if it is solved
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(!cube.getWhiteFace()[i][j].equals("W") || !cube.getGreenFace()[i][j].equals("G") ||
                        !cube.getRedFace()[i][j].equals("R") || !cube.getBlueFace()[i][j].equals("B") ||
                        !cube.getOrangeFace()[i][j].equals("O") || !cube.getYellowFace()[i][j].equals("Y")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void makeSunflower(Cube cube) {
        cube.move("Z2"); //rotates the cube 180 degrees
        solution.add("Z2");
        List<String> currentPetals = getPetals(cube); //creates a new list of petals currently around the cube

        //Loops while there are less than 4 white edges around yellow centre
        while(currentPetals.size() < 4) {

            // loops through each item in the list of white edges around white centre on bottom
            for(String s : getBottomEdges(cube)) {
                // these statements cycle through position, and applies the appropriate move to insert
                // while loop checks to see if a white piece is already directly above, if so, continually
                // rotate white face until there isn't a white edge above
                // then adds the edge to the list of currently solved petals
                if(s == "U") {
                    while(cube.getWhiteFace()[2][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("F2");
                    solution.add("F2");
                    currentPetals.add(cube.getGreenFace()[0][1]);
                }
                if(s == "V") {
                    while(cube.getWhiteFace()[1][2] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("R2");
                    solution.add("R2");
                    currentPetals.add(cube.getRedFace()[0][1]);
                }
                if(s == "W") {
                    while(cube.getWhiteFace()[0][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("B2");
                    solution.add("B2");
                    currentPetals.add(cube.getBlueFace()[0][1]);
                }
                if(s == "X") {
                    while(cube.getWhiteFace()[1][0] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("L2");
                    solution.add("L2");
                    currentPetals.add(cube.getOrangeFace()[0][1]);
                }
            }

            //Deals with edges in the middle layer
            while(getNextMiddleWhiteEdge(cube) != null) {
                // these statements cycle through position, and applies the appropriate move to insert
                // while loop checks to see if a white piece is already directly above, if so, continually
                // rotate white face until there isn't a white edge above
                // then adds the edge to the list of currently solved petals
                String pos = getNextMiddleWhiteEdge(cube);
                if(pos == "J") {
                    while(cube.getWhiteFace()[1][2] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("R");
                    solution.add("R");
                    currentPetals.add(cube.getRedFace()[0][1]);
                }
                if(pos == "L") {
                    while(cube.getWhiteFace()[1][0] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("L'");
                    solution.add("L'");
                    currentPetals.add(cube.getOrangeFace()[0][1]);
                }
                if(pos == "N") {
                    while(cube.getWhiteFace()[0][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("B");
                    solution.add("B");
                    currentPetals.add(cube.getBlueFace()[0][1]);
                }
                if(pos == "P") {
                    while(cube.getWhiteFace()[2][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("F'");
                    solution.add("F'");
                    currentPetals.add(cube.getGreenFace()[0][1]);
                }
                if(pos == "R") {
                    while(cube.getWhiteFace()[1][0] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("L");
                    solution.add("L");
                    currentPetals.add(cube.getOrangeFace()[0][1]);
                }
                if(pos == "T") {
                    while(cube.getWhiteFace()[1][2] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("R'");
                    solution.add("R'");
                    currentPetals.add(cube.getRedFace()[0][1]);
                }
                if(pos == "F") {
                    while(cube.getWhiteFace()[2][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("F");
                    solution.add("F");
                    currentPetals.add(cube.getGreenFace()[0][1]);
                }
                if(pos == "H") {
                    while(cube.getWhiteFace()[0][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move("B'");
                    solution.add("B'");
                    currentPetals.add(cube.getBlueFace()[0][1]);
                }
            }

            while(getNextBottomWhiteEdge(cube) != null) {
                String pos = getNextBottomWhiteEdge(cube);
                // these statements cycle through position, and applies the appropriate move to insert
                // while loop checks to see if a white piece is already directly above, if so, continually
                // rotate white face until there isn't a white edge above
                // then adds the edge to the list of currently solved petals
                if(pos == "K") {
                    while(cube.getWhiteFace()[2][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move(new String[]{"F", "U", "L'"});
                    solution.addAll(Arrays.asList("F", "U", "L'"));
                    currentPetals.add(cube.getOrangeFace()[0][1]);
                }
                if(pos == "O") {
                    while(cube.getWhiteFace()[1][2] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move(new String[]{"R", "U", "F'"});
                    solution.addAll(Arrays.asList("R", "U", "F'"));
                    currentPetals.add(cube.getGreenFace()[0][1]);
                }
                if(pos == "S") {
                    while(cube.getWhiteFace()[0][1] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move(new String[]{"B", "U", "R'"});
                    solution.addAll(Arrays.asList("B", "U", "R'"));
                    currentPetals.add(cube.getRedFace()[0][1]);
                }
                if(pos == "G") {
                    while(cube.getWhiteFace()[1][0] == "W") {
                        cube.move("U");
                        solution.add("U");
                    }
                    cube.move(new String[]{"L", "U", "B'"});
                    solution.addAll(Arrays.asList("L", "U", "B'"));
                    currentPetals.add(cube.getBlueFace()[0][1]);
                }
            }

            while(getNextTopWhiteEdge(cube) != null) {
                String pos = getNextTopWhiteEdge(cube);
                // these statements cycle through position, and applies the appropriate move to insert
                // while loop checks to see if a white piece is already directly above, if so, continually
                // rotate white face until there isn't a white edge above
                // then adds the edge to the list of currently solved petals
                if(pos == "I") {
                    cube.move(new String[]{"F'", "U", "L'"});
                    solution.addAll(Arrays.asList("F'", "U", "L'"));
                    currentPetals.add(cube.getOrangeFace()[0][1]);
                }
                if(pos == "M") {
                    cube.move(new String[]{"R'", "U", "F'"});
                    solution.addAll(Arrays.asList("R'", "U", "F'"));
                    currentPetals.add(cube.getGreenFace()[0][1]);
                }
                if(pos == "Q") {
                    cube.move(new String[]{"B'", "U", "R'"});
                    solution.addAll(Arrays.asList("B'", "U", "R'"));
                    currentPetals.add(cube.getRedFace()[0][1]);
                }
                if(pos == "E") {
                    cube.move(new String[]{"L'", "U", "B'"});
                    solution.addAll(Arrays.asList("L'", "U", "B'"));
                    currentPetals.add(cube.getBlueFace()[0][1]);
                }
            }
        }
    }

    public static String getNextTopWhiteEdge(Cube cube) {
        //Creates an array of all the stickers of edges in the top layer, facing forwards
        String[] edges = {cube.getGreenFace()[0][1], cube.getRedFace()[0][1], cube.getBlueFace()[0][1], cube.getOrangeFace()[0][1]};

        //Creates an array where the ith sticker in the above array corresponds to the ith position on the cube in this array
        String[] positions = {"I", "M", "Q", "E"};

        //Loops through the array of stickers
        for(int i = 0; i < edges.length; i++) {

            //If a white sticker is encountered
            if(Objects.equals(edges[i], "W")) {
                //Return it's corresponding position
                return positions[i];
            }
        }
        //If no white sticker found then return null
        return null;
    }

    public static String getNextBottomWhiteEdge(Cube cube) {
        //Creates an array of all the stickers of edges in the bottom layer, facing forwards
        String[] edges = {cube.getGreenFace()[2][1], cube.getRedFace()[2][1], cube.getBlueFace()[2][1], cube.getOrangeFace()[2][1]};

        //Creates an array where the ith sticker in the above array corresponds to the ith position on the cube in this array
        String[] positions = {"K", "O", "S", "G"};

        //Loops through the array of stickers
        for(int i = 0; i < edges.length; i++) {

            //If a white sticker is encountered
            if(Objects.equals(edges[i], "W")) {
                //Return it's corresponding position
                return positions[i];
            }
        }
        //If no white sticker found then return null
        return null;
    }

    public static String getNextMiddleWhiteEdge(Cube cube) {

        //Creates an array of all the stickers of edges in the middle layer
        String edges = Utils.getCurrentEdge("J", cube) + Utils.getCurrentEdge("N", cube) +
                Utils.getCurrentEdge("R", cube) + Utils.getCurrentEdge("F", cube);
        String[] edgesArray = edges.split("");

        //Creates an array where the ith sticker in the above array corresponds to the ith position on the cube in this array
        String[] positions = {"J", "P", "N", "T", "R", "H", "F", "L"};

        //Loops through the array of stickers
        for(int i = 0; i < edgesArray.length; i++) {

            //If a white sticker is encountered
            if(Objects.equals(edgesArray[i], "W")) {
                //Return it's corresponding position
                return positions[i];
            }
        }
        //If no white sticker found then return null
        return null;
    }

    public static String[] getBottomEdges(Cube cube) {
        List<String> positions = new ArrayList<>(); //creates a list

        // if statements to go through each surrounding sticker of the white centre on the bottom
        // If it is white, then it adds the appropriate position of that corner to the list
        if(cube.getYellowFace()[0][1] == "W") {
            positions.add("U");
        }
        if(cube.getYellowFace()[1][2] == "W") {
            positions.add("V");
        }
        if(cube.getYellowFace()[2][1] == "W") {
            positions.add("W");
        }
        if(cube.getYellowFace()[1][0] == "W") {
            positions.add("X");
        }
        return positions.toArray(new String[positions.size()]); // Converts list to array and then returns it
    }

    public static List<String> getPetals(Cube cube) {
        //Creates arrays of the colours of the stickers around the yellow centre, as well as their adjacent stickers
        String[] up = {cube.getWhiteFace()[0][1], cube.getWhiteFace()[1][0], cube.getWhiteFace()[1][2], cube.getWhiteFace()[2][1]};
        String[] down = {cube.getBlueFace()[0][1], cube.getOrangeFace()[0][1], cube.getRedFace()[0][1], cube.getGreenFace()[0][1]};

        List<String> result = new ArrayList<>(); //Creates a new list

        //Goes through each item in the up list, and if it is white, then append it's respective edge colour to the list
        for(int i = 0; i < 4; i++) {
            if(up[i].equals("W")) {
                result.add(down[i]);
            }
        }
        return(result); //returns the list
    }

    private static void makeWhiteCross(Cube cube) {
        //set ups some arrays, the first array is the order of the centre stickers starting from green and working anticlockwise round the cube
        //next array is the 2 colours of the white edges on the sunflower, going in the same order as the previous array
        //final array is the respective positions of each of the edges
        String[] centres = {"G", "O", "B", "R"};
        String[] edges = getSunflowerEdges(cube);
        String[] positions = {"I", "M", "Q", "E"};
        //variable used to count how many edges have been done
        int edgesDone = 0;
        while(edgesDone < 4) {
            edges = getSunflowerEdges(cube);
            edgesDone += checkMatchedPetal(cube, edges, centres, positions);
            edges = getSunflowerEdges(cube);
            edgesDone += checkMultiMovePetal(cube, edges, centres, positions);
        }
        cube.move("Z2");
        solution.add("Z2");
    }

    // this function simply returns a list of the current edges around the sunflower
    // This function is needed as these edges keep changing so the latest 'update' is always needed
    public static String[] getSunflowerEdges(Cube cube) {
        return new String[]{Utils.getCurrentEdge("I", cube), Utils.getCurrentEdge("M", cube),
                Utils.getCurrentEdge("Q", cube), Utils.getCurrentEdge("E", cube)};
    }

    public static int checkMatchedPetal(Cube cube, String[] edges, String[] centres, String[] positions) {
        //creates an initial return value
        int ret = 0;
        //loops through each edge
        for(int i = 0; i < 4; i++) {
            //If the top of the edge is white, ie does the current edge need to be solved
            if(edges[i].substring(1, 2).equals("W")) {
                //If it is a white edge, does the other colour match the centre it is above
                if(edges[i].substring(0, 1).equals(centres[i])) {

                    //Applies a move to insert it based in the edge's position
                    switch (positions[i]) {
                        case "I": cube.move("F2"); solution.add("F2"); break;
                        case "M": cube.move("R2"); solution.add("R2"); break;
                        case "Q": cube.move("B2"); solution.add("B2"); break;
                        case "E": cube.move("L2"); solution.add("L2"); break;
                    }
                    //increments return value by one
                    ret += 1;
                }
            }
        }
        return ret;
    }

    public static int checkMultiMovePetal(Cube cube, String[] edges, String[] centres, String[] positions) {
        int ret = 0;
        //loops through each edge
        for(int i = 0; i < 4; i++) {
            //If the top of the edge is white, ie does the current edge need to be solved
            if(edges[i].substring(1, 2).equals("W")) {
                //if the colour on the edge is to the left of the desired centre colour
                if(edges[i].substring(0, 1).equals(centres[(i+1) % 4])) {
                    //Move to top face to match edge with centre
                    cube.move("U'");
                    solution.add("U'");

                    //Depending on the edge's original position, apply the follwing move
                    switch (positions[i]) {
                        case "I": cube.move("R2"); solution.add("R2"); break;
                        case "M": cube.move("B2"); solution.add("B2"); break;
                        case "Q": cube.move("L2"); solution.add("L2"); break;
                        case "E": cube.move("F2"); solution.add("F2"); break;
                    }
                    //Add 1 to the return value, and then break from the for loop (as we don't want to solve another edge)
                    ret += 1;
                    break;
                    //if the colour on the edge is to the right of the desired centre colour
                }else if(edges[i].substring(0, 1).equals(centres[(i+3) % 4])) {
                    //Move to top face to match edge with centre
                    cube.move("U");
                    solution.add("U");

                    //Depending on the edge's original position, apply the follwing move
                    switch (positions[i]) {
                        case "I": cube.move("L2"); solution.add("L2"); break;
                        case "M": cube.move("F2"); solution.add("F2"); break;
                        case "Q": cube.move("R2"); solution.add("R2"); break;
                        case "E": cube.move("B2"); solution.add("B2"); break;
                    }
                    ret += 1;
                    break;
                    //if the colour on the edge is opposite to the desired centre colour
                }else if(edges[i].substring(0, 1).equals(centres[(i+2) % 4])) {
                    //Move to top face to match edge with centre
                    cube.move("U2");
                    solution.add("U2");

                    //Depending on the edge's original position, apply the follwing move
                    switch (positions[i]) {
                        case "I": cube.move("B2"); solution.add("B2"); break;
                        case "M": cube.move("L2"); solution.add("L2"); break;
                        case "Q": cube.move("F2"); solution.add("F2"); break;
                        case "E": cube.move("R2"); solution.add("R2"); break;
                    }
                    //Add 1 to the return value, and then break from the for loop (as we don't want to solve another edge)
                    ret += 1;
                    break;
                }
            }
        }
        return ret;
    }

    private static void insertWhiteCorners(Cube cube) {
        List<String> completeCorners = getCompletedWhiteCorners(cube);

        while(completeCorners.size() < 4) {
            String corner = insertNextWhiteCorner(cube);
            if(corner != null) {
                completeCorners.add(corner);
            }
        }
    }

    public static String insertNextWhiteCorner(Cube cube) {

        String[] bottomForwardCorners = getBottomForwardCorners(cube);

        String bottomForwardCorner = insertNextBottomForwardCorner(cube, bottomForwardCorners);

        if(bottomForwardCorner != null) {
            return bottomForwardCorner;
        }

        String badCorner = setupBadCorner(cube);

        if(badCorner != null) {
            bottomForwardCorners = getBottomForwardCorners(cube);
            bottomForwardCorner = insertNextBottomForwardCorner(cube, bottomForwardCorners);
            if(bottomForwardCorner != null) {
                return bottomForwardCorner;
            }
        }

        return null;
    }

    public static String[] getBottomForwardCorners(Cube cube) {
        return new String[]{Utils.getCurrentCorner("K", cube), Utils.getCurrentCorner("P", cube),
                Utils.getCurrentCorner("O", cube), Utils.getCurrentCorner("T", cube), Utils.getCurrentCorner("S", cube),
                Utils.getCurrentCorner("H", cube), Utils.getCurrentCorner("G", cube), Utils.getCurrentCorner("L", cube)};
    }

    private static String setupBadCorner(Cube cube) {

        //Creates an array of all the top-forward corners
        String[] topForwardCorners = {Utils.getCurrentCorner("J", cube), Utils.getCurrentCorner("M", cube),
                Utils.getCurrentCorner("N", cube), Utils.getCurrentCorner("Q", cube), Utils.getCurrentCorner("R", cube),
                Utils.getCurrentCorner("E", cube), Utils.getCurrentCorner("F", cube), Utils.getCurrentCorner("I", cube)};

        //Creates an array of all the bottom-down corners
        String[] bottomDownCorners = {Utils.getCurrentCorner("V", cube), Utils.getCurrentCorner("W", cube),
                Utils.getCurrentCorner("X", cube), Utils.getCurrentCorner("U", cube)};

        //creates an array of all the top-up corners
        String[] topUpCorners = {Utils.getCurrentCorner("C", cube), Utils.getCurrentCorner("B", cube),
                Utils.getCurrentCorner("A", cube), Utils.getCurrentCorner("D", cube)};

        //Creates array of some corresponding position letters for each of the above arrays
        String[] bdPos = {"V", "W", "X", "U"};
        String[] tuPos = {"C", "B", "A", "D"};
        String[] tfPos = {"J", "M", "N", "Q", "R", "E", "F", "I"};

        //Creates an array of pairs of adjacent centre colours, used for the top-up corners
        String[] tuCentres = {"RG", "BR", "OB", "GO"};

        //loops through bottom down corners
        for(int i = 0; i < bottomDownCorners.length; i++) {
            String[] corners = bottomDownCorners;
            //If the corner is white (facing down)
            if(corners[i].substring(0, 1).equals("W")) {
                int adjust = 0;
                //Rotates upper face so that there is an 'empty' space above the current corner to be corrected
                while(Utils.getCurrentCorner(tuPos[i], cube).substring(0,1).equals("W")) {
                    cube.move("U");
                    solution.add("U");
                    adjust+=1;
                }
                //Applies relevant algorithm based on it's position
                switch(bdPos[i]) {
                    case "V": cube.move(Algorithm.vCornerRemove); solution.addAll(Arrays.asList(Algorithm.vCornerRemove)); break;
                    case "W": cube.move(Algorithm.wCornerRemove); solution.addAll(Arrays.asList(Algorithm.wCornerRemove)); break;
                    case "X": cube.move(Algorithm.xCornerRemove); solution.addAll(Arrays.asList(Algorithm.xCornerRemove)); break;
                    case "U": cube.move(Algorithm.uCornerRemove); solution.addAll(Arrays.asList(Algorithm.uCornerRemove)); break;
                }
                //Re-rotates the upper face so that is returns back to normal
                for(int j = 0; j < adjust; j++) {
                    cube.move("U'");
                    solution.add("U'");
                }

                return corners[i];
            }
        }

        //loops through top forward corners
        for(int i = 0; i <topForwardCorners.length; i++) {
            String[] corners = topForwardCorners;
            //If the corner is white (facing forward)
            if(corners[i].substring(0, 1).equals("W")) {
                switch(tfPos[i]) {
                    case "J": cube.move(Algorithm.jCornerRemove); solution.addAll(Arrays.asList(Algorithm.jCornerRemove)); return corners[i];
                    case "M": cube.move(Algorithm.mCornerRemove); solution.addAll(Arrays.asList(Algorithm.mCornerRemove)); return corners[i];
                    case "N": cube.move(Algorithm.nCornerRemove); solution.addAll(Arrays.asList(Algorithm.nCornerRemove)); return corners[i];
                    case "Q": cube.move(Algorithm.qCornerRemove); solution.addAll(Arrays.asList(Algorithm.qCornerRemove)); return corners[i];
                    case "R": cube.move(Algorithm.rCornerRemove); solution.addAll(Arrays.asList(Algorithm.rCornerRemove)); return corners[i];
                    case "E": cube.move(Algorithm.eCornerRemove); solution.addAll(Arrays.asList(Algorithm.eCornerRemove)); return corners[i];
                    case "F": cube.move(Algorithm.fCornerRemove); solution.addAll(Arrays.asList(Algorithm.fCornerRemove)); return corners[i];
                    case "I": cube.move(Algorithm.iCornerRemove); solution.addAll(Arrays.asList(Algorithm.iCornerRemove)); return corners[i];
                }
            }
        }

        //loops through top up corners
        for(int i = 0; i < topUpCorners.length; i++) {
            String[] corners = topUpCorners;
            //If the corner is white (facing up)
            if(corners[i].substring(0, 1).equals("W")) {

                String[] otherCentres = {corners[i].substring(1, 2), corners[i].substring(2,3)};

                if(!(otherCentres[0].equals(tuCentres[i].substring(0, 1)) && otherCentres[1].equals(tuCentres[i].substring(1, 2)))) {
                    switch(tuPos[i]) {
                        case "C": cube.move(Algorithm.cCornerRemove); solution.addAll(Arrays.asList(Algorithm.cCornerRemove)); return corners[i];
                        case "B": cube.move(Algorithm.bCornerRemove); solution.addAll(Arrays.asList(Algorithm.bCornerRemove)); return corners[i];
                        case "A": cube.move(Algorithm.aCornerRemove); solution.addAll(Arrays.asList(Algorithm.aCornerRemove)); return corners[i];
                        case "D": cube.move(Algorithm.dCornerRemove); solution.addAll(Arrays.asList(Algorithm.dCornerRemove)); return corners[i];
                    }
                }
            }
        }

        return null;

    }

    private static String insertNextBottomForwardCorner(Cube cube, String[] corners) {

        //Array of corresponding positions
        String[] pos = {"K", "P", "O", "T", "S", "H", "G", "L"};

        for(int i = 0; i < corners.length; i++) {
            if(corners[i].substring(0, 1).equals("W")) {
                //Extracts the 2 other colours of the white corner
                String[] otherCentres = {corners[i].substring(1, 2), corners[i].substring(2,3)};
                int adjust = 0;
                //Goes through cases for the 4 possible other colours
                if(otherCentres[0].equals("R") && otherCentres[1].equals("G")){
                    //If the white is on the right of a side
                    if((i % 2) == 0) {
                        //while the edge is not in the right place, keep rotating the bottom layer
                        while(!pos[(i+adjust) % 8].equals("K")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        //apply the relevant moves
                        cube.move(Algorithm.kCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.kCornerInsert));
                        return corners[i];
                        //Else if the corner is on the left of a side
                    }else {
                        //while the edge is not in the right place, keep rotating the bottom layer
                        while(!pos[(i+adjust) % 8].equals("P")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        //apply the relevant moves
                        cube.move(Algorithm.pCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.pCornerInsert));
                        return corners[i];
                    }
                    //Repeated process for the other possible colours
                }else if(otherCentres[0].equals("B") && otherCentres[1].equals("R")){
                    if((i % 2) == 0) {
                        while(!pos[(i+adjust) % 8].equals("O")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        cube.move(Algorithm.oCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.oCornerInsert));
                        return corners[i];
                    }else {
                        while(!pos[(i+adjust) % 8].equals("T")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        cube.move(Algorithm.tCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.tCornerInsert));
                        return corners[i];
                    }

                }else if(otherCentres[0].equals("O") && otherCentres[1].equals("B")){
                    if((i % 2) == 0) {
                        while(!pos[(i+adjust) % 8].equals("S")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        cube.move(Algorithm.sCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.sCornerInsert));
                        return corners[i];
                    }else {
                        while(!pos[(i+adjust) % 8].equals("H")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        cube.move(Algorithm.hCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.hCornerInsert));
                        return corners[i];
                    }
                }else if(otherCentres[0].equals("G") && otherCentres[1].equals("O")){
                    if((i % 2) == 0) {
                        while(!pos[(i+adjust) % 8].equals("G")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        cube.move(Algorithm.gCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.gCornerInsert));
                        return corners[i];
                    }else {
                        while(!pos[(i+adjust) % 8].equals("L")) {
                            cube.move("D");
                            solution.add("D");
                            adjust+=2;
                        }
                        cube.move(Algorithm.lCornerInsert);
                        solution.addAll(Arrays.asList(Algorithm.lCornerInsert));
                        return corners[i];
                    }
                }
                //resets the adjustment variable to zero, used when corners weren't in the correct position to be inserted
                adjust = 0;
            }
        }
        return null;
    }

    public static List<String> getCompletedWhiteCorners(Cube cube) {
        //Creates a list which will be the return variable
        List<String> ret = new ArrayList<>();

        //Creates an array of the current top face corners of the cube
        String[] corners = {Utils.getCurrentCorner("D", cube), Utils.getCurrentCorner("C", cube),
                Utils.getCurrentCorner("B", cube), Utils.getCurrentCorner("A", cube)};

        //Creates an array of all of the completed top face/white corners from the completed cube
        String[] correctCorners = {"WGO", "WRG", "WBR", "WOB"};

        //Compares the ith corner against the ith correct corner, and if they match, add the corner to the return list
        for (int i = 0; i < 4; i++) {
            if(corners[i].equals(correctCorners[i])) {
                ret.add(corners[i]);
            }
        }
        return ret;
    }

    private static void insertMiddleEdges(Cube cube) {

        //Creates a list with existing solved middle edges
        List<String> completeEdges = getCompletedMiddleEdges(cube);

        //Keeps looping while there are still unsolved edges
        while(completeEdges.size() < 4) {
            String edge = insertNextMiddleEdge(cube);
            if(edge != null) {
                //Adds an edge to the list
                completeEdges.add(edge);
            }
        }

    }

    private static List<String> getCompletedMiddleEdges(Cube cube) {
        //Creates a list which will be the return variable
        List<String> ret = new ArrayList<>();

        //Creates an array of the current middle layer edges
        String[] edges = {Utils.getCurrentEdge("J", cube), Utils.getCurrentEdge("N", cube),
                Utils.getCurrentEdge("R", cube), Utils.getCurrentEdge("F", cube)};

        //Creates an array of all of the completed middle layer edges from the completed cube
        String[] correctEdges = {"GR", "RB", "BO", "OG"};

        //Compares the ith edge against the ith correct edge, and if they match, add the edge to the return list
        for (int i = 0; i < 4; i++) {
            if(edges[i].equals(correctEdges[i])) {
                ret.add(edges[i]);
            }
        }
        return ret;
    }

    private static String insertNextMiddleEdge(Cube cube) {

        //Gets current bottom edges
        String[] bottomEdges = getBottomLayerEdges(cube);

        //Will return the edge that has just been solved
        String bottomEdge = insertNextBottomEdge(cube, bottomEdges);

        //If the edge retuned isn't null then append to list
        if(bottomEdge != null) {
            return bottomEdge;
        }

        //If the edge was null then search/setup a bad edge
        String badEdge = setupBadEdge(cube);

        //If a bad edge was found and setup
        if(badEdge != null) {
            //There will always be a normal edge in the bottom after setting up a bad edge
            bottomEdges = getBottomLayerEdges(cube);
            bottomEdge = insertNextBottomEdge(cube, bottomEdges);
            if(bottomEdge != null) {
                return bottomEdge;
            }
        }

        return null;
    }

    private static String[] getBottomLayerEdges(Cube cube) {
        return new String[]{Utils.getCurrentEdge("K", cube), Utils.getCurrentEdge("O", cube),
                Utils.getCurrentEdge("S", cube), Utils.getCurrentEdge("G", cube)};
    }

    private static String insertNextBottomEdge(Cube cube, String[] edges) {

        //Array of corresponding positions
        String[] pos = {"K", "O", "S", "G"};
        String[] centres = {"G", "R", "B", "O"};

        //Loops through each edge
        for(int i = 0; i < edges.length; i++) {
            //Checks to see iff the edge is not a yellow edge
            if(!(edges[i].substring(0,1).equals("Y") || edges[i].substring(1,2).equals("Y"))) {
                //Stores the 2 colours on the edge
                String forwardColour = edges[i].substring(0, 1);
                String bottomColour = edges[i].substring(1,2);

                //Lines up the forward sticker on the edge with the correct centre piece
                int adjust = 0;
                while(!forwardColour.equals(centres[(i+adjust) % 4])) {
                    cube.move("D");
                    solution.add("D");
                    adjust += 1;
                }

                //Gets the new position of the edge after being lined up
                String newPos = pos[(i+adjust) % 4];

                //Goes through each case of the edge colours and applies appropriate algorithm
                if(newPos.equals("K")) {
                    switch (bottomColour) {
                        case "O": cube.move(Algorithm.greenOrangeEdge); solution.addAll(Arrays.asList(Algorithm.greenOrangeEdge)); return edges[i];
                        case "R": cube.move(Algorithm.greenRedEdge); solution.addAll(Arrays.asList(Algorithm.greenRedEdge)); return edges[i];
                    }
                }else if(newPos.equals("O")) {
                    switch (bottomColour) {
                        case "G": cube.move(Algorithm.redGreenEdge); solution.addAll(Arrays.asList(Algorithm.redGreenEdge)); return edges[i];
                        case "B": cube.move(Algorithm.redBlueEdge); solution.addAll(Arrays.asList(Algorithm.redBlueEdge)); return edges[i];
                    }
                }else if(newPos.equals("S")) {
                    switch (bottomColour) {
                        case "R": cube.move(Algorithm.blueRedEdge); solution.addAll(Arrays.asList(Algorithm.blueRedEdge)); return edges[i];
                        case "O": cube.move(Algorithm.blueOrangeEdge); solution.addAll(Arrays.asList(Algorithm.blueOrangeEdge)); return edges[i];
                    }
                }else if(newPos.equals("G")) {
                    switch (bottomColour) {
                        case "B": cube.move(Algorithm.orangeBlueEdge); solution.addAll(Arrays.asList(Algorithm.orangeBlueEdge)); return edges[i];
                        case "G": cube.move(Algorithm.orangeGreenEdge); solution.addAll(Arrays.asList(Algorithm.orangeGreenEdge)); return edges[i];
                    }
                }
            }
        }
        //returns null if there are no bottom edges to be solved
        return null;
    }

    private static String setupBadEdge(Cube cube) {

        //Gets a list of middle edges
        String[] edges = {Utils.getCurrentEdge("J", cube), Utils.getCurrentEdge("N", cube),
                Utils.getCurrentEdge("R", cube), Utils.getCurrentEdge("F", cube)};

        //Creates an array of all of the completed middle layer edges from the completed cube
        String[] correctEdges = {"GR", "RB", "BO", "OG"};

        //Creates an array of corresponding positions
        String[] pos = {"J", "N", "R", "F"};

        //Loops through each edge
        for(int i = 0; i < edges.length; i++) {
            //Checks to see iff the edge is not a yellow edge
            if(!(edges[i].charAt(0) == 'Y' || edges[i].charAt(1) == 'Y') &&
                    !(edges[i].equals(correctEdges[i]))) {
                //If so, it applies the relevant algorithm to remove the edge and re-insert the corner
                switch (pos[i]) {
                    case "J": cube.move(Algorithm.greenRedEdge); solution.addAll(Arrays.asList(Algorithm.greenRedEdge)); return edges[i];
                    case "N": cube.move(Algorithm.redBlueEdge); solution.addAll(Arrays.asList(Algorithm.redBlueEdge)); return edges[i];
                    case "R": cube.move(Algorithm.blueOrangeEdge); solution.addAll(Arrays.asList(Algorithm.blueOrangeEdge)); return edges[i];
                    case "F": cube.move(Algorithm.orangeGreenEdge); solution.addAll(Arrays.asList(Algorithm.orangeGreenEdge)); return edges[i];
                }
            }
        }

        return null;

    }

    private static String makeYellowCross(Cube cube) {
        //Rotates cube so yellow faces up
        cube.move("Z2");
        solution.add("Z2");

        //Identifies state of top layer edges
        String edgeCase = identifyPreYellowCrossState(cube);

        //Goes through each case and applies the relevant algorithm
        if(edgeCase.equals("done")) {;}
        else if(edgeCase.equals("zero")) {
            cube.move(Algorithm.yellowCross);
            cube.move(Algorithm.yellowCrossAlt);
            solution.addAll(Arrays.asList(Algorithm.yellowCross));
            solution.addAll(Arrays.asList(Algorithm.yellowCrossAlt));
        }else if(edgeCase.equals("adj")) {
            while(!(cube.getWhiteFace()[1][2].equals("Y") && cube.getWhiteFace()[2][1].equals("Y"))) {
                cube.move("U");
                solution.add("U");
            }
            cube.move(Algorithm.yellowCrossAlt);
            solution.addAll(Arrays.asList(Algorithm.yellowCrossAlt));
        }else if(edgeCase.equals("line")) {
            while(!(cube.getWhiteFace()[1][0].equals("Y") && cube.getWhiteFace()[1][2].equals("Y"))) {
                cube.move("U");
                solution.add("U");
            }
            cube.move(Algorithm.yellowCross);
            solution.addAll(Arrays.asList(Algorithm.yellowCross));
        }else if(edgeCase.equals("flippedEdge")) {
            return "bad";
        }

        return "fine";
    }

    private static String identifyPreYellowCrossState(Cube cube) {

        //Gets an array of current top face edges
        String[] edges = {Utils.getCurrentEdge("C", cube).substring(0, 1), Utils.getCurrentEdge("B", cube).substring(0, 1),
                Utils.getCurrentEdge("A", cube).substring(0, 1), Utils.getCurrentEdge("D", cube).substring(0, 1)};

        int[] val = {1,2,3,4};

        int count = 0;
        int count2 = 0;

        //Increments count depending on the position of the next yellow edge
        for(int i = 0; i < edges.length; i ++) {
            if(edges[i].equals("Y")) {
                count+=val[i];
                count2+=1;
            }
        }

        //Check to see if there is a flipped edge in the scramble
        if(count2 == 3 || count2 == 1) {
            return "flippedEdge";
        }

        //returns the case depending on the count value
        if(count == 0) {
            return "zero";
        }else if(count == 10) {
            return "done";
        }else if(count % 2 == 0){
            return "line";
        }else {
            return "adj";
        }

    }

    private static void orientLastLayer(Cube cube) {

        //Finds the case of the top layer of the cube
        String ollCase = findOllCase(cube);

        //Performs algorithm depending on the OLL case
        switch (ollCase) {
            case "done": break;
            case "sune":
                //Moves upper face to correct position
                while(!(cube.getWhiteFace()[2][0].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.sune);
                solution.addAll(Arrays.asList(Algorithm.sune));
                break;
            case "antisune":
                //Moves upper face to correct position
                while(!(cube.getWhiteFace()[0][2].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.antiSune);
                solution.addAll(Arrays.asList(Algorithm.antiSune));
                break;
            case "H":
                //Moves upper face to correct position
                while(!(cube.getRedFace()[0][0].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.H);
                solution.addAll(Arrays.asList(Algorithm.H));
                break;
            case "pi":
                //Moves upper face to correct position
                while(!(cube.getOrangeFace()[0][0].equals("Y") && cube.getOrangeFace()[0][2].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.pi);
                solution.addAll(Arrays.asList(Algorithm.pi));
                break;
            case "L":
                //Moves upper face to correct position
                while(!(cube.getGreenFace()[0][0].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.L);
                solution.addAll(Arrays.asList(Algorithm.L));
                break;
            case "U":
                //Moves upper face to correct position
                while(!(cube.getGreenFace()[0][0].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.U);
                solution.addAll(Arrays.asList(Algorithm.U));
                break;
            case "T":
                //Moves upper face to correct position
                while(!(cube.getGreenFace()[0][0].equals("Y"))) {
                    cube.move("U");
                    solution.add("U");
                }
                //Applies algorithm
                cube.move(Algorithm.T);
                solution.addAll(Arrays.asList(Algorithm.T));
                break;
        }
    }

    private static String findOllCase(Cube cube) {

        //Creates arrays of the colours of the top corners, and the colours on the sides of those corners
        String[] topColours = {cube.getWhiteFace()[2][2], cube.getWhiteFace()[0][2], cube.getWhiteFace()[0][0], cube.getWhiteFace()[2][0]};
        String[] sideColours = {cube.getGreenFace()[0][2], cube.getRedFace()[0][0], cube.getRedFace()[0][2], cube.getBlueFace()[0][0],
                cube.getBlueFace()[0][2], cube.getOrangeFace()[0][0], cube.getOrangeFace()[0][2], cube.getGreenFace()[0][0]};

        List<Integer> indexOfTopYellow = new ArrayList<Integer>();
        List<Integer> indexOfSideYellow = new ArrayList<Integer>();

        //Adds indexes of top-facing yellow corners to a list
        for(int i = 0; i < topColours.length; i++) {
            if(topColours[i].equals("Y")) {
                indexOfTopYellow.add(i);
            }
        }

        //Adds indexes of side-facing yellow corners to a list
        for(int i = 0; i < sideColours.length; i++) {
            if(sideColours[i].equals("Y")) {
                indexOfSideYellow.add(i);
            }
        }

        //If all 4 top facing corners are yellow
        if(indexOfTopYellow.size() == 4) {
            return "done";

            //When there is one face-up yellow corner, there are 2 possible cases
        }else if(indexOfTopYellow.size() == 1) {
            int index = indexOfTopYellow.get(0);
            if(sideColours[((2*index) + 2) % 8].equals("Y")) {
                return "sune";
            }else {
                return "antisune";
            }

            //When there are 2 face-up yellow corners, there are 3 possible cases
        }else if(indexOfTopYellow.size() == 2) {
            if((indexOfTopYellow.get(0) + indexOfTopYellow.get(1)) % 2 == 0) {
                return "L";
            }else {
                if((indexOfSideYellow.get(1) - indexOfSideYellow.get(0)) == 1 || (indexOfSideYellow.get(1) - indexOfSideYellow.get(0)) == 7) {
                    return "U";
                }else {
                    return "T";
                }
            }

            //When there are no face-up yellow corners, there are 2 possible cases
        }else if(indexOfTopYellow.size() == 0) {
            int adjust = 0;
            String ret = null;
            //Moves the upper face to help identify what case there is
            while(!(cube.getGreenFace()[0][0].equals("Y") && cube.getGreenFace()[0][2].equals("Y"))) {
                cube.move("U");
                adjust+=1;
            }
            if(cube.getRedFace()[0][2].equals("Y")) {
                ret = "pi";
            }else {
                ret = "H";
            }
            //re-moves the upper face back
            for(int i = 0; i < adjust; i ++) {
                cube.move("U'");
            }
            return ret;
        }else {
            return null;
        }

    }

    private static void permuteLastLayer(Cube cube) {
        createHeadlights(cube);
        finalAlgorithm(cube);

        while(!(cube.getGreenFace()[0][0].equals(cube.getGreenFace()[1][0]))) {
            cube.move("U");
            solution.add("U");
        }
    }

    private static void createHeadlights(Cube cube) {

        //Gets list of side corner colours on the top face
        String[] corners = {cube.getGreenFace()[0][2], cube.getRedFace()[0][0], cube.getRedFace()[0][2], cube.getBlueFace()[0][0],
                cube.getBlueFace()[0][2], cube.getOrangeFace()[0][0], cube.getOrangeFace()[0][2], cube.getGreenFace()[0][0]};

        //counts number of pairs of headlights
        int numHeadlights = 0;
        for(int i = 0; i < corners.length; i++) {
            if(corners[i].equals(corners[(i+1) % 8])) {
                numHeadlights+=1;
            }
        }

        //goes through the cases and applies appropriate algorithm
        if(numHeadlights == 4) {;}
        else if(numHeadlights == 0) {
            cube.move(Algorithm.createHeadlightsNoPair);
            solution.addAll(Arrays.asList(Algorithm.createHeadlightsNoPair));
            createHeadlights(cube);
        }else if(numHeadlights == 1) {
            //lines the headlights up to the left of the cube
            while(!(cube.getOrangeFace()[0][0].equals(cube.getOrangeFace()[0][2]))) {
                cube.move("U");
                solution.add("U");
            }
            cube.move(Algorithm.createHeadlightsOnePair);
            solution.addAll(Arrays.asList(Algorithm.createHeadlightsOnePair));
        }
    }

    private static void finalAlgorithm(Cube cube) {

        List<String> colours = new ArrayList<>();
        //appends the 1st row of the sides around top layer
        colours.addAll(Arrays.asList(cube.getGreenFace()[0]).subList(0, 3));
        colours.addAll(Arrays.asList(cube.getRedFace()[0]).subList(0, 3));
        colours.addAll(Arrays.asList(cube.getBlueFace()[0]).subList(0, 3));
        colours.addAll(Arrays.asList(cube.getOrangeFace()[0]).subList(0, 3));

        //Creates a dictionary of opposite colours
        Map<String, String> opposites = new HashMap<String, String>();
        opposites.put("G", "B");
        opposites.put("B", "G");
        opposites.put("R", "O");
        opposites.put("O", "R");

        //Checks if there is a bar of 3 identical colours, and if there are headlights with an opposite colour in between
        boolean hasBar = false;
        boolean hasOppHeadlight = false;
        for(int i = 0; i < colours.size(); i++) {
            if(colours.get(i).equals(colours.get((i+1) % 12)) && colours.get(i).equals(colours.get((i+2) % 12))) {
                hasBar = true;
            }
            if(i % 3 == 0) {
                if(colours.get(i).equals(colours.get((i+2) % 12)) && colours.get((i+1) % 12).equals(opposites.get(colours.get(i)))) {
                    hasOppHeadlight = true;
                }
            }
        }

        //goes through each case and applies the relevant algorithm
        if(hasOppHeadlight == true) {
            if(hasBar == true) {
                //lines up cube so that the bar is on the back
                while(!(cube.getBlueFace()[0][0].equals(cube.getBlueFace()[0][1]) && cube.getBlueFace()[0][0].equals(cube.getBlueFace()[0][2]))) {
                    cube.move("U");
                    solution.add("U");
                }
                if(cube.getOrangeFace()[0][0].equals(opposites.get(cube.getOrangeFace()[0][1]))) {
                    cube.move(Algorithm.pllLeft3Cycle);
                    solution.addAll(Arrays.asList(Algorithm.pllLeft3Cycle));
                }else {
                    cube.move(Algorithm.pllRight3Cycle);
                    solution.addAll(Arrays.asList(Algorithm.pllRight3Cycle));
                }
            }else {
                cube.move(Algorithm.pllPlus);
                solution.addAll(Arrays.asList(Algorithm.pllPlus));
            }
        }else {
            if(hasBar == true) {
                ;
            }else {
                //lines up cube so that an adjacent swap is on the bottom right of the top layer
                while(!(cube.getGreenFace()[0][1].equals(cube.getRedFace()[0][0]))) {
                    cube.move("U");
                    solution.add("U");
                }
                cube.move(Algorithm.pllAdjacent);
                solution.addAll(Arrays.asList(Algorithm.pllAdjacent));
            }
        }
    }

}