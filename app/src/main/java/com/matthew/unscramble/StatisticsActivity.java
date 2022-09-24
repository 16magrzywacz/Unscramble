package com.matthew.unscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    ImageButton home;

    GraphView graph;

    TextView bestText, meanText, fiveText, twelveText;

    Button reset;

    double best;
    double avg5, avg12, avgTot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        home = (ImageButton) findViewById(R.id.statisticsMenuHome);

        home.setOnClickListener(v -> {
            Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        update();

        reset = (Button) findViewById(R.id.resetTimes);

        reset.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("times");
            editor.apply();
            update();
        });


    }

    public void update() {
        //Obtains list of times
        SharedPreferences sharedPreferences = getSharedPreferences("UNSCRAMBLE", MODE_PRIVATE);
        String json = sharedPreferences.getString("times", "");
        Gson gson = new Gson();
        ArrayList<Integer> times;
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        if(json.equals("")) {
            times = new ArrayList<>();
        }else {
            times = gson.fromJson(json, type);
        }
        //Obtains size of times list
        int size = times.size();
        //If there are some recorded times
        if(size > 0) {
            //Obtains fastest time and total mean average
            best = (double) Collections.min(times) / 1000;
            avgTot = mean(times);
            //If at least 5 solves have been done, perform an ao5 for the last 5 solves
            if(size > 4) {
                avg5 = averageOfx(times, 5) / 1000;
                //If at least 12 solves have been done, perform an ao5 for the last 12 solves
                if(size > 11) {
                    avg12 = averageOfx(times, 12) / 1000;
                }else {
                    avg12 = 0;
                }
            }else {
                avg5 = 0;
            }
            //If no solves have been done, set all variables to 0;
        }else {
            avg5 = 0;
            avg12 = 0;
            avgTot = 0;
            best = 0;
        }

        graph = (GraphView) findViewById(R.id.graph);
        //Creates empty set of coordinates
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        //Works out standard deviation (sigma) and mean (mew) for the normal distribution
        float mew = (float) avgTot;
        StandardDeviation standardDeviation = new StandardDeviation();
        double[] vals = times.stream().mapToDouble(i -> (double) i).toArray();
        //Converts time in milliseconds to seconds (2dp)
        for(int i = 0; i < vals.length; i++) {
            vals[i] = (double) Math.round((vals[i] / 1000) * 100d) / 100d;
        }
        //Works out standard deviation of data set
        float sigma = (float) standardDeviation.evaluate(vals);

        //Creates set of 100 evenly spaced x-points, starting and ending at 3 sd away from mean
        double[] x1 = linspace(mew-(3*sigma), mew+(3*sigma), 100);

        //Only create distribution if sd is greater than 0
        if(sigma > 0) {
            //Creates normal distribution based on this mean and sd
            NormalDistribution normalDistribution = new NormalDistribution(mew, sigma);
            //Pairs up each x coord with the y coord from normal distribution, and appends to coordinate list
            for (double x : x1) {
                double y = normalDistribution.density(x);
                series.appendData(new DataPoint(x,y),false, x1.length);
            }
        }

        //Sets up the graph
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Density");
        graph.getGridLabelRenderer().setNumVerticalLabels(6);
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);

        //Want to plot data when at least 10 times are recorded
        if(vals.length > 10) {
            graph.getViewport().setMaxX(Collections.max(times) / 1000);
            graph.getViewport().setMinX(Collections.min(times) / 1000);
            graph.addSeries(series);
        }else {
            graph.removeAllSeries();
        }

        bestText = (TextView) findViewById(R.id.bestTime);
        meanText = (TextView) findViewById(R.id.meanTime);
        fiveText = (TextView) findViewById(R.id.ao5Time);
        twelveText = (TextView) findViewById(R.id.ao12Time);

        //Creating object that will format the number to 2dp
        DecimalFormat df = new DecimalFormat("0.00");

        //Setting text to formatted numbers
        bestText.setText("Best Time:               " + df.format(best) + "s");
        meanText.setText("Mean Time:             " + df.format(avgTot) + "s");
        fiveText.setText("Current Ao5:          " + df.format(avg5) + "s");
        twelveText.setText("Current Ao12:        " + df.format(avg12) + "s");
    }

    public double mean(ArrayList<Integer> times) {
        int val = 0;
        for(int i = 0; i < times.size(); i++) {
            val+= times.get(i);
        }
        return (double) val / times.size() / 1000;
    }

    public static double[] linspace(double start, double stop, int num) {
        //Calculates step value
        double step = (stop - start)/(num - 1);
        //Creates empty return variable
        double[] ret = new double[num];
        //Sets ith item to the start number plus i number of steps
        for(int i = 0; i < num; i++) {
            ret[i] = start + (i*step);
        }
        return ret;
    }

    //Cubing average, so remove best and worst times, and calculate mean of remaining times
    public double averageOfx(ArrayList<Integer> times, int x) {
        //Obtains list of the last x items
        List<Integer> lastFive = new ArrayList<>(times.subList(times.size() - x, times.size()));
        //Removes the fastest and slowest solve time
        lastFive.remove(Collections.max(lastFive));
        lastFive.remove(Collections.min(lastFive));
        //Returns mean average of remaining items
        return lastFive.stream().mapToInt(val -> val).average().orElse(0);
    }
}