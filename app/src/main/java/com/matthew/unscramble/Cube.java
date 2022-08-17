package com.matthew.unscramble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cube {

    private String[][] whiteFace, greenFace, redFace, blueFace, orangeFace, yellowFace; //creates 2d arrays for each side of the cube

    //Creates a constructor to initialise a new cube object
    public Cube(String[][] whiteFace, String[][] greenFace, String[][] redFace, String[][] blueFace, String[][] orangeFace, String[][] yellowFace) {
        this.whiteFace = whiteFace;
        this.greenFace = greenFace;
        this.redFace = redFace;
        this.blueFace = blueFace;
        this.orangeFace = orangeFace;
        this.yellowFace = yellowFace;
    }

    // Takes an input move string and applies the move
    public void move(String move){
        switch (move) {
            case "F": front(1); break;
            case "B": back(1); break;
            case "L": left(1); break;
            case "R": right(1); break;
            case "U": up(1); break;
            case "D": down(1); break;
            case "Z": z(1); break;
            case "f": littleFront(1); break;
            case "X": x(1); break;
            case "Y": y(1); break;
            case "r": littleRight(1); break;
            case "M": middle(1); break;
            case "F'": front(3); break;
            case "B'": back(3); break;
            case "L'": left(3); break;
            case "R'": right(3); break;
            case "U'": up(3); break;
            case "D'": down(3); break;
            case "Z'": z(3); break;
            case "f'": littleFront(3); break;
            case "X'": x(3); break;
            case "Y'": y(3); break;
            case "r'": littleRight(3); break;
            case "M'": middle(3); break;
            case "F2": front(2); break;
            case "B2": back(2); break;
            case "L2": left(2); break;
            case "R2": right(2); break;
            case "U2": up(2); break;
            case "D2": down(2); break;
            case "Z2": z(2); break;
            case "f2": littleFront(2); break;
            case "X2": x(2); break;
            case "Y2": y(2); break;
            case "r2": littleRight(2); break;
            case "M2": middle(2); break;
            case "scramble": move(generateScramble()); break;
            case "reset": reset(); break;
            case "solve": Solver.solve(this); break;
            case "exit": System.exit(0); break;
        }
    }

    // Takes an input array of moves and applies each move individually
    public void move(String[] move){
        for (String s : move) {
            move(s);
        }
    }

    public void littleFront(int num) {
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            move(new String[]{"Z", "B"});
        }
    }

    public void littleRight(int num) {
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            move(new String[]{"X", "L"});
        }
    }

    public void middle(int num) {
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            move(new String[]{"X", "L", "R'"});
        }
    }

    public void up(int num){
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.whiteFace = Utils.rotateClockWise(this.whiteFace); //performs a rotation of the white side
            List<String> tempList = new ArrayList<>(); //creates a list variable
            //next 4 lines will append the 1st row of the sides adjacent to the white side as individual items
            tempList.addAll(Arrays.asList(this.greenFace[0]).subList(0, 3));
            tempList.addAll(Arrays.asList(this.redFace[0]).subList(0, 3));
            tempList.addAll(Arrays.asList(this.blueFace[0]).subList(0, 3));
            tempList.addAll(Arrays.asList(this.orangeFace[0]).subList(0, 3));

            //Performs a form of swap, where each item in the row is replaced with an item 3 more than its place in the list
            for(int j = 0; j < 3; j++) {
                this.greenFace[0][j] = tempList.get((j + 3));
                this.redFace[0][j] = tempList.get(j + 6);
                this.blueFace[0][j] = tempList.get(j + 9);
                this.orangeFace[0][j] = tempList.get((j + 12) % 12);
            }
        }
    }

    public void down(int num){
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.yellowFace = Utils.rotateClockWise(this.yellowFace); //performs a rotation of the yellow side
            List<String> tempList = new ArrayList<>();
            //These lines append each element of the 3rd row of the sides adjacent to the yellow face
            tempList.addAll(Arrays.asList(this.greenFace[2]).subList(0, 3));
            tempList.addAll(Arrays.asList(this.redFace[2]).subList(0, 3));
            tempList.addAll(Arrays.asList(this.blueFace[2]).subList(0, 3));
            tempList.addAll(Arrays.asList(this.orangeFace[2]).subList(0, 3));

            //Replaces elements in the 3rd row of the 4 sides with ones that are in the list
            for(int j = 0; j < 3; j++) {
                this.greenFace[2][j] = tempList.get(j + 9);
                this.redFace[2][j] = tempList.get((j + 12) % 12);
                this.blueFace[2][j] = tempList.get(j + 3);
                this.orangeFace[2][j] = tempList.get(j + 6);
            }
        }
    }

    public void left(int num){
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.orangeFace = Utils.rotateClockWise(this.orangeFace); //rotates orange face clockwise
            List<String> tempList = new ArrayList<String>(); //creates a list variable
            //Adds each element of the 1st column of the white side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.whiteFace[j][0]);
            }
            //Adds each element of the 1st column of the green side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.greenFace[j][0]);
            }
            //Adds each element of the 1st column of the yellow side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.yellowFace[j][0]);
            }
            //Adds each element of the 3rd column of the blue side to list, but in reverse order
            for(int j = 2; j > -1; j--){
                tempList.add(this.blueFace[j][2]);
            }
            //Replaces columns of adjacent sides to orange with certain elements in the list
            for(int j = 0; j < 3; j++){
                this.whiteFace[j][0] = tempList.get(j + 9); // items 9, 10, 11
                this.greenFace[j][0] = tempList.get((j + 12) % 12); // items 0, 1, 2
                this.yellowFace[j][0] = tempList.get(j + 3); // items 3, 4, 5
                this.blueFace[j][2] = tempList.get(8-j); // items 8, 7, 6
            }
        }
    }

    public void right(int num){
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.redFace = Utils.rotateClockWise(this.redFace); //rotates red face clockwise
            List<String> tempList = new ArrayList<>(); //creates a list variable
            //Adds each element of the 3rd column of the white side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.whiteFace[j][2]);
            }
            //Adds each element of the 3rd column of the green side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.greenFace[j][2]);
            }
            //Adds each element of the 3rd column of the yellow side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.yellowFace[j][2]);
            }
            //Adds each element of the 1st column of the blue side to list, in reverse order
            for(int j = 2; j > -1; j--){
                tempList.add(this.blueFace[j][0]);
            }
            //Replaces columns of adjacent sides to red with certain elements in the list
            for(int j = 0; j < 3; j++){
                this.whiteFace[j][2] = tempList.get(j + 3); //items 3, 4, 5
                this.greenFace[j][2] = tempList.get(j + 6); //items 6, 7, 8
                this.yellowFace[j][2] = tempList.get(j + 9); //items 9, 10, 11
                this.blueFace[j][0] = tempList.get(2-j); //items 2, 1, 0
            }
        }
    }

    public void front(int num){
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.greenFace = Utils.rotateClockWise(this.greenFace); //rotates green face clockwise
            //creates a list variable, and instantly appends the last row of the white face
            List<String> tempList = new ArrayList<>(Arrays.asList(this.whiteFace[2]).subList(0, 3));
            //Adds each element of the 1st column of the red side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.redFace[j][0]);
            }
            //Adds each element of the 1st row of the yellow side in reverse order to list
            for(int j = 2; j > -1; j--){
                tempList.add(this.yellowFace[0][j]);
            }
            //Adds each element of the 3rd column of the orange side in reverse order to list
            for(int j = 2; j > -1; j--){
                tempList.add(this.orangeFace[j][2]);
            }
            //Replaces columns/rows of adjacent sides to red with certain elements in the list
            for(int j = 0; j < 3; j++){
                this.whiteFace[2][j] = tempList.get(j + 9);
                this.redFace[j][0] = tempList.get(j);
                this.yellowFace[0][2-j] = tempList.get(j + 3);
                this.orangeFace[2-j][2] = tempList.get(j + 6);
            }
        }
    }

    public void back(int num){
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.blueFace = Utils.rotateClockWise(this.blueFace); //rotates blue face clockwise
            //creates a list variable, and instantly appends the first row of the white face
            List<String> tempList = new ArrayList<>(Arrays.asList(this.whiteFace[0]).subList(0, 3));
            //Adds each element of the 3rd column of the red side to list
            for(int j = 0; j < 3; j++){
                tempList.add(this.redFace[j][2]);
            }
            //Adds each element of the 3rd row of the yellow side in reverse order to list
            for(int j = 2; j > -1; j--){
                tempList.add(this.yellowFace[2][j]);
            }
            //Adds each element of the 1st column of the orange side in reverse order to list
            for(int j = 2; j > -1; j--){
                tempList.add(this.orangeFace[j][0]);
            }
            //Replaces columns/rows of adjacent sides to blue with certain elements in the list
            for(int j = 0; j < 3; j++){
                this.whiteFace[0][j] = tempList.get(j + 3);
                this.redFace[j][2] = tempList.get(j + 6);
                this.yellowFace[2][2-j] = tempList.get(j + 9);
                this.orangeFace[2-j][0] = tempList.get(j);
            }
        }
    }

    public void x(int num) {
        for(int i = 0; i < num; i++) {
            //Rotates rad and orange sides
            this.redFace = Utils.rotateClockWise(this.redFace);
            this.orangeFace = Utils.rotateAntiClockWise(this.orangeFace);
            String[][] tempWhite = this.whiteFace;
            String[][] tempBlue = this.blueFace;
            String[][] tempYellow = this.yellowFace;
            String[][] tempGreen = this.greenFace;

            //White side becomes green side, green side becomes yellow side
            this.whiteFace = tempGreen;
            this.greenFace = tempYellow;
            //Blue side becomes a flipped variant or the white side, yellow side becomes
            //flipped varaint of blue side
            this.blueFace = Utils.horizontalFlip(Utils.verticalFlip(tempWhite));
            this.yellowFace = Utils.horizontalFlip(Utils.verticalFlip(tempBlue));
        }
    }

    public void y(int num) {
        for(int i = 0; i < num; i++) {
            //rotates white and yellow sides
            this.whiteFace = Utils.rotateClockWise(this.whiteFace);
            this.yellowFace = Utils.rotateAntiClockWise(this.yellowFace);
            String[][] tempRed = this.redFace;
            String[][] tempOrange = this.orangeFace;
            String[][] tempBlue = this.blueFace;
            String[][] tempGreen = this.greenFace;

            //Performs a 4-way shuffle of sides
            this.greenFace = tempRed;
            this.redFace = tempBlue;
            this.blueFace = tempOrange;
            this.orangeFace = tempGreen;
        }
    }

    public void z(int num) {
        // iterates the move for a given number of times
        for(int i = 0; i < num; i++) {
            this.greenFace = Utils.rotateClockWise(this.greenFace); // Rotates the green face clockwise
            this.blueFace = Utils.rotateAntiClockWise(this.blueFace); // Rotates the blue face anticlockwise
            // Creates a series of temporary variables for the remaining sides
            String[][] tempRed = this.redFace;
            String[][] tempOrange = this.orangeFace;
            String[][] tempWhite = this.whiteFace;
            String[][] tempYellow = this.yellowFace;

            // Replaces each face with rotated variants of the temporary variables
            this.whiteFace = Utils.rotateClockWise(tempOrange);
            this.redFace = Utils.rotateClockWise(tempWhite);
            this.yellowFace = Utils.rotateClockWise(tempRed);
            this.orangeFace = Utils.rotateClockWise(tempYellow);
        }
    }

    public static String[] generateScramble() {

        //Creates variables for the possible moves and suffixes for those move
        String[] moves = {"U", "D", "F", "B", "L", "R"};
        String[] dir  = {"", "2", "'"};
        //Creates a variable for random number generation
        Random generator = new Random();

        // Creates a list
        String[][] scramble = new String[2][20];

        // loops through 20 times
        for(int i = 0; i < 20; i++) {

            // Generates a random move and suffix
            String tempMove = moves[generator.nextInt(moves.length)];
            String tempDir = dir[generator.nextInt(dir.length)];

            // While loop to current move against previous 2 moves in list
            while(tempMove.equals(scramble[0][Math.floorMod(i-2, 20)]) || tempMove.equals(scramble[0][Math.floorMod(i-1, 20)])) {
                // If it is the case, it generates another move
                tempMove = moves[generator.nextInt(moves.length)];
            }

            // Sets the ith items in the list to the generated values
            scramble[0][i] = tempMove;
            scramble[1][i] = tempDir;
        }

        // creates a 1d array
        String[] array = new String[20];

        // concatenates the two parts from the original array into 1 combined part
        for(int i = 0; i < 20; i++) {
            array[i] = scramble[0][i] + scramble[1][i];
        }
        System.out.println("Scrambled with " + Arrays.toString(array));
        return array;
    }

    public void reset() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.whiteFace[i][j] = "W";
                this.greenFace[i][j] = "G";
                this.redFace[i][j] = "R";
                this.blueFace[i][j] = "B";
                this.orangeFace[i][j] = "O";
                this.yellowFace[i][j] = "Y";
            }
        }
    }

    // Some getters and setters, to allow for retrieval and settings of the side variables
    public String[][] getWhiteFace() {
        return whiteFace;
    }

    public void setWhiteFace(String[][] whiteFace) {
        this.whiteFace = whiteFace;
    }

    public String[][] getGreenFace() {
        return greenFace;
    }

    public void setGreenFace(String[][] greenFace) {
        this.greenFace = greenFace;
    }

    public String[][] getRedFace() {
        return redFace;
    }

    public void setRedFace(String[][] redFace) {
        this.redFace = redFace;
    }

    public String[][] getBlueFace() {
        return blueFace;
    }

    public void setBlueFace(String[][] blueFace) {
        this.blueFace = blueFace;
    }

    public String[][] getOrangeFace() {
        return orangeFace;
    }

    public void setOrangeFace(String[][] orangeFace) {
        this.orangeFace = orangeFace;
    }

    public String[][] getYellowFace() {
        return yellowFace;
    }

    public void setYellowFace(String[][] yellowFace) {
        this.yellowFace = yellowFace;
    }
}
