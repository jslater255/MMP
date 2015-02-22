package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;

public class Activity2 extends ActionBarActivity {

    TextView textView;
    ReadWrite rw;
    ArrayList<Double> wav = new ArrayList<>();
    Context context;

    GraphView graph;

    ProgressBar mProgress;

    // Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activity2);

        rw = new ReadWrite();

        context = this;
        mProgress = (ProgressBar) findViewById(R.id.progBar);


        startLongRunningOperation();

        textView = (TextView) findViewById(R.id.userNameOut);



        //Gets previous intent from MainActivity
        Intent intent = getIntent();//

        //intent.getExtras().getString(MainActivity.EXTRA_MESSAGE) "/storage/emulated/0/pdclass/ea.csv"


        //rw.writeCSV(this,"/storage/emulated/0/pdclass/");

        //wav = rw.readCSV(this, "/storage/emulated/0/pdclass/");
        textView.setText(Integer.toString(wav.size()));

        graph = (GraphView) findViewById(R.id.graph);

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(500);
        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.setVisibility(INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void startLongRunningOperation() {

        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread() {
            public void run() {
                wav = rw.readCSV(context);
                mHandler.post(mUpdateResults);
            }
        };
        t.start();
    }

    private void updateResultsInUi() {

        // Back in the UI thread -- update our UI elements based on the data in mResults

        DataPoint[] data = new DataPoint[wav.size()];
        for (int idx=0; idx < wav.size(); idx++)
        {
            data[idx] = new DataPoint(idx, wav.get(idx));

        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
        graph.setVisibility(View.VISIBLE);
        mProgress.setVisibility(INVISIBLE);
    }
}
