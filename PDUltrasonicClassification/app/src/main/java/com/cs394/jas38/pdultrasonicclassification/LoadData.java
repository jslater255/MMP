package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     :
 * <p/>
 * FUNCTION      :
 * <p/>
 * INPUTS        :
 * <p/>
 * OUTPUTS       :
 * <p/>
 * AMENDMENTS    :  Created by, James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class LoadData extends ActionBarActivity {

    ReadWrite rw;
    StatCalculator st;
    Broker broker;
    ArrayList<Double> wav = new ArrayList<>();
    Context context;

    GraphView graph;

    ProgressBar mProgress;

    TextView avg_out;
    TextView stan_dev;

    Button playBtn;

    // Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();

    // Create runnable for posting
    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreate
     * <p/>
     * FUNCTION      : inflates the activity_load_data.xml UI file
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_load_data);
        this.setTitle("View Data");

        // Creates instance of the ReadWrite class to read the CSV file
        rw = new ReadWrite();
        // Creates an instance of the StatCalculator class
        st = new StatCalculator();
        // Create instance of the Broker class
        broker = new Broker();

        // Sets the Context to this so it can be passed to other classes that need to update or
        // have access to the screen
        context = this;

        broker.brokerCallIntNative(context);
        broker.brokerCallNative(context);
        System.out.println("Path File: " + context.getExternalFilesDir(null) + "/ea.csv");
        broker.brokerCallNativeOpenFile(context, context.getExternalFilesDir(null) + "/ea.wav");

        // Gets the progress wheel in the xml file to be able to set
        // invisible once the graph is ready to load.
        mProgress = (ProgressBar) findViewById(R.id.progBar);


        //startLoadingCSV();

        // Gets a pointer the the TextView from the XML
        avg_out = (TextView) findViewById(R.id.avg_out);
        // Gets a pointer the the TextView from the XML
        stan_dev = (TextView) findViewById(R.id.stan_dev_out);

        // Gets a pointer the the Button from the XML
        playBtn = (Button) findViewById(R.id.playWAV);

        // Adds a listener to the play button to play back th wav file.
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(context.getExternalFilesDir(null), "/ea.wav");
                Context appContext = getApplicationContext();
                MediaPlayer mp = MediaPlayer.create(appContext, Uri.fromFile(file));
               // mp.start();

                openWav();
            }
        });

        //Gets previous intent from MainActivity
        Intent intent = getIntent();//

        // Gets a pointer the the Graph from the XML
        graph = (GraphView) findViewById(R.id.graph);

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(2000);
        // set manual Y bounds
        //graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setMinY(-1);
        //graph.getViewport().setMaxY(1);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        // Turn the graph to not visible as it has no data.
        // It will be visible once the thread has loaded the CSV file
        graph.setVisibility(INVISIBLE);
    }

    // convert two bytes to one double in the range -1 to 1
    static double bytesToDouble(byte firstByte, byte secondByte) {
        // convert two bytes to one short (little endian)
        int s = (secondByte << 8) | firstByte;
        // convert to range from -1 to (just below) 1
        return s / 32768.0;
    }

    // Returns left and right double arrays. 'right' will be null if sound is mono.
    public void openWav()
    {
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreateOptionsMenu
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onOptionsItemSelected
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
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

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : startLoadingCSV
     * <p/>
     * FUNCTION      : Creates a thread to load the CSV file. This is needed as it is
     *                  a very big file and it allows the UI thread to stay responsive
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    protected void startLoadingCSV() {

        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread() {
            public void run() {
                wav = rw.readCSV(context, "/ea.csv");
                mHandler.post(mUpdateResults);
            }
        };
        t.start();
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : updateResultsInUi
     * <p/>
     * FUNCTION      : Once the thread has returned we update the UI
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void updateResultsInUi() {

        // Back in the UI thread -- update our UI elements based on the data in mResults

        DataPoint[] data = new DataPoint[wav.size()-1];
        for (int idx=0; idx < (wav.size()-1); idx++)
        {
            //we put idx by 0.6 as that is how many milliseconds there are for each point
            //data[idx] = new DataPoint((0.6*idx), st.getSlope(wav.get(idx),wav.get(idx+1),idx));// To see the DY/DX plot
            data[idx] = new DataPoint(idx, wav.get(idx));

        }

        // Creates a series to add the to the graph
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
        // Sets the Graph to visible and the progress wheel to invisible
        graph.setVisibility(View.VISIBLE);
        mProgress.setVisibility(INVISIBLE);

        // Gets some stats from the array loaded
        Double avg = st.avg(wav);
        avg_out.setText(fourDecPla(avg));
        stan_dev.setText(fourDecPla(st.stand_dev(avg,wav)));

        st.countCrossZero(wav, context);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : fourDecPla
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        : Double val to convert to 2 decimal places
     * <p/>
     * OUTPUTS       : returns a string of the double number
     * <p/>
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public String fourDecPla(double val){

        //sets the val double to string
        //using 10000 gives 4 decimal places
        return Double.toString(Math.round(val*10000)/10000.0d);
    }
}
