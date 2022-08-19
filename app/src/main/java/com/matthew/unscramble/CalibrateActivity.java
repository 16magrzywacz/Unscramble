package com.matthew.unscramble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.PopupWindowCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

public class CalibrateActivity extends AppCompatActivity implements JavaCamera2View.CvCameraViewListener2  {

    JavaCamera2View javaCamera2View;
    Mat mRGBA;
    ImageButton home;
    Button next;

    int sidesDone;
    int[][] colours;
    int[] colour;

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(CalibrateActivity.this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                //will enable the camera view if when the camera module is activated
                //and marked as successful
                case BaseLoaderCallback.SUCCESS: javaCamera2View.enableView(); break;
                default: super.onManagerConnected(status); break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        sidesDone = 0;
        colours = new int[6][3];
        colour = new int[3];

        home = (ImageButton)findViewById(R.id.calibrationHome);

        //Returns to home when pressed
        home.setOnClickListener(view -> {
            Intent intent = new Intent(CalibrateActivity.this, MainActivity.class);
            startActivity(intent);
        });

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(CalibrateActivity.this);
        dialog.setTitle("Calibration");
        dialog.setMessage("Place a red sticker inside the square, then press capture");
        dialog.setPositiveButton("OK", (dialog1, which) -> {/*Do nothing*/});
        dialog.show();

        next = (Button) findViewById(R.id.calibrateNext);

        next.setOnClickListener(view -> {
            // stored in this colour order R G B W Y O
            colours[sidesDone][0] = colour[0];
            colours[sidesDone][1] = colour[1];
            colours[sidesDone][2] = colour[2];
            System.out.println(Arrays.deepToString(colours));
            sidesDone+=1;


            switch (sidesDone) {
                case 1: dialog.setMessage("Place a green sticker inside the square, then press capture"); break;
                case 2: dialog.setMessage("Place a blue sticker inside the square, then press capture"); break;
                case 3: dialog.setMessage("Place a white sticker inside the square, then press capture"); break;
                case 4: dialog.setMessage("Place a yellow sticker inside the square, then press capture"); break;
                case 5: dialog.setMessage("Place a orange sticker inside the square, then press finish"); next.setText("Finish"); break;
                case 6: {
                    System.out.println(Arrays.deepToString(colours));

                    //Converts colour variable to a JSON string
                    Gson gson = new Gson();
                    String json = gson.toJson(colours);

                    //Adds this json to the shared preferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("calibrated_colours", json);
                    editor.apply();

                    Intent intent;
                    //Redirects user based on where they originally came from
                    if(sharedPreferences.getString("cal_redirect", "").equals("settings")) {
                        intent = new Intent(CalibrateActivity.this, SettingsActivity.class);
                    }else {
                        intent = new Intent(CalibrateActivity.this, CameraInputActivity.class);
                    }
                    startActivity(intent);
                }
            }
            if(sidesDone != 6) {
                dialog.show();
            }
        });

        //Requesting the app to use the camera
        ActivityCompat.requestPermissions(CalibrateActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

        javaCamera2View = (JavaCamera2View)findViewById(R.id.calibratejavaCameraView);
        javaCamera2View.setVisibility(SurfaceView.VISIBLE);
        javaCamera2View.setCvCameraViewListener(CalibrateActivity.this);
    }

    //Runs when a permission was allowed/denied
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                //If the camera permission was granted, then notify the camera view that the permission was granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    javaCamera2View.setCameraPermissionGranted();
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
        mRGBA = inputFrame.rgba();

        //obtains width and height of camera
        int width = mRGBA.width();
        int height = mRGBA.height();

        //sets up some variables
        double totR = 0;
        double totG = 0;
        double totB = 0;
        //loops through a 60x60 grid of pixels, about centre
        for(int i = 0; i < 60; i++) {
            for(int j = 0; j < 60; j++) {
                //obtains color info at that point
                double[] tempCol = mRGBA.get((height/2) - 30 + i,(width/2) - 30 + j);
                //increments total values
                totR += tempCol[0];
                totG += tempCol[1];
                totB += tempCol[2];
            }
        }
        //Draws square in centre of image
        Imgproc.rectangle(mRGBA, new Point(width/2 - 30, height/2 - 30), new Point(width/2 + 30, height/2 + 30), new Scalar(255,255,255), 5);

        //divides by total pixels to gain average and assigns to colour variable
        colour[0] = (int) Math.round(totR/3600);
        colour[1] = (int) Math.round(totG/3600);
        colour[2] = (int) Math.round(totB/3600);

        return mRGBA;
    }

    //Disables camera view when page is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(javaCamera2View != null) {
            javaCamera2View.disableView();
        }
    }

    //Disabled camera view when the user has the app minimised for example
    @Override
    protected void onPause() {
        super.onPause();
        if(javaCamera2View != null) {
            javaCamera2View.disableView();
        }
    }

    //Enables/marks success when the app is resumed
    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, CalibrateActivity.this, baseLoaderCallback);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}