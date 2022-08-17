package com.matthew.unscramble;

public class Algorithm {

    public static final String[] kCornerInsert = {"D'", "R'", "D", "R"};
    public static final String[] pCornerInsert = {"D", "F", "D'", "F'"};
    public static final String[] oCornerInsert = {"D'", "B'", "D", "B"};
    public static final String[] tCornerInsert = {"D", "R", "D'", "R'"};
    public static final String[] sCornerInsert = {"D'", "L'", "D", "L"};
    public static final String[] hCornerInsert = {"D", "B", "D'", "B'"};
    public static final String[] gCornerInsert = {"D'", "F'", "D", "F"};
    public static final String[] lCornerInsert = {"D", "L", "D'", "L'"};

    public static final String[] vCornerRemove = {"F", "D'", "F'"};
    public static final String[] wCornerRemove = {"R", "D'", "R'"};
    public static final String[] xCornerRemove = {"B", "D'", "B'"};
    public static final String[] uCornerRemove = {"L", "D'", "L'"};

    public static final String[] jCornerRemove = {"F", "D", "F'"};
    public static final String[] mCornerRemove = {"R'", "D'", "R"};
    public static final String[] nCornerRemove = {"R", "D", "R'"};
    public static final String[] qCornerRemove = {"B'", "D'", "B"};
    public static final String[] rCornerRemove = {"B", "D", "B'"};
    public static final String[] eCornerRemove = {"L'", "D'", "L"};
    public static final String[] fCornerRemove = {"L", "D", "L'"};
    public static final String[] iCornerRemove = {"F'", "D'", "F"};

    public static final String[] cCornerRemove = {"R'", "D'", "R"};
    public static final String[] bCornerRemove = {"B'", "D'", "B"};
    public static final String[] aCornerRemove = {"L'", "D'", "L"};
    public static final String[] dCornerRemove = {"F'", "D'", "F"};


    public static final String[] greenOrangeEdge = {"D", "L", "D'", "L'", "D'", "F'", "D", "F"};
    public static final String[] greenRedEdge = {"D'", "R'", "D", "R", "D", "F", "D'", "F'"};
    public static final String[] redGreenEdge = {"D", "F", "D'", "F'", "D'", "R'", "D", "R"};
    public static final String[] redBlueEdge = {"D'", "B'", "D", "B", "D", "R", "D'", "R'"};
    public static final String[] blueRedEdge = {"D", "R", "D'", "R'", "D'", "B'", "D", "B"};
    public static final String[] blueOrangeEdge = {"D'", "L'", "D", "L", "D", "B", "D'", "B'"};
    public static final String[] orangeBlueEdge = {"D", "B", "D'", "B'", "D'", "L'", "D", "L"};
    public static final String[] orangeGreenEdge = {"D'", "F'", "D", "F", "D", "L", "D'", "L'"};

    public static final String[] yellowCross = {"F", "R", "U", "R'", "U'", "F'"};
    public static final String[] yellowCrossAlt = {"f", "R", "U", "R'", "U'", "f'"};

    public static final String[] sune = {"R", "U", "R'", "U", "R", "U2", "R'"};
    public static final String[] antiSune = {"R", "U2", "R'", "U'", "R", "U'", "R'"};
    public static final String[] pi = {"R", "U2", "R2", "U'", "R2", "U'", "R2", "U2", "R"};
    public static final String[] H = {"R", "U", "R'", "U", "R", "U'", "R'", "U", "R", "U2", "R'"};
    public static final String[] L = {"F", "R'", "F'", "r", "U", "R", "U'", "r'"};
    public static final String[] T = {"r", "U", "R'", "U'", "r'", "F", "R", "F'"};
    public static final String[] U = {"R2", "D", "R'", "U2", "R", "D'", "R'", "U2", "R'"};

    public static final String[] createHeadlightsOnePair = {"R", "U", "R'", "U'", "R'", "F", "R2", "U'", "R'", "U'", "R", "U", "R'", "F'"};
    public static final String[] createHeadlightsNoPair = {"F", "R", "U'", "R'", "U'", "R", "U", "R'", "F'", "R", "U", "R'", "U'", "R'", "F", "R", "F'"};

    public static final String[] pllPlus = {"M2", "U", "M2", "U2", "M2", "U", "M2"};
    public static final String[] pllAdjacent = {"M2", "U", "M2", "U", "M", "U2", "M2", "U2", "M"};
    public static final String[] pllLeft3Cycle = {"L'", "U", "L'", "U'", "L'", "U'", "L'", "U", "L", "U", "L2"};
    public static final String[] pllRight3Cycle = {"R", "U'", "R", "U", "R", "U", "R", "U'", "R'", "U'", "R2"};

}
