package com.matthew.unscramble;

public class Utils {

    //Creates some variables for completed sides
    public static final String[][] completeface1 = {{"W","W","W"},{"W","W","W"},{"W","W","W"}};
    public static final String[][] completeface2 = {{"G","G","G"},{"G","G","G"},{"G","G","G"}};
    public static final String[][] completeface3 = {{"R","R","R"},{"R","R","R"},{"R","R","R"}};
    public static final String[][] completeface4 = {{"B","B","B"},{"B","B","B"},{"B","B","B"}};
    public static final String[][] completeface5 = {{"O","O","O"},{"O","O","O"},{"O","O","O"}};
    public static final String[][] completeface6 = {{"Y","Y","Y"},{"Y","Y","Y"},{"Y","Y","Y"}};

    public static String[][] rotateClockWise(String[][] array) {
        int size = array.length;
        String[][] arr = new String[size][size]; //Creates a new array the same size as input array

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) { //A nested loop iterating the size of the array many times
                arr[i][j] = array[size - j - 1][i]; //The nth row becomes the backwards of the nth column
            }
        }
        return arr;
    }

    public static String[][] rotateAntiClockWise(String[][] array) {
        int size = array.length;
        String[][] arr = new String[size][size]; //Creates a new array the same size as input array

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) { //A nested loop iterating the size of the array many times
                arr[i][j] = array[j][size - i - 1]; //The nth column becomes the backwards of the nth row
            }
        }
        return arr;
    }

    public static String[][] horizontalFlip(String[][] array){
        //creates temporary variable used for swapping
        String temp;
        //loops through each column
        for (int i = 0; i < array.length; i++) {
            //loops through half of the rows (to avoid re-swapping elements)
            for (int j = 0; j < array[i].length / 2; j++) {
                //performs a swap on elements
                temp = array[i][j];
                array[i][j] = array[i][array.length - 1 - j];
                array[i][array.length - 1 - j] = temp;
            }
        }
        return array;
    }

    public static String[][] verticalFlip(String[][] array){
        String temp;
        //loops through half of the columns (to avoid re-swapping elements)
        for (int i = 0; i < array.length / 2; i++) {
            //loops through each row
            for (int j = 0; j < array[i].length; j++) {
                //performs a swap on two elements
                temp = array[i][j];
                array[i][j] = array[array.length - 1 - i][j];
                array[array.length - 1 - i][j] = temp;
            }
        }
        return array;
    }

    //will return index of the target item in a given array
    public static int findIndex(String[] array, String target) {
        if (array == null) {
            return -1; //returns -1 if array is empty
        }

        int len = array.length;
        int i = 0;

        while (i < len) {
            if(array[i].equals(target)) {
                return i; //if the ith item in array equals target then return the index
            }else {
                i = i + 1;
            }
        }
        return -1; //if not found in array then return -1
    }

    public static String getCurrentEdge(String edge, Cube cube) {
        String retEdge;
        switch (edge) {
            case "A": retEdge = cube.getWhiteFace()[0][1] + cube.getBlueFace()[0][1]; break;
            case "B": retEdge = cube.getWhiteFace()[1][2] + cube.getRedFace()[0][1]; break;
            case "C": retEdge = cube.getWhiteFace()[2][1] + cube.getGreenFace()[0][1]; break;
            case "D": retEdge = cube.getWhiteFace()[1][0] + cube.getOrangeFace()[0][1]; break;
            case "E": retEdge = cube.getOrangeFace()[0][1] + cube.getWhiteFace()[1][0]; break;
            case "F": retEdge = cube.getOrangeFace()[1][2] + cube.getGreenFace()[1][0]; break;
            case "G": retEdge = cube.getOrangeFace()[2][1] + cube.getYellowFace()[1][0]; break;
            case "H": retEdge = cube.getOrangeFace()[1][0] + cube.getBlueFace()[1][2]; break;
            case "I": retEdge = cube.getGreenFace()[0][1] + cube.getWhiteFace()[2][1]; break;
            case "J": retEdge = cube.getGreenFace()[1][2] + cube.getRedFace()[1][0]; break;
            case "K": retEdge = cube.getGreenFace()[2][1] + cube.getYellowFace()[0][1]; break;
            case "L": retEdge = cube.getGreenFace()[1][0] + cube.getOrangeFace()[1][2]; break;
            case "M": retEdge = cube.getRedFace()[0][1] + cube.getWhiteFace()[1][2]; break;
            case "N": retEdge = cube.getRedFace()[1][2] + cube.getBlueFace()[1][0]; break;
            case "O": retEdge = cube.getRedFace()[2][1] + cube.getYellowFace()[1][2]; break;
            case "P": retEdge = cube.getRedFace()[1][0] + cube.getGreenFace()[1][2]; break;
            case "Q": retEdge = cube.getBlueFace()[0][1] + cube.getWhiteFace()[0][1]; break;
            case "R": retEdge = cube.getBlueFace()[1][2] + cube.getOrangeFace()[1][0]; break;
            case "S": retEdge = cube.getBlueFace()[2][1] + cube.getYellowFace()[2][1]; break;
            case "T": retEdge = cube.getBlueFace()[1][0] + cube.getRedFace()[1][2]; break;
            case "U": retEdge = cube.getYellowFace()[0][1] + cube.getGreenFace()[2][1]; break;
            case "V": retEdge = cube.getYellowFace()[1][2] + cube.getRedFace()[2][1]; break;
            case "W": retEdge = cube.getYellowFace()[2][1] + cube.getBlueFace()[2][1]; break;
            case "X": retEdge = cube.getYellowFace()[1][0] + cube.getOrangeFace()[2][1]; break;
            default: retEdge = null;
        }
        return retEdge;
    }

    public static String getCurrentCorner(String corner, Cube cube) {
        String retCorner;
        switch (corner) {
            case "A": retCorner = (cube.getWhiteFace()[0][0] + cube.getOrangeFace()[0][0] + cube.getBlueFace()[0][2]); break;
            case "B": retCorner = (cube.getWhiteFace()[0][2] + cube.getBlueFace()[0][0] + cube.getRedFace()[0][2]); break;
            case "C": retCorner = (cube.getWhiteFace()[2][2] + cube.getRedFace()[0][0] + cube.getGreenFace()[0][2]); break;
            case "D": retCorner = (cube.getWhiteFace()[2][0] + cube.getGreenFace()[0][0] + cube.getOrangeFace()[0][2]); break;
            case "E": retCorner = (cube.getOrangeFace()[0][0] + cube.getBlueFace()[0][2] + cube.getWhiteFace()[0][0]); break;
            case "F": retCorner = (cube.getOrangeFace()[0][2] + cube.getWhiteFace()[2][0] + cube.getGreenFace()[0][0]); break;
            case "G": retCorner = (cube.getOrangeFace()[2][2] + cube.getGreenFace()[2][0] + cube.getYellowFace()[0][0]); break;
            case "H": retCorner = (cube.getOrangeFace()[2][0] + cube.getYellowFace()[2][0] + cube.getBlueFace()[2][2]); break;
            case "I": retCorner = (cube.getGreenFace()[0][0] + cube.getOrangeFace()[0][2] + cube.getWhiteFace()[2][0]); break;
            case "J": retCorner = (cube.getGreenFace()[0][2] + cube.getWhiteFace()[2][2] + cube.getRedFace()[0][0]); break;
            case "K": retCorner = (cube.getGreenFace()[2][2] + cube.getRedFace()[2][0] + cube.getYellowFace()[0][2]); break;
            case "L": retCorner = (cube.getGreenFace()[2][0] + cube.getYellowFace()[0][0] + cube.getOrangeFace()[2][2]); break;
            case "M": retCorner = (cube.getRedFace()[0][0] + cube.getGreenFace()[0][2] + cube.getWhiteFace()[2][2]); break;
            case "N": retCorner = (cube.getRedFace()[0][2] + cube.getWhiteFace()[0][2] + cube.getBlueFace()[0][0]); break;
            case "O": retCorner = (cube.getRedFace()[2][2] + cube.getBlueFace()[2][0] + cube.getYellowFace()[2][2]); break;
            case "P": retCorner = (cube.getRedFace()[2][0] + cube.getYellowFace()[0][2] + cube.getGreenFace()[2][2]); break;
            case "Q": retCorner = (cube.getBlueFace()[0][0] + cube.getRedFace()[0][2] + cube.getWhiteFace()[0][2]); break;
            case "R": retCorner = (cube.getBlueFace()[0][2] + cube.getWhiteFace()[0][0] + cube.getOrangeFace()[0][0]); break;
            case "S": retCorner = (cube.getBlueFace()[2][2] + cube.getOrangeFace()[2][0] + cube.getYellowFace()[2][0]); break;
            case "T": retCorner = (cube.getBlueFace()[2][0] + cube.getYellowFace()[2][2] + cube.getRedFace()[2][2]); break;
            case "U": retCorner = (cube.getYellowFace()[0][0] + cube.getOrangeFace()[2][2] + cube.getGreenFace()[2][0]); break;
            case "V": retCorner = (cube.getYellowFace()[0][2] + cube.getGreenFace()[2][2] + cube.getRedFace()[2][0]); break;
            case "W": retCorner = (cube.getYellowFace()[2][2] + cube.getRedFace()[2][2] + cube.getBlueFace()[2][0]); break;
            case "X": retCorner = (cube.getYellowFace()[2][0] + cube.getBlueFace()[2][2] + cube.getOrangeFace()[2][0]); break;
            default: retCorner = null;
        }
        return retCorner;
    }

    //Prints out each side of the cube in the command line in a net form
    public static void formatOutput(Cube cube){
        String line1 = "    " + cube.getWhiteFace()[0][0] + cube.getWhiteFace()[0][1] + cube.getWhiteFace()[0][2];
        String line2 = "    " + cube.getWhiteFace()[1][0] + cube.getWhiteFace()[1][1] + cube.getWhiteFace()[1][2];
        String line3 = "    " + cube.getWhiteFace()[2][0] + cube.getWhiteFace()[2][1] + cube.getWhiteFace()[2][2];
        String line4 = cube.getOrangeFace()[0][0] + cube.getOrangeFace()[0][1] + cube.getOrangeFace()[0][2] + " " + cube.getGreenFace()[0][0] + cube.getGreenFace()[0][1] +
                cube.getGreenFace()[0][2] + " " + cube.getRedFace()[0][0] + cube.getRedFace()[0][1] + cube.getRedFace()[0][2] + " " + cube.getBlueFace()[0][
                0] + cube.getBlueFace()[0][1] + cube.getBlueFace()[0][2];
        String line5 = cube.getOrangeFace()[1][0] + cube.getOrangeFace()[1][1] + cube.getOrangeFace()[1][2] + " " + cube.getGreenFace()[1][0] + cube.getGreenFace()[1][1] +
                cube.getGreenFace()[1][2] + " " + cube.getRedFace()[1][0] + cube.getRedFace()[1][1] + cube.getRedFace()[1][2] + " " + cube.getBlueFace()[1][
                0] + cube.getBlueFace()[1][1] + cube.getBlueFace()[1][2];
        String line6 = cube.getOrangeFace()[2][0] + cube.getOrangeFace()[2][1] + cube.getOrangeFace()[2][2] + " " + cube.getGreenFace()[2][0] + cube.getGreenFace()[2][1] +
                cube.getGreenFace()[2][2] + " " + cube.getRedFace()[2][0] + cube.getRedFace()[2][1] + cube.getRedFace()[2][2] + " " + cube.getBlueFace()[2][
                0] + cube.getBlueFace()[2][1] + cube.getBlueFace()[2][2];
        String line7 = "    " + cube.getYellowFace()[0][0] + cube.getYellowFace()[0][1] + cube.getYellowFace()[0][2];
        String line8 = "    " + cube.getYellowFace()[1][0] + cube.getYellowFace()[1][1] + cube.getYellowFace()[1][2];
        String line9 = "    " + cube.getYellowFace()[2][0] + cube.getYellowFace()[2][1] + cube.getYellowFace()[2][2];
        String line10 = "";
        String line11 = "---------------";
        String line12 = "";
        System.out.println("---------------" + "\n" + line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line5 + "\n" + line6 + "\n" + line7 + "\n" + line8 + "\n" + line9 + "\n" + line10 + "\n" + line11 + "\n" + line12);
    }

}
