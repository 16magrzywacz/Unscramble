package com.matthew.unscramble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Type;
import java.util.Arrays;

public class CameraInputActivity extends AppCompatActivity implements JavaCamera2View.CvCameraViewListener2 {

    JavaCamera2View cameraBridgeViewBase;
    Mat mRGBA;

    Button capture;
    ImageButton home;

    ImageView centreSquare;
    ImageView indicatorTop;
    ImageView indicatorBottom;

    //This object is what manages/notifies the camera about certain things such as
    //if the camera is accessed/loaded and will take appropriate action depending
    //on the status
    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(CameraInputActivity.this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                //will enable the camera view if when the camera module is activated
                //and marked as successful
                case BaseLoaderCallback.SUCCESS: cameraBridgeViewBase.enableView(); break;
                default: super.onManagerConnected(status); break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_input);

        Store.usingCamera = true;

        Store.actualCols = new String[9];

        //Requesting the app to use the camera
        ActivityCompat.requestPermissions(CameraInputActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

        //Linking camera object to the one in the layout, and setting it visible
        cameraBridgeViewBase = (JavaCamera2View)findViewById(R.id.javaCameraView);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(CameraInputActivity.this);

        //links centre square and indicators to ones in the layout
        centreSquare = (ImageView)findViewById(R.id.square5);
        indicatorTop = (ImageView)findViewById(R.id.cameraIndicatorTop);
        indicatorBottom = (ImageView)findViewById(R.id.cameraIndicatorBottom);

        //Loads the calibrated colours, to see if the user has performed a calibration or not
        SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
        String cal = sharedPreferences.getString("calibrated_colours", "");
        //If not calibration, display a warning
        if(cal.equals("")) {
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
            dialog.setTitle("Warning!");
            dialog.setMessage("Look like you haven't calibrated any colours yet! Would you like to do " +
                    "the calibration or use the default colours for now?");
            dialog.setNegativeButton("Use Default", (dialog12, which) -> {/*Do nothing*/});
            dialog.setPositiveButton("Calibrate", (dialog1, which) -> {
                //Sets a preference so that the app knows where to redirect the user to
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cal_redirect", "input");
                editor.apply();
                //Switches view
                Intent intent = new Intent(this, CalibrateActivity.class);
                startActivity(intent);
            });
            dialog.show();
        }

        home = (ImageButton)findViewById(R.id.cameraHome);

        //Returns to home when pressed
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraInputActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Updates the above 3 variables to different colours
        updateIndicatorsAndCentre();

        //Links camera to the one in the layour file
        capture = (Button)findViewById(R.id.captureButton);

        //Switches view when button pressed
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraInputActivity.this, ManualInputActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateIndicatorsAndCentre() {
        //Changes the images shown based on how many sides have been done
        switch (Store.sidesDone) {
            case 0: break;
            case 1: centreSquare.setImageResource(R.drawable.green_square); indicatorTop.setImageResource(R.drawable.white_square);
            indicatorBottom.setImageResource(R.drawable.yellow_square); break;
            case 2: centreSquare.setImageResource(R.drawable.red_square); indicatorTop.setImageResource(R.drawable.white_square);
            indicatorBottom.setImageResource(R.drawable.yellow_square); break;
            case 3: centreSquare.setImageResource(R.drawable.blue_square); indicatorTop.setImageResource(R.drawable.white_square);
            indicatorBottom.setImageResource(R.drawable.yellow_square); break;
            case 4: centreSquare.setImageResource(R.drawable.orange_square); indicatorTop.setImageResource(R.drawable.white_square);
            indicatorBottom.setImageResource(R.drawable.yellow_square); break;
            case 5: centreSquare.setImageResource(R.drawable.yellow_square); indicatorTop.setImageResource(R.drawable.green_square);
            indicatorBottom.setImageResource(R.drawable.blue_square); break;
        }
    }

    //Runs when a permission was allowed/denied
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                //If the camera permission was granted, then notify the camera view that the permission was granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraBridgeViewBase.setCameraPermissionGranted();
                } else {
                    //Permission not granted
                }
                return;
            }
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        //Initialising the two material variables
        mRGBA = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        //Setting the 2 materials to different channels on the input frame
        mRGBA = inputFrame.rgba();

        //Scale factor
        float scaleFactor = mRGBA.width() / 408;

        //2d array holding colour info for the 9 squares
        double[][] colVals = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};

        //loops through a 45dp*45dp square
        for(int i = Math.round(-22*scaleFactor); i < Math.round(23*scaleFactor); i ++) {
            for(int j = Math.round(-22*scaleFactor); j < Math.round(23*scaleFactor); j ++) {
                //loops through each row and column of squares
                for(int k = 0; k < 3; k++) {
                    for(int l = 0; l < 3; l++) {
                        //loops through each item in the individual array
                        for(int m = 0; m < 4; m++) {
                            //Does some maths to get coordinates by shifting around the centre point, depending on the loop variables
                            colVals[3*k + l][m] += mRGBA.get(j + Math.round(mRGBA.height()/2 + 101*scaleFactor - l*101*scaleFactor),
                                    i + Math.round(mRGBA.width()/2 - 101*scaleFactor + k*101*scaleFactor))[m];
                        }

                    }
                }
            }
        }

        //Divides each element by number of pixels to finish average
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 4; j ++) {
                colVals[i][j] = Math.round(colVals[i][j] / ((45*scaleFactor)*(45*scaleFactor)));
            }
        }

        //Gets an array of the 9 squares in the layout file.
        ImageView[] squares = {(ImageView)findViewById(R.id.square1), (ImageView)findViewById(R.id.square2),
                (ImageView)findViewById(R.id.square3), (ImageView)findViewById(R.id.square4),
                (ImageView)findViewById(R.id.square5), (ImageView)findViewById(R.id.square6),
                (ImageView)findViewById(R.id.square7), (ImageView)findViewById(R.id.square8),
                (ImageView)findViewById(R.id.square9)};

        //Converts the array of colour values to an array of colours
        for(int i = 0; i < 9; i++) {
            Store.actualCols[i] = getColour(colVals[i]);

            if(i != 4) {
                //Sets the colour of the ith square with the ith colour in the actualCols variable
                switch (Store.actualCols[i]) {
                    case "R": squares[i].setImageResource(R.drawable.red_square); break;
                    case "G": squares[i].setImageResource(R.drawable.green_square); break;
                    case "B": squares[i].setImageResource(R.drawable.blue_square); break;
                    case "W": squares[i].setImageResource(R.drawable.white_square); break;
                    case "Y": squares[i].setImageResource(R.drawable.yellow_square); break;
                    case "O": squares[i].setImageResource(R.drawable.orange_square); break;
                }
            }else {
                //Ensures the stored value for the centre sticker remains constant
                switch (Store.sidesDone) {
                    case 0: Store.actualCols[4] = "W"; break;
                    case 1: Store.actualCols[4] = "G"; break;
                    case 2: Store.actualCols[4] = "R"; break;
                    case 3: Store.actualCols[4] = "B"; break;
                    case 4: Store.actualCols[4] = "O"; break;
                    case 5: Store.actualCols[4] = "Y"; break;
                }
            }
        }

        return mRGBA;
    }


    public String getColour(double[] vals) {

        //Stores red, green, and blue values of the input colour
        int red = (int) vals[0];
        int green = (int) vals[1];
        int blue = (int) vals[2];

        // stored in this colour order R G B W Y O
        int[] closeness = new int[6];

        //Obtains calibrated colours, and determines if a calibration had been performed
        SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
        String json = sharedPreferences.getString("calibrated_colours", "");
        boolean calibrated;
        calibrated = !json.equals("");

        //Converts the json back to int arrays
        Type type = new TypeToken<int[][]>() {}.getType();
        Gson gson = new Gson();
        int[][] colours = gson.fromJson(json, type);

        //Calculates a closeness value to each of the calibrated colours by adding the difference of each
        // of the 3 red, green and blue colour vals
        for(int i = 0; i < 6; i++) {
            //Depending on if the colours have been calibrated or not, set the arrays to be used
            int[][] arr;
            System.out.println(calibrated);
            if(!calibrated) {
                arr = Store.calibratedColours;
            }else {
                arr = colours;
            }
            System.out.println(Arrays.deepToString(arr));
            closeness[i] = Math.abs(red - arr[i][0]) + Math.abs(green - arr[i][1])
                    + Math.abs(blue - arr[i][2]);
        }

        //Gets the smallest closeness value, and stores the index of that value
        int closestVal = 99999999;
        int closestIndex = 0;
        for(int i = 0; i < 6; i++) {
            if(closeness[i] < closestVal) {
                closestIndex = i;
                closestVal = closeness[i];
            }
        }

        //Depending on the index of the closeness value, return the relevant colour
        switch (closestIndex) {
            case 0: return "R";
            case 1: return "G";
            case 2: return "B";
            case 3: return "W";
            case 4: return "Y";
            case 5: return "O";
        }

        return null;

    }

    //Disables camera view when page is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    //Disabled camera view when the user has the app minimised for example
    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    //Enables/marks success when the app is resumed
    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, CameraInputActivity.this, baseLoaderCallback);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}