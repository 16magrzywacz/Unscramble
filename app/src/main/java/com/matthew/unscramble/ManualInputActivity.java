package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

public class ManualInputActivity extends AppCompatActivity {

    Button whiteButton, yellowButton, redButton, orangeButton, greenButton, blueButton, nextButton;
    String selectedColour;

    ImageView grid1, grid2, grid3, grid4, grid5, grid6, grid7, grid8, grid9;

    ImageView currentColour, topIndicator, bottomIndicator;

    ImageButton home;

    String[] gridColours = new String[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        selectedColour = "W";

        grid1 = (ImageView)findViewById(R.id.grid1);
        grid2 = (ImageView)findViewById(R.id.grid2);
        grid3 = (ImageView)findViewById(R.id.grid3);
        grid4 = (ImageView)findViewById(R.id.grid4);
        grid5 = (ImageView)findViewById(R.id.grid5);
        grid6 = (ImageView)findViewById(R.id.grid6);
        grid7 = (ImageView)findViewById(R.id.grid7);
        grid8 = (ImageView)findViewById(R.id.grid8);
        grid9 = (ImageView)findViewById(R.id.grid9);

        //Preloads each grid tile with the colours stored in the Store class
        for(int i = 0; i < 9; i++) {
            gridColours[i] = Store.actualCols[i];
        }

        grid1.setImageResource(colToImage(gridColours[0]));
        grid2.setImageResource(colToImage(gridColours[1]));
        grid3.setImageResource(colToImage(gridColours[2]));
        grid4.setImageResource(colToImage(gridColours[3]));
        grid5.setImageResource(colToImage(gridColours[4]));
        grid6.setImageResource(colToImage(gridColours[5]));
        grid7.setImageResource(colToImage(gridColours[6]));
        grid8.setImageResource(colToImage(gridColours[7]));
        grid9.setImageResource(colToImage(gridColours[8]));

        //linking each button to the buttons in the layout xml
        whiteButton = (Button)findViewById(R.id.whiteButton);
        yellowButton = (Button)findViewById(R.id.yellowButton);
        redButton = (Button)findViewById(R.id.redButton);
        orangeButton = (Button)findViewById(R.id.orangeButton);
        greenButton = (Button)findViewById(R.id.greenButton);
        blueButton = (Button)findViewById(R.id.blueButton);
        nextButton = (Button)findViewById(R.id.nextSideButton);

        if(Store.sidesDone == 5) {
            nextButton.setText("DONE");
        }

        home = (ImageButton)findViewById(R.id.manualHome);

        //Returns to home when pressed
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManualInputActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        currentColour = (ImageView)findViewById(R.id.selectedColour);

        topIndicator = (ImageView)findViewById(R.id.indicatorTop);
        bottomIndicator = (ImageView)findViewById(R.id.indicatorBottom);

        //Changes the colour variable when button pressed
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColour = "W";
                currentColour.setImageResource(R.drawable.white_square);
            }
        });

        //Changes the colour variable when button pressed
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColour = "Y";
                currentColour.setImageResource(R.drawable.yellow_square);
            }
        });

        //Changes the colour variable when button pressed
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColour = "R";
                currentColour.setImageResource(R.drawable.red_square);
            }
        });

        //Changes the colour variable when button pressed
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColour = "O";
                currentColour.setImageResource(R.drawable.orange_square);
            }
        });

        //Changes the colour variable when button pressed
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColour = "G";
                currentColour.setImageResource(R.drawable.green_square);
            }
        });

        //Changes the colour variable when button pressed
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColour = "B";
                currentColour.setImageResource(R.drawable.blue_square);
            }
        });

        //Changes colour of grid depending on selected colour
        grid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid1.setImageResource(getSquareColour());
                gridColours[0] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid2.setImageResource(getSquareColour());
                gridColours[1] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid3.setImageResource(getSquareColour());
                gridColours[2] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid4.setImageResource(getSquareColour());
                gridColours[3] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid6.setImageResource(getSquareColour());
                gridColours[5] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid7.setImageResource(getSquareColour());
                gridColours[6] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid8.setImageResource(getSquareColour());
                gridColours[7] = selectedColour;
            }
        });

        //Changes colour of grid depending on selected colour
        grid9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid9.setImageResource(getSquareColour());
                gridColours[8] = selectedColour;
            }
        });

        //Functionality for the next side button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Store.usingCamera) {
                    //determines what side is currently being coloured in
                    if (Store.sidesDone == 0) {
                        //sets the side variable to colours stored in the grid
                        Store.whiteSide = setSide(Store.whiteSide);
                        //Sets indicator and centre grid tile to different images/colours
                        grid5.setImageResource(R.drawable.green_square);
                        topIndicator.setImageResource(R.drawable.white_square);
                        bottomIndicator.setImageResource(R.drawable.yellow_square);
                        gridColours[4] = "G";
                    } else if (Store.sidesDone == 1) {
                        Store.greenSide = setSide(Store.greenSide);
                        grid5.setImageResource(R.drawable.red_square);
                        topIndicator.setImageResource(R.drawable.white_square);
                        bottomIndicator.setImageResource(R.drawable.yellow_square);
                        gridColours[4] = "R";
                    } else if (Store.sidesDone == 2) {
                        Store.redSide = setSide(Store.redSide);
                        grid5.setImageResource(R.drawable.blue_square);
                        topIndicator.setImageResource(R.drawable.white_square);
                        bottomIndicator.setImageResource(R.drawable.yellow_square);
                        gridColours[4] = "B";
                    } else if (Store.sidesDone == 3) {
                        Store.blueSide = setSide(Store.blueSide);
                        grid5.setImageResource(R.drawable.orange_square);
                        topIndicator.setImageResource(R.drawable.white_square);
                        bottomIndicator.setImageResource(R.drawable.yellow_square);
                        gridColours[4] = "O";
                    } else if (Store.sidesDone == 4) {
                        Store.orangeSide = setSide(Store.orangeSide);
                        grid5.setImageResource(R.drawable.yellow_square);
                        topIndicator.setImageResource(R.drawable.green_square);
                        bottomIndicator.setImageResource(R.drawable.blue_square);
                        gridColours[4] = "Y";
                        nextButton.setText("DONE");
                    } else if (Store.sidesDone == 5) {
                        //Checks to see if cube is a legit scramble before trying to solve
                        Store.yellowSide = setSide(Store.yellowSide);
                        if(Verify.verify(new String[][][]{Store.redSide, Store.orangeSide, Store.greenSide, Store.blueSide,
                                Store.whiteSide, Store.yellowSide}) == null) {
                            MainActivity.usercube = new Cube(Store.whiteSide, Store.greenSide, Store.redSide,
                                    Store.blueSide, Store.orangeSide, Store.yellowSide);
                            Intent intent = new Intent(ManualInputActivity.this, SolutionActivity.class);
                            startActivity(intent);
                        }else {
                            Toast toast = new Toast(ManualInputActivity.this);
                            switch(Objects.requireNonNull(Verify.verify(new String[][][]{Store.redSide, Store.orangeSide, Store.greenSide,
                                    Store.blueSide, Store.whiteSide, Store.yellowSide}))) {
                                case "1": toast.setText("There are not 9 of each colour!"); break;
                                case "2": toast.setText("One or more edges/corners contain colours that are opposites of each other!"); break;
                                case "3": toast.setText("One or more corners have been twisted!"); break;
                                case "4": toast.setText("One or more edges have been flipped!"); break;
                                case "5": toast.setText("One or more edges or corners have been swapped!"); break;
                            }
                            toast.show();
                            Intent intent = new Intent(ManualInputActivity.this, SolveMenuActivity.class);
                            startActivity(intent);
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (i != 4) {
                            gridColours[i] = "W";
                        }
                    }
                    resetTiles();
                    Store.sidesDone += 1;

                }else {
                    //determines what side is currently being coloured in
                    if (Store.sidesDone == 0) {
                        //sets the side variable to colours stored in the grid
                        Store.whiteSide = setSide(Store.whiteSide);
                        Intent intent = new Intent(ManualInputActivity.this, CameraInputActivity.class);
                        startActivity(intent);
                    } else if (Store.sidesDone == 1) {
                        Store.greenSide = setSide(Store.greenSide);
                        Intent intent = new Intent(ManualInputActivity.this, CameraInputActivity.class);
                        startActivity(intent);
                    } else if (Store.sidesDone == 2) {
                        Store.redSide = setSide(Store.redSide);
                        Intent intent = new Intent(ManualInputActivity.this, CameraInputActivity.class);
                        startActivity(intent);
                    } else if (Store.sidesDone == 3) {
                        Store.blueSide = setSide(Store.blueSide);
                        Intent intent = new Intent(ManualInputActivity.this, CameraInputActivity.class);
                        startActivity(intent);
                    } else if (Store.sidesDone == 4) {
                        Store.orangeSide = setSide(Store.orangeSide);
                        Intent intent = new Intent(ManualInputActivity.this, CameraInputActivity.class);
                        startActivity(intent);
                    } else if (Store.sidesDone == 5) {
                        //Checks to see if cube is a legit scramble before trying to solve
                        Store.yellowSide = setSide(Store.yellowSide);
                        if(Verify.verify(new String[][][]{Store.redSide, Store.orangeSide, Store.greenSide, Store.blueSide, Store.whiteSide, Store.yellowSide}) == null) {
                            MainActivity.usercube = new Cube(Store.whiteSide, Store.greenSide, Store.redSide, Store.blueSide, Store.orangeSide, Store.yellowSide);
                            Intent intent = new Intent(ManualInputActivity.this, SolutionActivity.class);
                            startActivity(intent);
                        }else {
                            Toast toast = new Toast(ManualInputActivity.this);
                            switch(Verify.verify(new String[][][]{Store.redSide, Store.orangeSide, Store.greenSide, Store.blueSide, Store.whiteSide, Store.yellowSide})) {
                                case "1": toast.setText("There are not 9 of each colour!"); break;
                                case "2": toast.setText("One or more edges/corners contain colours that are opposites of each other!"); break;
                                case "3": toast.setText("One or more corners have been twisted!"); break;
                                case "4": toast.setText("One or more edges have been flipped!"); break;
                                case "5": toast.setText("One or more edges or corners have been swapped!"); break;
                            }
                            toast.show();
                            Intent intent = new Intent(ManualInputActivity.this, SolveMenuActivity.class);
                            startActivity(intent);
                        }
                    }
                    Store.sidesDone+=1;
                }
            }
        });


    }

    public int colToImage(String col) {
        switch (col) {
            case "W": return R.drawable.white_square;
            case "Y": return R.drawable.yellow_square;
            case "R": return R.drawable.red_square;
            case "O": return R.drawable.orange_square;
            case "G": return R.drawable.green_square;
            case "B": return R.drawable.blue_square;
            default: return 0;
        }
    }

    public void resetTiles() {
        //Resets every tile except central tile to white
        grid1.setImageResource(R.drawable.white_square);
        grid2.setImageResource(R.drawable.white_square);
        grid3.setImageResource(R.drawable.white_square);
        grid4.setImageResource(R.drawable.white_square);
        grid6.setImageResource(R.drawable.white_square);
        grid7.setImageResource(R.drawable.white_square);
        grid8.setImageResource(R.drawable.white_square);
        grid9.setImageResource(R.drawable.white_square);

    }

    public String[][] setSide(String[][] side) {
        //creates new array of same size
        String[][] ret = new String[3][3];
        for(int i = 0; i < 3; i ++) {
            for(int j = 0; j < 3; j++) {
                //replaces each empty element with the respective element of the current grid colours
                ret[i][j] = gridColours[(3*i) + j];
            }
        }
        return ret;
    }

    //Returns the id of the different images depending on the selected colour
    public int getSquareColour() {
        switch (selectedColour) {
            case "W": return R.drawable.white_square;
            case "Y": return R.drawable.yellow_square;
            case "R": return R.drawable.red_square;
            case "O": return R.drawable.orange_square;
            case "G": return R.drawable.green_square;
            case "B": return R.drawable.blue_square;
            default: return 0;
        }
    }
}